package ru.jevent.model.converter;

import ru.jevent.model.Enums.SlotType;

import javax.persistence.AttributeConverter;

public class SlotTypeConverter implements AttributeConverter<SlotType, Long> {

    @Override
    public Long convertToDatabaseColumn(SlotType type) {
        switch(type) {
            case CHECK_IN:
                return 90050L;
            case KEYNOTE:
                return 90051L;
            case BREAK:
                return 90053L;
            case LECTURE:
                return 90054L;
            default:
                throw new IllegalArgumentException();
        }

    }

    @Override
    public SlotType convertToEntityAttribute(Long aLong) {
        if(aLong.equals(90050L)) return SlotType.CHECK_IN;
        else if(aLong.equals(90051L)) return SlotType.KEYNOTE;
        else if(aLong.equals(90053L)) return SlotType.BREAK;
        else if(aLong.equals(90054L)) return SlotType.LECTURE;
        else throw new IllegalArgumentException();
    }
}
