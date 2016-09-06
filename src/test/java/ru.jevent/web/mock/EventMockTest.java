package ru.jevent.web.mock;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.jevent.model.Event;
import ru.jevent.util.exception.NotFoundException;
import ru.jevent.web.Event.EventRestController;

import java.util.List;

public class EventMockTest {

    private static ConfigurableApplicationContext appCtx;
    private static EventRestController controller;

    @BeforeClass
    public static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(EventRestController.class);
    }

    @AfterClass
    public static void afterClass() {
        appCtx.close();
    }

    @Test
    public void testCreate() {
        controller.create(new Event());
    }

    @Test
    public void testUpdate() {
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
    public void testGetAll() {
        List<Event> l = controller.getAll();
        l.get(0);
    }
}
