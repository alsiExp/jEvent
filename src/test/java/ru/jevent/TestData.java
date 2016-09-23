package ru.jevent;

import ru.jevent.model.*;
import ru.jevent.model.Enums.RateType;
import ru.jevent.model.Enums.Sex;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestData {

    public User getNewUser() {
        User user = new User("Test", "User", Sex.MALE, true, "photo.jpg", "login", "pass");
        return user;
    }

    public Comment getNewComment() {
        Comment comment = new Comment();
        comment.setContent("Insert new comment");
        comment.setDate(LocalDateTime.now());
        return comment;
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
        List<Comment> list = new ArrayList<>();

        return visitor;
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

        Rate r1 = new Rate("Личное присутствие Standart", RateType.PERSOONAL_STANDART, LocalDateTime.of(2016, 4, 1, 0, 0), LocalDateTime.of(2016, 7, 1, 23, 59), 12000);
        Rate r2 = new Rate("Онлайн-Трансляция Standart", RateType.ONLINE_STANDART, LocalDateTime.of(2016, 4, 1, 0, 0), LocalDateTime.of(2016, 7, 1, 23, 59), 8000);

        event.setRates(Arrays.asList(r1, r2));

        return event;
    }

    public List<OfferDetails> getOfferDetails() {
        OfferDetails details1 = new OfferDetails(LocalDateTime.now().minusDays(12), "First speech", "Test description", 25000.00);
        OfferDetails details2 = new OfferDetails(LocalDateTime.now().minusDays(10), "Second speech", "Test description", 20000.00);
        OfferDetails details3 = new OfferDetails(LocalDateTime.now().minusDays(10), "Third speech", "Test description", 50000.00);
        return Arrays.asList(details1, details2, details3);
    }

}
