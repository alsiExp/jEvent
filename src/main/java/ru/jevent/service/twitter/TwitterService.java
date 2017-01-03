package ru.jevent.service.twitter;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


@Service
public class TwitterService {

    public int getFollowers(String login) throws IOException {

        String userUrl = "https://twitter.com/" + login;
        URL url = new URL(userUrl);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String tmp = bufferedReader.readLine();
        String stringHtml = new String();
        while (tmp != null) {
            stringHtml += tmp;
            tmp = bufferedReader.readLine();
        }
        bufferedReader.close();
        int startIndexOf = stringHtml.indexOf("Читатели") + 15;
        int endIndexOf = stringHtml.indexOf("</span>", startIndexOf);
        String stringSpan = stringHtml.substring(startIndexOf, endIndexOf);

        int num = 160;
        char c = (char)num;
        stringSpan = stringSpan.replaceAll(String.valueOf(c), "");
        String[] numbers = stringSpan.split(">");

        return Integer.parseInt(numbers[1]);
    }
}
