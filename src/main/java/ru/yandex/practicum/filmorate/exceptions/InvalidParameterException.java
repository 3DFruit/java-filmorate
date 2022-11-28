package ru.yandex.practicum.filmorate.exceptions;

public class InvalidParameterException extends RuntimeException{
    public InvalidParameterException(String message) {
        super(message);
    }
}
