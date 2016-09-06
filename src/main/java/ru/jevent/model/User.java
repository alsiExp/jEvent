package ru.jevent.model;

import ru.jevent.model.Enums.Role;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

public class User extends Person {
    private String email;

    //  Length(min = 5)
    private String password;

    private boolean enabled = true;

    private Set<Role> authorities;

    public User() {
    }


    public User(long id, String firstName, String lastName, String email, String password, Role role, Role... roles) {
        super(id, firstName, lastName);
        this.email = email;
        this.password = password;
        this.enabled = true;
        this.authorities = EnumSet.of(role, roles);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void addAuthority(Role authority) {
        if (authorities == null) {
            authorities = EnumSet.of(authority);
        } else {
            authorities.add(authority);
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Collection<Role> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                '}';
    }
}
