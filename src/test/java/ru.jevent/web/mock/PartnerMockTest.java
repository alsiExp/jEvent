package ru.jevent.web.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.model.Partner;
import ru.jevent.util.exception.NotFoundException;
import ru.jevent.web.Partner.PartnerRestController;

@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class PartnerMockTest {
    @Autowired
    private  PartnerRestController controller;

    @Test
    public void testCreate() throws Exception {
        controller.create(new Partner());
    }

    @Test
    public void testUpdate() throws Exception {
        controller.create(new Partner(5, "Somebody"));
    }

    @Test
    public void testDelete() throws Exception {
        controller.delete(13);
    }

    @Test
    public void testGet() throws Exception {
        controller.get(13);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        controller.get(2);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        controller.delete(0);
    }

    @Test
    public void getAll() throws Exception {
        controller.getAll();
    }

}
