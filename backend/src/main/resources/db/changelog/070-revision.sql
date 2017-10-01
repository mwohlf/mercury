--liquibase formatted sql
--changeset michael:70

create table MC.REVISION (
    REVISION_ID bigint primary key,
    REVISION_DATE timestamp
);

--rollback drop table MC.REVISION;