DELETE FROM slots;
DELETE FROM tracks;
DELETE FROM events_probable_speakers;
DELETE FROM events_by_rate_confirmed_visitors;
DELETE FROM task_statuses;
DELETE FROM task_user_target;
DELETE FROM task_attach_events;
DELETE FROM task_attach_visitors;
DELETE FROM task_attach_partners;
DELETE FROM events_comments;
DELETE FROM visitors_comments;
DELETE FROM tasks_comments;
DELETE FROM rates;

DELETE FROM tasks;
DELETE FROM comments;
DELETE FROM events;
DELETE FROM users;
DELETE FROM visitors;
DELETE FROM partners;

DELETE FROM person_sex;
DELETE FROM current_task_status;
DELETE FROM rate_type;
DELETE FROM slot_type;
DELETE FROM user_roles;

ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

-- password
INSERT INTO person_sex (id, sex)
VALUES (90000, 'MALE'), (90001, 'FEMALE');

INSERT INTO current_task_status (id, status)
VALUES (90010, 'NEW'), (90011, 'PAUSED'), (90013, 'IN_WORK'), (90014, 'DONE'), (90015, 'FAILED');

INSERT INTO rate_type (id, type)
VALUES (90030, 'ONLINE_LITE'), (90031, 'ONLINE_STANDARD'), (90032, 'ONLINE_BUSINESS'),
  (90033, 'PERSONAL_LITE'), (90034, 'PERSONAL_STANDARD'), (90035, 'PERSONAL_BUSINESS');


INSERT INTO slot_type (id, type)
VALUES (90050, 'CHECK_IN'), (90051, 'KEYNOTE'), (90053, 'BREAK'), (90054, 'LECTURE');

INSERT INTO user_roles (id, role)
VALUES (90070, 'ROLE_USER'), (90071, 'ROLE_ADMIN');


INSERT INTO partners (name, email, phone, description, logo_url)
VALUES
  ('Одноклассники', '8-800-000-00-00', 'cv@odnoklassniki.ru',
   'Одноклассники — это в первую очередь команда единомышленников, любящих своё дело и делающих общение в интернете удобнее, гармоничнее и лучше. Мы даём людям возможность виртуально встречаться, делиться эмоциями и каждый день узнавать что-то новое. Вместе.',
   'ok_logo.png'),
  ('T-Systems', '+7-812-677-66-86', 'info@t-systems.ru',
   'T-Systems — стратегическое подразделение Deutsche Telekom, один из лидеров в сфере информационных и телекоммуникационных услуг для таких отраслей, как промышленность, автомобилестроение, энергетика, транспорт, нефтегаз. T-Systems имеет офисы более чем в 20 странах и около 47 600 профессионалов по всему миру.',
   't-sys_200_200.png'),
  ('JetBrains', '+7-812-380-1641', 'eugenia.dubova@jetbrains.com',
   'At JetBrains, code is our passion. For over 15 years we have strived to make the strongest, most effective developer tools on earth. By automating routine checks and corrections, our tools speed up production, freeing developers to grow, discover and create.',
   'jb_200_200.png');

INSERT INTO visitors (first_name, last_name, sex, enabled, photo_url, birthday, registered_date, email,
                      phone, github_account, linkedin_account, twitter_account,
                      employer, biography, description, cost)
VALUES
  ('Виктор', 'Гамов', 90000, TRUE, 'vgamov.jpg', NULL, TIMESTAMP '2016-10-10 09:00',
             'gamov@gmail.com',
             '+7-000-000-00-00', 'gAmUssA', 'vikgamov', 'gamussa',
   'Hazelcast', NULL,
   'Виктор Гамов — со-основатель и лидер вашего любимого правильного подкаста для IT-шников «Разбор Полетов» и по совместительству Senior Solution Architect в компании Hazelcast, которая занимается разработкой in-memory data grid с открытым исходным кодом. Накопил большой опыт, участвуя во множестве Java/JavaScript/HTML5 проектов, что легло в основу книги «Enterprise Web Development» издательства O’Reilly, которую он писал в соавторстве с известными персонами из Java мира. Виктор помогает клиентам финансового и телекоммуникационного сектора в проектировании и разработке высоконагруженных систем.
 В свободное от работы время Виктор не забывает про качалку, а также выступает на международных конференциях, пишет в твиттер, помогает с организацией встреч Princetown JUG и NYC Hazelcast User Group.',
   10000),
  ('Барух', 'Садогурский', 90000, TRUE, 'bsadogursky.jpg', TIMESTAMP '1970-11-25 00:00', TIMESTAMP '2016-10-10 07:00',
            'jbaruch@gmail.com',
            '+7-000-000-00-00', 'jbaruch', NULL, 'jbaruch',
   'JFrog',
   'Developer advocate в компании JFrog, и делает в жизни ровно 3 вещи: зависает с разработчиками Bintray и Artifactory, пописывает для них код, и рассказывает о впечатлениях в блогах и на конференциях. И так несколько лет подряд, ни минуты об этом не жалея.',
   'Поскольку «религия не позволяет» быть евангелистом, Барух — developer advocate в компании JFrog и делает в жизни ровно 3 вещи: зависает с разработчиками Bintray и Artifactory, пописывает для них код, и рассказывает о впечатлениях в блогах и на конференциях, таких как JavaOne, Devoxx, OSCON, конечно же JPoint и Joker, да и многих других. И так более десяти лет подряд.',
   -90000),
  ('Яков', 'Файн', 90000, TRUE, 'yfain.jpg', TIMESTAMP '1960-07-01 00:00', TIMESTAMP '2016-10-15 12:36',
           'notlull@email.ru',
           NULL, NULL, 'yfain', 'yfain',
   'SuranceBay, Farata Systems',
   'Yakov is a partner and co-founder of two companies: Farata Systems (IT consultancy) and SuranceBay (software for the Insurance industry). Yakov leads various projects related to Web development of complex enterprise applications. In his spare time Yakov enjoys teaching software and writing books.',
   NULL,
   65000);

