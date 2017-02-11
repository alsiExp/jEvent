package ru.jevent.web.Speech;


import com.sun.org.apache.regexp.internal.RE;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.TestData;
import ru.jevent.model.Event;
import ru.jevent.model.Partner;
import ru.jevent.model.Speech;
import ru.jevent.model.User;
import ru.jevent.service.EventService;
import ru.jevent.service.SpeechService;
import ru.jevent.web.WebTest;
import ru.jevent.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.jevent.Profiles.JPA;
import static ru.jevent.Profiles.POSTGRES;


@ContextConfiguration({
        // "classpath:spring/spring-app.xml",
        // "classpath:spring/spring-db.xml",
        "classpath:spring/service.xml"
})

@ActiveProfiles({POSTGRES, JPA})
public class SpeechRestControllerTest extends WebTest{

    @Autowired
    private SpeechHelper helper;

    @Autowired
    private TestData testData;

    @Autowired
    private SpeechService speechService;

    @Autowired
    private EventService eventService;

    private static final String REST_URL = "/rest/speeches";

    // Speech testSpeech = speechService.get(100016L);

    @Test
    public void createTest() throws Exception {
    User user = testData.getNewUser();
        System.out.println(user.toString());
        System.out.println(JsonUtil.writeValue(user));


        Event event = testData.getEventWithComments();
        System.out.println(JsonUtil.writeValue(event));


//                Speech testSpeech = new Speech();
//        Partner partner = testData.getNewPartner();
//        testSpeech.setPartner(partner);
//        testSpeech.setName("test");
//
//        testSpeech.setSpeakers(testData.getMixedVisitorsSet());
//
//
//        testSpeech.setEvent(eventService.get(100013));
//
//        testSpeech.setShortDescription("TestSpeech");
//        testSpeech.setId(100001L);
//
//
//
//
//         System.out.print(testSpeech.toString());
//         System.out.print(JsonUtil.writeValue(testSpeech));






        //System.out.print(JsonUtil.writeValue(testSpeech));


     //   System.out.print(testSpeech.toString());


//        mockMvc.perform(post(REST_URL)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(JsonUtil.writeValue(testSpeech)))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isOk())
//                .andDo(print());
        // helper.getByPartner();
    }

}
