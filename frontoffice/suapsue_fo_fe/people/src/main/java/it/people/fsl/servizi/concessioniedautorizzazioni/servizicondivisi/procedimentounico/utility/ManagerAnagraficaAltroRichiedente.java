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
import it.people.IValidationErrors;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AnagraficaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.HrefCampiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean;
import it.people.wrappers.IRequestWrapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManagerAnagraficaAltroRichiedente {

    public String getHtmlNonCompilabileInizialeAltroRichiedente(AnagraficaBean anagraficaAltroRichiedente, IRequestWrapper request, int index) {
        SezioneCompilabileBean scb = new SezioneCompilabileBean();
        BuilderHtml builder = new BuilderHtml();
        String html = "";
        int numRiga = 0;
        int maxcolonna = 0;
        for (Iterator iterator = anagraficaAltroRichiedente.getListaCampi().iterator(); iterator.hasNext();) {
            HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator.next();
            if ((campoAnagrafica.getCampo_collegato() == null || campoAnagrafica.getCampo_collegato().equalsIgnoreCase("")) && (campoAnagrafica.getVal_campo_collegato() == null || campoAnagrafica.getVal_campo_collegato().equalsIgnoreCase(""))) {
                //if (campoAnagrafica.getLivello()==1) {
                if (campoAnagrafica.getRiga() > numRiga) {
                    numRiga = campoAnagrafica.getRiga();
                }
                if (campoAnagrafica.getPosizione() > maxcolonna) {
                    maxcolonna = campoAnagrafica.getPosizione();
                }
                scb.addCampi(campoAnagrafica);
            }
        }
        // se provengo giÃ  da una sezione dell'anagrafica devo sostituire i valori iniziali con quelli inseriri dall'utente
        if (anagraficaAltroRichiedente.getListaCampiStep() != null) {
            for (Iterator iterator = scb.getCampi().iterator(); iterator.hasNext();) {
                HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator.next();
                for (Iterator iterator2 = anagraficaAltroRichiedente.getListaCampiStep().iterator(); iterator2.hasNext();) {
                    HrefCampiBean campoAnagraficaValorizzato = (HrefCampiBean) iterator2.next();
                    if (campoAnagrafica.getContatore().equalsIgnoreCase(campoAnagraficaValorizzato.getContatore())
                            && campoAnagrafica.getNome().equalsIgnoreCase(campoAnagraficaValorizzato.getNome())) {
                        campoAnagrafica.setValoreUtente(campoAnagraficaValorizzato.getValoreUtente());
                    }
                }
            }
        }
        scb.setRowCount(numRiga);
        scb.setTdCount(maxcolonna);
        scb.setTitolo("");
        scb.setDescrizione("");
        scb.setPiedeHref("");
        scb.setHref("ANAG_" + index);
        html = builder.generateHtmlNonCompilabile(null, scb, request);
        return html;
    }

    public void genereteStepAnagraficaAltroRichiedente(ProcessData dataForm, IRequestWrapper request, int index) {
        SezioneCompilabileBean scb = new SezioneCompilabileBean();
        BuilderHtml builder = new BuilderHtml();
        String html = "";
        if (((AnagraficaBean) (dataForm.getAltriRichiedenti().get(index))).getListaCampiStep() == null || ((AnagraficaBean) (dataForm.getAltriRichiedenti().get(index))).getListaCampiStep().size() == 0) {
            // siamo sulla radice dell'albero dell'anagrafica
            int numRiga = 0;
            int maxcolonna = 0;
            for (Iterator iterator = ((AnagraficaBean) (dataForm.getAltriRichiedenti().get(index))).getListaCampi().iterator(); iterator.hasNext();) {
                HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator.next();
                if ((campoAnagrafica.getCampo_collegato() == null || campoAnagrafica.getCampo_collegato().equalsIgnoreCase("")) && (campoAnagrafica.getVal_campo_collegato() == null || campoAnagrafica.getVal_campo_collegato().equalsIgnoreCase("")) && campoAnagrafica.getLivello() == 1) {
                    //if (campoAnagrafica.getLivello()==1) {
                    if (campoAnagrafica.getRiga() > numRiga) {
                        numRiga = campoAnagrafica.getRiga();
                    }
                    if (campoAnagrafica.getPosizione() > maxcolonna) {
                        maxcolonna = campoAnagrafica.getPosizione();
                    }
                    scb.addCampi(campoAnagrafica);
                    ((AnagraficaBean) (dataForm.getAltriRichiedenti().get(index))).addListaCampiStep(campoAnagrafica);
                }
            }
            scb.setRowCount(numRiga);
            scb.setTdCount(maxcolonna);
            scb.setTitolo("");
            scb.setPiedeHref("");
            scb.setHref("ANAG_" + index + "");
            html = builder.generateHtmlCompilabile(dataForm, scb, request);
            //request.setAttribute("AVANTI", "true");

            ((AnagraficaBean) (dataForm.getAltriRichiedenti().get(index))).setHtmlStepAttuale(html);
        } else {
            int numRiga = 0;
            int maxcolonna = 0;
            ArrayList listaCampiStep = ((AnagraficaBean) dataForm.getAltriRichiedenti().get(index)).getListaCampiStep();
            Iterator iterator = listaCampiStep.iterator();
            while (iterator.hasNext()) {
//             for (Iterator iterator = ((AnagraficaBean)(dataForm.getAltriRichiedenti().get(index))).getListaCampiStep().iterator(); iterator.hasNext();) {
                HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator.next();
//				if ((campoAnagrafica.getCampo_collegato()==null || campoAnagrafica.getCampo_collegato().equalsIgnoreCase("")) && (campoAnagrafica.getVal_campo_collegato()==null || campoAnagrafica.getVal_campo_collegato().equalsIgnoreCase("")) && campoAnagrafica.getLivello()==1) {
                //if (campoAnagrafica.getLivello()==1) {
                if (campoAnagrafica.getRiga() > numRiga) {
                    numRiga = campoAnagrafica.getRiga();
                }
                if (campoAnagrafica.getPosizione() > maxcolonna) {
                    maxcolonna = campoAnagrafica.getPosizione();
                }
                scb.addCampi(campoAnagrafica);
//				}
//			}
            }
            scb.setRowCount(numRiga);
            scb.setTdCount(maxcolonna);
            scb.setTitolo("");
            scb.setPiedeHref("");
            scb.setHref("ANAG_" + index + "");
            html = builder.generateHtmlCompilabile(dataForm, scb, request);
            //request.setAttribute("AVANTI", "true");

            ((AnagraficaBean) (dataForm.getAltriRichiedenti().get(index))).setHtmlStepAttuale(html);
        }

    }

    public void salvaDatiAnagAltriRich(IRequestWrapper request, ProcessData dataForm, AnagraficaBean an) {
        if (!an.isFineCompilazione()) {
            for (Iterator iterator = an.getListaCampiStep().iterator(); iterator.hasNext();) {
                HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator.next();

                if (campoAnagrafica.getTipo().equalsIgnoreCase("R")) {
                    String ret = (String) request.getParameter("ANAG_" + an.getCodice() + "_" + campoAnagrafica.getNome());
                    //System.out.println("Campo "+campoAnagrafica.getNome()+" : "+ret);
                    if (ret != null && campoAnagrafica.getValore().equalsIgnoreCase(ret)) {
                        campoAnagrafica.setValoreUtente(ret);
                    } else {
                        campoAnagrafica.setValoreUtente("");
                    }
                } else {
                    String ret = (String) request.getParameter("ANAG_" + an.getCodice() + "_" + campoAnagrafica.getNome());
                    //System.out.println("Campo "+campoAnagrafica.getNome()+" : "+ret);
                    if (ret != null) {
                        campoAnagrafica.setValoreUtente(ret);
                    } else {
                        campoAnagrafica.setValoreUtente("");
                    }
                }
            }
        }
    }

    public void genereteNextStepAnagraficaAltroRichiedente(AnagraficaBean an, IRequestWrapper request) {
        String html = "";
        SezioneCompilabileBean scb = new SezioneCompilabileBean();
        BuilderHtml builder = new BuilderHtml();
        ArrayList listaCampiStepSuccessivo = new ArrayList();
        salvaHtmlHistory(an, request);
//		if (dataForm.getAnagrafica().getListaCampiStep()==null || dataForm.getAnagrafica().getListaCampiStep().size()==0) { // siamo sulla radice dell'albero
//
//			// recupero lista dei campi radice
//			for (Iterator iterator = dataForm.getAnagrafica().getListaCampi().iterator(); iterator.hasNext();) {
//				HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator.next();
//				if ((campoAnagrafica.getCampo_collegato()==null || campoAnagrafica.getCampo_collegato().equalsIgnoreCase("")) && (campoAnagrafica.getVal_campo_collegato()==null || campoAnagrafica.getVal_campo_collegato().equalsIgnoreCase(""))) {
//					listaCampi.add(campoAnagrafica);
//				}
//			}
        // scorro la lista dei campi dell'anagrafica x trovare quelli attivati dalle scelte fatte allo step precedente
        for (Iterator iterator = an.getListaCampi().iterator(); iterator.hasNext();) {
            HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator.next();
            if ((campoAnagrafica.getCampo_collegato() != null && !campoAnagrafica.getCampo_collegato().equalsIgnoreCase("")) || ((campoAnagrafica.getLivello() == (an.getLivelloAttuale() + 1)) && (campoAnagrafica.getCampo_collegato() == null))) {
                if (checkAttivazione(campoAnagrafica, an.getListaCampiStep()) || ((campoAnagrafica.getLivello() == (an.getLivelloAttuale() + 1)) && (campoAnagrafica.getCampo_collegato() == null))) {
                    listaCampiStepSuccessivo.add(campoAnagrafica);
                }
            }
        }
        if (listaCampiStepSuccessivo.size() > 0) {
            an.setListaCampiStep(listaCampiStepSuccessivo);

            int numRiga = 0;
            int maxcolonna = 0;
            for (Iterator iterator = listaCampiStepSuccessivo.iterator(); iterator.hasNext();) {
                HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator.next();
                if (campoAnagrafica.getRiga() > numRiga) {
                    numRiga = campoAnagrafica.getRiga();
                }
                if (campoAnagrafica.getPosizione() > maxcolonna) {
                    maxcolonna = campoAnagrafica.getPosizione();
                }
                scb.addCampi(campoAnagrafica);
            }
            scb.setRowCount(numRiga);
            scb.setTdCount(maxcolonna);
            scb.setTitolo("");
            scb.setPiedeHref("");
            scb.setHref("ANAG_" + an.getCodice());
            html = builder.generateHtmlCompilabile(null, scb, request);
//	        request.setAttribute("AVANTI", "true");
//	        request.setAttribute("INDIETRO", "true");
            an.setHtmlStepAttuale(html);
        } else {
//			request.setAttribute("INDIETRO", "true");
            an.setFineCompilazione(true);
        }

    }

    public void salvaHtmlHistory(AnagraficaBean an, IRequestWrapper request) {
        if (!an.isFineCompilazione()) {
            String html = "";
            SezioneCompilabileBean scb = new SezioneCompilabileBean();
            BuilderHtml builder = new BuilderHtml();
            int numRiga = 0;
            int maxcolonna = 0;
            for (Iterator iterator = an.getListaCampiStep().iterator(); iterator.hasNext();) {
                HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator.next();
                if (campoAnagrafica.getRiga() > numRiga) {
                    numRiga = campoAnagrafica.getRiga();
                }
                if (campoAnagrafica.getPosizione() > maxcolonna) {
                    maxcolonna = campoAnagrafica.getPosizione();
                }
                scb.addCampi(campoAnagrafica);
            }
            scb.setRowCount(numRiga);
            scb.setTdCount(maxcolonna);
            scb.setTitolo("");
            scb.setPiedeHref("");
            scb.setHref("ANAG_" + an.getCodice());
            html = builder.generateHtmlNonCompilabileAnagrafica(null, scb, request);
            an.addHtmlHistory(html);
        }
    }

    private boolean checkAttivazione(HrefCampiBean campoAnagrafica, ArrayList listaCampi) {
        Iterator it = listaCampi.iterator();
        boolean trovato = false;
        while (it.hasNext() && !trovato) {
            HrefCampiBean campo = (HrefCampiBean) it.next();
            if (campo.getNome().equalsIgnoreCase(campoAnagrafica.getCampo_collegato()) && !campo.getValoreUtente().equalsIgnoreCase("")) {
                if (campo.getTipo().equalsIgnoreCase("R")) {
                    if (campoAnagrafica.getVal_campo_collegato().equalsIgnoreCase(campo.getValoreUtente())) {
                        trovato = true;
                    }
                } else {
                    trovato = true;
                }
            }
        }
        return trovato;
    }

    public boolean checkComplete(AnagraficaBean an, IValidationErrors errors) {
        boolean complete = true;
        for (Iterator iterator = an.getListaCampiStep().iterator(); iterator.hasNext();) {
            HrefCampiBean campo = (HrefCampiBean) iterator.next();
            if (campo.getTipo().equalsIgnoreCase("R") && campo.getControllo() != null && campo.getControllo().equalsIgnoreCase("O")) {
                boolean radiobuttonComplete = checkCompleteRadioButton(campo, an.getListaCampiStep());
                if (!radiobuttonComplete) {
                    if (!errors.get("error.radioButtonAnagrafica").hasNext()) {
                        errors.add("error.radioButtonAnagrafica");
                    }
                }
                complete = complete && radiobuttonComplete;
            } else if (campo.getTipo().equalsIgnoreCase("C")) {
                if ((campo.getControllo() != null && campo.getControllo().equalsIgnoreCase("O")) && (campo.getValoreUtente() == null || campo.getValoreUtente().equalsIgnoreCase(""))) {
                    complete = false;
                    errors.add("error.checkboxAnagrafica", "Spuntare la voce '<i>" + campo.getDescrizione() + "</i> ' per poter proseguire");
                }
            } else if (campo.getTipo().equalsIgnoreCase("I") || campo.getTipo().equalsIgnoreCase("A")) {
                if (campo.getControllo() != null && campo.getControllo().equalsIgnoreCase("O")) {
                    if (campo.getValoreUtente() == null || campo.getValoreUtente().equalsIgnoreCase("")) {
                        complete = false;
                        errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' Ã¨ obbligatorio");
                         // PC - nuovo controllo
                    // } else {
                    }
                }
                if (campo.getValoreUtente() != null && !campo.getValoreUtente().equalsIgnoreCase("")) {
                    // PC - nuovo controllo
                        if (campo.getTp_controllo() != null && campo.getTp_controllo().equalsIgnoreCase("D") && campo.getValoreUtente() != null && !checkValidFormatDate(campo.getValoreUtente())) {
                            complete = false;
                            // PC - nuovo controllo
                            // errors.add("error.inputAnagraficaNull","Il campo '<i>"+campo.getDescrizione()+"</i> ' non correttamente formattato (gg/mm/yyyy) o data non valida");
                            errors.add("error.inputAnagraficaNull", errorMessage(campo.getErr_msg(), "Il campo '<i>" + campo.getDescrizione() + "</i> ' non correttamente formattato (gg/mm/yyyy) o data non valida"));
                            // PC - nuovo controllo
                        } else if (campo.getTp_controllo() != null && campo.getTp_controllo().equalsIgnoreCase("F") && campo.getValoreUtente() != null && !checkValidCodiceFiscale(campo.getValoreUtente())) {
                            complete = false;
                            // PC - nuovo controllo
                            // errors.add("error.inputAnagraficaNull","Il campo '<i>"+campo.getDescrizione()+"</i> ' non Ã¨ un codice fiscale corretto o non valorizzato");
                            errors.add("error.inputAnagraficaNull", errorMessage(campo.getErr_msg(), "Il campo '<i>" + campo.getDescrizione() + "</i> ' non Ã¨ un codice fiscale corretto o non valorizzato"));
                            // PC - nuovo controllo
                        } else if (campo.getTp_controllo() != null && campo.getTp_controllo().equalsIgnoreCase("I") && campo.getValoreUtente() != null && !checkValidPartitaIva(campo.getValoreUtente())) {
                            complete = false;
                            // PC - nuovo controllo
                            // errors.add("error.inputAnagraficaNull","Il campo '<i>"+campo.getDescrizione()+"</i> ' non Ã¨ una partita iva corretta o non valorizzato");
                            errors.add("error.inputAnagraficaNull", errorMessage(campo.getErr_msg(), "Il campo '<i>" + campo.getDescrizione() + "</i> ' non Ã¨ una partita iva corretta o non valorizzato"));
                            // PC - nuovo controllo
                        } // PC - nuovo controllo
                        else if (campo.getTp_controllo() != null && campo.getTp_controllo().equalsIgnoreCase("X") && campo.getValoreUtente() != null && !checkValidRegExp(campo.getValoreUtente(), campo.getPattern())) {
                            complete = false;
                            errors.add("error.inputAnagraficaNull", errorMessage(campo.getErr_msg(), "Il campo '<i>" + campo.getDescrizione() + "</i> ' formalmente non corretto"));
                            // PC - nuovo controllo
                        } else if (campo.getTp_controllo() != null && campo.getTp_controllo().equalsIgnoreCase("N") && campo.getValoreUtente() != null && !checkValidNumeric(campo.getValoreUtente(), campo.getLunghezza(), campo.getDecimali())) {
                            complete = false;
                            // PC - nuovo controllo
                            // errors.add("error.inputAnagraficaNull","Il campo '<i>"+campo.getDescrizione()+"</i> ' deve essere numerico (massima lunghezza "+campo.getLunghezza()+" di cui "+campo.getDecimali()+" decimali");
                            errors.add("error.inputAnagraficaNull", errorMessage(campo.getErr_msg(), "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere numerico (massima lunghezza " + campo.getLunghezza() + " di cui " + campo.getDecimali() + " decimali"));
                            // PC - nuovo controllo
                        } else if (campo.getTp_controllo() != null && campo.getTp_controllo().equalsIgnoreCase("C") && campo.getValoreUtente() != null && !checkValidCodiceFiscalePIVA(campo.getValoreUtente())) {
                            complete = false;
                            // PC - nuovo controllo
                            // errors.add("error.inputAnagraficaNull","Il campo '<i>"+campo.getDescrizione()+"</i> ' non Ã¨ un codice fiscale corretto o non valorizzato");
                            errors.add("error.inputAnagraficaNull", errorMessage(campo.getErr_msg(), "Il campo '<i>" + campo.getDescrizione() + "</i> ' non Ã¨ un codice fiscale o partita IVA corretto o non valorizzato"));
                            // PC - nuovo controllo
                        }
                    }

            // PC - nuovo controllo
            // }
            // PC - nuovo controllo

            } else if (campo.getTipo().equalsIgnoreCase("L")) {
                if ((campo.getControllo() != null && campo.getControllo().equalsIgnoreCase("O")) && (campo.getValoreUtente() == null || campo.getValoreUtente().equalsIgnoreCase(""))) {
                    complete = false;
                    errors.add("error.combolistNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' non Ã¨ valorizzato");
                }
                if (campo.getControllo() != null && campo.getControllo().equalsIgnoreCase("O") && campo.getValoreUtente() != null && !campo.getValoreUtente().equalsIgnoreCase("")) {
                    if (campo.getValoreUtente().indexOf("ALTRO#") != -1) {
                        String[] token = campo.getValoreUtente().split("#");
                        if (token != null && token.length > 1) {
                            boolean trovato = false;
                            Iterator itt = an.getListaCampiStep().iterator();
                            HrefCampiBean campoAnag = null;
                            while (itt.hasNext() && !trovato) {
                                campoAnag = (HrefCampiBean) itt.next();
                                if (campoAnag.getNome().equalsIgnoreCase(token[1])) {
                                    trovato = true;
                                }
                            }
                            if (!trovato || campoAnag.getValoreUtente() == null || campoAnag.getValoreUtente().equalsIgnoreCase("")) {
                                complete = false;
                                errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campoAnag.getDescrizione() + "</i> ' in base alle scelte effettuate deve essere valorizzato");
                            }
                        }
                    }
                }
            } else if (campo.getTipo().equalsIgnoreCase("N")) {
                if ((campo.getControllo() != null && campo.getControllo().equalsIgnoreCase("O")) && (campo.getValoreUtente() == null || campo.getValoreUtente().equalsIgnoreCase(""))) {
                    complete = false;
                    errors.add("error.hiddenAnagraficaNull", "Alcuni campi obbligatori risultanto non compilati");
                }
            }
        }
        return complete;
    }

