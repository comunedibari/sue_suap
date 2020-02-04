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
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 *
 * @author Luca
 */
public class ConsoleTest {

    static final private String TESTFAMILY = "GestioneEnti";
    static final private String TEST_1 = "Ordine_console";
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

    private void GoTo_console(WebDriver driver) throws Exception {
        SeleniumUtilities.safeClick(driver, By.linkText("Impostazioni"), tmess);
        SeleniumUtilities.safeClick(driver, By.linkText("Console"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
    }

    @Test
    public void Ordine_console() throws Exception {
        boolean success = true;
        tmess.NuovoTest(TEST_1, TESTFAMILY);
        SeleniumUtilities.HomePage(driver);
        GoTo_console(driver);

        tmess.azione("Ordino in modo crescente le Date e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_errori_data"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        List<List<String>> textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        List<String> elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(1));
        }
        if (TestUtilities.CheckDateCrescent(elenco, true, tmess)) {
            tmess.Generico("Le date sono in ordine crescente");
        } else {
            success = false;
            tmess.Errori("Le date non sono in ordine crescente!");
        }

        tmess.azione("Ordino in modo decrescente le Date e controllo");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_errori_data"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(1));
        }
        if (TestUtilities.CheckDateCrescent(elenco, false, tmess)) {
            tmess.Generico("Le date sono in ordine decrescente");
        } else {
            success = false;
            tmess.Errori("Le date non sono in ordine decrescente!");
        }

        tmess.azione("Ordino in modo crescente le Descrizioni");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_errori_descrizione"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(2));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, true)) {
            tmess.Generico("Le descrizioni sono in ordine crescente");
        } else {
            success = false;
            tmess.Errori("Le descrizioni non sono in ordine crescente!");
        }

        tmess.azione("Ordino in modo decrescente le Descrizioni");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_errori_descrizione"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(2));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, false)) {
            tmess.Generico("Le descrizioni sono in ordine decrescente");
        } else {
            success = false;
            tmess.Errori("Le descrizioni non sono in ordine decrescente!");
        }

        tmess.azione("Ordino in modo crescente le Trace");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_errori_trace"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(3));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, true)) {
            tmess.Generico("Le descrizioni sono in ordine crescente");
        } else {
            success = false;
            tmess.Errori("Le descrizioni non sono in ordine crescente!");
        }

        tmess.azione("Ordino in modo decrescente le Trace");
        SeleniumUtilities.safeClick(driver, By.id("jqgh_errori_trace"), tmess);
        SeleniumUtilities.wait_for_ajax_loading(driver, tmess);
        textable = SeleniumUtilities.getTable(driver, "list", "title", tmess);
        elenco = new ArrayList<String>();
        for (List<String> row : textable) {
            elenco.add(row.get(3));
        }
        if (TestUtilities.isSortedAlphabetically(elenco, false)) {
            tmess.Generico("Le descrizioni sono in ordine decrescente");
        } else {
            success = false;
            tmess.Errori("Le descrizioni non sono in ordine decrescente!");
        }

        tmess.conclusione(TEST_1, TESTFAMILY, success);
    }

    @AfterClass

    static public void tearDown() throws Exception {
        driver.quit();
    }

}
