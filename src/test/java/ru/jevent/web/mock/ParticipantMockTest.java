package ru.jevent.web.mock;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.jevent.model.Participant;
import ru.jevent.util.exception.NotFoundException;
import ru.jevent.web.Participant.ParticipantRestController;


public class ParticipantMockTest {

    private static ConfigurableApplicationContext appCtx;
    private static ParticipantRestController controller;

    @BeforeClass
    public static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-mvc.xml", "spring/spring-app.xml", "spring/mock.xml");
        for(String s : appCtx.getBeanDefinitionNames()) {
            System.out.println(s);
        }
        controller = appCtx.getBean(ParticipantRestController.class);
    }

    @AfterClass
    public static void afterClass() {
        appCtx.close();
    }


    @Test
    public void testCreate() throws Exception {
        controller.create(new Participant());
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
