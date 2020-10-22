INSERT INTO user (id, username, password, about) VALUES (12, 'Matteo', '1234StrongPassword', 'I love to code');
INSERT INTO user (id, username, password, about) VALUES (1, 'Fred', 'abcd', '');
INSERT INTO user (id, username, password, about) VALUES (3,'Albert', '123abc', 'I like cycling');

INSERT INTO post (id, user_id, text) VALUES (1, 12, 'Text 1');
INSERT INTO post (id, user_id, text) VALUES (2, 12, 'Text 2');