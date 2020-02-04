/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.selenium;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author GabrieleM
 */
public class TMess {

    static void azione(List<String> pratica) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    String logFile = "";
    String resultFile = "";
    PrintStream outLog;
    PrintStream outResult;
    PrintStream def;

    TMess() throws FileNotFoundException {
        String s = "ST_Log";
        def = System.out;
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date date = new Date();
        String data = dateFormat.format(date);
        logFile = s + data + ".html";
        outLog = new PrintStream(new FileOutputStream(logFile));
        String t = "SeleniumTestSummary";
        DateFormat date1Format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date date1 = new Date();
        String data1 = date1Format.format(date1);
        resultFile = t + data1 + ".html";
        outResult = new PrintStream(new FileOutputStream(resultFile));
    }

    public void SetSeleniumOutStreamLog() throws FileNotFoundException {
        System.setOut(outLog);
    }

//    private void SetSeleniumOutStreamTestSummary() throws FileNotFoundException {
//        System.setOut(outResult);
//    }
    
    public void SetSeleniumDefault() throws FileNotFoundException {
        System.setOut(def);
    } 

    public void Errori(String message) {
        System.setOut(outLog);
        System.out.println("<center><p>**************************************</p>");
        System.out.println("<p>ERRORE---> " + message + "</p>");
        System.out.println("<p>************************************************</p><center>");
        System.setOut(def);
    }

    public void DatiIngresso(String message) {
        System.setOut(outLog);
        System.out.println("<p>DATI DI INGRESSO---> " + message + "</p>");
        System.setOut(def);
    }

    public void azione(String message) {
        System.setOut(outLog);
        System.out.println("<p>---->" + message + "</p>");
        System.setOut(def);
    }

    public void risultati(String message) {
        System.setOut(outLog);
        System.out.println("<center><p>******************************************************************************************</p>");
        System.out.println("<p>RISULTATO OTTENUTO---> " + message + "</p>");
        System.out.println("<p>******************************************************************************************</p></center>");
        System.setOut(def);
    }

    public void Generico(String message) {
        System.setOut(outLog);
        System.out.println("<p>******************************************************************************************</p>");
        System.out.println("<p>" + message + "</p>");
        System.out.println("<p>******************************************************************************************</p>");
        System.setOut(def);
    }

    public void NuovoTest(String testName, String famiglia) {
        System.setOut(outLog);
        System.out.println("<p>                                                                                          </p>");
        System.out.println("<center><p>===================================================================================================</p>");
        System.out.println("<h1>INIZIO NUOVO TEST: " + testName + " in " + famiglia + "</h1>");
        System.out.println("<p>===================================================================================================</p></center>");
        System.out.println("<p>                                                                                        </p> ");
        System.setOut(def);
    }

    public void NuovaFamigliaTest(String famiglia) {
        System.setOut(outLog);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        String data = dateFormat.format(date);
        System.out.println("<center><p>                                                                                         </p>");
        System.out.println(" <p>                                                                                         </p>");
        System.out.println("<p>******************************************************************************************</p>");
        System.out.println("<p>##########################################################################################</p>");
        System.out.println("<center><h1>FAMIGLIA DI TEST: " + famiglia + " IN DATA: " + data + "</h1></center>");
        System.out.println("<p>******************************************************************************************</p>");
        System.out.println("<p>******************************************************************************************</p>");
        System.out.println("<p>                                                                                          </p>");
        System.out.println("<p>                                                                                          </p></center>");
        System.setOut(def);
    }

    public void conclusione(String testName, String famiglia, Boolean esito) throws FileNotFoundException {
        System.setOut(outLog);
        System.out.println("<center><p>===================================================================================================</p>");
        if (esito) {
            System.out.println("<h1>TEST " + testName + " DELLA FAMIGLIA " + famiglia + " <BR> SUPERATO </h1>");
        } else {
            System.out.println("<h1>TEST " + testName + " DELLA FAMIGLIA " + famiglia + " <BR> FALLITO </h1>");
        }
        System.out.println("<p>===================================================================================================</p></center>");
        System.setOut(def);
        System.out.println("<p>===================================================================================================</p>");
        if (esito) {
            System.out.println("<h1>TEST " + testName + " DELLA FAMIGLIA " + famiglia + " <BR> SUPERATO </h1>");
        } else {
            System.out.println("<h1>TEST " + testName + " DELLA FAMIGLIA " + famiglia + " <BR> FALLITO </h1>");
        }
        System.out.println("<p>===================================================================================================</p></center>");      
        System.setOut(def);
    }

    public void warning(String messaggio) {
        System.setOut(outLog);
        System.out.println("<p>WARNING: " + messaggio + "</p>");
        System.setOut(def);
    }

    public void stampaTabellaDueRighe(List<List<String>> tabella) {
        System.setOut(outLog);
        Boolean correct = true;
        if (tabella.size() < 2) {
            for (Integer i = 0; i < tabella.get(0).size(); i++) {
                System.out.println(tabella.get(0).get(i));
            }
            for (Integer i = 0; i < tabella.get(0).size(); i++) {
                System.out.println(tabella.get(1).get(i));
            }
            warning("la tabella non ha 2 righe.");
            correct = false;
        }
        if (tabella.get(0).size() != tabella.get(1).size()) {
            for (Integer i = 0; i < tabella.get(0).size(); i++) {
                System.out.println("<p>" + tabella.get(0).get(i) + "</p>");
            }
            for (Integer i = 0; i < tabella.get(0).size(); i++) {
                System.out.println("<p>" + tabella.get(1).get(i) + "</p>");
            }
            warning("non e' una tabella: le righe hanno dimensioni diverse.");
            correct = false;
        }
        if (correct) {
            System.out.println("<tr>");
            for (Integer i = 0; i < tabella.get(0).size(); i++) {
                System.out.println("<td>" + tabella.get(0).get(i) + "</td>");
                System.out.println("<td>" + tabella.get(1).get(i) + "</td>");
            }
            System.out.println("</tr>");
        }
        System.setOut(def);
    }

    public void printTwoListAsTable(List<String> labels, List<String> values) {
        System.setOut(outLog);
        System.out.println("<table border='1'>");
        
        System.out.println("<tr>");
        for (String label : labels) {
            System.out.print("<th>");
            System.out.print(label);
            System.out.print("</th>");

        }
        System.out.println("</tr>");
        
        System.out.println("<tr>");
        for (String value : values) {
            System.out.print("<td>");
            System.out.print(value);
            System.out.print("</td>");

        }
        System.out.println("</tr>");

        System.out.println("</table>");
        System.setOut(def);

    }
}
