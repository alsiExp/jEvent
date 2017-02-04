package ru.jevent.web;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.jevent.Profiles.JPA;
import static ru.jevent.Profiles.POSTGRES;

@ActiveProfiles({POSTGRES, JPA})
public class RootControllerTest extends WebTest{

    @Test
    public void userListTest() throws Exception {
//        String path = "/users";
//        mockMvc.perform(get(path))
//               .andDo(print());
    }

    @Test
    public void participantListTest() throws Exception {
        String path = "/participants";
        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(view().name("participantList"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/participantList.jsp"));

    }

    @Test
    public void speakerTest() throws Exception {
        String path = "/speaker/100019";
        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(view().name("speaker"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/speaker.jsp"))
                .andExpect(model().attribute("speakerId", "100019"))
                .andExpect(status().isOk());
    }

    @Test
    public void eventTest() throws Exception {
        String path = "/event/100012";
        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(view().name("event"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/event.jsp"))
                .andExpect(model().attribute("eventId", "100012"))
                .andExpect(status().isOk());
    }

    @Test
    public void speechTest() throws Exception {
        String path = "/speech/100039";
        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(view().name("speech"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/speech.jsp"))
                .andExpect(model().attribute("speechId", "100039"))
                .andExpect(status().isOk());
    }

    @Test
    public void eventListTest() throws Exception {
        String path = "/events";
        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(view().name("eventList"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/eventList.jsp"));

    }

    @Test
    public void profileTest() throws Exception {
        String path = "/profile";
        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(view().name("profile"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/profile.jsp"));
    }

}
