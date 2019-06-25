INSERT INTO ROLES(id, name) VALUES(1, 'ROLE_USER');
INSERT INTO ROLES(id, name) VALUES(2, 'ROLE_ADMIN');

INSERT INTO USERS(id, enabled, firstname, lastname, username, password, email, job_desc, job_location, full_time, email_notification, notification_time) VALUES(1, false, 'Stefan', 'Tofilovic', 's', '$2a$10$kw8hQ8bfRObakm/FoPbuiuAR0RTvoOQTGtCB0foejBRTXiYLW.N2O', 'tflc.stefan@hotmail.com', 'java', 'berlin', true, 'DAILY', '23:30');
INSERT INTO USERS(id, enabled, firstname, lastname, username, password, email, full_time, email_notification, notification_time) VALUES(2, true, 'Viktor', 'Nikolic', 'v', '$2a$10$q8dpRAkr87xlmkFx7kmdJuyYuPAcuM2JZB9m3wOor.Kf1LqhKgh5u', 'stefam1996@live.com', true, 'WEEKLY', '22:42:15');
INSERT INTO USERS(id, enabled, firstname, lastname, username, password, email, full_time, email_notification, job_location) VALUES(3, true, 'Test', 'Testic', 'test', '$2a$10$0USuUDFaW9R5RO7bWiyC8.I8lJrHYpBfOW.CYS2VC8BCD/K9TWOAm', 'test@mail.com', true, 'NEVER', 'New York');
INSERT INTO USERS(id, enabled, firstname, lastname, username, password, email, full_time, email_notification, job_desc) VALUES(4, true, 'Admin', 'Admin', 'admin', '$2a$10$acx.XljV1SzHF.tnVqhiau6lEXWDW9eoUDFn0qqTQfiI8BkHj3fq.', 'admin@mail.com', true, 'NEVER', 'c');

INSERT INTO USER_ROLES(user_id, role_id) VALUES(1, 1);
INSERT INTO USER_ROLES(user_id, role_id) VALUES(1, 2);
INSERT INTO USER_ROLES(user_id, role_id) VALUES(2, 1);
INSERT INTO USER_ROLES(user_id, role_id) VALUES(3, 1);
INSERT INTO USER_ROLES(user_id, role_id) VALUES(4, 1);
INSERT INTO USER_ROLES(user_id, role_id) VALUES(4, 2);