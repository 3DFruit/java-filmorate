package ru.yandex.practicum.filmorate.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDateConstraint, LocalDate> {

    @Override
    public void initialize(ReleaseDateConstraint releaseDateConstraint) {

    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return !localDate.isBefore(Film.MIN_RELEASE_DATE);
    }


}
