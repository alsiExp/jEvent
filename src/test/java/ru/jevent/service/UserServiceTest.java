package ru.jevent.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.TestData;
import ru.jevent.model.User;
import ru.jevent.util.DbPopulator;

import java.util.List;

import static ru.jevent.Profiles.JPA;
import static ru.jevent.Profiles.POSTGRES;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/service.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({POSTGRES, JPA})
public class UserServiceTest {

    @Autowired
    private UserService service;
    @Autowired
    private DbPopulator dbPopulator;
    @Autowired
    private TestData testData;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        User u = service.get(100008L);
        if(!u.equals(testData.getExistingUser())) {
            throw new Exception();
        }
    }

    @Test
    public void testSave() throws Exception {
        User user = testData.getNewUser();
        service.save(user);
        User savedUser = service.get(user.getId());
        if (!savedUser.equals(user))
            throw new Exception();
    }

    @Test
    public void testUpdate() throws Exception {
        User user = testData.getExistingUser();
        user.setLogin("testlogin");
        service.update(user);
        User savedUser = service.get(user.getId());
        if (!savedUser.equals(user))
            throw new Exception();
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveWithoutLogin() throws Exception {
        User user = testData.getExistingUser();
        user.setLogin(null);
        service.save(user);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveWithEmptyPass() throws Exception {
        User user = testData.getExistingUser();
        user.setPassword("");
        service.save(user);
    }

    @Test
    public void testDeleteAndGetAll() throws Exception {
        service.delete(100006L);
        List<User> list = service.getAll();
        if(list.isEmpty()) throw new Exception();
        for(User u : list) {
            if(u.getId().equals(100006L))
                throw new Exception();
        }
    }
}
