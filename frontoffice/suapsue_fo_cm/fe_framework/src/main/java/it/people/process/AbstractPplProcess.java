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
package it.people.process;

import it.people.ActivityState;
import it.people.City;
import it.people.DtoStep;
import it.people.IActivity;
import it.people.IProcess;
import it.people.IStep;
import it.people.IValidationErrors;
import it.people.IView;
import it.people.PeopleConstants;
import it.people.StepState;
import it.people.SummaryActivity;
import it.people.SummaryPage;
import it.people.ValidationErrors;
import it.people.action.SaveUploadFile;
import it.people.core.PeopleContext;
import it.people.core.PplACE;
import it.people.core.PplDelegate;
import it.people.core.PplPrincipal;
import it.people.core.PplRole;
import it.people.core.PplUser;
import it.people.core.exception.MappingException;
import it.people.core.exception.ServiceException;
import it.people.core.persistence.exception.peopleException;
import it.people.dao.DAOFactory;
import it.people.dao.IDataAccess;
import it.people.db.fedb.Service;
import it.people.envelope.EnvelopeFactory_modellazioneb116_envelopeb002;
import it.people.envelope.IEnvelopeFactory;
import it.people.envelope.IRequestEnvelope;
import it.people.envelope.beans.ContenutoBusta;
import it.people.envelope.util.EnvelopeHelper;
import it.people.fsl.servizi.oggetticondivisi.IdentificatorePeople;
import it.people.parser.exception.ParserException;
import it.people.process.attachment.RequiredAttachmentList;
import it.people.process.common.entity.Attachment;
import it.people.process.data.AbstractData;
import it.people.process.data.TransientData;
import it.people.process.dto.PeopleDto;
import it.people.process.dto.ServiceConf;
import it.people.process.print.ConcretePrint;
import it.people.process.sign.ConcreteSign;
import it.people.process.sign.entity.OffLineSignProcess;
import it.people.process.view.ConcreteView;
import it.people.util.Device;
import it.people.util.EMailSender;
import it.people.util.IDGenerator;
import it.people.util.IUserPanel;
import it.people.util.IdentificatoreUnivoco;
import it.people.util.MessageBundleHelper;
import it.people.util.MimeType;
import it.people.util.PeopleProperties;
import it.people.util.RequestUtils;
import it.people.util.UserPanel;
import it.people.util.XmlObjectWrapper;
import it.people.util.params.ParamNotFoundException;
import it.people.util.params.ParameterHelper;
import it.people.util.status.PaymentStatusEnum;
import it.people.util.table.OptionBean;
import it.people.util.table.TableHelper;
import it.people.util.table.TableNotFoundException;
import it.people.vsl.PipelineHandler;
import it.people.vsl.exception.SendException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;
import org.apache.xmlbeans.XmlObject;

/**
 * 
 * User: sergio Date: Sep 8, 2003 Time: 2:12:30 PM <br>
 * <br>
 * 
 * Questa classe e' il padre di tutti i processi e ne definisce le
 * caratteristiche principali. Tutte le classi che rappresentano uno specifico
 * processo devono estendere questa classe.
 */
public abstract class AbstractPplProcess extends DynaActionForm {

    // estrae la parte relativa al codice fiscale
    protected static final String ANONYMOUS_ID_PREFIX = PeopleConstants.ANONYMOUS_USERID
	    .substring(0, 16);

    // Chiave per il messaggio di errore nel caso si superi la dimensione
    // massima nel'upload di un allegato
    protected static final String MESSAGE_KEY_MAX_LENGTH_EXCEEDED = "errors.uploadExceedMaxLength";
    protected static final String MESSAGE_KEY_MAX_TOTAL_LENGTH_EXCEEDED = "errors.attachmentListExceedMaxLength";
    protected static final String SAVE_UPLOAD_ACTION_PATH = "/saveUploadedFile";

    // Logger
    private transient Logger logger = Logger
	    .getLogger(AbstractPplProcess.class);

    // Introdotto la deserializzazione lazy del xml.
    private PplData m_data;

    private HashMap m_principals;
    private HashMap m_delegates;

    protected Long m_oid;
    protected String currentOperationId;
    protected Boolean m_sent;
    protected String m_status;
    protected String m_owner;

    protected ConcreteView m_viewDelegate;
    protected ConcreteSign m_signDelegate;
    protected HashMap m_printDelegates = new HashMap();

    protected PplObserver m_observer;
    protected SummaryPage m_summaryDelegate;
    protected boolean m_initialized;
    protected City m_commune;

    protected Timestamp m_lastModifiedTime;
    protected Timestamp m_creationTime;

    protected String m_currentCategory = "";
    protected String m_currentContent = "";
    protected String m_currentContentID = "";

    private PplACE[] m_sendACEs;
    protected IProcess m_process;
    protected String m_processName;

    protected int lastActivityIdx;
    protected int lastStepIdx;

    protected String lastActivityId;
    protected String lastStepId;

    protected PeopleContext m_context = null;
    protected Device m_device;

    /**
     * Errori di validazione che devono essere mostrati all'interno del
     * servizio, sono gestiti attraverso il framework Struts e mostrati
     * attraverso il custom tag PeopleErrorsTag.
     */
    protected IValidationErrors errors = null;

    /**
     * Errori generali del servizio e del framework che devono essere mostrati
     * all'interno dello step del servizio sempre attraverso il custom tag
     * PeopleErrorsTag. L'array contiene le chiavi dei messaggi di errore che
     * dovranno essere specificati in uno dei file di properties previsti.
     */
    protected ArrayList serviceErrors = new ArrayList();

    /**
     * Il metodo permette di aggiungere un messaggio di errore che deve essere
     * mostrato all'interno del servizio attraverso il custom tag
     * PeopleErrorsTag, il messaggio sar� accodato agli eventuali messaggi di
     * errore riportati dalla validazione. N.B. il messaggio non � la chiave
     * ma il testo vero e proprio.
     * 
     * @param message
     */
    public void addServiceError(String message) {
	this.serviceErrors.add(message);
    }

    /**
     * Il metodo ritorna l'elenco delle chiavi dei messaggi di errore generici.
     * Per generici si intendono tutti i messaggi di errore che devono essere
     * mostrati all'interno dello step del servizio ma che non sono collegati
     * alla validazione.
     * 
     * @return
     */
    public ArrayList getServiceError() {
	return this.serviceErrors;
    }

    /**
     * @supplierCardinality 1
     */
    protected RequiredAttachmentList requiredAttachments = new RequiredAttachmentList();

    private OffLineSignProcess offLineSignProcess = new OffLineSignProcess();

