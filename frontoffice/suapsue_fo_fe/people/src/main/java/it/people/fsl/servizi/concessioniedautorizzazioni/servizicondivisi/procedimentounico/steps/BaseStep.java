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
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.steps;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.gruppoinit.commons.DBCPManager;
import it.gruppoinit.commons.Utilities;
import it.people.Activity;
import it.people.IValidationErrors;
import it.people.Step;
import it.people.core.PeopleContext;
import it.people.core.PplUser;
import it.people.core.ServiceProfileStore;
import it.people.core.ServiceProfileStoreManager;
import it.people.core.exception.ServiceException;
import it.people.core.persistence.exception.peopleException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.BookmarkDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.ProcedimentoUnicoDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AnagraficaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ComuneBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ErrorBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.EventiVitaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.HrefCampiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ParametriPUBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoUnicoException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SportelloBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Bean2XML;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.BuilderHtml;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Costant;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.ManagerBandi;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.ManagerIntermediari;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.SenderMail;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.UtilProperties;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.XPathReader;
import it.people.layout.LayoutMenu;
import it.people.layout.NavigationBar;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.process.SurfDirection;
import it.people.process.common.entity.Attachment;
import it.people.process.common.entity.SignedSummaryAttachment;
import it.people.process.common.entity.UnsignedSummaryAttachment;
import it.people.process.view.ConcreteView;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.serviceprofile.xml.AccessoIntermediari;
import it.people.sirac.serviceprofile.xml.AccessoUtentePeopleRegistrato;
import it.people.sirac.serviceprofile.xml.AuthorizationProfile;
import it.people.sirac.serviceprofile.xml.PeopleServiceProfile;
import it.people.sirac.serviceprofile.xml.PeopleServiceProfileDocument;
import it.people.sirac.serviceprofile.xml.SecurityProfile;
import it.people.sirac.serviceprofile.xml.Service;
import it.people.util.PeopleProperties;
import it.people.util.ServiceParameters;
import it.people.wrappers.IRequestWrapper;

public class BaseStep extends Step {

    public final Log logger = LogFactory.getLog(this.getClass());
    protected String language = "it"; // la lingua di default è 'it' (italiano)
    protected HttpSession session;
    protected DBCPManager db;
    public final String RIEPILOGO_FIRMATO = "riepilogo.pdf.p7m";

    public BaseStep() {
        super();
    }

    protected boolean initialise(AbstractPplProcess process,
			IRequestWrapper request) {
        if (request == null || request.getUnwrappedRequest() == null || request.getUnwrappedRequest().getSession() == null) {
            return false;
        } else {
            try {
                session = request.getUnwrappedRequest().getSession();
                ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
                //System.out.println(params.get("dbjndi"));


                Map<String, String> reversedMap = new TreeMap<String, String>(params.getParametersHasMap());
                for (Map.Entry<String, String> entry : reversedMap.entrySet()) {
                    logger.info(entry.getKey() + ", " + entry.getValue());
                }

                try {
                    String jndi = params.get("dbjndi");
                    logger.info("jndi: " + jndi);
                    if (session.getAttribute("DB") == null) {
                        this.db = new DBCPManager(jndi);
                        session.setAttribute("DB", db);
                    } else {
                        this.db = (DBCPManager) session.getAttribute("DB");
                        if (!this.db.getResourceName().equals(jndi)) {
                            // VUOL DIRE CHE IL CITTADINO E' ACCEDUTO SUL  SERVIZIO
                            // DI UN COMUNE DIFFERENTE
                            this.db = new DBCPManager(jndi);
                            session.setAttribute("DB", this.db);
                        }
                    }
                } catch (Exception e) {
                    logger.error("", e);
                    return false;
                }


                // Setto la lingua
                if (params.get("language") != null) {
                    language = params.get("language");
                }
                ProcessData dataForm = (ProcessData) process.getData();
                dataForm.setLanguage(language);

                if (request.getSessionAttribute("initFromMyPage") != null 
                		&& Boolean.parseBoolean(String.valueOf(request.getSessionAttribute("initFromMyPage")))) {
	                ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(this.db, "it");
	
	                SportelloBean sportello = null;
	                Set chiaviSettore = dataForm.getListaSportelli().keySet();
	                boolean trovato = sportello != null;
	                Iterator chiaviSettoreIterator = chiaviSettore.iterator();
	                while (chiaviSettoreIterator.hasNext() && !trovato){
	                    String chiaveSettore = (String) chiaviSettoreIterator.next();
	                    if (sportello == null) {
	                        sportello = (SportelloBean) dataForm.getListaSportelli().get(chiaveSettore);
	                        trovato = true;
	                    }
	                }
	
	                if (trovato && sportello != null) {
	                	sportello = delegate.updateDatiSportello(sportello, dataForm);
	                }
	                request.getUnwrappedRequest().getSession().removeAttribute("initFromMyPage");
                }
                
                return true;
            } catch (Exception e) {
            	logger.error("", e);
                return false;
            }
        }
    }

    public boolean logicalValidate(AbstractPplProcess process, IRequestWrapper request, IValidationErrors errors) throws ParserException {
        return super.logicalValidate(process, request, errors);
    }

    public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException, ServletException {
        logger.debug("loopBack BaseStep.java");
        logger.debug("propertyName=" + propertyName);
    }

    protected void gestioneEccezioni(AbstractPplProcess process, int codiceErrore, Exception e) {
        // TODO salvataggio xml del processData
        // TODO invio email al responsabile People
        ProcessData dataForm = (ProcessData) process.getData();
        ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
        if (params.get("mostraDettagliErrore") != null && (params.get("mostraDettagliErrore").equalsIgnoreCase("true"))) {
            dataForm.getErrore().setViewStackTrace(true);
        }
        if (params.get("invioMailErrore") != null && (params.get("invioMailErrore").equalsIgnoreCase("true"))) {
            dataForm.getErrore().setSendMailError(true);


        }
        if (params.get("salvaXMLErrore") != null && (params.get("salvaXMLErrore").equalsIgnoreCase("true"))) {
            dataForm.getErrore().setSaveXML(true);
        }

        if (dataForm.getErrore().isViewStackTrace()) {
            ArrayList stack = new ArrayList();
            for (int i = 0; i < e.getStackTrace().length; i++) {
                stack.add(e.getStackTrace()[i].toString());
            }
            dataForm.getErrore().setStackTrace(stack);
        }
        Date d = new Date();
        String nomeFileXMLerrore = "";
        if (dataForm.getErrore().isSaveXML()) {
//            String absPathToService = params.get("absPathToService");
//            String resourcePath = absPathToService+System.getProperty("file.separator")+"risorse"+System.getProperty("file.separator");
//            Properties props[] = UtilProperties.getProperties(resourcePath,"parametri", process.getCommune().getOid());
//            String path = UtilProperties.getPropertyKey(props[0], props[1], props[2],"pathXMLError");

            ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
            String path = delegate.getParametroConfigurazione(process.getCommune().getOid(), "pathXMLError");

            try {

                FileOutputStream file = new FileOutputStream(path + System.getProperty("file.separator") + "A&C_error_" + d.getTime() + ".txt");
                PrintStream output = new PrintStream(file);
                output.write(Bean2XML.marshallPplData(dataForm, process.getContext().getCharacterEncoding()).getBytes());
                output.close();
                file.close();
                nomeFileXMLerrore = path + System.getProperty("file.separator") + "A&C_error_" + d.getTime() + ".txt";
            } catch (Exception ex) {
            }
        }

        if (dataForm.getErrore().isSendMailError()) {
            sendNotificationError(process, nomeFileXMLerrore, "A&C_error_" + d.getTime() + ".txt", Utilities.NVL(dataForm.getIdentificatorePeople().getIdentificatoreProcedimento(), ""));
        }

        dataForm.getErrore().setShortMessage(e.getMessage());

        String absPathToService = params.get("absPathToService");
        String resourcePath = absPathToService + System.getProperty("file.separator") + "risorse" + System.getProperty("file.separator");
        Properties props[] = UtilProperties.getProperties(resourcePath, "messaggi", process.getCommune().getOid());

        String error = UtilProperties.getPropertyKey(props[0], props[1], props[2], "errorMessageHTML");
        dataForm.getErrore().setDescrizione(error);

        process.getProcessWorkflow().getProcessView().setBottomNavigationBarEnabled(false);
        process.getProcessWorkflow().getProcessView().setBottomSaveBarEnabled(false);
        showJsp(process, "defaultError.jsp", false);
    }

