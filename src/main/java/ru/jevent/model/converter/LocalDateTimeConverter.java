package ru.jevent.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime ldt) {
        if(ldt == null) {
            return null;
        } else {
            return Timestamp.valueOf(ldt);
        }
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp ts) {
        if(ts == null) {
            return null;
        } else {
            return ts.toLocalDateTime();
        }
    }
}
