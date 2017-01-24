package ru.jevent.web.User;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import ru.jevent.web.WebTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.jevent.Profiles.JPA;
import static ru.jevent.Profiles.POSTGRES;
import ru.jevent.model.User;

@ActiveProfiles({POSTGRES, JPA})
public class AdminRestControllerTest extends WebTest {

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL+ (100006)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private static final String REST_URL = "/rest/admin/users/";

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + (100006)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testUpdate() throws Exception {
        User user=new User();
        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON_VALUE).content(user.toString()))
                .andDo(print())
                .andExpect(status().isOk());


    }

}