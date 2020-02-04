/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.test;

import it.wego.cross.genova.actions.PluginGenovaAction;
import java.util.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author giuseppe
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/test/application-context-test.xml"})
public class PluginTest {

    @Autowired
    private PluginGenovaAction schedulerAction;

    private static final Logger log = Logger.getLogger("plugin");

    @Test
    public void aggiornamentoDocumenti() {
        log.info("[START] aggiornamentoDocumenti");
        //schedulerAction.aggiornaAllegatiPerAuriga(100, 0, false);
        log.info("[END] aggiornamentoDocumenti");
    }
}
