/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.selenium;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

public class GestioneProcessiTest {

    static final private String TESTFAMILY = "GestioneProcessi";
    static final private String TEST_01 = "Ordinamento_processi";
    static final private String TEST_02 = "Aggiungi_processo";
    static final private String TEST_03 = "Modifica_processo";
    static final private String TEST_04 = "Gestione_eventi_del_processo";
    static final private String TEST_05 = "Aggiungi_evento";
    static final private String TEST_06 = "Inserimento_stato_della_pratica_dopo_evento";
    static final private String TEST_07 = "Inserimento_tipologia_di_mittente_1";
    static final private String TEST_08 = "Inserimento_tipologia_di_destinatario_1";
    static final private String TEST_09 = "Scelta_verso_della_comunicazione_uscita";
    static final private String TEST_10 = "Scelta_consenti_invio_mail_si";
    static final private String TEST_11 = "Scelta_consenti_invio_mail_no";
    static final private String TEST_12 = "Scelta_consenti_caricamento_allegati_nelle_email_no";
    static final private String TEST_13 = "Scelta_consenti_caricamento_allegati_nelle_email_si";
    static final private String TEST_14 = "Inserimento_oggetto_email";
    static final private String TEST_15 = "Inserimento_corpo_email";
    static final private String TEST_16 = "Scelta_permetti_inserimento_e_visualizzazione_di_destinatari_mittenti_no";
    static final private String TEST_17 = "Scelta_permetti_inserimento_e_visualizzazione_di_destinatari_mittenti_si";
    static final private String TEST_18 = "Scelta_limita_i_destinatari_mittenti_solamente_agli_enti_no";
    static final private String TEST_19 = "Scelta_limita_i_destinatari_mittenti_solamente_agli_enti_si";
    static final private String TEST_20 = "Scelta_limita_il_numero_massimo_di_destinantari_1";
    static final private String TEST_21 = "Scelta_effettua_la_protocollazione_no";
    static final private String TEST_22 = "Scelta_effettua_la_protocollazione_si";
    static final private String TEST_23 = "Scelta_visualizza_pulsante_di_download_del_template_no";
    static final private String TEST_24 = "Scelta_visualizza_pulsante_di_download_del_template_si";
    static final private String TEST_25 = "Scelta_carica_solo_allegati_firmati_no";
    static final private String TEST_26 = "Scelta_carica_solo_allegati_firmati_si";
    static final private String TEST_27 = "Scelta_apri_sottopratiche_si";
    static final private String TEST_28 = "Scelta_apri_sottopratiche_no";
    static final private String TEST_29 = "Aggiungi_scadenza_aperta_scadenza_non_visualizzata";
    static final private String TEST_30 = "Aggiungi_scadenza_aperta_scadenza_visualizzata";
    static final private String TEST_31 = "Aggiungi_scadenza_chiusa_scadenza_visualizzata";
    static final private String TEST_32 = "Aggiungi_scadenza_chiusa_scadenza_non_visualizzata";
    static final private String TEST_33 = "Modifica_scadenza";
    
    static private WebDriver driver;
    private static TMess tmess;

    @BeforeClass
    public static void setUp() throws MalformedURLException, FileNotFoundException, UnsupportedEncodingException {
        DesiredCapabilities capability = DesiredCapabilities.firefox();
        System.setProperty(SeleniumConfiguration.CHROMEDRIVER, SeleniumConfiguration.CHROMEDRIVERLOCATION);
        driver = new ChromeDriver();  //for local check
        driver.manage().window().setSize(new Dimension(3000, 3000));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        tmess = new TMess();
        tmess.SetSeleniumOutStreamLog();
        tmess.NuovaFamigliaTest(TESTFAMILY);
        tmess.azione("LOGGING IN");
        SeleniumUtilities.Login(driver, tmess);
        tmess.azione("LOG IN SUCCESS");
    }

    private void GoTo_gestione_processi(WebDriver driver) {
        SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_GESUT_IMPOSTAZIONI_BUTTON_CSSSELECTOR), tmess);
        SeleniumUtilities.safeClick(driver, By.xpath(WebElMap.AP_GESPR_APERTURA_PROCESSI_BUTTON_XPATH), tmess);
    }

    
    @Test
    public void Ordinamento_processi() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_01, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletext = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        List<String> codiceCol = TestUtilities.getCol(tabletext, 1);
        List<String> descriCol = TestUtilities.getCol(tabletext, 2);
        tmess.azione("Recupero l'ordinamento dei codici processo all'apertura della pagina: " + codiceCol.toString());
        tmess.azione("Recupero l'ordinamento delle descrizioni processo all'apertura della pagina: " + descriCol.toString());
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_codProcesso"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletext2 = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        List<String> codiceColOrd = TestUtilities.getCol(tabletext2, 1);
        if (TestUtilities.isSortedAlphabetically(codiceColOrd, true)) {
            tmess.risultati("I processi sono stati ordinati con successo secondo il loro codice.");
        } else {
            tmess.Errori("L'ordinamento secondo codice non ha subito variazioni.");
        }
        tmess.azione("Recupero l'ordinamento dei codici processo in seguito all'ordine crescente: " + codiceColOrd.toString());
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_desProcesso"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletext3 = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        List<String> descriColOrd = TestUtilities.getCol(tabletext3, 2);
        if (TestUtilities.isSortedAlphabetically(descriColOrd, true)) {
            tmess.risultati("I processi sono stati ordinati con successo secondo la loro descrizione.");
        } else {
            tmess.Errori("L'ordinamento secondo descrizione non ha subito variazioni.");
        }
        tmess.azione("Recupero l'ordinamento delle descrizioni processo in seguito all'ordine crescente: " + descriColOrd.toString());
        tmess.conclusione(TEST_01, TESTFAMILY, success);
    }

    
    @Test
    public void Aggiungi_processo() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_02, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletext = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        int val = tabletext.size();
        tmess.Generico("Ci sono " + val + " processi.");
        SeleniumUtilities.safeClick(driver, By.linkText("Aggiungi processo"), tmess);
        String coCodice = "PR";
        String coDescr = "Prova";
        SeleniumUtilities.safeFind(driver, By.id("codice"), tmess).sendKeys(coCodice);
        SeleniumUtilities.safeFind(driver, By.id("descrizione"), tmess).sendKeys(coDescr);
        SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
        tmess.azione("aggiungo un nuovo processo.");
        tabletext = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        tmess.Generico("Ci sono " + tabletext.size() + " processi.");
        if (tabletext.size() != (val + 1)) {
            tmess.Errori("Non e' stata aggiunto nessun processo!");
            tmess.conclusione(TEST_02, TESTFAMILY, false);
            return;
        } else {
            tmess.azione("E' stato aggiunto un processo");
            tabletext = SeleniumUtilities.getTable(driver, "list", "title", tmess);
            List<String> codeCol = TestUtilities.getCol(tabletext, 1);
            if (codeCol.contains(coCodice)) {
                tmess.Generico("il processo aggiunto e' corretto");
            } else {
                tmess.Errori("il processo aggiunto e' errato!");
                tmess.conclusione(TEST_02, TESTFAMILY, false);
                return;
            }
        }
        tmess.conclusione(TEST_02, TESTFAMILY, success);
    }

    
    @Test
    public void Modifica_processo() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_03, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletext = SeleniumUtilities.getTable(driver, "list", "title", tmess);

        Random generatorEnte = new Random();
        Integer m = generatorEnte.nextInt(tabletext.size());
        List<WebElement> actionCol = driver.findElements(By.className("modifica_ente"));
        Integer a = 0;
        for (int i = m; i < actionCol.size(); i++) {
            tmess.azione("premo il processo numero: " + m);
            actionCol.get(m).click();
            a = i;
            break;
        }
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<WebElement> boxDati = driver.findElements(By.className("inlineLabels"));
        List<String> bloccoLettura = new ArrayList<String>();
        for (int x = 0; x < boxDati.size(); x++) {
            List<List<String>> allDat = SeleniumUtilities.readLabelScadEven(boxDati.get(x));
            List<String> allDatas = allDat.get(1);
            bloccoLettura.addAll(allDatas); //risultati
        }
        String codiceLabel = bloccoLettura.get(1);
        String descrLabel = bloccoLettura.get(2);
        tmess.azione("I dati del processo sono, codice: " + codiceLabel + ", descrizione: " + descrLabel);

