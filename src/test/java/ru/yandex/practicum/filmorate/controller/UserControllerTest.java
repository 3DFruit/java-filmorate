package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    static UserController controller;

    @BeforeAll
    static void create(){
        controller = new UserController();
    }

    @Test
    void addUserEmptyEmail() {
        User user  = new User();
        user.setId(0);
        user.setName("resu");
        user.setLogin("user");
        user.setEmail("");
        user.setBirthday(LocalDate.of(2000, 1, 1));
        assertThrows(ValidationException.class, () -> controller.addUser(user));
    }

    @Test
    void addUserEmailWithoutAtSign() {
        User user  = new User();
        user.setId(0);
        user.setName("resu");
        user.setLogin("user");
        user.setEmail("emailexample");
        user.setBirthday(LocalDate.of(2000, 1, 1));
        assertThrows(ValidationException.class, () -> controller.addUser(user));
    }

    @Test
    void addUserEmptyLogin() {
        User user  = new User();
        user.setId(0);
        user.setName("resu");
        user.setLogin("");
        user.setEmail("email@example.com");
        user.setBirthday(LocalDate.of(2000, 1, 1));
        assertThrows(ValidationException.class, () -> controller.addUser(user));
    }

    @Test
    void addUserLoginWithSpaces() {
        User user  = new User();
        user.setId(0);
        user.setName("resu");
        user.setLogin("us er");
        user.setEmail("email@example.com");
        user.setBirthday(LocalDate.of(2000, 1, 1));
        assertThrows(ValidationException.class, () -> controller.addUser(user));
    }

    @Test
    void addUserWrongBirthday() {
        User user  = new User();
        user.setId(0);
        user.setName("resu");
        user.setLogin("user");
        user.setEmail("email@example.com");
        user.setBirthday(LocalDate.now());
        assertThrows(ValidationException.class, () -> controller.addUser(user));
    }

    @Test
    void addUserEmptyName() {
        User user  = new User();
        user.setId(0);
        user.setName("");
        user.setLogin("user");
        user.setEmail("email@example.com");
        user.setBirthday(LocalDate.of(2000, 1, 1));
        try {
            assertEquals(user,controller.addUser(user));
        }
        catch (ValidationException e) {
            e.printStackTrace();
        }
    }
}