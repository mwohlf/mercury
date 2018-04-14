--liquibase formatted sql
--changeset michael:90

create table MC.OAUTH_TOKEN (
    ID bigint primary key,
    REMOTE_PRINCIPAL_ID bigint,
    KEY character varying(255) NOT NULL,
    VALUE character varying(255) NOT NULL,

    foreign key (REMOTE_PRINCIPAL_ID) references MC.REMOTE_PRINCIPAL(ID) on delete cascade
);

--rollback drop table MC.OAUTH_TOKEN;
