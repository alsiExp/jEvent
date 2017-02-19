package ru.jevent.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.TestData;
import ru.jevent.model.Event;
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
public class EventServiceTest {

    @Autowired
    private EventService eventService;
    @Autowired
    private TestData testData;
    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
        eventService.dropCache();
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
    public void testEventWithTracksSave() throws Exception {
        Event testEvent = testData.getEventWithTracks();
        eventService.save(testEvent);

        Event savedEvent = eventService.get(testEvent.getId());
        if(!savedEvent.equals(testEvent))
            throw new Exception();
    }

    // For new Event comments are new too
    @Test
    public void testEventWithCommentsSave() throws Exception {
        Event testEvent = testData.getEventWithComments();
        eventService.save(testEvent);

        Event savedEvent = eventService.get(testEvent.getId());
        if(!savedEvent.equals(testEvent))
            throw new Exception();
    }

    @Test
    public void testUpdate() throws Exception {
        Event event = eventService.get(100012L);
        event.setName("Joker 2016 - Test name update");
        eventService.update(event);

        Event savedEvent = eventService.get(event.getId());
        if(!savedEvent.equals(event))
            throw new Exception();
    }

    @Test
    public void testDeleteAndGetAll() throws Exception {
        eventService.delete(100012L);

        List<Event> list = eventService.getAll();
        if(list.isEmpty()) throw new Exception();
        for(Event e : list) {
            if(e.getId().equals(100012L))
                throw new Exception();
        }
    }


}
