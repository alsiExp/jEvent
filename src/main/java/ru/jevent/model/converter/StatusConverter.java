package ru.jevent.model.converter;

import ru.jevent.model.Enums.CurrentTaskStatus;

import javax.persistence.AttributeConverter;

public class StatusConverter implements AttributeConverter<CurrentTaskStatus, Long> {

    @Override
    public Long convertToDatabaseColumn(CurrentTaskStatus status) {
        switch(status) {
            case NEW:
                return 90010L;
            case PAUSED:
                return 90011L;
            case IN_WORK:
                return 90013L;
            case DONE:
                return 90014L;
            case FAILED:
                return 90015L;
            default:
                throw new IllegalArgumentException();
        }

    }

    @Override
    public CurrentTaskStatus convertToEntityAttribute(Long aLong) {

        if(aLong.equals(90010L)) return CurrentTaskStatus.NEW;
        else if(aLong.equals(90011L)) return CurrentTaskStatus.PAUSED;
        else if(aLong.equals(90013L)) return CurrentTaskStatus.IN_WORK;
        else if(aLong.equals(90014L)) return CurrentTaskStatus.DONE;
        else if(aLong.equals(90015L)) return CurrentTaskStatus.FAILED;

        else throw new IllegalArgumentException();
    }
}
