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
package it.people.process.sign;

import it.people.Activity;
import it.people.ActivityState;
import it.people.City;
import it.people.Step;
import it.people.core.PeopleContext;
import it.people.core.PplPermission;
import it.people.core.PplPrincipal;
import it.people.core.PplSecurityManager;
import it.people.core.ProcessStepSignFactory;
import it.people.core.SignedDataManager;
import it.people.core.persistence.exception.peopleException;
import it.people.layout.ButtonKey;
import it.people.process.AbstractPplProcess;
import it.people.process.common.entity.SignedAttachment;
import it.people.process.common.entity.SignedSummaryAttachment;
import it.people.process.data.AbstractData;
import it.people.process.sign.entity.AttachmentSigningData;
import it.people.process.sign.entity.PrintedSigningData;
import it.people.process.sign.entity.SignedData;
import it.people.process.sign.entity.SignedInfo;
import it.people.process.sign.entity.SigningData;
import it.people.security.Policy;
import it.people.security.SignResponseManager;
import it.people.security.Exception.SignException;
import it.people.util.Device;
import it.people.util.MessageBundleHelper;
import it.people.util.attach.UtilityAttach;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.xerces.utils.Base64;

/**
 * 
 * User: sergio Date: Sep 3, 2003 Time: 1:53:47 PM
 * 
 */
public abstract class ConcreteSign {

    public static class Request {
	public static final String SIGNINGDATA = "SIGNING_DATA";
	public static final String INNERPAGE_HTML = "signPage";
    }

    public static final int STRING_TYPE = 1;
    public static final int ATTACHMENT_TYPE = 2;

    public static final int AUTOMATIC_REDIRECT = 10;
    public static final int MANUAL_REDIRECT = 11;
    public static final int NOOTHER_SIGNSTEP = 20;
    public static final int OTHER_SIGNSTEP = 21;

    protected AbstractPplProcess m_parent;
    private int m_totalSteps;

    protected Activity m_activity;
    protected String m_frameJspPage;
    protected String m_signFrameJspPage;

    protected PeopleContext m_context;

    protected String m_currentUser; // contiene i dati relativi all'utente
				    // attuale

    // protected Object m_currentStepSignData; //contiene i dati da firmare
    // protected int m_currentStepSignDataType = STRING_TYPE; //specifica il
    // tipo di dato da firmare

    private HashMap m_SignedDataStore; // contiene tutti gli oggetti firmati dei
				       // vari step
    // private HashMap m_SignedFiles; // contiene gli attachments firmati
    // private String m_masterSignedInfo; // memorizza l'indice del SI master

    private boolean m_bInitialized = false;

    protected String m_signPage = null;

    /**
     * Costruttore.
     */
    public ConcreteSign() {
	m_activity = null;
	m_SignedDataStore = new HashMap();
	// m_SignedFiles = new HashMap();
    }

    /**
     * Costruttore.
     * 
     * @param parent
     */
    public ConcreteSign(AbstractPplProcess parent) {
	this();
	m_parent = parent;
    }

    public void setPeopleContext(PeopleContext p_context) {
	m_context = p_context;
    }

    public void setPplProcess(AbstractPplProcess parent) {
	m_parent = parent;
    }

    /**
     * Inizializza la 'sign' del processo.
     * 
     * @param comune
     * @param device
     * @throws peopleException
     */
    public void initialize(City comune, Device device,
	    HttpServletRequest p_httpServletRequest) throws peopleException {
	doDefineActivities(p_httpServletRequest);
	doDefineFramePage();
	doDefineSignFramePage();

	// Legge i dati dal DB
	Collection signedDataColl = SignedDataManager.getInstance().get(
		m_parent);
	Iterator iter = signedDataColl.iterator();
	while (iter.hasNext()) {
	    SignedData sd = (SignedData) iter.next();
	    addSignedData(sd);
	}
	m_bInitialized = true;
    }

    public boolean isInitialized() {
	return m_bInitialized;
    }

    /**
     * Definisce le attivit� della sign.
     * 
     * @throws peopleException
     */
    protected abstract void doDefineActivities(HttpServletRequest request)
	    throws peopleException;

    public void setSignPage(String page) {
	m_signPage = page;
    }

    protected String getSignPage() {
	return m_signPage;
    }

    protected void doDefineSignFramePage() {
	m_signFrameJspPage = "/framework/sign/generic/default/html/signFrame.jsp";
    }

