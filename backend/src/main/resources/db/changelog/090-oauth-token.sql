--liquibase formatted sql
--changeset michael:90

create table MC.OAUTH_TOKEN (
    ID bigint primary key,
    OAUTH_ACCOUNT_ID bigint,
    KEY character varying(255) NOT NULL,
    VALUE character varying(255) NOT NULL,

    foreign key (OAUTH_ACCOUNT_ID) references MC.OAUTH_ACCOUNT(ID)
);

--rollback drop table MC.OAUTH_TOKEN;