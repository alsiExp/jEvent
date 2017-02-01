package ru.jevent.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import ru.jevent.model.enums.Role;
import ru.jevent.model.superclasses.Person;
import ru.jevent.util.HasPassword;

import javax.persistence.*;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "login", name = "unique_login")})
@NamedQueries({
        @NamedQuery(name = "User.delete", query = "DELETE from User u where u.id = :id"),
        @NamedQuery(name = "User.getAllSorted", query = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles ORDER BY u.id"),
        @NamedQuery(name = "User.setJiraValidCredentials", query = "UPDATE User u SET u.jiraValidCredentials = :cred WHERE u.id = :id"),
        @NamedQuery(name = User.BY_LOGIN, query = "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.login=?1")
})
public class User extends Person implements HasPassword {

    public static final String DELETE = "User.delete";
    public static final String ALL_SORTED = "User.getAllSorted";
    public static final String SET_JIRA_CRED = "User.setJiraValidCredentials";
    public static final String BY_LOGIN = "User.getByLogin";

    @Column(name = "login", nullable = false, unique = true)
    @NotEmpty
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

    @Column(name = "jira_login")
    @JsonIgnore
    private String jiraLogin;

    @Column(name = "jira_password")
    @JsonIgnore
    private String jiraPassword;

    @Column(name = "jira_valid_credentials")
    private boolean jiraValidCredentials;

    public User() {
    }

    public User(String fullName, boolean enabled, String photoURL, String login,
                String password) {
        super(fullName, enabled, photoURL);
        this.login = login;
        this.password = password;
    }

    public User(Long id, String fullName, boolean enabled, String photoURL, String login,
                String password) {
        super(fullName, enabled, photoURL);
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public User(Long id, String fullName, boolean enabled,  String login,
                String password, Role role, Role... roles) {
        super(fullName, enabled, "#");
        this.id = id;
        this.login = login;
        this.password = password;
        this.roles = EnumSet.of(role, roles);
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

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        if(roles == null) {
            roles = new HashSet<>();
        }
        return roles;
    }

    public String getJiraLogin() {
        return jiraLogin;
    }

    public void setJiraLogin(String jiraLogin) {
        this.jiraLogin = jiraLogin;
    }

    public String getJiraPassword() {
        return jiraPassword;
    }

    public void setJiraPassword(String jiraPassword) {
        this.jiraPassword = jiraPassword;
    }

    public boolean isJiraValidCredentials() {
        return jiraValidCredentials;
    }

    public void setJiraValidCredentials(boolean jiraValidCredentials) {
        this.jiraValidCredentials = jiraValidCredentials;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (jiraValidCredentials != user.jiraValidCredentials) return false;
        if (!login.equals(user.login)) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (jiraLogin != null ? !jiraLogin.equals(user.jiraLogin) : user.jiraLogin != null) return false;
        if (jiraPassword != null ? !jiraPassword.equals(user.jiraPassword) : user.jiraPassword != null) return false;
        return isEquals(this.getRoles(), user.getRoles());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (jiraLogin != null ? jiraLogin.hashCode() : 0);
        result = 31 * result + (jiraPassword != null ? jiraPassword.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (jiraValidCredentials ? 1 : 0);
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
                ", roles=" + roleSB.toString() +
                "}";
    }
}
