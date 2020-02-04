/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.test;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.AttachmentPart;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import it.wego.cross.client.stub.extractone.WSExtractOne;
import it.wego.cross.client.stub.extractone.WSExtractOneServiceLocator;
import it.wego.cross.client.stub.extractone.WSExtractOneSoapBindingStub;
import it.wego.cross.client.stub.getmetadata.WSGetMetadataUdServiceLocator;
import it.wego.cross.client.stub.getmetadata.WSGetMetadataUdSoapBindingStub;
import it.wego.cross.client.stub.getmetadata.WSGetMetadataUd_PortType;
import it.wego.cross.client.xml.getmetadata.AllegatoUDType;
import it.wego.cross.client.xml.getmetadata.DatiUD;
import it.wego.cross.dao.AllegatiDaAggiornareDao;
import it.wego.cross.genova.actions.PluginGenovaAction;
import it.wego.cross.utils.AurigaUtils;

/**
 *
 * @author Giuseppe
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/test/application-context-test.xml"})
public class AurigaTest {

    @Autowired
    private PluginGenovaAction pluginGenovaAction;
    @Autowired
    private AllegatiDaAggiornareDao allegatiDaAggiornareDao;
    
    
    private static final Logger log = LoggerFactory.getLogger("auriga");
//    AMBIENTE DI TEST
//    private static final String ENDPOINT_RICERCA_METADATA = "http://vm-testdoc:8080/aurigarepository/services/WSGetMetadataUd";
//    private static final String ENDPOINT_RICERCA_DOCUMENTALE_SINGOLA = "http://vm-testdoc:8080/aurigarepository/services/WSExtractOne";
//    private static final String COD_APPLICAZIONE = "egrammata";
//    private static final String COD_ISTANZA = "01";
//    private static final String DOCUMENTALE_USERNAME = "SEAP";
//    private static final String DOCUMENTALE_PASSWORD = "SPORTELLO1";

    private static final String ENDPOINT_RICERCA_METADATA = "http://localhost:8090/aurigarepository/services/WSGetMetadataUd";
    private static final String ENDPOINT_RICERCA_DOCUMENTALE_SINGOLA = "http://localhost:8090/aurigarepository/services/WSExtractOne";
    private static final String COD_APPLICAZIONE = "egrammata";
    private static final String COD_ISTANZA = "01";
    private static final String DOCUMENTALE_USERNAME = "EDILIZIA";
    private static final String DOCUMENTALE_PASSWORD = "PASSWORD1";

    @Test
    public void testFindAllegatiDaAggiornare() throws ParseException{
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = df.parse("01/05/2014");
        Date endDate = df.parse("31/05/2014");
        Long count = allegatiDaAggiornareDao.countPraticheEventiConAllegatiDaAggiornare(startDate, endDate);
        Preconditions.checkArgument(count > 0);
    }
    
    
    public void testAllegati() throws FileNotFoundException {
        List<ProtocolloDaTestare> protocolliDaTestare = getProtocolliDaTestare();
        PrintWriter out = new PrintWriter("C:\\logs\\auriga.log");
        try {
            out.println("###################  Avvio test verifica allegati auriga ######################");
            for (ProtocolloDaTestare toTest : protocolliDaTestare) {
                Integer anno = toTest.getAnnoProtocollo();
                Integer protocollo = toTest.getNumeroProtocollo();
                String registro = toTest.getCodRegistro();
                out.println("Cerco tutti gli allegati con i seguenti estremi: " + registro + "/" + anno + "/" + protocollo);
                DatiUD metadati = getMetadata(registro, String.valueOf(anno), String.valueOf(protocollo), out);
                if (metadati != null) {
                    List<AllegatoUDType> allegatiMetadata = metadati.getAllegatoUD();
                    if (allegatiMetadata != null && !allegatiMetadata.isEmpty()) {
                        out.println("Trovati " + allegatiMetadata.size() + " allegati");
                        for (AllegatoUDType allegatoMetadata : allegatiMetadata) {
                            String fileName = allegatoMetadata.getVersioneElettronica().getNomeFile();
                            out.println("Cerco su Auriga il file con nome " + fileName);
                            boolean isEmpty = isEmpty(anno, protocollo, fileName, out);
                            out.println("Il file è vuoto? " + isEmpty);
                        }
                    } else {
                        out.println("Non ho trovato allegati associati agli estremi indicati");
                    }
                } else {
                    out.println("Non ho trovato i metadati per la ricerca");
                }
                out.println("-----------------------------------------");
            }
        } catch (Exception ex) {
            out.println(ex);
        }
        out.println("###################  Fine test verifica allegati auriga ######################");
        out.close();
    }

    private List<ProtocolloDaTestare> getProtocolliDaTestare() {
        List<String> protocolliDaTestare = Lists.newArrayList(
                "PG/2014/129976",
                "PG/2014/130148",
                "PG/2014/130160",
                "PG/2014/130475",
                "PG/2014/130531",
                "PG/2014/130585",
                "PG/2014/140271",
                "PG/2014/148504",
                "PG/2014/153919",
                "PG/2014/153934",
                "PG/2014/159264",
                "PG/2014/164170",
                "PG/2014/173992",
                "PG/2014/176264",
                "PG/2014/177225",
                "PG/2014/185892",
                "PG/2014/190157",
                "PG/2014/190158",
                "PG/2014/198958",
                "PG/2014/202704",
                "PG/2014/203603",
                "PG/2014/207235",
                "PG/2014/207591",
                "PG/2014/212118",
                "PG/2014/214233",
                "PG/2014/215529",
                "PG/2014/217124",
                "PG/2014/223051",
                "PG/2014/230413",
                "PG/2014/232154",
                "PG/2014/232198",
                "PG/2014/232335",
                "PG/2014/232370",
                "PG/2014/232455",
                "PG/2014/232480",
                "PG/2014/232497",
                "PG/2014/232550",
                "PG/2014/232565",
                "PG/2014/232611",
                "PG/2014/232696",
                "PG/2014/232733",
                "PG/2014/232929");
//        List<String> protocolliDaTestare = Lists.newArrayList("PG/2014/129976");
        List<ProtocolloDaTestare> result = new ArrayList<ProtocolloDaTestare>();
        for (String item : protocolliDaTestare) {
            String[] protocolloSplitted = item.split("/");
            String registro = protocolloSplitted[0];
            String anno = protocolloSplitted[1];
            String numero = protocolloSplitted[2];
            result.add(new ProtocolloDaTestare(Integer.valueOf(anno), Integer.valueOf(numero), registro));
        }
        return result;
    }

    private DatiUD getMetadata(String registro, String anno, String numero, PrintWriter out) throws Exception {
        WSGetMetadataUdServiceLocator locator = new WSGetMetadataUdServiceLocator();
        String addressWsDoc = ENDPOINT_RICERCA_METADATA;
        locator.setWSGetMetadataUdEndpointAddress(addressWsDoc);
        WSGetMetadataUd_PortType port = locator.getWSGetMetadataUd();
        String codiceApplicazione = COD_APPLICAZIONE;
        String codiceIstanza = COD_ISTANZA;
        String username = DOCUMENTALE_USERNAME;
        String password = DOCUMENTALE_PASSWORD;
//        String xml = pluginGenovaAction.getXmlRecuperoMetadati(registro, anno, numero);
        String xml = "";
        log.debug("Auriga:getMetadati: XML da inviare al documentale: \n" + xml);
        String xmlHash = AurigaUtils.hashXML(xml, "");
        log.debug("Auriga:getMetadati:Hash dell'XML da inviare al Documentale: \n" + xmlHash);
        String risultato = port.service(codiceApplicazione, codiceIstanza, username, password, xml, xmlHash);
        risultato = AurigaUtils.decodeXml(risultato);
        log.debug("Auriga:getMetadati:Risposta del documentale: \n" + risultato);
        Object[] allegati = ((WSGetMetadataUdSoapBindingStub) port).getAttachments();
        log.debug("Auriga:getMetadati:Numero di allegati: " + allegati.length);
        if (allegati.length < 1) {
            out.println("Errore: non è stata trovata l'informazione relativa ai metadati");
            return null;
        } else {
            //Il secondo attachment contiene le informazioni sui metadati
            DataHandler allegatoFisico = ((AttachmentPart) allegati[0]).getDataHandler();
            if (allegatoFisico != null) {
                log.info("Auriga:getMetadati: Trovati i metadati");
                InputStream inputStream = allegatoFisico.getInputStream();
                byte[] content = IOUtils.toByteArray(inputStream);
                String metadatiXml = new String(content, Charset.forName("UTF-8"));
                log.info("XML con i metadati:\n" + metadatiXml);
                JAXBContext context = JAXBContext.newInstance(it.wego.cross.client.xml.getmetadata.DatiUD.class
                );
                Unmarshaller um = context.createUnmarshaller();
                DatiUD metadati = (DatiUD) um.unmarshal(new StringReader(metadatiXml));
                return metadati;
            } else {
                out.println("Errore leggendo il file fisico con i metadati");
                return null;
            }
        }
    }

    public boolean isEmpty(Integer annoProtocollo, Integer numeroProtocollo, String fileName, PrintWriter writer) throws Exception {
        String xmlRichiesta = pluginGenovaAction.getXmlPerExtractOne(annoProtocollo, numeroProtocollo, fileName);
        WSExtractOneServiceLocator wsLocator = new WSExtractOneServiceLocator();
        String addressWsDoc = ENDPOINT_RICERCA_DOCUMENTALE_SINGOLA;
        wsLocator.setWSExtractOneEndpointAddress(addressWsDoc);
        WSExtractOne wsExtractOne = wsLocator.getWSExtractOne();
        String codiceApplicazione = COD_APPLICAZIONE;
        String codiceIstanza = COD_ISTANZA;
        String username = DOCUMENTALE_USERNAME;
        String password = DOCUMENTALE_PASSWORD;
        log.debug("Auriga:getFileContent:XML da inviare al Documentale: \n" + xmlRichiesta);
        String xmlHash = AurigaUtils.hashXML(xmlRichiesta, "");
        log.debug("Auriga:getFileContent:Hash dell'XML da inviare al Documentale: \n" + xmlHash);
        String risultato = wsExtractOne.service(codiceApplicazione, codiceIstanza, username, password, xmlRichiesta, xmlHash);
        risultato = AurigaUtils.decodeXml(risultato);
        log.debug("Auriga:getFileContent:Risposta del documentale: \n" + risultato);
        Object[] allegati = ((WSExtractOneSoapBindingStub) wsExtractOne).getAttachments();
        log.debug("Auriga:getFileContent:Numero di allegati: " + allegati.length);
        if (allegati.length == 0) {
            log.debug("Auriga:getFileContent:Numero di allegati: " + allegati.length);
            writer.println("Non ho trovato nessun allegato");
            return true;
        } else {
            //Il secondo attachment è il file fisico da restituire
            DataHandler allegatoFisico = ((AttachmentPart) allegati[1]).getDataHandler();
            if (allegatoFisico != null) {
                log.debug("Auriga:getFileContent: Trovato un allegato fisico");
                InputStream inputStream = allegatoFisico.getInputStream();
                byte[] content = IOUtils.toByteArray(inputStream);
                writer.println(fileName + " size: " + content.length);
                return content.length == 0;
            } else {
                log.error("Auriga:non è stato trovato nessun allegato");
                writer.println("Non ho trovato il file fisico (DataHandler = null)");
                return true;
            }
        }
    }

    public class ProtocolloDaTestare {

        private Integer annoProtocollo;
        private Integer numeroProtocollo;
        private String codRegistro;

        public ProtocolloDaTestare() {
        }

        public ProtocolloDaTestare(Integer annoProtocollo, Integer numeroProtocollo, String codRegistro) {
            this.annoProtocollo = annoProtocollo;
            this.numeroProtocollo = numeroProtocollo;
            this.codRegistro = codRegistro;
        }

        public Integer getAnnoProtocollo() {
            return annoProtocollo;
        }

        public void setAnnoProtocollo(Integer annoProtocollo) {
            this.annoProtocollo = annoProtocollo;
        }

        public Integer getNumeroProtocollo() {
            return numeroProtocollo;
        }

        public void setNumeroProtocollo(Integer numeroProtocollo) {
            this.numeroProtocollo = numeroProtocollo;
        }

        public String getCodRegistro() {
            return codRegistro;
        }

        public void setCodRegistro(String codRegistro) {
            this.codRegistro = codRegistro;
        }

    }
}
