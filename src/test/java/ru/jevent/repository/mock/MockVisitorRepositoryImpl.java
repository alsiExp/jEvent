package ru.jevent.repository.mock;

import org.springframework.stereotype.Repository;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Enums.Sex;
import ru.jevent.model.Visitor;
import ru.jevent.repository.VisitorRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MockVisitorRepositoryImpl implements VisitorRepository{
    private static final LoggerWrapper LOG = LoggerWrapper.get(MockVisitorRepositoryImpl.class);

    private static Visitor visitor = new Visitor(42L, "Алексей", "Шипилев", Sex.MALE, null, LocalDateTime.now().minus(35, ChronoUnit.YEARS), LocalDateTime.now().minus(6, ChronoUnit.DAYS),
            "email", "phone", null, null, "twitter", null, null, "description", 5000, null);

    public static Visitor getVisitor() {
        return visitor;
    }

    @Override
    public Visitor save(Visitor visitor) {
        return this.visitor;
    }

    @Override
    public boolean delete(long id) {
        return visitor.getId() == id;
    }

    @Override
    public Visitor get(long id) {
        return visitor;
    }

    @Override
    public Visitor getByEmail(String email) {
        return visitor;
    }

    @Override
    public List<Visitor> getAll() {
        return new ArrayList<Visitor>();
    }
}
