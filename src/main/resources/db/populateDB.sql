DELETE FROM speeches_speech_tags;

DELETE FROM speech_participants;
DELETE FROM events_comments;
DELETE FROM participants_comments;
DELETE FROM speeches_comments;

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


DELETE FROM person_sex;
DELETE FROM partner_status;
DELETE FROM rate_type;
DELETE FROM user_roles;

ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;


INSERT INTO person_sex (id, sex)
VALUES (90000, 'MALE'), (90001, 'FEMALE');

INSERT INTO partner_status (id, type)
VALUES (90010, 'GOLD'), (90011, 'SILVER'), (90012, 'BRONZE'), (90013, 'INFO');

INSERT INTO rate_type (id, type)
VALUES (90030, 'ONLINE_LITE'), (90031, 'ONLINE_STANDARD'), (90032, 'ONLINE_BUSINESS'),
  (90033, 'PERSONAL_LITE'), (90034, 'PERSONAL_STANDARD'), (90035, 'PERSONAL_BUSINESS');


INSERT INTO partners (name, contact_email, contact_phone, contact_name, description, logo_url)
VALUES
  ('Одноклассники', 'cv@odnoklassniki.ru', '8-800-000-00-00', 'Анастасия',
   'Одноклассники — это в первую очередь команда единомышленников, любящих своё дело и делающих общение в интернете удобнее, гармоничнее и лучше. Мы даём людям возможность виртуально встречаться, делиться эмоциями и каждый день узнавать что-то новое. Вместе.',
   'ok_logo.png'),
  ('T-Systems', 'info@t-systems.ru', '+7-812-677-66-86', 'Виктор Андреевич',
   'T-Systems — стратегическое подразделение Deutsche Telekom, один из лидеров в сфере информационных и телекоммуникационных услуг для таких отраслей, как промышленность, автомобилестроение, энергетика, транспорт, нефтегаз. T-Systems имеет офисы более чем в 20 странах и около 47 600 профессионалов по всему миру.',
   't-sys_200_200.png'),
  ('JetBrains', 'eugenia.dubova@jetbrains.com', '+7-812-380-1641', 'Михаил',
   'At JetBrains, code is our passion. For over 15 years we have strived to make the strongest, most effective developer tools on earth. By automating routine checks and corrections, our tools speed up production, freeing developers to grow, discover and create.',
   'jb_200_200.png');

INSERT INTO participants (full_name, sex, enabled, photo_url, birthday, registered_date, phone, skype, city, employer, biography, travel_help)
VALUES
  ('Максим Дорофеев', 90000, TRUE, 'http://2016.jpoint.ru/img/dorofeev.jpg', NULL, TIMESTAMP '2016-10-10 09:00',
             '+7-000-000-00-00', NULL, 'Москва', 'mnogosdelal.ru',
   'Автор проекта mnogosdelal.ru, бизнес-тренер, консультант.',
   NULL),
  ('Барух Садогурский', 90000, TRUE, 'http://2016.jpoint.ru/img/baruch.png',
            TIMESTAMP '1970-11-25 00:00', TIMESTAMP '2016-10-10 07:00',
            '+7-000-000-00-00', 'jbaruh', 'Cupertino, CA', 'JFrog',
   'Developer advocate в компании JFrog, и делает в жизни ровно 3 вещи: зависает с разработчиками Bintray и Artifactory, пописывает для них код, и рассказывает о впечатлениях в блогах и на конференциях. И так несколько лет подряд, ни минуты об этом не жалея.',
   'No'),
  ('Яков Файн', 90000, TRUE, 'yfain.jpg', TIMESTAMP '1960-07-01 00:00', TIMESTAMP '2016-10-15 12:36',
           '+7-000-000-00-00', NULL, 'New York', 'SuranceBay, Farata Systems',
   'Yakov is a partner and co-founder of two companies: Farata Systems (IT consultancy) and SuranceBay (software for the Insurance industry). Yakov leads various projects related to Web development of complex enterprise applications. In his spare time Yakov enjoys teaching software and writing books.',
   'помощь с визой в РФ');