    protected void doDefineFramePage() {
	m_frameJspPage = "/framework/sign/generic/default/html/main.jsp";
    }

    public Activity getCurrentActivity() {
	return m_activity;
    }

    public int getTotalSteps() {
	return m_totalSteps;
    }

    public void setTotalSteps(int p_totalSteps) {
	this.m_totalSteps = p_totalSteps;
    }

    public void setCurrentStep(int p_currentStep) {
	m_activity.setCurrentStepIndex(p_currentStep);
    }

    public SignedData getSignedData(String key) {
	return (SignedData) m_SignedDataStore.get(key);
    }

    public SignedData getSignedDataVerion(String key) {
	/**
	 * Se non ho la versione...
	 */
	String tempKey;

	Iterator keySet = m_SignedDataStore.keySet().iterator();
	while (keySet.hasNext()) {
	    tempKey = (String) keySet.next();
	    if (tempKey.endsWith(key)) {
		return (SignedData) m_SignedDataStore.get(tempKey);
	    }
	}
	return null;
    }

    public void addSignedData(SignedData sd) {
	m_SignedDataStore.put(sd.getKey(), sd);
    }

    public List getSignedFiles() {
	return new ArrayList(m_SignedDataStore.values());
	// return m_SignedFiles;
    }

    public AbstractPplProcess getParent() {
	return m_parent;
    }

    /**
     * Aggiunge un'attivit�.
     * 
     * @param name
     * @param state
     * @param steps
     * @param currentStepIndex
     */
    protected void createActivity(String name, ActivityState state,
	    StepSign[] steps, int currentStepIndex) {
	Activity activity = new Activity(name, steps);
	activity.setState(state);
	activity.setCurrentStepIndex(currentStepIndex);
	m_totalSteps = activity.getStepCount();
	m_activity = activity;
    }

    public boolean isComplete() {
	try {
	    for (int i = 0; i < m_activity.getStepCount(); i++) {
		StepSign ss = (StepSign) m_activity.getStep(i);
		if (ss != null
			&& m_SignedDataStore.get(ss.getDo().getData().getKey()) == null)
		    return false;
	    }
	    return true;
	} catch (Exception ex) {

	}
	return false;
    }

    public void print(HttpServletRequest request, HttpServletResponse response)
	    throws IOException, ServletException {
	((StepSign) (m_activity.getStep(m_activity.getCurrentStepIndex())))
		.getDo().doBeforeSign(request, response);
	SigningData sd = (SigningData) request
		.getAttribute(ConcreteSign.Request.SIGNINGDATA);

	if (sd instanceof PrintedSigningData) {
	    String userId = request.getSession().getId();
	    String signPage = new String(sd.getBytes(),
		    request.getCharacterEncoding());
	    request.setAttribute(ConcreteSign.Request.INNERPAGE_HTML, signPage);
	    request.setAttribute("CurrentActivity", getCurrentActivity());
	    request.setAttribute("CurrentStep", getCurrentActivity()
		    .getCurrentStep());
	    request.getRequestDispatcher(m_signFrameJspPage).include(request,
		    response);
	}
	/*
	 * else if (sd instanceof AttachmentSigningData) { }
	 */
    }

    public SigningData getSigningData(HttpServletRequest request,
	    HttpServletResponse response) throws IOException, ServletException {

	((StepSign) (m_activity.getStep(m_activity.getCurrentStepIndex())))
		.getDo().doBeforeSign(request, response);
	SigningData sd = (SigningData) request
		.getAttribute(ConcreteSign.Request.SIGNINGDATA);

	return sd;

    }

    public int beginStep(HttpServletRequest request,
	    HttpServletResponse response) throws IOException, ServletException,
	    Exception {
	/*
	 * Metodo doBeforeSign prepara i dati da firmare e li mette nella
	 * request.
	 */
	((StepSign) (m_activity.getStep(m_activity.getCurrentStepIndex())))
		.getDo().doBeforeSign(request, response);

	if (doSign(request, response)) {
	    // Invio il tutto all'utente usando la pagina specifica per lo step
	    // corrente
	    request.setAttribute("CurrentActivity", getCurrentActivity());
	    request.setAttribute("CurrentStep", getCurrentActivity()
		    .getCurrentStep());
	    request.getRequestDispatcher(m_frameJspPage).include(request,
		    response);
	    return MANUAL_REDIRECT;
	}

	throw new Exception("Impossibile includere un allegato non firmato");
	// return MANUAL_REDIRECT;
    }

