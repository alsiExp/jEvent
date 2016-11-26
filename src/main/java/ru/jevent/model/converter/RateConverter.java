package ru.jevent.model.converter;

import ru.jevent.model.enums.RateType;

import javax.persistence.AttributeConverter;

public class RateConverter implements AttributeConverter<RateType, Long> {
    @Override
    public Long convertToDatabaseColumn(RateType rate) {
        switch(rate) {
            case ONLINE_LITE:
                return 90030L;
            case ONLINE_STANDARD:
                return 90031L;
            case ONLINE_BUSINESS:
                return 90032L;
            case PERSONAL_LITE:
                return 90033L;
            case PERSONAL_STANDARD:
                return 90034L;
            case PERSONAL_BUSINESS:
                return 90035L;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public RateType convertToEntityAttribute(Long aLong) {
        if(aLong.equals(90030L)) return RateType.ONLINE_LITE;
        else if(aLong.equals(90031L)) return RateType.ONLINE_STANDARD;
        else if(aLong.equals(90032L)) return RateType.ONLINE_BUSINESS;
        else if(aLong.equals(90033L)) return RateType.PERSONAL_LITE;
        else if(aLong.equals(90034L)) return RateType.PERSONAL_STANDARD;
        else if(aLong.equals(90035L)) return RateType.PERSONAL_BUSINESS;

        else throw new IllegalArgumentException();
    }
}
