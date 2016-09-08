DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS users;


DROP TABLE IF EXISTS person_sex;
DROP TABLE IF EXISTS current_task_status;
DROP TABLE IF EXISTS rate_type;
DROP TABLE IF EXISTS slot_type;
DROP TABLE IF EXISTS user_roles;


DROP SEQUENCE IF EXISTS GLOBAL_SEQ;

--   phone and email list VARCHAR NOT NULL

CREATE SEQUENCE GLOBAL_SEQ START 100000;

CREATE TABLE person_sex
(
  id  BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  sex VARCHAR
);

CREATE TABLE current_task_status
(
  id          BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  status_type VARCHAR
);

CREATE TABLE rate_type
(
  id        BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  rate_type VARCHAR
);

CREATE TABLE slot_type
(
  id        BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  slot_type VARCHAR
);

CREATE TABLE user_roles
(
  id   BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  role VARCHAR
);


CREATE TABLE users
(
  --   person
  id         BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  first_name VARCHAR,
  last_name  VARCHAR,
  sex        BIGINT,
  enabled    BOOL               DEFAULT FALSE,
  photo_URL  VARCHAR,
  --   user
  login      VARCHAR NOT NULL UNIQUE,
  password   VARCHAR NOT NULL,
  --   for now just one
  role       BIGINT,

  FOREIGN KEY (sex) REFERENCES person_sex (id),
  FOREIGN KEY (role) REFERENCES user_roles (id)
);

CREATE TABLE comments
(
  id      BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  content VARCHAR,
  date    TIMESTAMP NOT NULL DEFAULT now(),
  user_id BIGINT,

  FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE events
(
  id          BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  author_id   BIGINT,
  tag_name    VARCHAR,
  address     VARCHAR,
  description VARCHAR,
  logo_URL    VARCHAR,


  FOREIGN KEY (author_id) REFERENCES users (id)
);



