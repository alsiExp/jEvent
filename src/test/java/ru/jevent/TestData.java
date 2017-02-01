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
import ru.jevent.service.EventService;
import ru.jevent.service.ParticipantService;
import ru.jevent.service.PartnerService;
import ru.jevent.service.SpeechService;

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
    private SpeechService speechService;


    public TestData() {
    }

    @Autowired
    public TestData(PartnerService partnerService,
                    ParticipantService participantService,
                    EventService eventService,
                    SpeechService speechService) {
        this.partnerService = partnerService;
        this.participantService = participantService;
        this.eventService = eventService;
        this.speechService = speechService;
    }



    //Users
    public User getNewUser() {
        return new User("Test User",true, "photo.jpg", "login", "pass");
    }

    public User getExistingUser() {
        User exUser = new User();
        exUser.setLogin("ekaterina");
        exUser.setPassword("$2a$10$Sh0ZD2NFrzRRJJEKEWn8l.92ROEuzlVyzB9SV1AM8fdluPR0aC1ni");
        exUser.getRoles();
        exUser.addRoles(Role.ROLE_USER);
        exUser.addRoles(Role.ROLE_ADMIN);
        exUser.setFullName("Екатерина Курилова");
        exUser.setEnabled(true);
        exUser.setPhotoURL("kurilova.jpg");
        exUser.setId(100008L);
        return exUser;
    }

    public User getUser06() {
        User u = new User();
        u.setLogin("alexey");
        u.setPassword("$2a$10$Sh0ZD2NFrzRRJJEKEWn8l.92ROEuzlVyzB9SV1AM8fdluPR0aC1ni");
        u.getRoles();
        u.setFullName("Алексей Фёдоров");
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
        u.setPassword("$2a$10$Sh0ZD2NFrzRRJJEKEWn8l.92ROEuzlVyzB9SV1AM8fdluPR0aC1ni");
        u.getRoles();
        u.setFullName("Яна Пилюгина");
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
        part.setEnabled(true);
        part.setPhotoURL("http://2016.jpoint.ru/img/baruch.png");
        part.setId(100004L);
        part.setTravelHelp("No");

        part.setFullName("Барух Садогурский");

        Email email = new Email();
        email.setId(100072L);
        email.setEmail("jbaruch@gmail.com");
        email.setMain(true);
        email.setOwner(part);
        part.addEmail(email);

        Twitter t = new Twitter();
        t.setId(100017L);
        t.setAccountLink("jbaruch");

        part.setTwitter(t);

        Set<Speech> s = new HashSet<>();
        s.add(speechService.get(100018L));
        part.setSpeechSet(s);

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
        part.setEnabled(true);
        part.setPhotoURL("testvisitor.jpg");

        part.setFullName("Test Participant");

        Email email = new Email();
        email.setEmail("email@gmail.com");
        email.setMain(true);
        email.setOwner(part);
        part.addEmail(email);

        part.getSpeechSet();

        return part;
    }

    public Participant getNewParticipantWithNewTwitterGithub() {
        Participant part = getNewParticipant();

        Twitter t = new Twitter();
        t.setAccountLink("shipilev");
        t.setOwner(part);
        part.setTwitter(t);

        GitHub g = new GitHub();
        g.setAccountLink("alsiExp");
        g.setOwner(part);
        part.setGitHub(g);

        return part;
    }

    public List<Participant> getMixedVisitorsList() {
        Participant v1 = participantService.save(getNewParticipant());
        Participant v2 = participantService.get(100005L);
        Participant v3 = getNewParticipant();
        return Arrays.asList(v1, v2, v3);
    }

    //Partners
    public Partner getNewPartner() {
        Partner p = new Partner();
        p.setName("Test partner");
        p.setContactName("Test manager");
        p.setContactEmail("test@email.com");
        p.setContactPhone("+7-999-000-00-00");
        p.setDescription("Test partner description");
        p.setLogoURL("testpartner.jpg");
        return p;
    }

    //Speeches
    public Set<Speech> getSpeechSet(Event e) {
        Speech s = speechService.get(100016L);
        s.setId(null);
        s.setEvent(e);

        return new HashSet<>(Arrays.asList(s, s));
    }


    //Events
    public Event getSimpleEvent() {
        Event event = new Event();
        event.setName("Test Event");
        event.setVersion("test");
        event.setJiraKey("test adress");
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
        return event;
    }

    public List<Rate> getRates() {
        Rate r1 = new Rate("Личное присутствие Standard", RateType.PERSONAL_STANDARD, LocalDateTime.of(2016, 4, 1, 0, 0), LocalDateTime.of(2016, 7, 1, 23, 59), 12000);
        Rate r2 = new Rate("Онлайн-Трансляция Standard", RateType.ONLINE_STANDARD, LocalDateTime.of(2016, 4, 1, 0, 0), LocalDateTime.of(2016, 7, 1, 23, 59), 8000);
        return Arrays.asList(r2, r1);
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