    protected void sendNotificationError(AbstractPplProcess process, String pathNomeFile, String nomeF, String numPratica) {
        ProcessData dataForm = (ProcessData) process.getData();
        ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
        String absPathToService = params.get("absPathToService");
        String resourcePath = absPathToService + System.getProperty("file.separator") + "risorse" + System.getProperty("file.separator");
        Properties props[] = UtilProperties.getProperties(resourcePath, "messaggi", process.getCommune().getOid());

        String body = UtilProperties.getPropertyKey(props[0], props[1], props[2], "mailNotificationError.body");


        try {
            String nomeAttach = "";
            if (nomeF == null || nomeF.equalsIgnoreCase("")) {
                ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
                String path = delegate.getParametroConfigurazione(process.getCommune().getOid(), "pathXMLError");

                Date d = new Date();
                FileOutputStream file = new FileOutputStream(path + System.getProperty("file.separator") + "A&C_error_" + d.getTime() + ".txt");
                PrintStream output = new PrintStream(file);
                output.write(Bean2XML.marshallPplData(dataForm, process.getContext().getCharacterEncoding()).getBytes());
                output.close();
                file.close();
                nomeAttach = path + System.getProperty("file.separator") + "A&C_error_" + d.getTime() + ".txt";

                SenderMail ms = new SenderMail();
                ms.sendMail((String) PeopleProperties.SMTP_MAIL_SENDER_BACKEND.getValue(), "People - Notification Error A&C", body, path + System.getProperty("file.separator") + "A&C_error_" + d.getTime() + ".txt", "A&C_error_" + d.getTime() + ".txt", numPratica);


            } else {
                SenderMail ms = new SenderMail();
                ms.sendMail((String) PeopleProperties.SMTP_MAIL_SENDER_BACKEND.getValue(), "People - Notification Error A&C", body, pathNomeFile, nomeF, numPratica);
            }


            if (!nomeAttach.equalsIgnoreCase("")) {
                File f = new File(nomeAttach);
                f.delete();
            }
        } catch (Exception e) {
            logger.error("Errore durante la gestione degli errori");
        }

    }

    protected void showJsp(AbstractPplProcess process, String path, boolean completePath) {
        String htmlPath = "/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/";
        process.getView().getCurrentActivity().getCurrentStep().setJspPath((completePath ? "" : htmlPath) + path);
        logger.debug("you are going here --> " + process.getView().getCurrentActivity().getCurrentStep().getJspPath());
    }

    protected int getCurrentStepIndex(AbstractPplProcess process) {
        return process.getView().getCurrentActivity().getCurrentStepIndex();
    }

    public void setStep(AbstractPplProcess pplProcess, int index) {
        pplProcess.getView().getCurrentActivity().setCurrentStepIndex(index);
    }

    protected boolean goToStep(AbstractPplProcess process, IRequestWrapper request, int offset) {
        logger.debug("Inside goToStep with offset " + offset);
        int index = getCurrentStepIndex(process);
        logger.debug("Current index is " + index);
        logger.debug("Next index is " + (index + offset));
        setStep(process, (index + offset));

        try {
            process.getView().getCurrentActivity().getCurrentStep().service(process, request);
        } catch (IOException e) {
            logger.error("", e);
            return false;
        } catch (ServletException ex) {
            //
            logger.error("", ex);
            return false;
        }
        return true;
    }

    public SurfDirection getSurfDirection(AbstractPplProcess process) {
        return getConcreteView(process).getSurfDirection();
    }

    private ConcreteView getConcreteView(AbstractPplProcess process) {
        return process.getProcessWorkflow().getProcessView();
    }

    protected void resetError(ProcessData dataForm) {
        dataForm.setInternalError(false);
        dataForm.setErrore(new ErrorBean());
    }

    protected void enableBottomNavigationBar(AbstractPplProcess process, boolean enable) {
        process.getView().setBottomNavigationBarEnabled(enable);
    }

