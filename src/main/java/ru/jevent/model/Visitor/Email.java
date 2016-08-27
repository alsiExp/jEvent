package ru.jevent.model.Visitor;

import ru.jevent.model.BaseEntity;

public class Email extends BaseEntity {
    private String email;
    private String note;

    public boolean validateEmail(String newEmail) {
        return true;
    }
}