INSERT INTO users (full_name, sex, enabled, photo_url, login, password)
VALUES
  ('Алексей Фёдоров', 90000, TRUE, 'fedorov.jpg', 'alexey', 'user'),
  ('Андрей Дмитриев', 90000, TRUE, 'dmitriev.jpg', 'andrey', 'admin'),
  ('Екатерина Курилова', 90001, TRUE, 'kurilova.jpg', 'ekaterina', 'user'),
  ('Руслан Ахметзянов', 90000, FALSE, 'ahmetzyanov.jpg', 'ruslan', 'user'),
  ('Максим Зверев', 90000, TRUE, 'zverev.jpg', 'maxim', 'user'),
  ('Яна Пилюгина', 90001, TRUE, 'pilugina.jpg', 'yana', 'user');


INSERT INTO events (name, author_id, jira_name, jira_link, version, start_date, address, description, logo_url)
VALUES
  ('Joker', 100006, 'JOKER', NULL, '2016 Piter', TIMESTAMP '2016-10-14 00:00', NULL,
   'Главная Java-конференция в России. Санкт-Петербург, 14-15 октября 2016',
   'joker_logo.png'),
  ('JPoint', 100008, 'JPOINT', NULL, '2016 Moscow', TIMESTAMP '2016-04-23 00:00',
   'Москва, гостиница «Radisson Славянская» (площадь Европы, 2)',
   'JPoint — Java-конференция только для опытных Java-разработчиков и только про разработку. Это будет уже четвертая по счету конференция JPoint: с каждым годом она получается еще больше, еще интереснее и еще хардкорнее!',
   'jpoint_logo.png');

-- simple info about speakers
INSERT INTO participants (full_name, sex, enabled, photo_url, employer, biography)
VALUES
  ('Tim Berglund',
   90000,
   TRUE,
   'http://2016.jpoint.ru/img/berglund.jpg',
   'DataStax',
   'Tim is a teacher, author, and technology leader with DataStax, where he serves as the Director of Training. He can frequently be found at speaking at conferences in the United States and all over the world. He is the co-presenter of various O’Reilly training videos on topics ranging from Git to Distributed Systems, and is the author of Gradle Beyond the Basics. He tweets as @tlberglund, blogs very occasionally at http://timberglund.com, and lives in Littleton, CO, USA with the wife of his youth and their youngest child.'
  );

INSERT INTO twitteraccs (account_link, owner_id)
VALUES
  ('https://twitter.com/tlberglund', 100014);

INSERT INTO speeches (event_id, partner_id, short_desc, is_from_jira, name, full_desc, speaker_cost)
VALUES
  (100013,
   NULL,
   'A series of technical and leadership lessons from the world of filmmaking.',
   FALSE,
   'Seven lessons developers can learn from film',
   '<p>Filmmaking is a deeply technical, collaborative, artistic, high-stakes endeavor. The details of this mysterious craft are unknown to all but an elite few, while the benefits are enjoyed by almost everyone. Sound like a profession you know?</p><p>In this short talk, we’ll explore some stories from filmmaking’s past and present and learn from another industry’s technological transitions, collaborative patterns, and specialized roles. From synchronized sound to diva directors to dolly grips, this highly scientific art has a lot to teach us about the way we developers are changing the world.</p>',
   2560
  );
INSERT INTO speech_participants (speech_id, participant_id)
VALUES
  (100016, 100014);


-- baruh sadogursky
INSERT INTO twitteraccs (account_link, owner_id)
VALUES
  ('https://twitter.com/jbaruch', 100004);

