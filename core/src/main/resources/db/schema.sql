drop table if exists users;
drop table if exists roles;
drop table if exists practices;
drop table if exists practiceStatuses;

-- CREATE TABLE users (
-- );

CREATE TABLE practiceStatuses (
    id BIGINT AUTO_INCREMENT primary key,
    status VARCHAR(50) NOT NULL,
    UNIQUE (status)
);

CREATE TABLE practices (
    id BIGINT AUTO_INCREMENT primary key,
    date DATE NOT NULL,
    location VARCHAR(254) NOT NULL,
    statusId BIGINT NOT NULL,
    mark SMALLINT NOT NULL,
    CONSTRAINT FK_practices_practiceStatuses FOREIGN KEY (statusId) REFERENCES practiceStatuses (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT primary key,
    role VARCHAR(50) NOT NULL,
    UNIQUE (role)
);

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT primary key,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(30) NOT NULL,
    roleId BIGINT NOT NULL,
    UNIQUE (username),
    CONSTRAINT FK_users_roles FOREIGN KEY (roleId) REFERENCES roles (id) ON DELETE CASCADE ON UPDATE CASCADE
)