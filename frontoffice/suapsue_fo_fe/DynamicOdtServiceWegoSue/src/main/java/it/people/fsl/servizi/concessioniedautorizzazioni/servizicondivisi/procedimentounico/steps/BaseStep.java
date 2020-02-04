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

import it.gruppoinit.commons.DBCPManager;
import it.people.Activity;
import it.people.IValidationErrors;
import it.people.Step;
import it.people.core.ServiceProfileStore;
import it.people.core.ServiceProfileStoreManager;
import it.people.core.persistence.exception.peopleException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.ProcedimentoUnicoDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ComuneBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.EventiVitaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoUnicoException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.RiepilogoOneri;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ServiziBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.StepBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.support.Bean2XML;
import it.people.fsl.servizi.oggetticondivisi.Richiedente;
import it.people.fsl.servizi.oggetticondivisi.Titolare;
import it.people.layout.LayoutMenu;
import it.people.layout.NavigationBar;
import it.people.parser.FiscalCodeValidator;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.process.SurfDirection;
import it.people.process.view.ConcreteView;
import it.people.sirac.serviceprofile.xml.AccessoIntermediari;
import it.people.sirac.serviceprofile.xml.AccessoUtentePeopleRegistrato;
import it.people.sirac.serviceprofile.xml.AuthorizationProfile;
import it.people.sirac.serviceprofile.xml.PeopleServiceProfile;
import it.people.sirac.serviceprofile.xml.PeopleServiceProfileDocument;
import it.people.sirac.serviceprofile.xml.SecurityProfile;
import it.people.sirac.serviceprofile.xml.Service;
import it.people.util.ServiceParameters;
import it.people.wrappers.IRequestWrapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe base in cui sono presenti, tra gli altri, metodi per la gestione: <br/>
 * - Bookmark <br/>
 * - Gestione apertura pagine non di iter <br/>
 * - Gestione tasto back (saveHistory)
 *
 * @author InIT http://www.gruppoinit.it
 *
 * 16-giu-2006
 */
public class BaseStep extends Step {

    /**
     * logger
     */
    public final Log logger = LogFactory.getLog(this.getClass());

    /**
     * stringa che indentifica il nome dell'input button
     */
    private final String UPLOAD_BUTTON = "uploadFile";
    
    public final String RIEPILOGO_FIRMATO = "riepilogo.html.p7m";

    protected String resourceFile = "";

    protected String crlf = File.separator;

    protected String SEPARATOR = ";";

    protected DBCPManager db;

    private String basePath = "";

    private String htmlPath = "";
    
    protected String language = "it";

    protected HttpSession session;
    
    public BaseStep() {
    }

    protected boolean initialise(IRequestWrapper request) {
        session = request.getUnwrappedRequest().getSession();
        //String encoding = request.getUnwrappedRequest().getCharacterEncoding();
        //logger.debug("DEBUG request.getCharacterEncoding()"+encoding);
        
        if (session != null) {
            ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
            //debug parametri del servizio
            logger.debug("basePath="+params.get("basePath"));
            logger.debug("dbjndi="+params.get("dbjndi"));
            logger.info("absPathToService="+params.get("absPathToService"));
            
//            if (session.getAttribute("basePath") == null) {
                this.basePath = params.get("basePath");
                this.htmlPath = getBasePath().concat("view/default/html/");
                session.setAttribute("basePath", getBasePath());
//            } else {
//                this.basePath = params.get("basePath");
//                this.htmlPath = getBasePath().concat("view/default/html/");
//            }
            if (session.getAttribute("DB") == null) {
                String jndi = params.get("dbjndi");
                try {
                    this.db = new DBCPManager(jndi);
                
//                } catch (ClassNotFoundException e) {
//                    logger.error(e);
//                    return false;
//                
                } catch (Exception e) {
                    logger.error(e);
                    return false;
                }
                session.setAttribute("DB", db);
            } else {
                this.db = (DBCPManager) session.getAttribute("DB");
            }
            // Setto la lingua
            if(params.get("language") != null){
                language = params.get("language");
            }
            return true;
        } else {
            return false;
        }
    }

    public DBCPManager getDb() {
        return db;
    }

    public void setDb(DBCPManager db) {
        this.db = db;
    }

    /**
     * Dummy implementation of logicaValidate. Override in derived classes if
     * needed
     * 
     * @param process
     * @param request
     * @param errors
     * @return true
     * @throws it.people.parser.exception.ParserException
     */
    public boolean logicalValidate(AbstractPplProcess process, IRequestWrapper request, IValidationErrors errors) throws ParserException {
        return super.logicalValidate(process, request, errors);
    }

