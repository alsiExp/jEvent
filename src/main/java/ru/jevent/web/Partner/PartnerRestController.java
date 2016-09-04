package ru.jevent.web.Partner;

import ru.jevent.LoggedUser;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Partner;
import ru.jevent.service.PartnerService;
import ru.jevent.web.Event.EventRestController;

import java.util.List;

public class PartnerRestController {
    private static final LoggerWrapper LOG = LoggerWrapper.get(EventRestController.class);

    private PartnerService service;

    public Partner create(Partner partner) {
        long userId = LoggedUser.id();
        LOG.info("create {} by user {}", partner, userId);
        return service.save(partner);
    }

    public void update(Partner partner) {
        long userId = LoggedUser.id();
        LOG.info("update {} by user {}", partner, userId);
        service.update(partner);
    }

    public Partner get(long id) {
        long userId = LoggedUser.id();
        LOG.info("get partner {} by user {}", id, userId);
        return service.get(id);
    }

    public void delete(long id) {
        long userId = LoggedUser.id();
        LOG.info("delete partner {} by user {}", id, userId);
        service.delete(id);
    }

    public List<Partner> getAll() {
        long userId = LoggedUser.id();
        LOG.info("getAll partner by user {}", userId);
        return service.getAll();
    }
}