    private PaymentStatusEnum paymentStatus;

    private boolean resendProcess;

    private transient IUserPanel userPanel;

    private ServiceConf serviceConf;

    public AbstractPplProcess() {
	super();
	init();
    }

    public IValidationErrors getValidationErrors() {
	return errors;
    }

    /**
     * @return Returns the m_tData.
     */
    public TransientData getTrnData() {
	return (TransientData) get("trnData");
    }

    /**
     * @param data
     *            The m_tData to set.
     */
    public void setTrnData(TransientData data) {
	set("trnData", data);
    }

    /**
     * @return Returns the m_tData.
     */
    public PeopleDto getDto() {
	return (PeopleDto) get("dto");
    }

    /**
     * Ritorna l'identificativo operazione corrente, se � stata invocato
     * getNewIdentificativoOperazione() ma non � ancora stata effettuata una
     * chiamata al web-service di back-end attraverso callService(), allora l'id
     * ritornato � il nuovo id generato altrimenti l'id ritornato �
     * l'identificativo della pratica. N.B. L'identificativo generato � sempre
     * rimosso dall'invocazione della callService(), anche in caso di errore,
     * � quindi sempre necessario rigenerarlo prima di effettuare nuove
     * chiamate al back-end.
     * 
     * @return identificativo operazione
     */
    public String getCurrentOperationId() {

	if (currentOperationId == null) {
	    IdentificatoreUnivoco idUnivoco = ((AbstractData) this.getData())
		    .getIdentificatoreUnivoco();
	    return idUnivoco.getCodiceSistema()
		    .getCodiceIdentificativoOperazione();
	} else {
	    return this.currentOperationId;
	}
    }

    public void init() {
	m_oid = null;
	m_status = "S_EDIT";
	m_viewDelegate = null;
	m_signDelegate = null;
	m_initialized = false;
	m_sent = new Boolean(false);
	m_principals = new HashMap();
	m_delegates = new HashMap();
	m_sendACEs = null;
	m_process = null;
	m_context = null;
	m_device = null;
	// aggiunte ...

	m_owner = null;
	m_printDelegates = new HashMap();
	m_observer = null;
	m_summaryDelegate = null;
	m_commune = null;
	m_lastModifiedTime = null;
	m_creationTime = null;
	m_currentCategory = "";
	m_currentContent = "";
	m_currentContentID = "";
	m_process = null;
	m_processName = null;

	/*
	 * Correzione bug segnalato da regulus (Cedaf - Michele Fabbri)
	 * 
	 * "Se nella prima pagina di un servizio la/ logicalValidate/ fallisce,
	 * tornando, senza terminare la sessione, all'indice dei servizi e
	 * scegliendo un'altro servizio la pagina centrale e' vuota e il metodo/
	 * service/ non viene chiamata; a questo punto, selezionando la prima
	 * attivita' del servizio, la pagina viene visualizzata correttamente."
	 * 
	 * La action form di un nuovo procedimento contiene ancora la lista
	 * degli errori accumulati in un procedimento precedente, � necessario
	 * azzerarli
	 */
	this.errors = null;

	// Azzerra gli errori di servizio non collegati alla validazione
	this.serviceErrors.clear();

	this.setPaymentStatus(null);

	this.setResendProcess(false);

	this.userPanel = new UserPanel(this);

    }

    /**
     * istanzio il DTO corretto rispetto allo STEP corrente sulla base delle
     * informazioni presenti nel process
     */
    public void createDto() throws MappingException {
	IStep currentStep = getView().getCurrentActivity().getCurrentStep();
	String dtoClass = currentStep.getDto();
	if (dtoClass != null) {
	    Class dtoClazz = null;
	    try {
		dtoClazz = Class.forName(dtoClass);
	    } catch (ClassNotFoundException e) {
		logger.error("DTO CLASS NOT FOUND", e);
		throw new MappingException("DTO CLASS NOT FOUND");
	    }
	    // SE ESISTE UN DTO PRESENTE NON NE CREO UNO NUOVO !!!
	    // ES. se c'� un problema sulla validazione ripresento i dati
	    Class ttt = getDto().getClass();
	    if (getDto() != null && !(ttt == dtoClazz)) {
		// istanzio il DTO corretto
		Object dto = null;
		try {
		    dto = dtoClazz.newInstance();
		} catch (InstantiationException e) {
		    logger.error("DTO CLASS ILLEGAL INSTANTIATE", e);
		    throw new MappingException("DTO CLASS ILLEGAL INSTANTIATE");
		} catch (IllegalAccessException e) {
		    logger.error("DTO CLASS ILLEGAL ACCESS", e);
		    throw new MappingException("DTO CLASS ILLEGAL ACCESS");
		}
		this.set("dto", dto);
	    }
	} else {
	    // STEP SENZA DTO
	    this.set("dto", new PeopleDto());
	}

    }

    /**
     * Popola il DTO partendo dagli oggetti BO
     * 
     */
    public void writeDto() throws MappingException {
	if (getView().getCurrentActivity().getCurrentStep() instanceof DtoStep) {
	    ((DtoStep) getView().getCurrentActivity().getCurrentStep())
		    .writeDto(getData(), (PeopleDto) get("dto"));
	}
    }

    /**
     * Legge il DTO per popolare dagli oggetti BO
     * 
     */
    public void readDto() throws MappingException {
	if (getView().getCurrentActivity().getCurrentStep() instanceof DtoStep) {
	    ((DtoStep) getView().getCurrentActivity().getCurrentStep())
		    .readDto(getData(), (PeopleDto) get("dto"));
	}
    }

    /**
     * 
     * @param process
     */
    public void setProcessWorkflow(IProcess process) {
	m_process = process;
	updateLastCoords();
    }

    /**
     * 
     * @return
     */
    public IProcess getProcessWorkflow() {
	return m_process;
    }

    /**
     * Inizializza il processo.
     * 
     * @param context
     * @param unComune
     * @param device
     * @throws Exception
     * @throws peopleException
     */
    public void initialize(PeopleContext context, City unComune, Device device)
	    throws Exception, peopleException {

	m_context = context;
	m_commune = unComune;
	m_device = device;

	// Azzerra gli errori di servizio non collegati alla validazione
	this.serviceErrors.clear();
    }

    public String getParameter(String paramName) throws ParamNotFoundException {

	return ParameterHelper.getInstance(this.getCommune()).getParameter(
		this, paramName);
    }

    /**
     * @see it.people.util.params.IParamHelper#paramExist(it.people.process.AbstractPplProcess,
     *      java.lang.String)
     */
    public boolean paramExist(String paramName) {
	return ParameterHelper.getInstance(this.getCommune()).paramExist(this,
		paramName);
    }

