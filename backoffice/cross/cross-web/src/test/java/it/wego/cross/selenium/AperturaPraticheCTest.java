/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.selenium;

import static it.wego.cross.selenium.AperturaPraticheBTest.showDettaglioBeneficiario;
import static it.wego.cross.selenium.AperturaPraticheBTest.showDettaglioRichiedente;
import static it.wego.cross.selenium.WebElMap.clickAnagraficaDettaglio;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
public class AperturaPraticheCTest {

    static final private String TESTFAMILY = "AperturaPratiche";
    static final private String TEST_17 = "SelezionaPraticaDaAprire";
    static final private String TEST_18 = "InserimentoNumeroProtocollo";
    static final private String TEST_19 = "ScaricaRiepilogo";
    static final private String TEST_20 = " ConfrontoDatiAnagraficiRichiedenteApertura";
    static final private String TEST_21 = " ConfrontoDatiAnagraficiRichiedente1";
    static final private String TEST_22 = " ConfrontoDatiAnagraficiRichiedente2";
    static final private String TEST_23 = " ConfrontoDatiAnagraficiRichiedente3";
    static final private String TEST_24 = " ConfrontoDatiAnagraficiRichiedente4";
    static final private String PAGENUMBERID = "sp_1_pager";
    static final private String GOTOFIRSTPAGEBOTTON = "ui-icon.ui-icon-seek-first";
    static final private Integer PERSONA_FISICA = 4;
    static final private Integer DITTA_INDIVIDUALE = 3;
    static final private Integer PERSONA_GIURIDICA = 5;
    static private WebDriver driver;
    static private UrlStatusChecker urlChecker;
    static private FileDownloader fileDownloader;
    private boolean acceptNextAlert = true;
    static private TMess tmess;

    @BeforeClass
    public static void setUp() throws MalformedURLException, FileNotFoundException, UnsupportedEncodingException, URISyntaxException {
        DesiredCapabilities capability = DesiredCapabilities.firefox();
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        driver = new ChromeDriver();  //for local check
        driver.manage().window().setSize(new Dimension(1920, 1080));
//        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        tmess = new TMess();
        tmess.SetSeleniumOutStreamLog();
        tmess.NuovaFamigliaTest(TESTFAMILY);
        urlChecker = new UrlStatusChecker(driver);
        fileDownloader = new FileDownloader(driver);
        SeleniumUtilities.Login(driver, tmess);
    }

    private void goTo_elenco_praticheNuove(WebDriver driver) throws Exception {
        WebElMap.click_Gestione_Voce(driver, WebElMap.MENU_GESTIONE_APERTURA_PRATICHE);
    }

    private void aperturaPratica(Integer npratica) {
        Integer praticaCliccata = 1;

        List<WebElement> pulsantiAzione = SeleniumUtilities.findWebElementInCol(driver, "list", WebElMap.AP_PRAT_COLONNA_AZIONE, 1, -1, By.tagName("div"), tmess);
        pulsantiAzione.get(praticaCliccata).click();
    }

    private String aperturaPraticaCasuale(List<String> praticheId) throws Exception {
        tmess.azione("Scelgo una pratica in modo casuale tra quelle della prima pagina.");
        Random generator = new Random();
        Integer praticaScelta = generator.nextInt(praticheId.size()) + 1;
        String protocollo = SeleniumUtilities.readDatumFromTable(driver, "list", "title", praticaScelta - 1, WebElMap.AP_PRAT_COLONNA_ID);
        tmess.azione("protocollo" + protocollo);
        tmess.azione("Clicco il pulsante azione relativo alla pratica con id" + praticheId.get(praticaScelta - 1));
        aperturaPratica(praticaScelta);
        return protocollo;
    }

    private List<String> getIdPratiche() {
        List<List<String>> table = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        return TestUtilities.getCol(table, WebElMap.AP_PRAT_COLONNA_ID);
    }


    @Ignore
    @Test
    //Verifica che le pratiche vengano ordinate correttamente per data e per oggetto
    public void aperturaPratiche() throws Exception {
              Boolean success = true;
        tmess.NuovoTest(TEST_17, TESTFAMILY);
        tmess.azione("Questo test deve essere ancora fatto.");
        tmess.conclusione(TEST_17, TESTFAMILY, success);

    }

    //Questo test e' da chiedere, serviranno pratiche che non hanno il numero di protocollo inserito
    @Ignore
    @Test
    public void inserimentoNumeroProtocollo() throws Exception {
        Boolean success = true;
        tmess.NuovoTest(TEST_18, TESTFAMILY);
        tmess.azione("Questo test deve essere ancora fatto.");
        tmess.conclusione(TEST_18, TESTFAMILY, success);

    }
// http://ardesco.lazerycode.com/index.php/2012/07/how-to-download-files-with-selenium-and-why-you-shouldnt/
//test ancora da verificare, vedere nel link per verificare il file scaricato.

