package ru.jevent.repository;

import ru.jevent.model.Visitor;
import java.util.List;

public interface VisitorRepository {

    Visitor save(Visitor visitor);

    boolean delete(long id);

    Visitor get(long id);

    Visitor getByEmail(String email);

    List<Visitor> getAll();
}
