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

public class GestioneEventiTest {

    static final private String TESTFAMILY = "GestioneEventi";
    static final private String TEST_1 = "Ricerca_ elenco_gestione_pratiche_1";
    static final private String TEST_2 = "Ricerca_elenco_gestione_pratiche_2";
    static final private String TEST_3 = "Ricerca_elenco_gestione_pratiche_3";
    static final private String TEST_4 = "Ricerca_elenco_gestione_pratiche_4";
    static final private String TEST_5 = "Ricerca_elenco_gestione_pratiche_5";
    static final private String TEST_6 = "Ricerca_elenco_gestione_pratiche_8";
    static final private String TEST_7 = "Ricerca_elenco_gestione_pratiche_9_1";
    static final private String TEST_8 = "Ricerca_elenco_gestione_pratiche_9_2";
    static final private String TEST_9 = "Ricerca_elenco_gestione_pratiche_6";
    static final private String TEST_10 = "Ricerca_elenco_gestione_pratiche_7";
    static final private String TEST_11 = "Ricerca_elenco_gestione_pratiche_14";
    static final private String PAGENUMBERID = "sp_1_pager";
    static final private String GOTOFIRSTPAGEBOTTON = "ui-icon.ui-icon-seek-first";
    static private Integer ENTE = 6;
    static private Integer COMUNE = 7;
    static private Integer STATO = 1;
    static private Integer ID = 5;
    static private Integer RICHIEDENTE = 8;
    private static TMess tmess;
    static private WebDriver driver;
    private boolean acceptNextAlert = true;

    @BeforeClass
    public static void setUp() throws MalformedURLException, FileNotFoundException, UnsupportedEncodingException {
        DesiredCapabilities capability = DesiredCapabilities.firefox();
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        driver = new ChromeDriver();  //for local check
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        tmess = new TMess();
        tmess.SetSeleniumOutStreamLog();
        tmess.NuovaFamigliaTest(TESTFAMILY);
        SeleniumUtilities.Login(driver, tmess);
    }

    private void GoTo_gestione_eventi(WebDriver driver) throws Exception {
        SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_PRAT_GESTIONE_BUTTON_CSSSELECTOR), tmess);
        SeleniumUtilities.safeClick(driver, By.xpath(WebElMap.AP_EVEN_APERTURA_EVENTI_BUTTON_XPATH), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
    }

    private void aperturaPratica() {
        Integer praticaCliccata = 1;

        List<WebElement> pulsantiAzione = SeleniumUtilities.findWebElementInCol(driver, "list", WebElMap.AP_PRAT_COLONNA_AZIONE, 1, -1, By.tagName("div"), tmess);
        pulsantiAzione.get(praticaCliccata).click();
    }

