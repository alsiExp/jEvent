DROP TABLE IF EXISTS event_partners;
DROP TABLE IF EXISTS speech_participants;
DROP TABLE IF EXISTS speeches_speech_tags;

DROP TABLE IF EXISTS speeches;
DROP TABLE IF EXISTS speech_tags;
DROP TABLE IF EXISTS visitors;


DROP TABLE IF EXISTS rates;
DROP TABLE IF EXISTS user_roles;

DROP TABLE IF EXISTS emails;
DROP TABLE IF EXISTS githubaccs;
DROP TABLE IF EXISTS twitteraccs;

DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS participants;
DROP TABLE IF EXISTS partners;

DROP TABLE IF EXISTS person_sex;
DROP TABLE IF EXISTS partner_status;
DROP TABLE IF EXISTS rate_type;


DROP SEQUENCE IF EXISTS GLOBAL_SEQ;

CREATE SEQUENCE GLOBAL_SEQ START 100000;


CREATE TABLE rate_type
(
  id   BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  type VARCHAR UNIQUE
);


CREATE TABLE partner_status
(
  id   BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  type VARCHAR UNIQUE
);


CREATE TABLE users
(
  --   person
  id                     BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  full_name              VARCHAR NOT NULL CHECK (full_name <> ''),
  enabled                BOOL               DEFAULT FALSE,
  photo_URL              VARCHAR,
  --   user
  login                  VARCHAR NOT NULL CHECK (login <> ''),
  password               VARCHAR NOT NULL CHECK (password <> ''),

  jira_login             VARCHAR,
  jira_password          VARCHAR,

  jira_valid_credentials BOOL               DEFAULT FALSE

);
CREATE UNIQUE INDEX unique_login
  ON users (login);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);


CREATE TABLE participants
(
  --   person
  id              BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  full_name       VARCHAR   NOT NULL CHECK (full_name <> ''),
  full_name_en    VARCHAR,
  enabled         BOOL               DEFAULT FALSE,
  photo_URL       VARCHAR,
  --   participant
  birthday        TIMESTAMP,
  registered_date TIMESTAMP NOT NULL DEFAULT now(),
  phone           VARCHAR,
  skype           VARCHAR,
  city            VARCHAR,
  employer        VARCHAR,
  biography       VARCHAR,
  biography_en    VARCHAR,
  background      VARCHAR,
  travel_help     VARCHAR

);

CREATE TABLE githubaccs
(
  id           BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  account_link VARCHAR,
  owner_id     BIGINT,

  FOREIGN KEY (owner_id) REFERENCES participants (id)
);

CREATE TABLE twitteraccs
(
  id           BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  account_link VARCHAR,
  owner_id     BIGINT,

  FOREIGN KEY (owner_id) REFERENCES participants (id)
);

CREATE TABLE emails
(
  id       BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  email    VARCHAR NOT NULL CHECK (email <> ''),
  main     BOOL,
  owner_id BIGINT,

  FOREIGN KEY (owner_id) REFERENCES participants (id) ON DELETE CASCADE

);


CREATE TABLE partners
(
  id            BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  name          VARCHAR NOT NULL CHECK (name <> ''),
  contact_email VARCHAR,
  contact_phone VARCHAR,
  contact_name  VARCHAR,
  description   VARCHAR,
  logo_URL      VARCHAR
);


CREATE TABLE events
(
  id          BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  name        VARCHAR NOT NULL CHECK (name <> ''),
  jira_link   VARCHAR,
  version     VARCHAR NOT NULL CHECK (version <> ''),
  start_date  TIMESTAMP,
  jira_sync   TIMESTAMP,
  description VARCHAR,
  logo_URL    VARCHAR,
  jira_key    VARCHAR,
  jira_id     INT                DEFAULT 0

);


CREATE TABLE rates
(
  id         BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  name       VARCHAR   NOT NULL CHECK (name <> ''),
  event_id   BIGINT    NOT NULL,
  rate_type  BIGINT    NOT NULL,
  start_date TIMESTAMP NOT NULL,
  end_date   TIMESTAMP NOT NULL,
  cost       NUMERIC(20, 2)     DEFAULT 0,

  FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE,
  FOREIGN KEY (rate_type) REFERENCES rate_type (id) ON DELETE CASCADE
);


CREATE TABLE visitors
(
  id             BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  participant_id BIGINT NOT NULL,
  event_id       BIGINT NOT NULL,
  buy_date       TIMESTAMP,
  pay_comment    VARCHAR,
  rate_id        BIGINT NOT NULL,
  real_cost      NUMERIC(20, 2)     DEFAULT 0,

  FOREIGN KEY (participant_id) REFERENCES participants (id) ON DELETE CASCADE,
  FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE,
  FOREIGN KEY (rate_id) REFERENCES rates (id)
);

CREATE TABLE speeches
(
  id               BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  event_id         BIGINT,
  partner_id       BIGINT,
  short_desc       VARCHAR,
  jira_status      VARCHAR NOT NULL   DEFAULT ('Local'),
  jira_link        VARCHAR,
  sync_time        TIMESTAMP,
  rating           NUMERIC(8, 4)      DEFAULT 0,

  jira_id          INT                DEFAULT 0,
  jira_key         VARCHAR,
  jira_resolution  VARCHAR,
  jira_comments    VARCHAR,

  name             VARCHAR,
  full_desc        VARCHAR,
  viewer_value     VARCHAR,
  focus            VARCHAR,
  plan             VARCHAR,

  speaker_cost     NUMERIC(20, 2)     DEFAULT 0,
  name_en          VARCHAR,
  full_desc_en     VARCHAR,
  short_desc_en    VARCHAR,

  FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE,
  FOREIGN KEY (partner_id) REFERENCES partners (id)
);

CREATE TABLE speech_tags
(
  id  BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  tag VARCHAR UNIQUE

);

CREATE TABLE speech_participants
(
  speech_id      BIGINT,
  participant_id BIGINT,

  PRIMARY KEY (speech_id, participant_id),
  FOREIGN KEY (speech_id) REFERENCES speeches (id) ON DELETE CASCADE,
  FOREIGN KEY (participant_id) REFERENCES participants (id) ON DELETE CASCADE
);


CREATE TABLE speeches_speech_tags
(
  speech_id BIGINT,
  tag_id    BIGINT,

  PRIMARY KEY (speech_id, tag_id),
  FOREIGN KEY (speech_id) REFERENCES speeches (id) ON DELETE CASCADE,
  FOREIGN KEY (tag_id) REFERENCES speech_tags (id) ON DELETE CASCADE
);

CREATE TABLE event_partners
(
  id         BIGINT PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  event_id   BIGINT,
  partner_id BIGINT,
  status_id  BIGINT,
  payment    NUMERIC(20, 2)     DEFAULT 0,

  FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE,
  FOREIGN KEY (partner_id) REFERENCES partners (id) ON DELETE CASCADE,
  FOREIGN KEY (status_id) REFERENCES partner_status (id) ON DELETE CASCADE
);