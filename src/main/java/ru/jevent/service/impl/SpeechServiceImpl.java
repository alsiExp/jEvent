package ru.jevent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jevent.model.Speech;
import ru.jevent.repository.SpeechRepository;
import ru.jevent.service.SpeechService;
import ru.jevent.util.exception.ExceptionUtil;
import ru.jevent.util.exception.NotFoundException;

@Service
public class SpeechServiceImpl implements SpeechService{

    private SpeechRepository repository;

    @Autowired
    public SpeechServiceImpl(SpeechRepository repository) {
        this.repository = repository;
    }

    @Override
    public Speech save(Speech speech) {
        return repository.save(speech);
    }

    @Override
    public void update(Speech speech) throws NotFoundException {
        ExceptionUtil.check(repository.save(speech), speech.getId());
    }

    @Override
    public Speech get(long id) throws NotFoundException {
        return ExceptionUtil.check(repository.get(id), id);
    }

    @Override
    public void delete(long id) throws NotFoundException {
        ExceptionUtil.check(repository.delete(id), id);
    }
}
