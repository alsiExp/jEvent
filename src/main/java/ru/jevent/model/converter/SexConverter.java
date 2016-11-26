package ru.jevent.model.converter;

import ru.jevent.model.enums.Sex;

import javax.persistence.AttributeConverter;

public class SexConverter implements AttributeConverter<Sex, Long> {
    @Override
    public Long convertToDatabaseColumn(Sex sex) {
        switch(sex) {
            case MALE:
                return 90000L;
            case FEMALE:
                return 90001L;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public Sex convertToEntityAttribute(Long aLong) {
        if(aLong.equals(90000L)) return Sex.MALE;
        else if(aLong.equals(90001L)) return Sex.FEMALE;
        else throw new IllegalArgumentException();
    }
}
