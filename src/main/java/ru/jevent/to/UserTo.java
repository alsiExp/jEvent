package ru.jevent.to;

import org.hibernate.validator.constraints.NotEmpty;
import ru.jevent.model.User;
import ru.jevent.model.enums.Role;
import ru.jevent.util.HasPassword;

import javax.validation.constraints.Size;

public class UserTo implements HasPassword{
    protected int id;

    @NotEmpty
    protected String name;

    @NotEmpty
    protected String login;

    @Size(min = 5, max = 15, message = " must between 5 and 15 characters")
    protected String password;

    protected boolean enabled;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFullName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void updateUser(User user) {
        user.setFullName(name);
        user.setLogin(login);
        user.setPassword(password);
        user.setEnabled(enabled);
    }

    public User asNewUser() {
        return new User(null, name, enabled, login, password,  Role.ROLE_USER);
    }
}
