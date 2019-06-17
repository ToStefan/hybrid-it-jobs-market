INSERT INTO ROLES(id, name) VALUES(1, 'ROLE_USER');
INSERT INTO ROLES(id, name) VALUES(2, 'ROLE_ADMIN');

INSERT INTO USERS(id, firstname, lastname, username, password, email, job_desc, job_location, working_type, email_notification, notification_time) VALUES(1, 'Stefan', 'Tofilovic', 's', 's', 'tflc.stefan@hotmail.com', 'java', 'Serbia', 'FULL_TIME', 'NEVER', '2019-06-14');
INSERT INTO USERS(id, firstname, lastname, username, password, email, email_notification) VALUES(2, 'Viktor', 'Nikolic', 'v', 'v', 'viktor@mail.com', 'NEVER');
INSERT INTO USERS(id, firstname, lastname, username, password, email, email_notification) VALUES(3, 'Test', 'Testic', 'test', 'test', 'test@mail.com', 'NEVER');
INSERT INTO USERS(id, firstname, lastname, username, password, email, email_notification) VALUES(4, 'Admin', 'Admin', 'admin', 'admin', 'admin@mail.com', 'NEVER');

INSERT INTO USER_ROLES(user_id, role_id) VALUES(1, 1);
INSERT INTO USER_ROLES(user_id, role_id) VALUES(1, 2);
INSERT INTO USER_ROLES(user_id, role_id) VALUES(2, 1);
INSERT INTO USER_ROLES(user_id, role_id) VALUES(3, 1);
INSERT INTO USER_ROLES(user_id, role_id) VALUES(4, 1);
INSERT INTO USER_ROLES(user_id, role_id) VALUES(4, 2);