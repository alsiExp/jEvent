package ru.jevent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public Participant save(Participant participant) {
        return repository.save(participant);
    }

    @Override
    public void update(Participant participant) throws NotFoundException {
        ExceptionUtil.check(repository.save(participant), participant.getId());
    }

    @Override
    public Participant get(long id) throws NotFoundException {
        return ExceptionUtil.check(repository.get(id), id);
    }

    @Override
    public Participant getByEmail(String email) throws NotFoundException {
        return ExceptionUtil.check(repository.getByEmail(email), "email=" + email);
    }

    @Override
    public void delete(long id) throws NotFoundException {
        ExceptionUtil.check(repository.delete(id), id);
    }

    @Override
    public List<Participant> getAll() {
        return repository.getAll();
    }
}
