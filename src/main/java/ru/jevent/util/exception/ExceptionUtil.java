package ru.jevent.util.exception;

import ru.jevent.LoggerWrapper;


public class ExceptionUtil {

    private static final LoggerWrapper LOG = LoggerWrapper.get(ExceptionUtil.class);

    public static void check(boolean found, String msg) {
        if(!found) throw LOG.getNotFoundException("Not found Entity with " + msg);
    }

    public static void check(boolean found, long id) {
        check(found, "id = " + id);
    }

    public static <T> T check(T obj, String msg) {
        if(obj == null) throw LOG.getNotFoundException("Not found Entity with " + msg);
        return obj;
    }

    public static <T> T check(T obj, long id) {
        return check(obj, "id = " + id);
    }

}