INSERT INTO speeches (event_id, partner_id, short_desc, is_from_jira, name, full_desc, speaker_cost)
VALUES
  (100013,
   NULL,
   'За несколько лет существования Bintray-я мы постоянно его ковыряем. В этом докладе я расскажу зачем мы это делаем, что изменилось.',
   FALSE,
   'Работает — не трогай! Или зачем мы опять переделали всю архитектуру',
   '<p>Нет, ну естественно мы всё планировали заранее. Нагрузку, модель использования, необходимые фичи. И как всегда в разработке софта, «что-то пошло не так.»</p><p>Bintray изначально разрабатывался как высоконагруженная распределенная система, мы готовились к масштабу, и всё равно, когда оно всё навалилось, нам пришлось несладко.</p><p>На этом докладе я представлю вам два года динамичной архитектуры проекта, что было, что мы меняли, что стало, и самое главное — почему. На примере наших успехов, проколов и «Разборов Полетов» я покажу вам столько граблей, что хватит на ваши нынешние и будущие проекты.</p>',
   0
  );
INSERT INTO speech_participants (speech_id, participant_id)
VALUES
  (100018, 100004);

-- alexey shipilev
INSERT INTO participants (full_name, sex, enabled, photo_url, employer, biography)
VALUES
  ('Алексей Шипилёв',
   90000,
   TRUE,
   'http://2016.jpoint.ru/img/shipilev.png',
   'Oracle',
   'Работает над производительностью Java около 10 лет. За это время он успел позаниматься Apache Harmony в Intel, затем перешёл в Sun Microsystems, а потом и в Oracle, где сегодня работает над Sun/Oracle JDK, главным образом над изменениями, связанными с производительностью JVM, библиотек классов, фреймворков и приложений. Разрабатывает и поддерживает под-проекты OpenJDK: jmh, jcstress, jol. Работает в экспертных группах, связанных с формализацией, тестированием и производительностью concurrency.'
  );

INSERT INTO twitteraccs (account_link, owner_id)
VALUES
  ('https://twitter.com/shipilev', 100019);

INSERT INTO speeches (event_id, partner_id, short_desc, is_from_jira, name, full_desc, speaker_cost)
VALUES
  (100013,
   NULL,
   'Дизайн и реализация JEP-254 (Compact Strings) и JEP-280 (Indify String Concat): инсайты и идеи, ангст и грабли, катарсис и бенчмарки.',
   FALSE,
   'The Lord of the Strings: Two Scours',
   '<p>java. lang.String — один из наиболее часто используемых классов в Java-приложениях. Не удивительно, что мы пытаемся его улучшать и микро-, и макро-оптимизациями. В докладе будут освещены вопросы рациональности, подходов к реализации, практических граблей, с которыми сталкиваются разработчики JDK, пытающиеся ничего не сломать в огромной экосистеме, а также чем эта подковёрная деятельность грозит простым пользователям.</p><p>В этом докладе мы посмотрим на две грядущие фичи в JDK 9, направленные на оптимизацию строк: Compact Strings, сжимающие строки с однобайтовыми символами, что улучшает футпринт и даже общую производительность; и Indify String Concat, использующий магию invokedynamic для конкатенации строк, позволяющий подкручивать реализацию конкатенации без рекомпиляции программ.</p>',
   580
  );
INSERT INTO speech_participants (speech_id, participant_id)
VALUES
  (100021, 100019);
INSERT INTO speech_tags (tag)
VALUES
  ('JDK 9');
INSERT INTO speeches_speech_tags (speech_id, tag_id)
    VALUES
      (100021, 100022);

-- egor bugaenko
INSERT INTO participants (full_name, sex, enabled, photo_url, employer, biography)
VALUES
  ('Егор Бугаенко',
   90000,
   TRUE,
   'http://2016.jpoint.ru/img/bugayenko.jpg',
   'Teamed.io',
   '<p>Егор уже более десяти лет CTO в Teamed.io — софтверной компании с уникальной методологией разработки в распределенном режиме. Егор регулярно пишет на www.yegor256.com, пишет на Java в rultor.com, takes.org и jcabi.com. Егор живет то в Пало-Альто, то в Киеве.</p><p>Поищите в Гугле «ORM» и найдете статью Егора на эту тему. Тема очень интересна для обсуждения, в основном потому, что резко протестует против сложившихся стандартов работы с базами данных в Java. Против JPA и Hibernate, в частности.</p>'
  );

