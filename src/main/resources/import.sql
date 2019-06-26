INSERT INTO ROLES(id, name) VALUES(1, 'ROLE_USER');
INSERT INTO ROLES(id, name) VALUES(2, 'ROLE_ADMIN');

INSERT INTO USERS(id, enabled, firstname, lastname, username, password, email, job_desc, job_location, full_time, notification_type, notification_time, notification_on) VALUES(1, true, 'Viktor', 'Nikolic', 'v', '$2a$10$q8dpRAkr87xlmkFx7kmdJuyYuPAcuM2JZB9m3wOor.Kf1LqhKgh5u', 'tflc.stefan@hotmail.com', 'java', 'berlin', true, 'DAILY', '13:30:15', false);
INSERT INTO USERS(id, enabled, firstname, lastname, username, password, email, job_desc, job_location, full_time, notification_type, notification_time, notification_on, task_id) VALUES(2, true, 'Admin', 'Admin', 'admin', '$2a$10$acx.XljV1SzHF.tnVqhiau6lEXWDW9eoUDFn0qqTQfiI8BkHj3fq.', 'stefam1996@live.com', 'c', 'new york', true, 'DAILY', '14:50', true, 1);

INSERT INTO USER_ROLES(user_id, role_id) VALUES(2, 1);
INSERT INTO USER_ROLES(user_id, role_id) VALUES(2, 2);
INSERT INTO USER_ROLES(user_id, role_id) VALUES(1, 1);