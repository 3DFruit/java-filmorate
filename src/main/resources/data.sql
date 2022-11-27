MERGE INTO
    STATUSES(STATUS_NAME)
    KEY (STATUS_NAME)
VALUES
    ( 'PENDING' ),
    ( 'APPROVED' ),
    ( 'DECLINED' );
MERGE INTO
    MPA(MPA_NAME)
    KEY (MPA_NAME)
VALUES
    ( 'G' ),
    ( 'PG' ),
    ( 'PG13' ),
    ( 'R' ),
    ( 'NC17' );
MERGE INTO
    GENRES(GENRE_NAME)
    KEY (GENRE_NAME)
VALUES
    ( 'Комедия' ),
    ( 'Драма' ),
    ( 'Мультфильм' ),
    ( 'Триллер' ),
    ( 'Документальный' ),
    ( 'Боевик' );