package ru.jevent.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;
import ru.jevent.model.additionalEntity.Twitter;

public class StringToTwitterConverter implements Converter<String, Twitter> {
    @Override
    public Twitter convert(String source) {
        if(!StringUtils.isEmpty(source)){
            Twitter twitter = new Twitter();
            twitter.setAccount(source);
            twitter.setValid(true);
            return twitter;
        }
        return new Twitter();
    }
}
