package ru.jevent.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import ru.jevent.model.enums.Role;
import ru.jevent.model.enums.Sex;
import ru.jevent.model.superclasses.Person;

import javax.persistence.*;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "login", name = "unique_login")})
@NamedQueries({
        @NamedQuery(name = "User.delete", query = "DELETE from User u where u.id = :id"),
        @NamedQuery(name = "User.getAllSorted", query = "SELECT u FROM User u LEFT JOIN FETCH u.roles ORDER BY u.id")
})
public class User extends Person {

    public static final String DELETE = "User.delete";
    public static final String ALL_SORTED = "User.getAllSorted";

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false)
    @NotEmpty
    @Length(min = 4)
    private String password;


    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.LAZY)
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
