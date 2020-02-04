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
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

public class GestioneTemplatesTest {

    static final private String TESTFAMILY = "GestioneTemplates";
    static final private String TEST_1 = "Ricerca_templates_1";
    static final private String TEST_2 = "Ricerca_templates_2";
    static final private String TEST_3 = "Ricerca_templates_3";
    static final private String TEST_4 = "Ordinamento_templates";
    static final private String TEST_5 = "Aggiungi_templates_1";
    static final private String TEST_6 = "Scarica_file_nuovo_template";
    static final private String TEST_7 = "Aggiungi_Record";
    static final private String TEST_8 = "Modifica_Record";
    private static ChromeDriver driver;
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

    private void GoTo_gestione_templates(WebDriver driver) throws Exception {
        SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_ENTI_APERTURA_ENTI_BUTTON_CSS), tmess);
        SeleniumUtilities.safeClick(driver, By.xpath(WebElMap.AP_ENTI_APERTURA_TEMPLATES_BUTTON_XPATH), tmess);
        OpenSearchPanel();
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

    }

    @Ignore
    @Test
    public void Ricerca_templates_1() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_1, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_templates(driver);
        List<List<String>> textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        String evento = textable.get(0).get(2);

        tmess.azione("Faccio una ricerca attraverso l'Evento " + evento + " e controllo i dati trovati");
        new Select(SeleniumUtilities.safeFind(driver, By.id("listaEventi"), tmess)).selectByVisibleText(evento);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        for (String e : TestUtilities.getCol(textable, 2)) {
            if (!e.equals(evento)) {
                tmess.Errori("Gli eventi trovati non sono coerenti con la ricerca");
                success = false;
                break;
            }
        }

        tmess.conclusione(TEST_1, TESTFAMILY, success);
    }

    @Ignore
    @Test
    public void Ricerca_templates_2() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_2, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_templates(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        String evento = textable.get(0).get(4);

        tmess.azione("Faccio una ricerca attraverso l'Ente " + evento + " e controllo i dati trovati");
        new Select(SeleniumUtilities.safeFind(driver, By.id("listaEnti"), tmess)).selectByVisibleText(evento);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        for (String e : TestUtilities.getCol(textable, 4)) {
            if (!e.equals(evento)) {
                tmess.Errori("Gli enti trovati non sono coerenti con la ricerca");
                success = false;
                break;
            }
        }

        tmess.conclusione(TEST_2, TESTFAMILY, success);
    }

    @Ignore
    @Test
    public void Ricerca_templates_3() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_3, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_templates(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<List<String>> textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        String evento = textable.get(0).get(10);

        tmess.azione("Faccio una ricerca attraverso il Template " + evento + " e controllo i dati trovati");
        new Select(SeleniumUtilities.safeFind(driver, By.id("idTemplate"), tmess)).selectByVisibleText(evento);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        for (String e : TestUtilities.getCol(textable, 10)) {
            if (!e.equals(evento)) {
                tmess.Errori("Gli enti trovati non sono coerenti con la ricerca");
                success = false;
                break;
            }
        }
        tmess.conclusione(TEST_3, TESTFAMILY, success);
    }

    @Ignore
    @Test
    public void Ordinamento_templates() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_4, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_templates(driver);

        tmess.azione("Ordino in modo crescente gli Eventi e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_descEvento"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        List<String> elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(2));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, true)) {
            tmess.Generico("Gli Eventi sono in ordine crescente");
        } else {
            success = false;
            tmess.Errori("Gli Eventi non sono in ordine crescente!");
        }

        tmess.azione("Ordino in modo decrescente gli Eventi e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_descEvento"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(2));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, false)) {
            tmess.Generico("Gli Eventi sono in ordine decrescente");
        } else {
            success = false;
            tmess.Errori("Gli Eventi non sono in ordine decrescente!");
        }

        tmess.azione("Ordino in modo crescente gli Enti e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_descEnte"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(4));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, true)) {
            tmess.Generico("Gli Enti sono in ordine crescente");
        } else {
            success = false;
            tmess.Errori("Gli Enti non sono in ordine crescente!");
        }

        tmess.azione("Ordino in modo decrescente gli Enti e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_descEnte"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(4));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, false)) {
            tmess.Generico("Gli Enti sono in ordine decrescente");
        } else {
            success = false;
            tmess.Errori("Gli Enti non sono in ordine decrescente!");
        }

        tmess.azione("Ordino in modo crescente i Template e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_descTemplate"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(10));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, true)) {
            tmess.Generico("I Template sono in ordine crescente");
        } else {
            success = false;
            tmess.Errori("I Template non sono in ordine crescente!");
        }

        tmess.azione("Ordino in modo decrescente i Template e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_descTemplate"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(10));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, false)) {
            tmess.Generico("I Template sono in ordine decrescente");
        } else {
            success = false;
            tmess.Errori("I Template non sono in ordine decrescente!");
        }

        tmess.conclusione(TEST_4, TESTFAMILY, success);
    }

    @Ignore
    @Test
    public void Aggiungi_templates_1() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_5, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_templates(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        SeleniumUtilities.safeClick(driver, By.className("addgenerico"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        Random generator = new Random();
        Integer n = generator.nextInt(9);
        String descrizione = SeleniumUtilities.getTable(driver, "list", "title", tmess).get(n).get(1);
        tmess.azione("La descrizione del template scelto e' " + descrizione);

        clickAzione(n, 4);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        if (SeleniumUtilities.safeFind(driver, By.id("descrizioneTemplate"), tmess).getAttribute("value").equals(descrizione)) {
            tmess.Generico("I dati coincidono");
            String precedente = SeleniumUtilities.safeFind(driver, By.id("descrizioneTemplate"), tmess).getAttribute("value");
            String modifica = "ciao";
            tmess.azione("Modifico la descrizione in " + modifica);
            SeleniumUtilities.safeFind(driver, By.id("descrizioneTemplate"), tmess).sendKeys(modifica);
            SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            SeleniumUtilities.safeClick(driver, By.linkText("Ricerca"), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            SeleniumUtilities.safeFind(driver, By.className("textInput"), tmess).clear();
            SeleniumUtilities.safeFind(driver, By.className("textInput"), tmess).sendKeys(modifica);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            if (SeleniumUtilities.getTable(driver, "list", "title", tmess).size() == 1) {
                tmess.Generico("L'elemento e' stato modificato correttamente");
            } else {
                tmess.Errori("L'elemento non e' stato modificato correttamente");
                success = false;
            }
            clickAzione(0, 4);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            SeleniumUtilities.safeFind(driver, By.className("textInput"), tmess).clear();
            SeleniumUtilities.safeFind(driver, By.className("textInput"), tmess).sendKeys(precedente);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        } else {
            tmess.Errori("I dati non coincidono!");
            success = false;
        }
        tmess.conclusione(TEST_5, TESTFAMILY, success);
    }

    @Ignore
    @Test
    public void Scarica_file_nuovo_template() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_6, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_templates(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        UrlStatusChecker u = new UrlStatusChecker(driver);
        tmess.azione("premo il tasto Aggiungi template.");
        SeleniumUtilities.safeClick(driver, By.linkText("Aggiungi template"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<WebElement> downBtn = SeleniumUtilities.safeFindList(driver, By.className("scarica"), tmess);
        Random generator = new Random();
        Integer n = generator.nextInt(downBtn.size() - 1);
        tmess.azione("Premo il bottone Download del template numero: " + n);

        String downloadUrl = downBtn.get(n).getAttribute("href");
        tmess.azione("Seleziono il Tab documenti e controllo il primo file della lista.");
        u.setURIToCheck(downloadUrl);
        u.setHTTPRequestMethod(RequestMethod.GET);
        Integer codeStatus = u.getHTTPStatusCode();
        tmess.risultati("La risposta del server al link di download e': " + codeStatus.toString());
        if (codeStatus.intValue() == 404 || codeStatus.intValue() == 500) {
            tmess.Errori("Il link dei download effettua una richiesta non valida.");
        }
        tmess.conclusione(TEST_6, TESTFAMILY, success);
    }

    @Ignore
    @Test
    public void Aggiungi_Record() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_7, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_templates(driver);

        String ente = "SUAP Genova";
        String processo = "Attività produttive automatizzato";
        String evento = "Ricezione pratica";
        String template = "Ricevuta utente";

        SeleniumUtilities.safeClick(driver, By.className("ui-icon-plus"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        new Select(SeleniumUtilities.safeFind(driver, By.id("listaEntiDaSelezionare"), tmess)).selectByVisibleText(ente);
        new Select(SeleniumUtilities.safeFind(driver, By.id("listaProcessiDaSelezionare"), tmess)).selectByVisibleText(processo);
        new Select(SeleniumUtilities.safeFind(driver, By.id("listaProcessiEventiDaSelezionare"), tmess)).selectByVisibleText(evento);
        new Select(SeleniumUtilities.safeFind(driver, By.id("idTemplateA"), tmess)).selectByVisibleText(template);
        SeleniumUtilities.safeClick(driver, By.cssSelector("div.ui-dialog-buttonpane:nth-child(11) > div:nth-child(1) > button:nth-child(1)"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        try {
            new Select(SeleniumUtilities.safeFind(driver, By.id("listaEventi"), tmess)).selectByVisibleText(evento);
            tmess.risultati("E' stato aggiunto il nuovo record con successo.");
        } catch (Exception e) {
            tmess.Errori(e.toString());
            tmess.conclusione(TEST_7, TESTFAMILY, false);
        }

        tmess.azione("Ora elimino il record aggiunto.");
        new Select(SeleniumUtilities.safeFind(driver, By.id("listaEnti"), tmess)).selectByVisibleText(ente);
        new Select(SeleniumUtilities.safeFind(driver, By.id("idTemplate"), tmess)).selectByVisibleText(template);
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.cssSelector(".descEvento > div:nth-child(1)"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.className("ui-icon-trash"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.className("ui-state-focus"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tmess.azione("Controllo che sia stato eliminato");
        new Select(SeleniumUtilities.safeFind(driver, By.id("listaEventi"), tmess)).selectByVisibleText(evento);
        new Select(SeleniumUtilities.safeFind(driver, By.id("listaEnti"), tmess)).selectByVisibleText(ente);
        new Select(SeleniumUtilities.safeFind(driver, By.id("idTemplate"), tmess)).selectByVisibleText(template);
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        if (textable.size() < 1) {
            tmess.risultati("E' stato eliminato.");
        } else {
            tmess.Errori("Non e' stato eliminato.");
            success = false;
        }

        tmess.conclusione(TEST_7, TESTFAMILY, success);
    }

    @Test
    public void Modifica_Record() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_8, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_templates(driver);

        List<WebElement> rows = driver.findElements(By.tagName("tr"));
        Random generator = new Random();
        Integer n = generator.nextInt(rows.size() - 7) + 1;
        String templateold = SeleniumUtilities.getTable(driver, "list", "title", tmess).get(n).get(10);
        tmess.azione("Modifico il record numero " + n);
        Actions action = new Actions(driver);
        action.doubleClick(rows.get(n));
        action.perform();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        try {
            SeleniumUtilities.safeClick(driver, By.cssSelector(".ui-dialog-titlebar-close > span:nth-child(1)"), tmess);
        } catch (Exception e) {
            tmess.Errori(e.toString());
            tmess.conclusione(TEST_8, TESTFAMILY, false);
        }

        SeleniumUtilities.safeClick(driver, By.className("ui-icon-pencil"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        new Select(SeleniumUtilities.safeFind(driver, By.id("idTemplateM"), tmess)).selectByVisibleText("Ricevuta utente");
        SeleniumUtilities.safeClick(driver, By.cssSelector("button.ui-button:nth-child(1)"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        new Select(SeleniumUtilities.safeFind(driver, By.id("idTemplate"), tmess)).selectByVisibleText("Ricevuta utente");
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        if (textable.size() > 0) {
            tmess.risultati("E' stato modificato.");
        } else {
            tmess.Errori("Non e' stato modificato.");
            tmess.conclusione(TEST_8, TESTFAMILY, false);
        }
        tmess.azione("Rinserisco i dati precedenti nel record modificato.");
        driver.findElements(By.tagName("tr")).get(2).click();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.className("ui-icon-pencil"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        new Select(SeleniumUtilities.safeFind(driver, By.id("idTemplateM"), tmess)).selectByVisibleText(templateold);
        SeleniumUtilities.safeClick(driver, By.cssSelector("button.ui-button:nth-child(1)"), tmess);

        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        if (textable.isEmpty()) {
            tmess.risultati("Sono stati rinseriti i dati precedenti.");
        } else {
            tmess.Errori("Non e' stato possibile rinserire i dati precedenti.");
            success = false;
        }

        tmess.conclusione(TEST_8, TESTFAMILY, success);
    }

    private void clickAzione(Integer npratica, Integer column) throws Exception {
        while (npratica > 9) {
            npratica -= 10;
            SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_EVEN_VAI_ALLA_PAGINA_SUCCESSIVA_BUTTON_CSSSELECTOR), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        }
        List<WebElement> pulsantiAzione = SeleniumUtilities.findWebElementInCol(driver, "list", column, 1, -1, By.tagName("form"), tmess);
        pulsantiAzione.get(npratica).click();
    }

    @AfterClass

    static public void tearDown() throws Exception {
        driver.quit();
    }

    private void OpenSearchPanel() {
        SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_EVEN_APRI_PANNELLO_RICERCA_BUTTON_CSSSELECTOR), tmess);
        new Select(SeleniumUtilities.safeFind(driver, By.id("listaEventi"), tmess)).selectByVisibleText("Selezionare Evento");
        new Select(SeleniumUtilities.safeFind(driver, By.id("listaEnti"), tmess)).selectByVisibleText("Selezionare Ente");
        new Select(SeleniumUtilities.safeFind(driver, By.id("listaProcedimenti"), tmess)).selectByVisibleText("Selezionare Procedimento");
        new Select(SeleniumUtilities.safeFind(driver, By.id("idTemplate"), tmess)).selectByVisibleText("Selezionare");
    }

}
