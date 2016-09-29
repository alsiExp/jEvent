package ru.jevent.model;

import ru.jevent.model.Enums.Role;
import ru.jevent.model.Enums.Sex;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class User extends Person {

    private String login;
    //  Length(min = 5)
    private String password;
    private Set<Role> roles;

    public User() {
    }

    public User(String firstName, String lastName, Sex sex, boolean enabled, String photoURL, String login,
                String password) {
        super(firstName, lastName, sex, enabled, photoURL);
        this.login = login;
        this.password = password;
    }

    public User(Long id, String firstName, String lastName, Sex sex, boolean enabled, String photoURL, String login,
                String password) {
        super(firstName, lastName, sex, enabled, photoURL);
        this.id = id;
        this.login = login;
        this.password = password;
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

    public void addRoles(Role authority) {
        if (roles == null) {
            roles = EnumSet.of(authority);
        } else {
            roles.add(authority);
        }
    }

    public Set<Role> getRoles() {
        if(roles == null) {
            roles = new HashSet<>();
        }
        return roles;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (!login.equals(user.login)) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        //return roles != null ? roles.equals(user.roles) : user.roles == null;
        //better way to compare two sets:
        //  they must be not null (use our get)
        //  then use equals from AbstractSet
        if(this.getRoles().size() != user.getRoles().size()) {
            return false;
        }
        if(!isEquals(this.getRoles(), user.getRoles())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder roleSB = new StringBuilder();
        if(!this.getRoles().isEmpty()) {
            String prefix = "";
            roleSB.append('[');
            for (Object o : this.getRoles().toArray()) {
                roleSB.append(prefix);
                prefix = ",";
                roleSB.append((o).toString());
            }
            roleSB.append(']');
        }

        return "User{" +
                super.toString() +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roleSB.toString() +
                "}";
    }
}
