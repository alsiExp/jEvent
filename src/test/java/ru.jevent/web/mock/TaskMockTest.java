package ru.jevent.web.mock;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.jevent.model.Task;
import ru.jevent.util.exception.NotFoundException;
import ru.jevent.web.Task.TaskRestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
    public void testCreate() throws Exception {
        controller.create(new Task());
    }

    @Test
    public void testUpdate() throws Exception {
        controller.update(controller.get(1));
    }

    @Test
    public void testDelete() throws Exception {
        controller.delete(13);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        controller.delete(0);
    }

    @Test
    public void testGetByInterval() throws Exception {
        controller.getByInterval(LocalDateTime.now(), LocalDateTime.now().plus(5, ChronoUnit.DAYS));
    }

    @Test
    public void testGetAllAssigned() throws Exception {
        controller.getAllAssigned();
    }

    @Test
    public void testGetAllCreated() throws Exception {
        controller.getAllCreated();
    }

}
