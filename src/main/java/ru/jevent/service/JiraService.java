package ru.jevent.service;

import net.rcarz.jiraclient.JiraException;

import java.util.List;

public interface JiraService {
    List<String> getAllEvent(long userId)  throws JiraException;

    List<String> test(long userId)throws JiraException;

    List<String> getEventSpeechList(long eventId, long userId) throws JiraException;
}
