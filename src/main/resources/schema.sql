create table if not exists MPA
(
    MPA_ID   INTEGER auto_increment,
    MPA_NAME CHARACTER VARYING(5)
        constraint MPA_UNIQUE_NAME
            unique,
    constraint MPA_PK
        primary key (MPA_ID)
);
create table if not exists FILMS
(
    FILM_ID      INTEGER auto_increment,
    TITLE        CHARACTER VARYING(100) not null,
    DESCRIPTION  CHARACTER VARYING(200),
    DURATION     INTEGER                not null,
    RELEASE_DATE DATE                   not null,
    MPA_ID       INTEGER,
    constraint FILMS_PK
        primary key (FILM_ID),
    constraint FILMS_MPA_MPA_ID_FK
        foreign key (MPA_ID) references MPA
            on update cascade
);
create table if not exists GENRES
(
    GENRE_ID   INTEGER auto_increment,
    GENRE_NAME CHARACTER VARYING(20) not null
        constraint GENRES_UNIQUE_NAME
            unique,
    constraint "GENRES_pk"
        primary key (GENRE_ID)
);
create table if not exists FILMS_GENRES
(
    FILM_GENRE_ID INTEGER auto_increment,
    GENRE_ID      INTEGER not null,
    FILM_ID       INTEGER not null,
    constraint FILMS_GENRES_PK
        primary key (FILM_GENRE_ID),
    constraint FILMS_GENRES_UNIQUE
        unique (FILM_ID, GENRE_ID),
    constraint "FILMS_GENRES_FILMS_null_fk"
        foreign key (FILM_ID) references FILMS
            on update cascade on delete cascade,
    constraint FILMS_GENRES_GENRES_GENRE_ID_FK
        foreign key (GENRE_ID) references GENRES
            on update cascade
);
create table if not exists USERS
(
    USER_ID   INTEGER auto_increment,
    EMAIL     CHARACTER VARYING(30) not null,
    LOGIN     CHARACTER VARYING(50) not null
        constraint USERS_LOGIN_UNIQUE
            unique,
    USER_NAME CHARACTER VARYING(50) not null,
    BIRTHDAY  DATE,
    constraint USERS_PK
        primary key (USER_ID)
);
create table if not exists FILMS_LIKES
(
    FILM_LIKE_ID INTEGER auto_increment,
    USER_ID      INTEGER not null,
    FILM_ID      INTEGER not null,
    constraint FILMS_LIKES_PK
        primary key (FILM_LIKE_ID),
    constraint FILMS_LIKES_FILMS_FILM_ID_FK
        foreign key (FILM_ID) references FILMS
            on update cascade on delete cascade,
    constraint FILMS_LIKES_USERS_USER_ID_FK
        foreign key (USER_ID) references USERS
            on update cascade on delete cascade
);
create table if not exists STATUSES
(
    STATUS_ID   INTEGER auto_increment,
    STATUS_NAME CHARACTER VARYING(10) not null
        constraint STATUSES_UNIQUE_NAME
            unique,
    constraint STATUSES_PK
        primary key (STATUS_ID)
);
create table if not exists FRIENDSHIPS
(
    FRIENDSHIP_ID    INTEGER auto_increment,
    SENDER_USER_ID   INTEGER not null,
    RECEIVER_USER_ID INTEGER not null,
    STATUS_ID        INTEGER not null,
    constraint FRIENDSHIPS_PK
        primary key (FRIENDSHIP_ID),
    constraint FRIENDSHIPS_STATUSES_STATUS_ID_FK
        foreign key (STATUS_ID) references STATUSES
            on update cascade,
    constraint FRIENDSHIPS_USERS_USER_ID_FK
        foreign key (SENDER_USER_ID) references USERS
            on update cascade on delete cascade,
    constraint FRIENDSHIPS_USERS_USER_ID_FK_2
        foreign key (RECEIVER_USER_ID) references USERS
            on update cascade on delete cascade
);