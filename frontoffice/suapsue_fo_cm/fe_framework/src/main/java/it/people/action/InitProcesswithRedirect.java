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
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 **/
package it.people.action;

import it.people.City;
import it.people.IProcess;
import it.people.PeopleConstants;
import it.people.core.CommuneManager;
import it.people.core.PeopleContext;
import it.people.core.PplDelegate;
import it.people.core.PplPrincipal;
import it.people.core.PplProcessDelegate;
import it.people.core.PplProcessDelegateManager;
import it.people.core.PplRole;
import it.people.core.PplUser;
import it.people.core.ProcessManager;
import it.people.core.persistence.exception.dbAccessException;
import it.people.core.persistence.exception.peopleException;
import it.people.db.DBConnector;
import it.people.db.fedb.Service;
import it.people.db.fedb.ServiceFactory;
import it.people.error.MessagesFactory;
import it.people.error.errorMessage;
import it.people.exceptions.PeopleDBException;
import it.people.layout.LayoutMenu;
import it.people.layout.NavigationBar;
import it.people.process.AbstractPplProcess;
import it.people.process.data.TransientData;
import it.people.process.workflow.IWorkFlow;
import it.people.process.workflow.IWorkFlowFactory;
import it.people.process.workflow.WorkFlowFactory;
import it.people.propertymgr.PropertyFormatException;
import it.people.util.ActivityLogger;
import it.people.util.Device;
import it.people.util.ProcessMonitor;
import it.people.util.ProcessUtils;
import it.people.util.ServiceParameters;
import it.people.util.payment.EsitoPagamento;
import it.people.util.payment.PendingPaymentException;
import it.people.util.status.PaymentStatusEnum;
import it.people.util.status.ProcessStatus;
import it.people.util.status.ProcessStatusTable;
import it.people.util.status.StatusHelper;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Category;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ControllerConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.ModuleUtils;
import org.apache.struts.util.RequestUtils;

/**
 * 
 * User: sergio Date: Sep 8, 2003 Time: 1:45:31 PM <br>
 * <br>
 * Inizializza il processo creandolo o caricandolo dal db, se l'utente che sta
 * creando il processo e' un possibile delegato per quella tipologia di processo
 * viene reindirizzato alla pagina di scelta del delegante.
 */
public class InitProcesswithRedirect extends Action {
    private static String ERROR_JSP = "/framework/genericErrors/ProcessError.jsp";
    private errorMessage error;
    private Category cat = Category.getInstance(InitProcesswithRedirect.class
	    .getName());
    private PeopleContext pplContext;