    protected void gestioneBookmark(AbstractPplProcess process, IRequestWrapper request) throws Exception {

        ProcessData dataForm = (ProcessData) process.getData();
        String action = request.getParameter("action");
        String operazione = request.getParameter("operazione");
        BookmarkDAO delegate = new BookmarkDAO(db, language);
        if (action != null && action.equalsIgnoreCase("servizi.jsp")) {
            enableBottomNavigationBar(process, false);
            //enableBottomSaveBar(process, false);

            request.setAttribute("numTab", ((String) request.getParameter("tab") != null && !((String) request.getParameter("tab")).equalsIgnoreCase("")) ? (String) request.getParameter("tab") : "1");
            if (request.getParameter("tab") != null && request.getParameter("tab").equalsIgnoreCase("4")) {
                String nodoPeople=process.getCommune().getKey();
                ArrayList lista = delegate.getListaBookmarkUrl(nodoPeople);
                request.setAttribute("view", "true");
                if (lista.size() > 0) {
                    request.setAttribute("listaBookmark", lista);
                }
            } else {
            String comune = ((ComuneBean) dataForm.getComuneSelezionato()).getCodEnte();
            String serviceCategory = "servizicondivisi";
            String serviceSubcategory = "procedimentounico";
            String serviceName = "it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico";
            if (operazione != null) {
                if (operazione.equalsIgnoreCase("insert")) {
                    String nuovoServizio = request.getParameter("nuovoServizio");
                    String descrizioneServizio = request.getParameter("descrizioneServizio");
//                    System.out.println("NOME_SERVIZIO --> "+nuovoServizio);
//                    System.out.println("DESC SERVIZIO --> "+descrizioneServizio);
                    if (nuovoServizio == null || nuovoServizio.equals("")) {
                        return;
                    } else {
                        dataForm.setLastActivityIdx(getCurrentActivityIndex(process));
                        dataForm.setLastStepIdx(getCurrentStepIndex(process));
                        dataForm.setLastStepId(process.getView().getCurrentActivity().getCurrentStep().getId());
                        dataForm.setLastActivityId(process.getView().getCurrentActivity().getId());
                        dataForm.setNomeBookmark(nuovoServizio);
                        dataForm.setDescBookmark(descrizioneServizio);
                        String bookmarkPointer = Bean2XML.marshallPplData(process.getData(), request.getUnwrappedRequest().getCharacterEncoding());/*getLastStepDataAsString(process, request)*/
//                        System.out.println(bookmarkPointer);
                        // Effettuo l'insert nel db
                        int[] codServizio = delegate.insertServizio(request, comune, Integer.parseInt((String) dataForm.getDatiTemporanei().getEventoVitaSel()), bookmarkPointer, nuovoServizio, descrizioneServizio, dataForm.getDatiTemporanei().getComuniBookmarkSel());
                        // Inserisco il sevice profile
                        try {
                            String serviceProfileXML = createServiceProfileXML(process,
                                    serviceName,
                                    serviceCategory,
                                    serviceSubcategory,
                                    request.getParameter("AAF").equalsIgnoreCase("si"),
                                    request.getParameter("AAD").equalsIgnoreCase("si"),
                                    request.getParameter("AI").equalsIgnoreCase("si"),
                                    request.getParameter("AUR").equalsIgnoreCase("si"));
                            this.debug(process, serviceProfileXML);
                            saveServiceProfileXML(process, serviceProfileXML, codServizio, serviceName);
                        } catch (peopleException e) {
                            logger.error("", e);
                        } catch (Exception ex) {
                            logger.error("", ex);
                        }
                        if (dataForm.getDatiTemporanei().getEventoVitaSel() != null) {
                            dataForm.getDatiTemporanei().setServizi(delegate.getServiziPerEventiVita(Integer.parseInt((String) dataForm.getDatiTemporanei().getEventoVitaSel()), comune, serviceName));
//                            ArrayList servizi = (ArrayList) dataForm.getDatiTemporanei().getServizi();
                        }
                    }
                } else if (operazione.equalsIgnoreCase("update")) {
                    if (dataForm.getDatiTemporanei().getServizioSel() != null) {
                        dataForm.setLastActivityIdx(getCurrentActivityIndex(process));
                        dataForm.setLastStepIdx(getCurrentStepIndex(process));
                        dataForm.setLastStepId(process.getView().getCurrentActivity().getCurrentStep().getId());
                        dataForm.setLastActivityId(process.getView().getCurrentActivity().getId());
                        String descrizioneServizio = request.getParameter("descrizioneServizio");
                        String nomeServizio = request.getParameter("nuovoServizio");
                        dataForm.setNomeBookmark(nomeServizio);
                        dataForm.setDescBookmark(descrizioneServizio);
                        String bookmarkPointer = Bean2XML.marshallPplData(process.getData(), request.getUnwrappedRequest().getCharacterEncoding());/*getLastStepDataAsString(process, request)*/
                        ;
                        // Effettuo l'insert nel db
                        boolean modificaLight = request.getParameter("modificaLight") == null ? false : true;
                        String codServizio = (String) dataForm.getDatiTemporanei().getServizioSel();
                        delegate.updateServizio(request, Integer.parseInt(codServizio), comune, Integer.parseInt((String) dataForm.getDatiTemporanei().getEventoVitaSel()), bookmarkPointer, descrizioneServizio, nomeServizio, modificaLight);
                        ServiceProfileStoreManager spsManager = ServiceProfileStoreManager.getInstance();
                        //spsManager.delete(serviceName, codServizio);
                        try {
                            String serviceProfileXML = createServiceProfileXML(process,
                                    serviceName,
                                    serviceCategory,
                                    serviceSubcategory,
                                    request.getParameter("AAF").equalsIgnoreCase("si"),
                                    request.getParameter("AAD").equalsIgnoreCase("si"),
                                    request.getParameter("AI").equalsIgnoreCase("si"),
                                    request.getParameter("AUR").equalsIgnoreCase("si"));
                            this.debug(process, serviceProfileXML);
                            int[] codServizio2 = {Integer.parseInt(codServizio)};

                            saveServiceProfileXML(process, serviceProfileXML, codServizio2, serviceName);
                        } catch (peopleException e) {
                            logger.error("", e);
                        } catch (Exception ex) {
                            logger.error("", ex);
                        }
                        if (dataForm.getDatiTemporanei().getEventoVitaSel() != null) {
                            dataForm.getDatiTemporanei().setServizi(delegate.getServiziPerEventiVita(Integer.parseInt((String) dataForm.getDatiTemporanei().getEventoVitaSel()), comune, serviceName));
//                            ArrayList servizi = (ArrayList) dataForm.getDatiTemporanei().getServizi();
                        }
                    }
                } else if (operazione.equalsIgnoreCase("delete")) {
                    if (dataForm.getDatiTemporanei().getServizioSel() != null) {
                        // Effettuo la delete nel db
                        String codServizio = (String) dataForm.getDatiTemporanei().getServizioSel();
                        delegate.deleteServizio(Integer.parseInt(codServizio), comune, Integer.parseInt((String) dataForm.getDatiTemporanei().getEventoVitaSel()));
                        ServiceProfileStoreManager spsManager = ServiceProfileStoreManager.getInstance();
                        /** TODO il problema è qui **/
                        spsManager.delete(serviceName, codServizio);

                        if (dataForm.getDatiTemporanei().getEventoVitaSel() != null) {
                            dataForm.getDatiTemporanei().setServizi(delegate.getServiziPerEventiVita(Integer.parseInt((String) dataForm.getDatiTemporanei().getEventoVitaSel()), comune, serviceName));
//                            ArrayList servizi = (ArrayList) dataForm.getDatiTemporanei().getServizi();
                        }
                    }
                } else if (operazione.startsWith("bandi")) {
                    ManagerBandi mb = new ManagerBandi(db, language);
                    mb.managerMain(process, request);
                }
            } else {
                logger.debug(process.getView().getCurrentActivity().getCurrentStep().getJspPath());
                dataForm.getDatiTemporanei().setEventiVita(delegate.getEventiVita());
                //dataForm.getDatiTemporanei().setParametriPU(delegate.getParametriPU());
                if (dataForm.getDatiTemporanei().getEventoVitaSel() != null) {
                    dataForm.getDatiTemporanei().setServizi(delegate.getServiziPerEventiVita(Integer.parseInt((String) dataForm.getDatiTemporanei().getEventoVitaSel()), comune, serviceName));
//                    ArrayList servizi = (ArrayList) dataForm.getDatiTemporanei().getServizi();
                } else {
                    // E' la prima volta che entro: mi posiziono sul
                    // primo evento della vita
                    ArrayList list = (ArrayList) dataForm.getDatiTemporanei().getEventiVita();
                    if (list != null && list.size() > 0) {
                        dataForm.getDatiTemporanei().setServizi(delegate.getServiziPerEventiVita(((EventiVitaBean) list.get(0)).getCodiceEventoVita(), comune, serviceName));
                    }
                }
            }
            try {
                ArrayList listaComuni = new ArrayList();
                int i = getCurrentActivityIndex(process);
                int j = getCurrentStepIndex(process);
                if (i == 0 && j <= 3) {
                    listaComuni = delegate.getListaComuniCreazioneBookmark1(dataForm.getComuneSelezionato().getTipAggregazione(), dataForm.getSettoreScelto().getCodice(), dataForm.getComuneSelezionato().getCodEnte());
                } else {
                    listaComuni = delegate.getListaComuniCreazioneBookmark2(dataForm.getComuneSelezionato().getCodEnte());
                }
                dataForm.getDatiTemporanei().setComuniBookmark(listaComuni);
                //request.setAttribute("listaComuni", listaComuni);
            } catch (Exception e) {
                logger.error("", e);
            }
            // La prima volta memorizzo la jsp che mi ha chiamato
            if (dataForm.getDatiTemporanei().getJspChiamante() == null) {
                dataForm.getDatiTemporanei().setJspChiamante(getCurrentJspPath(process));
            }
            }
            showJsp(process, action, false);
        } else if (action != null && action.equalsIgnoreCase("eventiVita.jsp")) {
            //logger.debug(process.getView().getCurrentActivity().getCurrentStep().getJspPath());
            if (operazione != null) {
                if (operazione.equalsIgnoreCase("insert")) {
                    String nuovoEventoVita = request.getParameter("eventoVita");
                    if (nuovoEventoVita == null
                            || nuovoEventoVita.equalsIgnoreCase("")) {
                        return;
                    } else {
                        // Effettuo l'insert nel db
                        delegate.insertEventoVita(nuovoEventoVita);
                        dataForm.getDatiTemporanei().setEventiVita(delegate.getEventiVita());
                    }
                } else if (operazione.equalsIgnoreCase("delete")) {
                    // Effettuo la delete nel db
                    if (dataForm.getDatiTemporanei().getEventoVitaSel() != null) {
                        delegate.deleteEventoVita(Integer.parseInt((String) dataForm.getDatiTemporanei().getEventoVitaSel()));
                        dataForm.getDatiTemporanei().setEventiVita(delegate.getEventiVita());
                        ArrayList servizi = (ArrayList) dataForm.getDatiTemporanei().getServizi();
                    }
                }
            } else {
            }
            showJsp(process, action, false);
        } else if (action == null || action.equalsIgnoreCase("chiamante.jsp")) {
            enableBottomNavigationBar(process, true);

            // salvo i parametri del Procedimento Unico
            if (operazione != null && operazione.equalsIgnoreCase("insertPU")) {
                ProcedimentoUnicoDAO deleg2 = new ProcedimentoUnicoDAO(db, language);
                try {
                    deleg2.setParametriPU(request, dataForm);
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
            process.getView().getCurrentActivity().getCurrentStep().setJspPath(dataForm.getDatiTemporanei().getJspChiamante());
            setStep(process, request, getCurrentActivityIndex(process), getCurrentStepIndex(process));
        }
    }

    /**
     * Salva la serializzazione in xml del service profile su DB
     * @param serviceProfileXML xml del service profile
     * @throws peopleException
     */
    protected void saveServiceProfileXML(AbstractPplProcess process, String serviceProfileXML, int[] bookmarkId, String serviceName)
            throws peopleException {
//        String serviceCategory = "demo";
//        String serviceSubcategory = "cedaf";
//        String serviceName = "it.people.fsl.servizi.test.cedaf.serviceprofile";
//        String bookmarkId = "1";
//        boolean strongAuthentication = true;
//        boolean weakAuthentication = false;
//        boolean abilitaIntermediari = false;
//        boolean abilitaUtenteRegistrato = true;

        // Crea il manager per la gestione del ServiceProfileStore
        // il ServiceProfileStore e' l'oggetto contenente la serializzazione
        // del service profile su DB.

        ServiceProfileStoreManager spsManager = ServiceProfileStoreManager.getInstance();

        // Crea un nuovo ServiceProfileStore, passandogli il nome del servizio
        // l'identificativo del bookmark e l'xml serializzazione del service profile
        for (int i = 0; i < bookmarkId.length; i++) {


            ServiceProfileStore serviceProfileStore = spsManager.create(serviceName,
                    String.valueOf(bookmarkId[i]),
                    serviceProfileXML);

            if (serviceProfileStore != null) {
                // Salva il ServiceProfileStore
                try {
                    ServiceProfileStoreManager.getInstance().set(serviceProfileStore);
                } catch (peopleException ex) {
                    this.error(process, "Impossibile salvare il service profile");
                    ex.printStackTrace();
                } catch (Exception e) {
                    this.error(process, "Impossibile salvare il service profile");
                    e.printStackTrace();
                }
            } else {
                this.error(process, "Xml non valido");
            }
        }
    }

    /**
     * Crea l'xml per un service profile
     * @return il service profile serializzato in xml
     */
    protected String createServiceProfileXML(AbstractPplProcess process,
            String serviceName,
            String serviceCategory,
            String serviceSubcategory,
            boolean strongAuthentication,
            boolean weakAuthentication,
            boolean abilitaIntermediari,
            boolean abilitaUtenteRegistrato)
            throws Exception {
        // Crea i nodi principali
        PeopleServiceProfileDocument serviceProfileDoc = PeopleServiceProfileDocument.Factory.newInstance();
        PeopleServiceProfile serviceProfile = serviceProfileDoc.addNewPeopleServiceProfile();
        Service service = serviceProfile.addNewService();

        service.setCategory(serviceCategory);
        service.setSubcategory(serviceSubcategory);
        service.setName(serviceName);

        // Nodo Security
        SecurityProfile securityProfile = service.addNewSecurityProfile();
        securityProfile.setStrongAuthentication(strongAuthentication);
        securityProfile.setWeakAuthentication(weakAuthentication);

        // Nodo Authorization
        AuthorizationProfile authorizationProfile = service.addNewAuthorizationProfile();

        // N.B. la configurazione di accesso agli intermediari
        //      può essere dettagliata per ogni singola categoria
        //      di intermediario, quella presentata in questo step
        //      è solo una traccia all'utilizzo di xmlbeans per la
        //      creazione di un service profile valido.

        AccessoIntermediari accessoIntermediari = authorizationProfile.addNewAccessoIntermediari();
        accessoIntermediari.setEnabled(abilitaIntermediari);
        accessoIntermediari.setAll(true);
        AccessoUtentePeopleRegistrato accessoUtentePeopleRegistrato = authorizationProfile.addNewAccessoUtentePeopleRegistrato();
        accessoUtentePeopleRegistrato.setEnabled(abilitaUtenteRegistrato);

        // mostra l'xml
        //System.out.print(serviceProfileDoc.toString());

        if (!serviceProfileDoc.validate()) {
            throw new Exception("L'xml non è conforme allo schema.");
        }
        return serviceProfileDoc.toString();
    }

    protected String getCurrentJspPath(AbstractPplProcess process) {
        return process.getView().getCurrentActivity().getCurrentStep().getJspPath();
    }

    protected int getCurrentActivityIndex(AbstractPplProcess process) {
        return process.getView().getCurrentActivityIndex();
    }

    protected void setStep(AbstractPplProcess pplProcess, IRequestWrapper request, int activityIndex, int stepIndex) {
        pplProcess.getView().setCurrentActivityIndex(activityIndex, false);
        pplProcess.getView().getCurrentActivity().setCurrentStepIndex(stepIndex);
        try {
            pplProcess.getView().getCurrentActivity().getCurrentStep().service(pplProcess, request);
        } catch (IOException e) {
            logger.error("", e);
            //gestioneEccezioni(pplProcess, e);
        } catch (ServletException ex) {
            logger.error("", ex);
            //gestioneEccezioni(pplProcess, ex);
        }

    }

    protected void initBookmark(AbstractPplProcess process, IRequestWrapper request, String idBookmark) throws ProcedimentoUnicoException {

        ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
        // String xml = delegate.getBookmarkXML(idBookmark);
        String xml = delegate.getBookmarkXML(idBookmark, request.getParameter("codEnte"), request.getParameter("codEveVita"));
        // nessun bookmark trovato per l'id passato solleva un eccezione
        if (xml == null) {
            throw new ProcedimentoUnicoException("Nessun bookmark trovato per l'id "
                    + idBookmark);
        }
        //ripristina il processData al momento in cui è stato salvato nel bookmark
        process.setData((ProcessData) Bean2XML.unmarshall(ProcessData.class, xml, request.getUnwrappedRequest().getCharacterEncoding()));
        String stepId = ((ProcessData) process.getData()).getLastStepId();
        Activity[] activities = process.getView().getActivities();
        Activity activity = activities[0];
        Step step = null;
        while (activity.getStepList().size() > 1) {
            //salta lo step di informazioni sul servizio che deve sempre essere presente
            step = (Step) activity.getStepList().get(1);
            if (step.getId().equalsIgnoreCase(stepId)) {
                break;
            }
            activity.getStepList().remove(1);
        }

        int stepIndex = ((ProcessData) process.getData()).getLastStepIdx();
        String[] stepOrder = activity.getStepOrder();
        String newStepOrder = "";
        for (int i = stepIndex; i < stepOrder.length; i++) {
            if (i == (stepOrder.length - 1)) {
                newStepOrder += stepOrder[i];
            } else {
                newStepOrder += stepOrder[i] + ",";
            }
        }

        activity.setStepOrder(newStepOrder);
        LayoutMenu menu = (LayoutMenu) session.getAttribute("menuObject");
        NavigationBar navBar = (NavigationBar) session.getAttribute("navBarObject");
        if (menu != null && navBar != null) {
            menu.update(process.getView());
            navBar.update(process.getView());
        }

        if (idBookmark != null) {
            String userID = (String) session.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER);
            boolean isAnonymus = SiracHelper.isAnonymousUser(userID);
            ((ProcessData) process.getData()).setIdBookmark(idBookmark);
            String xmlPermessi = delegate.getXMLPermessiBookmark(idBookmark, (String) request.getParameter("codEnte"), (String) request.getParameter("codEveVita"));
            delegate.getDescrizioneBookmark(((ProcessData) process.getData()), idBookmark, (String) request.getParameter("codEnte"), (String) request.getParameter("codEveVita"));
            XPathReader xpr = new XPathReader(xmlPermessi);
            String tipoBookmark = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/TYPE"), "");
            String tipoFirma = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/FIRMADIGITALE"), "");
            String tipoPagamenti = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/PAGAMENTO"), "");
            String conInvioString = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/CON_INVIO"), "");
            boolean conInvio = Utilities.checked(conInvioString.equalsIgnoreCase("") ? "true" : conInvioString);
            if (tipoBookmark.equalsIgnoreCase(Costant.bookmarkTypeCompleteLabel)) {
                if (isAnonymus) {
                    if (conInvio) {
                        ((ProcessData) process.getData()).setTipoBookmark(Costant.bookmarkTypeCortesiaLabel);
                    } else {
                        ((ProcessData) process.getData()).setTipoBookmark(Costant.bookmarkTypeLivello2Label);
                    }
                } else {
                    ((ProcessData) process.getData()).setTipoBookmark(Costant.bookmarkTypeCompleteLabel);
                }
            } else if (tipoBookmark.equalsIgnoreCase(Costant.bookmarkTypeCortesiaLabel)) {
                if (isAnonymus) {
                    ((ProcessData) process.getData()).setTipoBookmark(Costant.bookmarkTypeCortesiaLabel);
                } else {
                    ((ProcessData) process.getData()).setTipoBookmark(Costant.bookmarkTypeCortesiaLabel);
                }
            } else {
                ((ProcessData) process.getData()).setTipoBookmark(Costant.bookmarkTypeLivello2Label);
            }


            if (tipoBookmark.equalsIgnoreCase(Costant.bookmarkTypeCompleteLabel)) {
                if (tipoFirma.equalsIgnoreCase(Costant.conFirmaLabel)) {
                    if (isAnonymus) {
                        ((ProcessData) process.getData()).setFirmaBookmark(Costant.senzaFirmaLabel);
                        if (conInvio) {
                            ((ProcessData) process.getData()).setTipoBookmark(Costant.bookmarkTypeCortesiaLabel);
                        } else {
                            ((ProcessData) process.getData()).setTipoBookmark(Costant.bookmarkTypeLivello2Label);
                        }
                        ((ProcessData) process.getData()).setTipoPagamentoBookmark(Costant.disabilitaPagamentoLabel);
                        tipoPagamenti = new String(Costant.disabilitaPagamentoLabel);
                    } else {
                        ((ProcessData) process.getData()).setFirmaBookmark(Costant.conFirmaLabel);
                    }
                } else {
                    ((ProcessData) process.getData()).setFirmaBookmark(Costant.senzaFirmaLabel);
//	    			((ProcessData)process.getData()).setTipoBookmark(Costant.bookmarkTypeCortesiaLabel);
//	    			((ProcessData)process.getData()).setTipoPagamentoBookmark(Costant.disabilitaPagamentoLabel);
//	    			tipoPagamenti = new String(Costant.disabilitaPagamentoLabel);
                }
                if (tipoPagamenti.equalsIgnoreCase(Costant.forzaPagamentoLabel)) {
                    ((ProcessData) process.getData()).setTipoPagamentoBookmark(Costant.forzaPagamentoLabel);
                } else if (tipoPagamenti.equalsIgnoreCase(Costant.disabilitaPagamentoLabel)) {
                    ((ProcessData) process.getData()).setTipoPagamentoBookmark(Costant.disabilitaPagamentoLabel);
                } else {
                    ((ProcessData) process.getData()).setTipoPagamentoBookmark(Costant.pagamentoOpzionaleLabel);
                }
            } else if (tipoBookmark.equalsIgnoreCase(Costant.bookmarkTypeCortesiaLabel)) {
                ((ProcessData) process.getData()).setFirmaBookmark(Costant.senzaFirmaLabel);
                ((ProcessData) process.getData()).setTipoPagamentoBookmark(Costant.disabilitaPagamentoLabel);
            } else { // livello 2
                ((ProcessData) process.getData()).setTipoPagamentoBookmark(Costant.disabilitaPagamentoLabel);
            }

            if (tipoPagamenti.equalsIgnoreCase(Costant.forzaPagamentoLabel)) {
                ((ProcessData) process.getData()).setAttivaPagamenti(true);
            } else if (tipoPagamenti.equalsIgnoreCase(Costant.disabilitaPagamentoLabel)) {
                ((ProcessData) process.getData()).setAttivaPagamenti(false);
            }

            //

            if (isAnonymus) {
                ((ProcessData) process.getData()).setAttivaPagamenti(false);
                ((ProcessData) process.getData()).setFirmaBookmark(Costant.senzaFirmaLabel);
            }
        }


    }

    protected void settaParametriModelloUnico(AbstractPplProcess process, HttpSession session) {

        ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
        String xmlPermessi = "";
        try {
            xmlPermessi = delegate.getXMLPermessiPU(((ProcessData) process.getData()).getIdentificatoreUnivoco().getCodiceSistema().getCodiceAmministrazione());
        } catch (Exception e) {
        }
        XPathReader xpr = new XPathReader(xmlPermessi);
        String tipoBookmark = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/TYPE"), "");
        String tipoFirma = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/FIRMADIGITALE"), "");
        String tipoPagamenti = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/PAGAMENTO"), "");
        String modalitaPagamenti = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/MODALITA_PAGAMENTO"), "");
        String modalitaPagamentiOpzionali = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/MODALITA_PAGAMENTO_OPZIONALE"), "");
        String conInvioString = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/CON_INVIO"), "");


        String userID = (String) session.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER);
        boolean isAnonymus = SiracHelper.isAnonymousUser(userID);

        boolean conInvio = Utilities.checked(conInvioString.equalsIgnoreCase("") ? "true" : conInvioString);
        ParametriPUBean parametri = new ParametriPUBean();
        parametri.setConInvio(conInvio);
        parametri.setTipo(tipoBookmark);
        parametri.setPagamenti(tipoPagamenti);
        parametri.setModalitaPagamenti(modalitaPagamenti);
        parametri.setModalitaPagamentiOpzionali(modalitaPagamentiOpzionali);
        parametri.setAbilitaFirma(Utilities.checked(tipoFirma.equalsIgnoreCase("") ? "true" : tipoFirma));
        ((ProcessData) process.getData()).getDatiTemporanei().setParametriPU(parametri);

        if (tipoBookmark.equalsIgnoreCase(Costant.bookmarkTypeCompleteLabel)) {
            if (isAnonymus) {
                if (conInvio) {
                    ((ProcessData) process.getData()).setTipoBookmark(Costant.bookmarkTypeCortesiaLabel);
                } else {
                    ((ProcessData) process.getData()).setTipoBookmark(Costant.bookmarkTypeLivello2Label);
                }
            } else {
                ((ProcessData) process.getData()).setTipoBookmark(Costant.bookmarkTypeCompleteLabel);
            }
        } else if (tipoBookmark.equalsIgnoreCase(Costant.bookmarkTypeCortesiaLabel)) {
            if (isAnonymus) {
                ((ProcessData) process.getData()).setTipoBookmark(Costant.bookmarkTypeCortesiaLabel);
            } else {
                ((ProcessData) process.getData()).setTipoBookmark(Costant.bookmarkTypeCortesiaLabel);
            }
        } else {
            ((ProcessData) process.getData()).setTipoBookmark(Costant.bookmarkTypeLivello2Label);
        }


        if (tipoBookmark.equalsIgnoreCase(Costant.bookmarkTypeCompleteLabel)) {
            if (tipoFirma.equalsIgnoreCase(Costant.conFirmaLabel)) {
                if (isAnonymus) {
                    ((ProcessData) process.getData()).setFirmaBookmark(Costant.senzaFirmaLabel);
                    if (conInvio) {
                        ((ProcessData) process.getData()).setTipoBookmark(Costant.bookmarkTypeCortesiaLabel);
                    } else {
                        ((ProcessData) process.getData()).setTipoBookmark(Costant.bookmarkTypeLivello2Label);
                    }
                    ((ProcessData) process.getData()).setTipoPagamentoBookmark(Costant.disabilitaPagamentoLabel);
                    ((ProcessData) process.getData()).setModalitaPagamentoBookmarkSoloOnLine(true);
                    ((ProcessData) process.getData()).setModalitaPagamentoSoloOnLine(true);
                    ((ProcessData) process.getData()).setModalitaPagamentoOpzionaleSoloOnLine(true);
                    tipoPagamenti = new String(Costant.disabilitaPagamentoLabel);
                    modalitaPagamenti = new String(Costant.modalitaPagamentoSoloOnlineLabel);
                    modalitaPagamentiOpzionali = new String(Costant.modalitaPagamentoSoloOnlineLabel);
                } else {
                    ((ProcessData) process.getData()).setFirmaBookmark(Costant.conFirmaLabel);
                }
            } else {
                ((ProcessData) process.getData()).setFirmaBookmark(Costant.senzaFirmaLabel);
//    			((ProcessData)process.getData()).setTipoBookmark(Costant.bookmarkTypeCortesiaLabel);
//    			((ProcessData)process.getData()).setTipoPagamentoBookmark(Costant.disabilitaPagamentoLabel);
//    			tipoPagamenti = new String(Costant.disabilitaPagamentoLabel);
            }
            if (tipoPagamenti.equalsIgnoreCase(Costant.forzaPagamentoLabel)) {
                ((ProcessData) process.getData()).setTipoPagamentoBookmark(Costant.forzaPagamentoLabel);
                ((ProcessData) process.getData()).setModalitaPagamentoSoloOnLine(modalitaPagamentiOpzionali.equalsIgnoreCase(Costant.modalitaPagamentoSoloOnlineLabel));
                ((ProcessData) process.getData()).setModalitaPagamentoOpzionaleSoloOnLine(modalitaPagamentiOpzionali.equalsIgnoreCase(Costant.modalitaPagamentoSoloOnlineLabel));
                ((ProcessData) process.getData()).setModalitaPagamentoBookmarkSoloOnLine(modalitaPagamentiOpzionali.equalsIgnoreCase(Costant.modalitaPagamentoSoloOnlineLabel));
                ((ProcessData) process.getData()).setModalitaPagamentoOpzionaleBookmarkSoloOnLine(modalitaPagamentiOpzionali.equalsIgnoreCase(Costant.modalitaPagamentoSoloOnlineLabel));
            } else if (tipoPagamenti.equalsIgnoreCase(Costant.disabilitaPagamentoLabel)) {
                ((ProcessData) process.getData()).setTipoPagamentoBookmark(Costant.disabilitaPagamentoLabel);
                ((ProcessData) process.getData()).setModalitaPagamentoSoloOnLine(true);
                ((ProcessData) process.getData()).setModalitaPagamentoOpzionaleSoloOnLine(true);
                ((ProcessData) process.getData()).setModalitaPagamentoBookmarkSoloOnLine(true);
                ((ProcessData) process.getData()).setModalitaPagamentoOpzionaleBookmarkSoloOnLine(true);
            } else {
                ((ProcessData) process.getData()).setTipoPagamentoBookmark(Costant.pagamentoOpzionaleLabel);
                ((ProcessData) process.getData()).setModalitaPagamentoSoloOnLine(modalitaPagamentiOpzionali.equalsIgnoreCase(Costant.modalitaPagamentoSoloOnlineLabel));
                ((ProcessData) process.getData()).setModalitaPagamentoOpzionaleSoloOnLine(modalitaPagamentiOpzionali.equalsIgnoreCase(Costant.modalitaPagamentoSoloOnlineLabel));
                ((ProcessData) process.getData()).setModalitaPagamentoBookmarkSoloOnLine(modalitaPagamentiOpzionali.equalsIgnoreCase(Costant.modalitaPagamentoSoloOnlineLabel));
                ((ProcessData) process.getData()).setModalitaPagamentoOpzionaleBookmarkSoloOnLine(modalitaPagamentiOpzionali.equalsIgnoreCase(Costant.modalitaPagamentoSoloOnlineLabel));
            }
        } else if (tipoBookmark.equalsIgnoreCase(Costant.bookmarkTypeCortesiaLabel)) {
            ((ProcessData) process.getData()).setFirmaBookmark(Costant.senzaFirmaLabel);
            ((ProcessData) process.getData()).setTipoPagamentoBookmark(Costant.disabilitaPagamentoLabel);
            ((ProcessData) process.getData()).setModalitaPagamentoSoloOnLine(true);
            ((ProcessData) process.getData()).setModalitaPagamentoOpzionaleSoloOnLine(true);
            ((ProcessData) process.getData()).setModalitaPagamentoBookmarkSoloOnLine(true);
            ((ProcessData) process.getData()).setModalitaPagamentoOpzionaleBookmarkSoloOnLine(true);
        } else { // livello 2
            ((ProcessData) process.getData()).setTipoPagamentoBookmark(Costant.disabilitaPagamentoLabel);
            ((ProcessData) process.getData()).setModalitaPagamentoSoloOnLine(true);
            ((ProcessData) process.getData()).setModalitaPagamentoOpzionaleSoloOnLine(true);
            ((ProcessData) process.getData()).setModalitaPagamentoBookmarkSoloOnLine(true);
            ((ProcessData) process.getData()).setModalitaPagamentoOpzionaleBookmarkSoloOnLine(true);
        }

        if (tipoPagamenti.equalsIgnoreCase(Costant.forzaPagamentoLabel)) {
            ((ProcessData) process.getData()).setAttivaPagamenti(true);
            ((ProcessData) process.getData()).setModalitaPagamentoSoloOnLine(modalitaPagamentiOpzionali.equalsIgnoreCase(Costant.modalitaPagamentoSoloOnlineLabel));
            ((ProcessData) process.getData()).setModalitaPagamentoOpzionaleSoloOnLine(modalitaPagamentiOpzionali.equalsIgnoreCase(Costant.modalitaPagamentoSoloOnlineLabel));
        } else if (tipoPagamenti.equalsIgnoreCase(Costant.disabilitaPagamentoLabel)) {
            ((ProcessData) process.getData()).setAttivaPagamenti(false);
            ((ProcessData) process.getData()).setModalitaPagamentoSoloOnLine(true);
            ((ProcessData) process.getData()).setModalitaPagamentoOpzionaleSoloOnLine(true);
        } else {
            ((ProcessData) process.getData()).setModalitaPagamentoSoloOnLine(modalitaPagamentiOpzionali.equalsIgnoreCase(Costant.modalitaPagamentoSoloOnlineLabel));
            ((ProcessData) process.getData()).setModalitaPagamentoOpzionaleSoloOnLine(modalitaPagamentiOpzionali.equalsIgnoreCase(Costant.modalitaPagamentoSoloOnlineLabel));
        }

        //

        if (isAnonymus) {
            ((ProcessData) process.getData()).setAttivaPagamenti(false);
            ((ProcessData) process.getData()).setModalitaPagamentoSoloOnLine(true);
            ((ProcessData) process.getData()).setModalitaPagamentoOpzionaleSoloOnLine(true);
            ((ProcessData) process.getData()).setFirmaBookmark(Costant.senzaFirmaLabel);
        }

    }

