package ru.jevent.repository;

import ru.jevent.model.Common.Comment;

public interface CommentRepository {

    Comment save(Comment comment);

    boolean delete(long id);

    Comment get(long id);

}
