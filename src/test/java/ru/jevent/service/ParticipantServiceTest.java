package ru.jevent.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.TestData;
import ru.jevent.model.Participant;
import ru.jevent.util.DbPopulator;

import java.util.List;

import static ru.jevent.Profiles.JPA;
import static ru.jevent.Profiles.POSTGRES;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/service.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({POSTGRES, JPA})
public class ParticipantServiceTest {

    @Autowired
    private ParticipantService service;
    @Autowired
    private TestData testData;
    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
        service.dropCache();
    }

    @Test
    public void testGet() throws Exception {
        Participant part = service.get(100004L);
        Participant exPatr = testData.getExistingParticipant();
        if (!part.equals(exPatr)) {
            throw new Exception();
        }
    }

    @Test
    public void testGetByEmail() throws Exception {
        Participant v = service.getByEmail("jbaruch@gmail.com");
        Participant exPatr = testData.getExistingParticipant();
        if (!v.equals(exPatr)) {
            throw new Exception();
        }
    }

    @Test
    public void testSimpleSave() throws Exception {
        Participant participant = testData.getNewParticipant();
        service.save(participant);
        Participant savedParticipant = service.get(participant.getId());
        if (!savedParticipant.equals(participant)) {
            throw new Exception();
        }
    }


    @Test
    public void testSaveExistingWithNewTwitterGithub() throws Exception {
        Participant participant = testData.getNewParticipantWithNewTwitterGithub();

        service.save(participant);
        Participant savedParticipant = service.get(participant.getId());
        if (!savedParticipant.equals(participant)) {
            throw new Exception();
        }
    }

    @Test
    public void testUpdate() throws Exception {
        Participant participant = service.get(100004L);
        participant.setBiography("Actual test bio");
        service.update(participant);
        Participant savedParticipant = service.get(participant.getId());
        if (!savedParticipant.equals(participant)) {
            throw new Exception();
        }
    }

    @Test
    public void testDeleteAndGetAll() throws Exception {
        service.delete(100003L);
        List<Participant> list = service.getAll();
        if (list.isEmpty()) throw new Exception();
        for (Participant v : list) {
            if (v.getId().equals(100003L))
                throw new Exception();
        }
    }


    @Test
    public void testGetByTag() throws Exception {
        List<Participant> list = service.getByTag(100022L);
        if(list.size() < 1) {
            throw new Exception();
        }
    }
}
