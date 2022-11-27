package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private int id;
    @NotNull
    @Email
    private String email;
    @Pattern(regexp = "\\S+")
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;
    private final Set<Integer> friends = new HashSet<>();
}
