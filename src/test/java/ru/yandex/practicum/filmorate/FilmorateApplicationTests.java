package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.filmgenre.FilmGenreDbStorage;
import ru.yandex.practicum.filmorate.storage.friendship.FriendshipDbStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeDbStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
	private final UserDbStorage userStorage;
	private final FilmDbStorage filmStorage;
	private final FilmGenreDbStorage filmGenreStorage;
	private final FriendshipDbStorage friendshipStorage;
	private final GenreDbStorage genreStorage;
	private final LikeDbStorage likeStorage;
	private final MpaStorage mpaStorage;

	@Test
	public void testGetAllGenres() {
		Collection<Genre> genres = genreStorage.getGenres();
		Assertions.assertThat(genres)
				.isNotEmpty()
				.extracting(Genre::getName)
				.containsAll(Arrays.asList("Комедия", "Драма", "Мультфильм", "Триллер", "Документальный", "Боевик"));
	}

	@Test
	public void testGetGenreById() {
		Genre genre = genreStorage.getGenre(2);
		Assertions.assertThat(genre)
				.hasFieldOrPropertyWithValue("id", 2)
				.hasFieldOrPropertyWithValue("name", "Драма");
	}

	@Test
	public void testGetGenreNotFound() {
		Assertions.assertThat(genreStorage.getGenre(9999))
				.isNull();
	}

	@Test
	public void testGetAllMpa() {
		Collection<Mpa> mpas = mpaStorage.getMpa();
		Assertions.assertThat(mpas)
				.isNotEmpty()
				.extracting(Mpa::getName)
				.containsAll(Arrays.asList("G", "PG", "PG-13", "R", "NC-17"));
	}

	@Test
	public void testGetMpaById() {
		Mpa mpa = mpaStorage.getMpa(3);
		Assertions.assertThat(mpa)
				.hasFieldOrPropertyWithValue("id", 3)
				.hasFieldOrPropertyWithValue("name", "PG-13");
	}

	@Test
	public void testGetMpaNotFound() {
		Assertions.assertThat(mpaStorage.getMpa(9999))
				.isNull();
	}

	@Test
	public void testAddUser() {
		User user = User.builder()
				.login("login")
				.email("example@mail.mail")
				.birthday(LocalDate.of(2000, 12, 22))
				.build();
		userStorage.addUser(user);
		AssertionsForClassTypes.assertThat(user).extracting("id").isNotNull();
		AssertionsForClassTypes.assertThat(user).extracting("name").isNotNull();
	}

	@Test
	public void testRemoveUser() {
		User user = User.builder()
				.login("testLogin")
				.email("example@mail.mail")
				.birthday(LocalDate.of(2000, 12, 22))
				.build();
		userStorage.addUser(user);
		userStorage.removeUserById(user.getId());
		Assertions.assertThat(userStorage.getUser(user.getId()))
				.isNull();
	}

	@Test
	public void testUpdateUser() {
		User user = User.builder()
				.login("testName")
				.email("example@mail.mail")
				.birthday(LocalDate.of(2000, 12, 22))
				.build();
		userStorage.addUser(user);
		user.setLogin("testUpdatedName");
		user.setName("  	");
		user.setEmail("updatedExample@mail.mail");
		userStorage.updateUser(user);
		AssertionsForClassTypes.assertThat(userStorage.getUser(user.getId()))
				.hasFieldOrPropertyWithValue("login", "testUpdatedName")
				.hasFieldOrPropertyWithValue("name", "testUpdatedName")
				.hasFieldOrPropertyWithValue("email", "updatedExample@mail.mail");
	}

	@Test
	public void testUpdateUserNotFound() {
		User user = User.builder()
				.id(9999)
				.login("testName")
				.email("example@mail.mail")
				.birthday(LocalDate.of(2000, 12, 22))
				.build();
		Assertions.assertThat(userStorage.updateUser(user))
				.isNull();
	}

	@Test
	public void testFindUserById() {
		User user = User.builder()
				.login("example")
				.email("example@mail.mail")
				.birthday(LocalDate.of(2000, 12, 22))
				.build();
		userStorage.addUser(user);
		userStorage.getUser(user.getId());
		AssertionsForClassTypes.assertThat(user).hasFieldOrPropertyWithValue("id", user.getId());
	}

	@Test
	public void testGetAllUsers() {
		User user = User.builder()
				.login("testExample")
				.email("example@mail.mail")
				.birthday(LocalDate.of(2000, 12, 22))
				.build();
		userStorage.addUser(user);
		Collection<User> users = userStorage.getUsers();
		Assertions.assertThat(users)
				.isNotEmpty();
	}

	@Test
	public void testAddFilm() {
		Film film = Film.builder()
				.name("testFilm")
				.releaseDate(LocalDate.of(2020, 1, 1))
				.mpa(new Mpa(1, null))
				.description("desc")
				.duration(110)
				.build();
		filmStorage.addFilm(film);
		AssertionsForClassTypes.assertThat(film).extracting("id").isNotNull();
		AssertionsForClassTypes.assertThat(film).extracting("name").isNotNull();
	}

	@Test
	public void testRemoveFilm() {
		Film film = Film.builder()
				.name("testFilm")
				.releaseDate(LocalDate.of(2020, 1, 1))
				.mpa(new Mpa(1, null))
				.description("desc")
				.duration(110)
				.build();
		filmStorage.addFilm(film);
		filmStorage.removeFilmById(film.getId());
		Assertions.assertThat(filmStorage.getFilm(film.getId()))
				.isNull();
	}

	@Test
	public void testUpdateFilm() {
		Film film = Film.builder()
				.name("testFilm")
				.releaseDate(LocalDate.of(2020, 1, 1))
				.mpa(new Mpa(1, null))
				.description("desc")
				.duration(110)
				.build();
		filmStorage.addFilm(film);
		film.setName("testUpdatedName");
		film.setReleaseDate(LocalDate.of(2020, 1, 2));
		film.setMpa(new Mpa(2, null));
		film.setDescription("updatedDescription");
		film.setDuration(69);
		filmStorage.updateFilm(film);
		AssertionsForClassTypes.assertThat(filmStorage.getFilm(film.getId()))
				.hasFieldOrPropertyWithValue("name", "testUpdatedName")
				.hasFieldOrPropertyWithValue("releaseDate", LocalDate.of(2020, 1, 2))
				.hasFieldOrPropertyWithValue("description", "updatedDescription")
				.hasFieldOrPropertyWithValue("duration", 69L)
				.extracting(Film::getMpa)
				.extracting(Mpa::getId)
				.isEqualTo(2);
	}

	@Test
	public void testUpdateFilmNotFound() {
		Film film = Film.builder()
				.id(9999)
				.name("testFilm")
				.releaseDate(LocalDate.of(2020, 1, 1))
				.mpa(new Mpa(1, null))
				.description("desc")
				.duration(110)
				.build();
		Assertions.assertThat(filmStorage.updateFilm(film))
				.isNull();
	}

	@Test
	public void testFindFilmById() {
		Film film = Film.builder()
				.name("testFilm")
				.releaseDate(LocalDate.of(2020, 1, 1))
				.mpa(new Mpa(1, null))
				.description("desc")
				.duration(110)
				.build();
		filmStorage.addFilm(film);
		filmStorage.getFilm(film.getId());
		AssertionsForClassTypes.assertThat(film).hasFieldOrPropertyWithValue("id", film.getId());
	}

	@Test
	public void testGetAllFilms() {
		Film film = Film.builder()
				.name("testFilm")
				.releaseDate(LocalDate.of(2020, 1, 1))
				.mpa(new Mpa(1, null))
				.description("desc")
				.duration(110)
				.build();
		filmStorage.addFilm(film);
		Collection<Film> films = filmStorage.getFilms();
		Assertions.assertThat(films)
				.isNotEmpty();
	}

	@Test
	public void testLikeDbStorage() {
		User user = User.builder()
				.login("testLikeUser")
				.email("example@mail.mail")
				.birthday(LocalDate.of(2000, 12, 22))
				.build();
		userStorage.addUser(user);

		Film film = Film.builder()
				.name("testLikeFilm")
				.releaseDate(LocalDate.of(2020, 1, 1))
				.mpa(new Mpa(1, null))
				.description("desc")
				.duration(110)
				.build();
		filmStorage.addFilm(film);
		likeStorage.addLike(film.getId(), user.getId());
		Assertions.assertThat(likeStorage.getLikesQuantity(film.getId())).isEqualTo(1);
		likeStorage.removeLike(film.getId(), user.getId());
		Assertions.assertThat(likeStorage.getLikesQuantity(film.getId())).isEqualTo(0);
	}

	@Test
	public void FilmGenreDbStorage() {
		Film film = Film.builder()
				.name("testFilmGenres")
				.releaseDate(LocalDate.of(2020, 1, 1))
				.mpa(new Mpa(1, null))
				.description("desc")
				.duration(110)
				.genres(new HashSet<>(Arrays.asList(new Genre(1, null), new Genre(4, null))))
				.build();
		filmStorage.addFilm(film);
		filmGenreStorage.updateGenresOfFilm(film);
		Assertions.assertThat(filmGenreStorage.getGenresOfFilms(List.of(film.getId())).get(film.getId()))
				.extracting(Genre::getId)
						.containsAll(Arrays.asList(1, 4));
		Assertions.assertThat(filmGenreStorage.getGenresOfFilms(List.of(film.getId())).get(film.getId()))
				.extracting(Genre::getName)
				.containsAll(Arrays.asList("Комедия", "Триллер"));
		film.setGenres(new HashSet<>(Arrays.asList(new Genre(1, null),
				new Genre(4, null),
				new Genre(5, null)
		)));
		filmGenreStorage.updateGenresOfFilm(film);
		Assertions.assertThat(filmGenreStorage.getGenresOfFilms(List.of(film.getId())).get(film.getId()))
				.extracting(Genre::getId)
				.contains(5);
		Assertions.assertThat(filmGenreStorage.getGenresOfFilms(List.of(film.getId())).get(film.getId()))
				.extracting(Genre::getName)
				.contains("Документальный");
		film.setGenres(new HashSet<>(List.of(new Genre(3, null))));
		filmGenreStorage.updateGenresOfFilm(film);
		Assertions.assertThat(filmGenreStorage.getGenresOfFilms(List.of(film.getId())).get(film.getId()))
				.extracting(Genre::getName)
				.contains("Мультфильм");
	}

	@Test
	public void testFriendshipDbStorage() {
		User user1 = User.builder()
				.login("testUser1")
				.email("example@mail.mail")
				.birthday(LocalDate.of(2000, 12, 22))
				.build();
		userStorage.addUser(user1);
		User user2 = User.builder()
				.login("user2")
				.email("example@mail.mail")
				.birthday(LocalDate.of(2000, 12, 22))
				.build();
		userStorage.addUser(user2);
		User user3 = User.builder()
				.login("testUser3")
				.email("example@mail.mail")
				.birthday(LocalDate.of(2000, 12, 22))
				.build();
		userStorage.addUser(user3);
		Assertions.assertThat(friendshipStorage.getFriends(user1.getId()))
				.isEmpty();
		friendshipStorage.addToFriends(user1.getId(), user2.getId());
		friendshipStorage.addToFriends(user1.getId(), user3.getId());
		Assertions.assertThat(friendshipStorage.getFriends(user1.getId()))
				.hasSize(2);
		Assertions.assertThat(friendshipStorage.getFriends(user1.getId()))
				.extracting(User::getId)
				.containsAll(Arrays.asList(user2.getId(), user3.getId()));
		friendshipStorage.removeFromFriends(user1.getId(), user2.getId());
		Assertions.assertThat(friendshipStorage.getFriends(user1.getId()))
				.hasSize(1);
		Assertions.assertThat(friendshipStorage.getFriends(user1.getId()))
				.extracting(User::getId)
				.containsAll(List.of(user3.getId()));
	}
}
