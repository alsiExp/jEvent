package ru.jevent.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.TestData;
import ru.jevent.model.Partner;
import ru.jevent.util.DbPopulator;
import ru.jevent.util.exception.NotFoundException;

import java.util.List;

import static ru.jevent.Profiles.JPA;
import static ru.jevent.Profiles.POSTGRES;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/service.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({POSTGRES, JPA})
public class PartnerServiceTest {

    @Autowired
    private PartnerService service;
    @Autowired
    private DbPopulator dbPopulator;
    @Autowired
    private TestData testData;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Partner p = service.get(100000L);
        if (p.getId() == null ||
                p.getName() == null ||
                p.getContactEmail() == null ||
                p.getSpeechSet().size() != 1 ||
                p.getEventPartners().size() != 2) throw new Exception();
        if(!p.getId().equals(100000L)) throw new Exception();
    }

    @Test
    public void testSimpleSave() throws Exception {
        Partner partner = testData.getNewPartner();
        service.save(partner);
        Partner savedPartner = service.get(partner.getId());
        if(!savedPartner.equals(partner))
            throw new Exception();
    }

    @Test
    public void testDeleteAndGetAll() throws Exception {
        service.delete(100001L);
        List<Partner> list = service.getAll();
        if(list.isEmpty()) throw new Exception();
        for(Partner p : list) {
            if(p.getId().equals(100001L))
                throw new Exception();
        }
    }

    //TODO: partnerOldHash and partnerNewHash are different
    // problem is in the Speech commentList
    @Test
    public void testUpdate() throws Exception {
        Partner partner = service.get(100000L);
        String newName = "New CompanyTest name";
        partner.setName(newName);
        service.update(partner);
        Partner savedPartner = service.get(partner.getId());
        int partnerOldHash = partner.hashCode();
        int partnerNewHash = savedPartner.hashCode();
        boolean eq = savedPartner.equals(partner);
        if(!savedPartner.getName().equals(newName))
            throw new Exception();
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateWithException() throws Exception {
        Partner p =  service.get(100000L);
        p.setName(null);
        try {
            service.update(p);
        }
        catch (DataIntegrityViolationException dve) {
            throw new NotFoundException("DataIntegrityViolationException");
        }
    }



}
