package ru.jevent.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;
import ru.jevent.model.enums.Sex;

public class StringToSexConverter implements Converter<String, Sex> {
    @Override
    public Sex convert(String param) {
        if(param.toLowerCase().equals("male")) {
            return Sex.MALE;
        } else if(param.toLowerCase().equals("female")) {
            return Sex.FEMALE;
        } else if(StringUtils.isEmpty(param)){
            return Sex.MALE;
        } else {
            throw new IllegalStateException("Wrong sex value: " + param);
        }
    }
}