    /**
     * ritorna la Collection con i dati della TABELLA di DOMINIO
     * 
     * @param tableID
     *            ID tabella di dominio
     * @return
     * @throws TableNotFoundException
     */
    public Collection getTableOptions(String tableID)
	    throws TableNotFoundException {
	if (getCommune() != null)
	    return TableHelper.getInstance(this.getCommune()).getTableValues(
		    getProcessName(), getCommune().getOid(), tableID);
	else
	    return null;
    }

    /**
     * ritorna la Collection con i dati della TABELLA di DOMINIO per la quale
     * nella colonna indicizzata da indexFilterColumn c'e' il valore
     * valueFilterColumn
     * 
     * @param tableID
     *            ID tabella di dominio
     * @return
     * @throws TableNotFoundException
     */
    public Collection getTableOptionsFiltered(String tableID,
	    int indexFilterColumn, String valueFilterColumn)
	    throws TableNotFoundException {
	if (getCommune() != null) {
	    Collection allTableValues = TableHelper.getInstance(
		    this.getCommune()).getTableValues(getProcessName(),
		    getCommune().getOid(), tableID);
	    Collection filteredValues = new ArrayList();

	    Iterator it = allTableValues.iterator();
	    String valueOfIndexedColumnInCurrentRow = null;
	    OptionBean ob = null;
	    while (it.hasNext()) {
		ob = (OptionBean) it.next();
		valueOfIndexedColumnInCurrentRow = ob
			.getLabel(indexFilterColumn - 1);
		if (valueOfIndexedColumnInCurrentRow
			.equalsIgnoreCase(valueFilterColumn))
		    filteredValues.add(ob);
	    }
	    return filteredValues;
	} else
	    return null;
    }

    /**
     * Ottiene dal workflow la classa da istanziare per visualizzare il processo
     * 
     * @param comune
     * @param device
     * @throws Exception
     * @throws peopleException
     */
    protected void createView(City comune, Device device) throws Exception,
	    peopleException {

	ConcreteView processView = m_process.getProcessView();
	try {
	    m_viewDelegate = m_process.getProcessView();
	    m_viewDelegate.setPplProcess(this);
	} catch (Exception e) {
	    logger.error(e);
	    throw new Exception(
		    "Cannot create view. Unable to instantiate class "
			    + processView + ". Exception:" + e.getMessage());
	}

	m_viewDelegate.setViewName(processView.getClass().getName());
	m_viewDelegate.initialize(comune, device, m_process);
    }

    /**
     * Ottiene dal workflow la classe da istanziare per stampare il processo
     * 
     * @param comune
     * @throws Exception
     * @throws peopleException
     */
    protected void createPrint(City comune) throws Exception, peopleException {

	Collection typesSupported = MimeType.values();
	if (typesSupported == null || typesSupported.isEmpty())
	    return;

	HashMap map = m_process.getProcessPrint();
	Set s = map.entrySet();
	Iterator it = s.iterator();
	while (it.hasNext()) {

	    Map.Entry entry = (Map.Entry) it.next();
	    String key = (String) entry.getKey();

	    Iterator iter = typesSupported.iterator();
	    while (iter.hasNext()) {
		MimeType type = (MimeType) iter.next();
		if (type.getKey().equalsIgnoreCase(key)) {

		    ConcretePrint print = (ConcretePrint) entry.getValue();
		    print.setPplProcess(this);
		    print.initialize(comune, type);
		    m_printDelegates.put(type, print);
		}
	    }
	}
    }

    /**
     * Ottiene dal workflow la classe da istanziare per firmare il processo
     * 
     * @param comune
     * @param device
     * @param context
     * @throws Exception
     * @throws peopleException
     */
    protected void createSign(City comune, Device device, PeopleContext context)
	    throws Exception, peopleException {

	if (m_process.getProcessSign() != null) {
	    m_signDelegate = m_process.getProcessSign();
	    m_signDelegate.setPplProcess(this);
	    m_signDelegate.setPeopleContext(context);
	}
    }

    /**
     * Crea il modello dei dati.
     * 
     * @param comune
     * @param device
     * @param context
     * @throws Exception
     * @throws peopleException
     */
    protected void createModel(City comune, Device device, PeopleContext context)
	    throws Exception, peopleException {

	String processModel = m_process.getProcessModel();
	m_data = (PplData) Class.forName(processModel).newInstance();
	m_data.initialize(context, this);
	m_data.initializeUser(context.getUser());

	/* Popolo l'identificatore PEOPLE */
	if (m_data instanceof AbstractData) {
	    AbstractData theData = (AbstractData) m_data;

	    /*
	     * FIX 2007-12-19 by CEFRIEL: aggiunte al ProcessData anche le info
	     * riguardo a Richiedente e Titolare in modo che possano essere
	     * recuperate al ripristino della pratica
	     */
	    theData.initializeProfili(context.getSession());
	    /* END FIX 2007-12-19 */

	    // mi assicuro che l'ID people non esista gi�
	    if (theData.getIdentificatorePeople() == null) {
		IdentificatorePeople idPeople = new IdentificatorePeople();

		// verifico che sia valorizzato il prefisso aoo
		if (comune.getAooPrefix() == null
			|| "".equals(comune.getAooPrefix()))
		    throw new Exception(
			    "Impossibile inizializzare la pratica il prefisso di Aoo non pu� essere nullo");

		String id = generaIdentificativoOperazione(comune, context);

		idPeople.setIdentificatoreProcedimento(id);
		theData.setIdentificatorePeople(idPeople);

		IdentificatoreUnivoco idUn = new IdentificatoreUnivoco(id,
			context.getCommune().getOid());
		theData.setIdentificatoreUnivoco(idUn);
	    }
	} else {

	}

	try {
	    setData(m_data);
	} catch (NullPointerException ne) {
	    // se non � definito non faccio nulla
	}
    }

    protected String generaIdentificativoOperazione(City commune,
	    PeopleContext context) {
	// il sirac ritorna il codice fiscale anche per l'utente anonimo
	String codiceFiscale = context.getUser().getUserData()
		.getCodiceFiscale();
	return IDGenerator.generateID(codiceFiscale, commune.getAooPrefix());
	// return IDGenerator.generateID(userPrefix + "-" +
	// commune.getAooPrefix());
    }

