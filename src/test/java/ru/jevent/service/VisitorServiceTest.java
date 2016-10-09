package ru.jevent.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.TestData;
import ru.jevent.model.Visitor;
import ru.jevent.util.DbPopulator;

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
        Visitor v = service.get(100004L);
        if(!v.equals(testData.getExistingVisitor())) {
            throw new Exception();
        }
    }

    @Test
    public void testGetByEmail() throws Exception {
        Visitor v = service.getByEmail("jbaruch@gmail.com");
        if(!v.equals(testData.getExistingVisitor())) {
            throw new Exception();
        }
    }

    @Test
    public void testSimpleSave() throws Exception {
        Visitor visitor = testData.getNewVisitor();
        service.save(visitor);
        Visitor savedVisitor = service.get(visitor.getId());
        if(!savedVisitor.equals(visitor)) {
            throw new Exception();
        }
    }

    @Test
    public void testSaveWithNewComments() throws Exception {
        Visitor visitor = testData.getNewVisitorWithNewComments();
        service.save(visitor);
        Visitor savedVisitor = service.get(visitor.getId());
        if(!savedVisitor.equals(visitor)) {
            throw new Exception();
        }
    }

    @Test
    public void testUpdate() throws Exception {
        Visitor visitor = service.get(100004L);
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
        if(list.isEmpty()) throw new Exception();
        for(Visitor v : list) {
            if(v.getId().equals(100003L))
                throw new Exception();
        }
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testUpdateWithException() throws Exception {
        Visitor v = testData.getExistingVisitor();
        v.setRegistered(null);
        service.update(v);
    }
}
