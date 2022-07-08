use partyup;

INSERT INTO role(name) VALUES('ROLE_ADMIN');
INSERT INTO role(name) VALUES('ROLE_USER');

INSERT INTO game(name) VALUES ('League Of Legends');
INSERT INTO game(name) VALUES ('Call Of Duty');
INSERT INTO game(name) VALUES ('Apex');
INSERT INTO game(name) VALUES ('PUBG');
INSERT INTO game(name) VALUES ('Read Dead Redemption');
INSERT INTO game(name) VALUES ('Battlefield');
INSERT INTO game(name) VALUES ('GTA V');
INSERT INTO game(name) VALUES ('Valorant');
INSERT INTO game(name) VALUES ('Rainbow Six Siege');


INSERT INTO question(question_string, review_question_string)VALUES ('I am energetic during the game.', 'Player is energetic during the game');
INSERT INTO question(question_string, review_question_string)VALUES ('I don''t talk a lot while gaming.', 'Player doesn''t talk a lot while gaming');
INSERT INTO question(question_string, review_question_string)VALUES ('I feel comfortable playing with others.', 'Player plays well in a team');
INSERT INTO question(question_string, review_question_string)VALUES ('I start conversations.', 'Player starts conversations');
INSERT INTO question(question_string, review_question_string)VALUES ('I like to talk to new people in games.', 'Player seemed to like talking to new people');
INSERT INTO question(question_string, review_question_string)VALUES ('I don''t like to draw attention to myself.', 'Player doesn''t draw a lot of attention to himself');
INSERT INTO question(question_string, review_question_string)VALUES ('I don''t mind being the center of attention.', 'Player likes to be the center of attention');
INSERT INTO question(question_string, review_question_string)VALUES ('I get stressed out easily.', 'Player gets stressed out easily');
INSERT INTO question(question_string, review_question_string)VALUES ('I am relaxed most of the time.', 'Player is relaxed most of the time');
INSERT INTO question(question_string, review_question_string)VALUES ('I get upset easily.', 'Player gets upset easily');
INSERT INTO question(question_string, review_question_string)VALUES ('I change my mood a lot.', 'Player changes mood a lot during the game');
INSERT INTO question(question_string, review_question_string)VALUES ('I prefer playing on my own.', 'Player likes playing on his own even in a team game');
INSERT INTO question(question_string, review_question_string)VALUES ('I am interested in knowing new people.', 'Player seemed interested to know new people');
INSERT INTO question(question_string, review_question_string)VALUES ('I get nervous when I lose.', 'Player gets nervous when they lose');
INSERT INTO question(question_string, review_question_string)VALUES ('I sympathize with others'' feelings.', 'Player respects other players in team');
INSERT INTO question(question_string, review_question_string)VALUES ('I prefer making friends over winning.', 'Player seems to prefer making friends over winning');
INSERT INTO question(question_string, review_question_string)VALUES ('I help newbies.', 'Player helps newbie teammates');
INSERT INTO question(question_string, review_question_string)VALUES ('I am always prepared.', 'Player seems confident and prepared');
INSERT INTO question(question_string, review_question_string)VALUES ('I pay attention to details.', 'Player pays attention to details');
INSERT INTO question(question_string, review_question_string)VALUES ('I don''t stick to a gaming style.', 'Player does not stick to a gaming style');
INSERT INTO question(question_string, review_question_string)VALUES ('I like order.', 'Player likes order');
INSERT INTO question(question_string, review_question_string)VALUES ('I exit the game when I feel I am losing.', 'Player exits the game when they feel like losing');
INSERT INTO question(question_string, review_question_string)VALUES ('I follow a plan in game.', 'Player follows a plan in game');
INSERT INTO question(question_string, review_question_string)VALUES ('I have difficulty understanding others'' plans.', 'Player has difficulty understanding plans');
INSERT INTO question(question_string, review_question_string)VALUES ('I have a vivid imagination.', 'Player has a vivid imagination');
INSERT INTO question(question_string, review_question_string)VALUES ('I have excellent ideas.', 'Player has excellent ideas');
INSERT INTO question(question_string, review_question_string)VALUES ('I am quick to understand things.', 'Player is quick to understand things');

CREATE VIEW player_games AS SELECT ph.player_id, game_id FROM player_handles as ph JOIN handle as h ON ph.handles_id=h.id;
CREATE VIEW players_rates_questions AS SELECT player_id, r.rate, questionid FROM rate as r JOIN question as q on r.questionid = q.id JOIN player_rates as pr on pr.rates_id=r.id;

INSERT INTO country(name) VALUES ('Egypt');