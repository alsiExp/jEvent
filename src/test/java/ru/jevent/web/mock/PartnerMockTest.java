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
        appCtx = new ClassPathXmlApplicationContext("spring/spring-mvc.xml", "spring/spring-app.xml", "spring/mock.xml");
        for(String s : appCtx.getBeanDefinitionNames()) {
            System.out.println(s);
        }
        controller = appCtx.getBean(PartnerRestController.class);
    }

    @AfterClass
    public static void afterClass() {
        appCtx.close();
    }



    @Test
    public void testUpdate() throws Exception {
        Partner partner = controller.get(13L);
        partner.setId(5L);
        partner.setName("T-Systems");
        partner.setContactEmail("email");
        controller.update(partner);
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
