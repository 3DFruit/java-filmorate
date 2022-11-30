# java-filmorate
Template repository for Filmorate project.
![Схема базы жанных](/QuickDBD.png)

Примеры запросов:  
|  Запрос | Применение | |-----|-----| 
| select FG.GENRE_ID, G.GENRE_NAME  
from FILMS_GENRES as FG  
join GENRES as G on G.GENRE_ID = FG.GENRE_ID  
where FG.FILM_ID=1  |  Получение списка жанров для фильма с id 1 |
| select F.FILM_ID, F.TITLE, F.DESCRIPTION, F.DURATION, F.RELEASE_DATE, F.MPA_ID, M.MPA_NAME  
from FILMS as F  
join MPA as M on F.MPA_ID = M.MPA_ID  
order by FILM_ID | Получение списка фильмов с указанием рейтинга |
| insert into FRIENDSHIPS ( SENDER_USER_ID, RECEIVER_USER_ID, STATUS_ID )  
values ( 1, 2, 1 ) | Запрос на добавление в друзья пользователя с id 2 от пользователя с id 1  |
| delete from FRIENDSHIPS where SENDER_USER_ID in (1 , 2) and RECEIVER_USER_ID in (1, 2) | Удаление дружбы между пользователями с id 1 и 2 |
| insert into FILMS_LIKES ( FILM_ID, USER_ID )   
values ( 1, 2 ) |  Добавление лайка от пользовате с id 1 фильму с id 2   |
| update users set email = example@email.com, login = updatedLogin, user_name = newName, birthday = 2022-10-1 where user_id = 5 | Обновление данных  о пользоватле с id 5  |


