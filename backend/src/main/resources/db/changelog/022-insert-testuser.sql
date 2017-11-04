
--liquibase formatted sql
--changeset michael:22

insert into MC.USER (ID, EMAIL, NAME, PASSWORD) values (1, 'test1@test.de', 'test1', 'test1');
insert into MC.USER (ID, EMAIL, NAME, PASSWORD) values (2, 'test2@test.de', 'test2', 'test2');
insert into MC.USER (ID, EMAIL, NAME, PASSWORD) values (3, 'test3@test.de', 'test3', 'test3');
