package ru.jevent.repository;

import ru.jevent.model.Visitor.Visitor;

public interface VisitorRepository {

    Visitor save(Visitor visitor);

    boolean delete(long id);

    Visitor get(long id);

}
