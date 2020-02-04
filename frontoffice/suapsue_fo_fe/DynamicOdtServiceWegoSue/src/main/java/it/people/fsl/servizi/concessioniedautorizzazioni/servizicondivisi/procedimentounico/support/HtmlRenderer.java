/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 * 
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.support;

import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.BaseBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.HrefCampiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.steps.BaseStep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe che crea il codice html delle dichiarazioni dinamiche
 *
 * @author InIT http://www.gruppoinit.it
 *
 * 16-giu-2006
 */
public class HtmlRenderer extends BaseStep {
    
    private static Log log = LogFactory.getLog(HtmlRenderer.class);
    
    public static String costruisciStringaHtml(SezioneCompilabileBean compilabileBean, boolean modalitaEdit, String styleTabella, String styleTitolo, String width, String border, String cellpadding, String cellspacing, String dimensioneText) {
        return costruisciStringaHtml(compilabileBean, modalitaEdit, styleTabella, styleTitolo, width, border, cellpadding, cellspacing, dimensioneText, 4, null);
    }
    
    private static String ultimoCampoPerPrecompilazione = null;
    private static String campoRicerca = "  <i>(Campo Ricerca)</i>";
    private static HashMap hmTipiRagruppamento;
        
    public static String costruisciStringaHtml(SezioneCompilabileBean compilabileBean, boolean modalitaEdit, String styleTabella, String styleTitolo, String width, String border, String cellpadding, String cellspacing, String dimensioneText, int livelloCampiMultili, ProcessData dataForm) {
        // SezioneCompilabileBean compilabileBean = new
        // SezioneCompilabileBean();

        // Default costanti per Tabella
        if (width == null)
            width = "100%";
        if (border == null)
            border = "1";
        if (cellpadding == null)
            cellpadding = "2";
        if (cellspacing == null)
            cellspacing = "0";
        if (dimensioneText == null)
            dimensioneText = "8";

        int numColonne = compilabileBean.getTdCount();
        int numRighe = compilabileBean.getRowCount();
        int recordAttuale = 0;
        
        String htmlString = "";
        
        hmTipiRagruppamento = new HashMap();
        
        // Inizio
        htmlString = "<table width=\"" + width + "\" cellpadding=\""
                            + cellpadding + "\" cellspacing=\"" + cellspacing
                            + "\"><tr><td><b>" + compilabileBean.getDescrizione()
                            + "</b></td></tr></table>";
        htmlString += "<table width=\"" + width + "\" border=\"" + border
                      + "\" cellpadding=\"" + cellpadding + "\" cellspacing=\""
                      + cellspacing + "\">";

        java.util.ArrayList campi = (java.util.ArrayList) compilabileBean.getCampi();
        HrefCampiBean rigaDati = null;
        if (campi.size() > 0)
            rigaDati = (HrefCampiBean) campi.get(recordAttuale);
        boolean inseritoTastoAccedi = false;
        
        for (int i = 1; i < numRighe + 1; i++) {
            htmlString = apriTR(htmlString);
            int maxColonnePerRiga = maxColonnaPerRiga(campi, i, numColonne);
            for (int n = 1; n < maxColonnePerRiga + 1; n++) {
                if (n == maxColonnePerRiga && maxColonnePerRiga < numColonne)
                    htmlString = apriTD(htmlString, ""
                                                    + (numColonne
                                                       - maxColonnePerRiga + 1), i, rigaDati.getTipo(), modalitaEdit);
                else
                    htmlString = apriTD(htmlString, null, i, rigaDati.getTipo(), modalitaEdit);
                if (rigaDati.getRiga() == i && rigaDati.getPosizione() == n) {
                    // Controllo se deve apparire il pulsante "Accedi" (viene
                    // messo al pi? una volta): se si passo il codice dell'href,
                    // altrimenti passo null (o "" se ? chiave ma l'avevo gi?
                    // messo)
                    String hrefAccedi = null;
                    ultimoCampoPerPrecompilazione = getUltimoCampoPerPrecompilazione(compilabileBean);
                    if(ultimoCampoPerPrecompilazione != null && rigaDati.getCampo_key() != null && rigaDati.getNome().equalsIgnoreCase(ultimoCampoPerPrecompilazione)){
                        hrefAccedi = compilabileBean.getHref();
                    }
                    else if(ultimoCampoPerPrecompilazione != null && rigaDati.getCampo_key() != null){
                        hrefAccedi = "";
                    }
                    
                    /*String hrefAccedi = rigaDati.getCampo_key() != null
                                        && !inseritoTastoAccedi ? compilabileBean.getHref() : (rigaDati.getCampo_key() != null ? "" : null);
                    if (hrefAccedi != null) {
                        inseritoTastoAccedi = true;
                    }*/
                    
                    htmlString = inserisciCampo(htmlString, rigaDati.getTipo(), rigaDati.getNome(), "".equalsIgnoreCase(rigaDati.getValoreUtente()) ? null : rigaDati.getValoreUtente(), rigaDati.getValore(), dimensioneText, rigaDati.getDescrizione(), modalitaEdit, "O".equalsIgnoreCase(rigaDati.getControllo()) ? true : false, (ArrayList) rigaDati.getOpzioniCombo(), hrefAccedi, rigaDati.getNumCampo(), compilabileBean.getHref(), i, rigaDati.getTp_controllo(), rigaDati.getLunghezza(), rigaDati.getDecimali(), livelloCampiMultili, rigaDati.getEdit(), rigaDati.getRaggruppamento_check());
                    recordAttuale++;
                    if (recordAttuale < campi.size())
                        rigaDati = (HrefCampiBean) campi.get(recordAttuale);
                } else {
                    htmlString = inserisciCampoVuoto(htmlString);
                }
                htmlString = chiudiTD(htmlString, rigaDati.getTipo(), modalitaEdit);
            }
            htmlString = chiudiTR(htmlString);
            
            // Test (da mettere in caso di problemi con la paginazione dei campi multipli)
            //htmlString += "</table>";
            //htmlString += "<table width=\"" + width + "\" border=\"" + border
            //+ "\" cellpadding=\"" + cellpadding + "\" cellspacing=\""
            //+ cellspacing + "\">";
            // Fine test
           
        }
        htmlString += "</table>" + "<table><tr><td>"
                      + compilabileBean.getPiedeHref()
                      + "</td></tr></table><br/>";
        ;

        return htmlString;
    }
    
