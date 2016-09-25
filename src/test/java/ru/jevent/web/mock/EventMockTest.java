package ru.jevent.web.mock;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.jevent.model.Event;
import ru.jevent.model.User;
import ru.jevent.util.exception.NotFoundException;
import ru.jevent.web.Event.EventRestController;

import java.util.Arrays;
import java.util.List;


public class EventMockTest {

    private static ConfigurableApplicationContext appCtx;
    private static EventRestController controller;

    @BeforeClass
    public static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/mock.xml");
        System.out.println("\n" + Arrays.toString(appCtx.getBeanDefinitionNames()) + "\n");
        controller = appCtx.getBean(EventRestController.class);
    }

    @AfterClass
    public static void afterClass() {
        appCtx.close();
    }


    @Test
    public void testCreate() throws Exception {
        controller.create(new Event());
    }

    @Test
    public void testUpdate() throws Exception {
        controller.update(new Event(8, "Конференция Joker", new User(), "joker16", "Conf address",
                "Joker 2016 description", null, null, null, null, null, null));
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
