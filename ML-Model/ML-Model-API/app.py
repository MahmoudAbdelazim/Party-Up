import json
import os

import pandas as pd
import pymysql
from flask import Flask
from sklearn.cluster import KMeans
from sklearn.neighbors import NearestNeighbors

app = Flask(__name__)


def k_means():
    global personality_data, user_games_data, db
    db = pymysql.connect(host=os.getenv('DB_HOST'), user=os.getenv('DB_USERNAME'), password=os.getenv('DB_PASSWORD'),
                         database=os.getenv('DB_DATABASE'))
    user_games_data = pd.read_sql('SELECT * FROM player_games', db)
    personality_data = pd.read_sql('SELECT * FROM players_rates_questions', db)
    personality_data = personality_data.pivot_table('rate', 'player_id', 'questionid').reset_index()
    num_clusters = max(2, len(personality_data) // 1000)
    kmeans = KMeans(n_clusters=num_clusters, random_state=42).fit(personality_data.iloc[:, 1:28])
    labels = kmeans.labels_
    # print(metrics.calinski_harabasz_score(personality_data.iloc[:, 1:28], labels)) # Variance Ratio Criterion
    personality_data['cluster'] = labels
    return num_clusters


def knn(cluster, player_dataframe):
    nbrs = NearestNeighbors(n_neighbors=len(cluster), algorithm='ball_tree').fit(cluster.iloc[:, 1:28])
    return nbrs.kneighbors(player_dataframe, return_distance=True)


def isPeer(player1_id, player2_id):
    peers_data = pd.read_sql('SELECT * FROM player_peers WHERE player_id = {}'.format(player1_id), db)
    if ((peers_data['player_id'] == player1_id) & (peers_data['peers_id'] == player2_id)).any():
        return True
    return False


def addDataFromCluster(player_id, game_id, cluster_num, ids_list, size, player_dataframe):
    cluster = personality_data.loc[personality_data['cluster'] == cluster_num]
    distances, neighbors = knn(cluster, player_dataframe)
    sorted_cluster = cluster.iloc[neighbors.tolist()[0]]
    sorted_cluster['distance'] = distances.tolist()[0]
    ids = sorted_cluster['player_id'].tolist()
    game_users = user_games_data.loc[user_games_data['game_id'] == game_id]
    for i in ids:
        if isPeer(player_id, i):
            continue
        df2 = game_users.loc[game_users['player_id'] == i]
        if len(df2):
            ids_list.append(sorted_cluster.loc[sorted_cluster['player_id'] == i]['player_id'].tolist()[0])
            if len(ids_list) == size:
                return ids_list
    return ids_list


def getDataFrame(player_id, game_id, size, num_clusters):
    cluster_num = personality_data[personality_data['player_id'] == player_id]['cluster'].tolist()[0]
    cluster = personality_data.loc[personality_data['cluster'] == cluster_num]
    player_dataframe = cluster.loc[cluster['player_id'] == player_id].drop(columns=['player_id', 'cluster'])
    distances, neighbors = knn(cluster, player_dataframe)
    sorted_cluster = cluster.iloc[neighbors.tolist()[0]]
    sorted_cluster['distance'] = distances.tolist()[0]
    ids_list = []
    upper_cluster = cluster_num
    lower_cluster = cluster_num - 1
    while len(ids_list) < size:
        if upper_cluster < num_clusters:
            ids_list = addDataFromCluster(player_id, game_id, upper_cluster, ids_list, size, player_dataframe)
        if len(ids_list) < size and lower_cluster >= 0:
            ids_list = addDataFromCluster(player_id, game_id, lower_cluster, ids_list, size, player_dataframe)
        if upper_cluster >= num_clusters and lower_cluster < 0:
            break
        upper_cluster += 1
        lower_cluster -= 1
    return ids_list


def get(player_id, game_id):
    num_clusters = k_means()
    idsList = getDataFrame(player_id, game_id, 10, num_clusters)
    res = []
    for id in idsList:
        if (player_id != id):
            res.append({'id': id})
    return res


@app.route('/<player_id>/<int:game_id>')
def hello_world(player_id, game_id):
    player_id = int(player_id)
    game_id = int(game_id)
    player_ids = get(player_id, game_id)
    response = app.response_class(
        response=json.dumps(player_ids),
        status=200,
        mimetype='application/json'
    )
    return response


if __name__ == '__main__':
    app.run(host='0.0.0.0')
