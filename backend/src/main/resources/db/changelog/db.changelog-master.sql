--liquibase formatted sql

--changeset mercury:1
CREATE TABLE role (
    id bigint PRIMARY KEY,
    label character varying(255)
);
INSERT INTO role (id, label) VALUES (1, 'user');
INSERT INTO role (id, label) VALUES (2, 'admin');

--rollback DROP TABLE role;


--changeset mercury:2
CREATE TABLE user (
    id bigint PRIMARY KEY,
    email character varying(255) NOT NULL UNIQUE,
    name character varying(255) NOT NULL UNIQUE,
    password character varying(255) NOT NULL,
    role_id bigint REFERENCES role(id)
);
--rollback DROP TABLE user;


