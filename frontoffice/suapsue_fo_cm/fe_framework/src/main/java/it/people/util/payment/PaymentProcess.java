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
/*
 * Creato il 6-lug-2006 da Cedaf s.r.l.
 *
 */
package it.people.util.payment;

import it.people.City;
import it.people.core.exception.ServiceException;
import it.people.dao.DAOFactory;
import it.people.dao.IDataAccess;
import it.people.logger.Logger;
import it.people.util.EMailSender;
import it.people.util.IDGenerator;
import it.people.util.payment.request.ObserverData;
import it.people.vsl.exception.SendException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 */
public class PaymentProcess {
    private Logger serviceLogger = null;
    private City commune;
    private String processName;
    private String userEmailAddress;
    private String peopleProtocolId;
    private String userId;
    private Long saveProcessId;
    private ObserverData observerData;

    PaymentProcess(City commune, String processName, String userEmailAddress,
	    String peopleProtocolId, String userId, Long saveProcessId) {
	this.commune = commune;
	this.processName = processName;
	this.userEmailAddress = userEmailAddress;
	this.peopleProtocolId = peopleProtocolId;
	this.userId = userId;
	this.saveProcessId = saveProcessId;
    }

    PaymentProcess(City commune, String processName, String userEmailAddress,
	    String peopleProtocolId, String userId, Long saveProcessId,
	    ObserverData observerData) {
	this(commune, processName, userEmailAddress, peopleProtocolId, userId,
		saveProcessId);
	this.observerData = observerData;
    }

    /**
     * Chiama un servizio di back-end
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
		this.commune.getOid());
	return da.call(parameter, this.commune.getOid(), this.userId,
		this.processName, this.saveProcessId);
    }

    /**
     * Ritorna un nuovo identificativo operazione.
     * 
     * @return identificativo
     */
    public String getNewIdentificativoOperazione() {
	String aooPrefix = this.commune.getAooPrefix();
	String codiceFiscale = this.getCodiceFiscale();
	return IDGenerator.generateID(codiceFiscale, aooPrefix);
    }

    protected String getCodiceFiscale() {
	// FIX 2007-12-19 by CEFRIEL
	// Lo userID nel caso di operatori associazione di categoria
	// pu� non avere il formato "COD.FIS.@CA". Nel caso non sia presente
	// il carattere "@" si assume che userID=COD.FIS.
	int userIdSeparatorPos = this.userId.indexOf('@');
	if (userIdSeparatorPos >= 0) {
	    return this.userId.substring(0, this.userId.indexOf('@'));
	} else {
	    return this.userId;
	}
	// END FIX 2007-12-19
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
	sendMail(userEmailAddress, subject, body);
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
	sendMail(address, subject, body, this.commune.getKey());
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

    /*
     * Gestione del Log
     */
    protected it.people.logger.Logger getLogger() {
	return Logger.getLogger(this.processName, this.commune);
    }

    /**
     * Effettua il log a livello debug per il servizio corrente
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
     * Effettua il log a livello info per il servizio corrente
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
     * Effettua il log a livello warn per il servizio corrente
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
     * Effettua il log a livello error per il servizio corrente
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
     * Effettua il log a livello fatal per il servizio corrente
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
     * Comune per il quale � stato effettuato il pagamento
     * 
     * @return
     */
    public City getCommune() {
	return commune;
    }

    /**
     * Identificativo pratica People
     * 
     * @return
     */
    public String getPeopleProtocolId() {
	return peopleProtocolId;
    }

    /**
     * Identificativo del servizio comprensivo del package
     * 
     * @return
     */
    public String getProcessName() {
	return processName;
    }

    /**
     * Indirizzo email dell'utente
     * 
     * @return
     */
    public String getUserEmailAddress() {
	return userEmailAddress;
    }

    /**
     * Identificativo dell'utente
     * 
     * @return
     */
    public String getUserId() {
	return userId;
    }

    /**
     * Dati Estesi passati esclusivamente alla classe observer
     * 
     * @return
     */
    public ObserverData getObserverData() {
	return observerData;
    }
}
