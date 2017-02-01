package ru.jevent.util.exception;

import ru.jevent.LoggerWrapper;

import javax.persistence.NonUniqueResultException;
import java.util.List;

public class ExceptionUtil {

    private static final LoggerWrapper LOG = LoggerWrapper.get(ExceptionUtil.class);

    public static void check(boolean found, String msg) {
        if(!found) throw LOG.getNotFoundException("Not found Entity with " + msg);
    }

    public static void check(boolean found, long id) {
        check(found, "id = " + id);
    }

    public static <T> T check(T obj, String msg) {
        check(obj != null, msg);
        return obj;
    }

    public static <T> T check(T obj, long id) {
        return check(obj, "id = " + id);
    }

    public static <T> T checkUniqueResult(List<T> result) {
        if(result.size() == 1) {
            return result.get(0);
        } else if(result.size() == 0) {
            return null;
        } else {
            throw new NonUniqueResultException();
        }
    }

}
