package ru.jevent.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;
import ru.jevent.model.additionalEntity.Email;

import java.util.HashSet;
import java.util.Set;

public class StringToEmailSetConverter implements Converter<String, Set<Email>> {
    @Override
    public Set<Email> convert(String source) {
        if(!StringUtils.isEmpty(source)) {
            Set<Email> emails = new HashSet<>();
            boolean isFirst = true;
            String separator = "::";
            for (String e : source.split(separator)) {
                Email email = new Email();
                email.setEmail(e);
                if (isFirst) {
                    email.setMain(true);
                    isFirst = false;
                }
                emails.add(email);
            }
            return emails;
        } else return null;
    }
}
