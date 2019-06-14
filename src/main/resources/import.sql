INSERT INTO ROLES(id, name) VALUES(1, ROLE_USER);
INSERT INTO ROLES(id, name) VALUES(2, ROLE_ADMIN);

INSERT INTO USER(id, firstname, lastname, username, password, job-desc, job-location, working-type, email-notification, notification-time) VALUES(1, 'Stefan', 'Tofilovic', 's', 's','java', 'Serbia', 'FULL_TIME', 'NEVER', '2019-06-14');

INSERT INTO USER_ROLES(user_id, role_id) VALUES(1, 1);
INSERT INTO USER_ROLES(user_id, role_id) VALUES(1, 2);