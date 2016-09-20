package ru.jevent.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.model.Comment;
import ru.jevent.model.Enums.Sex;
import ru.jevent.model.User;
import ru.jevent.model.Visitor;
import ru.jevent.util.DbPopulator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class VisitorServiceTest {

    @Autowired
    private VisitorService service;
    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Visitor v = service.get(100003);
        if(v.getId() == null ||
                v.getEmail() == null ||
                v.getFirstName() == null) throw new Exception();
        if(v.getClass() != new Visitor().getClass()) throw new Exception();
        if(v.getId() != 100003) throw new Exception();
        if(v.getCommentList().size() == 0) throw new Exception();
    }

    @Test
    public void testSave() throws Exception {
        Visitor visitor = new Visitor();
        visitor.setFirstName("Test");
        visitor.setLastName("Visitor");
        visitor.setSex(Sex.FEMALE);
        visitor.setEnabled(true);
        visitor.setPhotoURL("testvisitor.jpg");
        visitor.setBirthDay(LocalDateTime.now().minus(39, ChronoUnit.YEARS));
        visitor.setRegistered(LocalDate.now().minus(20, ChronoUnit.DAYS));
        visitor.setEmail("test@visitor.com");
        visitor.setPhone("+0-000-000-00-00");
        visitor.setGitHubAccount("testgGithub");
        //
        visitor.setBiography("test biography");
        visitor.setDescription("test description");
        visitor.setCost(5000);
        List<Comment> list = new ArrayList<>();
        User u = new User();
        u.setId(100009L);
        Comment c = new Comment();
        c.setContent("Insert new comment");
        c.setDate(LocalDateTime.now());
        c.setAuthor(u);
        list.add(c);
        service.save(visitor);
    }

    @Test
    public void testDeleteAndGetAll() throws Exception {

    }
}
