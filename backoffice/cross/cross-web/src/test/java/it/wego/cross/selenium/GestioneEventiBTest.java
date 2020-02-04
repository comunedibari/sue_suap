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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class GestioneEventiBTest {

    static final private String TESTFAMILY = "GestioneEventi";
    static final private String TEST_15 = "Visualizzazione_dettaglio_dati_anagrafici"; //3.22
    static final private String TEST_16 = "Collega_nuova_Angrafica";
    static final private String TEST_17 = "Controllo_Tasti_Dettaglio_Pratiche_2";
    static final private String TEST_18 = "Visualizza_Scadenze";
    static final private String TEST_19 = "Inserisci_note";
    static final private String TEST_20 = "Confronto_Eveneti";
    static final private String TEST_21 = "Aggiungi_Eveneti";
    static final private String TEST_22 = "Controllo_endoprocedimenti";
    static final private String TEST_23 = "Aggiunta_endoprocedimento";
    static final private String PAGENUMBERID = "sp_1_pager";
    static final private String GOTOFIRSTPAGEBOTTON = "ui-icon.ui-icon-seek-first";
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

    private void GoTo_gestione_eventi(WebDriver driver) {
        SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_PRAT_GESTIONE_BUTTON_CSSSELECTOR), tmess);
        SeleniumUtilities.safeClick(driver, By.xpath(WebElMap.AP_EVEN_APERTURA_EVENTI_BUTTON_XPATH), tmess);
    }

    
    @Test
    public void Visualizzazione_dettaglio_dati_anagrafici() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_15, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_eventi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

//      Premo il tasto Gestisci Pratica.
        List<WebElement> table = driver.findElements(By.tagName("table"));
        List<WebElement> rows = table.get(1).findElements(By.tagName("tr"));
        Random generator = new Random();
        Integer n = generator.nextInt(rows.size() - 1) + 1;
        List<WebElement> column = rows.get(n).findElements(By.tagName("td"));
        WebElement cell = column.get(10).findElement(By.tagName("button"));
        cell.click();

//      analizzo le tabelle in Gestione Pratiche.
        WebElement tabellaAnagrafiche = SeleniumUtilities.safeFind(driver, By.tagName("table"), tmess);
        List<WebElement> trAnagrafiche = tabellaAnagrafiche.findElements(By.tagName("td"));
        tmess.azione("Eseguo la ricerca nel dettaglio della Gestione Pratiche");
        List<List<List<String>>> anagraficheCollegate = new ArrayList<List<List<String>>>();
        for (WebElement tr : trAnagrafiche) {
            List<List<String>> anagrafica = SeleniumUtilities.readLebelledInputMask(tr);
            anagraficheCollegate.add(anagrafica);
        }

        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        int i = 0;

        for (List<List<String>> labelList : anagraficheCollegate) {

            if (!labelList.get(0).get(2).equals("Partita IVA")
                    && success) {

                //      Recupero i dati singoli.        
                String nomeGp = labelList.get(1).get(1);
                tmess.azione("La ricerca nel campo nome ha dato come risultato: " + nomeGp);

                String cognomeGp = labelList.get(1).get(2);
                tmess.azione("La ricerca nel campo Cognome ha dato come risultato: " + cognomeGp);

                String codiceFiscaleGp = labelList.get(1).get(3);
                tmess.azione("La ricerca nel campo Codice Fiscale ha dato come risultato: " + codiceFiscaleGp);

//      Apro il PopUp di dettaglio Dati.
                List<WebElement> tabella = driver.findElements(By.tagName("table"));
                List<WebElement> tabellaTd = tabella.get(0).findElements(By.tagName("td"));
                WebElement tastoDiv = tabellaTd.get(i).findElement(By.className("cerca_lente_rosso"));
                tastoDiv.click();
                SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

//      Leggo il primo elemento dettaglio.
                List<WebElement> popUp = driver.findElements(By.className("fieldsetComunicazione"));
                List<List<String>> tablepopUp = new ArrayList<List<String>>();
                tablepopUp.add(SeleniumUtilities.changeListToString(popUp.get(0).findElements(By.className("detailKey"))));
                tablepopUp.add(SeleniumUtilities.changeListToString(popUp.get(0).findElements(By.className("detailValue"))));
                tablepopUp.add(SeleniumUtilities.changeListToString(popUp.get(1).findElements(By.className("detailKey"))));
                tablepopUp.add(SeleniumUtilities.changeListToString(popUp.get(1).findElements(By.className("detailValue"))));

                String nomeDetail = tablepopUp.get(1).get(0);
                tmess.azione("La ricerca del dettaglio nel campo nome ha dato come risultato: " + nomeDetail);

                String cognomeDetail = tablepopUp.get(1).get(1);
                tmess.azione("La ricerca del dettaglio nel campo Cognome ha dato come risultato: " + cognomeDetail);

                String codiceFiscaleDetail = tablepopUp.get(1).get(2);
                tmess.azione("La ricerca del dettaglio nel campo Codice Fiscale ha dato come risultato: " + codiceFiscaleDetail);

                SeleniumUtilities.safeClick(driver, By.linkText("close"), tmess);

                if (driver.findElement(By.className("ui-dialog-title")).isDisplayed()) {
                    success = false;
                    tmess.Errori("La finestra non si e' chiusa!");
                }

                if (nomeDetail.equals(nomeGp + " ")
                        && cognomeDetail.equals(cognomeGp + " ")
                        && codiceFiscaleDetail.equals(codiceFiscaleGp + " ")) {
                    tmess.risultati("I dati delle Anagrafiche coincidono con quelli del Dettaglio Anagrafiche.");
                } else {
                    tmess.Errori("Uno o piu' dati delle Anagrafiche non coincidono con quelli del Dettaglio Anagrafiche.");
                }

            }
            i++;
        }

        tmess.conclusione(TEST_15, TESTFAMILY, success);
    }

    
    @Test
    public void Controllo_Tasti_Dettaglio_Pratiche() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_16, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_eventi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

