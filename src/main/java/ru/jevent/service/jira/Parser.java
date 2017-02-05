package ru.jevent.service.jira;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.util.StringUtils.isEmpty;

public class Parser {
    private Map<String, String> result = new HashMap<>();
    private final String description;
    private String debug;

    private String email;
    private String name;
    private String nameEN = null;
    private String company;
    private String skype;
    private String photo;
    private String phone;
    private String twitter;
    private String city;
    private String travel;
    private String bio;
    private String bioEN;
    private String back;

    private String title;
    private String titleEN;

    private String desc;
    private String descEN;

    private String profit;
    private String plan;
    private String focus;

    private String shortDesc;
    private String shortDescEN;

    public Parser(String description) {
        this.description = description;
    }

    public Parser(String description, String speechJiraKey) {
        this.description = description;
        debug = speechJiraKey;
    }


    public Map<String, String> getResult() {
        if(parse(description)) {
            fillResult();
            return result;
        } else
            return null;
    }

    private void fillResult() {
        result.put("email", email);
        result.put("name", name);
        result.put("nameEN", nameEN);
        result.put("company", company);
        result.put("skype", skype);
        result.put("photo", photo);
        result.put("phone", phone);
        result.put("twitter", twitter);
        result.put("city", city);
        result.put("travel", travel);
        result.put("bio", bio);
        result.put("bioEN", bioEN);
        result.put("back", back);
        result.put("title", title);
        result.put("titleEN", titleEN);
        result.put("desc", desc);
        result.put("descEN", descEN);
        result.put("profit", profit);
        result.put("plan", plan);
        result.put("focus", focus);
        result.put("shortDesc", shortDesc);
        result.put("shortDescEN", shortDescEN);
    }


