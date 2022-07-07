from flask import Flask
from flask import jsonify
import json
import pandas as pd
import numpy as np
import pymysql
import time
from matplotlib import pyplot as plt
from scipy.cluster.hierarchy import dendrogram
from sklearn.datasets import load_iris
from sklearn.cluster import AgglomerativeClustering
from sklearn.neighbors import NearestNeighbors
from sklearn.cluster import KMeans
from IPython.display import display
from sklearn import metrics
from sklearn.metrics import pairwise_distances
from imblearn.over_sampling import SMOTE
import json
from flask_mysqldb import MySQL
import os

app = Flask(__name__)

db = pymysql.connect(host="localhost", user=os.getenv('DB_USERNAME'), password=os.getenv('DB_PASSWORD'), database="partyup")

pd.set_option('display.float_format', lambda x: '%.2f' % x)

personality_data = pd.read_sql('SELECT * FROM players_rates_questions', db)
personality_data = personality_data.pivot_table('rate', 'player_id', 'questionid').reset_index()
# print(personality_data)

user_games_data = pd.read_sql('SELECT * FROM player_games', db)
# print(user_games_data)

def k_means():
    kmeans = KMeans(n_clusters=max(2, personality_data.size // 1000), random_state=42).fit(personality_data.iloc[:, 1:28])
    labels = kmeans.labels_
    # print(metrics.calinski_harabasz_score(personality_data.iloc[:, 1:28], labels)) # Variance Ratio Criterion
    personality_data['cluster'] = labels
    # print(personality_data)
k_means()

def knn(cluster, id):
    nbrs = NearestNeighbors(n_neighbors=len(cluster), algorithm='ball_tree').fit(cluster.iloc[:, 1:28])
    test = cluster.loc[cluster['player_id'] == id].drop(columns=['player_id', 'cluster'])
    return nbrs.kneighbors(test, return_distance=True)

def getDataFrame(player_id, game_id):
    clusterNum = personality_data[personality_data['player_id'] == player_id]['cluster'].tolist()[0]
    cluster = personality_data.loc[personality_data['cluster'] == clusterNum]
    distances, neighbors = knn(cluster, player_id)
    sorted_cluster = cluster.iloc[neighbors.tolist()[0]]
    sorted_cluster['distance'] = distances.tolist()[0]
    ids = sorted_cluster['player_id'].tolist()
    df = sorted_cluster.iloc[[0]]
    game_users = user_games_data.loc[user_games_data['game_id'] == game_id]
    for i in ids:
        df2 = game_users.loc[game_users['player_id'] == i]
        if len(df2):
            df = df.append(sorted_cluster.loc[sorted_cluster['player_id'] == i])
    return df.loc[:, ['player_id']]

def get(player_id, game_id):
    idsList = getDataFrame(player_id, game_id)['player_id'].tolist()
    res = []
    for id in idsList:
        res.append({'id': id})
    return res

print(get(1, 1))

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
    app.run()