    /**
     * Ritorna un nuovo identificativo, � da utilizzare nella costruzione
     * dell'xml inviato ai servizi di back-office di supporto. Prestare
     * attenzione al fatto che all'invocazione del metodo l'id generato �
     * salvato temporaneamente per essere utilizzato alla prima chiamata ad un
     * servizio di back-end attraverso la callService(). Non appena effettuata
     * la chiamata con successo l'id temporaneo � consumato ed � necessario
     * generarne uno nuovo per le successive chiamate. Per la chiamata ai
     * back-end finali per i servizi di visura non dovrebbe essere generato un
     * nuovo identificativo, in questo modo l'id utilizzato � quello della
     * pratica.
     * 
     * @return un nuovo identificativo operazione.
     */
    public String getNewIdentificativoOperazione() {
	this.currentOperationId = generaIdentificativoOperazione(
		this.getCommune(), this.m_context);
	return this.currentOperationId; // generaIdentificativoOperazione(this.getCommune(),
					// this.m_context);
    }

    public static boolean isAnonymousPplProcess(
	    String identificatoreProcedimento) {
	return identificatoreProcedimento.startsWith(ANONYMOUS_ID_PREFIX);
    }

    /**
     * Valida i dati, restituisce un 'ActionErrors' contenete gli eventuali
     * errori.
     * 
     * @param mapping
     * @param request
     * @return
     */
    public ActionErrors validate(ActionMapping mapping,
	    HttpServletRequest request) {

	if (getData() == null)
	    return (ActionErrors) this.errors;

	cleanErrors();

	// E' necessario distinguere tra la validazione relativa all'upload di
	// un
	// file e la validazione vera e propria e relativa alla verifica dei
	// dati
	// inseriti.

	if (SAVE_UPLOAD_ACTION_PATH.equals(mapping.getPath())) {
	    logger.debug("Validazione allegati caricati.");

	    // Verifica che l'eventuale allegato non superi la dimensione
	    // massima consentita
	    // In caso di errore esce dalla validazione senza richiamare quella
	    // degli step
	    Boolean maxLengthExceeded = (Boolean) request
		    .getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);

	    if ((maxLengthExceeded != null)
		    && (maxLengthExceeded.booleanValue())) {
		this.errors.add(MESSAGE_KEY_MAX_LENGTH_EXCEEDED,
			new ActionError(MESSAGE_KEY_MAX_LENGTH_EXCEEDED));
		logger.info("Inserito allegato di dimensioni superiori a quelle previste");
		return (ActionErrors) this.errors;
	    }

	    // Verifica che la dimensione complessiva degli allegati non superi
	    // il limite
	    // massimo di memoria previsto nella propriet� di configurazione
	    if (this.m_data instanceof AbstractData) {

		int uploadedFileSize = getUploadedFileSize();

		if (uploadedFileSize > 0) {

		    AbstractData data = (AbstractData) this.m_data;
		    List allegati = data.getAllegati();

		    int totalLength = uploadedFileSize; // dimensione
							// dell'ultimo allegato
							// caricato
		    for (Iterator iter = allegati.iterator(); iter.hasNext();) {
			Attachment allegato = (Attachment) iter.next();
			totalLength += allegato.getFileLenght();
		    }

		    // Lunghezza massima totale espressa in KB
		    int maxLength = ((AbstractData) this.m_data)
			    .getAttachmentsMaximunSize();
		    if (maxLength != AbstractData.UNLIMITED_ATTACHMENTS_MAX_TOTAL_SIZE) {
			if (maxLength == AbstractData.PEOPLE_ATTACHMENTS_MAX_TOTAL_SIZE) {
			    try {
				maxLength = Integer
					.parseInt(PeopleProperties.ATTACHMENT_MAX_TOTAL_SIZE
						.getValueString(this.m_commune
							.getOid())) * 1024;
			    } catch (Exception ex) {
				logger.warn("Impossibile determinare il parametro che indica la dimensione massima totale per l'elenco degli allegati di una pratica");
			    }
			}
			// try {
			// maxLength =
			// Integer.parseInt(PeopleProperties.ATTACHMENT_MAX_TOTAL_SIZE.getValueString(this.m_commune.getOid()))
			// * 1024;
			// } catch(Exception ex) {
			// logger.warn("Impossibile determinare il parametro che indica la dimensione massima totale per l'elenco degli allegati di una pratica");
			// }

			// N.B. L'inserimento dell'allegato avviene solo se �
			// superata la validazione
			if (maxLength > 0 && totalLength > maxLength) {
			    logger.info("La dimensione massima degli allegati inseriti supera il limite previsto");
			    this.errors
				    .add(MESSAGE_KEY_MAX_TOTAL_LENGTH_EXCEEDED,
					    new ActionError(
						    MESSAGE_KEY_MAX_TOTAL_LENGTH_EXCEEDED));
			}
		    }
		}
	    }

	} else {
	    logger.debug("Validazione dello Step");

	    // notifica della proprieta' modificate
	    getView().propertyUpdate(
		    RequestUtils.getChangedProperties(this, request, "data"));

	    // Validazione dei contenuti
	    try {
		getView().getCurrentActivity().validate(this,
			getServlet().getServletContext(), request, errors,
			mapping.getPath());
	    } catch (ParserException pEx) {
		logger.error(pEx);
		this.errors.add(pEx.getPropertyName(),
			new ActionError(pEx.getPropertyName()));
	    }
	}

