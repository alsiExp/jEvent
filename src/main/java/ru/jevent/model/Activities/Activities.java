package ru.jevent.model.Activities;

import ru.jevent.model.BaseEntity;
import ru.jevent.model.NamedEntity;

import java.util.ArrayList;

public class Activities extends BaseEntity {
    // TODO there is something wrong... :(
    private Activity root;
    private static int maxValue;        // maximal value of all activities

    public class Activity extends NamedEntity {
        private int value;
        private Activity parent;
        private ArrayList<Activity> children;
    }
}