    public ActionForward execute(ActionMapping p_actionMapping,
	    ActionForm p_actionForm, HttpServletRequest p_servletRequest,
	    HttpServletResponse p_servletResponse) throws Exception {

	super.execute(p_actionMapping, p_actionForm, p_servletRequest,
		p_servletResponse);
	try {
	    HttpSession session = p_servletRequest.getSession(true);
	    ProcessUtils.clearProcessSessionClearableData(p_servletRequest);
	    pplContext = PeopleContext.create(p_servletRequest);

	    pplContext.setCharacterEncoding(p_servletRequest
		    .getCharacterEncoding());

	    // id del procedimento
	    String idProcess = p_servletRequest.getParameter("processId");

	    // Is from my page?
	    boolean initFromMyPage = Boolean
		    .parseBoolean(org.apache.commons.lang.StringUtils
			    .defaultString(p_servletRequest
				    .getParameter("initFromMyPage"), "false"));

	    // Multiente
	    String communeKey = pplContext.getCommune().getKey();

	    // package del servizio da istanziare (es.
	    // it.people.fsl.servizi.demo.tutorial
	    String processName = p_servletRequest.getParameter("processName");

	    boolean isResend = p_servletRequest.getParameter("resend") != null;

	    if (isResend) {
		p_servletRequest.getSession().setAttribute("sbmtProcessId",
			p_servletRequest.getParameter("sbmtProcessId"));
	    }

	    // Legge la configurazione del Servizio dalla tabella service
	    // dell'FEDB
	    Service serviceConf = (new ServiceFactory()).getService(
		    processName, communeKey);
	    if (serviceConf == null)
		throw new Exception("Il servizio è inesistente.");

	    // tipo del procedimento (es. GenericProcess)
	    String processType = serviceConf.getProcessType();

	    ((AbstractPplProcess) p_actionForm).setTrnData(new TransientData());

	    // determina se si sta caricando un procedimento
	    boolean isLoadProcess = (idProcess != null);

	    // ETNO
	    ProcessMonitor pm = new ProcessMonitor();

	    if (!pm.isStarted(communeKey, processName)) {
		this.error = MessagesFactory.getInstance().getErrorMessage(
			communeKey, "InitProcess.StoppedService");
		this.error.setErrorForward(ERROR_JSP);
		p_servletRequest.setAttribute("errorMessage", this.error);
		return p_actionMapping.findForward("failed"); // Non � mai una
							      // redirect
	    }

	    // Recupero l'utente
	    PplUser pplUser = pplContext.getUser();

	    String operazione = p_servletRequest.getParameter("operazione");
	    boolean isCreateNew = "new".equalsIgnoreCase(operazione);
	    boolean isCreateFor = "delegate".equalsIgnoreCase(operazione);
	    String ownerId = (isCreateFor ? p_servletRequest
		    .getParameter("userId") : pplUser.getUserID());
	    String delegateId = (isCreateFor ? pplUser.getUserID() : null);

	    // With CCD version of Struts, struts is allowed to set the max file
	    // size dinamically,
	    // to value configured in struts-config.xml
	    ModuleUtils moduleUtils = ModuleUtils.getInstance();
	    if (moduleUtils != null) {
		ModuleConfig moduleConfig = moduleUtils
			.getModuleConfig(p_servletRequest);
		if (moduleConfig != null) {
		    ControllerConfig controllerConfig = moduleConfig
			    .getControllerConfig();
		    if (controllerConfig != null) {
			controllerConfig.resetMaxFileSizeToConfiguredValue();
		    }
		}
	    }

	    if (isLoadProcess) {
		// Carico il processo
		if (loadPplProcess((AbstractPplProcess) p_actionForm,
			p_servletRequest, idProcess, serviceConf)) {
		    // Se e' tutto ok success
		    initLayoutComponents((AbstractPplProcess) p_actionForm,
			    session, serviceConf);
		    initProcessParams(processName, session);

		    String outcome = "success";
		    if (isResend) {
			outcome = "resendProcess";
		    }

		    p_servletRequest.getSession().setAttribute(
			    "initFromMyPage", new Boolean(initFromMyPage));

		    // FIX 20080313 BY CEFRIEL
		    // Supporto a redirect mantenendo i parametri in query
		    // string
		    // return p_actionMapping.findForward("success");
		    return StrutsActionUtils.findForwardOrRedirect(
			    p_servletRequest,
			    p_actionMapping.findForward(outcome));
		}
	    } else {
		// Creo il processo
		createPplProcess((AbstractPplProcess) p_actionForm,
			p_servletRequest, processName, communeKey, ownerId,
			delegateId, serviceConf);

		if (p_actionForm != null) {
		    initLayoutComponents((AbstractPplProcess) p_actionForm,
			    session, serviceConf);
		    initProcessParams(processName, session);
		    // FIX 20080313 BY CEFRIEL
		    // Supporto a redirect mantenendo i parametri in query
		    // string
		    // return p_actionMapping.findForward("success");
		    return StrutsActionUtils.findForwardOrRedirect(
			    p_servletRequest,
			    p_actionMapping.findForward("success"));
		}
	    }
	} catch (PendingPaymentException ppex) {
	    cat.info(ppex);
	    return p_actionMapping.findForward("pendingPayment"); // Non � mai
								  // una
								  // redirect?
	} catch (Exception ex) {
	    cat.error(ex);
	}
	return p_actionMapping.findForward("failed"); // Non � mai una
						      // redirect
    }

