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

  Licenza:	    Licenza Progetto PEOPLE
  Fornitore:    CEFRIEL
  Autori:       M. Pianciamore, P. Selvini

  Questo codice sorgente � protetto dalla licenza valida nell'ambito del
  progetto PEOPLE. La propriet� intellettuale di questo codice � e rester�
  esclusiva di "CEFRIEL Societ� Consortile a Responsabilit� Limitata" con
  sede legale in via Renato Fucini 2, 20133 Milano (MI).

  Disclaimer:

  COVERED CODE IS PROVIDED UNDER THIS LICENSE ON AN "AS IS" BASIS, WITHOUT
  WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, WITHOUT 
  LIMITATION, WARRANTIES THAT THE COVERED CODE IS FREE OF DEFECTS, MERCHANTABLE,
  FIT FOR A PARTICULAR PURPOSE OR NON-INFRINGING. THE ENTIRE RISK AS TO THE
  QUALITY AND PERFORMANCE OF THE COVERED CODE IS WITH YOU. SHOULD ANY COVERED
  CODE PROVE DEFECTIVE IN ANY RESPECT, YOU (NOT THE INITIAL DEVELOPER OR ANY
  OTHER CONTRIBUTOR) ASSUME THE COST OF ANY NECESSARY SERVICING, REPAIR OR
  CORRECTION.
    
*/
package it.idp.people.admin.sqlmap.capeople.userdata;

import it.idp.people.admin.common.TableDAO;
import it.idp.people.admin.faces.Manager;
import it.idp.people.admin.sqlmap.capeople.IbatisCAPeople;
import it.idp.people.admin.sqlmap.capeople.userkeystoredata.KeystoreGenerator;
import it.idp.people.admin.sqlmap.capeople.userkeystoredata.Userkeystoredata;
import it.idp.people.admin.sqlmap.common.IdpPeopleAdminConstants;
import it.idp.people.admin.sqlmap.util.MailSender;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Vector;

import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.common.jdbc.exception.NestedSQLException;
import com.ibatis.sqlmap.client.SqlMapClient;

public class UserdataDAO implements TableDAO {
	
	private static Logger logger = LoggerFactory.getLogger(UserdataDAO.class);
	
	public final static String ERROR_INSERT = "Errore nell'inserimento dell'utente";
	public final static String ERROR_UPDATE = "Errore nell'aggiornamento dell'utente";
	public final static String ERROR_DELETE = "Errore nella cancellazione dell'utente";
	public final static String ERROR_PIN = "Errore nella generazione del nuovo pin";
	public final static String ERROR_SMTP = "Errore durante l'invio e-mail";
	public final static String ERROR_DUPLICATE = "L'utente che si sta inserendo risulta gi� presente";
	
	private final static String CONFIGURATION_FILE = "/properties/userdata.properties";
	
	private SqlMapClient sqlMap = null;
	private boolean ascending;
	private String newPwd = null;
	private String newPin = null;
	private Userkeystoredata newUserKeystoreData = null;
	private String sortColumn = null;
	private HashMap headers;
	private String whereClause = null;
	private Properties properties = null;
	private String error;
	private Date newDataRegistrazione = null;
	private Date newDataAttivazione = null;
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public UserdataDAO()
	{
		sqlMap = IbatisCAPeople.getInstance();
		logger.debug("UserdataDAO() - Inizializzazione...");
		properties = loadProperties(CONFIGURATION_FILE);
		logger.debug("UserdataDAO() - Inizializzazione completata.");
	}
	
	public boolean isShowPin() {
		String showPin = properties.getProperty(IdpPeopleAdminConstants.USERS_SHOWPIN);
		return Boolean.valueOf(showPin).booleanValue(); 
	}
	
	public boolean isShowPassword() {
		String showPassword = properties.getProperty(IdpPeopleAdminConstants.USERS_SHOWPASSWORD);
		return Boolean.valueOf(showPassword).booleanValue(); 
	}
	