INSERT INTO users (first_name, last_name, sex, enabled, photo_url, login, password)
VALUES
  ('Алексей', 'Фёдоров', 90000, TRUE, 'fedorov.jpg', 'alexey', 'user'),
  ('Андрей', 'Дмитриев', 90000, TRUE, 'dmitriev.jpg', 'andrey', 'admin'),
  ('Екатерина', 'Курилова', 90001, TRUE, 'kurilova.jpg', 'ekaterina', 'user'),
  ('Руслан', 'Ахметзянов', 90000, FALSE, 'ahmetzyanov.jpg', 'ruslan', 'user'),
  ('Максим', 'Зверев', 90000, TRUE, 'zverev.jpg', 'maxim', 'user'),
  ('Яна', 'Пилюгина', 90001, TRUE, 'pilugina.jpg', 'yana', 'user');

INSERT INTO events (name, author_id, tag_name, start, address, description, logo_url)
VALUES
  ('Joker 2016', 100006, 'joker16', TIMESTAMP '2016-10-14 00:00' ,'196140, Санкт-Петербург, Петербургское шоссе, 64/1',
   'Спикеры - Барух Садогурский и Виктор Гамов, посетитель - Яков Файн (также он возможный докладчик). Главная Java-конференция в России. Санкт-Петербург, 14-15 октября 2016',
   'joker_logo.png'),
  ('JPoint 2016', 100008, 'jpoint16', TIMESTAMP '2016-04-23 00:00' , 'Москва, гостиница «Radisson Славянская» (площадь Европы, 2)',
   'Спикер - Барух Садогурский, посетители -  Виктор Гамов и Яков Файн. JPoint — Java-конференция только для опытных Java-разработчиков и только про разработку. Это будет уже четвертая по счету конференция JPoint: с каждым годом она получается еще больше, еще интереснее и еще хардкорнее!',
   'jpoint_logo.png');

INSERT INTO comments (content, date, user_id)
VALUES
  -- events
  ('Комментарий про jpoint16 #1', TIMESTAMP '2016-04-13 13:23', 100006),
  ('Комментарий про jpoint16 #2', TIMESTAMP '2016-04-13 20:08', 100008),
  ('Комментарий про jpoint16 #3', TIMESTAMP '2016-04-15 09:59', 100009),
  ('Комментарий про jpoint16 #4', TIMESTAMP '2016-04-25 13:07', 100006),

  ('Комментарий про joker16 #1', TIMESTAMP '2016-09-15 11:07', 100006),
  ('Комментарий про joker16 #2', TIMESTAMP '2016-09-18 07:00', 100010),
  ('Комментарий про joker16 #3', TIMESTAMP '2016-09-19 07:00', 100011),

  -- visitors
  ('Комментарий про Виктора #1', TIMESTAMP '2016-10-19 07:00', 100011),
  ('Комментарий про Виктора #2', TIMESTAMP '2016-10-21 14:53', 100009),

  ('Комментарий про Баруха #1', TIMESTAMP '2016-09-19 07:00', 100011),
  ('Комментарий про Якова Файна #1', TIMESTAMP '2016-09-21 12:48', 100006);


