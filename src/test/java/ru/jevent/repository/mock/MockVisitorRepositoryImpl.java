package ru.jevent.repository.mock;

import org.springframework.stereotype.Repository;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Email;
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

    private static Visitor visitor;

    static {
        visitor = new Visitor();
        visitor.setId(42L);
        visitor.setFirstName("Алексей");
        visitor.setLastName("Шипилев");
        visitor.setSex(Sex.MALE);
        visitor.setBirthDay(LocalDateTime.now().minus(39, ChronoUnit.YEARS));
        visitor.setRegistered(LocalDateTime.now().minus(6, ChronoUnit.DAYS));
        visitor.addEmail(new Email());
        visitor.setPhone("+7-000-000");
        visitor.setDescription("description");
        visitor.setCost(5000);
    }

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