    protected void checkRecoveryBookmark(AbstractPplProcess process, IRequestWrapper request) throws Exception {
        ProcessData dataForm = (ProcessData) process.getData();
        String procId = (String) request.getParameter("processId");
        if (procId != null && request.getAttribute("recoveryProcess") == null) {
            request.setAttribute("recoveryProcess", "Y");
            if (dataForm.getIdBookmark() != null) {
                // controllo che non siano attivi i bandi e in caso affermativo controllo che il bando sia attivo
                ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
                String bandiString = params.get("bandi");
                if (bandiString != null && bandiString.equalsIgnoreCase("true")) {
                    if (!isBandoValido(dataForm.getIdBookmark(), request)) {
                        request.setAttribute("bookmarkNonDisponibile", "true");
                        process.getView().setBottomNavigationBarEnabled(false);
                        process.getView().setBottomSaveBarEnabled(false);
                        showJsp(process, "bandi_error.jsp", false);
                        return;
                    }
                }
                PplUser peopleUser = PeopleContext.create(request.getUnwrappedRequest()).getUser();
                ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
                boolean ok = delegate.checkAccessList(process, dataForm.getIdBookmark(), peopleUser.getUserData().getCodiceFiscale());
                if (!ok) {
                    request.setAttribute("bookmarkAccessListLock", "true");
                    process.getView().setBottomNavigationBarEnabled(false);
                    process.getView().setBottomSaveBarEnabled(false);
                    showJsp(process, "bandi_error.jsp", false);
                    return;
                }
                // fine controllo bandi

                recoveryBookmark(process, request, dataForm.getIdBookmark());

//				ServiceParameters params = (ServiceParameters) request.getUnwrappedRequest().getSession().getAttribute("serviceParameters");
                String intermed = params.get("abilita_intermediari");
                if (intermed != null && intermed.equalsIgnoreCase("true")) {
                    ManagerIntermediari.rebuildBeanIntermediari(request, dataForm);
                }

            }
        }
    }

