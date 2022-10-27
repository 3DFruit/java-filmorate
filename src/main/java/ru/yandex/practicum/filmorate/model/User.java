package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class User {
    private int id;
    @NotNull
    @Email
    private String email;
    @Pattern(regexp = "\\S+")
    private String login;
    private String name;
    private LocalDate birthday;
}
