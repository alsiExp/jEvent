package ru.jevent.repository.mock;

import org.springframework.stereotype.Repository;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Speech;
import ru.jevent.model.additionalEntity.SpeechTag;
import ru.jevent.repository.SpeechRepository;

import java.util.Arrays;
import java.util.List;

@Repository
public class MockSpeechRepositoryImpl implements SpeechRepository {

    private static final LoggerWrapper LOG = LoggerWrapper.get(MockSpeechRepositoryImpl.class);

    private static Speech speech = new Speech();
    static {
        speech.setId(1300L);
        speech.setName("Test speech");
    }

    @Override
    public Speech save(Speech speech) {
        return speech;
    }

    @Override
    public boolean delete(long id) {
        return true;
    }

    @Override
    public Speech get(long id) {
        return speech;
    }

    @Override
    public List<Speech> getByPartner(long id) {
        return Arrays.asList(speech);
    }

    @Override
    public List<SpeechTag> getPossibleTags(long speechId) {
        return null;
    }
}
