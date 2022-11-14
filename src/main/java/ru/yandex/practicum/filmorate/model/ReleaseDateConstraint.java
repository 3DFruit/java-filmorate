package ru.yandex.practicum.filmorate.model;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ReleaseDateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReleaseDateConstraint {
    String message() default "Дата выхода раньше минимальной возможной";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
