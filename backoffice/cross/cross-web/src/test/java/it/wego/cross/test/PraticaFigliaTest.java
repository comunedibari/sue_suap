/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.test;

import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.dto.filters.ComunicazioneFilter;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.service.ClearService;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.webservices.client.clear.stubs.Log;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gabriele
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/test/application-context-test.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class PraticaFigliaTest {

    @Autowired
    ClearService clearService;
    @Autowired
    ProcedimentiDao procedimentiDao;
    @Autowired
    PraticaDao praticaDao;
    @Autowired
    PraticheService praticheService;
    @Autowired
    EntiService entiService;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    @Transactional
    public void creaProcedimentoClearTest() throws Exception {
        Integer idEnte = 800;
        Integer idProc = 1005;

        List<Enti> entiList = entiService.findAll(new ComunicazioneFilter());

    }
}