    private static String getUltimoCampoPerPrecompilazione(SezioneCompilabileBean compilabileBean){
        List campiBean = compilabileBean.getCampi();
        String retVal = null;
        Iterator it = campiBean.iterator();
        while(it.hasNext()){
            HrefCampiBean hrcb = (HrefCampiBean) it.next();
            if(hrcb.getCampo_key() != null){
                retVal = hrcb.getNome();
            }
        }
        return retVal;
    }

    private static int maxColonnaPerRiga(java.util.ArrayList campi, int riga, int numMaxColonne) {
        int maxColonnaTemp = 0;
        for (int i = 0; i < campi.size(); i++) {
            HrefCampiBean rigaDati = (HrefCampiBean) campi.get(i);
            if (rigaDati.getRiga() == riga
                && rigaDati.getPosizione() > maxColonnaTemp)
                maxColonnaTemp = rigaDati.getPosizione();
        }
        return maxColonnaTemp;
    }

    private static String inserisciCampo(String htmlString, String tipo, String nome, String valoreUtente, String valore, String dimensione, String desc, boolean modalitaEdit, boolean obbligatorio, ArrayList opzioniCombo, String hrefAccedi, int molteplicitaCampo, String href, int i, String tp_controllo, int lunghezza, int decimali, int livelloCampiMultili, String edit, String raggruppamento_check) {
        if (tipo.equalsIgnoreCase("R")) {
            return htmlString = inserisciRadioButton(htmlString, nome, valoreUtente, valore, desc, modalitaEdit, obbligatorio, i, molteplicitaCampo, href, livelloCampiMultili, hrefAccedi);
        } else if (tipo.equalsIgnoreCase("I")) {
            return htmlString = inserisciText(htmlString, nome, valoreUtente, valore != null ? valore : "", dimensione, desc, modalitaEdit, obbligatorio, hrefAccedi, molteplicitaCampo, href, i, tp_controllo, lunghezza, decimali, livelloCampiMultili, edit);
        } else if (tipo.equalsIgnoreCase("C")) {
            return htmlString = inserisciCheckbox(htmlString, nome, valoreUtente, valore, desc, modalitaEdit, obbligatorio, i, molteplicitaCampo, href, livelloCampiMultili, hrefAccedi, raggruppamento_check);
        } else if (tipo.equalsIgnoreCase("L")) {
            return htmlString = inserisciCombobox(htmlString, nome, valoreUtente, valore, desc, modalitaEdit, obbligatorio, opzioniCombo, molteplicitaCampo, href, livelloCampiMultili, hrefAccedi);
        } else if (tipo.equalsIgnoreCase("T")) {
            return htmlString = inserisciCommento(htmlString, desc);
        }else if (tipo.equalsIgnoreCase("N")) {
            return htmlString = inserisciTextNascosto(htmlString, nome, valoreUtente, valore != null ? valore : "", dimensione, desc, modalitaEdit, obbligatorio, hrefAccedi, molteplicitaCampo, href, i, tp_controllo, lunghezza, decimali, livelloCampiMultili, edit);
        }
        // Todo: mettere altri tipi di oggetti
        else
            return inserisciCampoVuoto(htmlString);
    }

    private static String apriTD(String htmlString, String colspan, int i, String tipo, boolean modalitaEdit) {
        String retString = htmlString;
        String aggiuntaColSpan = "";
        String apriLabelFor = "";
        if (colspan != null) {
            aggiuntaColSpan = " colspan=" + colspan;
        }
        if (modalitaEdit) {

            if (tipo.equalsIgnoreCase("C")) {
                apriLabelFor = "<label for='checkbox_" + i + "'>";
            }
            else if (tipo.equalsIgnoreCase("R")) {
                apriLabelFor = "<label for='radiobutton_" + i + "'>";
            }
            else if (tipo.equalsIgnoreCase("I")) {
                apriLabelFor = "<label for='text_" + i + "'>";
            }
        } else {
            if (tipo.equalsIgnoreCase("C")) {
                apriLabelFor = "<label for='checkbox_" + i + "'>";
            }
            else if(tipo.equalsIgnoreCase("R")) {
                apriLabelFor = "<label for='radiobutton_" + i + "'>";
            }
        }

        retString += "<td " + aggiuntaColSpan + ">" + apriLabelFor;

        return retString;
    }

    private static String chiudiTD(String htmlString, String tipo, boolean modalitaEdit) {
        String retString = htmlString;
        String chiudiLabelFor = "";
        if (modalitaEdit) {

            if (tipo.equalsIgnoreCase("C") || tipo.equalsIgnoreCase("R") || tipo.equalsIgnoreCase("I")) {
                chiudiLabelFor = "</label>";
            }
     
        } else {
            if (tipo.equalsIgnoreCase("C") || tipo.equalsIgnoreCase("R")){
                chiudiLabelFor = "</label>";
            }
        }

        retString += chiudiLabelFor + "</td>";

        return retString;
    }

    private static String apriTR(String htmlString) {
        return htmlString += "<tr>";
    }

    private static String chiudiTR(String htmlString) {
        return htmlString += "</tr>";
    }

    private static String inserisciCampoVuoto(String htmlString) {
        return htmlString += "&nbsp;";
    }