//      Premo il tasto Gestisci Pratica.
        List<WebElement> table = driver.findElements(By.tagName("table"));
        List<WebElement> rows = table.get(1).findElements(By.tagName("tr"));
        Random generator = new Random();
        Integer n = generator.nextInt(rows.size() - 1) + 1;
        tmess.azione("Apro la pratica numero " + n);
        List<WebElement> columns = rows.get(n).findElements(By.tagName("td"));
        WebElement cell = columns.get(10).findElement(By.tagName("button"));
        cell.click();
        int t = readTableElementsInSelect().size();
        tmess.azione("Ci sono " + t + " anagrafiche nella pratica");
//      analizzo le tabelle in Gestione Pratiche.
        String codF = "";
        String ruolo = "";
        for (List<List<String>> label : readTableElementsInSelect()) {
            tmess.azione("La ricerca nel campo ruolo ruolo ha dato come risultato: " + label.get(1).get(0));
            ruolo = label.get(1).get(0);
            if (label.get(0).get(2).equals("Cognome")) {
                tmess.azione("La ricerca nel campo Codice Fiscale ha dato come risultato: " + label.get(1).get(3));
                codF = label.get(1).get(3);
            } else {
                tmess.azione("La ricerca nel campo Partita IVA ha dato come risultato: " + label.get(1).get(2));
                codF = label.get(1).get(2);
            }
        }
        tmess.azione("Prendo questo codice fiscale/partita IVA: " + codF + " e questo ruolo: " + ruolo);