//      aggiungo il valore 1 su tutti i campi
        List<WebElement> areaInput = driver.findElements(By.className("textInput"));
        for (int i = 0; i < areaInput.size(); i++) {
            areaInput.get(i).sendKeys("1");
        }
//      tasto salva
        WebElement boxButtons = SeleniumUtilities.safeFind(driver, By.className("buttonHolder"), tmess);
        List<WebElement> twoButtons = boxButtons.findElements(By.tagName("button"));
        WebElement btnSave = twoButtons.get(1);
        btnSave.click();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        //controllo immissione dati.
        tabletext = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        String codiceResult = TestUtilities.getCol(tabletext, 1).get(a);
        String descrResult = TestUtilities.getCol(tabletext, 2).get(a);
        tmess.azione("I dati modificati del processo sono, codice: " + codiceResult + ", descrizione: " + descrResult);
        if (codiceResult.equals(codiceLabel + "1")
                && descrResult.equals(descrLabel + "1")) {
            tmess.risultati("I dati del processo sono stati modificati correttamente.");
        } else {
            tmess.Errori("I dati del processo non hanno subito variazioni, o hanno subito modifiche inaspettate.");
            //gli accenti presenti nella descrizione del processo causano le modifiche inaspettate.
        }

        actionCol = driver.findElements(By.className("modifica_ente"));
        actionCol.get(a).click();

        //TMess.azione(bloccoLettura.toString());
        tmess.azione("Controllo completato, reinserisco i dati originali.");

        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        areaInput = driver.findElements(By.className("textInput"));
        for (int i = 0; i < areaInput.size(); i++) {
            areaInput.get(i).clear();
            areaInput.get(i).sendKeys(bloccoLettura.get(i + 1));
        }
        boxButtons = SeleniumUtilities.safeFind(driver, By.className("buttonHolder"), tmess);
        twoButtons = boxButtons.findElements(By.tagName("button"));
        btnSave = twoButtons.get(1);
        btnSave.click();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tmess.conclusione(TEST_03, TESTFAMILY, success);
    }

    
    @Test
    public void Gestione_eventi_del_processo() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_04, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletext = SeleniumUtilities.getTable(driver, "list", "title", tmess);

        Random generatorEnte = new Random();
        Integer m = generatorEnte.nextInt(tabletext.size());
        List<WebElement> actionCol = driver.findElements(By.className("gestione_eventi"));
        for (int i = m; i < actionCol.size(); i++) {
            tmess.azione("premo la gestione eventi del processo numero: " + m);
            actionCol.get(m).click();
            break;
        }
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        WebElement pathFinder = SeleniumUtilities.safeFind(driver, By.className("path"), tmess);
        if (pathFinder.getText().contains("/ Gestione flow eventi")) {
            tmess.azione("trovato il flow eventi.");
        } else {
            tmess.azione("la posizione raggiunta dopo il click del tasto non è corretta.");
        }
        tabletext = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        if (tabletext.isEmpty()) {
            tmess.warning("Attenzione, il processo selezionato e' ex novo o non contiene alcun evento.");
        }

        tmess.conclusione(TEST_04, TESTFAMILY, success);
    }

    
    @Test
    public void Aggiungi_evento() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_05, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletext = readTableElementsInPages();

        Random generatorEnte = new Random();
        Integer m = generatorEnte.nextInt(tabletext.size());
        List<WebElement> actionCol = driver.findElements(By.className("gestione_eventi"));
        for (int i = m; i < actionCol.size(); i++) {
            tmess.azione("premo la gestione eventi del processo numero: " + m);
            actionCol.get(m).click();
            break;
        }
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tabletext = readTableElementsInPages();
        int val = tabletext.size();
        tmess.Generico("Ci sono " + tabletext.size() + " eventi.");
        tmess.azione("aggiungo un nuovo evento.");
        SeleniumUtilities.safeClick(driver, By.linkText("Aggiungi evento"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        String codeTest = "codice_test";

//        //metodo automatico, da rivedere...
//        WebElement frameB = driver.findElement(By.className("frames"));
//        WebElement frame = frameB.findElement(By.id("frame1"));
//        WebElement boxDati = frame.findElement(By.className("inlineLabels"));
//        List<WebElement> ctrlHolder = boxDati.findElements(By.className("ctrlHolder"));  
//        WebElement selector = SeleniumUtilities.safeFind(driver, By.tagName("select"));
//        
//        for (int f = 0; f < ctrlHolder.size(); f++) {
//               //modifica 1 solo campo.
//                new Select(selector).selectByIndex(1);               
//            }
        //metodo funzionante.   
        SeleniumUtilities.safeFind(driver, By.id("codice"), tmess).sendKeys(codeTest);
        SeleniumUtilities.safeFind(driver, By.name("descrizioneEvento"), tmess).sendKeys("descriz_test");
        new Select(SeleniumUtilities.safeFind(driver, By.id("statoPost"), tmess)).selectByIndex(1);
        new Select(SeleniumUtilities.safeFind(driver, By.id("tipoMittente"), tmess)).selectByIndex(1);
        new Select(SeleniumUtilities.safeFind(driver, By.id("tipoDestinatario"), tmess)).selectByIndex(1);
        SeleniumUtilities.safeFind(driver, By.id("scriptScadenzaEvento"), tmess).sendKeys("script_test");
        new Select(SeleniumUtilities.safeFind(driver, By.id("verso"), tmess)).selectByIndex(1);
        SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

//        controllo Numero di eventi.
        List<List<String>> tabletext1 = readTableElementsInPages();
        tmess.Generico("Ci sono " + tabletext1.size() + " eventi." + val);

        if (tabletext1.size() != (val + 1)) {
            tmess.Errori("Non e' stato aggiunto nessun evento!");
            tmess.conclusione(TEST_02, TESTFAMILY, false);
            return;
        } else {
            tmess.azione("E' stato aggiunto un nuovo evento.");
            tabletext = SeleniumUtilities.getTable(driver, "list", "title", tmess);
            List<String> codeCol = TestUtilities.getCol(tabletext, 1);
            if (codeCol.contains(codeTest)) {
                tmess.Generico("l'evento aggiunto e' corretto");
            } else {
                tmess.Errori("l'evento aggiunto e' errato!");
                tmess.conclusione(TEST_02, TESTFAMILY, false);
                return;
            }
        }
        tmess.conclusione(TEST_05, TESTFAMILY, success);
    }

    
    @Test
    public void Inserimento_stato_della_pratica_dopo_evento() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_06, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        testAddSelectors(By.id("statoPost"));

        tmess.conclusione(TEST_06, TESTFAMILY, success);
    }

   
    @Test
    public void Inserimento_tipologia_di_mittente_1() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_07, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        testAddSelectors(By.id("tipoMittente"));

        tmess.conclusione(TEST_07, TESTFAMILY, success);
    }

    
    @Test
    public void Inserimento_tipologia_di_destinatario_1() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_08, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        testAddSelectors(By.id("tipoDestinatario"));
        tmess.conclusione(TEST_08, TESTFAMILY, success);
    }

    
    @Test
    public void Scelta_verso_della_comunicazione_uscita() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_09, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        testAddSelectors(By.id("verso"));
        tmess.conclusione(TEST_09, TESTFAMILY, success);
    }

    
    @Test
    public void Scelta_consenti_invio_mail_si() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_10, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        testRadioButton(1, 13, By.id("flgMail2"), By.id("flgMail1"), true);
