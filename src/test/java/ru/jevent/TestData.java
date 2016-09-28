package ru.jevent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jevent.model.*;
import ru.jevent.model.Enums.RateType;
import ru.jevent.model.Enums.Sex;
import ru.jevent.model.Enums.SlotType;
import ru.jevent.service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class TestData {

    private UserService userService;
    private CommentService commentService;
    private PartnerService partnerService;
    private VisitorService visitorService;
    private EventService eventService;
    private TaskService taskService;

    public TestData() {
    }

    @Autowired
    public TestData(UserService userService, CommentService commentService,
                    PartnerService partnerService, VisitorService visitorService,
                    EventService eventService, TaskService taskService) {
        this.userService = userService;
        this.commentService = commentService;
        this.partnerService = partnerService;
        this.visitorService = visitorService;
        this.eventService = eventService;
        this.taskService = taskService;
    }

    public User getNewUser() {
        return new User("Test", "User", Sex.MALE, true, "photo.jpg", "login", "pass");
    }

    public User getExistingUser() {
        return userService.get(100008L);
    }

    public List<User> getExistingUsersList() {
        return userService.getAll();
    }

    public Comment getNewComment() {
        return new Comment("New test comment", this.getExistingUser(), LocalDateTime.now());
    }

    public Comment getExistingComment() {
        return commentService.get(100014L);
    }

    public List<Comment> getMixedCommentsList() {
        return Arrays.asList(getExistingComment(), getNewComment(), getNewComment());
    }

    public Partner getNewPartner() {
        return  new Partner("Test partner", "test@email.com", "+7-999-000-00-00", "Test partner description", "testpartner.jpg");
    }

    public Partner getExistingPartner() {
        return partnerService.get(100000L);
    }

    public List<Partner> getMixedPartnersList() {
        return Arrays.asList(getNewPartner(), getExistingPartner(), getNewPartner());
    }

    public Visitor getNewVisitor() {
        Visitor visitor = new Visitor();
        visitor.setFirstName("Test");
        visitor.setLastName("Visitor");
        visitor.setSex(Sex.FEMALE);
        visitor.setEnabled(true);

        visitor.setPhotoURL("testvisitor.jpg");
        visitor.setBirthDay(LocalDateTime.now().minus(39, ChronoUnit.YEARS));
        visitor.setRegistered(LocalDate.now().minus(20, ChronoUnit.DAYS));
        visitor.setEmail("test@visitor.com");
        visitor.setPhone("+0-000-000-00-00");
        visitor.setGitHubAccount("testgGithub");
        visitor.setLinkedInAccount("testLinkedIn");
        visitor.setTwitterAccount("@test");
        visitor.setEmployer("Одноклассники");
        visitor.setBiography("test biography");
        visitor.setDescription("test description");
        visitor.setCost(5000);
        visitor.setCommentList(getMixedCommentsList());
        return visitor;
    }

    public Visitor getExistingVisitor() {
        return visitorService.get(100004L);
    }

    public List<Visitor> getMixedVisitorsList() {
        Visitor v1 = visitorService.save(getNewVisitor());
        Visitor v2 = visitorService.get(100005L);
        Visitor v3 = getNewVisitor();
        return Arrays.asList(v1, v2, v3);
    }

    public Event getSimpleEvent() {
        Event event = new Event();
        event.setName("Test Event");
        event.setTagName("test");
        event.setAuthor(userService.get(100006L));
        event.setAddress("test adress");
        event.setDescription("Test description");
        event.setLogoURL("testevent.jpg");
        event.setStartDate(LocalDateTime.now().plusDays(25));
        return event;
    }

    public Event getEventWithRates() {
        Event event = getSimpleEvent();
        event.setRates(getRates());
        return event;
    }

    public Event getEventWithRPs() {
        Event event = getEventWithRates();

        Map<Visitor, OfferDetails> probableSpeakers = new HashMap<>();
        probableSpeakers.put(getMixedVisitorsList().get(0), getOfferDetails().get(0));
        probableSpeakers.put(getMixedVisitorsList().get(1), getOfferDetails().get(1));
        probableSpeakers.put(getMixedVisitorsList().get(2), getOfferDetails().get(2));

        event.setProbableSpeakers(probableSpeakers);

        return event;
    }

    public Event getEventWithRPsCv() {
        Event event = getEventWithRPs();

        Map<Visitor, PayDetails> confirmedVisitors = new HashMap<>();
        confirmedVisitors.put(getMixedVisitorsList().get(0), getPayDetails(event.getRates()).get(0));
        confirmedVisitors.put(getMixedVisitorsList().get(1), getPayDetails(event.getRates()).get(1));
        confirmedVisitors.put(getMixedVisitorsList().get(2), getPayDetails(event.getRates()).get(2));

        event.setConfirmedVisitors(confirmedVisitors);

        return event;
    }

    public Event getExistingEvent() {
        return eventService.get(100012L);
    }

    public Event getCompletedEvent() {
        Event event = getEventWithRPsCv();
        event.setCommentList(getMixedCommentsList());
        event.setTracks(getTrackList());
        return event;
    }

    public List<Rate> getRates() {
        Rate r1 = new Rate("Личное присутствие Standard", RateType.PERSONAL_STANDARD, LocalDateTime.of(2016, 4, 1, 0, 0), LocalDateTime.of(2016, 7, 1, 23, 59), 12000);
        Rate r2 = new Rate("Онлайн-Трансляция Standard", RateType.ONLINE_STANDARD, LocalDateTime.of(2016, 4, 1, 0, 0), LocalDateTime.of(2016, 7, 1, 23, 59), 8000);
        return Arrays.asList(r2, r1);
    }

    public List<OfferDetails> getOfferDetails() {
        OfferDetails details1 = new OfferDetails(LocalDateTime.now().minusDays(12), "First speech", "Test description", 25000.00);
        OfferDetails details2 = new OfferDetails(LocalDateTime.now().minusDays(10), "Second speech", "Test description", 20000.00);
        OfferDetails details3 = new OfferDetails(LocalDateTime.now().minusDays(10), "Third speech", "Test description", 50000.00);
        return Arrays.asList(details1, details2, details3);
    }

    public List<PayDetails> getPayDetails(List<Rate> rates) {
        PayDetails details1 = new PayDetails(LocalDateTime.now().minusWeeks(3), rates.get(0));
        PayDetails details2 = new PayDetails(LocalDateTime.now().minusWeeks(2), rates.get(1));
        PayDetails details3 = new PayDetails(LocalDateTime.now().minusWeeks(1), rates.get(0));
        return Arrays.asList(details1, details2, details3);
    }

    public Set<Track> getTrackList() {
        Track t1 = new Track();
        t1.setName("First track");
        t1.setDescription("First track description");

        Track t2 = new Track();
        t2.setName("Second track");

        Track t3 = new Track();
        t3.setName("Third track");

        Slot s1 = new Slot();
        s1.setSlotType(SlotType.CHECK_IN);
        s1.setName("Check in");
        s1.setSlotDescription("Just check in");
        s1.setStart(LocalDateTime.now().plusDays(30));


        Slot s2 = new Slot();
        s2.setSlotType(SlotType.LECTURE);
        s2.setName("Some lecture with visitor");
        s2.setSlotDescription("Lecture");
        s2.setStart(LocalDateTime.now().plusDays(30).plusHours(1));
        s2.setApprovedSpeaker(getExistingVisitor());
        s2.setPrice(70000);

        Slot s3 = new Slot();
        s3.setSlotType(SlotType.BREAK);
        s3.setName("just break");
        s3.setStart(LocalDateTime.now().plusDays(30).plusHours(2));

        Slot s4 = new Slot();
        s4.setSlotType(SlotType.LECTURE);
        s4.setName("Some lecture with new visitor");
        s4.setSlotDescription("Lecture");
        s4.setStart(LocalDateTime.now().plusDays(30).plusHours(3));
        s4.setApprovedSpeaker(getNewVisitor());
        s4.setPrice(50000);

        Slot s5 = new Slot();
        s5.setSlotType(SlotType.CHECK_IN);
        s5.setName("Check in");
        s5.setSlotDescription("Just check in");
        s5.setStart(LocalDateTime.now().plusDays(30));

        Slot s6 = new Slot();
        s6.setSlotType(SlotType.LECTURE);
        s6.setName("Some lecture with new visitor");
        s6.setSlotDescription("Lecture");
        s6.setStart(LocalDateTime.now().plusDays(30).plusHours(3));
        s6.setApprovedSpeaker(getNewVisitor());
        s6.setPrice(50000);

        t1.setSlotOrder(Arrays.asList(s1, s2, s3, s4));
        t2.setSlotOrder(Arrays.asList(s5, s6));
        return new HashSet<>(Arrays.asList(t1, t2, t3));
    }

    public Task getSimpleTask() {
        Task task = new Task();
        task.setName("Test task");
        task.setAuthor(this.getExistingUser());
        task.setTarget(new HashSet<>(getExistingUsersList()));

        task.setStart(LocalDateTime.now().minusDays(7));
        task.setDeadline(LocalDateTime.now().plusWeeks(4));

        task.setDescription("Test task description");
        task.setActive(true);

        return task;
    }
}
