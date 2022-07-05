use partyup;

INSERT INTO role(name) VALUES('ROLE_ADMIN');
INSERT INTO role(name) VALUES('ROLE_USER');

INSERT INTO game(id, name) VALUES (1, 'League Of Legends');
INSERT INTO game(id, name) VALUES (2, 'Call Of Duty');
INSERT INTO game(id, name) VALUES (3, 'Apex');
INSERT INTO game(id, name) VALUES (4, 'PUBG');
INSERT INTO game(id, name) VALUES (5, 'Read Dead Redemption');
INSERT INTO game(id, name) VALUES (6, 'Battlefield');
INSERT INTO game(id, name) VALUES (7, 'GTA V');


INSERT INTO question(id, question_string) VALUES (1, 'I am energetic during the game.');
INSERT INTO question(id, question_string) VALUES (2, 'I don''t talk a lot while gaming.');
INSERT INTO question(id, question_string)VALUES (3, 'I feel comfortable playing with others.');
INSERT INTO question(id, question_string)VALUES (4, 'I start conversations.');
INSERT INTO question(id, question_string)VALUES (5, 'I like to talk to new people in games.');
INSERT INTO question(id, question_string)VALUES (6, 'I don''t like to draw attention to myself.');
INSERT INTO question(id, question_string)VALUES (7, 'I don''t mind being the center of attention.');
INSERT INTO question(id, question_string)VALUES (8, 'I get stressed out easily.');
INSERT INTO question(id, question_string)VALUES (9, 'I am relaxed most of the time.');
INSERT INTO question(id, question_string)VALUES (10, 'I get upset easily.');
INSERT INTO question(id, question_string)VALUES (11, 'I change my mood a lot.');
INSERT INTO question(id, question_string)VALUES (12, 'I prefer playing on my own.');
INSERT INTO question(id, question_string)VALUES (13, 'I am interested in knowing new people.');
INSERT INTO question(id, question_string)VALUES (14, 'I get nervous when I lose.');
INSERT INTO question(id, question_string)VALUES (15, 'I sympathize with others'' feelings.');
INSERT INTO question(id, question_string)VALUES (16, 'I prefer making friends over winning.');
INSERT INTO question(id, question_string)VALUES (17, 'I help newbies.');
INSERT INTO question(id, question_string)VALUES (18, 'I am always prepared.');
INSERT INTO question(id, question_string)VALUES (19, 'I pay attention to details.');
INSERT INTO question(id, question_string)VALUES (20, 'I don''t stick to a gaming style.');
INSERT INTO question(id, question_string)VALUES (21, 'I like order.');
INSERT INTO question(id, question_string)VALUES (22, 'I exit the game when I feel I am losing.');
INSERT INTO question(id, question_string)VALUES (23, 'I follow a plan in game.');
INSERT INTO question(id, question_string)VALUES (24, 'I have difficulty understanding others'' plans.');
INSERT INTO question(id, question_string)VALUES (25, 'I have a vivid imagination.');
INSERT INTO question(id, question_string)VALUES (26, 'I have excellent ideas.');
INSERT INTO question(id, question_string)VALUES (27, 'I am quick to understand things.');

CREATE VIEW player_games AS SELECT ph.player_id, game_id FROM player_handles as ph JOIN handle as h ON ph.handles_id=h.id;
CREATE VIEW players_rates_questions AS SELECT player_id, r.rate, questionid FROM rate as r JOIN question as q on r.questionid = q.id JOIN player_rates as pr on pr.rates_id=r.id;

LOAD DATA INFILE 'countries.txt' INTO TABLE country;
