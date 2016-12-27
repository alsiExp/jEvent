package ru.jevent.util;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    public static final DateTimeFormatter DATE_TME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String toString(LocalDateTime ldt) {
        return toString(ldt, DATE_TME_FORMATTER);
    }

    public static String toString(LocalDateTime ldt, DateTimeFormatter formatter) {
        return ldt == null ? "" : ldt.format(formatter);
    }

    public static LocalDateTime toDateTime(String str) {
        return toDateTime(str, DATE_TME_FORMATTER);
    }

    public static LocalDateTime toDateTime(String str, DateTimeFormatter formatter) {
        return StringUtils.isEmpty(str) ? LocalDateTime.now() : LocalDateTime.parse(str, formatter);
    }
}
