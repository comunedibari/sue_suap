/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.test;

import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.UtentiDao;
import it.wego.cross.service.PraticheService;
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
 * @author Gabriele
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/test/application-context-test.xml"})
public class IntegrationTest {

    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private UtentiDao utentiDao;

    public IntegrationTest() {
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
    }

//    @Test
//    @Transactional(rollbackFor = Exception.class)
//    public void callMyPageTest() throws Exception {
//        GestionePratica gestionePratica = new GestionePratica();
//        String pippo = gestionePratica.notificaSuMyPage("CTTTCN57P51F205W-AA01528-0075177");
//        String gnappo = "";
//    }
}
