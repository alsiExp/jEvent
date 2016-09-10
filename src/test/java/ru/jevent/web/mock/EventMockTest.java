package ru.jevent.web.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.model.Event;
import ru.jevent.util.exception.NotFoundException;
import ru.jevent.web.Event.EventRestController;

import java.util.List;

@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class EventMockTest {
    @Autowired
    private EventRestController controller;

    @Test
    public void testCreate() throws Exception {
        controller.create(new Event());
    }

    @Test
    public void testUpdate() throws Exception {
        controller.update(new Event(8, "Event"));
    }

    @Test
    public void testDelete() throws Exception {
        controller.delete(2);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        controller.delete(0);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Event> l = controller.getAll();
        l.get(0);
    }
}
