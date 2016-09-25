package ru.jevent.web.mock;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.jevent.web.User.UserRestController;

import java.util.Arrays;


public class UserMockTest {
    private static ConfigurableApplicationContext appCtx;
    private static UserRestController controller;

    @BeforeClass
    public static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/mock.xml");
        System.out.println("\n" + Arrays.toString(appCtx.getBeanDefinitionNames()) + "\n");
        controller = appCtx.getBean(UserRestController.class);
    }

    @AfterClass
    public static void afterClass() {
        appCtx.close();
    }


    @Test
    public void testGet() throws Exception {
        controller.get(1);
    }

    @Test
    public void testGetAll() throws Exception {
        controller.getAll();
    }
}