//    @Test
//    public void prova_metodo() throws Exception {
//        Boolean success = true;
//        tmess.NuovoTest(TEST_1, TESTFAMILY);
//        GoTo_gestione_eventi(driver);
//        OpenSearchPanel();
//        SeleniumUtilities.wait_for_ajax_loading(driver);
//        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_PER_COMUNE_INPUT_ID)).sendKeys("ALLEIN");
//        SeleniumUtilities.wait_for_ajax_loading(driver);
//        WebElement container1 = driver.findElement(By.cssSelector("[class = 'ui-autocomplete ui-menu ui-widget ui-widget-content ui-corner-all']"));
//        List<WebElement> optionList1 = container1.findElements(By.tagName("li"));
//        optionList1.get(0).click();
//        driver.findElement(By.id("ricerca_button")).click();
//        SeleniumUtilities.wait_for_ajax_loading(driver);
//        CleanSearch(true);
//        SeleniumUtilities.wait_for_ajax_loading(driver);
//
//        tmess.conclusione(TEST_1, TESTFAMILY, success);
//    }
    @Ignore
    @Test
    public void Ricerca_elenco_gestione_pratiche_1() throws Exception {
        Boolean success = true;
        tmess.NuovoTest(TEST_1, TESTFAMILY);
        GoTo_gestione_eventi(driver);
        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        CleanSearch(true);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletext = readTableElementsInPages();
        List<String> praticaCodes = TestUtilities.getCol(tabletext, ID);
        String firstnProtocol = praticaCodes.get(0).split("/")[0];
        Integer nPraticheProtocol = SeleniumUtilities.countOccurenceProtocolInCol(praticaCodes, firstnProtocol);
        tmess.azione("Dalla tabella  leggo il numero di protocollo della prima pratica: " + firstnProtocol);
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_PER_ID_INPUT_ID), tmess).sendKeys(firstnProtocol);
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_EVEN_START_RICERCA_BUTTON_ID), tmess);
        tmess.azione("Effettuo la ricerca secondo il numero di protocollo letto");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tabletext = readTableElementsInPages();
        praticaCodes = TestUtilities.getCol(tabletext, ID);
        tmess.azione("Verifico che tutte le pratiche presentate abbiano il numero di protocollo selezionato  ");
        if (praticaCodes.size() != nPraticheProtocol) {
            tmess.Errori("Sono state trovate " + praticaCodes.size() + " pratiche con il protocollo " + firstnProtocol + " mentre prima ne erano presenti" + nPraticheProtocol);
            success = false;
        }
        for (String praticaCode : praticaCodes) {
            String temp = praticaCode.split("/")[0];
            String tempcomp = praticaCode;
            if (!temp.equals(firstnProtocol)) {
                tmess.Errori("la pratica visualizzata: " + temp + " non corrisponde con la pratica ricercata " + firstnProtocol);
                success = false;
            }
            if (success) {
                tmess.azione("Sono state trovate le seguenti pratiche: " + tempcomp);
                tmess.risultati("Tutte le pratiche presentate hanno il numero di protocollo selezionato ");
            }
            tmess.conclusione(TEST_1, TESTFAMILY, success);
        }
    }

    @Ignore
    @Test
    public void Ricerca_elenco_gestione_pratiche_2() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_2, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_eventi(driver);
        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        CleanSearch(true);
        List<List<String>> tabletext = readTableElementsInPages();
        List<String> praticaCodes1 = TestUtilities.getCol(tabletext, 5);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<WebElement> praticaCodes = SeleniumUtilities.findWebElementInCol(driver, WebElMap.AP_PRAT_TABELLA_ID, 8, 1, -1, By.tagName("div"), tmess);
        List<List<String>> nomiPratiche = new ArrayList<List<String>>();
        for (WebElement s : praticaCodes) {
            List<String> nomepratica = SeleniumUtilities.readTestotable(s);
            nomiPratiche.add(nomepratica);
        }

        String name = nomiPratiche.get(0).get(0);
        List<String> IdTrov = SeleniumUtilities.SearchIdPratica(praticaCodes1, nomiPratiche, name);

        tmess.azione("sono state trovati i seguenti ID:");

        for (String b : IdTrov) {
            tmess.azione(b);
        }

        tmess.azione("Eseguo la ricerca manuale");
        CleanSearch(false);
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_PER_NOMINATIVO_INPUT_ID), tmess).sendKeys(name);
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_EVEN_START_RICERCA_BUTTON_ID), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<List<String>> tableTextRis = readTableElementsInPages();
        List<String> idTableTextRis = TestUtilities.getCol(tableTextRis, 5);
        tmess.azione("ricerca terminata");
        tmess.azione("scrivo il risultato della ricerca manuale:");
        for (String c : idTableTextRis) {
            tmess.azione(c);
        }
        tmess.azione("Infine verifico che gli id delle pratiche trovate coincidano");

        for (String a : idTableTextRis) {
            int i = 0;
            if (!a.equals(IdTrov.get(i))) {
                success = false;
                tmess.Errori("La pratica trovata: " + a + " non corrisponde con la pratica ricercata: " + IdTrov.get(i));
                break;
            } else {
                tmess.risultati("La pratica trovata: " + a + "corrisponde con la pratica ricercata: " + IdTrov.get(i));
            }
            i++;
        }
        if (idTableTextRis.isEmpty()) {
            success = false;
            tmess.Errori("La tabella risulta vuota!");
        }

        tmess.conclusione(TEST_2, TESTFAMILY, success);
    }

    @Ignore
    @Test

    public void ordinamento_elenco_gestione_eventi() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_3, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_eventi(driver);
        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        CleanSearch(true);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<List<String>> tabletext = readTableElementsInPages();
        List<String> elencoDate = TestUtilities.getCol(tabletext, 3);
        tmess.azione("Date attuali:");
        for (String d : elencoDate) {
            tmess.azione(d);
        }

        tmess.azione("Ordino le date in ordine crescente");
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_EVEN_ORGANIZZA_PER_DATA_BUTTON_ID), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletext1 = readTableElementsInPages();
        List<String> elencoDate1 = TestUtilities.getCol(tabletext1, 3);
        tmess.azione("Leggo le date trovate:");
        for (String d : elencoDate1) {
            tmess.azione(d);
        }
        if (TestUtilities.CheckDateCrescent(elencoDate1, true, tmess)) {
            tmess.Generico("Le date sono in ordine crescente");
        } else {
            success = false;
            tmess.warning("Le date non sono in ordine crescente!");
        }

        tmess.azione("Ordino le date in ordine decrescente");
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_EVEN_ORGANIZZA_PER_DATA_BUTTON_ID), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletext2 = readTableElementsInPages();
        List<String> elencoDate2 = TestUtilities.getCol(tabletext2, 3);
        tmess.azione("Leggo le date trovate:");
        for (String d : elencoDate2) {
            tmess.azione(d);
        }
        if (TestUtilities.CheckDateCrescent(elencoDate2, false, tmess)) {
            tmess.Generico("Le date sono in ordine decrescente");
        } else {
            success = false;
            tmess.warning("Le date non sono in ordine decrescente!");
        }

        List<List<String>> tabletext3 = readTableElementsInPages();
        List<String> elencoOggetti = TestUtilities.getCol(tabletext3, 4);
        tmess.azione("Oggetti attuali:");
        for (String o : elencoOggetti) {
            tmess.azione(o);
        }

        tmess.azione("Ordino gli oggetti in ordine crescente");
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_EVEN_ORGANIZZA_PER_OGGETTO_BUTTON_ID), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletext4 = readTableElementsInPages();
        List<String> elencoOggetti1 = TestUtilities.getCol(tabletext4, 4);
        tmess.azione("Leggo gli oggetti trovati:");
        for (String o : elencoOggetti1) {
            tmess.azione(o);
        }
        if (TestUtilities.isSortedAlphabetically(elencoOggetti1, true)) {
            tmess.Generico("Gli oggetti sono in ordine crescente");
        } else {
            success = false;
            tmess.warning("Gli oggetti non sono in ordine crescente!");
        }

        tmess.azione("Ordino gli oggetti in ordine decrescente");
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_EVEN_ORGANIZZA_PER_OGGETTO_BUTTON_ID), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletext5 = readTableElementsInPages();
        List<String> elencoOggetti2 = TestUtilities.getCol(tabletext5, 4);
        tmess.azione("Leggo gli oggetti trovati:");
        for (String o : elencoOggetti2) {
            tmess.azione(o);
        }
        if (TestUtilities.isSortedAlphabetically(elencoOggetti2, false)) {
            tmess.Generico("Gli oggetti sono in ordine decrescente");
        } else {
            success = false;
            tmess.warning("Gli oggetti non sono in ordine decrescente!");
        }

        List<List<String>> tabletext6 = readTableElementsInPages();
        List<String> elencoStati = TestUtilities.getCol(tabletext6, 1);
        tmess.azione("Stati attuali:");
        for (String s : elencoStati) {
            tmess.azione(s);
        }

        tmess.azione("Ordino gli stati in ordine crescente");
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_EVEN_ORGANIZZA_PER_STATO_PRATICA_BUTTON_ID), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletext7 = readTableElementsInPages();
        List<String> elencoStati1 = TestUtilities.getCol(tabletext7, 1);
        tmess.azione("Leggo gli stati trovati:");
        for (String s : elencoStati1) {
            tmess.azione(s);
        }
        if (TestUtilities.isSortedAlphabetically(elencoStati1, true)) {
            tmess.Generico("Gli stati sono in ordine crescente");
        } else {
            success = false;
            tmess.warning("Gli stati non sono in ordine crescente!");
        }

        tmess.azione("Ordino gli stati in ordine decrescente");
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_EVEN_ORGANIZZA_PER_STATO_PRATICA_BUTTON_ID), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletext8 = readTableElementsInPages();
        List<String> elencoStati2 = TestUtilities.getCol(tabletext8, 1);
        tmess.azione("Leggo gli stati trovati:");
        for (String s : elencoStati2) {
            tmess.azione(s);
        }
        if (TestUtilities.isSortedAlphabetically(elencoStati2, false)) {
            tmess.Generico("Gli stati sono in ordine decrescente");
        } else {
            success = false;
            tmess.warning("Gli stati non sono in ordine decrescente!");
        }

        tmess.conclusione(TEST_3, TESTFAMILY, success);
    }

    @Ignore
    @Test
    public void ordinamento_elenco_gestione_eventi_data_sel() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_4, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_eventi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        CleanSearch(true);

        List<List<String>> tabletext = readTableElementsInPages();
        List<String> elencoDate = TestUtilities.getCol(tabletext, 3);
        tmess.azione("Date attuali:");
        for (String d : elencoDate) {
            tmess.azione(d);
        }

        List<String> firstData = TestUtilities.switchFromDateToDateSearchInput(elencoDate.get(1), tmess);
        List<String> secondData = TestUtilities.switchFromDateToDateSearchInput(elencoDate.get(2), tmess);
        tmess.azione("Eseguo la ricerca da " + elencoDate.get(1) + " a " + elencoDate.get(2));
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_EVEN_RICERCA_SELEZIONA_DATA_DA_INPUT_ID), tmess);
        tmess.azione("Seleziono il mese di inizio:" + firstData.get(1));
        new Select(SeleniumUtilities.safeFind(driver, By.className(WebElMap.AP_PRAT_SELEZIONA_MESE_INPUT_CSSSELECTOR), tmess)).selectByVisibleText(firstData.get(1));
        tmess.azione("Seleziono l'anno di inizio:" + firstData.get(2));
        new Select(SeleniumUtilities.safeFind(driver, By.className(WebElMap.AP_PRAT_SELEZIONA_ANNO_INPUT_CSSSELECTOR), tmess)).selectByVisibleText(firstData.get(2));
        tmess.azione("Seleziono il giorno di inizio:" + firstData.get(0));
        SeleniumUtilities.safeClick(driver, By.linkText(firstData.get(0)), tmess);
        SeleniumUtilities.waitForElToDisappear(driver, By.id(WebElMap.AP_EVEN_RICERCA_SELEZIONA_DATA_INPUT_ID), tmess);
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_EVEN_RICERCA_SELEZIONA_DATA_A_INPUT_ID), tmess);
        tmess.azione("Seleziono il mese di fine:" + secondData.get(1));
        new Select(SeleniumUtilities.safeFind(driver, By.className(WebElMap.AP_PRAT_SELEZIONA_MESE_INPUT_CSSSELECTOR), tmess)).selectByVisibleText(secondData.get(1));
        tmess.azione("Seleziono l'anno di fine:" + secondData.get(2));
        new Select(SeleniumUtilities.safeFind(driver, By.className(WebElMap.AP_PRAT_SELEZIONA_ANNO_INPUT_CSSSELECTOR), tmess)).selectByVisibleText(secondData.get(2));
        tmess.azione("Seleziono il giorno di fine:" + secondData.get(0));
        SeleniumUtilities.safeClick(driver, By.linkText(secondData.get(0)), tmess);
        tmess.azione("Effettuo la ricerca secondo l'intervallo selezionato ");
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_PRAT_START_RICERCA_BUTTON_ID), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tabletext = readTableElementsInPages();
        List<String> elencoDateRis = TestUtilities.getCol(tabletext, 3);
        tmess.azione("Date trovate:");
        for (String d : elencoDateRis) {
            tmess.azione(d);
        }

        tmess.azione("Verifico che tutte le pratiche si trovino nell'intervallo selezionato  ");
        for (String DataRis : elencoDateRis) {
            if (!SeleniumUtilities.checkDateInterval(elencoDate.get(1), elencoDate.get(2), DataRis)) {
                tmess.Errori("La pratica visualizzata: " + DataRis + " non appartiene all'intervallo ricercato " + firstData + "-" + secondData);
                success = false;
            }

        }

        if (success) {
            tmess.risultati("Le pratiche visualizzate sono tutte e sole quelle con data nell'intervallo selezionato ");
        }

        tmess.conclusione(TEST_4, TESTFAMILY, success);
    }

    @Ignore
    @Test
    public void ordinamento_elenco_gestione_eventi_data() throws Exception {

        boolean success = true;
        tmess.NuovoTest(TEST_5, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_eventi(driver);
        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        CleanSearch(true);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<List<String>> tabletext = readTableElementsInPages();
        List<String> elencoDate = TestUtilities.getCol(tabletext, 3);
        tmess.azione("Date attuali:");
        for (String d : elencoDate) {
            tmess.azione(d);
        }

        String firstData = elencoDate.get(1);
        String secondData = elencoDate.get(0);
        tmess.azione("Eseguo la ricerca da " + firstData + " a " + secondData);
        CleanSearch(false);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_SELEZIONA_DATA_DA_INPUT_ID), tmess).sendKeys(firstData);
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_SELEZIONA_DATA_A_INPUT_ID), tmess).sendKeys(secondData);
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_PRAT_START_RICERCA_BUTTON_ID), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tmess.azione("Date trovate:");
        tabletext = readTableElementsInPages();
        List<String> elencoDateRis = TestUtilities.getCol(tabletext, 3);
        for (String d : elencoDateRis) {
            tmess.azione(d);
        }

        tmess.azione("Verifico che tutte le pratiche si trovino nell'intervallo selezionato ");
        for (String DataRis : elencoDateRis) {
            if (!SeleniumUtilities.checkDateInterval(firstData, secondData, DataRis)) {
                tmess.Errori("La pratica visualizzata: " + DataRis + " non appartiene all'intervallo ricercato " + firstData + "-" + secondData);
                success = false;
            }

        }

        if (success) {
            tmess.risultati("Le pratiche visualizzate sono tutte e sole quelle con data nell'intervallo selezionato ");
        }

        tmess.conclusione(TEST_5, TESTFAMILY, success);

    }

    @Ignore
    @Test
    public void ordinamento_elenco_gestione_eventi_comune() throws Exception {

        boolean success = true;
        tmess.NuovoTest(TEST_6, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_eventi(driver);
        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        CleanSearch(true);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        String ricerca = "ALLE";
        tmess.azione("Scrivo " + ricerca + " su ricerca comune");
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_PER_COMUNE_INPUT_ID), tmess).sendKeys(ricerca);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        WebElement container = driver.findElement(By.cssSelector("[class = 'ui-autocomplete ui-menu ui-widget ui-widget-content ui-corner-all']"));
        List<WebElement> optionList = container.findElements(By.tagName("li"));
        List<String> Comuni = new ArrayList<String>();
        for (WebElement w : optionList) {
            Comuni.add(w.getText());
        }

        tmess.azione("Controllo se " + ricerca + " è all'interno di ogni comune trovato");
        for (String c : Comuni) {
            if (!SeleniumUtilities.CheckSubstring(c, ricerca, true)) {
                tmess.warning("Il comune " + c + " non ccontiene " + ricerca);
                success = false;
            }
        }
        if (success) {
            tmess.Generico("Tutti i comuni trovati contengo " + ricerca);
        }

        tmess.azione("Controllo se " + ricerca + " è all'inizio di ogni comune trovato");
        for (String c : Comuni) {
            if (!SeleniumUtilities.CheckSubstring(c, ricerca, false)) {
                success = false;
            }
        }

        if (!success) {
            tmess.warning("Ci sono dei comuni che non iniziano per " + ricerca);
        }

        //      Eseguo il clean della ricerca. 
        CleanSearch(true);

        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletext = readTableElementsInPages();
        List<String> listaComuni = TestUtilities.getCol(tabletext, COMUNE);