INSERT INTO tasks (name, user_id, start, deadline, description, active)
VALUES
  ('Задача для joker16 #1', 100006, now(), (now() + (5 * INTERVAL '1 day')), 'Задача для всех (в т.ч. автора), приложен Виктор Гамов, Яков Файн, Одноклассники и Joker 2016', TRUE ),
  ('Задача для joker16 #2', 100006, (now() - (5 * INTERVAL '1 day')), (now() + (1 * INTERVAL '1 day')),
   'Задача для Екатерины, Руслана, Максима и Яны, дэдлайн через сутки, приложен Барух Садогурский', TRUE ),
  ('Задача для joker16 #3', 100006, (now() - (7 * INTERVAL '1 day')), (now() + (179 * INTERVAL '1 minute')),
   'Задача для всех, дэдлайн через 3 часа (или час, если я еще не разобрался с таймзоой). Никто не приложен.', TRUE );


INSERT INTO rates (name, event_id, rate_type, start_date, end_date, cost)
VALUES
  ('Личное присутствие Standart', 100012, 90034, TIMESTAMP '2016-04-01 00:00', TIMESTAMP '2016-07-01 23:59', 12000),
  ('Личное присутствие Standart', 100012, 90034, TIMESTAMP '2016-07-02 00:00', TIMESTAMP '2016-07-31 23:59', 15000),
  ('Личное присутствие Standart', 100012, 90034, TIMESTAMP '2016-08-01 00:00', TIMESTAMP '2016-08-31 23:59', 18000),
  ('Личное присутствие Standart', 100012, 90034, TIMESTAMP '2016-09-01 00:00', TIMESTAMP '2016-09-30 23:59', 21000),
  ('Личное присутствие Standart', 100012, 90034, TIMESTAMP '2016-10-01 00:00', TIMESTAMP '2016-12-31 23:59', 24000),

  ('Личное присутствие Business', 100012, 90035, TIMESTAMP '2016-04-01 00:00', TIMESTAMP '2016-07-01 23:59', 24000),
  ('Личное присутствие Business', 100012, 90035, TIMESTAMP '2016-07-02 00:00', TIMESTAMP '2016-07-31 23:59', 30000),
  ('Личное присутствие Business', 100012, 90035, TIMESTAMP '2016-08-01 00:00', TIMESTAMP '2016-08-31 23:59', 36000),
  ('Личное присутствие Business', 100012, 90035, TIMESTAMP '2016-09-01 00:00', TIMESTAMP '2016-09-30 23:59', 42000),
  ('Личное присутствие Business', 100012, 90035, TIMESTAMP '2016-10-01 00:00', TIMESTAMP '2016-12-31 23:59', 48000),

  ('Онлайн-Трансляция Standart', 100012, 90031, TIMESTAMP '2016-04-01 00:00', TIMESTAMP '2016-07-01 23:59', 8000),
  ('Онлайн-Трансляция Standart', 100012, 90031, TIMESTAMP '2016-07-02 00:00', TIMESTAMP '2016-07-31 23:59', 10000),
  ('Онлайн-Трансляция Standart', 100012, 90031, TIMESTAMP '2016-08-01 00:00', TIMESTAMP '2016-08-31 23:59', 12000),
  ('Онлайн-Трансляция Standart', 100012, 90031, TIMESTAMP '2016-09-01 00:00', TIMESTAMP '2016-09-30 23:59', 14000),
  ('Онлайн-Трансляция Standart', 100012, 90031, TIMESTAMP '2016-10-01 00:00', TIMESTAMP '2016-12-31 23:59', 16000),

  ('Онлайн-Трансляция Corporate', 100012, 90032, TIMESTAMP '2016-04-01 00:00', TIMESTAMP '2016-07-01 23:59', 32000),
  ('Онлайн-Трансляция Corporate', 100012, 90032, TIMESTAMP '2016-07-02 00:00', TIMESTAMP '2016-07-31 23:59', 40000),
  ('Онлайн-Трансляция Corporate', 100012, 90032, TIMESTAMP '2016-08-01 00:00', TIMESTAMP '2016-08-31 23:59', 48000),
  ('Онлайн-Трансляция Corporate', 100012, 90032, TIMESTAMP '2016-09-01 00:00', TIMESTAMP '2016-09-30 23:59', 56000),
  ('Онлайн-Трансляция Corporate', 100012, 90032, TIMESTAMP '2016-10-01 00:00', TIMESTAMP '2016-12-31 23:59', 64000);

INSERT INTO visitors_comments (visitor_id, comment_id)
VALUES
  (100003, 100021),
  (100003, 100022),
  (100004, 100023),
  (100005, 100024);

INSERT INTO events_comments (event_id, comment_id)
VALUES
  (100012, 100018),
  (100012, 100019),
  (100012, 100020),

  (100013, 100014),
  (100013, 100015),
  (100013, 100016),
  (100013, 100017);

