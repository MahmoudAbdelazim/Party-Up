from flask import Flask
from flask import jsonify
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

app = Flask(__name__)

db = pymysql.connect(host="localhost", user="root", password="mahmoud16", database="partyup")

pd.set_option('display.float_format', lambda x: '%.2f' % x)

personality_data = pd.read_sql('SELECT * FROM players_rates_questions', db)
personality_data = personality_data.pivot_table('rate', 'player_id', 'question_id')
print(personality_data)

user_games_data = pd.read_csv('steam_users_adjusted.csv', index_col=[0])

# K-Means Clustering
from sklearn.cluster import AgglomerativeClustering
def k_means():
    kmeans = KMeans(n_clusters=3, random_state=42).fit(personality_data.iloc[:, 1:4])
    labels = kmeans.labels_
    print(metrics.calinski_harabasz_score(personality_data.iloc[:, 1:28], labels)) # Variance Ratio Criterion
    personality_data['cluster'] = labels
    print(personality_data)
k_means()

# def get(user_id, game_id):
    # clusterNum = data[data['ID'] == id]['cluster'].tolist()[0]
    # cluster = data.loc[data['cluster'] == clusterNum]
    # distances, neighbors = knn(cluster, id)
    # sorted_cluster = cluster.iloc[neighbors.tolist()[0]]
    # cluster = sorted_cluster.copy()
    # sorted_cluster['distance'] = distances.tolist()[0]
    # ids = sorted_cluster['ID'].tolist()
    # df = sorted_cluster.iloc[[0]]
    # game_users = users_data.loc[users_data['Game'] == game]
    # for i in ids:
    #     df2 = game_users.loc[game_users['ID'] == i]
    #     if len(df2):
    #         df = df.append(sorted_cluster.loc[sorted_cluster['ID'] == i])
    # return df.loc[:, ['ID', 'cluster', 'distance']]

# KNN
# def knn(cluster, id):
#     nbrs = NearestNeighbors(n_neighbors=len(cluster), algorithm='ball_tree').fit(cluster.iloc[:, 1:28])
#     test = cluster.loc[cluster['ID'] == id].drop(columns=['ID', 'cluster'])
#     return nbrs.kneighbors(test, return_distance=True)


# @app.route('/<int:user_id>/<string:game>')
# def hello_world(user_id, game):
#     return get(user_id, game)[1:20].to_json(orient="index")
#

# if __name__ == '__main__':
#     app.run()
