package ru.jevent.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;
import ru.jevent.model.additionalEntity.SpeechTag;

import java.util.HashSet;
import java.util.Set;

public class StringToTagSetConverter implements Converter<String[], Set<SpeechTag>> {
    @Override
    public Set<SpeechTag> convert(String[] source) {
        if(!StringUtils.isEmpty(source)) {
            Set<SpeechTag> tags = new HashSet<>();
            for(String s: source){
                int index = s.indexOf('-');
                SpeechTag tag = new SpeechTag();
                Long id = Long.parseLong(s.substring(0, index));
                if(id != 0) {
                    tag.setId(id);
                }
                tag.setTag(s.substring(index + 1));
                tags.add(tag);
            }
            return tags;
        }
         else return null;
    }
}
