/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.test;

import it.wego.cross.dao.EntiDao;
import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.LkComuni;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author giuseppe
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/test/application-context-test.xml"})
public class EntiTest {

    @Autowired
    private EntiDao entiDao; 
    @Autowired
    private ProcedimentiDao procedimentiDao; 
            
    public EntiTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void findComuniCollegati() throws Exception {
        Filter filter = new Filter();
        filter.setLimit(20);
        filter.setOffset(0);
        filter.setOrderColumn("idComune");
        filter.setOrderDirection("asc");
        List<LkComuni> comuni = entiDao.findComuniCollegati(2, filter);
        System.out.println("Comuni collegati " + comuni.size());
    }
    
    @Test
    @Ignore
    @Transactional(rollbackFor = Exception.class)
    public void countProcedimentiLocalizzati() throws Exception {
        Long count = procedimentiDao.countAllProcedimentiLocalizzati("it", null);;
        System.out.println("Comuni collegati " + count);
    }
}