    public void setStep(AbstractPplProcess pplProcess, int index) {
        pplProcess.getView().getCurrentActivity().setCurrentStepIndex(index);
    }

    protected void setStep(AbstractPplProcess pplProcess, IRequestWrapper request, int activityIndex, int stepIndex) {
        pplProcess.getView().setCurrentActivityIndex(activityIndex, false);
        pplProcess.getView().getCurrentActivity().setCurrentStepIndex(stepIndex);
        try {
            pplProcess.getView().getCurrentActivity().getCurrentStep().service(pplProcess, request);
        } catch (IOException e) {
            logger.error(e);
            gestioneEccezioni(pplProcess, e);
        } catch (ServletException ex) {
            logger.error("", ex);
            gestioneEccezioni(pplProcess, ex);
        }

    }

    protected String getResFilePath(IRequestWrapper request) {
        ServiceParameters spar = (ServiceParameters) request.getUnwrappedRequest().getSession().getAttribute("serviceParameters");
        return spar.get("resPath");
    }

    public boolean validateCodiceFiscale(String codiceFiscale) {

        // Delego il controllo at it.people.parser.FiscalCodeValidator
        FiscalCodeValidator aFiscalCodeValidator = new FiscalCodeValidator();
        boolean result = true;
        try {
            result = aFiscalCodeValidator.parserValidate(codiceFiscale);
        } catch (ParserException pe) {
            return false;
        }
        return true;

    }

    public String initCap(final String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    protected void logStep(AbstractPplProcess process) {
        logger.debug("Step # " + getCurrentStepIndex(process));
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
            logger.error(e);
            return false;
        } catch (ServletException ex) {
            // 
            logger.error("", ex);
            return false;
        }
        return true;
    }

    protected void showJsp(AbstractPplProcess process, String path, boolean completePath) {

        process.getView().getCurrentActivity().getCurrentStep().setJspPath((completePath ? "" : htmlPath)
                                                                           + path);
        logger.debug("you are going here --> "
                     + process.getView().getCurrentActivity().getCurrentStep().getJspPath());
    }

    protected void showCurrentJsp(AbstractPplProcess process) {
        logger.debug("you are going here --> "
                     + process.getView().getCurrentActivity().getCurrentStep().getJspPath());
    }

    public String getActionPath(IRequestWrapper request) {
        String answer = request.getUnwrappedRequest().getRequestURI();
        if (answer.indexOf("/") >= 0) {
            answer = answer.substring(answer.lastIndexOf("/") + 1);
        }
        return answer;

    }

    /*
     * TODO mettere controllo pi� accurato
     *  
     */
    protected boolean saveHistory(AbstractPplProcess process, IRequestWrapper request) {
        return saveHistory(process, request, false);
    }
    
