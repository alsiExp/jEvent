package ru.jevent.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.model.Visitor;
import ru.jevent.util.DbPopulator;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class VisitorServiceTest {

    @Autowired
    private VisitorService visitorService;
    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Visitor v = visitorService.get(100003);
        if(v.getId() == null ||
                v.getEmail() == null ||
                v.getFirstName() == null) throw new Exception();
        if(v.getClass() != new Visitor().getClass()) throw new Exception();
        if(v.getId() != 100003) throw new Exception();
        if(v.getCommentList().size() == 0) throw new Exception();
    }

}
