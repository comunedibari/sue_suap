/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.selenium;

import static it.wego.cross.selenium.WebElMap.clickAnagraficaDettaglio;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
public class AperturaPraticheBTest {

    static final private String TESTFAMILY = "AperturaPratiche";
    static final private String TEST_9 = "SelezionaPraticaDaAprire";
    static final private String TEST_10 = "InserimentoNumeroProtocollo";
    static final private String TEST_11 = "ScaricaRiepilogo";
    static final private String TEST_12 = " ConfrontoDatiAnagraficiRichiedenteApertura";
    static final private String TEST_13 = " ConfrontoDatiAnagraficiRichiedente1";
    static final private String TEST_14 = " ConfrontoDatiAnagraficiRichiedente2";
    static final private String TEST_15 = " ConfrontoDatiAnagraficiRichiedente3";
    static final private String TEST_16 = " ConfrontoDatiAnagraficiRichiedente4";
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
        //   DesiredCapabilities capability = DesiredCapabilities.chrome();
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        driver = new ChromeDriver();  //for local check
        driver.manage().window().setSize(new Dimension(1920, 1080));
//        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        tmess = new TMess();
        //       tmess.SetSeleniumOutStreamLog();
        tmess.NuovaFamigliaTest(TESTFAMILY);
        urlChecker = new UrlStatusChecker(driver);
        fileDownloader = new FileDownloader(driver);
        SeleniumUtilities.Login(driver, tmess);
    }

    private void goTo_elenco_praticheNuove(WebDriver driver) throws Exception {
        SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_PRAT_GESTIONE_BUTTON_CSSSELECTOR), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.linkText("Apertura pratiche"), tmess);
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

    public static void showDettaglioRichiedente(List<List<List<String>>> anagrafiche) {
        for (Integer i = 0; i.intValue() < anagrafiche.size(); i++) {
            List<List<String>> anagrafica = anagrafiche.get(i);
            String ruolo = anagrafica.get(1).get(0);
            if (ruolo.equals("Richiedente")) {
                clickAnagraficaDettaglio(driver);
            }

        }
    }

    public static void showDettaglioBeneficiario(List<List<List<String>>> anagrafiche) {
        for (Integer i = 0; i.intValue() < anagrafiche.size(); i++) {
            List<List<String>> anagrafica = anagrafiche.get(i);
            String ruolo = anagrafica.get(1).get(0);
            if (ruolo.equals("Beneficiario")) {
                clickAnagraficaDettaglio(driver);
            }
        }
    }

    @Ignore
    @Test
    //Verifica che le pratiche vengano ordinate correttamente per data e per oggetto
    public void aperturaPratiche() throws Exception {
        tmess.NuovoTest(TEST_9, TESTFAMILY);
        Boolean success = true;
        goTo_elenco_praticheNuove(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tmess.azione("Prendo una pratica casuale");
        List<List<String>> pratiche = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        Random generator = new Random();
        Integer n = generator.nextInt(pratiche.size() - 1) + 1;
        tmess.azione(pratiche.size() + "  " + n);
        List<String> labelstable = SeleniumUtilities.changeListToString(driver.findElements(By.className("ui-jqgrid-sortable")));
        List<String> valoritable = pratiche.get(n);
        tmess.azione("Label tabella: " + labelstable.toString());
        tmess.azione("I dati di questa pratica sono: " + valoritable.toString());
        tmess.azione("Accedo alla pratica scelta.");
        clickAzione(n, 8);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        WebElement table = SeleniumUtilities.safeFind(driver, By.id("frame0"), tmess);
        List<WebElement> labels = table.findElements(By.tagName("label"));
        List<WebElement> valori = table.findElements(By.tagName("a"));
        tmess.azione("I dati nel dettaglio sono: ");
        for (int a = 0; a < labels.size(); a++) {
            if (a == 7) {
                continue;
            }
            tmess.azione(labels.get(a).getText() + " = " + valori.get(a).getText());
            for (int b = 0; b < labelstable.size(); b++) {
                if (labelstable.get(b).equals(labels.get(a).getText())
                        && !valoritable.get(b).equals(valori.get(a).getText())) {
                    tmess.Errori("Il valore trovato " + valoritable.get(b) + " non e' uguale a " + valori.get(a).getText());
                    success = false;
                }
            }
        }
        if (success) {
            tmess.risultati("I dati confrontati sono esatti.");
        }

        tmess.conclusione(TEST_9, TESTFAMILY, success);
    }

    //Questo test e' da chiedere, serviranno pratiche che non hanno il numero di protocollo inserito
    
    
    @Test
    public void inserimentoNumeroProtocollo() throws Exception {
        Boolean success = true;
        tmess.NuovoTest(TEST_10, TESTFAMILY);
        goTo_elenco_praticheNuove(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tmess.azione("Prendo una pratica casuale");
        List<List<String>> pratiche = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        Random generator = new Random();
        Integer n = generator.nextInt(pratiche.size() + 1) - 1;
        List<String> labelstable = SeleniumUtilities.changeListToString(driver.findElements(By.className("ui-jqgrid-sortable")));
        List<String> valoritable = pratiche.get(n);
        tmess.azione("Label tabella: " + labelstable.toString());
        tmess.azione("I dati di questa pratica sono: " + valoritable.toString());
        tmess.azione("Accedo alla pratica scelta.");
        clickAzione(n, 8);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        String valold = SeleniumUtilities.safeFind(driver, By.id("pratica_protocollo_segnatura"), tmess).getText();

        String protocollo = "gen/2014/111";

        tmess.azione("Il dato di protocollo inserisco e' " + protocollo);
        SeleniumUtilities.safeClick(driver, By.id("pratica_protocollo_segnatura"), tmess);
        SeleniumUtilities.safeFind(driver, By.cssSelector(".editable-input > input:nth-child(1)"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.cssSelector(".editable-input > input:nth-child(1)"), tmess).sendKeys(protocollo);
        SeleniumUtilities.safeClick(driver, By.className("editable-submit"), tmess);
        SeleniumUtilities.safeClick(driver, By.className("secondaryAction"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        clickAzione(n, 8);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        String praticavis = SeleniumUtilities.safeFind(driver, By.id("pratica_protocollo_segnatura"), tmess).getText();
        if (praticavis.equals(protocollo)){
            tmess.risultati("Il dato protocollo " + praticavis + " coincide con quello inserito");
        }else{
            tmess.Errori("Il dato protocollo visualizzato " + praticavis + " e' diverso da quello inserito: " + protocollo);
            success = false;
        }
        tmess.azione("Rinserisco i valori precedenti.");
        SeleniumUtilities.safeClick(driver, By.id("pratica_protocollo_segnatura"), tmess);
        SeleniumUtilities.safeFind(driver, By.cssSelector(".editable-input > input:nth-child(1)"), tmess).clear();
        SeleniumUtilities.safeFind(driver, By.cssSelector(".editable-input > input:nth-child(1)"), tmess).sendKeys(valold);

        tmess.conclusione(TEST_10, TESTFAMILY, success);
    }
//http://ardesco.lazerycode.com/index.php/2012/07/how-to-download-files-with-selenium-and-why-you-shouldnt/
//test ancora da verificare, vedere nel link per verificare il file scaricato.

    @Ignore
    @Test
    public void scaricaRiepilogo() throws Exception {
        SeleniumUtilities.HomePage(driver);
        goTo_elenco_praticheNuove(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        //trovare il modo di controllare se l'id del file scaricato e' giusto
        String idPratica = aperturaPraticaCasuale(getIdPratiche());
        tmess.NuovoTest(TEST_11, TESTFAMILY);
        Boolean success = true;
        String downloadUrl = driver.findElement(By.className("scarica")).getAttribute("href");
        String fileId = downloadUrl.split("=")[1];
        tmess.azione("Si sta per effettuare il download del file con id:" + fileId);
        urlChecker.setURIToCheck(downloadUrl);
        urlChecker.setHTTPRequestMethod(RequestMethod.GET);
        Integer codeStatus = urlChecker.getHTTPStatusCode();
        tmess.risultati("La risposta del server al link di download e': " + codeStatus.toString());
        if (codeStatus.intValue() == 404 || codeStatus.intValue() == 500) {
            tmess.Errori("Il link dei download effettua una richiesta non valida.");
        }
        tmess.azione("Clicco il pulsante di download per verificare che la risposta funzioni correttamente");
        WebElement downloadLink = driver.findElement(By.className("scarica"));
        String downloadedFileAbsoluteLocation = fileDownloader.downloadFile(downloadLink);
        if (new File(downloadedFileAbsoluteLocation).exists()) {
            tmess.Errori("Il file non e' stato scaricato.");
            success = false;
        } else {
            tmess.risultati("Il file e' stato correttamente scaricato e presente.");
        }
        if (fileDownloader.getHTTPStatusOfLastDownloadAttempt() != 200) {
            tmess.Errori("La risposta del server alla richiesta di download non e' stata positiva: " + fileDownloader.getHTTPStatusOfLastDownloadAttempt());
            success = false;
        } else {
            tmess.risultati("La risposta del server e' corretta.");
        }
        tmess.conclusione(TEST_11, TESTFAMILY, success);
    }

    @Ignore
    @Test
    public void ConfrontoDatiAnagraficiRichiedenteApertura() throws Exception {
        Boolean success = true;
        SeleniumUtilities.HomePage(driver);
        goTo_elenco_praticheNuove(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        String idPratica = aperturaPraticaCasuale(getIdPratiche());
        tmess.NuovoTest(TEST_12, TESTFAMILY);
        WebElement tabellaAnagrafiche = SeleniumUtilities.safeFind(driver, By.id("list"), tmess);
        List<WebElement> trAnagrafiche = tabellaAnagrafiche.findElements(By.tagName("td"));
        List<List<List<String>>> anagraficheCollegate = new ArrayList<List<List<String>>>();
        for (WebElement tr : trAnagrafiche) {
            List<List<String>> anagrafica = SeleniumUtilities.readLebelledInputMask(tr);
            anagraficheCollegate.add(anagrafica);
        }
        List<List<String>> pratiche = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        if (trAnagrafiche.size() > 0) {
            List<String> anagraficaRichiedente = anagraficheCollegate.get(0).get(1);
            tmess.azione(anagraficaRichiedente.toString());
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
            WebElMap.clickFirstAnagraficaDettaglio(driver);
            WebElement containerAnagrafiche = SeleniumUtilities.safeFind(driver, By.className(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_CLASS), tmess);
            tmess.azione("Ho trovato il container dei dati in anagrafica. ");
            WebElement firstContainer = containerAnagrafiche.findElement(By.cssSelector(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_PERVENUTI_CSSSELECTOR));
            tmess.azione("Seleziono il container del dettaglio anagrafica. ");
            List<WebElement> containers = firstContainer.findElements(By.className(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_SEZIONEDETTAGLIO_CLASS));
            List<String> dettaglioAnagrafiche = SeleniumUtilities.readDettaglioAnagrafiche(driver,
                    containers.get(0),
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_PERVENUTI_SELECTS_ID);
            tmess.azione("Leggo le label e la lista dei campi compilati. ");
            List<String> labels = SeleniumUtilities.readDettaglioAnagraficheLabels(driver, containers.get(0), WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG, WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_PERVENUTI_SELECTS_ID, tmess);
            tmess.azione("lista: " + dettaglioAnagrafiche.toString());
            tmess.azione("labels: " + labels.toString());
            tmess.azione("Verifico che le informazioni e le label nella tabella relativa ai dati in anagrafica siano corrette:");
            Map anagraficaDettaglio = TestUtilities.getAnagraficaMap(labels, dettaglioAnagrafiche, tmess);
            if (anagraficaDettaglio == null) {
                tmess.Errori("Si e' verificato un errore nella lettura della tabella delle anagrafiche. Le label non sono correttamente visualizzate o i valori non corrispondono. Controllare nella lista delle labels lette.");
                success = false;
            } else {

                List<String> erroriVisualizzazioneAnagrafica = new ArrayList<String>();

                tmess.azione("Il tipo di anagrafica deve essere una persona fisica.");
                String tipoAnagraficaLetta = (String) anagraficaDettaglio.get(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_LABEL_TIPOANAGRAFICA);
                if (tipoAnagraficaLetta == null) {
                    erroriVisualizzazioneAnagrafica.add("Il tipo  di anagrafica non e' correttamente visualizzato o la label non presenta la dicitura corretta.");
                } else {
                    Boolean correttaVisualizzazioneAnagrafica = true;
                    if (!tipoAnagraficaLetta.equals(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_VALUES_TIPOAN_PERSONAFISICA)) {
                        erroriVisualizzazioneAnagrafica.add("Il tipo di anagrafica non e' impostato su" + WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_VALUES_TIPOAN_PERSONAFISICA + "come dovrebbe essere l'anagrafica del richiedente ma su " + tipoAnagraficaLetta);
                    }
                }
                tmess.azione("Verifico che il tipo di ruolo sia quello di richiedente.");
                String ruoloLetto = (String) anagraficaDettaglio.get(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_LABEL_RUOLO);
                if (!WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_VALUES_RUOLO_RICHIEDENTE.equals(ruoloLetto)) {
                    erroriVisualizzazioneAnagrafica.add("Il ruolo specificato nell'anagrafica non e' quello di richiedente.");
                }

                tmess.azione("Nel caso in cui l'anagrafica sia del tipo di una ditta individuale la partita iva deve essere visualizzata come label, altrimenti no.");
                String pivaLetta = (String) anagraficaDettaglio.get(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_LABEL_PIVA);

                if ((tipoAnagrafica.intValue() == 4)) {
                    tmess.azione("L'anagrafica non e' una ditta individuale. Verifico che il campo della partita iva non sia presente.");
                    if (pivaLetta != null) {
                        erroriVisualizzazioneAnagrafica.add("L'anagrafica non e' una ditta individuale ma il campo della partita iva e' presente.");
                    }
                    tmess.azione("Verifico che i  campi di nome, cognome, codice fiscale siano correttamente visualizzati e valorizzati.");
                    String nomeLetto = (String) anagraficaDettaglio.get(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_LABEL_NOME);
                    String cognomeLetto = (String) anagraficaDettaglio.get(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_LABEL_COGNOME);
                    String codiceFiscaleLetto = (String) anagraficaDettaglio.get(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_LABEL_CODICEFISCALE);
                    if (!nomeLetto.equals(nome)) {
                        erroriVisualizzazioneAnagrafica.add("Il nome visualizzato nell'anagrafica dettaglio: " + nomeLetto + " e' diverso da quello visualizzato in precedenza: " + nome);
                    }
                    if (!cognomeLetto.equals(cognome)) {
                        erroriVisualizzazioneAnagrafica.add("Il cognome visualizzato nell'anagrafica dettaglio: " + cognomeLetto + " e' diverso da quello visualizzato in precedenza: " + cognome);
                    }
                    if (!codiceFiscaleLetto.equals(codicefiscale)) {
                        erroriVisualizzazioneAnagrafica.add("Il codice fiscale visualizzato nell'anagrafica dettaglio: " + codiceFiscaleLetto + " e' diverso da quello visualizzato: " + codicefiscale);
                    }
                } else if (tipoAnagrafica.intValue() == 5) {
                    tmess.azione("L'anagrafica e' una ditta individuale. Verifico che il campo della partita iva  sia presente.");
                    if (pivaLetta == null) {
                        erroriVisualizzazioneAnagrafica.add("L'anagrafica e' una ditta individuale ma il campo della partita iva non e' presente.");
                    }
                    tmess.azione("Verifico che i  campi di nome, cognome, codice fiscale, piva siano correttamente visualizzati e valorizzati.");
                    String nomeLetto = (String) anagraficaDettaglio.get(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_LABEL_NOME);
                    String cognomeLetto = (String) anagraficaDettaglio.get(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_LABEL_COGNOME);
                    String codicefiscaleLetto = (String) anagraficaDettaglio.get(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_LABEL_CODICEFISCALE);
                    if (!nome.equals(nomeLetto)) {
                        erroriVisualizzazioneAnagrafica.add("Il nome visualizzato nell'anagrafica dettaglio: " + nomeLetto + " e' diverso da quello visualizzato in precedenza: " + nome);
                    }
                    if (!cognome.equals(cognomeLetto)) {
                        erroriVisualizzazioneAnagrafica.add("Il cognome visualizzato nell'anagrafica dettaglio: " + cognomeLetto + " e' diverso da quello visualizzato in precedenza: " + cognome);
                    }
                    if (!codicefiscale.equals(codicefiscaleLetto)) {
                        erroriVisualizzazioneAnagrafica.add("Il codice fiscale visualizzato nell'anagrafica dettaglio: " + codicefiscaleLetto + " e' diverso da quello visualizzato: " + codicefiscale);
                    }
                    if (!partitaiva.equals(pivaLetta)) {
                        erroriVisualizzazioneAnagrafica.add("La partita iva visualizzata nell'anagrafica dettaglio: " + pivaLetta + " e' diversa da quello visualizzata in precedenza: " + partitaiva);
                    }

                } else if ((tipoAnagrafica.intValue() != 4) && (tipoAnagrafica.intValue() != 5)) {
                    erroriVisualizzazioneAnagrafica.add("Il richiedente deve essere una persona fisica o una ditta individuale.");
                }
                if (erroriVisualizzazioneAnagrafica.size() > 0) {
                    tmess.Errori("Si sono verificati i seguenti errori:");
                    for (String er : erroriVisualizzazioneAnagrafica) {
                        tmess.Errori(er);
                    }
                    success = false;
                } else {
                    tmess.risultati("Sono presenti tutti e soli i campi necessari e valorizzati coerentemente con le informazioni visualizzate in precedenza.");
                }
            }
        } else {
            tmess.Generico("Non sono presenti anagrafiche in questa pratica, non ho potuto eseguire il test.");
        }
        tmess.conclusione(TEST_12, TESTFAMILY, success);
    }

    @Ignore
    @Test
    public void ConfrontoDatiAnagraficaRichiedente1Anagrafica() throws Exception {
        Boolean success = true;
        tmess.NuovoTest(TEST_13, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        goTo_elenco_praticheNuove(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        String idPratica = aperturaPraticaCasuale(getIdPratiche());
        WebElement tabellaAnagrafiche = SeleniumUtilities.safeFind(driver, By.tagName("table"), tmess);
        List<WebElement> trAnagrafiche = tabellaAnagrafiche.findElements(By.tagName("td"));
        List<List<List<String>>> anagraficheCollegate = new ArrayList<List<List<String>>>();
        for (WebElement tr : trAnagrafiche) {
            List<List<String>> anagrafica = SeleniumUtilities.readLebelledInputMask(tr);
            anagraficheCollegate.add(anagrafica);
        }
        if (trAnagrafiche.size() > 0) {
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
            showDettaglioRichiedente(anagraficheCollegate);
            WebElement containerAnagrafiche = SeleniumUtilities.safeFind(driver, By.className(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_CLASS), tmess);
            tmess.azione("Ho trovato il container dei dati in anagrafica. ");
            WebElement firstContainer = containerAnagrafiche.findElement(By.cssSelector(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_INANAGRAFICA_CSSSELECTOR));
            tmess.azione("Leggo le informazioni contenute nel dettaglio anagrafica, nella sezione in anagrafica. ");
            List<WebElement> containers = firstContainer.findElements(By.className(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_SEZIONEDETTAGLIO_CLASS));
            WebElement inAnagraficaDettaglioContainer = containers.get(0);
            List<String> dettaglioAnagraficheInAnag = SeleniumUtilities.readDettaglioAnagrafiche(driver,
                    inAnagraficaDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_INANAGRAFICA_SELECTS_ID);
            tmess.azione("Leggo le label e la lista dei campi compilati. I campi sono i seguenti ");
            List<String> labels = SeleniumUtilities.readDettaglioAnagraficheLabels(driver,
                    inAnagraficaDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_INANAGRAFICA_SELECTS_ID, tmess);

            tmess.printTwoListAsTable(labels, dettaglioAnagraficheInAnag);

            tmess.azione("cancello tutti i valori presenti in anagrafica");

            SeleniumUtilities.cleanVisibileFieldFromContainer(inAnagraficaDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_INANAGRAFICA_SELECTS_ID,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_SELECTS_DEFAULT_OPTIONS, tmess);

            dettaglioAnagraficheInAnag = SeleniumUtilities.readDettaglioAnagrafiche(driver,
                    inAnagraficaDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_INANAGRAFICA_SELECTS_ID);

            tmess.azione("Verifico che i valori siano stati cancellati. ");
            tmess.printTwoListAsTable(labels, dettaglioAnagraficheInAnag);
            tmess.azione("Leggo i valori immessi nell'anagrafica pervenuta. ");
            WebElement secondContainer = containerAnagrafiche.findElement(
                    By.cssSelector(
                            WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_PERVENUTI_CSSSELECTOR));

            tmess.azione("Leggo le informazioni contenute nel dettaglio anagrafica, nella sezione in anagrafica. ");
            List<WebElement> containers1 = secondContainer.findElements(
                    By.className(
                            WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_SEZIONEDETTAGLIO_CLASS));
            WebElement pervenutiDettaglioContainer = containers1.get(0);
            List<String> dettaglioAnagraficheRicevute = SeleniumUtilities.readDettaglioAnagrafiche(
                    driver,
                    pervenutiDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_PERVENUTI_SELECTS_ID);
            tmess.azione("Leggo le label e la lista dei campi compilati (si ipotizza che le labels siano le medesime). I campi sono i seguenti ");
//        List<WebElMap.inputTypes> ty = SeleniumUtilities.findFirstTypeOfEachContainer(pervenutiDettaglioContainer,
//                WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
//                WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_PERVENUTI_SELECTS_ID,
//                WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_TIPOINPUT_CLASSE_AUTOCOMPLETE);
            tmess.printTwoListAsTable(labels, dettaglioAnagraficheRicevute);
//        TMess.azione(ty.toString());
            tmess.azione("Cancello il campo dell'anagrafica ricevuta.");
            SeleniumUtilities.cleanVisibileFieldFromContainer(
                    pervenutiDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_PERVENUTI_SELECTS_ID,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_SELECTS_DEFAULT_OPTIONS, tmess);
            tmess.azione("Compilo il campo dell'anagrafica ricevuta con valori di default.");
            SeleniumUtilities.autocompleteDefValues(
                    pervenutiDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_PERVENUTI_SELECTS_ID,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_TIPOINPUT_CLASSE_AUTOCOMPLETE,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    labels,
                    "default",
                    " defaultDate",
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_SELECTS_DEFAULT_OPTIONS, tmess);
            dettaglioAnagraficheRicevute = SeleniumUtilities.readDettaglioAnagrafiche(
                    driver,
                    pervenutiDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_PERVENUTI_SELECTS_ID);
            tmess.printTwoListAsTable(labels, dettaglioAnagraficheRicevute);
            List<Integer> notToAdd = new ArrayList<Integer>();
            notToAdd.add(0);
            SeleniumUtilities.selectVisibleTagAndPressButton(driver, pervenutiDettaglioContainer, By.id("spostaSingola"), WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG, notToAdd, By.cssSelector("ui-widget-overlay"));
            dettaglioAnagraficheInAnag = SeleniumUtilities.readDettaglioAnagrafiche(
                    driver,
                    inAnagraficaDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_INANAGRAFICA_SELECTS_ID);
            if (!TestUtilities.confrontaAnagrafiche(labels, dettaglioAnagraficheInAnag, dettaglioAnagraficheRicevute, true, tmess)) {
                success = false;
            }
        } else {
            tmess.Generico("Non sono presenti anagrafiche in questa pratica, non ho potuto eseguire il test.");
        }
        tmess.conclusione(TEST_13, TESTFAMILY, success);
    }

    @Ignore
    @Test
    public void ConfrontoDatiAnagraficiRichiedente1PrimoRecapito() throws Exception {
        Boolean success = true;
        tmess.NuovoTest(TEST_14, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        goTo_elenco_praticheNuove(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        String idPratica = aperturaPraticaCasuale(getIdPratiche());
        WebElement tabellaAnagrafiche = SeleniumUtilities.safeFind(driver, By.tagName("table"), tmess);
        List<WebElement> trAnagrafiche = tabellaAnagrafiche.findElements(By.tagName("td"));
        List<List<List<String>>> anagraficheCollegate = new ArrayList<List<List<String>>>();
        for (WebElement tr : trAnagrafiche) {
            List<List<String>> anagrafica = SeleniumUtilities.readLebelledInputMask(tr);
            anagraficheCollegate.add(anagrafica);
        }
        if (trAnagrafiche.size() > 0) {
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
            showDettaglioRichiedente(anagraficheCollegate);
            WebElement containerAnagrafiche = SeleniumUtilities.safeFind(driver, By.className(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_CLASS), tmess);
            tmess.azione("Ho trovato il container dei dati in anagrafica. ");
            WebElement firstContainer = containerAnagrafiche.findElement(By.cssSelector(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_INANAGRAFICA_CSSSELECTOR));
            tmess.azione("Leggo le informazioni contenute nel dettaglio anagrafica, nella sezione relativa al primo recapito. ");
            List<WebElement> containers = firstContainer.findElements(By.className(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_SEZIONEPRIMORECAPITO_CLASS));
            WebElement inAnagraficaDettaglioContainer = containers.get(0);
            List<String> emptyList = new ArrayList<String>();
            List<String> dettaglioAnagraficheInAnag = SeleniumUtilities.readDettaglioAnagrafiche(driver,
                    inAnagraficaDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    emptyList);
            tmess.azione("Leggo le label e la lista dei campi compilati. I campi sono i seguenti ");
            List<String> labels = SeleniumUtilities.readDettaglioAnagraficheLabels(driver,
                    inAnagraficaDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    emptyList, tmess);

            tmess.printTwoListAsTable(labels, dettaglioAnagraficheInAnag);

            tmess.azione("cancello tutti i valori presenti in anagrafica");

            SeleniumUtilities.cleanVisibileFieldFromContainer(inAnagraficaDettaglioContainer,
                    emptyList,
                    emptyList, tmess);

            dettaglioAnagraficheInAnag = SeleniumUtilities.readDettaglioAnagrafiche(driver,
                    inAnagraficaDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    emptyList);

            tmess.azione("Verifico che i valori siano stati cancellati. ");
            tmess.printTwoListAsTable(labels, dettaglioAnagraficheInAnag);
            tmess.azione("Leggo i valori immessi nell'anagrafica pervenuta. ");
            WebElement secondContainer = containerAnagrafiche.findElement(
                    By.cssSelector(
                            WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_PERVENUTI_CSSSELECTOR));

            tmess.azione("Leggo le informazioni contenute nel dettaglio anagrafica, nella sezione in anagrafica. ");
            List<WebElement> containers1 = secondContainer.findElements(
                    By.className(
                            WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_SEZIONEPRIMORECAPITO_CLASS));
            WebElement pervenutiDettaglioContainer = containers1.get(0);
            List<String> dettaglioAnagraficheRicevute = SeleniumUtilities.readDettaglioAnagrafiche(
                    driver,
                    pervenutiDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    emptyList);
            tmess.azione("Leggo le label e la lista dei campi compilati (si ipotizza che le labels siano le medesime). I campi sono i seguenti ");
//        List<WebElMap.inputTypes> ty = SeleniumUtilities.findFirstTypeOfEachContainer(pervenutiDettaglioContainer,
//                WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
//                WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_PERVENUTI_SELECTS_ID,
//                WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_TIPOINPUT_CLASSE_AUTOCOMPLETE);
            tmess.printTwoListAsTable(labels, dettaglioAnagraficheRicevute);
            tmess.azione(dettaglioAnagraficheRicevute.toString());
            tmess.azione("Cancello il campo dell'anagrafica ricevuta.");
            SeleniumUtilities.cleanVisibileFieldFromContainer(
                    pervenutiDettaglioContainer,
                    emptyList,
                    emptyList, tmess);
            tmess.azione("Compilo il campo dell'anagrafica ricevuta con valori di default.");
            SeleniumUtilities.autocompleteDefValues(
                    pervenutiDettaglioContainer,
                    emptyList,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_TIPOINPUT_CLASSE_AUTOCOMPLETE_RECAPITO,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    labels,
                    "default",
                    " defaultDate",
                    emptyList, tmess);
            dettaglioAnagraficheRicevute = SeleniumUtilities.readDettaglioAnagrafiche(
                    driver,
                    pervenutiDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    emptyList);
            tmess.printTwoListAsTable(labels, dettaglioAnagraficheRicevute);
            List<Integer> notToAdd = new ArrayList<Integer>();
            notToAdd.add(0);
            SeleniumUtilities.selectVisibleTagAndPressButton(driver, pervenutiDettaglioContainer, By.id("spostaSingola"), WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG, notToAdd, By.cssSelector("ui-widget-overlay"));
            dettaglioAnagraficheInAnag = SeleniumUtilities.readDettaglioAnagrafiche(
                    driver,
                    inAnagraficaDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    emptyList);
            if (!TestUtilities.confrontaAnagrafiche(labels, dettaglioAnagraficheInAnag, dettaglioAnagraficheRicevute, true, tmess)) {
                success = false;
            }
        } else {
            tmess.Generico("Non sono presenti anagrafiche in questa pratica, non ho potuto eseguire il test.");
        }
        tmess.conclusione(TEST_13, TESTFAMILY, success);
    }

    @Ignore
    @Test
    public void ConfrontoDatiAnagraficaBeneficiario1Anagrafica() throws Exception {
        Boolean success = true;
        tmess.NuovoTest(TEST_13, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        goTo_elenco_praticheNuove(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        String idPratica = aperturaPraticaCasuale(getIdPratiche());
        WebElement tabellaAnagrafiche = SeleniumUtilities.safeFind(driver, By.tagName("table"), tmess);
        List<WebElement> trAnagrafiche = tabellaAnagrafiche.findElements(By.tagName("td"));
        List<List<List<String>>> anagraficheCollegate = new ArrayList<List<List<String>>>();
        for (WebElement tr : trAnagrafiche) {
            List<List<String>> anagrafica = SeleniumUtilities.readLebelledInputMask(tr);
            anagraficheCollegate.add(anagrafica);
        }
        if (trAnagrafiche.size() > 0) {
            List<String> anagraficaBeneficiario = anagraficheCollegate.get(1).get(1);
            String nome = anagraficaBeneficiario.get(1);
            String cognome = anagraficaBeneficiario.get(2);
            String codicefiscale = anagraficaBeneficiario.get(3);
            String partitaiva = "";
            Integer tipoAnagrafica = anagraficaBeneficiario.size();
            if (tipoAnagrafica > 4) {
                partitaiva = anagraficaBeneficiario.get(4);
            }
            tmess.azione("anagrafiche lette:" + anagraficheCollegate.toString());
            tmess.azione("Seleziono l'anagrafica di un beneficiario della pratica che deve essere presente. ");
            showDettaglioBeneficiario(anagraficheCollegate);
            WebElement containerAnagrafiche = SeleniumUtilities.safeFind(driver, By.className(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_CLASS), tmess);
            tmess.azione("Ho trovato il container dei dati in anagrafica. ");
            WebElement firstContainer = containerAnagrafiche.findElement(By.cssSelector(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_INANAGRAFICA_CSSSELECTOR));
            tmess.azione("Leggo le informazioni contenute nel dettaglio anagrafica, nella sezione in anagrafica. ");
            List<WebElement> containers = firstContainer.findElements(By.className(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_SEZIONEDETTAGLIO_CLASS));
            WebElement inAnagraficaDettaglioContainer = containers.get(0);
            List<String> dettaglioAnagraficheInAnag = SeleniumUtilities.readDettaglioAnagrafiche(driver,
                    inAnagraficaDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_INANAGRAFICA_SELECTS_ID);
            tmess.azione("Leggo le label e la lista dei campi compilati. I campi sono i seguenti ");
            List<String> labels = SeleniumUtilities.readDettaglioAnagraficheLabels(driver,
                    inAnagraficaDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_INANAGRAFICA_SELECTS_ID, tmess);

            tmess.printTwoListAsTable(labels, dettaglioAnagraficheInAnag);

            tmess.azione("cancello tutti i valori presenti in anagrafica");

            SeleniumUtilities.cleanVisibileFieldFromContainer(inAnagraficaDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_INANAGRAFICA_SELECTS_ID,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_SELECTS_DEFAULT_OPTIONS, tmess);

            dettaglioAnagraficheInAnag = SeleniumUtilities.readDettaglioAnagrafiche(driver,
                    inAnagraficaDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_INANAGRAFICA_SELECTS_ID);

            tmess.azione("Verifico che i valori siano stati cancellati. ");
            tmess.printTwoListAsTable(labels, dettaglioAnagraficheInAnag);
            tmess.azione("Leggo i valori immessi nell'anagrafica pervenuta. ");
            WebElement secondContainer = containerAnagrafiche.findElement(
                    By.cssSelector(
                            WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_PERVENUTI_CSSSELECTOR));

            tmess.azione("Leggo le informazioni contenute nel dettaglio anagrafica, nella sezione in anagrafica. ");
            List<WebElement> containers1 = secondContainer.findElements(
                    By.className(
                            WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_SEZIONEDETTAGLIO_CLASS));
            WebElement pervenutiDettaglioContainer = containers1.get(0);
            List<String> dettaglioAnagraficheRicevute = SeleniumUtilities.readDettaglioAnagrafiche(
                    driver,
                    pervenutiDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_PERVENUTI_SELECTS_ID);
            tmess.azione("Leggo le label e la lista dei campi compilati (si ipotizza che le labels siano le medesime). I campi sono i seguenti ");
//        List<WebElMap.inputTypes> ty = SeleniumUtilities.findFirstTypeOfEachContainer(pervenutiDettaglioContainer,
//                WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
//                WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_PERVENUTI_SELECTS_ID,
//                WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_TIPOINPUT_CLASSE_AUTOCOMPLETE);
            tmess.printTwoListAsTable(labels, dettaglioAnagraficheRicevute);
//        TMess.azione(ty.toString());
            tmess.azione("Cancello il campo dell'anagrafica ricevuta.");
            SeleniumUtilities.cleanVisibileFieldFromContainer(
                    pervenutiDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_PERVENUTI_SELECTS_ID,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_SELECTS_DEFAULT_OPTIONS, tmess);
            tmess.azione("Compilo il campo dell'anagrafica ricevuta con valori di default.");
            SeleniumUtilities.autocompleteDefValues(
                    pervenutiDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_PERVENUTI_SELECTS_ID,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_TIPOINPUT_CLASSE_AUTOCOMPLETE,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    labels,
                    "default",
                    " defaultDate",
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_SELECTS_DEFAULT_OPTIONS, tmess);
            dettaglioAnagraficheRicevute = SeleniumUtilities.readDettaglioAnagrafiche(
                    driver,
                    pervenutiDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_PERVENUTI_SELECTS_ID);
            tmess.printTwoListAsTable(labels, dettaglioAnagraficheRicevute);
            List<Integer> notToAdd = new ArrayList<Integer>();
            notToAdd.add(0);
            SeleniumUtilities.selectVisibleTagAndPressButton(driver, pervenutiDettaglioContainer, By.id("spostaSingola"), WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG, notToAdd, By.cssSelector("ui-widget-overlay"));
            dettaglioAnagraficheInAnag = SeleniumUtilities.readDettaglioAnagrafiche(
                    driver,
                    inAnagraficaDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_INANAGRAFICA_SELECTS_ID);
            if (!TestUtilities.confrontaAnagrafiche(labels, dettaglioAnagraficheInAnag, dettaglioAnagraficheRicevute, true, tmess)) {
                success = false;
            }
        } else {
            tmess.Generico("Non sono presenti anagrafiche in questa pratica, non ho potuto eseguire il test.");
        }
        tmess.conclusione(TEST_13, TESTFAMILY, success);
    }

    @Ignore
    @Test
    public void ConfrontoDatiAnagraficiBeneficiario1PrimoRecapito() throws Exception {
        Boolean success = true;
        tmess.NuovoTest(TEST_14, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        goTo_elenco_praticheNuove(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        String idPratica = aperturaPraticaCasuale(getIdPratiche());
        WebElement tabellaAnagrafiche = SeleniumUtilities.safeFind(driver, By.tagName("table"), tmess);
        List<WebElement> trAnagrafiche = tabellaAnagrafiche.findElements(By.tagName("td"));
        List<List<List<String>>> anagraficheCollegate = new ArrayList<List<List<String>>>();
        for (WebElement tr : trAnagrafiche) {
            List<List<String>> anagrafica = SeleniumUtilities.readLebelledInputMask(tr);
            anagraficheCollegate.add(anagrafica);
        }
        if (trAnagrafiche.size() > 0) {
            List<String> anagraficaBeneficiario = anagraficheCollegate.get(1).get(1);
            String nome = anagraficaBeneficiario.get(1);
            String cognome = anagraficaBeneficiario.get(2);
            String codicefiscale = anagraficaBeneficiario.get(3);
            String partitaiva = "";
            Integer tipoAnagrafica = anagraficaBeneficiario.size();
            if (tipoAnagrafica > 4) {
                partitaiva = anagraficaBeneficiario.get(4);
            }
            tmess.azione("anagrafiche lette:" + anagraficheCollegate.toString());
            tmess.azione("Seleziono l'anagrafica di un beneficiario della pratica che deve essere presente. ");
            showDettaglioBeneficiario(anagraficheCollegate);
            WebElement containerAnagrafiche = SeleniumUtilities.safeFind(driver, By.className(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_CLASS), tmess);
            tmess.azione("Ho trovato il container dei dati in anagrafica. ");
            WebElement firstContainer = containerAnagrafiche.findElement(By.cssSelector(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_INANAGRAFICA_CSSSELECTOR));
            tmess.azione("Leggo le informazioni contenute nel dettaglio anagrafica, nella sezione relativa al primo recapito. ");
            List<WebElement> containers = firstContainer.findElements(By.className(WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_SEZIONEPRIMORECAPITO_CLASS));
            WebElement inAnagraficaDettaglioContainer = containers.get(0);
            List<String> emptyList = new ArrayList<String>();
            List<String> dettaglioAnagraficheInAnag = SeleniumUtilities.readDettaglioAnagrafiche(driver,
                    inAnagraficaDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    emptyList);
            tmess.azione("Leggo le label e la lista dei campi compilati. I campi sono i seguenti ");
            List<String> labels = SeleniumUtilities.readDettaglioAnagraficheLabels(driver,
                    inAnagraficaDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    emptyList, tmess);

            tmess.printTwoListAsTable(labels, dettaglioAnagraficheInAnag);

            tmess.azione("cancello tutti i valori presenti in anagrafica");

            SeleniumUtilities.cleanVisibileFieldFromContainer(inAnagraficaDettaglioContainer,
                    emptyList,
                    emptyList, tmess);

            dettaglioAnagraficheInAnag = SeleniumUtilities.readDettaglioAnagrafiche(driver,
                    inAnagraficaDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    emptyList);

            tmess.azione("Verifico che i valori siano stati cancellati. ");
            tmess.printTwoListAsTable(labels, dettaglioAnagraficheInAnag);
            tmess.azione("Leggo i valori immessi nell'anagrafica pervenuta. ");
            WebElement secondContainer = containerAnagrafiche.findElement(
                    By.cssSelector(
                            WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_PERVENUTI_CSSSELECTOR));

            tmess.azione("Leggo le informazioni contenute nel dettaglio anagrafica, nella sezione in anagrafica. ");
            List<WebElement> containers1 = secondContainer.findElements(
                    By.className(
                            WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_CONTAINER_SEZIONEPRIMORECAPITO_CLASS));
            WebElement pervenutiDettaglioContainer = containers1.get(0);
            List<String> dettaglioAnagraficheRicevute = SeleniumUtilities.readDettaglioAnagrafiche(
                    driver,
                    pervenutiDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    emptyList);
            tmess.azione("Leggo le label e la lista dei campi compilati (si ipotizza che le labels siano le medesime). I campi sono i seguenti ");
//        List<WebElMap.inputTypes> ty = SeleniumUtilities.findFirstTypeOfEachContainer(pervenutiDettaglioContainer,
//                WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
//                WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_PERVENUTI_SELECTS_ID,
//                WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_TIPOINPUT_CLASSE_AUTOCOMPLETE);
            tmess.printTwoListAsTable(labels, dettaglioAnagraficheRicevute);
            tmess.azione(dettaglioAnagraficheRicevute.toString());
            tmess.azione("Cancello il campo dell'anagrafica ricevuta.");
            SeleniumUtilities.cleanVisibileFieldFromContainer(
                    pervenutiDettaglioContainer,
                    emptyList,
                    emptyList, tmess);
            tmess.azione("Compilo il campo dell'anagrafica ricevuta con valori di default.");
            SeleniumUtilities.autocompleteDefValues(
                    pervenutiDettaglioContainer,
                    emptyList,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_ANAGRAFICHE_TIPOINPUT_CLASSE_AUTOCOMPLETE_RECAPITO,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    labels,
                    "default",
                    " defaultDate",
                    emptyList, tmess);
            dettaglioAnagraficheRicevute = SeleniumUtilities.readDettaglioAnagrafiche(
                    driver,
                    pervenutiDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    emptyList);
            tmess.printTwoListAsTable(labels, dettaglioAnagraficheRicevute);
            List<Integer> notToAdd = new ArrayList<Integer>();
            notToAdd.add(0);
            SeleniumUtilities.selectVisibleTagAndPressButton(driver, pervenutiDettaglioContainer, By.id("spostaSingola"), WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG, notToAdd, By.cssSelector("ui-widget-overlay"));
            dettaglioAnagraficheInAnag = SeleniumUtilities.readDettaglioAnagrafiche(
                    driver,
                    inAnagraficaDettaglioContainer,
                    WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_FIELD_CONTAINER_TAG,
                    emptyList);
            if (!TestUtilities.confrontaAnagrafiche(labels, dettaglioAnagraficheInAnag, dettaglioAnagraficheRicevute, true, tmess)) {
                success = false;
            }
        } else {
            tmess.Generico("Non sono presenti anagrafiche in questa pratica, non ho potuto eseguire il test.");
        }
        tmess.conclusione(TEST_13, TESTFAMILY, success);
    }

    @Ignore
    @Test
    public void ConfrontoDatiAnagraficiRichiedente2() throws Exception {
        Boolean success = true;
        tmess.NuovoTest(TEST_14, TESTFAMILY);
        tmess.conclusione(TEST_14, TESTFAMILY, success);

    }

    @Ignore
    @Test
    public void ConfrontoDatiAnagraficiRichiedente3() throws Exception {
        Boolean success = true;
        tmess.NuovoTest(TEST_9, TESTFAMILY);
        tmess.conclusione(TEST_9, TESTFAMILY, success);

    }

    @Ignore
    @Test
    public void ConfrontoDatiAnagraficiRichiedente4() throws Exception {
        Boolean success = true;
        tmess.NuovoTest(TEST_9, TESTFAMILY);
        tmess.conclusione(TEST_9, TESTFAMILY, success);

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

    private void clickAzione(Integer npratica, Integer column) throws Exception {
        List<WebElement> pulsantiAzione = SeleniumUtilities.findWebElementInCol(driver, "list", column, 1, -1, By.tagName("img"), tmess);
        pulsantiAzione.get(npratica).click();
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
