drop table if exists documents;
drop table if exists documentStatuses;
drop table if exists marks;
drop table if exists users;
drop table if exists roles;
drop table if exists practices;
drop table if exists practiceStatuses;
drop table if exists groups;
drop table if exists specialities;

CREATE TABLE specialities (
    id BIGINT AUTO_INCREMENT primary key,
    speciality VARCHAR(50) NOT NULL
);

CREATE TABLE groups (
    id BIGINT AUTO_INCREMENT primary key,
    specialityId BIGINT,
    CONSTRAINT FK_groups_specialities FOREIGN KEY (specialityId) REFERENCES specialities (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE practiceStatuses (
    id BIGINT AUTO_INCREMENT primary key,
    status VARCHAR(50) NOT NULL,
    UNIQUE (status)
);

CREATE TABLE practices (
    id BIGINT AUTO_INCREMENT primary key,
    practiceDateStart VARCHAR(50) NOT NULL,
    practiceDateEnd VARCHAR(50) NOT NULL,
    location VARCHAR(254) NOT NULL,
    statusId BIGINT NOT NULL,
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
    password VARCHAR(50) NOT NULL,
    roleId BIGINT NOT NULL,
    practiceId BIGINT NOT NULL,
    groupId BIGINT NOT NULL,
    UNIQUE (username),
    CONSTRAINT FK_users_roles FOREIGN KEY (roleId) REFERENCES roles (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_users_practices FOREIGN KEY (practiceId) REFERENCES practices (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_users_groups FOREIGN KEY (groupId) REFERENCES groups (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE marks (
    practiceId BIGINT NOT NULL,
    userId BIGINT NOT NULL,
    mark SMALLINT NOT NULL,
    UNIQUE (practiceId),
    CONSTRAINT FK_marks_practices FOREIGN KEY (practiceId) REFERENCES practices (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_marks_users FOREIGN KEY (userId) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE documentStatuses (
    id BIGINT AUTO_INCREMENT primary key,
    status VARCHAR(50) NOT NULL,
    UNIQUE (status)
);

CREATE TABLE documents (
    id BIGINT AUTO_INCREMENT primary key,
    path VARCHAR(254) NOT NULL,
    userId BIGINT NOT NULL,
    statusId BIGINT NOT NULL,
    CONSTRAINT FK_documents_users FOREIGN KEY (userId) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_documents_documentStatuses FOREIGN KEY (statusId) REFERENCES documentStatuses (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO specialities(speciality) VALUES ('test_speciality');
INSERT INTO groups(specialityId) VALUES (1);
INSERT INTO practiceStatuses(status) VALUES ('test_status');
INSERT INTO practices(practiceDateStart, practiceDateEnd, location, statusId) VALUES ('12/12/2021', '13/12/2021', 'test_location', 1);
INSERT INTO roles(role) VALUES ('admin', 'zavkaf', 'orgpract', 'rukprakt', 'student');
INSERT INTO users(username, password, roleId, practiceId, groupId) VALUES ('admin', 'admin', 1, 1, 1);
INSERT INTO marks(practiceId, userId, mark) VALUES (1, 1, 1);
INSERT INTO documentStatuses(status) VALUES ('test_status');
INSERT INTO documents(path, userId, statusId) VALUES ('test_path', 1, 1);