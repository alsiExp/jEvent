package ru.jevent.model.converter;

import ru.jevent.model.enums.PartnerStatus;

import javax.persistence.AttributeConverter;

public class PartnerStatusConverter implements AttributeConverter<PartnerStatus, Long> {
    @Override
    public Long convertToDatabaseColumn(PartnerStatus partnerStatus) {
        switch(partnerStatus) {
            case GOLD:
                return 90010L;
            case SILVER:
                return 90011L;
            case BRONZE:
                return 90012L;
            case INFO:
                return 90013L;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public PartnerStatus convertToEntityAttribute(Long aLong) {
        if(aLong.equals(90010L)) return PartnerStatus.GOLD;
        else if(aLong.equals(90011L)) return PartnerStatus.SILVER;
        else if(aLong.equals(90012L)) return PartnerStatus.BRONZE;
        else if(aLong.equals(90013L)) return PartnerStatus.INFO;
        else throw new IllegalArgumentException();
    }
}
