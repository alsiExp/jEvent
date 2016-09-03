package ru.jevent.repository;

import ru.jevent.model.Comment;

public interface CommentRepository {

    Comment save(Comment comment, long userId);

    boolean delete(long id, long userId);

    Comment get(long id, long userId);

}
