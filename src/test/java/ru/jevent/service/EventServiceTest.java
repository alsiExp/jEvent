package ru.jevent.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.TestData;
import ru.jevent.model.Event;
import ru.jevent.util.DbPopulator;
import ru.jevent.util.exception.NotFoundException;

import java.util.List;

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

    @Test
    public void testCompletedEventSave() throws Exception {
        Event testEvent = testData.getCompletedEvent();
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

    @Test(expected = NotFoundException.class)
    public void testUpdateWithException() throws Exception {
        Event e = testData.getExistingEvent();
        e.setId(9L);
        eventService.update(e);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveWrongData() throws Exception {
        boolean check = false;
        Event e = testData.getSimpleEvent();
        e.setName(null);
        try {
            eventService.save(e);
        } catch (DataIntegrityViolationException ex) {
            e.setName("name");
            check = true;
        }
        if(!check) {
            throw new Exception();
        } else {
            check = false;
        }
        e.setAuthor(null);
        try {
            eventService.update(e);
        }
        catch (DataIntegrityViolationException ex) {
            e.setAuthor(testData.getExistingUser());
            check = true;
        }

        if(!check) {
            throw new Exception();
        }

        e.setTagName("");
        eventService.update(e);

    }
}