INSERT INTO twitteraccs (account_link, owner_id)
VALUES
  ('https://twitter.com/yegor256', 100023);

INSERT INTO speeches (event_id, partner_id, short_desc, is_from_jira, name, full_desc, speaker_cost)
VALUES
  (100013,
   NULL,
   'Object-Relational Mapping(JPA/Hibernate) is a design pattern, which is very popular and totally anti basic principles of object-oriented programming',
   FALSE,
   'ORM — это обидно',
   '<p>Object-Relational Mapping (ORM), как метод интеграции реляционных баз данных с Java-приложениями, очень популярен последние лет десять. В то же время (и об этом будет мой доклад) он совершенно не совместим с ключевыми принципами объектно-ориентированного программирования. ORM превращает объекты в глупые и бездушные хранилища данных, грубо нарушая инкапсуляцию. В своем докладе Егор подробно раскроет эту тему и предложит альтернативу.</p>',
   4300
  );
INSERT INTO speech_participants (speech_id, participant_id)
VALUES
  (100025, 100023);

-- viktor gamov
INSERT INTO participants (full_name, sex, enabled, photo_url, employer, biography)
VALUES
  ('Виктор Гамов',
   90000,
   TRUE,
   'http://2016.jpoint.ru/img/gamov.jpg',
   'Hazelcast',
   '<p>Виктор Гамов — Senior Solution Architect в компании Hazelcast, которая занимается разработкой продукта для распределенной обработки данных (in-memory data grid) с открытым исходным кодом. Виктор помогает клиентам финансового и телекоммуникационного сектора в проектировании и разработке высоконагруженных систем. До этого он накопил большой опыт, участвуя в качестве консультанта, во множестве проектов Java/JavaScript/HTML5, что легло в основу книги «Enterprise Web Development» издательства O’Reilly, которую он писал в соавторстве с известными персонами из Java мира.</p><p>В свободное от работы время Виктор помогает с организацией встреч Princetown JUG и NYC Hazelcast User Group, а также выступает на международных конференциях, пишет в твитер, и поскольку совершенно неутомим, ведет «Правильный подкаст для IT-шников» «Разбор Полетов» и не забывает про качалку и бицуху.</p>'
  );

INSERT INTO twitteraccs (account_link, owner_id)
VALUES
  ('https://twitter.com/gamussa', 100026);

INSERT INTO speeches (event_id, partner_id, short_desc, is_from_jira, name, full_desc, speaker_cost)
VALUES
  (100013,
   NULL,
   'В своем докладе я собираюсь рассмотреть один из способов улучшения производительности Java-приложений, основанный на использовании стандарта JCache и возможностей, которые предоставляет распределенное кэширование данных.',
   FALSE,
   'JCache и Распределенные Кэши: Беспредел!',
   '<p>Java тормозит. Что же делать?! Вариантов видится несколько:</p><ol><li>Паника! (отставить панику)</li><li>Молиться на иконы Шипилёва</li><li>Всё кэшировать</li></ol><p>Даже если второй вариант выглядит заманчиво, мы пойдем третьим путем. Но на все данные серьезных пацанов никакого heap-а не хватит. Но тут на помощь приходит распределенное кэширование вообще, и стандарт JCache в частности. В этом докладе вы узнаете, что такое JCache, и требует ли он чтобы данные хранились в куче (on-heap) или вне кучи (off-heap), на одной Java-машине или на нескольких.</p>',
   4300
  );
INSERT INTO speech_participants (speech_id, participant_id)
VALUES
  (100028, 100026);


