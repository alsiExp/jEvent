package ru.jevent.service.jira;

import net.rcarz.jiraclient.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jevent.model.Event;
import ru.jevent.model.User;
import ru.jevent.service.JiraService;
import ru.jevent.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class JiraServiceImpl implements JiraService {

    private static final String BASE_URL = "http://jira.jugru.org/";
    private  static final String ISSUE_LIST_PATTERN = "project = %s AND issuetype = %s AND ";
    private  static final String EVENT_LINK_PATTERN = "http://jira.jugru.org/issues/?jql=project%20%3D%20HOLYJS%20AND%20affectedVersion%3D\"2016%20Piter\"";

    private UserService userService;

    @Autowired
    public JiraServiceImpl(UserService userService) {
        this.userService = userService;
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
    public List<Event> getAllEvent(long userId) throws JiraException {
        JiraClient jira = new JiraClient(BASE_URL, getCredentials(userId));
        List<Project> projectList = jira.getProjects();
        List<Event> eventList = new ArrayList<>();
        for(Project p : projectList) {
            p = jira.getProject(p.getKey());
            for(Version version : p.getVersions()) {
                Event ev = new Event();
                ev.setVersion(version.getName());
                ev.setLogoURL(p.getAvatarUrls().get("48x48"));
                ev.setName(p.getName());
                ev.setDescription(p.getDescription());
                ev.setJiraLink(UriHelper.getEventLink(p.getName(), version.getName()));
                ev.setJiraKey(p.getKey());
                ev.setJiraId(Integer.parseInt(version.getId()));
                eventList.add(ev);
            }
        }

        return eventList;
/*
        // project = HolyJS AND affectedVersion = "2016 Piter"  AND type = Доклад
        Issue.SearchResult sr = jira.searchIssues("project = HEISENBUG AND issuetype = Доклад ", 500);

        System.out.println("Total: " + sr.total);
        for (Issue i : sr.issues)
            System.out.println("Result: " + i);

*/

    }

    public static String getBaseUrl() {
        return BASE_URL;
    }
}
