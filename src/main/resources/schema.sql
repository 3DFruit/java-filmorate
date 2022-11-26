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
create table if not exists FILMS
(
    FILM_ID      INTEGER auto_increment,
    TITLE        CHARACTER VARYING(100) not null,
    DESCRIPTION  CHARACTER VARYING(200),
    DURATION     INTEGER                not null,
    RELEASE_DATE DATE                   not null,
    constraint FILMS_PK
        primary key (FILM_ID)
);
create table if not exists FILMS_GENRES
(
    FILM_GENRE_ID INTEGER auto_increment,
    GENRE         CHARACTER VARYING(14) not null,
    FILM_ID       INTEGER               not null,
    constraint FILMS_GENRES_PK
        primary key (FILM_GENRE_ID),
    constraint "FILMS_GENRES_FILMS_null_fk"
        foreign key (FILM_ID) references FILMS
            on update cascade on delete cascade
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
create table if not exists FRIENDSHIPS
(
    FRIENDSHIP_ID    INTEGER auto_increment,
    SENDER_USER_ID   INTEGER not null,
    RECEIVER_USER_ID INTEGER not null,
    STATUS           CHARACTER VARYING(13) not null,
    constraint "FRIENDSHIPS_PK"
        primary key (FRIENDSHIP_ID),
    constraint FRIENDSHIPS_USERS_USER_ID_FK
        foreign key (SENDER_USER_ID) references USERS
            on update cascade on delete cascade,
    constraint FRIENDSHIPS_USERS_USER_ID_FK_2
        foreign key (RECEIVER_USER_ID) references USERS
            on update cascade on delete cascade
);