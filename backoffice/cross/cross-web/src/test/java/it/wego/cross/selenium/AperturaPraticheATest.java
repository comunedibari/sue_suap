/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.selenium;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author GabrieleM
 */
public class AperturaPraticheATest {

    static final private String TESTFAMILY = "AperturaPratiche";
    static final private String TEST_1 = "Ordinamento_elenco_pratiche_nuove";
    static final private String TEST_2 = "Ricerca_apertura_pratiche_nuove_1";
    static final private String TEST_3 = "Ricerca_apertura_pratiche_nuove_2";
    static final private String TEST_4 = "Ricerca_apertura_pratiche_nuove_3";
    static final private String TEST_5 = "Ricerca_apertura_pratiche_nuove_4";
    static final private String TEST_6 = "Ricerca_apertura_pratiche_nuove_5";
    static final private String TEST_7 = "Ricerca_apertura_pratiche_nuove_6";
    static final private String TEST_8 = "Ricerca_apertura_pratiche_nuove_7";
    static final private String PAGENUMBERID = "sp_1_pager";
    static final private String GOTOFIRSTPAGEBOTTON = "ui-icon.ui-icon-seek-first";
    static private WebDriver driver;
    static private Integer DATA = 1;
    static private Integer OGGETTO = 2;
    static private Integer ID = 3;
    static private Integer ENTE = 4;
    static private Integer COMUNE = 5;
    static private Integer RICHIEDENTE = 6;
    static private Integer OPERATORE = 7;
    private boolean acceptNextAlert = true;
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

