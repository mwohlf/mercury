--liquibase formatted sql
--changeset michael:80

create table MC.OAUTH_ACCOUNT (
    ID bigint primary key,
    USER_ID bigint,
    PROVIDER_NAME character varying(255) NOT NULL,
    PROVIDER_UID character varying(255) NOT NULL
);

--rollback drop table MC.OAUTH_ACCOUNT;