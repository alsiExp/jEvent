package ru.jevent.web.mock;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.jevent.model.Comment;
import ru.jevent.model.User;
import ru.jevent.util.exception.NotFoundException;
import ru.jevent.web.Comment.CommentRestController;

import java.time.LocalDateTime;

public class CommentMockTest {
    private static ConfigurableApplicationContext appCtx;
    private static CommentRestController controller;

    @BeforeClass
    public static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(CommentRestController.class);
    }

    @AfterClass
    public static void afterClass() {
        appCtx.close();
    }

    @Test
    public void testCreate() throws Exception {
        controller.create(new Comment("Comment without id because it is new", new User(), LocalDateTime.now()));
    }

    @Test
    public void testUpdate() throws Exception {
        controller.update(new Comment(2, "Comment with id 2", new User(), LocalDateTime.now()));
    }

    @Test
    public void testDelete() throws Exception {
        controller.delete(2);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        controller.delete(0);
    }

}
