package ru.jevent.web.Partner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Partner;
import ru.jevent.service.PartnerService;

import java.util.List;

@Component
public class PartnerHelper {
    private static final LoggerWrapper LOG = LoggerWrapper.get(PartnerHelper.class);

    private PartnerService service;

    @Autowired
    public PartnerHelper(PartnerService service) {
        this.service = service;
    }

    public Partner create(Partner partner) {
        LOG.info("create " + partner);
        return service.save(partner);
    }

    public void update(Partner partner) {
        LOG.info("update " + partner);
        service.update(partner);
    }

    public Partner get(long id) {
        LOG.info("get partner " + id);
        return service.get(id);
    }

    public void delete(long id) {
        LOG.info("delete partner " + id);
        service.delete(id);
    }

    public List<Partner> getAll() {
        LOG.info("get all partners");
        return service.getAll();
    }
}
