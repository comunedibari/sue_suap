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
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility;

import it.gruppoinit.commons.Utilities;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.BaseBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.HrefCampiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.steps.BaseStep;
import it.people.process.AbstractPplProcess;
import it.people.util.MessageBundleHelper;
import it.people.wrappers.IRequestWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Classe che crea il codice html delle dichiarazioni dinamiche
 *
 * @author InIT http://www.gruppoinit.it
 *
 * 16-giu-2006
 */
public class HtmlRenderer extends BaseStep {

    public static String costruisciStringaHtml(SezioneCompilabileBean compilabileBean, boolean modalitaEdit, String styleTabella, String styleTitolo, String width, String border, String cellpadding, String cellspacing, String dimensioneText, IRequestWrapper request) {
        return costruisciStringaHtml(compilabileBean, modalitaEdit, styleTabella, styleTitolo, width, border, cellpadding, cellspacing, dimensioneText, null, request);
    }
    private static String ultimoCampoPerPrecompilazione = null;
    private static String campoRicerca = "  <i>(Campo Ricerca)</i>";
    private static String campoKey = "  <i>(Campo Chiave)</i>";
    private static HashMap hmTipiRagruppamento;

    public static String costruisciStringaHtml(SezioneCompilabileBean compilabileBean, boolean modalitaEdit, String styleTabella, String styleTitolo, String width, String border, String cellpadding, String cellspacing, String dimensioneText, ProcessData dataForm, IRequestWrapper request) {

    	AbstractPplProcess pplProcess = (AbstractPplProcess)request.getUnwrappedRequest().getSession().getAttribute("pplProcess");
    	String etichettaDescrittivaRicerca = "";
    	if (pplProcess != null && pplProcess.getContext() != null) {
    		etichettaDescrittivaRicerca = MessageBundleHelper.message("precompilazione.etichetta.descrittiva.tasto.cerca", 
    			null, pplProcess.getProcessName(), pplProcess.getContext().getCommune().getKey(), 
    			pplProcess.getContext().getLocale());
    	}
    	
        // Default costanti per Tabella
        if (width == null) {
            width = "100%";
        }
        if (border == null) {
            border = "1";
        }
        if (cellpadding == null) {
            cellpadding = "2";
        }
        if (cellspacing == null) {
            cellspacing = "0";
        }
        if (dimensioneText == null) {
            dimensioneText = "10";
        }

        int numColonne = compilabileBean.getTdCount();
        int numRighe = compilabileBean.getRowCount();
        int recordAttuale = 0;

        String htmlString = "";

        hmTipiRagruppamento = new HashMap();
        // Inizio
        String immagine = "";
        if (!(compilabileBean.getHref().indexOf("ANAG") != -1) && !modalitaEdit) {
            if (compilabileBean.isComplete()) {
                immagine = "<img src=\"/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/semaforo_verde.gif\" alt=\"Dichiarazione completa\" />";
            } else {
                immagine = "<img src=\"/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/semaforo_rosso.gif\" alt=\"Dichiarazione non completa\" />";
            }
        }
        String titolo = compilabileBean.getTitolo();
        if (!compilabileBean.getHref().equalsIgnoreCase("ANAG") && !modalitaEdit) {
            if (compilabileBean.getHref().indexOf("ANAG_") == -1) {
                titolo = "<b>&nbsp;" + compilabileBean.getDescrizione() + "&nbsp;&nbsp;&nbsp;</b><a href=\"/people/loopBack.do?propertyName=renderHref.jsp&href=" + compilabileBean.getHref() + "&MU=true" + "\" class=\"btn\" style=\"font-size:106%;\" ><b>&nbsp;COMPILA&nbsp;&nbsp;&nbsp;</b></a>";
            }
        }
        if (!(compilabileBean.getHref().indexOf("ANAG_") != -1) && !modalitaEdit) {
            htmlString += "<table width=\"" + width + "\" cellpadding=\""
                    + cellpadding + "\" style=\"color:" + (modalitaEdit ? "black;font-size:120%;" : ((compilabileBean.isComplete() ? "green;font-size:120%;" : "red;font-size:120%;"))) + "\"  cellspacing=\"" + cellspacing
                    + "\"><tr><td><b>" + immagine + "&nbsp;" + titolo//compilabileBean.getTitolo()
                    + "</b></td></tr></table>";
        }
        if (!(compilabileBean.getHref().indexOf("ANAG_") != -1) && modalitaEdit) {
            htmlString += "<h3>" + titolo + "</h3><br/>";
        }
        htmlString += "<table width=\"" + width + "\" border=\"" + border
                + "\" cellpadding=\"" + cellpadding + "\" cellspacing=\""
                + cellspacing + "\">";

        java.util.ArrayList campi = (java.util.ArrayList) compilabileBean.getCampi();
        int mcmColonne = mcm(campi);
        HrefCampiBean rigaDati = null;
        if (campi.size() > 0) {
            rigaDati = (HrefCampiBean) campi.get(recordAttuale);
        }
        boolean inseritoTastoAccedi = false;

        for (int i = 1; i < numRighe + 1; i++) {
            htmlString = apriTR(htmlString);
            int maxColonnePerRiga = maxColonnaPerRiga(campi, i, numColonne);
            for (int n = 1; n < maxColonnePerRiga + 1; n++) {
                /*                if (n == maxColonnePerRiga && maxColonnePerRiga < numColonne)
                htmlString = apriTD(htmlString, ""
                + (numColonne
                - maxColonnePerRiga + 1), i, rigaDati.getTipo(), modalitaEdit);
                else
                htmlString = apriTD(htmlString, null, i, rigaDati.getTipo(), modalitaEdit);*/


                htmlString = apriTD(htmlString, "" + (mcmColonne / maxColonnePerRiga), i, n, rigaDati.getTipo(), modalitaEdit);



                if (rigaDati.getRiga() == i && rigaDati.getPosizione() == n) {
                    // Controllo se deve apparire il pulsante "Accedi" (viene
                    // messo al pi� una volta): se si passo il codice dell'href,
                    // altrimenti passo null (o "" se � chiave ma l'avevo gi�
                    // messo)
                    String hrefAccedi = null;
                    ultimoCampoPerPrecompilazione = getUltimoCampoPerPrecompilazione(compilabileBean);
                    if (ultimoCampoPerPrecompilazione != null && rigaDati.getCampo_key() != null) {
                    	if (rigaDati.getCampo_key().equalsIgnoreCase("S")) {
                    		if (rigaDati.getNome().equalsIgnoreCase(ultimoCampoPerPrecompilazione)) {
                    			hrefAccedi = rigaDati.getNome();
                    		} else {
                    			hrefAccedi = "S";
                    		}
                    	} else if (rigaDati.getCampo_key().equalsIgnoreCase("N")) {
                    		hrefAccedi = "N";
                    	}
                    }
//                    if (ultimoCampoPerPrecompilazione != null && rigaDati.getCampo_key() != null && rigaDati.getNome().equalsIgnoreCase(ultimoCampoPerPrecompilazione)) {
//                        hrefAccedi = compilabileBean.getHref();
//                    } else if (ultimoCampoPerPrecompilazione != null && rigaDati.getCampo_key() != null) {
//                        hrefAccedi = "";
//                    }

                    /*String hrefAccedi = rigaDati.getCampo_key() != null
                    && !inseritoTastoAccedi ? compilabileBean.getHref() : (rigaDati.getCampo_key() != null ? "" : null);
                    if (hrefAccedi != null) {
                    inseritoTastoAccedi = true;
                    }*/
                    // PC - nuovo controllo
                    String pattern=null;
                    if (rigaDati.getPattern() != null) {
                        pattern=rigaDati.getPattern().replaceAll("\\\\", "\\\\\\\\");
                    }
                    // htmlString = inserisciCampo(htmlString, rigaDati.getTipo(), rigaDati.getNome(), "".equalsIgnoreCase(rigaDati.getValoreUtente()) ? null : rigaDati.getValoreUtente(), rigaDati.getValore(), dimensioneText, rigaDati.getDescrizione(), modalitaEdit, "O".equalsIgnoreCase(rigaDati.getControllo()) ? true : false, (ArrayList) rigaDati.getOpzioniCombo(), hrefAccedi, rigaDati.getNumCampo(), compilabileBean.getHref(), i, rigaDati.getTp_controllo(), rigaDati.getLunghezza(), rigaDati.getDecimali(), rigaDati.getEdit(), rigaDati.getRaggruppamento_check(), rigaDati.getCampiCollegati(), rigaDati.getCampo_collegato(), rigaDati.getVal_campo_collegato(), campi, request, compilabileBean.getHref().equalsIgnoreCase("ANAG"));
                    htmlString = inserisciCampo(htmlString, rigaDati.getTipo(), rigaDati.getNome(), 
                    		"".equalsIgnoreCase(rigaDati.getValoreUtente()) ? null : rigaDati.getValoreUtente(), 
                    				rigaDati.getValore(), dimensioneText, rigaDati.getDescrizione(), modalitaEdit, 
                    				"O".equalsIgnoreCase(rigaDati.getControllo()) ? true : false, 
                    						(ArrayList) rigaDati.getOpzioniCombo(), hrefAccedi, 
                    						rigaDati.getNumCampo(), compilabileBean.getHref(), i, 
                    						rigaDati.getTp_controllo(), rigaDati.getLunghezza(), 
                    						rigaDati.getDecimali(), rigaDati.getEdit(), 
                    						rigaDati.getRaggruppamento_check(), rigaDati.getCampiCollegati(), 
                    						rigaDati.getCampo_collegato(), rigaDati.getVal_campo_collegato(), 
                    						campi, request, compilabileBean.getHref().equalsIgnoreCase("ANAG"), 
                    						pattern, rigaDati.getErr_msg(), etichettaDescrittivaRicerca,
// pc - ws modificato inizio                                                                
                                                                rigaDati.getMolteplicita()
// pc - ws modificato fine                            
                            );
                    // PC - nuovo controllo
                    recordAttuale++;
                    if (recordAttuale < campi.size()) {
                        rigaDati = (HrefCampiBean) campi.get(recordAttuale);
                    }
                } else {
                    htmlString = inserisciCampoVuoto(htmlString);
                }
                htmlString = chiudiTD(htmlString, rigaDati.getTipo(), modalitaEdit);
            }
            htmlString = chiudiTR(htmlString);


            if (modalitaEdit && compilabileBean.getNumSezioniMultiple() > 0 && i == compilabileBean.getLastRowCampoMultiplo()) {
                htmlString = apriTR(htmlString);
                htmlString += "<td>";
                htmlString += inserisciTastoAggiungi(compilabileBean.getHref(), htmlString);
                if (compilabileBean.getNumSezioniMultiple() > 1) {
                    htmlString += inserisciTastoTogli(compilabileBean.getHref(), "", htmlString);
                }
                htmlString += "</td>";
                htmlString = chiudiTR(htmlString);
            }
        }
        htmlString += "</table>" + "<table><tr><td>"
                + Utilities.NVL(compilabileBean.getPiedeHref(), "")
                + "</td></tr></table><br/>";
        ;

        return htmlString;

    }