    public int completeStep(HttpServletRequest request,
	    HttpServletResponse response, AbstractPplProcess pplProcess)
	    throws IOException, ServletException, SignException,
	    peopleException {

	SignResponseManager srmgr = null;

	SignedInfo sInfo = null;

	if (pplProcess.isOffLineSign()
		&& pplProcess.isWaitingForOffLineSignedDocument()
		&& pplProcess.getOffLineSignDownloadedDocumentHash() != null
		&& !pplProcess.getOffLineSignDownloadedDocumentHash()
			.equalsIgnoreCase("")) {
	    srmgr = new SignResponseManager(pplProcess);
	} else {
	    srmgr = new SignResponseManager(request);
	}

	sInfo = srmgr.process();
	if (sInfo != null) {
	    SignedData sd = ((StepSign) (m_activity.getStep(m_activity
		    .getCurrentStepIndex()))).getDo().doAfterSign(sInfo);
	    /*
	     * Salvo i dati nella map
	     */
	    if (sd != null) {
		addSignedData(sd);
		/*
		 * N.B. Il metodo oltre a salvare il documento imposta anche il
		 * path corretto.
		 */
		SignedDataManager.getInstance().set(sd, m_parent, request);

		if (((StepSign) (m_activity.getStep(m_activity
			.getCurrentStepIndex()))).getDo() instanceof ModuleSignDo) {
		    /*
		     * se ho firmato un modulo lo aggiungo nel ppl data modulo
		     * � la pagina di riepilogo
		     */
		    /*
		     * Questa � la vecchia versione che includeva solo il
		     * riferimento al filesystem del Server del riepilogo ma non
		     * il contenuto
		     * 
		     * SignedData sdTmp=new SignedData();
		     * sdTmp.setFriendlyName(sd.getFriendlyName());
		     * sdTmp.setKey(sd.getKey()); sdTmp.setPath(sd.getPath());
		     * ((
		     * AbstractData)m_parent.getData()).addDocumentiFirmati(sdTmp
		     * );
		     */

		    /*
		     * Aggiungo il riepilogo come un qualsiasi allegato firmato,
		     */
		    SignedAttachment signedAttachment = new SignedSummaryAttachment(
			    sd.getFriendlyName(), // nome del file
			    sd.getPath(), // path del file
			    sInfo.getEncodedSing() // firma
		    );

		    signedAttachment.setDescrizione(sd.getFriendlyName()); // descrizione
									   // dell'allegato
		    signedAttachment.setFileLenght(sd.getSignedContent()
			    .getPkcs7Data().length);
		    // String nuovaGestioneFile =
		    // request.getSession().getServletContext().getInitParameter("remoteAttachFile");
		    // if (nuovaGestioneFile!=null &&
		    // nuovaGestioneFile.equalsIgnoreCase("true")){
		    if (!pplProcess.isEmbedAttachmentInXml()) {
			UtilityAttach ua = new UtilityAttach(pplProcess,
				request);
			String metaInfo = ua.getMetaInfoFile(signedAttachment);
			signedAttachment.setData(metaInfo);
		    } else {
			signedAttachment.setData(new String(Base64.encode(sInfo
				.getPkcs7Data())));
		    }
		    ((AbstractData) m_parent.getData())
			    .addAllegati(signedAttachment);
		}
	    } else {
		// todo ricordarsi di gestire l'errore di sd = null
	    }
	    // saveDBData(att);
	}

	PeopleContext context = PeopleContext.create(request);
	if (m_activity.getCurrentStepIndex() < m_activity.getStepCount() - 1) {
	    if (nextStep(context) == NOOTHER_SIGNSTEP) {
		return NOOTHER_SIGNSTEP;
	    }
	} else
	    return NOOTHER_SIGNSTEP;

	return OTHER_SIGNSTEP;
    }

    /**
     * 
     * @param context
     *            Contesto
     * @param p_currentStep
     *            Step corrente
     * @return Restituisce vero se l'utente pu� entrare nell step in esame
     */
    public final boolean canEnter(PeopleContext context, int p_currentStep) {
	Step tmpStep = m_activity.getStep(p_currentStep);
	if (tmpStep.isActive()) {
	    // Verifica se l'utente ha i permessi per poter accedere alla pagina
	    PplPrincipal[] princs = m_parent.findPrincipal(context.getUser());
	    if (!PplSecurityManager.getInstance().canDo(PplPermission.SIGN,
		    princs, tmpStep.getACL()))
		return false;

	    if (tmpStep.getAccessController() != null
		    && !tmpStep.getAccessController().canEnter(
			    m_parent.getData())) {
		return false;
	    }
	    return true;
	}
	return false;
    }