    private static String inserisciRadioButton(String htmlString, String nome, String valoreUtente, String valore, String desc, boolean modalitaEdit, boolean obbligatorio, int index, int molteplicitaCampo, String href, int livelloCampiMultili, String hrefAccedi) {
        if (molteplicitaCampo > 0) {
            List valoriUtente = parseValoriCampiMultipli(valoreUtente, livelloCampiMultili);
            String idCampo = "";
            int numCampi = valoriUtente.size();
            if(modalitaEdit){
                for (int i = 0; i < numCampi; i++) {
                    idCampo = (i == 0 ? nome : (nome + "#" + i));
                    htmlString += (i==0?(desc + (obbligatorio?"(*)<br/>":"<br/>")):"")
                                + "<input id='"+ idCampo +"' type=\"radio\" name=\"" + idCampo + "\""
                                + " value=\"" + valore + "\" "
                                + (modalitaEdit ? "" : " disabled ")
                                + (((""+(valoriUtente.size() > i ? valoriUtente.get(i) : null)).equals("null") || (""+(valoriUtente.size() > i ? valoriUtente.get(i) : null)).equals("")) ? "" : "checked") + ">" 
                                + (i == (numCampi - 1) ? "<br/>"
                                + inserisciTastoAggiungi(href, htmlString)
                                + (i != 0 ? inserisciTastoTogli(href, idCampo, htmlString) : "") : "")
                                + "<br/>";   
                }
            }
            else{
                for (int i = 0; i <= numCampi; i++) {
                    //piergiorgio
                    //htmlString += (i==0?"<b>"+desc+"</b>"+"<br/>":"")
                    //            + (((""+(valoriUtente.size() > i ? valoriUtente.get(i) : null)).equals("null") || (""+(valoriUtente.size() > i ? valoriUtente.get(i) : null)).equals("")) ? "" : "[ x ]")
                    //            + "<br/>";
                    htmlString += (i==0?"<b>"+desc+"</b>"+"<br/>":"")
                                + "[   ]"
                                + "<br/>";

                }
            }
            return htmlString;
        }
        else{
            return htmlString += "<input id='radiobutton_"+ index +"' type=\"radio\" name=\"" + nome
                                + "\" value=\"" + valore + "\" "
                                + (modalitaEdit ? "" : "disabled ")
                                + ((valoreUtente == null || valoreUtente.equals("&nbsp;")) ? "" : "checked")
                                + ">&nbsp;" + desc;/* + (obbligatorio?"(*)":"")
                                + (hrefAccedi != null ? (hrefAccedi.equalsIgnoreCase("") ? campoRicerca : " "
                                + inserisciTastoAccedi(hrefAccedi)) : "");*/
        }
    }

    private static String inserisciCombobox(String htmlString, String nome, String valoreUtente, String valore, String desc, boolean modalitaEdit, boolean obbligatorio, ArrayList opzioniCombo, int molteplicitaCampo, String href, int livelloCampiMultili, String hrefAccedi) {
        String combo = "";
        if (modalitaEdit) {
            if (molteplicitaCampo > 0) {
                List valoriUtente = parseValoriCampiMultipli(valoreUtente, livelloCampiMultili);
                String idCampo = "";
                int numCampi = valoriUtente.size();
                for (int i = 0; i < numCampi; i++) {
                    String valUtenteAttuale = (valoriUtente.size() > i ? (String)valoriUtente.get(i) : "");
                    
                    idCampo = (i == 0 ? nome : (nome + "#" + i));
                    combo += (i==0?(desc + (obbligatorio?"(*)<br/>":"<br/>")):"") 
                            + "<select name=\"" + idCampo + "\""
                            + (modalitaEdit ? " " : " disabled ") + ">";
                    combo += "<option VALUE=\"\""
                            + (valUtenteAttuale == null
                                || valUtenteAttuale.equalsIgnoreCase("") ? " selected " : "")
                            + ">";
                    Iterator it = opzioniCombo.iterator();
                    while (it.hasNext()) {
                        BaseBean bean = (BaseBean) it.next();
                        combo += "<option VALUE=\""
                                + bean.getCodice()
                                + "\""
                                + (bean.getCodice().equalsIgnoreCase(valUtenteAttuale) ? " selected " : "")
                                + ">" + bean.getDescrizione();
                    }
                    combo += "</select>";
                    combo +=  (i == (numCampi - 1) ? "<br/>"
                            + inserisciTastoAggiungi(href, htmlString)
                            + (i != 0 ? inserisciTastoTogli(href, idCampo, htmlString) : "") : "")
                            + "<br/>";   
                }
            }
            else{
                combo += desc + "<br/><select name=\"" + nome + "\""
                        + (modalitaEdit ? " " : " disabled ") + ">";
                combo += "<option VALUE=\"\""
                        + (valoreUtente == null
                            || valoreUtente.equalsIgnoreCase("") ? " selected " : "")
                        + ">";
                Iterator it = opzioniCombo.iterator();
                while (it.hasNext()) {
                    BaseBean bean = (BaseBean) it.next();
                    combo += "<option VALUE=\""
                            + bean.getCodice()
                            + "\""
                            + (bean.getCodice().equalsIgnoreCase(valoreUtente) ? " selected " : "")
                            + ">" + bean.getDescrizione();
                }
                combo += "</select>" + (obbligatorio?"(*)":"");
                combo += (hrefAccedi != null ? (hrefAccedi.equalsIgnoreCase("") ? campoRicerca : " "
                    + inserisciTastoAccedi(hrefAccedi)) : "");
            }
        } else {
            
            log.info("molteplicitaCampo "+molteplicitaCampo+" |"+valoreUtente+"|");
            
            if(molteplicitaCampo>0){
                List valoriUtente = parseValoriCampiMultipli(valoreUtente);
                int numCampi = valoriUtente.size();
                for (int i = 0; i < numCampi; i++) {
                    /*String descComboSel = "";
                    Iterator it = opzioniCombo.iterator();
                    while (it.hasNext()) {
                        BaseBean bean = (BaseBean) it.next();
                        if (bean.getCodice().equalsIgnoreCase((String)valoriUtente.get(i))) {
                            descComboSel = bean.getDescrizione();
                            break;
                        }
                    }
                    
                    log.info("molteplicitaCampo > 0 |"+desc+"| |"+descComboSel+"|");
                    
                    htmlString += (i==0?("<b>"+desc+"</b><br/>"):"");
                    if(descComboSel!=null && !descComboSel.equals("")){
                        htmlString +=descComboSel + "<br/>";
                    }*/
                    
                    String valUtenteAttuale=(String)valoriUtente.get(i);
                    
                    htmlString += (i==0?("<b>"+desc+"</b><br/>"):"");
                    
                    /*htmlString   += "<input id='checkbox_"+ i +"' type=\"checkbox\" name=\"" + 
                                "\" value=\"" + valUtenteAttuale + "\" " + 
                                (modalitaEdit ? "" : "disabled ") +                           
                                ">&nbsp;" + valUtenteAttuale+"<br/>";*/
                    
                    htmlString   += "[&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;]&nbsp;" + valUtenteAttuale+"<br/>";
                    
                    log.info("Aggiungo combo "+htmlString);
                    
                    
                }
            }
            else{
                
                log.debug("mydesc "+desc+" valoreUtente |"+valoreUtente+"|");
                
                if (valoreUtente == null || "".equalsIgnoreCase(valoreUtente))
                    return htmlString += desc + "&nbsp;__________ ?"
                                         + (obbligatorio ? "(*)" : "");
                else {
                  
                    
                     htmlString += desc + ":&nbsp;&nbsp;&nbsp;<b>";
                     
                     List valoriUtente = parseValoriCampiMultipli(valoreUtente);
                    int numCampi = valoriUtente.size();
                    for (int i = 0; i < numCampi; i++) {
                    
                        String valUtenteAttuale=(String)valoriUtente.get(i);
                        htmlString   += "[&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;]&nbsp;" + valUtenteAttuale;
                    }
                    
                    htmlString += "</b>";
                    
                    log.info("Aggiungo combo! "+htmlString);
                    
                    return htmlString;
                    
                    
                    
                    
                }
            }
        }
        return htmlString + combo;
    }