    /**
     * Valuta se l'utente e' un delegato possibile per questo processo.
     * 
     * @param pplContext
     *            il contesto peolple.
     * @param processType
     *            il tipo di processo.
     * @param communeKey
     *            l'id del comune.
     * @param userId
     *            la userID dell'utente da valutare.
     * @return se l'utente ha delle deleghe per questo processo.
     */
    private boolean isDelegate(PeopleContext pplContext, String processName,
	    String communeKey, String userId) {
	boolean isDelegate = false;

	// recupera il ProcessDelegate manager
	PplProcessDelegateManager myMgr = null;
	try {
	    myMgr = PplProcessDelegateManager.getInstance();
	    if (myMgr == null) {
		throw new Exception(
			"Cannot Instantiate PplProcessDelegateManager");
	    }

	    Timestamp now = new Timestamp(System.currentTimeMillis());

	    // Trovo tutti i delegatori dell'utente per quel comune-processo
	    PplProcessDelegate processDelegate = new PplProcessDelegate();
	    Collection collection = null;

	    processDelegate.setDelegateId(userId);
	    processDelegate.setProcessName(processName);
	    processDelegate.setCommuneId(communeKey);
	    processDelegate.setValidFrom(now);
	    processDelegate.setValidTo(now);

	    collection = myMgr.getProcessDelegates(processDelegate);

	    isDelegate = (!collection.isEmpty());

	} catch (Exception e) {
	    cat.error(e);
	} catch (peopleException e) {
	    cat.error(e);
	}

	return isDelegate;
    }

    /**
     * Crea il processo
     * 
     * @param p_servletRequest
     *            la request.
     * @param processType
     *            il tipo del processo.
     * @param communeKey
     *            l'id del comune.
     * @param ownerId
     *            Owner
     * @param delegateId
     *            Delegato
     * @return il processo creato.
     */
    private void createPplProcess(AbstractPplProcess proc,
	    HttpServletRequest p_servletRequest, String processType,
	    String communeKey, String ownerId, String delegateId,
	    Service serviceConf) {
	proc.init();
	try {
	    City curCity = CommuneManager.getInstance().get(communeKey);
	    // Ottengo le informazioni necessarie alla creazione del processo
	    // dal workflow.
	    IWorkFlowFactory factory = new WorkFlowFactory();

	    IWorkFlow wFlow = factory.createWorkFlow();

	    HttpSession session = ((HttpServletRequest) p_servletRequest)
		    .getSession();
	    IProcess process = wFlow.getProcess(session, processType, curCity);

	    pplContext = PeopleContext.create(p_servletRequest);
	    proc.setCreationTime(new Timestamp(new Date().getTime()));
	    proc.setProcessWorkflow(process);
	    proc.setProcessName(process.getName());

	    // Setto il currentCategory
	    proc.setCurrentCategory(p_servletRequest
		    .getParameter("currentCategory"));

	    // Setto il currentContentID e il currentContent
	    // la descrizione del servizio � letta direttamente dal DB
	    proc.setCurrentContent(serviceConf.getDescription());

	    // Aggiungo l'owner del processo
	    proc.setOwner(ownerId);
	    proc.addPrincipal(new PplPrincipal(ownerId, PplRole.RICHIEDENTE));

	    // Aggiungo l'eventuale delegato 'creatore' del processo
	    if (delegateId != null)
		proc.addDelegate(new PplDelegate(ownerId, delegateId));

	    // Salvo in sessione
	    p_servletRequest.getSession().setAttribute("pplProcess", proc);
	    p_servletRequest.getSession().setAttribute("City", curCity);

	    // inizializzo il processo
	    proc.initialize(pplContext, curCity, Device.HTML);

	    // Imposta la propriet� per l'invio degli allegati al cittadino
	    // la propriet� ReceiptMailAttachment deve essere salvabile
	    // nell'xml e quindi � contenuta nell'AbstractData.
	    // AbstractData � inizializzato in proc.initialize() e quindi
	    // l'impostazione di ReceiptMailAttachment deve essere successiva
	    // all'inizializzazione.
	    proc.setReceiptMailAttachment(serviceConf.isReceiptMailAttachment());

	    // Eng-28072011->
	    proc.setSendMailToOwner(serviceConf.isSendMailToOwner());
	    // <-Eng-28072011
	    proc.setEmbedAttachmentInXml(serviceConf.isEmbedAttachmentInXml());
	    proc.setShowPrivacyDisclaimer(serviceConf.isShowPrivacyDisclaimer());
	    proc.setPrivacyDisclaimerRequireAcceptance(serviceConf
		    .isPrivacyDisclaimerRequireAcceptance());

	    proc.setOnLineSign(serviceConf.isOnLineSign());
	    proc.setOffLineSign(serviceConf.isOffLineSign());

	    proc.setEnabledAuditProcessors(serviceConf.getEnabledProcessors());

	    proc.setServiceConf(serviceConf);

	    ActivityLogger.getInstance().log(proc, "Creazione Processo",
		    ActivityLogger.INFO);
	} catch (peopleException pex) {
	    if (pex instanceof dbAccessException) {
		this.error = MessagesFactory.getInstance().getErrorMessage(
			communeKey, "InitProcess.dbError");
		this.error.setErrorForward(ERROR_JSP);
		p_servletRequest.setAttribute("errorMessage", this.error);
		cat.error(pex);
	    }
	} catch (Exception ex) {
	    this.error = MessagesFactory.getInstance().getErrorMessage(
		    communeKey, "InitProcess.CreationError");
	    this.error.setErrorForward(ERROR_JSP);
	    p_servletRequest.setAttribute("errorMessage", this.error);
	    cat.error(ex);
	}

    }

