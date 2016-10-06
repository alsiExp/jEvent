-- old tables
DROP TABLE IF EXISTS events_visitors;
DROP TABLE IF EXISTS visitors_events_visits;
DROP TABLE IF EXISTS visitors_events_speakers;
DROP TABLE IF EXISTS task_statuses_tasks;
DROP TABLE IF EXISTS events_by_rate_confirmed_visitors;


DROP TABLE IF EXISTS slots;
DROP TABLE IF EXISTS tracks;
DROP TABLE IF EXISTS events_probable_speakers;
DROP TABLE IF EXISTS events_confirmed_visitors;
DROP TABLE IF EXISTS task_statuses;
DROP TABLE IF EXISTS task_user_target;
DROP TABLE IF EXISTS task_attach_events;
DROP TABLE IF EXISTS task_attach_visitors;
DROP TABLE IF EXISTS task_attach_partners;
DROP TABLE IF EXISTS events_comments;
DROP TABLE IF EXISTS visitors_comments;
DROP TABLE IF EXISTS tasks_comments;
DROP TABLE IF EXISTS rates;
DROP TABLE IF EXISTS user_roles;

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


DROP SEQUENCE IF EXISTS GLOBAL_SEQ;

CREATE SEQUENCE GLOBAL_SEQ START 100000;

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

CREATE TABLE users
(
  --   person
  id         BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  first_name VARCHAR NOT NULL CHECK (first_name <> ''),
  last_name  VARCHAR,
  sex        BIGINT,
  enabled    BOOL               DEFAULT FALSE,
  photo_URL  VARCHAR,
  --   user
  login      VARCHAR NOT NULL CHECK (login <> ''),
  password   VARCHAR NOT NULL CHECK (password <> ''),

  FOREIGN KEY (sex) REFERENCES person_sex (id)
);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE visitors
(
  --   person
  id               BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  first_name       VARCHAR   NOT NULL CHECK (first_name <> ''),
  last_name        VARCHAR,
  sex              BIGINT,
  enabled          BOOL               DEFAULT FALSE,
  photo_URL        VARCHAR,
  --   visitor
  birthday         TIMESTAMP,
  registered_date  TIMESTAMP NOT NULL DEFAULT now(),
  email            VARCHAR   NOT NULL CHECK (first_name <> ''),
  phone            VARCHAR,

  github_account   VARCHAR,
  linkedin_account VARCHAR,
  twitter_account  VARCHAR,

  employer         VARCHAR,
  biography        VARCHAR,
  description      VARCHAR,

  cost             NUMERIC(20, 2),


  FOREIGN KEY (sex) REFERENCES person_sex (id)
);

CREATE TABLE partners
(
  id          BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  name        VARCHAR NOT NULL CHECK (name <> ''),
  email       VARCHAR,
  phone       VARCHAR,
  description VARCHAR,
  logo_URL    VARCHAR

);


CREATE TABLE comments
(
  id      BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  content VARCHAR   NOT NULL CHECK (content <> ''),
  date    TIMESTAMP NOT NULL DEFAULT now(),
  user_id BIGINT    NOT NULL,

  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);


CREATE TABLE events
(
  id          BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  name        VARCHAR NOT NULL CHECK (name <> ''),
  author_id   BIGINT  NOT NULL,
  tag_name    VARCHAR NOT NULL CHECK (tag_name <> ''),
  start       TIMESTAMP,
  address     VARCHAR,
  description VARCHAR,
  logo_URL    VARCHAR,

  FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE CASCADE
);


CREATE TABLE rates
(
  id         BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  name       VARCHAR   NOT NULL CHECK (name <> ''),
  event_id   BIGINT    NOT NULL,
  rate_type  BIGINT    NOT NULL,
  start_date TIMESTAMP NOT NULL,
  end_date   TIMESTAMP NOT NULL,
  cost       NUMERIC(20, 2),

  FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE,
  FOREIGN KEY (rate_type) REFERENCES rate_type (id) ON DELETE CASCADE
);


CREATE TABLE tracks
(
  id          BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  name        VARCHAR NOT NULL CHECK (name <> ''),
  event_id    BIGINT  NOT NULL,
  description VARCHAR,

  FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE
);

