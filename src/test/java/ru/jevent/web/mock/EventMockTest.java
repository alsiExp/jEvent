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
        appCtx = new ClassPathXmlApplicationContext("spring/spring-mvc.xml", "spring/spring-app.xml", "spring/mock.xml");
        for(String s : appCtx.getBeanDefinitionNames()) {
            System.out.println(s);
        }
        controller = appCtx.getBean(EventRestController.class);
    }

    @AfterClass
    public static void afterClass() {
        appCtx.close();
    }



    @Test
    public void testUpdate() throws Exception {
        Event event = new Event();
        event.setId(8L);
        event.setName("Конференция Joker");
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
