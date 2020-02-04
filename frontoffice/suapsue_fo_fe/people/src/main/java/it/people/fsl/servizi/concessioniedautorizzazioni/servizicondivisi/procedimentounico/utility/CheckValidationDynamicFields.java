/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility;

import it.people.IValidationErrors;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.HrefCampiBean;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author piergiorgio
 */
public class CheckValidationDynamicFields {

    public static boolean CheckValidationDynamicFields(IValidationErrors errors, ArrayList listaCampi, boolean isAnagrafica) {
        boolean complete = true;
        HashMap raggruppamenti = caricaRaggruppamentoCheck(listaCampi);
        for (Iterator iterator = listaCampi.iterator(); iterator.hasNext();) {
            HrefCampiBean campo = (HrefCampiBean) iterator.next();
            if (controllaCollegato(campo, listaCampi, isAnagrafica)) {
                if (campo.getTipo().equalsIgnoreCase("R") && campo.getControllo() != null && campo.getControllo().equalsIgnoreCase("O")) {
                    boolean radiobuttonComplete = checkCompleteRadioButton(campo, listaCampi);
                    if (!radiobuttonComplete) {
                        if (!errors.get("error.radioButtonAnagrafica").hasNext()) {
                            errors.add("error.radioButtonAnagrafica");
                        }
                    }
                    complete = complete && radiobuttonComplete;
                } else if (campo.getTipo().equalsIgnoreCase("C")) {
                    if (campo.getRaggruppamento_check() != null && !campo.getRaggruppamento_check().equals("") && raggruppamenti.containsKey(campo.getRaggruppamento_check())) {
                        if (campo.getControllo() != null && campo.getControllo().equalsIgnoreCase("O") && campo.getValoreUtente() != null && !campo.getValoreUtente().equalsIgnoreCase("")) {
                            raggruppamenti.put(campo.getRaggruppamento_check(), "Y");
                        }
                    } else {
                        if (campo.getControllo() != null && campo.getControllo().equalsIgnoreCase("O") && (campo.getValoreUtente() == null || campo.getValoreUtente().equalsIgnoreCase(""))) {
                            complete = false;
                            errors.add("error.checkboxAnagrafica", campo.getDescrizione());
                        }
                    }

                } else if (campo.getTipo().equalsIgnoreCase("I") || campo.getTipo().equalsIgnoreCase("A")) {
                    if (campo.getControllo() != null && campo.getControllo().equalsIgnoreCase("O")) {
                        if (campo.getValoreUtente() == null || campo.getValoreUtente().equalsIgnoreCase("")) {
                            complete = false;
                            errors.add("error.inputAnagraficaNullGeneric", campo.getDescrizione());
                        }
                    }
                    if (campo.getValoreUtente() != null && !campo.getValoreUtente().equalsIgnoreCase("")) {
                        // PC - nuovo controllo
                        if (campo.getTp_controllo() != null && campo.getTp_controllo().equalsIgnoreCase("D") && !checkValidFormatDate(campo.getValoreUtente())) {
                            complete = false;
                            errorMessage(errors, "error.inputAnagraficaNull", campo.getErr_msg(), "error.inputAnagraficaNullData", campo.getDescrizione());
                        } else if (campo.getTp_controllo() != null && campo.getTp_controllo().equalsIgnoreCase("F") && !checkValidCodiceFiscale(campo.getValoreUtente())) {
                            complete = false;
                            errorMessage(errors, "error.inputAnagraficaNull", campo.getErr_msg(), "error.inputAnagraficaNullCodiceFiscale", campo.getDescrizione());
                        } else if (campo.getTp_controllo() != null && campo.getTp_controllo().equalsIgnoreCase("I") && !checkValidPartitaIva(campo.getValoreUtente())) {
                            complete = false;
                            errorMessage(errors, "error.inputAnagraficaNull", campo.getErr_msg(), "error.inputAnagraficaNullPartitaIva", campo.getDescrizione());
                        } else if (campo.getTp_controllo() != null && campo.getTp_controllo().equalsIgnoreCase("X") && !checkValidRegExp(campo.getValoreUtente(), campo.getPattern())) {
                            complete = false;
                            errorMessage(errors, "error.inputAnagraficaNull", campo.getErr_msg(), "error.inputAnagraficaNullNonCorretto", campo.getDescrizione());
                        } else if (campo.getTp_controllo() != null && campo.getTp_controllo().equalsIgnoreCase("N") && !checkValidNumeric(campo.getValoreUtente(), campo.getLunghezza(), campo.getDecimali())) {
                            complete = false;
                            errorMessage(errors, "error.inputAnagraficaNull", campo.getErr_msg(), "error.inputAnagraficaNullNumerico", campo.getDescrizione(), "" + campo.getLunghezza(), "" + campo.getDecimali());
                        } else if (campo.getTp_controllo() != null && campo.getTp_controllo().equalsIgnoreCase("C") && !checkValidCodiceFiscalePIVA(campo.getValoreUtente())) {
                            complete = false;
                            errorMessage(errors, "error.inputAnagraficaNull", campo.getErr_msg(), "error.inputAnagraficaNullCodiceFiscalePartitaIva", campo.getDescrizione());
                        }
                    }
                } else if (campo.getTipo().equalsIgnoreCase("L")) {
                    if (campo.getControllo() != null && campo.getControllo().equalsIgnoreCase("O") && (campo.getValoreUtente() == null || campo.getValoreUtente().equalsIgnoreCase(""))) {
                        complete = false;
                        errors.add("error.combolistNullGeneric", campo.getDescrizione());
                    }
                    if (campo.getControllo() != null && campo.getControllo().equalsIgnoreCase("O") && campo.getValoreUtente() != null && !campo.getValoreUtente().equalsIgnoreCase("")) {
                        if (campo.getValoreUtente().indexOf("ALTRO#") != -1) {
                            String[] token = campo.getValoreUtente().split("#");
                            if (token != null && token.length > 1) {
                                boolean trovato = false;
                                Iterator itt = listaCampi.iterator();
                                HrefCampiBean campoAnag = null;
                                while (itt.hasNext() && !trovato) {
                                    campoAnag = (HrefCampiBean) itt.next();
                                    if (campoAnag.getNome().equalsIgnoreCase(token[1])) {
                                        trovato = true;
                                    }
                                }
                                if (!trovato || campoAnag.getValoreUtente() == null || campoAnag.getValoreUtente().equalsIgnoreCase("")) {
                                    complete = false;
                                    errors.add("error.inputAnagraficaNullAltro", campoAnag.getDescrizione());
                                }
                            }
                        }
                    }
                } else if (campo.getTipo().equalsIgnoreCase("N")) {
                    if (campo.getControllo() != null && campo.getControllo().equalsIgnoreCase("O") && (campo.getValoreUtente() == null || campo.getValoreUtente().equalsIgnoreCase(""))) {
                        complete = false;
                        errors.add("error.hiddenAnagraficaNull.tipoN");
                    }
                }
            }
        }
        if (raggruppamenti != null) {
            for (Iterator iterator = raggruppamenti.keySet().iterator(); iterator.hasNext();) {
                if (((String) raggruppamenti.get(iterator.next())).equals("N")) {
                    complete = false;
                    errors.add("error.inputAnagraficaNullRaggruppamento");
                }
            }
        }
        return complete;
    }

