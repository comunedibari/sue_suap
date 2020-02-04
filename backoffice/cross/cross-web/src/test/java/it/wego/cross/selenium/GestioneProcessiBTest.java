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
import org.junit.Test;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

public class GestioneProcessiBTest {

    static final private String TESTFAMILY = "GestioneProcessi";
    static final private String TEST_34 = "Gestisce_flusso_aggiungi_evento_e_scelta_nulla";
    static final private String TEST_35 = "Gestione_flusso_rimuovi_evento";
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

    private void GoTo_gestione_processi(WebDriver driver) {
        SeleniumUtilities.safeClick(driver, By.cssSelector(WebElMap.AP_GESUT_IMPOSTAZIONI_BUTTON_CSSSELECTOR), tmess);
        SeleniumUtilities.safeClick(driver, By.xpath(WebElMap.AP_GESPR_APERTURA_PROCESSI_BUTTON_XPATH), tmess);
    }
    
    @Test
    public void Gestisce_flusso_aggiungi_evento_e_scelta_nulla() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_34, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        List<List<String>> tabletext = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        Random generatorEnte = new Random();
        Integer m = generatorEnte.nextInt(tabletext.size());
        List<WebElement> actionCol = driver.findElements(By.className("gestione_eventi"));
        for (int i = m; i < actionCol.size(); i++) {
            tmess.azione("Premo la gestione eventi del processo numero: " + m);
            actionCol.get(m).click();
            break;
        }
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tabletext = SeleniumUtilities.getTable(driver, "list", "title", tmess);

        Random generatorEvento = new Random();
        Integer s = generatorEvento.nextInt(tabletext.size());

        List<WebElement> eventCol = driver.findElements(By.className("gestione_flusso"));
        tmess.azione("Premo l'evento numero: " + s);
        SeleniumUtilities.safeClick2(driver, eventCol.get(s), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        
        WebElement selector = SeleniumUtilities.safeFind(driver, By.name("step"), tmess);
        List<String> options = SeleniumUtilities.changeListToString(selector.findElements(By.tagName("option")));
       
        tmess.azione("Vado a selezionare l'opzione Aggiungi Evento, all'interno del selector della colonna Azione.");
        new Select(driver.findElement(By.name("step"))).selectByVisibleText("Aggiungi evento");
        driver.findElement(By.cssSelector("option[value=\"ADD\"]")).click();
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tabletext = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        List<String> operaCol = TestUtilities.getCol(tabletext, 3);
        String primaOperazione = operaCol.get(0);
        tmess.azione("L'esito della modifica ha dato come risultato: " + primaOperazione);
        if(!options.get(1).equals(primaOperazione)){
            tmess.Errori("La modifica del selector nel campo azione e il risultato ottenuto nella colonna Operazione, non coincidono.");
        }
        
        tmess.conclusione(TEST_34, TESTFAMILY, success);
    }

    @Test
    public void Gestione_flusso_rimuovi_evento() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_35, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_gestione_processi(driver);
        List<List<String>> tabletext = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        Random generatorEnte = new Random();
        Integer m = generatorEnte.nextInt(tabletext.size());
        List<WebElement> actionCol = driver.findElements(By.className("gestione_eventi"));
        for (int i = m; i < actionCol.size(); i++) {
            tmess.azione("Premo la gestione eventi del processo numero: " + m);
            actionCol.get(m).click();
            break;
        }
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tabletext = SeleniumUtilities.getTable(driver, "list", "title", tmess);

        Random generatorEvento = new Random();
        Integer s = generatorEvento.nextInt(tabletext.size());

        List<WebElement> eventCol = driver.findElements(By.className("gestione_flusso"));
        tmess.azione("Premo l'evento numero: " + s);
        SeleniumUtilities.safeClick2(driver, eventCol.get(s), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        
        WebElement selector = SeleniumUtilities.safeFind(driver, By.name("step"), tmess);
        List<String> options = SeleniumUtilities.changeListToString(selector.findElements(By.tagName("option")));
       
        tmess.azione("Vado a selezionare l'opzione: "+options.get(2)+", all'interno del selector della colonna Azione.");
        new Select(driver.findElement(By.name("step"))).selectByVisibleText(options.get(2));
        driver.findElement(By.cssSelector("option[value=\"SUB\"]")).click();
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        tabletext = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        List<String> operaCol = TestUtilities.getCol(tabletext, 3);
        String primaOperazione = operaCol.get(0);
        tmess.azione("L'esito della modifica ha dato come risultato: " + primaOperazione);

        if(!options.get(2).equals(primaOperazione)){
            tmess.Errori("La modifica del selector nel campo azione e il risultato ottenuto nella colonna Operazione, non coincidono.");
        }
        
        tmess.conclusione(TEST_35, TESTFAMILY, success);
    }
    
    @AfterClass
    static public void tearDown() throws Exception {
        driver.quit();
    }
}
