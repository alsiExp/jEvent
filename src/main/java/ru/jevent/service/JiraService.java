package ru.jevent.service;

import net.rcarz.jiraclient.JiraException;
import ru.jevent.model.Event;

import java.util.List;

public interface JiraService {
    public List<Event> getAllEvent()  throws JiraException;

    public List<String> test(long userId)throws JiraException;
}