CREATE TABLE events_confirmed_visitors
(
  id         BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  visitor_id BIGINT    NOT NULL,
  event_id   BIGINT    NOT NULL,
  buy_date   TIMESTAMP NOT NULL,
  comment    VARCHAR,
  rate_id    BIGINT    NOT NULL,

  FOREIGN KEY (visitor_id) REFERENCES visitors (id) ON DELETE CASCADE,
  FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE,
  FOREIGN KEY (rate_id) REFERENCES rates (id) ON DELETE CASCADE
);

CREATE TABLE slots
(
  id               BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  name             VARCHAR,
  track_id         BIGINT,
  start            TIMESTAMP NOT NULL,
  visitor_id       BIGINT,
  slot_description VARCHAR,
  slot_type        BIGINT,
  grade            INT,
  price            INT,

  FOREIGN KEY (visitor_id) REFERENCES visitors (id) ON DELETE CASCADE,
  FOREIGN KEY (slot_type) REFERENCES slot_type (id) ON DELETE CASCADE,
  FOREIGN KEY (track_id) REFERENCES tracks (id) ON DELETE CASCADE

);


CREATE TABLE events_comments
(
  event_id   BIGINT,
  comment_id BIGINT,

  PRIMARY KEY (event_id, comment_id),
  FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE,
  FOREIGN KEY (comment_id) REFERENCES comments (id) ON DELETE CASCADE
);

CREATE TABLE visitors_comments
(
  visitor_id BIGINT,
  comment_id BIGINT,

  PRIMARY KEY (visitor_id, comment_id),
  FOREIGN KEY (visitor_id) REFERENCES visitors (id) ON DELETE CASCADE,
  FOREIGN KEY (comment_id) REFERENCES comments (id) ON DELETE CASCADE
);


CREATE TABLE events_probable_speakers
  -- probableSpeakers
(
  id                 BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  visitor_id         BIGINT,
  event_id           BIGINT,
  send_date          TIMESTAMP NOT NULL DEFAULT now(),
  speech_name        VARCHAR,
  speech_description VARCHAR,
  wish_Price         NUMERIC(20, 2),

  FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE,
  FOREIGN KEY (visitor_id) REFERENCES visitors (id) ON DELETE CASCADE
);
CREATE TABLE tasks
(
  id          BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  name        VARCHAR   NOT NULL CHECK (name <> ''),
  user_id     BIGINT,
  start       TIMESTAMP NOT NULL DEFAULT now(),
  deadline    TIMESTAMP,
  description VARCHAR,
  active      BOOL               DEFAULT FALSE,

  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE task_statuses
(
  id                     BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  user_id                BIGINT,
  task_id                BIGINT    NOT NULL,
  creation_time          TIMESTAMP NOT NULL DEFAULT now(),
  current_task_status_id BIGINT,
  description            VARCHAR   NOT NULL CHECK (description <> ''),

  FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY (current_task_status_id) REFERENCES current_task_status (id) ON DELETE CASCADE
);

CREATE TABLE tasks_comments
(
  task_id    BIGINT,
  comment_id BIGINT,

  PRIMARY KEY (task_id, comment_id),
  FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE CASCADE,
  FOREIGN KEY (comment_id) REFERENCES comments (id) ON DELETE CASCADE
);

CREATE TABLE task_user_target
(
  task_id BIGINT,
  user_id BIGINT,

  PRIMARY KEY (task_id, user_id),
  FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE task_attach_events
(
  task_id  BIGINT,
  event_id BIGINT,

  PRIMARY KEY (task_id, event_id),
  FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE CASCADE,
  FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE
);

CREATE TABLE task_attach_visitors
(
  task_id    BIGINT,
  visitor_id BIGINT,

  PRIMARY KEY (task_id, visitor_id),
  FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE CASCADE,
  FOREIGN KEY (visitor_id) REFERENCES visitors (id) ON DELETE CASCADE
);

CREATE TABLE task_attach_partners
(
  task_id    BIGINT,
  partner_id BIGINT,

  PRIMARY KEY (task_id, partner_id),
  FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE CASCADE,
  FOREIGN KEY (partner_id) REFERENCES partners (id) ON DELETE CASCADE
);