//      Controllo dei nominativi della lista.        
//        TMess.azione("Leggo i valori dell'ente delle pratiche, che sono: ");
//      for (String ComuniInList : listaComuni) {
//          TMess.azione(ComuniInList);
//      }
//      Selezione Stato Pratica.
        String primoComune = listaComuni.get(1);
        List<String> veroRisultato = TestUtilities.selectBy(tabletext, ID, COMUNE, primoComune);
        tmess.azione("Dalla tabella  leggo il comune della prima pratica:   " + primoComune);
        Integer nSelectedComune = SeleniumUtilities.countOccurenceInCol(listaComuni, primoComune);
        tmess.azione("Conto le pratiche con il comune selezionato:   " + veroRisultato.size());
        tmess.azione("Sono visibili :   " + nSelectedComune.toString() + " pratiche con il comune selezionato.");

        driver.findElement(By.id("search_des_comune")).sendKeys(primoComune);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        WebElement container1 = driver.findElement(By.cssSelector("[class = 'ui-autocomplete ui-menu ui-widget ui-widget-content ui-corner-all']"));
        List<WebElement> optionList1 = container1.findElements(By.tagName("li"));

        optionList1.get(0).click();
        driver.findElement(By.id("ricerca_button")).click();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tmess.azione("Effettuo la ricerca manuale secondo il comune selezionato");

        List<List<String>> tabletextManual = readTableElementsInPages();
        List<String> comunePraticaManual = TestUtilities.getCol(tabletextManual, COMUNE);
        List<String> risultatoRicercaManual = TestUtilities.getCol(tabletextManual, ID);

