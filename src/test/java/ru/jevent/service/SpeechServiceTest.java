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
}
