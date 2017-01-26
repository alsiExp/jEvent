package ru.jevent.service.jira;

import net.rcarz.jiraclient.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jevent.model.Event;
import ru.jevent.model.User;
import ru.jevent.service.EventService;
import ru.jevent.service.JiraService;
import ru.jevent.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class JiraServiceImpl implements JiraService {

    private static final String BASE_URL = "http://jira.jugru.org/";

    private final UserService userService;
    private final EventService eventService;

    @Autowired
    public JiraServiceImpl(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
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

                int maxResult = 250;
                Issue.SearchResult speechResult = jira.searchIssues(JiraHelper.getSpeechIssuesJQL(p.getKey(), version.getName()), maxResult);
                if(speechResult.total > maxResult) {
                    speechResult = jira.searchIssues(JiraHelper.getSpeechIssuesJQL(p.getKey(), version.getName()), speechResult.total);
                }
                for(Issue issue : speechResult.issues) {
                    parseIssue(issue, jira.getIssue(issue.getKey(),"comment").getComments());
                }
            }
        }

        return eventNames;
    }

    public void parseIssue(Issue issue, List<Comment> comments) {
        //TODO: parse Speech and Participant
    }

}
