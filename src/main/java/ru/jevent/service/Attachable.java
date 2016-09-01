package ru.jevent.service;

public interface Attachable {
    public String getAttachName();
    public String getAttachDescription();
    public String getAttachURL();
    public byte[] getAttachImage();  //or String/Path?
}
