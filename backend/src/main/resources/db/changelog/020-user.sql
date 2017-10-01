--liquibase formatted sql
--changeset michael:20

create table MC.USER (
    ID bigint primary key,
    EMAIL character varying(255) NOT NULL UNIQUE,
    NAME character varying(255) NOT NULL UNIQUE,
    PASSWORD character varying(255) NOT NULL
);

insert into MC.USER (ID, EMAIL, NAME, PASSWORD) values (1, 'test1@test.de', 'test1', 'test1');
insert into MC.USER (ID, EMAIL, NAME, PASSWORD) values (2, 'test2@test.de', 'test2', 'test2');
insert into MC.USER (ID, EMAIL, NAME, PASSWORD) values (3, 'test3@test.de', 'test3', 'test3');

--rollback drop table MC.USER;