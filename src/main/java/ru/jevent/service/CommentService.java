package ru.jevent.service;

import ru.jevent.model.Comment;
import ru.jevent.util.exception.NotFoundException;

public interface CommentService {

    Comment save(Comment comment, long userId);

    Comment update(Comment comment, long userId) throws NotFoundException;

    void delete(long id, long userId) throws NotFoundException;

    Comment get(long id, long userId) throws NotFoundException;

}
