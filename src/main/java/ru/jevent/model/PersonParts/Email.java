package ru.jevent.model.PersonParts;

import ru.jevent.model.BaseEntity;
import ru.jevent.model.Person;

public class Email extends BaseEntity {

    private Person person;
    private String email;
    private String note;
    private boolean isPrimary;
}
