package ru.jevent.web.User;

import net.rcarz.jiraclient.JiraException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jevent.LoggedUser;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.User;
import ru.jevent.service.JiraService;
import ru.jevent.service.UserService;

import java.util.List;

@Component
public class UserHelper {
    private static final LoggerWrapper LOG = LoggerWrapper.get(AdminRestController.class);
    private final UserService service;
    private final JiraService jiraService;

    @Autowired
    public UserHelper(UserService service, JiraService jiraService) {
        this.service = service;
        this.jiraService = jiraService;
    }

    public User create(User user) {
        LOG.info("create " + user);
        return service.save(user);
    }

    public void update(User user) {
        LOG.info("update " + user);
        service.update(user);
    }

    public User get(long id) {
        LOG.info("get " + id);
        return service.get(id);
    }

    public void delete(long id) {
        LOG.info("delete " + id);
        service.delete(id);
    }

    public List<User> getAll() {
        LOG.info("get all users");
        return service.getAll();
    }

    public void setJiraValidCredentials(long id, boolean cred) {
        LOG.info("User" + id + "set valid credential for Jira "  + cred);
        service.setJiraValidCredentials(id, cred);
    }

    public List<String> testJira() {
        try {
            return jiraService.test(LoggedUser.id());
        } catch (JiraException je) {
            System.out.println();
            System.err.println(je.getMessage());
            if (je.getCause() != null)
                System.err.println(je.getCause().getMessage());
            return null;
        }
    }


}
