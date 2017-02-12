package ru.jevent.service.twitter;

import org.springframework.stereotype.Service;
import ru.jevent.LoggerWrapper;
import ru.jevent.service.TwitterService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


@Service
public class TwitterServiceImpl implements TwitterService{

    private static final LoggerWrapper LOG = LoggerWrapper.get(TwitterServiceImpl.class);

    @Override
    public Integer getFollowers(String login) {

        try {
            String userUrl = "https://twitter.com/" + login;
            URL url = new URL(userUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String tmp = bufferedReader.readLine();
            String stringHtml = "";
            while (tmp != null) {
                stringHtml += tmp;
                tmp = bufferedReader.readLine();
            }
            bufferedReader.close();
            int startIndexOf = stringHtml.indexOf("Читатели") + 15;
            int endIndexOf = stringHtml.indexOf("</span>", startIndexOf);
            String stringSpan = stringHtml.substring(startIndexOf, endIndexOf);

            char c = (char) 160;
            stringSpan = stringSpan.replaceAll(String.valueOf(c), "");
            String[] numbers = stringSpan.split(">");

            return Integer.parseInt(numbers[1]);
        } catch (IOException ioe) {
            LOG.error(ioe.getMessage());
            ioe.printStackTrace();
            return null;
        }

    }
}
