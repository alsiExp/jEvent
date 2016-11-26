package ru.jevent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jevent.model.*;
import ru.jevent.model.additionalEntity.Rate;
import ru.jevent.model.enums.RateType;
import ru.jevent.model.enums.Role;
import ru.jevent.model.enums.Sex;
import ru.jevent.service.EventService;
import ru.jevent.service.ParticipantService;
import ru.jevent.service.PartnerService;
import ru.jevent.service.UserService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TestData {

    private UserService userService;
    private PartnerService partnerService;
    private ParticipantService participantService;
    private EventService eventService;


    public TestData() {
    }

    @Autowired
    public TestData(UserService userService,
                    PartnerService partnerService, ParticipantService participantService,
                    EventService eventService) {
        this.userService = userService;
        this.partnerService = partnerService;
        this.participantService = participantService;
        this.eventService = eventService;
    }

    public User getNewUser() {
        return new User("Test", "User", Sex.MALE, true, "photo.jpg", "login", "pass");
    }

    public User getExistingUser() {
//        User exUser = new User();
//        exUser.setLogin("ekaterina");
//        exUser.setPassword("user");
//        exUser.getRoles();
//        exUser.setFirstName("Екатерина");
//        exUser.setLastName("Курилова");
//        exUser.setSex(Sex.FEMALE);
//        exUser.setEnabled(true);
//        exUser.setPhotoURL("kurilova.jpg");
//        exUser.setId(100008L);
//        return exUser;
        return userService.get(100008L);
    }

    public User getUser06() {
        User u = new User();
        u.setLogin("alexey");
        u.setPassword("user");
        u.getRoles();
        u.setFirstName("Алексей");
        u.setLastName("Фёдоров");
        u.setSex(Sex.MALE);
        u.setEnabled(true);
        u.setPhotoURL("fedorov.jpg");
        u.setId(100006L);
        u.addRoles(Role.ROLE_ADMIN);
        u.addRoles(Role.ROLE_USER);
        return u;
    }

    public User getUser11() {
        User u = new User();
        u.setLogin("yana");
        u.setPassword("user");
        u.getRoles();
        u.setFirstName("Яна");
        u.setLastName("Пилюгина");
        u.setSex(Sex.FEMALE);
        u.setEnabled(true);
        u.setPhotoURL("pilugina.jpg");
        u.setId(100011L);
        return u;
    }

    public List<User> getExistingUsersList() {

        return userService.getAll();
    }

    public Comment getNewComment() {
        return new Comment("New test comment", this.getExistingUser(), LocalDateTime.now());
    }


    public Comment getExistingComment23() {
        return new Comment(100023L, "Комментарий про Баруха #1", getUser11(), LocalDateTime.of(2016,9,19,7,0,0,0));
    }

    public List<Comment> getMixedCommentsList() {
        return Arrays.asList(getNewComment(), getNewComment());
    }

    public Partner getNewPartner() {
//        return  new Partner("Test partner", "test@email.com", "+7-999-000-00-00", "Test partner description", "testpartner.jpg");
        return new Partner();
    }

    public Partner getExistingPartner() {
        return partnerService.get(100000L);
    }

    public List<Partner> getMixedPartnersList() {
        return Arrays.asList(getNewPartner(), getExistingPartner(), getNewPartner());
    }

    public Participant getNewVisitor() {
        Participant participant = new Participant();
        participant.setFirstName("Test");
        participant.setLastName("Participant");
        participant.setSex(Sex.FEMALE);
        participant.setEnabled(true);

        participant.setPhotoURL("testvisitor.jpg");
        participant.setBirthDay(LocalDateTime.now().minus(39, ChronoUnit.YEARS));
        participant.setRegistered(LocalDateTime.now().minus(20, ChronoUnit.DAYS));
        //participant.setEmail("test@participant.com");
        participant.setPhone("+0-000-000-00-00");
       // participant.setGitHubAccount("testgGithub");
      //  participant.setLinkedInAccount("testLinkedIn");
       // participant.setTwitterAccount("@test");
        participant.setEmployer("Одноклассники");
        participant.setBiography("test biography");
        participant.setDescription("test description");
        return participant;
    }

    public Participant getNewVisitorWithNewComments() {
        Participant participant = getNewVisitor();
        participant.setCommentList(Arrays.asList(getNewComment(), getNewComment()));

        return participant;
    }

    public Participant getExistingVisitor() {
//        Participant exVisitor = new Participant();
//        exVisitor.setBirthDay(LocalDateTime.of(1970, 11, 25, 0, 0, 0, 0));
//        exVisitor.setRegistered(LocalDate.of(2016, 10, 10));
//        exVisitor.setEmail("jbaruch@gmail.com");
//        exVisitor.setPhone("+7-000-000-00-00");
//        exVisitor.setGitHubAccount("jbaruch");
//        exVisitor.setTwitterAccount("jbaruch");
//        exVisitor.setEmployer("JFrog");
//        exVisitor.setBiography("Developer advocate в компании JFrog, и делает в жизни ровно 3 вещи: зависает с разработчиками Bintray и Artifactory, пописывает для них код, и рассказывает о впечатлениях в блогах и на конференциях. И так несколько лет подряд, ни минуты об этом не жалея.");
//        exVisitor.setDescription("Поскольку «религия не позволяет» быть евангелистом, Барух — developer advocate в компании JFrog и делает в жизни ровно 3 вещи: зависает с разработчиками Bintray и Artifactory, пописывает для них код, и рассказывает о впечатлениях в блогах и на конференциях, таких как JavaOne, Devoxx, OSCON, конечно же JPoint и Joker, да и многих других. И так более десяти лет подряд.");
//        exVisitor.setCost(-90000.0);
//        exVisitor.setCommentList(Arrays.asList(getExistingComment23()));
//        exVisitor.setFirstName("Барух");
//        exVisitor.setLastName("Садогурский");
//        exVisitor.setSex(Sex.MALE);
//        exVisitor.setEnabled(true);
//        exVisitor.setPhotoURL("bsadogursky.jpg");
//        exVisitor.setId(100004L);
//        return exVisitor;
        return participantService.get(100004L);
    }

    public List<Participant> getMixedVisitorsList() {
        Participant v1 = participantService.save(getNewVisitor());
        Participant v2 = participantService.get(100005L);
        Participant v3 = getNewVisitor();
        return Arrays.asList(v1, v2, v3);
    }

    public Event getSimpleEvent() {
        Event event = new Event();
        event.setName("Test Event");
        event.setVersion("test");
        event.setAuthor(this.getUser06());
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

    public Event getExistingEvent() {
        return eventService.get(100012L);
    }

    public Event getEventWithTracks() {
        Event event = getEventWithRates();
        return event;
    }

    public Event getEventWithComments() {
        Event event = getEventWithRates();
        event.setCommentList( Arrays.asList(getNewComment(), getNewComment()));
        return event;
    }

    public List<Rate> getRates() {
        Rate r1 = new Rate("Личное присутствие Standard", RateType.PERSONAL_STANDARD, LocalDateTime.of(2016, 4, 1, 0, 0), LocalDateTime.of(2016, 7, 1, 23, 59), 12000);
        Rate r2 = new Rate("Онлайн-Трансляция Standard", RateType.ONLINE_STANDARD, LocalDateTime.of(2016, 4, 1, 0, 0), LocalDateTime.of(2016, 7, 1, 23, 59), 8000);
        return Arrays.asList(r2, r1);
    }

    public Set<Speech> getProbableSpeakers(Event e) {
        Speech ps1 = new Speech();
        ps1.setEvent(e);
/*        ps1.setSendDate(LocalDateTime.now().minusWeeks(1));
        ps1.setSpeaker(getExistingVisitor());
        ps1.setFullDescription("test description");
        ps1.setSpeechName("Test speech name");
        ps1.setWishPrice(5000.0);*/

        Speech ps2 = new Speech();
        ps2.setEvent(e);
/*        ps2.setSendDate(LocalDateTime.now().minusWeeks(1));
        ps2.setSpeaker(getExistingVisitor());
        ps2.setFullDescription("test description");
        ps2.setSpeechName("Test speech name");
        ps2.setWishPrice(5000.0);*/

        return new HashSet<>(Arrays.asList(ps1, ps2));
    }

    public Set<Visitor> getConfirmedVisitors(Event e) {
        Visitor cv = new Visitor();
        cv.setEvent(e);
        cv.setBuyDate(LocalDateTime.now().minusWeeks(5));
        cv.setPayComment("no comments");
        cv.setParticipant(getExistingVisitor());
        Rate r = getRates().get(0);
        r.setId(100037L);
        cv.setRate(r);

        return new HashSet<>(Arrays.asList(cv));
    }
}
