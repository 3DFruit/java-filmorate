# java-filmorate
Template repository for Filmorate project.
![Схема базы жанных](/QuickDBD.png)

Примеры запросов:  
<dl>
    <dt>Получение списка жанров для фильма с id 1</dt>
    <dd>select FG.GENRE_ID, G.GENRE_NAME <br/>
        from FILMS_GENRES as FG  <br/>
        join GENRES as G on G.GENRE_ID = FG.GENRE_ID  <br/>
        where FG.FILM_ID=1  </dd>
    <dt>Получение списка фильмов с указанием рейтинга</dt>
    <dd>select F.FILM_ID, F.TITLE, F.DESCRIPTION, F.DURATION, F.RELEASE_DATE, F.MPA_ID, M.MPA_NAME  <br/>
        from FILMS as F<br/>  
        join MPA as M on F.MPA_ID = M.MPA_ID<br/>  
        order by FILM_ID</dd>
    <dt>Запрос на добавление в друзья пользователя с id 2 от пользователя с id 1</dt>
    <dd>insert into FRIENDSHIPS ( SENDER_USER_ID, RECEIVER_USER_ID, STATUS_ID ) <br/> 
        values ( 1, 2, 1 )</dd>
    <dt>Удаление дружбы между пользователями с id 1 и 2</dt>
    <dd>delete from FRIENDSHIPS where SENDER_USER_ID in (1 , 2) and RECEIVER_USER_ID in (1, 2) </dd>
    <dt>Добавление лайка от пользовате с id 1 фильму с id 2</dt>
    <dd>insert into FILMS_LIKES ( FILM_ID, USER_ID )   
        values ( 1, 2 )</dd>
    <dt>Обновление данных  о пользоватле с id 5</dt>
    <dd>update USERS set EMAIL = example@email.com, LOGIN = updatedLogin, USER_NAME = newName, BIRTHDAY = 2022-10-1 where USER_ID = 5 </dd>
</dl>


