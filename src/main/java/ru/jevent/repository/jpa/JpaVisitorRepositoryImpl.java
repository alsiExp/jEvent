package ru.jevent.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.jevent.model.Visitor;
import ru.jevent.repository.VisitorRepository;

import java.util.List;

@Repository
public class JpaVisitorRepositoryImpl implements VisitorRepository {

    @Override
    public Visitor save(Visitor visitor) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Visitor get(long id) {
        return null;
    }

    @Override
    public Visitor getByEmail(String email) {
        return null;
    }

    @Override
    public List<Visitor> getAll() {
        return null;
    }
}
