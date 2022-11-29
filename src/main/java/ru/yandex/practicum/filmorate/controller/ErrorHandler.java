package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.InvalidParameterException;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class,
            ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidateException (final Exception e) {
        log.warn("Ошибка валидации: " + e.getMessage());
        return new ErrorResponse(
                "Ошибка валидации: " + e.getMessage()
        );
    }

    @ExceptionHandler({ObjectNotFoundException.class,
            InvalidParameterException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException (final Exception e) {
        log.warn("Объект не найден: " + e.getMessage());
        return new ErrorResponse(
                "Объект не найден: " + e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Exception e) {
        log.warn("Необработанная ошибка: " + e);
        return new ErrorResponse(
                "Произошла непредвиденная ошибка. " + e.getMessage()
        );
    }
}
