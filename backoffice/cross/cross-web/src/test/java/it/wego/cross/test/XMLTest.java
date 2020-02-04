/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.test;

import it.wego.cross.dao.EntiDao;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.UtentiDao;
import it.wego.cross.dto.RecapitoDTO;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Utente;
import it.wego.cross.serializer.PraticheSerializer;
import it.wego.cross.service.PraticheService;
import it.wego.cross.utils.PraticaUtils;
import it.wego.cross.utils.RecapitoUtils;
import java.math.BigInteger;
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
 * @author CS
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/test/application-context-test.xml"})
public class XMLTest {

    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private EntiDao entiDao;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private UtentiDao utentiDao;

    public XMLTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

//    @Test
    @Transactional(rollbackFor = Exception.class)
    public void testModificaLista() throws Exception {
        Integer idPratica = 142;
        Pratica pratica = praticaDao.findPratica(idPratica);
        String xmlPratica = new String(pratica.getIdStaging().getXmlPratica());
        it.wego.cross.xml.Pratica praticaXML = PraticaUtils.getPraticaFromXML(xmlPratica);

        for (it.wego.cross.xml.Recapito recapitoXML : praticaXML.getAnagrafiche().get(0).getAnagrafica().getRecapiti().getRecapito()) {
            RecapitoDTO recapito = new RecapitoDTO();
            recapito.setIdProvincia(1);
            RecapitoUtils.copyRecapito2XML(recapito, recapitoXML, null);
            //recapitoXML.setIdProvincia(BigInteger.ONE);

            String asd = "";
        }
    }

    @Test
    public void voidTest() throws Exception {
        String pippo = "";
    }
}
