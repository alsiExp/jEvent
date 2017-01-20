package ru.jevent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jevent.model.additionalEntity.SpeechTag;
import ru.jevent.repository.SpeechTagRepository;
import ru.jevent.service.SpeechTagService;
import ru.jevent.util.exception.ExceptionUtil;
import ru.jevent.util.exception.NotFoundException;

import java.util.List;

@Service
public class SpeechTagServiceImpl implements SpeechTagService {
    private final SpeechTagRepository repository;

    @Autowired
    public SpeechTagServiceImpl(SpeechTagRepository repository) {
        this.repository = repository;
    }

    @Override
    public SpeechTag save(SpeechTag tag) {
        return repository.save(tag);
    }

    @Override
    public void update(SpeechTag tag) throws NotFoundException {
        ExceptionUtil.check(repository.save(tag), tag.getId());
    }

    @Override
    public SpeechTag get(long id) throws NotFoundException {
        return ExceptionUtil.check(repository.get(id), id);
    }

    @Override
    public void delete(long id) throws NotFoundException {
        ExceptionUtil.check(repository.delete(id), id);
    }

    @Override
    public List<SpeechTag> getAll() {
        return repository.getAll();
    }
}
