package ru.jevent.model.additionalEntity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import ru.jevent.model.Participant;
import ru.jevent.model.superclasses.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "emails")
public class Email extends BaseEntity {

    @Column(name = "email")
    private String email;
    @Column(name = "main")
    private boolean main;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    @JsonBackReference
    private Participant owner;

    public Email() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public Participant getOwner() {
        return owner;
    }

    public void setOwner(Participant owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Email email1 = (Email) o;

        if (main != email1.main) return false;
        if (email != null ? !email.equals(email1.email) : email1.email != null) return false;
        return owner != null ? owner.getId().equals(email1.owner.getId()) : email1.owner == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (main ? 1 : 0);
        if(owner != null) {
            result = 31 * result + (owner.getId() != null ? owner.getId().hashCode() : 0);
        }
        return result;
    }

    @Override
    public String toString() {
        String ownerName = "";
        if(owner != null) {
            ownerName = owner.getFullName();
        }
        return "Email{" +
                super.toString() +
                ", email='" + email + '\'' +
                ", main=" + main +
                ", owner=" + ownerName +
                "} ";
    }
}
