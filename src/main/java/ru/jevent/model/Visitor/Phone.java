package ru.jevent.model.Visitor;

import ru.jevent.model.BaseEntity;

public class Phone extends BaseEntity {

    private String phone;
    private String note;

    public boolean validatePhone(String newPhone) {
        return true;
    }
}
