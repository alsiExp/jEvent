package ru.jevent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.jevent.model.Participant;
import ru.jevent.repository.ParticipantRepository;
import ru.jevent.service.ParticipantService;
import ru.jevent.util.exception.ExceptionUtil;
import ru.jevent.util.exception.NotFoundException;

import java.util.List;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    private ParticipantRepository repository;

    @Autowired
    public ParticipantServiceImpl(ParticipantRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "participants", allEntries = true)
    @Override
    public Participant save(Participant participant) {
        return repository.save(participant);
    }

    @CacheEvict(value = "participants", allEntries = true)
    @Override
    public void update(Participant participant) throws NotFoundException {
        ExceptionUtil.check(repository.save(participant), participant.getId());
    }

    @Override
    public Participant get(long id) throws NotFoundException {
        return ExceptionUtil.check(repository.get(id), id);
    }

    @Override
    public Participant getByEmail(String email) {
        return repository.getByEmail(email);
    }

    @CacheEvict(value = "participants", allEntries = true)
    @Override
    public void delete(long id) throws NotFoundException {
        ExceptionUtil.check(repository.delete(id), id);
    }

    @Override
    public List<Participant> getByTag(long tagId) {
        return repository.getByTag(tagId);
    }

    @Cacheable("participants")
    @Override
    public List<Participant> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "participants", allEntries = true)
    @Override
    public void dropCache() {

    }
}
