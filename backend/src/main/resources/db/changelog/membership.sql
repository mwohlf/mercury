--liquibase formatted sql
--changeset michael:30
create table MC.MEMBERSHIP (
    ID bigint primary key,
    USER_ID bigint,
    ROLE_ID bigint,

    foreign key (USER_ID) references MC.USER(ID),
    foreign key (ROLE_ID) references MC.ROLE(ID)
);

--rollback drop table MC.ROLE;