//      Controllo dei nominativi della ricerca Manuale.        
//        TMess.azione("Leggo i valori del comune delle pratiche, che sono: ");
//      for (String ComuniInListManual : comunePraticaManual) {
//          TMess.azione(ComuniInListManual);
//      }
//     Controllo tra elementi trovati
        tmess.azione("Confronto il numero di pratiche trovate:" + comunePraticaManual.size());
        if (comunePraticaManual.size() != nSelectedComune) {
            tmess.Errori("Il numero di pratiche trovato: " + comunePraticaManual.size() + " non corrisponde a quello delle pratiche di partenza aventi il comune" + primoComune);
            success = false;
        } else {
            tmess.risultati("Il numero di pratiche coincide");
        }

//     Controllo per Comune
        tmess.azione("Verifico che tutte le pratiche presentate abbiano come Comune quello selezionato  ");
        for (String comuneSingolo : comunePraticaManual) {
            if (!comuneSingolo.equals(primoComune)) {
                tmess.Errori("il comune della pratica visualizzato: " + comuneSingolo + " non corrisponde con il comune della pratica ricercato: " + primoComune);
                success = false;
            } else {
                tmess.risultati("il comune delle pratiche coincide");
                break;
            }
        }
//     Controllo per ID e Comune
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tmess.azione("Infine verifico che gli id delle pratiche trovate coincidano");
        if (!TestUtilities.checkIfListSetIsPresent(risultatoRicercaManual, veroRisultato, tmess)) {
            tmess.Errori("Un risultato che non dovrebbe essere presente viene invece visualizzato.");
            success = false;
        }
        if (success) {
            tmess.risultati("Le pratiche visualizzate tramite ricerca sono esattamente tutte e solamente quelle aventi per comune quello selezionato.");
        }

        tmess.conclusione(TEST_6, TESTFAMILY, success);

    }

    @Ignore
    @Test

    public void ordinamento_elenco_gestione_eventi_anagrafica_IVA() throws Exception {

        boolean success = true;
        tmess.NuovoTest(TEST_7, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_eventi(driver);
        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        CleanSearch(true);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tmess.azione("Apro la prima pratica che trovo");
        clickAzione(0);
        tmess.azione("Leggo la prima partita iva");

        WebElement tabellaAnagrafiche = SeleniumUtilities.safeFind(driver, By.tagName("table"), tmess);
        List<WebElement> trAnagrafiche = tabellaAnagrafiche.findElements(By.tagName("td"));
        List<List<List<String>>> anagraficheCollegate = new ArrayList<List<List<String>>>();
        for (WebElement tr : trAnagrafiche) {
            List<List<String>> anagrafica = SeleniumUtilities.readLebelledInputMask(tr);
            anagraficheCollegate.add(anagrafica);
        }

        String partIVA = anagraficheCollegate.get(2).get(1).get(2);

        tmess.azione("Faccio la ricerca con la seguente partita iva: " + partIVA);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_eventi(driver);
        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        CleanSearch(false);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_PER_ANAGRAFE_INPUT_ID), tmess).sendKeys(partIVA);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_PRAT_START_RICERCA_BUTTON_ID), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tmess.azione("Controllo le pratiche trovate se hanno la seguente partita iva: " + partIVA);
        Boolean checkIva = true;
        for (int i = 0; i < readTableElementsInPages().size(); i++) {
            clickAzione(i);
            tabellaAnagrafiche = SeleniumUtilities.safeFind(driver, By.tagName("table"), tmess);
            trAnagrafiche = tabellaAnagrafiche.findElements(By.tagName("td"));
            anagraficheCollegate = new ArrayList<List<List<String>>>();
            for (WebElement tr : trAnagrafiche) {
                List<List<String>> anagrafica = SeleniumUtilities.readLebelledInputMask(tr);
                anagraficheCollegate.add(anagrafica);
            }
            for (List<List<String>> Label : anagraficheCollegate) {
                for (int c = 0; c < Label.get(0).size(); c++) {
                    if (Label.get(0).get(c).equals("Partita IVA")) {
                        if (Label.get(1).get(c).equals(partIVA)) {
                            tmess.Generico("La pratica numero: " + i + 1 + " ha la giusta partita iva");
                        } else {
                            tmess.warning("La pratica numero: " + i + 1 + " non ha la giusta partita iva");
                            success = false;
                        }
                        checkIva = false;
                    }
                }
            }
            if (checkIva) {
                tmess.warning("La pratica numero: " + i + 1 + " non ha partita iva");
                success = false;
            } else {
                checkIva = true;
            }

            SeleniumUtilities.HomePage(driver);
            GoTo_gestione_eventi(driver);
            OpenSearchPanel();
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            CleanSearch(false);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_PER_ANAGRAFE_INPUT_ID), tmess).sendKeys(partIVA);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_PRAT_START_RICERCA_BUTTON_ID), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        }

        tmess.conclusione(TEST_7, TESTFAMILY, success);
    }

    @Ignore
    @Test

    public void ordinamento_elenco_gestione_eventi_anagrafica_CF() throws Exception {

        boolean success = true;
        tmess.NuovoTest(TEST_8, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_eventi(driver);
        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        CleanSearch(true);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tmess.azione("Apro la prima pratica che trovo");
        clickAzione(1);
        tmess.azione("Leggo il primo codice fiscale");

        WebElement tabellaAnagrafiche = SeleniumUtilities.safeFind(driver, By.tagName("table"), tmess);
        List<WebElement> trAnagrafiche = tabellaAnagrafiche.findElements(By.tagName("td"));
        List<List<List<String>>> anagraficheCollegate = new ArrayList<List<List<String>>>();
        for (WebElement tr : trAnagrafiche) {
            List<List<String>> anagrafica = SeleniumUtilities.readLebelledInputMask(tr);
            anagraficheCollegate.add(anagrafica);
        }

        String codF = anagraficheCollegate.get(0).get(1).get(3);

        tmess.azione("Faccio la ricerca con il seguente codice fiscale: " + codF);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_eventi(driver);
        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        CleanSearch(false);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_PER_ANAGRAFE_INPUT_ID), tmess).sendKeys(codF);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_PRAT_START_RICERCA_BUTTON_ID), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        int row = readTableElementsInPages().size();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tmess.azione("Controllo le pratiche trovate se hanno il seguente codice fiscale: " + codF);
        Boolean checkCodF = true;
        int v = 0;
        for (int i = 0; i < row; i++) {
            v++;
            clickAzione(i);
            tabellaAnagrafiche = SeleniumUtilities.safeFind(driver, By.tagName("table"), tmess);
            trAnagrafiche = tabellaAnagrafiche.findElements(By.tagName("td"));
            anagraficheCollegate = new ArrayList<List<List<String>>>();
            for (WebElement tr : trAnagrafiche) {
                List<List<String>> anagrafica = SeleniumUtilities.readLebelledInputMask(tr);
                anagraficheCollegate.add(anagrafica);
            }
            for (List<List<String>> Label : anagraficheCollegate) {
                for (int c = 0; c < Label.get(0).size(); c++) {
                    if (Label.get(0).get(c).equals("Codice fiscale")
                            && Label.get(1).get(c).equals(codF)
                            && checkCodF) {
                        tmess.Generico("La pratica numero: " + v + " ha il giusto codice fiscale");
                        checkCodF = false;
                    }
                }
            }
            if (checkCodF) {
                tmess.warning("La pratica numero: " + v + " non ha il giusto codice fiscale");
                success = false;
            } else {
                checkCodF = true;
            }

            SeleniumUtilities.HomePage(driver);
            GoTo_gestione_eventi(driver);
            OpenSearchPanel();
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            CleanSearch(false);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_PER_ANAGRAFE_INPUT_ID), tmess).sendKeys(codF);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_PRAT_START_RICERCA_BUTTON_ID), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        }

        tmess.conclusione(TEST_8, TESTFAMILY, success);
    }

    @Ignore
    @Test
    public void Ricerca_elenco_gestione_pratiche_6() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_10, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_eventi(driver);

