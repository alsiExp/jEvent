package ru.jevent.repository;

import ru.jevent.model.Comment;

import java.util.List;

public interface CommentRepository {

    Comment save(Comment comment);

    boolean delete(long id);

    Comment get(long id);

    List<Comment> getAllByVisitorId(long id);
    List<Comment> getAllByEventId(long id);
    List<Comment> getAllByTaskId(long id);

    List<Comment> getAll();

}
