package ru.jevent.web.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.jevent.model.User;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/rest/admin/users")
public class AdminRestController {

    private final UserHelper helper;

    @Autowired
    public AdminRestController(UserHelper helper) {
        this.helper = helper;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@RequestBody User user) {
        User created = helper.create(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/admin/users/{id}")
                .buildAndExpand(created.getId()).toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriOfNewResource);

        return new ResponseEntity<>(created, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody User user) {
        helper.update(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable("id") long id) {
        return helper.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        helper.delete(id);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return helper.getAll();
    }
}
