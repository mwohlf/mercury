--liquibase formatted sql
--changeset michael:20

create table MC.SUBJECT (
    ID bigint primary key,
    EMAIL character varying(255) NOT NULL UNIQUE,
    NAME character varying(255) NULL,
    PASSWORD character varying(255) NULL
);

--rollback drop table MC.SUBJECT;
