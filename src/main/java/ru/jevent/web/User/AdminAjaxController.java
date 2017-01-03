package ru.jevent.web.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.jevent.model.User;
import ru.jevent.model.enums.Role;
import ru.jevent.model.enums.Sex;

import java.util.List;

@RestController
@RequestMapping("/ajax/admin/users")
public class AdminAjaxController {

    private final UserHelper helper;

    @Autowired
    public AdminAjaxController(UserHelper helper) {
        this.helper = helper;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return helper.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        helper.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public void update(@PathVariable("id") int id,
                       @RequestParam("firstName") String firstName,
                       @RequestParam("lastName") String lastName,
                       @RequestParam("login") String login,
                       @RequestParam("password") String password,
                       @RequestParam("enabled") boolean enabled,
                       @RequestParam("sex") String sex) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLogin(login);
        user.setPassword(password);
        user.setEnabled(enabled);
        if(sex.equals("male")) {
            user.setSex(Sex.MALE);
        } else if(sex.equals("female")) {
            user.setSex(Sex.FEMALE);
        } else {
            throw new IllegalStateException("Wrong sex value: " + sex);
        }
        user.addRoles(Role.ROLE_USER);

        if (id == 0) {
            helper.create(user);
        } else {
            helper.update(user);
        }
    }
}
