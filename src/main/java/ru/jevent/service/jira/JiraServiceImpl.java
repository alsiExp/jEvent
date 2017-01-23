package ru.jevent.service.jira;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.Project;
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
    public List<Event> getAllEvent() throws JiraException {

//        try {
//            Issue issue = jira.getIssue("name");
//            System.out.println(issue);
/*
        JiraClient jira = new JiraClient("http://jira.jugru.org/", creds);
        List<Project> list = jira.getProjects();
        for(Project p : list) {
            System.out.println(p);
            Project pr =  jira.getProject(p.getKey());
            System.out.println(pr);
        }
        // project = HolyJS AND affectedVersion = "2016 Piter"  AND type = Доклад
        Issue.SearchResult sr = jira.searchIssues("project = HEISENBUG AND issuetype = Доклад ", 500);

        System.out.println("Total: " + sr.total);
        for (Issue i : sr.issues)
            System.out.println("Result: " + i);
                    } catch (JiraException ex) {
            System.err.println(ex.getMessage());

            if (ex.getCause() != null)
                System.err.println(ex.getCause().getMessage());
        }
*/


        return null;
    }
}