INSERT INTO comments (content, date, user_id)
VALUES
  -- tasks
  ('Коммент к "Задача для joker16 #1"', TIMESTAMP '2016-04-13 13:23', 100006);

INSERT INTO tasks_comments (task_id, comment_id)
VALUES
  (100025, 100048);

INSERT INTO task_attach_events (task_id, event_id)
VALUES
  (100025, 100012);

INSERT INTO task_attach_visitors (task_id, visitor_id)
VALUES
  (100025, 100003),
  (100025, 100005),
  (100026, 100004);

INSERT INTO task_attach_partners (task_id, partner_id)
VALUES
  (100025, 100000);

INSERT INTO task_user_target (task_id, user_id)
VALUES
  (100025, 100006),
  (100025, 100007),
  (100025, 100008),
  (100025, 100009),
  (100025, 100010),
  (100025, 100011),

  (100026, 100008),
  (100026, 100009),
  (100026, 100010),
  (100026, 100011),

  (100027, 100006),
  (100027, 100007),
  (100027, 100008),
  (100027, 100009),
  (100027, 100010),
  (100027, 100011);

INSERT INTO task_statuses (user_id, task_id, creation_time, current_task_status_id, description)
VALUES
  (100006, 100025, (SELECT start FROM tasks WHERE id = 100025), 90010, 'Создал новую тестовую задачу для всех!'),
  (100008, 100025, ((SELECT start FROM tasks WHERE id = 100025) + (26 * INTERVAL '1 minute')), 90013, 'Взяла в работу'),
  (100011, 100025, ((SELECT start FROM tasks WHERE id = 100025) + (112 * INTERVAL '1 minute')), 90013, 'Я тоже помогу'),

  (100006, 100026, (SELECT start FROM tasks WHERE id = 100026), 90010, 'Новая задача'),

  (100006, 100027, (SELECT start FROM tasks WHERE id = 100027), 90010, 'Новая задача');

INSERT INTO rates (name, event_id, rate_type, start_date, end_date, cost)
VALUES
  ('Личное присутствие Standart', 100013, 90032, TIMESTAMP '2016-04-01 00:00', TIMESTAMP '2016-04-20 23:59', 34000);



INSERT INTO events_by_rate_confirmed_visitors (visitor_id, buy_date, rate_id)
VALUES
  (100005, TIMESTAMP '2016-07-15 15:53', 100034),
  (100005, TIMESTAMP '2016-01-29 07:48', 100054),
  (100003, TIMESTAMP '2016-08-15 16:00', 100054);

INSERT INTO events_probable_speakers (visitor_id, event_id, send_date, speech_name, speech_description, wish_price)
VALUES
  (100005, 100012, TIMESTAMP '2015-10-14 12:00', 'Название выступления', 'Описание выступления. Хочет 15 тысяч...', 15000);

INSERT INTO tracks (name, event_id, description)
VALUES
  ('Зал 1', 100012, 'Главный зал'),
  ('Зал 2', 100012, 'Доп. зал'),
  ('Основной зал', 100013, 'Самый главный трэк');


INSERT INTO slots (name, track_id, start, visitor_id, slot_description, slot_type, grade, price)
VALUES
  ('Регистрация + welcome кофе', 100055, TIMESTAMP '2016-10-14 08:30', 100004, NULL, 90050, NULL, 40000),
  ('Мавен против Грейдла: На заре автоматизации', 100055, TIMESTAMP '2016-10-14 12:00', 100004,
   'Ну, вы в курсе: монстр, мастадонт и владелец поляны Мавен против молодого, динамичного, изворотливого Грейдла! Битва до победного конца! А судьи — вы! Ведущие представят свои решения «классических» проблем автоматизации проекта с помощью обоих инструментов «живьём» на сцене, а вы проголосуете за тот инструмент, который лучше решает проблему.',
   90054, NULL, 40000),
  ('Верхом на реактивных стримах', 100055, TIMESTAMP '2016-10-14 12:00', 100003,
   'Вы из тех, кто считает, что, распараллелив любой цикл, можно улучшить перформанс, и Collection.parallelStream() — ваш лучший друг? А как вам идея — вбросить ещё пачку машин и получить распределенную обработку? Интересно? Тогда для вас этот доклад обязателен к просмотру. ' ||
   'Виктор познакомит слушателей со своим другом, Ориентированным (Направленным) Ациклическим Графом (или Маркизом?!), и покажет, как с его помощью была организована распределенная высокопроизводительная система обработки информации в памяти поверх нашего знакомого Java 8 Stream API.',
   90054, NULL, 30000);



