package ru.jevent.repository.mock;

import org.springframework.stereotype.Repository;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Partner;
import ru.jevent.repository.PartnerRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MockPartnerRepositoryImpl implements PartnerRepository {

    private static final LoggerWrapper LOG = LoggerWrapper.get(MockPartnerRepositoryImpl.class);

    private static Partner partner = new Partner(13, "T-Systems", "email", "+7-812-000-00-00", "Partner description", null);


    public static Partner getPartner() {
        return partner;
    }

    @Override
    public Partner save(Partner partner) {
        return this.partner;
    }

    @Override
    public boolean delete(long id) {
        return id != 0;
    }

    @Override
    public Partner get(long id) {
        if(id == 13) {
            return partner;
        }
        return null;
    }

    @Override
    public List<Partner> getAll() {
        return new ArrayList<Partner>() {{
            add(partner);
        }};
    }
}
