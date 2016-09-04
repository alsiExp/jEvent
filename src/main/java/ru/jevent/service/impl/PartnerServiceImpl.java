package ru.jevent.service.impl;

import ru.jevent.model.Partner;
import ru.jevent.repository.PartnerRepository;
import ru.jevent.service.PartnerService;
import ru.jevent.util.exception.ExceptionUtil;
import ru.jevent.util.exception.NotFoundException;

import java.util.List;

public class PartnerServiceImpl implements PartnerService {

    private PartnerRepository repository;

    @Override
    public Partner save(Partner partner) {
        return repository.save(partner);
    }

    @Override
    public void update(Partner partner) throws NotFoundException {
        ExceptionUtil.check(repository.save(partner), partner.getId());
    }

    @Override
    public Partner get(long id) throws NotFoundException {
        return ExceptionUtil.check(repository.get(id), id);
    }

    @Override
    public void delete(long id) throws NotFoundException {
        ExceptionUtil.check(repository.delete(id), id);
    }

    @Override
    public List<Partner> getAll() {
        return repository.getAll();
    }
}
