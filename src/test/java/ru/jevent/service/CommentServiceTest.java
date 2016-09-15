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
        if(c.getAuthor().getClass() != new User().getClass()) throw new Exception();
        if(c.getId() != 100014) throw new Exception();
    }
}