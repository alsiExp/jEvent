package ru.jevent.web.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.model.User;
import ru.jevent.web.User.AdminRestController;

@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class AdminMockTest {
    @Autowired
    private AdminRestController controller;

    @Test
    public void testCreate() throws Exception {
        controller.create(new User());
    }

    @Test
    public void testUpdate() throws Exception {
        controller.update(controller.get(1));
    }

    @Test
    public void testDelete() throws Exception {
        controller.delete(12);
    }

    @Test
    public void testGetAll() throws Exception {
        controller.getAll();
    }
}
