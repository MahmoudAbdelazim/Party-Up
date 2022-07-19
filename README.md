# Party-Up (Graduation Project)
This is our graduation project for the Bachelor's degree in computer science from Cairo University, Faculty of computers and AI.

## Motivation and Problem
As gaming community is growing bigger and bigger every day, the
need for matching players together in online Lobbies is evolving as well.

From that a big problem arose which is that players with different personalities get matched up together, which results in a bad gaming experience
for all of them.

So our aim is to help those and others in having a better gaming experience
through an efficient Player Recommendation system that would improve the
quality of teams.

## Our Solution
To solve this problem and offer a better gaming experience for all the
players with different personalities, we decided to make a platform that
contains all the Players and Match them with the most suitable players for
them based on their personalities.

The Player when signing up must take a personality test that will help
us define his behavior and personality.

Using a Machine Learning Model (Unsupervised Learning techniques), we match the player
with other players that are close in personality and also play the same games as well.

# The Machine Learning Model
## Datasets
The datasets we've worked on during this project in order to build the model and test its effectiveness are:
### 1- [Big 5 Personality Traits Dataset](https://www.kaggle.com/datasets/tunguz/big-five-personality-test)
Contains about 1 million records of users’ answers to the Big 5 personality
test, which gives scores for a person’s personality traits like extraversion and
agreeableness using 50 questions with answers from 1 to 5.

### 2- [Steam Games Users Dataset](https://www.kaggle.com/datasets/tamber/steam-video-games)
Contains 200K records of Steam (an online gaming platform) users, each
record contains a player ID and Game name.

## Data Preparation
We’ve adjusted the datasets we worked on to meet our project’s needs
according to the following:
### Feature Selection
We’ve selected only the features (questions) that can be relevant to our
purpose in the project in the personality test dataset, and we further adjusted
them to match our needs in the gaming platform, and this is the list of
features we’ll be working with in the personality dataset:

<img width="436" alt="fig 1" src="https://user-images.githubusercontent.com/43009893/179864764-32b84ec3-d915-44ca-81b5-e1747563a0da.png">


And we also adjusted the questions to allow players to review each others in
order to have a more realistic view of the players personalities:

<img width="434" alt="fig 2" src="https://user-images.githubusercontent.com/43009893/179864776-1a9ef478-0972-4bb3-8c39-8ede1ff10b5a.png">

And in the Gaming dataset:

<img width="439" alt="fig 3" src="https://user-images.githubusercontent.com/43009893/179864788-562d3044-4277-4ce7-a381-f89566b8a8a7.png">

### Handling Missing Values
1783 rows in the personality test dataset had missing values, which is a
small number compared to the size of the dataset, and in these records, the
whole rows had missing values not just one cell, so we simply removed
these records.

### Handling Invalid Values
The personality test dataset should contain values only between 1 and 5
inclusive, while some of the values (exactly 203108) had a value of 0 which
should not be valid, so we mapped these values to 1 instead, as they
correspond to the least value.

### Sampling
We used random sampling to sample the dataset to preserve the distribution
structure of features, we first worked with a small sample (about 10,000
records) to match the number of users in the steam dataset.


## Unsupervised Learning Techniques 
We have tried multiple ML unsupervised learning techniques in order to
know which one will serve us best throughout the project, such as
Agglomerative clustering, K-Means clustering and K-Nearest-Neighbors,
and we decided to:

Use a combination of both K-Means clustering and K-Nearest-Neighbors.

We first applied K-Means clustering using the personality dataset on the
whole dataset of users in the system, and this step needs to be done
periodically (every day for example), and when a user chooses to play a
certain game, KNN is applied to the user’s cluster in order to sort them in
ascending order of distance, and then we match the player with the closest
players in his cluster that also play the same game.

This flowchart shows the approach for finding peers:

<img width="344" alt="fig 6" src="https://user-images.githubusercontent.com/43009893/179864818-151054da-28a3-41fb-846e-ef49e5746b31.png">

# Platform Features
The website allows players to do the following:
- Sign Up and Login to the system
- Edit profile information and profile photo
- Add games and their handles in them in their profile
- Find peers playing a specific game
- Send a peer up request
- Accept or Decline a peer up request
- Review a peer (Updates the player's personality scores)
- Unpeer a peer

## Finding Peers Process
When a player requests to find a peer playing a certain game, the back-end application receives the request, retrieves the user's ID and the game's ID, and makes a request for the ML Model server, which in turn runs the ML Model with the specified parameters and returns a list of suggested player IDs which is responded back to the user in the form of profile tokens.

## Peer Review Process
When a player reviews a peer, a list of 10 questions is selected randomly from the list of personality questions (same questions in the personality test but re-phrased for reviews).

And when a player submits a review, the peer's personality scores get updated for those questions based on the average score for all persons who reviewed this player.

This allows for more realistic personality scores for all players in order to improve the quality of the service. 

# Project Structure

<img width="487" alt="fig 4" src="https://user-images.githubusercontent.com/43009893/179864855-f05c553d-970a-4ee2-a457-abe042216719.png">
The project is separated into these artifacts:

## Back-End
Used Java and Spring Boot for building the back-end server using Spring Data and Spring REST to allow communication with the front-end, also responsible for communication with the Machine Learning model server to expose the peers suggestion service.

## Front-End
Built in Angular and TypeScript and communicates with the back-end using RESTful APIs.

## ML Model
The model itself is built using SKLearn (K-Means Clustering and K-Nearest-Neighbors), while the REST API server is built using Flask to expose the service to the back-end application.

## Database
Database used is MySQL and the database schema is generated using Spring Data model entities, and the ML Model also uses views to the database to access personality-related data and apply the matching service.

# Conclusion
This project was created by:
### Mahmoud Abdelazim
- [GitHub](https://github.com/MahmoudAbdelazim)
- [LinkedIn](https://www.linkedin.com/in/mahmoudabdelazim/)

### Ahmed ShikhTawel
- [GitHub](https://github.com/ShikhTawel)
- [LinkedIn](https://www.linkedin.com/in/shikhtawel/)

### Mustafa Taha
- [GitHub](https://github.com/MustafaTaha09)
- [LinkedIn](https://www.linkedin.com/in/mustafa-taha-6ab52316a/)

### Kamel Mohamed
- [GitHub](https://github.com/KamelMohamed2000)
- [LinkedIn](https://www.linkedin.com/in/kamel-mohamed-33510a184/)

### Amr Bumadian
- [GitHub](https://github.com/AmrBumadian)
- [LinkedIn](https://www.linkedin.com/in/amr-bumadian/)

Under the supervision of Dr. Soha Makady, Cairo University, Faculty of Computers and AI, Computer Science department.

