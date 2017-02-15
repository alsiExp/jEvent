package ru.jevent.web.User;


import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.jevent.model.User;
import ru.jevent.service.UserService;
import ru.jevent.web.WebTest;
import ru.jevent.web.json.JsonUtil;

import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.jevent.Profiles.JPA;
import static ru.jevent.Profiles.POSTGRES;


@ActiveProfiles({POSTGRES, JPA})
public class AdminAjaxControllerTest extends WebTest {

    @Autowired
    private UserService service;
    private static final String REST_URL = "/ajax/admin/users";

    @Test
    public void getTest() throws Exception {

        MvcResult res_test = mockMvc.perform(get(REST_URL+ '/' + 100006))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String userString = res_test.getResponse().getContentAsString();
        User userResult = JsonUtil.readValue(userString, User.class);
        User userTest = service.get(100006);
        String userLogin = userTest.getLogin();
        Assert.assertTrue(userLogin.equals(userResult.getLogin()));
    }

    @Test
    public void getAllTest() throws Exception {

        mockMvc.perform(get(REST_URL))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete(REST_URL + '/' + 100006))
                .andExpect(status().isOk())
                .andDo(print());

        List<User> listUsers = service.getAll();
        for (User user:listUsers) {
            Assert.assertFalse(user.getId()==100006);
        }
    }

    @Test
    public void updateTest() throws Exception {
        User user = service.get(100006);
        user.setFullName("Test");
        mockMvc.perform(post(REST_URL)
                .param("userId", user.getId().toString())
                .param("fullName", user.getFullName())
                .param("login", user.getLogin())
                .param("password", user.getPassword())
                .param ("enabled", String.valueOf(user.isEnabled())))
                .andExpect(status().isOk())
                .andDo(print());
        User user1 = service.get(100006);
        Assert.assertTrue(user.getFullName().equals(user1.getFullName()));
    }
}
