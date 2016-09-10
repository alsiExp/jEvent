package ru.jevent.repository.mock;

import org.springframework.stereotype.Repository;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Enums.Role;
import ru.jevent.model.User;
import ru.jevent.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MockUserRepositoryImpl implements UserRepository {
    private static final LoggerWrapper LOG = LoggerWrapper.get(MockUserRepositoryImpl.class);
    private static User user = new User(1, "Екатерина", "Курилова", "partners@jugru.org", "jug", Role.ROLE_USER);

    public static User getUser() {
        return user;
    }

    @Override
    public User save(User user) {
        return this.user;
    }

    @Override
    public boolean delete(long id) {
        return id != 0;
    }

    @Override
    public User get(long id) {
        return user;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<User>() {{
            add(user);
        }};
    }
}
