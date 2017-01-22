package ru.jevent.service.jira;

import net.rcarz.jiraclient.BasicCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import ru.jevent.LoggedUser;
import ru.jevent.model.Event;
import ru.jevent.model.User;
import ru.jevent.service.JiraService;
import ru.jevent.service.UserService;

import java.util.List;

public class JiraServiceImpl implements JiraService {

    private final String base = "http://jira.jugru.org/";
    private UserService userService;

    @Autowired
    public JiraServiceImpl(UserService userService) {
        this.userService = userService;
    }

    private BasicCredentials getCredentials() {
        User user = userService.get(LoggedUser.id());


        return null;
    }

    @Override
    public List<Event> getAllEvent() {

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
