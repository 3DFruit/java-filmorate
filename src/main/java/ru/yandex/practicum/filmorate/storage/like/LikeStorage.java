package ru.yandex.practicum.filmorate.storage.like;


public interface LikeStorage {
    void addLike(int filmId, int userId);
    void removeLike(int filmId, int userId);

    Integer getLikesQuantity(int filmId);
}