    private static String getUltimoCampoPerPrecompilazione(SezioneCompilabileBean compilabileBean) {
        List campiBean = compilabileBean.getCampi();
        String retVal = null;
        Iterator it = campiBean.iterator();
        while (it.hasNext()) {
            HrefCampiBean hrcb = (HrefCampiBean) it.next();
            if (hrcb.getCampo_key() != null && hrcb.getCampo_key().equalsIgnoreCase("S")) {
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
                    && rigaDati.getPosizione() > maxColonnaTemp) {
                maxColonnaTemp = rigaDati.getPosizione();
            }
        }
        return maxColonnaTemp;
    }

    private static int mcm(java.util.ArrayList campi) {
        int mincommul = 1;
        int numriga = 0;
        int contatore = 0;
        ArrayList lista = new ArrayList();
        for (Iterator iterator = campi.iterator(); iterator.hasNext();) {
            HrefCampiBean campoBean = (HrefCampiBean) iterator.next();
            if (campoBean.getRiga() != numriga) {
                if (numriga != 0) {
                    lista.add(new Integer(contatore));
                }
                numriga = campoBean.getRiga();
                contatore = 1;
            } else {
                contatore++;
            }
        }
        if (numriga != 0) {
            lista.add(new Integer(contatore));
        }
        for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
            Integer valore = (Integer) iterator.next();
            mincommul = calcolaMinimoComuneMultiplo(mincommul, valore.intValue());
        }
        return mincommul;
    }

    public static int calcolaMassimoComuneDivisore(int x, int y) {
        int r;
        int MCD = 0;
        r = x % y;
        MCD = y;
        while (r > 0) {
            y = MCD;
            MCD = r;
            r = y % MCD;
        }
        return MCD;
    }

    public static int calcolaMinimoComuneMultiplo(int x, int y) {
        int mcm = 0;
        mcm = x * y / calcolaMassimoComuneDivisore(x, y);
        return mcm;
    }
    // PC - nuovo controllo