	logger.debug("Data.validate errors" + errors.getSize());
	return (ActionErrors) this.errors;
    }

    public String getProcessName() {
	return m_processName;
    }

    public void setProcessName(String name) {
	m_processName = name;
    }

    public String getStatus() {
	return m_status;
    }

    public void setStatus(String p_status) {
	m_status = p_status;
    }

    public Long getOid() {
	return m_oid;
    }

    public void setOid(Long p_oid) {
	m_oid = p_oid;
    }

    public Boolean getSent() {
	return m_sent;
    }

    public void setSent(Boolean p_sent) {
	m_sent = p_sent;
    }

    public final ConcreteView getView() {
	return m_viewDelegate;
    }

    public final ConcreteSign getSign() {
	return m_signDelegate;
    }

    public final ConcretePrint[] getPrint() {
	return (ConcretePrint[]) m_printDelegates.values().toArray(
		new ConcretePrint[0]);
    }

    public final ConcretePrint getPrint(MimeType type) {
	return (ConcretePrint) m_printDelegates.get(type);
    }

    public final PplObserver getObserver() {
	return m_observer;
    }

    public PplData getData() {
	PplData tempData = null;

	try {
	    tempData = (PplData) get("data");
	} catch (Exception e) {
	}

	if (tempData != null)
	    return tempData;

	return m_data;
    }

    public String getIdentificatoreProcedimento() {
	return ((AbstractData) this.getData()).getIdentificatorePeople()
		.getIdentificatoreProcedimento();
    }

    public void setData(PplData data) {
	m_data = data;
	try {
	    set("data", data);
	} catch (Exception e) {
	}
    }

    public final String getMarshalledData() {
	// return m_data.marshall();
	return ((AbstractData) getData()).marshall();
    }

    public final void setMarshalledData(String xmlMarshData) {
	// m_data = (PplData) m_data.unmarshall(xmlMarshData);

	if (xmlMarshData != null && getData() != null) {
	    setData((PplData) getData().unmarshall(xmlMarshData));
	}
    }

    public final boolean isInizialized() {
	return m_initialized;
    }

    public City getCommune() {
	return m_commune;
    }

    public void setCommune(City p_commune) {
	m_commune = p_commune;
    }

    public Timestamp getLastModifiedTime() {
	return m_lastModifiedTime;
    }

    public void setLastModifiedTime(Timestamp p_lastModifiedTime) {
	m_lastModifiedTime = p_lastModifiedTime;
    }

    public Timestamp getCreationTime() {
	return m_creationTime;
    }

    public void setCreationTime(Timestamp p_creationTime) {
	m_creationTime = p_creationTime;
    }

    // currentCategory
    public void setCurrentCategory(String p_currentCategory) {
	this.m_currentCategory = p_currentCategory;
    }

    public String getCurrentCategory() {
	return this.m_currentCategory;
    }

    // currentContent
    public String getCurrentContent() {
	return m_currentContent;
    }

    public void setCurrentContent(String p_currentContent) {
	this.m_currentContent = p_currentContent;
    }

    // currentContentID
    public String getCurrentContentID() {
	return m_currentContentID;
    }

    public void setCurrentContentID(String p_currentContentID) {
	this.m_currentContentID = p_currentContentID;
    }

    // il proprietario del processo
    public String getOwner() {
	return m_owner;
    }

    public void setOwner(String m_owner) {
	this.m_owner = m_owner;
    }

    // Delegate
    public PplDelegate[] getDelegate() {
	Iterator it = m_delegates.keySet().iterator();
	Vector vet = new Vector();
	while (it.hasNext())
	    vet.add(m_delegates.get(it.next()));
	return (PplDelegate[]) vet.toArray(new PplDelegate[vet.size()]);
    }

    public PplDelegate getDelegate(String delegateID) {
	return (PplDelegate) m_delegates.get(delegateID);
    }

    public void setDelegate(PplDelegate[] delegates) {
	m_delegates.clear();
	for (int i = 0; i < delegates.length; i++)
	    addDelegate(delegates[i]);
    }

    public void addDelegate(PplDelegate delegate) {
	m_delegates.put(delegate.getUserID(), delegate);
    }

    public void removeDelegate(PplDelegate delegate) {
	m_delegates.remove(delegate.getUserID());
    }

    // Principal
    public PplPrincipal[] getPrincipal() {
	return (PplPrincipal[]) m_principals.keySet().toArray(
		new PplPrincipal[0]);
    }

    public void setPrincipal(PplPrincipal princ) {
	addPrincipal(princ);
    }

    public void setPrincipal(PplPrincipal[] princs) {
	m_principals.clear();
	for (int i = 0; i < princs.length; i++)
	    addPrincipal(princs[i]);
    }

    public void addPrincipal(PplPrincipal principal) {
	m_principals.put(principal, principal.getRole());
    }

    public void removePrincipal(PplPrincipal principal) {
	m_principals.remove(principal);
    }

    public PplPrincipal[] findPrincipal(PplUser user) {
	ArrayList result = new ArrayList();
	if (user != null) {
	    Iterator iter = m_principals.keySet().iterator();
	    while (iter.hasNext()) {
		PplPrincipal princ = (PplPrincipal) iter.next();
		if (user.getUserID().equals(princ.getUserID()))
		    result.add(princ);
	    }
	}
	return (PplPrincipal[]) result.toArray(new PplPrincipal[0]);
    }

    public PplPrincipal[] findPrincipalWithDelegate(PplUser user) {
	ArrayList result = new ArrayList();
	if (user != null) {
	    Iterator iter = m_principals.keySet().iterator();
	    while (iter.hasNext()) {
		PplPrincipal princ = (PplPrincipal) iter.next();
		if (user.getUserID().equals(princ.getUserID()))
		    result.add(princ);
	    }

	    // crea principal anche per i delegati definiti
	    PplDelegate delegate = (PplDelegate) m_delegates.get(user
		    .getUserID());
	    if (delegate != null) {
		iter = m_principals.keySet().iterator();
		while (iter.hasNext()) {
		    PplPrincipal princ = (PplPrincipal) iter.next();
		    if (delegate.getOwner().getUserID()
			    .equals(princ.getUserID()))
			result.add(new PplPrincipal(delegate.getUserID(), princ
				.getRole()));
		}
	    }

	}
	return (PplPrincipal[]) result.toArray(new PplPrincipal[0]);
    }

    /**
     * @deprecated
     * @param user
     * @return
     */
    public PplRole[] findRole(PplUser user) {
	ArrayList result = new ArrayList();
	if (user != null) {
	    Iterator iter = m_principals.keySet().iterator();
	    while (iter.hasNext()) {
		PplPrincipal princ = (PplPrincipal) iter.next();
		if (user.getUserID().equals(princ.getUserID()))
		    result.add(princ.getRole());
	    }
	}
	return (PplRole[]) result.toArray(new PplRole[0]);
    }

    /**
     * @deprecated
     * @param role
     * @return
     */
    public PplPrincipal[] findPrincipal(PplRole role) {
	ArrayList result = new ArrayList();
	if (role != null) {
	    Iterator iter = m_principals.keySet().iterator();
	    while (iter.hasNext()) {
		PplPrincipal princ = (PplPrincipal) iter.next();
		if (role.equals(princ.getRole()))
		    result.add(princ);
	    }
	}
	return (PplPrincipal[]) result.toArray(new PplPrincipal[0]);
    }

    public void setProcessSendACE(PplACE[] p_sendACEs) {
	m_sendACEs = p_sendACEs;
    }

    public PplACE[] getProcessSendACEs() {
	return m_sendACEs;
    }

    /**
     * Restituisce l'implementazione della pipeline.
     */
    protected static Class getPipelineImpl() {
	return null;
    }

    /**
     * Restituisce gli Hendler associati al processo.
     */
    protected static PipelineHandler[] getPipelineHandlers() {
	return null;
    }

    /**
     * @return Returns the context.
     */
    public PeopleContext getContext() {
	return m_context;
    }

    /**
     * Chiama un servizio di back-end
     * 
     * @deprecated Il metodo non va utilizzato, utilizzare al suo posto la
     *             callService che accetta l'xml sottoforma di stringa
     * @param serviceName
     * @param inParameter
     * @return
     * @throws ServiceException
     * @throws SendException
     */

    public String callService(String serviceName, XmlObject parameter)
	    throws ServiceException, SendException {
	return this.callService(serviceName,
		XmlObjectWrapper.generateXml(parameter));
    }

    /**
     * Chiama un servizio di back-end.
     * 
     * @param serviceName
     * @param inParameter
     * @return
     * @throws ServiceException
     * @throws SendException
     */

    public String callService(String serviceName, String parameter)
	    throws ServiceException, SendException {
	IDataAccess da = DAOFactory.getInstance().create(serviceName,
		getCommune().getOid());

	try {
	    // intercetto il tracciato per il back-end e lo imbusto

	    IRequestEnvelope envelopeBean = EnvelopeHelper
		    .createEnvelopeFromPplProcessAndSession(this, getContext()
			    .getSession());

	    envelopeBean.setContenuto(new ContenutoBusta(parameter));
	    envelopeBean.setNomeServizio("unknown ("
		    + envelopeBean.getContestoServizio() + ")");

	    IEnvelopeFactory envelopeFactory = new EnvelopeFactory_modellazioneb116_envelopeb002();

	    String envelopeXML = envelopeFactory
		    .createEnvelopeXmlText(envelopeBean);

	    String xmlResponse = da.call(envelopeXML, getCommune().getOid(),
		    getContext().getUser().getUserID(), getProcessName(),
		    getOid());

	    return xmlResponse;
	} catch (SendException sendEx) {
	    // Rimuove dal messaggio di errore l'intestazione
	    // 'java.rmi.RemoteException: '
	    // String message =
	    // sendEx.getMessage().replaceFirst("java.rmi.RemoteException: ",
	    // "");
	    // this.addServiceError(message);
	    showBackEndError(serviceName, sendEx);
	    throw sendEx;
	} catch (ServiceException serviceEx) {
	    // this.addServiceError(serviceEx.getMessage());
	    showBackEndError(serviceName, serviceEx);
	    throw serviceEx;
	} catch (Exception ex) {
	    // this.addServiceError(ex.getMessage());
	    showBackEndError(serviceName, ex);
	    throw new ServiceException(ex.getMessage());
	} finally {
	    // l'identificativo generato � sempre rimosso anche in caso di
	    // errore, � quindi necessario rigenerarlo prima di ogni chiamata
	    this.currentOperationId = null;
	}
    }

    /**
     * Mostra il messaggio di errore di collegamento con il web-service di
     * back-end nello step solo se previsto dalla configurazione del servizio
     * stesso. L'errore specifico � registrato nel log.
     * 
     * @param message
     */
    protected void showBackEndError(String beServiceName, Throwable ex) {
	logger.error("Errore nel collegamento al back-end."
		+ " Servizio di front-end: " + this.getProcessName() + "."
		+ " Servizio di back-end: " + beServiceName, ex);

	if (this.getView().isShowBackEndError()) {
	    Object args[] = {};
	    String message = MessageBundleHelper.message(
		    "errors.CallServiceError", args, this.getProcessName(),
		    this.getCommune().getKey(), this.getContext().getLocale());
	    this.addServiceError(message);
	}
    }

    /**
     * Pulisce i messaggi di errori
     * 
     */
    public void cleanErrors() {
	this.errors = new ValidationErrors();
	this.serviceErrors.clear();
    }

    public void setErrors(IValidationErrors errors) {
	this.errors = errors;
    }

    /**
     * @return Returns the requiredAttachments.
     */
    public RequiredAttachmentList getRequiredAttachments() {
	return requiredAttachments;
    }

    public int getLastActivityIdx() {
	return lastActivityIdx;
    }

    public void setLastActivityIdx(int lastActivityIdx) {
	this.lastActivityIdx = lastActivityIdx;
    }

    public int getLastStepIdx() {
	return lastStepIdx;
    }

    public void setLastStepIdx(int lastStepIdx) {
	this.lastStepIdx = lastStepIdx;
    }

    /**
     * @return the lastActivityId
     */
    public final String getLastActivityId() {
	return this.lastActivityId;
    }

    /**
     * @param lastActivityId
     *            the lastActivityId to set
     */
    private final void setLastActivityId(String lastActivityId) {
	this.lastActivityId = lastActivityId;
    }

    /**
     * @return the lastStepId
     */
    public final String getLastStepId() {
	return this.lastStepId;
    }

    /**
     * @param lastStepId
     *            the lastStepId to set
     */
    private final void setLastStepId(String lastStepId) {
	this.lastStepId = lastStepId;
    }

    public boolean isReceiptMailAttachment() {
	return ((AbstractData) this.m_data).isReceiptMailAttachment();
    }

    public void setReceiptMailAttachment(boolean receiptMailAttachment) {
	((AbstractData) this.m_data)
		.setReceiptMailAttachment(receiptMailAttachment);
    }

    public boolean isSendMailToOwner() {
	return ((AbstractData) this.m_data).isSendMailToOwner();
    }

    public void setSendMailToOwner(boolean sendMailToOwner) {
	((AbstractData) this.m_data).setSendMailToOwner(sendMailToOwner);
    }

    public boolean isEmbedAttachmentInXml() {
	return ((AbstractData) this.m_data).isEmbedAttachmentInXml();
    }

    public void setEmbedAttachmentInXml(boolean embedAttachmentInXml) {
	((AbstractData) this.m_data)
		.setEmbedAttachmentInXml(embedAttachmentInXml);
    }

    public boolean isShowPrivacyDisclaimer() {
	return ((AbstractData) this.m_data).isShowPrivacyDisclaimer();
    }

    public void setShowPrivacyDisclaimer(boolean showPrivacyDisclaimer) {
	((AbstractData) this.m_data)
		.setShowPrivacyDisclaimer(showPrivacyDisclaimer);
    }

    public boolean isPrivacyDisclaimerRequireAcceptance() {
	return ((AbstractData) this.m_data)
		.isPrivacyDisclaimerRequireAcceptance();
    }

    public void setPrivacyDisclaimerRequireAcceptance(
	    boolean privacyDisclaimerRequireAcceptance) {
	((AbstractData) this.m_data)
		.setPrivacyDisclaimerRequireAcceptance(privacyDisclaimerRequireAcceptance);
    }

    public boolean isOnLineSign() {
	return ((AbstractData) this.m_data).isOnLineSign();
    }

    public void setOnLineSign(boolean onLineSign) {
	((AbstractData) this.m_data).setOnLineSign(onLineSign);
    }

    public boolean isOffLineSign() {
	return ((AbstractData) this.m_data).isOffLineSign();
    }

    public void setOffLineSign(boolean offLineSign) {
	((AbstractData) this.m_data).setOffLineSign(offLineSign);
    }

    public String getOffLineSignDownloadedDocumentHash() {
	return this.getOffLineSignProcess()
		.getOffLineSignDownloadedDocumentHash();
    }

    public void setOffLineSignDownloadedDocumentHash(
	    String offLineSignDownloadedDocumentHash) {
	this.getOffLineSignProcess().setOffLineSignDownloadedDocumentHash(
		offLineSignDownloadedDocumentHash);
    }

    public boolean isWaitingForOffLineSignedDocument() {
	return this.getOffLineSignProcess().isWaitingForOffLineSignedDocument();
    }

    public void setWaitingForOffLineSignedDocument(
	    boolean waitingForOffLineSignedDocument) {
	this.getOffLineSignProcess().setWaitingForOffLineSignedDocument(
		waitingForOffLineSignedDocument);
    }

    public final String getOffLineSignedData() {
	return this.getOffLineSignProcess().getOffLineSignedData();
    }

    public final String getOffLineSignUserID() {
	return this.getOffLineSignProcess().getUserID();
    }

    public final long getOffLineSignPolicyID() {
	return this.getOffLineSignProcess().getPolicyID();
    }

    public final void setOffLineSignedData(final String offLineSignedData,
	    final long policyID, final String userID, final String hash) {
	this.getOffLineSignProcess().setOffLineSignedData(offLineSignedData,
		policyID, userID, hash);
    }

    /**
     * @return
     */
    public final Vector<String> getEnabledAuditProcessors() {
	if (this.m_data != null) {
	    return ((AbstractData) this.m_data).getEnabledAuditProcessors();
	} else {
	    return new Vector<String>();
	}
    }

    /**
     * @param enabledAuditProcessors
     */
    public final void setEnabledAuditProcessors(
	    Vector<String> enabledAuditProcessors) {
	if (this.m_data != null) {
	    ((AbstractData) this.m_data)
		    .setEnabledAuditProcessors(enabledAuditProcessors);
	}
    }

    /**
     * @param auditProcessor
     * @return
     */
    public final boolean isAuditProcessorEnabled(final String auditProcessor) {
	if (this.m_data != null) {
	    return ((AbstractData) this.m_data)
		    .isAuditProcessorEnabled(auditProcessor);
	} else {
	    return false;
	}
    }

    /**
     * Indica se la firma del riepilogo � abilitata o meno
     * 
     * @return true se abilitata, false altrimenti
     */
    public boolean isSignEnabled() {
	SummaryActivity summaryActivity = this.getView().findSummaryActivity();
	// verifica sia l'abilitazione della firma,
	// sia l'effettiva presenza della ConcreteSign
	if (this.getStatus().equalsIgnoreCase("S_SIGN_CUSTOM_SUMMARY_ACTIVITY")) {
	    return this.getSign() != null;
	} else {
	    if (summaryActivity != null && this.getSign() != null)
		return summaryActivity.isSignEnabled();
	}

	return false;
    }

    /**
     * Permette a runtime di abilitare e disabilitare la firma del riepilogo,
     * solo se questa � stata impostata come abilitata a livello di
     * workflow.xml. Se la firma non � stata abiltata a livello di workflow,
     * l'impostazione � ininfluente.
     */
    public void setSignEnabled(boolean signEnabled) {
	// Verifica l'esistenza della ConcreteSign
	if (this.getSign() != null) {
	    SummaryActivity summaryActivity = this.getView()
		    .findSummaryActivity();
	    if (summaryActivity != null) {
		summaryActivity.setSignEnabled(signEnabled);
	    }
	} else {
	    logger.info("Tentativo di abilitazione della firma da parte del servizio non effettuata perch� la configurazione del workflow.xml non lo consente");
	}
    }

    /**
     * Ritorna lo stato del riepilogo
     * 
     * @return stato del riepilogo
     */
    public SummaryState getSummaryState() {
	SummaryActivity summary = getView().findSummaryActivity();
	if (summary == null)
	    return SummaryState.NONE;
	else
	    return summary.getSummaryState();
    }

    /**
     * Permette di impostare lo stato del riepilogo.
     * 
     * @param state
     *            stato del riepilogo
     */
    public void setSummaryState(SummaryState state) {
	IView view = getProcessWorkflow().getView();
	ArrayList activities = view.getActivities();
	SummaryActivity summaryActivity = getView().findSummaryActivity();
	if (summaryActivity != null) {
	    if (state.equals(SummaryState.NONE)) {
		// Rimuove il riepilogo
		activities.remove(summaryActivity);
	    } else
		summaryActivity.setSummaryState(state);
	} else {
	    // e' necessario creare il summary activity
	    summaryActivity = SummaryActivity.create(getSign() != null, state,
		    view);
	    if (summaryActivity != null)
		activities.add(summaryActivity);
	}
    }

    // Metodi di log
    private it.people.logger.Logger serviceLogger = null;

    protected it.people.logger.Logger getLogger() {
	return it.people.logger.Logger.getLogger(this.getProcessName(),
		this.getCommune());
    }

    /**
     * Registra un messaggio di log al livello debug
     * 
     * @param message
     *            messaggio da registrare
     */
    public void debug(String message) {
	if (this.serviceLogger == null)
	    this.serviceLogger = getLogger();

	this.serviceLogger.debug(message);
    }

    /**
     * Registra un messaggio di log al livello info
     * 
     * @param message
     *            messaggio da registrare
     */
    public void info(String message) {
	if (this.serviceLogger == null)
	    this.serviceLogger = getLogger();

	this.serviceLogger.info(message);
    }

    /**
     * Registra un messaggio di log al livello warning
     * 
     * @param message
     *            messaggio da registrare
     */
    public void warn(String message) {
	if (this.serviceLogger == null)
	    this.serviceLogger = getLogger();

	this.serviceLogger.warn(message);
    }

    /**
     * Registra un messaggio di log al livello error
     * 
     * @param message
     *            messaggio da registrare
     */
    public void error(String message) {
	if (this.serviceLogger == null)
	    this.serviceLogger = getLogger();

	this.serviceLogger.error(message);
    }

    /**
     * Registra un messaggio di log al livello fatal
     * 
     * @param message
     *            messaggio da registrare
     */
    public void fatal(String message) {
	if (this.serviceLogger == null)
	    this.serviceLogger = getLogger();

	this.serviceLogger.fatal(message);
    }

    /**
     * Invia un email al cittadino utilizzando le impostazioni correnti del
     * framework
     * 
     * @param subject
     *            oggetto del messaggio
     * @param body
     *            contenuto del messaggio
     * @throws AddressException
     * @throws MessagingException
     */
    public void sendMail(String subject, String body) throws AddressException,
	    MessagingException {
	PplUser user = this.getContext().getUser();

	// verifica che l'indirizzo di email sia valorizzato
	if (user.isAnonymous())
	    throw new AddressException(
		    "L'indirizzo di e-mail per gli utenti anonimi deve essere indicato esplicitamente.");

	sendMail(user.getEMail(), subject, body);
    }

    /**
     * Invia un email utilizzando le impostazioni correnti del framework
     * 
     * @param address
     *            indirizzo del destinatario
     * @param subject
     *            oggetto del messaggio
     * @param body
     *            contenuto del messaggio
     * @throws AddressException
     * @throws MessagingException
     */
    public void sendMail(String address, String subject, String body)
	    throws AddressException, MessagingException {
	sendMail(address, subject, body, this.getCommune().getKey());
    }

    /**
     * Invia un email utilizzando le impostazioni dell'ente indicato
     * 
     * @param address
     *            indirizzo del destinatario
     * @param subject
     *            oggetto del messaggio
     * @param body
     *            contenuto del messaggio
     * @param communeId
     *            codice istat del comune del quale utilizzare le impostazioni
     * @throws AddressException
     * @throws MessagingException
     */
    public void sendMail(String address, String subject, String body,
	    String communeId) throws AddressException, MessagingException {
	EMailSender sender = new EMailSender(communeId);
	sender.sendMail(address, subject, body);
    }

    protected int getUploadedFileSize() {
	try {
	    FormFile ff = (FormFile) PropertyUtils.getProperty(this.getData(),
		    SaveUploadFile.PARAMETER_UPLOADFILE);
	    if (ff != null)
		return ff.getFileSize();
	} catch (Exception ex) {
	    logger.error(ex);
	}
	return 0;
    }

    /**
     * @return the offLineSignProcess
     */
    public final OffLineSignProcess getOffLineSignProcess() {
	return this.offLineSignProcess;
    }

    /**
     * @param offLineSignProcess
     *            the offLineSignProcess to set
     */
    public final void setOffLineSignProcess(
	    OffLineSignProcess offLineSignProcess) {
	this.offLineSignProcess = offLineSignProcess;
    }

    public final void updateLastCoords() {

	if (logger.isDebugEnabled()) {
	    logger.debug("Retrieving view last coords...");
	}

	IActivity lastActivity = null;
	IStep lastActivityLastStep = null;

	if (this.getProcessWorkflow() != null
		&& this.getProcessWorkflow().getView() != null) {
	    String[] activitiesOrder = this.getProcessWorkflow().getView()
		    .getActivityOrder();
	    if (logger.isDebugEnabled()) {
		logger.debug("Found activities order string array: "
			+ activitiesOrder);
	    }
	    if (activitiesOrder != null && activitiesOrder.length > 0) {
		lastActivity = findLastActivityId(activitiesOrder); // this.getProcessWorkflow().getView().getActivityById(lastActivityId);
		if (lastActivity != null) {
		    if (logger.isDebugEnabled()) {
			logger.debug("Last activity id: "
				+ lastActivity.getId());
		    }
		    String[] lastActivityStepsOrder = lastActivity
			    .getStepOrder();
		    if (logger.isDebugEnabled()) {
			logger.debug("Found activity steps order string array: "
				+ lastActivityStepsOrder);
		    }
		    if (lastActivityStepsOrder != null
			    && lastActivityStepsOrder.length > 0) {
			lastActivityLastStep = findLastActivityLastStep(
				lastActivity, lastActivityStepsOrder);
			if (logger.isDebugEnabled()) {
			    logger.debug("Last activity step id: "
				    + lastActivityLastStep.getId());
			}
		    }
		}
	    }
	}

	if (lastActivity != null && lastActivityLastStep != null) {
	    this.setLastActivityId(lastActivity.getId());
	    this.setLastStepId(lastActivityLastStep.getId());
	    if (logger.isDebugEnabled()) {
		logger.debug("Setted last activity and step id.");
	    }
	} else {
	    this.setLastActivityId(null);
	    this.setLastStepId(null);
	}

    }

    private IActivity findLastActivityId(String[] activitiesOrder) {

	IActivity result = null;

	for (int index = (activitiesOrder.length - 1); index >= 0; index--) {
	    IActivity activity = this.getProcessWorkflow().getView()
		    .getActivityById(activitiesOrder[index]);
	    if (activity != null
		    && activity.getState().equals(ActivityState.ACTIVE)) {
		result = activity;
		break;
	    }
	}

	return result;

    }

    private IStep findLastActivityLastStep(IActivity lastActivity,
	    String[] lastActivityStepsOrder) {

	IStep result = null;

	for (int index = (lastActivityStepsOrder.length - 1); index >= 0; index--) {
	    IStep step = lastActivity
		    .getStepById(lastActivityStepsOrder[index]);
	    if (step != null && step.getState().equals(StepState.ACTIVE)) {
		result = step;
		break;
	    }
	}

	return result;

    }

    public final boolean isLastActivityLastStep() {

	if (this.getLastActivityId() != null && this.getLastStepId() != null) {
	    return this.getView().getCurrentActivity().getId()
		    .equalsIgnoreCase(this.getLastActivityId())
		    && this.getView().getCurrentActivity().getCurrentStep()
			    .getId().equalsIgnoreCase(this.getLastStepId());
	} else {
	    return false;
	}

    }

    /**
     * @return the paymentStatus
     */
    public final PaymentStatusEnum getPaymentStatus() {
	return this.paymentStatus;
    }

    /**
     * @param paymentStatus
     *            the paymentStatus to set
     */
    public final void setPaymentStatus(PaymentStatusEnum paymentStatus) {
	this.paymentStatus = paymentStatus;
    }

    /**
     * @return the resendProcess
     */
    public final boolean isResendProcess() {
	return this.resendProcess;
    }

    /**
     * @param resendProcess
     *            the resendProcess to set
     */
    public final void setResendProcess(boolean resendProcess) {
	this.resendProcess = resendProcess;
    }

    /**
     * @return the userPanel
     */
    public final IUserPanel getUserPanel() {
	return this.userPanel;
    }

    /**
     * @return the serviceConf
     */
    public final ServiceConf getServiceConf() {
	return serviceConf;
    }

    /**
     * @param service
     */
    public final void setServiceConf(Service service) {
	this.serviceConf = new ServiceConf(service);
    }

}
