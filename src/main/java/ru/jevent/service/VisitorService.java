package ru.jevent.service;

import ru.jevent.model.Visitor;
import ru.jevent.util.exception.NotFoundException;

import java.util.List;

public interface VisitorService {

    Visitor save(Visitor visitor);

    void update(Visitor visitor) throws NotFoundException;

    Visitor get(long id) throws NotFoundException;

    void delete(long id) throws NotFoundException;

    List<Visitor> getAll();
}
