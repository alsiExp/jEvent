package ru.jevent.service;


import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.service.github.GithubService;

import static ru.jevent.Profiles.JPA;
import static ru.jevent.Profiles.POSTGRES;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/service.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({POSTGRES, JPA})
public class GithubServiceTest {

    @Autowired
    GithubService githubService;

    @Test
    public void testGetLogin() throws Exception{
        String users[] = {"n0str", "glasnt", "nitsanw", "17mon"};

        for(String st: users) {
            JSONObject userInformation = githubService.getUserInformation(st);
            System.out.println("login: " + userInformation.getString("login"));
            System.out.println("public_repos:" + userInformation.getInt("public_repos"));
            System.out.println("followers:" + userInformation.getInt("followers"));
        }
    }
}