    protected void setError(String errorKey, HttpServletRequest p_servletRequest)
	    throws PropertyFormatException {
	this.error = MessagesFactory.getInstance().getErrorMessage(
		pplContext.getCommune().getKey(), errorKey);
	this.error.setErrorForward(ERROR_JSP);
	p_servletRequest.setAttribute("errorMessage", this.error);
    }

    /**
     * Carica il processo
     * 
     * @param p_servletRequest
     *            la request.
     * @param processId
     *            l'id del processo da caricare.
     * @return il processo caricato.
     */
    private boolean loadPplProcess(AbstractPplProcess proc,
	    HttpServletRequest p_servletRequest, String processId,
	    Service serviceConf) throws PendingPaymentException {
	Collection processes = null;
	Iterator processIter = null;
	boolean procedimentoCaricato = false;

	try {
	    // Per compatibilit� all'indietro sono previsti due tipi di
	    // caricamento
	    // o con l'OID che � un valore numerico
	    // o con il processID che � alfanumerico

	    Long oid = null;
	    try {
		oid = new Long(processId);
	    } catch (Exception ex) {
	    }

	    // Caricamento della pratica
	    if (oid != null) {
		// Caricamento della pratica per oid
		if (ProcessManager.getInstance().load(proc,
			PeopleContext.create(p_servletRequest), oid) == null) {
		    // la pratica non esiste ritorno errore
		    setError("InitProcess.load.ProcessNotFound",
			    p_servletRequest);
		    return false;
		}
	    } else {
		// Caricamento della pratica per processID
		if (ProcessManager.getInstance().load(proc,
			PeopleContext.create(p_servletRequest), processId) == null) {
		    // la pratica non esiste ritorno errore
		    setError("InitProcess.load.ProcessNotFound",
			    p_servletRequest);
		    return false;
		}
	    }

	    proc.getView().propertyUpdate();
	    if (proc != null) {
		p_servletRequest.getSession().setAttribute("pplProcess", proc);
		p_servletRequest.getSession().setAttribute("City",
			proc.getCommune());
	    }

	    // Determina lo stato della pratica
	    ProcessStatusTable processStatusTable = StatusHelper
		    .getProcessStatusTableFromProcessId(processId);
	    if (processStatusTable == null) {
		// nessun pagamento pendente
		procedimentoCaricato = true;
		proc.setPaymentStatus(null);

	    } else if (processStatusTable.getStatus() == ProcessStatus.PAYMENT_PENDING
		    .getStatusCode()) {
		// Indica all'utente che il pagamento � ancora pendente
		// String message = "Pratica " + processId
		// + " - tentativo di accesso con pagamento pendente";
		// throw new PendingPaymentException(message);
		proc.setPaymentStatus(PaymentStatusEnum.paymentPending);
	    } else if (processStatusTable.getStatus() == ProcessStatus.PAYMENT_ABORTED
		    .getStatusCode()) {
		// this.error = MessagesFactory.getInstance().getErrorMessage(
		// pplContext.getCommune().getKey(),
		// "pagamento.abortito");
		// this.error.setErrorForward(ERROR_JSP);
		// p_servletRequest.setAttribute("errorMessage", this.error);
		proc.setPaymentStatus(PaymentStatusEnum.paymentAborted);
	    } else {
		// if (processStatusTable.getStatus() ==
		// ProcessStatus.PAYMENT_OK.getStatusCode()) {
		// Se la pratica riguarda un pagamento � necessario ricaricare
		// l'esito pagamento
		// e attivare lo step di esito

		EsitoPagamento esitoPagamento = processStatusTable
			.getPaymentResult();
		p_servletRequest.getSession().setAttribute("EsitoPagamento",
			esitoPagamento);
		// Abilita solo gli step consultabili dopo il pagamento
		proc.getView().setActivePaymentResultStep();
		proc.setPaymentStatus(PaymentStatusEnum.paymentSuccessfull);
		// procedimentoCaricato = true;
	    }
	    procedimentoCaricato = true;

	    proc.setReceiptMailAttachment(serviceConf.isReceiptMailAttachment());
	    proc.setSendMailToOwner(serviceConf.isSendMailToOwner());
	    proc.setEmbedAttachmentInXml(serviceConf.isEmbedAttachmentInXml());
	    proc.setShowPrivacyDisclaimer(serviceConf.isShowPrivacyDisclaimer());
	    proc.setPrivacyDisclaimerRequireAcceptance(serviceConf
		    .isPrivacyDisclaimerRequireAcceptance());
	    proc.setOnLineSign(serviceConf.isOnLineSign());
	    proc.setOffLineSign(serviceConf.isOffLineSign());
	    proc.setEnabledAuditProcessors(serviceConf.getEnabledProcessors());
	    proc.setServiceConf(serviceConf);

	    ActivityLogger.getInstance().log(proc, "Caricamento Processo",
		    ActivityLogger.INFO);

	} catch (PendingPaymentException ppex) {
	    throw ppex;
	} catch (peopleException pex) {
	    if (pex instanceof dbAccessException) {
		try {
		    this.error = MessagesFactory.getInstance().getErrorMessage(
			    pplContext.getCommune().getKey(),
			    "InitProcess.dbError");
		    this.error.setErrorForward(ERROR_JSP);
		    p_servletRequest.setAttribute("errorMessage", this.error);
		    cat.error(pex);
		} catch (Exception ex) {
		    cat.error(ex);
		}
	    }
	} catch (Exception ex) {
	    cat.error(ex);
	}

	return procedimentoCaricato;
    }

