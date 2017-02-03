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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public List<String> getEventSpeechList(long eventId, long userId) {

        JiraClient jira = new JiraClient(BASE_URL, getCredentials(userId));
        Event event = eventService.get(eventId);

        int maxResult = 350;
        try {
        Issue.SearchResult speechResult = jira.searchIssues(JiraHelper.getSpeechIssuesJQL(event.getJiraKey(), event.getVersion()), maxResult);
        for(Issue issue : speechResult.issues) {
            List<Comment> commentList = jira.getIssue(issue.getKey(), "comment").getComments();
            Speech speech =  parseIssue(issue, event);
            if(speech != null) {
                System.out.println(speech);
            }
        }

        } catch(JiraException je){
            LOG.error(je.getMessage(), je);
         }
        return null;
    }

    public Speech parseIssue(Issue issue, Event event) {
        //TODO: parse Speech and Participant
        Matcher fullMatcher = Pattern.compile("\\*Name:\\*\\s+([\\s\\S]*);\\s*\\*Company:\\*\\s+([\\s\\S]*),\\s*\\*Photo link:\\*\\s+([\\s\\S]*);\\s*\\*Email:\\*\\s+([\\s\\S]*);\\s*\\*Skype:\\*\\s+([\\s\\S]*);\\s*\\*Phone:\\*\\s+([\\s\\S]*);\\s*\\*Twitter:\\*\\s+([\\s\\S]*)\\s*\\*Country, City:\\*\\s+([\\s\\S]*);\\s*\\*Travel:\\*\\s+([\\s\\S]*);\\s*\\*Bio:\\*\\s+([\\s\\S]*);\\s*\\*Speaker background:\\*\\s+([\\s\\S]*);\\s*\\*Talk title:\\*\\s+([\\s\\S]*);\\s*\\*Description:\\*\\s+([\\s\\S]*);\\s*\\*Short Description:\\*\\s+([\\s\\S]*);\\s*\\*Short Description:\\*\\s+([\\s\\S]*).").matcher(issue.getDescription());
        Matcher speakerMatcher = Pattern.compile("\\*Name:\\*\\s+([\\s\\S]*);\\s*\\*Company:\\*\\s+([\\s\\S]*),\\s*\\*Photo link:\\*\\s+([\\s\\S]*);\\s*\\*Email:\\*\\s+([\\s\\S]*);\\s*\\*Skype:\\*\\s+([\\s\\S]*);\\s*\\*Phone:\\*\\s+([\\s\\S]*);\\s*\\*Twitter:\\*\\s+([\\s\\S]*)\\s*\\*Country, City:\\*\\s+([\\s\\S]*);\\s*\\*Travel:\\*\\s+([\\s\\S]*);\\s*\\*Bio:\\*\\s+([\\s\\S]*);\\s*\\*Speaker background:\\*\\s+([\\s\\S]*);\\s*\\*Talk ").matcher(issue.getDescription());
        Participant part;
        Speech speech = new Speech();
        String speechTitle;
        String speechTitleEN;

        if (fullMatcher.find()) {
            String email = fullMatcher.group(4);
            part = participantService.getByEmail(email);
            if(part == null) {
                part = new Participant();
                part.addEmail(new Email(null, email, true, part));
            }


            String[] names = checkEN(fullMatcher.group(1));
            if (names != null) {
                part.setFullName(names[0]);
                part.setFullNameEN(names[1]);
            } else {
                part.setFullName(fullMatcher.group(1));
            }
            part.setEmployer(fullMatcher.group(2));
            part.setPhotoURL(fullMatcher.group(3));
            part.setSkype(fullMatcher.group(5));
            part.setPhone(fullMatcher.group(6));
            part.setTwitter(new Twitter(null, checkTwitter(fullMatcher.group(7)), part));
            part.setCity(fullMatcher.group(8));
            part.setTravelHelp(fullMatcher.group(9));
            // regex to separate with bio en: ([\s\S]*);\s+\*Bio en:\*\s+([\s\S]*)
            part.setBiography(fullMatcher.group(10));
            part.setSpeakerBackground(fullMatcher.group(11));



            String[] titles = checkEN(fullMatcher.group(12));
            if (titles != null) {
                speechTitle = titles[0];
                speechTitleEN = titles[1];
            } else
                speechTitle = fullMatcher.group(12);
            for (Speech s : part.getSpeechSet()) {
                if(s.getName().equals(speechTitle) && Objects.equals(s.getEvent().getId(), event.getId())) {
                    speech = s;
                }
            }
            speech.addSpeaker(part);
            speech.setFullDescription(fullMatcher.group(13));
            speech.setShortDescriptionEN(fullMatcher.group(14));
            speech.setShortDescription(fullMatcher.group(15));

            return new Speech();

        }
        if (speakerMatcher.find()) {
            String email = speakerMatcher.group(4);

/*            name = speakerMatcher.group(1);
            String[] names = checkEN(name);
            if (names != null) {
                name = names[0];
                nameEN = names[1];
            }
            company = speakerMatcher.group(2);
            photo = speakerMatcher.group(3);
            email = speakerMatcher.group(4);
            skype = speakerMatcher.group(5);
            phone = speakerMatcher.group(6);
            twitter = checkTwitter(speakerMatcher.group(7));
            city = speakerMatcher.group(8);
            travel = speakerMatcher.group(9);
            bio = speakerMatcher.group(10);
            back = speakerMatcher.group(11);
            if (back.contains("*Talk title:*")) {
                back = back.split(";\\s*\\*")[0];
            }

            if (email == null || name == null) {
                System.out.println(debug + " speakerMatcher error");
            }*/
        }
        return null;
    }

    private String[] checkEN(String str) {
        if (str != null) {
            if (str.contains("NameEN")) {
                String[] arr = str.split(";*\\s*\\*NameEN:\\*\\s*");
                if (arr.length > 1) {
                    if(arr[1].equals("null")){
                        arr[1] = null;
                    }
                    return arr;
                }
            } else if (str.contains("Talk title")) {
                String[] arr = str.split(";\\s*\\*Talk title:\\*\\s*");
                if (arr.length > 1) {
                    if(arr[1].equals("null")){
                        arr[1] = null;
                    }
                    return arr;
                }
            }
        }
        return null;

    }

    private String checkTwitter(String twitter) {
        if(twitter != null) {
            if (twitter.contains(";")) {
                String[] arr = twitter.split(";\\s*");
                if (arr.length > 0) {
                    twitter = arr[0];
                } else if (arr.length > 1) {
                    String f = arr[1];
                    // get followers count
                }
            }
        }
        return twitter;
    }

    private String checkTitle(String title) {
        if(title != null) {
            if (title.contains("\n")) {
                String[] arr = title.split(";");
                if (arr.length >= 1) {
                    title = arr[0];
                }
            }
        }
        return title;
    }


}
