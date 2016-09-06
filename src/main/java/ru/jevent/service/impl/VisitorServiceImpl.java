package ru.jevent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jevent.model.Visitor;
import ru.jevent.repository.VisitorRepository;
import ru.jevent.service.VisitorService;
import ru.jevent.util.exception.ExceptionUtil;
import ru.jevent.util.exception.NotFoundException;

import java.util.List;

@Service
public class VisitorServiceImpl implements VisitorService{

    private VisitorRepository repository;

    @Autowired
    public VisitorServiceImpl(VisitorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Visitor save(Visitor visitor) {
        return repository.save(visitor);
    }

    @Override
    public void update(Visitor visitor) throws NotFoundException {
        ExceptionUtil.check(repository.save(visitor), visitor.getId());
    }

    @Override
    public Visitor get(long id) throws NotFoundException {
        return ExceptionUtil.check(repository.get(id), id);
    }

    @Override
    public void delete(long id) throws NotFoundException {
        ExceptionUtil.check(repository.delete(id), id);
    }

    @Override
    public List<Visitor> getAll() {
        return repository.getAll();
    }
}
