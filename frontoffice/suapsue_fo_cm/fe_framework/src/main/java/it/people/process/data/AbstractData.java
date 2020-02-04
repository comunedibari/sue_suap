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
package it.people.process.data;

import it.people.core.PeopleContext;
import it.people.core.PplUser;
import it.people.core.PplUserData;
import it.people.fsl.servizi.oggetticondivisi.IdentificatorePeople;
import it.people.fsl.servizi.oggetticondivisi.IdentificatorediProtocollo;
import it.people.fsl.servizi.oggetticondivisi.PersonaFisica;
import it.people.fsl.servizi.oggetticondivisi.Richiedente;
import it.people.fsl.servizi.oggetticondivisi.Richiesta;
import it.people.fsl.servizi.oggetticondivisi.Titolare;
import it.people.fsl.servizi.oggetticondivisi.UtenteAutenticato;
import it.people.fsl.servizi.oggetticondivisi.luogo.Indirizzo;
import it.people.fsl.servizi.oggetticondivisi.luogo.Luogo;
import it.people.fsl.servizi.oggetticondivisi.luogo.Stato;
import it.people.fsl.servizi.oggetticondivisi.profili.AccreditamentoCorrente;
import it.people.fsl.servizi.oggetticondivisi.profili.ProfiloPersonaFisica;
import it.people.fsl.servizi.oggetticondivisi.profili.ProfiloTitolare;
import it.people.fsl.servizi.oggetticondivisi.profili.Session2AbstractDataProfileHelper;
import it.people.fsl.servizi.oggetticondivisi.tipibase.Data;
import it.people.parser.FieldValidator;
import it.people.parser.OptionalAttribute;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.process.PplData;
import it.people.process.SummaryState;
import it.people.process.common.entity.AbstractEntity;
import it.people.process.common.entity.Attachment;
import it.people.process.sign.entity.OffLineSignProcess;
import it.people.process.sign.entity.SignedData;
import it.people.util.IdentificatoreUnivoco;
import it.people.util.PeopleProperties;
import it.people.util.SignedAttachmentUtils;
import it.people.vsl.PipelineData;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Category;
import org.apache.struts.upload.FormFile;

/**
 * 
 * User: sergio Date: Sep 22, 2003 Time: 7:08:27 PM <br>
 * <br>
 * Questa classe definisce le caratteristiche che devono avere le classi che
 * rappresentano i dati inseriti dagli utenti.
 * 
 */