    private boolean parse(String description) {

        Matcher fullMatcher = Pattern.compile("\\*Name:\\*\\s+([\\s\\S]*);\\s*\\*Company:\\*\\s+([\\s\\S]*),\\s*\\*Photo link:\\*\\s+([\\s\\S]*);\\s*\\*Email:\\*\\s+([\\s\\S]*);\\s*\\*Skype:\\*\\s+([\\s\\S]*);\\s*\\*Phone:\\*\\s+([\\s\\S]*);\\s*\\*Twitter:\\*\\s+([\\s\\S]*)\\s*\\*Country, City:\\*\\s+([\\s\\S]*);\\s*\\*Travel:\\*\\s+([\\s\\S]*);\\s*\\*Bio:\\*\\s+([\\s\\S]*);\\s*\\*Speaker background:\\*\\s+([\\s\\S]*);\\s*\\*Talk title:\\*\\s+([\\s\\S]*);\\s*\\*Description:\\*\\s+([\\s\\S]*);\\s*\\*Short Description:\\*\\s+([\\s\\S]*);\\s*\\*Short Description:\\*\\s+([\\s\\S]*).").matcher(description);
        Matcher speakerMatcher = Pattern.compile("\\*Name:\\*\\s+([\\s\\S]*);\\s*\\*Company:\\*\\s+([\\s\\S]*),\\s*\\*Photo link:\\*\\s+([\\s\\S]*);\\s*\\*Email:\\*\\s+([\\s\\S]*);\\s*\\*Skype:\\*\\s+([\\s\\S]*);\\s*\\*Phone:\\*\\s+([\\s\\S]*);\\s*\\*Twitter:\\*\\s+([\\s\\S]*)\\s*\\*Country, City:\\*\\s+([\\s\\S]*);\\s*\\*Travel:\\*\\s+([\\s\\S]*);\\s*\\*Bio:\\*\\s+([\\s\\S]*);\\s*\\*Speaker background:\\*\\s+([\\s\\S]*);\\s*\\*Talk ").matcher(description);

        if (fullMatcher.find()) {
            String[] names = checkEN(fullMatcher.group(1));
            if (names != null) {
                name = names[0];
                nameEN = names[1];
            } else
                name = fullMatcher.group(1);
            company = fullMatcher.group(2);
            photo = fullMatcher.group(3);
            email = fullMatcher.group(4);
            skype = fullMatcher.group(5);
            phone = fullMatcher.group(6);
            twitter = checkTwitter(fullMatcher.group(7));
            city = fullMatcher.group(8);
            travel = fullMatcher.group(9);
            bio = fullMatcher.group(10);
            // regex to separate with bio en: ([\s\S]*);\s+\*Bio en:\*\s+([\s\S]*)
            back = fullMatcher.group(11);
            String[] titles = checkEN(fullMatcher.group(12));
            if (titles != null) {
                title = titles[0];
                titleEN = titles[1];
            } else
                title = fullMatcher.group(12);
            desc = fullMatcher.group(13);
            shortDescEN = fullMatcher.group(14);
            shortDesc = fullMatcher.group(15);

            if (isEmpty(email) || isEmpty(name) || isEmpty(title) || isEmpty(desc)) {
                if(debug != null)
                    System.err.println("Bad result " + debug);
                return false;
            }

            if (!name.isEmpty() && !title.isEmpty() && !desc.isEmpty()) {
                return true;
            }
        }
        if (speakerMatcher.find()) {
            name = speakerMatcher.group(1);
            String[] names = checkEN(name);
            if (names != null) {
                name = names[0];
                nameEN = names[1];
            }
            company = speakerMatcher.group(2);
            photo = speakerMatcher.group(3);
            email = speakerMatcher.group(4);
            skype = speakerMatcher.group(5);
            phone = speakerMatcher.group(6);
            twitter = checkTwitter(speakerMatcher.group(7));
            city = speakerMatcher.group(8);
            travel = speakerMatcher.group(9);
            bio = speakerMatcher.group(10);
            back = speakerMatcher.group(11);
            if (back.contains("*Talk title:*")) {
                back = back.split(";\\s*\\*")[0];
            }

            if (email == null || name == null) {
                if(debug != null)
                    System.err.println("SpeakerMatcher error " + debug);
                return false;
            }
        } else {
            customSpeakerParse(description);
        }


        Pattern speechWithPlanPattern = Pattern.compile("\\*Talk title:\\*\\s+([\\s\\S]*);\\s+\\*Talk title:\\*\\s+([\\s\\S]*);\\s+\\*Description:\\*\\s+([\\s\\S]*);\\s+\\*Description EN:\\*\\s+([\\s\\S]*);\\s+\\*Что получат:\\*\\s+([\\s\\S]*);\\s+\\*План:\\*\\s*([\\s\\S]*);\\s+\\*Доклад ориентирован:\\*\\s*([\\s\\S]*);\\s+\\*Short Description EN:\\*\\s*([\\s\\S]*);\\s*(?:\\*Short Description:\\*\\s*([\\s\\S]*))?", Pattern.DOTALL);
        Matcher speechWithPlanMatcher = speechWithPlanPattern.matcher(description);
        if (speechWithPlanMatcher.find()) {
            title = checkTitle(speechWithPlanMatcher.group(1));
            titleEN = speechWithPlanMatcher.group(2);
            if (titleEN.equals("null")) {
                titleEN = null;
            }
            desc = speechWithPlanMatcher.group(3);
            descEN = speechWithPlanMatcher.group(4);
            if (descEN.equals("null")) {
                descEN = null;
            }

            profit = speechWithPlanMatcher.group(5);
            plan = speechWithPlanMatcher.group(6);
            focus = speechWithPlanMatcher.group(7);

            shortDescEN = speechWithPlanMatcher.group(8);
            if (shortDescEN.equals("null")) {
                shortDescEN = null;
            }

            shortDesc = speechWithPlanMatcher.group(9);


            if (title == null || desc == null) {
                if(debug != null)
                    System.err.println("SpeechWithPlanMatcher error " + debug);
                return false;
            }
            return true;
        }

        Pattern speechWithoutPlanPattern = Pattern.compile("\\*Talk title:\\*\\s+([\\s\\S]*);\\s+\\*Talk title:\\*\\s+([\\s\\S]*);\\s+\\*Description:\\*\\s+([\\s\\S]*);\\s+\\*Description EN:\\*\\s+([\\s\\S]*);\\s+\\*Что получат:\\*\\s+([\\s\\S]*);\\s+\\*Доклад ориентирован:\\*\\s*([\\s\\S]*);\\s+\\*Short Description EN:\\*\\s*([\\s\\S]*);\\s*(?:\\*Short Description:\\*\\s*([\\s\\S]*))?", Pattern.DOTALL);
        Matcher speechWithoutPlanMatcher = speechWithoutPlanPattern.matcher(description);
        if (speechWithoutPlanMatcher.find()) {
            title = checkTitle(speechWithoutPlanMatcher.group(1));
            titleEN = speechWithoutPlanMatcher.group(2);
            if (titleEN.equals("null")) {
                titleEN = null;
            }
            desc = speechWithoutPlanMatcher.group(3);
            descEN = speechWithoutPlanMatcher.group(4);
            if (descEN.equals("null")) {
                descEN = null;
            }

            profit = speechWithoutPlanMatcher.group(5);
            focus = speechWithoutPlanMatcher.group(6);

            shortDescEN = speechWithoutPlanMatcher.group(7);
            if (shortDescEN.equals("null")) {
                shortDescEN = null;
            }

            shortDesc = speechWithoutPlanMatcher.group(8);


            if (title == null || desc == null) {
                if(debug != null)
                    System.err.println("SpeechWithoutPlanMatcher error" + debug);
                return false;
            }
            return true;
        }

        Pattern speechSimplePattern = Pattern.compile("\\*Talk title:\\*\\s+([\\s\\S]*);\\s*\\*Description:\\*\\s+([\\s\\S]*);\\s*\\*Short Description:\\*\\s+([\\s\\S]*);\\s*\\*Short Description:\\*\\s+([\\s\\S]*).");
        Matcher speechSimpleMatcher = speechSimplePattern.matcher(description);
        if(speechSimpleMatcher.find()) {
            title = checkTitle(speechSimpleMatcher.group(1));
            String[] titles = checkEN(title);
            if(titles != null) {
                title = titles[0];
                titleEN = titles[1];
            }
            desc = speechSimpleMatcher.group(2);
            shortDescEN = speechSimpleMatcher.group(3);
            if (shortDescEN.equals("null")) {
                shortDescEN = null;
            }
            shortDesc = speechSimpleMatcher.group(4);
            if (title == null || desc == null) {
                if(debug != null)
                    System.err.println("SpeechSimpleMatcher error " + debug);
                return false;
            }
            return true;
        }

        Pattern speechCommonPattern = Pattern.compile("\\*Talk title:\\*\\s+([\\s\\S]*);\\s*\\*Description:\\*\\s+([\\s\\S]*);\\s*\\*Short Description:\\*\\s+([\\s\\S]*).|!");
        Matcher speechCommonMatcher = speechCommonPattern.matcher(description);
        if(speechCommonMatcher.find()) {
            title = checkTitle(speechCommonMatcher.group(1));
            String[] titles = checkEN(title);
            if(titles != null) {
                title = titles[0];
                titleEN = titles[1];
            }
            desc = speechCommonMatcher.group(2);
            shortDesc = speechCommonMatcher.group(3);
            if (title == null || desc == null) {
                if(debug != null)
                    System.err.println("SpeechCommonMatcher error " + debug);
                return false;
            }
            return true;
        }

        Matcher speechCommonAltMatcher = Pattern.compile("\\*Talk title:\\*\\s+([\\s\\S]*);\\s*\\*Description:\\*\\s+([\\s\\S]*)(;|\\.)").matcher(description);
        if(speechCommonAltMatcher.find()) {
            title = checkTitle(speechCommonAltMatcher.group(1));
            String[] titles = checkEN(title);
            if (titles != null) {
                title = titles[0];
                titleEN = titles[1];
            }
            desc = speechCommonAltMatcher.group(2);
            return true;
        }
        if(debug != null)
            System.err.println("Parse error " + debug);
        return false;

    }

