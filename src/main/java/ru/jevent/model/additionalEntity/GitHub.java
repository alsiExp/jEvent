package ru.jevent.model.additionalEntity;

import ru.jevent.model.Participant;
import ru.jevent.model.superclasses.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "githubaccs")
public class GitHub extends BaseEntity {

    @Column(name = "account_link")
    String accountLink;
    @OneToOne(fetch= FetchType.LAZY, mappedBy="gitHub")
    Participant owner;

    public GitHub() {
    }

    public String getAccountLink() {
        return accountLink;
    }

    public void setAccountLink(String account) {
        this.accountLink = account;
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

        GitHub gitHub = (GitHub) o;

        if (accountLink != null ? !accountLink.equals(gitHub.accountLink) : gitHub.accountLink != null) return false;
        return owner != null ? owner.getId().equals(gitHub.owner.getId()) : gitHub.owner == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (accountLink != null ? accountLink.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.getId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GitHub{" +
                super.toString() +
                ", account='" + accountLink + '\'' +
                ", owner=" + owner +
                "}";
    }
}
