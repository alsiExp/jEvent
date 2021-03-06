package ru.jevent.web.Speech;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.jevent.TestData;
import ru.jevent.model.Event;
import ru.jevent.model.Partner;
import ru.jevent.model.Speech;
import ru.jevent.model.User;
import ru.jevent.service.EventService;
import ru.jevent.service.SpeechService;
import ru.jevent.web.WebTest;
import ru.jevent.web.json.JsonUtil;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.jevent.Profiles.JPA;
import static ru.jevent.Profiles.POSTGRES;


@ContextConfiguration({
        "classpath:spring/test-app.xml",
        "classpath:spring/spring-db.xml",
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

    private static final String REST_URL = "/rest/speeches/";


// Возникает ошибка в результате преобразования объекта speech в JSON.
// При этом user и event преобразуется.
    @Test
    public void createTest() throws Exception {
    //User user = testData.getNewUser();
//        System.out.println(user.toString());
//        System.out.println(JsonUtil.writeValue(user));
       // Event event = testData.getEventWithComments();
//        System.out.println(JsonUtil.writeValue(event));

        Speech testSpeech = speechService.get(100016L);
        //Speech testSpeech = new Speech();
        Partner partner = testData.getNewPartner();
        testSpeech.setPartner(partner);
        testSpeech.setName("test");

        testSpeech.setSpeakers(testData.getMixedVisitorsSet());


        testSpeech.setEvent(eventService.get(100013));

        testSpeech.setShortDescription("TestSpeech");
        testSpeech.setId(100001L);
         System.out.print(testSpeech.toString());
       //  System.out.print(JsonUtil.writeValue(testSpeech));

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

    @Test
    public void updateTest() throws Exception {}

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete(REST_URL + (100016))
                .with(httpBasic("yanah", "user")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getTest() throws Exception {
        mockMvc.perform(get(REST_URL + (100016)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }
}
