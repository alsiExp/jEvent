package ru.jevent;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.jevent.web.Participant.ParticipantRestController;


public class SpringMain {

    public static void main(String[] args) {

        try(ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            String[] beanNames = appCtx.getBeanDefinitionNames();
            for(String s : beanNames) {
                System.out.println(s);
            }
            ParticipantRestController commClr = appCtx.getBean(ParticipantRestController.class);
            commClr.delete(2);
            //commClr.get(2);
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
