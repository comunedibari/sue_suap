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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import it.people.IValidationErrors;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AnagraficaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.HrefCampiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean;
import it.people.fsl.servizi.fiscali.oggetticondivisi.Via;
import it.people.fsl.servizi.oggetticondivisi.profili.ProfiloPersonaFisica;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.core.SiracHelper;
import it.people.wrappers.IRequestWrapper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManagerAnagrafica {

    private static Logger logger = LoggerFactory.getLogger(ManagerAnagrafica.class);

    public void genereteStepAnagrafica(ProcessData dataForm, IRequestWrapper request) {
        SezioneCompilabileBean scb = new SezioneCompilabileBean();
        BuilderHtml builder = new BuilderHtml();
        String html = "";
        if (dataForm.getAnagrafica().getListaCampiStep() == null || dataForm.getAnagrafica().getListaCampiStep().size() == 0) {
            // siamo sulla radice dell'albero dell'anagrafica
            int numRiga = 0;
            int maxcolonna = 0;
            for (Iterator iterator = dataForm.getAnagrafica().getListaCampi().iterator(); iterator.hasNext();) {
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
                    dataForm.getAnagrafica().addListaCampiStep(campoAnagrafica);
                }
            }
            scb.setRowCount(numRiga);
            scb.setTdCount(maxcolonna);
            scb.setTitolo("");
            scb.setPiedeHref("");
            scb.setHref("ANAG");
            html = builder.generateHtmlCompilabile(dataForm, scb, request);
            //request.setAttribute("AVANTI", "true");

            dataForm.getAnagrafica().setHtmlStepAttuale(html);
        }

    }

    public void saveAnagrafica(ProcessData dataForm, IRequestWrapper request) {
    	
    	// La valorizzazione degli HrefCampiBean della propriet� listaCampi di anagrafica 
    	// avviene "automaticamente" in quanto listaCampi e listaCampiStep contengono il riferimento 
    	// allo stesso oggetto
        if (!dataForm.getAnagrafica().isFineCompilazione()) {
            for (Iterator iterator = dataForm.getAnagrafica().getListaCampiStep().iterator(); iterator.hasNext();) {
                HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator.next();

                if (campoAnagrafica.getTipo().equalsIgnoreCase("R")) {
                    String ret = (String) request.getParameter("ANAG_" + campoAnagrafica.getNome());
                    //System.out.println("Campo "+campoAnagrafica.getNome()+" : "+ret);
                    if (ret != null && campoAnagrafica.getValore().equalsIgnoreCase(ret)) {
                        campoAnagrafica.setValoreUtente(ret);
                    } else {
                        campoAnagrafica.setValoreUtente("");
                    }
                } else {
                    String ret = (String) request.getParameter("ANAG_" + campoAnagrafica.getNome());
                    
//            	if (campoAnagrafica.getNome().startsWith("ana020501") || campoAnagrafica.getNome().startsWith("ana020501")) {
//                    System.out.println("Campo "+campoAnagrafica.getNome()+" : "+ret);
//            	}
                    
                    if (ret != null) {
                        campoAnagrafica.setValoreUtente(ret);
                        
//                    	if (campoAnagrafica.getNome().startsWith("ana020501") || campoAnagrafica.getNome().startsWith("ana020501")) {
//                		System.out.println("-----MANAGER ANAGRAFICA SAVE ANAGRAFICA RET = " + ret);
//                    	}
                        
                    } else {
                        campoAnagrafica.setValoreUtente("");
                    }
                }

                // Le propriet� listaCampi e listaCampiStep di dataForm.anagrafica devono contenere lo stesso riferimento 
                // agli oggetti HrefCampiBean. Si veda il metodo saveAnagrafica.
                HrefCampiBean riferimentoCampo = campoAnagrafica;
                Iterator<HrefCampiBean> listaCampiIterator = dataForm.getAnagrafica().getListaCampi().iterator();
                while(listaCampiIterator.hasNext()) {
                	HrefCampiBean listaCampiCampoAnagrafica = listaCampiIterator.next();
                	if (listaCampiCampoAnagrafica.getNome().equalsIgnoreCase(campoAnagrafica.getNome()) &&
                			StringUtils.equalsIgnoreCase(StringUtils.defaultString(campoAnagrafica.getValore()), StringUtils.defaultString(listaCampiCampoAnagrafica.getValore()))
                		) {
                		riferimentoCampo = listaCampiCampoAnagrafica;
                		riferimentoCampo.setValoreUtente(campoAnagrafica.getValoreUtente());
                		break;
                	}
                }
                
            }
        }

        
        for(Enumeration requestParams = request.getParameterNames(); requestParams.hasMoreElements();)
        {
            String requestParameter = (String)requestParams.nextElement();
            if(requestParameter.startsWith("ANAG_") && !dataForm.getRegistryRequestSavedFields().contains(requestParameter.substring("ANAG_".length())))
            {
                if(logger.isDebugEnabled())
                    logger.debug((new StringBuilder("Campo da salvare: ")).append(requestParameter.substring("ANAG_".length())).toString());
                dataForm.getRegistryRequestSavedFields().add(requestParameter.substring("ANAG_".length()));
            }
        }


//		System.out.println("-----MANAGER ANAGRAFICA SAVE ANAGRAFICA");
//		System.out.println("-----CAMPI STEP");
//        for (int i = 0; i < dataForm.getAnagrafica().getListaCampiStep().size(); i++) {
//        	HrefCampiBean campo = (HrefCampiBean)dataForm.getAnagrafica().getListaCampiStep().get(i);
//        	if (campo.getNome().startsWith("ana020501") || campo.getNome().startsWith("ana020501")) {
//        		System.out.println(campo.getNome());
//        		System.out.println(campo.getDescrizione());
//        		System.out.println(campo.getValoreUtente());
//        		System.out.println();
//        	}
//        }
//        
//		System.out.println("-----CAMPI DATA FORM");
//        for (int i = 0; i < dataForm.getAnagrafica().getListaCampi().size(); i++) {
//        	HrefCampiBean campo = (HrefCampiBean)dataForm.getAnagrafica().getListaCampi().get(i);
//        	if (campo.getNome().startsWith("ana020501") || campo.getNome().startsWith("ana020501")) {
//        		System.out.println(campo.getNome());
//        		System.out.println(campo.getDescrizione());
//        		System.out.println(campo.getValoreUtente());
//        		System.out.println();
//        	}
//        }
        
    }

    public boolean checkComplete(ProcessData dataForm, IValidationErrors errors) {
        boolean complete = true;
        /* R2 */
        boolean numeroReaObbligatorio = false;
        boolean dataIscrReaObbligatorio = false;
       /* boolean numeroIscrRIObbligatorio = false;
        boolean dataIscrRIObbligatorio = false;*/
        /* Fine R2 */
        for (Iterator iterator = dataForm.getAnagrafica().getListaCampiStep().iterator(); iterator.hasNext();) {
            HrefCampiBean campo = (HrefCampiBean) iterator.next();
            if (campo.getTipo().equalsIgnoreCase("R") && campo.getControllo() != null && campo.getControllo().equalsIgnoreCase("O")) {
                boolean radiobuttonComplete = checkCompleteRadioButton(campo, dataForm.getAnagrafica().getListaCampiStep());
                if (!radiobuttonComplete) {
                    if (!errors.get("error.radioButtonAnagrafica").hasNext()) {
                        errors.add("error.radioButtonAnagrafica");
                    }
                }
                complete = complete && radiobuttonComplete;
            } else if (campo.getTipo().equalsIgnoreCase("C")) {
                if (campo.getControllo() != null && campo.getControllo().equalsIgnoreCase("O") && (campo.getValoreUtente() == null || campo.getValoreUtente().equalsIgnoreCase(""))) {
                    complete = false;
                    errors.add("error.checkboxAnagrafica", "Spuntare la voce '<i>" + campo.getDescrizione() + "</i> ' per poter proseguire");
                }
            } else if (campo.getTipo().equalsIgnoreCase("I") || campo.getTipo().equalsIgnoreCase("A")) {
                if (campo.getControllo() != null && campo.getControllo().equalsIgnoreCase("O")) {
                    if (campo.getValoreUtente() == null || campo.getValoreUtente().equalsIgnoreCase("")) {
                        complete = false;
                        errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' è obbligatorio");
                        // PC - nuovo controllo
                        // } else {
                    }
                }
                if (campo.getValoreUtente() != null && !campo.getValoreUtente().equalsIgnoreCase("")) {
                    // PC - nuovo controllo
                    if (campo.getTp_controllo() != null && campo.getTp_controllo().equalsIgnoreCase("D") && !checkValidFormatDate(campo.getValoreUtente())) {
                        complete = false;
                        // PC - nuovo controllo
                        // errors.add("error.inputAnagraficaNull","Il campo '<i>"+campo.getDescrizione()+"</i> ' non correttamente formattato (gg/mm/yyyy) o data non valida");
                        errors.add("error.inputAnagraficaNull", errorMessage(campo.getErr_msg(), "Il campo '<i>" + campo.getDescrizione() + "</i> ' non correttamente formattato (gg/mm/yyyy) o data non valida"));
                        // PC - nuovo controllo
                    } else if (campo.getTp_controllo() != null && campo.getTp_controllo().equalsIgnoreCase("F") && !checkValidCodiceFiscale(campo.getValoreUtente())) {
                        complete = false;
                        // PC - nuovo controllo
                        // errors.add("error.inputAnagraficaNull","Il campo '<i>"+campo.getDescrizione()+"</i> ' non Ã¨ un codice fiscale corretto o non valorizzato");
                        errors.add("error.inputAnagraficaNull", errorMessage(campo.getErr_msg(), "Il campo '<i>" + campo.getDescrizione() + "</i> ' non è un codice fiscale corretto o non valorizzato"));
                        // PC - nuovo controllo
                    } else if (campo.getTp_controllo() != null && campo.getTp_controllo().equalsIgnoreCase("I") && !checkValidPartitaIva(campo.getValoreUtente())) {
                        complete = false;
                        // PC - nuovo controllo
                        // errors.add("error.inputAnagraficaNull","Il campo '<i>"+campo.getDescrizione()+"</i> ' non Ã¨ una partita iva corretta o non valorizzato");
                        errors.add("error.inputAnagraficaNull", errorMessage(campo.getErr_msg(), "Il campo '<i>" + campo.getDescrizione() + "</i> ' non è una partita iva corretta o non valorizzato"));
                        // PC - nuovo controllo
                    } // PC - nuovo controllo
                    else if (campo.getTp_controllo() != null && campo.getTp_controllo().equalsIgnoreCase("X") && !checkValidRegExp(campo.getValoreUtente(), campo.getPattern())) {
                        complete = false;
                        errors.add("error.inputAnagraficaNull", errorMessage(campo.getErr_msg(), "Il campo '<i>" + campo.getDescrizione() + "</i> ' formalmente non corretto"));
                        // PC - nuovo controllo
                    } else if (campo.getTp_controllo() != null && campo.getTp_controllo().equalsIgnoreCase("N") && !checkValidNumeric(campo.getValoreUtente(), campo.getLunghezza(), campo.getDecimali())) {
                        complete = false;
                        // PC - nuovo controllo
                        // errors.add("error.inputAnagraficaNull","Il campo '<i>"+campo.getDescrizione()+"</i> ' deve essere numerico (massima lunghezza "+campo.getLunghezza()+" di cui "+campo.getDecimali()+" decimali");
                        errors.add("error.inputAnagraficaNull", errorMessage(campo.getErr_msg(), "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere numerico (massima lunghezza " + campo.getLunghezza() + " di cui " + campo.getDecimali() + " decimali"));
                        // PC - nuovo controllo
                    } else if (campo.getTp_controllo() != null && campo.getTp_controllo().equalsIgnoreCase("C") && !checkValidCodiceFiscalePIVA(campo.getValoreUtente())) {
                        complete = false;
                        // PC - nuovo controllo
                        // errors.add("error.inputAnagraficaNull","Il campo '<i>"+campo.getDescrizione()+"</i> ' non Ã¨ un codice fiscale corretto o non valorizzato");
                        errors.add("error.inputAnagraficaNull", errorMessage(campo.getErr_msg(), "Il campo '<i>" + campo.getDescrizione() + "</i> ' non è un codice fiscale o partita IVA corretto o non valorizzato"));
                        // PC - nuovo controllo
                    }
                }
//					else {
//						complete = false;
//						errors.add("error.inputAnagraficaNull","Il campo '<i>"+campo.getDescrizione()+"</i> ' è obbligatorio");
//					}
                // PC - nuovo controllo
                // }
                // PC - nuovo controllo
            } else if (campo.getTipo().equalsIgnoreCase("L")) {
                if (campo.getControllo() != null && campo.getControllo().equalsIgnoreCase("O") && (campo.getValoreUtente() == null || campo.getValoreUtente().equalsIgnoreCase(""))) {
                    complete = false;
                    errors.add("error.combolistNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' non è valorizzato");
                }
                if (campo.getControllo() != null && campo.getControllo().equalsIgnoreCase("O") && campo.getValoreUtente() != null && !campo.getValoreUtente().equalsIgnoreCase("")) {
                    if (campo.getValoreUtente().indexOf("ALTRO#") != -1) {
                        String[] token = campo.getValoreUtente().split("#");
                        if (token != null && token.length > 1) {
                            boolean trovato = false;
                            Iterator itt = dataForm.getAnagrafica().getListaCampiStep().iterator();
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
                if (campo.getControllo() != null && campo.getControllo().equalsIgnoreCase("O") && (campo.getValoreUtente() == null || campo.getValoreUtente().equalsIgnoreCase(""))) {
                    complete = false;
                    errors.add("error.hiddenAnagraficaNull", "Alcuni campi obbligatori risultanto non compilati");
                }
            }
            /* Requisito R2 */
            if((("ana000123").equalsIgnoreCase(campo.getNome()) || ("ana020562").equalsIgnoreCase(campo.getNome()) ) && !("").equalsIgnoreCase(campo.getValoreUtente())) {
            	numeroReaObbligatorio = true;
                dataIscrReaObbligatorio = true;
/*                numeroIscrRIObbligatorio = true;
                dataIscrRIObbligatorio = true;*/
            	
            }
            // controllo campo numero Rea
            if (numeroReaObbligatorio && (campo.getNome().equals("ana000124") || campo.getNome().equals("ana020563"))) {
            	if (campo.getValoreUtente().equalsIgnoreCase("") || campo.getValoreUtente()==null) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' in base alle scelte effettuate deve essere valorizzato");
            	}
            }
            // controllo data iscrizione REA
            if (dataIscrReaObbligatorio && (campo.getNome().equals("ana000126") || campo.getNome().equals("ana020565")) ) {
            	if (campo.getValoreUtente().equalsIgnoreCase("") || campo.getValoreUtente()==null) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' in base alle scelte effettuate deve essere valorizzato");
            	}
            }
            // controllo numero iscrizione RI Obbligatorio
/*            if (numeroIscrRIObbligatorio && (campo.getNome().equals("ana000125") || campo.getNome().equals("ana020564"))) {
            	if (campo.getValoreUtente().equalsIgnoreCase("") || campo.getValoreUtente()==null) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' in base alle scelte effettuate deve essere valorizzato");
            	}
            }*/
            // data iscrizione RI
/*            if (dataIscrRIObbligatorio && (campo.getNome().equals("ana000127") || campo.getNome().equals("ana020566") )) {
            	if (campo.getValoreUtente().equalsIgnoreCase("") || campo.getValoreUtente()==null) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' in base alle scelte effettuate deve essere valorizzato");
            	}
            }*/
            /* Fine Requisito R2*/
            
            /* Requisito R1 */
            // comune/stato estero di nascita -- Anagrafica richiedente
            if (campo.getNome().equals("ana000006")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // residente in -- Anagrafica richiedente
            if (campo.getNome().equals("ana000011")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // CAP -- Anagrafica richiedente
            if (campo.getNome().equals("ana000013")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // Indirizzo -- Anagrafica richiedente
            if (campo.getNome().equals("ana000014")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // N.civico -- Anagrafica richiedente
            if (campo.getNome().equals("ana000022")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // Tel -- Anagrafica richiedente
            if (campo.getNome().equals("ana000015")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // eMail -- Anagrafica richiedente
            if (campo.getNome().equals("ana000017")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // PEC -- Anagrafica richiedente
            if (campo.getNome().equals("ana000020")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // Denominazione -- Anagrafica richiedente persona giuridica
            if (campo.getNome().equals("ana000104")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // 	Data Costituzione -- Anagrafica richiedente persona giuridica
            if (campo.getNome().equals("ana000121")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // Con sede in -- Anagrafica richiedente persona giuridica
            if (campo.getNome().equals("ana000106")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // CAP -- Anagrafica richiedente persona giuridica
            if (campo.getNome().equals("ana000108")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // Indirizzo sede Legale -- Anagrafica richiedente persona giuridica
            if (campo.getNome().equals("ana000505")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // Numero civico -- Anagrafica richiedente persona giuridica
            if (campo.getNome().equals("ana000506")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // Telefono -- Anagrafica richiedente persona giuridica
            if (campo.getNome().equals("ana000110")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // Nome -- Anagrafica procuratore
            if (campo.getNome().equals("ana020519")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // Cognome -- Anagrafica procuratore
            if (campo.getNome().equals("ana020520")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // comune/stato estero di nascita -- Anagrafica procuratore
            if (campo.getNome().equals("ana020526")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // data di nascita -- Anagrafica procuratore
            if (campo.getNome().equals("ana020528")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // citta -- Anagrafica procuratore
            if (campo.getNome().equals("ana020529")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // CAP -- Anagrafica procuratore
            if (campo.getNome().equals("ana020531")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // via -- Anagrafica procuratore
            if (campo.getNome().equals("ana020532")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // N. civico -- Anagrafica procuratore
            if (campo.getNome().equals("ana020560")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // Tel -- Anagrafica procuratore
            if (campo.getNome().equals("ana020524")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            
            // Denominazione  -- Anagrafica Procuratore persona giuridica
            if (campo.getNome().equals("ana020501")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // Con sede in -- Anagrafica Procuratore persona giuridica
            if (campo.getNome().equals("ana020503")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // CAP -- Anagrafica Procuratore persona giuridica
            if (campo.getNome().equals("ana020505")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // Indirizzo sede legale -- Anagrafica Procuratore persona giuridica
            if (campo.getNome().equals("ana000535")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // Civico -- Anagrafica Procuratore persona giuridica
            if (campo.getNome().equals("ana000536")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // Telefono -- Anagrafica Procuratore persona giuridica
            if (campo.getNome().equals("ana020507")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // Nome -- Anagrafica Procuratore persona giuridica
            if (campo.getNome().equals("ana020542")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // Cognome -- Anagrafica Procuratore persona giuridica
            if (campo.getNome().equals("ana020543")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // comune/stato estero di nascita -- Anagrafica procuratore
            if (campo.getNome().equals("ana020546")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // Citta -- Anagrafica procuratore
            if (campo.getNome().equals("ana020549")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // CAP -- Anagrafica procuratore
            if (campo.getNome().equals("ana020552")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // Via -- Anagrafica procuratore
            if (campo.getNome().equals("ana020551")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // N. Civico -- Anagrafica procuratore
            if (campo.getNome().equals("ana020567")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            // Tel -- Anagrafica procuratore
            if (campo.getNome().equals("ana020554")) {
            	if(campo.getValoreUtente().length()>0 && ("").equals(campo.getValoreUtente().trim())) {
            		complete = false;
                    errors.add("error.inputAnagraficaNull", "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere valorizzato");
            	}
            }
            
            
            /* Fine Requisito R1 */
        }

        
        
        
//		System.out.println("-----MANAGER ANAGRAFICA CHECK COMPLETE");
//        for (int i = 0; i < dataForm.getAnagrafica().getListaCampi().size(); i++) {
//        	HrefCampiBean campo = (HrefCampiBean)dataForm.getAnagrafica().getListaCampi().get(i);
//        	if (campo.getNome().startsWith("ana01050") || campo.getNome().startsWith("ana01051")) {
//        		System.out.println(campo.getNome());
//        		System.out.println(campo.getDescrizione());
//        		System.out.println(campo.getValoreUtente());
//        		System.out.println();
//        	}
//        }
        
        
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

//
//
//
//	    try {
//	        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT); // YYYY-MM-DD
//	        df.setLenient(false);   // this is important!
//	        df.parse(valoreUtente);
//	    } catch (ParseException e) {
//	       return false;
//	    } catch (IllegalArgumentException e) {
//	       return false;
//	    }
//	    return true;
//	}
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

    public void genereteNextStepAnagrafica(ProcessData dataForm, IRequestWrapper request) {
        String html = "";
        SezioneCompilabileBean scb = new SezioneCompilabileBean();
        BuilderHtml builder = new BuilderHtml();
        ArrayList listaCampiStepSuccessivo = new ArrayList();
        salvaHtmlHistory(dataForm, request);
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
        for (Iterator iterator = dataForm.getAnagrafica().getListaCampi().iterator(); iterator.hasNext();) {
            HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator.next();
            if ((campoAnagrafica.getCampo_collegato() != null && !campoAnagrafica.getCampo_collegato().equalsIgnoreCase("")) || ((campoAnagrafica.getLivello() == (dataForm.getAnagrafica().getLivelloAttuale() + 1)) && (campoAnagrafica.getCampo_collegato() == null))) {
                if (checkAttivazione(campoAnagrafica, dataForm.getAnagrafica().getListaCampiStep()) || ((campoAnagrafica.getLivello() == (dataForm.getAnagrafica().getLivelloAttuale() + 1)) && (campoAnagrafica.getCampo_collegato() == null))) {
                    listaCampiStepSuccessivo.add(campoAnagrafica);
                }
            }
        }
        if (listaCampiStepSuccessivo.size() > 0) {
            dataForm.getAnagrafica().setListaCampiStep(listaCampiStepSuccessivo);

            int numRiga = 0;
            int maxcolonna = 0;
            int firstRowCampoMultiplo = 0;
            int lastRowCampoMultiplo = 0;
            boolean campiMultiplo = false;
            for (Iterator iterator = listaCampiStepSuccessivo.iterator(); iterator.hasNext();) {
                HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator.next();
                if (campoAnagrafica.getRiga() > numRiga) {
                    numRiga = campoAnagrafica.getRiga();
                }
                if (campoAnagrafica.getPosizione() > maxcolonna) {
                    maxcolonna = campoAnagrafica.getPosizione();
                }
                if (campoAnagrafica.getMolteplicita() > 0 && campoAnagrafica.getRiga() > firstRowCampoMultiplo) {
                    firstRowCampoMultiplo = campoAnagrafica.getRiga();
                    campiMultiplo = true;
                }
                if (campoAnagrafica.getMolteplicita() > 0 && campoAnagrafica.getRiga() > lastRowCampoMultiplo) {
                    lastRowCampoMultiplo = campoAnagrafica.getRiga();
                }
                scb.addCampi(campoAnagrafica);
            }
            scb.setRowCount(numRiga);
            scb.setTdCount(maxcolonna);
            scb.setTitolo("");
            scb.setPiedeHref("");
            scb.setHref("ANAG");

            if (campiMultiplo) {
                scb.setNumSezioniMultiple(1);
                scb.setFirstRowCampoMultiplo(firstRowCampoMultiplo);
                scb.setLastRowCampoMultiplo(lastRowCampoMultiplo);
            }


            html = builder.generateHtmlCompilabile(dataForm, scb, request);
//	        request.setAttribute("AVANTI", "true");
//	        request.setAttribute("INDIETRO", "true");
            dataForm.getAnagrafica().setHtmlStepAttuale(html);
        } else {
//			request.setAttribute("INDIETRO", "true");

            if(logger.isDebugEnabled())
            {
                for(Iterator requestSavedFieldsIterator = dataForm.getRegistryRequestSavedFields().iterator(); requestSavedFieldsIterator.hasNext(); logger.debug((new StringBuilder("Campi da request: ")).append(requestSavedFieldsIterator.next()).toString()));
                for(Iterator listaCampiIterator = dataForm.getAnagrafica().getListaCampi().iterator(); listaCampiIterator.hasNext();)
                {
                    HrefCampiBean campoAnagrafica = (HrefCampiBean)listaCampiIterator.next();
                    if(!dataForm.getRegistryRequestSavedFields().contains(campoAnagrafica.getNome()))
                        logger.debug((new StringBuilder("Campi da azzerare: ")).append(campoAnagrafica.getNome()).append(" (").append(campoAnagrafica.getCampo_xml_mod()).append(")").toString());
                }

            }
            boolean foundTitolare = false;
            for(Iterator listaCampiIterator = dataForm.getAnagrafica().getListaCampi().iterator(); listaCampiIterator.hasNext();)
            {
                HrefCampiBean campoAnagrafica = (HrefCampiBean)listaCampiIterator.next();
                if(dataForm.getRegistryRequestSavedFields().contains(campoAnagrafica.getNome()) && campoAnagrafica.getCampoTitolare() != null && campoAnagrafica.getCampoTitolare().startsWith("T"))
                {
                    if(logger.isDebugEnabled())
                    {
                        logger.debug((new StringBuilder("\nCampo: ")).append(campoAnagrafica.getNome()).toString());
                        logger.debug((new StringBuilder("Campo titolare: ")).append(campoAnagrafica.getCampoTitolare()).toString());
                        logger.debug((new StringBuilder("Xml mod: ")).append(campoAnagrafica.getCampo_xml_mod()).toString());
                        logger.debug((new StringBuilder("Valore utente: ")).append(campoAnagrafica.getValoreUtente()).append("\n").toString());
                    }
                    ProfiloPersonaFisica titolarePagamento = null;
                    if(dataForm.getTitolarePagamento() == null)
                    {
                        titolarePagamento = new ProfiloPersonaFisica();
                        dataForm.setTitolarePagamento(titolarePagamento);
                    } else
                    {
                        titolarePagamento = dataForm.getTitolarePagamento();
                    }
                    if(campoAnagrafica.getCampoTitolare().equalsIgnoreCase("TDICHCF") || 
                    		campoAnagrafica.getCampoTitolare().equalsIgnoreCase("TPFCF") || 
                    		campoAnagrafica.getCampoTitolare().equalsIgnoreCase("TPGCF") || 
                    		campoAnagrafica.getCampoTitolare().equalsIgnoreCase("TPGRLCF"))
                        titolarePagamento.setCodiceFiscale(campoAnagrafica.getValoreUtente());
                    if(campoAnagrafica.getCampoTitolare().equalsIgnoreCase("TDICHMAIL") || 
                    		campoAnagrafica.getCampoTitolare().equalsIgnoreCase("TPFMAIL") || 
                    		campoAnagrafica.getCampoTitolare().equalsIgnoreCase("TPFEM") || 
                    		campoAnagrafica.getCampoTitolare().equalsIgnoreCase("TPGMAIL") || 
                    		campoAnagrafica.getCampoTitolare().equalsIgnoreCase("TPGRLMAIL"))
                        titolarePagamento.setDomicilioElettronico(campoAnagrafica.getValoreUtente());
                    foundTitolare = true;
                    if (titolarePagamento != null) {
                    	Calendar calendar = Calendar.getInstance();
                    	calendar.set(Calendar.DAY_OF_MONTH, 1);
                    	calendar.set(Calendar.MONTH, 0);
                    	calendar.set(Calendar.YEAR, 1970);
                    	titolarePagamento.setDataNascita(calendar.getTime());
                    }
                }
            }

            if(!foundTitolare && logger.isDebugEnabled()) {
                logger.debug("\nNessun titolare trovato => titolare coincide con richiedente\n");
            }
        	
            dataForm.getAnagrafica().setFineCompilazione(true);
        }

    }

    public void salvaHtmlHistory(ProcessData dataForm, IRequestWrapper request) {
        if (!dataForm.getAnagrafica().isFineCompilazione()) {
            String html = "";
            SezioneCompilabileBean scb = new SezioneCompilabileBean();
            BuilderHtml builder = new BuilderHtml();
            int numRiga = 0;
            int maxcolonna = 0;
            for (Iterator iterator = dataForm.getAnagrafica().getListaCampiStep().iterator(); iterator.hasNext();) {
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
            scb.setHref("ANAG");
            html = builder.generateHtmlNonCompilabileAnagrafica(dataForm, scb, request);
            dataForm.getAnagrafica().addHtmlHistory(html);
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

    public void genereteBackStepAnagrafica(ProcessData dataForm, IRequestWrapper request) {

        dataForm.getAnagrafica().removeHtmlHistory();
        
        if (!dataForm.getAnagrafica().isFineCompilazione()) {
            ArrayList listaCampiPadre = new ArrayList();
            boolean fine = false;
            for (Iterator iterator = dataForm.getAnagrafica().getListaCampiStep().iterator(); iterator.hasNext();) {
                HrefCampiBean campo = (HrefCampiBean) iterator.next();
                Iterator it2 = dataForm.getAnagrafica().getListaCampi().iterator();
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
                for (Iterator iterator2 = dataForm.getAnagrafica().getListaCampi().iterator(); iterator2.hasNext();) {
                    HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator2.next();
                    if (campoAnagrafica.getLivello() == (dataForm.getAnagrafica().getLivelloAttuale() - 1) && campoAnagrafica.getCampo_collegato() == null) {
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
                scb.setHref("ANAG");
                html = builder.generateHtmlCompilabile(dataForm, scb, request);
                dataForm.getAnagrafica().setListaCampiStep(scb.getCampi());
                dataForm.getAnagrafica().setHtmlStepAttuale(html);
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
                        for (Iterator iterator2 = dataForm.getAnagrafica().getListaCampi().iterator(); iterator2.hasNext();) {
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
                        scb.setHref("ANAG");
                        html = builder.generateHtmlCompilabile(dataForm, scb, request);
                        dataForm.getAnagrafica().setListaCampiStep(scb.getCampi());
                        dataForm.getAnagrafica().setHtmlStepAttuale(html);
                    } else {
                        // TODO
                        String html = "";
                        SezioneCompilabileBean scb = new SezioneCompilabileBean();
                        BuilderHtml builder = new BuilderHtml();
                        int numRiga = 0;
                        int maxcolonna = 0;
                        for (Iterator iterator2 = dataForm.getAnagrafica().getListaCampi().iterator(); iterator2.hasNext();) {
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
                        for (Iterator iterator2 = dataForm.getAnagrafica().getListaCampi().iterator(); iterator2.hasNext();) {
                            HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator2.next();
                            if (campoAnagrafica.getLivello() == (dataForm.getAnagrafica().getLivelloAttuale() - 1) && campoAnagrafica.getCampo_collegato() == null) {
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
                        scb.setHref("ANAG");
                        html = builder.generateHtmlCompilabile(dataForm, scb, request);
                        dataForm.getAnagrafica().setListaCampiStep(scb.getCampi());
                        dataForm.getAnagrafica().setHtmlStepAttuale(html);
                        // -- TODO
                    }
                }
            }
        } else {

            dataForm.getAnagrafica().setFineCompilazione(false);
            String html = "";
            SezioneCompilabileBean scb = new SezioneCompilabileBean();
            BuilderHtml builder = new BuilderHtml();
            int numRiga = 0;
            int maxcolonna = 0;
            for (Iterator iterator2 = dataForm.getAnagrafica().getListaCampiStep().iterator(); iterator2.hasNext();) {
                HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator2.next();
                
                // Le propriet� listaCampi e listaCampiStep di dataForm.anagrafica devono contenere lo stesso riferimento 
                // agli oggetti HrefCampiBean. Si veda il metodo saveAnagrafica.
                HrefCampiBean riferimentoCampo = campoAnagrafica;
                Iterator<HrefCampiBean> listaCampiIterator = dataForm.getAnagrafica().getListaCampi().iterator();
                while(listaCampiIterator.hasNext()) {
                	HrefCampiBean listaCampiCampoAnagrafica = listaCampiIterator.next();
                	if (listaCampiCampoAnagrafica.getNome().equalsIgnoreCase(campoAnagrafica.getNome()) &&
                			StringUtils.equalsIgnoreCase(StringUtils.defaultString(campoAnagrafica.getValore()), StringUtils.defaultString(listaCampiCampoAnagrafica.getValore()))
            			) {
                		riferimentoCampo = listaCampiCampoAnagrafica;
                		riferimentoCampo.setValoreUtente(campoAnagrafica.getValoreUtente());
                		break;
                	}
                }
                
                if (riferimentoCampo.getRiga() > numRiga) {
                    numRiga = riferimentoCampo.getRiga();
                }
                if (riferimentoCampo.getPosizione() > maxcolonna) {
                    maxcolonna = riferimentoCampo.getPosizione();
                }
                scb.addCampi(riferimentoCampo);
            }
            scb.setRowCount(numRiga);
            scb.setTdCount(maxcolonna);
            scb.setTitolo("");
            scb.setPiedeHref("");
            scb.setHref("ANAG");
            html = builder.generateHtmlCompilabile(dataForm, scb, request);
            dataForm.getAnagrafica().setListaCampiStep(scb.getCampi());
            dataForm.getAnagrafica().setHtmlStepAttuale(html);

            
        }
        for(Enumeration requestParams = request.getParameterNames(); requestParams.hasMoreElements();)
        {
            String requestParameter = (String)requestParams.nextElement();
            if(requestParameter.startsWith("ANAG_"))
            {
                if(logger.isDebugEnabled())
                    logger.debug((new StringBuilder("Campo da rimuovere: ")).append(requestParameter.substring("ANAG_".length())).toString());
                dataForm.getRegistryRequestSavedFields().remove(requestParameter.substring("ANAG_".length()));
            }
        }
        
    }

    public void precompilaAnagraficaDaXML(HttpSession session, ProcessData dataForm, String encoding) {
        String userID = (String) session.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER);
        boolean isAnonymus = SiracHelper.isAnonymousUser(userID);
        if (!isAnonymus) {
        	/*
        	 * TODO La chiamata a Bean2XML deve essere fatta con la codifica ISO-8859-1 altrimenti 
        	 * xpr va in errore; probabilmente la libreria che viene sfruttata da xpr, di Init e di cui per 
        	 * ora non abbiamo i sorgenti, legge la stringa solo con quella codifica.
        	 */
//            String bookmarkPointer = Bean2XML.marshallPplData(dataForm, encoding);
            String bookmarkPointer = Bean2XML.marshallPplData(dataForm, "ISO-8859-1");
            XPathReader xpr = new XPathReader(bookmarkPointer);
            //
            //		it.gruppoinit.commons.Config c = new it.gruppoinit.commons.Config(null);
            //		c.setStringXml(bookmarkPointer);
            //		Vector ss = (Vector) c.getElemento("/ProcessData/richiedente/utenteAutenticato/nome");
            //		Vector ss2 = (Vector) c.getElemento("//ProcessData/richiedente/utenteAutenticato/nome");
            for (Iterator iterator = dataForm.getAnagrafica().getListaCampi().iterator(); iterator.hasNext();) {
                HrefCampiBean campo = (HrefCampiBean) iterator.next();
//				if (campo.getNome().equalsIgnoreCase("ana000015")){
//					System.out.println("");
//				}
                if (campo.getPrecompilazione() != null && !campo.getPrecompilazione().equalsIgnoreCase("")) {
                    if (campo.getPrecompilazione().charAt(campo.getPrecompilazione().length() - 1) == '#') {
                        if (dataForm.getRichiedente().getUtenteAutenticato() != null) {
                            campo.setEdit("N");
                        }
                        String ret = xpr.readElementString(campo.getPrecompilazione().substring(0, campo.getPrecompilazione().length() - 1));
                        campo.setValoreUtente(ret);
                    } else if (campo.getPrecompilazione().charAt(campo.getPrecompilazione().length() - 1) == '%') {
                        String ret = xpr.readElementString(campo.getPrecompilazione().substring(0, campo.getPrecompilazione().length() - 1));
                        if (dataForm.getRichiedente().getUtenteAutenticato() != null) {
                            if (ret != null && !"".equals(ret)) {
                                campo.setEdit("N");
                            }
                        }
                        campo.setValoreUtente(ret);
                    } else {
                        String ret = xpr.readElementString(campo.getPrecompilazione());
                        campo.setValoreUtente(ret);
                    }
                }
            }
        }

    }

    public void refresh(ProcessData dataForm, IRequestWrapper request) {
        String html = "";
        SezioneCompilabileBean scb = new SezioneCompilabileBean();
        BuilderHtml builder = new BuilderHtml();
        int numRiga = 0;
        int maxcolonna = 0;
        int firstRowCampoMultiplo = 0;
        int lastRowCampoMultiplo = 0;
        boolean campiMultiplo = false;
        for (Iterator iterator = dataForm.getAnagrafica().getListaCampiStep().iterator(); iterator.hasNext();) {
            HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator.next();
//			if ((campoAnagrafica.getCampo_collegato()==null || campoAnagrafica.getCampo_collegato().equalsIgnoreCase("")) && (campoAnagrafica.getVal_campo_collegato()==null || campoAnagrafica.getVal_campo_collegato().equalsIgnoreCase(""))) {
            if (campoAnagrafica.getRiga() > numRiga) {
                numRiga = campoAnagrafica.getRiga();
            }
            if (campoAnagrafica.getPosizione() > maxcolonna) {
                maxcolonna = campoAnagrafica.getPosizione();
            }
            if (campoAnagrafica.getMolteplicita() > 0 && campoAnagrafica.getRiga() > firstRowCampoMultiplo) {
                firstRowCampoMultiplo = campoAnagrafica.getRiga();
                campiMultiplo = true;
            }
            if (campoAnagrafica.getMolteplicita() > 0 && campoAnagrafica.getRiga() > lastRowCampoMultiplo) {
                lastRowCampoMultiplo = campoAnagrafica.getRiga();
            }
            scb.addCampi(campoAnagrafica);
//			}
        }
        scb.setRowCount(numRiga);
        scb.setTdCount(maxcolonna);
        if (campiMultiplo) {
            scb.setNumSezioniMultiple(1);
            scb.setFirstRowCampoMultiplo(firstRowCampoMultiplo);
            scb.setLastRowCampoMultiplo(lastRowCampoMultiplo);
        }
        scb.setTitolo("");
        scb.setPiedeHref("");
        scb.setHref("ANAG");
        html = builder.generateHtmlCompilabile(dataForm, scb, request);

        dataForm.getAnagrafica().setHtmlStepAttuale(html);

//		System.out.println("-----MANAGER ANAGRAFICA REFRESH");
//        for (int i = 0; i < dataForm.getAnagrafica().getListaCampi().size(); i++) {
//        	HrefCampiBean campo = (HrefCampiBean)dataForm.getAnagrafica().getListaCampi().get(i);
//        	if (campo.getNome().startsWith("ana01050") || campo.getNome().startsWith("ana01051")) {
//        		System.out.println(campo.getNome());
//        		System.out.println(campo.getDescrizione());
//        		System.out.println(campo.getValoreUtente());
//        		System.out.println();
//        	}
//        }
        
        
    }

    public String getHtmlNonCompilabileIniziale(ProcessData dataForm, IRequestWrapper request) {
        SezioneCompilabileBean scb = new SezioneCompilabileBean();
        BuilderHtml builder = new BuilderHtml();
        String html = "";
        int numRiga = 0;
        int maxcolonna = 0;
        for (Iterator iterator = dataForm.getAnagrafica().getListaCampi().iterator(); iterator.hasNext();) {
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
        if (dataForm.getAnagrafica().getListaCampiStep() != null) {
            for (Iterator iterator = scb.getCampi().iterator(); iterator.hasNext();) {
                HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator.next();
                for (Iterator iterator2 = dataForm.getAnagrafica().getListaCampiStep().iterator(); iterator2.hasNext();) {
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
        scb.setPiedeHref("");
        scb.setHref("ANAG");
        html = builder.generateHtmlNonCompilabile(dataForm, scb, request);
        return html;
    }
}
