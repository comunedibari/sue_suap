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
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model;

import it.diviana.egov.b109.oggettiCondivisi.AllegatoFirmatoDigitalmente;
import it.diviana.egov.b109.oggettiCondivisi.AllegatononFirmato;
import it.diviana.egov.b109.oggettiCondivisi.Comune;
import it.diviana.egov.b109.oggettiCondivisi.ComuneEsteso;
import it.diviana.egov.b109.oggettiCondivisi.CredenzialiUtenteCertificate;
import it.diviana.egov.b109.oggettiCondivisi.DUG;
import it.diviana.egov.b109.oggettiCondivisi.IdentificatoreUnivoco;
import it.diviana.egov.b109.oggettiCondivisi.IdentificatorediProtocollo;
import it.diviana.egov.b109.oggettiCondivisi.IdentificatorediRichiesta;
import it.diviana.egov.b109.oggettiCondivisi.IndirizzoStrutturatoCompleto;
import it.diviana.egov.b109.oggettiCondivisi.Localita;
import it.diviana.egov.b109.oggettiCondivisi.PersonaFisica;
import it.diviana.egov.b109.oggettiCondivisi.RappresentanteLegale;
import it.diviana.egov.b109.oggettiCondivisi.Recapito;
import it.diviana.egov.b109.oggettiCondivisi.SceltaLuogoEsteso;
import it.diviana.egov.b109.oggettiCondivisi.SceltaResidenza;
import it.diviana.egov.b109.oggettiCondivisi.SceltaRiepilogoRichiesta;
import it.diviana.egov.b109.oggettiCondivisi.SceltaTitolare;
import it.diviana.egov.b109.oggettiCondivisi.Sesso;
import it.diviana.egov.b109.oggettiCondivisi.SigladiProvinciaEstesa;
import it.diviana.egov.b109.oggettiCondivisi.Toponimo;
import it.diviana.egov.b109.oggettiCondivisi.Recapito.Priorita.Enum;
import it.gruppoinit.b109.concessioniEAutorizzazioni.procedimentoUnico.DichiarazioneCompilabile;
import it.gruppoinit.b109.concessioniEAutorizzazioni.procedimentoUnico.DichiarazioneStatica;
import it.gruppoinit.b109.concessioniEAutorizzazioni.procedimentoUnico.Domanda;
import it.gruppoinit.b109.concessioniEAutorizzazioni.procedimentoUnico.Ente;
import it.gruppoinit.b109.concessioniEAutorizzazioni.procedimentoUnico.ProcedimentoSemplice;
import it.gruppoinit.b109.concessioniEAutorizzazioni.procedimentoUnico.Richiedente;
import it.gruppoinit.b109.concessioniEAutorizzazioni.procedimentoUnico.RichiestaDocument;
import it.gruppoinit.b109.concessioniEAutorizzazioni.procedimentoUnico.RichiestadiConcessioniEAutorizzazioni;
import it.gruppoinit.b109.concessioniEAutorizzazioni.procedimentoUnico.SettoreAttivita;
import it.gruppoinit.b109.concessioniEAutorizzazioni.procedimentoUnico.DichiarazioneCompilabile.Valori;
import it.gruppoinit.b109.concessioniEAutorizzazioni.procedimentoUnico.DichiarazioneCompilabile.Valori.Valore;
import it.gruppoinit.b109.concessioniEAutorizzazioni.procedimentoUnico.Domanda.Dichiarazioni;
import it.gruppoinit.b109.concessioniEAutorizzazioni.procedimentoUnico.Domanda.Procedimenti;
import it.gruppoinit.b109.concessioniEAutorizzazioni.procedimentoUnico.Ente.Sportello;
import it.gruppoinit.b109.concessioniEAutorizzazioni.procedimentoUnico.ProcedimentoSemplice.Interventi;
import it.gruppoinit.b109.concessioniEAutorizzazioni.procedimentoUnico.ProcedimentoSemplice.Interventi.Intervento;
import it.gruppoinit.commons.DBCPManager;
import it.people.core.PeopleContext;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.*;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.ProcedimentoUnicoDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.support.Bean2XML;
import it.people.fsl.servizi.oggetticondivisi.UtenteAutenticato;
import it.people.fsl.servizi.oggetticondivisi.Titolare;
import it.people.fsl.servizi.oggetticondivisi.IdentificatorePeople;
import it.people.process.AbstractPplProcess;
import it.people.process.SurfDirection;
import it.people.process.common.entity.Attachment;
import it.people.process.common.entity.SignedAttachment;
import it.people.process.common.entity.SignedSummaryAttachment;
import it.people.process.data.AbstractData;
import it.people.process.sign.entity.SignedInfo;
import it.people.sirac.accr.beans.AbstractProfile;
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.ProfiloPersonaFisica;
import it.people.sirac.accr.beans.ProfiloPersonaGiuridica;
import it.people.sirac.core.SiracHelper;
import it.people.util.XmlObjectWrapper;
import it.people.util.ServiceParameters;
import it.people.util.IdentificatoreUnivoco.CodiceSistema;
import it.people.util.payment.EsitoPagamento;
import it.people.vsl.PipelineData;
import it.people.vsl.PipelineDataImpl;
import it.people.wrappers.IRequestWrapper;
import it.people.wrappers.HttpServletRequestDelegateWrapper;
import it.people.Activity;
import it.people.Step;
import it.people.layout.LayoutMenu;
import it.people.layout.NavigationBar;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.DataFormatException;
import java.text.*;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;
//import org.apache.xerces.utils.Base64;
import org.apache.xmlbeans.XmlObject;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Classe contenente tutti i dati necessari al servizio
 * 
 * @author InIT http://www.gruppoinit.it
 * 
 * 16-giu-2006
 */
public class ProcessData extends AbstractData {

    private static final long serialVersionUID = 6837105721146168438L;

    private Log logger = LogFactory.getLog(this.getClass());

    private FormFile uploadFile;

    private String[] opsVec;

    private String codiceSettore;

    private String codiceRamo;

    private String descrizioneSettore; // PD

    private Set operazioniSelezionate; // PD di (operazioneBean)

    private Set operazioniIndividuate;

    private Set interventiSelezionati; // PD x destinatario (interventoBean)

    private Set allegatiSelezionati;

    private Set allegatiBuffer;

    private String codiceComune;

    private List opzioniSettoreAttivita;

    private Set interventiFacoltativi;

    private List procedimenti; //PD x destinatario (posso recuperare anche gli allegati e le normative
    //by Cedaf - INIZIO
    //private List history;
    private LinkedList history;
    //by Cedaf - FINE

    private AnagraficaBean anagrafica;

    // condizione per verificare in Step.logicalValidate() se lo step corrente
    // va ripetuto
    private boolean loopBack;

    private String puDataSource;

    private ComuneBean comune;

    private Set oneriAnticipati;
    
    private Set oneriPosticipati;

    private String[] oneriVec;

    private boolean modelloUnicoComplete;

    private List sezioniCompilabili;

    private List dichiarazioniStatiche; //PD x destinatario (DichiarazioniStaticheBean)

    private boolean checkBoxValueSelected;

    private boolean initAlberoOneri;

    private String[] valoriHref;

    private boolean pagamentoEffettuato;

    private EsitoPagamento esitoPagamento;

    /*
     * Hasmap contenente: Chiave = href Oggetto = oggetto di tipo
     * SezioneCompilabileBean
     */
    private Map sezioniCompilabiliBean;

    private DatiTemporaneiBean datiTemporanei;

    private int tipoProcedura; // autocertificabile, semplice o dia

    private boolean azzeraValori;

    private boolean daFirmare;

    // private int stepIndex;
    // private int activityIndex;

    // costanti tipo procedura
    public static final int SEMPLICE = 0;

    public static final int AUTOCERTIFICABILE = 1;

    public static final int DIA = 2;

    private List codiciProcedimenti;

    private int bookmarkActivityIndex;

    private int bookmarkStepIndex;

    private int bookmarkSubStepIndex;

    private String bookmarkActivityId;

    private String bookmarkStepId;

    private boolean bookmark;

    private String codSportello; // PD x dest

    private String descSportello; // PD x dest

    private List processDataPerDestinatario;

    private int totaleProcedimenti = 0;

    private Output output;

    private List signStepsList;

    private Accreditamento tipoAccreditamento;

    private String credenzialiBase64;

    private AbstractProfile profiloTitolareNew;

    private boolean visualizzaBollo;
    
    private boolean disabilitaFirma;
    
    private String descBookmark;
    
    private String identificatoreProcedimentoPraticaSpacchettata; // PD x dest
    
    private List erroreSuHref;
    
    private AssociazioneDiCategoriaBean datiAssociazioneDiCategoria;
    
    private List descrizioneCampiNonCompilati;
    
    private String verticalizzazioneComune;
    
    // le informazioni sulla firma del richiedente
    // e sul contenuto (il riepilogo) firmato
    private SignedInfo sInfo = null;

    // by Cedaf - INIZIO
    private String idBookmark;

    public String getIdBookmark(){
        return this.idBookmark;
    }

    public void setIdBookmark(String idBookmark){
        this.idBookmark=idBookmark;
    }

    private DBCPManager getDb(HttpServletRequest request){
        String basePath = null;
        String htmlPath = null;
        DBCPManager db = null;
        ServiceParameters params = (ServiceParameters) request.getSession().getAttribute("serviceParameters");
            //debug parametri del servizio
            logger.debug("basePath="+params.get("basePath"));
            logger.debug("dbjndi="+params.get("dbjndi"));
            logger.debug("absPathToService="+params.get("absPathToService"));

//            if (session.getAttribute("basePath") == null) {
                basePath = params.get("basePath");
                //htmlPath = basePath.concat("view/default/html/");
                request.getSession().setAttribute("basePath", basePath);
//            } else {
//                this.basePath = params.get("basePath");
//                this.htmlPath = getBasePath().concat("view/default/html/");
//            }
            if (request.getSession().getAttribute("DB") == null) {
                String jndi = params.get("dbjndi");
                try {
                    db = new DBCPManager(jndi);
//                } catch (ClassNotFoundException e) {
//                    logger.error(e);
                
                } catch (Exception e) {
                    logger.error(e);
                }
                request.getSession().setAttribute("DB", db);
            } else {
                db = (DBCPManager) request.getSession().getAttribute("DB");
            }

        return db;

    }

    private static String getLanguage(HttpServletRequest request){
        String language = "it";
        ServiceParameters params = (ServiceParameters) request.getSession().getAttribute("serviceParameters");
        // Setto la lingua
            if(params.get("language") != null){
                language = params.get("language");
            }

        return language;
    }

     private static String getCurrentJspPath(AbstractPplProcess process) {
        return process.getView().getCurrentActivity().getCurrentStep().getJspPath();
    }

    private static String getHtmlPath(HttpServletRequest request){
        ServiceParameters params = (ServiceParameters) request.getSession().getAttribute("serviceParameters");
        String basePath = params.get("basePath");
        String htmlPath = basePath.concat("view/default/html/");

        return htmlPath;
    }

    private void showJsp(AbstractPplProcess process, String path, boolean completePath, HttpServletRequest request) {

        process.getView().getCurrentActivity().getCurrentStep().setJspPath((completePath ? "" : getHtmlPath(request))
                                                                           + path);
        logger.debug("you are going here --> "
                     + process.getView().getCurrentActivity().getCurrentStep().getJspPath());
    }

