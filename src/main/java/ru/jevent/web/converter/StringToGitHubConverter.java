package ru.jevent.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;
import ru.jevent.model.additionalEntity.GitHub;

public class StringToGitHubConverter implements Converter<String, GitHub> {
    @Override
    public GitHub convert(String source) {
        if(!StringUtils.isEmpty(source)){
            GitHub gitHub = new GitHub();
            gitHub.setAccount(source);
            return gitHub;
        }
        return null;
    }
}
