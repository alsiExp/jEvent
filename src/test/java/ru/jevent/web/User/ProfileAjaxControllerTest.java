package ru.jevent.web.User;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.jevent.LoggedUser;
import ru.jevent.model.User;
import ru.jevent.web.WebTest;
import ru.jevent.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ru.jevent.service.UserService;
import static ru.jevent.Profiles.JPA;
import static ru.jevent.Profiles.POSTGRES;

@ActiveProfiles({POSTGRES, JPA})
public class ProfileAjaxControllerTest extends WebTest{

    @Autowired
    private UserService service;
    private static final String REST_URL = "/ajax/profile";

    @Test
    public void testUpdate() throws Exception{
        User user1 = service.get(100006);
        user1.setFullName("Test");
        String FullName1 = user1.getFullName();
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .param("fullName", user1.getFullName())
                .param("login","yana")
                .param("password", "user")
                .param("jiraLogin","")
                .param("jiraPassword",""))
                .andDo(print())
                .andExpect(status().isOk());
        User user2 = service.get(100006);
        String FullName2 = user1.getFullName();
        Assert.assertTrue(FullName1.equals(user2.getFullName()));

    }

    @Test
    public void testGet() throws Exception{

        mockMvc.perform(get(REST_URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk());

    }

}
