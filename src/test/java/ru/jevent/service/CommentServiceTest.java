package ru.jevent.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.TestData;
import ru.jevent.model.Comment;
import ru.jevent.util.DbPopulator;
import ru.jevent.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/service.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class CommentServiceTest {

    @Autowired
    private CommentService service;
    @Autowired
    private TestData testData;
    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Comment c = service.get(100014L);
        if (c.getId() == null ||
                c.getContent() == null ||
                c.getDate() == null) throw new Exception();
        if(!c.getId().equals(100014L)) throw new Exception();
    }

    @Test
    public void testUpdate() throws Exception {
        Comment c = service.get(100021L);
        c.setContent("Updated content");
        c.setDate(LocalDateTime.now());
        service.update(c);

        Comment returnedComment = service.get(c.getId());
        if(!c.equals(returnedComment))
            throw new Exception();
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateWithException() throws Exception {
        Comment c = testData.getExistingComment();
        c.setId(1L);
        service.update(c);
    }

    @Test
    public void testDeleteAndGetAll() throws Exception {
        service.delete(100017L);
        List<Comment> list = service.getAll();
        if(list.isEmpty()) throw new Exception();
        for(Comment c : list) {
            if(c.getId().equals(100017L))
                throw new Exception();
        }
    }

    @Test
    public void testSave() throws Exception {
        Comment c = testData.getNewComment();
        service.save(c);
        Comment returnedComment = service.get(c.getId());
        if(!c.equals(returnedComment))
            throw new Exception();
    }
}
