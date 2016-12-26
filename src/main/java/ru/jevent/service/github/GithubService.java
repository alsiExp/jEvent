package ru.jevent.service.github;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Service
public class GithubService {

    public JSONObject getUserInformation(String login) throws IOException, JSONException {

            String userUrl = "https://api.github.com/users/"+ login;
            URL url = new URL(userUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String tmp = bufferedReader.readLine();
            String stringUser = new String();
            while (tmp != null) {
                stringUser += tmp;
                tmp = bufferedReader.readLine();
            }
            bufferedReader.close();

            JSONObject jsonObject = new JSONObject(stringUser);

        return jsonObject;
    }
}