    protected void recoveryBookmark(AbstractPplProcess process, IRequestWrapper request, String idBookmark) throws ProcedimentoUnicoException {

        ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
        //String xml = delegate.getBookmarkXML(idBookmark);
        String xml = delegate.getBookmarkXML(idBookmark, request.getParameter("codEnte"), request.getParameter("codEveVita"));
        //nessun bookmark trovato per l'id passato solleva un eccezione
        if (xml == null) {
            throw new ProcedimentoUnicoException("Nessun bookmark trovato per l'id "
                    + idBookmark);
        }
        //ripristina il processData al momento in cui � stato salvato nel bookmark
        int activityIndex = ((ProcessData) process.getData()).getLastActivityIdx();
        ProcessData pdTemporaneo = (ProcessData) Bean2XML.unmarshall(ProcessData.class, xml, request.getUnwrappedRequest().getCharacterEncoding());

        ArrayList stepBookmark = new ArrayList();
        String stepId = pdTemporaneo.getLastStepId();
        String[] stepBookmarkArray = null;
        if (stepId != null) {

            boolean stepBuono = false;
            stepBookmark.add(process.getView().getActivities()[0].getStepOrder()[0]);
            for (int i = 1; i < process.getView().getActivities()[0].getStepOrder().length; i++) {
                if (!stepBuono && (process.getView().getActivities()[0].getStepOrder()[i].equalsIgnoreCase(stepId))) {
                    stepBuono = true;
                }
                if (stepBuono) {
                    stepBookmark.add(process.getView().getActivities()[0].getStepOrder()[i]);
                }
            }
            stepBookmarkArray = new String[stepBookmark.size()];
            stepBookmark.toArray(stepBookmarkArray);
        }
        String ss = "";
        for (int i = 0; i < stepBookmarkArray.length; i++) {
            ss += stepBookmarkArray[i] + ",";
        }
        ss = ss.substring(0, ss.length() - 1);


        Activity[] activities = process.getView().getActivities();
        Activity activity = activities[0];


        Step step = null;
        while (activity.getStepList().size() > 1) {
            //salta lo step di informazioni sul servizio che deve sempre essere presente
            step = (Step) activity.getStepList().get(1);
            if (step.getId().equalsIgnoreCase(stepId)) {
                break;
            }
            activity.getStepList().remove(1);
        }

        activity.setStepOrder(ss);


        LayoutMenu menu = (LayoutMenu) session.getAttribute("menuObject");
        NavigationBar navBar = (NavigationBar) session.getAttribute("navBarObject");
        if (menu != null && navBar != null) {
            menu.update(process.getView());
            navBar.update(process.getView());
        }

        PplUser peopleUser = PeopleContext.create(request.getUnwrappedRequest()).getUser();
        ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
//        boolean amministratoreBookmark = false;
        String codFis = ((ProcessData) process.getData()).getRichiedente().getUtenteAutenticato().getCodiceFiscale();
        boolean amministratoreBookmark = delegate.isParametroConfigurazionePresent(process.getCommune().getOid(), "amministratoreBookmark", codFis);
        boolean amministratorePU = delegate.isParametroConfigurazionePresent(process.getCommune().getOid(), "amministratorePU", codFis);
        boolean amministratoreStampe = delegate.isParametroConfigurazionePresent(process.getCommune().getOid(), "amministratoreStampe", codFis);
        peopleUser.addExtendedAttribute(Costant.PplUser.AMMINISTRATORE_BOOKMARK, new Boolean(amministratoreBookmark));
        peopleUser.addExtendedAttribute(Costant.PplUser.AMMINISTRATORE_PROCEDIMENTO_UNICO, new Boolean(amministratorePU));
        peopleUser.addExtendedAttribute(Costant.PplUser.AMMINISTRATORE_STAMPE, new Boolean(amministratoreStampe));

//        if (trovato) {
//            peopleUser.setPeopleAdmin(true);
//        } else {
//            peopleUser.setPeopleAdmin(false);
//        }

        if (idBookmark != null) {
            ((ProcessData) process.getData()).setIdBookmark(idBookmark);
        }
        try {
            delegate.recoveryCampiCollegatiDichiarazioniDinamiche((ProcessData) process.getData());
        } catch (Exception e) {
            logger.error("", e);
        }
        setStep(process, request, activityIndex, ((ProcessData) process.getData()).getLastStepIdx());

    }

