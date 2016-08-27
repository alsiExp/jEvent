package ru.jevent.service;

import ru.jevent.exception.NotFoundException;
import ru.jevent.model.Common.Comment;

import java.util.List;

public interface CommentService {

    public Comment save(Comment comment);

    public void delete(long id) throws NotFoundException;

    public Comment get(long id) throws NotFoundException;

    public List<Comment> getAll();

    public void update(Comment comment) throws NotFoundException;

}
