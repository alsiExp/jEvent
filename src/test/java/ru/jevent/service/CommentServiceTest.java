package ru.jevent.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.model.Comment;
import ru.jevent.model.User;
import ru.jevent.util.DbPopulator;
import ru.jevent.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class CommentServiceTest {

    @Autowired
    private CommentService service;
    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Comment c = service.get(100014);
        if (c.getId() == null ||
                c.getContent() == null ||
                c.getDate() == null) throw new Exception();
        if(c.getId() != 100014) throw new Exception();
    }

    @Test
    public void testUpdate() throws Exception {
        Comment c = new Comment();
        c.setId(100024L);
        c.setContent("Updated content");
        c.setDate(LocalDateTime.now());
        service.update(c, 100009L);

        Comment returnedComment = service.get(100024L);
        if(!c.getId().equals(returnedComment.getId())) throw new Exception();
        if(!c.getContent().equals(returnedComment.getContent())) throw new Exception();
        if(c.getDate().compareTo(returnedComment.getDate()) < 0 ) throw new Exception();
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateWithException() throws Exception {
        Comment c = new Comment();
        c.setId(1L);
        c.setContent("Updated content");
        c.setDate(LocalDateTime.now());
        service.update(c, 100009L);
    }

    @Test
    public void testDeleteAndGetAll() throws Exception {
        service.delete(100017L);
        List<Comment> list = service.getAll();
        for(Comment c : list) {
            if(c.getId().equals(100017L))
                throw new Exception();
        }
    }

    @Test
    public void testSave()  throws Exception {
        User u = new User();
        u.setId(100009L);
        Comment c = new Comment();
        c.setContent("Insert new comment");
        c.setDate(LocalDateTime.now());
        c.setAuthor(u);
        service.save(c, 100009L);
        if(c.isNew())
            throw new Exception();
    }

}
