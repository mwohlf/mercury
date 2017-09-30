--liquibase formatted sql
--changeset michael:11
create table MC.USER (
    ID bigint primary key,
    EMAIL character varying(255) NOT NULL UNIQUE,
    NAME character varying(255) NOT NULL UNIQUE,
    PASSWORD character varying(255) NOT NULL
);

--rollback drop table MC.USER;