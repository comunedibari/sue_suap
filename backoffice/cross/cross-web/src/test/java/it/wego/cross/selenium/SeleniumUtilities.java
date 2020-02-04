/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.selenium;

import com.thoughtworks.selenium.Selenium;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author GabrieleM
 */
public class SeleniumUtilities {
    

    private static final String username = "CTTTCN57P51F205W";
    private static final String password = "wego2011";

    public static void Login(Selenium selenium) {
        selenium.open("/cross/");
        selenium.type("id=username", username);
        selenium.type("id=password", password);
        selenium.click("name=submit");
    }

    public static void Login(WebDriver driver, TMess tmess) {
        driver.get("http://localhost:8080/" + "/cross");
        WebElement password = safeFind(driver, By.id("password"), tmess);
        safeFind(driver, By.id("username"), tmess).sendKeys("CTTTCN57P51F205W");
        password.sendKeys("wego2013");
        WebElement submit = safeFind(driver, By.name("submit"), tmess);
        submit.click();
    }

    public static void HomePage(WebDriver driver) {
        driver.get("http://localhost:8080/" + "/cross");
    }

    /**
     *
     * @param driver
     * @param by
     */
    public static void safeClick(WebDriver driver, By by, TMess tmess) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = safeFind(driver, by, tmess);
        if (element == null) {
            tmess.warning("L'oggetto da cliccare " + by.toString() + " non e' stato trovato.");
            return;
        } else {
            element = wait.until(ExpectedConditions.elementToBeClickable(by));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView(true);", element);
            try {
                element.click();
            } catch (Exception e) {
                tmess.warning("Mi aspettavo di poter cliccare questo oggetto " + by.toString() + " ma non e' stato possibile. Lo stack-trace dell'errore e' " + e.toString());
            }
        }
    }

    public static WebElement safeFind(WebDriver driver, By by, TMess tmess) {
        WebElement result = null;
        try {
            result = driver.findElement(by);
        } catch (Exception e) {
            tmess.warning("L'oggetto html cercato:" + by.toString() + " non e' presente." + "Lo stack-trace e'" + e.toString());
        }
        return result;
    }
    
    public static List<WebElement> safeFindList(WebDriver driver, By by, TMess tmess) {
        List<WebElement> result = new ArrayList<WebElement>();
        try {
            result = driver.findElements(by);
        } catch (Exception e) {
            tmess.warning("L'oggetto html cercato:" + by.toString() + " non e' presente." + "Lo stack-trace e'" + e.toString());
        }
        return result;
    }
    
    public static void safeClick2(WebDriver driver, WebElement element, TMess tmess) {
        if (element == null) {
            tmess.warning("L'oggetto da cliccare " + element.toString() + " non e' stato trovato.");
            return;
        } else {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView(true);", element);
            try {
                element.click();
            } catch (Exception e) {
                tmess.warning("Mi aspettavo di poter cliccare questo oggetto " + element.toString() + " ma non e' stato possibile. Lo stack-trace dell'errore e' " + e.toString());
            }
        }
    }

    public static List<List<String>> getTable(WebDriver driver, String tableid, String tdattribute, TMess tmess) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        List<List<String>> tabletext = new ArrayList<List<String>>();
        try {
            WebElement table = wait.until(ExpectedConditions.elementToBeClickable(By.id(tableid)));
            List<WebElement> trs = table.findElements(By.tagName("tr"));
            for (WebElement tr : trs) {
                List<String> trtext = new ArrayList<String>();
                List<WebElement> tds = tr.findElements(By.tagName("td"));
                if (!tds.get(0).getAttribute(tdattribute).equals("")) {
                    for (WebElement td : tds) {
                        String temp = td.getAttribute(tdattribute);
                        trtext.add(temp);
                    }
                }
                if (trtext.size() > 0) {
                    tabletext.add(trtext);
                }
            }
        } catch (Exception e) {
            tmess.warning("Mi aspetavo una tabella in questa pagina e non l'ho trovata, questa tabella e' vuota.");
        }