public abstract class AbstractData extends AbstractEntity implements PplData,
	Richiesta {

    // Log
    private transient Category cat = Category.getInstance(AbstractData.class
	    .getName());

    public static final int PEOPLE_ATTACHMENTS_MAX_TOTAL_SIZE = -1;
    public static final int UNLIMITED_ATTACHMENTS_MAX_TOTAL_SIZE = 0;

    protected String oggettoPratica;
    protected String descrizionePratica;
    protected ArrayList altriDichiaranti;

    protected Class m_clazz = null;
    protected HashMap m_validators = new HashMap();
    protected String errorMessage = null;
    protected boolean hasError = false;

    private boolean presentiAllegati;
    /**
     * Elenco file allegati al procedimento
     */
    private List allegati = null;

    /**
     * Elenco documenti firmati del procedimento
     * 
     * @deprecated tutti gli allegati firmati e non sono salvati nella
     *             propriet� Allegati
     */
    private List documentiFirmati = null;

    /**
     * titolare del procedimento
     */
    private Titolare titolare = null;

    /**
     * Richiedente del procedimento
     */
    private Richiedente richiedente = null;

    private IdentificatorePeople identificatorePeople = null;

    private IdentificatoreUnivoco identificatoreUnivoco = null;

    private IdentificatorediProtocollo identificatoreDiProtocollo = null;

    /* FIX 2007-12-19 by CEFRIEL - Aggiunti attributi per salvataggio profili */
    private ProfiloPersonaFisica profiloOperatore = null;
    private ProfiloPersonaFisica profiloRichiedente = null;
    private ProfiloTitolare profiloTitolare = null;
    private AccreditamentoCorrente accreditamentoCorrente = null;
    private String domicilioElettronico = null;
    /* END FIX 2007-12-19 */

    private int attachmentsMaximunSize = 0;

    private int attachmentsRemainingSize = 0;

    private String pdfGeneratorError;

    private FormFile signedDocumentUploadFile;

    /**
     * Codice Istat dell'ente. Valorizzata in particolare con l'ente A&C per le
     * statistiche dell'osservatorio (tabella people.detail_process)
     */
    private String istatEnte;

    /**
     * Nome dell'ente. Valorizzata in particolare con l'ente A&C per le
     * statistiche dell'osservatorio (tabella people.detail_process)
     */
    private String nomeEnte;

    /**
     * Costruttore
     */
    public AbstractData() {
	doDefineValidators();
	allegati = new ArrayList();
	documentiFirmati = new ArrayList();
	presentiAllegati = false;
	this.setAttachmentsMaximunSize(PEOPLE_ATTACHMENTS_MAX_TOTAL_SIZE);
    }

    public void initializeUser(PplUser user) {
	titolare = new Titolare();

	richiedente = new Richiedente();
	titolare.setPersonaFisica(new PersonaFisica());
	richiedente.setUtenteAutenticato(new UtenteAutenticato());

	valorizzaPersonaFisica(titolare.getPersonaFisica(), user.getUserData());
	valorizzaPersonaFisica(richiedente.getUtenteAutenticato(),
		user.getUserData());
    }

    /*
     * FIX 2007-12-19 by CEFRIEL: aggiunto metodo per inizializzare Richiedente,
     * Titolare e domicilio elettronico all'interno del bean. Queste info sono
     * state aggiunte per poterle salvare insieme alla pratica e riuscire in
     * seguito a ripristinarle
     */
    public void initializeProfili(HttpSession session) throws Exception {
	profiloOperatore = Session2AbstractDataProfileHelper
		.getProfiloOperatoreFromSession(session);
	profiloRichiedente = Session2AbstractDataProfileHelper
		.getProfiloRichiedenteFromSession(session);
	profiloTitolare = Session2AbstractDataProfileHelper
		.getProfiloTitolareFromSession(session);
	accreditamentoCorrente = Session2AbstractDataProfileHelper
		.getAccreditamentoCorrenteFromSession(session);
	domicilioElettronico = Session2AbstractDataProfileHelper
		.getDomicilioElettronicoFromSession(session);
    }

    /**
     * @return the profiloOperatore
     */
    public final ProfiloPersonaFisica getProfiloOperatore() {
	return this.profiloOperatore;
    }

    /**
     * @param profiloOperatore
     *            the profiloOperatore to set
     */
    public final void setProfiloOperatore(ProfiloPersonaFisica profiloOperatore) {
	this.profiloOperatore = profiloOperatore;
    }

    public ProfiloPersonaFisica getProfiloRichiedente() {
	return profiloRichiedente;
    }

    public void setProfiloRichiedente(ProfiloPersonaFisica profiloRichiedente) {
	this.profiloRichiedente = profiloRichiedente;
    }

    public ProfiloTitolare getProfiloTitolare() {
	return profiloTitolare;
    }

    public void setProfiloTitolare(ProfiloTitolare profiloTitolare) {
	this.profiloTitolare = profiloTitolare;
    }

    /**
     * @return the accreditamentoCorrente
     */
    public final AccreditamentoCorrente getAccreditamentoCorrente() {
	return this.accreditamentoCorrente;
    }

    /**
     * @param accreditamentoCorrente
     *            the accreditamentoCorrente to set
     */
    public final void setAccreditamentoCorrente(
	    AccreditamentoCorrente accreditamentoCorrente) {
	this.accreditamentoCorrente = accreditamentoCorrente;
    }

    public String getDomicilioElettronico() {
	return domicilioElettronico;
    }

    public void setDomicilioElettronico(String domicilioElettronico) {
	this.domicilioElettronico = domicilioElettronico;
    }

    /* END FIX 2007-12-29 */

    private void valorizzaPersonaFisica(PersonaFisica persona, PplUserData data) {
	persona.setNome(data.getNome());
	persona.setCognome(data.getCognome());
	try {
	    if (data.getDataNascita() != null
		    && !"".equals(data.getDataNascita()))
		persona.setDatadiNascita(new Data(data.getDataNascita()));
	} catch (ParseException e) {
	    cat.error("ParseException.Data di Nascita Utente", e);
	}

	persona.setCodiceFiscale(data.getCodiceFiscale());
	persona.setSesso(data.getSesso());
	List titolo = new ArrayList();
	titolo.add(data.getTitolo());
	persona.setTitoloPersonale(titolo);

	List recapito = new ArrayList();
	recapito.add(data.getEmailaddress());
	recapito.add(data.getTelefono());
	persona.setRecapito(recapito);

	Indirizzo domicilio = new Indirizzo();
	domicilio.setCAP(data.getCapDomicilio());
	domicilio.setVia(data.getIndirizzoDomicilio());
	Luogo cittaDomicilio = new Luogo();
	Stato statoDomicilio = new Stato();
	statoDomicilio.setNome(data.getStatoDomicilio());
	statoDomicilio.setCodiceStato(data.getStatoDomicilio());
	cittaDomicilio.setStato(statoDomicilio);
	cittaDomicilio.getComune().setNome(data.getCittaDomicilio());
	cittaDomicilio.getComune().getProvincia()
		.setNome(data.getProvinciaDomicilio());
	domicilio.setLuogo(cittaDomicilio);

	Indirizzo residenza = new Indirizzo();
	residenza.setCAP(data.getCapResidenza());
	residenza.setVia(data.getIndirizzoResidenza());
	Luogo cittaResidenza = new Luogo();
	Stato statoResidenza = new Stato();
	statoResidenza.setNome(data.getStatoResidenza());
	statoResidenza.setCodiceStato(data.getStatoResidenza());
	cittaResidenza.setStato(statoResidenza);
	cittaResidenza.getComune().setNome(data.getCittaResidenza());
	cittaResidenza.getComune().getProvincia()
		.setNome(data.getProvinciaResidenza());
	residenza.setLuogo(cittaResidenza);

	Luogo cittaNascita = new Luogo();
	cittaNascita.getComune().setNome(data.getLuogoNascita());
	cittaNascita.getComune().getProvincia()
		.setNome(data.getProvinciaNascita());

	persona.setDomicilio(domicilio);
	persona.setResidenza(residenza);
	persona.setLuogodiNascita(cittaNascita);

    }

    /**
     * Inizializza i dati.
     * 
     * @param context
     */
    public abstract void initialize(PeopleContext context,
	    AbstractPplProcess pplProcess);

    /**
     * definisce i validatori per i campi del processo.
     */
    protected abstract void doDefineValidators();

    /**
     * Permette di esportare nei dati della pipeline delle informazioni
     * specifiche dei processi
     * 
     * @param pd
     *            PipelineData in cui scrivo
     */
    public abstract void exportToPipeline(PipelineData pd);

    public boolean validate() {
	return true;
    }

    /**
     * Valida la propriet? di nome 'propertyName'.
     * 
     * @param propertyName
     * @return
     */
    public boolean validate(String propertyName) {
	try {
	    return parserValidate(propertyName);
	} catch (ParserException pEx) {
	}
	return false;
    }

    /**
     * Valida la property 'propertyName'.
     * 
     * @param propertyName
     * @return
     * @throws ParserException
     */
    public boolean parserValidate(String propertyName) throws ParserException {

	FieldValidator validator = getValidator(propertyName);
	try {
	    if (validator != null)
		return validator.parserValidate(PropertyUtils.getProperty(this,
			propertyName));
	} catch (ParserException pEx) {
	    cat.error(pEx);
	    pEx.setPropertyName(propertyName);
	    throw pEx;
	} catch (Exception ex) {
	    cat.error(ex);
	}
	return true;
    }

    /**
     * Trasforma xml passato in ingresso in oggetto
     * 
     * @param xml
     *            Stringa contenente un xml
     * @return Oggetto rappresentante i dati inseriti dagli utenti
     */
    public AbstractEntity unmarshall(String xml) {
	Class classToDeserialize = (this.m_clazz != null ? this.m_clazz
		: getClass());
	return AbstractEntity.unmarshall(classToDeserialize, xml);
    }

    /**
     * Ottiene il validatore corrispondente alla proprieta'.
     * 
     * @param propertyName
     * @return
     */
    public FieldValidator getValidator(String propertyName) {
	FieldValidator fv = (FieldValidator) m_validators.get(propertyName);
	if (fv == null) {
	    int indexedPropRightBrack = propertyName.lastIndexOf(']');
	    int indexedPropLeftBrack = propertyName.lastIndexOf('[');

	    if (indexedPropLeftBrack != -1 && indexedPropRightBrack != -1
		    && indexedPropLeftBrack < indexedPropRightBrack) {
		String generalIndexPropertyName = propertyName.substring(0,
			indexedPropLeftBrack + 1)
			+ propertyName.substring(indexedPropRightBrack);
		fv = (FieldValidator) m_validators
			.get(generalIndexPropertyName);
	    }
	    // todo generalizzare per piu' array
	}
	return (fv != null ? fv : new OptionalAttribute());
    }

    /**
     * @deprecated tutti gli allegati firmati e non sono salvati nella
     *             propriet� Allegati
     */
    public List getDocumentiFirmati() {
	return documentiFirmati;
    }

    /**
     * @deprecated tutti gli allegati firmati e non sono salvati nella
     *             propriet� Allegati
     */
    public void setDocumentiFirmati(List p_attachements) {
	documentiFirmati.clear();
	documentiFirmati.addAll(p_attachements);
    }

    /**
     * @deprecated tutti gli allegati firmati e non sono salvati nella
     *             propriet� Allegati
     */
    public void newDocumentiFirmati() {
	documentiFirmati.add(new SignedData());
    }

    /**
     * @deprecated tutti gli allegati firmati e non sono salvati nella
     *             propriet� Allegati
     */
    public void removeDocumentiFirmati(int p_index) {
	if (p_index >= 0 && p_index < documentiFirmati.size()) {
	    documentiFirmati.remove(p_index);

	}
    }

    /**
     * @deprecated tutti gli allegati firmati e non sono salvati nella
     *             propriet� Allegati
     */
    public void addDocumentiFirmati(SignedData p_attachment) {
	documentiFirmati.add(p_attachment);

    }

    /**
     * @deprecated tutti gli allegati firmati e non sono salvati nella
     *             propriet� Allegati
     */
    public Attachment getDocumentiFirmati(int index) {
	return (Attachment) documentiFirmati.get(index);
    }

    /**
     * Indica se sono presenti allegati
     * 
     * @return true se sono presenti allegati, false altrimenti
     */
    public boolean isPresentiAllegati() {
	return presentiAllegati;
    }

    /**
     * @param b
     */
    public void setPresentiAllegati(boolean b) {
	presentiAllegati = b;

    }

    /**
     * @return
     */
    public String getErrorMessage() {
	return errorMessage;
    }

    /**
     * @return
     */
    public boolean isHasError() {
	return hasError;
    }

    /**
     * @param string
     */
    public void setErrorMessage(String string) {
	errorMessage = string;
    }

    /**
     * @param b
     */
    public void setHasError(boolean b) {
	hasError = b;
    }

    /**
     * Ritorna l'elenco di tutti gli allegati del servizio, compreso il
     * riepilogo firmato o meno. Il riepilogo sar� accessibile solo al termine
     * dell'inserimento della pratica da parte del cittadino ed in particolare
     * nel metodo exportToPipeline().
     * 
     * @return List di istanze della classe @see Attachment
     */
    public List getAllegati() {
	return allegati;
    }

    public void setAllegati(List p_attachements) {
	allegati.clear();
	allegati.addAll(p_attachements);
    }

    /**
     * @param person
     */
    public void newAllegati() {
	allegati.add(new Attachment());

    }

    public void removeAllegati(int p_index) {
	if (p_index >= 0 && p_index < allegati.size()) {
	    try {
		Attachment attachment = (Attachment) allegati.get(p_index);
		if (this.getAttachmentsMaximunSize() > 0) {
		    this.setAttachmentsRemainingSize(this
			    .getAttachmentsRemainingSize()
			    + attachment.getFileLenght());
		}
	    } catch (Exception ignore) {

	    }
	    allegati.remove(p_index);
	}
    }

    public void addAllegati(Attachment p_attachment) {
	allegati.add(SignedAttachmentUtils.returnSignedAttachment(p_attachment));
	try {
	    if (this.getAttachmentsMaximunSize() > 0) {
		this.setAttachmentsRemainingSize(this
			.getAttachmentsMaximunSize()
			- p_attachment.getFileLenght());
	    }
	} catch (Exception ignore) {

	}
    }

    public Attachment getAllegati(int index) {
	return (Attachment) allegati.get(index);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.fsl.servizi.oggetticondivisi.Richiesta#
     * getIdentificatorediProtocollo()
     */
    public IdentificatorediProtocollo getIdentificatorediProtocollo() {
	return identificatoreDiProtocollo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.people.fsl.servizi.oggetticondivisi.Richiesta#getIdentificatorePeople
     * ()
     */
    public IdentificatorePeople getIdentificatorePeople() {
	return identificatorePeople;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.fsl.servizi.oggetticondivisi.Richiesta#getRichiedente()
     */
    public Richiedente getRichiedente() {
	return richiedente;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.fsl.servizi.oggetticondivisi.Richiesta#getTitolare()
     */
    public Titolare getTitolare() {

	return titolare;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.fsl.servizi.oggetticondivisi.Richiesta#
     * setIdentificatorediProtocollo
     * (it.people.fsl.servizi.oggetticondivisi.IdentificatorediProtocollo)
     */
    public void setIdentificatorediProtocollo(
	    IdentificatorediProtocollo identificatorediProtocollo) {
	this.identificatoreDiProtocollo = identificatorediProtocollo;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.people.fsl.servizi.oggetticondivisi.Richiesta#setIdentificatorePeople
     * (it.people.fsl.servizi.oggetticondivisi.IdentificatorePeople)
     */
    public void setIdentificatorePeople(
	    IdentificatorePeople identificatorePeople) {
	this.identificatorePeople = identificatorePeople;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.people.fsl.servizi.oggetticondivisi.Richiesta#setRichiedente(it.people
     * .fsl.servizi.oggetticondivisi.Richiedente)
     */
    public void setRichiedente(Richiedente richiedente) {
	this.richiedente = richiedente;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.people.fsl.servizi.oggetticondivisi.Richiesta#setTitolare(it.people
     * .fsl.servizi.oggetticondivisi.Titolare)
     */
    public void setTitolare(Titolare titolare) {
	this.titolare = titolare;

    }

    public IdentificatoreUnivoco getIdentificatoreUnivoco() {
	return identificatoreUnivoco;
    }

    public void setIdentificatoreUnivoco(IdentificatoreUnivoco id) {
	identificatoreUnivoco = id;
    }

    // ***********************************************************************//
    // Utilizzate per il rispristino del workflow
    // ***********************************************************************//

    // Utilizzate per il ripristino dello stato di attivita' e step
    protected ArrayList persistentActivities = new ArrayList();

    public ArrayList getPersistentActivities() {
	return persistentActivities;
    }

    public void setPersistentActivities(ArrayList persistentActivities) {
	this.persistentActivities = persistentActivities;
    }

    public void addPersistentActivity(PersistentActivity persistentActivity) {
	this.persistentActivities.add(persistentActivity);
    }

    // Utilizzata per gestire l'invio degli allegati al cittadino
    protected boolean receiptMailAttachment = true;

    public boolean isReceiptMailAttachment() {
	return receiptMailAttachment;
    }

    public void setReceiptMailAttachment(boolean receiptMailAttachment) {
	this.receiptMailAttachment = receiptMailAttachment;
    }

    protected boolean sendMailToOwner = true;

    public boolean isSendMailToOwner() {
	return sendMailToOwner;
    }

    public void setSendMailToOwner(boolean sendMailToOwner) {
	this.sendMailToOwner = sendMailToOwner;
    }

    protected boolean embedAttachmentInXml = false;

    public boolean isEmbedAttachmentInXml() {
	return embedAttachmentInXml;
    }

    public void setEmbedAttachmentInXml(boolean embedAttachmentInXml) {
	this.embedAttachmentInXml = embedAttachmentInXml;
    }

    protected boolean showPrivacyDisclaimer = false;

    public boolean isShowPrivacyDisclaimer() {
	return showPrivacyDisclaimer;
    }

    public void setShowPrivacyDisclaimer(boolean showPrivacyDisclaimer) {
	this.showPrivacyDisclaimer = showPrivacyDisclaimer;
    }

    protected boolean privacyDisclaimerRequireAcceptance = false;

    public boolean isPrivacyDisclaimerRequireAcceptance() {
	return privacyDisclaimerRequireAcceptance;
    }

    public void setPrivacyDisclaimerRequireAcceptance(
	    boolean privacyDisclaimerRequireAcceptance) {
	this.privacyDisclaimerRequireAcceptance = privacyDisclaimerRequireAcceptance;
    }

    protected boolean privacyDisclaimerAccepted = false;

    /**
     * @return the privacyDisclaimerAccepted
     */
    public final boolean isPrivacyDisclaimerAccepted() {
	return this.privacyDisclaimerAccepted;
    }

    /**
     * @param privacyDisclaimerAccepted
     *            the privacyDisclaimerAccepted to set
     */
    public final void setPrivacyDisclaimerAccepted(
	    boolean privacyDisclaimerAccepted) {
	this.privacyDisclaimerAccepted = privacyDisclaimerAccepted;
    }

    protected boolean onLineSign = true;

    public boolean isOnLineSign() {
	return onLineSign;
    }

    public void setOnLineSign(boolean onLineSign) {
	this.onLineSign = onLineSign;
    }

    protected boolean offLineSign = false;

    public boolean isOffLineSign() {
	return offLineSign;
    }

    public void setOffLineSign(boolean offLineSign) {
	this.offLineSign = offLineSign;
    }

    protected Vector<String> enabledAuditProcessors;

    /**
     * @param enabledAuditProcessors
     */
    public final void setEnabledAuditProcessors(
	    Vector<String> enabledAuditProcessors) {
	this.enabledAuditProcessors = enabledAuditProcessors;
    }

    /**
     * @return the enabledAuditProcessors
     */
    public final Vector<String> getEnabledAuditProcessors() {
	return this.enabledAuditProcessors;
    }

    /**
     * @param auditProcessor
     * @return
     */
    public final boolean isAuditProcessorEnabled(final String auditProcessor) {
	if (this.getEnabledAuditProcessors() != null) {
	    return this.getEnabledAuditProcessors().contains(auditProcessor);
	} else {
	    return false;
	}
    }

    // Utilizzate per il ripristino dell'attivita' e dello step corrente
    protected int lastActivityIdx;
    protected int lastStepIdx;

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

    // utilizzate per riepilogo e firma
    private boolean signEnabled;
    private SummaryState summaryState;

    /**
     * Utilizzata per il ripristino della pratica dopo il salvataggio, non
     * utilizzare dai servizi
     * 
     * @return Returns the signEnabled.
     */
    public boolean isSignEnabled() {
	return signEnabled;
    }

    /**
     * Utilizzata per il ripristino della pratica dopo il salvataggio, non
     * utilizzare dai servizi
     * 
     * @param signEnabled
     *            The signEnabled to set.
     */
    public void setSignEnabled(boolean signEnabled) {
	this.signEnabled = signEnabled;
    }

    /**
     * Utilizzata per il ripristino della pratica dopo il salvataggio, non
     * utilizzare dai servizi
     * 
     * @return Returns the summaryState.
     */
    public SummaryState getSummaryState() {
	return summaryState;
    }

    /**
     * Utilizzata per il ripristino della pratica dopo il salvataggio, non
     * utilizzare dai servizi
     * 
     * @param summaryState
     *            The summaryState to set.
     */
    public void setSummaryState(SummaryState summaryState) {
	this.summaryState = summaryState;
    }

    // by Mauro (FIX per C&A) - INIZIO
    public void onLoad(AbstractPplProcess process,
	    HttpServletRequest p_servletRequest) {
    }

    // by Mauro (FIX per C&A) - FINE

    // by Mirko Calandrini - INIT ...altriDichiaranti
    public String getOggettoPratica() {
	return this.oggettoPratica;
    }

    public void setOggettoPratica(String oggettoPratica) {
	this.oggettoPratica = oggettoPratica;
    }

    public String getDescrizionePratica() {
	return this.descrizionePratica;
    }

    public void setDescrizionePratica(String descrizionePratica) {
	this.descrizionePratica = descrizionePratica;
    }

    public ArrayList getAltriDichiaranti() {
	return this.altriDichiaranti;
    }

    public void setAltriDichiaranti(ArrayList altriDichiaranti) {
	this.altriDichiaranti = altriDichiaranti;
    }

    public void addAltriDichiaranti(Titolare dichiarante) {
	if (this.altriDichiaranti == null) {
	    this.altriDichiaranti = new ArrayList();
	}
	this.altriDichiaranti.add(dichiarante);
    }

    /**
     * @return the attachmentsMaximunSize
     */
    public final int getAttachmentsMaximunSize() {
	return this.attachmentsMaximunSize;
    }

    /**
     * @param attachmentsMaximunSize
     *            the attachmentsMaximunSize to set
     */
    public final void setAttachmentsMaximunSize(int attachmentsMaximunSize) {
	this.attachmentsMaximunSize = attachmentsMaximunSize;
    }

    /**
     * @return the attachmentsRemainingSize
     */
    public final int getAttachmentsRemainingSize() {
	return this.attachmentsRemainingSize;
    }

    /**
     * @param attachmentsRemainingSize
     *            the attachmentsRemainingSize to set
     */
    public final void setAttachmentsRemainingSize(int attachmentsRemainingSize) {
	this.attachmentsRemainingSize = attachmentsRemainingSize;
    }

    /**
     * @return the pdfGeneratorError
     */
    public final String getPdfGeneratorError() {
	return this.pdfGeneratorError;
    }

    /**
     * @param pdfGeneratorError
     *            the pdfGeneratorError to set
     */
    public final void setPdfGeneratorError(String pdfGeneratorError) {
	this.pdfGeneratorError = pdfGeneratorError;
    }

    /**
     * @return the signedDocumentUploadFile
     */
    public final FormFile getSignedDocumentUploadFile() {
	return this.signedDocumentUploadFile;
    }

    /**
     * @param signedDocumentUploadFile
     *            the signedDocumentUploadFile to set
     */
    public final void setSignedDocumentUploadFile(
	    FormFile signedDocumentUploadFile) {
	this.signedDocumentUploadFile = signedDocumentUploadFile;
    }

    /**
     * @return the istatEnte
     */
    public final String getIstatEnte() {
	return this.istatEnte;
    }

    /**
     * @param istatEnte
     *            the istatEnte to set
     */
    public final void setIstatEnte(String istatEnte) {
	this.istatEnte = istatEnte;
    }

    /**
     * @return the nomeEnte
     */
    public final String getNomeEnte() {
	return this.nomeEnte;
    }

    /**
     * @param nomeEnte
     *            the nomeEnte to set
     */
    public final void setNomeEnte(String nomeEnte) {
	this.nomeEnte = nomeEnte;
    }

}
