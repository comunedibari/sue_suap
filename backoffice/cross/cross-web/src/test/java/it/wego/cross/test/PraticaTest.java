/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.test;

import it.wego.cross.constants.AnaTipiEvento;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.UtentiDao;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Utente;
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
public class PraticaTest {

    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private UtentiDao utentiDao;

    public PraticaTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Ignore
    public void queryRuoliSuPratica() throws Exception {
        Filter filter = new Filter();
        filter.setLimit(20);
        filter.setOffset(0);
        Utente utente = utentiDao.findUtenteByIdUtente(14);
        filter.setConnectedUser(utente);
        filter.setSearchByUtenteConnesso(Boolean.TRUE);
        List<Pratica> pratiche = praticaDao.findDaAprireFiltrate(filter);
        System.out.println(pratiche.size());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Ignore
    public void praticaHasPastEventTest() throws Exception {
        Integer idPratica = 1;
        String codEvento = "";

        Pratica pratica = praticaDao.findPratica(idPratica);
        Boolean hasPastEvento = praticheService.praticaHasPastEvent(pratica, codEvento);
        System.out.println("hasPastEvento:" + hasPastEvento);
    }
    
       
}
