package ru.jevent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jevent.model.*;
import ru.jevent.model.additionalEntity.Email;
import ru.jevent.model.additionalEntity.GitHub;
import ru.jevent.model.additionalEntity.Rate;
import ru.jevent.model.additionalEntity.Twitter;
import ru.jevent.model.enums.RateType;
import ru.jevent.model.enums.Role;
import ru.jevent.model.enums.Sex;
import ru.jevent.service.EventService;
import ru.jevent.service.ParticipantService;
import ru.jevent.service.PartnerService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TestData {

    private PartnerService partnerService;
    private ParticipantService participantService;
    private EventService eventService;


    public TestData() {
    }

    @Autowired
    public TestData(PartnerService partnerService,
                    ParticipantService participantService,
                    EventService eventService) {
        this.partnerService = partnerService;
        this.participantService = participantService;
        this.eventService = eventService;
    }

    //comments
    public Comment getNewComment() {
        return new Comment("New test comment", this.getExistingUser(), LocalDateTime.now());
    }


    public Comment getExistingComment23() {
        return new Comment(100023L, "Комментарий про Баруха #1", getUser11(), LocalDateTime.of(2016,9,19,7,0,0,0));
    }

    public List<Comment> getMixedCommentsList() {
        return Arrays.asList(getExistingComment23(), getNewComment());
    }

    //Users
    public User getNewUser() {
        return new User("Test", "User", Sex.MALE, true, "photo.jpg", "login", "pass");
    }

    public User getExistingUser() {
        User exUser = new User();
        exUser.setLogin("ekaterina");
        exUser.setPassword("user");
        exUser.getRoles();
        exUser.addRoles(Role.ROLE_USER);
        exUser.addRoles(Role.ROLE_ADMIN);
        exUser.setFirstName("Екатерина");
        exUser.setLastName("Курилова");
        exUser.setSex(Sex.FEMALE);
        exUser.setEnabled(true);
        exUser.setPhotoURL("kurilova.jpg");
        exUser.setId(100008L);
        return exUser;
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

    //Participants
    public Participant getExistingParticipant() {
        Participant part = new Participant();
        part.setBirthDay(LocalDateTime.of(1970, 11, 25, 0, 0, 0, 0));
        part.setRegistered(LocalDateTime.of(2016, 10, 10, 7, 0, 0));
        part.setPhone("+7-000-000-00-00");
        part.setSkype("jbaruh");
        part.setCity("Cupertino, CA");
        part.setEmployer("JFrog");
        part.setBiography("Developer advocate в компании JFrog, и делает в жизни ровно 3 вещи: зависает с разработчиками Bintray и Artifactory, пописывает для них код, и рассказывает о впечатлениях в блогах и на конференциях. И так несколько лет подряд, ни минуты об этом не жалея.");
        part.setDescription("Поскольку «религия не позволяет» быть евангелистом, Барух — developer advocate в компании JFrog и делает в жизни ровно 3 вещи: зависает с разработчиками Bintray и Artifactory, пописывает для них код, и рассказывает о впечатлениях в блогах и на конференциях, таких как JavaOne, Devoxx, OSCON, конечно же JPoint и Joker, да и многих других. И так более десяти лет подряд.");
        part.setTravelHelp("");
        part.setSex(Sex.MALE);
        part.setEnabled(true);
        part.setPhotoURL("http://2016.jpoint.ru/img/baruch.png");
        part.setId(100004L);

        part.setFirstName("Барух");
        part.setLastName("Садогурский");

        Email email = new Email();
        email.setId(100074L);
        email.setEmail("jbaruch@gmail.com");
        email.setMain(true);
        email.setOwner(part);
        part.addEmail(email);

        part.getCommentList();

        return part;
    }
    public Participant getNewParticipant() {
        Participant part = new Participant();
        part.setBirthDay(LocalDateTime.now().minus(39, ChronoUnit.YEARS));
        part.setRegistered(LocalDateTime.now().minus(20, ChronoUnit.DAYS));
        part.setPhone("+0-000-000-00-00");
        part.setSkype("skype");
        part.setCity("Санкт-Петербург");
        part.setEmployer("Test Empl.");
        part.setBiography("test biography");
        part.setDescription("test description");
        part.setSex(Sex.FEMALE);
        part.setEnabled(true);
        part.setPhotoURL("testvisitor.jpg");

        part.setFirstName("Test");
        part.setLastName("Participant");

        Email email = new Email();
        email.setEmail("email@gmail.com");
        email.setMain(true);
        email.setOwner(part);
        part.addEmail(email);

        return part;
    }


    public Participant getNewParticipantWithNewCommentTwitterGithub() {
        Participant part = getNewParticipant();
        part.setCommentList(Arrays.asList(getNewComment(), getNewComment()));

        Twitter t = new Twitter();
        t.setAccountLink("https://twitter.com/shipilev");
        t.setOwner(part);

        GitHub g = new GitHub();
        g.setAccountLink("https://github.com/alsiExp/jEvent");
        g.setOwner(part);

        return part;
    }



    public List<Participant> getMixedVisitorsList() {
        Participant v1 = participantService.save(getNewParticipant());
        Participant v2 = participantService.get(100005L);
        Participant v3 = getNewParticipant();
        return Arrays.asList(v1, v2, v3);
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
        ps1.setSpeaker(getExistingParticipant());
        ps1.setFullDescription("test description");
        ps1.setSpeechName("Test speech name");
        ps1.setWishPrice(5000.0);*/

        Speech ps2 = new Speech();
        ps2.setEvent(e);
/*        ps2.setSendDate(LocalDateTime.now().minusWeeks(1));
        ps2.setSpeaker(getExistingParticipant());
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
        cv.setParticipant(getExistingParticipant());
        Rate r = getRates().get(0);
        r.setId(100037L);
        cv.setRate(r);

        return new HashSet<>(Arrays.asList(cv));
    }
}
