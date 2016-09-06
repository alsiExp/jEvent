package ru.jevent.model;

import ru.jevent.model.Enums.Sex;
import ru.jevent.model.PersonParts.Email;
import ru.jevent.model.PersonParts.Phone;

import java.util.LinkedList;

public class Person extends BaseEntity {

    protected String firstName;
    protected Sex sex;
    protected String lastName;

    protected LinkedList<Phone> phones;
    protected LinkedList<Email> emails;

    protected byte[] photo;          // to store in DB

    public Person() {
    }

    public Person(Long id, String firstName, String lastName) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(Long id, String firstName, Sex sex, String lastName, LinkedList<Phone> phones, LinkedList<Email> emails, byte[] photo) {
        super(id);
        this.firstName = firstName;
        this.sex = sex;
        this.lastName = lastName;
        this.phones = phones;
        this.emails = emails;
        this.photo = photo;
    }

    public String getFullName() {
        return this.firstName + " " + this.getLastName();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LinkedList<Phone> getPhones() {
        if(phones == null) {
            phones = new LinkedList<>();
        }
        return phones;
    }

    public void setPhones(LinkedList<Phone> phones) {
        this.phones = phones;
    }

    public LinkedList<Email> getEmails() {
        if(emails == null) {
            emails = new LinkedList<>();
        }
        return emails;
    }

    public void setEmails(LinkedList<Email> emails) {
        this.emails = emails;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
