package ru.jevent.web.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.model.Task;
import ru.jevent.util.exception.NotFoundException;
import ru.jevent.web.Task.TaskRestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TaskMockTest {
    @Autowired
    private  TaskRestController controller;

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
