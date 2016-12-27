package ru.jevent.web.converter;

import org.springframework.core.convert.converter.Converter;
import ru.jevent.util.TimeUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String param) {
        return TimeUtil.toDateTime(param, DateTimeFormatter.ISO_DATE_TIME);
    }
}
