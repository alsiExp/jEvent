package ru.jevent.service.jira;

import net.rcarz.jiraclient.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Event;
import ru.jevent.model.Participant;
import ru.jevent.model.Speech;
import ru.jevent.model.User;
import ru.jevent.model.additionalEntity.Email;
import ru.jevent.model.additionalEntity.Twitter;
import ru.jevent.service.*;
import ru.jevent.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JiraServiceImpl implements JiraService {

    private static final LoggerWrapper LOG = LoggerWrapper.get(JiraService.class);
    private static final String BASE_URL = "http://jira.jugru.org/";

    private final UserService userService;
    private final EventService eventService;
    private final ParticipantService participantService;
    private final SpeechService speechService;

    @Autowired
    public JiraServiceImpl(UserService userService, EventService eventService,
                           ParticipantService participantService, SpeechService speechService) {
        this.userService = userService;
        this.eventService = eventService;
        this.participantService = participantService;
        this.speechService = speechService;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    private BasicCredentials getCredentials(long userId) {
        User user = userService.get(userId);
        return new BasicCredentials(user.getJiraLogin(), user.getJiraPassword());
    }

    @Override
    public List<String> test(long userId) throws JiraException {
        JiraClient jira = new JiraClient(BASE_URL, getCredentials(userId));
        List<Project> projectList = jira.getProjects();
        List<String> list = new ArrayList<>();
        for(Project p : projectList) {
            list.add(p.getName());
        }
        return list;
    }

    @Override
    public List<String> getAllEvent(long userId) throws JiraException {
        JiraClient jira = new JiraClient(BASE_URL, getCredentials(userId));
        List<Project> projectList = jira.getProjects();
        List<String> eventNames = new ArrayList<>();
        for(Project p : projectList) {
            p = jira.getProject(p.getKey());
            for(Version version : p.getVersions()) {
                int jiraId = Integer.parseInt(version.getId());
                Event event = eventService.getByJiraId(jiraId);
                if(event == null) {
                    event = new Event();
                }
                event.setVersion(version.getName());
                event.setLogoURL(p.getAvatarUrls().get("48x48"));
                event.setName(p.getName());
                event.setDescription(p.getDescription());
                event.setJiraLink(JiraHelper.getEventLink(p.getName(), version.getName()));
                event.setJiraKey(p.getKey());
                event.setJiraId(jiraId);
                event.setJiraSync(LocalDateTime.now());
                if(event.getId() == null) {
                    eventService.save(event);
                } else {
                    eventService.update(event);
                }
                eventNames.add(event.getName() + " " + event.getVersion());
            }
        }

        return eventNames;
    }

    @Override
    public List<String> getEventSpeechList(long eventId, long userId) throws JiraException {
        Map<String, List<String>> parseMap = new HashMap<>();
        List<String> success = new ArrayList<>();
        List<String> error = new ArrayList<>();
        parseMap.put("sucess", success);
        parseMap.put("error", error);

        JiraClient jira = new JiraClient(BASE_URL, getCredentials(userId));
        Event event = eventService.get(eventId);

        int maxResult = 350;
        Issue.SearchResult speechResult = jira.searchIssues(JiraHelper.getSpeechIssuesJQL(event.getJiraKey(), event.getVersion()), maxResult);
        for(Issue issue : speechResult.issues) {
            List<Comment> commentList = jira.getIssue(issue.getKey(), "comment").getComments();
            if(parseIssue(issue, commentList)) {
                success.add(issue.getKey());
            } else {
                error.add(issue.getKey());
            }
        }

        return success;
    }

    private boolean parseIssue(Issue issue, List<Comment> comments) {

        Participant part;
        Speech speech =  speechService.getByJiraId(Integer.parseInt(issue.getId()));
        if(speech == null) {
            speech = new Speech();
            speech.setJiraId(Integer.parseInt(issue.getId()));
            speech.setJiraKey(issue.getKey());
            speech.setJiraLink("http://jira.jugru.org/browse/" + issue.getKey());
        }
        speech.setJiraResolution(issue.getResolution().getName());
        speech.setJiraStatus(issue.getStatus().getName());
        speech.setJiraSync(LocalDateTime.now());

        // speaker - title
        String[] summary = issue.getSummary().split("\\s+\u2014\\s+");
        speech.setName(summary[1]);

        Map<String, String> result = new Parser(issue.getDescription(), issue.getKey()).getResult();
        if(result.get("email") != null) {
            part = participantService.getByEmail(result.get("email"));
        } else {
            throw new NotFoundException("Can`t parse email from " + issue.getKey());
        }
        if(part == null) {
            part = new Participant();
            part.setFullName(summary[0]);
            part.addEmail(new Email(null,result.get("email"), true, part));
        }
        if(part.getTwitter() == null && result.get("twitter") != null) {
            part.setTwitter(new Twitter(null, result.get("twitter"), part));
        }

        part.setFullNameEN(result.get("nameEN"));
        part.setEmployer(result.get("company"));
        part.setSkype(result.get("skype"));
        part.setPhotoURL(result.get("photo"));
        part.setPhone(result.get("phone"));
        part.setCity(result.get("city"));
        part.setTravelHelp(result.get("travel"));
        part.setBiography(result.get("bio"));
        part.setSpeakerBackground(result.get("back"));
        part.setRegistered(LocalDateTime.now());
        part.setEnabled(true);

        part = participantService.save(part);


        if(result.get("name") != null && result.get("title") != null) {
            System.out.println("Ok " + issue.getKey());
        }

        return true;
    }


}