-- andrey pangin
INSERT INTO participants (full_name, sex, enabled, photo_url, employer, biography)
VALUES
  ('Андрей Паньгин',
   90000,
   TRUE,
   'http://2016.jpoint.ru/img/pangin.png',
   'Одноклассники',
   '<p>Специализируется на создании ПО для высоконагруженных серверов в проекте Одноклассники. С увлечением копается во внутренностях JVM и JDK. Ранее работал в Sun Microsystems над виртуальной машиной HotSpot.</p>'
  );

INSERT INTO twitteraccs (account_link, owner_id)
VALUES
  ('https://twitter.com/AndreiPangin', 100029);

INSERT INTO speeches (event_id, partner_id, short_desc, is_from_jira, name, full_desc, speaker_cost)
VALUES
  (100013,
   100000,
   'Мифы и факты о внутренностях JVM, связанных со стеками потоков и дампами хипа.',
   FALSE,
   'Глубже стек-трейсов, шире хип-дампов',
   '<p>Stack trace и heap dump - не просто инструменты отладки; это потайные дверцы к самым недрам виртуальной Java машины. Доклад будет посвящён малоизвестным особенностям JDK, так или иначе связанным с обоходом хипа и стеками потоков.</p><p>Мы разберём:</p><ul><li>как снимать дампы в продакшне без побочных эффектов;</li><li>как работают утилиты jmap и jstack изнутри, и в чём хитрость forced режима;</li><li>почему все профилировщики врут, и как с этим бороться;</li><li>познакомимся с новым Stack-Walking API в Java 9;</li><li>научимся сканировать Heap средствами JVMTI;</li><li>узнаем о недокументированных функциях Хотспота и других интересных штуках.</li><ul>',
   0
  );
INSERT INTO speech_participants (speech_id, participant_id)
VALUES
  (100031, 100029);


-- nicolay alimenkov
INSERT INTO participants (full_name, sex, enabled, photo_url, employer, biography)
VALUES
  ('Николай Алименков',
   90000,
   TRUE,
   'http://2016.jpoint.ru/img/alimenkov.png',
   'EPAM',
   '<p>Практикующий Java-техлид и Delivery Manager. Эксперт в разработке на Java, Agile-практиках и управлении проектами. Разрабатывает на Java более 12 лет, специализируется на разработке сложных распределённых масштабируемых систем. Активный участник и докладчик многих международных конференций. Основатель и тренер тренингового центра XP Injection. Организатор и идеолог конференций Selenium Camp, JEEConf, XP Days Ukraine и IT Brunch. Основатель Клуба анонимных разработчиков.</p>'
  );

INSERT INTO twitteraccs (account_link, owner_id)
VALUES
  ('https://twitter.com/xpinjection', 100032);

INSERT INTO speeches (event_id, partner_id, short_desc, is_from_jira, name, full_desc, speaker_cost)
VALUES
  (100013,
   NULL,
   'Будут рассмотрены различные слои приложения с конкретными примерами и техниками тестирования.',
   FALSE,
   'Сага о том, как Java-разработчики должны тестировать свои приложения',
   '<p>В современном мире разработки все осознают пользу от тестирования. В теории, большая часть Java-разработчиков даже понимает, что нужно писать модульные и интеграционные, а еще лучше и функциональные тесты. Но в воздухе зависает основной вопрос: «КАК?» Ведь в реальной жизни не все так просто как в примерах, найденных в интернетах.</p><p>В докладе Николай расскажет о своем опыте и применяемых инструментах тестирования разных слоев приложений с участием реляционных БД, NoSQL хранилищ, файловой системы, MVC фреймворков, REST сервисов и прочих составляющих большинства продуктов. Слайдов практически не будет, зато будет много кода и тестов...</p><p><b>Внимание: продолжительность этого доклада — два часа!</b></p>',
   0
  );
INSERT INTO speech_participants (speech_id, participant_id)
VALUES
  (100034, 100032);

