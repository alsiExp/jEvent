package ru.jevent.model.PersonParts;

import ru.jevent.model.BaseEntity;
import ru.jevent.model.Person;

public class Phone extends BaseEntity {

    private Person person;
    private String phone;
    private String note;
    private boolean isPrimary;
}
