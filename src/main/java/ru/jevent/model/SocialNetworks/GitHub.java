package ru.jevent.model.SocialNetworks;

import ru.jevent.model.NamedEntity;
import ru.jevent.model.Visitor;

import javax.persistence.*;

@Entity
@Table(name = "githubaccs")
public class GitHub extends NamedEntity {

    @Column(name = "account")
    String account;
    @OneToOne(fetch= FetchType.LAZY, mappedBy="gitHub")
    Visitor owner;

    public GitHub() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

        GitHub gitHub = (GitHub) o;

        if (account != null ? !account.equals(gitHub.account) : gitHub.account != null) return false;
        return owner != null ? owner.getId().equals(gitHub.owner.getId()) : gitHub.owner == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.getId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GitHub{" +
                super.toString() +
                ", account='" + account + '\'' +
                ", owner=" + owner +
                "}";
    }
}