    private Long fetchProcessOidFromProcessStatus(String peopleId)
	    throws PeopleDBException {
	Connection conn = null;
	Statement stat = null;
	ResultSet rs = null;
	Long retVal = null;
	try {
	    conn = DBConnector.getInstance().connect(DBConnector.FEDB);
	    stat = conn.createStatement();
	    String query = "SELECT * FROM process_status WHERE peopleid = '"
		    + peopleId + "'";

	    rs = stat.executeQuery(query);

	    // PeopleId � univoco, al massimo esiste uno ed un solo risultato
	    if (rs.next()) {
		long pid = rs.getLong("savedprocessid");
		retVal = new Long(pid);
	    }
	} catch (SQLException e) {
	    throw new PeopleDBException(e.getMessage());
	} finally {
	    if (rs != null)
		try {
		    rs.close();
		} catch (Exception ex) {
		}
	    if (stat != null)
		try {
		    stat.close();
		} catch (Exception ex) {
		}
	    if (conn != null)
		try {
		    conn.close();
		} catch (Exception ex) {
		}
	}

	return retVal;
    }

    private void initLayoutComponents(AbstractPplProcess process,
	    HttpSession session, Service serviceConf) {
	LayoutMenu theMenu = new LayoutMenu(serviceConf.getId(),
		process.getView());
	NavigationBar theNavBar = new NavigationBar(process.getView());

	session.setAttribute("menuObject", theMenu);
	session.setAttribute("navBarObject", theNavBar);
    }

    private void initProcessParams(String serviceName, HttpSession session) {
	City city = (City) session
		.getAttribute(PeopleConstants.SESSION_NAME_COMMUNE);
	ServiceParameters sp = new ServiceParameters(serviceName, city.getKey());
	session.setAttribute("serviceParameters", sp);
    }
}
