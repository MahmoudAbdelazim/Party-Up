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


INSERT INTO question(question_string)VALUES ('I am energetic during the game.');
INSERT INTO question(question_string)VALUES ('I don''t talk a lot while gaming.');
INSERT INTO question(question_string)VALUES ('I feel comfortable playing with others.');
INSERT INTO question(question_string)VALUES ('I start conversations.');
INSERT INTO question(question_string)VALUES ('I like to talk to new people in games.');
INSERT INTO question(question_string)VALUES ('I don''t like to draw attention to myself.');
INSERT INTO question(question_string)VALUES ('I don''t mind being the center of attention.');
INSERT INTO question(question_string)VALUES ('I get stressed out easily.');
INSERT INTO question(question_string)VALUES ('I am relaxed most of the time.');
INSERT INTO question(question_string)VALUES ('I get upset easily.');
INSERT INTO question(question_string)VALUES ('I change my mood a lot.');
INSERT INTO question(question_string)VALUES ('I prefer playing on my own.');
INSERT INTO question(question_string)VALUES ('I am interested in knowing new people.');
INSERT INTO question(question_string)VALUES ('I get nervous when I lose.');
INSERT INTO question(question_string)VALUES ('I sympathize with others'' feelings.');
INSERT INTO question(question_string)VALUES ('I prefer making friends over winning.');
INSERT INTO question(question_string)VALUES ('I help newbies.');
INSERT INTO question(question_string)VALUES ('I am always prepared.');
INSERT INTO question(question_string)VALUES ('I pay attention to details.');
INSERT INTO question(question_string)VALUES ('I don''t stick to a gaming style.');
INSERT INTO question(question_string)VALUES ('I like order.');
INSERT INTO question(question_string)VALUES ('I exit the game when I feel I am losing.');
INSERT INTO question(question_string)VALUES ('I follow a plan in game.');
INSERT INTO question(question_string)VALUES ('I have difficulty understanding others'' plans.');
INSERT INTO question(question_string)VALUES ('I have a vivid imagination.');
INSERT INTO question(question_string)VALUES ('I have excellent ideas.');
INSERT INTO question(question_string)VALUES ('I am quick to understand things.');

CREATE VIEW player_games AS SELECT ph.player_id, game_id FROM player_handles as ph JOIN handle as h ON ph.handles_id=h.id;
CREATE VIEW players_rates_questions AS SELECT player_id, r.rate, questionid FROM rate as r JOIN question as q on r.questionid = q.id JOIN player_rates as pr on pr.rates_id=r.id;

INSERT INTO country(name) VALUES ('Egypt');