//    private static String inserisciCampo(
//            String htmlString,
//            String tipo,
//            String nome,
//            String valoreUtente,
//            String valore,
//            String dimensione,
//            String desc,
//            boolean modalitaEdit,
//            boolean obbligatorio,
//            ArrayList opzioniCombo,
//            String hrefAccedi,
//            int molteplicitaCampo,
//            String href,
//            int i,
//            String tp_controllo,
//            int lunghezza,
//            int decimali,
//            //int livelloCampiMultili,
//            String edit,
//            String raggruppamento_check,
//            List listaCampiCollegati,
//            String campoColl,
//            String valCampoColl,
//            ArrayList campi, IRequestWrapper request, boolean isAnagrafica) {

    private static String inserisciCampo(
            String htmlString,
            String tipo,
            String nome,
            String valoreUtente,
            String valore,
            String dimensione,
            String desc,
            boolean modalitaEdit,
            boolean obbligatorio,
            ArrayList opzioniCombo,
            String hrefAccedi,
            int molteplicitaCampo,
            String href,
            int i,
            String tp_controllo,
            int lunghezza,
            int decimali,
            //int livelloCampiMultili,
            String edit,
            String raggruppamento_check,
            List listaCampiCollegati,
            String campoColl,
            String valCampoColl,
            ArrayList campi, IRequestWrapper request, boolean isAnagrafica,
            String pattern,
            String msgErr, String etichettaDescrittivaRicerca,
// pc - ws modificato inizio
            int livelloMultiplo
// pc - ws modificato fine 
            ) {
        // PC - nuovo controllo
        String msg=null;
        if (msgErr != null) {
             msg=msgErr.replaceAll("'","\\\\'");
             msg=msg.replaceAll("\"","\\\\x22");
        }
        if (tipo.equalsIgnoreCase("R")) {
// pc - ws modificato inizio            
//            return htmlString = inserisciRadioButton(htmlString, nome, valoreUtente, valore, desc, 
//            		modalitaEdit, obbligatorio, i, molteplicitaCampo, href, hrefAccedi, 
//            		listaCampiCollegati, campoColl, valCampoColl, campi, request, isAnagrafica, etichettaDescrittivaRicerca);
            return htmlString = inserisciRadioButton(htmlString, nome, valoreUtente, valore, desc,
                    modalitaEdit, obbligatorio, i, molteplicitaCampo, href, hrefAccedi,
                    listaCampiCollegati, campoColl, valCampoColl, campi, request, isAnagrafica, edit,etichettaDescrittivaRicerca,livelloMultiplo);
// pc - ws modificato fine    
        } else if (tipo.equalsIgnoreCase("I")) {
            // PC - nuovo controllo
            // return htmlString = inserisciText(htmlString, nome, valoreUtente, valore != null ? valore : "", dimensione, desc, modalitaEdit, obbligatorio, hrefAccedi, /*molteplicitaCampo,*/ href, i, tp_controllo, lunghezza, decimali, edit, campoColl, valCampoColl, campi);
// pc - ws modificato inizio            
//            return htmlString = inserisciText(htmlString, nome, valoreUtente, 
//            		valore != null ? valore : "", dimensione, desc, modalitaEdit, obbligatorio, 
//            				hrefAccedi, /*molteplicitaCampo,*/ href, i, tp_controllo, lunghezza, 
//            				decimali, edit, campoColl, valCampoColl, campi, pattern, msg, etichettaDescrittivaRicerca);
            return htmlString = inserisciText(htmlString, nome, valoreUtente,
                    valore != null ? valore : "", dimensione, desc, modalitaEdit, obbligatorio,
                    hrefAccedi, /*molteplicitaCampo,*/ href, i, tp_controllo, lunghezza,
                    decimali, edit, campoColl, valCampoColl, campi, pattern, msg,etichettaDescrittivaRicerca,livelloMultiplo);
// pc - ws modificato fine 
            // PC - nuovo controllo
        } else if (tipo.equalsIgnoreCase("C")) {
// pc - ws modificato inizio            
//            return htmlString = inserisciCheckbox(htmlString, nome, valoreUtente, valore, 
//            		desc, modalitaEdit, obbligatorio, i, molteplicitaCampo, href, hrefAccedi, 
//            		raggruppamento_check, listaCampiCollegati, campoColl, valCampoColl, campi, 
//            		request, isAnagrafica, etichettaDescrittivaRicerca);
            return htmlString = inserisciCheckbox(htmlString, nome, valoreUtente, valore,
                    desc, modalitaEdit, obbligatorio, i, molteplicitaCampo, href, hrefAccedi,
                    raggruppamento_check, listaCampiCollegati, campoColl, valCampoColl, campi,
                    request, isAnagrafica,etichettaDescrittivaRicerca,livelloMultiplo);
// pc - ws modificato fine 
        } else if (tipo.equalsIgnoreCase("L")) {
// pc - ws modificato inizio            
//            return htmlString = inserisciCombobox(htmlString, nome, valoreUtente, valore, 
//            		desc, modalitaEdit, obbligatorio, opzioniCombo, molteplicitaCampo, href, 
//            		hrefAccedi, edit, campoColl, valCampoColl, campi, etichettaDescrittivaRicerca);
            return htmlString = inserisciCombobox(htmlString, nome, valoreUtente, valore,
                    desc, modalitaEdit, obbligatorio, opzioniCombo, molteplicitaCampo, href,
                    hrefAccedi, edit, campoColl, valCampoColl, campi,etichettaDescrittivaRicerca,livelloMultiplo);
// pc - ws modificato fine 
        } else if (tipo.equalsIgnoreCase("T")) {
            return htmlString = inserisciCommento(htmlString, desc);
        } else if (tipo.equalsIgnoreCase("N")) {
// pc - ws modificato inizio            
//            return htmlString = inserisciTextNascosto(htmlString, nome, valoreUtente, 
//            		valore != null ? valore : "", dimensione, desc, modalitaEdit, obbligatorio, 
//            				hrefAccedi, molteplicitaCampo, href, i, tp_controllo, lunghezza, 
//            				decimali, edit);
            return htmlString = inserisciTextNascosto(htmlString, nome, valoreUtente,
                    valore != null ? valore : "", dimensione, desc, modalitaEdit, obbligatorio,
                    hrefAccedi, molteplicitaCampo, href, i, tp_controllo, lunghezza,
                    decimali, edit,livelloMultiplo);
// pc - ws modificato fine    

        } else if (tipo.equalsIgnoreCase("A")) {
            // PC - nuovo controllo
            // return htmlString = inserisciTextArea(htmlString, nome, valoreUtente, valore != null ? valore : "", dimensione, desc, modalitaEdit, obbligatorio, hrefAccedi, /*molteplicitaCampo,*/ href, i, tp_controllo, lunghezza, decimali, edit, campoColl, valCampoColl, campi);
// pc - ws modificato inizio            
//            return htmlString = inserisciTextArea(htmlString, nome, valoreUtente, 
//            		valore != null ? valore : "", dimensione, desc, modalitaEdit, obbligatorio, 
//            		hrefAccedi, /*molteplicitaCampo,*/ href, i, tp_controllo, lunghezza, 
//            		decimali, edit, campoColl, valCampoColl, campi, pattern, msg, etichettaDescrittivaRicerca);
            return htmlString = inserisciTextArea(htmlString, nome, valoreUtente,
                    valore != null ? valore : "", dimensione, desc, modalitaEdit,
                    obbligatorio, hrefAccedi, /*molteplicitaCampo,*/ href, i, tp_controllo, lunghezza,


                    decimali, edit, campoColl, valCampoColl, campi, pattern, msg,etichettaDescrittivaRicerca,livelloMultiplo);
// pc - ws modificato fine              
                        // PC - nuovo controllo
        } // Todo: mettere altri tipi di oggetti
        else {
            return inserisciCampoVuoto(htmlString);
        }
    }

    private static String apriTD(String htmlString, String colspan, int i, int col, String tipo, boolean modalitaEdit) {
        String retString = htmlString;
        String aggiuntaColSpan = "";
        String apriLabelFor = "";
        if (colspan != null) {
            aggiuntaColSpan = " colspan=" + colspan;
        }
        if (modalitaEdit) {

            if (tipo.equalsIgnoreCase("C")) {
                apriLabelFor = "<label for='checkbox_" + i + "'>";
            } else if (tipo.equalsIgnoreCase("R")) {
                apriLabelFor = "<label for='radiobutton_" + i + "_" + col + "'>";
            } else if (tipo.equalsIgnoreCase("I")) {
                apriLabelFor = "<label for='text_" + i + "'>";
            }
        } else {
            if (tipo.equalsIgnoreCase("C")) {
                apriLabelFor = "<label for='checkbox_" + i + "'>";
            } else if (tipo.equalsIgnoreCase("R")) {
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
            if (tipo.equalsIgnoreCase("C") || tipo.equalsIgnoreCase("R")) {
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
    
    private static String attributiPreCompilazione(String hrefAccedi, String etichettaDescrittivaRicerca, int livelloMultiplo) {

    	if (hrefAccedi == null) {
    		return "";
    	}
		if (hrefAccedi.equalsIgnoreCase("N")) {
			return campoRicerca;
		} else if (hrefAccedi.equalsIgnoreCase("S")) {
			return campoKey;
		} else {
return " " + inserisciTastoAccedi(hrefAccedi, etichettaDescrittivaRicerca, livelloMultiplo);
		}
    }

// pc - ws modificato inizio
//    private static String inserisciRadioButton(String htmlString, String nome, String valoreUtente, String valore, 
//    		String desc, boolean modalitaEdit, boolean obbligatorio, int index, int molteplicitaCampo, 
//    		String href, String hrefAccedi, List listaCampiCollegati, String campoColl, 
//    		String valCampoColl, ArrayList campi, IRequestWrapper request, boolean isAnagrafica, 
//    		String etichettaDescrittivaRicerca) {        
    private static String inserisciRadioButton(String htmlString, String nome, String valoreUtente, String valore,
            String desc, boolean modalitaEdit, boolean obbligatorio, int index, int molteplicitaCampo,
            String href, String hrefAccedi, List listaCampiCollegati, String campoColl,
            String valCampoColl, ArrayList campi, IRequestWrapper request, boolean isAnagrafica,
            String edit,String etichettaDescrittivaRicerca,int livelloMultiplo) {
 // pc - ws modificato fine 
        String onClick = "";
        if ((listaCampiCollegati != null) && (listaCampiCollegati.size() > 0)) {
            String tmp1 = "var v_campitesto=new Array(";
            String tmp2 = "var v_rbassociati=new Array(";
            for (Iterator iterator = listaCampiCollegati.iterator(); iterator.hasNext();) {
                String[] campoCollegato = (String[]) iterator.next();
                if (campoCollegato != null) {
                    tmp1 += "'" + href + "_" + campoCollegato[0] + "'";
                    tmp2 += "'" + campoCollegato[1] + "'";
                    if (iterator.hasNext()) {
                        tmp1 += ",";
                        tmp2 += ",";
                    }
                }
            }
            tmp1 += ");";
            tmp2 += ");";
            onClick = "onClick=\"" + tmp1 + tmp2 + "cambiaStatoRadioButton(this.value,v_campitesto,v_rbassociati);return;\"";
        }
        String disabled = "";
        if ((campoColl != null) && (!campoColl.equalsIgnoreCase(""))) {
            for (Iterator iterator = campi.iterator(); iterator.hasNext();) {
                HrefCampiBean campoHref = (HrefCampiBean) iterator.next();
                if (campoHref.getTipo().equalsIgnoreCase("C") && campoHref.getNome().equalsIgnoreCase(campoColl)) {
                    if (campoHref.getValoreUtente() == null || campoHref.getValoreUtente().equalsIgnoreCase("")) {
                        disabled = " disabled=\"true\" ";
                    }
                } else if (campoHref.getTipo().equalsIgnoreCase("R") && campoHref.getNome().equalsIgnoreCase(campoColl) && campoHref.getValore().equalsIgnoreCase(valCampoColl)) {
                    if (campoHref.getValoreUtente() == null || campoHref.getValoreUtente().equalsIgnoreCase("")) {
                        disabled = " disabled=\"true\" ";
                    }
                }
            }
        }
        String noJavascript = (String) request.getUnwrappedRequest().getSession().getAttribute("NOJAVASCRIPT");
        
        if (noJavascript != null && modalitaEdit && !isAnagrafica) { // 	<input type="image"   >
            htmlString += "<input type=\"image\" src=\"/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/" + (valoreUtente == null ? "radio_deselezionato.png" : "radio_selezionato.png") + "\" name=\"navigation.button.loopback$renderHref.jsp&nojavascript=si&nome=" + nome + "&valore=" + valore + "&end=1\"   />";
            //System.out.println("<input type=\"image\" src=\"/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/"+(valoreUtente == null ? "radio_deselezionato.png" : "radio_selezionato.png")+"\" name=\"navigation.button.loopback$renderHref.jsp&nojavascript=si&nome="+nome+"&valore="+valore+"&end=1\" value=\"submit\"  />");
            htmlString += "<input id='" + href + "_" + nome + "' type=\"radio\" name=\"" + href + "_" + nome
                    + "\" value=\"" + valore + "\" "
                    + " style=\"display:none\" "
                    + (valoreUtente == null ? "" : "checked")
                    + (modalitaEdit ? " " + onClick + " " : " ")
                    + "/>&nbsp;" + desc + (obbligatorio ? "(*)" : "")
// pc - ws modificato inizio                    
                    + attributiPreCompilazione(hrefAccedi, etichettaDescrittivaRicerca, livelloMultiplo);
// pc - ws modificato fine   
        } else {
            // htmlString += "<input id='radiobutton_"+ index +"' type=\"radio\" name=\"" + nome
// PC - Anagrafica altri richiedenti inizio  
            if (edit != null && !"s".equalsIgnoreCase(edit)) {
                disabled = " disabled=\"true\" ";
            }
// PC - Anagrafica altri richiedenti fine                
            htmlString += "<input id='" + href + "_" + nome + "' type=\"radio\" name=\"" + href + "_" + nome
                    + "\" value=\"" + valore + "\" "
                    + (modalitaEdit ? disabled : "disabled ")
                    + (valoreUtente == null ? "" : "checked")
                    + (modalitaEdit ? " " + onClick + " " : " ")
                    + "/>&nbsp;" + desc + (obbligatorio ? "(*)" : "")
// pc - ws modificato inizio                    
                    + attributiPreCompilazione(hrefAccedi, etichettaDescrittivaRicerca, livelloMultiplo);
// pc - ws modificato fine   
        }

        return htmlString;
    }
    
     // pc - ws modificato inizio    
//    private static String inserisciCombobox(String htmlString, String nome, String valoreUtente, 
//    		String valore, String desc, boolean modalitaEdit, boolean obbligatorio, 
//    		ArrayList opzioniCombo, int molteplicitaCampo, String href, String hrefAccedi, 
//    		String edit, String campoColl, String valCampoColl, ArrayList campi,
//    		String etichettaDescrittivaRicerca) {    
    private static String inserisciCombobox(String htmlString, String nome, String valoreUtente,
            String valore, String desc, boolean modalitaEdit, boolean obbligatorio,
            ArrayList opzioniCombo, int molteplicitaCampo, String href, String hrefAccedi,
            String edit, String campoColl, String valCampoColl, ArrayList campi,String etichettaDescrittivaRicerca,int livelloMultiplo) {
    // pc - ws modificato fine  
	
        String combo = "";
        if (modalitaEdit) {
            String disabled = "";
            if ((campoColl != null) && (!campoColl.equalsIgnoreCase(""))) {
                for (Iterator iterator = campi.iterator(); iterator.hasNext();) {
                    HrefCampiBean campoHref = (HrefCampiBean) iterator.next();
                    if (campoHref.getTipo().equalsIgnoreCase("C") && campoHref.getNome().equalsIgnoreCase(campoColl)) {
                        if (campoHref.getValoreUtente() == null || campoHref.getValoreUtente().equalsIgnoreCase("")) {
                            disabled = " disabled=\"true\" ";
                        }
                    } else if (campoHref.getTipo().equalsIgnoreCase("R") && campoHref.getNome().equalsIgnoreCase(campoColl) && campoHref.getValore().equalsIgnoreCase(valCampoColl)) {
                        if (campoHref.getValoreUtente() == null || campoHref.getValoreUtente().equalsIgnoreCase("")) {
                            disabled = " disabled=\"true\" ";
                        }
                    }
                }
            }
            //+ ((modalitaEdit && edit.equalsIgnoreCase("s")) ? disabled : " disabled ") + ">";
// PC - Anagrafica corezione inizio  
// tolto             combo += desc + "&nbsp;<select name=\"" + href + "_" + nome + "\" id=\"" + href + "_" + nome + "\" "
// tolto                    + (modalitaEdit ? disabled : " disabled ") + ">";
            if (edit.equalsIgnoreCase("s")) {
            combo += desc + "&nbsp;<select name=\"" + href + "_" + nome + "\" id=\"" + href + "_" + nome + "\" "
                    + (modalitaEdit ? disabled : " disabled ") + ">";
            } else {
                combo += "<input type=\"hidden\" name=\"" + href + "_" + nome + "\" id=\"" + href + "_" + nome + "\" " + "value=\""+valoreUtente+"\"/>";
                combo += desc + "&nbsp;<select name=\"" + href + "_" + nome + "\" disabled=\"true\" " + ">";
            }
// PC - Anagrafica correzione fine            
            //+ ((modalitaEdit && edit.equalsIgnoreCase("s")) ? disabled : " disabled ") + ">";
            combo += "<option VALUE=\"\""
                    + (valoreUtente == null
                    || valoreUtente.equalsIgnoreCase("") ? " selected " : "")
                    + "></option>";
            Iterator it = opzioniCombo.iterator();
            while (it.hasNext()) {
                BaseBean bean = (BaseBean) it.next();
                combo += "<option VALUE=\""
                        + bean.getCodice()
                        + "\""
                        + (bean.getCodice().equalsIgnoreCase(valoreUtente) ? " selected " : "")
                        + ">" + bean.getDescrizione() + "</option>";
            }
            combo += "</select>" + (obbligatorio ? "(*)" : "");
// pc - ws modificato inizio                    
            combo += attributiPreCompilazione(hrefAccedi, etichettaDescrittivaRicerca, livelloMultiplo);
// pc - ws modificato fine   


        } else {

            if (valoreUtente == null || "".equalsIgnoreCase(valoreUtente)) {
                return htmlString += desc + "&nbsp;__________ "
                        + (obbligatorio ? "(*)" : "");
            } else {
                String descComboSel = "";
                Iterator it = opzioniCombo.iterator();
                while (it.hasNext()) {
                    BaseBean bean = (BaseBean) it.next();
                    if (bean.getCodice().equalsIgnoreCase(valoreUtente)) {
                        descComboSel = bean.getDescrizione();
                        break;
                    }
                }
                return htmlString += desc + ":&nbsp;&nbsp;&nbsp;<b>"
                        + descComboSel + "</b>";
            }
        }

        return htmlString + combo;
    }

// pc - ws modificato inizio
//    private static String inserisciCheckbox(String htmlString, String nome, String valoreUtente, 
//    		String valore, String desc, boolean modalitaEdit, boolean obbligatorio, int index, 
//    		int molteplicitaCampo, String href, String hrefAccedi, String raggruppamento_check, 
//    		List listaCampiCollegati, String campoColl, String valCampoColl, ArrayList campi, 
//    		IRequestWrapper request, boolean isAnagrafica, String etichettaDescrittivaRicerca) {
    private static String inserisciCheckbox(String htmlString, String nome, String valoreUtente,
            String valore, String desc, boolean modalitaEdit, boolean obbligatorio, int index,
            int molteplicitaCampo, String href, String hrefAccedi, String raggruppamento_check,
            List listaCampiCollegati, String campoColl, String valCampoColl, ArrayList campi,
            IRequestWrapper request, boolean isAnagrafica,String etichettaDescrittivaRicerca,int livelloMultiplo) {
    // pc - ws modificato fine
	
        String asterisco = "(*)";
        String[] asteriscoRagruppamento = new String[4];
        asteriscoRagruppamento[0] = "(**)"/*"<span style=\"color:blue\">(**)</span>"*/;
        asteriscoRagruppamento[1] = "(**)"/*"<span style=\"color:orange\">(**)</span>"*/;
        asteriscoRagruppamento[2] = "(**)"/*"<span style=\"color:red\">(**)</span>"*/;
        asteriscoRagruppamento[3] = "(**)"/*"<span style=\"color:green\">(**)</span>"*/;
        String opzioneAsteriscoAttivata = "";

        if (raggruppamento_check == null || raggruppamento_check.equalsIgnoreCase("")) {
            opzioneAsteriscoAttivata = asterisco;
        } else {
            hmTipiRagruppamento.put(raggruppamento_check, "Y");
            opzioneAsteriscoAttivata = asteriscoRagruppamento[(hmTipiRagruppamento.size() - 1) < 4 ? (hmTipiRagruppamento.size() - 1) : 3];
        }
        String onClick = "";
        if ((listaCampiCollegati != null) && (listaCampiCollegati.size() > 0)) {
            String tmp1 = "var v_campitesto=new Array(";
            for (Iterator iterator = listaCampiCollegati.iterator(); iterator.hasNext();) {
                String[] campoCollegato = (String[]) iterator.next();
                tmp1 += "'" + href + "_" + campoCollegato[0] + "'";
                if (iterator.hasNext()) {
                    tmp1 += ",";
                }
            }
            tmp1 += ");";
            onClick = "onClick=\"" + tmp1 + "cambiaStatoCheckBox(this,v_campitesto);return;\"";
        }

        String noJavascript = (String) request.getUnwrappedRequest().getSession().getAttribute("NOJAVASCRIPT");
        if (noJavascript != null && modalitaEdit && !isAnagrafica) { // 	<input type="image"   >
            htmlString += "<input type=\"image\" src=\"/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/" + (valoreUtente == null ? "check_des.png" : "check_sel.png") + "\" name=\"navigation.button.loopback$renderHref.jsp&nojavascript=si&nome=" + nome + "&valore=" + valore + "&end=1\"   />";
            //System.out.println("<input type=\"image\" src=\"/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/"+(valoreUtente == null ? "radio_deselezionato.png" : "radio_selezionato.png")+"\" name=\"navigation.button.loopback$renderHref.jsp&nojavascript=si&nome="+nome+"&valore="+valore+"&end=1\" value=\"submit\"  />");
            htmlString += "<input id='" + href + "_" + nome + "' type=\"checkbox\" name=\"" + href + "_" + nome
                    + "\" value=\"" + valore + "\" "
                    + " style=\"display:none\" "
                    + (valoreUtente == null ? "" : "checked")
                    + (modalitaEdit ? " " + onClick + " " : "")
                    + ">&nbsp;" + desc + (obbligatorio ? "(*)" : "")
// pc - ws modificato inizio                    
                    + attributiPreCompilazione(hrefAccedi, etichettaDescrittivaRicerca, livelloMultiplo);
// pc - ws modificato fine  
        } else {

            String disabled = "";
            if ((campoColl != null) && (!campoColl.equalsIgnoreCase(""))) {
                for (Iterator iterator = campi.iterator(); iterator.hasNext();) {
                    HrefCampiBean campoHref = (HrefCampiBean) iterator.next();
                    if (campoHref.getTipo().equalsIgnoreCase("C") && campoHref.getNome().equalsIgnoreCase(campoColl)) {
                        if (campoHref.getValoreUtente() == null || campoHref.getValoreUtente().equalsIgnoreCase("")) {
                            disabled = " disabled=\"true\" ";
                        }
                    } else if (campoHref.getTipo().equalsIgnoreCase("R") && campoHref.getNome().equalsIgnoreCase(campoColl) && campoHref.getValore().equalsIgnoreCase(valCampoColl)) {
                        if (campoHref.getValoreUtente() == null || campoHref.getValoreUtente().equalsIgnoreCase("")) {
                            disabled = " disabled=\"true\" ";
                        }
                    }
                }
            }

            // htmlString += "<input id='checkbox_"+ index +"' type=\"checkbox\" name=\"" + nome
            htmlString += "<input id='" + href + "_" + nome + "' type=\"checkbox\" name=\"" + href + "_" + nome
                    + "\" value=\"" + valore + "\" "
                    + (modalitaEdit ? disabled : "disabled ")
                    + (valoreUtente == null ? "" : "checked")
                    + (modalitaEdit ? " " + onClick + " " : "")
                    + "/>&nbsp;" + desc + (obbligatorio ? opzioneAsteriscoAttivata : "")
// pc - ws modificato inizio                    
                    + attributiPreCompilazione(hrefAccedi, etichettaDescrittivaRicerca, livelloMultiplo);
// pc - ws modificato fine 
        }
        return htmlString;
    }
    // PC - nuovo controllo
    // private static String inserisciText(String htmlString, String nome, String valoreUtente, String valore, String dimensione, String desc, boolean modalitaEdit, boolean obbligatorio, String hrefAccedi, /*int molteplicitaCampo,*/ String href, int index, String tp_controllo, int lunghezza, int decimali, String edit, String campoColl, String valCampoColl, ArrayList campi) {
    // pc - ws modificato inizio
//    private static String inserisciText(String htmlString, String nome, String valoreUtente, 
//    		String valore, String dimensione, String desc, boolean modalitaEdit, boolean obbligatorio, 
//    		String hrefAccedi, /*int molteplicitaCampo,*/ String href, int index, String tp_controllo, 
//    		int lunghezza, int decimali, String edit, String campoColl, String valCampoColl, 
//    		ArrayList campi, String pattern, String messaggio, String etichettaDescrittivaRicerca) {
        
    private static String inserisciText(String htmlString, String nome, String valoreUtente,
            String valore, String dimensione, String desc, boolean modalitaEdit, boolean obbligatorio,
            String hrefAccedi, /*int molteplicitaCampo,*/ String href, int index, String tp_controllo,
            int lunghezza, int decimali, String edit, String campoColl, String valCampoColl,
            ArrayList campi, String pattern, String messaggio,String etichettaDescrittivaRicerca,int livelloMultiplo) {
    // pc - ws modificato fine  
        String msg="";
        if (messaggio != null) {
            msg=messaggio;
        }
    // PC - nuovo controllo
        valoreUtente = valoreUtente == null ? "" : valoreUtente;
        //valoreUtente = Utilities.escapeXML(valoreUtente);
        if (!modalitaEdit) {

            if ("".equalsIgnoreCase(valoreUtente)) {
                return htmlString += Utilities.NVL(desc, "") + "&nbsp;__________"
                        + (obbligatorio ? "(*)" : "");
            } else {
                String duepunti = (Utilities.isset(desc)) ? " :" : "";
                return htmlString += Utilities.NVL(desc, "") + duepunti + "&nbsp;&nbsp;&nbsp;<b>"
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
            // PC - nuovo controllo
            boolean insRegExp = false;
            // PC - nuovo controllo
            boolean calcolaCampo = false;
            // Campo testo
            if (tp_controllo.equals("T")) {
                /*
                 * Modifica Init 23/02/2007 (versione 1.2.2):
                 * nel caso di campi testo è stato aggiunta la chiamata al metodo javascript onlyLettersAndNumeric ad ogni pressione di tasti.
                 * Questo metodo permette di controllare se il carattere digitato è valido (lettera o numero)
                 */
                onKeyPressString = "onkeypress=\"return onlyLettersAndNumeric(event)\"";
            } // Campo numerico
            else if (tp_controllo.equals("N")) {
                cifreIntere = lunghezza - decimali;
                cifreDecimali = decimali;
                onKeyPressString = "onkeypress=\"return onlyNumeric(event)\"";
            } // Campo data
            else if (tp_controllo.equals("D")) {
                insCalendario = true;
                /*
                 * Modifica Init 19/03/2007 (versione 2.0):
                 * Aggiunto javascript per controllo sulla validità della data inputata direttamente dall'utente.
                 * Tolta di conseguenza la non editabilità del campo
                 */
                // PC - nuovo controllo
                // onBlurString = "onblur=\"javascript:isValidDate(this,true);\"";
                onBlurString = "onblur=\"javascript:isValidDate(this,true,'"+msg+"');\"";
               // PC - nuovo controllo
            } // Codice fiscale
            else if (tp_controllo.equals("F")) {
                insCodFisc = true;
                // PC - nuovo controllo
                // onBlurString = "onblur=\"javascript:verificaCodFisc(this);\"";
                onBlurString = "onblur=\"javascript:verificaCodFisc(this,'"+msg+"');\"";
                // PC - nuovo controllo
            } // Partita IVA
            else if (tp_controllo.equals("I")) {
                insPIVA = true;
                // PC - nuovo controllo
                // onBlurString = "onblur=\"javascript:verificaPIVA(this);\"";
                onBlurString = "onblur=\"javascript:verificaPIVA(this,'"+msg+"');\"";
                // PC - nuovo controllo
            } // PC - nuovo controllo
            // Regular expression
            else if (tp_controllo.equals("X")) {
                insRegExp = true;
                onBlurString = "onblur=\"javascript:verificaREGEXP(this,'" + pattern + "','" + msg + "');\"";
            } // PC - nuovo controllo
            // Partita IVA o codice fiscale
            else if (tp_controllo.equals("C")) {
                insCodFisc = true;
                insPIVA = true;
                // PC - nuovo controllo
                // onBlurString = "onblur=\"javascript:verificaPIVACodFisc(this);\"";
                onBlurString = "onblur=\"javascript:verificaPIVACodFisc(this,'"+msg+"');\"";
                // PC - nuovo controllo
            }

            idCampo = nome;
            String disabled = "";
            if ((campoColl != null) && (!campoColl.equalsIgnoreCase(""))) {
                for (Iterator iterator = campi.iterator(); iterator.hasNext();) {
                    HrefCampiBean campoHref = (HrefCampiBean) iterator.next();
                    if (campoHref.getTipo().equalsIgnoreCase("C") && campoHref.getNome().equalsIgnoreCase(campoColl)) {
                        if (campoHref.getValoreUtente() == null || campoHref.getValoreUtente().equalsIgnoreCase("")) {
                            disabled = " disabled=\"true\" ";
                        }
                    } else if (campoHref.getTipo().equalsIgnoreCase("R") && campoHref.getNome().equalsIgnoreCase(campoColl) && campoHref.getValore().equalsIgnoreCase(valCampoColl)) {
                        if (campoHref.getValoreUtente() == null || campoHref.getValoreUtente().equalsIgnoreCase("")) {
                            disabled = " disabled=\"true\" ";
                        }
                    }
                }
            }
            if (tp_controllo.equals("N")) {
                onChangeString = "onchange=\"return formatNumber('" + idCampo + "','" + cifreIntere + "','" + cifreDecimali + "')\"";
            }
            htmlString += desc
                    // Messo per l'accessibilità: tolto per problemi con il fuoco sui campi multipli
                    //+ "&nbsp;<input id='text_"+ index +"' type=\"text\" name=\""
                    + "&nbsp;<input type=\"text\" name=\""
                    + href + "_" + nome + "\""
                    + " id=\""
                    + href + "_" + idCampo
                    + "\" value=\""
                    + valoreUtente
                    + "\" size=\""
                    + (lunghezza > 78 ? 80 : lunghezza + 2) + "\""
                    + " maxlength=\""
                    + (cifreDecimali == 0 ? lunghezza : (lunghezza + 1)) + "\""
                    + disabled
                    + ((modalitaEdit && edit.equalsIgnoreCase("s")) ? "" : " readonly ")
                    + " " + onChangeString + " " + onKeyPressString + " " + onBlurString + "/>"
                    + (obbligatorio ? "(*)" : "")
                    /*+ (hrefAccedi != null ? (hrefAccedi.equalsIgnoreCase("") ? campoRicerca : " "
                    + inserisciTastoAccedi(hrefAccedi)) : "")*/
    // pc - ws modificato inizio                     
                    + attributiPreCompilazione(hrefAccedi, etichettaDescrittivaRicerca, livelloMultiplo)
    // pc - ws modificato fine  
                    + (!edit.equalsIgnoreCase("s") ? "" : (insCalendario ? inserisciTastoCalendario(href + "_" + idCampo) : ""))
                    // PC - nuovo controllo
                    // + (insCodFisc ? inserisciTastoControlloCodiceFiscale(href + "_" + idCampog) : "")
                    // + (insPIVA ? inserisciTastoControlloPartitaIVA(href + "_" + idCampo) : "");
                    + (insCodFisc ? inserisciTastoControlloCodiceFiscale(href + "_" + idCampo, msg) : "")
                    + (insRegExp ? inserisciTastoControlloREGEXP(href + "_" + idCampo, pattern, msg) : "")
                    + (insPIVA ? inserisciTastoControlloPartitaIVA(href + "_" + idCampo, msg) : "");
                    // PC - nuovo controllo
            return htmlString;
        }
    }

    // PC - nuovo controllo
    // private static String inserisciTextArea(String htmlString, String nome,
    //         String valoreUtente, String string, String dimensione, String desc,
    //         boolean modalitaEdit, boolean obbligatorio, String hrefAccedi,
    //         String href, int i, String tp_controllo, int lunghezza,
    //         int decimali, String edit, String campoColl, String valCampoColl,
    //         ArrayList campi) {
// pc - ws modificato inizio
//    private static String inserisciTextArea(String htmlString, String nome,
//            String valoreUtente, String string, String dimensione, String desc,
//            boolean modalitaEdit, boolean obbligatorio, String hrefAccedi,
//            String href, int i, String tp_controllo, int lunghezza,
//            int decimali, String edit, String campoColl, String valCampoColl,
//            ArrayList campi, String pattern, String msg, String etichettaDescrittivaRicerca) {        
    private static String inserisciTextArea(String htmlString, String nome,
            String valoreUtente, String string, String dimensione, String desc,
            boolean modalitaEdit, boolean obbligatorio, String hrefAccedi,
            String href, int i, String tp_controllo, int lunghezza,
            int decimali, String edit, String campoColl, String valCampoColl,
            ArrayList campi, String pattern, String msg, String etichettaDescrittivaRicerca, int livelloMultiplo) {
// pc - ws modificato fine   

   // PC - nuovo controllo
        valoreUtente = valoreUtente == null ? "" : valoreUtente;
        if (!modalitaEdit) {

            if ("".equalsIgnoreCase(valoreUtente)) {
                return htmlString += desc + "&nbsp;__________"
                        + (obbligatorio ? "(*)" : "");
            } else {
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
            // PC - nuovo controllo
            boolean insRegExp = false;
            // PC - nuovo controllo
            boolean calcolaCampo = false;

            // Campo testo
            if (tp_controllo.equals("T")) {
                onKeyPressString = "onkeypress=\"return onlyLettersAndNumeric(event)\"";
            } // Campo numerico
            else if (tp_controllo.equals("N")) {
                cifreIntere = lunghezza - decimali;
                cifreDecimali = decimali;
                onKeyPressString = "onkeypress=\"return onlyNumeric(event)\"";
            } // Campo data
            else if (tp_controllo.equals("D")) {
                insCalendario = true;
                /*
                 * Modifica Init 19/03/2007 (versione 2.0):
                 * Aggiunto javascript per controllo sulla validità della data inputata direttamente dall'utente.
                 * Tolta di conseguenza la non editabilità del campo
                 */
                // PC - nuovo controllo
                // onBlurString = "onblur=\"javascript:isValidDate(this,true);\"";
                onBlurString = "onblur=\"javascript:isValidDate(this,true,'"+msg+"');\"";
                // PC - nuovo controllo
            } // Codice fiscale
            else if (tp_controllo.equals("F")) {
                insCodFisc = true;
                // PC - nuovo controllo
                // onBlurString = "onblur=\"javascript:verificaCodFisc(this);\"";
                onBlurString = "onblur=\"javascript:verificaCodFisc(this,'"+msg+"');\"";
                // PC - nuovo controllo
            } // Partita IVA
            else if (tp_controllo.equals("I")) {
                insPIVA = true;
                // PC - nuovo controllo
                // onBlurString = "onblur=\"javascript:verificaPIVA(this);\"";
                onBlurString = "onblur=\"javascript:verificaPIVA(this,'"+msg+"');\"";
                // PC - nuovo controllo
            }
            // PC - nuovo controllo
            // Regular expression
            else if (tp_controllo.equals("X")) {
                insRegExp = true;
                onBlurString = "onblur=\"javascript:verificaREGEXP(this,'" + pattern + "','" + msg + "');\"";
            } // PC - nuovo controllo
            else if (tp_controllo.equals("C")) {
                calcolaCampo = true;
            }
            idCampo = href + "_" + nome;
            String disabled = "";
            if ((campoColl != null) && (!campoColl.equalsIgnoreCase(""))) {
                for (Iterator iterator = campi.iterator(); iterator.hasNext();) {
                    HrefCampiBean campoHref = (HrefCampiBean) iterator.next();
                    if (campoHref.getTipo().equalsIgnoreCase("C") && campoHref.getNome().equalsIgnoreCase(campoColl)) {
                        if (campoHref.getValoreUtente() == null || campoHref.getValoreUtente().equalsIgnoreCase("")) {
                            disabled = " disabled=\"true\" ";
                        }
                    } else if (campoHref.getTipo().equalsIgnoreCase("R") && campoHref.getNome().equalsIgnoreCase(campoColl) && campoHref.getValore().equalsIgnoreCase(valCampoColl)) {
                        if (campoHref.getValoreUtente() == null || campoHref.getValoreUtente().equalsIgnoreCase("")) {
                            disabled = " disabled=\"true\" ";
                        }
                    }
                }
            }
            if (tp_controllo.equals("N")) {
                onChangeString = "onchange=\"return formatNumber('" + idCampo + "','" + cifreIntere + "','" + cifreDecimali + "')\"";
            }

            htmlString += desc
                    // Messo per l'accessibilita: tolto per problemi con il fuoco sui campi multipli
                    //+ "&nbsp;<input id='text_"+ index +"' type=\"text\" name=\""
                    + "&nbsp;<br/><textarea name=\""
                    + href + "_" + nome + "\""
                    + " id=\""
                    + idCampo
                    + "\" value=\""
                    + "\" rows=\"6\" cols=\"50\" "
                    //            		"size=\""  + (lunghezza>78?80:lunghezza+2) + "\""
                    + " maxlength=\""
                    + (cifreDecimali == 0 ? lunghezza : (lunghezza + 1)) + "\""
                    + disabled
                    + ((modalitaEdit && edit.equalsIgnoreCase("s")) ? "" : " readonly ")
                    + " onKeyDown = \"limitText(this.form." + href + "_" + nome + ",this.form.countdown" + href + "_" + nome + "," + lunghezza + ");\" "
                    + " onKeyUp = \"limitText(this.form." + href + "_" + nome + ",this.form.countdown" + href + "_" + nome + "," + lunghezza + ");\" "
                    + " " + onChangeString + " " + onKeyPressString + " " + onBlurString + ">" + valoreUtente + "</textarea>"
                    + (obbligatorio ? "(*)" : "")
                    + "<font size=\"1\">(Caratteri massimi: " + lunghezza + ")<br> "
                    + "Caratteri disponibili rimanenti <input readonly type=\"text\" name=\"countdown" + href + "_" + nome + "\" size=\"3\" value=\"" + (lunghezza - valoreUtente.length()) + "\"></font> "
                    /*+ (hrefAccedi != null ? (hrefAccedi.equalsIgnoreCase("") ? campoRicerca : " "
                    + inserisciTastoAccedi(hrefAccedi)) : "")*/
    // pc - ws modificato inizio                      
                    + attributiPreCompilazione(hrefAccedi, etichettaDescrittivaRicerca, livelloMultiplo)
    // pc - ws modificato fine   
                    + (!edit.equalsIgnoreCase("s") ? "" : (insCalendario ? inserisciTastoCalendario(idCampo) : ""))
                    // PC - nuovo controllo
                    // + (insCodFisc ? inserisciTastoControlloCodiceFiscale(idCampo) : "")
                    // + (insPIVA ? inserisciTastoControlloPartitaIVA(idCampo) : "");
                    + (insCodFisc ? inserisciTastoControlloCodiceFiscale(idCampo, msg) : "")
                    + (insRegExp ? inserisciTastoControlloREGEXP(idCampo, pattern, msg) : "")
                    + (insPIVA ? inserisciTastoControlloPartitaIVA(idCampo, msg) : "");
                    // PC - nuovo controllo

        }
        return htmlString;
    }
// pc - ws modificato inizio
    private static String inserisciTextNascosto(String htmlString, String nome, String valoreUtente, String valore, String dimensione, String desc, boolean modalitaEdit, boolean obbligatorio, String hrefAccedi, int molteplicitaCampo, String href, int index, String tp_controllo, int lunghezza, int decimali, String edit,int livelloMultiplo) {

//    private static String inserisciTextNascosto(String htmlString, String nome, String valoreUtente, String valore, String dimensione, String desc, boolean modalitaEdit, boolean obbligatorio, String hrefAccedi, int molteplicitaCampo, String href, int index, String tp_controllo, int lunghezza, int decimali, String edit) {
// pc - ws modificato fine 
        valoreUtente = valoreUtente == null ? "" : valoreUtente;
        if (!Utilities.isset(valoreUtente)) {
            valoreUtente = Utilities.NVL(valore, "");
        }
        if (!modalitaEdit) {
            return htmlString += " ";
        } else {
            int cifreDecimali = 0;
            String idCampo = "";

            idCampo = nome;
            htmlString += " "
                    + "&nbsp;<input type=\"hidden\" name=\""
                    + href + "_" + nome + "\""
                    + " id=\""
                    + href + "_" + idCampo
                    + "\" value=\""
                    + valoreUtente
                    + "\" size=\""
                    + (lunghezza > 78 ? 80 : lunghezza + 2) + "\""
                    + " maxlength=\""
                    + (cifreDecimali == 0 ? lunghezza : (lunghezza + 1)) + "\">";
            return htmlString;
        }
    }

    private static String inserisciCommento(String htmlString, String desc) {
        return htmlString += desc;
    }
    // pc - ws modificato inizio    
    private static String inserisciTastoAccedi(String hrefAccedi,String etichettaDescrittivaRicerca, int livelloMultiplo) {
        String pulsante = "<input type=\"submit\" name=\"navigation.button.loopback$renderHref.jsp&accedi=si&livello="+livelloMultiplo+"&href=" + hrefAccedi + "\" value=\"CERCA\" class=\"btn\" />" + etichettaDescrittivaRicerca;

//  private static String inserisciTastoAccedi(String hrefAccedi, String etichettaDescrittivaRicerca) {
//      String pulsante = "<input type=\"submit\" name=\"navigation.button.loopback$renderHref.jsp&accedi=si&href=" 
//        		+ hrefAccedi + "\" value=\"CERCA\" class=\"btn\" />" + etichettaDescrittivaRicerca;
//
    // pc - ws modificato fine
//        String javaScript = "javascript:executeSubmit('loopBack.do?propertyName=renderHref.jsp&accedi=si&href="
//                            + hrefAccedi + "');";
//        return "<input type=\"button\" onclick=\"JavaScript:" + javaScript
//               + "\" property=\"accedi\" value=\"Cerca\" />";
        return pulsante;
    }

    private static String inserisciTastoCalcolaCampo(String hrefAccedi, String idCampo) {
        String javaScript = "javascript:executeSubmit('loopBack.do?propertyName=renderHref.jsp&calcola=si&href="
                + hrefAccedi + "&idCampo=" + idCampo + "');";
        return "<input type=\"button\" onclick=\"JavaScript:" + javaScript
                + "\" property=\"accedi\" value=\"Calcola\" />";
    }

    /*    <ppl:commandLoopback styleClass="btn" validate="true" >SALVA E TORNA ALLE DICHIARAZIONI</ppl:commandLoopback>
    <ppl:linkLoopback property="propertyName=renderHref.jsp&accedi=si&href="+ hrefAccedi + "'">Anagrafica</ppl:linkLoopback>*/
//    private static String inserisciTastoCalendario(String idCampo) {
//        return " <input type=\"button\" value=\" ... \" onclick=\"return showCalendar('"+idCampo+"', '%d/%m/%Y');\">";
//    }
    private static String inserisciTastoCalendario(String idCampo) {
        String calendario = "" +//<a id=\"calDataScadenza"+idCampo+"\" href=\"\" title=\"Calendario\">"+
                //	"<img src=\"/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/calendario.gif\" alt=\"Calendario\"/></a>"+
                "		<script type='text/javascript'>"
                + "Calendar.setup({"
                + "inputField     :    \"" + idCampo + "\","
                + "button         :    \"" + idCampo + "\""
                + "});"
                + "</script>";

        return calendario;
        // return " <input type=\"button\" value=\"Scegli\" onclick=\"javascript:NewCal('"+idCampo+"','ddmmyyyy',false,24,'Seleziona una Data',event.screenY+4,event.screenX+4)\">"

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
    // PC - nuovo controllo
//    private static String inserisciTastoControlloPartitaIVA(String idCampo) {
//        return " ";//" <input type=\"button\" value=\"Valida\" onclick=\"verificaPIVA('"+idCampo+"')\">";
//    }
//
//    private static String inserisciTastoControlloCodiceFiscale(String idCampo) {
//        return " ";//" <input type=\"button\" value=\"Valida\" onclick=\"verificaCodFisc('"+idCampo+"')\">";
//    }
    private static String inserisciTastoControlloPartitaIVA(String idCampo, String msg) {
        return " ";//" <input type=\"button\" value=\"Valida\" onclick=\"verificaPIVA('"+idCampo+"')\">";
    }
    private static String inserisciTastoControlloCodiceFiscale(String idCampo, String msg) {
        return " ";//" <input type=\"button\" value=\"Valida\" onclick=\"verificaCodFisc('"+idCampo+"')\">";
    }
    private static String inserisciTastoControlloREGEXP(String idCampo, String pattern, String msg) {
        return " ";//" <input type=\"button\" value=\"Valida\" onclick=\"verificaREGEXP('"+idCampo+"')\">";
    }
    // PC - nuovo controllo

    private static String inserisciTastoAggiungi(String hrefAccedi, String htmlString) {

        String pulsante = "<input type=\"submit\" name=\"navigation.button.loopback$renderHref.jsp&aggiungi=si&href=" + hrefAccedi + "\" value=\"+\" class=\"btn\" />";

        if (hrefAccedi.indexOf("ANAG") == -1) {
            if (htmlString.indexOf("property=\"aggiungi\" value=\"+\"") > 0) {
                return "";
            } else {
                return pulsante;
            }
        } else {
            return "<input type=\"submit\" name=\"navigation.button.loopback$addCampiMultipliAnagrafica&aggiungi=si&href=" + hrefAccedi + "\" value=\"+\" class=\"btn\" />";
        }
    }

    /*
     * Modifica Init 21/05/2007 (1.3.3)
     * Tolta la variabile "nomeCampo" e sostituita con la stringa "si": evita che il servizio vada in errore se si preme il tasto "-"
     * su campi multipli nel caso che il primo campo multiplo sia un radiobutton o un checkbox
     *
     */
    private static String inserisciTastoTogli(String hrefAccedi, String nomeCampo, String htmlString) {
        String pulsante = "<input type=\"submit\" name=\"navigation.button.loopback$renderHref.jsp&togli=si&href=" + hrefAccedi + "\" value=\"-\" class=\"btn\" />";
//        String javaScript = "javascript:executeSubmit('loopBack.do?propertyName=renderHref.jsp&togli="
//                            + /*nomeCampo*/"si" + "&href=" + hrefAccedi + "');";
        if (htmlString.indexOf("property=\"togli\" value=\"-\"") > 0) {
            return "";
        } else {
            return pulsante;
//            return "<input type=\"button\" onclick=\"JavaScript:" + javaScript
//               + "\" property=\"togli\" value=\"-\" />";
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
                if (hrefCB != null && hrefCB.getValoreUtente() != null && hrefCB.getValoreUtente().indexOf("|@|") >= 0) {
                    campo[3] = "Y";
                    List valoriMultipli = parseValoriCampiMultipli(hrefCB.getValoreUtente());
                    Iterator itValMult = valoriMultipli.iterator();
                    String tmp = campo[0] + "\n\n";
                    while (itValMult.hasNext()) {
                        Object obj = itValMult.next();
                        String descCombo = "";
                        if (obj != null && !obj.equals("null")) {
                            Iterator itCombo = hrefCB.getOpzioniCombo().iterator();
                            while (itCombo.hasNext()) {
                                BaseBean bb = (BaseBean) itCombo.next();
                                if (bb.getCodice().equals(obj)) {
                                    descCombo = bb.getDescrizione();
                                    break;
                                }
                            }
                        }
                        tmp += descCombo + "\n";
                    }
                    campo[1] = tmp;
                } else {
                    Iterator itCombo = hrefCB.getOpzioniCombo().iterator();
                    String listaCombo = "";
                    while (itCombo.hasNext()) {
                        BaseBean bb = (BaseBean) itCombo.next();
                        listaCombo += "[   ] " + bb.getDescrizione() + "\n";
                        if (bb.getCodice().equalsIgnoreCase(hrefCB.getValoreUtente())) {
                            campo[1] = bb.getDescrizione();
                            //break;
                        }
                    }
                    campo[4] = listaCombo;
                }
            } // Da modificare: ora è come fosse un testo
            else if (campo[2].equalsIgnoreCase("R") || campo[2].equalsIgnoreCase("C")) {
                if (hrefCB != null && hrefCB.getValoreUtente() != null && hrefCB.getValoreUtente().indexOf("|@|") >= 0) {
                    campo[3] = "Y";
                    List valoriMultipli = parseValoriCampiMultipli(hrefCB.getValoreUtente());
                    Iterator itValMult = valoriMultipli.iterator();
                    String tmp = campo[0] + "\n\n";
                    while (itValMult.hasNext()) {
                        Object obj = itValMult.next();
                        tmp += (obj.equals("null") ? " " : "X") + "\n";
                    }
                    campo[1] = tmp;
                } else {
                    campo[1] = hrefCB.getValoreUtente();
                }
            } else {
                if (hrefCB != null && hrefCB.getValoreUtente() != null && hrefCB.getValoreUtente().indexOf("|@|") >= 0) {
                    campo[3] = "Y";
                    List valoriMultipli = parseValoriCampiMultipli(hrefCB.getValoreUtente());
                    Iterator itValMult = valoriMultipli.iterator();
                    String tmp = campo[0] + "\n\n";
                    while (itValMult.hasNext()) {
                        Object obj = itValMult.next();
                        tmp += (obj.equals("null") ? " " : obj) + "\n";
                    }
                    campo[1] = tmp;
                } else {
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
                if (hrefCB != null && hrefCB.getValoreUtente() != null && hrefCB.getValoreUtente().indexOf("|@|") >= 0) {
                    campo[3] = "Y";
                    List valoriMultipli = parseValoriCampiMultipli(hrefCB.getValoreUtente());
                    Iterator itValMult = valoriMultipli.iterator();
                    String tmp = campo[0] + "\n\n";
                    while (itValMult.hasNext()) {
                        Object obj = itValMult.next();
                        String descCombo = "";
                        if (obj != null && !obj.equals("null")) {
                            Iterator itCombo = hrefCB.getOpzioniCombo().iterator();
                            while (itCombo.hasNext()) {
                                BaseBean bb = (BaseBean) itCombo.next();
                                if (bb.getCodice().equals(obj)) {
                                    descCombo = bb.getDescrizione();
                                    break;
                                }
                            }
                        }
                        tmp += descCombo + "\n";
                    }
                    campo[1] = tmp;
                } else {
                    Iterator itCombo = hrefCB.getOpzioniCombo().iterator();
                    String listaCombo = "";
                    while (itCombo.hasNext()) {
                        BaseBean bb = (BaseBean) itCombo.next();
                        listaCombo += "[   ] " + bb.getDescrizione() + "\n";
                        if (bb.getCodice().equalsIgnoreCase(hrefCB.getValoreUtente())) {
                            campo[1] = bb.getDescrizione();
                            //break;
                        }
                    }
                    campo[4] = listaCombo;
                }
            } else if (campo[2].equalsIgnoreCase("R") || campo[2].equalsIgnoreCase("C")) {
                if (hrefCB != null && hrefCB.getValoreUtente() != null && hrefCB.getValoreUtente().indexOf("|@|") >= 0) {
                    campo[3] = "Y";
                    List valoriMultipli = parseValoriCampiMultipli(hrefCB.getValoreUtente());
                    Iterator itValMult = valoriMultipli.iterator();
                    String tmp = campo[0] + "\n\n";
                    while (itValMult.hasNext()) {
                        Object obj = itValMult.next();
                        tmp += (obj.equals("null") ? "[   ]" : "[ x ]") + "\n";
                    }
                    for (int i = 0; i < 5; i++) {
                        tmp += "[   ]\n";
                    }
                    campo[1] = tmp;
                } else if (hrefCB.getNumCampo() == 1) {
                    campo[3] = "Y";
                    String tmp = campo[0] + "\n\n";
                    for (int i = 0; i < 5; i++) {
                        tmp += "[   ]\n";
                    }
                    campo[1] = tmp;
                } else {
                    campo[1] = hrefCB.getValoreUtente();
                }
            } else {
                if (hrefCB != null && hrefCB.getValoreUtente() != null && hrefCB.getValoreUtente().indexOf("|@|") >= 0) {
                    campo[3] = "Y";
                    List valoriMultipli = parseValoriCampiMultipli(hrefCB.getValoreUtente());
                    Iterator itValMult = valoriMultipli.iterator();
                    String tmp = campo[0] + "\n\n";
                    /*while(itValMult.hasNext()){
                    Object obj = itValMult.next();
                    tmp += ((obj == null || obj.equals("null") || obj.equals(""))?"___________":obj)+"\n";
                    }*/
                    for (int i = 0; i < 5; i++) {
                        tmp += "___________\n";
                    }
                    campo[1] = tmp;
                } else if (hrefCB.getNumCampo() == 1) {
                    campo[3] = "Y";
                    String tmp = campo[0] + "\n\n";
                    for (int i = 0; i < 5; i++) {
                        tmp += "___________\n";
                    }
                    campo[1] = tmp;
                } else {
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
                retVal.add(valore.substring(0, valore.indexOf("|@|")));
                valore = valore.substring(valore.indexOf("|@|") + 3);
                continua = valore.indexOf("|@|") >= 0;
                if (!continua) {
                    retVal.add(valore);
                }
            }
        }
        int diff = livelloCampiMultili - retVal.size();
        if (diff != 0) {
            if (diff > 0) {
                for (int i = 0; i < diff; i++) {
                    retVal.add("");
                }
            } else {
                for (int i = 0; i < diff * (-1); i++) {
                    retVal.remove(retVal.size() - 1);
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
