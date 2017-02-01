package ru.jevent.web.User;

import net.rcarz.jiraclient.JiraException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jevent.LoggedUser;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.User;
import ru.jevent.service.JiraService;
import ru.jevent.service.UserService;
import ru.jevent.util.PasswordUtil;

import java.util.List;

@Component
public class UserHelper {
    private static final LoggerWrapper LOG = LoggerWrapper.get(UserHelper.class);
    private final UserService service;
    private final JiraService jiraService;

    @Autowired
    public UserHelper(UserService service, JiraService jiraService) {
        this.service = service;
        this.jiraService = jiraService;
    }

    public User create(User user) {
        LOG.info("create " + user);
        return service.save(PasswordUtil.getEncoded(user));
    }

    public void update(User user) {
        LOG.info("update " + user);
        service.update(PasswordUtil.getEncoded(user));
    }

    public User get(long id) {
        LOG.info("get " + id);
        User user = service.get(id);
        user.setPassword(null);
        return user;
    }

    public void delete(long id) {
        LOG.info("delete " + id);
        service.delete(id);
    }

    public List<User> getAll() {
        LOG.info("get all users");
        List<User> all = service.getAll();
        all.forEach(u -> u.setPassword(null));
        return all;
    }

    public void setJiraValidCredentials(long id, boolean cred) {
        LOG.info("User" + id + "set valid credential for Jira "  + cred);
        service.setJiraValidCredentials(id, cred);
    }

    public List<String> testJira() {
        try {
            return jiraService.test(LoggedUser.id());
        } catch (JiraException je) {
            LOG.error(je.getMessage());
            je.printStackTrace();
            return null;
        }
    }


}
