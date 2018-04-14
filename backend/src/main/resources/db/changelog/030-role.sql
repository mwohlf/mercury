--liquibase formatted sql
--changeset michael:30

create table MC.ROLE (
    ID bigint primary key,
    NAME character varying(255) NOT NULL UNIQUE,
    PARENT_ID bigint
);

insert into MC.ROLE (ID, NAME) values (1, 'subject');
insert into MC.ROLE (ID, NAME) values (2, 'admin');

--rollback drop table MC.ROLE;
