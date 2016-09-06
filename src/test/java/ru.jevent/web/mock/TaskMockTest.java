package ru.jevent.web.mock;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.jevent.model.Task;
import ru.jevent.web.Task.TaskRestController;

public class TaskMockTest {
    private static ConfigurableApplicationContext appCtx;
    private static TaskRestController controller;

    @BeforeClass
    public static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(TaskRestController.class);
    }

    @AfterClass
    public static void afterClass() {
        appCtx.close();
    }

    @Test
    public void testCreate() {
        controller.create(new Task());
    }

    @Test
    public void testDelete() {
        controller.delete(13);
    }

}
