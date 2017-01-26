package ru.jevent.web.User;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import ru.jevent.model.enums.Role;
import ru.jevent.web.WebTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.jevent.Profiles.JPA;
import static ru.jevent.Profiles.POSTGRES;
import ru.jevent.model.User;
import ru.jevent.web.json.JsonUtil;


@ActiveProfiles({POSTGRES, JPA})
public class AdminRestControllerTest extends WebTest {

    private static final String REST_URL = "/rest/admin/users/";

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL+ (100006)))
                .andExpect(status().isOk())
                .andDo(print());
    }


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
        User exUser = new User();
        exUser.setLogin("ekaterina");
        exUser.setPassword("user");
        exUser.getRoles();
        exUser.addRoles(Role.ROLE_USER);
        exUser.addRoles(Role.ROLE_ADMIN);
        exUser.setFullName("Екатерина Курилова");
        exUser.setEnabled(true);
        exUser.setPhotoURL("kurilova.jpg");
        exUser.setId(100008L);
        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(exUser)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void testCreate() throws Exception {
        User exUser = new User();
        exUser.setLogin("testuser");
        exUser.setPassword("user");
        exUser.getRoles();
        exUser.addRoles(Role.ROLE_USER);
        exUser.addRoles(Role.ROLE_ADMIN);
        exUser.setFullName("Test Test");
        exUser.setEnabled(true);
        exUser.setPhotoURL("test.jpg");
        exUser.setId(100066L);
        mockMvc.perform(post(REST_URL).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(exUser)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

}