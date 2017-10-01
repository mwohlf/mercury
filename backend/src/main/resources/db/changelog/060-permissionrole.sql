--liquibase formatted sql
--changeset michael:60

create table MC.PERMISSION_ROLE (
    ID bigint primary key,
    ROLE_ID bigint,
    PERMISSION_ID bigint,

    foreign key (ROLE_ID) references MC.ROLE(ID),
    foreign key (PERMISSION_ID) references MC.PERMISSION(ID)
);

--rollback drop table MC.PERMISSION_ROLE;