    private void GoTo_elenco_praticheNuove(WebDriver driver) throws Exception {

        SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_PRAT_GESTIONE_BUTTON_CSSSELECTOR), tmess);
        SeleniumUtilities.safeClick(driver, By.linkText("Apertura pratiche"), tmess);
        OpenSearchPanel();
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
    }

    @Ignore
    @Test
    //Verifica che le pratiche vengano ordinate correttamente per data e per oggetto
    public void Ordinamento_elenco_pratiche_nuove() throws Exception {
        Boolean success = true;
        tmess.NuovoTest(TEST_1, TESTFAMILY);
        GoTo_elenco_praticheNuove(driver);

        tmess.azione("Ordino in modo crescente le date e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_dataRicezione"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        List<String> elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(1));
        }
        if (TestUtilities.CheckDateCrescent(elenco, true, tmess)) {
            tmess.Generico("Le date sono in ordine crescente");
        } else {
            success = false;
            tmess.Errori("Le date non sono in ordine crescente!");
        }
        
        tmess.azione("Ordino in modo decrescente le date e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_dataRicezione"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(1));
        }
        if (TestUtilities.CheckDateCrescent(elenco, false, tmess)) {
            tmess.Generico("Le date sono in ordine decrescente");
        } else {
            success = false;
            tmess.Errori("Le date non sono in ordine decrescente!");
        }
        
        tmess.azione("Ordino in modo crescente gli oggetti e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_oggettoPratica"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(2));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, true)) {
            tmess.Generico("Gli oggetti sono in ordine crescente");
        } else {
            success = false;
            tmess.Errori("Gli oggetti non sono in ordine crescente!");
        }
        
        tmess.azione("Ordino in modo decrescente gli oggetti e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_oggettoPratica"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(2));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, false)) {
            tmess.Generico("Gli oggetti sono in ordine decrescente");
        } else {
            success = false;
            tmess.Errori("Gli oggetti non sono in ordine crescente!");
        }

        tmess.conclusione(TEST_1, TESTFAMILY, success);
    }

    @Ignore
    @Test
    //Verifica i risultati della ricerca per id delle pratiche
    public void Ricerca_apertura_pratiche_nuove_1() throws Exception {
        Boolean success = true;
        tmess.NuovoTest(TEST_2, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_elenco_praticheNuove(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletext = readTableElementsInPages();
        List<String> praticaCodes = TestUtilities.getCol(tabletext, ID);
        String firstnProtocol = praticaCodes.get(0).split("/")[0];
        Integer nPraticheProtocol = SeleniumUtilities.countOccurenceProtocolInCol(praticaCodes, firstnProtocol);
        tmess.azione("Dalla tabella  leggo il numero di protocollo della prima pratica:   " + firstnProtocol);
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_PRAT_RICERCA_PER_ID_INPUT_ID), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_PRAT_RICERCA_PER_ID_INPUT_ID), tmess).sendKeys(firstnProtocol);
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_PRAT_START_RICERCA_BUTTON_ID), tmess);
        tmess.azione("Effettuo la ricerca secondo il numero di protocollo letto");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tabletext = readTableElementsInPages();
        praticaCodes = TestUtilities.getCol(tabletext, ID);
        tmess.azione("Verifico che tutte le pratiche presentate abbiano il numero di protocollo selezionato ");
        if (praticaCodes.size() != nPraticheProtocol) {
            tmess.Errori("Sono state trovate " + praticaCodes.size() + " pratiche con il protocollo " + firstnProtocol + " mentre prima ne erano presenti" + nPraticheProtocol);
            success = false;
        }

        for (String praticaCode : praticaCodes) {
            String temp = praticaCode.split("/")[0];
            if (!temp.equals(firstnProtocol)) {
                tmess.Errori("la pratica visualizzata: " + temp + " non corrisponde con la pratica ricercata " + firstnProtocol);
                success = false;
            }
            if (success) {
                tmess.risultati("Tutte le pratiche presentate hanno il numero di protocollo selezionato ");
            }
            tmess.conclusione(TEST_2, TESTFAMILY, success);
        }
    }

    @Ignore
    @Test
    //Verifica i risultati della ricerca per ente delle pratiche
    public void Ricerca_apertura_pratiche_nuove_2() throws Exception {
        Boolean success = true;

        tmess.NuovoTest(TEST_3, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_elenco_praticheNuove(driver);

        tmess.azione("Visualizzo tutte le pratiche per maggiore sicurezza");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
//        OpenSearchPanel();
//        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        CleanSearch();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_PRAT_VISUALIZZA_TUTTE_PARTICHE_CHECKBOX_ID), tmess);
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_PRAT_START_RICERCA_BUTTON_ID), tmess);

        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletext = readTableElementsInPages();
        List<String> praticaEnti = TestUtilities.getCol(tabletext, ENTE);
        List<String> risultatoRicerca = TestUtilities.getCol(tabletext, ID);
        String firstEnte = praticaEnti.get(0);
        List<String> veroRisultato = TestUtilities.selectBy(tabletext, ID, ENTE, firstEnte);
        tmess.azione("Dalla tabella  leggo l'ente della prima pratica:   " + firstEnte);
        Integer nSelectedEnte = SeleniumUtilities.countOccurenceInCol(praticaEnti, firstEnte);
        tmess.azione("Conto le pratiche con l'ente selezionato:   " + veroRisultato.size());
        tmess.azione("Sono visibili :   " + nSelectedEnte.toString() + " pratiche con l'ente selezionato.");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        new Select(SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_PRAT_RICERCA_SELEZIONA_ENTE_SELECTOR_ID), tmess)).selectByVisibleText(firstEnte);
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_PRAT_START_RICERCA_BUTTON_ID), tmess);
        tmess.azione("Effettuo la ricerca secondo l'ente selezionato visualizzando tutte le pratiche");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tabletext = readTableElementsInPages();
        praticaEnti = TestUtilities.getCol(tabletext, ENTE);
        risultatoRicerca = TestUtilities.getCol(tabletext, ID);
        tmess.azione("Confronto il numero di pratiche trovate:" + praticaEnti.size());
        if (praticaEnti.size() != nSelectedEnte) {
            tmess.Errori("Il numero di pratiche trovato: " + praticaEnti.size() + " non corrisponde a quello delle pratiche di partenza aventi l'ente" + firstEnte);
            success = false;
        } else {
            tmess.risultati("Il numero di pratiche coincide");
        }
        tmess.azione("Verifico che tutte le pratiche presentate abbiano come ente quello selezionato  ");
        for (String praticaEnte : praticaEnti) {
            if (!praticaEnte.equals(firstEnte)) {
                tmess.Errori("l'ente della pratica visualizzato: " + praticaEnte + " non corrisponde con l'ente della pratica ricercato " + firstEnte);
                success = false;
            }

        }
        tmess.azione("Infine verifico che gli id delle pratiche trovate coincidano");