    public int firstStep(PeopleContext context) {
	setCurrentStep(0);

	if (isStepCompleted(0)
		|| !canEnter(context, getCurrentActivity()
			.getCurrentStepIndex()))
	    return nextStep(context);

	return OTHER_SIGNSTEP;
    }

    protected int nextStep(PeopleContext context) {
	boolean moveNext = true;
	int nextStep = m_activity.getCurrentStepIndex();
	do {
	    nextStep++;
	    if (nextStep < m_totalSteps) {
		if (!isStepCompleted(nextStep) && canEnter(context, nextStep)) {
		    doEnter(nextStep);
		    moveNext = false;
		} else {
		    moveNext = true;
		}
	    } else {
		moveNext = false;
		return NOOTHER_SIGNSTEP;
	    }
	} while (moveNext);
	return OTHER_SIGNSTEP;
    }

    private void doEnter(int p_currentStep) {
	m_activity.setCurrentStepIndex(p_currentStep);
    }

    /**
     * Crea il frammento di Html contenente: 1) l'oggetto da firmare 2) gli
     * script per firmare
     * 
     * TODO: localizzare i messaggi (es. il testo contenuto nel pulsante di
     * submit)
     * 
     * @param request
     * @param response
     * @return true in caso di successo, false altrimenti
     */
    protected boolean doSign(HttpServletRequest request,
	    HttpServletResponse response) {
	String codiceFiscale = m_context.getUser().getUserData()
		.getCodiceFiscale();
	SigningData sd = (SigningData) request
		.getAttribute(ConcreteSign.Request.SIGNINGDATA);

	if (sd instanceof PrintedSigningData
		|| sd instanceof AttachmentSigningData) {
	    String oid = m_parent.getOid().toString();
	    String processName = m_parent.getProcessName();

	    String userId = request.getSession().getId();
	    String cssClass = "class=\"btn\"";
	    String buttonSignCssClass = "class=\"btn\"";

	    /*
	     * N.B. In caso di mancata validazione non � consentito entrare
	     * negli step di Firma, il framework si comporta allo stesso modo
	     * anche con l'utente anonimo che non pu� inviare. La motivazione
	     * di questa scelta � che il processo di firma � abbastanza
	     * pesante prevedendo il salavataggio della pratica stessa.
	     * 
	     * // In caso di mancata validazione non mostra il pulsante di
	     * invio. if (!this.getParent().getData().validate())
	     * buttonSignCssClass = "class=\"btnNoDisplay\"";
	     */

	    // L'header � incluso nella main.jsp della firma

	    String signPage = "";
	    if (sd instanceof PrintedSigningData) {

		String abortLink = "abortSign.do?processId=" + oid
			+ "&amp;processName=" + processName;

		// Firma del riepilogo
		// signPage +=
		// "<form id=\"signForm\" method=\"post\" action=\"abortSign.do?processId="
		// + oid + "&amp;processName=" + processName +
		// "\" class=\"sign\">\n";
		signPage += "<form id=\"signForm\" method=\"post\" action=\"/people/lookupDispatchProcess.do\" enctype=\"multipart/form-data\" class=\"sign\">\n";

		// Div del riepilogo, senza scrollbar per evitare problemi con
		// firefox
		signPage += "<div id=\"Content\" >";
		try {
		    signPage += new String(sd.getBytes(),
			    request.getCharacterEncoding());
		} catch (Exception e) {
		    signPage += new String(sd.getBytes());
		}
		signPage += "</div>\n";

		String labelLegendFirmaOnLine = MessageBundleHelper.message(
			"label.onLineSign.legend", null, processName, m_context
				.getCommune().getKey(), m_context.getLocale());
		String onLineSignLabel = MessageBundleHelper.message(
			"label.onLineSign.sign", null, processName, m_context
				.getCommune().getKey(), m_context.getLocale());

		String offLineSignLegendLabel = MessageBundleHelper.message(
			"label.offLineSign.legend", null, processName,
			m_context.getCommune().getKey(), m_context.getLocale());
		String offLineSignFileDownloadLabel = MessageBundleHelper
			.message("label.offLineSign.downloadDocument", null,
				processName, m_context.getCommune().getKey(),
				m_context.getLocale());
		String offLineSignFileUploadLabel = MessageBundleHelper
			.message("label.offLineSign.uploadSignedDocument",
				null, processName, m_context.getCommune()
					.getKey(), m_context.getLocale());

		String onLineSignControl = "<td align=\"center\"><fieldset style=\"float: right; padding: 5px; border: solid; border-width: 1px;\"><legend style=\"font-weight: bold;\">"
			+ labelLegendFirmaOnLine
			+ "</legend>\n\t\t\t<input type=\"button\" "
			+ buttonSignCssClass
			+ " id=\"firma2\" name=\"firma2\" value=\" "
			+ onLineSignLabel
			+ " \"  onclick=\"javascript:SignClickNew();\" disabled=\"disabled\" /></fieldset></td>";
		String offLineSignFileDownloadControl = "<td align=\"center\"><fieldset style=\"float: right; padding: 5px; border: solid; border-width: 1px;\"><legend style=\"font-weight: bold;\">"
			+ offLineSignLegendLabel
			+ "</legend>\n\t\t\t<input type=\"submit\" "
			+ buttonSignCssClass
			+ " id=\"firmaOffLineDownload\" name=\""
			+ ButtonKey.OFF_LINE_SIGN_DOWNLOAD
			+ "\" value=\" "
			+ offLineSignFileDownloadLabel
			+ " \"  disabled=\"disabled\" />&nbsp;&nbsp;&nbsp;";

		// String offLineSignFileUploadControl =
		// "\n\t\t\t<input type=\"submit\" " + buttonSignCssClass +
		// " id=\"firmaOffLineUpload\" name=\"" +
		// ButtonKey.OFF_LINE_SIGN_UPLOAD + "\" value=\" " +
		// offLineSignFileUploadLabel
		// + " \"  disabled=\"disabled\" />";

		String offLineSignFileUploadControl = "<input type=\"button\" class=\"btn\" id=\"pseudobutton\" value=\" "
			+ offLineSignFileUploadLabel
			+ " \" onclick=\"javascript:peopleOffLineSign.browseDocument();\">"
			+ "<input type=\"file\" class=\"offLineSignUploadHide\" id=\"openssme\" onchange=\"javascript:peopleOffLineSign.uploadDocument();\"  name=\"data.signedDocumentUploadFile\" value=\" "
			+ offLineSignFileUploadLabel
			+ " \" />"
			+ "&nbsp;&nbsp;&nbsp;&nbsp;<script type=\"text/javascript\">var peopleOffLineSign = peopleOffLineSign || {};peopleOffLineSign.browseDocument = function() {	document.getElementById(\"openssme\").click();};"
			+ "peopleOffLineSign.uploadDocument = function() {var hiddenField = document.createElement(\"input\");hiddenField.setAttribute(\"type\", \"hidden\");"
			+ "hiddenField.setAttribute(\"name\", \""
			+ ButtonKey.OFF_LINE_SIGN_UPLOAD
			+ "\");hiddenField.setAttribute(\"value\", \"\");"
			+ "document.getElementById(\"signForm\").appendChild(hiddenField);document.forms[0].submit();}</script></fieldset></td>";

		String browser = request.getHeader("User-Agent");
		boolean isMSIE = browser.indexOf("MSIE") > 0;

		if (isMSIE) {
		    offLineSignFileUploadControl = "<input type=\"file\" class=\"btn\"  name=\"data.signedDocumentUploadFile\" value=\" "
			    + offLineSignFileUploadLabel
			    + " \" /><input type=\"submit\" class=\"pulsanteFirma\" "
			    + "value=\" "
			    + offLineSignFileUploadLabel
			    + " \" name=\"navigation.button.offLineSignFileUpload\" /><script type=\"text/javascript\">var peopleOffLineSign = peopleOffLineSign || {};"
			    + "peopleOffLineSign.browseDocument = function() {	document.getElementById(\"openssme\").click();};peopleOffLineSign.uploadDocument = function() "
			    + "{var hiddenField = document.createElement(\"input\");hiddenField.setAttribute(\"type\", \"hidden\");hiddenField.setAttribute(\"name\", \""
			    + ButtonKey.OFF_LINE_SIGN_UPLOAD
			    + "\");"
			    + "hiddenField.setAttribute(\"value\", \"\");document.getElementById(\"pplProcess\").appendChild(hiddenField);}</script></fieldset></td>";
		}

		if (!m_parent.isOnLineSign()) {
		    onLineSignControl = "";
		}

		if (!m_parent.isOffLineSign()) {
		    offLineSignFileDownloadControl = "";
		    offLineSignFileUploadControl = "";
		}

		if (m_parent.isOnLineSign() && m_parent.isOffLineSign()) {
		    onLineSignControl += "&nbsp;&nbsp;&nbsp;&nbsp;";
		}

		// Footer con i pulsanti
		signPage += "\n<table id=\"footer\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\" align=\"right\">"
			+ "\n\t<tr><td>&nbsp;</td></tr>"
			+ "\n\t<tr>\n\t\t"
			+ "<td align=\"center\"><fieldset style=\"float: right; border: none; padding-top: 18px;\">"
			+
			// se sono disabilitati gli script � comunque
			// possibile annullare la firma
			"\n\t\t\t<input type=\"submit\" "
			+ cssClass
			+ " name=\"annulla\" value=\"     Annulla    \" onclick=\"executeSubmit('"
			+ abortLink
			+ "')\" />"
			+ "&nbsp;&nbsp;&nbsp;&nbsp;"
			+ "<input type=\"button\"  "
			+ cssClass
			+ " id=\"stampa\" name=\"stampa\"  value=\"Versione Stampabile\" onclick=\"executeSubmit('signPrintProcess.do')\" disabled=\"disabled\" />"
			+ "</fieldset></td>"
			+
			// "\n\t\t\t<input type=\"button\"  " +
			// buttonSignCssClass +
			// " id=\"firma\" name=\"firma\"   value=\"       Firma       \" onclick=\"SignClick()\" disabled=\"disabled\" />"
			// +
			// "\n\t\t\t<input type=\"button\" " +
			// buttonSignCssClass +
			// " id=\"firma2\" name=\"firma2\" value=\" Firma \"  onclick=\"javascript:SignClickNew();\" disabled=\"disabled\" />"
			// +
			onLineSignControl
			+ offLineSignFileDownloadControl
			+ offLineSignFileUploadControl
			+ "\n\t\t\t<input type=\"hidden\" name=\"codiceFiscale\" value=\""
			+ codiceFiscale
			+ "\" />"
			+ "\n\t\t\t<input type=\"hidden\" id=\"signedData\" name=\"signedData\" value=\"\" />"
			+ "\n\t\t\t<input type=\"hidden\" name=\"policyID\" value=\""
			+ Policy.XMLTEXT
			+ "\" />"
			+ "\n\t\t\t<input type=\"hidden\" name=\"userID\" value=\""
			+ userId
			+ "\" />"
			+ "\n\t</td>\n</tr>\n</table>"
			+ "</form>\n";
	    } else if (sd instanceof AttachmentSigningData) {
	    }

	    // Visualizza
	    request.setAttribute(ConcreteSign.Request.INNERPAGE_HTML, signPage);
	    return true;
	}

	return false;
    }

