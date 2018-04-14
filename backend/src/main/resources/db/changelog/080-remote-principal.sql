--liquibase formatted sql
--changeset michael:80

create table MC.REMOTE_PRINCIPAL (
    ID bigint primary key,
    SUBJECT_ID bigint,
    PROVIDER_NAME character varying(255) NOT NULL,
    REMOTE_USER_ID character varying(255) NOT NULL,

    foreign key (SUBJECT_ID) references MC.SUBJECT(ID) on delete cascade
);

--rollback drop table MC.REMOTE_PRINCIPAL;