    public String getCampoDinamicoAnagrafica(ArrayList listaCampi, String campoXmlMod) {
        boolean trovato = false;
        String ret = "";
        Iterator it = listaCampi.iterator();
        while (it.hasNext() && !trovato) {
            HrefCampiBean campoAnagrafica = (HrefCampiBean) it.next();
            if (campoAnagrafica.getCampo_xml_mod() != null && campoAnagrafica.getCampo_xml_mod().equalsIgnoreCase(campoXmlMod)) {
                trovato = true;
                ret = campoAnagrafica.getValoreUtente();
            }
        }
        return ret;
    }

    public boolean checkValiditaRiepiloghi(ProcessData dataForm) {
        boolean valido = true;
        try {
            Iterator it = dataForm.getListaSportelli().keySet().iterator();
            while (it.hasNext() && valido) {
                String codSportello = (String) it.next();
                SportelloBean sportello = (SportelloBean) dataForm.getListaSportelli().get(codSportello);
                boolean riepilogoTrovato = false;
                for (Iterator iterator = sportello.getListaAllegati().iterator(); iterator.hasNext();) {
                    Attachment attach = (Attachment) iterator.next();
                    if ((attach instanceof SignedSummaryAttachment) || (attach instanceof UnsignedSummaryAttachment)) {
                        riepilogoTrovato = true;
                    }
                }
                valido = valido && riepilogoTrovato;
            }
        } catch (Exception e) {
        	logger.error("", e);
            e.printStackTrace();
        }
        return valido;
    }
    protected void doSave(AbstractPplProcess process, IRequestWrapper request) {
        String buttonSave = request.getParameter("navigation.button.save");
        if (buttonSave != null && buttonSave.equalsIgnoreCase("Salva")) {
            if (session == null) {
                session = request.getUnwrappedRequest().getSession();
            }
            ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
            String intermed = params.get("abilita_intermediari");
            if (intermed != null && intermed.equalsIgnoreCase("true")) {
                try {
                    ProcessData dataForm = (ProcessData) process.getData();
                    if (dataForm.getAnagrafica() != null && dataForm.getAnagrafica().getListaCampi() != null) {
                        String capAzienda = getCampoDinamicoAnagrafica(dataForm.getAnagrafica().getListaCampi(), "ANAG_RAPPSOC_CAPSEDE");
                        String cittaAzienda = getCampoDinamicoAnagrafica(dataForm.getAnagrafica().getListaCampi(), "ANAG_RAPPSOC_COMUNESEDE");
                        String indirizzoAzienda = getCampoDinamicoAnagrafica(dataForm.getAnagrafica().getListaCampi(), "ANAG_RAPPSOC_VIASEDE");
                        String mailLegaleRappresentanteAzienda = getCampoDinamicoAnagrafica(dataForm.getAnagrafica().getListaCampi(), "ANAG_RAPPSOC_EMAILSEDE");
                        String telefonoLegaleRappresentanteAzienda = getCampoDinamicoAnagrafica(dataForm.getAnagrafica().getListaCampi(), "ANAG_RAPPSOC_TELSEDE");
                        ManagerIntermediari.sendSegnalazionePratica(process, request, capAzienda, cittaAzienda, indirizzoAzienda, mailLegaleRappresentanteAzienda, telefonoLegaleRappresentanteAzienda);
                    } else {
                        ManagerIntermediari.sendSegnalazionePratica(process, request, null, null, null, null, null);
                    }
                } catch (Exception e) {
                    logger.error("", e);
                } catch (ServiceException e) {
                    logger.error("", e);
                }
            }

        }
    }

