package ru.jevent.web.mock;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.jevent.model.Partner;
import ru.jevent.util.exception.NotFoundException;
import ru.jevent.web.Partner.PartnerRestController;

import java.util.Arrays;


public class PartnerMockTest {

    private static ConfigurableApplicationContext appCtx;
    private static PartnerRestController controller;

    @BeforeClass
    public static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/mock.xml");
        System.out.println("\n" + Arrays.toString(appCtx.getBeanDefinitionNames()) + "\n");
        controller = appCtx.getBean(PartnerRestController.class);
    }

    @AfterClass
    public static void afterClass() {
        appCtx.close();
    }


    @Test
    public void testCreate() throws Exception {
        controller.create(new Partner());
    }

    @Test
    public void testUpdate() throws Exception {
        controller.create(new Partner(5, "T-Systems", "email", "+7-812-000-00-00", "Partner description", null));
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
