/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.selenium;

import static it.wego.cross.selenium.GestioneEventiTest.readTableElementsInPages;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
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
public class GestioneEntiTest {

    static final private String TESTFAMILY = "GestioneEnti";
    static final private String TEST_1 = "Ricerca_enti_1";
    static final private String TEST_2 = "Ricerca_enti_2";
    static final private String TEST_3 = "Ricerca_enti_3";
    static final private String TEST_4 = "Modifica_enti_1";
    static final private String TEST_5 = "Modifica_enti_2";
    static final private String TEST_6 = "Inserisci_ente_1";
    static final private String TEST_7 = "Assegana_comune";
    static final private String TEST_8 = "Gestione_procedimenti";
    static final private String TEST_9 = "Ordine_procedimenti";
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

    private void GoTo_gestione_enti(WebDriver driver) throws Exception {
        SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_ENTI_APERTURA_ENTI_BUTTON_CSS), tmess);
        SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_ENTI_APERTURA_ENTI_2_BUTTON_CSS), tmess);
        OpenSearchPanel();
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

    }

    
    @Test
    public void Ricerca_enti_1() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_1, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_enti(driver);

        List<List<String>> tabletext = readTableElementsInPages();
        Random generator = new Random();
        Integer n = generator.nextInt(tabletext.size());

        List<String> nomi = TestUtilities.getCol(tabletext, 1);
        String nomeScelto = nomi.get(n);
        tmess.azione("Prendo questo nome ente: " + nomeScelto);

        List<List<String>> praticheScelte = new ArrayList<List<String>>();
        for (List<String> pratica : tabletext) {
            if (pratica.get(1).toLowerCase().indexOf(nomeScelto.toLowerCase()) >= 0) {
                praticheScelte.add(pratica);
            }

        }

        tmess.azione("Inserisco il nome scelto e cerco.");
        SeleniumUtilities.safeFind(driver, By.id("enteDescrizione"), tmess).sendKeys(nomeScelto);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tabletext = readTableElementsInPages();
        if (tabletext.size() != praticheScelte.size()) {
            tmess.Errori("Sono state trovate meno o piu' utenti di quello che ci si aspettava!");
            tmess.conclusione(TEST_1, TESTFAMILY, false);
            return;
        }
        for (int a = 0; a < tabletext.size(); a++) {
            for (int b = 0; b < 7; b++) {
                if (!tabletext.get(a).get(b).equals(praticheScelte.get(a).get(b))) {
                    tmess.Errori("Nell'utente numero " + a + " dovrebbe avere il dato " + praticheScelte.get(a).get(b)
                            + " invece ha il dato " + tabletext.get(a).get(b));
                    success = false;
                }
            }
        }
        if (tabletext.isEmpty()) {
            tmess.Errori("Non e' stato trovato nessun utente!");
        }

        if (success) {
            tmess.Generico("Tutti gli utenti trovate sono giuste!");
        }

        tmess.conclusione(TEST_1, TESTFAMILY, success);
    }

    
    @Test
    public void Ricerca_enti_2() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_2, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_enti(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<List<String>> tabletext = readTableElementsInPages();
        String codF = "";
        for (int i = 0; i < tabletext.size(); i++) {
            clickAzione(i, 7);
            if (!driver.findElement(By.id("codiceFiscale")).getAttribute("value").isEmpty()) {
                codF = driver.findElement(By.id("codiceFiscale")).getAttribute("value");
                break;
            }
            SeleniumUtilities.safeClick(driver, By.className("secondaryAction"), tmess);
        }
        tmess.azione("Cerco gli utenti con questo codice fiscale " + codF);
//        SeleniumUtilities.safeClick(driver, By.className("secondaryAction"), tmess);
//        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id("codiceFiscale"), tmess).sendKeys(codF);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tabletext = readTableElementsInPages();
        if (tabletext.isEmpty()) {
            tmess.Errori("Non e' stato trovato nessun utente!");
            success = false;
        }
        for (int a = 0; a < tabletext.size(); a++) {
            clickAzione(a, 7);
            if (!driver.findElement(By.id("codiceFiscale")).getAttribute("value").equals(codF)) {
                tmess.Errori("l'utente trovata numero " + a + " non ha il giusto codice fiscale.");
                success = false;
            } else {
                tmess.Generico("l'utente trovata numero " + a + " ha il giusto codice fiscale.");
            }
            SeleniumUtilities.safeClick(driver, By.className("secondaryAction"), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        }

        tmess.conclusione(TEST_2, TESTFAMILY, success);
    }

    
    @Test
    public void Ricerca_enti_3() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_3, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_enti(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<List<String>> tabletext = readTableElementsInPages();
        String pIva = "";
        for (int i = 0; i < tabletext.size(); i++) {
            clickAzione(i, 7);
            if (!driver.findElement(By.id("partitaIva")).getAttribute("value").isEmpty()) {
                pIva = driver.findElement(By.id("partitaIva")).getAttribute("value");
                break;
            }
            SeleniumUtilities.safeClick(driver, By.className("secondaryAction"), tmess);
        }
        tmess.azione("Cerco gli utenti con questa partita iva " + pIva);
//        SeleniumUtilities.safeClick(driver, By.className("secondaryAction"), tmess);
//        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id("partitaIva"), tmess).sendKeys(pIva);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tabletext = readTableElementsInPages();
        if (tabletext.isEmpty()) {
            tmess.Errori("Non e' stato trovato nessun utente!");
            success = false;
        }
        for (int a = 0; a < tabletext.size(); a++) {
            clickAzione(a, 7);
            if (!driver.findElement(By.id("partitaIva")).getAttribute("value").equals(pIva)) {
                tmess.Errori("l'utente trovata numero " + a + " non ha la giusta partita iva.");
                success = false;
            } else {
                tmess.Generico("l'utente trovata numero " + a + " ha la giusta partita iva.");
            }
            SeleniumUtilities.safeClick(driver, By.className("secondaryAction"), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        }

        tmess.conclusione(TEST_3, TESTFAMILY, success);
    }

    
    @Test
    public void Modifica_enti_1() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_4, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_enti(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<List<String>> tabletext = readTableElementsInPages();
        Random generator = new Random();
        Integer n = generator.nextInt(tabletext.size());
        tmess.azione("Prendo la pratica numero " + n + " e cancello i dati obbligatori.");
        clickAzione(n, 7);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        SeleniumUtilities.safeFind(driver, By.id("descrizione"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.cssSelector("div.ctrlHolder:nth-child(2) > input:nth-child(2)"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("email"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("pec"), tmess).clear();
        SeleniumUtilities.safeClick(driver, By.cssSelector("button.primaryAction:nth-child(2)"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        if (!driver.findElements(By.id("errorMsg")).isEmpty()) {
            tmess.Generico(SeleniumUtilities.safeFind(driver, By.id("errorMsg"), tmess).getText());
            List<String> listError = Arrays.asList(SeleniumUtilities.safeFind(driver, By.id("errorMsg"), tmess).getText().split("\n"));
            if (!listError.contains("Specificare il nome dell'ente")) {
                tmess.Errori("L'errore sul nome dell'ente non e' presente!");
                success = false;
            }
            if (!listError.contains("Indirizzo Pec assente o errato")) {
                tmess.Errori("L'errore sull'indirizzo Pec non e' presente!");
                success = false;
            }
            if (!listError.contains("Indicare il codice AOO")) {
                tmess.Errori("L'errore sul codice AOO non e' presente!");
                success = false;
            }
        } else {
            tmess.Errori("E' stato confermato un'ente senza dati obbligatori!");
            success = false;
        }

        tmess.conclusione(TEST_4, TESTFAMILY, success);
    }

   
    @Test
    public void Modifica_enti_2() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_5, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_enti(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<List<String>> tabletext = readTableElementsInPages();
        Random generator = new Random();
        Integer n = generator.nextInt(tabletext.size());
        tmess.azione("Prendo la pratica numero " + n + " e modifico il Nome Ente e codice AOO.");
        clickAzione(n, 7);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        String old = SeleniumUtilities.safeFind(driver, By.id("descrizione"), tmess).getText();
        String nomeEnte = "Prova";
        String codiceAOO = "111";

        SeleniumUtilities.safeFind(driver, By.id("descrizione"), tmess).sendKeys(nomeEnte);
        SeleniumUtilities.safeFind(driver, By.cssSelector("div.ctrlHolder:nth-child(2) > input:nth-child(2)"), tmess).sendKeys(codiceAOO);
        SeleniumUtilities.safeClick(driver, By.cssSelector("button.primaryAction:nth-child(2)"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        if (!driver.findElements(By.id("errorMsg")).isEmpty()) {
            tmess.Errori("La modifica ha dato i seguenti errori:\n" + driver.findElement(By.id("errorMsg")).getText());
            success = false;
        } else {
            tmess.Generico("L'ente e' stato modificato correttamente");
            tmess.azione("Ora vado a rimodificare l'ente!");
            clickAzione(n, 7);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            SeleniumUtilities.safeFind(driver, By.id("descrizione"), tmess).sendKeys(old);
            SeleniumUtilities.safeClick(driver, By.cssSelector("button.primaryAction:nth-child(2)"), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        }

        tmess.conclusione(TEST_5, TESTFAMILY, success);
    }

    
    @Test
    public void Inserisci_ente_1() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_6, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_enti(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tmess.azione("Aggiungo un'ente.");
        SeleniumUtilities.safeClick(driver, By.className("addgenerico"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        String tipologia = "Ente esterno";
        String nomEnte = "Pippo";
        String codiceEnte = "444";
        String codiceAOO = "444";
        String pec = "prova@libero.it";
        String unitaOrganizzativa = "444";

        new Select(SeleniumUtilities.safeFind(driver, By.id("tipoEnte"), tmess)).selectByVisibleText(tipologia);
        SeleniumUtilities.safeFind(driver, By.id("descrizione"), tmess).sendKeys(nomEnte);
        SeleniumUtilities.safeFind(driver, By.id("codEnte"), tmess).sendKeys(codiceEnte);
        SeleniumUtilities.safeFind(driver, By.id("codiceAoo"), tmess).sendKeys(codiceAOO);
        SeleniumUtilities.safeFind(driver, By.id("pec"), tmess).sendKeys(pec);
        SeleniumUtilities.safeFind(driver, By.id("unitaOrganizzativa"), tmess).sendKeys(unitaOrganizzativa);
        SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        if (!driver.findElements(By.id("errorMsg")).isEmpty()) {
            tmess.Errori("E' stato visualizzato questo errore: "
                    + SeleniumUtilities.safeFind(driver, By.id("errorMsg"), tmess).getText());
            success = false;
        } else {
            tmess.azione("E' stato aggiunto un'ente, ed ora vado ad eliminarlo!");
            SeleniumUtilities.safeClick(driver, By.cssSelector(".ui-accordion-header > a:nth-child(2)"), tmess);
            SeleniumUtilities.safeFind(driver, By.id("enteDescrizione"), tmess).sendKeys(nomEnte);
            SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            clickElimina(0);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            if (readTableElementsInPages().size() < 1) {
                tmess.Generico("E' stato eliminato correttamente.");
            } else {
                tmess.Errori("Non e' stato eliminato alcun dato.");
                success = false;
            }
        }

        tmess.conclusione(TEST_6, TESTFAMILY, success);
    }

    
    @Test
    public void Assegana_comune() throws Exception {
        boolean success = false;
        tmess.NuovoTest(TEST_7, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_enti(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<List<String>> tabletext = readTableElementsInPages();
        Random generator = new Random();
        Integer n = generator.nextInt(tabletext.size());
        tmess.azione("Prendo la pratica numero " + n + " e assegno un comune");
        clickAzione(n, 7);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.cssSelector("button.primaryAction:nth-child(4)"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        String comune;
        Integer num = readTableElementsInPages().size();
        int i = 0;
        tmess.Generico("Ci sono " + num + " comuni assegnati e ora ne assegno uno nuovo!");
        do {
            SeleniumUtilities.safeClick(driver, By.linkText("Aggiungi comune"), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            comune = SeleniumUtilities.getTable(driver, "list", "title", tmess).get(i).get(1);
            List<WebElement> pulsantiAzione = SeleniumUtilities.findWebElementInCol(driver, "list", 4, 1, -1, By.tagName("button"), tmess);
            pulsantiAzione.get(i).click();
            i++;
        } while (num == readTableElementsInPages().size());

        tmess.Generico("E' stato assegneto il comune " + comune + ", ora lo elimino");
        i = 0;
        List<List<String>> enti = readTableElementsInPages();
        for (List<String> ente : enti) {
//            tmess.azione(ente.get(1) + " -> " + comune);
            if (ente.get(1).equals(comune)) {
                clickCancella(i);
                SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
                SeleniumUtilities.safeClick(driver, By.cssSelector("button.ui-button-text-only:nth-child(1)"), tmess);
                SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
                success = true;
                break;
            }
        }
        if (success) {
            tmess.Generico("Il comune e' stato eliminato correttamente.");
        } else {
            tmess.Errori("Il comune non e' stato eliminato!");
        }

        tmess.conclusione(TEST_7, TESTFAMILY, success);
    }

    
    @Test
    public void Gestione_procedimenti() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_8, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_enti(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<List<String>> tabletext = readTableElementsInPages();
        Random generator = new Random();
        Integer n = generator.nextInt(tabletext.size());
        tmess.azione("Prendo la pratica numero " + n + " e modifico un procedimento.");
        clickAzione(n, 7);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.cssSelector("button.primaryAction:nth-child(3)"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        
        if (readTableElementsInPages().isEmpty()) {
            tmess.Errori("Non ci sono procedimenti!");
            tmess.conclusione(TEST_8, TESTFAMILY, false);
            return;
        }
        clickAzione(0, 5);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        n = generator.nextInt(readTableElementsInPages().size());
        clickAzione(n, 4);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.className("secondaryAction"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tabletext = readTableElementsInPages();
        if (tabletext.get(0).get(4).equals("")) {
            tmess.Errori("Non e' stato selsezionato nessun processo");
        } else {
            tmess.Generico(tabletext.get(0).get(4));
        }

        clickModifica(0);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tabletext = readTableElementsInPages();
        if (tabletext.get(0).get(4).equals("")) {
            tmess.Generico("E' stato modificato con successo");
        } else {
            tmess.Generico(tabletext.get(0).get(4));
        }

        tmess.conclusione(TEST_8, TESTFAMILY, success);
    }

    
    @Test
    public void Ordine_procedimenti() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_9, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_enti(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tmess.azione("Ordino in modo crescente i Nomi Ente e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_descrizione"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        List<String> elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(1));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, true)) {
            tmess.Generico("I nomi sono in ordine crescente");
        } else {
            success = false;
            tmess.Errori("I nomi non sono in ordine crescente!");
        }

        tmess.azione("Ordino in modo decrescente i Nomi Ente e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_descrizione"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(1));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, false)) {
            tmess.Generico("I nomi sono in ordine decrescente");
        } else {
            success = false;
            tmess.Errori("Gli oggetti non sono in ordine decrescente!");
        }

        tmess.azione("Ordino in modo crescente le citta' e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_citta"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(2));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, true)) {
            tmess.Generico("Le citta' sono in ordine crescente");
        } else {
            success = false;
            tmess.Errori("Le citta' non sono in ordine crescente!");
        }

        tmess.azione("Ordino in modo decrescente le citta' e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_citta"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(2));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, false)) {
            tmess.Generico("Le citta' sono in ordine decrescente");
        } else {
            success = false;
            tmess.Errori("Le citta' non sono in ordine decrescente!");
        }

        tmess.azione("Ordino in modo crescente le Province e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_provincia"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(3));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, true)) {
            tmess.Generico("Le Province sono in ordine crescente");
        } else {
            success = false;
            tmess.Errori("Le Province non sono in ordine crescente!");
        }

        tmess.azione("Ordino in modo decrescente le Province e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_provincia"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(3));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, false)) {
            tmess.Generico("Le Province sono in ordine decrescente");
        } else {
            success = false;
            tmess.Errori("Le Province non sono in ordine decrescente!");
        }

        tmess.azione("Ordino in modo crescente le email e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_email"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(4));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, true)) {
            tmess.Generico("Le email sono in ordine crescente");
        } else {
            success = false;
            tmess.Errori("Le email non sono in ordine crescente!");
        }

        tmess.azione("Ordino in modo decrescente le email e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_email"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(4));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, false)) {
            tmess.Generico("Le email sono in ordine decrescente");
        } else {
            success = false;
            tmess.Errori("Le email non sono in ordine decrescente!");
        }

        tmess.azione("Ordino in modo crescente le PEC e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_pec"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(5));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, true)) {
            tmess.Generico("Le PEC sono in ordine crescente");
        } else {
            success = false;
            tmess.Errori("Le PEC non sono in ordine crescente!");
        }

        tmess.azione("Ordino in modo decrescente le PEC e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_pec"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(5));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, false)) {
            tmess.Generico("Le PEC sono in ordine decrescente");
        } else {
            success = false;
            tmess.Errori("Le PEC non sono in ordine decrescente!");
        }

        tmess.azione("Ordino in modo crescente i numeri di telefono e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_telefono"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(6));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, true)) {
            tmess.Generico("I numeri di telefono sono in ordine crescente");
        } else {
            success = false;
            tmess.Errori("I numeri di telefono non sono in ordine crescente!");
        }

        tmess.azione("Ordino in modo decrescente i numeri di telefono e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_list_telefono"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(6));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, false)) {
            tmess.Generico("I numeri di telefono sono in ordine decrescente");
        } else {
            success = false;
            tmess.Errori("I numeri di telefono non sono in ordine decrescente!");
        }

        tmess.conclusione(TEST_9, TESTFAMILY, success);
    }

    static public List<List<String>> readTableElementsInPages() throws Exception {
        WebElement tel = SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_EVEN_CONTAPAGINE_ID), tmess);
        String temp = tel.getText();
        Integer pageNumber;
        if (temp.equals("")) {
            pageNumber = 0;
        } else {
            pageNumber = Integer.parseInt(temp);
        }
        tmess.azione("page number" + pageNumber.toString());
        List<List<String>> result = SeleniumUtilities.readTableElementsInPages(driver,
                By.cssSelector(WebElMap.AP_EVEN_VAI_ALLA_PAGINA_SUCCESSIVA_BUTTON_CSSSELECTOR),
                By.cssSelector(WebElMap.AP_EVEN_VAI_ALLA_PRIMA_PAGINA_BUTTON_CSSSELECTOR),
                WebElMap.AP_EVEN_TABELLA_ID,
                WebElMap.AP_EVEN_TABELLA_CAMPO_DA_LEGGERE,
                pageNumber, tmess);
        return result;
    }

    private void clickAzione(Integer npratica, Integer column) throws Exception {
        while (npratica > 9) {
            npratica -= 10;
            SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_EVEN_VAI_ALLA_PAGINA_SUCCESSIVA_BUTTON_CSSSELECTOR), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        }
        List<WebElement> pulsantiAzione = SeleniumUtilities.findWebElementInCol(driver, "list", column, 1, -1, By.tagName("a"), tmess);
        pulsantiAzione.get(npratica).click();
    }

    private void clickCancella(Integer npratica) throws Exception {
        while (npratica > 9) {
            npratica -= 10;
            SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_EVEN_VAI_ALLA_PAGINA_SUCCESSIVA_BUTTON_CSSSELECTOR), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        }
        List<WebElement> pulsantiAzione = SeleniumUtilities.findWebElementInCol(driver, "list", 4, 1, -1, By.tagName("button"), tmess);
        pulsantiAzione.get(npratica).click();
    }

    private void clickElimina(Integer npratica) throws Exception {
        List<WebElement> pulsantiAzione = SeleniumUtilities.findWebElementInCol(driver, "list", 7, 1, -1, By.className("elimina_ente"), tmess);
        pulsantiAzione.get(npratica).click();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.cssSelector("button.ui-state-default:nth-child(1)"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
    }

    private void clickModifica(Integer npratica) throws Exception {
        List<WebElement> pulsantiAzione = SeleniumUtilities.findWebElementInCol(driver, "list", 5, 1, -1, By.tagName("button"), tmess);
        pulsantiAzione.get(npratica).click();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
    }

    @AfterClass

    static public void tearDown() throws Exception {
        driver.quit();
    }

    private void OpenSearchPanel() throws Exception {
        SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_EVEN_APRI_PANNELLO_RICERCA_BUTTON_CSSSELECTOR), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id("enteDescrizione"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("codiceFiscale"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("partitaIva"), tmess).clear();
    }
}
