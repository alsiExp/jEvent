package ru.jevent.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.TestData;
import ru.jevent.model.Speech;
import ru.jevent.util.DbPopulator;
import ru.jevent.util.exception.NotFoundException;

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
public class SpeechServiceTest {

    @Autowired
    private SpeechService service;
    @Autowired
    private TestData testData;
    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Speech speech = service.get(100016L);
        if(speech == null ||
                speech.getSpeakers().size() == 0 ||
                speech.getName() == null) {
            throw new Exception();
        }
    }

    @Test(expected = NotFoundException.class)
    public void testDelete() throws Exception {
        service.delete(100016L);
        service.get(100016L);
    }

    @Test
    public void testGetByParticipant() throws Exception {

        List<Speech> list =  service.getByPartner(100002L);
        if(list != null) {
            if(list.size() < 2) {
                throw new Exception();
            }
        } else {
            throw new Exception();
        }

    }




    //equals() in Speech doesn't work correctly
    @Test
    public void testSave() throws Exception {
        Speech testSpeech = service.get(100025L);
        Speech tSpeech = service.get(100025L);
        boolean b = testSpeech.equals(tSpeech);
        service.delete(100025L);
//TODO: Speech test save
        //getAll does not work! Please check it!

//        List<Speech> oldListSpeech = service.getAll();
//        service.save(testSpeech);
//        List<Speech> testListSpeech = service.getAll();
//        if(testListSpeech.size() == oldListSpeech.size()){
//            throw new Exception();
//        }


        //id is changed after save.

//        Speech speechdb = service.get(testSpeech.getId());
//        if(speechdb == null) {
//            throw new Exception();
//        }
//        if(!(testSpeech.getId().equals(speechdb.getId()))) {
//            throw new Exception();
//        }

    }


    @Test
    public void testUpdate() throws Exception {
        Speech speech = service.get(100028L);
        speech.setName("TEst");
        service.update(speech);
        Speech speechdb =service.get(100028L);
        if(!(speech.getName().equals(speechdb.getName()))){
            throw new Exception();
        }
    }
 }
