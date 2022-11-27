package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Friendship {
    private Integer id;
    private Integer senderId;
    private Integer receiverId;
    private FriendshipStatus status;
}
