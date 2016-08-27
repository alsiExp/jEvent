package ru.jevent.model.Common;

import ru.jevent.model.BaseEntity;
import ru.jevent.model.Team.User;

import java.time.LocalDateTime;


public class Comment extends BaseEntity {
    // simple linear comments
    private String content;
    private User autor;
    private LocalDateTime creationTime;

}
