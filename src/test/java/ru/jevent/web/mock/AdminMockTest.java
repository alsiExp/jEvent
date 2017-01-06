package ru.jevent.web.mock;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.jevent.web.User.AdminRestController;


public class AdminMockTest {

    private static ConfigurableApplicationContext appCtx;
    private static AdminRestController controller;

    @BeforeClass
    public static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-mvc.xml", "spring/spring-app.xml", "spring/mock.xml");
        for(String s : appCtx.getBeanDefinitionNames()) {
            System.out.println(s);
        }
        controller = appCtx.getBean(AdminRestController.class);
    }

    @AfterClass
    public static void afterClass() {
        appCtx.close();
    }

    @Test
    public void testUpdate() throws Exception {
        controller.update(controller.get(1));
    }

    @Test
    public void testDelete() throws Exception {
        controller.delete(12);
    }

    @Test
    public void testGetAll() throws Exception {
        controller.getAll();
    }
}
