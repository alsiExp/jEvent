DROP TABLE IF EXISTS slots;
DROP TABLE IF EXISTS tracks;
DROP TABLE IF EXISTS events_visitors;
DROP TABLE IF EXISTS visitors_events_speakers;
DROP TABLE IF EXISTS visitors_events_visits;
DROP TABLE IF EXISTS task_statuses_tasks;
DROP TABLE IF EXISTS task_statuses;
DROP TABLE IF EXISTS task_user_target;
DROP TABLE IF EXISTS task_attach;
DROP TABLE IF EXISTS events_comments;
DROP TABLE IF EXISTS rates;

DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS visitors;
DROP TABLE IF EXISTS partners;


DROP TABLE IF EXISTS person_sex;
DROP TABLE IF EXISTS current_task_status;
DROP TABLE IF EXISTS rate_type;
DROP TABLE IF EXISTS slot_type;
DROP TABLE IF EXISTS user_roles;


DROP SEQUENCE IF EXISTS GLOBAL_SEQ;

--   phone and email list VARCHAR NOT NULL

CREATE SEQUENCE GLOBAL_SEQ START 110000;

CREATE TABLE person_sex
(
  id  BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  sex VARCHAR UNIQUE
);

CREATE TABLE current_task_status
(
  id     BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  status VARCHAR UNIQUE
);

CREATE TABLE rate_type
(
  id   BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  type VARCHAR UNIQUE
);

CREATE TABLE slot_type
(
  id   BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  type VARCHAR UNIQUE
);

CREATE TABLE user_roles
(
  id   BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  role VARCHAR UNIQUE
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

CREATE TABLE visitors
(
  --   person
  id         BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  first_name VARCHAR,
  last_name  VARCHAR,
  sex        BIGINT,
  enabled    BOOL               DEFAULT FALSE,
  photo_URL  VARCHAR,
  --   visitor
  birthday    TIMESTAMP,
  registered_date    TIMESTAMP NOT NULL DEFAULT now(),
  email VARCHAR,
  phone VARCHAR,

  github_account VARCHAR,
  linkedin_account VARCHAR,
  twitter_account VARCHAR,

  employer VARCHAR,
  biography VARCHAR,
  description VARCHAR,

  cost MONEY,


  FOREIGN KEY (sex) REFERENCES person_sex (id)
);

CREATE TABLE partners
(
  id      BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  name  VARCHAR,
  email VARCHAR,
  phone VARCHAR,
  description        VARCHAR,
  logo_URL           VARCHAR

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
  id                 BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  name           VARCHAR,
  author_id          BIGINT,
  tag_name           VARCHAR,
  address            VARCHAR,
  description        VARCHAR,
  logo_URL           VARCHAR,

  FOREIGN KEY (author_id) REFERENCES users (id)
);


CREATE TABLE rates
(
  id         BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  name VARCHAR,
  event_id BIGINT,
  rate_type  BIGINT,
  start_date TIMESTAMP NOT NULL,
  end_date   TIMESTAMP NOT NULL,
  cost       MONEY,

  FOREIGN KEY (event_id) REFERENCES events (id),
  FOREIGN KEY (rate_type) REFERENCES rate_type (id)
);


CREATE TABLE tracks
(
  id          BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  name                        VARCHAR,
  event_id  BIGINT,
  description VARCHAR,
  position  INT,

  FOREIGN KEY (event_id) REFERENCES events (id)
);



CREATE TABLE visitors_events_speakers
(
  id                 BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  visitor_id   BIGINT,
  event_id BIGINT,
  price int,

  FOREIGN KEY (event_id) REFERENCES events (id),
  FOREIGN KEY (visitor_id) REFERENCES visitors (id)
);

CREATE TABLE visitors_events_visits
(
  visitor_id   BIGINT,
  event_id BIGINT,

  FOREIGN KEY (event_id) REFERENCES events (id),
  FOREIGN KEY (visitor_id) REFERENCES visitors (id)
);

CREATE TABLE slots
(
  id                          BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  name                        VARCHAR,
  track_id                    BIGINT,
  start                       TIMESTAMP NOT NULL,
  visitors_events_speaker_id BIGINT,
  slot_type                   BIGINT,
  grade                       INT,

  FOREIGN KEY (slot_type) REFERENCES slot_type (id),
  FOREIGN KEY (track_id) REFERENCES tracks (id),
  FOREIGN KEY (visitors_events_speaker_id) REFERENCES visitors_events_speakers (id)

);


CREATE TABLE events_comments
(
  event_id   BIGINT,
  comment_id BIGINT,

  FOREIGN KEY (event_id) REFERENCES events (id),
  FOREIGN KEY (comment_id) REFERENCES comments (id)
);



CREATE TABLE events_visitors
(
  visitor_id   BIGINT,
  event_id BIGINT,

  FOREIGN KEY (event_id) REFERENCES events (id),
  FOREIGN KEY (visitor_id) REFERENCES visitors (id)
);

CREATE TABLE task_statuses
(
  id      BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  user_id BIGINT,
  creation_time    TIMESTAMP NOT NULL DEFAULT now(),
  current_task_status_id BIGINT,
  description VARCHAR NOT NULL,

  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (current_task_status_id) REFERENCES current_task_status (id)
);

CREATE TABLE tasks
(
  id      BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  name VARCHAR,
  user_id BIGINT,
  start    TIMESTAMP NOT NULL DEFAULT now(),
  deadline    TIMESTAMP,
  description VARCHAR,

  FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE task_statuses_tasks
(
  task_status_id BIGINT,
  task_id BIGINT,

  FOREIGN KEY (task_status_id) REFERENCES task_statuses (id),
  FOREIGN KEY (task_id) REFERENCES tasks (id)
);

CREATE TABLE task_user_target
(
  task_id BIGINT,
  user_id BIGINT,

  FOREIGN KEY (task_id) REFERENCES tasks (id),
  FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE task_attach
(
  task_id BIGINT,
  attach_id BIGINT,

  FOREIGN KEY (task_id) REFERENCES tasks (id)
);