    private static String inserisciCheckbox(String htmlString, String nome, String valoreUtente, String valore, String desc, boolean modalitaEdit, boolean obbligatorio, int index, int molteplicitaCampo, String href, int livelloCampiMultili, String hrefAccedi, String raggruppamento_check) {
        if (molteplicitaCampo > 0) {
            List valoriUtente = parseValoriCampiMultipli(valoreUtente, livelloCampiMultili);
            if(modalitaEdit){
                String idCampo = "";
                int numCampi = valoriUtente.size();
                for (int i = 0; i < numCampi; i++) {
                    idCampo = (i == 0 ? nome : (nome + "#" + i));
                    htmlString += (i==0?(desc + (obbligatorio?"(*)<br/>":"<br/>")):"")
                                + "<input id='"+ idCampo +"' type=\"checkbox\" name=\"" + idCampo + "\""
                                + " value=\"" + valore + "\" "
                                + (modalitaEdit ? "" : "disabled ")
                                + (((""+(valoriUtente.size() > i ? valoriUtente.get(i) : null)).equals("null") || (""+(valoriUtente.size() > i ? valoriUtente.get(i) : null)).equals("")) ? "" : "checked")
                                + ">&nbsp;" 
                                + (i == (numCampi - 1) ? "<br/>"
                                + inserisciTastoAggiungi(href, htmlString)
                                + (i != 0 ? inserisciTastoTogli(href, idCampo, htmlString) : "") : "")
                                + "<br/>";   
                }
            }
            else{
                int numCampi = valoriUtente.size();
                for (int i = 0; i <= numCampi; i++) {
                    //piergiorgio
                    //htmlString += (i==0?"<b>"+desc+"</b>"+"<br/>":"")
                    //            + (((""+(valoriUtente.size() > i ? valoriUtente.get(i) : null)).equals("null") || (""+(valoriUtente.size() > i ? valoriUtente.get(i) : null)).equals("")) ? "" : "[ x ]")
                    //            + "<br/>";
                    htmlString += (i==0?"<b>"+desc+"</b>"+"<br/>":"")
                                + "[   ]"
                                + "<br/>";
                }
            }
            return htmlString;
        }
        else{
            String asterisco = "(*)";
            String[] asteriscoRagruppamento = new String[4];
            asteriscoRagruppamento[0] = "<span style=\"color:blue\">(*)</span>";
            asteriscoRagruppamento[1] = "<span style=\"color:orange\">(*)</span>";
            asteriscoRagruppamento[2] = "<span style=\"color:red\">(*)</span>";
            asteriscoRagruppamento[3] = "<span style=\"color:green\">(*)</span>";
            String opzioneAsteriscoAttivata = "";
            
            if(raggruppamento_check == null || raggruppamento_check.equalsIgnoreCase("")){
                opzioneAsteriscoAttivata = asterisco;
            }
            else{
                hmTipiRagruppamento.put(raggruppamento_check, "Y");
                opzioneAsteriscoAttivata = asteriscoRagruppamento[(hmTipiRagruppamento.size()-1)<4 ? (hmTipiRagruppamento.size()-1) : 3];
            }
            
            return htmlString += "<input id='checkbox_"+ index +"' type=\"checkbox\" name=\"" + nome
                                + "\" value=\"" + valore + "\" "
                                + (modalitaEdit ? "" : "disabled ")
                                + ((valoreUtente == null || valoreUtente.equals("&nbsp;")) ? "" : "checked")
                                + ">&nbsp;" + desc;/* + (obbligatorio ? opzioneAsteriscoAttivata : "");
                                /*+ (hrefAccedi != null ? (hrefAccedi.equalsIgnoreCase("") ? campoRicerca : " "
                                + inserisciTastoAccedi(hrefAccedi)) : "");*/
        }
    }