//        for (Integer i = 0; i < veroRisultato.size(); i++) {
//            if (!veroRisultato.get(i).equals(risultatoRicerca.get(i))) {
//                TMess.Errori("L'id della pratica trovata: " + risultatoRicerca.get(i) + " non corrisponde con quewllo della pratica che dovrebbe essere presente " + veroRisultato.get(i));
//                success = false;
//            }
//        }
        if (!TestUtilities.checkIfListSetIsPresent(risultatoRicerca, veroRisultato, tmess)) {
            tmess.Errori("Un risultato che non dovrebbe essere presente viene invece visualizzato.");
            success = false;
        }
        if (success) {
            tmess.risultati("Le pratiche visualizzate tramite ricerca sono esattamente tutte e sole quelle aventi per ente quello selezionato.");
        }
        tmess.conclusione(TEST_3, TESTFAMILY, success);
    }
    //DA RIVEDERE perché in questo caso lo stato non è visibile nella tabella

    @Ignore
    @Test
    //Verifica i risultati della ricerca per stato delle pratiche
    public void Ricerca_apertura_pratiche_nuove_3() throws Exception {
        Boolean success = true;
        tmess.NuovoTest(TEST_4, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_elenco_praticheNuove(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
//        OpenSearchPanel();
//        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        CleanSearch();
        tmess.azione("Controllo che ci siano pratiche ricevute: le pratiche ricevute sono????");
        new Select(SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_PRAT_RICERCA_SELEZIONA_STATO_SELECTOR_ID), tmess)).selectByVisibleText("Ricevuta");
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_PRAT_START_RICERCA_BUTTON_ID), tmess);
        tmess.azione("Effettuo la ricerca secondo lo stato selezionato");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletext = readTableElementsInPages();
        List<String> praticaStati = TestUtilities.getCol(tabletext, 3);
        tmess.azione("Verifico che tutte le pratiche presentate abbiano come stato quello selezionato  ???");
        if (praticaStati.size() <= 0) {
            tmess.Errori("Nessuna pratica è stata trovata nonostante prima fosse presente almeno una pratica con stato" + "Ricevuta");
            success = false;
        }
        if (success) {
            tmess.risultati("Tutte le pratiche presentate hanno lo stato selezionato ");
        }
        tmess.conclusione(TEST_4, TESTFAMILY + " QUESTO TEST NON VALE!!!!!!!!!!", success);
    }

    @Ignore
    @Test
    //Verifica i risultati della ricerca per intervallo di date delle pratiche
    public void Ricerca_apertura_pratiche_nuove_4() throws Exception {
        Boolean success = true;
        tmess.NuovoTest(TEST_5, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_elenco_praticheNuove(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
//        OpenSearchPanel();
//        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        CleanSearch();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletext = readTableElementsInPages();
        List<String> praticaDate = TestUtilities.getCol(tabletext, 1);
        String firstData = "";
        for (int i = 0; i < praticaDate.size(); i++) {
            if (Integer.parseInt(praticaDate.get(i).split("/")[2]) <= 2014) {
//                tmess.azione(praticaDate.get(i).split("/")[2]);
                firstData = praticaDate.get(i);
                i = praticaDate.size() + 1;
            }
        }
        String secondData = firstData;
        for (int i = 0; i < praticaDate.size(); i++) {
            if (!secondData.equals(praticaDate.get(i)) && Integer.parseInt(praticaDate.get(i).split("/")[2]) <= 2014) {
                secondData = praticaDate.get(i);
                i = praticaDate.size() + 1;
            }
        }
        List<String> orderedData = TestUtilities.orderDates(firstData, secondData);
        List<String> veroRisultato = TestUtilities.selectByDateInterval(tabletext, ID, DATA, orderedData.get(0), orderedData.get(1));
        List<String> firstDataInput = TestUtilities.switchFromDateToDateSearchInput(orderedData.get(0), tmess);
        List<String> secondDataInput = TestUtilities.switchFromDateToDateSearchInput(orderedData.get(1), tmess);

        tmess.azione("Dalla tabella  leggo la data della prima pratica e ne cerco una differente se possibile. Le date trovate sono:" + orderedData.get(0) + "," + orderedData.get(1));
        Integer nSelectedData = SeleniumUtilities.countOccurrenceOfDataInCol(praticaDate, orderedData.get(0), orderedData.get(1));
        tmess.azione("Sono visibili :   " + nSelectedData.toString() + " pratiche nell'intervallo selezionato" + orderedData.get(0) + "," + orderedData.get(1) + "tra tutte le " + praticaDate.size() + "pratiche visualizzate");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_PRAT_RICERCA_SELEZIONA_DATA_DA_INPUT_ID), tmess);
        tmess.azione("Seleziono il mese di inizio:" + firstDataInput.get(1));
        new Select(SeleniumUtilities.safeFind(driver, By.className(WebElMap.AP_PRAT_SELEZIONA_MESE_INPUT_CSSSELECTOR), tmess)).selectByVisibleText(firstDataInput.get(1));
        tmess.azione("Seleziono l'anno di inizio:" + firstDataInput.get(2));
        new Select(SeleniumUtilities.safeFind(driver, By.className(WebElMap.AP_PRAT_SELEZIONA_ANNO_INPUT_CSSSELECTOR), tmess)).selectByVisibleText(firstDataInput.get(2));
        tmess.azione("Seleziono il giorno di inizio:" + firstDataInput.get(0));
        SeleniumUtilities.safeClick(driver, By.linkText(firstDataInput.get(0)), tmess);
        SeleniumUtilities.waitForElToDisappear(driver, By.id("ui-datepicker-div"), tmess);
        SeleniumUtilities.safeClick(driver, By.id("search_data_to"), tmess);
        tmess.azione("Seleziono il mese di fine:" + secondDataInput.get(1));
        new Select(SeleniumUtilities.safeFind(driver, By.className(WebElMap.AP_PRAT_SELEZIONA_MESE_INPUT_CSSSELECTOR), tmess)).selectByVisibleText(secondDataInput.get(1));
        tmess.azione("Seleziono l'anno di fine:" + secondDataInput.get(2));
        new Select(SeleniumUtilities.safeFind(driver, By.className(WebElMap.AP_PRAT_SELEZIONA_ANNO_INPUT_CSSSELECTOR), tmess)).selectByVisibleText(secondDataInput.get(2));
        tmess.azione("Seleziono il giorno di fine:" + secondDataInput.get(0));
        SeleniumUtilities.safeClick(driver, By.linkText(secondDataInput.get(0)), tmess);
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_PRAT_START_RICERCA_BUTTON_ID), tmess);
        tmess.azione("Effettuo la ricerca secondo l'intervallo selezionato ");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tabletext = readTableElementsInPages();
        praticaDate = TestUtilities.getCol(tabletext, DATA);
        List<String> risultatoRicerca = TestUtilities.getCol(tabletext, ID);
        tmess.azione("Verifico che il numero di pratiche coincida con quello trovato  ");
        if (praticaDate.size() != nSelectedData) {
            tmess.risultati("Il numero di pratiche trovato: " + praticaDate.size() + " non corrisponde a quello delle pratiche di partenza con l'intervallo selezionato");
            success = false;
        } else {
            tmess.azione("Il numero di pratiche trovato corrisponde con quello precedentemente visualizzato ");
        }
        tmess.azione("Verifico che tutte le pratiche si trovino nell'intervallo selezionato  ");
        for (String praticaData : praticaDate) {
            if (!SeleniumUtilities.checkDateInterval(orderedData.get(0), orderedData.get(1), praticaData)) {
                tmess.Errori("La della pratica visualizzata: " + praticaData + " non appartiene all'intervallo ricercato " + orderedData.get(0) + "-" + orderedData.get(1));
                success = false;
            }

        }
        tmess.azione("Verifico infine che gli id delle pratiche trovate nella ricerca coincidano con quelli visualizzati nella ricerca globale");
        if (praticaDate.size() != risultatoRicerca.size()) {
            tmess.Errori("Le dimensioni degli array colonna relativi alle date a agli enti non coincidono, probabile un errore di lettura dei dati");
            success = false;
        }
