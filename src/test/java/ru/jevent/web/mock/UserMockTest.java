package ru.jevent.web.mock;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.jevent.web.User.ProfileRestController;


public class UserMockTest {
    private static ConfigurableApplicationContext appCtx;
    private static ProfileRestController controller;

    @BeforeClass
    public static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-mvc.xml", "spring/spring-app.xml", "spring/mock.xml");
        for(String s : appCtx.getBeanDefinitionNames()) {
            System.out.println(s);
        }
        controller = appCtx.getBean(ProfileRestController.class);
    }

    @AfterClass
    public static void afterClass() {
        appCtx.close();
    }


    @Test
    public void testGet() throws Exception {
        controller.get();
    }
}