//      Eseguo il clean della ricerca.        
        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        CleanSearch(true);

        List<List<String>> tabletext = readTableElementsInPages();
        List<String> statoPratica = TestUtilities.getCol(tabletext, STATO);

//      Controllo dei nominativi della lista.        
//      TMess.azione("Leggo i valori dello stato delle pratiche, che sono: ");
//      for (String statprat : statoPratica) {
//          TMess.azione(statprat);
//      }
//      Selezione Stato Pratica.
        String primoStatoPratica = statoPratica.get(0);
        List<String> veroRisultato = TestUtilities.selectBy(tabletext, ID, STATO, primoStatoPratica);
        tmess.azione("Dalla tabella  leggo lo stato della prima pratica:   " + primoStatoPratica);
        Integer nSelectedStatoPratica = SeleniumUtilities.countOccurenceInCol(statoPratica, primoStatoPratica);
        tmess.azione("Conto le pratiche con lo stato selezionato:   " + veroRisultato.size());
        tmess.azione("Sono visibili :   " + nSelectedStatoPratica.toString() + " pratiche con lo stato selezionato.");

//      Ricerca Manuale.       
        CleanSearch(false);
        new Select(driver.findElement(By.id("search_stato"))).selectByVisibleText(primoStatoPratica.toString());
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_EVEN_START_RICERCA_BUTTON_ID), tmess);
        tmess.azione("Effettuo la ricerca manuale secondo lo stato selezionato");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletextManual = readTableElementsInPages();
        List<String> statoPraticaManual = TestUtilities.getCol(tabletextManual, STATO);
        List<String> risultatoRicercaManual = TestUtilities.getCol(tabletextManual, ID);