    public boolean isBandoValido(String idBookmark, IRequestWrapper request) {
        ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
        boolean valido = true;
        try {
            Date ds = delegate.getDataScadenza(idBookmark);
            if (ds != null) {
                request.setAttribute("dataFineValidita", (new SimpleDateFormat("dd/MM/yyyy")).format(ds));
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTimeInMillis(ds.getTime());
                gc.add(GregorianCalendar.DAY_OF_WEEK, 1);
                Date primogiornoNonValido = new Date(gc.getTimeInMillis());
                Date now = new Date();
                if (now.after(primogiornoNonValido)) {
                    valido = false;
                }
            }
            ds = delegate.getDataInizioValidita(idBookmark);
            if (ds != null) {
                request.setAttribute("dataInvioValidita", (new SimpleDateFormat("dd/MM/yyyy")).format(ds));
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTimeInMillis(ds.getTime());
                Date primogiornoValido = new Date(gc.getTimeInMillis());
                Date now = new Date();
                if (primogiornoValido.after(now)) {
                    valido = false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return valido;
    }

    public ArrayList buildAnagrafica(ProcessData dataForm) {
        ArrayList listaAnagrafiche = new ArrayList();
        String richiedente = "";
        String nome = "";
        String cognome = "";
        String codFisc = "";
        String inQualitaDi = null;
        String inQualitaDiValore = null;
        ArrayList lista = dataForm.getAnagrafica().getListaCampi();
        for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
            HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator.next();
            String campoXMLmod = campoAnagrafica.getCampo_xml_mod();
            if (campoXMLmod != null && campoXMLmod.equalsIgnoreCase("ANAG_DICHIARANTE_NOME")) {
                nome = Utilities.NVL(campoAnagrafica.getValoreUtente(), "-");
            } else if (campoXMLmod != null && campoXMLmod.equalsIgnoreCase("ANAG_DICHIARANTE_COGNOME")) {
                cognome = Utilities.NVL(campoAnagrafica.getValoreUtente(), "-");
            } else if (campoXMLmod != null && campoXMLmod.equalsIgnoreCase("ANAG_CODFISC_DICHIARANTE")) {
                codFisc = Utilities.NVL(campoAnagrafica.getValoreUtente(), "-");
            } else if (campoXMLmod != null && campoXMLmod.indexOf("ANAG_DICHIARANTE_QUALITA_") != -1 && Utilities.isset(campoAnagrafica.getValoreUtente())) {
                inQualitaDi = Utilities.NVL(campoAnagrafica.getDescrizione(), "-");
                inQualitaDiValore = campoAnagrafica.getValoreUtente();
            }
        }
        richiedente = nome.toUpperCase() + " " + cognome.toUpperCase() + " - " + codFisc.toUpperCase();
        // richiedente = richiedente + (inQualitaDi!=null?" ("+inQualitaDi+")":"");
        if (inQualitaDi != null) {
            richiedente += getInQualitaDi(inQualitaDiValore, inQualitaDi, dataForm.getAnagrafica().getListaCampi());
        }
        listaAnagrafiche.add(richiedente);
        if (dataForm.getAltriRichiedenti() != null && dataForm.getAltriRichiedenti().size() > 0) {
            for (Iterator it = dataForm.getAltriRichiedenti().iterator(); it.hasNext();) {
                AnagraficaBean anagrafica = (AnagraficaBean) it.next();
                richiedente = "";
                nome = "";
                cognome = "";
                codFisc = "";
                inQualitaDi = null;
                inQualitaDiValore = null;
                for (Iterator iterator = anagrafica.getListaCampi().iterator(); iterator.hasNext();) {
                    HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator.next();
                    String campoXMLmod = campoAnagrafica.getCampo_xml_mod();
                    if (campoXMLmod != null && campoXMLmod.equalsIgnoreCase("ANAG_DICHIARANTE_NOME")) {
                        nome = Utilities.NVL(campoAnagrafica.getValoreUtente(), "-");
                    } else if (campoXMLmod != null && campoXMLmod.equalsIgnoreCase("ANAG_DICHIARANTE_COGNOME")) {
                        cognome = Utilities.NVL(campoAnagrafica.getValoreUtente(), "-");
                    } else if (campoXMLmod != null && campoXMLmod.equalsIgnoreCase("ANAG_CODFISC_DICHIARANTE")) {
                        codFisc = Utilities.NVL(campoAnagrafica.getValoreUtente(), "-");
                    } else if (campoXMLmod != null && campoXMLmod.indexOf("ANAG_DICHIARANTE_QUALITA_") != -1 && Utilities.isset(campoAnagrafica.getValoreUtente())) {
                        inQualitaDi = Utilities.NVL(campoAnagrafica.getDescrizione(), "-");
                        inQualitaDiValore = campoAnagrafica.getValoreUtente();
                    }
                }
                richiedente = nome.toUpperCase() + " " + cognome.toUpperCase() + " - " + codFisc.toUpperCase();
                // richiedente = richiedente + (inQualitaDi!=null?" ("+inQualitaDi+")":"");
                if (inQualitaDi != null) {
                    richiedente += getInQualitaDi(inQualitaDiValore, inQualitaDi, anagrafica.getListaCampi());
                }
                listaAnagrafiche.add(richiedente);
            }
        }
        return listaAnagrafiche;
    }

    private String getInQualitaDi(String inQualitaDiValore, String inQualitaDiDescrizione, ArrayList listaCampi) {
        String ret = "<br>";
        if (inQualitaDiValore.equalsIgnoreCase("1")) {
            ret += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
            String denom = "";
            String piva = "";
            for (Iterator iterator = listaCampi.iterator(); iterator.hasNext();) {
                HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator.next();
                String campoXMLmod = campoAnagrafica.getCampo_xml_mod();
                if (campoXMLmod != null && campoXMLmod.equalsIgnoreCase("ANAG_RAPPSOC_DENOM")) {
                    denom = Utilities.NVL(campoAnagrafica.getValoreUtente(), "-");
                } else if (campoXMLmod != null && campoXMLmod.equalsIgnoreCase("ANAG_RAPPSOC_PIVA")) {
                    piva = Utilities.NVL(campoAnagrafica.getValoreUtente(), "-");
                }
            }
            ret += "( " + inQualitaDiDescrizione + " <i>" + denom.toUpperCase() + " " + piva.toUpperCase() + "</i> )";
        } else if (inQualitaDiValore.equalsIgnoreCase("2")) {
            ret += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
            ret += "( " + inQualitaDiDescrizione + " )";
        } else if (inQualitaDiValore.equalsIgnoreCase("5")) {
            ret += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
            String denom = "";
            String piva = "";
            String nomeP = "";
            String cognomeP = "";
            String codFiscP = "";
            String rb_PERSFISICA = "";
            String rb_PERSGIURIDICA = "";
            for (Iterator iterator = listaCampi.iterator(); iterator.hasNext();) {
                HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator.next();
                String campoXMLmod = campoAnagrafica.getCampo_xml_mod();
                if (campoXMLmod != null && campoXMLmod.equalsIgnoreCase("ANAG_PROF_PERSFISICA")) {
                    rb_PERSFISICA = Utilities.NVL(campoAnagrafica.getValoreUtente(), "");
                } else if (campoXMLmod != null && campoXMLmod.equalsIgnoreCase("ANAG_PROF_PERSGIURIDICA")) {
                    rb_PERSGIURIDICA = Utilities.NVL(campoAnagrafica.getValoreUtente(), "");
                } else if (campoXMLmod != null && campoXMLmod.equalsIgnoreCase("ANAG_PGIURIDICA_DENOM")) {
                    denom = Utilities.NVL(campoAnagrafica.getValoreUtente(), "-");
                } else if (campoXMLmod != null && campoXMLmod.equalsIgnoreCase("ANAG_PGIURIDICA_PIVA")) {
                    piva = Utilities.NVL(campoAnagrafica.getValoreUtente(), "-");
                } else if (campoXMLmod != null && campoXMLmod.equalsIgnoreCase("ANAG_PFISICA_NOME")) {
                    nomeP = Utilities.NVL(campoAnagrafica.getValoreUtente(), "-");
                } else if (campoXMLmod != null && campoXMLmod.equalsIgnoreCase("ANAG_PFISICA_COGNOME")) {
                    cognomeP = Utilities.NVL(campoAnagrafica.getValoreUtente(), "-");
                } else if (campoXMLmod != null && campoXMLmod.equalsIgnoreCase("ANAG_PFISICA_CODFISC")) {
                    codFiscP = Utilities.NVL(campoAnagrafica.getValoreUtente(), "-");
                }
            }
            if (Utilities.isset(rb_PERSFISICA)) {
                ret += "( " + inQualitaDiDescrizione + " <i>" + nomeP + " " + cognomeP + " " + codFiscP + "</i> )";
            } else if (Utilities.isset(rb_PERSGIURIDICA)) {
                ret += "( " + inQualitaDiDescrizione + " <i>" + denom + " " + piva + "</i> )";
            }
        }
        return ret;
    }

    public String recuperaRiepilogoStatico(IRequestWrapper request, AbstractPplProcess process, int idxSportello, ArrayList listaAnagrafiche) {
        String html = "";
        Set chiaviSportelli = ((ProcessData) process.getData()).getListaSportelli().keySet();
        boolean trovato = false;
        SportelloBean sportello = null;
        Iterator it = chiaviSportelli.iterator();
        while (it.hasNext() && !trovato) {
            String chiaveSportelli = (String) it.next();
            sportello = (SportelloBean) ((ProcessData) process.getData()).getListaSportelli().get(chiaveSportelli);
            if (sportello.getIdx() == idxSportello) {
                trovato = true;
            }
        }
        if (sportello != null) {
            BuilderHtml bh = new BuilderHtml();
            html = bh.generateRiepilogoNonFirmatoSmall(request, process, sportello, listaAnagrafiche);
        }
        return html;
    }
    public boolean checkValidita(ProcessData dataForm, ArrayList errors, ArrayList listaCampiHref) {
        boolean complete = true;
        for (Iterator iterator = listaCampiHref.iterator(); iterator.hasNext();) {
            HrefCampiBean campo = (HrefCampiBean) iterator.next();
            if (campo.getTipo().equalsIgnoreCase("I") || campo.getTipo().equalsIgnoreCase("A")) {
                if (campo.getTp_controllo().equalsIgnoreCase("D") && campo.getValoreUtente() != null && !campo.getValoreUtente().equalsIgnoreCase("") && !checkValidFormatDate(campo.getValoreUtente())) {
                    complete = false;
                    // PC - nuovo controllo
                    // errors.add("Il campo '<i>" + campo.getDescrizione() + "</i> ' non correttamente formattato (gg/mm/yyyy) o data non valida");
                    errors.add(errorMessage(campo.getErr_msg(), "Il campo '<i>" + campo.getDescrizione() + "</i> ' non correttamente formattato (gg/mm/yyyy) o data non valida"));
                    // PC - nuovo controllo
                } else if (campo.getTp_controllo().equalsIgnoreCase("F") && campo.getValoreUtente() != null && !campo.getValoreUtente().equalsIgnoreCase("") && !checkValidCodiceFiscale(campo.getValoreUtente())) {
                    complete = false;
                    // PC - nuovo controllo
                    // errors.add("Il campo '<i>" + campo.getDescrizione() + "</i> ' non Ã¨ un codice fiscale corretto");
                    errors.add(errorMessage(campo.getErr_msg(), "Il campo '<i>" + campo.getDescrizione() + "</i> ' non Ã¨ un codice fiscale corretto"));
                    // PC - nuovo controllo
                } else if (campo.getTp_controllo().equalsIgnoreCase("I") && campo.getValoreUtente() != null && !campo.getValoreUtente().equalsIgnoreCase("") && !checkValidPartitaIva(campo.getValoreUtente())) {
                    complete = false;
                    // PC - nuovo controllo
                    // errors.add("Il campo '<i>" + campo.getDescrizione() + "</i> ' non Ã¨ una partita iva corretta");
                    errors.add(errorMessage(campo.getErr_msg(), "Il campo '<i>" + campo.getDescrizione() + "</i> ' non Ã¨ una partita iva corretta"));
                    // PC - nuovo controllo
                } else if (campo.getTp_controllo().equalsIgnoreCase("N") && campo.getValoreUtente() != null && !campo.getValoreUtente().equalsIgnoreCase("") && !checkValidNumeric(campo.getValoreUtente(), campo.getLunghezza(), campo.getDecimali())) {
                    complete = false;
                    // PC - nuovo controllo
                    // errors.add("Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere numerico (massima lunghezza " + campo.getLunghezza() + " di cui " + campo.getDecimali() + " decimali");
                    errors.add(errorMessage(campo.getErr_msg(), "Il campo '<i>" + campo.getDescrizione() + "</i> ' deve essere numerico (massima lunghezza " + campo.getLunghezza() + " di cui " + campo.getDecimali() + " decimali"));
                    // PC - nuovo controllo
                } // PC - nuovo controllo
                else if (campo.getTp_controllo().equalsIgnoreCase("X") && campo.getValoreUtente() != null && !campo.getValoreUtente().equalsIgnoreCase("") && !checkValidRegExp(campo.getValoreUtente(), campo.getPattern())) {
                    complete = false;
                    errors.add(errorMessage(campo.getErr_msg(), "Il campo '<i>" + campo.getDescrizione() + "</i> ' formalmente non corretto"));
                    // PC - nuovo controllo
                } else if (campo.getValoreUtente() != null && !campo.getValoreUtente().equalsIgnoreCase("") && campo.getLunghezza() < campo.getValoreUtente().length()) {
                    complete = false;
                    // PC - nuovo controllo
                    // errors.add("Il campo '<i>" + campo.getDescrizione() + "</i> ' puÃ² contenere al massimo " + campo.getLunghezza() + " caratteri");
                    errors.add(errorMessage(campo.getErr_msg(), "Il campo '<i>" + campo.getDescrizione() + "</i> ' puÃ² contenere al massimo " + campo.getLunghezza() + " caratteri"));
                    // PC - nuovo controllo
                }// PC - nuovo controllo
                else if (campo.getTp_controllo().equalsIgnoreCase("C") && campo.getValoreUtente() != null && !campo.getValoreUtente().equalsIgnoreCase("") && !checkValidCodiceFiscalePIVA(campo.getValoreUtente())) {
                    complete = false;
                    errors.add(errorMessage(campo.getErr_msg(), "Il campo '<i>" + campo.getDescrizione() + "</i> ' codice fiscale o partita IVA formalmente non corretti"));
                    // PC - nuovo controllo
                }
            }
        }
        return complete;
    }

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

}
