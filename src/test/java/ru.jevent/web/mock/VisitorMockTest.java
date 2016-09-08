package ru.jevent.web.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.model.Visitor;
import ru.jevent.util.exception.NotFoundException;
import ru.jevent.web.Visitor.VisitorRestController;

@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class VisitorMockTest {
    @Autowired
    private VisitorRestController controller;

    @Test
    public void testCreate() throws Exception {
        controller.create(new Visitor());
    }

    @Test
    public void testUpdate() throws Exception {
        controller.update(controller.get(1));
    }

    @Test
    public void testDelete() throws Exception {
        controller.delete(42);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        controller.delete(2);
    }

    @Test
    public void testAll() throws Exception {
        controller.getAll();
    }


}
