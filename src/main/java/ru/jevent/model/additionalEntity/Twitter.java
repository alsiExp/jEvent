package ru.jevent.model.additionalEntity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.util.StringUtils;
import ru.jevent.model.Participant;
import ru.jevent.model.superclasses.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "twitteraccs")
public class Twitter extends BaseEntity {

    private final static String baseURL = "https://twitter.com/";

    @Column(name = "account_link")
    private String account;

    @JsonBackReference
    @OneToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private Participant owner;

    public Twitter() {
    }

    public Twitter(Long id, String account, Participant owner) {
        super(id);
        this.account = account;
        this.owner = owner;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Participant getOwner() {
        return owner;
    }

    public void setOwner(Participant owner) {
        this.owner = owner;
    }

    public String getFullLink() {
        if(!StringUtils.isEmpty(account)) {
            return baseURL + account;
        } else return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Twitter twitter = (Twitter) o;

        if (account != null ? !account.equals(twitter.account) : twitter.account != null) return false;
        return owner != null ? owner.getId().equals(twitter.owner.getId()) : twitter.owner == null;
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
        return "Twitter{" +
                super.toString() +
                ", account='" + account + '\'' +
                "}";
    }
}