    protected boolean saveHistory(AbstractPplProcess process, IRequestWrapper request, boolean forceForwardAction) {
        if (process == null) {
            return true;
        }

        // Test per togliere il link dal menu di sx
        if(process.getView().getCurrentActivity().getId().equalsIgnoreCase("0")){
            if(process.getView().getCurrentActivity().getCurrentStep().getId().equalsIgnoreCase("10")){
                process.getView().getActivityById("1").setState(it.people.ActivityState.INACTIVE);
            }
            process.getView().getActivityById("3").setState(it.people.ActivityState.INACTIVE);
        }
        else if(process.getView().getCurrentActivity().getId().equalsIgnoreCase("1")){
            if(process.getView().getCurrentActivity().getCurrentStep().getId().equalsIgnoreCase("0")){
                process.getView().getActivityById("0").setState(it.people.ActivityState.ACTIVE);
                process.getView().getActivityById("3").setState(it.people.ActivityState.INACTIVE);
            }
            else if(process.getView().getCurrentActivity().getCurrentStep().getId().equalsIgnoreCase("1")){
                process.getView().getActivityById("0").setState(it.people.ActivityState.INACTIVE);
                //process.getView().getActivityById("3").setState(it.people.ActivityState.ACTIVE);
            }
        }
        else if(process.getView().getCurrentActivity().getId().equalsIgnoreCase("3")){
            if(process.getView().getCurrentActivity().getCurrentStep().getId().equalsIgnoreCase("0")){
                process.getView().getActivityById("1").setState(it.people.ActivityState.ACTIVE);                
            }
            else{
                process.getView().getActivityById("1").setState(it.people.ActivityState.INACTIVE);                
            }
            process.getView().getActivityById("0").setState(it.people.ActivityState.INACTIVE);
        }
        // Fine test
        
        boolean bloccaService = true;
        String xml = null;
        StepBean currentStep = new StepBean();
        //        ProcessData processData = (ProcessData)process.getData();

        if (session.getAttribute("history") == null) {
            List history = new LinkedList();
            session.setAttribute("history", history);
            // by Cedaf - INIZIO
            // Tolto per testare soluzione cedaf con prestazioni migliori
            //((ProcessData)process.getData()).setHistory((LinkedList)history);
            // by Cedaf - FINE
        }
        LinkedList list = (LinkedList) session.getAttribute("history");

        currentStep.setActivityIndex(getCurrentActivityIndex(process));
        currentStep.setStepIndex(getCurrentStepIndex(process));
        currentStep.setSubStepIndex(getMaxSubStep(currentStep.getActivityIndex(), currentStep.getStepIndex(), list) + 1);
        currentStep.setActivityId(getCurrentActivityId(process));
        currentStep.setStepId(getCurrentStepId(process));
        //by Cedaf - INIZIO
        currentStep.setOpsVec(((ProcessData)process.getData()).getOpsVec());
        currentStep.setOneriVec(((ProcessData)process.getData()).getOneriVec());
        //by Cedaf - FINE
        if (list == null) {
            return true;
        }
        
        // Salvo i check dell'utente
        logger.debug("Direction = "+surfDirection(process));
        if (getSurfDirection(process)==SurfDirection.forward  || getSurfDirection(process)==SurfDirection.other) {
            saveCheck(process, list, request);
        }
        //((ProcessData) process.getData()).setOneriVec(null);
        if (currentStep.getActivityId().equalsIgnoreCase("0")
            && currentStep.getStepId().equalsIgnoreCase("9")) {
            Set oneriAnt = null;
            if (((ProcessData) process.getData()).getOneriAnticipati() != null)
                oneriAnt = ((ProcessData) process.getData()).getOneriAnticipati();
            ((StepBean) list.getLast()).setOneriAnticipati(oneriAnt);
        }
        if (currentStep.getActivityId().equalsIgnoreCase("0")
            && currentStep.getStepId().equalsIgnoreCase("8")) {
            ((StepBean) list.getLast()).setOneriVec(((ProcessData) process.getData()).getOneriVec());
        }
        /*
         * Modifica Ferretti 09/03/2007 (versione 1.2.3): risolto bug che mandava in errore la signPrintPage nel caso 
         * l'utente premeva back sulla pagina di avviso firma multipla e poi tornava avanti
         */
        //if (currentStep.getActivityId().equalsIgnoreCase("1")
        //    && currentStep.getStepId().equalsIgnoreCase("1")) {
        if(!list.isEmpty()){
            ((StepBean) list.getLast()).setSezioniCompilabiliBean(((ProcessData) process.getData()).getSezioniCompilabiliBean());
            ((StepBean) list.getLast()).setDichiarazioniStatiche(((ProcessData) process.getData()).getDichiarazioniStatiche());
            ((StepBean) list.getLast()).setAnagrafica(((ProcessData) process.getData()).getAnagrafica());
            ((StepBean) list.getLast()).setDatiAssociazionecategoria(((ProcessData) process.getData()).getDatiAssociazioneDiCategoria());
        }
        //}

        if (getSurfDirection(process)==SurfDirection.forward || getSurfDirection(process)==SurfDirection.other || forceForwardAction) {

            ((ProcessData) process.getData()).setBookmarkActivityIndex(currentStep.getActivityIndex());
            ((ProcessData) process.getData()).setBookmarkStepIndex(currentStep.getStepIndex());
            
            //Da questo punto in poi, savehistory() deve comportarsi come
            //un normale servizio, incrementando il substep a partire da quello impostato qui
//            if ((currentStep.getActivityId().equalsIgnoreCase("0")
//                    && (currentStep.getStepId().equalsIgnoreCase("3") 
//                    || currentStep.getStepId().equalsIgnoreCase("0"))) 
//                    && ((ProcessData) process.getData()).isBookmark()) {
//                if(currentStep.getStepId().equalsIgnoreCase("3")){
//                    ((ProcessData) process.getData()).setBookmark(false);
//                }
//            } else {
//                ((ProcessData) process.getData()).setBookmarkSubStepIndex(currentStep.getSubStepIndex());
//            }
            
            ((ProcessData) process.getData()).setBookmarkActivityId(currentStep.getActivityId());
            ((ProcessData) process.getData()).setBookmarkStepId(currentStep.getStepId());
            xml = Bean2XML.marshallPplData(process.getData());
            
            /*if(currentStep.getActivityId().equalsIgnoreCase("1") && currentStep.getStepId().equalsIgnoreCase("1")){
                FileOutputStream fout;      
                try{
                    FileOutputStream fos = new FileOutputStream("c:/temp/xml.xml");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(process.getData());
                    oos.close();
                }
                catch (IOException e){}
            }*/
            
            //logger.debug(xml);
            currentStep.setData(xml);
            logger.debug("added Step(" + currentStep.getActivityId() + ","
                         + currentStep.getStepId() + ", substep:"
                         + currentStep.getSubStepIndex() + " )");
            list.add(currentStep);
            bloccaService = false;
            session.setAttribute("history", list);
            // by Cedaf - INIZIO
            // Tolto per testare soluzione cedaf con prestazioni migliori
            // ((ProcessData)process.getData()).setHistory(list);
            // by Cedaf - FINE
        } else {
            if (session.getAttribute("eliminatoElemento") == null) {
                //deve sempre esserci almeno un elemnto
                if (list != null && list.size() > 1) {
                    if (!((ProcessData) process.getData()).isLoopBack()
                        || (((ProcessData) process.getData()).isLoopBack() && process.getValidationErrors().isEmpty())) {
                        logger.debug("removing step("
                                     + ((StepBean) list.getLast()).getActivityIndex()
                                     + ", "
                                     + ((StepBean) list.getLast()).getStepIndex()
                                     + ", substep: "
                                     + ((StepBean) list.getLast()).getSubStepIndex()
                                     + ")");
                        list.removeLast();
                    }
                }
                StepBean newLast = (StepBean) list.getLast();
                // Cancellare
                /*if(currentStep.getActivityId().equalsIgnoreCase("0") && currentStep.getStepId().equalsIgnoreCase("10")){
                    System.out.println(newLast.getData());
                }*/
                // Fine da cancellare
                ProcessData data = (ProcessData) Bean2XML.unmarshall(ProcessData.class, newLast.getData());
                if(session.getAttribute("pdpd") != null && (data.getProcessDataPerDestinatario()==null || data.getProcessDataPerDestinatario().isEmpty())){
                    List pdpd = (List) session.getAttribute("pdpd");
                    data.setProcessDataPerDestinatario(pdpd);
                }
                if(session.getAttribute("titolare") != null && data.getTitolare()==null){
                    Titolare tit = (Titolare) session.getAttribute("titolare");
                    data.setTitolare(tit);
                }
                if(session.getAttribute("richiedente") != null && data.getRichiedente()==null){
                    Richiedente ric = (Richiedente) session.getAttribute("richiedente");
                    data.setRichiedente(ric);
                }
                if(session.getAttribute("riepilogoOneri") != null){
                    data.getDatiTemporanei().setRiepilogoOneri((RiepilogoOneri)session.getAttribute("riepilogoOneri"));
                }
                if(session.getAttribute("oneriPosticipati") != null){
                    data.setOneriPosticipati((Set)session.getAttribute("oneriPosticipati"));
                }
                process.setData(data);
                // Rendo disponibili i dati per il tasto back
                //by Cedaf - INIZIO
                ((ProcessData) process.getData()).getDatiTemporanei().setOpsVec(newLast.getOpsVec());
                ((ProcessData) process.getData()).setOpsVec(newLast.getOpsVec());
                ((ProcessData) process.getData()).getDatiTemporanei().setCodiceRamo(newLast.getCodiceRamo());
                ((ProcessData) process.getData()).setCodiceRamo(newLast.getCodiceRamo());
                //by Ceda - FINE
                ((ProcessData) process.getData()).setSezioniCompilabiliBean(newLast.getSezioniCompilabiliBean());
                ((ProcessData) process.getData()).setDichiarazioniStatiche(newLast.getDichiarazioniStatiche());
                if(newLast.getAnagrafica() != null){
                    ((ProcessData) process.getData()).setAnagrafica(newLast.getAnagrafica());
                }
                if(newLast.getDatiAssociazionecategoria() != null){
                    ((ProcessData) process.getData()).setDatiAssociazioneDiCategoria(newLast.getDatiAssociazionecategoria());
                }
                if (newLast.getOneriAnticipati() != null
                    && ((ProcessData) process.getData()).getOneriAnticipati() == null)
                    ((ProcessData) process.getData()).setOneriAnticipati(newLast.getOneriAnticipati());
                ((ProcessData) process.getData()).getDatiTemporanei().setOneriVec(newLast.getOneriVec());

                session.setAttribute("history", list);
                // by Cedaf - INIZIO
                // Tolto per testare soluzione cedaf con prestazioni migliori
                //((ProcessData)process.getData()).setHistory(list);
                // by Cedaf - FINE
                session.setAttribute("eliminatoElemento", new Boolean(true));
                setStep(process, request, newLast.getActivityIndex(), newLast.getStepIndex());
            } else {
                session.removeAttribute("eliminatoElemento");
                bloccaService = false;
            }
        }
        return bloccaService;
    }

