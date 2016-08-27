package ru.jevent.model.Partner;

import ru.jevent.model.BaseEntity;
import ru.jevent.model.Common.Attachable;

public class Partner extends BaseEntity implements Attachable {

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