    private void customSpeakerParse(String description) {
        Matcher nameMatcher = Pattern.compile("\\*Name:\\*\\s+([\\s\\S]*);").matcher(description);
        if(nameMatcher.find()){
            name = nameMatcher.group(1).split(";")[0];
        }

        Matcher nameENMatcher = Pattern.compile("\\*NameEN:\\*\\s+([\\s\\S]*);").matcher(description);
        if(nameENMatcher.find()){
            nameEN = nameENMatcher.group(1).split(";")[0];
        }

        Matcher companyMatcher = Pattern.compile("\\*Company:\\*\\s+([\\s\\S]*),").matcher(description);
        if(companyMatcher.find()){
            company = companyMatcher.group(1).split(",")[0];
        }

        Matcher photoMatcher = Pattern.compile("\\*Photo link:\\*\\s+([\\s\\S]*);").matcher(description);
        if(photoMatcher.find()){
            photo = photoMatcher.group(1).split(";")[0];
        }

        Matcher emailMatcher = Pattern.compile("\\*Email:\\*\\s+([\\s\\S]*);").matcher(description);
        if(emailMatcher.find()){
            email = emailMatcher.group(1).split(";")[0];
        }

        Matcher skypeMatcher = Pattern.compile("\\*Skype:\\*\\s+([\\s\\S]*);").matcher(description);
        if(skypeMatcher.find()){
            skype = skypeMatcher.group(1).split(";")[0];
        }

        Matcher phoneMatcher = Pattern.compile("\\*Phone:\\*\\s+([\\s\\S]*);").matcher(description);
        if(phoneMatcher.find()){
            phone = phoneMatcher.group(1).split(";")[0];
        }

        Matcher twitterMatcher = Pattern.compile("\\*Twitter:\\*\\s+([\\s\\S]*);").matcher(description);
        if(twitterMatcher.find()){
            twitter = phoneMatcher.group(1).split(";")[0];
        }

        Matcher cityMatcher = Pattern.compile("\\*Country, City:\\*\\s+([\\s\\S]*);").matcher(description);
        if(cityMatcher.find()){
            city = cityMatcher.group(1).split(";")[0];
        }

        Matcher travelMatcher = Pattern.compile("\\*Travel:\\*\\s+([\\s\\S]*);").matcher(description);
        if(travelMatcher.find()){
            travel = travelMatcher.group(1).split(";")[0];
        }

        Matcher bioMatcher = Pattern.compile("\\*Bio:\\*\\s+([\\s\\S]*);").matcher(description);
        if(bioMatcher.find()){
            bio = bioMatcher.group(1).split(";")[0];
        }

        Matcher bioENMatcher = Pattern.compile("\\*Bio en:\\*\\s+([\\s\\S]*);").matcher(description);
        if(bioENMatcher.find()){
            bioEN = bioENMatcher.group(1).split(";")[0];
        }

        Matcher backMatcher = Pattern.compile("\\*Speaker background:\\*\\s+([\\s\\S]*);").matcher(description);
        if(backMatcher.find()){
            back = backMatcher.group(1).split(";")[0];
        }

    }


    private String[] checkEN(String str) {
        if (str != null) {
            if (str.contains("NameEN")) {
                String[] arr = str.split(";*\\s*\\*NameEN:\\*\\s*");
                if (arr.length > 1) {
                    if(arr[1].equals("null")){
                        arr[1] = null;
                    }
                    return arr;
                }
            } else if (str.contains("Talk title")) {
                String[] arr = str.split(";\\s*\\*Talk title:\\*\\s*");
                if (arr.length > 1) {
                    if(arr[1].equals("null")){
                        arr[1] = null;
                    }
                    return arr;
                }
            }
        }
        return null;

    }

    private String checkTwitter(String twitter) {
        if(twitter != null) {
            if (twitter.contains(";")) {
                String[] arr = twitter.split(";\\s*");
                if (arr.length > 0) {
                    twitter = arr[0];
                } else if (arr.length > 1) {
                    String f = arr[1];
                    // get followers count
                }
            }
        }
        return twitter;
    }

    private String checkTitle(String title) {
        if(title != null) {
            if (title.contains("\n")) {
                String[] arr = title.split(";");
                if (arr.length >= 1) {
                    title = arr[0];
                }
            }
        }
        return title;
    }

}