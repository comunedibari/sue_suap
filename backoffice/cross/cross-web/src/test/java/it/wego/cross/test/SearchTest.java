/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.test;

import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.UtentiDao;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.LkProvincie;
import it.wego.cross.entity.LkStatoPratica;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Utente;
import java.util.Arrays;
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
public class SearchTest {
    
    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private UtentiDao utentiDao;
    @Autowired
    private PraticaDao praticaDao;


    public SearchTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    @Ignore
    @Transactional(rollbackFor = Exception.class)
    public void findAvailableEvents() throws Exception {
        String descrizione = "mil";
        List<LkProvincie> provincie = lookupDao.findProvinceInfocamereByDescrizione(descrizione);
        System.out.println("Provincie trovate " + provincie.size());
    }
    
     @Test
    @Transactional(rollbackFor = Exception.class)
    public void praticaRicercaTest() throws Exception {
        Integer idPratica = 1;
        String codEvento = "";
        Pratica p = praticaDao.findByProtocollo("1236", 2014);
        LkStatoPratica lk = lookupDao.findLkStatoPraticaByIdStatoPratica(3);
        Filter filter = new Filter();
        filter.setOrderColumn("idPratica");
        filter.setConnectedUser(p.getIdUtente());
       // filter.setAnnoRiferimento(2013);
        filter.setIdTipoSistemaCatastale(1);
        filter.setIdTipoParticella(2);
        filter.setPraticheDaEscludere(Arrays.asList(lk));
          List<Pratica> pratiches = praticaDao.findFiltrate(filter);
        Long pratiche = praticaDao.countFiltrate(filter);
        System.out.println("TESTTT");
        System.out.println(pratiche);
        }
}
