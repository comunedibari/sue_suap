/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.test;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.protocollo.GestioneProtocollo;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloRequest;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloResponse;
import it.wego.cross.plugins.protocollo.beans.SoggettoProtocollo;
import it.wego.cross.service.PluginService;

/**
 *
 * @author giuseppe
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/test/application-context-test.xml"})
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class ProtocolloTest {

    Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm").setPrettyPrinting().create();
    private static final Logger log = LoggerFactory.getLogger("auriga");

  

    @Autowired
    PluginService pluginService;

    public ProtocolloTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

//    @Ignore
    @Test
    @Ignore
    @Transactional(rollbackFor = Exception.class)
    public void testProtocolloFromJson() throws Exception {
        URI fileUri = ProtocolloTest.class.getClassLoader().getResource("protocollo_genuit.json").toURI();
        String praticaXml = FileUtils.readFileToString(new File(fileUri));
        DocumentoProtocolloRequest documentoProtocollo = gson.fromJson(praticaXml, DocumentoProtocolloRequest.class);

        Allegato allegatoOriginale = documentoProtocollo.getAllegatoOriginale();
        FileUtils.writeByteArrayToFile(new File("c:\\temp\\" + UUID.randomUUID() + "_" + allegatoOriginale.getNomeFile()), allegatoOriginale.getFile());
        List<Allegato> allegati = documentoProtocollo.getAllegati();
        for (Allegato allegato : allegati) {
            FileUtils.writeByteArrayToFile(new File("c:\\temp\\" + UUID.randomUUID() + "_" + allegato.getNomeFile()), allegato.getFile());
        }

        GestioneProtocollo gestioneProtocollo = pluginService.getGestioneProtocollo(null, null);

        DocumentoProtocolloResponse protocollo = gestioneProtocollo.protocolla(documentoProtocollo);
        System.out.println(gson.toJson(protocollo));

        String pippo = "";
    }

    @Test
//    @Ignore
    @Transactional(rollbackFor = Exception.class)
    public void testPraticaTree() throws Exception {

        List<SoggettoProtocollo> soggettiProtocollo = new ArrayList<SoggettoProtocollo>();

        SoggettoProtocollo soggetto01 = new SoggettoProtocollo();
//        soggetto01.setDescrizione("BEA SAS DI TELESFORO MARIO & C.");
//        soggetto01.setCodiceFiscale("00630100071");
//        soggetto01.setPartitaIva("00630100071");
//        soggetto01.setIndirizzo("LOCALITA' CAPOLUOGO");
        soggetto01.setDenominazione("RADIO TELEVISIONE DI CAMPIONE SPA");
        soggetto01.setCodiceFiscale("95007460132");
        soggetto01.setPartitaIva("01651810135");
        soggetto01.setIndirizzo("VIA TOTONE LOCALITA GIOSCIO");
        soggetto01.setMezzo("Raccomandata");

        SoggettoProtocollo soggetto02 = new SoggettoProtocollo();
        soggetto02.setDenominazione("BEA SAS DI TELESFORO MARIO & C.");
        soggetto02.setCodiceFiscale("00630100071");
        soggetto02.setPartitaIva("00630100071");
        soggetto02.setIndirizzo("LOCALITA' CAPOLUOGO");

        soggettiProtocollo.add(soggetto01);
        soggettiProtocollo.add(soggetto02);

        DocumentoProtocolloRequest documentoProtocollo = new DocumentoProtocolloRequest();
        //Anno corrente
        documentoProtocollo.setAnnoFascicolo(Calendar.getInstance().get(Calendar.YEAR));
        //FISSO A SPORTELLO
        documentoProtocollo.setAoo("SPORTELLO");
        //E per documenti in entrata, U per i documenti in uscita
        documentoProtocollo.setDirezione("E");
        //Oggetto della pratica
        documentoProtocollo.setOggetto("Oggetto documento con 1 soggetto");
        //Fisso a PORTALE CROSS
        documentoProtocollo.setSource("PORTALE CROSS");
        //Valori ammessi:
        //DOCARR: Documento generico in arrivo
        //LET: Lettera in uscita
        //UNICO: Procedimento Unico
        documentoProtocollo.setTipoDoc("UNICO");
        //Prendilo da UO Enti
        documentoProtocollo.setUo("ALTA_VALLE");
        //Fisso a 11
        documentoProtocollo.setTitolario("11");
        //Lo prende dal procedimento. In caso di procedimenti multipli prende il procedimento di peso maggiore
        documentoProtocollo.setClassifica("VIII/01");

        documentoProtocollo.setSoggetti(soggettiProtocollo);
        List<Allegato> allegatiTest = getAllegatiTest();
        documentoProtocollo.setAllegatoOriginale(allegatiTest.get(0));
        allegatiTest.remove(0);
        documentoProtocollo.setAllegati(allegatiTest);

        documentoProtocollo.setIdentificativoPratica("GRTGPP83C30L483G-AOO533-4056458");

        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm").setPrettyPrinting().create();
        System.out.println(gson.toJson(documentoProtocollo));

        GestioneProtocollo gestioneProtocollo = pluginService.getGestioneProtocollo(null, null);
        DocumentoProtocolloResponse protocollo = gestioneProtocollo.protocolla(documentoProtocollo);
        System.out.println(gson.toJson(protocollo));

    }

    public List<Allegato> getAllegatiTest() throws Exception {
        URL url1 = ProtocolloTest.class.getResource("/foto.JPG");
        assert url1 != null : "Impossibile caricare il file per il test di upload";
        File file1 = new File(url1.toURI());
        byte[] bFile1 = new byte[(int) file1.length()];
        FileInputStream fileInputStream1 = new FileInputStream(file1);
        fileInputStream1.read(bFile1);
        fileInputStream1.close();

        URL url2 = ProtocolloTest.class.getResource("/image.png");
        assert url2 != null : "Impossibile caricare il file per il test di upload";
        File file2 = new File(url2.toURI());
        byte[] bFile2 = new byte[(int) file2.length()];
        FileInputStream fileInputStream2 = new FileInputStream(file2);
        fileInputStream2.read(bFile2);
        fileInputStream2.close();

        URL url3 = ProtocolloTest.class.getResource("/donkey.png");
        assert url3 != null : "Impossibile caricare il file per il test di upload";
        File file3 = new File(url3.toURI());
        byte[] bFile3 = new byte[(int) file3.length()];
        FileInputStream fileInputStream3 = new FileInputStream(file3);
        fileInputStream3.read(bFile3);
        fileInputStream3.close();

        Allegato allegato1 = new Allegato();
        allegato1.setDescrizione("DESC 01");
        allegato1.setFile(bFile1);
        allegato1.setNomeFile(file1.getName());
        allegato1.setTipoFile("text/plain");

        Allegato allegato2 = new Allegato();
        allegato2.setDescrizione("DESC 02");
        allegato2.setFile(bFile2);
        allegato2.setNomeFile(file2.getName());
        allegato2.setTipoFile("text/plain");

        Allegato allegato3 = new Allegato();
        allegato3.setDescrizione("DESC 03");
        allegato3.setFile(bFile3);
        allegato3.setNomeFile(file3.getName());
        allegato3.setTipoFile("text/plain");

        List<Allegato> allegati = new ArrayList<Allegato>();
        allegati.add(allegato1);
        allegati.add(allegato2);
        allegati.add(allegato3);

        return allegati;
    }
}