    protected void saveCheck(AbstractPplProcess process, LinkedList list, boolean reset, IRequestWrapper request) {

        if (list != null && !list.isEmpty()) {
            logger.debug("inside saveCheck");
            StepBean sb = (StepBean) list.getLast();
            if (reset) {
                sb.setOpsVec(null);
                session.setAttribute("history", list);
            } else {
                ProcessData dataForm = (ProcessData) (process.getData());
                if(dataForm.getOpsVec() != null)
                    sb.setOpsVec(dataForm.getOpsVec());
                sb.setCodiceRamo(dataForm.getCodiceRamo());
                session.setAttribute("history", list);
            }
        }
    }

    protected void saveCheck(AbstractPplProcess process, LinkedList list, IRequestWrapper request) {
        saveCheck(process, list, false, request);
    }

    protected void removeLastHistory(IRequestWrapper request) {
        removeLastHistory(request, -1, -1);
    }
    
    protected void removeLastHistory(IRequestWrapper request, int activityIndex, int stepIndex) {
        LinkedList list = (LinkedList) session.getAttribute("history");
        if (list != null && !list.isEmpty()) {
            StepBean last = (StepBean) list.getLast();
            logger.debug("removing step(" + last.getActivityIndex() + ", " + last.getStepIndex() + ", substep: " + last.getSubStepIndex() + ")");
            if((activityIndex < 0 && stepIndex < 0) || (last.getActivityIndex() == activityIndex && last.getStepIndex() == stepIndex)){
                list.removeLast();
            }
        }
        session.setAttribute("history", list);
    }