    @Ignore
    @Test
    public void scaricaRiepilogo() throws Exception {
        Boolean success = true;
        tmess.NuovoTest(TEST_19, TESTFAMILY);
       
        tmess.conclusione(TEST_19, TESTFAMILY, success);
    }

  
    @Test
    public void ConfrontoDatiAnagraficiRichiedenteApertura() throws Exception {
        Boolean success = true;
        tmess.NuovoTest(TEST_20, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        goTo_elenco_praticheNuove(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess); 
        WebElement tabellaAnagrafiche = SeleniumUtilities.safeFind(driver, By.tagName("table"), tmess);
        List<WebElement> trAnagrafiche = tabellaAnagrafiche.findElements(By.tagName("td"));
        List<Boolean> confermate = new ArrayList<Boolean>();
        for(WebElement tr : trAnagrafiche){
          confermate.add(SeleniumUtilities.findAnagraficaConfermata(tr, WebElMap.AP_PRAT_ANAGRAFICA_CONFERMATA_TITLE, tmess));
        }
        List<List<List<String>>> anagraficheCollegate = new ArrayList<List<List<String>>>();
        for (WebElement tr : trAnagrafiche) {
            List<List<String>> anagrafica = SeleniumUtilities.readLebelledInputMask(tr);
            anagraficheCollegate.add(anagrafica);
        }
        List<String> anagraficaRichiedente = anagraficheCollegate.get(0).get(1);
        String nome = anagraficaRichiedente.get(1);
        String cognome = anagraficaRichiedente.get(2);
        String codicefiscale = anagraficaRichiedente.get(3);
        String partitaiva = "";
        Integer tipoAnagrafica = anagraficaRichiedente.size();
        if (tipoAnagrafica > 4) {
            partitaiva = anagraficaRichiedente.get(4);
        }
        tmess.azione("anagrafiche lette:" + anagraficheCollegate.toString());
        tmess.azione("Seleziono l'anagrafica del richiedente della pratica che deve essere presente. ");
         tmess.azione("Confermate." + confermate.toString());
        tmess.conclusione(TEST_20, TESTFAMILY, success);
    }


    @Ignore
    @Test
    public void ConfrontoDatiAnagraficiRichiedente2() throws Exception {
        Boolean success = true;
        tmess.NuovoTest(TEST_21, TESTFAMILY);
        tmess.conclusione(TEST_21, TESTFAMILY, success);

    }

    @Ignore
    @Test
    public void ConfrontoDatiAnagraficiRichiedente3() throws Exception {
        Boolean success = true;
        tmess.NuovoTest(TEST_22, TESTFAMILY);
        tmess.conclusione(TEST_22, TESTFAMILY, success);

    }

    @Ignore
    @Test
    public void ConfrontoDatiAnagraficiRichiedente4() throws Exception {
        Boolean success = true;
        tmess.NuovoTest(TEST_23, TESTFAMILY);
        tmess.conclusione(TEST_23, TESTFAMILY, success);

    }

    static public List<List<String>> readTableElementsInPages() throws Exception {
        System.out.println("inizio");
        Integer pageNumber = Integer.parseInt(driver.findElement(By.id(PAGENUMBERID)).getText());
        tmess.azione("page number" + pageNumber.toString());
        List<List<String>> result = SeleniumUtilities.readTableElementsInPages(driver, By.cssSelector("span.ui-icon.ui-icon-seek-next"), By.cssSelector("span.ui-icon.ui-icon-seek-first"), "list", "title", pageNumber, tmess);
        return result;
    }

    @AfterClass
    static public void tearDown() throws Exception {
        driver.quit();
    }

    private void OpenSearchPanel() {
        driver.findElement(By.cssSelector("span.ui-icon.ui-icon-triangle-1-e")).click();
    }

    private void CleanSearch(Boolean commit) throws Exception {
        driver.findElement(By.id("search_data_to")).clear();
        driver.findElement(By.id("search_id_pratica")).clear();
        WebElement check = driver.findElement(By.id("search_all"));
        driver.findElement(By.id("search_all")).click();
        if (check.isSelected()) {
            driver.findElement(By.id("search_all")).click();
        } else {
        }
        new Select(driver.findElement(By.id("search_ente"))).selectByVisibleText("Qualisiasi ente");
        new Select(driver.findElement(By.id("search_stato"))).selectByVisibleText("Qualsiasi stato");
        driver.findElement(By.id("search_data_from")).clear();
        if (commit) {
            driver.findElement(By.id("ricerca_button")).click();
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
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

    private static class ArrayListImpl extends ArrayList<Integer> {

        public ArrayListImpl() {
        }
    }
}
