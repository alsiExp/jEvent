package ru.jevent.model;

import org.hibernate.validator.constraints.NotEmpty;
import ru.jevent.model.Enums.Sex;
import ru.jevent.model.converter.SexConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class Person extends BaseEntity {

    @NotEmpty
    @Column(name = "first_name", nullable = false)
    protected String firstName;
    @Column(name = "last_name")
    protected String lastName;

    // see http://stackoverflow.com/questions/5596518/read-enum-and-its-fields-from-a-database-jpa
    @Column(name = "sex")
    @Convert(converter = SexConverter.class)
    protected Sex sex;

    @Column(name = "enabled")
    protected boolean enabled;

    //    store images in directory:
    //    src/main/webapp/resources/images/person/
    @Column(name = "photo_url")
    protected String photoURL;

    public Person() {
    }

    public Person(String firstName, String lastName, Sex sex, boolean enabled, String photoURL) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.enabled = enabled;
        this.photoURL = photoURL;
    }

    public Person(Long id, String firstName, String lastName, Sex sex, String photoURL) {
        super(id);
        this.firstName = firstName;
        this.sex = sex;
        this.lastName = lastName;
        this.photoURL = photoURL;
    }

    public String getFullName() {
        return this.getFirstName() + " " + this.getLastName();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        if (!super.equals(o)) return false;

        Person person = (Person) o;

        if (enabled != person.enabled) return false;
        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) return false;
        if (lastName != null ? !lastName.equals(person.lastName) : person.lastName != null) return false;
        if (sex != person.sex) return false;
        return photoURL != null ? photoURL.equals(person.photoURL) : person.photoURL == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (enabled ? 1 : 0);
        result = 31 * result + (photoURL != null ? photoURL.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", sex=" + sex +
                ", enabled=" + enabled +
                ", photoURL='" + photoURL + '\'' +
                '}';
    }


}
