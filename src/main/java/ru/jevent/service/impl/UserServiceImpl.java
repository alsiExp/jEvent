package ru.jevent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jevent.model.User;
import ru.jevent.repository.UserRepository;
import ru.jevent.service.UserService;
import ru.jevent.util.exception.ExceptionUtil;
import ru.jevent.util.exception.NotFoundException;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        if(user.getId() != 0 && user.getJiraLogin() == null && user.getJiraPassword() == null) {
            User oldUser = repository.get(user.getId());
            user.setJiraLogin(oldUser.getJiraLogin());
            user.setJiraPassword(oldUser.getJiraPassword());
        }
        return repository.save(user);
    }

    @Override
    public void update(User user) throws NotFoundException {
        ExceptionUtil.check(repository.save(user), user.getId());
    }

    @Override
    public User get(long id) throws NotFoundException {
        return ExceptionUtil.check(repository.get(id), id);
    }

    @Override
    public void delete(long id) throws NotFoundException {
        ExceptionUtil.check(repository.delete(id), id);
    }

    @Override
    public List<User> getAll() {
        return repository.getAll();
    }
}
