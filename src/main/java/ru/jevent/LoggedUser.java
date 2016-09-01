package ru.jevent;

import ru.jevent.model.Role;

import java.util.Collections;
import java.util.Set;

public class LoggedUser {
    protected long id = 0;
    protected Set<Role> roles = Collections.singleton(Role.ROLE_USER);
    protected boolean enabled = true;

    private static LoggedUser LOGGED_USER = new LoggedUser();

    public static LoggedUser get() {
        return LOGGED_USER;
    }

    public static long id() {
        return get().id;
    }

    public Set<Role> getAuthorities() {
        return roles;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
