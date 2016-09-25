package ru.jevent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jevent.model.*;
import ru.jevent.model.Enums.RateType;
import ru.jevent.model.Enums.Sex;
import ru.jevent.service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        return Arrays.asList(getNewComment(), getExistingComment(), getNewComment());
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

    public Event getSimpleEvent() {
        Event event = new Event();
        event.setName("Test Event");
        event.setTagName("test");
        event.setAddress("test adress");
        event.setDescription("Test description");
        event.setLogoURL("testevent.jpg");
        return event;
    }

    public Event getEventWithRates() {
        Event event = getSimpleEvent();
        event.setRates(getRates());

        return event;
    }

    public List<Rate> getRates() {
        Rate r1 = new Rate("Личное присутствие Standart", RateType.PERSOONAL_STANDART, LocalDateTime.of(2016, 4, 1, 0, 0), LocalDateTime.of(2016, 7, 1, 23, 59), 12000);
        Rate r2 = new Rate("Онлайн-Трансляция Standart", RateType.ONLINE_STANDART, LocalDateTime.of(2016, 4, 1, 0, 0), LocalDateTime.of(2016, 7, 1, 23, 59), 8000);
        return Arrays.asList(r1, r2);
    }

    public List<OfferDetails> getOfferDetails() {
        OfferDetails details1 = new OfferDetails(LocalDateTime.now().minusDays(12), "First speech", "Test description", 25000.00);
        OfferDetails details2 = new OfferDetails(LocalDateTime.now().minusDays(10), "Second speech", "Test description", 20000.00);
        OfferDetails details3 = new OfferDetails(LocalDateTime.now().minusDays(10), "Third speech", "Test description", 50000.00);
        return Arrays.asList(details1, details2, details3);
    }

    public List<PayDetails> getPayDetails(Event event) {
        List<Rate> rates = event.getRates();
        PayDetails details1 = new PayDetails(LocalDateTime.now().minusWeeks(3), rates.get(0));
        PayDetails details2 = new PayDetails(LocalDateTime.now().minusWeeks(2), rates.get(1));
        PayDetails details3 = new PayDetails(LocalDateTime.now().minusWeeks(1), rates.get(0));
        return Arrays.asList(details1, details2, details3);
    }

    public List<Track> getTrackList(List<Visitor> visitors) {
        List<Track> trackList = new ArrayList<>();


        return trackList;
    }



}
