package ru.jevent.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.service.github.GithubService;
import ru.jevent.service.twitter.TwitterService;

import static ru.jevent.Profiles.JPA;
import static ru.jevent.Profiles.POSTGRES;

    @ContextConfiguration({
            "classpath:spring/spring-app.xml",
            "classpath:spring/spring-db.xml",
            "classpath:spring/service.xml"
    })
    @RunWith(SpringJUnit4ClassRunner.class)
    @ActiveProfiles({POSTGRES, JPA})
    public class TwitterServiceTest {

        @Autowired
        TwitterService twitterService;

        @Test
        public void testGetFollowers() throws Exception {
            String users[] = {"comaqa", "ilarihenrik", "LaikaTestDog", "shipilev"};

            for(String st: users) {
            int twitterFolowers = twitterService.getFollowers(st);
            System.out.println("login: " + st);
            System.out.println("followers: " + twitterFolowers);
        }
        }
    }