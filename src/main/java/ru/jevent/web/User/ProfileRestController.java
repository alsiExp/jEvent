package ru.jevent.web.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.jevent.LoggedUser;
import ru.jevent.model.User;

@RestController
@RequestMapping("/rest/profile")
public class ProfileRestController {

    private UserHelper helper;

    @Autowired
    public ProfileRestController(UserHelper helper) {
        this.helper = helper;
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody User user) {
        helper.update(user);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        long userId = LoggedUser.id();
        return helper.get(userId);
    }
}
