package ru.jevent.model.Event;

import ru.jevent.model.Common.*;
import ru.jevent.model.NamedEntity;

public class Event extends NamedEntity implements Attachable {

    public String getAttachName() {
        return null;
    }

    public String getAttachDescription() {
        return null;
    }

    public String getAttachURL() {
        return null;
    }

    public byte[] getAttachImage() {
        return new byte[0];
    }
}