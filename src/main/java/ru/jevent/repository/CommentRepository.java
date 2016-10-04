package ru.jevent.repository;

import ru.jevent.model.Comment;

import java.util.List;

public interface CommentRepository {

    Comment save(Comment comment);

    boolean delete(long id);

    Comment get(long id);

    List<Comment> getAll();

}
