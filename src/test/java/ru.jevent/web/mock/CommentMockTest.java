package ru.jevent.web.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.model.Comment;
import ru.jevent.model.User;
import ru.jevent.util.exception.NotFoundException;
import ru.jevent.web.Comment.CommentRestController;

import java.time.LocalDateTime;

@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CommentMockTest {
    @Autowired
    private CommentRestController controller;

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
