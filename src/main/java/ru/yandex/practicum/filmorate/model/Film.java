package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Film {
    public static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    private int id;
    @NotBlank(message = "Имя не должно быть пустым")
    private String name;
    @Size(max = 200, message = "Описание не должно превышать 200 символов")
    private String description;
    @ReleaseDateConstraint
    private LocalDate releaseDate;
    @Min(value = 1, message = "Продолжительность не может быть отрицательной")
    private long duration;
    @NotNull
    private Mpa mpa;
    private Set<Genre> genres;
}