    private static String inserisciText(String htmlString, String nome, String valoreUtente, String valore, String dimensione, String desc, boolean modalitaEdit, boolean obbligatorio, String hrefAccedi, int molteplicitaCampo, String href, int index, String tp_controllo, int lunghezza, int decimali, int livelloCampiMultili, String edit) {
        valoreUtente = valoreUtente == null ? "" : valoreUtente;
        if (!modalitaEdit) {
            if (molteplicitaCampo > 0) {
//                if ("".equalsIgnoreCase(valoreUtente)) {
//                    return htmlString += desc + "&nbsp;__________"
//                                         + (obbligatorio ? "(*)" : "");
//                } 
//                else{
                
                log.info("valoreUtente "+valoreUtente);
                
                List valori = parseValoriCampiMultipli(valoreUtente);
                int numCampi = valori.size();
                for (int i = 0; i < numCampi; i++) {
                    
                    log.info("valori.get(i) "+valori.get(i));
                    
                    htmlString += (i==0?"<b>"+desc+"</b><br/>":"")
                                  + (valori.size() > i ? valori.get(i) : "")
                                  + "<br/>";
                }
                return htmlString;
//                }
            } else {
                if ("".equalsIgnoreCase(valoreUtente))
                    return htmlString += desc + "&nbsp;__________"
                                         + (obbligatorio ? "(*)" : "");
                else
                    return htmlString += desc + ":&nbsp;&nbsp;&nbsp;<b>"
                                         + valoreUtente + "</b>";
            }
        } else {
            int cifreIntere = 0;
            int cifreDecimali = 0;
            String onKeyPressString = "";
            String onChangeString = "";
            String onBlurString = "";
            String idCampo = "";
            boolean insCalendario = false;
            boolean insPIVA = false;
            boolean insCodFisc = false;
            // Campo testo
            if(tp_controllo.equals("T")){
                /*
                 * Modifica Init 23/02/2007 (versione 1.2.2): 
                 * nel caso di campi testo ? stato aggiunta la chiamata al metodo javascript onlyLettersAndNumeric ad ogni pressione di tasti.
                 * Questo metodo permette di controllare se il carattere digitato ? valido (lettera o numero)
                 */
                onKeyPressString = "onkeypress=\"return onlyLettersAndNumeric(event)\"";
            }
            // Campo numerico
            else if(tp_controllo.equals("N")){
                cifreIntere = lunghezza-decimali;
                cifreDecimali = decimali;
                onKeyPressString = "onkeypress=\"return onlyNumeric(event)\"";
            }
            // Campo data
            else if(tp_controllo.equals("D")){
                insCalendario = true;
                /*
                 * Modifica Init 19/03/2007 (versione 2.0): 
                 * Aggiunto javascript per controllo sulla validit? della data inputata direttamente dall'utente.
                 * Tolta di conseguenza la non editabilit? del campo
                 */
                onBlurString = "onblur=\"javascript:isValidDate(this,true);\"";
            }
            // Codice fiscale
            else if(tp_controllo.equals("F")){
                insCodFisc = true;
            }
            // Partita IVA
            else if(tp_controllo.equals("I")){
                insPIVA = true;
            }
            
            if (molteplicitaCampo > 0) {
                List valori = parseValoriCampiMultipli(valoreUtente, livelloCampiMultili);
                int numCampi = valori.size();
                for (int i = 0; i < numCampi; i++) {
                    idCampo = (i == 0 ? nome : (nome + "#" + i));
                    if(tp_controllo.equals("N"))
                        onChangeString = "onchange=\"return formatNumber('"+idCampo+"','"+cifreIntere+"','"+cifreDecimali+"')\"";
                    htmlString += (i==0?desc+"<br/>":"")
                                  //+ "&nbsp;"
                                  //+ (i + 1)
                                  // Messo per l'accessibilit?: tolto per problemi con il fuoco sui campi multipli
                                  //+ "&nbsp;<input id='text_"+ index +"' type=\"text\" name=\""
                                  + "<input type=\"text\" name=\""
                                  + idCampo + "\""
                                  + " id=\""
                                  + idCampo
                                  + "\" value=\""
                                  + (valori.size() > i ? valori.get(i) : "")
                                  + "\" size=\""
                                  + (lunghezza>78?80:lunghezza+2) + "\""
                                  + " maxlength=\""
                                  + (cifreDecimali==0?lunghezza:(lunghezza+1)) + "\""
                                  + (modalitaEdit ? "" : "readonly")
                                  + " "+onChangeString+" "+onKeyPressString+" "+onBlurString+">"
                                  + ((obbligatorio && i == 0) ? "(*)" : "")
                                  + (hrefAccedi != null ? (hrefAccedi.equalsIgnoreCase("") ? campoRicerca : " "
                                  + inserisciTastoAccedi(hrefAccedi)) : "")
                                  /*
                                   * Modifica Init 29/06/2007 (1.3.5)
                                   * tolto il tasto per l'apertura del calendario nei campi multipli
                                   */
                                  /*+ (insCalendario?inserisciTastoCalendario(idCampo):"")*/
                                  /*+ (insCodFisc?inserisciTastoControlloCodiceFiscale(idCampo):"")*/
                                  /*+ (insPIVA?inserisciTastoControlloPartitaIVA(idCampo):"")*/
                                  + (i == (numCampi - 1) ? "<br/>"
                                  + inserisciTastoAggiungi(href, htmlString)
                                  + (i != 0 ? inserisciTastoTogli(href, nome, htmlString) : "") : "")
                                  + "<br/>";
                }
            } else {
                idCampo = nome;
                if(tp_controllo.equals("N"))
                    onChangeString = "onchange=\"return formatNumber('"+idCampo+"','"+cifreIntere+"','"+cifreDecimali+"')\"";
                htmlString += desc
                              // Messo per l'accessibilit?: tolto per problemi con il fuoco sui campi multipli
                              //+ "&nbsp;<input id='text_"+ index +"' type=\"text\" name=\""
                              + "&nbsp;<input type=\"text\" name=\""
                              + nome + "\""
                              + " id=\""
                              + idCampo
                              + "\" value=\""
                              + valoreUtente
                              + "\" size=\""
                              + (lunghezza>78?80:lunghezza+2) + "\""
                              + " maxlength=\""
                              + (cifreDecimali==0?lunghezza:(lunghezza+1)) + "\""
                              + ((modalitaEdit && edit.equalsIgnoreCase("s")) ? "" : " readonly ")
                              + " "+onChangeString+" "+onKeyPressString+" "+onBlurString+">"
                              + (obbligatorio ? "(*)" : "")
                              /*+ (hrefAccedi != null ? (hrefAccedi.equalsIgnoreCase("") ? campoRicerca : " "
                              + inserisciTastoAccedi(hrefAccedi)) : "")*/
                              + (hrefAccedi != null ? (hrefAccedi.equalsIgnoreCase("") ? campoRicerca : " "
                              + inserisciTastoAccedi(hrefAccedi)) : "")
                              + (!edit.equalsIgnoreCase("s") ? "" : (insCalendario?inserisciTastoCalendario(idCampo):""))
                              + (insCodFisc?inserisciTastoControlloCodiceFiscale(idCampo):"")
                              + (insPIVA?inserisciTastoControlloPartitaIVA(idCampo):"");
            }
            return htmlString;
        }
    }
    
