package ru.jevent.service.jira;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UriHelper {


    public static String getEventLink(String eventName, String versionName) {
        StringBuilder uri = new StringBuilder();
        uri.append(JiraServiceImpl.getBaseUrl());
        uri.append("issues/?jql=");
        uri.append(encode("project = " + eventName + " AND " + "affectedVersion = \"" + versionName + "\""));
        return uri.toString();
    }

    private static String encode(String jql) {
        try {
            return URLEncoder.encode(jql, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
