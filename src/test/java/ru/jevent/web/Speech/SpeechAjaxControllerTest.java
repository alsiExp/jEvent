package ru.jevent.web.Speech;


import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import ru.jevent.model.Speech;
import ru.jevent.service.SpeechService;
import ru.jevent.web.WebTest;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.jevent.Profiles.JPA;
import static ru.jevent.Profiles.POSTGRES;

@ActiveProfiles({POSTGRES, JPA})

public class SpeechAjaxControllerTest extends WebTest{

    public static final String REST_URL = "/ajax/speeches/";

    @Autowired
    SpeechService speechService;

    @Test
    public void getTest() throws Exception {
        mockMvc.perform(get(REST_URL + (100016)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete(REST_URL + (100039)))
                .andExpect(status().isOk())
                .andDo(print());

        List<Speech> SpeechPartner = speechService.getByPartner(100002);
        for(Speech speechTest : SpeechPartner)
            Assert.assertFalse(speechTest.getId() == 100039);
    }
}
