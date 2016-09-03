package ru.jevent.repository;

import ru.jevent.model.Partner;
import java.util.List;


public interface PartnerRepository {

    Partner save(Partner partner);

    boolean delete(long id);

    Partner get(long id);

    List<Partner> getAll();
}
