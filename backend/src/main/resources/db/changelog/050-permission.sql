--liquibase formatted sql
--changeset michael:50

create table MC.PERMISSION (
    ID bigint primary key,
    TYPE character varying(20),
    NAME character varying(255) NOT NULL UNIQUE
);
insert into MC.PERMISSION (ID, NAME) values (1, 'user');
insert into MC.PERMISSION (ID, NAME) values (2, 'admin');

--rollback drop table MC.PERMISSION;