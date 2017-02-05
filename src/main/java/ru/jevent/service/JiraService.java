package ru.jevent.service;

import net.rcarz.jiraclient.JiraException;

import java.util.List;
import java.util.Map;

public interface JiraService {
    Map<String, List<String>> getAllEvent(long userId)  throws JiraException;

    List<String> test(long userId)throws JiraException;

    Map<String, List<String>> getEventSpeechList(long eventId, long userId) throws JiraException;
}