    private boolean isStepCompleted(int stepIndex) {
	StepSign currStep = (StepSign) getCurrentActivity().getStep(stepIndex);
	String stepCode = currStep.getDo().getData().getKey();
	return (findSignedDataByKey(stepCode) != null);
    }

    private SignedData findSignedDataByKey(String stepcode) {
	return (SignedData) m_SignedDataStore.get(stepcode);
    }

    protected ArrayList getProcessStep(String p_processName)
	    throws peopleException {

	Collection stepsCollection = ProcessStepSignFactory.getInstance().get(
		p_processName);
	Iterator iterator = stepsCollection.iterator();
	ArrayList lista = new ArrayList();

	while (iterator.hasNext()) {
	    lista.add(iterator.next());
	}
	return lista;
    }

    protected String generateStepKey(String p_baseStepKey, String p_version) {
	return p_version + "." + p_baseStepKey;
    }

    /*
     * Determina quali script di firma includere in base al browser del client
     */
    public static String getScriptFileName(HttpServletRequest request) {
	String[] userAgentIdentifiers = { "MSIE", "Gecko" };
	String[] userAgentScriptNames = { "iiFSMie.js", "iiFSMnn.js" };

	String userAgent = request.getHeader("User-Agent");

	for (int i = 0; i < userAgentIdentifiers.length; i++) {
	    if (userAgent.indexOf(userAgentIdentifiers[i]) != -1)
		return userAgentScriptNames[i];
	}

	return "iiFSMop.js";
    }
}
