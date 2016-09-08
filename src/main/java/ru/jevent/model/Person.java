package ru.jevent.model;

import ru.jevent.model.Enums.Sex;

public class Person extends BaseEntity {

    protected String firstName;
    protected String lastName;
    protected Sex sex;

    protected boolean enabled;

    //    store images in directory:
    //    src/main/webapp/resources/images/person/
    protected String photoURL;

    public Person() {
    }

    public Person(Long id, String firstName, String lastName) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(Long id, String firstName, String lastName, Sex sex, String photoURL) {
        super(id);
        this.firstName = firstName;
        this.sex = sex;
        this.lastName = lastName;
        this.photoURL = photoURL;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photo) {
        this.photoURL = photo;
    }
}
