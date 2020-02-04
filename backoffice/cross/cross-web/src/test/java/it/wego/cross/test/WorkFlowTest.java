/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.test;

import it.wego.cross.beans.AnagraficaRecapito;
import it.wego.cross.beans.AttoriComunicazione;
import it.wego.cross.dao.AllegatiDao;
import it.wego.cross.dao.AnagraficheDao;
import it.wego.cross.dao.EntiDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.ProcessiDao;
import it.wego.cross.dao.UtentiDao;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.events.comunicazione.bean.ComunicazioneBean;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.WorkFlowService;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author giuseppe
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/test/application-context-test.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class WorkFlowTest {

    @Autowired
    WorkFlowService workflowService;
    @Autowired
    ProcessiDao processiDao;
    @Autowired
    AllegatiDao allegatiDao;
    @Autowired
    AnagraficheDao anagraficheDao;
    @Autowired
    PraticaDao praticaDao;
    @Autowired
    UtentiDao utentiDao;
    @Autowired
    PluginService pluginService;
    @Autowired
    EntiDao entiDao;

    public WorkFlowTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void voidTest() throws Exception {
        String pippo = "";
    }

//    @Test
    @Transactional()
    public void findAvailableEventsTest() throws Exception {
        System.out.println("CHIAMATO TEST findAvailableEvents");

        List<ProcessiEventi> availableEvents = workflowService.findAvailableEvents(142);
        System.out.println("Numero Eventi: " + availableEvents.size());
        for (ProcessiEventi processiEvento : availableEvents) {
            System.out.println("[" + processiEvento.getIdEvento() + "] " + processiEvento.getDesEvento());
        }
    }
//
//    @Test
//    @Transactional(rollbackFor = Exception.class)
//    public void getProcessToUseTest() throws Exception {
//        Processi processo = workflowService.getProcessToUse(2, 1);
//        System.out.println("Per l'ente con ID=2, al procedimento con ID=1, è associato il processo con CODE:" + processo.getCodProcesso());
//    }

    @Ignore
    @Test
    @Transactional(rollbackFor = Exception.class)
    public void gestisciProcessoEventoTest() throws Exception {
        URL url1 = WorkFlowTest.class.getResource("/AdminService.wsdl");
        assert url1 != null : "Impossibile caricare il file per il test di upload";
        File file1 = new File(url1.toURI());
        byte[] bFile1 = new byte[(int) file1.length()];
        FileInputStream fileInputStream1 = new FileInputStream(file1);
        fileInputStream1.read(bFile1);
        fileInputStream1.close();

        URL url2 = WorkFlowTest.class.getResource("/tmp.docx");
        assert url2 != null : "Impossibile caricare il file per il test di upload";
        File file2 = new File(url2.toURI());
        byte[] bFile2 = new byte[(int) file2.length()];
        FileInputStream fileInputStream2 = new FileInputStream(file2);
        fileInputStream2.read(bFile2);
        fileInputStream2.close();

        Allegato allegato1 = new Allegato();
        allegato1.setDescrizione("DESC 01");
        allegato1.setFile(bFile1);
        allegato1.setNomeFile(file1.getName());
        allegato1.setTipoFile("text/plain");

        Allegato allegato2 = new Allegato();
        allegato2.setDescrizione("DESC 01");
        allegato2.setFile(bFile2);
        allegato2.setNomeFile(file2.getName());
        allegato2.setTipoFile("text/plain");

        Integer idPratica = 1;
        Integer idEvento = 3;
        Integer idUtente = 11;

        Pratica pratica = praticaDao.findPratica(idPratica);
        ProcessiEventi eventoProcesso = processiDao.findByIdEvento(idEvento);
        Utente utenteEvento = utentiDao.findUtenteByIdUtente(idUtente);

        AttoriComunicazione destinatari = new AttoriComunicazione();
        Anagrafica a1 = anagraficheDao.findByCodiceFiscale("GRTGPP83C30L483G");
        Anagrafica a2 = anagraficheDao.findByPartitaIva("02274890306");
        AnagraficaRecapito ar1 = new AnagraficaRecapito();
        ar1.setAnagrafica(a1);
        ar1.setRecapito(a1.getAnagraficaRecapitiList().get(0).getIdRecapito());
        AnagraficaRecapito ar2 = new AnagraficaRecapito();
        ar1.setAnagrafica(a2);
        ar1.setRecapito(a2.getAnagraficaRecapitiList().get(0).getIdRecapito());
//        destinatari.addAnagrafica(ar1);
//        destinatari.addAnagrafica(ar2);
        destinatari.addEnte(entiDao.findByIdEnte(1).getIdEnte());
        destinatari.addEnte(entiDao.findByIdEnte(2).getIdEnte());

        ComunicazioneBean cb = new ComunicazioneBean();
        cb.setIdPratica(idPratica);
        cb.setIdEventoProcesso(eventoProcesso.getIdEvento());
        cb.setIdUtente(utenteEvento.getIdUtente());
        cb.setDestinatari(destinatari);
        cb.addAllegato(allegato1);
        cb.addAllegato(allegato2);
//        wfb.addAllegato(allegatiDao.getAllegato(57));
        cb.setVisibilitaCross(Boolean.TRUE);
        cb.setVisibilitaUtente(Boolean.FALSE);
        cb.setNote("NOTE NOTE NOTE");

        workflowService.gestisciProcessoEvento(cb);

        String pippo = "";
    }
//
//    @Test
//    @Transactional(rollbackFor = Exception.class)
//    public void getDestinatariDefaultEventoTest() throws Exception {
//        Integer idPratica = 1;
////        Integer idEvento = 1;
////        Integer idEvento = 2;
//        Integer idEvento = 3;
//
//        Pratica pratica = praticaDao.findPratica(idPratica);
//        ProcessiEventi eventoProcesso = processiDao.findByIdEvento(idEvento);
//        Destinatari destinatariDefaultEvento = workflowService.getDestinatariDefaultEvento(pratica, eventoProcesso, null);
//
//        String pippo = "";
//    }
//
//    @Test
//    @Transactional(rollbackFor = Exception.class)
//    public void findLastEventForIdPraticaIdEventoTest() throws Exception {
//        Integer idPratica = 1;
//        Integer idEvento = 1;
////        Integer idEvento = 2;
////        Integer idEvento = 3;
//
//        Pratica pratica = praticaDao.findPratica(idPratica);
//        ProcessiEventi eventoProcesso = processiDao.findByIdEvento(idEvento);
//
//        PraticheEventi findLastEventForIdPraticaIdEvento = processiDao.findLastEventForPraticaEvento(pratica, eventoProcesso);
//        List<Allegati> allegatiList = findLastEventForIdPraticaIdEvento.getAllegatiList();
//
//        String pippo = "";
//    }
//
//    @Test
//    @Transactional(rollbackFor = Exception.class)
//    public void findLastScadenzaForIdPraticaIdEventoTest() throws Exception {
//        Integer idPratica = 1;
//        Integer idEvento = 1;
////        Integer idEvento = 2;
////        Integer idEvento = 3;
//
//        Pratica pratica = praticaDao.findPratica(idPratica);
//        ProcessiEventi eventoProcesso = processiDao.findByIdEvento(idEvento);
//        Scadenze findLastScadenzaForPraticaEvento = processiDao.findLastScadenzaForPraticaEvento(pratica, eventoProcesso);
//        if (findLastScadenzaForPraticaEvento != null) {
//            Date dataFineScadenza = findLastScadenzaForPraticaEvento.getDataFineScadenza();
//        }
//
//        String pippo = "";
//    }
//
//    @Test
//    @Transactional(rollbackFor = Exception.class)
//    public void getRicevutaTest() throws Exception {
//        Integer idPratica = 1;
//        Integer idEvento = 1;
////        Integer idEvento = 2;
////        Integer idEvento = 3;
//
//        Pratica pratica = praticaDao.findPratica(idPratica);
//        ProcessiEventi eventoProcesso = processiDao.findByIdEvento(idEvento);
//
//        WorkFlowBean wfb = new WorkFlowBean();
//        wfb.setPratica(pratica);
//        wfb.setEventoProcesso(eventoProcesso);
//
//
//        byte[] documento = workflowService.generaDocumento(wfb);
//
////        FileOutputStream fileOuputStream = new FileOutputStream("c:\\output.pdf");
////        fileOuputStream.write(documento);
////        fileOuputStream.close();
//
//        String pippo = "";
//    }
//    @Test
//    @Transactional(rollbackFor = Exception.class)
//    public void gestisciProcessoEventoCondivisioneTest() throws Exception {
//        Integer idPratica = 1;
//        Integer idEvento = 3;
//        Integer idUtente = 5;
//
//        Pratica pratica = praticaDao.findPratica(idPratica);
//        ProcessiEventi eventoProcesso = processiDao.findByIdEvento(idEvento);
//        Utente utenteEvento = utentiDao.findUtenteByIdUtente(idUtente);
//
//        WorkFlowBean wfb = new WorkFlowBean();
//        wfb.setPratica(pratica);
//        wfb.setEventoProcesso(eventoProcesso);
//        wfb.setUtenteEvento(utenteEvento);
//        wfb.setVisibilitaCross(Boolean.TRUE);
//        wfb.setVisibilitaUtente(Boolean.FALSE);
//        wfb.setNote("NOTE NOTE NOTE");
//
//        workflowService.gestisciProcessoEvento(wfb);
//    }
//    @Test
//    @Transactional(rollbackFor = Exception.class)
//    public void testPraticaTree() throws Exception {
//        Integer idPratica = 87;
//        Integer idEvento = 1;
////        Integer idEvento = 2;
////        Integer idEvento = 3;
//
//        Pratica pratica = praticaDao.findPratica(idPratica);
//        ProcessiEventi eventoProcesso = processiDao.findByIdEvento(idEvento);
//        
//        Destinatari destinatari = new Destinatari();
//        destinatari.addAnagrafica(anagraficheDao.findByCodiceFiscale("DVXBCC95A10H503F"));
//        destinatari.addAnagrafica(anagraficheDao.findByPartitaIva("01425360938"));
//        destinatari.addEnte(entiDao.findByIdEnte(1));
//        destinatari.addEnte(entiDao.findByIdEnte(2));
//        destinatari.addEnte(entiDao.findByIdEnte(3));
//        destinatari.addEnte(entiDao.findByIdEnte(4));
//        destinatari.addEnte(entiDao.findByIdEnte(5));
//        destinatari.addEnte(entiDao.findByIdEnte(6));
//
//        WorkFlowBean wfb = new WorkFlowBean();
//        wfb.setPratica(pratica);
//        wfb.setEventoProcesso(eventoProcesso);
//        wfb.setDestinatari(destinatari);
//
//        workflowService.updatePraticheFiglie(wfb);
//
//
//        String pippo = "";
//    }
//    @Test
//    @Transactional(rollbackFor = Exception.class)
//    public void getOggettoMailTest() throws Exception {
//        Log.APP.setLevel(Level.TRACE);
//        
//        Integer idPratica = 58;
//        Integer idEvento = 1;
////        Integer idEvento = 2;
////        Integer idEvento = 3;
//
//        Pratica pratica = praticaDao.findPratica(idPratica);
//        ProcessiEventi eventoProcesso = processiDao.findByIdEvento(idEvento);
//
//        WorkFlowBean wfb = new WorkFlowBean();
//        wfb.setPratica(pratica);
//        wfb.setEventoProcesso(eventoProcesso);
//        
////        String defaultMailObject = workflowService.getDefaultMailObject(wfb);
//        String defaultMailBody = workflowService.getDefaultMailBody(wfb);
//        
//        FileUtils.writeStringToFile(Utils.getTempFile("_res.txt"), defaultMailBody);
//
//        String pippo = "";
//    }
}
