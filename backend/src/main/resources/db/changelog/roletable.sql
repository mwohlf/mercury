--liquibase formatted sql
--changeset michael:10
create table MC.ROLE (
    ID bigint primary key,
    LABEL character varying(255)
);
insert into MC.ROLE (ID, LABEL) values (1, 'user');
insert into MC.ROLE (ID, LABEL) values (2, 'admin');

--rollback drop table MC.ROLE;