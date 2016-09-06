package ru.jevent.web.mock;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.jevent.model.Partner;
import ru.jevent.util.exception.NotFoundException;
import ru.jevent.web.Partner.PartnerRestController;

public class PartnerMockTest {
    private static ConfigurableApplicationContext appCtx;
    private static PartnerRestController controller;

    @BeforeClass
    public static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(PartnerRestController.class);
    }

    @AfterClass
    public static void afterClass() {
        appCtx.close();
    }

    @Test
    public void testCreate() {
        controller.create(new Partner());
    }

    @Test
    public void testUpdate() {
        controller.create(new Partner(5, "Somebody"));
    }

    @Test
    public void testDelete() {
        controller.delete(13);
    }

    @Test
    public void testGet() {
        controller.get(13);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() {
        controller.get(2);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() {
        controller.delete(0);
    }

    @Test
    public void getAll() {
        controller.getAll();
    }

}
