package ru.jevent.service.jira;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

class JiraHelper {

    private  static final String ISSUE_LIST_PATTERN = "project = %s AND issuetype = %s AND ";


    static String getSpeechIssuesJQL(String key, String version) {
        return String.format(ISSUE_LIST_PATTERN, key, version);
    }

    static String getEventLink(String eventName, String versionName) {
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
