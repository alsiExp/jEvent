package ru.jevent.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.jevent.model.Partner;
import ru.jevent.repository.PartnerRepository;

import java.util.List;

@Repository
public class JpaPartnerRepositoryImpl implements PartnerRepository {

    @Override
    public Partner save(Partner partner) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Partner get(long id) {
        return null;
    }

    @Override
    public List<Partner> getAll() {
        return null;
    }
}