//        for (Integer i = 0; i < veroRisultato.size(); i++) {
//            if (!veroRisultato.get(i).equals(risultatoRicerca.get(i))) {
//                TMess.Errori("L'id della pratica: " + risultatoRicerca.get(i) + " non coincide con l'id della nell'ordine visualizzato in precedenza " + veroRisultato.get(i));
//                success = false;
//            }
//        }
        if (!TestUtilities.checkIfListSetIsPresent(risultatoRicerca, veroRisultato, tmess)) {
            tmess.Errori("Un risultato che non dovrebbe essere presente viene invece visualizzato.");
            success = false;
        }
        if (success) {
            tmess.risultati("Le pratiche visualizzate sono tutte e sole quelle con data nell'intervallo selezionato ");
        }
//        CleanSearch();
        tmess.conclusione(TEST_5, TESTFAMILY, success);
    }

    @Ignore
    @Test
    //Questo test è molto simile al precedente, cambia solo il modo ti selezionare la data
    //in questo caso viene immessa tramite la tastiera
    public void Ricerca_apertura_pratiche_nuove_5() throws Exception {
        Boolean success = true;

        tmess.NuovoTest(TEST_6, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_elenco_praticheNuove(driver);

//        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
//        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        CleanSearch();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletext = readTableElementsInPages();
        List<String> praticaDate = TestUtilities.getCol(tabletext, 1);
        String firstData = "";
        for (int i = 0; i < praticaDate.size(); i++) {
            if (Integer.parseInt(praticaDate.get(i).split("/")[2]) <= 2014) {
                tmess.azione(praticaDate.get(i).split("/")[2]);
                firstData = praticaDate.get(i);
                i = praticaDate.size() + 1;
            }
        }
        String secondData = firstData;
        for (int i = 0; i < praticaDate.size(); i++) {
            if (!secondData.equals(praticaDate.get(i)) && Integer.parseInt(praticaDate.get(i).split("/")[2]) <= 2014) {
                secondData = praticaDate.get(i);
                i = praticaDate.size() + 1;
            }
        }
        List<String> orderedData = TestUtilities.orderDates(firstData, secondData);
        tmess.azione("Dalla tabella  leggo la data della prima pratica e ne cerco una differente se possibile. Le date trovate sono:" + orderedData.get(0) + "," + orderedData.get(1));
        Integer nSelectedData = SeleniumUtilities.countOccurrenceOfDataInCol(praticaDate, orderedData.get(0), orderedData.get(1));
        tmess.azione("Sono visibili :   " + nSelectedData.toString() + " pratiche nell'intervallo selezionato.");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_PRAT_RICERCA_SELEZIONA_DATA_DA_INPUT_ID), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_PRAT_RICERCA_SELEZIONA_DATA_DA_INPUT_ID), tmess).sendKeys(orderedData.get(0));

        SeleniumUtilities.safeFind(driver, By.id("search_data_to"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("search_data_to"), tmess).sendKeys(orderedData.get(1));
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_PRAT_START_RICERCA_BUTTON_ID), tmess);
        tmess.azione("Effettuo la ricerca secondo l'intervallo selezionato ");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tabletext = readTableElementsInPages();
        praticaDate = TestUtilities.getCol(tabletext, 1);
        tmess.azione("Verifico che tutte le pratiche presentate siano del numero corretto  ");
        if (praticaDate.size() != nSelectedData) {
            tmess.Errori("Il numero di pratiche trovato: " + praticaDate.size() + " non corrisponde a quello delle pratiche di partenza con l'intervallo selezionato");
            success = false;
        }
        for (String praticaData : praticaDate) {
            if (!SeleniumUtilities.checkDateInterval(orderedData.get(0), orderedData.get(1), praticaData)) {
                tmess.Errori("La della pratica visualizzata: " + praticaData + " non appartiene all'intervallo ricercato ");
                success = false;
            }

        }
        if (success) {
            tmess.risultati("Tutte le pratiche presentate sono nell'intervallo selezionato ");
        }
        tmess.conclusione(TEST_6, TESTFAMILY, success);
    }

    @Ignore
    @Test
    //Il test verifica i risultati della ricerca per intervalli di date anche nel caso di visualizzazione di tutte le pratiche eseguendo dei controlli solo quantitativi.
    public void Ricerca_apertura_pratiche_nuove_6() throws Exception {
        Boolean success = true;

        tmess.NuovoTest(TEST_7, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_elenco_praticheNuove(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
//        OpenSearchPanel();
//        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        CleanSearch();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletext = readTableElementsInPages();
        List<String> praticaDate = TestUtilities.getCol(tabletext, 1);
        String operatore = TestUtilities.getCol(tabletext, 7).get(0);
        tmess.azione("leggo l'operatore della prima pratica che dovrebbe essere lo stesso per tutte:" + operatore);
        String firstData = "";
        for (int i = 0; i < praticaDate.size(); i++) {
            if (Integer.parseInt(praticaDate.get(i).split("/")[2]) <= 2014) {
                tmess.azione(praticaDate.get(i).split("/")[2]);
                firstData = praticaDate.get(i);
                i = praticaDate.size() + 1;
            }
        }
        String secondData = firstData;
        for (int i = 0; i < praticaDate.size(); i++) {
            if (!secondData.equals(praticaDate.get(i)) && Integer.parseInt(praticaDate.get(i).split("/")[2]) <= 2014) {
                secondData = praticaDate.get(i);
                i = praticaDate.size() + 1;
            }
        }
        List<String> orderedData = TestUtilities.orderDates(firstData, secondData);
        tmess.azione("Dalla tabella  leggo la data della prima pratica e ne cerco una differente se possibile. Le date trovate sono:" + orderedData.get(0) + "," + orderedData.get(1));
        Integer nSelectedData = SeleniumUtilities.countOccurrenceOfDataInCol(praticaDate, orderedData.get(0), orderedData.get(1));
        tmess.azione("Sono visibili :   " + nSelectedData.toString() + " pratiche nell'intervallo selezionato.");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_PRAT_RICERCA_SELEZIONA_DATA_DA_INPUT_ID), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_PRAT_RICERCA_SELEZIONA_DATA_DA_INPUT_ID), tmess).sendKeys(orderedData.get(0));

        SeleniumUtilities.safeFind(driver, By.id("search_data_to"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("search_data_to"), tmess).sendKeys(orderedData.get(1));
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_PRAT_START_RICERCA_BUTTON_ID), tmess);
        tmess.azione("Effettuo la ricerca secondo l'intervallo selezionato ");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tabletext = readTableElementsInPages();
        praticaDate = TestUtilities.getCol(tabletext, 1);
        List<String> operatori = TestUtilities.getCol(tabletext, 7);

        if (praticaDate.size() != nSelectedData) {
            tmess.Errori("Il numero di pratiche trovato: " + praticaDate.size() + " non corrisponde a quello delle pratiche di partenza con l'intervallo selezionato");
            success = false;
        }
        tmess.azione("Controllo che le pratiche visualizzate abbiano tutte lo stesso operatore cioè quello di partenza.");
        for (String operatoren : operatori) {
            if (!operatore.equals(operatoren)) {
                tmess.Errori("L'operatore " + operatoren + " visualizzato " + "è diverso dall'operatore precedente ma non è stato selezionato di visualizzare tutte le pratiche");
                success = false;
            }
        }
        tmess.azione("Verifico che tutte le pratiche presentate abbiano come data una nell'intervallo selezionato  ");
        for (String praticaData : praticaDate) {
            if (!SeleniumUtilities.checkDateInterval(firstData, secondData, praticaData)) {
                tmess.Errori("La della pratica visualizzata: " + praticaData + " non appartiene all'intervallo ricercato ");
                success = false;
            }

        }
        if (success) {
            tmess.risultati("Tutte le pratiche presentate sono nell'intervallo selezionato ");
        }
        CleanSearch();
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_PRAT_VISUALIZZA_TUTTE_PARTICHE_CHECKBOX_ID), tmess);
        tmess.azione("Visualizzo ora tutte le pratiche");
