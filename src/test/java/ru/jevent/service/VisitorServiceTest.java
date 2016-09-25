package ru.jevent.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.TestData;
import ru.jevent.model.Visitor;
import ru.jevent.util.DbPopulator;
import ru.jevent.util.exception.NotFoundException;

import java.util.List;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/service.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class VisitorServiceTest {

    @Autowired
    private VisitorService service;
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
        Visitor visitor = testData.getNewVisitor();
        service.save(visitor);
        Visitor savedVisitor = service.get(visitor.getId());
        if(!savedVisitor.equals(visitor)) {
            throw new Exception();
        }
    }

    @Test
    public void testUpdate() throws Exception {
        Visitor visitor = testData.getNewVisitor();
        visitor.setDescription("Actual test description");
        service.update(visitor);
        Visitor savedVisitor = service.get(visitor.getId());
        if(!savedVisitor.equals(visitor)) {
            throw new Exception();
        }
    }

    @Test
    public void testDeleteAndGetAll() throws Exception {
        service.delete(100003L);
        List<Visitor> list = service.getAll();
        for(Visitor v : list) {
            if(v.getId().equals(100003L))
                throw new Exception();
        }
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateWithException() throws Exception {
        Visitor v = testData.getExistingVisitor();
        v.setId(911L);
        service.update(v);
    }
}
