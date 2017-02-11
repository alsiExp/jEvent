package ru.jevent.web.User;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import ru.jevent.LoggedUser;
import ru.jevent.model.User;
import ru.jevent.model.enums.Role;
import ru.jevent.web.WebTest;

import static ru.jevent.Profiles.JPA;
import static ru.jevent.Profiles.POSTGRES;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ru.jevent.service.UserService;
import ru.jevent.web.json.JsonUtil;
import org.junit.Assert;

/**
 * Created by user on 26.01.2017.
 */
@ActiveProfiles({POSTGRES, JPA})
public class ProfileRestControllerTest extends WebTest{

    @Autowired
    private UserService service;

    private static final String REST_URL = "/rest/profile";

    @Test
    public void testGet() throws  Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());

    }

    @Test
    public void testUpdate() throws Exception {
        User user1 = service.get(LoggedUser.id());
        user1.setFullName("Test");
        String FullName1 = user1.getFullName();
        mockMvc.perform(post(REST_URL).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(user1)))
                .andDo(print())
                .andExpect(status().isOk());
        User user2 = service.get(LoggedUser.id());
        Assert.assertTrue(FullName1.equals(user2.getFullName()));

    }

}
