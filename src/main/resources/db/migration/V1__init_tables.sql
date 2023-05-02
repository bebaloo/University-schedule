create sequence courses_seq start with 1 increment by 1;
create sequence groups_seq start with 1 increment by 1;
create sequence lessons_seq start with 1 increment by 1;
create sequence timetables_seq start with 1 increment by 1;
create sequence users_seq start with 1 increment by 1;

create table courses
(
    id   bigint not null,
    name varchar(255),
    primary key (id)
);

create table groups
(
    id        bigint not null,
    name      varchar(255),
    course_id bigint,
    primary key (id)
);

create table lessons
(
    id          bigint not null,
    classroom   varchar(255),
    day_of_week varchar(255),
    name        varchar(255),
    group_id    bigint,
    timetable_id bigint,
    tutor_id    bigint,
    primary key (id)
);

create table timetables
(
    id      bigint not null,
    user_id bigint,
    primary key (id)
);

create table users
(
    id         bigint not null,
    department varchar(255),
    email      varchar(255),
    faculty    varchar(255),
    firstname  varchar(255),
    lastname   varchar(255),
    password   varchar(255),
    role       varchar(255),
    status     varchar(255),
    group_id   bigint,
    primary key (id)
);