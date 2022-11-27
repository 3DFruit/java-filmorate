package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    public static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    private int id;
    @NotBlank(message = "Имя не должно быть пустым")
    private String title;
    @Size(max = 200, message = "Описание не должно превышать 200 символов")
    private String description;
    @ReleaseDateConstraint
    private LocalDate releaseDate;
    @Min(value = 1, message = "Продолжительность не может быть отрицательной")
    private long duration;
    private Mpa mpa;
    private Set<Genre> genres;

    private final Set<Integer> likes = new HashSet<>();
}
