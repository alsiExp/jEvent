package ru.jevent.service;

import ru.jevent.model.User;
import ru.jevent.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

    User save(User user);

    void update(User user) throws NotFoundException;

    User get(long id) throws NotFoundException;

    void delete(long id) throws NotFoundException;

    List<User> getAll();
}
