package ru.jevent.service;

import ru.jevent.model.Partner;
import ru.jevent.util.exception.NotFoundException;

import java.util.List;

public interface PartnerService {

    Partner save(Partner partner);

    void update(Partner partner) throws NotFoundException;

    Partner get(long id) throws NotFoundException;

    void delete(long id) throws NotFoundException;

    List<Partner> getAll();
}
