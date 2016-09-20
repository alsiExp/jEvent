package ru.jevent.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.model.Partner;
import ru.jevent.util.DbPopulator;

import java.util.List;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class PartnerServiceTest {

    @Autowired
    private PartnerService service;
    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Partner p = service.get(100000L);
        System.out.println(p);
    }

    @Test
    public void testSave() throws Exception {
        Partner partner = new Partner("Test partner", "test@email.com", "+7-999-000-00-00", "Test partner description", "testpartner.jpg");
        service.save(partner);
        if(partner.isNew())
            throw new Exception();
        Partner savedPartner = service.get(partner.getId());
        if(!savedPartner.equals(partner))
            throw new Exception();
    }

    @Test
    public void testDeleteAndGetAll() throws Exception {
        service.delete(100001L);
        List<Partner> list = service.getAll();
        for(Partner p : list) {
            if(p.getId().equals(100001L))
                throw new Exception();
        }
    }

}
