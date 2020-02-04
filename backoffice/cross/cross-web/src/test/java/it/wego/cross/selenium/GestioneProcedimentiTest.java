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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author Luca
 */
public class GestioneProcedimentiTest {

    static final private String TESTFAMILY = "GestioneEnti";
    static final private String TEST_1 = "Ricerca_procedimenti_1";
    static final private String TEST_2 = "Ricerca_procedimenti_2";
    static final private String TEST_3 = "Modifica_procedimenti_1";
    static final private String TEST_4 = "Modifica_procedimenti_2";
    static final private String TEST_5 = "Ordinamento_procedimenti";
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

    private void GoTo_gestione_procedimenti(WebDriver driver) throws Exception {
        SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_ENTI_APERTURA_ENTI_BUTTON_CSS), tmess);
        SeleniumUtilities.safeClick(driver, By.xpath(WebElMap.AP_PROC_APERTURA_GESTIONE_BUTTON_XPATH), tmess);
        OpenSearchPanel();
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
    }

    @Ignore
    @Test
    public void Ricerca_procedimenti_1() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_1, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_procedimenti(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<List<String>> textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        Random generator = new Random();
        Integer n = generator.nextInt(textable.size());
        String descrizione = textable.get(n).get(1);
        tmess.azione("Eseguo la ricerca secondo questa descrizione " + descrizione);
        SeleniumUtilities.safeFind(driver, By.id("desProcedimento"), tmess).sendKeys(descrizione);
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tmess.azione("Controllo che i dati trovati hanno tutti la stessa descrizione");
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        for (List<String> row : textable) {
            if (!row.get(1).equals(descrizione)) {
                tmess.Errori("I dati trovati non sono corretti!");
                tmess.conclusione(TEST_1, TESTFAMILY, false);
                return;
            }
        }
        tmess.Generico("I dati trovati son corretti.");

        tmess.conclusione(TEST_1, TESTFAMILY, success);
    }

    @Ignore
    @Test
    public void Ricerca_procedimenti_2() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_2, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_procedimenti(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<List<String>> textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        Random generator = new Random();
        Integer n = generator.nextInt(textable.size());
        String tipo = textable.get(n).get(2);
        tmess.azione("Eseguo la ricerca secondo questo tipo procedimento " + tipo);
        new Select(SeleniumUtilities.safeFind(driver, By.id("tipoProcedimento"), tmess)).selectByVisibleText(tipo);
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tmess.azione("Controllo che i dati trovati hanno tutti lao stesso tipo procedimento");
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        for (List<String> row : textable) {
            if (!row.get(2).equals(tipo)) {
                tmess.Errori("I dati trovati non sono corretti!");
                tmess.conclusione(TEST_2, TESTFAMILY, false);
                return;
            }
        }
        tmess.Generico("I dati trovati son corretti.");

        tmess.conclusione(TEST_2, TESTFAMILY, success);
    }

    @Ignore
    @Test
    public void Modifica_procedimenti_1() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_3, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_procedimenti(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<List<String>> textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        Random generator = new Random();
        Integer n = generator.nextInt(textable.size());
        tmess.azione("Apro il procedimento " + n);
        clickAzione(n, 4);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        String mod = "ciao";
        tmess.azione("Modifico il campo 'Descrizione Procedimento' inserendo " + mod);
        String old = SeleniumUtilities.safeFind(driver, By.id("desProcedimento"), tmess).getAttribute("value");
        SeleniumUtilities.safeFind(driver, By.id("desProcedimento"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("desProcedimento"), tmess).sendKeys(mod);
        SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        OpenSearchPanel();
        SeleniumUtilities.safeFind(driver, By.id("desProcedimento"), tmess).sendKeys(mod);
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        if (textable.get(0).get(1).equals(mod)) {
            tmess.Generico("Le modifiche sono state effetuate con successo");
            tmess.azione("Rimetto i dati precedenti");
            clickAzione(0, 4);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            SeleniumUtilities.safeFind(driver, By.id("desProcedimento"), tmess).clear();
            SeleniumUtilities.safeFind(driver, By.id("desProcedimento"), tmess).sendKeys(old);
            SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        } else {
            tmess.Errori("Le modifiche non sono state effetuate!");
            success = false;
        }

        tmess.conclusione(TEST_3, TESTFAMILY, success);
    }

    @Ignore
    @Test
    public void Modifica_procedimenti_2() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_4, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_procedimenti(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<List<String>> textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        Random generator = new Random();
        Integer n = generator.nextInt(textable.size());
        tmess.azione("Apro il procedimento " + n);
        clickAzione(n, 4);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tmess.azione("Salvo tutti i dati");
        String desProcedimento = SeleniumUtilities.safeFind(driver, By.id("desProcedimento"), tmess).getAttribute("value");
        String codProcedimento = SeleniumUtilities.safeFind(driver, By.id("codProcedimento"), tmess).getAttribute("value");
        String tipoProc = SeleniumUtilities.safeFind(driver, By.id("tipoProc"), tmess).getAttribute("value");
        String termini = SeleniumUtilities.safeFind(driver, By.id("termini"), tmess).getAttribute("value");
        String classifica = SeleniumUtilities.safeFind(driver, By.id("classifica"), tmess).getAttribute("value");
        String peso = SeleniumUtilities.safeFind(driver, By.id("peso"), tmess).getAttribute("value");
        SeleniumUtilities.safeClick(driver, By.className("secondaryAction"), tmess);

        tmess.azione("Elimino il medesimo procedimento ");
        clickElimina(n, 4);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        tmess.Generico("E' stato eliminato correttamente!");
        tmess.azione("Ora aggiungo il procedimento eliminato");
        SeleniumUtilities.safeClick(driver, By.cssSelector(".table-add-link > a:nth-child(1)"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id("desProcedimento"), tmess).sendKeys(desProcedimento);
        SeleniumUtilities.safeFind(driver, By.id("codProcedimento"), tmess).sendKeys(codProcedimento);
        SeleniumUtilities.safeFind(driver, By.id("tipoProc"), tmess).sendKeys(tipoProc);
        SeleniumUtilities.safeFind(driver, By.id("termini"), tmess).sendKeys(termini);
        SeleniumUtilities.safeFind(driver, By.id("classifica"), tmess).sendKeys(classifica);
        SeleniumUtilities.safeFind(driver, By.id("peso"), tmess).sendKeys(peso);
        SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tmess.Generico("L'elemento e' stato riaggiunto!");

        tmess.conclusione(TEST_4, TESTFAMILY, success);
    }
    
    @Test
    public void Ordinamento_procedimenti() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_5, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_procedimenti(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tmess.azione("Ordino in modo crescente i Procedimenti e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_desProcedimento"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        List<String> elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(1));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, true)) {
            tmess.Generico("I Procedimenti sono in ordine crescente");
        } else {
            success = false;
            tmess.Errori("I Procedimenti non sono in ordine crescente!");
        }

        tmess.azione("Ordino in modo decrescente i Procedimenti e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_desProcedimento"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(1));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, false)) {
            tmess.Generico("I procedimenti sono in ordine decrescente");
        } else {
            success = false;
            tmess.Errori("I procedimenti non sono in ordine decrescente!");
        }
        
        tmess.conclusione(TEST_5, TESTFAMILY, success);
    }

    private void clickAzione(Integer npratica, Integer column) throws Exception {
        List<WebElement> pulsantiAzione = SeleniumUtilities.findWebElementInCol(driver, "list", column, 1, -1, By.tagName("form"), tmess);
        pulsantiAzione.get(npratica).click();
    }

    private void clickElimina(Integer npratica, Integer column) throws Exception {
        List<WebElement> pulsantiAzione = SeleniumUtilities.findWebElementInCol(driver, "list", column, 1, -1, By.className("cancella_ente"), tmess);
        pulsantiAzione.get(npratica).click();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.className("ui-state-focus"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
    }

    @AfterClass

    static public void tearDown() throws Exception {
        driver.quit();
    }

    private void OpenSearchPanel() throws Exception {
        SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_EVEN_APRI_PANNELLO_RICERCA_BUTTON_CSSSELECTOR), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id("desProcedimento"), tmess).clear();
        new Select(SeleniumUtilities.safeFind(driver, By.id("tipoProcedimento"), tmess)).selectByVisibleText("");
    }

}
