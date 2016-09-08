package ru.jevent.web.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.web.User.UserRestController;

@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMockTest {
    @Autowired
    private UserRestController controller;

    @Test
    public void testGet() throws Exception {
        controller.get(1);
    }

    @Test
    public void testGetAll() throws Exception {
        controller.getAll();
    }
}
