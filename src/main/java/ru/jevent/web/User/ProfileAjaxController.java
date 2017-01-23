package ru.jevent.web.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.jevent.LoggedUser;
import ru.jevent.model.User;

import java.util.List;

@RestController
@RequestMapping("/ajax/profile")
public class ProfileAjaxController {
    private final UserHelper helper;

    @Autowired
    public ProfileAjaxController(UserHelper helper) {
        this.helper = helper;
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<String> update(@RequestParam("fullName") String fullName,
                       @RequestParam("login") String login,
                       @RequestParam("password") String password,
                       @RequestParam(value = "jiraLogin", required = false) String jiraLogin,
                       @RequestParam(value = "jiraPassword", required = false) String jiraPassword) {
        User user = new User();
        user.setId(LoggedUser.id());
        user.setFullName(fullName);
        user.setLogin(login);
        user.setPassword(password);
        user.setEnabled(LoggedUser.get().isEnabled());
        user.setRoles(LoggedUser.get().getAuthorities());
        if(!jiraLogin.isEmpty()){
            user.setJiraLogin(jiraLogin);
        }
        if(!jiraPassword.isEmpty()){
            user.setJiraPassword(jiraPassword);
        }

        helper.update(user);
        List<String> list = helper.testJira();
        if(list != null && !list.isEmpty()) {
            helper.setJiraValidCredentials(user.getId(), true);
            return list;
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        long userId = LoggedUser.id();
        return helper.get(userId);
    }
}
