package ru.jevent.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.model.Enums.Role;
import ru.jevent.model.Enums.Sex;
import ru.jevent.model.User;
import ru.jevent.util.DbPopulator;

import java.util.List;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService service;
    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        User u = service.get(100006);
        if(u.getId() == null ||
                u.getLogin() == null ||
                u.getFirstName() == null) throw new Exception();
        if(u.getId() != 100006) throw new Exception();
    }

    @Test
    public void testSave() throws Exception {
        User user = new User("Test", "User", Sex.MALE, true, "photo.jpg", "login", "pass", Role.ROLE_USER);
        service.save(user);
        if (user.isNew())
            throw new Exception();
        User savedUser = service.get(user.getId());
        savedUser.addRoles(Role.ROLE_USER);
        if (!savedUser.equals(user))
            throw new Exception();

    }

    @Test
    public void testDeleteAndGetAll() throws Exception {
        service.delete(100006L);
        List<User> list = service.getAll();
        for(User u : list) {
            if(u.getId().equals(100006L))
                throw new Exception();
        }
    }
}
