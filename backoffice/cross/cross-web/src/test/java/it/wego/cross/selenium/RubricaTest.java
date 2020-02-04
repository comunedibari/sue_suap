/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.selenium;

import static it.wego.cross.selenium.AperturaPraticheATest.readTableElementsInPages;
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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class RubricaTest {

    static final private String TESTFAMILY = "OrdinamentoRubrica";
    static final private String TEST_01 = "Ordinamento_Rubrica";
    static final private String TEST_02 = "Ricerca_rubrica_1";
    static final private String TEST_03 = "Ricerca_rubrica_2";
    static final private String TEST_04 = "Ricerca_rubrica_4";
    static final private String TEST_05 = "Ricerca_rubrica_5";
    static final private String TEST_06 = "Ricerca_rubrica_9";
    static final private String TEST_07 = "Ricerca_rubrica_10";
    static final private String TEST_08 = "Ricerca_Rubrica_3";
    static final private String TEST_09 = "Selezione_anagrafica_da_modificare"; //5.8
    static final private String TEST_12 = "Modifica_anagrafica_persona_fisica_1"; //5.12‏
    static final private String TEST_13 = "Modifica_anagrafica_persona_fisica_2"; //5.13
    static final private String TEST_14 = "Modifica_anagrafica_persona_fisica_3"; //5.14
    static final private String TEST_17 = "Modifica_anagrafica_persona_giuridica_1"; //5.17
    static final private String PAGENUMBERID = "sp_1_pager";
    static final private String GOTOFIRSTPAGEBOTTON = "ui-icon.ui-icon-seek-first";
    static private WebDriver driver;
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

    private void GoTo_rubrica(WebDriver driver) {
        SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_PRAT_GESTIONE_BUTTON_CSSSELECTOR), tmess);
        SeleniumUtilities.safeClick(driver, By.xpath(WebElMap.AP_RUBR_APERTURA_RUBRICA_BUTTON_XPATH), tmess);
    }

    
    @Test
    public void Organizza_Rubrica() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_01, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_rubrica(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> tabletext = readTableElementsInPages();
        List<String> pIva = TestUtilities.getCol(tabletext, 3);
        Boolean crescentObj = TestUtilities.isSortedAlphabetically(pIva, true);
        tmess.azione("Controllo se le partite IVA sono ordinate:  " + crescentObj);

        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_RUBR_ORGANIZZA_PIVA_ID), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tabletext = readTableElementsInPages();
        pIva = TestUtilities.getCol(tabletext, 3);
        crescentObj = TestUtilities.isSortedAlphabetically(pIva, true);
        tmess.risultati("Le partite IVA sono ordinate dopo aver premuto il pulsante?  " + crescentObj);
        if (!crescentObj) {
            tmess.Errori("Le entry della tabella non sono state riorganizzate in ordine decrescente.");
            success = false;
        }

        List<String> codFis = TestUtilities.getCol(tabletext, 4);
        Boolean crescentObjCF = TestUtilities.isSortedAlphabetically(codFis, true);
        tmess.azione("Controllo se i Codici Fiscali sono ordinati: " + crescentObjCF);
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_RUBR_ORGANIZZA_CFIS_ID), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tabletext = readTableElementsInPages();
        codFis = TestUtilities.getCol(tabletext, 4);
        crescentObjCF = TestUtilities.isSortedAlphabetically(codFis, true);
        tmess.risultati("I Codici Fiscali sono ordinati dopo aver premuto il pulsante?  " + crescentObjCF);
        if (!crescentObjCF) {
            tmess.Errori("Le entry della tabella non sono state riorganizzate in ordine decrescente.");
            success = false;
        }

        tmess.conclusione(TEST_01, TESTFAMILY, success);

    }

    
    @Test
    public void Ricerca_rubrica_1() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_02, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_rubrica(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<List<String>> tabletext = readTableElementsInPages();
        List<String> nomiPratiche = new ArrayList<String>();
        for (List<String> row : tabletext) {
            if (row.get(1).equals("Persona Fisica")
                    || row.get(1).equals("Ditta Individuale")) {
                nomiPratiche.add(row.get(2));
            }
        }

        Random generator = new Random();
        Integer n = generator.nextInt(nomiPratiche.size());
        if (nomiPratiche.get(n).indexOf(" ") < 0) {
            tmess.Errori("Il nome " + nomiPratiche.get(n) + " non e' completo!");
            success = false;
            tmess.conclusione(TEST_02, TESTFAMILY, success);
            return;
        }
        String nomeScelto = nomiPratiche.get(n).split(" ")[1];
        tmess.azione("Il nome scelto e' " + nomeScelto);

        int count = 0;
        for (String nome : nomiPratiche) {
            if (nome.indexOf(" ") < 0) {
                tmess.Errori("Il nome " + nome + " non e' completo!");
                success = false;
                tmess.conclusione(TEST_02, TESTFAMILY, success);
                return;
            } else if (nome.split(" ")[1].equals(nomeScelto)) {
                count++;
            }
        }
        tmess.Generico("Ci sono " + count + " enti cone " + nomeScelto);

        tmess.azione("Eseguo la ricerca");
        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id("search_nome"), tmess).sendKeys(nomeScelto);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tabletext = readTableElementsInPages();
        nomiPratiche = TestUtilities.getCol(tabletext, 2);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tmess.Generico("Sono stati trovati " + nomiPratiche.size() + " enti");

        count = 0;
        for (String nome : nomiPratiche) {
            count++;
            if (!nome.split(" ")[1].equals(nomeScelto)) {
                tmess.Errori("L'enete trovato numero " + count + " non ha il nome " + nomeScelto);
                success = false;
            }
        }
        if (success || count == 0) {
            tmess.Generico("Tutti gli enti hanno il nome " + nomeScelto);
        }

        tmess.conclusione(TEST_02, TESTFAMILY, success);
    }

    
    @Test
    public void Ricerca_rubrica_2() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_03, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_rubrica(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<List<String>> tabletext = readTableElementsInPages();
        List<String> nomiPratiche = new ArrayList<String>();
        for (List<String> row : tabletext) {
            if (row.get(1).equals("Persona Fisica")
                    || row.get(1).equals("Ditta Individuale")) {
                nomiPratiche.add(row.get(2));
            }
        }

        Random generator = new Random();
        Integer n = generator.nextInt(nomiPratiche.size());
        String nomeScelto = nomiPratiche.get(n).split(" ")[0];
        tmess.azione("Il nome scelto e' " + nomeScelto);

        int count = 0;
        for (String nome : nomiPratiche) {
            if (nome.split(" ")[0].equals(nomeScelto)) {
                count++;
            }
        }
        tmess.Generico("Ci sono " + count + " enti cone " + nomeScelto);

        tmess.azione("Eseguo la ricerca");
        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id("search_cognome"), tmess).sendKeys(nomeScelto);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tabletext = readTableElementsInPages();
        nomiPratiche = TestUtilities.getCol(tabletext, 2);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tmess.Generico("Sono stati trovati " + nomiPratiche.size() + " enti");

        count = 0;
        for (String nome : nomiPratiche) {
            count++;
            if (!nome.split(" ")[0].equals(nomeScelto)) {
                tmess.Errori("L'enete trovato numero " + count + " non ha il nome " + nomeScelto);
                success = false;
            }
        }
        if (success || count == 0) {
            tmess.Generico("Tutti gli enti hanno il nome " + nomeScelto);
        }

        tmess.conclusione(TEST_03, TESTFAMILY, success);
    }

    
    @Test
    public void Ricerca_rubrica_4() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_04, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_rubrica(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<List<String>> tabletext = readTableElementsInPages();
        List<String> listacodF = TestUtilities.getCol(tabletext, 4);

        Random generator = new Random();
        Integer n = generator.nextInt(listacodF.size());
        String codFScelto = listacodF.get(n);
        tmess.azione("Il codice fiscale scelto e' " + codFScelto);

        int count = 0;
        for (String cod : listacodF) {
            if (cod.equals(codFScelto)) {
                count++;
            }
        }
        tmess.Generico("Ci sono " + count + " enti cone " + codFScelto);

        tmess.azione("Eseguo la ricerca");
        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id("search_codice_fiscale"), tmess).sendKeys(codFScelto);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tabletext = readTableElementsInPages();
        listacodF = TestUtilities.getCol(tabletext, 4);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tmess.Generico("Sono stati trovati " + listacodF.size() + " enti");

        count = 0;
        for (String codF : listacodF) {
            count++;
            if (!codF.equals(codFScelto)) {
                tmess.Errori("L'enete trovato numero " + count + " non ha il codice fiscale " + codFScelto);
                success = false;
            }
        }
        if (success || count == 0) {
            tmess.Generico("Tutti gli enti hanno il codice fiscale " + codFScelto);
        }

        tmess.conclusione(TEST_04, TESTFAMILY, success);
    }

    
    @Test
    public void Ricerca_rubrica_5() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_05, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_rubrica(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<List<String>> tabletext = readTableElementsInPages();
        List<String> listarow = TestUtilities.getCol(tabletext, 3);
        List<String> listaPiva = new ArrayList<String>();
        for (String row : listarow) {
            if (!row.equals("")) {
                listaPiva.add(row);
            }
        }

        Random generator = new Random();
        Integer n = generator.nextInt(listaPiva.size());
        String pivaScelto = listaPiva.get(n);
        tmess.azione("La partita iva scelta e' " + pivaScelto);

        int count = 0;
        for (String piva : listaPiva) {
            if (piva.equals(pivaScelto)) {
                count++;
            }
        }
        tmess.Generico("Ci sono " + count + " enti cone " + pivaScelto);

        tmess.azione("Eseguo la ricerca");
        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id("search_partita_iva"), tmess).sendKeys(pivaScelto);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tabletext = readTableElementsInPages();
        listaPiva = TestUtilities.getCol(tabletext, 3);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tmess.Generico("Sono stati trovati " + listaPiva.size() + " enti");

        count = 0;
        for (String codF : listaPiva) {
            count++;
            if (!codF.equals(pivaScelto)) {
                tmess.Errori("L'enete trovato numero " + count + " non ha la partita iva " + pivaScelto);
                success = false;
            }
        }
        if (success || count == 0) {
            tmess.Generico("Tutti gli enti hanno la partita iva " + pivaScelto);
        }

        tmess.conclusione(TEST_05, TESTFAMILY, success);
    }

    
    @Test
    public void Ricerca_rubrica_9() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_06, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_rubrica(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        String nome = "Lo Chiamavono";
        String cognome = "Trinità";
        String codF = "DNTMRT15M18E394P";
        String cit = "Italiana";
        String com = "BASILIANO";
        String ind = "vie per la'";
        String numC = "7";

        List<List<String>> tabletext = readTableElementsInPages();
        int val = tabletext.size();
        tmess.Generico("Ci sono " + val + " enti.");

        tmess.azione("Premo il tasto 'Inserisci nuova anagrafica' e inserisco i dati");
        SeleniumUtilities.safeClick(driver, By.className("inserisciAnagrafica"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        new Select(SeleniumUtilities.safeFind(driver, By.id("tipoAnagrafica"), tmess)).selectByVisibleText("Persona fisica");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id("nome"), tmess).sendKeys(nome);
        SeleniumUtilities.safeFind(driver, By.id("cognome"), tmess).sendKeys(cognome);
        SeleniumUtilities.safeFind(driver, By.id("codiceFiscale"), tmess).sendKeys(codF);
        SeleniumUtilities.safeFind(driver, By.id("desCittadinanza"), tmess).sendKeys(cit);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.linkText(cit), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id("desComuneRecapito"), tmess).sendKeys(com);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.linkText(com + "(UD)"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id("indirizzoRecapito"), tmess).sendKeys(ind);
        SeleniumUtilities.safeFind(driver, By.id("civicoRecapito"), tmess).sendKeys(numC);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        if (!driver.findElements(By.id("errorMsg")).isEmpty()) {
            tmess.azione(driver.findElement(By.id("errorMsg")).getText());
            tmess.Errori("Non e' possibile inserire questo ente!");
            tmess.conclusione(TEST_06, TESTFAMILY, false);
            return;
        }

        tabletext = readTableElementsInPages();
        tmess.Generico("Ci sono " + tabletext.size() + " enti.");
        if (tabletext.size() != (val + 1)) {
            tmess.Errori("Non e' stata aggiunta nessun ente!");
            tmess.conclusione(TEST_06, TESTFAMILY, false);
            return;
        } else {
            tmess.azione("E' stato aggiunto un'ente!");
            if (tabletext.get(val).get(4).equals(codF)) {
                tmess.Generico("L'ente aggiunto e' corretto");
            } else {
                tmess.Errori("L'ente aggiunto e' errato!");
                tmess.conclusione(TEST_06, TESTFAMILY, false);
                return;
            }

        }

        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tmess.azione("Seleziono una anagrafica dalla lista.");
        List<WebElement> table = driver.findElements(By.tagName("table"));
        List<WebElement> rows = table.get(1).findElements(By.tagName("tr"));
        while (rows.size() < (val + 1)) {
            SeleniumUtilities.safeClick(driver, By.className("ui-icon-seek-next"), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            table = driver.findElements(By.tagName("table"));
            rows = table.get(1).findElements(By.tagName("tr"));
            val -= 11;
        }
        List<WebElement> column = rows.get(val + 1).findElements(By.tagName("td"));
        WebElement cell = column.get(5).findElement(By.tagName("button"));
        cell.click();

        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        WebElement tabledetail = SeleniumUtilities.safeFind(driver, By.tagName("fieldset"), tmess);
        List<List<String>> dati = SeleniumUtilities.readLabelScadEven(tabledetail);

        tmess.azione(dati.get(1).toString());

        tmess.conclusione(TEST_06, TESTFAMILY, success);
    }

    
    @Test
    public void Ricerca_rubrica_10() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_07, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_rubrica(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tmess.azione("Premo il tasto 'Inserisci nuova anagrafica'");
        SeleniumUtilities.safeClick(driver, By.className("inserisciAnagrafica"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tmess.azione("Scelgo 'Persona Fisica' e controllo se i campi sono corretti");
        new Select(SeleniumUtilities.safeFind(driver, By.id("tipoAnagrafica"), tmess)).selectByVisibleText("Persona fisica");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        WebElement tabletext = SeleniumUtilities.safeFind(driver, By.className("personaFisica"), tmess);
        List<List<String>> dati = SeleniumUtilities.readLabelScadEven(tabletext);

        String pfIdeale = "Imposta come Ditta Individuale, Nome, Cognome, Sesso, Codice fiscale, Data di nascita, Cittadinanza, Comune (nascita), Località (nascita), , , , , , , , , , , , , , , ";
        List<String> pf = new ArrayList<String>();
        pf.addAll(Arrays.asList(pfIdeale.split(", ")));
        for (int i = 0; i < pf.size(); i++) {
            if (!dati.get(0).get(i).equals(pf.get(i))) {
                tmess.Errori("Il campo " + dati.get(0).get(i) + " dovrebbe essere " + pf.get(i));
                tmess.conclusione(TEST_07, TESTFAMILY, false);
                return;
            }
        }
        tmess.Generico("I campi sono giusti!");

        tmess.azione("Seleziono 'Imposta come Ditta Individuale' e controllo se i campi sono corretti");
        SeleniumUtilities.safeClick(driver, By.id("I"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tabletext = SeleniumUtilities.safeFind(driver, By.className("personaFisica"), tmess);
        dati = SeleniumUtilities.readLabelScadEven(tabletext);

        String diIdeale = "Imposta come Ditta Individuale, Nome, Cognome, Sesso, Codice fiscale, Data di nascita, Cittadinanza, Comune (nascita), Località (nascita), , , , , , , Partita IVA, Ragione sociale, In attesa di iscrizione al Registro Imprese, Numero di iscrizione al Registro Imprese, Data di iscrizione al Registro Imprese, Provincia CCIAA, In attesa di iscrizione al R.E.A., Numero iscrizione R.E.A., Data iscrizione R.E.A.";
        List<String> di = new ArrayList<String>();
        di.addAll(Arrays.asList(diIdeale.split(", ")));
        for (int i = 0; i < di.size(); i++) {
            if (!dati.get(0).get(i).equals(di.get(i))) {
                tmess.Errori("Il campo " + dati.get(0).get(i) + " dovrebbe essere " + di.get(i));
                tmess.conclusione(TEST_07, TESTFAMILY, false);
                return;
            }
        }
        tmess.Generico("I campi sono giusti!");

        tmess.azione("Scelgo 'Persona Giuridica' e controllo se i campi sono corretti");
        new Select(SeleniumUtilities.safeFind(driver, By.id("tipoAnagrafica"), tmess)).selectByVisibleText("Persona giuridica");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tabletext = SeleniumUtilities.safeFind(driver, By.className("personaGiuridica"), tmess);
        dati = SeleniumUtilities.readLabelScadEven(tabletext);

        String pgIdeale = "Ragione sociale, Forma giuridica, Partita IVA, Codice fiscale, In attesa di iscrizione al Registro Imprese, Numero di iscrizione al Registro Imprese, Data di iscrizione al Registro Imprese, Provincia CCIAA, In attesa di iscrizione al R.E.A., Numero iscrizione R.E.A., Data iscrizione R.E.A.";
        List<String> pg = new ArrayList<String>();
        pg.addAll(Arrays.asList(pgIdeale.split(", ")));
        for (int i = 0; i < pg.size(); i++) {
            if (!dati.get(0).get(i).equals(pg.get(i))) {
                tmess.Errori("Il campo " + dati.get(0).get(i) + " dovrebbe essere " + pg.get(i));
                tmess.conclusione(TEST_07, TESTFAMILY, false);
                return;
            }
        }
        tmess.Generico("I campi sono giusti!");

        tmess.conclusione(TEST_07, TESTFAMILY, success);
    }

    
    @Test
    public void Ricerca_Rubrica_02() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_08, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_rubrica(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        CleanSearch(true);

        String randomDenom = "";

        List<List<String>> tabletext = readTableElementsInPages();
        for (List<String> row : tabletext) {
            if (row.get(1).equals("Persona Giuridica")) {
                randomDenom = row.get(2);
            }
        }

        tmess.azione("Scelgo una denominazione: " + randomDenom);

        tmess.azione("Eseguo la ricerca della denominazione scelta dal pannello di ricerca.");
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_RUBR_CLEAN_DENOM_ID), tmess).sendKeys(randomDenom);
        SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_RUBR_CLEANBTN_ID), tmess);
        List<List<String>> tableResult = readTableElementsInPages();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<String> denomResult = tableResult.get(0);
        String singleDenomResult = denomResult.get(2);
        tmess.azione("Il risultato della ricerca e' stato: " + singleDenomResult);

        tmess.azione("Controllo che i due risultati coincidano.");
        if (randomDenom.equals(singleDenomResult)) {
            tmess.risultati("La denominazione cercata: " + singleDenomResult + " risulta essere presente all'intenrno tabella: " + randomDenom);
        } else {
            tmess.Errori("La denominazione cercata non corrisponde a nessuna tra le presenti all'interno della tabella.");
        }
        tmess.conclusione(TEST_08, TESTFAMILY, success);
    }

    
    @Test
    public void Selezione_anagrafica_da_modificare() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_09, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_rubrica(driver);

        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tmess.azione("Seleziono una anagrafica dalla lista.");
        List<WebElement> table = driver.findElements(By.tagName("table"));
        List<WebElement> rows = table.get(1).findElements(By.tagName("tr"));
        Random generator = new Random();
        Integer n = generator.nextInt(rows.size() - 1) + 1;
        List<WebElement> column = rows.get(n).findElements(By.tagName("td"));
        WebElement cell = column.get(5).findElement(By.tagName("button"));
        tmess.azione("seleziono l'anagrafica " + n);
        cell.click();

        WebElement trField = driver.findElement(By.className("fieldsetComunicazione"));

        String unLabel = SeleniumUtilities.readLabelScadEven(trField).get(0).get(1);  //prima targhetta
        if (unLabel.equals("Nome")) {
            String nameDetail = SeleniumUtilities.readLabelScadEven(trField).get(1).get(1); //nome singolo 
            String surDetail = SeleniumUtilities.readLabelScadEven(trField).get(1).get(2); //cognome singolo
            String codDetail = SeleniumUtilities.readLabelScadEven(trField).get(1).get(3); //codice fiscale
            tmess.azione("L'anagrafica selezionata ha come nome: " + nameDetail + " ,cognome: " + surDetail + " ,e Codice Fiscale: " + codDetail);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            SeleniumUtilities.safeClick(driver, By.linkText("← Indietro"), tmess);

            OpenSearchPanel();
            SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_RUBR_CLEAN_NOME_ID), tmess).sendKeys(nameDetail);
            SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_RUBR_CLEAN_COGNOME_ID), tmess).sendKeys(surDetail);
            SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_RUBR_CLEAN_CODFIS_ID), tmess).sendKeys(codDetail);
            SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_RUBR_CLEANBTN_ID), tmess);

            List<List<String>> srcResult = readTableElementsInPages();
            List<String> fisResult = TestUtilities.getCol(srcResult, 4);
            tmess.azione("L'anagrafica cercata ha risultato come Codice Fiscale: " + fisResult.get(0));

            if (!fisResult.get(0).equals(codDetail)) {
                tmess.Errori("I risultati di ricerca e anagrafica selezionata non coincidono.");
            } else {
                tmess.risultati("I risultati di ricrca e anagrafica selezionata coincidono.");
            }
        } else {
            String ragDetail = SeleniumUtilities.readLabelScadEven(trField).get(1).get(0); //ragione sociale
            String pIvaDetail = SeleniumUtilities.readLabelScadEven(trField).get(1).get(3); //partita iva
            tmess.azione("L'anagrafica selezionata ha come ragione sociale: " + ragDetail + " ,e come partita IVA: " + pIvaDetail);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            SeleniumUtilities.safeClick(driver, By.linkText("← Indietro"), tmess);

            OpenSearchPanel();
            SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_RUBR_CLEAN_DENOM_ID), tmess).sendKeys(ragDetail);
            SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_RUBR_CLEAN_PIVA_ID), tmess).sendKeys(pIvaDetail);
            SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_RUBR_CLEANBTN_ID), tmess);

            List<List<String>> srcResult = readTableElementsInPages();
            List<String> fisResult = TestUtilities.getCol(srcResult, 3);
            tmess.azione("L'anagrafica cercata ha risultato come Partita IVA: " + fisResult.get(0));

            if (!fisResult.get(0).equals(pIvaDetail)) {
                tmess.Errori("I risultati di ricerca e anagrafica selezionata non coincidono.");
            } else {
                tmess.risultati("I risultati di ricrca e anagrafica selezionata coincidono.");
            }
        }
        tmess.conclusione(TEST_09, TESTFAMILY, success);
    }

    
    @Test
    public void Modifica_anagrafica_persona_fisica_1() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_12, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_rubrica(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        int a = 1;
        List<List<String>> tabletext = readTableElementsInPages();
        for (int i = 0; i < tabletext.size(); i++) {
            List<String> row = tabletext.get(i);
            if (row.get(1).equals("Persona Fisica")) {
                a = i;
                break;
            }
        }

        List<WebElement> button = SeleniumUtilities.findWebElementInCol(driver, "list", 5, 1, -1, By.tagName("div"), tmess);
        button.get(a).click();

        WebElement trField = driver.findElement(By.className("fieldsetComunicazione"));
        String allDatas = SeleniumUtilities.readLabelScadEven(trField).get(1).toString();
        List<String> bloccoLettura = new ArrayList<String>();
        String substring = allDatas.substring(1, allDatas.length() - 3);
        bloccoLettura.addAll(Arrays.asList(substring.split(", ")));

        tmess.azione(substring);
        tmess.azione("Il dettaglio dell'anagrafica scelta presenta i seguenti dati, nome: " + bloccoLettura.get(1) + " cognome: " + bloccoLettura.get(2) + " data: " + bloccoLettura.get(4) + " cittadinanza: " + bloccoLettura.get(6)
                + " comune: " + bloccoLettura.get(8));

        CleanDetail();

        SeleniumUtilities.safeFind(driver, By.id("nome"), tmess).sendKeys("Dati_prova");
        SeleniumUtilities.safeFind(driver, By.id("cognome"), tmess).sendKeys("Dati_prova");
        SeleniumUtilities.safeFind(driver, By.id("dataNascita"), tmess).sendKeys("01/01/00");
        SeleniumUtilities.safeFind(driver, By.id("desCittadinanza"), tmess).sendKeys("Francese");
        SeleniumUtilities.safeClick(driver, By.linkText("Francese"), tmess);
        SeleniumUtilities.safeFind(driver, By.id("desComuneNascita"), tmess).sendKeys("Filadelfia");
        SeleniumUtilities.safeClick(driver, By.linkText("FILADELFIA(CZ)"), tmess);
        SeleniumUtilities.safeFind(driver, By.id("localitaNascita"), tmess).sendKeys("Localita_nascita");
//        SeleniumUtilities.safeFind(driver, By.id("partitaIvaProfessionista")).sendKeys("P00I00V00A");
//        SeleniumUtilities.safeFind(driver, By.id("desTipoCollegio")).sendKeys("Dati_collegio");
//        SeleniumUtilities.safeFind(driver, By.id("numeroIscrizione")).sendKeys("001");
//        SeleniumUtilities.safeFind(driver, By.id("dataIscrizione")).sendKeys(("02/01/00"));
//        SeleniumUtilities.safeFind(driver, By.id("desProvinciaIscrizione")).sendKeys("Dati_provincia");
//        SeleniumUtilities.safeFind(driver, By.id("nIscrizioneRea")).sendKeys("101");
//        SeleniumUtilities.safeFind(driver, By.id("dataIscrizioneRea")).sendKeys("03/01/00");

        SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        SeleniumUtilities.HomePage(driver);
        GoTo_rubrica(driver);

        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        button = SeleniumUtilities.findWebElementInCol(driver, "list", 5, 1, -1, By.tagName("div"), tmess);
        button.get(a).click();

        WebElement trField2 = driver.findElement(By.className("fieldsetComunicazione"));
        String allDatas2 = SeleniumUtilities.readLabelScadEven(trField2).get(1).toString();
        List<String> bloccoLettura2 = new ArrayList<String>();
        String substring2 = allDatas2.substring(1, allDatas2.length() - 1);
        bloccoLettura2.addAll(Arrays.asList(substring2.split(", ")));

        tmess.azione("modifico i dati ottenendo la seguente configurazione, nome: " + bloccoLettura2.get(1) + ", cognome: " + bloccoLettura2.get(2) + ", data: " + bloccoLettura2.get(4) + ", cittadinanza: " + bloccoLettura2.get(6)
                + ", comune: " + bloccoLettura2.get(8) + ", localita' nascita: " + bloccoLettura2.get(9)
        //                + ", partita IVA: " + bloccoLettura2.get(9) + ", tipo collegio: " + bloccoLettura2.get(11) + ", numero iscrizione: " + bloccoLettura2.get(12)
        //                + ", data iscrizione: " + bloccoLettura2.get(13) + ", provincia iscrizione: " + bloccoLettura2.get(15) + ", numero REA: " + bloccoLettura2.get(17) + ", data REA: " + bloccoLettura2.get(18)
        );

        if (bloccoLettura2.equals(bloccoLettura)) {
            tmess.Errori("Il risultato salvato non corrisponde a quello letto.");
        } else {
            tmess.risultati("I dati sono stati modificati correttamente.");
        }
        tmess.azione("reinserisco i dati originali.");
        CleanDetail();
        SeleniumUtilities.safeFind(driver, By.id("nome"), tmess).sendKeys(bloccoLettura.get(1));
        SeleniumUtilities.safeFind(driver, By.id("cognome"), tmess).sendKeys(bloccoLettura.get(2));
        SeleniumUtilities.safeFind(driver, By.id("dataNascita"), tmess).sendKeys(bloccoLettura.get(4));
        SeleniumUtilities.safeFind(driver, By.id("desCittadinanza"), tmess).sendKeys(bloccoLettura.get(6));
        SeleniumUtilities.safeClick(driver, By.linkText(bloccoLettura.get(6)), tmess);
        SeleniumUtilities.safeFind(driver, By.id("desComuneNascita"), tmess).sendKeys(bloccoLettura.get(8));
        SeleniumUtilities.safeClick(driver, By.partialLinkText(bloccoLettura.get(8)), tmess);
        SeleniumUtilities.safeFind(driver, By.id("localitaNascita"), tmess).clear();
//        SeleniumUtilities.safeFind(driver, By.id("partitaIvaProfessionista")).clear();
//        SeleniumUtilities.safeFind(driver, By.id("desTipoCollegio")).clear();
//        SeleniumUtilities.safeFind(driver, By.id("numeroIscrizione")).clear();
//        SeleniumUtilities.safeFind(driver, By.id("dataIscrizione")).clear();
//        SeleniumUtilities.safeFind(driver, By.id("desProvinciaIscrizione")).clear();
//        SeleniumUtilities.safeFind(driver, By.id("nIscrizioneRea")).clear();
//        SeleniumUtilities.safeFind(driver, By.id("dataIscrizioneRea")).clear();
        SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tmess.conclusione(TEST_12, TESTFAMILY, success);
    }

    
    @Test
    public void Modifica_anagrafica_persona_fisisca_2() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_13, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_rubrica(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<List<String>> tabletext = readTableElementsInPages();
        for (int i = 0; i < tabletext.size(); i++) {
            List<String> row = tabletext.get(i);
            if (row.get(1).equals("Persona Fisica")) {
                List<WebElement> button = SeleniumUtilities.findWebElementInCol(driver, "list", 5, 1, -1, By.tagName("div"), tmess);
                button.get(i).click();
                SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

                if (driver.findElements(By.id("2")).isEmpty()) {
                    SeleniumUtilities.safeClick(driver, By.className("secondaryAction"), tmess);
                    SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
                } else {
                    break;
                }
            }
        }

        SeleniumUtilities.safeClick(driver, By.id("2"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        WebElement dialog = driver.findElement(By.className("ui-dialog"));
        WebElement section = dialog.findElement(By.id("recapitiSection"));
        WebElement comune = section.findElement(By.id("ComuneRecapito"));
        comune.sendKeys("Incudine");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.cssSelector("[class = 'ui-autocomplete ui-menu ui-widget ui-widget-content ui-corner-all']"), tmess);
        SeleniumUtilities.safeClick(driver, By.linkText("INCUDINE(BS)"), tmess);
        WebElement recapito = section.findElement(By.id("localitaRecapito"));
        recapito.sendKeys("Al Solivo");
        WebElement indirizzo = section.findElement(By.id("indirizzoRecapito"));
        indirizzo.sendKeys("via Martello");
        WebElement altreInfo = section.findElement(By.id("altreInfoIndirizzo"));
        altreInfo.sendKeys("");
        WebElement civico = section.findElement(By.id("civicoRecapito"));
        civico.sendKeys("1");
        WebElement cap = section.findElement(By.id("capRecapito"));
        cap.sendKeys("25040");
        WebElement casellaPostale = section.findElement(By.id("casellaPostaleRecapito"));
        casellaPostale.sendKeys("23");
        WebElement telefono = section.findElement(By.id("telefonoRecapito"));
        telefono.sendKeys("0323909090");
        WebElement cellulare = section.findElement(By.id("cellulareRecapito"));
        cellulare.sendKeys("892424");
        WebElement fax = section.findElement(By.id("faxRecapito"));
        fax.sendKeys("892425");
        WebElement mail = section.findElement(By.id("emailRecapito"));
        mail.sendKeys("carlo@comune.incudine.it");
        WebElement pec = section.findElement(By.id("pecRecapito"));
        pec.sendKeys("carlocert@comune.incudine.it");

        SeleniumUtilities.safeClick(driver, By.xpath("(//button[@type='button'])[4]"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tmess.Errori("Il popUp seguente non e' cliccabile da Selenium, pertanto non e' possibile modificare l'anagrafica.");
        tmess.conclusione(TEST_13, TESTFAMILY, success);
    }

    
    @Test
    public void Modifica_anagrafica_persona_fisica_3() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_14, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_rubrica(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        Integer a = 0;

        List<List<String>> tabletext = readTableElementsInPages();
        for (int i = 0; i < tabletext.size(); i++) {
            List<String> row = tabletext.get(i);
            if (row.get(1).equals("Persona Fisica")) {
                List<WebElement> button = SeleniumUtilities.findWebElementInCol(driver, "list", 5, 1, -1, By.tagName("div"), tmess);
                button.get(i).click();
                SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
                List<String> listBl = new ArrayList<String>();
                List<String> legendListString = SeleniumUtilities.changeListToString(driver.findElements(By.tagName("legend")));
                listBl.addAll(legendListString);
                tmess.azione("Seleziono l'anagrafica con nominativo: " + row.get(2) + " e codice fiscale: " + row.get(4));
                tmess.azione("Ho trovato i seguenti campi all'interno della Anagrafica: " + legendListString.toString());

                if (listBl.size() > 2) {
                    a = i;
                    break;
                } else {
                    tmess.azione("Non sono state trovate informazioni di domicilio nell'Anagrafica selezionata.");
                    SeleniumUtilities.safeClick(driver, By.className("secondaryAction"), tmess);
                }
            }
        }
        List<WebElement> boxDati = driver.findElements(By.className("fieldsetComunicazione"));
        List<String> bloccoLettura = new ArrayList<String>();
        for (int x = 0; x < boxDati.size(); x++) {
            List<String> legendListString = SeleniumUtilities.changeListToString(boxDati.get(x).findElements(By.tagName("legend")));
            if (legendListString.contains("DOMICILIO")) {
                List<List<String>> allDat = SeleniumUtilities.readLabelScadEven(boxDati.get(x));
                List<String> allDatas = allDat.get(1);
                bloccoLettura.addAll(allDatas);
            }
        }
        //recupero la string con tutti i dati del blocco DOMICILIO
        String comuneDato = bloccoLettura.get(5);
        String localitaDato = bloccoLettura.get(7);
        String indirizzoDato = bloccoLettura.get(8);
        String altreInfoDato = bloccoLettura.get(9);
        String civicoDato = bloccoLettura.get(10);
        String capDato = bloccoLettura.get(11);
        String postaDato = bloccoLettura.get(12);
        String telDato = bloccoLettura.get(13);
        String cellDato = bloccoLettura.get(14);
        String faxDato = bloccoLettura.get(15);
        String mailDato = bloccoLettura.get(16);
        String pecDato = bloccoLettura.get(17);

        SeleniumUtilities.safeClick(driver, By.name("2"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.className("secondaryAction"), tmess);

        List<WebElement> button = SeleniumUtilities.findWebElementInCol(driver, "list", 5, 1, -1, By.tagName("div"), tmess);
        button.get(a).click();

        List<WebElement> legendList = driver.findElements(By.tagName("legend"));
        List<String> legendListString = SeleniumUtilities.changeListToString(legendList);
        tmess.azione("Ho trovato i seguenti campi dopo l'eliminazione: " + legendListString.toString());

        if (!legendListString.contains("DOMICILIO")) {
            tmess.risultati("I dati di domicilio sono stati correttamente eliminati.");
        } else {
            tmess.Errori("I dati di domicilio sono ancora presenti.");
        }
        //reinserimento dei dati.
        SeleniumUtilities.safeClick(driver, By.id("2"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        WebElement dialog = driver.findElement(By.className("ui-dialog"));
        WebElement section = dialog.findElement(By.id("recapitiSection"));
        WebElement comune = section.findElement(By.id("ComuneRecapito"));
        comune.sendKeys(comuneDato);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.cssSelector("[class = 'ui-autocomplete ui-menu ui-widget ui-widget-content ui-corner-all']"), tmess);
        SeleniumUtilities.safeClick(driver, By.partialLinkText(comuneDato), tmess);
        WebElement recapito = section.findElement(By.id("localitaRecapito"));
        recapito.sendKeys(localitaDato);
        WebElement indirizzo = section.findElement(By.id("indirizzoRecapito"));
        indirizzo.sendKeys(indirizzoDato);
        WebElement altreInfo = section.findElement(By.id("altreInfoIndirizzo"));
        altreInfo.sendKeys(altreInfoDato);
        WebElement civico = section.findElement(By.id("civicoRecapito"));
        civico.sendKeys(civicoDato);
        WebElement cap = section.findElement(By.id("capRecapito"));
        cap.sendKeys(capDato);
        WebElement casellaPostale = section.findElement(By.id("casellaPostaleRecapito"));
        casellaPostale.sendKeys(postaDato);
        WebElement telefono = section.findElement(By.id("telefonoRecapito"));
        telefono.sendKeys(telDato);
        WebElement cellulare = section.findElement(By.id("cellulareRecapito"));
        cellulare.sendKeys(cellDato);
        WebElement fax = section.findElement(By.id("faxRecapito"));
        fax.sendKeys(faxDato);
        WebElement mail = section.findElement(By.id("emailRecapito"));
        mail.sendKeys(mailDato);
        WebElement pec = section.findElement(By.id("pecRecapito"));
        pec.sendKeys(pecDato);

        WebElement btnBox = driver.findElement(By.className("ui-dialog-buttonset"));
        List<WebElement> buttons = btnBox.findElements(By.className("ui-button"));
        WebElement btnOk = buttons.get(0);
        btnOk.click();
        tmess.azione("Dati reinseriti nel loro campo di appartenenza.");
        tmess.conclusione(TEST_14, TESTFAMILY, success);
    }

    
    @Test
    public void Modifica_anagrafica_persona_giuridica_1() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_17, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_rubrica(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        Integer a = 0;

        List<List<String>> tabletext = readTableElementsInPages();
        for (int i = 0; i < tabletext.size(); i++) {
            List<String> row = tabletext.get(i);
            if (row.get(1).equals("Persona Giuridica")) {
                List<WebElement> button = SeleniumUtilities.findWebElementInCol(driver, "list", 5, 1, -1, By.tagName("div"), tmess);
                button.get(i).click();
                a = 1;
                break;
            }
        }

        List<WebElement> boxDati = driver.findElements(By.className("fieldsetComunicazione"));
        List<String> bloccoLettura = new ArrayList<String>();
        for (int x = 0; x < boxDati.size(); x++) {
            List<List<String>> allDat = SeleniumUtilities.readLabelScadEven(boxDati.get(x));
            List<String> allDatas = allDat.get(1);
            bloccoLettura.addAll(allDatas); //risultati
        }
        //TMess.azione(bloccoLettura.toString());

        String ragioneSociale = bloccoLettura.get(0);
        String formaGiuridica = bloccoLettura.get(2);
        String nIscrizRI = bloccoLettura.get(7);
        String dIscrizRI = bloccoLettura.get(8);
        String ProvinCIAA = bloccoLettura.get(9);
        String nIscrizRea = bloccoLettura.get(12);
        String dIscrizRea = bloccoLettura.get(13);
        String comune = bloccoLettura.get(18);
        String localita = bloccoLettura.get(20);
        String indirizzo = bloccoLettura.get(21);
        String nCivico = bloccoLettura.get(23);
        String cap = bloccoLettura.get(24);
        String casellaPo = bloccoLettura.get(25);
        String telefono = bloccoLettura.get(26);
        String cell = bloccoLettura.get(27);
        String fax = bloccoLettura.get(28);
        String email = bloccoLettura.get(29);
        String pec = bloccoLettura.get(30);
        boolean selectorRI = SeleniumUtilities.safeFind(driver, By.id("flgAttesaIscrizioneRi"), tmess).isSelected(); //True
        boolean selectorREA = SeleniumUtilities.safeFind(driver, By.id("flgAttesaIscrizioneRea"), tmess).isSelected();//false

        CleanDetailPersonaGiuridica();
        tmess.azione("Clean dei campi e immissione di nuovi dati");
        SeleniumUtilities.safeFind(driver, By.id("denominazione"), tmess).sendKeys("Ditta Srl");
        SeleniumUtilities.safeFind(driver, By.id("desFormaGiuridica"), tmess).sendKeys("Associazione riconosciuta");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.linkText("Associazione riconosciuta"), tmess);
        SeleniumUtilities.safeFind(driver, By.id("nIscrizioneRi"), tmess).sendKeys("0000");
        SeleniumUtilities.safeFind(driver, By.id("dataIscrizioneRi"), tmess).sendKeys("01/01/2000");
        SeleniumUtilities.safeClick(driver, By.cssSelector(".ui-state-active"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id("desProvinciaIscrizioneRi"), tmess).sendKeys("Aosta");
        SeleniumUtilities.safeFind(driver, By.id("nIscrizioneRea"), tmess).sendKeys("0000");
        SeleniumUtilities.safeFind(driver, By.id("dataIscrizioneRea"), tmess).sendKeys("01/01/2000");
        SeleniumUtilities.safeClick(driver, By.cssSelector(".ui-state-active"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id("ComuneRecapito"), tmess).sendKeys("AOSTA");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.linkText("AOSTA(AO)"), tmess);
        SeleniumUtilities.safeFind(driver, By.id("localitaRecapito"), tmess).sendKeys("Courmayeur");
        SeleniumUtilities.safeFind(driver, By.id("indirizzoRecapito"), tmess).sendKeys("Via generale");
        SeleniumUtilities.safeFind(driver, By.id("civicoRecapito"), tmess).sendKeys("00");
        SeleniumUtilities.safeFind(driver, By.id("capRecapito"), tmess).sendKeys("00000");
        SeleniumUtilities.safeFind(driver, By.id("casellaPostaleRecapito"), tmess).sendKeys("00");
        SeleniumUtilities.safeFind(driver, By.id("telefonoRecapito"), tmess).sendKeys("0000000000");
        SeleniumUtilities.safeFind(driver, By.id("cellulareRecapito"), tmess).sendKeys("0000000000");
        SeleniumUtilities.safeFind(driver, By.id("faxRecapito"), tmess).sendKeys("000000000");
        SeleniumUtilities.safeFind(driver, By.id("emailRecapito"), tmess).sendKeys("aaaa@bbbb.it");
        SeleniumUtilities.safeFind(driver, By.id("pecRecapito"), tmess).sendKeys("cccc@dddd.it");
        WebElement check = SeleniumUtilities.safeFind(driver, By.id("flgAttesaIscrizioneRi"), tmess);
        if (check.isSelected()) {
            SeleniumUtilities.safeClick(driver, By.id("flgAttesaIscrizioneRi"), tmess);
        }
        WebElement checkREA = SeleniumUtilities.safeFind(driver, By.id("flgAttesaIscrizioneRea"), tmess);
        if (checkREA.isSelected()) {
            SeleniumUtilities.safeClick(driver, By.id("flgAttesaIscrizioneRea"), tmess);
        }
        SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        //torno indietro e riclicco la stessa pratica.
        List<WebElement> button = SeleniumUtilities.findWebElementInCol(driver, "list", 5, 1, -1, By.tagName("div"), tmess);
        button.get(a).click();

        boxDati = driver.findElements(By.className("fieldsetComunicazione"));
        List<String> controlloLettura = new ArrayList<String>();
        for (int x = 0; x < boxDati.size(); x++) {
            List<List<String>> allData = SeleniumUtilities.readLabelScadEven(boxDati.get(x));
            List<String> allDati = allData.get(1);
            controlloLettura.addAll(allDati); //risultati
        }
//        TMess.azione(controlloLettura.toString());
//        TMess.azione(bloccoLettura.toString());
        if (controlloLettura.toString().equals(bloccoLettura.toString())) {
            tmess.Errori("I dati dell'anagrafica non sono sati modificati.");
        } else {
            tmess.risultati("I dati dell'anagrafica sono stati modificati con successo.");
        }
        CleanDetailPersonaGiuridica();
        tmess.azione("Clean e reinserimento dei dati originali.");
        SeleniumUtilities.safeFind(driver, By.id("denominazione"), tmess).sendKeys(ragioneSociale);
        SeleniumUtilities.safeFind(driver, By.id("desFormaGiuridica"), tmess).sendKeys(formaGiuridica.replace("à", "a"));
        SeleniumUtilities.safeClick(driver, By.linkText(formaGiuridica), tmess);
        SeleniumUtilities.safeFind(driver, By.id("nIscrizioneRi"), tmess).sendKeys(nIscrizRI);
        SeleniumUtilities.safeFind(driver, By.id("dataIscrizioneRi"), tmess).sendKeys(dIscrizRI);
        SeleniumUtilities.safeClick(driver, By.id("flgAttesaIscrizioneRi"), tmess);
        SeleniumUtilities.safeFind(driver, By.id("desProvinciaIscrizioneRi"), tmess).sendKeys(ProvinCIAA);
        SeleniumUtilities.safeFind(driver, By.id("nIscrizioneRea"), tmess).sendKeys(nIscrizRea);
        SeleniumUtilities.safeFind(driver, By.id("dataIscrizioneRea"), tmess).sendKeys(dIscrizRea);
        SeleniumUtilities.safeClick(driver, By.id("flgAttesaIscrizioneRi"), tmess);
        SeleniumUtilities.safeFind(driver, By.id("ComuneRecapito"), tmess).sendKeys(comune);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.partialLinkText(comune), tmess);
        SeleniumUtilities.safeFind(driver, By.id("localitaRecapito"), tmess).sendKeys(localita);
        SeleniumUtilities.safeFind(driver, By.id("indirizzoRecapito"), tmess).sendKeys(indirizzo);
        SeleniumUtilities.safeFind(driver, By.id("civicoRecapito"), tmess).sendKeys(nCivico);
        SeleniumUtilities.safeFind(driver, By.id("capRecapito"), tmess).sendKeys(cap);
        SeleniumUtilities.safeFind(driver, By.id("casellaPostaleRecapito"), tmess).sendKeys(casellaPo);
        SeleniumUtilities.safeFind(driver, By.id("telefonoRecapito"), tmess).sendKeys(telefono);
        SeleniumUtilities.safeFind(driver, By.id("cellulareRecapito"), tmess).sendKeys(cell);
        SeleniumUtilities.safeFind(driver, By.id("faxRecapito"), tmess).sendKeys(fax);
        SeleniumUtilities.safeFind(driver, By.id("emailRecapito"), tmess).sendKeys(email);
        SeleniumUtilities.safeFind(driver, By.id("pecRecapito"), tmess).sendKeys(pec);
        if (selectorRI == true) {
            SeleniumUtilities.safeClick(driver, By.id("flgAttesaIscrizioneRi"), tmess);
        }
        if (selectorREA == true) {
            SeleniumUtilities.safeClick(driver, By.id("flgAttesaIscrizioneRea"), tmess);
        }
        SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
        tmess.conclusione(TEST_17, TESTFAMILY, success);
    }

    @AfterClass
    static public void tearDown() throws Exception {
        driver.quit();
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

    private void OpenSearchPanel() {
        SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_PRAT_APRI_PANNELLO_RICERCA_BUTTON_CSSSELECTOR), tmess);
    }

    private void clickAzione(Integer npratica) {
        List<WebElement> pulsantiAzione = SeleniumUtilities.findWebElementInCol(driver, "list", 10, 1, -1, By.tagName("div"), tmess);
        pulsantiAzione.get(npratica).click();
    }

    private void CleanSearch(Boolean commit) throws Exception {
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_RUBR_CLEAN_NOME_ID), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_RUBR_CLEAN_COGNOME_ID), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_RUBR_CLEAN_DENOM_ID), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_RUBR_CLEAN_CODFIS_ID), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id(WebElMap.AP_RUBR_CLEAN_PIVA_ID), tmess).clear();
        if (commit) {
            tmess.azione("faccio partire la ricerca");
            SeleniumUtilities.safeClick(driver, By.id(WebElMap.AP_RUBR_CLEANBTN_ID), tmess);
        }
    }

    private void CleanDetailPersonaGiuridica() throws Exception {
        SeleniumUtilities.safeFind(driver, By.id("denominazione"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("desFormaGiuridica"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("nIscrizioneRi"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("dataIscrizioneRi"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("desProvinciaIscrizioneRi"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("nIscrizioneRea"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("dataIscrizioneRea"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("ComuneRecapito"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("localitaRecapito"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("indirizzoRecapito"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("altreInfoIndirizzo"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("civicoRecapito"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("capRecapito"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("casellaPostaleRecapito"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("telefonoRecapito"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("cellulareRecapito"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("faxRecapito"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("emailRecapito"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("pecRecapito"), tmess).clear();
    }

    private void CleanDetail() throws Exception {
        SeleniumUtilities.safeFind(driver, By.id("nome"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("cognome"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("dataNascita"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("desCittadinanza"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("desComuneNascita"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.id("localitaNascita"), tmess).clear();
        new Select(driver.findElement(By.id("sesso"))).selectByVisibleText("Seleziona il sesso");
    }

}
