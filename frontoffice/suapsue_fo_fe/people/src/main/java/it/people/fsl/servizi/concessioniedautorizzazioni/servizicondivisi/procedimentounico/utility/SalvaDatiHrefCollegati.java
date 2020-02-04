/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility;

import it.gruppoinit.commons.Utilities;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.HrefCampiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean;
import it.people.wrappers.IRequestWrapper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Piergiorgio
 */
public class SalvaDatiHrefCollegati {

    public static void salvaDatiHrefCollegati(String href, ProcessData dataForm, IRequestWrapper request) {
        HashMap valoriIncrociati = new HashMap();
        HashMap hmCheckRaggruppati = new HashMap();
        HashMap hm = dataForm.getListaHref();
        SezioneCompilabileBean sezComp = (SezioneCompilabileBean) hm.get(href);
        boolean complete = true;
        if (sezComp != null) {
            List listaHrefCampiBean = sezComp.getCampi();
            Iterator it = listaHrefCampiBean.iterator();

            while (it.hasNext()) {
                HrefCampiBean hrefCampi = (HrefCampiBean) it.next();
                String nomeCampo = hrefCampi.getNome();
                if (!hrefCampi.getTipo().equalsIgnoreCase("R")) {
                    //String valore = request.getParameter(nomeCampo);
                    String valore = hrefCampi.getValoreUtente();
                    boolean continua = true;

                    //hrefCampi.setValoreUtente(valore);
                    if ((valore == null || valore.equalsIgnoreCase(""))
                            && hrefCampi.getControllo() != null
                            && !hrefCampi.getControllo().equalsIgnoreCase("")) {
                        if (hrefCampi.getTipo().equalsIgnoreCase("C") && hrefCampi.getRaggruppamento_check() != null && !hrefCampi.getRaggruppamento_check().equalsIgnoreCase("")) {
                            if (hmCheckRaggruppati.get(hrefCampi.getRaggruppamento_check()) == null) {
                                // controllo se il checkbox � collegato a qualche radiobutton
                                if (Utilities.isset(hrefCampi.getCampo_collegato())) {
                                    //String radioButtonCollegato = request.getParameter(hrefCampi.getCampo_collegato());
                                    String radioButtonCollegato = hrefCampi.getCampo_collegato();
                                    if (radioButtonCollegato != null && radioButtonCollegato.equalsIgnoreCase(hrefCampi.getVal_campo_collegato())) {
                                        hmCheckRaggruppati.put(hrefCampi.getRaggruppamento_check(), "N");
                                    }
                                } else {
                                    hmCheckRaggruppati.put(hrefCampi.getRaggruppamento_check(), "N");
                                }
                            }
                            //complete = true;
                        } else if (hrefCampi.getTipo().equalsIgnoreCase("I")
                                && hrefCampi.getCampo_collegato() != null && !hrefCampi.getCampo_collegato().equalsIgnoreCase("")
                                && hrefCampi.getVal_campo_collegato() != null && !hrefCampi.getVal_campo_collegato().equalsIgnoreCase("")) {
                            //String valCampoCollegato = request.getParameter(hrefCampi.getCampo_collegato());
                            String valCampoCollegato = hrefCampi.getCampo_collegato();
                            if (valCampoCollegato != null && valCampoCollegato.equalsIgnoreCase(hrefCampi.getVal_campo_collegato())) {
                                complete = false;
                            }
                        } else {
                            complete = false;
                        }
                    } else {
                        if (hrefCampi.getTipo().equalsIgnoreCase("C") && hrefCampi.getRaggruppamento_check() != null && !hrefCampi.getRaggruppamento_check().equalsIgnoreCase("")) {
                            hmCheckRaggruppati.put(hrefCampi.getRaggruppamento_check(), "Y");
                        }
                    }
                } else {
                    //String radioSelezionato = request.getParameter(nomeCampo);
                    String radioSelezionato = hrefCampi.getValore();
                    if (radioSelezionato != null && radioSelezionato.equalsIgnoreCase(hrefCampi.getValoreUtente())) {
                        hrefCampi.setValoreUtente(radioSelezionato);
                    } else {
                        hrefCampi.setValoreUtente(null);
                    }
                }
            }
            sezComp.setHtml(HtmlRenderer.costruisciStringaHtml(sezComp, false, "SectionText", null, null, null, null, null, null, dataForm, request));
            sezComp.setHtmlEditable(HtmlRenderer.costruisciStringaHtml(sezComp, true, "SectionText", "TableRow0", "96%", "0", "6", null, "25", dataForm, request));

            // Gestione check raggruppati
            if (hmCheckRaggruppati.size() > 0) {
                Set key = hmCheckRaggruppati.keySet();
                Iterator itKey = key.iterator();
                while (itKey.hasNext()) {
                    String value = (String) hmCheckRaggruppati.get((String) itKey.next());
                    if (value != null && value.equalsIgnoreCase("N")) {
                        complete = false;
                    }
                }
            }
            sezComp.setComplete(complete);

        }

    }
}
