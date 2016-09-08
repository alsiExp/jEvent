package ru.jevent.model;

import ru.jevent.model.Enums.Role;
import ru.jevent.model.Enums.Sex;

import java.util.EnumSet;
import java.util.Set;

public class User extends Person {

    private String login;
    //  Length(min = 5)
    private String password;
    private Set<Role> roles;

    public User() {
    }

    public User(long id, String firstName, String lastName, String login, String password, Role role, Role... roles) {
        super(id, firstName, lastName);
        this.login = login;
        this.password = password;
        this.roles = EnumSet.of(role, roles);
    }

    public User(long id, String firstName, String lastName, Sex sex, String photoURL, String login, String password, Role role, Role... roles) {
        super(id, firstName, lastName, sex, photoURL);
        this.login = login;
        this.password = password;
        this.roles = EnumSet.of(role, roles);
    }

    public User(Long id, String firstName, String lastName, Sex sex, String photoURL, String login, String password, Set<Role> roles) {
        super(id, firstName, lastName, sex, photoURL);
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addAuthority(Role authority) {
        if (roles == null) {
            roles = EnumSet.of(authority);
        } else {
            roles.add(authority);
        }
    }

    public Set<Role> getRoles() {
        return roles;
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
