package ru.jevent.web.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.jevent.model.User;
import ru.jevent.model.enums.Role;

import java.util.List;

@RestController
@RequestMapping("/ajax/admin/users")
public class AdminAjaxController {

    private final UserHelper helper;

    @Autowired
    public AdminAjaxController(UserHelper helper) {
        this.helper = helper;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable("id") long id) {
        return helper.get(id);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return helper.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        helper.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void update(@RequestParam("userId") long id,
                       @RequestParam("fullName") String fullName,
                       @RequestParam("login") String login,
                       @RequestParam("password") String password,
                       @RequestParam("enabled") boolean enabled) {
        User user = new User();
        user.setFullName(fullName);
        user.setLogin(login);
        user.setPassword(password);
        user.setEnabled(enabled);
        user.addRoles(Role.ROLE_USER);
        if (id == 0) {
            helper.create(user);
        } else {
            user.setId(id);
            helper.update(user);
        }
    }

/*    @RequestMapping(method = RequestMethod.POST)
    public void update(UserTo userTo) {
        if(userTo != null) {
            System.out.println(userTo);
        }
    }*/
}