//      Controllo dei nominativi della ricerca manuale.
//      TMess.azione("I Risultati della ricerca al valore Aperta sono: ");
//      for (String x : statoPraticaManual) {
//         TMess.azione(x);
//      }
        tmess.azione("Confronto il numero di pratiche trovate:" + statoPraticaManual.size());
        if (statoPraticaManual.size() != nSelectedStatoPratica) {
            tmess.Errori("Il numero di pratiche trovato: " + statoPraticaManual.size() + " non corrisponde a quello delle pratiche di partenza aventi lo stato" + primoStatoPratica);
            success = false;
        } else {
            tmess.risultati("Il numero di pratiche coincide");
        }

        tmess.azione("Verifico che tutte le pratiche presentate abbiano come stato quello selezionato  ");
        for (String statoSingolo : statoPraticaManual) {
            if (!statoSingolo.equals(primoStatoPratica)) {
                tmess.Errori("l'ente della pratica visualizzato: " + statoSingolo + " non corrisponde con lo stato della pratica ricercato " + primoStatoPratica);
                success = false;
            } else {
                tmess.risultati("Lo stato delle pratiche coincide");
                break;
            }
        }

        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tmess.azione("Infine verifico che gli id delle pratiche trovate coincidano");
        if (!TestUtilities.checkIfListSetIsPresent(risultatoRicercaManual, veroRisultato, tmess)) {
            tmess.Errori("Un risultato che non dovrebbe essere presente viene invece visualizzato.");
            success = false;
        }
        if (success) {
            tmess.risultati("Le pratiche visualizzate tramite ricerca sono esattamente tutte e solamente quelle aventi per stato quello selezionato.");
        }

        tmess.conclusione(TEST_9, TESTFAMILY, success);
    }

    @Ignore
    @Test
    public void Ricerca_elenco_gestione_pratiche_7() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_7, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_eventi(driver);

//      Eseguo il clean della ricerca.        
        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        CleanSearch(true);

        List<List<String>> tabletext = readTableElementsInPages();
        List<String> listaEnti = TestUtilities.getCol(tabletext, ENTE);

