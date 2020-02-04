/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.test;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import it.wego.cross.plugins.aec.Anagrafica;
import it.wego.cross.utils.Utils;
import it.wego.cross.xml.Anagrafiche;
import java.io.InputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Gabriele
 */
//@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/test/application-context-test.xml"})
// @TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class GroovyTest {

//    @Autowired
//    ClearService clearService;
//    @Autowired
//    ProcedimentiDao procedimentiDao;
//    @Autowired
//    PraticaDao praticaDao;
//    @Autowired
//    PraticheService praticheService;
//    @Autowired
//    EntiService entiService;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
//    @Transactional
    public void testGroovy() throws Exception {
        InputStream anagraficaGroovy = GroovyTest.class.getClassLoader().getResourceAsStream("anagrafica.groovy");
        String scriptAnagraficaGroovy = Utils.convertStreamToString(anagraficaGroovy);

        Anagrafiche anagrafica = new Anagrafiche();
        Anagrafica configuration = new Anagrafica();
        anagrafica.setPec("gabriele.muscas@gmail.com");
        
        GroovyShell gs = new GroovyShell();
        Script script = gs.parse(scriptAnagraficaGroovy);
        Binding binding = new Binding();
        binding.setVariable("anagrafica", anagrafica);
        binding.setVariable("configuration", configuration);
        Object invokeMethod = script.invokeMethod("updateAnagrafica", binding);
        
        String pippo = "";
    }

    
}