//        aggiungiEvento();
//        String codData = "test_codice";
//        SeleniumUtilities.safeFind(driver, By.id("codice")).sendKeys(codData);
//        changeTab(1);
//        SeleniumUtilities.safeClick(driver, By.id("flgMail1"));
//        WebElement radioBtnSi = SeleniumUtilities.safeFind(driver, By.id("flgMail1"));
//        if (radioBtnSi.isSelected()) {
//            TMess.Generico("Il bottone SI e' selezionato.");
//        }
//        TMess.azione("Eseguo nuovamente la ricerca dell'Evento.");
//        SeleniumUtilities.safeClick(driver, By.className("primaryAction"));
//        sameEventLoop(codData);
//        changeTab(1);
//        radioBtnSi = SeleniumUtilities.safeFind(driver, By.id("flgMail1"));
//        if (radioBtnSi.isSelected()) {
//            TMess.Generico("Il bottone SI e' selezionato.");
//        }
//        cancellaEvento();
        tmess.conclusione(TEST_10, TESTFAMILY, success);
    }

    
    @Test
    public void Scelta_consenti_invio_mail_no() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_11, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        testRadioButton(1, 13, By.id("flgMail2"), By.id("flgMail1"), false);
        tmess.conclusione(TEST_11, TESTFAMILY, success);
    }

    
    @Test
    public void Scelta_consenti_caricamento_allegati_nelle_email_no() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_12, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        testRadioButton(1, 14, By.id("flgAllegatiEmail2"), By.id("flgAllegatiEmail1"), false);
        tmess.conclusione(TEST_12, TESTFAMILY, success);
    }

    
    @Test
    public void Scelta_consenti_caricamento_allegati_nelle_email_si() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_13, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        testRadioButton(1, 14, By.id("flgAllegatiEmail2"), By.id("flgAllegatiEmail1"), true);
        tmess.conclusione(TEST_13, TESTFAMILY, success);
    }

    
    @Test
    public void Inserimento_oggetto_email() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_14, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        aggiungiEvento();
        String codData = "test_codice";
        SeleniumUtilities.safeFind(driver, By.id("codice"), tmess).sendKeys(codData);
        changeTab(1);
        itemLabel(15);
        String mailObj = "oggetto_email";
        tmess.Generico("Inserisco l'oggetto della email: " + mailObj);
        SeleniumUtilities.safeFind(driver, By.id("oggettoMail"), tmess).sendKeys(mailObj);
        SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        sameEventLoop(codData);
        changeTab(1);
        itemLabel(15);
        WebElement rdOggetto = SeleniumUtilities.safeFind(driver, By.id("oggettoMail"), tmess);
        tmess.azione("Il contenuto dell'oggetto e': " + rdOggetto.getText());
        if (rdOggetto.getText().equals(mailObj)) {
            tmess.Generico("Il contenuto dell'oggetto della email e' stato salvato correttamente.");
        }
        cancellaEvento();
        tmess.conclusione(TEST_14, TESTFAMILY, success);
    }

    
    @Test
    public void Inserimento_corpo_email() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_15, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        aggiungiEvento();
        String codData = "test_codice";
        SeleniumUtilities.safeFind(driver, By.id("codice"), tmess).sendKeys(codData);
        changeTab(1);
        itemLabel(16);
        String mailObj = "corpo_email";
        tmess.Generico("Inserisco l'oggetto della email: " + mailObj);
        SeleniumUtilities.safeFind(driver, By.id("corpoMail"), tmess).sendKeys(mailObj);
        SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        sameEventLoop(codData);
        changeTab(1);
        itemLabel(16);
        WebElement rdOggetto = SeleniumUtilities.safeFind(driver, By.id("corpoMail"), tmess);
        tmess.azione("Il contenuto dell'oggetto e': " + rdOggetto.getText());
        if (rdOggetto.getText().equals(mailObj)) {
            tmess.Generico("Il contenuto dell'oggetto della email e' stato salvato correttamente.");
        }
        cancellaEvento();
        tmess.conclusione(TEST_15, TESTFAMILY, success);
    }

    
    @Test
    public void Scelta_permetti_inserimento_e_visualizzazione_di_destinatari_mittenti_no() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_16, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        testRadioButton(1, 17, By.id("flgDestinatari2"), By.id("flgDestinatari1"), false);
        tmess.conclusione(TEST_16, TESTFAMILY, success);
    }

    
    @Test
    public void Scelta_permetti_inserimento_e_visualizzazione_di_destinatari_mittenti_si() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_17, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        testRadioButton(1, 17, By.id("flgDestinatari2"), By.id("flgDestinatari1"), true);
        tmess.conclusione(TEST_17, TESTFAMILY, success);
    }

    
    @Test
    public void Scelta_limita_i_destinatari_mittenti_solamente_agli_enti_no() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_18, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        testRadioButton(1, 18, By.id("flgDestinatariSoloEnti2"), By.id("flgDestinatariSoloEnti1"), false);
        tmess.conclusione(TEST_18, TESTFAMILY, success);
    }

    
    @Test
    public void Scelta_limita_i_destinatari_mittenti_solamente_agli_enti_si() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_19, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        testRadioButton(1, 18, By.id("flgDestinatariSoloEnti2"), By.id("flgDestinatariSoloEnti1"), true);
        tmess.conclusione(TEST_19, TESTFAMILY, success);
    }

    
    @Test
    public void Scelta_limita_il_numero_massimo_di_destinantari_1() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_20, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        aggiungiEvento();
        String codData = "test_codice";
        SeleniumUtilities.safeFind(driver, By.id("codice"), tmess).sendKeys(codData);
        changeTab(1);
        itemLabel(19);
        List<String> valori = new ArrayList<String>();
        String nMaxDes = "2";
        valori.add(nMaxDes);
        String nMaxDes2 = "10";
        valori.add(nMaxDes2);
        for (int i = 0; i < valori.size(); i++) {
            tmess.Generico("Inserisco il numero massimo di destinatari: " + valori.get(i));
            SeleniumUtilities.safeFind(driver, By.id("maxDestinatari"), tmess).clear();
            SeleniumUtilities.safeFind(driver, By.id("maxDestinatari"), tmess).sendKeys(valori.get(i));
            SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            sameEventLoop(codData);
            changeTab(1);
            itemLabel(19);
            WebElement rdOggetto = SeleniumUtilities.safeFind(driver, By.id("maxDestinatari"), tmess);
            String valueObj = rdOggetto.getAttribute("value");
            tmess.azione("Il numero massimo di destinatari e': " + valueObj);
            if (valueObj.equals(valori.get(i))) {
                tmess.Generico("Il valore massimo di destinatari e' stato salvato correttamente.");
            } else {
                tmess.Errori("Il valore massimo di destinatari non corrisponde a quello immesso.");
                break;
            }
        }
        cancellaEvento();
        tmess.conclusione(TEST_20, TESTFAMILY, success);
    }

    
    @Test
    public void Scelta_effettua_la_protocollazione_no() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_21, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        testRadioButton(2, 21, By.id("flgProtocollazione2"), By.id("flgProtocollazione1"), false);
        tmess.conclusione(TEST_21, TESTFAMILY, success);
    }

    
    @Test
    public void Scelta_effettua_la_protocollazione_si() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_22, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        testRadioButton(2, 21, By.id("flgProtocollazione2"), By.id("flgProtocollazione1"), true);
        tmess.conclusione(TEST_22, TESTFAMILY, success);
    }

    
    @Test
    public void Scelta_visualizza_pulsante_di_download_del_template_no() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_23, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        testRadioButton(2, 22, By.id("flgRicevuta2"), By.id("flgRicevuta1"), false);
        tmess.conclusione(TEST_23, TESTFAMILY, success);
    }

    
    @Test
    public void Scelta_visualizza_pulsante_di_download_del_template_si() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_24, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        testRadioButton(2, 22, By.id("flgRicevuta2"), By.id("flgRicevuta1"), true);
        tmess.conclusione(TEST_24, TESTFAMILY, success);
    }

    
    @Test
    public void Scelta_carica_solo_allegati_firmati_no() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_25, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        testRadioButton(2, 23, By.id("flgFirmato2"), By.id("flgFirmato1"), false);
        tmess.conclusione(TEST_25, TESTFAMILY, success);
    }

    
    @Test
    public void Scelta_carica_solo_allegati_firmati_si() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_26, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        testRadioButton(2, 23, By.id("flgFirmato2"), By.id("flgFirmato1"), true);
        tmess.conclusione(TEST_26, TESTFAMILY, success);
    }

    
    @Test
    public void Scelta_apri_sottopratiche_si() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_27, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        testRadioButton(2, 24, By.id("flgApriSottoPratica2"), By.id("flgApriSottoPratica1"), true);
        tmess.conclusione(TEST_27, TESTFAMILY, success);
    }

    
    @Test
    public void Scelta_apri_sottopratiche_no() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_28, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        testRadioButton(2, 24, By.id("flgApriSottoPratica2"), By.id("flgApriSottoPratica1"), false);
        tmess.conclusione(TEST_28, TESTFAMILY, success);
    }

    
    @Test
    public void Aggiungi_scadenza_aperta_scadenza_non_visualizzata() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_29, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        testSelectorScadenze(true, false);
        tmess.conclusione(TEST_29, TESTFAMILY, success);
    }

    
    @Test
    public void Aggiungi_scadenza_aperta_scadenza_visualizzata() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_30, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        testSelectorScadenze(true, true);
        tmess.conclusione(TEST_30, TESTFAMILY, success);
    }

    
    @Test
    public void Aggiungi_scadenza_chiusa_scadenza_visualizzata() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_31, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        testSelectorScadenze(false, true);
        tmess.conclusione(TEST_31, TESTFAMILY, success);
    }

    
    @Test
    public void Aggiungi_scadenza_chiusa_scadenza_non_visualizzata() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_32, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        testSelectorScadenze(false, false);
        tmess.conclusione(TEST_32, TESTFAMILY, success);
    }

    
    @Test
    public void Modifica_scadenza() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_33, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        List<String> saved = new ArrayList<String>();
        List<String> current = new ArrayList<String>();
        List<List<String>> tabletext = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        Random generatorEnte = new Random();
        Integer m = generatorEnte.nextInt(tabletext.size());
        List<WebElement> actionCol = driver.findElements(By.className("gestione_eventi"));
        for (int i = m; i < actionCol.size(); i++) {
            tmess.azione("Premo la gestione eventi del processo numero: " + m);
            actionCol.get(m).click();
            break;
        }
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        int nEvento = 0;

        List<WebElement> eventCol = driver.findElements(By.className("modifica_ente"));
        boolean trovatoElemento = false;
        int sizeLoop = eventCol.size();
        //TMess.azione(""+sizeLoop);
        for (int s = 0; s < sizeLoop - 1; s++) {
            //TMess.azione(eventCol.toString());
            nEvento = s;
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            eventCol = driver.findElements(By.className("modifica_ente"));
            tmess.azione("Premo l'evento numero: " + s);
            SeleniumUtilities.safeClick2(driver, eventCol.get(s), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            changeTab(3);

            List<String> deleteLinks = SeleniumUtilities.changeListToString(driver.findElements(By.id("scadenza_0")));

            if (!deleteLinks.isEmpty()) {
                s = eventCol.size() + 1;
                trovatoElemento = true;

            } else {
                tmess.warning("Non sono stati trovati dati riguardanti la scadenza, cerco un'altra pratica.");
                SeleniumUtilities.safeClick(driver, By.className("secondaryAction"), tmess);
                SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            }
        }
        if (trovatoElemento) {
            //PUNTO PAGINA APERTA CON DATI
            List<List<WebElement>> listOptions = new ArrayList<List<WebElement>>();
            //RECUPERO DATI SELECTORS
            List<WebElement> selector = driver.findElements(By.tagName("select"));
            List<WebElement> optionsTipo = selector.get(4).findElements(By.tagName("option"));
            listOptions.add(optionsTipo);
            List<WebElement> optionsStato = selector.get(5).findElements(By.tagName("option"));
            listOptions.add(optionsStato);
            List<WebElement> optionsVisual = selector.get(6).findElements(By.tagName("option"));
            listOptions.add(optionsVisual);
            for (int i = 0; i < listOptions.size(); i++) {
                for (int u = 0; u < listOptions.get(i).size(); u++) {
                    if (listOptions.get(i).get(u).isSelected()) {
                        saved.add(listOptions.get(i).get(u).getText());
                    }
                }
            }
            tmess.Generico("Registro i dati gia' presenti all'interno dell'evento.");
            tmess.azione("Il valore originale del campo Tipologia e': " + saved.get(0));
            tmess.azione("Il valore originale del campo Stato Scadenza e': " + saved.get(1));
            tmess.azione("Il valore originale del campo Visualizza Scadenza e': " + saved.get(2));
            //TMess.azione(saved.toString());
            //RECUPERO DATI TERMINE
            WebElement terminiScadenza = SeleniumUtilities.safeFind(driver, By.id("termini"), tmess);
            String datoTermScad = terminiScadenza.getAttribute("value");
            tmess.azione("Il valore originale del campo Termini Scadenza e': " + datoTermScad);
            //Inserimento dati opposti, presi da una lista di elementi che non sono selezionati inizialmente.
            List<WebElement> selectableTipo = new ArrayList<WebElement>();
            for (int t = 0; t < optionsTipo.size(); t++) {
                if (!optionsTipo.get(t).isSelected()) {
                    selectableTipo.add(optionsTipo.get(t));
                    new Select(SeleniumUtilities.safeFind(driver, By.id("tipologiaScadenza"), tmess)).selectByVisibleText(selectableTipo.get(0).getText());
                }
            }
            List<WebElement> selectableStato = new ArrayList<WebElement>();
            for (int t = 0; t < optionsStato.size(); t++) {
                if (!optionsStato.get(t).isSelected()) {
                    selectableStato.add(optionsStato.get(t));
                    new Select(SeleniumUtilities.safeFind(driver, By.id("scadenze0.idStatoScadenza"), tmess)).selectByVisibleText(selectableStato.get(0).getText());
                }
            }
            List<WebElement> selectableVisual = new ArrayList<WebElement>();
            for (int t = 0; t < optionsVisual.size(); t++) {
                if (!optionsVisual.get(t).isSelected()) {
                    selectableVisual.add(optionsVisual.get(t));
                    new Select(SeleniumUtilities.safeFind(driver, By.id("visualizzaScadenza"), tmess)).selectByVisibleText(selectableVisual.get(0).getText());
                }
            }
            //cambiare il dato termine
            SeleniumUtilities.safeFind(driver, By.id("termini"), tmess).clear();
            String newDatoTermineScad = "20";
            SeleniumUtilities.safeFind(driver, By.id("termini"), tmess).sendKeys(newDatoTermineScad);
            //salvare e rientrare nella pratica
            SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            eventCol = driver.findElements(By.className("modifica_ente"));
            tmess.azione("Eseguo una nuova ricerca.");
            SeleniumUtilities.safeClick2(driver, eventCol.get(nEvento), tmess);
            tmess.azione("Premo l'evento numero: " + nEvento);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            //leggere i dati correnti e controllare che siano modificati
            changeTab(3);
            List<List<WebElement>> listOptionsCheck = new ArrayList<List<WebElement>>();
            //RECUPERO DATI CORRENTI SELECTORS
            selector = driver.findElements(By.tagName("select"));
            optionsTipo = selector.get(4).findElements(By.tagName("option"));
            listOptionsCheck.add(optionsTipo);
            optionsStato = selector.get(5).findElements(By.tagName("option"));
            listOptionsCheck.add(optionsStato);
            optionsVisual = selector.get(6).findElements(By.tagName("option"));
            listOptionsCheck.add(optionsVisual);
            for (int i = 0; i < listOptionsCheck.size(); i++) {
                for (int u = 0; u < listOptionsCheck.get(i).size(); u++) {
                    if (listOptionsCheck.get(i).get(u).isSelected()) {
                        current.add(listOptionsCheck.get(i).get(u).getText());
                    }
                }
            }
            //TMess.azione(current.toString());
            //RECUPERO DATI TERMINE
            terminiScadenza = SeleniumUtilities.safeFind(driver, By.id("termini"), tmess);
            String newDatoTermScad = terminiScadenza.getAttribute("value");
            //TMess.azione(newDatoTermScad);
            //TMess.azione(datoTermScad);
            if (!saved.get(0).equals(current.get(0))) {
                tmess.Generico("Il dato nel campo, Tipologia, e' stato modificato correttamente. Dato originale: " + saved.get(0) + " Dato modificato: " + current.get(0));
            } else {
                tmess.Errori("Il dato nel campo, Tipologia, non ha subito modifiche.");
            }

            if (!saved.get(1).equals(current.get(1))) {
                tmess.Generico("Il dato nel campo, Stato Scadenza, e' stato modificato correttamente. Dato originale: " + saved.get(1) + " Dato modificato: " + current.get(1));
            } else {
                tmess.Errori("Il dato nel campo, Stato Scadenza, non ha subito modifiche.");
            }

            if (!newDatoTermScad.equals(datoTermScad)) {
                tmess.Generico("Il dato nel campo, Termine Scadenza, e' stato modificato correttamente. Dato originale: " + datoTermScad + " Dato modificato: " + newDatoTermScad);
            } else {
                tmess.Errori("Il dato nel campo, Termine Scadenza, non ha subito modifiche.");
            }

            if (!saved.get(2).equals(current.get(2))) {
                tmess.Generico("Il dato nel campo, Visualizza Scadenza, e' stato modificato correttamente. Dato originale: " + saved.get(2) + " Dato modificato: " + current.get(2));
            } else {
                tmess.Errori("Il dato nel campo, Visualizza Scadenza, non ha subito modifiche.");
            }
            //reinserire i dati precedenti e chiudere il test.
            new Select(SeleniumUtilities.safeFind(driver, By.id("tipologiaScadenza"), tmess)).selectByVisibleText(saved.get(0));
            new Select(SeleniumUtilities.safeFind(driver, By.id("scadenze0.idStatoScadenza"), tmess)).selectByVisibleText(saved.get(1));
            new Select(SeleniumUtilities.safeFind(driver, By.id("visualizzaScadenza"), tmess)).selectByVisibleText(saved.get(2));
            SeleniumUtilities.safeFind(driver, By.id("termini"), tmess).clear();
            SeleniumUtilities.safeFind(driver, By.id("termini"), tmess).sendKeys(datoTermScad);
            SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);

        } else {
            success = false;
            tmess.risultati("Non è stato possibile eseguire il test perche' non ci sono Eventi con Scadenza.");
        }
        tmess.conclusione(TEST_33, TESTFAMILY, success);

    }

    @AfterClass
    static public void tearDown() throws Exception {
        driver.quit();
    }

    private void changeTab(int nTab) throws Exception {
        //nTab sono:
        //0 Generale
        //1 Mail
        //2 Protocollo
        //3 Scadenze
        WebElement tabContainer = SeleniumUtilities.safeFind(driver, By.className("page-control"), tmess);
        WebElement ulTab = tabContainer.findElement(By.tagName("ul"));
        List<WebElement> liTab = ulTab.findElements(By.tagName("li"));
        WebElement tabPremuto = liTab.get(nTab);
        tmess.azione("Seleziono il Tab: " + tabPremuto.getText());
        SeleniumUtilities.safeClick2(driver, liTab.get(nTab), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
    }

    private void itemLabel(int nLabel) throws Exception {
        //nLabel EMAIL sono:
        //13 Consenti invio email
        //14 Consenti caricamento allegati nelle email
        //15 Oggetto email
        //16 Corpo email
        //17 Permetti inserimento e visualizzazione di destinatari/mittenti
        //18 Limita i destinatari/mittenti solamente agli enti
        //19 Limita il numero massimo di destinatari a 
        //nLabel PROTOCOLLO sono:
        //21 Effettua la protocollazione
        //22 Visualizza pulsante di download del/dei template
        //23 Carica solo allegati firmati
        //24 Apri sottopratiche
        //25 Script evento
        //27 Script protocollo
        //29 Funziona applicativa
        //31 Evento automatico
        List<String> label = SeleniumUtilities.changeListToString(SeleniumUtilities.safeFindList(driver, By.className("required"), tmess));
        //TMess.azione(label.toString()); //STAMPA TUTTI I VALORI
        tmess.azione("Modifico il dato: " + label.get(nLabel));
    }

    private void aggiungiEvento() throws Exception {
        List<List<String>> tabletext = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        Random generatorEnte = new Random();
        Integer m = generatorEnte.nextInt(tabletext.size());
        List<WebElement> actionCol = driver.findElements(By.className("gestione_eventi"));
        for (int i = m; i < actionCol.size(); i++) {
            tmess.azione("Premo la gestione eventi del processo numero: " + m);
            actionCol.get(m).click();
            break;
        } //premuto un gestione eventi random

        tmess.Generico("Creo un Nuovo Evento.");
        SeleniumUtilities.safeClick(driver, By.className("addgenerico"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
    }

    private void cancellaEvento() throws Exception {
        tmess.azione("Elimino l'evento creato per il Test.");
        SeleniumUtilities.safeClick(driver, By.className("cancella_generico"), tmess);
        SeleniumUtilities.safeClick(driver, By.className("ui-button"), tmess);
    }

    private void sameEventLoop(String codice) throws Exception {
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletext = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        List<String> colCod = TestUtilities.getCol(tabletext, 1);
        List<WebElement> colBtn = driver.findElements(By.className("modifica_ente"));
        List<Integer> u = new ArrayList<Integer>();
        for (int f = 0; f < colCod.size(); f++) {
            if (colCod.get(f).contains(codice)) {
                u.add(f);
                break;
            }
        }
        for (int s = 0; s < colBtn.size(); s++) {
            if (s == u.get(0)) {
                SeleniumUtilities.safeClick2(driver, colBtn.get(s), tmess);
            }
            break;
        }
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
    }

    private void testSelectors(By by) throws Exception {
        List<List<String>> tabletext = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        Random generatorEnte = new Random();
        Integer m = generatorEnte.nextInt(tabletext.size());
        List<WebElement> actionCol = driver.findElements(By.className("gestione_eventi"));
        for (int i = m; i < actionCol.size(); i++) {
            tmess.azione("Premo la gestione eventi del processo numero: " + m);
            actionCol.get(m).click();
            break;
        }
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tabletext = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        Random generatorEvent = new Random();
        Integer r = generatorEvent.nextInt(tabletext.size());
        List<WebElement> eventCol = driver.findElements(By.className("modifica_ente"));
        List<String> saved = new ArrayList<String>();
        List<String> current = new ArrayList<String>();

        tmess.azione("Premo l'evento numero: " + r);
        SeleniumUtilities.safeClick2(driver, eventCol.get(r), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        WebElement selector = driver.findElement(by);
        List<WebElement> listOptions = selector.findElements(By.tagName("option"));

        for (int i = 0; i < listOptions.size(); i++) {
            if (listOptions.get(i).isSelected()) {
                saved.add(listOptions.get(i).getText());
                tmess.azione("Il dato originale dell'evento selezionato risulta essere: " + saved.get(0));
                break;
            }
        }
        SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        eventCol = driver.findElements(By.className("modifica_ente"));
        SeleniumUtilities.safeClick2(driver, eventCol.get(r), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        selector = driver.findElement(by);
        listOptions = selector.findElements(By.tagName("option"));

        for (int p = 0; p < listOptions.size(); p++) {
            new Select(SeleniumUtilities.safeFind(driver, by, tmess)).selectByIndex(p);
            current.add(listOptions.get(p).getText());
            tmess.azione("Il dato dopo l'evento risulta essere: " + current.get(p));
            SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            eventCol = driver.findElements(By.className("modifica_ente"));
            SeleniumUtilities.safeClick2(driver, eventCol.get(r), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            selector = driver.findElement(by);
            listOptions = selector.findElements(By.tagName("option"));
        }

        tmess.azione("Reinserico il dato originale.");
        new Select(SeleniumUtilities.safeFind(driver, by, tmess)).selectByVisibleText(saved.get(0));
        SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);

    }

    private void selectorOptions(int nSelector, By by, List<WebElement> selectableList, List<String> currentSelector) throws Exception {
        List<WebElement> selector = SeleniumUtilities.safeFindList(driver, By.tagName("select"), tmess);
        List<WebElement> option = selector.get(nSelector).findElements(By.tagName("option"));
        for (int i = 0; i < option.size(); i++) {
            if (option.get(i).isSelected()) {
                currentSelector.add(option.get(i).getText());
            }
            if (!option.get(i).isSelected()) {
                selectableList.add(option.get(i));
//                if (selectableList.get(0).equals("")) {
//                    new Select(SeleniumUtilities.safeFind(driver, by, tmess)).selectByIndex(0);
//                } else {
                    new Select(SeleniumUtilities.safeFind(driver, by, tmess)).selectByVisibleText(selectableList.get(0).getText());
//              }
            }
        }
    }

    private void testAddSelectors(By by) throws Exception {
        String codData = "test_evento";
        List<List<String>> tabletext = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        Random generatorEnte = new Random();
        Integer m = generatorEnte.nextInt(tabletext.size());
        List<WebElement> actionCol = driver.findElements(By.className("gestione_eventi"));
        for (int i = m; i < actionCol.size(); i++) {
            tmess.azione("Premo la gestione eventi del processo numero: " + m);
            actionCol.get(m).click();
            break;
        } //premuto un gestione eventi random

        //premo l'aggiungi evento
        List<String> current = new ArrayList<String>();
        SeleniumUtilities.safeClick(driver, By.className("addgenerico"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        //aggiungo campi obbligatori
        SeleniumUtilities.safeFind(driver, By.id("codice"), tmess).sendKeys(codData);
        //leggo i campi selector
        WebElement selector = driver.findElement(by);
        List<WebElement> listOptions = selector.findElements(By.tagName("option"));

        for (int p = 0; p < listOptions.size(); p++) {
            new Select(SeleniumUtilities.safeFind(driver, by, tmess)).selectByIndex(p);
            current.add(listOptions.get(p).getText());
            tmess.azione("Il dato dopo l'evento risulta essere: " + current.get(p));
            if (p == listOptions.size() - 1) {
                cancellaEvento();
                break;
            }
            SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
//            SeleniumUtilities.wait_for_ajax_loading(driver);
//            tabletext = SeleniumUtilities.getTable(driver, "list", "title");
//            List<String> colCod = TestUtilities.getCol(tabletext, 1);
//            List<WebElement> colBtn = driver.findElements(By.className("modifica_ente"));
//            List<Integer> u = new ArrayList<Integer>();
//            for (int f = 0; f < colCod.size(); f++) {
//                if (colCod.get(f).contains(codData)) {
//                    u.add(f);
//                    break;
//                }
//            }
//            for (int s = 0; s < colBtn.size(); s++) {
//                if (s == u.get(0)) {
//                    SeleniumUtilities.safeClick2(driver, colBtn.get(s));
//                }
//                break;
//            }
            sameEventLoop(codData);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            selector = driver.findElement(by);
            listOptions = selector.findElements(By.tagName("option"));
        }
    }

    private void testRadioButton(int nTab, int nLabel, By byNo, By byYes, Boolean True_False) throws Exception {
        //nTab - numero del tab
        //nLabel - numero della Label
        //byNo - id button No
        //byYes - id button Yes
        //true_False - True: Yes False: No
        aggiungiEvento();
        String codData = "test_codice";
        SeleniumUtilities.safeFind(driver, By.id("codice"), tmess).sendKeys(codData);
        changeTab(nTab);
        itemLabel(nLabel);
        WebElement radioBtnNo = SeleniumUtilities.safeFind(driver, byNo, tmess);
        WebElement radioBtnSi = SeleniumUtilities.safeFind(driver, byYes, tmess);
        if (True_False.equals(true)) {
            SeleniumUtilities.safeClick(driver, byYes, tmess);
            if (radioBtnSi.isSelected()) {
                tmess.Generico("Il bottone SI e' selezionato.");
            }
            tmess.azione("Eseguo nuovamente la ricerca dell'Evento.");
            SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
            sameEventLoop(codData);
            changeTab(nTab);
            itemLabel(nLabel);
            radioBtnSi = SeleniumUtilities.safeFind(driver, byYes, tmess);
            if (radioBtnSi.isSelected()) {
                tmess.Generico("Il bottone SI e' selezionato.");
            }
        }
        if (True_False.equals(false)) {
            SeleniumUtilities.safeClick(driver, byNo, tmess);
            if (radioBtnNo.isSelected()) {
                tmess.Generico("Il bottone NO e' selezionato.");
            }
            tmess.azione("Eseguo nuovamente la ricerca dell'Evento.");
            SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
            sameEventLoop(codData);
            changeTab(nTab);
            itemLabel(nLabel);
            radioBtnNo = SeleniumUtilities.safeFind(driver, byNo, tmess);
            if (radioBtnNo.isSelected()) {
                tmess.Generico("Il bottone NO e' selezionato.");
            }
        }
        cancellaEvento();
    }

    public List<List<String>> readTableElementsInPages() throws Exception {
        WebElement tel = SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_PRAT_CONTAPAGINE_ID), tmess);
        String temp = tel.getText();
        Integer pageNumber = Integer.parseInt(temp);
        tmess.azione("page number" + pageNumber.toString());
        List<List<String>> result = SeleniumUtilities.readTableElementsInPages(driver,
                By.cssSelector(WebElMap.AP_PRAT_VAI_ALLA_PAGINA_SUCCESSIVA_BUTTON_CSSSELECTOR),
                By.cssSelector(WebElMap.AP_PRAT_VAI_ALLA_PRIMA_PAGINA_BUTTON_CSSSELECTOR),
                WebElMap.AP_PRAT_TABELLA_ID,
                WebElMap.AP_PRAT_TABELLA_CAMPO_DA_LEGGERE,
                pageNumber, tmess);
        return result;
    }

    public void testScadenze(Boolean scadApC, Boolean visSiNo) throws Exception {
        //scadApC = Stato scadenza
        //true = Aperto
        //false = Chiuso
        //visSiNo = visibilità scadenza
        //true = Si
        //false = no
        aggiungiEvento();
        String codData = "test_codice";
        SeleniumUtilities.safeFind(driver, By.id("codice"), tmess).sendKeys(codData);
        changeTab(3);
        SeleniumUtilities.safeClick(driver, By.linkText("Aggiungi scadenza"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<String> current = new ArrayList<String>();
        WebElement selector = driver.findElement(By.name("scadenze[0].idAnaScadenza"));
        WebElement scadenza = driver.findElement(By.name("scadenze[0].idStatoScadenza"));
        List<WebElement> optionScadenza = scadenza.findElements(By.tagName("option"));
        //selettore STATO SCADENZA
        for (int i = 0; i < optionScadenza.size(); i++) {
            if (scadApC.equals(true)) {
                if (scadApC.equals(optionScadenza.get(i).isSelected())) {
                    tmess.azione("Il valore di stato scadenza e': " + optionScadenza.get(i).getText());
                } else {
                    new Select(scadenza).selectByVisibleText("Aperta");
                    tmess.azione("Seleziono il valore APERTA nel campo stato scadenza.");
                }
            } else {
                if (scadApC.equals(optionScadenza.get(i).isSelected())) {
                    tmess.azione("Il valore di stato scadenza e': " + optionScadenza.get(i).getText());
                } else {
                    new Select(scadenza).selectByVisibleText("Chiusa");
                    tmess.azione("Seleziono il valore CHIUSA nel campo stato scadenza.");
                }
            }
        }
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        WebElement visualiz = driver.findElement(By.name("scadenze[0].flgVisualizzaScadenza"));
        WebElement optionVisualiz = visualiz.findElement(By.tagName("option"));
        //selettore VISUALIZZA SCADENZA
        if (visSiNo == true) {
            if (visSiNo.equals(optionVisualiz.isSelected())) {
                tmess.azione("Il valore di visibilita' scadenza e': " + optionVisualiz.getText());
            } else {
                new Select(visualiz).selectByVisibleText("Si");
                tmess.azione("Seleziono il valore SI nel campo visibilita' scadenza.");
            }
        } else {
            if (visSiNo.equals(optionVisualiz.isSelected())) {
                tmess.azione("Il valore di visibilita' scadenza e': " + optionVisualiz.getText());
            } else {
                new Select(visualiz).selectByVisibleText("No");
                tmess.azione("Seleziono il valore NO nel campo visibilita' scadenza.");
            }
        }

        List<WebElement> listOptions = selector.findElements(By.tagName("option"));
        for (int p = 0; p < listOptions.size(); p++) {
            new Select(SeleniumUtilities.safeFind(driver, By.name("scadenze[0].idAnaScadenza"), tmess)).selectByIndex(p);
            current.add(listOptions.get(p).getText());
            tmess.azione("Seleziono la tipologia: " + current.get(p));

            if (p == listOptions.size() - 1) {
                SeleniumUtilities.safeClick(driver, By.className("menogenerico"), tmess);
                cancellaEvento();
                break;
            }
            tmess.azione("Eseguo una nuova ricerca.");
            SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            sameEventLoop(codData);
            changeTab(3);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            // controllo su STATO SCADENZA
            WebElement neWscadenza = driver.findElement(By.name("scadenze[0].idStatoScadenza"));
            List<WebElement> neWoptionScadenza = neWscadenza.findElements(By.tagName("option"));
            for (int y = 0; y < neWoptionScadenza.size(); y++) {

                if (scadApC.equals(neWoptionScadenza.get(y).isSelected())) {
                    tmess.Generico("Il valore di Stato scadenza e': " + neWoptionScadenza.get(y).getText() + ". Ed e' stato correttamente selezionato.");
                } else {
                    tmess.Errori("Il valore di Stato scadenza: " + neWoptionScadenza.get(y).getText() + ", e' errato.");
                    break;
                }
            }
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            //controllo su VISUALIZZA SCADENZA
            visualiz = driver.findElement(By.name("scadenze[0].flgVisualizzaScadenza"));
            optionVisualiz = visualiz.findElement(By.tagName("option"));
            if (visSiNo.equals(optionVisualiz.isSelected())) {
                tmess.Generico("Il valore di Visualizza scadenza e': " + optionVisualiz.getText() + ". Ed e' stato correttamente selezionato.");
            } else {
                tmess.Errori("Il valore di Visualizza scadenza: " + optionVisualiz.getText() + ", e' errato.");
                break;
            }
            selector = driver.findElement(By.name("scadenze[0].idAnaScadenza"));
            listOptions = selector.findElements(By.tagName("option"));
        }
    }

    public void testSelectorScadenze(boolean sceltaStato, boolean sceltaVisibilita) throws Exception {
        //sceltaStato = Stato scadenza
        //true = Aperta
        //false = Chiusa
        //sceltaVisibilita = visibilità scadenza
        //true = Si
        //false = no
        aggiungiEvento();
        String codData = "test_codice";
        SeleniumUtilities.safeFind(driver, By.id("codice"), tmess).sendKeys(codData);
        changeTab(3);
        SeleniumUtilities.safeClick(driver, By.linkText("Aggiungi scadenza"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        WebElement statoSelector = SeleniumUtilities.safeFind(driver, By.name("scadenze[0].idStatoScadenza"), tmess);
        List<WebElement> optionStato = statoSelector.findElements(By.tagName("option"));
        WebElement visSelector = SeleniumUtilities.safeFind(driver, By.name("scadenze[0].flgVisualizzaScadenza"), tmess);
        List<WebElement> optionVis = visSelector.findElements(By.tagName("option"));
        String termDato = "90";

        if (sceltaStato) {
            new Select(statoSelector).selectByVisibleText("Aperta");
            if (optionStato.get(0).isSelected()) {
                tmess.azione("L'opzione di Stato scadenza: " + optionStato.get(0).getText() + ", e' selezionata correttamente.");
            }
        } else {
            new Select(statoSelector).selectByVisibleText("Chiusa");
            if (optionStato.get(1).isSelected()) {
                tmess.azione("L'opzione di Stato scadenza: " + optionStato.get(1).getText() + ", e' selezionata correttamente.");
            }
        }
        tmess.azione("Inserisco il valore: " + termDato + ", nel campo Termini Scadenza");
        WebElement terminiScadenza = SeleniumUtilities.safeFind(driver, By.name("scadenze[0].terminiScadenza"), tmess);
        terminiScadenza.sendKeys(termDato);

        if (sceltaVisibilita) {
            new Select(visSelector).selectByVisibleText("Si");
            if (optionVis.get(0).isSelected()) {
                tmess.azione("L'opzione di Visibilita' scadenza: " + optionVis.get(0).getText() + ", e' selezionata correttamente.");
            }
        } else {
            new Select(visSelector).selectByVisibleText("No");
            if (optionVis.get(1).isSelected()) {
                tmess.azione("L'opzione di Visibilita' scadenza: " + optionVis.get(1).getText() + ", e' selezionata correttamente.");
            }
        }

        tmess.azione("Eseguo una nuova ricerca.");
        SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        sameEventLoop(codData);
        changeTab(3);
        //controllo stato
        statoSelector = SeleniumUtilities.safeFind(driver, By.name("scadenze[0].idStatoScadenza"), tmess);
        optionStato = statoSelector.findElements(By.tagName("option"));
        if (sceltaStato) {
            if (optionStato.get(0).isSelected()) {
                tmess.azione("L'opzione di Stato scadenza: " + optionStato.get(0).getText() + ", e' ancora selezionata correttamente.");
            }
        } else {
            if (optionStato.get(1).isSelected()) {
                tmess.azione("L'opzione di Stato scadenza: " + optionStato.get(1).getText() + ", e' ancora selezionata correttamente.");
            }
        }
        //controllo termini scadenza
        terminiScadenza = SeleniumUtilities.safeFind(driver, By.name("scadenze[0].terminiScadenza"), tmess);
        if (terminiScadenza.getAttribute("value").contentEquals(termDato)) {
            tmess.azione("Il valore immesso nel campo Termini di scadenza e' stato riportato correttamente.");
        }
        //controllo visibilità
        visSelector = SeleniumUtilities.safeFind(driver, By.name("scadenze[0].flgVisualizzaScadenza"), tmess);
        optionVis = visSelector.findElements(By.tagName("option"));
        if (sceltaVisibilita) {
            if (optionVis.get(0).isSelected()) {
                tmess.azione("L'opzione di Visibilita' scadenza: " + optionVis.get(0).getText() + ", e' ancora selezionata correttamente.");
            }
        } else {
            if (optionVis.get(1).isSelected()) {
                tmess.azione("L'opzione di Visibilita' scadenza: " + optionVis.get(1).getText() + ", e' ancora selezionata correttamente.");
            }
        }
        cancellaEvento();
    }
}