    private static boolean checkValidRegExp(String valoreUtente, String pattern) {
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

    private static void errorMessage(IValidationErrors errors, String tipoMsgDb, String msgDb, String tipoMsgDefault, String msgDefault) {
        if (msgDb != null) {
            errors.add(tipoMsgDb, msgDb);
        } else {
            errors.add(tipoMsgDefault, msgDefault);
        }
    }

    private static void errorMessage(IValidationErrors errors, String tipoMsgDb, String msgDb, String tipoMsgDefault, String msgDefault, String msgDefault02, String msgDefault03) {
        if (msgDb != null) {
            errors.add(tipoMsgDb, msgDb);
        } else {
            errors.add(tipoMsgDefault, msgDefault, msgDefault02, msgDefault03);
        }
    }

    private static boolean checkValidCodiceFiscalePIVA(String valoreUtente) {
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

    private static boolean checkValidNumeric(String valoreUtente, int lunghezza, int decimali) {
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

    private static boolean checkValidPartitaIva(String valoreUtente) {
        if (valoreUtente.length() != 11) {
            return false;
        }
        return true;
    }

    private static boolean checkValidCodiceFiscale(String valoreUtente) {
        if (valoreUtente.length() != 16) {
            return false;
        }
        return true;
    }

    private static boolean checkValidFormatDate(String valoreUtente) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date testDate = null;
        try {
            sdf.setLenient(false);
            testDate = sdf.parse(valoreUtente);
//			Calendar cal = Calendar.getInstance();
//			cal.set(Calendar.HOUR_OF_DAY, 0);
//			cal.set(Calendar.MINUTE, 0);
//			cal.set(Calendar.SECOND, 0);
//			cal.set(Calendar.MILLISECOND, 0);
//			if (cal.getTime().after(testDate)) return false;
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    private static boolean checkCompleteRadioButton(HrefCampiBean campo, ArrayList listaCampi) {
        boolean complete = false;
        for (Iterator iterator = listaCampi.iterator(); iterator.hasNext();) {
            HrefCampiBean c = (HrefCampiBean) iterator.next();
            if (c.getNome().equalsIgnoreCase(campo.getNome())) {
                complete = (complete || it.gruppoinit.commons.Utilities.isset(c.getValoreUtente()));
            }
        }
        return complete;
    }

    private static boolean controllaCollegato(HrefCampiBean campo, ArrayList listaCampi, boolean isAnagrafica) {
        if (campo.getCampo_collegato() != null && !campo.getCampo_collegato().equalsIgnoreCase("")) {
            for (Iterator iterator = listaCampi.iterator(); iterator.hasNext();) {
                HrefCampiBean c = (HrefCampiBean) iterator.next();
                if (campo.getCampo_collegato().equalsIgnoreCase(c.getNome()) && campo.getVal_campo_collegato().equalsIgnoreCase(c.getValoreUtente())) {
                    return true;
                }
            }
            if (isAnagrafica) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    private static HashMap caricaRaggruppamentoCheck(ArrayList listaCampi) {
        HashMap raggruppamenti = new HashMap();
        for (Iterator iterator = listaCampi.iterator(); iterator.hasNext();) {
            HrefCampiBean campo = (HrefCampiBean) iterator.next();
            if (campo.getTipo().equalsIgnoreCase("C") && campo.getRaggruppamento_check() != null && !campo.getRaggruppamento_check().equalsIgnoreCase("")) {
//                if (campo.getCampo_collegato() != null && !campo.getCampo_collegato().equalsIgnoreCase("") && campo.getVal_campo_collegato() != null && !campo.getVal_campo_collegato().equalsIgnoreCase("")) {
//                    for (Iterator it = listaCampi.iterator(); it.hasNext();) {
//                        HrefCampiBean c = (HrefCampiBean) it.next();
//                        if (campo.getCampo_collegato().equalsIgnoreCase(c.getNome()) && campo.getVal_campo_collegato().equalsIgnoreCase(c.getValoreUtente())) {
//                            if (campo.getControllo() != null && campo.getControllo().equalsIgnoreCase("O")) {
                                raggruppamenti.put(campo.getRaggruppamento_check(), "N");
//                            }
//                        }
//                    }
//                }
            }
        }
        return raggruppamenti;
    }
}
