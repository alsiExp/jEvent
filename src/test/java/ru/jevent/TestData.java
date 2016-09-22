package ru.jevent;

import ru.jevent.model.Comment;
import ru.jevent.model.Enums.Sex;
import ru.jevent.model.User;
import ru.jevent.model.Visitor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

}
