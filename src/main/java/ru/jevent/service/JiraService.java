package ru.jevent.service;

import net.rcarz.jiraclient.JiraException;

import java.util.List;

public interface JiraService {
    public List<String> getAllEvent(long userId)  throws JiraException;

    public List<String> test(long userId)throws JiraException;
}
