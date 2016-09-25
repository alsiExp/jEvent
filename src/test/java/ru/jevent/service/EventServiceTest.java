package ru.jevent.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.TestData;
import ru.jevent.model.Event;
import ru.jevent.util.DbPopulator;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/service.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class EventServiceTest {

    @Autowired
    EventService eventService;
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
        Event joker = eventService.get(100012L);
        if (joker.getId() == null ||
                joker.getName() == null ||
                joker.getRates().isEmpty()) throw new Exception();
        if(!joker.getId().equals(100012L)) throw new Exception();
    }

    @Test
    public void testSimpleSave() throws Exception {
        Event testEvent = testData.getSimpleEvent();
        eventService.save(testEvent);

        Event savedEvent = eventService.get(testEvent.getId());
        if(!savedEvent.equals(testEvent))
            throw new Exception();
    }

    @Test
    public void testEventWithRatesSave() throws Exception {
        Event testEvent = testData.getEventWithRates();
        eventService.save(testEvent);

        Event savedEvent = eventService.get(testEvent.getId());
        if(!savedEvent.equals(testEvent))
            throw new Exception();
    }

    @Test
    public void testEventWithProbableSpeakersSave() throws Exception {
        Event testEvent = testData.getEventWithRPs();
        eventService.save(testEvent);

        Event savedEvent = eventService.get(testEvent.getId());
        if(!savedEvent.equals(testEvent))
            throw new Exception();
    }

    @Test
    public void testEventWithRatesAndConfirmedVisitorsSave() throws Exception {
        Event testEvent = testData.getEventWithRPsCv();
        eventService.save(testEvent);

        Event savedEvent = eventService.get(testEvent.getId());
        if(!savedEvent.equals(testEvent))
            throw new Exception();
    }
}
