package ru.jevent.service;

import ru.jevent.model.Comment;
import ru.jevent.util.exception.NotFoundException;

import java.util.List;

public interface CommentService {

    Comment save(Comment comment, long userId);

    Comment update(Comment comment, long userId) throws NotFoundException;

    void delete(long id) throws NotFoundException;

    Comment get(long id) throws NotFoundException;

    List<Comment> getAll();
}
