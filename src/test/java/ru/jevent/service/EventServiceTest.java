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
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class EventServiceTest {

    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;
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
}
