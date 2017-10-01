--liquibase formatted sql
--changeset michael:10

create schema MC;

--rollback drop table ROLE;