//      Controllo dei nominativi della lista.        
//        TMess.azione("Leggo i valori dell'ente delle pratiche, che sono: ");
//       for (String enteInList : listaEnti) {
//          TMess.azione(enteInList);
//      }
//      Selezione Stato Pratica.
        String primoEnte = listaEnti.get(0);
        List<String> veroRisultato = TestUtilities.selectBy(tabletext, ID, ENTE, primoEnte);
        tmess.azione("Dalla tabella  leggo l'ente della prima pratica:   " + primoEnte);
        Integer nSelectedEnte = SeleniumUtilities.countOccurenceInCol(listaEnti, primoEnte);
        tmess.azione("Conto le pratiche con l'ente selezionato:   " + veroRisultato.size());
        tmess.azione("Sono visibili :   " + nSelectedEnte.toString() + " pratiche con l'ente selezionato.");

//      Ricerca Manuale.             
        CleanSearch(false);
        new Select(driver.findElement(By.id("search_ente"))).selectByVisibleText(primoEnte.toString());
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_EVEN_START_RICERCA_BUTTON_ID), tmess);
        tmess.azione("Effettuo la ricerca manuale secondo lo stato selezionato");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletextManual = readTableElementsInPages();
        List<String> entePraticaManual = TestUtilities.getCol(tabletextManual, ENTE);
        List<String> risultatoRicercaManual = TestUtilities.getCol(tabletextManual, ID);

//      Controllo dei nominativi della ricerca manuale.
//      TMess.azione("Leggo i valori dell'ente delle pratiche dalla ricerca manuale, che sono: ");
//      for (String x : entePraticaManual) {
//          TMess.azione(x);
//      }
        tmess.azione("Confronto il numero di pratiche trovate:" + entePraticaManual.size());
        if (entePraticaManual.size() != nSelectedEnte) {
            tmess.Errori("Il numero di pratiche trovato: " + entePraticaManual.size() + " non corrisponde a quello delle pratiche di partenza aventi lo stato" + primoEnte);
            success = false;
        } else {
            tmess.risultati("Il numero di pratiche coincide");
        }

        tmess.azione("Verifico che tutte le pratiche presentate abbiano come Ente quello selezionato  ");
        for (String enteSingolo : entePraticaManual) {
            if (!enteSingolo.equals(primoEnte)) {
                tmess.Errori("l'ente della pratica visualizzato: " + enteSingolo + " non corrisponde con lo stato della pratica ricercato: " + primoEnte);
                success = false;
            } else {
                tmess.risultati("L'Ente delle pratiche coincide");
                break;
            }
        }

        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tmess.azione("Infine verifico che gli id delle pratiche trovate coincidano");
        if (!TestUtilities.checkIfListSetIsPresent(risultatoRicercaManual, veroRisultato, tmess)) {
            tmess.Errori("Un risultato che non dovrebbe essere presente viene invece visualizzato.");
            success = false;
        }
        if (success) {
            tmess.risultati("Le pratiche visualizzate tramite ricerca sono esattamente tutte e solamente quelle aventi per stato quello selezionato.");
        }

        tmess.conclusione(TEST_10, TESTFAMILY, success);

    }

    @Test

    public void gestione_eventi_selezione_pratiche() throws Exception {

        boolean success = true;
        tmess.NuovoTest(TEST_11, TESTFAMILY);

        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_eventi(driver);
        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        CleanSearch(true);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<List<String>> tabletext = readTableElementsInPages();
        Random generator = new Random();
        Integer n = generator.nextInt(tabletext.size());
        tmess.azione("Il protoccollo numero " + n + " ha:");

        List<String> praticaCodes = TestUtilities.getCol(tabletext, ID);
        String number = praticaCodes.get(n).split("/")[0];
        tmess.azione("Questo numero di protocollo: " + number);

        List<WebElement> praticaNomi = SeleniumUtilities.findWebElementInCol(driver, WebElMap.AP_PRAT_TABELLA_ID, 8, 1, -1, By.tagName("div"), tmess);
        List<List<String>> nomiPratiche = new ArrayList<List<String>>();
        for (WebElement s : praticaNomi) {
            List<String> nomepratica = SeleniumUtilities.readTestotable(s);
            nomiPratiche.add(nomepratica);
        }
        String name = nomiPratiche.get(n).get(0);
        tmess.azione("Questo nome anagrafica: " + name);

        List<String> elencoDate = TestUtilities.getCol(tabletext, 3);
        String data = elencoDate.get(n);
        tmess.azione("Questa data: " + data);

        List<String> listaComuni = TestUtilities.getCol(tabletext, COMUNE);
        String comune = listaComuni.get(n);
        tmess.azione("Questo comune: " + comune);

        List<String> statoPratica = TestUtilities.getCol(tabletext, STATO);
        String stato = statoPratica.get(n);
        tmess.azione("Questo stato: " + stato);

        List<String> listaEnti = TestUtilities.getCol(tabletext, ENTE);
        String ente = listaEnti.get(n);
        tmess.azione("Questo ente: " + ente);

        clickAzione(n);
        WebElement tabellaAnagrafiche = SeleniumUtilities.safeFind(driver, By.tagName("table"), tmess);
        List<WebElement> trAnagrafiche = tabellaAnagrafiche.findElements(By.tagName("td"));
        List<List<List<String>>> anagraficheCollegate = new ArrayList<List<List<String>>>();
        for (WebElement tr : trAnagrafiche) {
            List<List<String>> anagrafica = SeleniumUtilities.readLebelledInputMask(tr);
            anagraficheCollegate.add(anagrafica);
        }
        String anagrafica;
        if (anagraficheCollegate.get(0).get(0).get(2).equals("Partita IVA")) {
            anagrafica = anagraficheCollegate.get(0).get(1).get(2);
            tmess.azione("Questa partita iva: " + anagrafica);
        } else {
            anagrafica = anagraficheCollegate.get(0).get(1).get(3);
            tmess.azione("Questo codice fiscale: " + anagrafica);
        }

        tmess.azione("Inserisco i dati nel pannello di ricerca ed eseguo la ricerca");
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_eventi(driver);
        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        CleanSearch(true);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_PER_ID_INPUT_ID), tmess).sendKeys(number);
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_PER_NOMINATIVO_INPUT_ID), tmess).sendKeys(name);
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_PER_COMUNE_INPUT_ID), tmess).sendKeys(comune);
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_PER_ANAGRAFE_INPUT_ID), tmess).sendKeys(anagrafica);
        new Select(driver.findElement(By.id("search_stato"))).selectByVisibleText(stato.toString());
        new Select(driver.findElement(By.id("search_ente"))).selectByVisibleText(ente.toString());
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_SELEZIONA_DATA_DA_INPUT_ID), tmess).sendKeys(data);
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_SELEZIONA_DATA_A_INPUT_ID), tmess).sendKeys(data);
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_EVEN_START_RICERCA_BUTTON_ID), tmess);

        tmess.azione("Leggo l' ID della pratica trovata");
        tabletext = readTableElementsInPages();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        praticaCodes = TestUtilities.getCol(tabletext, ID);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        if (praticaCodes.size() <= 0) {
            tmess.warning("Non sono state trovate pratiche!");
            success = false;
        } else if (praticaCodes.size() > 2) {
            tmess.warning("Sono state trovate più di una pratica!");
            success = false;
        } else {
            String numberTrov = praticaCodes.get(0).split("/")[0];
            if (numberTrov.equals(number)) {
                tmess.Generico("L' ID della pratica trovata coincide con quella cercata");
            } else {
                tmess.warning("L' ID della pratica trovata non coincide con quella cercata");
                success = false;
            }
        }

        tmess.conclusione(TEST_11, TESTFAMILY, success);
    }

    static public List<List<String>> readTableElementsInPages() throws Exception {
        WebElement tel = SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_CONTAPAGINE_ID), tmess);
        String temp = tel.getText();
        Integer pageNumber = Integer.parseInt(temp);
        tmess.azione("page number" + pageNumber.toString());
        List<List<String>> result = SeleniumUtilities.readTableElementsInPages(driver,
                By.cssSelector(WebElMap.AP_EVEN_VAI_ALLA_PAGINA_SUCCESSIVA_BUTTON_CSSSELECTOR),
                By.cssSelector(WebElMap.AP_EVEN_VAI_ALLA_PRIMA_PAGINA_BUTTON_CSSSELECTOR),
                WebElMap.AP_EVEN_TABELLA_ID,
                WebElMap.AP_EVEN_TABELLA_CAMPO_DA_LEGGERE,
                pageNumber, tmess);
        return result;
    }

