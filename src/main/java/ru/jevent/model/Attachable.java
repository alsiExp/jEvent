package ru.jevent.model;

public interface Attachable {
    String getAttachName();
    String getAttachDescription();
    byte[] getAttachImage();  //or String/Path?
}
