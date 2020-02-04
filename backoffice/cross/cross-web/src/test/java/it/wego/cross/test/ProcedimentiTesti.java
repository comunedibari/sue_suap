/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.test;

import it.wego.cross.constants.AnaTipiEvento;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.dao.UtentiDao;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.Utente;
import it.wego.cross.entity.view.ProcedimentiLocalizzatiView;
import it.wego.cross.service.PraticheService;
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
public class ProcedimentiTesti {

    @Autowired
    private ProcedimentiDao procedimentiDao;
    
    public ProcedimentiTesti() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    //@Ignore
    public void queryProcedimentiLocalizzati() throws Exception {
        Filter filter = new Filter();
        filter.setLimit(20);
        filter.setOffset(0);
        filter.setIdEnte(1);
        filter.setProcedimento("avvio");
        List<ProcedimentiLocalizzatiView> list = procedimentiDao.findAllProcedimentiLocalizzatiPaginated("it", filter);
        System.out.println("******************MONA"+list.size());
    }
       
}
