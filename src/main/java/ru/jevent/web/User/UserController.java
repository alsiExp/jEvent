package ru.jevent.web.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.jevent.service.UserService;

@Controller
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }



    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String userList() {
        return "userList";
    }
}