    protected int getMaxSubStep(int activityIndex, int stepIndex, LinkedList list) {
        int retVal = -1;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            StepBean sb = (StepBean) it.next();
            if (sb.getActivityIndex() == activityIndex
                && sb.getStepIndex() == stepIndex) {
                if (sb.getSubStepIndex() > retVal)
                    retVal = sb.getSubStepIndex();
            }
        }
        return retVal;
    }

    public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException, ServletException {
        logger.debug("loopBack BaseStep.java");
        logger.debug("propertyName=" + propertyName);
        logStep(process);
    }

    private int getMaxCodServizi(ProcessData dataForm) {
        ArrayList servizi = (ArrayList) dataForm.getDatiTemporanei().getServizi();
        int retVal = 0;
        Iterator it = servizi.iterator();
        while (it.hasNext()) {
            ServiziBean servizioBean = (ServiziBean) it.next();
            if (servizioBean.getCodiceServizio() > retVal) {
                retVal = servizioBean.getCodiceServizio();
            }
        }
        return retVal;
    }

    protected int getCurrentStepIndex(AbstractPplProcess process) {
        return process.getView().getCurrentActivity().getCurrentStepIndex();
    }

    protected int getCurrentActivityIndex(AbstractPplProcess process) {
        return process.getView().getCurrentActivityIndex();
    }

    protected int getLastSubStepIndex() {
        if (session == null || session.getAttribute("history") == null) {
            return -1;
        }
        return ((StepBean) ((LinkedList) session.getAttribute("history")).getLast()).getSubStepIndex();
    }

    protected void enableBottomSaveBar(AbstractPplProcess process, boolean enable) {
        process.getView().setBottomSaveBarEnabled(enable);
    }

    protected void enableBottomNavigationBar(AbstractPplProcess process, boolean enable) {
        process.getView().setBottomNavigationBarEnabled(enable);
    }

    /**
     * @return Returns the basePath.
     */
    public String getBasePath() {
        return basePath;
    }

    /**
     * @param basePath
     *            The basePath to set.
     */
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    /**
     * @return Returns the htmlPath.
     */
    public String getHtmlPath() {
        return htmlPath;
    }

    /**
     * @param htmlPath
     *            The htmlPath to set.
     */
    public void setHtmlPath(String htmlPath) {
        this.htmlPath = htmlPath;
    }

    protected void gestioneBookmark(AbstractPplProcess process, IRequestWrapper request) throws IOException, ServletException, SQLException, ProcedimentoUnicoException {

        ProcessData dataForm = (ProcessData) process.getData();
        String action = request.getParameter("action");
        String operazione = request.getParameter("operazione");
        ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
        if (action != null && action.equalsIgnoreCase("servizi.jsp")) {
            enableBottomNavigationBar(process, false);
            //enableBottomSaveBar(process, false);
            String comune = ((ComuneBean) dataForm.getComune()).getCodEnte();
            String serviceCategory = "servizicondivisi";
            String serviceSubcategory = "procedimentounico";      
            String serviceName = "it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico";
            if (operazione != null) {
                if (operazione.equalsIgnoreCase("insert")) {
                    String nuovoServizio = request.getParameter("nuovoServizio");
                    String descrizioneServizio = request.getParameter("descrizioneServizio");
                    if (nuovoServizio == null || nuovoServizio.equals("")
                        || descrizioneServizio == null
                        || descrizioneServizio.equals("")) {
                        return;
                    } else {
                        String bookmarkPointer = getLastStepDataAsString(process, request);
                        // Effettuo l'insert nel db
                        int codServizio = delegate.insertServizio(comune, Integer.parseInt((String) dataForm.getDatiTemporanei().getEventoVitaSel()), bookmarkPointer, nuovoServizio, descrizioneServizio);
                        // Inserisco il sevice profile
                        try{
                            String serviceProfileXML = createServiceProfileXML( process, 
                                                                                serviceName, 
                                                                                serviceCategory, 
                                                                                serviceSubcategory, 
                                                                                request.getParameter("AAF").equalsIgnoreCase("si"), 
                                                                                request.getParameter("AAD").equalsIgnoreCase("si"), 
                                                                                request.getParameter("AI").equalsIgnoreCase("si"), 
                                                                                request.getParameter("AUR").equalsIgnoreCase("si"));
                            this.debug(process, serviceProfileXML);
                            saveServiceProfileXML(process, serviceProfileXML, ""+codServizio, serviceName);
                        }
                        catch (peopleException e) {
                            logger.error(e);
                        }
                        catch (Exception ex) {
                            logger.error("", ex);
                        }
                        if (dataForm.getDatiTemporanei().getEventoVitaSel() != null) {
                            dataForm.getDatiTemporanei().setServizi(delegate.getServiziPerEventiVita(Integer.parseInt((String) dataForm.getDatiTemporanei().getEventoVitaSel()), comune, serviceName));
//                            ArrayList servizi = (ArrayList) dataForm.getDatiTemporanei().getServizi();
                        }
                    }
                } else if (operazione.equalsIgnoreCase("update")) {
                    if (dataForm.getDatiTemporanei().getServizioSel() != null) {
                        String bookmarkPointer = getLastStepDataAsString(process, request);
                        // Effettuo l'insert nel db
                        String descrizioneServizio = request.getParameter("descrizioneServizio");
                        String nomeServizio = request.getParameter("nuovoServizio");
                        boolean modificaLight = request.getParameter("modificaLight")==null ? false : true;
                        String codServizio = (String) dataForm.getDatiTemporanei().getServizioSel();
                        delegate.updateServizio(Integer.parseInt(codServizio), comune, Integer.parseInt((String) dataForm.getDatiTemporanei().getEventoVitaSel()), bookmarkPointer, descrizioneServizio, nomeServizio, modificaLight);
                        ServiceProfileStoreManager spsManager = ServiceProfileStoreManager.getInstance();
                        //spsManager.delete(serviceName, codServizio);
                        try{
                            String serviceProfileXML = createServiceProfileXML(process, 
                                                                                serviceName, 
                                                                                serviceCategory, 
                                                                                serviceSubcategory, 
                                                                                request.getParameter("AAF").equalsIgnoreCase("si"), 
                                                                                request.getParameter("AAD").equalsIgnoreCase("si"), 
                                                                                request.getParameter("AI").equalsIgnoreCase("si"), 
                                                                                request.getParameter("AUR").equalsIgnoreCase("si"));
                            this.debug(process, serviceProfileXML);
                            saveServiceProfileXML(process, serviceProfileXML, ""+codServizio, serviceName);
                        }
                        catch (peopleException e) {
                            logger.error(e);
                        }
                        catch (Exception ex) {
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
                        /** TODO il problema � qui **/
                        spsManager.delete(serviceName, codServizio);
                        
                        if (dataForm.getDatiTemporanei().getEventoVitaSel() != null) {
                            dataForm.getDatiTemporanei().setServizi(delegate.getServiziPerEventiVita(Integer.parseInt((String) dataForm.getDatiTemporanei().getEventoVitaSel()), comune, serviceName));
//                            ArrayList servizi = (ArrayList) dataForm.getDatiTemporanei().getServizi();
                        }
                    }
                }
            } else {
                logger.debug(process.getView().getCurrentActivity().getCurrentStep().getJspPath());
                dataForm.getDatiTemporanei().setEventiVita(delegate.getEventiVita());
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
            // La prima volta memorizzo la jsp che mi ha chiamato
            if (dataForm.getDatiTemporanei().getJspChiamante() == null) {
                dataForm.getDatiTemporanei().setJspChiamante(getCurrentJspPath(process));
            }
            showJsp(process, action, false);
        } else if (action.equalsIgnoreCase("eventiVita.jsp")) {
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
        } else if (action.equalsIgnoreCase("chiamante.jsp")) {
            enableBottomNavigationBar(process, true);
            //enableBottomSaveBar(process, true);
            process.setData((ProcessData) Bean2XML.unmarshall(ProcessData.class, getLastStepDataAsString(process, request)));
            process.getView().getCurrentActivity().getCurrentStep().setJspPath(dataForm.getDatiTemporanei().getJspChiamante());
            setStep(process, request, getCurrentActivityIndex(process), getCurrentStepIndex(process));
        }
    }

    /**
     * Metodo unico per la gestione delle eccezioni. Tutte le eccezioni
     * dell'applicativo sono di tipo ProcedimentoUnicoException
     *  
     */
    protected void gestioneEccezioni(AbstractPplProcess process, Exception e) {
        process.getValidationErrors().add("error.generic", e.getMessage());
    }

    protected String getCurrentJspPath(AbstractPplProcess process) {
        return process.getView().getCurrentActivity().getCurrentStep().getJspPath();
    }

    private String getLastStepDataAsString(AbstractPplProcess process, IRequestWrapper request) throws ProcedimentoUnicoException {

        List list = (List) session.getAttribute("history");
        if (list == null) {
            throw new ProcedimentoUnicoException("oggetto history non trovato in sessione");
        }
        int size = list.size();
        StepBean step = (StepBean) list.get(size - 1);
        return step.getData();

    }

    /**
     * Inizializza il servizio quando si tratta di un bookmark. In particolare,
     * aggiorna il menu e la barra di navigazione mostrando solo gli step attivi
     * e disabilitando il pulsante "modulo precedente" nella prima pagina del
     * bookmark
     * 
     * @param process
     * @param request
     * @param idBookmark
     * @throws ProcedimentoUnicoException
     */
    protected void initBookmark(AbstractPplProcess process, IRequestWrapper request, String idBookmark) throws ProcedimentoUnicoException {

        ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
        String xml = delegate.getBookmarkXML(idBookmark);
        //nessun bookmark trovato per l'id passato
        //solleva un eccezione
        if (xml == null) {
            throw new ProcedimentoUnicoException("Nessun bookmark trovato per l'id "
                                                 + idBookmark);
        }
        //ripristina il processData al momento in cui � stato salvato
        //nel bookmark
        process.setData((ProcessData) Bean2XML.unmarshall(ProcessData.class, xml));
        String stepId = ((ProcessData) process.getData()).getBookmarkStepId();
        Activity[] activities = process.getView().getActivities();
        Activity activity = activities[0];
        Step step = null;
        while (activity.getStepList().size() > 1) {
            //salta lo step di informazioni sul servizio
            //che deve sempre essere presente
            step = (Step) activity.getStepList().get(1);
            if (step.getId().equalsIgnoreCase(stepId)) {
                break;
            }
            activity.getStepList().remove(1);
        }

        int stepIndex = ((ProcessData) process.getData()).getBookmarkStepIndex();
        String[] stepOrder = activity.getStepOrder();
        int lastStepId = Integer.parseInt(stepOrder[stepOrder.length - 1]);
        String newStepOrder = "";
        for (; stepIndex <= lastStepId; stepIndex++) {
            if (stepIndex == lastStepId) {
                newStepOrder += stepIndex;
            } else {
                newStepOrder += stepIndex + ",";
            }
        }

        activity.setStepOrder(newStepOrder);
        LayoutMenu menu = (LayoutMenu) session.getAttribute("menuObject");
        NavigationBar navBar = (NavigationBar) session.getAttribute("navBarObject");
        if (menu != null && navBar != null) {
            menu.update(process.getView());
            navBar.update(process.getView());
        }
        //by Cedaf - INIZIO
        if(idBookmark!=null)
            ((ProcessData)process.getData()).setIdBookmark(idBookmark);
        //by Cedaf - FINE
    }

    protected String getCurrentActivityId(AbstractPplProcess process) {
        return process.getView().getCurrentActivity().getId();
    }

    protected String getCurrentStepId(AbstractPplProcess process) {
        return process.getView().getCurrentActivity().getCurrentStep().getId();
    }

    /**
     * @return Returns the surfDirection.
     */
    public SurfDirection getSurfDirection(AbstractPplProcess process) {
        return getConcreteView(process).getSurfDirection();
    }
    
    private ConcreteView getConcreteView(AbstractPplProcess process){
        return process.getProcessWorkflow().getProcessView(); 
    }
    
    protected String surfDirection(AbstractPplProcess process){
        String forward ="forward";
        String backward ="backward";
        String other ="other";
        if(getConcreteView(process).getSurfDirection()==SurfDirection.forward){
            return forward;
        }else if(getConcreteView(process).getSurfDirection()==SurfDirection.backward){
            return backward;
        }else if(getConcreteView(process).getSurfDirection()==SurfDirection.other){
            return other;
        }
        return null;
    }
    
    /**
     * Salva la serializzazione in xml del service profile su DB
     * @param serviceProfileXML xml del service profile
     * @throws peopleException
     */
    protected void saveServiceProfileXML(AbstractPplProcess process, String serviceProfileXML, String bookmarkId, String serviceName)
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
        // il ServiceProfileStore � l'oggetto contenente la serializzazione
        // del service profile su DB.
        ServiceProfileStoreManager spsManager = ServiceProfileStoreManager.getInstance();
        
        // Crea un nuovo ServiceProfileStore, passandogli il nome del servizio
        // l'identificativo del bookmark e l'xml serializzazione del service profile
        ServiceProfileStore serviceProfileStore = spsManager.create(serviceName,
                                                                    bookmarkId,
                                                                    serviceProfileXML);
        
        if (serviceProfileStore != null) {
            // Salva il ServiceProfileStore
            try {
                ServiceProfileStoreManager.getInstance().set(serviceProfileStore);
            } catch(peopleException ex) {
                this.error(process, "Impossibile salvare il service profile");
                ex.printStackTrace();
            } catch(Exception e) {
                this.error(process, "Impossibile salvare il service profile");
                e.printStackTrace();
            }
        } else
            this.error(process, "Xml non valido");
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
        //      pu� essere dettagliata per ogni singola categoria 
        //      di intermediario, quella presentata in questo step
        //      � solo una traccia all'utilizzo di xmlbeans per la
        //      creazione di un service profile valido.
        
        AccessoIntermediari accessoIntermediari = authorizationProfile.addNewAccessoIntermediari();
        accessoIntermediari.setEnabled(abilitaIntermediari);
        accessoIntermediari.setAll(true);
        AccessoUtentePeopleRegistrato accessoUtentePeopleRegistrato = authorizationProfile.addNewAccessoUtentePeopleRegistrato();
        accessoUtentePeopleRegistrato.setEnabled(abilitaUtenteRegistrato);
        
        // mostra l'xml
        System.out.print(serviceProfileDoc.toString());
        
        if (!serviceProfileDoc.validate())
            throw new Exception("L'xml non � conforme allo schema.");
        return serviceProfileDoc.toString();
    }
    
    protected void resettaListaProcessData(List dataPerDest){
        Iterator it = dataPerDest.iterator();
        while(it.hasNext()){
            ProcessData procDatPerDest = (ProcessData)it.next();
            procDatPerDest.setDaFirmare(false);
        }
        if(dataPerDest.size() > 0){
            ProcessData primoProcessData = (ProcessData)dataPerDest.get(0);
            primoProcessData.setDaFirmare(true);            
        }
    }

    protected boolean setNextProcessData(List dataPerDest){
        Iterator it = dataPerDest.iterator();
        boolean prossimoDaFirmare = false;
        while(it.hasNext()){
            ProcessData procDatPerDest = (ProcessData)it.next();
            if(prossimoDaFirmare){
                procDatPerDest.setDaFirmare(true);
                prossimoDaFirmare = false;
                break;
            }
            else{
                if(procDatPerDest.isDaFirmare()){
                    procDatPerDest.setDaFirmare(false);
                    prossimoDaFirmare = true;
                }
            }
        }
        return !prossimoDaFirmare;
    }
}







