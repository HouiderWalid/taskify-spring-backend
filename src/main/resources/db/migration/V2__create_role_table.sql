CREATE TABLE roles
(
    id   BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE NOT NULL
);

INSERT INTO roles (name)
values ('admin'),
       ('manager'),
       ('member')