//    static public void clickAzione(WebDriver driver, int number) throws Exception {
//        number++;
//        List<WebElement> textTable = driver.findElements(By.tagName("table"));
//        List<WebElement> listatr = textTable.get(1).findElements(By.tagName("tr"));
//        TMess.azione("num prat: " + listatr.size());
//        List<WebElement> listatd = listatr.get(number).findElements(By.tagName("td"));
//        TMess.azione("num col: " + listatd.size());
//        WebElement button = listatd.get(10).findElement(By.tagName("button"));
//        SeleniumUtilities.wait_for_ajax_loading(driver);
//        
//
//    }
//    
    private void clickAzione(Integer npratica) {

        List<WebElement> pulsantiAzione = SeleniumUtilities.findWebElementInCol(driver, "list", 10, 1, -1, By.tagName("div"), tmess);
        pulsantiAzione.get(npratica).click();
    }

    @AfterClass

    static public void tearDown() throws Exception {
        driver.quit();
    }

    private void OpenSearchPanel() {
        SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_EVEN_APRI_PANNELLO_RICERCA_BUTTON_CSSSELECTOR), tmess);
    }

    private void CleanSearch(Boolean commit) throws Exception {
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_PER_ANAGRAFE_INPUT_ID), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_PER_NOMINATIVO_INPUT_ID), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_PER_ID_INPUT_ID), tmess).clear();
        new Select(SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_SELEZIONA_ENTE_SELECTOR_ID), tmess)).selectByVisibleText("Qualisiasi ente");
        new Select(SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_SELEZIONA_STATO_SELECTOR_ID), tmess)).selectByVisibleText("Qualsiasi stato");
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_SELEZIONA_DATA_DA_INPUT_ID), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_SELEZIONA_DATA_A_INPUT_ID), tmess).clear();
        WebElement check = SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_VISUALIZZA_TUTTE_PARTICHE_CHECKBOX_ID), tmess);
        if (check.isSelected()) {
            tmess.azione("premuto");
            SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_EVEN_VISUALIZZA_TUTTE_PARTICHE_CHECKBOX_ID), tmess);
        } else {
            SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_EVEN_VISUALIZZA_TUTTE_PARTICHE_CHECKBOX_ID), tmess);
            SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_EVEN_VISUALIZZA_TUTTE_PARTICHE_CHECKBOX_ID), tmess);
        }
        SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_EVEN_CLEAN_COMUNE_BUTTON_CSS), tmess);
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_PER_FOGLIO_INPUT_ID), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_PER_MAPPALE_INPUT_ID), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_RICERCA_PER_SUBALTERNO_INPUT_ID), tmess).clear();
        if (commit) {
            tmess.azione("faccio partire la ricerca");
            SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_EVEN_START_RICERCA_BUTTON_ID), tmess);
        }
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