//      apertura pannello aggiungi Anagrafica.
        WebElement collegaNuovaAnagrafica = driver.findElement(By.linkText("Collega nuova anagrafica"));
        collegaNuovaAnagrafica.click();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        int i = 0;
        String ruoloAnagrafica = "";
        String codFscelto = "";
        WebElement tableSelect = driver.findElement(By.id("list"));
        List<WebElement> rowsSelect = tableSelect.findElements(By.tagName("tr"));
        List<WebElement> columnsSelect = rowsSelect.get(i).findElements(By.tagName("td"));
        WebElement codiceFiscaleSA = columnsSelect.get(4);
        new Select(SeleniumUtilities.safeFind(driver, By.className("ui-pg-selbox"), tmess)).selectByVisibleText("10");
        tmess.azione("Cerco l' anagrafica con lo stesso codice fiscale");
        for (int a = 1; a < rowsSelect.size(); a++) {
            tableSelect = driver.findElement(By.id("list"));
            rowsSelect = tableSelect.findElements(By.tagName("tr"));
            columnsSelect = rowsSelect.get(a).findElements(By.tagName("td"));
            codiceFiscaleSA = columnsSelect.get(4);
            tmess.azione(codiceFiscaleSA.getText());
            if (codiceFiscaleSA.getText().equals(codF)) {
                a = rowsSelect.size();
            } else if (a == rowsSelect.size() - 1
                    && driver.findElement(By.cssSelector("input.ui-pg-input")).getAttribute("value")
                    .equals(driver.findElement(By.id("sp_1_pager")).getText())) {
                tmess.Errori("Non è stata trovata una anagrafica con lo stesso codice fiscale nella lista delle anagrafiche!");
                success = false;
                tmess.conclusione(TEST_16, TESTFAMILY, success);
                return;
            } else if (a == rowsSelect.size() - 1) {
                SeleniumUtilities.safeClick(driver, By.cssSelector("SPAN.ui-icon.ui-icon-seek-next"), tmess);
                SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
                a = 0;
            }
        }
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        WebElement cellSelect = columnsSelect.get(5).findElement(By.tagName("button"));
        //      Premo il tasto.       
        cellSelect.click();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        //      Seleziono il Ruolo.        
        new Select(SeleniumUtilities.safeFind(driver, By.id("ruoli"), tmess)).selectByVisibleText(ruolo);

        //Premo Salva
        SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        i = 0;
        //       while (!driver.findElements(By.id("errorMsg")).isEmpty()) {
        for (WebElement w : rowsSelect) {
            i++;
            tmess.Generico("Questa anagraficha esiste gia'! Quindi cerco un'altra.");

//      Ritorno alla pagina di selezione e scielgo un'altra anagrafica
            SeleniumUtilities.safeClick(driver, By.linkText("← Indietro"), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

            tableSelect = driver.findElement(By.id("list"));
            rowsSelect = tableSelect.findElements(By.tagName("tr"));

//      Se tutte le anagrafiche della prima pagina sono gia state aggiunte, apriamo la seconda pagina
            if (i == rowsSelect.size()) {
                if (driver.findElement(By.cssSelector("input.ui-pg-input")).getAttribute("value")
                        .equals(driver.findElement(By.id("sp_1_pager")).getText())) {
                    tmess.Errori("Non è stata trovata un'anagrafica da poter inserire!");
                    success = false;
                    tmess.conclusione(TEST_16, TESTFAMILY, success);
                    return;
                }
                WebElement next = driver.findElement(By.id("next_pager"));
                next.click();
                SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
                i = 1;
            }

            columnsSelect = rowsSelect.get(i).findElements(By.tagName("td"));
//      Trovo il Codice Fiscale della selezione Anagrafiche. (e lo stampo).
            codiceFiscaleSA = columnsSelect.get(4);
            codFscelto = codiceFiscaleSA.getText();
            tmess.azione("Inserisco questo codice fiscale/partita IVA: " + codFscelto);
            ruoloAnagrafica = columnsSelect.get(1).getText();
//      Premo il tasto.     
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            cellSelect = columnsSelect.get(5).findElement(By.tagName("button"));
            cellSelect.click();
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            //Premo Salva
            SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

            if (driver.findElements(By.id("errorMsg")).isEmpty()) {
                break;
            }
        }
        if (i == 0) {
            tmess.warning("e' stata aggiunta una anagrafica che esiste gia'!");
        }

        i = -1;
        int anagrafica = 0;
        List<List<List<String>>> tableTrov = readTableElementsInSelect();
        if (tableTrov.size() == t + 1) {
            tmess.azione("e' stata aggiunta una anagrafica di tipo " + ruoloAnagrafica);
            success = false;
            for (List<List<String>> a : tableTrov) {
                i++;
                if (a.get(1).get(0).equals("Beneficiario")) {
                    if (ruoloAnagrafica.equals("Persona Fisica")
                            && a.get(0).size() == 4
                            && a.get(1).get(3).equals(codFscelto)) {
                        tmess.Generico("La angarafica Persona Fisica aggiunta e' giusta.");
                        anagrafica = i;
                        success = true;
                    }
                    if (ruoloAnagrafica.equals("Ditta Individuale")
                            && a.get(0).size() == 7
                            && a.get(1).get(3).equals(codFscelto)) {
                        tmess.Generico("La angarafica Ditta Individuale aggiunta e' giusta.");
                        anagrafica = i;
                        success = true;
                    }
                    if (ruoloAnagrafica.equals("Persona Giuridica")
                            && a.get(0).size() == 3
                            && a.get(1).get(2).equals(codFscelto)) {
                        tmess.Generico("La angarafica Persona Giusidica aggiunta e' giusta.");
                        anagrafica = i;
                        success = true;
                    }
                }
            }
            if (!success) {
                tmess.Errori("e' stata aggiunta una anagrafica sbagliata!");
            }
            tmess.azione("Cancello la anagrafica aggiunta");
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            WebElement controlloTabella = SeleniumUtilities.safeFind(driver, By.tagName("table"), tmess);
            List<WebElement> trScollega = controlloTabella.findElements(By.tagName("td"));
            List<WebElement> circleClass = trScollega.get(anagrafica).findElements(By.className("circle"));
            WebElement ctrlHolder = circleClass.get(0).findElement(By.className("showdetail"));
            ctrlHolder.click();

        } else {
            tmess.Errori("non e' stata aggiunta nessuna anagrafica o sono state aggiunte piu di una!");
            success = false;
        }

//     Leggo le anagrafiche presenti e controllo se è stata eliminata.
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        for (List<List<String>> label : readTableElementsInSelect()) {
            if (label.get(0).get(2).equals("Cognome")
                    && label.get(1).get(3).equals(codFscelto)) {
                tmess.Errori("La anagrafica non è stata cancellata!");
                success = false;
                tmess.conclusione(TEST_16, TESTFAMILY, success);
                return;
            } else if (!label.get(0).get(2).equals("Cognome")
                    && label.get(1).get(2).equals(codFscelto)) {
                tmess.Errori("La anagrafica non è stata cancellata!");
                success = false;
                tmess.conclusione(TEST_16, TESTFAMILY, success);
                return;
            }
        }
        tmess.risultati("La anagrafica e' stata cancellata");

        tmess.conclusione(TEST_16, TESTFAMILY, success);
    }

    
    @Test
    public void Controllo_Tasti_Dettaglio_Pratiche_2() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_17, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_eventi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        //      Premo il tasto Gestisci Pratica.
        List<WebElement> table = driver.findElements(By.tagName("table"));
        List<WebElement> rows = table.get(1).findElements(By.tagName("tr"));
        Random generator = new Random();
        Integer n = generator.nextInt(rows.size() - 1) + 1;
        tmess.azione("Apro la pratica numero " + n);
        List<WebElement> columns = rows.get(n).findElements(By.tagName("td"));
        WebElement cell = columns.get(10).findElement(By.tagName("button"));
        cell.click();
        // Lista di riferimenti delle anagrafiche con ruolo beneficiario
        String codF = "";
        Integer t = 0;
        List<Integer> rifA = new ArrayList<Integer>();
        for (List<List<String>> label : readTableElementsInSelect()) {
            if (label.get(1).get(0).equals("Beneficiario")) {
                rifA.add(t);
            }
            t++;
        }
        tmess.azione("Ci sono " + t + " anagrafiche nella pratica di cui " + rifA + " beneficiari.");

        if (rifA.size() < 1) {
            tmess.Errori("Non sono presenti Beneficiari");
            success = false;
            tmess.conclusione(TEST_17, TESTFAMILY, success);
            return;
        }

        if (rifA.size() == 1) {
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            WebElement controlloTabella = SeleniumUtilities.safeFind(driver, By.tagName("table"), tmess);
            List<WebElement> trScollega = controlloTabella.findElements(By.tagName("td"));
            if (trScollega.get(rifA.get(0)).getText().indexOf("Scollega") > 0) {
                tmess.Errori("E' presente un solo Beneficiario e si puo' scollegare!");
                success = false;
                tmess.conclusione(TEST_17, TESTFAMILY, success);
                return;
            }
            tmess.Generico("E' presente un solo Beneficiario e non si puo' scollegare!");
            tmess.azione("Collego una nuova anagrafica Beneficiario");
            WebElement collegaNuovaAnagrafica = driver.findElement(By.linkText("Collega nuova anagrafica"));
            collegaNuovaAnagrafica.click();
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            //Aggiungo una anagrafica non ancora presente
            WebElement tableSelect = driver.findElement(By.id("list"));
            List<WebElement> rowsSelect = tableSelect.findElements(By.tagName("tr"));
            for (int a = 1; a < rowsSelect.size(); a++) {
                tableSelect = driver.findElement(By.id("list"));
                rowsSelect = tableSelect.findElements(By.tagName("tr"));
                List<WebElement> columnsSelect = rowsSelect.get(a).findElements(By.tagName("td"));
                WebElement cellSelect = columnsSelect.get(5).findElement(By.tagName("button"));
                cellSelect.click();
                SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
                codF = driver.findElement(By.id("codiceFiscale")).getText();
                //Premo Salva
                SeleniumUtilities.safeClick(driver, By.tagName("button"), tmess);
                SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
                if (driver.findElements(By.id("errorMsg")).isEmpty()) {
                    break;
                }
                SeleniumUtilities.safeClick(driver, By.linkText("← Indietro"), tmess);
                SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            }
            t = 0;
            rifA = new ArrayList<Integer>();
            for (List<List<String>> label : readTableElementsInSelect()) {
                if (label.get(1).get(0).equals("Beneficiario")) {
                    rifA.add(t);
                }
                t++;
            }
            tmess.azione("Ci sono " + t + " anagrafiche nella pratica di cui " + rifA + " beneficiari.");
        }
        int page = 0;
        for (Integer rif : rifA) {
            for (Integer i = 2; i < rif; i++) {
                if (page < rif - 2) {
                    page++;
                    SeleniumUtilities.safeClick(driver, By.className("box_left"), tmess);
                    SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
                }
            }
            WebElement controlloTabella = SeleniumUtilities.safeFind(driver, By.tagName("table"), tmess);
            List<WebElement> trScollega = controlloTabella.findElements(By.tagName("td"));
            if (trScollega.get(rif).getText().indexOf("Scollega") < 0) {
                tmess.Errori("Sono presenti piu' Beneficiari e uno o piu' non si possono scollegare!");
                success = false;
                tmess.conclusione(TEST_17, TESTFAMILY, success);
                return;
            }
        }
        for (int i = 0; i < page; i++) {
            SeleniumUtilities.safeClick(driver, By.className("box_right"), tmess);
            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        }
        tmess.Generico("Sono presenti piu' Beneficiari e si possono scollegare!");
        if (!codF.equals("")) {
            tmess.azione("Scollego la anagrafica aggiunta.");
            int c = 0;
            for (List<List<String>> label : readTableElementsInSelect()) {
                if (label.get(0).get(2).equals("Cognome")) {
                    if (label.get(1).get(3).equals(codF)) {
                        WebElement controlloTabella = SeleniumUtilities.safeFind(driver, By.tagName("table"), tmess);
                        List<WebElement> trScollega = controlloTabella.findElements(By.tagName("td"));
                        WebElement buttonScollega = trScollega.get(c).findElement(By.tagName("a"));
                        buttonScollega.click();
                    }
                } else {
                    if (label.get(1).get(2).equals(codF)) {
                        WebElement controlloTabella = SeleniumUtilities.safeFind(driver, By.tagName("table"), tmess);
                        List<WebElement> trScollega = controlloTabella.findElements(By.tagName("td"));
                        WebElement buttonScollega = trScollega.get(c).findElement(By.tagName("a"));
                        buttonScollega.click();
                    }
                }
                c++;
            }
        } else {
            tmess.azione("Scollego le angrafiche Beneficiario tranne una.");
            int elim = rifA.size() - 1;
            List<String> codFEl = new ArrayList<String>();
            for (int b = 0; b < elim; b++) {
                List<List<List<String>>> tableRis = readTableElementsInSelect();
                if (tableRis.get(rifA.get(0)).get(0).get(2).equals("Cognome")) {
                    codFEl.add(tableRis.get(rifA.get(0)).get(1).get(3));
                } else {
                    codFEl.add(tableRis.get(rifA.get(0)).get(1).get(2));
                }
                WebElement controlloTabella = SeleniumUtilities.safeFind(driver, By.tagName("table"), tmess);
                List<WebElement> trScollega = controlloTabella.findElements(By.tagName("td"));
                WebElement buttonScollega = trScollega.get(rifA.get(0)).findElement(By.tagName("a"));
                buttonScollega.click();
                SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
                t = 0;
                rifA = new ArrayList<Integer>();
                for (List<List<String>> label : readTableElementsInSelect()) {
                    if (label.get(1).get(0).equals("Beneficiario")) {
                        rifA.add(t);
                    }
                    t++;
                }
                tmess.azione(rifA.toString());
            }
            WebElement controlloTabella = SeleniumUtilities.safeFind(driver, By.tagName("table"), tmess);
            List<WebElement> trScollega = controlloTabella.findElements(By.tagName("td"));
            if (trScollega.get(rifA.get(0)).getText().indexOf("Scollega") < 0) {
                tmess.Generico("La anagrafica Beneficiario rimasta non si puo' scollegare!");
            } else {
                tmess.Errori("La angrafica Beneficiario rimasta si puo' scollegare!");
                success = false;
                tmess.conclusione(TEST_17, TESTFAMILY, success);
                return;
            }
            tmess.azione("Ricollego le anagrafiche scollegate");
            int pageselect = 0;
            for (String cod : codFEl) {
                boolean trovata = false;
                int cont = 0;
                WebElement collegaNuovaAnagrafica = driver.findElement(By.linkText("Collega nuova anagrafica"));
                collegaNuovaAnagrafica.click();
                SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
                if (pageselect != 0) {
                    SeleniumUtilities.safeClick(driver, By.id("first_pager"), tmess);
                    SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
                    pageselect = 0;
                }
                while (!trovata) {
                    cont++;
                    WebElement tableSelect = driver.findElement(By.id("list"));
                    List<WebElement> rowsSelect = tableSelect.findElements(By.tagName("tr"));
                    List<WebElement> columnsSelect = rowsSelect.get(cont).findElements(By.tagName("td"));
                    if (columnsSelect.get(4).getText().equals(cod)) {
                        WebElement cellSelect = columnsSelect.get(5).findElement(By.tagName("button"));
                        cellSelect.click();
                        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
                        trovata = true;
                    } else if (cont == 10) {
                        SeleniumUtilities.safeClick(driver, By.id("next_pager"), tmess);
                        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
                        pageselect++;
                        cont = 0;
                    }
                }
                SeleniumUtilities.safeClick(driver, By.tagName("button"), tmess);
                SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
            }
        }

        tmess.conclusione(TEST_17, TESTFAMILY, success);
    }

    
    @Test
    public void Visualizza_Scadenze() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_18, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_eventi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        //      Premo il tasto Gestisci Pratica.
        List<WebElement> table = driver.findElements(By.tagName("table"));
        List<WebElement> rows = table.get(1).findElements(By.tagName("tr"));
        Random generator = new Random();
        Integer n = generator.nextInt(rows.size() - 1) + 1;
        tmess.azione("Apro la pratica numero " + n);
        List<WebElement> columns = rows.get(n).findElements(By.tagName("td"));
        WebElement cell = columns.get(10).findElement(By.tagName("button"));
        cell.click();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        //      Premo il tasto Scadenze
        SeleniumUtilities.safeClick(driver, By.linkText("Scadenze"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        //      Leggo la tabella
        WebElement tabellaScadenze = SeleniumUtilities.safeFind(driver, By.className("timeline_items"), tmess);
        List<WebElement> scadenze = tabellaScadenze.findElements(By.className("item"));
        List<List<List<String>>> scadenzeAnagrafiche = new ArrayList<List<List<String>>>();
        for (WebElement s : scadenze) {
            List<List<String>> scadenza = SeleniumUtilities.readLabelScadEven(s);
            scadenzeAnagrafiche.add(scadenza);
        }
        tmess.Generico("Ci sono " + scadenzeAnagrafiche.size() + " scadenze, di cui:");
        for (List<List<String>> s : scadenzeAnagrafiche) {
            tmess.Generico(s.get(0).get(0) + s.get(1).get(0) + "   "
                    + s.get(0).get(1) + s.get(1).get(1) + "   "
                    + s.get(0).get(2) + s.get(1).get(2));
        }
        tmess.conclusione(TEST_18, TESTFAMILY, success);
    }

    
    @Test
    public void Inserisci_note() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_19, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_eventi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        //      Premo il tasto Gestisci Pratica.
        List<WebElement> table = driver.findElements(By.tagName("table"));
        List<WebElement> rows = table.get(1).findElements(By.tagName("tr"));
        Random generator = new Random();
        Integer n = generator.nextInt(rows.size() - 1) + 1;
        tmess.azione("Apro la pratica numero " + n);
        List<WebElement> columns = rows.get(n).findElements(By.tagName("td"));
        WebElement cell = columns.get(10).findElement(By.tagName("button"));
        cell.click();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        //     Premo il tasto Note
        SeleniumUtilities.safeClick(driver, By.linkText("Note"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        //     Leggo le note
        WebElement tabellaNote = SeleniumUtilities.safeFind(driver, By.id("frame7"), tmess);
        List<WebElement> listNote = tabellaNote.findElements(By.tagName("tr"));
        List<List<String>> valoriNote = new ArrayList<List<String>>();
        for (int a = 0; a < listNote.size(); a++) {
            if (a == 0) {
                valoriNote.add(SeleniumUtilities.changeListToString(listNote.get(a).findElements(By.tagName("th"))));
            } else if (!listNote.get(a).getText().equals("Non vi sono note per questa pratica")) {
                valoriNote.add(SeleniumUtilities.changeListToString(listNote.get(a).findElements(By.tagName("td"))));
            }
        }
        int numNote = valoriNote.size() - 1;
        tmess.Generico("Ci sono " + numNote + " note presenti");
        if (numNote > 1) {
            tmess.azione("Di cui:");
            for (int i = 1; i < valoriNote.size(); i++) {
                tmess.Generico(valoriNote.get(0).get(0) + ":  " + valoriNote.get(i).get(0) + "<BR>"
                        + valoriNote.get(0).get(1) + ":  " + valoriNote.get(i).get(1) + "<BR>"
                        + valoriNote.get(0).get(2) + ":  " + valoriNote.get(i).get(2));
            }
        }
        tmess.azione("Aggiungo una nota con su scritto 'Prova Nota'");
        SeleniumUtilities.safeClick(driver, By.className("insersisciNota"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.name("testo"), tmess).clear();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.name("testo"), tmess).sendKeys("Prova Nota");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.checkWindow(driver, "Ok", tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        //     Leggo le note
        SeleniumUtilities.safeClick(driver, By.linkText("Note"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tabellaNote = SeleniumUtilities.safeFind(driver, By.id("frame7"), tmess);
        listNote = tabellaNote.findElements(By.tagName("tr"));
        valoriNote = new ArrayList<List<String>>();
        for (int a = 0; a < listNote.size(); a++) {
            if (a == 0) {
                valoriNote.add(SeleniumUtilities.changeListToString(listNote.get(a).findElements(By.tagName("th"))));
            } else {
                valoriNote.add(SeleniumUtilities.changeListToString(listNote.get(a).findElements(By.tagName("td"))));
            }
        }
        tmess.Generico("Ora ci sono " + (valoriNote.size() - 1) + " note presenti");
        if (numNote != (valoriNote.size() - 2)) {
            tmess.Errori("Non è stata aggiunta nessuna nota!");
            success = false;
            tmess.conclusione(TEST_19, TESTFAMILY, success);
            return;
        }
        tmess.azione("Di cui:");
        for (int i = 1; i < valoriNote.size(); i++) {
            tmess.Generico(valoriNote.get(0).get(0) + ":  " + valoriNote.get(i).get(0) + "<BR>"
                    + valoriNote.get(0).get(1) + ":  " + valoriNote.get(i).get(1) + "<BR>"
                    + valoriNote.get(0).get(2) + ":  " + valoriNote.get(i).get(2));
        }
        tmess.conclusione(TEST_19, TESTFAMILY, success);
    }

    
    @Test
    public void Confronto_Eveneti() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_20, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_eventi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        //      Premo il tasto Gestisci Pratica.
        List<WebElement> table = driver.findElements(By.tagName("table"));
        List<WebElement> rows = table.get(1).findElements(By.tagName("tr"));
        tmess.azione("Apro la pratica numero " + 3);
        List<WebElement> columns = rows.get(3).findElements(By.tagName("td"));
        WebElement cell = columns.get(10).findElement(By.tagName("button"));
        cell.click();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        //     Premo il tasto Eventi
        SeleniumUtilities.safeClick(driver, By.linkText("Eventi"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        // Premo 16 volte il tasto indietro
        for (int i = 0; i < 16; i++) {
            SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_EVEN_BACK_BUTTON_EVENTI_CSS), tmess);
        }
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tmess.azione("Apro il tab 'eventi' e leggo il primo evento");
        WebElement firstLabel = SeleniumUtilities.safeFind(driver, By.cssSelector(WebElMap.AP_EVEN_READ_EVENTI_CSS), tmess);
        List<List<String>> datiEv = SeleniumUtilities.readLabelScadEven(firstLabel);

        tmess.Generico(datiEv.get(0).get(0) + "  " + datiEv.get(1).get(0) + "<BR>"
                + datiEv.get(0).get(1) + "  " + datiEv.get(1).get(1) + "<BR>"
                + datiEv.get(0).get(2) + "  " + datiEv.get(1).get(2));

        tmess.azione("Apro il dettaglio dell'evento letto");
        SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_EVEN_DETTAGLIO_BUTTON_EVENTI_CSS), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        WebElement secondLabel = SeleniumUtilities.safeFind(driver, By.cssSelector(WebElMap.AP_EVEN_READ_DETTAGLIO_EVENTI_CSS), tmess);
        List<List<String>> datiDettaglio = SeleniumUtilities.readLabelDetEven(secondLabel);

        tmess.Generico(datiDettaglio.get(0).get(4) + ":  " + datiDettaglio.get(1).get(4) + "<BR>"
                + datiDettaglio.get(0).get(0) + ":  " + datiDettaglio.get(1).get(0) + "<BR>"
                + datiDettaglio.get(0).get(1) + ":  " + datiDettaglio.get(1).get(1));

        if (datiEv.get(1).get(0).equals(datiDettaglio.get(1).get(4))
                && datiEv.get(1).get(1).equals(datiDettaglio.get(1).get(0))
                && datiEv.get(1).get(2).equals(datiDettaglio.get(1).get(1))) {
            tmess.Generico("I dati letti coincidono!");
        } else {
            tmess.Errori("Uno o piu' dati non coincidono!");
            success = false;
        }

        tmess.conclusione(TEST_20, TESTFAMILY, success);
    }

    @Test
    public void Aggiungi_Eveneti() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_21, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_eventi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        //      Premo il tasto Gestisci Pratica.
        List<WebElement> table = driver.findElements(By.tagName("table"));
        List<WebElement> rows = table.get(1).findElements(By.tagName("tr"));
        Random generator = new Random();
        Integer n = generator.nextInt(rows.size() - 1) + 1;
        tmess.azione("Apro la pratica numero " + n);
        List<WebElement> columns = rows.get(n).findElements(By.tagName("td"));
        WebElement cell = columns.get(10).findElement(By.tagName("button"));
        cell.click();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tmess.azione("Premo il tasto Eventi");
        SeleniumUtilities.safeClick(driver, By.linkText("Eventi"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        WebElement tableLabel = SeleniumUtilities.safeFind(driver, By.cssSelector(WebElMap.AP_EVEN_READ_NUMBER_EVENTI_CSS), tmess);
        List<WebElement> listLabel = tableLabel.findElements(By.className("item"));
        int a = listLabel.size();
        tmess.Generico("Ci sono " + a + " eventi.");

        tmess.azione("Premo il tasto Crea Nuovo Evento");
        SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        tmess.azione("Creo un evento 'Ricezione contro deduzioni', email di Gortan Giuseppe e una nota 'Prova Nota'");
        new Select(SeleniumUtilities.safeFind(driver, By.id("id_evento"), tmess)).selectByVisibleText("Ricezione contro deduzioni");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.className("primaryAction"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id("token-input-mittenti_a"), tmess).sendKeys("da");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        WebElement mittente = SeleniumUtilities.safeFind(driver, By.className("token-input-dropdown-item2-facebook"), tmess);
        tmess.azione("Inserisco il seguente mittente: " + mittente.getText());
        SeleniumUtilities.safeClick(driver, By.className("token-input-dropdown-item2-facebook"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.linkText("Altre azioni"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeFind(driver, By.id("note"), tmess).sendKeys("Prova Nota");
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.id("creaEvento"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        if (!driver.findElements(By.id("errorMsg")).isEmpty()) {
            tmess.Errori("Errore server!");
            success = false;
            tmess.conclusione(TEST_21, TESTFAMILY, success);
            return;
        } else {
            tmess.Generico("E' stato aggiunto un'evento.");
        }

        tmess.azione("Apro il tab 'eventi' e controllo se e' stato aggiunto un'evento");
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_eventi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        table = driver.findElements(By.tagName("table"));
        rows = table.get(1).findElements(By.tagName("tr"));
        columns = rows.get(n).findElements(By.tagName("td"));
        cell = columns.get(10).findElement(By.tagName("button"));
        cell.click();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        SeleniumUtilities.safeClick(driver, By.linkText("Eventi"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tableLabel = SeleniumUtilities.safeFind(driver, By.cssSelector(WebElMap.AP_EVEN_READ_NUMBER_EVENTI_CSS), tmess);
        listLabel = tableLabel.findElements(By.className("item"));

        tmess.azione("ci sono " + listLabel.size() + " eventi");

        if ((a + 1) == listLabel.size()) {
            tmess.Generico("E' stato aggiunto un'evento!");
        } else {
            tmess.Errori("Non è stato aggiunto nessun evento!");
            success = false;
            tmess.conclusione(TEST_21, TESTFAMILY, success);
            return;
        }

        WebElement show = listLabel.get(a).findElement(By.className("showdettaglio"));
        WebElement button = show.findElement(By.className("button"));
        button.click();
        tmess.conclusione(TEST_21, TESTFAMILY, success);
        WebElement secondLabel = SeleniumUtilities.safeFind(driver, By.cssSelector(WebElMap.AP_EVEN_READ_DETTAGLIO_EVENTI_CSS), tmess);
        List<List<String>> datiDettaglio = SeleniumUtilities.readLabelDetEven(secondLabel);
        if (datiDettaglio.get(1).get(0).equals("Ricezione contro deduzioni")
                && datiDettaglio.get(1).get(5).equals("Prova Nota")) {
            tmess.Generico("E' stato aggiunto l'evento giusto");
        } else {
            tmess.Errori("E' stato aggiunto un'evento sbagliato!");
            success = false;
        }

        tmess.conclusione(TEST_21, TESTFAMILY, success);
    }
    
    @Test
    public void Controllo_endoprocedimenti() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_22, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_eventi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

//      Premo il tasto Gestisci Pratica.
        List<WebElement> table = driver.findElements(By.tagName("table"));
        List<WebElement> rows = table.get(1).findElements(By.tagName("tr"));
        Random generator = new Random();
        Integer n = generator.nextInt(rows.size() - 1) + 1;
        tmess.azione("Apro la pratica numero " + n);
        List<WebElement> columns = rows.get(n).findElements(By.tagName("td"));
        WebElement cell = columns.get(10).findElement(By.tagName("button"));
        cell.click();

        driver.findElement(By.linkText("Endoprocedimenti")).click();

        List<WebElement> tabEndo = driver.findElements(By.tagName("tbody"));
        List<WebElement> trEndo = tabEndo.get(1).findElements(By.tagName("tr"));
        List<WebElement> labelEndo = trEndo.get(0).findElements(By.tagName("td"));

        for (int i = 0; i < labelEndo.size(); i++) {
            List<List<String>> listLabelEndo = SeleniumUtilities.readLebelledInputMask(labelEndo.get(i));
            //TMess.azione(""+listLabelEndo);

            SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

            String procedimento = listLabelEndo.get(1).get(0);
            tmess.azione(procedimento);

            String terminiEvasione = listLabelEndo.get(1).get(1);
            tmess.azione(terminiEvasione);

            String enteCompetente = listLabelEndo.get(1).get(2);
            tmess.azione(enteCompetente);
        }

        tmess.conclusione(TEST_22, TESTFAMILY, success);
    }
    
    
    @Test
    public void Aggiunta_endoprocedimento() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_23, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_eventi(driver);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

//      Premo il tasto Gestisci Pratica.
        List<WebElement> table = driver.findElements(By.tagName("table"));
        List<WebElement> rows = table.get(1).findElements(By.tagName("tr"));
        Random generator = new Random();
        Integer n = generator.nextInt(rows.size() - 1) + 1;
        tmess.azione("Apro la pratica numero " + n);
        List<WebElement> columns = rows.get(n).findElements(By.tagName("td"));
        WebElement cell = columns.get(10).findElement(By.tagName("button"));
        cell.click();

        driver.findElement(By.linkText("Endoprocedimenti")).click();
        
        List<List<List<String>>> edProcedures = readEndoProcedures();
        Integer j = edProcedures.size();

        SeleniumUtilities.safeClick(driver, By.linkText("Aggiungi procedimento"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

        List<WebElement> tableAct = driver.findElements(By.tagName("table"));
        List<WebElement> rowsAct = tableAct.get(1).findElements(By.tagName("tr"));

        for (int x = 1; x < rowsAct.size(); x++) {
            tableAct = driver.findElements(By.tagName("table"));
            rowsAct = tableAct.get(1).findElements(By.tagName("tr"));
            List<WebElement> columnsAct = rowsAct.get(x).findElements(By.tagName("td"));
            WebElement cellName = columnsAct.get(1).findElement(By.className("testotable"));
            tmess.azione("Seleziono il procedimento: " + cellName.getText());
            WebElement cellAct = columnsAct.get(2).findElement(By.tagName("button"));
            cellAct.click();

            WebElement tableEnte = driver.findElement(By.id("list"));
            List<WebElement> rowsEnte = tableEnte.findElements(By.tagName("tr"));

            for (int i = 1; i < rowsEnte.size(); i++) {
                tableEnte = driver.findElement(By.id("list"));
                rowsEnte = tableEnte.findElements(By.tagName("tr"));
                List<WebElement> columnsEnte = rowsEnte.get(i).findElements(By.tagName("td"));
                WebElement cellEnteName = columnsEnte.get(1).findElement(By.className("testotable"));
                tmess.azione("Seleziono l'ente: " + cellEnteName.getText());
                WebElement cellEnte = columnsEnte.get(2).findElement(By.tagName("button"));
                cellEnte.click();

                SeleniumUtilities.wait_for_ajax_loading(driver, tmess);

                if (driver.findElements(By.id("errorMsg")).isEmpty()) {
                    x = rowsAct.size();
                    tmess.azione("L'endoprocedimento relativo all'ente selezionato verra' aggiunto.");
                    break;
                } else {
                    tmess.azione("L'ente dell'endoprocedimento che si e' cercato di inserire risulta essere gia' presente.");
                }
            }
            if (x != rowsAct.size()) {
                tmess.azione("Nessuno dei precedenti Enti è associabile al procedimento, scelgo un nuovo procedimento.");
                WebElement backBtn = driver.findElement(By.className("secondaryAction"));
                backBtn.click();
            }
        }
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        driver.findElement(By.linkText("Endoprocedimenti")).click();
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        
        List<List<List<String>>> ed2Procedures = readEndoProcedures();
        Integer k = ed2Procedures.size();

            if (k == j + 1) {
                tmess.risultati("e' stato aggiunto con successo un nuovo procedimento.");
                success = false;
            } else {
                tmess.Errori("Non è stato aggiunto alcun nuovo procedimento.");
            }
       
        tmess.conclusione(TEST_23, TESTFAMILY, success);
    }

     public List<List<List<String>>> readEndoProcedures() throws Exception{
        List<WebElement> tabEndo = driver.findElements(By.tagName("tbody"));
        List<WebElement> trEndo = tabEndo.get(1).findElements(By.tagName("tr"));
        List<WebElement> labelEndo = trEndo.get(0).findElements(By.tagName("td"));
        List<List<List<String>>> allEndoProcedures = new ArrayList<List<List<String>>>();
        for (int i = 0; i < labelEndo.size(); i++) {
            List<List<String>> listLabelEndo = SeleniumUtilities.readLebelledInputMask(labelEndo.get(i));
            allEndoProcedures.add(listLabelEndo);
        }
        return allEndoProcedures;
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

    public List<List<List<String>>> readTableElementsInSelect() throws Exception {
        WebElement tabellaAnagrafiche = SeleniumUtilities.safeFind(driver, By.tagName("table"), tmess);
        List<WebElement> trAnagrafiche = tabellaAnagrafiche.findElements(By.tagName("td"));
        List<List<List<String>>> anagraficheCollegate = new ArrayList<List<List<String>>>();
        for (WebElement tr : trAnagrafiche) {
            List<List<String>> anagrafica = SeleniumUtilities.readLebelledInputMask(tr);
            anagraficheCollegate.add(anagrafica);
        }
        return anagraficheCollegate;
    }

    @AfterClass
    static public void tearDown() throws Exception {
        driver.quit();
    }

    private void OpenSearchPanel() {
        SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_PRAT_APRI_PANNELLO_RICERCA_BUTTON_CSSSELECTOR), tmess);
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
}
