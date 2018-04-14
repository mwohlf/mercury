insert into MC.SUBJECT (ID, EMAIL, NAME, PASSWORD) values (1, 'test1@test.de', 'test1', 'test1');
insert into MC.REMOTE_PRINCIPAL (ID, SUBJECT_ID, PROVIDER_NAME, REMOTE_USER_ID) values (1, 1, 'google', 'uid');
insert into MC.REMOTE_PRINCIPAL (ID, SUBJECT_ID, PROVIDER_NAME, REMOTE_USER_ID) values (2, 1, 'facebook', 'uid');