    private static int getMaxSubStep(int activityIndex, int stepIndex, LinkedList list) {
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

    private void saveCheck(AbstractPplProcess process, LinkedList list, boolean reset, HttpServletRequest request) {

        if (list != null && !list.isEmpty()) {
            logger.debug("inside saveCheck");
            StepBean sb = (StepBean) list.getLast();
            if (reset) {
                sb.setOpsVec(null);
                request.getSession().setAttribute("history", list);
            } else {
                ProcessData dataForm = (ProcessData) (process.getData());
                if(dataForm.getOpsVec() != null)
                    sb.setOpsVec(dataForm.getOpsVec());
                sb.setCodiceRamo(dataForm.getCodiceRamo());
                request.getSession().setAttribute("history", list);
            }
        }
    }

    protected static void gestioneEccezioni(AbstractPplProcess process, Exception e) {
        process.getValidationErrors().add("error.generic", e.getMessage());
    }

     private void setStep(AbstractPplProcess pplProcess, HttpServletRequest request, int activityIndex, int stepIndex) {
        pplProcess.getView().setCurrentActivityIndex(activityIndex, false);
        pplProcess.getView().getCurrentActivity().setCurrentStepIndex(stepIndex);
        try {
            IRequestWrapper requestWrapper = new HttpServletRequestDelegateWrapper(request);
            pplProcess.getView().getCurrentActivity().getCurrentStep().service(pplProcess, requestWrapper);
        } catch (IOException e) {
            logger.error(e);
            gestioneEccezioni(pplProcess, e);
        } catch (ServletException ex) {
            logger.error("", ex);
            gestioneEccezioni(pplProcess, ex);
        }

    }


    private boolean saveHistory(AbstractPplProcess process, HttpServletRequest request, boolean forceForwardAction) {
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
        //StepBean currentStep = new StepBean();
        //        ProcessData processData = (ProcessData)process.getData();

        /*
        if (request.getSession().getAttribute("history") == null) {
            List history = new LinkedList();
            request.getSession().setAttribute("history", history);
        } 
        LinkedList list = (LinkedList) request.getSession().getAttribute("history");
        */

        request.getSession().setAttribute("history",((ProcessData)process.getData()).getHistory());
        LinkedList list = (LinkedList) request.getSession().getAttribute("history");
        StepBean currentStep = (StepBean)list.getLast();
        

        currentStep.setActivityIndex(process.getView().getCurrentActivityIndex());
        currentStep.setStepIndex(process.getView().getCurrentActivity().getCurrentStepIndex());
        currentStep.setSubStepIndex(getMaxSubStep(currentStep.getActivityIndex(), currentStep.getStepIndex(), list) + 1);
        currentStep.setActivityId(process.getView().getCurrentActivity().getId());
        currentStep.setStepId(process.getView().getCurrentActivity().getCurrentStep().getId());
        if (list == null) {
            return true;
        }

        // Salvo i check dell'utente
        logger.debug("Direction = "+process.getView().getSurfDirection());
        if (process.getView().getSurfDirection()==SurfDirection.forward  || process.getView().getSurfDirection()==SurfDirection.other) {
            saveCheck(process, list, false, request);
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

        if (process.getView().getSurfDirection()==SurfDirection.forward || process.getView().getSurfDirection()==SurfDirection.other || forceForwardAction) {

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

            /*
            currentStep.setData(xml);
            logger.debug("added Step(" + currentStep.getActivityId() + ","
                         + currentStep.getStepId() + ", substep:"
                         + currentStep.getSubStepIndex() + " )");
            list.add(currentStep);
            */

            ProcessData data = (ProcessData) Bean2XML.unmarshall(ProcessData.class, currentStep.getData());







            if(request.getSession().getAttribute("pdpd") != null && (data.getProcessDataPerDestinatario()==null || data.getProcessDataPerDestinatario().isEmpty())){
                    List pdpd = (List) request.getSession().getAttribute("pdpd");
                    data.setProcessDataPerDestinatario(pdpd);
                }
                if(request.getSession().getAttribute("titolare") != null && data.getTitolare()==null){
                    Titolare tit = (Titolare) request.getSession().getAttribute("titolare");
                    data.setTitolare(tit);
                }
                if(request.getSession().getAttribute("richiedente") != null && data.getRichiedente()==null){
                    it.people.fsl.servizi.oggetticondivisi.Richiedente ric = (it.people.fsl.servizi.oggetticondivisi.Richiedente) request.getSession().getAttribute("richiedente");
                    data.setRichiedente(ric);
                }
                if(request.getSession().getAttribute("riepilogoOneri") != null){
                    data.getDatiTemporanei().setRiepilogoOneri((RiepilogoOneri)request.getSession().getAttribute("riepilogoOneri"));
                }
                if(request.getSession().getAttribute("oneriPosticipati") != null){
                    data.setOneriPosticipati((Set)request.getSession().getAttribute("oneriPosticipati"));
                }
                process.setData(data);
                // Rendo disponibili i dati per il tasto back
                ((ProcessData) process.getData()).getDatiTemporanei().setOpsVec(currentStep.getOpsVec());
                ((ProcessData) process.getData()).getDatiTemporanei().setCodiceRamo(currentStep.getCodiceRamo());
                ((ProcessData) process.getData()).setSezioniCompilabiliBean(currentStep.getSezioniCompilabiliBean());
                ((ProcessData) process.getData()).setDichiarazioniStatiche(currentStep.getDichiarazioniStatiche());
                if(currentStep.getAnagrafica() != null){
                    ((ProcessData) process.getData()).setAnagrafica(currentStep.getAnagrafica());
                }
                if(currentStep.getDatiAssociazionecategoria() != null){
                    ((ProcessData) process.getData()).setDatiAssociazioneDiCategoria(currentStep.getDatiAssociazionecategoria());
                }
                if (currentStep.getOneriAnticipati() != null
                    && ((ProcessData) process.getData()).getOneriAnticipati() != null)
                    ((ProcessData) process.getData()).setOneriAnticipati(currentStep.getOneriAnticipati());
                ((ProcessData) process.getData()).getDatiTemporanei().setOneriVec(currentStep.getOneriVec());

                request.getSession().setAttribute("history", list);
                //request.getSession().setAttribute("eliminatoElemento", new Boolean(true));
                setStep(process, request, currentStep.getActivityIndex(), currentStep.getStepIndex());












            bloccaService = false;
            request.getSession().setAttribute("history", list);
        } else {
            if (request.getSession().getAttribute("eliminatoElemento") == null) {
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
                if(request.getSession().getAttribute("pdpd") != null && (data.getProcessDataPerDestinatario()==null || data.getProcessDataPerDestinatario().isEmpty())){
                    List pdpd = (List) request.getSession().getAttribute("pdpd");
                    data.setProcessDataPerDestinatario(pdpd);
                }
                if(request.getSession().getAttribute("titolare") != null && data.getTitolare()==null){
                    Titolare tit = (Titolare) request.getSession().getAttribute("titolare");
                    data.setTitolare(tit);
                }
                if(request.getSession().getAttribute("richiedente") != null && data.getRichiedente()==null){
                    it.people.fsl.servizi.oggetticondivisi.Richiedente ric = (it.people.fsl.servizi.oggetticondivisi.Richiedente) request.getSession().getAttribute("richiedente");
                    data.setRichiedente(ric);
                }
                if(request.getSession().getAttribute("riepilogoOneri") != null){
                    data.getDatiTemporanei().setRiepilogoOneri((RiepilogoOneri)request.getSession().getAttribute("riepilogoOneri"));
                }
                if(request.getSession().getAttribute("oneriPosticipati") != null){
                    data.setOneriPosticipati((Set)request.getSession().getAttribute("oneriPosticipati"));
                }
                process.setData(data);
                // Rendo disponibili i dati per il tasto back
                ((ProcessData) process.getData()).getDatiTemporanei().setOpsVec(newLast.getOpsVec());
                ((ProcessData) process.getData()).getDatiTemporanei().setCodiceRamo(newLast.getCodiceRamo());
                ((ProcessData) process.getData()).setSezioniCompilabiliBean(newLast.getSezioniCompilabiliBean());
                ((ProcessData) process.getData()).setDichiarazioniStatiche(newLast.getDichiarazioniStatiche());
                if(newLast.getAnagrafica() != null){
                    ((ProcessData) process.getData()).setAnagrafica(newLast.getAnagrafica());
                }
                if(newLast.getDatiAssociazionecategoria() != null){
                    ((ProcessData) process.getData()).setDatiAssociazioneDiCategoria(newLast.getDatiAssociazionecategoria());
                }
                if (newLast.getOneriAnticipati() != null
                    && ((ProcessData) process.getData()).getOneriAnticipati() != null)
                    ((ProcessData) process.getData()).setOneriAnticipati(newLast.getOneriAnticipati());
                ((ProcessData) process.getData()).getDatiTemporanei().setOneriVec(newLast.getOneriVec());

                request.getSession().setAttribute("history", list);
                request.getSession().setAttribute("eliminatoElemento", new Boolean(true));
                setStep(process, request, newLast.getActivityIndex(), newLast.getStepIndex());
            } else {
                request.getSession().removeAttribute("eliminatoElemento");
                bloccaService = false;
            }
        }
        return bloccaService;
    }

    private static String getIdBookmark(HttpServletRequest request, AbstractPplProcess process){
        String idBookmark = null;
        if(request != null)
            idBookmark=request.getParameter("idBookmark");
        if(idBookmark == null && process != null && process.getData() != null)
            idBookmark = ((ProcessData)process.getData()).getIdBookmark();

        return idBookmark;
    }


    private static ProcessData syncProcessData(ProcessData pDataRestored, ProcessData pDataBookmark){
        /*pDataBookmark.setAllegati(pDataRestored.getAllegati());
        pDataBookmark.setAllegatiBuffer(pDataRestored.getAllegatiBuffer());
        pDataBookmark.setAllegatiSelezionati(pDataRestored.getAllegatiSelezionati());
        pDataBookmark.setAnagrafica(pDataRestored.getAnagrafica());
        pDataBookmark.setAzzeraValori(pDataRestored.isAzzeraValori());
        pDataBookmark.setBookmark(pDataRestored.isBookmark());
        pDataBookmark.setBookmarkActivityId(pDataRestored.getBookmarkActivityId());
        pDataBookmark.setBookmarkActivityIndex(pDataRestored.getBookmarkActivityIndex());
        pDataBookmark.setBookmarkStepId(pDataRestored.getBookmarkStepId());
        pDataBookmark.setBookmarkStepIndex(pDataRestored.getBookmarkStepIndex());
        pDataBookmark.setBookmarkSubStepIndex(pDataRestored.getBookmarkSubStepIndex());
        pDataBookmark.setCheckBoxValueSelected(pDataRestored.isCheckBoxValueSelected());
        pDataBookmark.setCodiceComune(pDataRestored.getCodiceComune());
        pDataBookmark.setCodiceRamo(pDataRestored.getCodiceRamo());
        pDataBookmark.setCodiceSettore(pDataRestored.getCodiceSettore());
        pDataBookmark.setCodiciProcedimenti(pDataRestored.getCodiciProcedimenti());
        pDataBookmark.setCodSportello(pDataRestored.getCodSportello());
        pDataBookmark.setComune(pDataRestored.getComune());
        pDataBookmark.setCredenzialiBase64(pDataRestored.getCredenzialiBase64());
        pDataBookmark.setDaFirmare(pDataRestored.isDaFirmare());
        pDataBookmark.setDatiAssociazioneDiCategoria(pDataRestored.getDatiAssociazioneDiCategoria());
        pDataBookmark.setDatiTemporanei(pDataRestored.getDatiTemporanei());
        pDataBookmark.setDescBookmark(pDataRestored.getDescBookmark());
        pDataBookmark.setDescrizioneSettore(pDataRestored.getDescrizioneSettore());
        pDataBookmark.setDescSportello(pDataRestored.getDescSportello());
        pDataBookmark.setDichiarazioniStatiche(pDataRestored.getDichiarazioniStatiche());
        pDataBookmark.setDisabilitaFirma(pDataRestored.isDisabilitaFirma());
        pDataBookmark.setErroreSuHref((ArrayList)pDataRestored.getErroreSuHref());
        pDataBookmark.setErrorMessage(pDataRestored.getErrorMessage());
        pDataBookmark.setEsitoPagamento(pDataRestored.getEsitoPagamento());
        pDataBookmark.setHasError(pDataRestored.hasError);
        pDataBookmark.setHistory(pDataRestored.getHistory());
        pDataBookmark.setIdBookmark(pDataRestored.getIdBookmark());
        pDataBookmark.setIdentificatorediProtocollo(pDataRestored.getIdentificatorediProtocollo());
        pDataBookmark.setIdentificatorePeople(pDataRestored.getIdentificatorePeople());
        pDataBookmark.setIdentificatoreProcedimentoPraticaSpacchettata(pDataRestored.getIdentificatoreProcedimentoPraticaSpacchettata());
        pDataBookmark.setIdentificatoreUnivoco(pDataRestored.getIdentificatoreUnivoco());
        pDataBookmark.setInitAlberoOneri(pDataRestored.isInitAlberoOneri());
        pDataBookmark.setInterventiFacoltativi(pDataRestored.getInterventiFacoltativi());
        pDataBookmark.setInterventiSelezionati(pDataRestored.getInterventiSelezionati());
        pDataBookmark.setLastActivityIdx(pDataRestored.getLastActivityIdx());
        pDataBookmark.setLastStepIdx(pDataRestored.getLastStepIdx());
        pDataBookmark.setLoopBack(pDataRestored.isLoopBack());
        pDataBookmark.setModelloUnicoComplete(pDataRestored.isModelloUnicoComplete());
        pDataBookmark.setOneriAnticipati(pDataRestored.getOneriAnticipati());
        pDataBookmark.setOneriPosticipati(pDataRestored.getOneriPosticipati());
        pDataBookmark.setOneriVec(pDataRestored.getOneriVec());
        pDataBookmark.setOperazioniIndividuate(pDataRestored.getOperazioniIndividuate());
        pDataBookmark.setOperazioniSelezionate(pDataRestored.getOperazioniSelezionate());
        pDataBookmark.setOpsVec(pDataRestored.getOpsVec());
        pDataBookmark.setOpzioniSettoreAttivita(pDataRestored.getOpzioniSettoreAttivita());
        pDataBookmark.setOutput(pDataRestored.getOutput());
        pDataBookmark.setPagamentoEffettuato(pDataRestored.isPagamentoEffettuato());
        pDataBookmark.setPersistentActivities(pDataRestored.getPersistentActivities());
        pDataBookmark.setPresentiAllegati(pDataRestored.isPresentiAllegati());
        pDataBookmark.setProcedimenti(pDataRestored.getProcedimenti());
        pDataBookmark.setProcessDataPerDestinatario(pDataRestored.getProcessDataPerDestinatario());
        pDataBookmark.setProfiloTitolare(pDataRestored.getProfiloTitolare());
        pDataBookmark.setPuDataSource(pDataRestored.getPuDataSource());
        pDataBookmark.setReceiptMailAttachment(pDataRestored.isReceiptMailAttachment());
        pDataBookmark.setRichiedente(pDataRestored.getRichiedente());
        pDataBookmark.setSezioniCompilabili(pDataRestored.getSezioniCompilabili());
        pDataBookmark.setSezioniCompilabiliBean(pDataRestored.getSezioniCompilabiliBean());
        pDataBookmark.setSignedInfo(pDataRestored.getSignedInfo());
        pDataBookmark.setSignEnabled(pDataRestored.isSignEnabled());
        pDataBookmark.setSignStepsList(pDataRestored.getSignStepsList());
        pDataBookmark.setSummaryState(pDataRestored.getSummaryState());
        pDataBookmark.setTipoAccreditamento(pDataRestored.getTipoAccreditamento());
        pDataBookmark.setTipoProcedura(pDataRestored.getTipoProcedura());
        pDataBookmark.setTitolare(pDataRestored.getTitolare());
        pDataBookmark.setTotaleProcedimenti(pDataRestored.getTotaleProcedimenti());
        pDataBookmark.setUploadFile(pDataRestored.getUploadFile());
        pDataBookmark.setValoriHref(pDataRestored.getValoriHref());
        pDataBookmark.setVisualizzaBollo(pDataRestored.isVisualizzaBollo());
        */

        //todo by Cedaf: da capire perch? sono necessarie le righe di codice seguenti.....

        pDataRestored.setBookmarkActivityId(pDataBookmark.getBookmarkActivityId());
        pDataRestored.setBookmarkActivityIndex(pDataBookmark.getBookmarkActivityIndex());
        pDataRestored.setBookmarkStepId(pDataBookmark.getBookmarkStepId());
        pDataRestored.setBookmarkStepIndex(pDataBookmark.getBookmarkStepIndex());

        return pDataRestored;
    }


    protected void initBookmark(AbstractPplProcess process, HttpServletRequest request, String idBookmark) throws ProcedimentoUnicoException {

        ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(getDb(request), getLanguage(request));
        String xml = delegate.getBookmarkXML(idBookmark);
        //nessun bookmark trovato per l'id passato
        //solleva un eccezione
        if (xml == null) {
            throw new ProcedimentoUnicoException("Nessun bookmark trovato per l'id "
                                                 + idBookmark);
        }
        //ripristina il processData al momento in cui ? stato salvato
        //nel bookmark
        ProcessData pDataBookmark = (ProcessData) Bean2XML.unmarshall(ProcessData.class, xml);
        ProcessData pDataRestored = (ProcessData)process.getData();
        //request.getSession().setAttribute("history",pDataRestored.getHistory());
        
        process.setData(syncProcessData(pDataRestored,pDataBookmark));
        //process.setData((ProcessData) Bean2XML.unmarshall(ProcessData.class, xml));
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
        LayoutMenu menu = (LayoutMenu) request.getSession().getAttribute("menuObject");
        NavigationBar navBar = (NavigationBar) request.getSession().getAttribute("navBarObject");
        if (menu != null && navBar != null) {
            menu.update(process.getView());
            navBar.update(process.getView());
        }

        
    }

    /*
    private void buildHistory(AbstractPplProcess process, HttpServletRequest request, Activity activity, String bookmarkStepId){

                    List history = new LinkedList();
                    request.getSession().setAttribute("history", history);


            for(int i=0; i<activity.getStepList().size() && activity.getStep(i).getId()!=bookmarkStepId;i++){



                        // Test Mauro - INIZIO

                        StepBean currentStep = new StepBean();

                        if (request.getSession().getAttribute("history") == null) {
                            request.getSession().setAttribute("history", history);
                        }
                        LinkedList list = (LinkedList) request.getSession().getAttribute("history");

                        //currentStep.setActivityIndex(process.getView().getCurrentActivityIndex());
                        currentStep.setActivityIndex(0);
                        //currentStep.setStepIndex(process.getView().getCurrentActivity().getCurrentStepIndex());
                        currentStep.setStepIndex(i);
                        currentStep.setSubStepIndex(getMaxSubStep(currentStep.getActivityIndex(), currentStep.getStepIndex(), list) + 1);
                        //currentStep.setActivityId(process.getView().getCurrentActivity().getId());
                        currentStep.setActivityId(process.getView().getActivities()[0].getId());
                        //currentStep.setStepId(process.getView().getCurrentActivity().getCurrentStep().getId());
                        currentStep.setStepId(process.getView().getActivities()[0].getStep(i).getId());
                        if (list == null) {
                            return;
                        }

                        // Salvo i check dell'utente
                        logger.debug("Direction = "+process.getView().getSurfDirection());
                        if (process.getView().getSurfDirection()==SurfDirection.forward  || process.getView().getSurfDirection()==SurfDirection.other) {
                            saveCheck(process, list, false, request);
                        }

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

                        if(!list.isEmpty()){
                            ((StepBean) list.getLast()).setSezioniCompilabiliBean(((ProcessData) process.getData()).getSezioniCompilabiliBean());
                            ((StepBean) list.getLast()).setDichiarazioniStatiche(((ProcessData) process.getData()).getDichiarazioniStatiche());
                            ((StepBean) list.getLast()).setAnagrafica(((ProcessData) process.getData()).getAnagrafica());
                            ((StepBean) list.getLast()).setDatiAssociazionecategoria(((ProcessData) process.getData()).getDatiAssociazioneDiCategoria());
                        }
                        //}

                        if (process.getView().getSurfDirection()==SurfDirection.forward || process.getView().getSurfDirection()==SurfDirection.other) {

                            ((ProcessData) process.getData()).setBookmarkActivityIndex(currentStep.getActivityIndex());
                            ((ProcessData) process.getData()).setBookmarkStepIndex(currentStep.getStepIndex());



                            ((ProcessData) process.getData()).setBookmarkActivityId(currentStep.getActivityId());
                            ((ProcessData) process.getData()).setBookmarkStepId(currentStep.getStepId());
                            String xml = Bean2XML.marshallPplData(process.getData());



                            //logger.debug(xml);
                            currentStep.setData(xml);
                            logger.debug("added Step(" + currentStep.getActivityId() + ","
                                         + currentStep.getStepId() + ", substep:"
                                         + currentStep.getSubStepIndex() + " )");
                            list.add(currentStep);
                            request.getSession().setAttribute("history", list);
                            }

                        // Test Mauro - FINE

                    //}
                }
            //}
    }
    */

    public void onLoad(AbstractPplProcess process, HttpServletRequest request){

        // se l'istanza del servizio precedente era un bookmark
            // rimuovi dalla sessione i seguenti attributi
            if (!((ProcessData) process.getData()).isBookmark()) {
                request.getSession().removeAttribute("nomeServizio");
                request.getSession().removeAttribute("descrizioneServizio");
            }
            ServletContext servletContext = request.getSession().getServletContext();
            logger.debug("servletContext=" + servletContext);
            ServiceParameters params = (ServiceParameters) request.getSession().getAttribute("serviceParameters");

            // l'istanza del procedimento ? partita da un bookmark?
            //String idBookmark = request.getUnwrappedRequest().getParameter("idBookmark");
            String idBookmark = getIdBookmark(request,process);
            String jndi = null;

            try {
                if (request.getSession().getAttribute("DB") == null) {
                    jndi = params.get("dbjndi");
                    ((ProcessData) process.getData()).setPuDataSource(jndi);
                    DBCPManager db = new DBCPManager(jndi);
                    logger.debug("DBCPManager=" + db);
                    logger.debug(db.getDriver());
                    logger.debug(db.getResourceName());
                    request.getSession().setAttribute("DB", db);
                }

                // l'istanza ? stata generata da un bookmark
                if (idBookmark != null) {
                    //salvo il titolare, richiedente e altri dati specifici di *questa* istanza del bookmark
                    //che altrimenti verrebbero sovrascritti dal ProcessData creato al momento del salvataggio
                    //del bookmark stesso. In caso contrario, verrebbero mostrati i dati dell'utente che ha creato
                    //il bookmark.
                    Titolare titolareTemp = ((ProcessData) process.getData()).getTitolare();
                    it.people.fsl.servizi.oggetticondivisi.Richiedente richiedenteTemp = ((ProcessData) process.getData()).getRichiedente();
                    it.people.fsl.servizi.oggetticondivisi.IdentificatorediProtocollo idProtTemp = ((ProcessData) process.getData()).getIdentificatorediProtocollo();
                    IdentificatorePeople idPeopleTemp = ((ProcessData) process.getData()).getIdentificatorePeople();
                    it.people.util.IdentificatoreUnivoco idUnivocoTemp = ((ProcessData) process.getData()).getIdentificatoreUnivoco();
                    initBookmark(process, request, idBookmark);
                    ((ProcessData) process.getData()).setBookmark(true);
                    //reinserisco nel ProcessData i dati corretti relativi a questa istanza del bookmark
                    ((ProcessData) process.getData()).setTitolare(titolareTemp);
                    ((ProcessData) process.getData()).setRichiedente(richiedenteTemp);
                    ((ProcessData) process.getData()).setIdentificatorediProtocollo(idProtTemp);
                    ((ProcessData) process.getData()).setIdentificatorePeople(idPeopleTemp);
                    ((ProcessData) process.getData()).setIdentificatoreUnivoco(idUnivocoTemp);
                    ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(getDb(request), getLanguage(request));
                    String nomeServizio = delegate.getNomeServizio(idBookmark);
                    String descrizioneServizio = delegate.getDescrizioneServizio(idBookmark);
                    request.getSession().setAttribute("nomeServizio", nomeServizio.toUpperCase());
                    request.getSession().setAttribute("descrizioneServizio", descrizioneServizio);
                    request.getSession().setAttribute("idbookmark", idBookmark);

                    if(nomeServizio != null){
                        ((ProcessData) process.getData()).setDescBookmark(nomeServizio);
                    }

                    // va sempre mostrata la pagina delll'attivit? 0, step 0
                    showJsp(process, getCurrentJspPath(process), true,request);
                    //process.getView().setCurrentActivityIndex(0);
                    //process.getView().getActivities()[0].setCurrentStepIndex(1);

                }
                // recupero dalla sessione i dati del richiedente
                // accreditato
                Accreditamento accreditamento = (Accreditamento) request.getSession().getAttribute(it.people.sirac.core.SiracConstants.SIRAC_ACCR_ACCRSEL);
                ((ProcessData) process.getData()).setTipoAccreditamento(accreditamento);

                String credenzialiBase64 = SiracHelper.getCredenzialiCertificateUtenteAutenticatoBase64(request);

                /* Risolvo il problema con le credenziali firmate digitalmente:
                 * le tolgo dal ProcessData (per evitare di serilizzarlo e deserializzarlo ad ogni step)
                 * e lo metto in sessione in modo da recuperarlo prima dell'invio finale
                 */
                ((ProcessData) process.getData()).setCredenzialiBase64(credenzialiBase64);
//                ((ProcessData) process.getData()).setCredenzialiBase64(null);
                if(((ProcessData) process.getData()).getTipoAccreditamento().getProfilo() != null){
                    ((ProcessData) process.getData()).getTipoAccreditamento().setProfilo(null);
                }
//                session.setAttribute("credenzialiBase64", credenzialiBase64);

                // recupero il profilo del titolare della domanda
                AbstractProfile profiloTitolare = (AbstractProfile) request.getSession().getAttribute(it.people.sirac.core.SiracConstants.SIRAC_ACCR_TITOLARE);
                ((ProcessData) process.getData()).setProfiloTitolareNew(profiloTitolare);

//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//                logger.error(e);
//                //gestioneEccezioni(process, e);
           
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e);
                //gestioneEccezioni(process, e);
            }
            saveHistory(process, request, false);
        //request.getSession().setAttribute("history",((ProcessData)process.getData()).getHistory());

    }

    //by Cedaf - FINE



    /**
     * @return Returns the output.
     */
    public Output getOutput() {
        return output;
    }

    /**
     * @param output
     *            The output to set.
     */
    public void setOutput(Output output) {
        this.output = output;
    }

    /**
     * @return Returns the processDataPerDestinatario.
     */
    public List getProcessDataPerDestinatario() {
        return processDataPerDestinatario;
    }

    /**
     * @param processDataPerDestinatario
     *            The processDataPerDestinatario to set.
     */
    public void setProcessDataPerDestinatario(List processDataPerDestinatario) {
        this.processDataPerDestinatario = processDataPerDestinatario;
    }

    /**
     * @return Returns the puDataSource.
     */
    public String getPuDataSource() {
        return puDataSource;
    }

    /**
     * @param puDataSource
     *            The puDataSource to set.
     */
    public void setPuDataSource(String puDataSource) {
        this.puDataSource = puDataSource;
    }

    public ProcessData() {
        super();
        initialize();
    }

    private void initialize() {
        logger.debug("inside ProcessData.initialize()");
        m_clazz = ProcessData.class;
        operazioniSelezionate = new TreeSet(new OperazioneBeanComparator());
        interventiSelezionati = new TreeSet(new BaseBeanComparator());
        interventiFacoltativi = new TreeSet(new BaseBeanComparator());
        allegatiSelezionati = new TreeSet(new AllegatiBeanComparatorCodice());
        opzioniSettoreAttivita = new ArrayList();
        procedimenti = new ArrayList();
        anagrafica = new AnagraficaBean();
        oneriAnticipati = new TreeSet(new OneriBeanComparator());
        oneriPosticipati = new TreeSet(new OneriBeanComparator());
        comune = new ComuneBean();
        allegatiBuffer = new TreeSet(new AllegatiBeanComparator());
        operazioniIndividuate = new TreeSet(new OperazioneBeanComparator());
        modelloUnicoComplete = false;
        sezioniCompilabili = new ArrayList();
        //by Cedaf - INIZIO
        //history = new ArrayList();
        history = new LinkedList();
        //by Cedaf - FINE
        dichiarazioniStatiche = new ArrayList();
        codiciProcedimenti = new ArrayList();
        processDataPerDestinatario = new ArrayList();
        sezioniCompilabiliBean = new HashMap();
        datiTemporanei = new DatiTemporaneiBean();
        opsVec = new String[0];
        oneriVec = new String[0];
        pagamentoEffettuato = false;
        bookmarkActivityIndex = bookmarkStepIndex = -1;
        bookmarkActivityId = bookmarkStepId = null;
        bookmark = false;
        uploadFile = null;
        daFirmare = false;
        signStepsList = new ArrayList();
        // stepIndex=activityIndex=0;
        output = new Output();
        tipoAccreditamento = null;
        profiloTitolareNew = null;
        credenzialiBase64 = "";
        visualizzaBollo = false;
        disabilitaFirma = false;
        erroreSuHref=new ArrayList();
        datiAssociazioneDiCategoria = new AssociazioneDiCategoriaBean();
        valoriHref=new String[0];
        descrizioneCampiNonCompilati = new ArrayList();
        
        //by Cedaf - INIZIO
        this.idBookmark=null;
        //by Cedaf - FINE
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.process.data.AbstractData#doDefineValidators()
     */
    protected void doDefineValidators() {
        // TODO Auto-generated method stub

    }

    public void exportToPipeline(PipelineData pd) {
        logger.debug("begin " + this.getClass().getName()
                     + ".exportToPipeline()");

        RichiestaDocument richiestaDocument = RichiestaDocument.Factory.newInstance();
        RichiestadiConcessioniEAutorizzazioni richiesta = richiestaDocument.addNewRichiesta();

        Iterator iter = this.getAllegati().iterator();
        AllegatoFirmatoDigitalmente allegatoFirmato = null;
        AllegatoFirmatoDigitalmente allegatoFirmatoRiepilogo = null;
        AllegatononFirmato allegatoNonFirmato = null;
        SceltaRiepilogoRichiesta riepilogo = richiesta.addNewRiepilogoRichiesta();
        // XXX occorre decodificare l'array di byte che viene da people
        // perch? xmlbeans effettua gi? la propria codifica per gli elementi
        // di tipo base64Binary, pertanto senza questa decodifica il
        // contenuto dell'allegato sarebbe codificato due volte.
        // Segnalazione telefonica di Massimiliano Pianciamore del
        // 19/10/2006
        byte[] decoded = null;
        while (iter.hasNext()) {
            Attachment temp = (Attachment) iter.next();
            // riepilogo firmato: uno per richiesta
            if (temp instanceof SignedAttachment) {
                if (temp instanceof SignedSummaryAttachment) {
                    allegatoFirmatoRiepilogo = AllegatoFirmatoDigitalmente.Factory.newInstance();
                    decoded = null;//Base64.decode(((SignedSummaryAttachment) temp).getData().getBytes());
                    allegatoFirmatoRiepilogo.setContenuto(decoded);
                    allegatoFirmatoRiepilogo.setDescrizione(((SignedSummaryAttachment) temp).getDescrizione());
                    allegatoFirmatoRiepilogo.setNomeFile(((SignedSummaryAttachment) temp).getName());
                    riepilogo.setAllegatoFirmatoDigitalmente(allegatoFirmatoRiepilogo);
                    logger.debug("inserito il riepilogo firmato");
                    // allegato firmato: non riepilogo
                } else {
                    allegatoFirmato = richiesta.addNewAllegatoFirmatoDigitalmente();
                    decoded = null;//Base64.decode(((SignedAttachment) temp).getData().getBytes());
                    allegatoFirmato.setContenuto(decoded);
                    allegatoFirmato.setDescrizione(((SignedAttachment) temp).getDescrizione());
                    allegatoFirmato.setNomeFile(((SignedAttachment) temp).getName());
                    logger.debug("inserito un allegato firmato");
                }
                // allegatoNonFirmato: riepilogo
            } else if (((Attachment) temp).getName().indexOf("riepilogo") != -1) {
                allegatoNonFirmato = AllegatononFirmato.Factory.newInstance();
                decoded = null; //Base64.decode(((Attachment) temp).getData().getBytes());
                if(decoded != null){
                    allegatoNonFirmato.setContenuto(decoded);
                } else {
                    allegatoNonFirmato.setContenuto(((Attachment) temp).getData().getBytes());
                }
                allegatoNonFirmato.setDescrizione(((Attachment) temp).getDescrizione());
                allegatoNonFirmato.setNomeFile(((Attachment) temp).getName());
                riepilogo.setAllegatononFirmato(allegatoNonFirmato);
                logger.debug("inserito il riepilogo non firmato");
                // allegatoNonFirmato:
            } else {
                allegatoNonFirmato = richiesta.addNewAllegatononFirmato();
                decoded = null;//;Base64.decode(((Attachment) temp).getData().getBytes());
                allegatoNonFirmato.setContenuto(decoded);
                allegatoNonFirmato.setDescrizione(((Attachment) temp).getDescrizione());
                allegatoNonFirmato.setNomeFile(((Attachment) temp).getName());
                logger.debug("inserito un allegato non firmato");
            }
        }
        // recapito
        Recapito recapito = richiesta.addNewRecapito();

        UtenteAutenticato utenteAutenticato = getRichiedente().getUtenteAutenticato();
        List recapiti = utenteAutenticato.getRecapito();
        // ora recupero solo l'indirizzo email (sufficiente per Connects)
        recapito.setIndirizzoemail((String) recapiti.get(0));
        recapito.setReferente("test");
        recapito.setPriorita(Enum.forInt(1));

        // recapito.addNewRecapitoTelefonico().addNewNumeroTelefonicoNazionale().setNumero((String)recapiti.get(1));
        logger.debug("inserito il recapito");

        SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
        sfd.format(new Date());
        // identificatore richiesta
        Calendar today = sfd.getCalendar();

        IdentificatorediRichiesta identificatoreDiRichiesta = richiesta.addNewIdentificatorediRichiesta();
        IdentificatorediProtocollo protocollo = identificatoreDiRichiesta.addNewIdentificatorediProtocollo();
        protocollo.setCodiceAmministrazione("12345678");
        protocollo.setCodiceAOO("12345678");
        
        /*String tmpProtocollo = simulaProtocollo();
        if(tmpProtocollo != null){
            protocollo.setNumerodiRegistrazione(new BigInteger(tmpProtocollo));
        }
        else{
            protocollo.setNumerodiRegistrazione(new BigInteger("1234567891234"));
        }*/
        protocollo.setNumerodiRegistrazione(new BigInteger("134"));
        protocollo.setDatadiRegistrazione(today);
        IdentificatoreUnivoco idUnivoco = identificatoreDiRichiesta.addNewIdentificatoreUnivoco();
        it.people.util.IdentificatoreUnivoco idUnivocoTemp = this.getIdentificatoreUnivoco();
        CodiceSistema codiceSistema = idUnivocoTemp.getCodiceSistema();
        // Modificato per inserire nell'xml degli identificativi sempre differenti per ciascuna firma
        //idUnivoco.setCodiceIdentificativoOperazione(codiceSistema.getCodiceIdentificativoOperazione());
        idUnivoco.setCodiceIdentificativoOperazione(getIdentificatoreProcedimentoPraticaSpacchettata());
        idUnivoco.setCodiceProgetto(idUnivocoTemp.getCodiceProgetto());
        idUnivoco.addNewCodiceSistema();
        idUnivoco.getCodiceSistema().setCodiceAmministrazione(comune.getCodIstat());
        idUnivoco.getCodiceSistema().setNomeServer(codiceSistema.getNomeServer());
        idUnivoco.setDatadiRegistrazione(today);

        logger.debug("inserito l'identificatore di richiesta");

        // richiedente
        CredenzialiUtenteCertificate credenzialiRichiedente = richiesta.addNewRichiedente();

        // imposto i dati del richiedente da inviare al BE
        credenzialiRichiedente.setCodiceFiscale(getRichiedente().getUtenteAutenticato().getCodiceFiscale());

        AllegatoFirmatoDigitalmente credenziali = credenzialiRichiedente.addNewCredenzialiFirmate();
        credenziali.setContenuto(this.getCredenzialiBase64().getBytes());
        credenziali.setDescrizione("Credenziali utente autenticato");
        credenziali.setNomeFile("CredenzialiFirmateXML_"
                                + this.getIdentificatorePeople().getIdentificatoreProcedimento()
                                + "_Base64.txt");

        logger.debug("inserito il richiedente");

        /*
         * Da completare per la gestione senza accreditamento
         * 
         */
        SceltaTitolare sceltaTitolare = richiesta.addNewTitolare();
        if(this.getAnagrafica().getDichiarante().getAgiscePerContoDi().equalsIgnoreCase("2")){
            // Persona fisica
            logger.debug("Il titolare ? una persona fisica");
            PersonaFisica pf = sceltaTitolare.addNewPersonaFisica();
            pf.setCodiceFiscale(this.getAnagrafica().getDatiPersonaDeleganteBean().getCodiceFiscale());
            //Calendar calendar = Calendar.getInstance();
            Calendar calendar = new SimpleDateFormat("dd/MM/yyyy").getCalendar();
            // Possible errore
            String dataNascita = this.getAnagrafica().getDatiPersonaDeleganteBean().getDataNascita();
            calendar.setTime(dataNascita == null || dataNascita.equalsIgnoreCase("") ? new Date() : new Date(dataNascita));
            pf.setDatadiNascita(calendar);
            pf.setCognome(this.getAnagrafica().getDatiPersonaDeleganteBean().getCognome());
            pf.setNome(this.getAnagrafica().getDatiPersonaDeleganteBean().getNome());

            if (this.getAnagrafica().getDatiPersonaDeleganteBean().getSesso() != null && this.getAnagrafica().getDatiPersonaDeleganteBean().getSesso().equalsIgnoreCase("F")) {
                pf.setSesso(Sesso.FEMMINA);
            } else {
                pf.setSesso(Sesso.MASCHIO);
            }

            SceltaLuogoEsteso luogoDiNascita = pf.addNewLuogodiNascita();
            Localita localita = luogoDiNascita.addNewLocalita();
            localita.setNome(this.getAnagrafica().getDatiPersonaDeleganteBean().getLuogoNascita());
            localita.setCodiceStato("");

            /*ComuneEsteso comune = luogoDiNascita.addNewComuneEsteso();
            comune.setNome(this.getAnagrafica().getDatiPersonaDeleganteBean().getLuogoNascita());*/
            
            SceltaResidenza residenza = pf.addNewResidenza();

            IndirizzoStrutturatoCompleto indirizzo = residenza.addNewIndirizzoStrutturatoCompleto();
            indirizzo.setCivicoChilometrico(new BigDecimal(0));
            Toponimo toponimo = Toponimo.Factory.newInstance();
            //
            toponimo.setDenominazione(this.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getVia());
            // FIXME dove si recupera il DUG?
            toponimo.setDUG(DUG.Enum.forInt(1));
            indirizzo.setToponimo(toponimo);
            Comune comuneResidenza = Comune.Factory.newInstance();
            comuneResidenza.setNome(this.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getCitta());
            comuneResidenza.setSigladiProvinciaISTAT(this.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getProvincia());
            indirizzo.setComune(comuneResidenza);
            // Possibile errore
            if(this.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getCap() != null)
                indirizzo.setCAP(new BigInteger(this.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getCap()));
        }
        else if(this.getAnagrafica().getDichiarante().getAgiscePerContoDi().equalsIgnoreCase("0")){
            logger.debug("Il titolare ? una persona giuridica");
            it.diviana.egov.b109.oggettiCondivisi.PersonaGiuridica pg = sceltaTitolare.addNewPersonaGiuridica();
            //ProfiloPersonaGiuridica pt = (ProfiloPersonaGiuridica) profiloTitolare;
            pg.setDenominazioneoRagioneSociale(this.getAnagrafica().getAttivita().getDenominazione());
            String codiceFiscalePG = this.getAnagrafica().getAttivita().getCodiceFiscale();
            String partitaIvaPG = this.getAnagrafica().getAttivita().getPartitaIva();
            if (partitaIvaPG != null && !partitaIvaPG.equals("")) {
                pg.setCodiceFiscalePersonaGiuridica(new Long(partitaIvaPG).longValue());
            } else {
                // FIXME numero di protocollo fake per testare il servizio
                pg.setCodiceFiscalePersonaGiuridica(12345678911L);
            }
            it.diviana.egov.b109.oggettiCondivisi.Sede sede = pg.addNewSedeLegale();
            IndirizzoStrutturatoCompleto indirizzo = sede.addNewIndirizzoStrutturatoCompleto();
            // FIXME non ? chiaro dove recuperare indirizzo richiedente
            indirizzo.setCivicoChilometrico(new BigDecimal(0));
            if(this.getAnagrafica().getAttivita().getSede().getCap() != null && !this.getAnagrafica().getAttivita().getSede().getCap().equalsIgnoreCase(""))
                indirizzo.setCAP(new BigInteger(this.getAnagrafica().getAttivita().getSede().getCap()));
            Toponimo toponimo = Toponimo.Factory.newInstance();
            toponimo.setDenominazione(this.getAnagrafica().getAttivita().getSede().getVia());
            toponimo.setDUG(DUG.Enum.forInt(1));
            indirizzo.setToponimo(toponimo);
            Comune comuneResidenza = Comune.Factory.newInstance();
            comuneResidenza.setNome(this.getAnagrafica().getAttivita().getSede().getCitta());
            comuneResidenza.setSigladiProvinciaISTAT(this.getAnagrafica().getAttivita().getSede().getProvincia());
            indirizzo.setComune(comuneResidenza);
        }
        else if(this.getAnagrafica().getDichiarante().getAgiscePerContoDi().equalsIgnoreCase("3")){
            logger.debug("Il titolare ? una persona giuridica");
            it.diviana.egov.b109.oggettiCondivisi.PersonaGiuridica pg = sceltaTitolare.addNewPersonaGiuridica();
            //ProfiloPersonaGiuridica pt = (ProfiloPersonaGiuridica) profiloTitolare;
            pg.setDenominazioneoRagioneSociale(this.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getDenominazione());
            String codiceFiscalePG = this.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getCodFiscale();
            String partitaIvaPG = this.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getPartitaIva();
            if (partitaIvaPG != null && !partitaIvaPG.equals("")) {
                pg.setCodiceFiscalePersonaGiuridica(new Long(partitaIvaPG).longValue());
            } else {
                // FIXME numero di protocollo fake per testare il servizio
                pg.setCodiceFiscalePersonaGiuridica(12345678911L);
            }
            it.diviana.egov.b109.oggettiCondivisi.Sede sede = pg.addNewSedeLegale();
            IndirizzoStrutturatoCompleto indirizzo = sede.addNewIndirizzoStrutturatoCompleto();
            // FIXME non ? chiaro dove recuperare indirizzo richiedente
            indirizzo.setCivicoChilometrico(new BigDecimal(0));
            if(this.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getCap() != null && !this.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getCap().equalsIgnoreCase(""))
                indirizzo.setCAP(new BigInteger(this.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getCap()));
            Toponimo toponimo = Toponimo.Factory.newInstance();
            toponimo.setDenominazione(this.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getVia());
            toponimo.setDUG(DUG.Enum.forInt(1));
            indirizzo.setToponimo(toponimo);
            Comune comuneResidenza = Comune.Factory.newInstance();
            comuneResidenza.setNome(this.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getCitta());
            comuneResidenza.setSigladiProvinciaISTAT(this.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getProvincia());
            indirizzo.setComune(comuneResidenza);
        }
        else if(this.getAnagrafica().getDichiarante().getAgiscePerContoDi().equalsIgnoreCase("4")){
            // Professionista munito di procura
            if(this.getAnagrafica().getDatiProfessionistaBean().getAgiscePerContoDi() != null){
                if(this.getAnagrafica().getDatiProfessionistaBean().getAgiscePerContoDi().equalsIgnoreCase("PG")){
                    logger.debug("Il titolare ? una persona giuridica");
                    it.diviana.egov.b109.oggettiCondivisi.PersonaGiuridica pg = sceltaTitolare.addNewPersonaGiuridica();
                    //ProfiloPersonaGiuridica pt = (ProfiloPersonaGiuridica) profiloTitolare;
                    pg.setDenominazioneoRagioneSociale(this.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getDenominazione());
                    String codiceFiscalePG = this.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getCodiceFiscale();
                    String partitaIvaPG = this.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getPartitaIva();
                    if (partitaIvaPG != null && !partitaIvaPG.equals("")) {
                        pg.setCodiceFiscalePersonaGiuridica(new Long(partitaIvaPG).longValue());
                    } else {
                        // FIXME numero di protocollo fake per testare il servizio
                        pg.setCodiceFiscalePersonaGiuridica(12345678911L);
                    }
                    it.diviana.egov.b109.oggettiCondivisi.Sede sede = pg.addNewSedeLegale();
                    IndirizzoStrutturatoCompleto indirizzo = sede.addNewIndirizzoStrutturatoCompleto();
                    // FIXME non ? chiaro dove recuperare indirizzo richiedente
                    indirizzo.setCivicoChilometrico(new BigDecimal(0));
                    if(this.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getCap() != null && !this.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getCap().equalsIgnoreCase(""))
                        indirizzo.setCAP(new BigInteger(this.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getCap()));
                    Toponimo toponimo = Toponimo.Factory.newInstance();
                    toponimo.setDenominazione(this.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getVia());
                    toponimo.setDUG(DUG.Enum.forInt(1));
                    indirizzo.setToponimo(toponimo);
                    Comune comuneResidenza = Comune.Factory.newInstance();
                    comuneResidenza.setNome(this.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getCitta());
                    comuneResidenza.setSigladiProvinciaISTAT(this.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getProvincia());
                    indirizzo.setComune(comuneResidenza);
                    
                    // Dati rappresentante legale
                    // Tolto per tentare di risolvere il problema di Firenze: da rimettere!!!!
                    RappresentanteLegale rappresentanteLegale = pg.addNewRappresentanteLegale();
                    String codiceFiscale = this.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getRappresentanteLegale().getCodiceFiscale();
                    String cognome = this.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getRappresentanteLegale().getCognome();
                    String nome = this.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getRappresentanteLegale().getNome();
                    rappresentanteLegale.setCodiceFiscale(codiceFiscale == null ? "" : codiceFiscale);
                    rappresentanteLegale.setCognome(cognome == null ? "" : cognome);
                    rappresentanteLegale.setNome(nome == null ? "" : nome);
                }
                else if(this.getAnagrafica().getDatiProfessionistaBean().getAgiscePerContoDi().equalsIgnoreCase("PF")){
                    // persona fisica
                    logger.debug("Il titolare ? una persona fisica");
                    PersonaFisica pf = sceltaTitolare.addNewPersonaFisica();
                    pf.setCodiceFiscale(this.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getCodiceFiscale());
                    Calendar calendar = Calendar.getInstance();
                    // Possible errore
                    String dataNascita = this.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getDataNascita();
                    calendar.setTime(dataNascita == null || dataNascita.equalsIgnoreCase("") ? new Date() : new Date(dataNascita));
                    pf.setDatadiNascita(calendar);
                    pf.setCognome(this.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getCognome());
                    pf.setNome(this.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getNome());
                    // Manca il dato in anagrafica.jsp
                    if (this.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getSesso() != null && this.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getSesso().equalsIgnoreCase("F")) {
                        pf.setSesso(Sesso.FEMMINA);
                    } else {
                        pf.setSesso(Sesso.MASCHIO);
                    }

                    SceltaLuogoEsteso luogoDiNascita = pf.addNewLuogodiNascita();
                    Localita localita = luogoDiNascita.addNewLocalita();
                    localita.setNome(this.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getLuogoNascita());
                    localita.setCodiceStato("");
                    
                    /*ComuneEsteso comune = luogoDiNascita.addNewComuneEsteso();
                    comune.setNome(this.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getLuogoNascita());*/

                    SceltaResidenza residenza = pf.addNewResidenza();

                    IndirizzoStrutturatoCompleto indirizzo = residenza.addNewIndirizzoStrutturatoCompleto();
                    indirizzo.setCivicoChilometrico(new BigDecimal(0));
                    Toponimo toponimo = Toponimo.Factory.newInstance();
                    //
                    toponimo.setDenominazione(this.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getResidenza().getVia());
                    // FIXME dove si recupera il DUG?
                    toponimo.setDUG(DUG.Enum.forInt(1));
                    indirizzo.setToponimo(toponimo);
                    Comune comuneResidenza = Comune.Factory.newInstance();
                    comuneResidenza.setNome(this.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getResidenza().getCitta());
                    comuneResidenza.setSigladiProvinciaISTAT(this.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getResidenza().getProvincia());
                    indirizzo.setComune(comuneResidenza);
                    // Possibile errore
                    if(this.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getResidenza().getCap() != null)
                        indirizzo.setCAP(new BigInteger(this.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getResidenza().getCap()));
                }
            }
        }
        
        // titolare: controllo se ? persona fisica o giuridica
        /*SceltaTitolare sceltaTitolare = richiesta.addNewTitolare();
        if (profiloTitolare instanceof ProfiloPersonaFisica) {
            logger.debug("Il titolare ? una persona fisica");
            PersonaFisica pf = sceltaTitolare.addNewPersonaFisica();
            ProfiloPersonaFisica pt = (ProfiloPersonaFisica) profiloTitolare;
            pf.setCodiceFiscale(pt.getCodiceFiscale());
            Calendar calendar = Calendar.getInstance();
            // controllo per evitare java.lang.NullPointerException
            calendar.setTime(pt.getDataNascita() == null ? new Date() : pt.getDataNascita());
            pf.setDatadiNascita(calendar);
            pf.setCognome(pt.getCognome());
            pf.setNome(pt.getNome());
            // FIXME
            if (pt.getSesso().equalsIgnoreCase("F")) {
                pf.setSesso(Sesso.FEMMINA);
            } else {
                pf.setSesso(Sesso.MASCHIO);
            }

            SceltaLuogoEsteso luogoDiNascita = pf.addNewLuogodiNascita();
            Localita comune = luogoDiNascita.addNewLocalita();
            // FIXME non ? chiaro dove recuperare comune e provincia di
            // nascita
            // del richiedente
            comune.setNome(pt.getLuogoNascita());
            comune.setCodiceStato("");

            SceltaResidenza residenza = pf.addNewResidenza();

            IndirizzoStrutturatoCompleto indirizzo = residenza.addNewIndirizzoStrutturatoCompleto();

            // FIXME non ? chiaro dove recuperare indirizzo richiedente
            // String numeroCivico =
            // utenteAutenticato.getResidenza().getNumeroCivico();
            // boolean isNumeric = StringUtils.isNumeric(numeroCivico);
            // indirizzo.setCivicoChilometrico(isNumeric ? new
            // BigDecimal(numeroCivico) : new BigDecimal(0));
            indirizzo.setCivicoChilometrico(new BigDecimal(0));
            Toponimo toponimo = Toponimo.Factory.newInstance();
            //
            toponimo.setDenominazione(utenteAutenticato.getResidenza().getVia());
            toponimo.setDenominazione(pt.getIndirizzoResidenza());
            // FIXME dove si recupera il DUG?
            toponimo.setDUG(DUG.Enum.forInt(1));
            indirizzo.setToponimo(toponimo);
            Comune comuneResidenza = Comune.Factory.newInstance();
            comuneResidenza.setNome(utenteAutenticato.getResidenza().getLuogo().getComune().getNome());
            indirizzo.setComune(comuneResidenza);
        } else {
            logger.debug("Il titolare ? una persona giuridica");

            it.diviana.egov.b109.oggettiCondivisi.PersonaGiuridica pg = sceltaTitolare.addNewPersonaGiuridica();
            ProfiloPersonaGiuridica pt = (ProfiloPersonaGiuridica) profiloTitolare;
            pg.setDenominazioneoRagioneSociale(pt.getDenominazione());
            String codiceFiscalePG = pt.getCodiceFiscale();
            String partitaIvaPG = pt.getPartitaIva();
            if (partitaIvaPG != null && !partitaIvaPG.equals("")) {
                pg.setCodiceFiscalePersonaGiuridica(new Long(partitaIvaPG).longValue());
            } else {
                // FIXME numero di protocollo fake per testare il servizio
                pg.setCodiceFiscalePersonaGiuridica(12345678911L);
            }
            it.diviana.egov.b109.oggettiCondivisi.Sede sede = pg.addNewSedeLegale();
            IndirizzoStrutturatoCompleto indirizzo = sede.addNewIndirizzoStrutturatoCompleto();
            // FIXME non ? chiaro dove recuperare indirizzo richiedente
            indirizzo.setCivicoChilometrico(new BigDecimal(0));
            Toponimo toponimo = Toponimo.Factory.newInstance();
            toponimo.setDenominazione("");
            // FIXME dove si recupera il DUG? E soprattutto, che cosa ? il
            // DUG?
            toponimo.setDUG(DUG.Enum.forInt(1));
            indirizzo.setToponimo(toponimo);
            Comune comuneResidenza = Comune.Factory.newInstance();
            comuneResidenza.setNome("");
            indirizzo.setComune(comuneResidenza);
            RappresentanteLegale rappresentanteLegale = pg.addNewRappresentanteLegale();
            ProfiloPersonaFisica profiloRappresentanteLegale = pt.getRappresentanteLegale();
            rappresentanteLegale.setCodiceFiscale(profiloRappresentanteLegale.getCodiceFiscale());
            rappresentanteLegale.setCognome(profiloRappresentanteLegale.getCognome());
            rappresentanteLegale.setNome(profiloRappresentanteLegale.getNome());
        }*/
                
        logger.debug("inserito il titolare");
        // Richiedeente esteso
        Richiedente richiedente = richiesta.addNewAnagraficaRichiedente();
        richiedente.setNome(this.getAnagrafica().getDichiarante().getNome());
        richiedente.setCognome(this.getAnagrafica().getDichiarante().getCognome());
        richiedente.setPartitaIva("");
        richiedente.setCodFiscale(this.getAnagrafica().getDichiarante().getCodiceFiscale());
        richiedente.setIndirizzoResidenza(this.getAnagrafica().getDichiarante().getResidenza().getVia());
        richiedente.setComuneResidenza(this.getAnagrafica().getDichiarante().getResidenza().getCitta());
        richiedente.setProvinciaResidenza(this.getAnagrafica().getDichiarante().getResidenza().getProvincia());
        richiedente.setLocalitaNascita(this.getAnagrafica().getDichiarante().getLuogoNascita());
        //richiedente.setDataNascita();
        richiedente.setEmail(this.getAnagrafica().getDichiarante().getResidenza().getEmail());
        richiedente.setTelefono(this.getAnagrafica().getDichiarante().getResidenza().getTelefono());
        richiedente.setFax(this.getAnagrafica().getDichiarante().getResidenza().getFax());
        richiedente.setCap(this.getAnagrafica().getDichiarante().getResidenza().getCap());
        // domanda
        Domanda domanda = richiesta.addNewDomanda();
        Procedimenti procedimenti = domanda.addNewProcedimenti();

        // procedimenti
        iter = getProcedimenti().iterator();

        while (iter.hasNext()) {
            ProcedimentoBean bean = (ProcedimentoBean) iter.next();
            ProcedimentoSempliceBean temp = bean.getProcedimento();
            ProcedimentoSemplice procedimentoSemplice = procedimenti.addNewProcedimento();
            // codice, descrizione, termini
            procedimentoSemplice.setCodProcedimento(temp.getCodice());
            procedimentoSemplice.setDesProcedimento(temp.getNome());
            procedimentoSemplice.setTerminiEvasione(temp.getTerminiEvasione());
            // interventi
            Interventi interventi = procedimentoSemplice.addNewInterventi();
            List interventiList = temp.getInterventi();
            Iterator iterInterventi = interventiList.iterator();
            while (iterInterventi.hasNext()) {
                InterventoBean interventoBean = (InterventoBean) iterInterventi.next();
                Intervento intervento = interventi.addNewIntervento();
                intervento.setCodIntervento(interventoBean.getCodice()); 
                intervento.setDesIntervento(interventoBean.getDescrizione());
            }
            // oneri per procedimento
            procedimentoSemplice.setOneri(temp.getOneriDovuti());
        }
        logger.debug("inseriti i procedimenti");
        // dichiarazioni
        Dichiarazioni dichiarazioni = domanda.addNewDichiarazioni();
        Iterator iterDichiarazioni = getDichiarazioniStatiche().iterator();
        // dichiarazioni statiche
        while (iterDichiarazioni.hasNext()) {
            DichiarazioniStaticheBean temp = (DichiarazioniStaticheBean) iterDichiarazioni.next();
            DichiarazioneStatica dichiarazioneStatica = dichiarazioni.addNewDichiarazioneStatica();
            dichiarazioneStatica.setChiave(temp.getCodice());
            dichiarazioneStatica.setTitolo(temp.getTitolo());
            dichiarazioneStatica.setValore(temp.getDescrizione());
        }
        logger.debug("inserite le dichiarazioni statiche");
        // dichiarazioni compilabili
        Map map = getSezioniCompilabiliBean();
        // le chiavi della mappa
        iterDichiarazioni = map.keySet().iterator();
        while (iterDichiarazioni.hasNext()) {
            String key = (String) iterDichiarazioni.next();
            SezioneCompilabileBean temp = (SezioneCompilabileBean) map.get(key);
            DichiarazioneCompilabile dichiarazioneCompilabile = dichiarazioni.addNewDichiarazioneCompilabile();
            dichiarazioneCompilabile.setChiave(key);
            dichiarazioneCompilabile.setDescrizione(temp.getDescrizione());
            // ogni sezione o dichiarazione compilabile contiene uan lista di
            // elementi
            // HrefCampiBean da cui ricavo i valori inseriti dagli utenti
            List campiList = temp.getCampi();
            Iterator iteratorCampi = campiList.iterator();
            Valori valori = dichiarazioneCompilabile.addNewValori();           
            while (iteratorCampi.hasNext()) {
                Valore valore = valori.addNewValore();
                HrefCampiBean campo = (HrefCampiBean) iteratorCampi.next();
                valore.setTitoloCampo(campo.getDescrizione());
                String valoreUtente = campo.getValoreUtente();
                if(valoreUtente == null || "".equals(valoreUtente)){
                    valoreUtente = "-";
                }
                valore.setContatore(campo.getContatore() != null ? campo.getContatore() : "");
                valore.setCodiceCampo(campo.getNome() != null ? campo.getNome() : "");
                valore.setCampoXmlMod(campo.getCampo_xml_mod() != null ? campo.getCampo_xml_mod() : "");
                if(valoreUtente.indexOf("|@|") == -1){
                    valore.addValoreCampo(valoreUtente);
                }
                else{
                    List valoriCampiMultitpli = parseValoriCampiMultipli(valoreUtente);
                    Iterator itCM = valoriCampiMultitpli.iterator();
                    boolean inseritoUno = false;
                    while(itCM.hasNext()){
                        Object val = itCM.next();
                        if(val != null && !val.equals("")){
                            valore.addValoreCampo(""+val);
                            inseritoUno = true;
                        }
                    }
                    if(!inseritoUno){
                        valore.addValoreCampo("-");
                    }
                }
            }
        }
        logger.debug("inserite le dichiarazioni dinamiche");
        logger.debug("inserita la domanda");
        // settore di attivit?
        SettoreAttivita settore = richiesta.addNewSettoreAttivita();
        settore.setCodiceSettore(this.getCodiceSettore());
        //settore.setDescrizioneSettore(this.getDescrizioneSettore());
        settore.setDescrizioneSettore(getDescBookmark());
        
        // data e ora di presentazione
        richiesta.setDataOraPresentazione(today);

        // ente destinatario
        Ente ente = richiesta.addNewEnteDestinatario();
        ente.setCodEnte(getComune().getCodEnte());
        ente.setDesEnte(getComune().getDescrizione());
        Sportello sportello = ente.addNewSportello();
        try {
            BeanUtils.copyProperties(sportello, getComune().getSportello());
            ente.setSportello(sportello);
        } catch (IllegalAccessException e) {
            logger.error(e);
        } catch (InvocationTargetException e) {
            logger.error(e);
        }
        logger.debug("inserito l'ente destinatario");

        // oneri
        // Oneri oneri = richiesta.addNewOneri();
        // //questo dato lo recupero dal processData comune a tutte le richieste
        // (in caso di richieste multiple)
        // oneri.setTotaleOneri(0.0);
        // oneri.setPagamentoEffettuato(this.isPagamentoEffettuato());
        // it.diviana.egov.b109.concessioniEAutorizzazioni.EsitoPagamento esito =
        // oneri.addNewEsitoPagamento();
        // try {
        // BeanUtils.copyProperties(esito, this.getEsitoPagamento());
        // } catch (IllegalAccessException e) {
        // logger.error(e);
        // } catch (InvocationTargetException e) {
        // logger.error(e);
        // }
        richiesta.setTipoDocumento(getCodSportello());

        logger.debug("inserito il codice sportello");

        logger.debug("XML prodotto: " + richiestaDocument.xmlText());
        //System.out.println(XmlObjectWrapper.generateXml(richiestaDocument));
        
        // pd.toString();
        /*
         * Modifica Init 27/04/2007 (1.3.1)
         * Modificata l'intestazione dell'xml finale (encoding ISO-8859-1)
         */
        //pd.setAttribute(PipelineDataImpl.XML_PROCESSDATA_PARAMNAME, XmlObjectWrapper.generateXml(richiestaDocument));
        pd.setAttribute(PipelineDataImpl.XML_PROCESSDATA_PARAMNAME, generateXml(richiestaDocument));
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

    /*
     * (non-Javadoc)
     * 
     * @see it.people.process.PplData#initialize(it.people.core.PeopleContext,
     *      it.people.process.AbstractPplProcess)
     */
    public void initialize(PeopleContext context, AbstractPplProcess pplProcess) {
        // TODO Auto-generated method stub

    }

    /**
     * @return Returns the allegatiBuffer.
     */
    public Set getAllegatiBuffer() {
        return allegatiBuffer;
    }

    /**
     * @param allegatiBuffer
     *            The allegatiBuffer to set.
     */
    public void setAllegatiBuffer(Set allegatiBuffer) {
        this.allegatiBuffer = allegatiBuffer;
    }

    /**
     * @return Returns the allegatiSelezionati.
     */
    public Set getAllegatiSelezionati() {
        return allegatiSelezionati;
    }

    /**
     * @param allegatiSelezionati
     *            The allegatiSelezionati to set.
     */
    public void setAllegatiSelezionati(Set allegatiSelezionati) {
        this.allegatiSelezionati = allegatiSelezionati;
    }

    /**
     * @return Returns the anagrafica.
     */
    public AnagraficaBean getAnagrafica() {
        return anagrafica;
    }


    public void setAnagrafica(AnagraficaBean anagraficaImpresa) {
        this.anagrafica = anagraficaImpresa;
    }

    /**
     * @return Returns the azzeraValori.
     */
    public boolean isAzzeraValori() {
        return azzeraValori;
    }

    /**
     * @param azzeraValori
     *            The azzeraValori to set.
     */
    public void setAzzeraValori(boolean azzeraValori) {
        this.azzeraValori = azzeraValori;
    }

    /**
     * @return Returns the checkBoxValueSelected.
     */
    public boolean isCheckBoxValueSelected() {
        return checkBoxValueSelected;
    }

    /**
     * @param checkBoxValueSelected
     *            The checkBoxValueSelected to set.
     */
    public void setCheckBoxValueSelected(boolean checkBoxValueSelected) {
        this.checkBoxValueSelected = checkBoxValueSelected;
    }

    /**
     * @return Returns the codiceComune.
     */
    public String getCodiceComune() {
        return codiceComune;
    }

    /**
     * @param codiceComune
     *            The codiceComune to set.
     */
    public void setCodiceComune(String codiceComune) {
        this.codiceComune = codiceComune;
    }

    /**
     * @return Returns the codiceSettore.
     */
    public String getCodiceSettore() {
        return codiceSettore;
    }

    /**
     * @param codiceSettore
     *            The codiceSettore to set.
     */
    public void setCodiceSettore(String codiceSettore) {
        this.codiceSettore = codiceSettore;
    }

    /**
     * @return Returns the codiceRamo.
     */
    public String getCodiceRamo() {
        return codiceRamo;
    }

    /**
     * @param codiceRamo
     *            The codiceRamo to set.
     */
    public void setCodiceRamo(String codiceRamo) {
        this.codiceRamo = codiceRamo;
    }

    /**
     * @return Returns the codiciProcedimenti.
     */
    public List getCodiciProcedimenti() {
        return codiciProcedimenti;
    }

    /**
     * @param codiciProcedimenti
     *            The codiciProcedimenti to set.
     */
    public void setCodiciProcedimenti(List codiciProcedimenti) {
        this.codiciProcedimenti = codiciProcedimenti;
    }

    /**
     * @return Returns the comune.
     */
    public ComuneBean getComune() {
        return comune;
    }

    /**
     * @param comune
     *            The comune to set.
     */
    public void setComune(ComuneBean comune) {
        this.comune = comune;
    }

    /**
     * @return Returns the descrizioneSettore.
     */
    public String getDescrizioneSettore() {
        return descrizioneSettore;
    }

    /**
     * @param descrizioneSettore
     *            The descrizioneSettore to set.
     */
    public void setDescrizioneSettore(String descrizioneSettore) {
        this.descrizioneSettore = descrizioneSettore;
    }

    /**
     * @return Returns the initAlberoOneri.
     */
    public boolean isInitAlberoOneri() {
        return initAlberoOneri;
    }

    /**
     * @param initAlberoOneri
     *            The initAlberoOneri to set.
     */
    public void setInitAlberoOneri(boolean initAlberoOneri) {
        this.initAlberoOneri = initAlberoOneri;
    }

    /**
     * @return Returns the interventiFacoltativi.
     */
    public Set getInterventiFacoltativi() {
        return interventiFacoltativi;
    }

    /**
     * @param interventiFacoltativi
     *            The interventiFacoltativi to set.
     */
    public void setInterventiFacoltativi(Set interventiFacoltativi) {
        this.interventiFacoltativi = interventiFacoltativi;
    }

    /**
     * @return Returns the interventiSelezionati.
     */
    public Set getInterventiSelezionati() {
        return interventiSelezionati;
    }

    /**
     * @param interventiSelezionati
     *            The interventiSelezionati to set.
     */
    public void setInterventiSelezionati(Set interventiSelezionati) {
        this.interventiSelezionati = interventiSelezionati;
    }

    /**
     * @return Returns the loopBack.
     */
    public boolean isLoopBack() {
        return loopBack;
    }

    /**
     * @param loopBack
     *            The loopBack to set.
     */
    public void setLoopBack(boolean loopBack) {
        this.loopBack = loopBack;
    }

    /**
     * @return Returns the modelloUnicoComplete.
     */
    public boolean isModelloUnicoComplete() {
        return modelloUnicoComplete;
    }

    /**
     * @param modelloUnicoComplete
     *            The modelloUnicoComplete to set.
     */
    public void setModelloUnicoComplete(boolean modelloUnicoComplete) {
        this.modelloUnicoComplete = modelloUnicoComplete;
    }

    /**
     * @return Returns the oneriAnticipati.
     */
    public Set getOneriAnticipati() {
        return oneriAnticipati;
    }

    /**
     * @param oneriAnticipati
     *            The oneriAnticipati to set.
     */
    public void setOneriAnticipati(Set oneriAnticipati) {
        this.oneriAnticipati = oneriAnticipati;
    }

    /**
     * @return Returns the oneriVec.
     */
    public String[] getOneriVec() {
        return oneriVec;
    }

    /**
     * @param oneriVec
     *            The oneriVec to set.
     */
    public void setOneriVec(String[] oneriVec) {
        this.oneriVec = oneriVec;
    }

    /**
     * @return Returns the operazioniIndividuate.
     */
    public Set getOperazioniIndividuate() {
        return operazioniIndividuate;
    }

    /**
     * @param operazioniIndividuate
     *            The operazioniIndividuate to set.
     */
    public void setOperazioniIndividuate(Set operazioniIndividuate) {
        this.operazioniIndividuate = operazioniIndividuate;
    }

    /**
     * @return Returns the operazioniSelezionate.
     */
    public Set getOperazioniSelezionate() {
        return operazioniSelezionate;
    }

    /**
     * @param operazioniSelezionate
     *            The operazioniSelezionate to set.
     */
    public void setOperazioniSelezionate(Set operazioniSelezionate) {
        this.operazioniSelezionate = operazioniSelezionate;
    }

    /**
     * @return Returns the opsVec.
     */
    public String[] getOpsVec() {
        return opsVec;
    }

    /**
     * @param opsVec
     *            The opsVec to set.
     */
    public void setOpsVec(String[] opsVec) {
        this.opsVec = opsVec;
    }

    /**
     * @return Returns the opzioniSettoreAttivita.
     */
    public List getOpzioniSettoreAttivita() {
        return opzioniSettoreAttivita;
    }

    /**
     * @param opzioniSettoreAttivita
     *            The opzioniSettoreAttivita to set.
     */
    public void setOpzioniSettoreAttivita(List opzioniSettoreAttivita) {
        this.opzioniSettoreAttivita = opzioniSettoreAttivita;
    }

    /**
     * @return Returns the procedimenti.
     */
    public List getProcedimenti() {
        return procedimenti;
    }

    /**
     * @param procedimenti
     *            The procedimenti to set.
     */
    public void setProcedimenti(List procedimenti) {
        this.procedimenti = procedimenti;
    }

    /**
     * @return Returns the sezioniCompilabili.
     */
    public List getSezioniCompilabili() {
        return sezioniCompilabili;
    }

    /**
     * @param sezioniCompilabili
     *            The sezioniCompilabili to set.
     */
    public void setSezioniCompilabili(List sezioniCompilabili) {
        this.sezioniCompilabili = sezioniCompilabili;
    }

    /**
     * @return Returns the sezioniCompilabiliBean.
     */
    public Map getSezioniCompilabiliBean() {
        return sezioniCompilabiliBean;
    }

    /**
     * @param sezioniCompilabiliBean
     *            The sezioniCompilabiliBean to set.
     */
    public void setSezioniCompilabiliBean(Map sezioniCompilabiliBean) {
        this.sezioniCompilabiliBean = sezioniCompilabiliBean;
    }

    /**
     * @return Returns the tipoProcedura.
     */
    public int getTipoProcedura() {
        return tipoProcedura;
    }

    /**
     * @param tipoProcedura
     *            The tipoProcedura to set.
     */
    public void setTipoProcedura(int tipoProcedura) {
        this.tipoProcedura = tipoProcedura;
    }

    /**
     * @return Returns the uploadFile.
     */
    public FormFile getUploadFile() {
        return uploadFile;
    }

    /**
     * @param uploadFile
     *            The uploadFile to set.
     */
    public void setUploadFile(FormFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    /**
     * @return Returns the valoriHref.
     */
    public String[] getValoriHref() {
        return valoriHref;
    }

    /**
     * @param valoriHref
     *            The valoriHref to set.
     */
    public void setValoriHref(String[] valoriHref) {
        this.valoriHref = valoriHref;
    }
    
    public void addValoriHref(String str){
        logger.debug("inside addValoriHref: str=" + str);

        int size = valoriHref.length + 1;
        String[] temp = new String[size];
        logger.debug("inside addValoriHref: size=" + size);
        logger.debug("inside addValoriHref: valoriHref.length=" + valoriHref.length);

        try {
            for (int i = 0; i < valoriHref.length; i++) {
                temp[i] = valoriHref[i];
            }
            temp[size - 1] = str;
            valoriHref = temp;
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void addOperazioniSelezionate(OperazioneBean bean) {
        logger.debug("inside addOperazioniSelezionate: bean=" + bean);
        try {
            operazioniSelezionate.add(bean);
        } catch (Exception e) {
            logger.error(e);
        }

    }

    public void addInterventiSelezionati(InterventoBean bean) {
        logger.debug("inside addInterventiSelezionati: bean=" + bean);
        try {
            interventiSelezionati.add(bean);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void addInterventiFacoltativi(InterventoBean bean) {
        logger.debug("inside addInterventiFacoltativi: bean" + bean);
        try {
            interventiFacoltativi.add(bean);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void addAllegatiSelezionati(AllegatiBean bean) {
        logger.debug("inside addAllegatiSelezionati: bean=" + bean);
        try {
            allegatiSelezionati.add(bean);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void addOpzioniSettoreAttivita(String str) {
        logger.debug("inside addOpzioniSettoreAttivita: str=" + str);
        try {
            opzioniSettoreAttivita.add(str);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void addProcedimenti(ProcedimentoBean bean) {
        logger.debug("inside addProcedimenti: bean=" + bean);
        try {
            procedimenti.add(bean);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void addOneriAnticipati(OneriBean bean) {
        logger.debug("inside addOneriAnticipati: bean=" + bean);
        try {
            oneriAnticipati.add(bean);
        } catch (Exception e) {
            logger.error(e);
        }
    }
    
    public void addOneriPosticipati(OneriBean bean) {
        logger.debug("inside addOneriAnticipati: bean=" + bean);
        try {
            oneriPosticipati.add(bean);
        } catch (Exception e) {
            logger.error(e);
        }
    }
    
    public void addOperazioniIndividuate(OperazioneBean bean) {
        logger.debug("inside addOperazioniIndividuate: bean=" + bean);
        try {
            operazioniIndividuate.add(bean);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void addSezioniCompilabili(SezioneCompilabileBean bean) {
        logger.debug("inside addSezioniCompilabili: bean=" + bean);
        try {
            sezioniCompilabili.add(bean);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void addCodiciProcedimenti(String str) {
        logger.debug("inside addCodiciProcedimenti: str=" + str);
        try {
            codiciProcedimenti.add(str);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void addProcessDataPerDestinatario(ProcessData bean) {
        logger.debug("inside addProcessDataPerDestinatario: str="
                     + bean.getDescSportello());
        try {
            processDataPerDestinatario.add(bean);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void addSezioniCompilabiliBean(String key, SezioneCompilabileBean bean) {
        logger.debug("inside addSezioniCompilabiliBean: key=" + key + ",bean="
                     + bean);
        try {
            sezioniCompilabiliBean.put(key, bean);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /*
     * public void addDatiTemporanei(String key, Object value){
     * logger.debug("inside addDatiTemporanei: key="+key+",value="+value); try {
     * if(value instanceof String){ logger.debug("value is a String");
     * datiTemporanei.put(key, (String)value); }else if(value instanceof Set){
     * logger.debug("value is a Set"); datiTemporanei.put(key, (Set)value);
     * }else if(value instanceof List){ logger.debug("value is a List");
     * datiTemporanei.put(key, (List)value); }else if(value instanceof Map){
     * logger.debug("value is a Map"); datiTemporanei.put(key, (Map)value);
     * }else{ //caso di default logger.debug("value is a
     * "+value.getClass().getName()); datiTemporanei.put(key, value); } } catch
     * (Exception e) { logger.error(e); } }
     */
    /*
     * public void addDatiTemporaneiValue(String key, Object value){
     * logger.debug("inside addDatiTemporaneiValue: key="+key+",value="+value);
     * try { datiTemporanei.put(key, value); } catch (Exception e) {
     * logger.error(e); } }
     */
    // public void addDatiTemporaneiValue(String key, String value){
    // logger.debug("inside addDatiTemporanei(String key, String value):
    // key="+key+",value="+value);
    // try {
    // datiTemporanei.put(key, value);
    //            
    // } catch (Exception e) {
    // logger.error(e);
    // }
    // }
    // public void addDatiTemporaneiValue(String key, Object value){
    // logger.debug("inside addDatiTemporanei(String key, ArrayList value):
    // key="+key+",value="+value);
    // try {
    // datiTemporanei.put(key, value);
    //            
    // } catch (Exception e) {
    // logger.error(e);
    // }
    // }
    // public void addDatiTemporaneiValue(String key, Set value){
    // logger.debug("inside addDatiTemporanei(String key, Set value):
    // key="+key+",value="+value);
    // try {
    // datiTemporanei.put(key, value);
    //            
    // } catch (Exception e) {
    // logger.error(e);
    // }
    // }
    // public void addDatiTemporaneiValue(String key, Map value){
    // logger.debug("inside addDatiTemporanei(String key, Map value):
    // key="+key+",value="+value);
    // try {
    // datiTemporanei.put(key, value);
    //            
    // } catch (Exception e) {
    // logger.error(e);
    // }
    // }
    public void addOpsVec(String str) {
        logger.debug("inside addOpsVec: str=" + str);
        int size = opsVec.length + 1;
        String[] temp = new String[size];
        logger.debug("inside addOpsVec: size=" + size);
        logger.debug("inside addOpsVec: opsVec.length=" + opsVec.length);

        try {
            for (int i = 0; i < opsVec.length; i++) {
                temp[i] = opsVec[i];
            }
            temp[size - 1] = str;
            opsVec = temp;
            // opsVec=vec;
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void addOneriVec(String str) {
        logger.debug("inside addOneriVec: str=" + str);

        int size = oneriVec.length + 1;
        String[] temp = new String[size];
        logger.debug("inside addOneriVec: size=" + size);
        logger.debug("inside addOneriVec: oneriVec.length=" + oneriVec.length);

        try {
            for (int i = 0; i < oneriVec.length; i++) {
                temp[i] = oneriVec[i];
            }
            temp[size - 1] = str;
            oneriVec = temp;
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * @return Returns the datiTemporanei.
     */
    public DatiTemporaneiBean getDatiTemporanei() {
        return datiTemporanei;
    }

    /**
     * @param datiTemporanei
     *            The datiTemporanei to set.
     */
    public void setDatiTemporanei(DatiTemporaneiBean datiTemporanei) {
        this.datiTemporanei = datiTemporanei;
    }

    // /**
    // * @return Returns the activityIndex.
    // */
    // public int getActivityIndex() {
    // return activityIndex;
    // }
    // /**
    // * @param activityIndex The activityIndex to set.
    // */
    // public void setActivityIndex(int activityIndex) {
    // this.activityIndex = activityIndex;
    // }
    // /**
    // * @return Returns the stepIndex.
    // */
    // public int getStepIndex() {
    // return stepIndex;
    // }
    // /**
    // * @param stepIndex The stepIndex to set.
    // */
    // public void setStepIndex(int stepIndex) {
    // this.stepIndex = stepIndex;
    // }
    /**
     * @return Returns the pagamentoEffettuato.
     */
    public boolean isPagamentoEffettuato() {
        return pagamentoEffettuato;
    }

    /**
     * @param pagamentoEffettuato
     *            The pagamentoEffettuato to set.
     */
    public void setPagamentoEffettuato(boolean pagamentoEffettuato) {
        this.pagamentoEffettuato = pagamentoEffettuato;
    }

    /**
     * @return Returns the esitoPagamento.
     */
    public EsitoPagamento getEsitoPagamento() {
        return esitoPagamento;
    }

    /**
     * @param esitoPagamento
     *            The esitoPagamento to set.
     */
    public void setEsitoPagamento(EsitoPagamento esitoPagamento) {
        this.esitoPagamento = esitoPagamento;
    }

    /**
     * @return Returns the dichiarazioniStatiche.
     */
    public List getDichiarazioniStatiche() {
        return dichiarazioniStatiche;
    }

    /**
     * @param dichiarazioniStatiche
     *            The dichiarazioniStatiche to set.
     */
    public void setDichiarazioniStatiche(List dichiarazioniStatiche) {
        this.dichiarazioniStatiche = dichiarazioniStatiche;
    }

    public void addDichiarazioniStatiche(DichiarazioniStaticheBean bean) {
        logger.debug("inside addDichiarazioniStatiche: bean=" + bean);
        try {
            dichiarazioniStatiche.add(bean);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * @return Returns the history.
     */
    //by Cedaf - INIZIO
    /*
    public List getHistory() {
        return history;
    } */
    public LinkedList getHistory(){
        return this.history;
    }

    //by Cedaf - FINE

    /**
     * @param history
     *            The history to set.
     */
    //by Cedaf - INIZIO
    /*
    public void setHistory(List history) {
        this.history = history;
    }
    */
    public void setHistory(LinkedList history){
        this.history=history;
    }
    //by Cedaf - FINE

    public void addHistory(StepBean bean) {
        logger.debug("inside addHistory: bean=" + bean);
        try {
            history.add(bean);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * @return Returns the bookmarkActivityIndex.
     */
    public int getBookmarkActivityIndex() {
        return bookmarkActivityIndex;
    }

    /**
     * @param bookmarkActivityIndex
     *            The bookmarkActivityIndex to set.
     */
    public void setBookmarkActivityIndex(int bookmarkActivityIndex) {
        this.bookmarkActivityIndex = bookmarkActivityIndex;
    }

    /**
     * @return Returns the bookmarkStepIndex.
     */
    public int getBookmarkStepIndex() {
        return bookmarkStepIndex;
    }

    /**
     * @param bookmarkStepIndex
     *            The bookmarkStepIndex to set.
     */
    public void setBookmarkStepIndex(int bookmarkStepIndex) {
        this.bookmarkStepIndex = bookmarkStepIndex;
    }

    /**
     * @return Returns the bookmarkActivityId.
     */
    public String getBookmarkActivityId() {
        return bookmarkActivityId;
    }

    /**
     * @param bookmarkActivityId
     *            The bookmarkActivityId to set.
     */
    public void setBookmarkActivityId(String bookmarkActivityId) {
        this.bookmarkActivityId = bookmarkActivityId;
    }

    /**
     * @return Returns the bookmarkStepId.
     */
    public String getBookmarkStepId() {
        return bookmarkStepId;
    }

    /**
     * @param bookmarkStepId
     *            The bookmarkStepId to set.
     */
    public void setBookmarkStepId(String bookmarkStepId) {
        this.bookmarkStepId = bookmarkStepId;
    }

    /**
     * @return Returns the bookmark.
     */
    public boolean isBookmark() {
        return bookmark;
    }

    
    public void setBookmark(boolean bookmarkInitiated) {
        this.bookmark = bookmarkInitiated;
    }

    /**
     * @return Returns the bookmarkSubStepIndex.
     */
    public int getBookmarkSubStepIndex() {
        return bookmarkSubStepIndex;
    }

    /**
     * @param bookmarkSubStepIndex
     *            The bookmarkSubStepIndex to set.
     */
    public void setBookmarkSubStepIndex(int bookmarkSubStepIndex) {
        this.bookmarkSubStepIndex = bookmarkSubStepIndex;
    }

    /**
     * @return Returns the codSportello.
     */
    public String getCodSportello() {
        return codSportello;
    }

    /**
     * @param codSportello
     *            The codSportello to set.
     */
    public void setCodSportello(String codSportello) {
        this.codSportello = codSportello;
    }

    /**
     * @return Returns the descSportello.
     */
    public String getDescSportello() {
        return descSportello;
    }

    /**
     * @param descSportello
     *            The descSportello to set.
     */
    public void setDescSportello(String descSportello) {
        this.descSportello = descSportello;
    }

    /**
     * @return Returns the daFirmare.
     */
    public boolean isDaFirmare() {
        return daFirmare;
    }

    /**
     * @param daFirmare
     *            The daFirmare to set.
     */
    public void setDaFirmare(boolean daFirmare) {
        this.daFirmare = daFirmare;
    }

    public void setSignStepsList(List signStepsList) {
        this.signStepsList = signStepsList;
    }

    /**
     * @return Returns the signStepsList.
     */
    public List getSignStepsList() {
        return signStepsList;
    }

    /**
     * @return Returns the credenzialiBase64.
     */
    public String getCredenzialiBase64() {
        return credenzialiBase64;
    }

    /**
     * @param credenzialiBase64
     *            The credenzialiBase64 to set.
     */
    public void setCredenzialiBase64(String credenzialiBase64) {
        this.credenzialiBase64 = credenzialiBase64;
    }

    // Vecchia signatura prima di framework 2.0.2
    /*public AbstractProfile getProfiloTitolare() {
        return profiloTitolare;
    }

    public void setProfiloTitolare(AbstractProfile profiloTitolare) {
        this.profiloTitolare = profiloTitolare;
    }*/

    /**
     * @return Returns the tipoAccreditamento.
     */
    public Accreditamento getTipoAccreditamento() {
        return tipoAccreditamento;
    }

    /**
     * @param tipoAccreditamento
     *            The tipoAccreditamento to set.
     */
    public void setTipoAccreditamento(Accreditamento tipoAccreditamento) {
        this.tipoAccreditamento = tipoAccreditamento;
    }

    /**
     * @return Returns the visualizzaBollo.
     */
    public boolean isVisualizzaBollo() {
        return visualizzaBollo;
    }

    /**
     * @param visualizzaBollo
     *            The visualizzaBollo to set.
     */
    public void setVisualizzaBollo(boolean visualizzaBollo) {
        this.visualizzaBollo = visualizzaBollo;
    }

    /**
     * @return the totaleProcedimenti
     */
    public int getTotaleProcedimenti() {
        return totaleProcedimenti;
    }

    /**
     * @param totaleProcedimenti
     *            the totaleProcedimenti to set
     */
    public void setTotaleProcedimenti(int totaleProcedimenti) {
        this.totaleProcedimenti = totaleProcedimenti;
    }

    public void setSignedInfo(SignedInfo info) {
        this.sInfo = info;
    }

    public SignedInfo getSignedInfo() {
        return sInfo;
    }

    public Set getOneriPosticipati() {
        return oneriPosticipati;
    }

    public void setOneriPosticipati(Set oneriPosticipati) {
        this.oneriPosticipati = oneriPosticipati;
    }

    public boolean isDisabilitaFirma() {
        return disabilitaFirma;
    }

    public void setDisabilitaFirma(boolean disabilitaFirma) {
        this.disabilitaFirma = disabilitaFirma;
    }

    public static void scriviProtocolloSuccessivo(String protocollo){
        FileOutputStream fout;      
        try{
            fout = new FileOutputStream ("c:/temp/protocollo.txt");
            new PrintStream(fout).println (protocollo);
            fout.close();       
        }
        catch (IOException e){
            System.err.println ("Unable to write to file");
        }
    }
    
    public static String leggiProtocollo(){
        FileInputStream fin;        
        String retVal = "456";
        try{
            fin = new FileInputStream ("c:/temp/protocollo.txt");
            retVal = new DataInputStream(fin).readLine();
            fin.close();        
        }
        catch (IOException e){
            System.err.println ("Unable to read from file");
        }
        return retVal;
    }   
    
    private String simulaProtocollo(){
        String protocolloTmp = leggiProtocollo();
        String protocolloNew = "1";
        try{
            protocolloNew = ""+(Integer.parseInt(protocolloTmp)+1);
        }
        catch(NumberFormatException e){}
        scriviProtocolloSuccessivo(protocolloNew);
        return protocolloNew;
    }

    public String getDescBookmark() {
        return descBookmark;
    }

    public void setDescBookmark(String descBookmark) {
        this.descBookmark = descBookmark;
    }
    
    public static String generateXml(XmlObject object) {
        String ret = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n";
        ret += object.toString();
        return ret;
    }

    public String getIdentificatoreProcedimentoPraticaSpacchettata() {
        return identificatoreProcedimentoPraticaSpacchettata;
    }

    public void setIdentificatoreProcedimentoPraticaSpacchettata(String identificatoreProcedimentoPraticaSpacchettata) {
        this.identificatoreProcedimentoPraticaSpacchettata = identificatoreProcedimentoPraticaSpacchettata;
    }

    public List getErroreSuHref() {
        return erroreSuHref;
    }

    public void setErroreSuHref(ArrayList erroreSuHref) {
        this.erroreSuHref = erroreSuHref;
    }

    public AssociazioneDiCategoriaBean getDatiAssociazioneDiCategoria() {
        return datiAssociazioneDiCategoria;
    }

    public void setDatiAssociazioneDiCategoria(AssociazioneDiCategoriaBean datiAssociazioneDiCategoria) {
        this.datiAssociazioneDiCategoria = datiAssociazioneDiCategoria;
    }

    public List getDescrizioneCampiNonCompilati() {
        return descrizioneCampiNonCompilati;
    }

    public void setDescrizioneCampiNonCompilati(List descrizioneCampiNonCompilati) {
        this.descrizioneCampiNonCompilati = descrizioneCampiNonCompilati;
    }

    public AbstractProfile getProfiloTitolareNew() {
        return profiloTitolareNew;
    }

    public void setProfiloTitolareNew(AbstractProfile profiloTitolareNew) {
        this.profiloTitolareNew = profiloTitolareNew;
    }

	public String getVerticalizzazioneComune() {
		return verticalizzazioneComune;
	}

	public void setVerticalizzazioneComune(String verticalizzazioneComune) {
		this.verticalizzazioneComune = verticalizzazioneComune;
	}
}
