/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 *
 * @author GabrieleM
 * La classe aggiunge dei metodi che permettono di gestire il pulsante indietro anche nella situazione in cui alla stessa pagina si accede da diverse altre pagine
 * anche se c'è un reindirizzamento prima della restituzione della pagina.
 * Nella request di una delle pagina di partenza (non quella di default) aggiungere il parametro indietroLink valorizzato con il nome del mapping corrente sostituendo / con _.
 * Aggiungere nella mappaController la stringa descritta prima come chiave e la stringa vera del requestMapping come valore.
 * Aggiungere nella mappaIndietroDefaults la requestMapping della pagina a cui si può accedere in modi diversi come chiave e come valore la requestMapping che si vuole mettere di default per l'indietro.
 * Nel metodo con requestMapping quella a cui si accede in modi diversi usare il metodo inizializeIndietro subito prima di restituire la view.
 * Nel  caso si arrivi alla pagina a cui si accede da punti diversi con un redirect è necessario aggiungere il metodo setSessionValues nel controller in cui si fa il redirect per salvare i valori dalla request alla sessione.
 * Ogni volta che il metodo insizializeIndietro viene invocato si cancellano i valori di sessione. Per un corretto funzionamento è quindi necessario invocare questo metodo nel controller a cui si viene reindirizzati.
 * Per sfruttare la funzionalità è necessario aggiungere nella views.xml relativa alla view della pagina a cui si accede in modi diversi Indietro.jsp con nome indietro.
 * Quindi aggiungere il tiles indietro nella jsp.
 */
@Component
public class IndietroAction {

    private static final Map<String, String> mappaController = new HashMap<String, String>() {
        {
            put("scadenziario", "/pratiche/scadenzario");
            put("pratiche_nuove_apertura_dettaglio", "/pratiche/nuove/apertura/dettaglio");
            put("pratiche_nuove_apertura", "/pratiche/nuove/apertura");
        }
    };
    private static final Map<String, String> mappaIndietroDefaults = new HashMap<String, String>() {
        {
            put("/pratiche/nuove/apertura/dettaglio", "/pratiche/nuove/apertura");
             put("/pratiche/dettaglio", "/pratiche/gestisci");
        }
    };
    String sessionLinkIndietro = "";

    public void inizializeIndietro(Model model, HttpServletRequest request, String controller) {
        String key = request.getParameter("linkIndietro");
        String linkIndietro = mappaController.get(key);
        if ((!("".equals(linkIndietro))) && (linkIndietro != null)) {
            model.addAttribute("linkIndietro", linkIndietro);
        } else {
            inizializeIndietroFromSession(model, controller);
        }
        sessionLinkIndietro = "";
    }

    //Chiamare prima di un redirect, nel controller a cui si è reindirizzati si deve poi chiamare InizializeIndietro
    public void setSessionValues(HttpServletRequest request) {
        String key = request.getParameter("linkIndietro");
        String linkIndietro = mappaController.get(key);
        sessionLinkIndietro = linkIndietro;
    }

    private void inizializeIndietroFromSession(Model model, String controller) {
        if (!"".equals(sessionLinkIndietro) && sessionLinkIndietro != null) {
            model.addAttribute("linkIndietro", sessionLinkIndietro);
        } else {
            model.addAttribute("linkIndietro", mappaIndietroDefaults.get(controller));
        }
    }
}
