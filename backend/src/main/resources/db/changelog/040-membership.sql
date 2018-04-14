--liquibase formatted sql
--changeset michael:40

create table MC.MEMBERSHIP (
    ID bigint primary key,
    SUBJECT_ID bigint,
    ROLE_ID bigint,

    foreign key (SUBJECT_ID) references MC.SUBJECT(ID) on delete cascade,
    foreign key (ROLE_ID) references MC.ROLE(ID) on delete cascade
);

--rollback drop table MC.MEMBERSHIP;