	public boolean mustSendNewPwdMail()	{
		String sendMail = properties.getProperty(IdpPeopleAdminConstants.USERS_SENDNEWPWDMAIL);
		return Boolean.valueOf(sendMail).booleanValue(); 
	}
	
	public boolean mustSendNewPinMail()	{
		String sendMail = properties.getProperty(IdpPeopleAdminConstants.USERS_SENDNEWPINMAIL);
		return Boolean.valueOf(sendMail).booleanValue(); 
	}
	
	public boolean mustSendNewUserMail() {
		String sendMail = properties.getProperty(IdpPeopleAdminConstants.USERS_SENDNEWUSERMAIL);
		return Boolean.valueOf(sendMail).booleanValue(); 
	}
	
	public List getList() {
		List list = null;
		try {
			if(sqlMap != null) {		
				list = sqlMap.queryForList("getUserdataList", null);				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List getPaginatedList(int exclude, int maxrows) {
		logger.debug("getPaginatedList() - start");
		List list = null;
		try {
			if(sqlMap != null) {		
				Map queryParameters = new HashMap();
				queryParameters.put("exclude", new Integer(exclude));
				queryParameters.put("max", new Integer(maxrows));
				if(sortColumn != null) {
					int columnIndex = Integer.parseInt(sortColumn.substring(3));
					String columnName = (String)getColumnName().get(columnIndex);
					queryParameters.put("column", columnName);
					if(ascending) {
						queryParameters.put("ascending", "ASC");
					}
					else {
						queryParameters.put("ascending", "DESC");
					}
				}
				else {
					queryParameters.put("column", "cognome");
					queryParameters.put("ascending", "ASC");
				}
				
				if(whereClause != null)	{
					queryParameters.put("whereClause", whereClause);
				}
				
				list = sqlMap.queryForList("getUserdataList", queryParameters);
			}
			logger.debug("getPaginatedList() - end");
		} catch(Exception e) {
			logger.error("getPaginatedList() - Errore durante la costruzione dell'elenco utenti. Causa: " + e.getMessage());
			logger.debug("getPaginatedList() - end");
			e.printStackTrace();
		}
		return list;
	}

	public int getRowsCount() {
		try {
			Map queryParameters = new HashMap();
			if(whereClause!=null) {
				queryParameters.put("whereClause", whereClause);
			}
			
			return ((Integer)sqlMap.queryForObject("getUserdataCount", queryParameters)).intValue();
		} 
		catch (Exception e)	{
			e.printStackTrace();
			return 0;
		}	
	}

	public String insert(Object current) {
		logger.debug("insert() - start");
		SqlMapClient sqlMap = IbatisCAPeople.getInstance();
		if(sqlMap != null) {
			try {
				Properties globalProperties = new Properties();
				URL globalUrl = this.getClass().getResource("/properties/app.properties");
				globalProperties.load(globalUrl.openStream());
				
				Timestamp now = new Timestamp(newDataRegistrazione.getTime());
				((Userdata)current).setDataAttivazione(now);
				((Userdata)current).setDataRegistrazione(now);
//				((Userdata)current).setStatus(SiracAdminConstants.USER_STATUS_ACTIVE);
//				if(newPwd == null || "".equals(newPwd)){
					generateNewPassword();
					logger.debug("insert() - Generata nuova password");
//				} else {
//					((Userdata)current).setPassword(newPwd);	
//				}
//				if(newPin == null || "".equals(newPin)){
					generateNewPin();
					logger.debug("insert() - Generato nuovo PIN e nuovo keystore.");
//				} else {
//					((Userdata)current).setPin(newPin);
//				}
				((Userdata)current).setIdComune(globalProperties.getProperty(IdpPeopleAdminConstants.ID_COMUNE));
//				((Userdata)current).setDomicilioElettronico(((Userdata)current).getCodiceFiscale().toUpperCase() + globalProperties.getProperty(SiracAdminConstants.USERNAME_SUFFIX));
				if(((Userdata)current).getIndirizzoDomicilio() == null || "".equals(((Userdata)current).getIndirizzoDomicilio())){
					((Userdata)current).setIndirizzoDomicilio(((Userdata)current).getIndirizzoResidenza());
				}
				if(((Userdata)current).getCapDomicilio() == null || "".equals(((Userdata)current).getCapDomicilio())){
					((Userdata)current).setCapDomicilio(((Userdata)current).getCapResidenza());
				}
				if(((Userdata)current).getCittaDomicilio() == null || "".equals(((Userdata)current).getCittaDomicilio())){
					((Userdata)current).setCittaDomicilio(((Userdata)current).getCittaResidenza());
				}
				if(((Userdata)current).getProvinciaDomicilio() == null || "".equals(((Userdata)current).getProvinciaDomicilio())){
					((Userdata)current).setProvinciaDomicilio(((Userdata)current).getProvinciaResidenza());
				}
				if(((Userdata)current).getStatoDomicilio() == null || "".equals(((Userdata)current).getStatoDomicilio())){
					((Userdata)current).setStatoDomicilio(((Userdata)current).getStatoResidenza());
				}
				logger.debug("insert() - Inizio aggiornamento dati su DB");
				logger.debug("insert() - Inserimento nuovo profilo per l'utente " + ((Userdata)current).getCodiceFiscale() + " in corso...");
				sqlMap.insert("insertUserdata", current);
				logger.debug("insert() - Inserimento nuovo profilo completato.");
				
				logger.debug("insert() - Rimozione precedente keystore per l'utente " + ((Userdata)current).getCodiceFiscale() + " dal database in corso...");
				sqlMap.delete("deleteUserkeystoredata", newUserKeystoreData);
				logger.debug("insert() - Rimozione completata.");
				
				logger.debug("insert() - Inserimento del nuovo keystore per l'utente " + ((Userdata)current).getCodiceFiscale() + " nel database in corso...");
				sqlMap.insert("insertUserkeystoredata", newUserKeystoreData);
				logger.debug("insert() - Inserimento del nuovo keystore completato.");
				
				logger.debug("insert() - Fine aggiornamento dati su DB");
			} catch(NestedSQLException ex) {
				String cause = ex.getCause().getMessage();
				if(cause.startsWith("Duplicate entry"))	{
					setError(UserdataDAO.ERROR_DUPLICATE);	
					logger.error("insert() - Errore durante l'aggiornamento dei dati su DB: utente gi� presente.");
				}
				else {
					setError(UserdataDAO.ERROR_INSERT);
					logger.error("insert() - Errore durante l'aggiornamento dei dati su DB. Causa: " + ex.getMessage());
				}
				return IdpPeopleAdminConstants.RETURN_STATUS_FAILED;
			} catch (Exception e)	{
				setError(UserdataDAO.ERROR_INSERT);
				logger.error("insert() - Si � verificato un errore: " + e.getMessage());
				return IdpPeopleAdminConstants.RETURN_STATUS_FAILED;
			}
			
//			try {
//				logger.debug("insert() - Generazione nuovo keystore per l'utente " + ((Userdata)current).getCodiceFiscale());
//				Userkeystoredata newKeyStoreData = new Userkeystoredata();
//				newKeyStoreData.setCodiceFiscale(((Userdata)current).getCodiceFiscale());
//				newKeyStoreData.setKeyStore(generateKeyStore((Userdata)current));
//				newKeyStoreData.setPin(((Userdata)current).getPin());
//				logger.debug("insert() - Inserimento del nuovo keystore nel database...");
//				sqlMap.insert("insertUserkeystoredata", newKeyStoreData);
//				logger.debug("insert() - Inserimento del nuovo keystore nel database completato.");
//			} catch(SQLException e) {
//				logger.error("insert() - Errore durante l'inserimento del nuovo keystore nel database. Causa: " + e.getMessage());
//				setError(UserdataDAO.ERROR_INSERT);
//				return SiracAdminConstants.RETURN_STATUS_FAILED;
//			}
			
			if(mustSendNewUserMail()) {
				try {
					logger.debug("insert() - Invio mail per comunicazione attivazione nuova utenza in corso...");
					sendNewUserMail((Userdata)current);
					logger.debug("insert() - Invio mail per comunicazione attivazione nuova utenza completato.");
				} catch (RuntimeException e) {
					logger.error("insert() - Errore durante l'invio mail. Causa: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		setError("");
		logger.error("insert() - end.");
		return IdpPeopleAdminConstants.RETURN_STATUS_SUCCESS;
	}

	public String update(Object current) {
		logger.debug("update() - start");
		SqlMapClient sqlMap = IbatisCAPeople.getInstance();
		if(sqlMap != null) {
			try	{
				logger.debug("update() - Aggiornamento del profilo dell'utente " + ((Userdata)current).getCodiceFiscale() + " nel database...");
				sqlMap.update("updateUserdata", current);
				logger.debug("update() - Aggiornamento del profilo dell'utente " + ((Userdata)current).getCodiceFiscale() + " nel database completato.");
				
				if(newUserKeystoreData != null){
					logger.debug("update() - Aggiornamento del keystore dell'utente " + ((Userdata)current).getCodiceFiscale() + " nel database...");
					sqlMap.delete("deleteUserkeystoredata", newUserKeystoreData);
					sqlMap.insert("insertUserkeystoredata", newUserKeystoreData);
					logger.debug("update() - Aggiornamento del keystore dell'utente completato.");
				}
			} catch (SQLException e) {
				logger.error("update() - Errore durante l'aggiornamento del profilo dell'utente " + ((Userdata)current).getCodiceFiscale() + " nel database. Causa: " + e.getMessage());
				logger.debug("update() - end");
				setError(UserdataDAO.ERROR_UPDATE);
				return IdpPeopleAdminConstants.RETURN_STATUS_FAILED;
			}			
		} else {
			logger.error("update() - Errore durante la lettura delle informazioni di configurazione per l'accesso al database");
			setError(UserdataDAO.ERROR_UPDATE);
			return IdpPeopleAdminConstants.RETURN_STATUS_FAILED;
		}
		if(((Userdata)current).isPasswordChanged() && mustSendNewPwdMail()) {
			try {
				logger.debug("insert() - Invio mail per comunicazione variazione password in corso...");
				sendNewPwdMail(((Userdata)current));
				logger.debug("insert() - Invio mail per comunicazione variazione password completata.");
			} catch(Exception e){ 
				logger.error("insert() - Errore durante l'invio mail. Causa: " + e.getMessage());
				logger.debug("update() - end");
				setError(UserdataDAO.ERROR_SMTP);
				return IdpPeopleAdminConstants.RETURN_STATUS_FAILED;
			}
		}

		if(((Userdata)current).isPinChanged() && mustSendNewPinMail()) {
			try {
				logger.debug("insert() - Invio mail per comunicazione variazione PIN in corso...");
				sendNewPinMail(((Userdata)current));
				logger.debug("insert() - Invio mail per comunicazione variazione PIN completata.");
			} catch(Exception e){ 
				logger.error("insert() - Errore durante l'invio mail. Causa: " + e.getMessage());
				logger.debug("update() - end");
				setError(UserdataDAO.ERROR_SMTP);
				return IdpPeopleAdminConstants.RETURN_STATUS_FAILED;
			}
		}
		setError("");
		logger.debug("update() - end");
		return IdpPeopleAdminConstants.RETURN_STATUS_SUCCESS;
	}

	public String delete(Object current) {
		logger.debug("delete() - start");
		SqlMapClient sqlMap = IbatisCAPeople.getInstance();
		if(sqlMap != null) {
			try {
				logger.debug("delete() - Rimozione dati con profilo utente dal database in corso...");
				sqlMap.delete("deleteUserdata", current);
				logger.debug("delete() - Rimozione dati con profilo utente dal database completata.");
				Userkeystoredata keyStoreData = new Userkeystoredata();
				keyStoreData.setCodiceFiscale(((Userdata)current).getCodiceFiscale());
				logger.debug("delete() - Rimozione del keystore di firma dal database in corso...");
				sqlMap.delete("deleteUserkeystoredata", keyStoreData);
				logger.debug("delete() - Rimozione del keystore di firma dal database completata.");
			} 
			catch (SQLException e) {
				logger.error("delete() - Errore durante la rimozione dei dati del profilo utente dal database. Causa: " + e.getMessage());
				logger.debug("delete() - end");
				setError(UserdataDAO.ERROR_DELETE);
				return IdpPeopleAdminConstants.RETURN_STATUS_FAILED;
			}
		}
		logger.debug("delete() - end");
		setError("");
		return IdpPeopleAdminConstants.RETURN_STATUS_SUCCESS;
	}
	
	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	
    public HashMap getHeaders()	{
		if(headers==null) {
			headers = new HashMap();
	        headers.put("codiceFiscale", "Codice Fiscale");
			//headers.put("cartaIdentita", "Carta d'indentit�");
			headers.put("nome", "Nome");
			headers.put("cognome", "Cognome");
			headers.put("sesso", "Sesso");
			headers.put("dataNascita", "Data nascita");
			headers.put("luogoNascita", "Comune nascita");
			headers.put("provinciaNascita", "Prov. nascita");
			//headers.put("statoNascita", "Stato Nascita");
			//headers.put("cittaDomicilio", "Citt� Dom.");
			//headers.put("provinciaDomicilio", "Provincia Dom.");
			//headers.put("capDomicilio", "CAP Dom.");
			//headers.put("capResidenza", "CAP Res.");
			//headers.put("cittaResidenza", "Citt� Res.");
			//headers.put("indirizzoDomicilio", "Indirizzo Dom.");
			//headers.put("indirizzoResidenza", "Indirizzo Res.");
			//headers.put("titolo", "Titolo");
			//headers.put("lavoro", "Lavoro");
			//headers.put("provinciaResidenza", "Provincia Res.");
			//headers.put("statoDomicilio", "Stato Dom.");
			//headers.put("statoResidenza", "Stato Res.");
			//headers.put("telefono", "Telefono");
			//headers.put("cellulare", "Cellulare");
			headers.put("email", "Email");
			headers.put("domicilioElettronico", "Domicilio Elettronico");
			//headers.put("idComune", "Comune");
			//headers.put("password", "Comune");
			//headers.put("pin", "Comune");
			headers.put("dataRegistrazione", "Data Registrazione");
			headers.put("dataAttivazione", "Data Attivazione");
			headers.put("status", "Stato");
			headers.put("ruolo", "Ruolo");
			headers.put("territorio", "Territorio");
		}
		return headers;
	}
    
    public Vector getColumnName() {
    	Vector columnName = new Vector();
    	columnName.add("CODICE_FISCALE");
    	columnName.add("CARTA_IDENTITA");
		columnName.add("NOME");
		columnName.add("COGNOME");
		columnName.add("DATA_NASCITA");
		columnName.add("CAP_DOMICILIO");
		columnName.add("CAP_RESIDENZA");
		columnName.add("CITTA_DOMICILIO");
		columnName.add("CITTA_RESIDENZA");
		columnName.add("INDIRIZZO_DOMICILIO");
		columnName.add("INDIRIZZO_RESIDENZA");
		columnName.add("LAVORO");
		columnName.add("LUOGO_NASCITA");
		columnName.add("PROVINCIA_DOMICILIO");
		columnName.add("PROVINCIA_NASCITA");
		columnName.add("PROVINCIA_RESIDENZA");
		columnName.add("SESSO");
		columnName.add("STATO_DOMICILIO");
		columnName.add("STATO_RESIDENZA");
		columnName.add("STATO_NASCITA");
		columnName.add("TELEFONO");
		columnName.add("CELLULARE");
		columnName.add("TITOLO");
		columnName.add("E_MAIL");
		columnName.add("DOMICILIO_ELETTRONICO");
		columnName.add("ID_COMUNE");
		columnName.add("PASSWORD");
		columnName.add("PIN");
		columnName.add("DATA_REGISTRAZIONE");
		columnName.add("DATA_ATTIVAZIONE");
		columnName.add("STATUS");
		columnName.add("RUOLO");
		columnName.add("TERRITORIO");
    	return columnName;
	}

	public String getWhereClause() {
		return whereClause;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}
	
	public String generateNewPassword() {
		logger.debug("generateNewPassword() - start");
		newPwd = generatePassword();
		Manager tableManager = (Manager)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tableManager");
		if(tableManager != null) {
			((Userdata)tableManager.getCurrent()).setPassword(newPwd);
		}
		logger.debug("generateNewPassword() - end");
		setError("");
		return IdpPeopleAdminConstants.RETURN_STATUS_SUCCESS;
	}
	
	public String generateNewPin() {
		logger.debug("generateNewPin() - start");
		newPin = generatePin();
		Manager tableManager = (Manager)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tableManager");
		if(tableManager != null) {
			try {
				newUserKeystoreData = new Userkeystoredata();
				newUserKeystoreData.setCodiceFiscale(((Userdata)tableManager.getCurrent()).getCodiceFiscale());
//				logger.debug("generateNewPin() - Rimozione precedente keystore per l'utente " + ((Userdata)tableManager.getCurrent()).getCodiceFiscale() + " dal database in corso...");
//				sqlMap.delete("deleteUserkeystoredata", newKeyStoreData);
//				logger.debug("generateNewPin() - Rimozione completata.");
				((Userdata)tableManager.getCurrent()).setPin(newPin);
				logger.debug("generateNewPin() - Generazione di un nuovo keystore l'utente " + ((Userdata)tableManager.getCurrent()).getCodiceFiscale() + " in corso...");
				newUserKeystoreData.setKeyStore(generateKeyStore((Userdata)tableManager.getCurrent()));
				logger.debug("generateNewPin() - Generazione di un nuovo keystore completata.");
				newUserKeystoreData.setPin(newPin);
//				sqlMap.insert("insertUserkeystoredata", newKeyStoreData);
//				logger.debug("generateNewPin() - Inserimento del nuovo keystore per l'utente " + ((Userdata)tableManager.getCurrent()).getCodiceFiscale() + " nel database in corso...");
//				logger.debug("generateNewPin() - Inserimento del nuovo keystore completato.");
				logger.debug("generateNewPin() - end");
				setError("");
				return IdpPeopleAdminConstants.RETURN_STATUS_SUCCESS;
			} catch (Exception e) {
				logger.error("generateNewPin() - Errore durante il processo di generazione del nuovo PIN. Causa: " + e.getMessage());
				logger.debug("generateNewPin() - end");
				setError(UserdataDAO.ERROR_PIN);
				return IdpPeopleAdminConstants.RETURN_STATUS_FAILED;
			}
		} else {
			logger.error("generateNewPin() - Errore durante il processo di generazione del nuovo PIN.");
			logger.debug("generateNewPin() - end");
			setError(UserdataDAO.ERROR_PIN);
			return IdpPeopleAdminConstants.RETURN_STATUS_FAILED;
		}
	}
	
	private void sendNewUserMail(Userdata current) {
//		Properties properties = new Properties();
//		java.net.URL url = this.getClass().getResource("/properties/it/sirac/admin/capeople/userdata/newUserMail.properties");
//		try 
//		{
//			properties.load(url.openStream());
//		} 
//		catch (IOException e) 
//		{
//			e.printStackTrace();
//		}
		logger.debug("sendNewUserMail() - start");
		Properties properties = loadProperties(CONFIGURATION_FILE);
		properties.setProperty(IdpPeopleAdminConstants.MAIL_DESTINATION, current.getEmail());
		String bodyTemplate = properties.getProperty(IdpPeopleAdminConstants.MAIL_NEWUSER_MESSAGE);
		
		Object[] arguments = { 
								current.getCognome() + " " + current.getNome(),
								current.getDomicilioElettronico(),
								current.getPassword(),
								current.getPin() 
							};
		String body = MessageFormat.format(bodyTemplate, arguments);
		
		properties.setProperty(IdpPeopleAdminConstants.MAIL_NEWUSER_MESSAGE, body);
		properties.setProperty(IdpPeopleAdminConstants.MAIL_SUBJECT, properties.getProperty(IdpPeopleAdminConstants.MAIL_NEWUSER_SUBJECT));
		properties.setProperty(IdpPeopleAdminConstants.MAIL_MESSAGE, properties.getProperty(IdpPeopleAdminConstants.MAIL_NEWUSER_MESSAGE));
		
		MailSender mailSender = new MailSender();
		try {
			mailSender.initialize(properties);
			mailSender.sendMail();
		} catch (Exception e) {
			logger.error("sendNewUserMail() - Errore durante l'invio mail. Causa: " + e.getMessage());
			logger.debug("sendNewUserMail() - end");
			e.printStackTrace();
		}
		logger.debug("sendNewUserMail() - end");
	}

	private void sendNewPwdMail(Userdata current) throws Exception {
//		Properties properties = new Properties();
//		java.net.URL url = this.getClass().getResource("/properties/it/sirac/admin/capeople/userdata/newPwdPinMail.properties");
//		try 
//		{
//			properties.load(url.openStream());
//		} 
//		catch (IOException e) 
//		{
//			e.printStackTrace();
//		}
		logger.debug("sendNewPwdMail() - start");
		Properties properties = loadProperties(CONFIGURATION_FILE);
		properties.setProperty(IdpPeopleAdminConstants.MAIL_DESTINATION, current.getDomicilioElettronico());
		String bodyTemplate = properties.getProperty(IdpPeopleAdminConstants.MAIL_NEWPWD_MESSAGE);
		
		Object[] arguments = { current.getCognome() + " " + current.getNome(), current.getPassword() };
		String body = MessageFormat.format(bodyTemplate, arguments);
		
		properties.setProperty(IdpPeopleAdminConstants.MAIL_NEWPWD_MESSAGE, body);
		properties.setProperty(IdpPeopleAdminConstants.MAIL_SUBJECT, properties.getProperty(IdpPeopleAdminConstants.MAIL_NEWPWD_SUBJECT));
		properties.setProperty(IdpPeopleAdminConstants.MAIL_MESSAGE, properties.getProperty(IdpPeopleAdminConstants.MAIL_NEWPWD_MESSAGE));
		
		MailSender mailSender = new MailSender();
		try {
			mailSender.initialize(properties);
			mailSender.sendMail();
		} catch (Exception e) {
			logger.error("sendNewPwdMail() - Errore durante l'invio mail. Causa: " + e.getMessage());
			logger.debug("sendNewPwdMail() - end");
			throw e;
		}
		logger.debug("sendNewPwdMail() - end");
	}

	private void sendNewPinMail(Userdata current) throws Exception {
//		Properties properties = new Properties();
//		java.net.URL url = this.getClass().getResource("/properties/it/sirac/admin/capeople/userdata/newPwdPinMail.properties");
//		try	{
//			properties.load(url.openStream());
//		} 
//		catch (IOException e) {
//			e.printStackTrace();
//		}
		Properties properties = loadProperties(CONFIGURATION_FILE);
		logger.debug("sendNewPinMail() - start");
		properties.setProperty(IdpPeopleAdminConstants.MAIL_DESTINATION, current.getDomicilioElettronico());
		String bodyTemplate = properties.getProperty(IdpPeopleAdminConstants.MAIL_NEWPIN_MESSAGE);
		
		Object[] arguments = { current.getCognome() + " " + current.getNome(), current.getPin()};
		String body = MessageFormat.format(bodyTemplate, arguments);
		
		properties.setProperty(IdpPeopleAdminConstants.MAIL_NEWPIN_MESSAGE, body);
		properties.setProperty(IdpPeopleAdminConstants.MAIL_SUBJECT, properties.getProperty(IdpPeopleAdminConstants.MAIL_NEWPIN_SUBJECT));
		properties.setProperty(IdpPeopleAdminConstants.MAIL_MESSAGE, properties.getProperty(IdpPeopleAdminConstants.MAIL_NEWPIN_MESSAGE));
		
		MailSender mailSender = new MailSender();
		try {
			mailSender.initialize(properties);
			mailSender.sendMail();
		} catch (Exception e)	{
			logger.error("Errore durante l'invio mail: " + e.getMessage());
			logger.debug("sendNewPinMail() - end");
			throw e;
		}
		logger.debug("sendNewPinMail() - end");
	}

	public String getNewPin() {
		newPin = generatePin();
		return newPin;
	}
	
	public String getNewPassword() {
		newPwd = generatePassword();
		return newPwd;
	}
	
	public Date getNewDataRegistrazione(){
		newDataRegistrazione = new Date();
		return newDataRegistrazione;
	}
	
	public Date getNewDataAttivazione(){
		newDataAttivazione = new Date();
		return newDataAttivazione;
	}
	
	protected String generatePassword() {
		logger.debug("generatePassword() - start");
		// Password formata da
		// A-Z e a-z
		// cifre 0-9
		Random rnd = new Random();
		StringBuffer pwd = new StringBuffer(8);
		final String block1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789";		  
		int i;
		
		for(i=0; i<8; i++){
			pwd.append(block1.charAt(rnd.nextInt(block1.length())));
		}
		logger.debug("generatePassword() - end");
		return pwd.toString();
	}
	
	protected String generatePin() {
		logger.debug("generatePin() - start");
		Random rnd = new Random();
		int i;
		StringBuffer pin = new StringBuffer(10);
		final String numbers = "0123456789";
		for(i=0; i<9; i++)
		  pin.append(numbers.charAt(rnd.nextInt(10)));
		logger.debug("generatePin() - end");
		return pin.toString();
	}
	
	private byte[] generateKeyStore(Userdata user) {
		logger.debug("generateKeyStore() - start");
		String caKeyStoreFilename = properties.getProperty(IdpPeopleAdminConstants.USERS_CAKEYSTOREFILE);
		String caKeyStorePassword = properties.getProperty(IdpPeopleAdminConstants.USERS_CAKEYSTOREPASSWORD);
		String caKeyStoreAlias = properties.getProperty(IdpPeopleAdminConstants.USERS_CAKEYSTOREALIAS);
		String caKeyStoreType = properties.getProperty(IdpPeopleAdminConstants.USERS_CAKEYSTORETYPE);
		byte[] result = null;
		try {
			InputStream keyStoreFile = this.getClass().getResourceAsStream(caKeyStoreFilename);
			KeyStore caKeyStore = KeyStore.getInstance(caKeyStoreType);
			caKeyStore.load(keyStoreFile, caKeyStorePassword.toCharArray());
				
			KeyStore keyStore = KeystoreGenerator.createP12(user.getCodiceFiscale(),caKeyStore, caKeyStoreAlias, caKeyStorePassword, user.getPin(), user.getDomicilioElettronico(), user.getNome(), user.getCognome());
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			keyStore.store(baos, user.getPin().toCharArray());
			baos.flush();
			byte[] keystoreBytes = baos.toByteArray();
			baos.close();
			result = keystoreBytes;
		} catch (Exception e)	{
			logger.error("generateKeyStore() - Errore durante la generazione del nuovo keystore. Causa: " + e.getMessage());
			logger.debug("generateKeyStore() - end");
			e.printStackTrace();
		}
		logger.debug("generateKeyStore() - end");
		return result;
	}
	
	private Properties loadProperties(String resourceName){
		Properties properties = new Properties();
		java.net.URL url = this.getClass().getResource(resourceName);
		try {
			logger.debug("loadProperties() - Lettura parametri da file di configurazione in corso...");
			properties.load(url.openStream());
			logger.debug("loadProperties() - Lettura parametri completata.");
			return properties;
		} 
		catch (IOException e) {
			logger.error("loadProperties() - Errore durante la lettura parametri dal file di configurazione. Causa: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}
