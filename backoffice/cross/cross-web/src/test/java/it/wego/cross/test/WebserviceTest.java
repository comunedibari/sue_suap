/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.test;

import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.ProcessiDao;
import it.wego.cross.entity.Configuration;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.service.ConfigurationService;
import java.util.List;
import org.junit.After;
import org.junit.Before;
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
public class WebserviceTest {

    @Autowired
    LookupDao lookupDao;
    @Autowired
//    ConfigurationDao configurationDao;
    ConfigurationService configurationService;
    @Autowired
    PraticaDao praticaDao;
    @Autowired
    ProcessiDao processiDao;
//    @Autowired
//    private EntiService entiService;

    public WebserviceTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void findAvailableEvents() throws Exception {
        String codCatastale = "L205";
        LkComuni comune = lookupDao.findComuneByCodCatastale(codCatastale);
        System.out.println(comune.getDescrizione());
    }

}