    private static String inserisciTextNascosto(String htmlString, String nome, String valoreUtente, String valore, String dimensione, String desc, boolean modalitaEdit, boolean obbligatorio, String hrefAccedi, int molteplicitaCampo, String href, int index, String tp_controllo, int lunghezza, int decimali, int livelloCampiMultili, String edit) {
        valoreUtente = valoreUtente == null ? "" : valoreUtente;
        if (!modalitaEdit) {
            return htmlString += " ";
        } else {
            int cifreDecimali = 0;
            String idCampo = "";

            idCampo = nome;
            htmlString += " "
                          + "&nbsp;<input type=\"hidden\" name=\""
                          + nome + "\""
                          + " id=\""
                          + idCampo
                          + "\" value=\""
                          + valore
                          + "\" size=\""
                          + (lunghezza>78?80:lunghezza+2) + "\""
                          + " maxlength=\""
                          + (cifreDecimali==0?lunghezza:(lunghezza+1)) + "\"";
            return htmlString;
        }
    }

    private static String inserisciCommento(String htmlString, String desc) {
        
        return htmlString += desc;
        
    }

    private static String inserisciTastoAccedi(String hrefAccedi) {
        String javaScript = "javascript:executeSubmit('loopBack.do?propertyName=renderHref.jsp&accedi=si&href="
                            + hrefAccedi + "');";
        return "<input type=\"button\" onclick=\"JavaScript:" + javaScript
               + "\" property=\"accedi\" value=\"Accedi\" />";
    }
    
    private static String inserisciTastoAccedi2(String hrefAccedi) {
        return "<input type=\"submit\" name=\"navigation.button.loopback?propertyName=renderHref.jsp&accedi=si&href="+ hrefAccedi+"\" value=\"Accedi\" class=\"btn\">";
    }
    
/*    <ppl:commandLoopback styleClass="btn" validate="true" >SALVA E TORNA ALLE DICHIARAZIONI</ppl:commandLoopback>
    <ppl:linkLoopback property="propertyName=renderHref.jsp&accedi=si&href="+ hrefAccedi + "'">Anagrafica</ppl:linkLoopback>*/
    
//    private static String inserisciTastoCalendario(String idCampo) {
//        return " <input type=\"button\" value=\" ... \" onclick=\"return showCalendar('"+idCampo+"', '%d/%m/%Y');\">";
//    }
    
    private static String inserisciTastoCalendario(String idCampo) {
        return " <input type=\"button\" value=\"Scegli\" onclick=\"javascript:NewCal('"+idCampo+"','ddmmyyyy',false,24,'Seleziona una Data',event.screenY+4,event.screenX+4)\">"
                /*+"<input type=\"button\" value=\"Svuota\" onclick=\"javascript:pulisci('"+idCampo+"')\">"*/;
    }
    
//    private static String inserisciTastoCalendario(String idCampo) {
//        return " <input type=\"button\" name=\"anchor1\" id=\"anchor1\" value=\" ... \" onclick=\"cal.select(document.getElementBy('"+idCampo+"'),'anchor1','dd/MM/yyyy'); return false;\">";
//    }
    
//    private static String inserisciTastoCalendario(String idCampo) {
//        return "<form action=\"#\" method=\"get\">" 
//        + "<input type=\"text\" name=\"date\" id=\""+ idCampo +"\" /><button type=\"reset\" id=\"f_trigger_b\">...</button>" 
//        + "</form>"    
//        + "<script type=\"text/javascript\">"
//        + "    Calendar.setup({"
//        + "        inputField     :    \""+ idCampo +"\","
//        + "        ifFormat       :    \"%m/%d/%Y %I:%M %p\","
//        + "        showsTime      :    false,"
//        + "        button         :    \"f_trigger_b\","   // trigger for the calendar (button ID)
//        + "        singleClick    :    false,"           // double-click mode
//        + "        step           :    1"                // show all years in drop-down boxes (instead of every other year as default)
//        + "    });"
//        + "</script>";
//    }
    
    private static String inserisciTastoControlloPartitaIVA(String idCampo) {
        return " <input type=\"button\" value=\"Valida\" onclick=\"verificaPIVA('"+idCampo+"')\">";
    }
    
    private static String inserisciTastoControlloCodiceFiscale(String idCampo) {
        return " <input type=\"button\" value=\"Valida\" onclick=\"verificaCodFisc('"+idCampo+"')\">";
    }

    private static String inserisciTastoAggiungi(String hrefAccedi, String htmlString) {
        String javaScript = "javascript:executeSubmit('loopBack.do?propertyName=renderHref.jsp&aggiungi=si&href="
                            + hrefAccedi + "');";
        if(htmlString.indexOf("property=\"aggiungi\" value=\"+\"")>0){
            return "";
        }
        else{
            return "<input type=\"button\" onclick=\"JavaScript:" + javaScript
                    + "\" property=\"aggiungi\" value=\"+\" />";
        }
    }
    