//        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_PRAT_VISUALIZZA_TUTTE_PARTICHE_CHECKBOX_ID)).click();
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_PRAT_START_RICERCA_BUTTON_ID), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tabletext = readTableElementsInPages();
        praticaDate = TestUtilities.getCol(tabletext, 1);

        nSelectedData = SeleniumUtilities.countOccurrenceOfDataInCol(praticaDate, orderedData.get(0), orderedData.get(1));
        tmess.azione("All'interno di tutte le pratiche trovo quelle con data nell'intervallo scelto in precedenza. Si sono trovate " + nSelectedData + "pratiche");
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_PRAT_RICERCA_SELEZIONA_DATA_DA_INPUT_ID), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_PRAT_RICERCA_SELEZIONA_DATA_DA_INPUT_ID), tmess).sendKeys(orderedData.get(0));
        SeleniumUtilities.safeFind(driver, By.id("search_data_to"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("search_data_to"), tmess).sendKeys(orderedData.get(1));
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_PRAT_START_RICERCA_BUTTON_ID), tmess);
        tmess.azione("Leggo i risultati trovati con la ricerca di tutte le pratiche");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tabletext = readTableElementsInPages();
        praticaDate = TestUtilities.getCol(tabletext, 1);
        tmess.azione("Verifico che le pratiche siano in numero corretto");
        if (praticaDate.size() != nSelectedData) {
            tmess.Errori("Il numero di pratiche nell'intervallo selezionato non corrisponde con quello reale nel caso di visualizzazione di tutte le pratiche.");
            success = false;
        }

        tmess.conclusione(TEST_7, TESTFAMILY, success);
    }

    
    @Test
    //Il test verifica i risultati della ricerca se lanciata quando vuota. Ovviamente per un effettivo controllo delle pratiche
    //sarebbe necessario conoscere tutte le pratiche dal db. In questo caso si controlla solo che non siano presenti errori 
    //quando la ricerca è effettuata non si verificano errori.
    public void Ricerca_apertura_pratiche_nuove_7() throws Exception {
        Boolean success = true;
        List<List<String>> tabletext = new ArrayList();
        tmess.NuovoTest(TEST_8, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_elenco_praticheNuove(driver);
        try {
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            tmess.azione("Apro il pannello di ricerca");
//            OpenSearchPanel();
            tmess.azione("Pulisco la ricerca e visualizzo le pratiche");
//            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            CleanSearch();
            tmess.azione("Leggo gli elementi in ciascuna pratica");
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            tabletext = readTableElementsInPages();
            List<String> praticaDate = TestUtilities.getCol(tabletext, 1);
            tmess.risultati("Leggo gli elementi in ciascuna pratica");
        } catch (Exception e) {
            tmess.Errori("Si sono verificati degli errori nella ricerca:" + e);
            success = false;
        }
        if (success) {
            tmess.risultati("Non si sono verificati errori nella ricerca. Numero di risultati trovati:" + tabletext.size());
        }
        tmess.conclusione(TEST_8, TESTFAMILY, success);
    }

    static public List<List<String>> readTableElementsInPages() throws Exception {
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

    @AfterClass
    static public void tearDown() throws Exception {
        driver.quit();
    }

    private void OpenSearchPanel() throws Exception {
        SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_PRAT_APRI_PANNELLO_RICERCA_BUTTON_CSSSELECTOR), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        CleanSearch();
    }

    private void CleanSearch() throws Exception {
        SeleniumUtilities.safeFind(driver, By.id("search_id_pratica"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("search_data_from"), tmess).clear();
        WebElement check = SeleniumUtilities.safeFind(driver, By.id("search_all"), tmess);
        if (check.isSelected()) {
            SeleniumUtilities.safeClick(driver, By.id("search_all"), tmess);
        }
        new Select(SeleniumUtilities.safeFind(driver, By.id("search_ente"), tmess)).selectByVisibleText("Qualisiasi ente");
        new Select(SeleniumUtilities.safeFind(driver, By.id("search_stato"), tmess)).selectByVisibleText("Qualsiasi stato");
    }

    private boolean isElementPresent(By by) {
        try {
            SeleniumUtilities.safeFind(driver, by, tmess);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
