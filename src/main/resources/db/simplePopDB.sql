DELETE FROM speeches_speech_tags;

DELETE FROM speech_participants;

DELETE FROM rates;
DELETE FROM emails;
DELETE FROM githubaccs;
DELETE FROM twitteraccs;
DELETE FROM speech_tags;


DELETE FROM speeches;
DELETE FROM events;
DELETE FROM users;
DELETE FROM visitors;
DELETE FROM partners;
DELETE FROM participants;


DELETE FROM partner_status;
DELETE FROM rate_type;
DELETE FROM user_roles;

ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;


INSERT INTO partner_status (id, type)
VALUES (90010, 'GOLD'), (90011, 'SILVER'), (90012, 'BRONZE'), (90013, 'INFO');

INSERT INTO rate_type (id, type)
VALUES (90030, 'ONLINE_LITE'), (90031, 'ONLINE_STANDARD'), (90032, 'ONLINE_BUSINESS'),
  (90033, 'PERSONAL_LITE'), (90034, 'PERSONAL_STANDARD'), (90035, 'PERSONAL_BUSINESS');


/*
  password => $2a$10$Sh0ZD2NFrzRRJJEKEWn8l.92ROEuzlVyzB9SV1AM8fdluPR0aC1ni
  admin    => $2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju
  user     => $2a$10$MZJEp4xVP4k8ga2Du5Czqe4OFq3yDl37nBYjnMidEspAMaWkLOW4K
*/

INSERT INTO users (full_name, enabled, photo_url, login, password)
VALUES
  ('Админ Вася', TRUE, NULL, 'admin', '$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju'),
  ('Юзер Петя', TRUE, NULL, 'user', '$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju');

INSERT INTO user_roles(user_id, role)
VALUES
  (100000, 'ROLE_USER'),
  (100000, 'ROLE_ADMIN'),
  (100001, 'ROLE_USER');