    /*
     * Modifica Init 21/05/2007 (1.3.3)
     * Tolta la variabile "nomeCampo" e sostituita con la stringa "si": evita che il servizio vada in errore se si preme il tasto "-" 
     * su campi multipli nel caso che il primo campo multiplo sia un radiobutton o un checkbox
     * 
     */
    private static String inserisciTastoTogli(String hrefAccedi, String nomeCampo, String htmlString) {
        String javaScript = "javascript:executeSubmit('loopBack.do?propertyName=renderHref.jsp&togli="
                            + /*nomeCampo*/"si" + "&href=" + hrefAccedi + "');";
        if(htmlString.indexOf("property=\"togli\" value=\"-\"")>0){
            return "";
        }
        else{
            return "<input type=\"button\" onclick=\"JavaScript:" + javaScript
               + "\" property=\"togli\" value=\"-\" />";
        }
    }

    public static Object[][] getDatiHref(SezioneCompilabileBean compilabileBean) {
        int numColonne = compilabileBean.getTdCount();
        int numRighe = compilabileBean.getRowCount();
        Object[][] retVal = new Object[numRighe][numColonne];
        ArrayList campi = (ArrayList) compilabileBean.getCampi();
        Iterator it = campi.iterator();
        while (it.hasNext()) {
            HrefCampiBean hrefCB = (HrefCampiBean) it.next();
            String[] campo = new String[5];
            campo[0] = hrefCB.getDescrizione();    
            campo[2] = hrefCB.getTipo();
            if (campo[2].equalsIgnoreCase("L")) {
                if(hrefCB != null && hrefCB.getValoreUtente() != null && hrefCB.getValoreUtente().indexOf("|@|") >= 0){
                    campo[3]="Y";
                    List valoriMultipli = parseValoriCampiMultipli(hrefCB.getValoreUtente());
                    Iterator itValMult = valoriMultipli.iterator();
                    String tmp = campo[0]+"\n\n";
                    while(itValMult.hasNext()){
                        Object obj = itValMult.next();
                        String descCombo = "";
                        if(obj != null && !obj.equals("null")){
                            Iterator itCombo = hrefCB.getOpzioniCombo().iterator();
                            while (itCombo.hasNext()) {
                                BaseBean bb = (BaseBean) itCombo.next();
                                if (bb.getCodice().equals(obj)) {
                                    descCombo = bb.getDescrizione();
                                    break;
                                }
                            }
                        }
                        tmp += descCombo+"\n";
                    }
                    campo[1] = tmp;
                }
                else{
                    Iterator itCombo = hrefCB.getOpzioniCombo().iterator();
                    String listaCombo = "";
                    while (itCombo.hasNext()) {
                        BaseBean bb = (BaseBean) itCombo.next();
                        listaCombo += "[   ] "+bb.getDescrizione()+"\n";
                        if (bb.getCodice().equalsIgnoreCase(hrefCB.getValoreUtente())) {
                            campo[1] = bb.getDescrizione();
                            //break;
                        }
                    }
                    campo[4] = listaCombo;
                }
            }
            // Da modificare: ora ? come fosse un testo
            else if(campo[2].equalsIgnoreCase("R") || campo[2].equalsIgnoreCase("C")){
                if(hrefCB != null && hrefCB.getValoreUtente() != null && hrefCB.getValoreUtente().indexOf("|@|") >= 0){
                    campo[3]="Y";
                    List valoriMultipli = parseValoriCampiMultipli(hrefCB.getValoreUtente());
                    Iterator itValMult = valoriMultipli.iterator();
                    String tmp = campo[0]+"\n\n";
                    while(itValMult.hasNext()){
                        Object obj = itValMult.next();
                        tmp += (obj.equals("null")?" ":"X")+"\n";
                    }
                    campo[1] = tmp;
                }
                else{
                    campo[1] = hrefCB.getValoreUtente();
                }
            }
            else {
                if(hrefCB != null && hrefCB.getValoreUtente() != null && hrefCB.getValoreUtente().indexOf("|@|") >= 0){
                    campo[3]="Y";
                    List valoriMultipli = parseValoriCampiMultipli(hrefCB.getValoreUtente());
                    Iterator itValMult = valoriMultipli.iterator();
                    String tmp = campo[0]+"\n\n";
                    while(itValMult.hasNext()){
                        Object obj = itValMult.next();
                        tmp += (obj.equals("null")?" ":obj)+"\n";
                    }
                    campo[1] = tmp;
                }
                else{
                    campo[1] = hrefCB.getValoreUtente();
                }
            }
            retVal[hrefCB.getRiga() - 1][hrefCB.getPosizione() - 1] = campo;
        }
        return retVal;
    }
    
