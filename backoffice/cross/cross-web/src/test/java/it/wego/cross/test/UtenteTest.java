/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.test;

import com.google.common.base.Preconditions;
import it.wego.cross.constants.Constants;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.Utente;
import it.wego.cross.service.ProcedimentiService;
import it.wego.cross.service.UtentiService;
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
public class UtenteTest {

    @Autowired
    private UtentiService utentiService;
    @Autowired
    private ProcedimentiService procedimentiService;

    public UtenteTest() {
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
        Filter filter = new Filter();
        filter.setLimit(20);
        filter.setOffset(0);
        filter.setOrderColumn("idUtente");
        filter.setOrderDirection("asc");
        filter.setUserStatus(Constants.UTENTE_ATTIVO);
        List<Utente> utenti = utentiService.findAll(filter);
        System.out.println("Utenti attivi" + utenti.size());
    }
    
    
    @Test
    public void testSearchAllSuperuserActive(){
        List<Utente> superusers = utentiService.findAllSystemSuperusers();
        Preconditions.checkNotNull(superusers);
        Preconditions.checkArgument(!superusers.isEmpty());
    }
    
//    @Test
//    @Transactional(rollbackFor = Exception.class)
//    public void findProcedimentiByCodEnte() throws Exception {
//        Filter filter = new Filter();
//        filter.setLimit(20);
//        filter.setOffset(0);
//        filter.setOrderColumn("idProcEnte");
//        filter.setOrderDirection("asc"); 
//        List<Procedimenti> procedimenti = procedimentiService.findProcedimentiByCodEntePaginated(19, filter);
//        System.out.println(procedimenti.size());
//    }
}