//           List<WebElement> trs=safeFinds(By.tagName("tr"));
        return tabletext;
    }

    static public void wait_for_ajax_loading(WebDriver driver, TMess tmess) throws Exception {
        try {
            Thread.sleep(2000);
            if (safeFind(driver, By.id("loading"), tmess) != null) {
                while (safeFind(driver, By.id("loading"), tmess) != null && safeFind(driver, By.id("loading"), tmess).isDisplayed())//wait till the loading screen disappears
                {
                    Thread.sleep(1000);

                }
            }
        } catch (Exception e) {
            tmess.warning("Problemi nell'esecuzione di un comando ajax");
        }

    }

    static public void waitForElToDisappear(WebDriver driver, By by, TMess tmess) throws Exception {
        Integer loop = 0;
        try {
            Thread.sleep(2000);
            if (safeFind(driver, by, tmess) != null) {
                while (safeFind(driver, by, tmess) != null && safeFind(driver, by, tmess).isDisplayed() && loop < 10)//wait till the loading screen disappears
                {
                    Thread.sleep(1000);
                    loop++;
                }
            }
        } catch (Exception e) {
            tmess.warning("Aspettavo che l'elemento scomparisse ma non scompare");
        }

    }

    static public void printTable(List<List<String>> tabletext) {
        for (List<String> trtext : tabletext) {
            for (String tdtext : trtext) {
                System.out.print(tdtext);
                System.out.print("---");
            }
            System.out.print("\n");
            System.out.print("\n");
        }
    }

    static public List<List<String>> readTableElementsInPages(WebDriver driver, By nextPageSelector, By backTofirst, String tableId, String tableAttribute, Integer pageNumber, TMess tmess) throws Exception {
        tmess.azione("Inizio la lettura della tabella");
        List<List<String>> finalTable = new ArrayList<List<String>>();
        for (Integer i = 0; i < pageNumber; i++) {
            tmess.azione("page" + i);
            List<List<String>> tempTable = getTable(driver, tableId, tableAttribute, tmess);
            finalTable.addAll(tempTable);
            tmess.azione("Nella pagina:" + tempTable.size());
//             for(List<String> s : tempTable)
//        {
//            System.out.println("PAGINA: "+ i.toString() +s.toString());

            //
            //WebElement webElement = safeFind(driver, nextPageSelector);
            //((JavascriptExecutor) driver).executeScript(
            //       "arguments[0].scrollIntoView(true);", webElement);
            //TMess.azione("Clicco");
            //
            tryAndClick(driver, nextPageSelector, tmess);
//            safeFind(nextPageSelector).click();
            wait_for_ajax_loading(driver, tmess);
        }
        wait_for_ajax_loading(driver, tmess);
        safeClick(driver, backTofirst, tmess);
        wait_for_ajax_loading(driver, tmess);
        return finalTable;
    }

    static public Integer countOccurenceInCol(List<String> col, String occurence) {
        Integer result = 0;
        for (String s : col) {
            if (s.equals(occurence)) {
                result++;
            }
        }
        return result;
    }

    static public Integer countOccurenceProtocolInCol(List<String> col, String occurence) {
        Integer result = 0;
        for (String s : col) {
            String t = s.split("/")[0];
            if (t.equals(occurence)) {
                result++;
            }
        }
        return result;
    }

    static public Integer countOccurrenceOfDataInCol(List<String> col, String fromdate, String todate) throws ParseException {
        Integer result = 0;
        for (String s : col) {
            if (checkDateInterval(fromdate, todate, s)) {
                result = result + 1;
            }
        }
        return result;
    }

    static public Boolean checkDateInterval(String first, String second, String check) throws ParseException {
        Boolean result = true;
        Date current = new SimpleDateFormat("dd/MM/yyyy").parse(check);
        Date dateb = new SimpleDateFormat("dd/MM/yyyy").parse(first);
        Date datea = new SimpleDateFormat("dd/MM/yyyy").parse(second);
        if (current.after(datea) || current.before(dateb)) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    static public void waitUntilElementIsClickable(WebDriver driver, By by) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    static public void wait(WebDriver driver) throws InterruptedException {
        for (Integer i = 0; i < 5; i++) {
            Thread.sleep(1000);
        }
    }

    static public void tryAndClick(WebDriver driver, By by, TMess tmess) throws InterruptedException {
        try {
            safeClick(driver, by, tmess);
        } catch (Exception e) {
            try {
                Thread.sleep(1000);
                safeClick(driver, by, tmess);
            } catch (Exception ex) {
                try {
                    Thread.sleep(1000);
                    safeClick(driver, by, tmess);
                } catch (Exception exc) {
                    tmess.warning("Non riesco a cliccare l'elemento," + exc);
                }
            }
        }
    }

    static public List<String> readTestotable(WebElement testotable) {
        List<String> result;
        result = Arrays.asList(testotable.getText().split("[\\r\\n]+"));
        return result;
    }

    static public List<WebElement> findWebElementInCol(WebDriver driver, String tableid, Integer column, Integer fromline, Integer toline, By by, TMess tmess) {
        List<WebElement> elements = new ArrayList<WebElement>();
        WebDriverWait wait = new WebDriverWait(driver, 1);
        WebElement table = wait.until(ExpectedConditions.elementToBeClickable(By.id(tableid)));
        List<WebElement> trs = table.findElements(By.tagName("tr"));
        Integer limit = toline;
        if (limit.intValue() == -1) {
            limit = trs.size() - 1;
        }
        for (Integer i = fromline; i <= limit; i++) {
            List<WebElement> tds = trs.get(i).findElements(By.tagName("td"));
            WebElement toadd = null;
            try {
                toadd = tds.get(column).findElement(by);
            } catch (Exception e) {
                tmess.azione("elemento non presente");
            }
            if (toadd != null) {
                elements.add(toadd);
            }
        }
        return elements;
    }

    static public List<List<String>> readLebelledInputMask(WebElement container) {
        List<List<String>> result = new ArrayList<List<String>>();
        List<WebElement> labels = container.findElements(By.tagName("label"));
        List<WebElement> disabledinputs = container.findElements(By.className("input_anagrafica_disable"));
        List<WebElement> textareas = container.findElements(By.tagName("textarea"));
        List<String> etichette = new ArrayList<String>();
        List<String> valori = new ArrayList<String>();
        for (WebElement label : labels) {
            etichette.add(label.getText());
        }
        for (WebElement disabledinput : disabledinputs) {
            valori.add(disabledinput.getAttribute("value"));

        }
        for (WebElement testarea : textareas) {
            valori.add(testarea.getText());
        }
        List<WebElement> allValori = new ArrayList<WebElement>();
        allValori.addAll(disabledinputs);
        if (textareas.size() > 0) {
            allValori.addAll(textareas);
            List<Integer> permutation = orderElementsTopBottom(allValori);
            valori = orderListWithGivenPermutation(valori, permutation);
        }
        
        result.add(etichette);
        result.add(valori);
        return result;
    }
    
    static public List<List<String>> readLabelScadEven(WebElement container){       
        List<List<String>> result = new ArrayList<List<String>>();
        List<WebElement> labels = container.findElements(By.tagName("label"));
        List<WebElement> values = container.findElements(By.tagName("input"));
        List<String> etichette = new ArrayList<String>();
        List<String> valori = new ArrayList<String>();        
        for (WebElement label : labels){
            etichette.add(label.getText());
        }
        for (WebElement value : values){
            valori.add(value.getAttribute("value"));
        }
        result.add(etichette);
        result.add(valori);
        return result;
    }
    
    static public List<List<String>> readLabelDetEven(WebElement container){
        List<List<String>> result = new ArrayList<List<String>>();
        List<WebElement> labels = container.findElements(By.tagName("label"));
        List<WebElement> values = container.findElements(By.tagName("span"));
        List<String> etichette = new ArrayList<String>();
        List<String> valori = new ArrayList<String>();        
        for (WebElement label : labels){
            etichette.add(label.getText());
        }
        for (WebElement value : values){
            valori.add(value.getText());
        }
        result.add(etichette);
        result.add(valori);
        return result;
    }

    static public List<Integer> orderElementsTopBottom(List<WebElement> elements) {
        List<Integer> orderList = new ArrayList<Integer>();
        Map<Integer, Integer> elemento_posizione = new TreeMap<Integer, Integer>();
        Integer count = 0;
        for (WebElement element : elements) {
            elemento_posizione.put(element.getLocation().getY(), count);
            count++;
        }
        for (Map.Entry entry : elemento_posizione.entrySet()) {
            orderList.add((Integer) entry.getValue());
        }
        return orderList;
    }

    static public List<String> orderListWithGivenPermutation(List<String> unorderd, List<Integer> permutation) {
        List<String> result = new ArrayList<String>();
        for (Integer i : permutation) {
            result.add(unorderd.get(i));
        }
        return result;
    }

    private static Map sortByComparator(Map unsortMap) {

        List list = new LinkedList(unsortMap.entrySet());

        // sort list based on comparator
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        // put sorted list into map again
        //LinkedHashMap make sure order in which keys were inserted
        Map sortedMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public static List<String> readSidebarElemento(WebDriver driver, By by) {
        List<String> result = new ArrayList<String>();
        WebElement sideBar = driver.findElement(by);
        result = Arrays.asList(sideBar.getText().split("[\\r\\n]+"));
//        List<WebElement> pars = sideBar.findElements(By.tagName("p"));
//        String protocollo="";
//        try {
//            protocollo = pars.get(1).getText().split(" ")[1];
//        } catch (Exception e) {
//            TMess.warning("Errore nella visualizzazione del numero di protocollo.");
//        }
//        result.add(oggetto);
//        result.add(protocollo);
        return result;
    }

    public static String readDatumFromTable(WebDriver driver, String tableid, String attribute, Integer row, Integer col) {
        String result = "error";
        WebDriverWait wait = new WebDriverWait(driver, 5);
        List<List<String>> tabletext = new ArrayList<List<String>>();
        WebElement table = wait.until(ExpectedConditions.elementToBeClickable(By.id(tableid)));
        List<WebElement> trs = table.findElements(By.tagName("tr"));
        List<WebElement> tds = trs.get(row).findElements(By.tagName("td"));
        result = tds.get(col).getAttribute(attribute);
        return result;
    }

    public static List<String> readVisibleFieldLabelFromContainer(WebElement container, List<String> selectIds) {
        List<String> result = new ArrayList();
        if (!container.isDisplayed()) {
            return result;
        }
        List<WebElement> inputs = container.findElements(By.tagName("input"));
        List<WebElement> selectorsel = new ArrayList<WebElement>();
        for (String selectid : selectIds) {
            selectorsel = container.findElements(By.id(selectid));
        }
        List<Select> selectors = new ArrayList<Select>();
        for (WebElement sel : selectorsel) {
            if (sel.isDisplayed()) {
                selectors.add(new Select(sel));
            }
        }
        //aggiungere altre liste di oggetti se servono
        for (WebElement el : inputs) {
            if (!el.getAttribute("type").equals("hidden")) {
                result.add(el.getAttribute("value"));
            }
        }
        for (Select sel : selectors) {
            result.add(sel.getFirstSelectedOption().getText());
        }
        return result;
    }

    public static void cleanVisibileFieldFromContainer(WebElement container, List<String> selectIds, List<String> defaultOptions, TMess tmess) {
        if (!container.isDisplayed()) {
            return;
        }
        List<WebElement> inputs = container.findElements(By.tagName("input"));
        List<WebElement> selectorsel = new ArrayList<WebElement>();
        for (String selectid : selectIds) {
            selectorsel = container.findElements(By.id(selectid));
        }
        List<Select> selectors = new ArrayList<Select>();
        for (WebElement sel : selectorsel) {
            if ((sel.isDisplayed())&&(sel.getAttribute("type")==null)&&(sel.getAttribute("hidden")==null)&&(sel.getAttribute("disabled")==null)) {
                selectors.add(new Select(sel));
            }
        }
        //aggiungere altre liste di oggetti se servono
        for (WebElement el : inputs) {
            if ((!el.getAttribute("type").equals("hidden") && (el.getAttribute("readonly") == null)) && (el.isDisplayed()) && (el.getAttribute("disabled") == null)) {
                el.clear();
                tmess.azione("cancellato il campo:" + el.getAttribute("name") + ".");
            } else if ((!el.getAttribute("type").equals("hidden") && (el.getAttribute("readonly") == null)) && (el.isDisplayed())&& (el.getAttribute("readonly") != null)) {
                tmess.azione("Il campo:" + el.getAttribute("name") + " e' readonly e non può essere cancellato.");
            }
        }
        for (Integer i = 0; i < selectors.size(); i++) {
            selectors.get(i).selectByVisibleText(defaultOptions.get(i));
            if (selectors.get(i).isMultiple()) {
                selectors.get(i).deselectAll();
            }
        }
    }

    public static List<WebElMap.inputTypes> findFirstTypeOfEachContainer(WebElement bigContainer, String containerTag, List<String> possibleSelectsIds, String autocompleteClass) {
        List<WebElMap.inputTypes> inputTypes = new ArrayList<WebElMap.inputTypes>();
        List<WebElement> tags = bigContainer.findElements(By.tagName(containerTag));
        for (WebElement tag : tags) {
            List<WebElMap.inputTypes> temp = findVisibileInputTypeInContainer(tag, possibleSelectsIds, autocompleteClass);
            if (temp.size() > 0) {
                inputTypes.add(temp.get(0));
            }
        }
        return inputTypes;
    }

    public static List<WebElMap.inputTypes> findVisibileInputTypeInContainer(WebElement container, List<String> possibleSelectsIds, String autocompleteClass) {
        List<WebElMap.inputTypes> inputTypes = new ArrayList<WebElMap.inputTypes>();
        List<WebElement> selectors = new ArrayList<WebElement>();
        for (String possibleSelectId : possibleSelectsIds) {
            selectors.addAll(container.findElements(By.id(possibleSelectId)));
        }
        for (ListIterator<WebElement> iter = selectors.listIterator(selectors.size()); iter.hasPrevious();) {
            WebElement sel = iter.previous();
            if ((!sel.isDisplayed()) || (sel.getAttribute("type").equals("hidden") || sel.getAttribute("disable") != null)) {
                iter.remove();
            }
        }
        List<WebElement> inputs = container.findElements(By.tagName("input"));
        for (ListIterator<WebElement> iter = inputs.listIterator(inputs.size()); iter.hasPrevious();) {
            WebElement input = iter.previous();
            if ((!input.isDisplayed() || (input.getAttribute("type").equals("hidden")) || (input.getAttribute("disable") != null))) {
                iter.remove();
            }
        }
        for (WebElement input : inputs) {
            if (input.getAttribute("readonly") != null) {
                inputTypes.add(WebElMap.inputTypes.READONLYINPUT);
            } else if (input.getAttribute("class").equals(autocompleteClass)) {
                inputTypes.add(WebElMap.inputTypes.AUTOCOMPLETEINPUT);
            } else {
                inputTypes.add(WebElMap.inputTypes.SIMPLEINPUT);
            }

        }
        for (WebElement sel : selectors) {
            inputTypes.add(WebElMap.inputTypes.SIMPLESELECT);
        }
        return inputTypes;
    }

    public static Boolean sendkeyToVisibileInputAutoCompleteFieldFromContainer(WebElement container, String label, String value, TMess tmess) {
        if (!container.isDisplayed()) {
            return false;
        }
        List<WebElement> inputs = container.findElements(By.tagName("input"));
        for (WebElement el : inputs) {
            if ((!el.getAttribute("type").equals("hidden") && (el.getAttribute("readonly") == null)) && (el.isDisplayed()) && (el.getAttribute("disabled") == null)) {
                el.sendKeys(value);
                tmess.azione("Campo " + label + " compilato con il valore " + value);
                return true;
            }
        }
        return false;
    }

    public static Boolean selectOptionFromVisibleSelectFieldFromContainer(WebElement container, String label, String selectid, String textToselect, TMess tmess) {
        Select selector;
        List<WebElement> selects = container.findElements(By.id(selectid));
        if (selects.size() == 0) {
            return false;
        } else {
            for (WebElement el : selects) {
                if ((el.isDisplayed()) && (el.getAttribute("type").equals("hidden"))) {
                    selector = new Select(el);
                    selector.selectByVisibleText(textToselect);
                    tmess.azione("Per il campo " + label + " selezionato il valore " + textToselect);
                    return true;
                }
            }
            return false;
        }
    }

    public static Boolean sendkeyToVisibileInputFieldFromContainer(WebElement container, String label, String value, TMess tmess) {
        if (!container.isDisplayed()) {
            return false;
        }
        List<WebElement> inputs = container.findElements(By.tagName("input"));
        for (WebElement el : inputs) {
            if ((!el.getAttribute("type").equals("hidden") && (el.getAttribute("readonly") == null)) && (el.isDisplayed()) && (el.getAttribute("disabled") == null)) {
                el.sendKeys(value);
                tmess.azione("Campo" + label + " compilato con il valore " + value);
                return true;
            }
        }
        return false;
    }

    public static void autocompleteDefValues(WebElement bigContainer, List<String> possibleSelectsIds, String autocompleteClass, String containerTag, List<String> labels, String defaultForInputs, String defaultDate, List<String> defaultsForSelect, TMess tmess) {
        List<WebElMap.inputTypes> types = findVisibileInputTypeInContainer(bigContainer, possibleSelectsIds, autocompleteClass);
        List<WebElement> tags = bigContainer.findElements(By.tagName(containerTag));
        Integer selectorCount = 0;

        if (types.size() != tags.size()) {
            Integer labelIndex = 0;
            for (Integer i = 0; i < tags.size(); i++) {
                List< WebElMap.inputTypes> currentTypes = findVisibileInputTypeInContainer(tags.get(i), possibleSelectsIds, autocompleteClass);
                if (currentTypes.size() > 0) {
                    WebElMap.inputTypes currentType = currentTypes.get(0);
                    Boolean isvisible = false;
                    if (currentType.equals(WebElMap.inputTypes.READONLYINPUT)) {
                        tmess.azione("Il campo " + labels.get(labelIndex) + " e' readonly e non può essere modificato");
                        labelIndex++;
                    } else if (currentType.equals(WebElMap.inputTypes.AUTOCOMPLETEINPUT)) {
                        sendkeyToVisibileInputAutoCompleteFieldFromContainer(tags.get(i), labels.get(labelIndex), defaultForInputs, tmess);
                        labelIndex++;
                    } else if (currentType.equals(WebElMap.inputTypes.SIMPLEINPUT)) {
                        sendkeyToVisibileInputFieldFromContainer(tags.get(i), labels.get(labelIndex), defaultForInputs, tmess);
                        labelIndex++;
                    } else if (currentType.equals(WebElMap.inputTypes.SIMPLESELECT)) {
                        selectOptionFromVisibleSelectFieldFromContainer(tags.get(i), labels.get(labelIndex), possibleSelectsIds.get(selectorCount), defaultsForSelect.get(selectorCount), tmess);
                        labelIndex++;
                        selectorCount++;
                    }
                }
            }
        }
    }

    public static List<String> readDettaglioAnagrafiche(WebDriver driver, WebElement container, String tagContainingFields, List<String> selectIds) {
        List<String> result = new ArrayList<String>();
        List<WebElement> tagConts = container.findElements(By.tagName(tagContainingFields));
        for (WebElement cont : tagConts) {
            List< String> toadd = readVisibleFieldLabelFromContainer(cont, selectIds);
            if (toadd.size() > 0) //per ora prendo solo il primo elemento della lista restituita perché di solito ce n'e' uno.
            {
                result.add(toadd.get(0));
            }
        }
        return result;
    }

    public static List<String> readDettaglioAnagraficheLabels(WebDriver driver, WebElement container, String tagContainingLabels, List<String> selectIds, TMess tmess) {
        List<String> result = new ArrayList<String>();
        List<WebElement> tagConts = container.findElements(By.tagName(tagContainingLabels));
        for (WebElement cont : tagConts) {
            List<WebElement> toadd = cont.findElements(By.tagName("label"));
            if (toadd.size() > 1) {
                tmess.warning("Sono presenti più labels per lo stesso campo");
            }
            if ((toadd.size() == 1) && (toadd.get(0).isDisplayed())) {
                String sadd = toadd.get(0).getText();
                result.add(sadd);
            }
        }
        return result;
    }

    public static void cleanDettaglioAnagraficheLabels(WebDriver driver, WebElement container, String tagContainingFields, List<String> selectIds, TMess tmess) {
        List<WebElement> tagConts = container.findElements(By.tagName(tagContainingFields));
        for (WebElement cont : tagConts) {
            cleanVisibileFieldFromContainer(cont, selectIds, WebElMap.AP_PRAT_ANAGRAFICA_DETTAGLIO_SELECTS_DEFAULT_OPTIONS, tmess);
        }
    }
    //TO DO rinominare

    static public List<String> SearchIdPratica(List<String> idProfilo, List<List<String>> Anagrafica, String name) {
        List<Integer> row = new ArrayList<Integer>();
        List<String> result = new ArrayList<String>();
        for (Integer i = 0; i.intValue() < Anagrafica.size(); i++) {
            for (String s : Anagrafica.get(i)) {
                if (s.equals(name)) {
                    row.add(i);
                    break;
                }
            }
        }
        for (Integer b : row) {
            result.add(idProfilo.get(b));

        }
        return result;
    }
    
    static public void selectVisibleTagAndPressButton(WebDriver driver, WebElement tagContainer, By byButtonToPress, String tagName, List<Integer> noClick, By emptySpaceToClick){
        List<WebElement> tags = tagContainer.findElements(By.tagName(tagName));
        for(Integer i =0; i< tags.size(); i++){
            if((tags.get(i).isDisplayed())&&(!noClick.contains(i))){
                tags.get(i).click();
                WebElement button = driver.findElement(byButtonToPress);
                button.click();
                tags.get(0).click();
            }
        }
    }
    
    
    static public Boolean CheckSubstring(String parola, String index, Boolean intern) {
        if (intern) {
            if (parola.indexOf(index) != -1) {
                return true;
            } else {
                return false;
            }
        } else {

            if (parola.indexOf(index) == 0) {
                return true;
            } else {
                return false;
            }
        }

    }
    
    static public List<String> changeListToString(List<WebElement> lista){
        List<String> result = new ArrayList<String>();
        for(WebElement l : lista){
            result.add(l.getText());
        }
        return result;
    }
    
    static public void checkWindow(WebDriver driver, String val, TMess tmess){
        WebElement tableButton = safeFind(driver, By.className("ui-dialog-buttonpane"), tmess);
        List<WebElement> buttons =  tableButton.findElements(By.tagName("button"));
        Boolean is = false;
        for(WebElement b : buttons){
            if(b.getText().equals(val)){
                b.click();
                is = true;
            }
        }
        if(!is){
            tmess.warning("Il tasto non esiste in questa finestra!");
        }
    }
    
        public static Boolean findAnagraficaConfermata(WebElement container, String cssClassConfermata, TMess tmess){
        List<WebElement> confermate = container.findElements(By.tagName("div"));
          tmess.azione("container" +  container.getTagName() + container.toString());
        if(confermate.size()>0)
        { 
            if(confermate.get(0).getAttribute("title").equals(cssClassConfermata)){ 
            return true;
            }else 
                return false;
        }
        else{
            return false;
        }
    }
    
    
}
