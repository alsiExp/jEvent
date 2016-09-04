package ru.jevent.repository;

import ru.jevent.model.User;

import java.util.List;

public interface UserRepository {
    User save(User user);

    // false if not found
    boolean delete(long id);

    // null if not found
    User get(long id);


    List<User> getAll();
}
