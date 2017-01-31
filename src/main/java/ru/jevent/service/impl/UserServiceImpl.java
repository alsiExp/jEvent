package ru.jevent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.jevent.LoggedUser;
import ru.jevent.model.User;
import ru.jevent.repository.UserRepository;
import ru.jevent.service.UserService;
import ru.jevent.util.exception.ExceptionUtil;
import ru.jevent.util.exception.NotFoundException;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public void update(User user) throws NotFoundException {
        if(user.getId() != 0) {
            User oldUser = repository.get(user.getId());
            if(user.getJiraLogin() == null) {
                user.setJiraLogin(oldUser.getJiraLogin());
            }
            if(user.getJiraPassword() == null) {
                user.setJiraPassword(oldUser.getJiraPassword());
            }
        }
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

    @Override
    public void setJiraValidCredentials(long id, boolean cred) {
        ExceptionUtil.check(repository.setJiraValidCredentials(id, cred), id);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User u = repository.getByLogin(login);
        if (u == null) {
            throw new UsernameNotFoundException("User " + login + " is not found");
        }
        return new LoggedUser(u);
    }
}
