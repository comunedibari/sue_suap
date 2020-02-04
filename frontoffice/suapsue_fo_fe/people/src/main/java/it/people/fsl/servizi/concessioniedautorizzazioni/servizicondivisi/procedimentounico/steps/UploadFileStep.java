/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 *
 * For convenience a plain text copy of the English version of the Licence can
 * be found in the file LICENCE.txt in the top-level directory of this software
 * distribution.
 *
 * You may obtain a copy of the Licence in any of 22 European Languages at:
 *
 * http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations under
 * the Licence.
*
 */
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.steps;

import it.gruppoinit.commons.Utilities;
import it.people.ActivityState;
import it.people.IValidationErrors;
import it.people.core.PeopleContext;
import it.people.core.ProcessManager;
import it.people.core.persistence.exception.peopleException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.ProcedimentoUnicoDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AllegatoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AnagraficaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.BaseBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.HrefCampiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ModulisticaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoUnicoException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Costant;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.ManagerAllegati;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.process.SurfDirection;
import it.people.process.common.entity.Attachment;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.core.SiracHelper;
import it.people.util.PeopleProperties;
import it.people.util.ServiceParameters;
import it.people.wrappers.IRequestWrapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.upload.FormFile;

public class UploadFileStep extends BaseStep {

    public void service(AbstractPplProcess process, IRequestWrapper request) {
        try {
            // PC - Aggiorna pending process - inizio
            boolean aggiorna = false;
            // PC - Aggiorna pending process - fine                       
            if (initialise(process, request)) {
                logger.debug("UploadFileStep - service method");
                checkRecoveryBookmark(process, request);
                ProcessData dataForm = (ProcessData) process.getData();
                resetError(dataForm);
                String backString = (String) request.getAttribute("back");
                boolean back = (backString != null && backString.equalsIgnoreCase("back"));
                String forwardString = (String) request.getAttribute("forward");
                boolean forward = (forwardString != null && forwardString.equalsIgnoreCase("forward"));

                if ((getSurfDirection(process) == SurfDirection.backward || back)) {
                    if (dataForm.isAttivaPagamenti()) {
                        process.getView().getActivityById("2").setState(it.people.ActivityState.ACTIVE);
                    } else {
                        process.getView().getActivityById("2").setState(it.people.ActivityState.INACTIVE);
                    }
                }
                if ((getSurfDirection(process) == SurfDirection.forward || forward) && dataForm.getTipoBookmark().equalsIgnoreCase(Costant.bookmarkTypeLivello2Label)) {
                    ActivityState as = process.getView().getActivityById("2").getState(); // step dei pagamenti
                    if (as.equals(ActivityState.ACTIVE)) {
                        setStep(process, request, 2, 0);
                    } else {
                        setStep(process, request, 3, 0);
                    }
                }
                String codiceDocumento = (String) request.getSessionAttribute("codiceDocumento");
                boolean valido = true;
                if (codiceDocumento != null) {
                    request.getUnwrappedRequest().getSession().removeAttribute("codiceDocumento");
                    boolean trovato = false;
                    Attachment all = null;
                    Iterator it = dataForm.getAllegati().iterator();
                    int i = 0;
                    while (it.hasNext() && !trovato) {
                        all = (Attachment) it.next();
                        if (all.getDescrizione().equalsIgnoreCase(codiceDocumento)) {
                            trovato = true;
                        } else {
                            i++;
                        }
                    }
                    if (trovato && all != null && codiceDocumento.indexOf("FREE_") == -1) {
						// si tratta di un allegato NON libero, quindi :
                        // controllo se il documento allegato ha particolari vincoli (dimensione, estensione, ecc..)
                        // se ha vincoli e non li rispetta, va eliminato dal file system,dalla lista degli Allegati,e avvertito l'utente.
                        ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
                        String hostOO = delegate.getParametroConfigurazione(process.getCommune().getOid(), "openOfficeIP");
                        String portOO = delegate.getParametroConfigurazione(process.getCommune().getOid(), "openOfficePort");
                        String basePathName = PeopleProperties.UPLOAD_DIRECTORY.getValueString(process.getCommune().getKey());
                        ManagerAllegati am = new ManagerAllegati();
                        valido = am.checkValidita(request, process.getCommune().getOid(), dataForm, codiceDocumento, all, hostOO, portOO, basePathName);
                        if (!valido) {
                            File f = new File(all.getPath());
                            f.delete();
                            dataForm.getAllegati().remove(i);
                            session.setAttribute("codiceDocumento", codiceDocumento);
                            showJsp(process, "upload_documenti.jsp", false);
                        }
                    }

                    if (valido) {
                        showJsp(process, "uploadFile.jsp", false);
                    }
                    // PC - Aggiorna pending process - inizio                                        
                    aggiorna = true;
                    // PC - Aggiorna pending process - fine                                        
                }

                // controllo presenza procura speciale
                if ((forward) && codiceDocumento == null) {
                    ArrayList listaProcuratori = checkPresenzaProcuratoreSpeciale(dataForm, dataForm.getAnagrafica(), dataForm.getAltriRichiedenti());
                    updateListaAllegati(dataForm, listaProcuratori);
// PC - Reiterazione domanda inizio                    
//                    removeAllegatiProcureSpeciali(dataForm);
// PC - Reiterazione doamanda fine                    
                    dataForm.setListaProcuratori(listaProcuratori);
                }
                // PC - Aggiorna pending process - inizio                                
                if (aggiorna) {
                    PeopleContext peopleContext = process.getContext();
                    ProcessManager.getInstance().set(peopleContext, process);
                }
                // PC - Aggiorna pending process - fine                            
            } else {
                throw new ProcedimentoUnicoException("Sessione scaduta");
            } //////// -------------------------------
            logger.debug("UploadFileStep - service method END");
        } catch (Exception e) {
            gestioneEccezioni(process, 5, e);
        } catch (peopleException ex) {
            Logger.getLogger(UploadFileStep.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateListaAllegati(ProcessData dataForm, ArrayList listaProcuratori) {
        removeModulisticaProcuratori(dataForm);
        insertNewModulisticaProcuratori(dataForm, listaProcuratori);
    }

    private void insertNewModulisticaProcuratori(ProcessData dataForm, ArrayList listaProcuratori) {
        for (Iterator iterator = listaProcuratori.iterator(); iterator.hasNext();) {
            BaseBean bb = (BaseBean) iterator.next();
            ModulisticaBean mb = new ModulisticaBean();
            mb.setCodiceDoc("PROC_SPEC_" + bb.getCodice());
            mb.setContent(null);
            mb.setNomeFile("ProcuraSpeciale_" + bb.getCodice());
            mb.setTip_doc("");
            ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
            if (params.get("verticalizzazioneRavenna") != null && (params.get("verticalizzazioneRavenna").equalsIgnoreCase("true"))) {
                mb.setTitolo("Modulo Procura Speciale precompilato( " + bb.getDescrizione() + ")");
            } else {
                mb.setTitolo("Modulo Procura Speciale precompilato(Procuratore : " + bb.getDescrizione() + ")");
            }
            dataForm.getListaModulistica().put("PROC_SPEC_" + bb.getCodice(), mb);
            AllegatoBean allegato = new AllegatoBean();
            allegato.setFlg_obb(true);
            allegato.setCodice("PROC_SPEC_" + bb.getCodice());
            allegato.setTipologieAllegati("p7m");
            allegato.setNum_max_pag("");
            allegato.setDimensione_max("");
            allegato.setHref(null);
            allegato.setSignVerify(true); //Modifica inserita il 5/5/2017 per rendere obbligatoria al verifica della firma sullla procura speciale
            if (params.get("verticalizzazioneRavenna") != null && (params.get("verticalizzazioneRavenna").equalsIgnoreCase("true"))) {
                allegato.setTitolo("Allegato Procura Speciale precompilato(" + bb.getDescrizione() + ")");
            } else {
                allegato.setTitolo("Allegato Procura Speciale precompilato(Procuratore : " + bb.getDescrizione() + ")");
            }
            allegato.setDescrizione("Allegato Procura Speciale (" + bb.getDescrizione() + "");
            dataForm.getListaAllegati().put("PROC_SPEC_" + bb.getCodice(), allegato);

        }

    }

    private void removeModulisticaProcuratori(ProcessData dataForm) {
        for (Iterator iterator = dataForm.getListaProcuratori().iterator(); iterator.hasNext();) {
            BaseBean bb = (BaseBean) iterator.next();
            if (dataForm.getListaModulistica().containsKey("PROC_SPEC_" + bb.getCodice())) {
                dataForm.getListaModulistica().remove("PROC_SPEC_" + bb.getCodice());
            }
            if (dataForm.getListaAllegati().containsKey("PROC_SPEC_" + bb.getCodice())) {
                dataForm.getListaAllegati().remove("PROC_SPEC_" + bb.getCodice());
            }
        }
    }

    public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException, ServletException {
        try {
            logger.debug("UploadFileStep - loopBack method");
            ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
            ProcessData dataForm = (ProcessData) process.getData();
            if (propertyName.equalsIgnoreCase("upload_documenti.jsp")) {
                String idx = request.getParameter("index");
//                String codDoc = request.getParameter("codDoc");
//                request.setAttribute("listaDocumenti", delegate.getDocumentiAllegati(String.valueOf(idx)));
                //request.setAttribute("codice", idx);
                if (idx != null && idx.equalsIgnoreCase("FREE")) {
                    Date d = new Date();
                    idx = idx.concat("_" + String.valueOf(d.getTime()));
                }
                session.setAttribute("codiceDocumento", idx);
                showJsp(process, propertyName, false);
            } else if (propertyName.equalsIgnoreCase("uploadFile.jsp")) {
                String confirm = request.getParameter("confirm");
                if (confirm != null && confirm.equalsIgnoreCase("SI")) {
                    String codiceAllegatoDaEliminare = (String) request.getParameter("cod");
                    // System.out.println("RIMUOVERE l'allegato "+codiceAllegatoDaEliminare);
                    if (codiceAllegatoDaEliminare != null) {
                        boolean trovato = false;
                        int i = 0;
                        Iterator it = dataForm.getAllegati().iterator();
                        while (it.hasNext() && !trovato) {
                            Attachment att = (Attachment) it.next();
                            if (att.getDescrizione().equalsIgnoreCase(codiceAllegatoDaEliminare)) {
                                File fTmp = new File(att.getPath());
                                fTmp.delete();
                                dataForm.removeAllegati(i);
                                trovato = true;
                            }
                            i++;
                        }
                        // PC - Aggiorna pending process - inizio  
                        if (trovato) {
                            PeopleContext peopleContext = process.getContext();
                            ProcessManager.getInstance().set(peopleContext, process);
                        }
                        // PC - Aggiorna pending process - fine                                                  
                    }
                }
                showJsp(process, propertyName, false);
            } else if (propertyName.equalsIgnoreCase("delete_doc_confirm.jsp")) {
                String idx = request.getParameter("index");
                session.setAttribute("codiceDocumento", idx);
                showJsp(process, propertyName, false);
            }                       
            logger.debug("UploadFileStep - loopBack method END");
        } catch (Exception e) {
            gestioneEccezioni(process, 5, e);
        } catch (peopleException ex) {
            Logger.getLogger(UploadFileStep.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean logicalValidate(AbstractPplProcess process, IRequestWrapper request, IValidationErrors errors) throws ParserException {
        ProcessData dataForm = (ProcessData) process.getData();
        doSave(process, request);
        String buttonSave = request.getParameter("navigation.button.save");
        if (buttonSave != null && buttonSave.equalsIgnoreCase("Salva")) {
            return true;
        }
        try {
            logger.debug("UploadFileStep - logicalValidate method");

            if (dataForm.isAttivaPagamenti() && dataForm.getRiepilogoOneri().getTotale() > 0) {
                process.getView().getActivityById("2").setState(it.people.ActivityState.ACTIVE);
            } else {
                process.getView().getActivityById("2").setState(it.people.ActivityState.INACTIVE);
            }

            String userID = (String) session.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER);
            boolean isAnonymus = SiracHelper.isAnonymousUser(userID);
            if (isAnonymus || dataForm.getTipoBookmark().equalsIgnoreCase(Costant.bookmarkTypeCortesiaLabel) || dataForm.getTipoBookmark().equalsIgnoreCase(Costant.bookmarkTypeLivello2Label)) {
                return true;
            }

            ManagerAllegati ma = new ManagerAllegati();
            boolean ok = ma.checkNumeroMinimoAllegati(dataForm);
            if (!ok) {
                errors.add("error.allegatiObbligatori");
                return false;
            }
            logger.debug("UploadFileStep - logicalValidate method END");
            return true;
        } catch (Exception e) {
            errors.add("error.generic", "Errore interno");
            gestioneEccezioni(process, 5, e);
            dataForm.setInternalError(true);
            return false;
        }
    }

    private boolean checkPresenzaTemplateProcuraSpeciale(ProcessData process) {
        String codSportello = "";
        if (process.getListaSportelli() != null && process.getListaSportelli().keySet() != null) {
            Iterator itt = process.getListaSportelli().keySet().iterator();
            if (itt.hasNext()) {
                codSportello = (String) itt.next();
            }
        }

        ProcedimentoUnicoDAO prDao = new ProcedimentoUnicoDAO(db, language);
        return prDao.hasTemplateVarioForSportello(codSportello, "ProcuraSpeciale");
    }

    private ArrayList checkPresenzaProcuratoreSpeciale(ProcessData dataForm, AnagraficaBean anagrafica, ArrayList altriRichiedenti) {
        ArrayList listaProcuratori = new ArrayList();
        String nome = "";
        String cognome = "";
        String codFisc = "";
        boolean isProcuratoreSpeciale = false;
        HashMap mappaProcuratori = new HashMap();
        for (Iterator iterator = anagrafica.getListaCampi().iterator(); iterator.hasNext();) {
            HrefCampiBean campoAnag = (HrefCampiBean) iterator.next();
            String campoXMLmod = campoAnag.getCampo_xml_mod();
            if (campoXMLmod != null && campoXMLmod.equalsIgnoreCase("ANAG_DICHIARANTE_NOME")) {
                nome = Utilities.NVL(campoAnag.getValoreUtente(), "-");
            } else if (campoXMLmod != null && campoXMLmod.equalsIgnoreCase("ANAG_DICHIARANTE_COGNOME")) {
                cognome = Utilities.NVL(campoAnag.getValoreUtente(), "-");
            } else if (campoXMLmod != null && campoXMLmod.equalsIgnoreCase("ANAG_CODFISC_DICHIARANTE")) {
                codFisc = campoAnag.getValoreUtente();
            }
            if (it.gruppoinit.commons.Utilities.isset(campoAnag.getAzione()) && campoAnag.getAzione().equalsIgnoreCase("ProcuraSpeciale")) {
                if (it.gruppoinit.commons.Utilities.isset(campoAnag.getValoreUtente())) {
                    isProcuratoreSpeciale = true;
                }
            }
        }
        if (isProcuratoreSpeciale && codFisc != null && !mappaProcuratori.containsKey(codFisc)) {
            mappaProcuratori.put(codFisc, nome + " " + cognome + " (" + codFisc + ")");
        }

        for (Iterator iterator = altriRichiedenti.iterator(); iterator.hasNext();) {
            AnagraficaBean anagAltriDichiaranti = (AnagraficaBean) iterator.next();
            nome = "";
            cognome = "";
            codFisc = null;
            isProcuratoreSpeciale = false;
            for (Iterator iterator2 = anagAltriDichiaranti.getListaCampi().iterator(); iterator2.hasNext();) {
                HrefCampiBean campoAnag = (HrefCampiBean) iterator2.next();
                String campoXMLmod = campoAnag.getCampo_xml_mod();
                if (campoXMLmod != null && campoXMLmod.equalsIgnoreCase("ANAG_DICHIARANTE_NOME")) {
                    nome = Utilities.NVL(campoAnag.getValoreUtente(), "-");
                } else if (campoXMLmod != null && campoXMLmod.equalsIgnoreCase("ANAG_DICHIARANTE_COGNOME")) {
                    cognome = Utilities.NVL(campoAnag.getValoreUtente(), "-");
                } else if (campoXMLmod != null && campoXMLmod.equalsIgnoreCase("ANAG_CODFISC_DICHIARANTE")) {
                    codFisc = campoAnag.getValoreUtente();
                }
                if (it.gruppoinit.commons.Utilities.isset(campoAnag.getAzione()) && campoAnag.getAzione().equalsIgnoreCase("ProcuraSpeciale")) {
                    if (it.gruppoinit.commons.Utilities.isset(campoAnag.getValoreUtente())) {
                        isProcuratoreSpeciale = true;
                    }
                }
            }
            if (isProcuratoreSpeciale && codFisc != null && !mappaProcuratori.containsKey(codFisc)) {
                mappaProcuratori.put(codFisc, nome + " " + cognome + " (" + codFisc + ")");
            }
        }

        if (mappaProcuratori.size() > 0 && checkPresenzaTemplateProcuraSpeciale(dataForm)) {
            Set set = mappaProcuratori.keySet();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = (String) mappaProcuratori.get(key);
                BaseBean bb = new BaseBean(key, value);
                listaProcuratori.add(bb);
            }
        }
        return listaProcuratori;
    }

    private void removeAllegatiProcureSpeciali(ProcessData dataForm) {
		// TODO Auto-generated method stub
        //uploadDescription
        ArrayList allegatiDaEliminare = new ArrayList();
        ArrayList nuovaListaAllegati = new ArrayList();
        for (Iterator iterator = dataForm.getListaProcuratori().iterator(); iterator.hasNext();) {
            BaseBean bb = (BaseBean) iterator.next();
            for (Iterator iterator2 = dataForm.getAllegati().iterator(); iterator2.hasNext();) {
                Attachment allegato = (Attachment) iterator2.next();
                if (allegato.getDescrizione() != null && allegato.getDescrizione().equalsIgnoreCase("PROC_SPEC_" + bb.getCodice())) {
                    File f = new File(allegato.getPath());
                    f.delete();
                    allegatiDaEliminare.add(allegato);
                }
            }
        }
        for (Iterator iterator = dataForm.getAllegati().iterator(); iterator.hasNext();) {
            Attachment allegato = (Attachment) iterator.next();
            boolean trovato = false;
            Iterator it2 = allegatiDaEliminare.iterator();
            while (it2.hasNext() && !trovato) {
                Attachment at = (Attachment) it2.next();
                if (allegato.equals(at)) {
                    trovato = true;
                }
            }
            if (!trovato) {
                nuovaListaAllegati.add(allegato);
            }
        }
        dataForm.setAllegati(nuovaListaAllegati);
    }
}
