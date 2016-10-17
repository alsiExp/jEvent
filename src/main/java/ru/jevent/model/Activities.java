package ru.jevent.model;

import java.util.ArrayList;

public class Activities extends BaseEntity {

    private Activity root;
    private static int maxValue;        // maximal value of all activities

    public class Activity extends NamedEntity {
        private int value;
        private Activity parent;
        private ArrayList<Activity> children;
    }
}
