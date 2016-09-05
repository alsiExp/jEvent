package ru.jevent;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.jevent.web.Comment.CommentRestController;

public class SpringMain {

    public static void main(String[] args) {

        try(ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            String[] beanNames = appCtx.getBeanDefinitionNames();
            for(String s : beanNames) {
                System.out.println(s);
            }
            CommentRestController commClr = appCtx.getBean(CommentRestController.class);
            commClr.delete(2);
        }
    }

}
