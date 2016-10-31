package ru.jevent.model;

import javax.persistence.*;

@Entity
@Table(name = "emails")
public class Email extends NamedEntity{

    @Column(name = "email")
    String email;
    @Column(name = "main")
    private boolean main;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private Visitor owner;

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

    public Visitor getOwner() {
        return owner;
    }

    public void setOwner(Visitor owner) {
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
        result = 31 * result + (owner != null ? owner.getId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Email{" +
                super.toString() +
                ", email='" + email + '\'' +
                ", main=" + main +
                ", owner=" + owner +
                "} ";
    }
}
