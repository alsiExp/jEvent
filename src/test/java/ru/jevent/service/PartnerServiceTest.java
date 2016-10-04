package ru.jevent.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.TestData;
import ru.jevent.model.Partner;
import ru.jevent.util.DbPopulator;
import ru.jevent.util.exception.NotFoundException;

import java.util.List;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/service.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
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
                p.getEmail() == null) throw new Exception();
        if(!p.getId().equals(100000L)) throw new Exception();
    }

    @Test
    public void testSave() throws Exception {
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

    @Test
    public void testUpdate() throws Exception {
        Partner partner = testData.getExistingPartner();
        partner.setName("New CompanyTest name");
        service.update(partner);
        Partner savedPartner = service.get(partner.getId());
        if(!savedPartner.equals(partner))
            throw new Exception();
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateWithException() throws Exception {
        Partner p = testData.getExistingPartner();
        p.setName(null);
        try {
            service.update(p);
        }
        catch (DataIntegrityViolationException dve) {
            throw new NotFoundException("DataIntegrityViolationException");
        }
    }

}
