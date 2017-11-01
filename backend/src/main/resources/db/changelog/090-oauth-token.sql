--liquibase formatted sql
--changeset michael:90

create table MC.OAUTH_TOKEN (
    ID bigint primary key,
    OAUTH_ACCOUNT_ID bigint,
    KEY character varying(255) NOT NULL,
    VALUE character varying(255) NOT NULL
);

--rollback drop table MC.OAUTH_TOKEN;