-- tagir valeev
INSERT INTO participants (full_name, sex, enabled, photo_url, employer, biography)
VALUES
  ('Тагир Валеев',
   90000,
   TRUE,
   'http://images.contentful.com/oxjq45e8ilak/xCu5sBC6EosmaWKCue8mu/06cf1280147bafb06f80d6eedf17b706/PTG9GIpTtmk.jpg?w=300',
   'JetBrains',
   'C недавних пор работает в JetBrains, занимается статическим анализатором кода IntelliJ IDEA, инспекциями и квик-фиксами. Также он кидает патчики в OpenJDK, разрабатывает опенсорсную библиотеку StreamEx и анализатор байткода Java HuntBugs. Известен на Хабрахабре как lany.'
  );

INSERT INTO twitteraccs (account_link, owner_id)
VALUES
  ('https://twitter.com/tagir_valeev', 100035);

INSERT INTO speeches (event_id, partner_id, short_desc, is_from_jira, name, full_desc, speaker_cost)
VALUES
  (100013,
   NULL,
   'Stream API: производительность, загадочное поведение, интересные задачи и практические советы.',
   FALSE,
   'Странности Stream API',
   '<p>На разных конференциях было много докладов на тему, что такое Stream API и как им пользоваться. Здесь мы сосредоточимся на деталях реализации. Какие операции выполняются быстро, какие медленно, какие неожиданно кушают много памяти, какие просто ведут себя странно. Мы разберёмся, какие баги есть в Java 8 Stream API и что будет исправлено в Java 9. Также мы посмотрим, как решать некоторые нетривиальные задачи. Будет немного рекламы моей бесплатной библиотеки StreamEx.</p>',
   0
  );
INSERT INTO speech_participants (speech_id, participant_id)
VALUES
  (100037, 100035);
INSERT INTO speech_tags (tag)
VALUES
  ('Stream API');
INSERT INTO speeches_speech_tags (speech_id, tag_id)
VALUES
  (100037, 100038);

-- joker 2016
-- tagir valeev
INSERT INTO speeches (event_id, partner_id, short_desc, is_from_jira, rating, name, full_desc, speaker_cost)
VALUES
  (100012,
   100002,
   NULL,
   FALSE,
   5,
   'Причуды Stream API',
   '<p>Stream API уже не первый год с нами, и многие разработчики вовсю им пользуются. Однако за волшебным API скрывается обычный Java-код со своими причудами. Тагир вместе со слушателями приглядится к некоторым конструкциям, которые могут привести к неожиданным провалам в производительности, или работать не так, как вы ожидаете. Также мы посмотрим на примере, как можно расширить Stream API, добавив свою операцию, и при этом добиться наилучшей производительности. Доклад развивает темы, затронутые Тагиром в этом году на JPoint ("Странности Stream API") и JBreak ("Stream API: рекомендации лучших собаководов").</p>',
   1300
  );
INSERT INTO speech_participants (speech_id, participant_id)
VALUES
  (100039, 100035);
INSERT INTO speeches_speech_tags (speech_id, tag_id)
VALUES
  (100039, 100038);

-- shipilev

INSERT INTO speeches (event_id, partner_id, short_desc, is_from_jira, rating, name, full_desc, speaker_cost)
VALUES
  (100012,
   100002,
   NULL,
   FALSE,
   4.88,
   'Перформанс: Что В Имени Тебе Моём?',
   '<p>Оптимизация производительности бередит умы опытных разработчиков с начала компьютерных времён. В коллективном бессознательном оптимизация — это то, что делает программирование интересным, конференции раскупаемыми, личный послужной лист — золотым.</p><p>В этом обзорном докладе мы поговорим об оптимизации больших/инфраструктурных проектов (к примеру, OpenJDK): общих принципах, тенденциях и соотношениях; жизненном цикле проекта и экономике оптимизаций; роли и жизненном цикле тестов производительности; типичных ловушках, разногласиях и противоречиях, в которых оказываются оптимизационные задачи в крупных проектах.</p>',
   0
  );
