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
import java.util.Arrays;
import java.util.HashSet;
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

public class GestioneUtentiTest {

    static final private String TESTFAMILY = "GestioneUtenti";
    static final private String TEST_01 = "Controllo_ricerca_gestione_utenti";
    static final private String TEST_02 = "Inserimento_nuovo_utente";
    static final private String TEST_03 = "Modifica_utente";
    static final private String TEST_04 = "Seleziona_ente";
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

    private void GoTo_gestione_utenti(WebDriver driver) {
        SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_GESUT_IMPOSTAZIONI_BUTTON_CSSSELECTOR), tmess);
        SeleniumUtilities.safeClick(driver, By.xpath(WebElMap.AP_GESUT_APERTURA_GESTIONE_BUTTON_XPATH), tmess);
    }

    
    @Test
    public void Controllo_ricerca_gestione_utenti() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_03, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_utenti(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        OpenSearchPanel();
        List<List<String>> tabletext = readTableElementsInPages();
        List<String> cFiscale = TestUtilities.getCol(tabletext, 3);
        Random generator = new Random();
        Integer n = generator.nextInt(tabletext.size());
        tmess.azione("Apro la pratica utente numero " + n);
        String singleCfiscale = cFiscale.get(n);
        SeleniumUtilities.safeFind(driver, By.id("codiceFiscale"), tmess).sendKeys(singleCfiscale);
        SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        
        SeleniumUtilities.safeClick(driver, By.cssSelector("div.gridActionContainer > a > img"), tmess);

        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tmess.azione("Recupero i dati di nome e cognome dal dettaglio dell'Utente.");
        List<WebElement> boxDati = driver.findElements(By.id("frame1"));
        List<List<String>> allDat = SeleniumUtilities.readLabelScadEven(boxDati.get(0));
        List<String> allDatas = allDat.get(1);
        String nome = allDatas.get(1);
        String cognome = allDatas.get(2);
        String codiceFiscale = new String();
        if (!allDatas.get(3).isEmpty()) {
            codiceFiscale = allDatas.get(3);
        } else {
            tmess.warning("Attenzione, il codice fiscale dell'utente selezionato e' assente, verra' fornito un codice fiscale alternativo.");
            codiceFiscale = ("LNNCTA62L59H282V");
        }

        SeleniumUtilities.safeClick(driver, By.className("secondaryAction"), tmess);
        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id("codiceFiscale"), tmess).sendKeys(codiceFiscale);
        SeleniumUtilities.safeFind(driver, By.id("nome"), tmess).sendKeys(nome);
        SeleniumUtilities.safeFind(driver, By.id("cognome"), tmess).sendKeys(cognome);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        tmess.azione("Eseguo la ricerca secondo i dati di codice fiscale, nome e cognome dell'utente.");
        List<List<String>> tabletextResult = readTableElementsInPages();
        String resultNome = TestUtilities.getCol(tabletextResult, 2).get(0);
        String resultCf = TestUtilities.getCol(tabletextResult, 3).get(0);

        if (resultNome.equals(cognome + " " + nome)
                && resultCf.equals(codiceFiscale)) {
            tmess.risultati("Il risultato della ricerca corrisponde all'utente selezionato.");
        } else {
            tmess.Errori("Il risultato della ricerca non corrisponde all'utente selezionato.");
        }
        tmess.conclusione(TEST_01, TESTFAMILY, success);
    }

    @Ignore
    @Test
    public void Inserimento_nuovo_utente() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_02, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_utenti(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.linkText("Aggiungi utente"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        String nome = ("nomeUtente");
        String cognome = ("cognomeUtente");
        String cFiscale = ("CF0000000");
        SeleniumUtilities.safeFind(driver, By.id("nome"), tmess).sendKeys(nome);
        SeleniumUtilities.safeFind(driver, By.id("cognome"), tmess).sendKeys(cognome);
        SeleniumUtilities.safeFind(driver, By.id("codiceFiscale"), tmess).sendKeys(cFiscale);
        SeleniumUtilities.safeFind(driver, By.id("username"), tmess).sendKeys(cFiscale);
        SeleniumUtilities.safeFind(driver, By.id("email"), tmess).sendKeys("utente@mail.com");
        SeleniumUtilities.safeFind(driver, By.id("telefono"), tmess).sendKeys("0000000000");
        WebElement checkSuperUser = SeleniumUtilities.safeFind(driver, By.id("superusercheck"), tmess);
        if (!checkSuperUser.isSelected()) {
            SeleniumUtilities.safeClick(driver, By.id("superusercheck"), tmess);
        }
        SeleniumUtilities.safeFind(driver, By.id("note"), tmess).sendKeys("notaUtente");
        SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<WebElement> errorMsg = driver.findElements (By.id("errorMsg"));
        if(!errorMsg.isEmpty()){
            SeleniumUtilities.safeFind(driver, By.id("username"), tmess).sendKeys("1");
            SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
            
        }
        OpenSearchPanel();
        SeleniumUtilities.safeFind(driver, By.id("nome"), tmess).sendKeys(nome);
        SeleniumUtilities.safeFind(driver, By.id("cognome"), tmess).sendKeys(cognome);
        SeleniumUtilities.safeFind(driver, By.id("codiceFiscale"), tmess).sendKeys(cFiscale);
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        List<List<String>> tabletext = readTableElementsInPages();
        String singleCFsearch = TestUtilities.getCol(tabletext, 3).get(0);
        String singleNameSearch = TestUtilities.getCol(tabletext, 2).get(0);
        String cognomeSrc = singleNameSearch.split(" ")[0];
        String nomeSrc = singleNameSearch.split(" ")[1];
        if (nomeSrc.equals(nome)
                && cognomeSrc.equals(cognome)
                && singleCFsearch.equals(cFiscale)) {
            tmess.risultati("Il risultato dela ricerca ha confermato la presenza del nuovo utente, nella lista utenti.");
        } else {
            tmess.Errori("La ricerca non ha trovato il nuovo utente inserito.");
        }
        tmess.warning("L'eliminazione dell'utente non e' stata possibile dalla sessione corrente.");
        tmess.conclusione(TEST_02, TESTFAMILY, success);
    }

    @Ignore
    @Test
    public void Modifica_utente() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_03, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_utenti(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        OpenSearchPanel();
        List<List<String>> tabletext = readTableElementsInPages();
        tmess.azione("Seleziono un utente dalla lista in Gestione Utenti.");
//          //METODO PER RECUPERARE L'USER ATTIVO (NON CANCELLARE!!)
//          WebElement userBar = SeleniumUtilities.safeFind(driver, By.id("user-bar"), tmess);
//          WebElement pBox = userBar.findElement(By.tagName("p"));
//          WebElement strongBox = pBox.findElement(By.tagName("strong")); //.getText() per avere il nome utente.
//          //tmess.azione(strongBox.getText());
//          String utenteAttivo = strongBox.getText();
//          HashSet utenteH = new HashSet();
//          String nomeAttivo = utenteAttivo.split(" ")[0];
//          utenteH.add(nomeAttivo);
//          String cognomeAttivo = utenteAttivo.split(" ")[1];
//          utenteH.add(cognomeAttivo);     

        int tableSiz = tabletext.size();
        Random generator = new Random();
        Integer n = generator.nextInt(tableSiz);

//          List<String> nomeSrcA = new ArrayList<String>();
//          HashSet nomeSrcH = new HashSet();
//          HashSet cognomeSrcA = new HashSet();
//          //avvio una ricerca per codice fiscale.
//          for (int y = n; y < tableSiz; y++) {
        String singleCFsearch = TestUtilities.getCol(tabletext, 3).get(n);
        SeleniumUtilities.safeFind(driver, By.id("codiceFiscale"), tmess).sendKeys(singleCFsearch);
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tabletext = readTableElementsInPages();
        singleCFsearch = TestUtilities.getCol(tabletext, 3).get(0);
        String singleNameSearch = TestUtilities.getCol(tabletext, 2).get(0);
        //tmess.azione(singleNameSearch + " = " + utenteAttivo);

        String cognomeSrc = singleNameSearch.split(" ")[0];
//            nomeSrcH.add(cognomeSrc);
//            nomeSrcA.add(cognomeSrc);
        String nomeSrc = singleNameSearch.split(" ")[1];
//            nomeSrcH.add(nomeSrc);
//            nomeSrcA.add(nomeSrc);

//            if (utenteH != nomeSrcH) {
        tmess.azione("E' stato trovato l'utente avente come nome: " + nomeSrc + ", cognome: " + cognomeSrc + ", codice fiscale: " + singleCFsearch);  
        SeleniumUtilities.safeClick(driver, By.cssSelector("div.gridActionContainer > a > img"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
//                break;
//            } else {
//                tmess.azione("L'utente selezionato e' l'utente della sessione corrente, pertanto ogni modifica e' sconsigliata. Verra' selezionato un'altro utente dalla lista.");
//                SeleniumUtilities.safeFind(driver, By.id("codiceFiscale"), tmess).clear();
//               SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
//            }
//        }

        WebElement allLabels = SeleniumUtilities.safeFind(driver, By.className("inlineLabels"), tmess);
        List<WebElement> areaInput = allLabels.findElements(By.className("textInput"));
        tmess.azione("modifico i valori all'interno del dettaglio utente.");

        List<String> values = new ArrayList<String>();
        //METODO RACCOLTA DATI.
        for (int i = 0; i < areaInput.size(); i++) {
            values.add(areaInput.get(i).getAttribute("value"));
        }

        //METODO PER L'INSERIMENTO DATI
        for (int i = 0; i < areaInput.size(); i++) {
            if (i == 3) {
                continue;
            }
            areaInput.get(i).sendKeys("1");
        }
        //tmess.azione(values.toString());
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.name("action"), tmess);
        tmess.azione("Torno alla lista utenti ed eseguo una nuova ricerca dello stesso utente, specificando i nuovi valori modificati.");
        OpenSearchPanel();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id("nome"), tmess).sendKeys(values.get(0) + "1");
        SeleniumUtilities.safeFind(driver, By.id("cognome"), tmess).sendKeys(values.get(1) + "1");
        SeleniumUtilities.safeClick(driver, By.id("ricerca_button"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tabletext = readTableElementsInPages();
        String cFconfirm = TestUtilities.getCol(tabletext, 3).get(0);
        String nomeCognomeConfirm = TestUtilities.getCol(tabletext, 2).get(0);
        String cognomeConfirm = nomeCognomeConfirm.split(" ")[0];
        String nomeConfirm = nomeCognomeConfirm.split(" ")[1];

        if (nomeConfirm.equals(nomeSrc + "1")
                && cognomeConfirm.equals(cognomeSrc + "1")) {
            tmess.risultati("E' stato trovato l'utente avente come nome: " + nomeConfirm + ", cognome: " + cognomeConfirm + ", codice fiscale: " + cFconfirm + ", modifica avvenuta con successo.");
        } else {
            tmess.Errori("Uno o piu' parametri del'utente non sono stati modificati.");
        }
        SeleniumUtilities.safeClick(driver, By.cssSelector("div.gridActionContainer > a > img"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        allLabels = SeleniumUtilities.safeFind(driver, By.className("inlineLabels"), tmess);
        areaInput = allLabels.findElements(By.className("textInput"));
        int totValori = values.size();

        for (int c = 0; c < totValori; c++) {
            if (c == 3 || c == 6) {
                continue;
            }
            areaInput.get(c).clear();
            areaInput.get(c).sendKeys(values.get(c));
        }

        SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);

        tmess.conclusione(TEST_03, TESTFAMILY, success);
    }

   @Ignore
   @Test
    public void Seleziona_ente() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_04, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_utenti(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        OpenSearchPanel();
        List<List<String>> tabletext = readTableElementsInPages();
        List<String> cFiscale = TestUtilities.getCol(tabletext, 3);
        Random generator = new Random();
        Integer n = generator.nextInt(tabletext.size());
        tmess.azione("Apro la pratica utente numero " + n);
        String singleCfiscale = cFiscale.get(n);
        SeleniumUtilities.safeFind(driver, By.id("codiceFiscale"), tmess).sendKeys(singleCfiscale);
        SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.cssSelector("div.gridActionContainer > a > img"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.xpath("(//button[@name='action'])[2]"), tmess);
        tmess.azione("Apro il pannello Seleziona Ente.");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<List<String>> textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        Random generatorEnte = new Random();
        Integer m = generatorEnte.nextInt(textable.size());

        List<String> actionCol = TestUtilities.getCol(textable, 7);
        for (int i = m; i < actionCol.size(); i++) {
            tmess.azione("premo l'ente numero: " + m);
            SeleniumUtilities.safeClick(driver, By.className("ui-button"), tmess);
            break;
        }
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> paginaVuota = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        tmess.azione(paginaVuota.toString());
        if (paginaVuota.isEmpty()) {
            tmess.Errori("Non e' stato trovato alcun ente da selezionare.");
        }
        tmess.conclusione(TEST_04, TESTFAMILY, success);
    }

    //I Test seguenti necessitano l'abilitazione delle tabelle di gestione enti.
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
}
