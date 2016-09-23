package ru.jevent.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.TestData;
import ru.jevent.model.Event;
import ru.jevent.model.OfferDetails;
import ru.jevent.model.Visitor;
import ru.jevent.util.DbPopulator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class EventServiceTest {

    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;
    @Autowired
    VisitorService visitorService;
    @Autowired
    private DbPopulator dbPopulator;

    private TestData testData = new TestData();

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Event joker = eventService.get(100012);
        Event jpoint = eventService.get(100013);
        if(joker.equals(jpoint))
            throw new Exception();
    }

    @Test
    public void testSimpleSave() throws Exception {
        Event testEvent = testData.getSimpleEvent();
        testEvent.setAuthor(userService.get(100006L));
        eventService.save(testEvent);

        Event savedEvent = eventService.get(testEvent.getId());
        if(!savedEvent.equals(testEvent))
            throw new Exception();
    }

    @Test
    public void testEventWithRatesSave() throws Exception {
        Event testEvent = testData.getEventWithRates();
        testEvent.setAuthor(userService.get(100006L));
        eventService.save(testEvent);

        Event savedEvent = eventService.get(testEvent.getId());
        if(!savedEvent.equals(testEvent))
            throw new Exception();
    }

    @Test
    public void testEventWithOfferDetails() throws Exception {
        Event testEvent = testData.getSimpleEvent();
        testEvent.setAuthor(userService.get(100008L));
        Visitor v1 = visitorService.save(testData.getNewVisitor());
        Visitor v2 = visitorService.get(100005L);
        Visitor v3 = testData.getNewVisitor();
        List<OfferDetails> offers = testData.getOfferDetails();
        Map<Visitor, OfferDetails> probableSpeakers = new HashMap<>();
        probableSpeakers.put(v1, offers.get(0));
        probableSpeakers.put(v2, offers.get(1));
        probableSpeakers.put(v3, offers.get(2));
        testEvent.setProbableSpeakers(probableSpeakers);

        eventService.save(testEvent);

        Event savedEvent = eventService.get(testEvent.getId());
        if(!savedEvent.equals(testEvent))
            throw new Exception();
    }
}
