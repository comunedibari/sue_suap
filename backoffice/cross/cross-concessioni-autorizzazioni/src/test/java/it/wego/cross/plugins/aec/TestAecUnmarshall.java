/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.aec;

import it.diviana.egov.b109.oggettiCondivisi.IdentificatoreUnivoco;
import it.diviana.egov.b109.oggettiCondivisi.IdentificatorediRichiesta;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.RichiestaDocument;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.RichiestadiConcessioniEAutorizzazioniType;
import static it.wego.cross.plugins.aec.AeCGestionePratica.XPATH_ID_ENTE;
import java.io.File;
import java.io.StringReader;
import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.io.FileUtils;
import org.apache.xmlbeans.XmlException;
import org.junit.Test;
import org.xml.sax.InputSource;

/**
 *
 * @author Gabriele
 */
public class TestAecUnmarshall {

    @Test
    public void testAecUnmarshall() throws Exception {
//        String xmlFile = "aec_aosta.xml";
        String xmlFile = "aec_genova.xml";
        
        String praticaXml = FileUtils.readFileToString(new File(TestAecUnmarshall.class.getClassLoader().getResource(xmlFile).toURI()));
        RichiestaDocument richiesta = RichiestaDocument.Factory.parse(praticaXml);

        InputSource source = new InputSource(new StringReader(praticaXml));
        XPath xpath = XPathFactory.newInstance().newXPath();
        NamespaceContext context = new ConcessioniAutorizzazioniNamespaceContext(
                "cea", "http://gruppoinit.it/b110/ConcessioniEAutorizzazioni/procedimentoUnico",
                "ogg", "http://egov.diviana.it/b109/OggettiCondivisi");
        xpath.setNamespaceContext(context);
        String sportello = xpath.evaluate(XPATH_ID_ENTE, source);
        
        RichiestadiConcessioniEAutorizzazioniType cea = richiesta.getRichiesta();
        IdentificatorediRichiesta identificatorediRichiesta = cea.getIdentificatorediRichiesta();
        IdentificatoreUnivoco identificatoreUnivoco = identificatorediRichiesta.getIdentificatoreUnivoco();
        String codiceIdentificativoOperazione = identificatoreUnivoco.getCodiceIdentificativoOperazione();
        String idPraticaPerFE = codiceIdentificativoOperazione.split("/")[0];

        String pippo = "";
    }
}