// PC - nuovo controllo
    private boolean checkValidRegExp(String valoreUtente, String pattern) {
        boolean ret = true;
        if (pattern != null) {
            if (pattern.length() > 0) {
                Pattern p = Pattern.compile(pattern);
                Matcher matcher = p.matcher(valoreUtente);
                if (!matcher.matches()) {
                    ret = false;
                }
            }
        }
        return ret;
    }

    private String errorMessage(String msgDb, String msgDefault) {
        if (msgDb != null) {
            return msgDb;
        } else {
            return msgDefault;
        }
    }

    private boolean checkValidCodiceFiscalePIVA(String valoreUtente) {
        boolean ret = true;
        if (valoreUtente != null) {
            if (valoreUtente.length() == 11) {
                ret = checkValidPartitaIva(valoreUtente);
            } else if (valoreUtente.length() == 16) {
                ret = checkValidCodiceFiscale(valoreUtente);
            } else {
                ret = false;
            }
        }
        return ret;
    }
// PC - nuovo controllo

    private boolean checkValidNumeric(String valoreUtente, int lunghezza, int decimali) {
        if (valoreUtente.indexOf(',') > 0) {
            String[] temp = valoreUtente.split(",");
            if (temp[0].length() > (lunghezza - decimali)) {
                return false;
            } else if (temp[1].length() > decimali) {
                return false;
            }
        } else {
            if (valoreUtente.length() > (lunghezza - decimali)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkValidPartitaIva(String valoreUtente) {
        if (valoreUtente.length() != 11) {
            return false;
        }
        return true;
    }

    private boolean checkValidCodiceFiscale(String valoreUtente) {
        if (valoreUtente.length() != 16) {
            return false;
        }
        return true;
    }

    private boolean checkValidFormatDate(String valoreUtente) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date testDate = null;
        try {
            sdf.setLenient(false);
            testDate = sdf.parse(valoreUtente);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    private boolean checkCompleteRadioButton(HrefCampiBean campo, ArrayList listaCampi) {
        boolean complete = false;
        for (Iterator iterator = listaCampi.iterator(); iterator.hasNext();) {
            HrefCampiBean c = (HrefCampiBean) iterator.next();
            if (c.getNome().equalsIgnoreCase(campo.getNome())) {
                complete = (complete || it.gruppoinit.commons.Utilities.isset(c.getValoreUtente()));
            }
        }
        return complete;
    }

    public void refresh(AnagraficaBean an, IRequestWrapper request) {
        String html = "";
        SezioneCompilabileBean scb = new SezioneCompilabileBean();
        BuilderHtml builder = new BuilderHtml();
        int numRiga = 0;
        int maxcolonna = 0;
        for (Iterator iterator = an.getListaCampiStep().iterator(); iterator.hasNext();) {
            HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator.next();
//			if ((campoAnagrafica.getCampo_collegato()==null || campoAnagrafica.getCampo_collegato().equalsIgnoreCase("")) && (campoAnagrafica.getVal_campo_collegato()==null || campoAnagrafica.getVal_campo_collegato().equalsIgnoreCase(""))) {
            if (campoAnagrafica.getRiga() > numRiga) {
                numRiga = campoAnagrafica.getRiga();
            }
            if (campoAnagrafica.getPosizione() > maxcolonna) {
                maxcolonna = campoAnagrafica.getPosizione();
            }
            scb.addCampi(campoAnagrafica);
//			}
        }
        scb.setRowCount(numRiga);
        scb.setTdCount(maxcolonna);
        scb.setTitolo("");
        scb.setPiedeHref("");
        scb.setHref("ANAG_" + an.getCodice());
        html = builder.generateHtmlCompilabile(null, scb, request);
        an.setHtmlStepAttuale(html);

    }

    public void genereteBackStepAnagraficaAltroRichiedente(ProcessData dataForm, IRequestWrapper request, AnagraficaBean an) {

        an.removeHtmlHistory();
        if (!an.isFineCompilazione()) {
            ArrayList listaCampiPadre = new ArrayList();
            boolean fine = false;
            for (Iterator iterator = an.getListaCampiStep().iterator(); iterator.hasNext();) {
                HrefCampiBean campo = (HrefCampiBean) iterator.next();
                Iterator it2 = an.getListaCampi().iterator();
                while (it2.hasNext() && !fine) {
                    HrefCampiBean c = (HrefCampiBean) it2.next();
                    if (campo.getCampo_collegato() != null && campo.getCampo_collegato().equalsIgnoreCase(c.getNome())) {
                        if (c.getTipo().equalsIgnoreCase("R")) {
                            if (campo.getVal_campo_collegato().equalsIgnoreCase(c.getValoreUtente())) {
                                fine = true;
                                listaCampiPadre.add(c);
                            }
                        } else {
                            fine = true;
                            listaCampiPadre.add(c);
                        }
                    }
                }
            }

            if (listaCampiPadre == null || listaCampiPadre.size() == 0) {
                String html = "";
                SezioneCompilabileBean scb = new SezioneCompilabileBean();
                BuilderHtml builder = new BuilderHtml();
                int numRiga = 0;
                int maxcolonna = 0;
                for (Iterator iterator2 = an.getListaCampi().iterator(); iterator2.hasNext();) {
                    HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator2.next();
                    if (campoAnagrafica.getLivello() == (an.getLivelloAttuale() - 1) && campoAnagrafica.getCampo_collegato() == null) {
                        if (campoAnagrafica.getRiga() > numRiga) {
                            numRiga = campoAnagrafica.getRiga();
                        }
                        if (campoAnagrafica.getPosizione() > maxcolonna) {
                            maxcolonna = campoAnagrafica.getPosizione();
                        }
                        scb.addCampi(campoAnagrafica);
                    }
                }
                scb.setRowCount(numRiga);
                scb.setTdCount(maxcolonna);
                scb.setTitolo("");
                scb.setPiedeHref("");
                scb.setHref("ANAG_" + an.getCodice());
                html = builder.generateHtmlCompilabile(dataForm, scb, request);
                an.setListaCampiStep(scb.getCampi());
                an.setHtmlStepAttuale(html);
            } else {

                for (Iterator iterator = listaCampiPadre.iterator(); iterator.hasNext();) {
                    HrefCampiBean campo = (HrefCampiBean) iterator.next();
                    if (campo.getCampo_collegato() == null || campo.getCampo_collegato().equalsIgnoreCase("")) {
                        // recupero la root dell'anagrafica
                        String html = "";
                        SezioneCompilabileBean scb = new SezioneCompilabileBean();
                        BuilderHtml builder = new BuilderHtml();
                        int numRiga = 0;
                        int maxcolonna = 0;
                        for (Iterator iterator2 = an.getListaCampi().iterator(); iterator2.hasNext();) {
                            HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator2.next();
                            if ((campoAnagrafica.getCampo_collegato() == null || campoAnagrafica.getCampo_collegato().equalsIgnoreCase("")) && (campoAnagrafica.getVal_campo_collegato() == null || campoAnagrafica.getVal_campo_collegato().equalsIgnoreCase(""))) {
                                if (campoAnagrafica.getRiga() > numRiga) {
                                    numRiga = campoAnagrafica.getRiga();
                                }
                                if (campoAnagrafica.getPosizione() > maxcolonna) {
                                    maxcolonna = campoAnagrafica.getPosizione();
                                }
                                scb.addCampi(campoAnagrafica);

                            }
                        }
                        scb.setRowCount(numRiga);
                        scb.setTdCount(maxcolonna);
                        scb.setTitolo("");
                        scb.setPiedeHref("");
                        scb.setHref("ANAG_" + an.getCodice());
                        html = builder.generateHtmlCompilabile(dataForm, scb, request);
                        an.setListaCampiStep(scb.getCampi());
                        an.setHtmlStepAttuale(html);
                    } else {
                        String html = "";
                        SezioneCompilabileBean scb = new SezioneCompilabileBean();
                        BuilderHtml builder = new BuilderHtml();
                        int numRiga = 0;
                        int maxcolonna = 0;
                        for (Iterator iterator2 = an.getListaCampi().iterator(); iterator2.hasNext();) {
                            HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator2.next();
                            if ((campoAnagrafica.getCampo_collegato() != null && campoAnagrafica.getCampo_collegato().equalsIgnoreCase(campo.getCampo_collegato()))
                                    && ((campoAnagrafica.getVal_campo_collegato() == null && campo.getVal_campo_collegato() == null)
                                    || campoAnagrafica.getVal_campo_collegato().equalsIgnoreCase(campo.getVal_campo_collegato()))) {
                                if (campoAnagrafica.getRiga() > numRiga) {
                                    numRiga = campoAnagrafica.getRiga();
                                }
                                if (campoAnagrafica.getPosizione() > maxcolonna) {
                                    maxcolonna = campoAnagrafica.getPosizione();
                                }
                                scb.addCampi(campoAnagrafica);

                            }
                        }
                        for (Iterator iterator2 = an.getListaCampi().iterator(); iterator2.hasNext();) {
                            HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator2.next();
                            if (campoAnagrafica.getLivello() == (an.getLivelloAttuale() - 1) && campoAnagrafica.getCampo_collegato() == null) {
                                if (campoAnagrafica.getRiga() > numRiga) {
                                    numRiga = campoAnagrafica.getRiga();
                                }
                                if (campoAnagrafica.getPosizione() > maxcolonna) {
                                    maxcolonna = campoAnagrafica.getPosizione();
                                }
                                scb.addCampi(campoAnagrafica);
                            }
                        }
                        scb.setRowCount(numRiga);
                        scb.setTdCount(maxcolonna);
                        scb.setTitolo("");
                        scb.setPiedeHref("");
                        scb.setHref("ANAG_" + an.getCodice());
                        html = builder.generateHtmlCompilabile(dataForm, scb, request);
                        an.setListaCampiStep(scb.getCampi());
                        an.setHtmlStepAttuale(html);
                    }
                }
            }
        } else {
            an.setFineCompilazione(false);
            String html = "";
            SezioneCompilabileBean scb = new SezioneCompilabileBean();
            BuilderHtml builder = new BuilderHtml();
            int numRiga = 0;
            int maxcolonna = 0;
            for (Iterator iterator2 = an.getListaCampiStep().iterator(); iterator2.hasNext();) {
                HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator2.next();
                if (campoAnagrafica.getRiga() > numRiga) {
                    numRiga = campoAnagrafica.getRiga();
                }
                if (campoAnagrafica.getPosizione() > maxcolonna) {
                    maxcolonna = campoAnagrafica.getPosizione();
                }
                scb.addCampi(campoAnagrafica);
            }
            scb.setRowCount(numRiga);
            scb.setTdCount(maxcolonna);
            scb.setTitolo("");
            scb.setPiedeHref("");
            scb.setHref("ANAG_" + an.getCodice());
            html = builder.generateHtmlCompilabile(dataForm, scb, request);
            an.setHtmlStepAttuale(html);
        }
    }
}
