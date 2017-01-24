package ru.jevent.model.superclasses;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class Person extends BaseEntity {

    @NotEmpty
    @Column(name = "full_name", nullable = false)
    protected String fullName;

    @Column(name = "enabled")
    protected boolean enabled;

    //    store images in directory:
    //    src/main/webapp/resources/images/person/
    @Column(name = "photo_url")
    protected String photoURL;

    public Person() {
    }

    public Person(String fullName, boolean enabled, String photoURL) {
        this.fullName = fullName;
        this.enabled = enabled;
        this.photoURL = photoURL;
    }

    public Person(Long id, String fullName, String lastName, String photoURL) {
        super(id);
        this.fullName = fullName;
        this.photoURL = photoURL;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String firstName) {
        this.fullName = firstName;
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
        if (fullName != null ? !fullName.equals(person.fullName) : person.fullName != null) return false;
        return photoURL != null ? photoURL.equals(person.photoURL) : person.photoURL == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (enabled ? 1 : 0);
        result = 31 * result + (photoURL != null ? photoURL.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + fullName + '\'' +
                ", enabled=" + enabled +
                ", photoURL='" + photoURL + '\'' +
                '}';
    }


}