INSERT INTO speech_participants (speech_id, participant_id)
VALUES
  (100040, 100019);


INSERT INTO events_comments(event_id, content, date, user_id)
    VALUES
      (100012, 'Комментарий про jpoint16 #1', TIMESTAMP '2016-04-13 13:23', 100006),
      (100012,'Комментарий про jpoint16 #2', TIMESTAMP '2016-04-13 20:08', 100008),
      (100012,'Комментарий про jpoint16 #3', TIMESTAMP '2016-04-15 09:59', 100009),
      (100012,'Комментарий про jpoint16 #4', TIMESTAMP '2016-04-25 13:07', 100006),

      (100013,'Комментарий про joker16 #1', TIMESTAMP '2016-09-15 11:07', 100006),
      (100013,'Комментарий про joker16 #2', TIMESTAMP '2016-09-18 07:00', 100010);


INSERT INTO speeches_comments (speech_id, content, date, user_id)
VALUES
     (100025, 'Комментарий про ORM — это обидно от Егора Бугаенко', TIMESTAMP '2016-09-18 17:20', 100010);


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
  ('Онлайн-Трансляция Corporate', 100012, 90032, TIMESTAMP '2016-10-01 00:00', TIMESTAMP '2016-12-31 23:59', 64000),

  ('Standart', 100013, 90034, TIMESTAMP '2016-01-01 00:00', TIMESTAMP '2016-01-31 23:59', 15000),
  ('Standart', 100013, 90034, TIMESTAMP '2016-02-01 00:00', TIMESTAMP '2016-02-29 23:59', 18000),
  ('Standart', 100013, 90034, TIMESTAMP '2016-03-01 00:00', TIMESTAMP '2016-03-31 23:59', 21000),
  ('Standart', 100013, 90034, TIMESTAMP '2016-04-01 00:00', TIMESTAMP '2016-04-22 23:59', 24000);

INSERT INTO user_roles(user_id, role)
VALUES
  (100006, 'ROLE_USER'),
  (100006, 'ROLE_ADMIN'),
  (100007, 'ROLE_USER'),
  (100007, 'ROLE_ADMIN'),
  (100008, 'ROLE_USER'),
  (100008, 'ROLE_ADMIN'),
  (100009, 'ROLE_USER'),
  (100010, 'ROLE_USER'),
  (100011, 'ROLE_USER');

INSERT INTO emails(email, main, owner_id)
VALUES
  ('gamov@gmail.com', TRUE, 100003),
  ('jbaruch@gmail.com', TRUE, 100004),
  ('notlull@email.ru', TRUE, 100005);

INSERT INTO event_partners (event_id, partner_id, status_id, payment)
VALUES
  (100013, 100000, 90010, 200000),
  (100013, 100001, 90011, 100000),
  (100013, 100002, 90012, 50000),

  (100012, 100000, 90010, 300000);


INSERT INTO visitors (participant_id, event_id, buy_date, pay_comment, rate_id, real_cost)
VALUES
  (100005, 100012, TIMESTAMP '2016-07-15 15:53', 'some comment about participant 100005' , 100055, 15000),
  (100005, 100013, TIMESTAMP '2016-01-29 07:48', NULL, 100070, 0);


INSERT INTO speeches_comments (speech_id, content, date, user_id)
    VALUES
      (100031, 'Комментарий о speech 100031', TIMESTAMP '2016-06-29 07:48', 100007);

INSERT INTO participants_comments (participant_id, content, date, user_id)
VALUES
  (100019 ,'Комментарий про Алексея Шипилёва', TIMESTAMP '2016-09-18 17:00', 100010),
  (100029 ,'Комментарий про Андрея Паньгина', TIMESTAMP '2016-09-18 17:00', 100010);