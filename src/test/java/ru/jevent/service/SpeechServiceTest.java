package ru.jevent.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.TestData;
import ru.jevent.model.Partner;
import ru.jevent.model.Speech;
import ru.jevent.model.additionalEntity.SpeechTag;
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
    private PartnerService partnerService;
    @Autowired
    private SpeechTagService tagService;
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
    public void testGetByPartner() throws Exception {

        List<Speech> list =  service.getByPartner(100002L);
        if(list != null) {
            if(list.size() < 2) {
                throw new Exception();
            }
        } else {
            throw new Exception();
        }
    }

    @Test
    public void testSave() throws Exception {
        Speech testSpeech = service.get(100025L);
        service.delete(100025L);
        Partner idPartner = partnerService.get(100002);
        testSpeech.setPartner(idPartner);
        List<Speech> oldListSpeech = service.getByPartner(100002);
        service.save(testSpeech);
        List<Speech> testListSpeech = service.getByPartner(100002);
        if(testListSpeech.size() == oldListSpeech.size()){
            throw new Exception();
        }
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

    @Test
    public void addTag() throws Exception {
        Speech speech = service.get(100028L);
        SpeechTag tag = new SpeechTag();
        tag.setTag("JCache");
        tagService.save(tag);
        speech.addTag(tag);
        service.update(speech);

        Speech speechWithTag = service.get(100028L);
        if(speechWithTag.getTags().isEmpty()) {
            throw new Exception();
        }
        for(SpeechTag t : speechWithTag.getTags()) {
            if(t.getTag() == null) {
                throw new Exception();
            }
        }

        speech = service.get(100021L);
        speech.addTag(tag);
        service.update(speech);
        speechWithTag = service.get(100021L);
        if(speechWithTag.getTags().isEmpty()) {
            throw new Exception();
        }

        for(SpeechTag t : speechWithTag.getTags()) {
            if(t.getTag() == null) {
                throw new Exception();
            }
        }
    }

    @Test
    public void getPossibleTags() throws Exception {
        List<SpeechTag> tags = service.getPossibleTags(100021L);
        if(tags.size() == 0) {
            throw new Exception();
        }
    }
}