    public static Object[][] getDatiHrefPdfBlanc(SezioneCompilabileBean compilabileBean) {
        int numColonne = compilabileBean.getTdCount();
        int numRighe = compilabileBean.getRowCount();
        Object[][] retVal = new Object[numRighe][numColonne];
        ArrayList campi = (ArrayList) compilabileBean.getCampi();
        Iterator it = campi.iterator();
        while (it.hasNext()) {
            HrefCampiBean hrefCB = (HrefCampiBean) it.next();
            String[] campo = new String[5];
            campo[0] = hrefCB.getDescrizione();    
            campo[2] = hrefCB.getTipo();
            if (campo[2].equalsIgnoreCase("L")) {
                if(hrefCB != null && hrefCB.getValoreUtente() != null && hrefCB.getValoreUtente().indexOf("|@|") >= 0){
                    campo[3]="Y";
                    List valoriMultipli = parseValoriCampiMultipli(hrefCB.getValoreUtente());
                    Iterator itValMult = valoriMultipli.iterator();
                    String tmp = campo[0]+"\n\n";
                    while(itValMult.hasNext()){
                        Object obj = itValMult.next();
                        String descCombo = "";
                        if(obj != null && !obj.equals("null")){
                            Iterator itCombo = hrefCB.getOpzioniCombo().iterator();
                            while (itCombo.hasNext()) {
                                BaseBean bb = (BaseBean) itCombo.next();
                                if (bb.getCodice().equals(obj)) {
                                    descCombo = bb.getDescrizione();
                                    break;
                                }
                            }
                        }
                        tmp += descCombo+"\n";
                    }
                    campo[1] = tmp;
                }
                else{
                    Iterator itCombo = hrefCB.getOpzioniCombo().iterator();
                    String listaCombo = "";
                    while (itCombo.hasNext()) {
                        BaseBean bb = (BaseBean) itCombo.next();
                        listaCombo += "[   ] "+bb.getDescrizione()+"\n";
                        if (bb.getCodice().equalsIgnoreCase(hrefCB.getValoreUtente())) {
                            campo[1] = bb.getDescrizione();
                            //break;
                        }
                    }
                    campo[4] = listaCombo;
                }
            }
            else if(campo[2].equalsIgnoreCase("R") || campo[2].equalsIgnoreCase("C")){
                if(hrefCB != null && hrefCB.getValoreUtente() != null && hrefCB.getValoreUtente().indexOf("|@|") >= 0){
                    campo[3]="Y";
                    List valoriMultipli = parseValoriCampiMultipli(hrefCB.getValoreUtente());
                    Iterator itValMult = valoriMultipli.iterator();
                    String tmp = campo[0]+"\n\n";
                    while(itValMult.hasNext()){
                        Object obj = itValMult.next();
                        tmp += (obj.equals("null")?"[   ]":"[ x ]")+"\n";
                    }
                    for(int i=0; i<5; i++){
                        tmp += "[   ]\n";
                    }
                    campo[1] = tmp;
                }
                else if(hrefCB.getNumCampo() == 1){
                    campo[3]="Y";
                    String tmp = campo[0]+"\n\n";
                    for(int i=0; i<5; i++){
                        tmp += "[   ]\n";
                    }
                    campo[1] = tmp;
                }
                else{
                    campo[1] = hrefCB.getValoreUtente();
                }
            }
            else {
                if(hrefCB != null && hrefCB.getValoreUtente() != null && hrefCB.getValoreUtente().indexOf("|@|") >= 0){
                    campo[3]="Y";
                    List valoriMultipli = parseValoriCampiMultipli(hrefCB.getValoreUtente());
                    Iterator itValMult = valoriMultipli.iterator();
                    String tmp = campo[0]+"\n\n";
                    /*while(itValMult.hasNext()){
                        Object obj = itValMult.next();
                        tmp += ((obj == null || obj.equals("null") || obj.equals(""))?"___________":obj)+"\n";
                    }*/
                    for(int i=0; i<5; i++){
                        tmp += "___________\n";
                    }
                    campo[1] = tmp;
                }
                else if(hrefCB.getNumCampo() == 1){
                    campo[3]="Y";
                    String tmp = campo[0]+"\n\n";
                    for(int i=0; i<5; i++){
                        tmp += "___________\n";
                    }
                    campo[1] = tmp;
                }
                else{
                    campo[1] = ""/*hrefCB.getValoreUtente()*/;
                }
            }
            retVal[hrefCB.getRiga() - 1][hrefCB.getPosizione() - 1] = campo;
        }
        return retVal;
    }

    private static List parseValoriCampiMultipli(String valore, int livelloCampiMultili) {
//        if(livello == -1){
//            int livelloTmp = 0;
//            String valoreTmp = valore;
//            if (valoreTmp != null && !valoreTmp.equalsIgnoreCase("")) {
//                boolean continua = valoreTmp.indexOf("|@|") >= 0;
//                if (!continua) {
//                    livelloTmp++;
//                }
//                while (continua) {
//                    livelloTmp++;
//                    valoreTmp = valoreTmp.substring(valoreTmp.indexOf("|@|") + 3);
//                    continua = valoreTmp.indexOf("|@|") >= 0;
//                    if (!continua) {
//                        livelloTmp++;
//                    }
//                }
//            }
//            
//        }
//        else{
//            
//        }
        List retVal = new ArrayList();
        if (valore != null && !valore.equalsIgnoreCase("")) {
            boolean continua = valore.indexOf("|@|") >= 0;
            if (!continua) {
                retVal.add(valore);
            }
            while (continua) {
                log.info("AddValore: "+valore.substring(0, valore.indexOf("|@|")));
                retVal.add(valore.substring(0, valore.indexOf("|@|")));
                valore = valore.substring(valore.indexOf("|@|") + 3);
                continua = valore.indexOf("|@|") >= 0;
                if (!continua) {
                    log.info("AddValore2: "+valore);
                    retVal.add(valore);
                }
            }
        }
        int diff = livelloCampiMultili-retVal.size();
        if(diff!=0){
            log.info("diffVal "+diff);
            if(diff>0){
                for(int i=0; i<diff; i++){
                    retVal.add("");
                }
            }
            else{
                for(int i=0; i<diff*(-1); i++){
                    log.info("removeVal "+(retVal.size()-1));
                    retVal.remove(retVal.size()-1);
                }
            }
        }
        return retVal;
    }
    
    private static List parseValoriCampiMultipli(String valore) {
      List retVal = new ArrayList();
      if (valore != null && !valore.equalsIgnoreCase("")) {
          boolean continua = valore.indexOf("|@|") >= 0;
          if (!continua) {
              retVal.add(valore);
          }
          while (continua) {
              retVal.add(valore.substring(0, valore.indexOf("|@|")));
              valore = valore.substring(valore.indexOf("|@|") + 3);
              continua = valore.indexOf("|@|") >= 0;
              if (!continua) {
                  retVal.add(valore);
              }
          }
      }
      return retVal;
  }
}
