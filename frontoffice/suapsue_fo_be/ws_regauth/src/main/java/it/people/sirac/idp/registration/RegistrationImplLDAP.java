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

package it.people.sirac.idp.registration;

import it.intersail.people.siracl.ldap.PeopleComune;
import it.intersail.people.siracl.ldap.PeopleComuni;
import it.intersail.people.siracl.ldap.PeopleUser;
import it.intersail.people.siracl.ldap.PeopleUsers;
import it.people.sirac.idp.beans.ComuneBean;
import it.people.sirac.idp.beans.RegBean;
import it.people.sirac.idp.beans.ResKeystoreBean;
import it.people.sirac.idp.beans.ResRegBean;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.sql.DataSource;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RegistrationImplLDAP implements RegistrationInterface {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(RegistrationImplLDAP.class);

	protected static final String MSG_CREATESUCCESS = "SUCCESSFUL REGISTRATION";
	protected static final String MSG_UPDATESUCCESS = "SUCCESSFUL UPDATE";
	protected static final String MSG_DELETESUCCESS = "SUCCESSFUL REMOVAL";
	protected static final String MSG_ACTIVATIONSUCCESS = "SUCCESSFUL ACTIVATION";
	protected static final String MSG_CREATEKEYSTORESUCCESS = "SUCCESSFUL KEYSTORE CREATION";
	protected static final String MSG_USERALREADYPRESENT = "THE USER IS ALREADY PRESENT";
	protected static final String MSG_USERNOTEXISTENT = "THE USER DOES NOT EXIST";
	protected static final String MSG_PASSWORDCHANGESUCCESS = "PASSWORD CHANGED";
	protected static final String MSG_INITFAILED = "ERRORE DURANTE LA CREAZIONE DELLA USER FACTORY";
	protected static final String MSG_CREATEFAILED = "ERRORE DURANTE LA CREAZIONE DELL'UTENTE NEL REPOSITORY";
	protected static final String MSG_UPDATEFAILED = "ERRORE DURANTE L'AGGIORNAMENTO DEI DATI DELL'UTENTE NEL REPOSITORY";
	protected static final String MSG_DELETEFAILED = "ERRORE DURANTE LA RIMOZIONE DELL'UTENTE DAL REPOSITORY";
	protected static final String MSG_ACTIVATIONFAILED = "ERRORE DURANTE L'ATTIVAZIONE DELL'UTENTE";
	protected static final String MSG_CREATEKEYSTOREFAILED = "ERRORE DURANTE LA CREAZIONE DEL KEYSTORE PER LA FIRMA REMOTA.";
	protected static final String MSG_PASSWORDCHANGEFAILED = "ERRORE DURANTE IL CAMBIO PASSWORD";

	protected static final String ECODE_CREATESUCCESS = "APPLICATION.000";
	protected static final String ECODE_UPDATESUCCESS = "APPLICATION.001";
	protected static final String ECODE_DELETESUCCESS = "APPLICATION.002";
	protected static final String ECODE_ACTIVATIONSUCCESS = "APPLICATION.003";
	protected static final String ECODE_USERALREADYPRESENT  = "APPLICATION.004";
	protected static final String ECODE_USERNOTEXISTENT  = "APPLICATION.005";
	protected static final String ECODE_PASSWORDCHANGESUCCESS = "APPLICATION.006";
	protected static final String ECODE_INITFAILED = "APPLICATION.100";
	protected static final String ECODE_CREATEFAILED = "APPLICATION.101";
	protected static final String ECODE_UPDATEFAILED = "APPLICATION.102";
	protected static final String ECODE_DELETEFAILED = "APPLICATION.103";
	protected static final String ECODE_ACTIVATIONFAILED = "APPLICATION.104";
	protected static final String ECODE_CREATEKEYSTOREFAILED = "APPLICATION.105";
	protected static final String ECODE_PASSWORDCHANGEFAILED = "APPLICATION.106";
	
	protected static DataSource dataSources = null;
	
	protected Connection getConnection() throws NamingException, SQLException
  {
    if (dataSources == null)
    {
        Context initContext = new InitialContext();
        dataSources = (DataSource)initContext.lookup("java:/comp/env/jdbc/WSAuthDB");     
    }
    
    return dataSources.getConnection();
  }
	
  public static String getEnvVar(String key)
  {
    try
    {
      Context initCtx = new InitialContext();
      Context envCtx = (Context)initCtx.lookup("java:comp/env");

      return (String)envCtx.lookup(key);
    } catch(NamingException e) {
      logger.error("getEnvVar("+key+") - Exception : " + e.getMessage());
      StringWriter w = new StringWriter();
      e.printStackTrace(new PrintWriter(w));
      logger.error("Stack: " + w);
      return "";
    }
  }
  
  protected static Hashtable getLdapEnv() throws NamingException
  {
    Hashtable env = new Hashtable(11);
    
    Context initCtx = new InitialContext();
    Context envCtx = (Context)initCtx.lookup("java:comp/env");
        
    env.put(Context.INITIAL_CONTEXT_FACTORY, envCtx.lookup("LDAPContextFactory")); // LDAP Context Provider
    env.put(Context.PROVIDER_URL, envCtx.lookup("LDAPProviderURL"));
    env.put(Context.SECURITY_AUTHENTICATION, envCtx.lookup("LDAPSecurityAuthentication"));
    env.put(Context.SECURITY_PRINCIPAL, envCtx.lookup("LDAPSecurityPrincipal"));  // specify the username
    env.put(Context.SECURITY_CREDENTIALS, envCtx.lookup("LDAPSecurityCredentials"));  // specify the password
    env.put("java.naming.ldap.version", envCtx.lookup("LDAPVersion"));
    
    return env;
  }
  
  public static PeopleUsers createPeopleUsers() throws NamingException
  {
    Hashtable env = getLdapEnv();

    PeopleUsers users = null;

    DirContext rootCtx = new InitialDirContext(env);
    DirContext ctx = (DirContext)rootCtx.lookup(getEnvVar("LDAPCAContext"));
    DirContext schemaCtx = (DirContext)rootCtx.lookup(getEnvVar("LDAPSchema"));
    
    users = new PeopleUsers(schemaCtx, ctx);
    
    return users;
  }
   
  public static PeopleComuni createPeopleComuni() throws NamingException
  {
    Hashtable env = getLdapEnv();

    PeopleComuni comuni = null;

    DirContext rootCtx = new InitialDirContext(env);
    DirContext ctx = (DirContext)rootCtx.lookup(getEnvVar("LDAPComuniContext"));
    DirContext schemaCtx = (DirContext)rootCtx.lookup(getEnvVar("LDAPSchema"));
    
    comuni = new PeopleComuni(schemaCtx, ctx);
    
    return comuni;
  }
  
  /*
   * submit a User Registration request to the Registration service
   * Returns a ResRegBean object containing the response
  */
  public ResRegBean executeRegistration(RegBean userRegistrationData) {
    ResRegBean response = new ResRegBean();

    Date now = new Date();
    
    response.setTimestamp(now.toString());
    
    if(logger.isDebugEnabled())
    {
    	logger.debug("executeRegistration() - START");
    	logger.debug("Codice fiscale = " + userRegistrationData.getCodiceFiscale());
    }
    
    PeopleUsers users = null;
    try {
    	users = createPeopleUsers();
    } catch (NamingException e) {
		  logger.error("executeRegistration() - exception in createUser: "+e.getMessage());
    	if(logger.isDebugEnabled())
    	{
    		StringWriter w = new StringWriter();
    		e.printStackTrace(new PrintWriter(w));
    		logger.error("Exception stack:\n"+w);			
    	}
    	
    	response.setEsito("FAILED");
    	response.setMessaggio(MSG_INITFAILED);
    	response.setErrorCode(ECODE_INITFAILED);
    	
    	return response;
    }
    
    if(users == null)
    {
    	logger.error("Strange error: PeopleUsers == null. Check your code, please...");
    
    	response.setEsito("FAILED");
    	response.setMessaggio("This should not have happened...");
    	response.setErrorCode("STRANGE.001");
    	return response;
    }
    
    boolean alreadyRegistered = false;
    
    try {
    	alreadyRegistered = isUserRegistered(userRegistrationData.getCodiceFiscale());
    } catch(Exception ex){
    	logger.error("executeRegistration() - Exception: " + ex.getMessage());
    	response.setEsito("FAILED");
      response.setMessaggio(MSG_INITFAILED);
      response.setErrorCode(ECODE_INITFAILED);
    	return response;
    }
    
    if(alreadyRegistered){
    	logger.error("executeRegistration() - Exception: " + userRegistrationData.getCodiceFiscale() + " � gi� presente nel repository.");
    	response.setEsito("FAILED");
      response.setMessaggio(MSG_USERALREADYPRESENT);
      response.setErrorCode(ECODE_USERALREADYPRESENT);
    	return response;
    } else {    	
	    
	    if(logger.isDebugEnabled())
	    	logger.debug("executeRegistration() - Setting user attributes table");
	    
	    Hashtable userAtt = new Hashtable();	
	    
	    userAtt.put("peopleNome",userRegistrationData.getNome());
	    userAtt.put("peopleCognome",userRegistrationData.getCognome());
	    userAtt.put("peopleCodiceFiscale",userRegistrationData.getCodiceFiscale().toUpperCase());
	    userAtt.put("peopleEmailAddressPersonale",userRegistrationData.getEmail());
	    userAtt.put("peopleIndirizzoResidenza",userRegistrationData.getIndirizzoResidenza());
	    userAtt.put("peopleCapResidenza",userRegistrationData.getCapResidenza());
	    userAtt.put("peopleCittaResidenza",userRegistrationData.getCittaResidenza());
	    userAtt.put("peopleProvinciaResidenza",userRegistrationData.getProvinciaResidenza());
	    userAtt.put("peopleStatoResidenza",userRegistrationData.getStatoResidenza());
	    userAtt.put("peopleLavoro",userRegistrationData.getLavoro());
	    userAtt.put("peopleIndirizzoDomicilio",userRegistrationData.getIndirizzoDomicilio());
	    userAtt.put("peopleCapDomicilio",userRegistrationData.getCapDomicilio());
	    userAtt.put("peopleCittaDomicilio",userRegistrationData.getCittaDomicilio());
	    userAtt.put("peopleProvinciaDomicilio",userRegistrationData.getProvinciaDomicilio());
	    userAtt.put("peopleStatoDomicilio",userRegistrationData.getStatoDomicilio());
	    userAtt.put("peopleDataNascita",userRegistrationData.getDataNascita());
	    userAtt.put("peopleLuogoNascita",userRegistrationData.getLuogoNascita());    			
	    userAtt.put("peopleProvinciaNascita",userRegistrationData.getProvinciaNascita());
	    userAtt.put("peopleStatoNascita",userRegistrationData.getStatoNascita());
	    userAtt.put("peopleSesso",userRegistrationData.getSesso());
	    userAtt.put("peopleTelefono",userRegistrationData.getTelefono());
	    userAtt.put("peopleCellulare",userRegistrationData.getCellulare());
	    userAtt.put("peopleTitolo",userRegistrationData.getTitolo());
	    userAtt.put("peoplePassword", userRegistrationData.getPassword());
	    userAtt.put("peoplePin", userRegistrationData.getPin());
	    userAtt.put("peopleDomicilioElettronico",userRegistrationData.getDomicilioElettronico());
	    userAtt.put("peopleIdComuneRegistrazione", userRegistrationData.getIdComune());	  
	    userAtt.put("peopleCodiceCarta", userRegistrationData.getCartaIdentita());    
	    userAtt.put("peopleStatus", "INATTIVO");
	    
	    Timestamp tsObj = new Timestamp(now.getTime());
	    logger.debug("peopleTimestampRegistrazione");userAtt.put("peopleTimestampRegistrazione", tsObj.toString());
	    
	    PeopleUser user = null; 
	    try {
	    	if(logger.isDebugEnabled())
	    		logger.debug("Create PeopleUser");
	    	
	    	String uClass = getEnvVar("LDAPUserClass");
	    	
	    	if(logger.isDebugEnabled())
	    		logger.debug("User class = " + uClass);
	    	
	    	user = users.createUser(userRegistrationData.getCodiceFiscale().toUpperCase(), uClass, userAtt);
	    } catch (Exception e) {
	      logger.error("executeRegistration() - exception in createUser: "+e.getMessage());
	
	  		if(logger.isDebugEnabled())
	      {
	      	StringWriter w = new StringWriter();
	      	e.printStackTrace(new PrintWriter(w));
	      	
	      	logger.error("Exception stack:\n"+w);
	      }
	    	
	      response.setEsito("FAILED");
	      response.setMessaggio(MSG_CREATEFAILED);
	      response.setErrorCode(ECODE_CREATEFAILED);
	      	
	      return response;
	    }
	    
	    if(user==null)
	    {
	    	logger.error("Strange error: PeopleUser = null. Please check your code....");
	    	
	    	response.setEsito("FAILED");
	    	response.setMessaggio("This sould not have happened.");
	    	response.setErrorCode("STRANGE.002");
	    	return response;
	    } 
	
	    if(logger.isDebugEnabled())
			logger.debug("executeRegistration() - Aggiunta utente: Codice fiscale = "+user.getAttributeValue("peopleCodiceFiscale")+", Nome = "+user.getAttributeValue("peopleNome")+", Cognome = "+user.getAttributeValue("peopleCognome")+", Nascita = "+user.getAttributeValue("peopleDataNascita"));
	    
	    try {
	    	users.addUser(user);
	    } catch (Exception e) {
	      logger.error("executeRegistration() - exception in createUser: " + e.getMessage());
	
	      if(logger.isDebugEnabled())
	    	{
	    		StringWriter w = new StringWriter();
	    		e.printStackTrace(new PrintWriter(w));
	    		
	    		logger.error("Exception stack:\n");
	    	}
	    	
	    	response.setEsito("FAILED");
	    	response.setMessaggio(MSG_UPDATEFAILED);
	    	response.setErrorCode(ECODE_UPDATEFAILED);
	    	
	    	return response;
	    }   
	    
	    if(logger.isDebugEnabled())
	    	logger.debug("executeRegistration() - Utente registrato.");
	    
	    response.setEsito("OK");
	    response.setMessaggio(MSG_CREATESUCCESS);
	    response.setErrorCode(ECODE_CREATESUCCESS);
	    response.setCodiceFiscale(userRegistrationData.getCodiceFiscale().toUpperCase());
	    response.setUserId(userRegistrationData.getCodiceFiscale().toUpperCase());
	
	    return response;
    }
  }

  // Insert new keystore into storage. ATTN: the keystore is ALWAYS stored into a DB. LDAP is never used for this.
  public ResKeystoreBean insertNewKeystoreData(String userID, String pin, String keystoreB64){
    
    logger.debug("insertNewKeystoreData() - Username: " + userID);
    
    ResKeystoreBean response = new ResKeystoreBean();
    if( userID == null || pin == null || keystoreB64 == null){
      response.setEsito("FAILED");
      response.setErrorCode(ECODE_CREATEKEYSTOREFAILED);
      if (logger.isDebugEnabled()) {
        logger.debug("insertNewKeystoreData() - UserID, pin o keystoreB64 non validi.");
      }
      return response;
    }

    Connection userdbConn = null;
    
    try {
      userdbConn = getConnection();
      byte[] keystore = Base64.decodeBase64(keystoreB64.getBytes());
      
      String dbQuery = "INSERT INTO USERKEYSTOREDATA VALUES (?, ?, ?)";
      PreparedStatement statement = userdbConn.prepareStatement(dbQuery);
      statement.setString(1, userID.toUpperCase());
      statement.setString(2, pin);
      statement.setBinaryStream(3, new ByteArrayInputStream(keystore),keystore.length);

      statement.executeUpdate();
      statement.close();

      response.setEsito("OK");
      response.setMessaggio(MSG_CREATEKEYSTORESUCCESS);
      response.setKeystoreB64(keystoreB64);
      response.setPin(pin);

    } catch (NamingException e) {
      logger.error("insertNewKeystoreData() - Error reading datasources: " + e.getMessage());
      response.setEsito("FAILED");
      response.setErrorCode(ECODE_CREATEKEYSTOREFAILED);
      return response;      
    } catch (SQLException e) {
      logger.error("insertNewKeystoreData() - Error creating connection: " + e.getMessage());
      response.setEsito("FAILED");
      response.setErrorCode(ECODE_CREATEKEYSTOREFAILED);
      return response;      
    } catch (Exception e) {
      logger.error("insertNewKeystoreData() - Error creating connection: " + e.getMessage());
      response.setEsito("FAILED");
      response.setErrorCode(ECODE_CREATEKEYSTOREFAILED);
      return response;      
    } finally {
      try {
        userdbConn.close();
      } catch (SQLException e) {
          logger.error("insertNewKeystoreData() - Can't close connection: " + e.getMessage());
      }
    }
    if (logger.isDebugEnabled()) {
      logger.debug("insertNewKeystoreData() - DEBUG: FINE");
    }
    return response;

  }
  
  /*
   * submit a User update request to the Registration service
   * Returns a ResRegBean object containing the response
  */
  public ResRegBean updateRegistration(RegBean userRegistrationData) {
    ResRegBean response = new ResRegBean();

    Date now = new Date();
    
    response.setTimestamp(now.toString());
    
    if(logger.isDebugEnabled())
    {
    	logger.debug("updateRegistration() - START");
    	logger.debug("updateRegistration() - Codice fiscale = " + userRegistrationData.getCodiceFiscale());
    }
    
    PeopleUsers users = null;
    try {
    	users = createPeopleUsers();
    } catch (NamingException e) {
		  logger.error("updateRegistration() - exception in createUser: "+e.getMessage());
    	if(logger.isDebugEnabled())
    	{
    		StringWriter w = new StringWriter();
    		e.printStackTrace(new PrintWriter(w));
    		logger.error("Exception stack:\n"+w);			
    	}
    	
    	response.setEsito("FAILED");
    	response.setMessaggio(MSG_INITFAILED);
    	response.setErrorCode(ECODE_INITFAILED);
    	
    	return response;
    }
    
    if(users == null)
    {
    	logger.error("Strange error: PeopleUsers == null. Check your code, please...");
    
    	response.setEsito("FAILED");
    	response.setMessaggio("This should not have happened...");
    	response.setErrorCode("STRANGE.001");
    	return response;
    }
    
    boolean alreadyRegistered = false;
    
    try {
    	alreadyRegistered = isUserRegistered(userRegistrationData.getCodiceFiscale());
    } catch(Exception ex){
    	logger.error("updateRegistration() - Exception: " + ex.getMessage());
    	response.setEsito("FAILED");
      response.setMessaggio(MSG_UPDATEFAILED);
      response.setErrorCode(ECODE_UPDATEFAILED);
    	return response;
    }
    
    if(!alreadyRegistered){
    	logger.error("updateRegistration() - Exception: " + userRegistrationData.getCodiceFiscale() + " non � presente nel repository.");
    	response.setEsito("FAILED");
      response.setMessaggio(MSG_UPDATEFAILED);
      response.setErrorCode(ECODE_UPDATEFAILED);
    	return response;
    } else {
    	PeopleUser user = users.getUser("peopleCodiceFiscale", userRegistrationData.getCodiceFiscale().toUpperCase());
      	RegBean existingUserData = new RegBean();
      	existingUserData.setNome(user.getAttributeValue("peopleNome"));
      	existingUserData.setCognome(user.getAttributeValue("peopleCognome"));
      	existingUserData.setCodiceFiscale(user.getAttributeValue("peopleCodiceFiscale"));
      	existingUserData.setEmail(user.getAttributeValue("peopleEmailAddressPersonale"));
      	existingUserData.setDataNascita(user.getAttributeValue("peopleDataNascita"));
      	existingUserData.setLuogoNascita(user.getAttributeValue("peopleLuogoNascita"));
      	existingUserData.setProvinciaNascita(user.getAttributeValue("peopleProvinciaNascita"));
      	existingUserData.setStatoNascita(user.getAttributeValue("peopleStatoNascita"));
      	existingUserData.setLavoro(user.getAttributeValue("peopleLavoro"));
      	existingUserData.setSesso(user.getAttributeValue("peopleSesso"));
      	existingUserData.setTelefono(user.getAttributeValue("peopleTelefono"));
      	existingUserData.setTitolo(user.getAttributeValue("peopleTitolo"));
      	existingUserData.setIndirizzoResidenza(user.getAttributeValue("peopleIndirizzoResidenza"));
      	existingUserData.setIndirizzoDomicilio(user.getAttributeValue("peopleIndirizzoDomicilio"));
      	existingUserData.setCittaResidenza(user.getAttributeValue("peopleCittaResidenza"));
      	existingUserData.setCittaDomicilio(user.getAttributeValue("peopleCittaDomicilio"));
      	existingUserData.setCapResidenza(user.getAttributeValue("peopleCapResidenza"));
      	existingUserData.setCapDomicilio(user.getAttributeValue("peopleCapDomicilio"));
      	existingUserData.setProvinciaResidenza(user.getAttributeValue("peopleProvinciaResidenza"));
      	existingUserData.setProvinciaDomicilio(user.getAttributeValue("peopleProvinciaDomicilio"));
      	existingUserData.setStatoResidenza(user.getAttributeValue("peopleStatoResidenza"));
      	existingUserData.setStatoDomicilio(user.getAttributeValue("peopleStatoDomicilio"));
      	existingUserData.setDomicilioElettronico(user.getAttributeValue("peopleDomicilioElettronico"));
      	existingUserData.setIdComune(user.getAttributeValue("peopleIdComuneRegistrazione"));
      	existingUserData.setCartaIdentita(user.getAttributeValue("peopleCodiceCarta"));
      	existingUserData.setPassword(user.getAttributeValue("peoplePassword"));
      	existingUserData.setPin(user.getAttributeValue("peoplePin"));
      	existingUserData.setStatus(user.getAttributeValue("peopleStatus"));
      	existingUserData.setTimestampRegistrazione(user.getAttributeValue("peopleTimestampRegistrazione"));
        String timestampAttivazione = user.getAttributeValue("peopleTimestampAttivazione");
        if(timestampAttivazione!=null && !"".equalsIgnoreCase(timestampAttivazione)){
        	existingUserData.setTimestampAttivazione(timestampAttivazione);
        } else {
        	existingUserData.setTimestampAttivazione("");
        }
    	
	    if(logger.isDebugEnabled())
	    	logger.debug("updateRegistration() - Setting user attributes table");
	    
	    Hashtable userAtt = new Hashtable();	
	    
	    userAtt.put("peopleNome",userRegistrationData.getNome());
	    userAtt.put("peopleCognome",userRegistrationData.getCognome());
	    userAtt.put("peopleCodiceFiscale",userRegistrationData.getCodiceFiscale().toUpperCase());
	    userAtt.put("peopleEmailAddressPersonale",userRegistrationData.getEmail());
	    userAtt.put("peopleIndirizzoResidenza",userRegistrationData.getIndirizzoResidenza());
	    userAtt.put("peopleCapResidenza",userRegistrationData.getCapResidenza());
	    userAtt.put("peopleCittaResidenza",userRegistrationData.getCittaResidenza());
	    userAtt.put("peopleProvinciaResidenza",userRegistrationData.getProvinciaResidenza());
	    userAtt.put("peopleStatoResidenza",userRegistrationData.getStatoResidenza());
	    userAtt.put("peopleLavoro",userRegistrationData.getLavoro());
	    userAtt.put("peopleIndirizzoDomicilio",userRegistrationData.getIndirizzoDomicilio());
	    userAtt.put("peopleCapDomicilio",userRegistrationData.getCapDomicilio());
	    userAtt.put("peopleCittaDomicilio",userRegistrationData.getCittaDomicilio());
	    userAtt.put("peopleProvinciaDomicilio",userRegistrationData.getProvinciaDomicilio());
	    userAtt.put("peopleStatoDomicilio",userRegistrationData.getStatoDomicilio());
	    userAtt.put("peopleDataNascita",userRegistrationData.getDataNascita());
	    userAtt.put("peopleLuogoNascita",userRegistrationData.getLuogoNascita());    			
	    userAtt.put("peopleProvinciaNascita",userRegistrationData.getProvinciaNascita());
	    userAtt.put("peopleStatoNascita",userRegistrationData.getStatoNascita());
	    userAtt.put("peopleSesso",userRegistrationData.getSesso());
	    userAtt.put("peopleTelefono",userRegistrationData.getTelefono());
	    userAtt.put("peopleCellulare",userRegistrationData.getCellulare());
	    userAtt.put("peopleTitolo",userRegistrationData.getTitolo());
	    userAtt.put("peopleDomicilioElettronico",userRegistrationData.getDomicilioElettronico());
	    userAtt.put("peopleIdComuneRegistrazione", userRegistrationData.getIdComune());	  
	    userAtt.put("peopleCodiceCarta", userRegistrationData.getCartaIdentita());
	    userAtt.put("peopleCodiceCarta", userRegistrationData.getCartaIdentita());
	    userAtt.put("peopleStatus", existingUserData.getStatus());
	    userAtt.put("peoplePassword", existingUserData.getPassword());
	    userAtt.put("peoplePin", existingUserData.getPin());
	    userAtt.put("peopleTimestampRegistrazione", existingUserData.getTimestampRegistrazione());
	    userAtt.put("peopleTimestampAttivazione", existingUserData.getTimestampAttivazione());
	    
	    PeopleUser modifiedUser = null; 
	    try {
	    	if(logger.isDebugEnabled())
	    		logger.debug("Create PeopleUser");
	    	
	    	String uClass = getEnvVar("LDAPUserClass");
	    	
	    	if(logger.isDebugEnabled())
	    		logger.debug("User class = " + uClass);
	    	
	    	modifiedUser = users.createUser(userRegistrationData.getCodiceFiscale().toUpperCase(), uClass, userAtt);
	    } catch (Exception e) {
	      logger.error("updateRegistration() - exception in createUser: "+e.getMessage());
	
	  		if(logger.isDebugEnabled())
	      {
	      	StringWriter w = new StringWriter();
	      	e.printStackTrace(new PrintWriter(w));
	      	
	      	logger.error("Exception stack:\n"+w);
	      }
	    	
	      response.setEsito("FAILED");
	      response.setMessaggio(MSG_UPDATEFAILED);
	      response.setErrorCode(ECODE_UPDATEFAILED);
	      	
	      return response;
	    }
	    
	    if(modifiedUser==null)
	    {
	    	logger.error("Strange error: PeopleUser = null. Please check your code....");
	    	
	    	response.setEsito("FAILED");
	    	response.setMessaggio("This sould haven't happened.");
	    	response.setErrorCode("STRANGE.002");
	    	return response;
	    } 
	
	    if(logger.isDebugEnabled())
			logger.debug("updateRegistration() - Updating: Codice fiscale = "+modifiedUser.getAttributeValue("peopleCodiceFiscale")+", Nome = "+user.getAttributeValue("peopleNome")+", Cognome = "+user.getAttributeValue("peopleCognome")+", Nascita = "+user.getAttributeValue("peopleDataNascita"));
	    
	    try {
	    	users.saveUser(modifiedUser, true);
	    } catch (Exception e) {
	      logger.error("updateRegistration() - exception in createUser: "+e.getMessage());
	
	      if(logger.isDebugEnabled())
	    	{
	    		StringWriter w = new StringWriter();
	    		e.printStackTrace(new PrintWriter(w));
	    		
	    		logger.error("Exception stack:\n");
	    	}
	    	
	    	response.setEsito("FAILED");
	    	response.setMessaggio(MSG_UPDATEFAILED);
	    	response.setErrorCode(ECODE_UPDATEFAILED);
	    	
	    	return response;
	    }   
	    
	    if(logger.isDebugEnabled())
	    	logger.debug("wsReg - User added");
	    
	    response.setEsito("OK");
	    response.setMessaggio(MSG_UPDATESUCCESS);
	    response.setErrorCode(ECODE_UPDATESUCCESS);
	    response.setCodiceFiscale(userRegistrationData.getCodiceFiscale().toUpperCase());
	    response.setUserId(userRegistrationData.getCodiceFiscale().toUpperCase());
	
	    return response;
    }
  }
  
  /*
   * activate the specified user
   * Returns a ResRegBean object containing the response
  */
  public ResRegBean activateUser(String codiceFiscale) throws RemoteException {

  	if(codiceFiscale == null){
    	throw new RemoteException("activateUser(). Codice fiscale non valido: " + codiceFiscale);
    }
  	
  	ResRegBean response = new ResRegBean();
    Date now = new Date();
    response.setTimestamp(now.toString());
    
    if(logger.isDebugEnabled())
    {
    	logger.debug("activateUser() - START");
    	logger.debug("activateUser() - Codice fiscale = " + codiceFiscale);
    }

    PeopleUser userActivated = null; 
    PeopleUsers users = null;
    try {
    	users = createPeopleUsers();
    } catch (NamingException e) {
		  logger.error("activateUser() - exception in createUser: "+e.getMessage());
    	if(logger.isDebugEnabled())
    	{
    		StringWriter w = new StringWriter();
    		e.printStackTrace(new PrintWriter(w));
    		logger.error("Exception stack:\n"+w);			
    	}
    	
    	response.setEsito("FAILED");
    	response.setMessaggio(MSG_INITFAILED);
    	response.setErrorCode(ECODE_INITFAILED);
    	
    	return response;
    }
    
    if(users == null)
    {
    	logger.error("Strange error: PeopleUsers == null. Check your code, please...");
    
    	response.setEsito("FAILED");
    	response.setMessaggio("This should not have happened...");
    	response.setErrorCode("STRANGE.001");
    	return response;
    }
    
    PeopleUser user = users.getUser("peopleCodiceFiscale", codiceFiscale.toUpperCase());

    if(user == null){
    	logger.debug("activateUser() - Nessun utente con codice fiscale = " + codiceFiscale + " trovato.");
     	response.setEsito("FAILED");
    	response.setMessaggio(MSG_INITFAILED);
    	response.setErrorCode(ECODE_INITFAILED);
    	
    	return response;
    } else {
    	RegBean userRegistrationData = new RegBean();
    	userRegistrationData.setNome(user.getAttributeValue("peopleNome"));
    	userRegistrationData.setCognome(user.getAttributeValue("peopleCognome"));
    	userRegistrationData.setCodiceFiscale(user.getAttributeValue("peopleCodiceFiscale").toUpperCase());
    	userRegistrationData.setEmail(user.getAttributeValue("peopleEmailAddressPersonale"));
    	userRegistrationData.setDataNascita(user.getAttributeValue("peopleDataNascita"));
    	userRegistrationData.setLuogoNascita(user.getAttributeValue("peopleLuogoNascita"));
    	userRegistrationData.setProvinciaNascita(user.getAttributeValue("peopleProvinciaNascita"));
    	userRegistrationData.setStatoNascita(user.getAttributeValue("peopleStatoNascita"));
    	userRegistrationData.setLavoro(user.getAttributeValue("peopleLavoro"));
    	userRegistrationData.setSesso(user.getAttributeValue("peopleSesso"));
    	userRegistrationData.setTelefono(user.getAttributeValue("peopleTelefono"));
    	userRegistrationData.setCellulare(user.getAttributeValue("peopleCellulare"));
    	userRegistrationData.setTitolo(user.getAttributeValue("peopleTitolo"));
    	userRegistrationData.setIndirizzoResidenza(user.getAttributeValue("peopleIndirizzoResidenza"));
    	userRegistrationData.setIndirizzoDomicilio(user.getAttributeValue("peopleIndirizzoDomicilio"));
    	userRegistrationData.setCittaResidenza(user.getAttributeValue("peopleCittaResidenza"));
    	userRegistrationData.setCittaDomicilio(user.getAttributeValue("peopleCittaDomicilio"));
    	userRegistrationData.setCapResidenza(user.getAttributeValue("peopleCapResidenza"));
    	userRegistrationData.setCapDomicilio(user.getAttributeValue("peopleCapDomicilio"));
    	userRegistrationData.setProvinciaResidenza(user.getAttributeValue("peopleProvinciaResidenza"));
    	userRegistrationData.setProvinciaDomicilio(user.getAttributeValue("peopleProvinciaDomicilio"));
    	userRegistrationData.setStatoResidenza(user.getAttributeValue("peopleStatoResidenza"));
    	userRegistrationData.setStatoDomicilio(user.getAttributeValue("peopleStatoDomicilio"));
    	userRegistrationData.setDomicilioElettronico(user.getAttributeValue("peopleDomicilioElettronico"));
    	userRegistrationData.setIdComune(user.getAttributeValue("peopleIdComuneRegistrazione"));
    	userRegistrationData.setCartaIdentita(user.getAttributeValue("peopleCodiceCarta"));
    	userRegistrationData.setStatus("ATTIVO");
    	userRegistrationData.setPassword(user.getAttributeValue("peoplePassword"));
    	userRegistrationData.setPin(user.getAttributeValue("peoplePin"));
    	userRegistrationData.setTimestampRegistrazione(user.getAttributeValue("peopleTimestampRegistrazione"));
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String timestampAttivazione = sdf.format(now);
     	userRegistrationData.setTimestampAttivazione(timestampAttivazione);
      
	    
	    Hashtable userAtt = new Hashtable();	
	    
	    userAtt.put("peopleNome",userRegistrationData.getNome());
	    userAtt.put("peopleCognome",userRegistrationData.getCognome());
	    userAtt.put("peopleCodiceFiscale",userRegistrationData.getCodiceFiscale().toUpperCase());
	    userAtt.put("peopleEmailAddressPersonale",userRegistrationData.getEmail());
	    userAtt.put("peopleIndirizzoResidenza",userRegistrationData.getIndirizzoResidenza());
	    userAtt.put("peopleCapResidenza",userRegistrationData.getCapResidenza());
	    userAtt.put("peopleCittaResidenza",userRegistrationData.getCittaResidenza());
	    userAtt.put("peopleProvinciaResidenza",userRegistrationData.getProvinciaResidenza());
	    userAtt.put("peopleStatoResidenza",userRegistrationData.getStatoResidenza());
	    userAtt.put("peopleLavoro",userRegistrationData.getLavoro());
	    userAtt.put("peopleIndirizzoDomicilio",userRegistrationData.getIndirizzoDomicilio());
	    userAtt.put("peopleCapDomicilio",userRegistrationData.getCapDomicilio());
	    userAtt.put("peopleCittaDomicilio",userRegistrationData.getCittaDomicilio());
	    userAtt.put("peopleProvinciaDomicilio",userRegistrationData.getProvinciaDomicilio());
	    userAtt.put("peopleStatoDomicilio",userRegistrationData.getStatoDomicilio());
	    userAtt.put("peopleDataNascita",userRegistrationData.getDataNascita());
	    userAtt.put("peopleLuogoNascita",userRegistrationData.getLuogoNascita());    			
	    userAtt.put("peopleProvinciaNascita",userRegistrationData.getProvinciaNascita());
	    userAtt.put("peopleStatoNascita",userRegistrationData.getStatoNascita());
	    userAtt.put("peopleSesso",userRegistrationData.getSesso());
	    userAtt.put("peopleTelefono",userRegistrationData.getTelefono());
	    userAtt.put("peopleCellulare",userRegistrationData.getCellulare());
	    userAtt.put("peopleTitolo",userRegistrationData.getTitolo());
	    userAtt.put("peoplePassword",userRegistrationData.getPassword());
	    userAtt.put("peoplePin",userRegistrationData.getPin());
	    userAtt.put("peopleDomicilioElettronico",userRegistrationData.getDomicilioElettronico());
	    userAtt.put("peopleIdComuneRegistrazione", userRegistrationData.getIdComune());	  
	    userAtt.put("peopleCodiceCarta", userRegistrationData.getCartaIdentita());
	    userAtt.put("peopleStatus", userRegistrationData.getStatus());
	    userAtt.put("peopleTimestampRegistrazione", userRegistrationData.getTimestampRegistrazione());
	    userAtt.put("peopleTimestampAttivazione", userRegistrationData.getTimestampAttivazione());
	    
	    
	    try {
	    	if(logger.isDebugEnabled())
	    		logger.debug("Create PeopleUser");
	    	
	    	String uClass = getEnvVar("LDAPUserClass");
	    	
	    	if(logger.isDebugEnabled())
	    		logger.debug("User class = " + uClass);
	    	
	    	userActivated = users.createUser(userRegistrationData.getCodiceFiscale().toUpperCase(), uClass, userAtt);
	    } catch (Exception e) {
	      logger.error("activateUser() - exception in createUser: "+e.getMessage());
	
	  		if(logger.isDebugEnabled())
	      {
	      	StringWriter w = new StringWriter();
	      	e.printStackTrace(new PrintWriter(w));
	      	
	      	logger.error("Exception stack:\n"+w);
	      }
	    	
	      response.setEsito("FAILED");
	    	response.setMessaggio(MSG_ACTIVATIONFAILED);
	    	response.setErrorCode(ECODE_ACTIVATIONFAILED);
	      	
	      return response;
	    }
    }
    
    if(userActivated==null)
    {
    	logger.error("Strange error: PeopleUser = null. Please check your code....");
    	
    	response.setEsito("FAILED");
    	response.setMessaggio("This sould not have happened.");
    	response.setErrorCode("STRANGE.002");
    	return response;
    } 

    if(logger.isDebugEnabled())
		logger.debug("activateUser() - Attivazione utente - Codice fiscale = "+user.getAttributeValue("peopleCodiceFiscale")+", Nome = "+user.getAttributeValue("peopleNome")+", Cognome = "+user.getAttributeValue("peopleCognome")+", Nascita = "+user.getAttributeValue("peopleDataNascita"));
    
    try {
    	users.saveUser(userActivated, true);
    } catch (Exception e) {
      logger.error("activateUser() - exception in createUser: "+e.getMessage());

      if(logger.isDebugEnabled())
    	{
    		StringWriter w = new StringWriter();
    		e.printStackTrace(new PrintWriter(w));
    		
    		logger.error("Exception stack:\n");
    	}
    	
    	response.setEsito("FAILED");
    	response.setMessaggio(MSG_ACTIVATIONFAILED);
    	response.setErrorCode(ECODE_ACTIVATIONFAILED);
    	
    	return response;
    }   
    
    if(logger.isDebugEnabled())
    	logger.debug("activateUser() - User attivato.");
    
    response.setEsito("OK");
    response.setMessaggio(MSG_ACTIVATIONSUCCESS);
    response.setErrorCode(ECODE_ACTIVATIONSUCCESS);
    response.setCodiceFiscale(codiceFiscale.toUpperCase());
    response.setUserId(codiceFiscale.toUpperCase());

    return response;
  }
  
  
  public ResRegBean changePassword(String codiceFiscale, String oldPassword, String newPassword) throws RemoteException {
  	
  	if(codiceFiscale==null || oldPassword == null || newPassword == null || "".equalsIgnoreCase(codiceFiscale) || "".equalsIgnoreCase(oldPassword) || "".equalsIgnoreCase(newPassword)){
  		throw new RemoteException("changePassword() - parametri di ingresso non validi");
  	}
  	
  	ResRegBean response = new ResRegBean();	
  	Date now = new Date();
  	response.setTimestamp(now.toString());
    
    if(logger.isDebugEnabled())
    {
    	logger.debug("changePassword() - START");
    	logger.debug("changePassword() - Codice fiscale = " + codiceFiscale);
    }

    PeopleUser userModified = null; 
    PeopleUsers users = null;
    try {
    	users = createPeopleUsers();
    } catch (NamingException e) {
		  logger.error("changePassword() - exception in createUser: "+e.getMessage());
    	if(logger.isDebugEnabled())
    	{
    		StringWriter w = new StringWriter();
    		e.printStackTrace(new PrintWriter(w));
    		logger.error("Exception stack:\n"+w);			
    	}
    	
    	response.setEsito("FAILED");
    	response.setMessaggio(MSG_INITFAILED);
    	response.setErrorCode(ECODE_INITFAILED);
    	
    	return response;
    }
    
    if(users == null)
    {
    	logger.error("Strange error: PeopleUsers == null. Check your code, please...");
    
    	response.setEsito("FAILED");
    	response.setMessaggio("This should not have happened...");
    	response.setErrorCode("STRANGE.001");
    	return response;
    }
    
    PeopleUser user = users.getUser("peopleCodiceFiscale", codiceFiscale.toUpperCase());

    if(user == null){
    	logger.debug("activateUser() - Nessun utente con codice fiscale = " + codiceFiscale + " trovato.");
     	response.setEsito("FAILED");
    	response.setMessaggio(MSG_INITFAILED);
    	response.setErrorCode(ECODE_INITFAILED);
    	
    	return response;    
    } else {
    	if(oldPassword.equals(user.getAttributeValue("peoplePassword"))){
    	  RegBean userRegistrationData = new RegBean();
	      userRegistrationData.setNome(user.getAttributeValue("peopleNome"));
	      userRegistrationData.setCognome(user.getAttributeValue("peopleCognome"));
	      userRegistrationData.setCodiceFiscale(user.getAttributeValue("peopleCodiceFiscale").toUpperCase());
	      userRegistrationData.setEmail(user.getAttributeValue("peopleEmailAddressPersonale"));
	      userRegistrationData.setDataNascita(user.getAttributeValue("peopleDataNascita"));
	      userRegistrationData.setLuogoNascita(user.getAttributeValue("peopleLuogoNascita"));
	      userRegistrationData.setProvinciaNascita(user.getAttributeValue("peopleProvinciaNascita"));
	      userRegistrationData.setStatoNascita(user.getAttributeValue("peopleStatoNascita"));
	      userRegistrationData.setLavoro(user.getAttributeValue("peopleLavoro"));
	      userRegistrationData.setSesso(user.getAttributeValue("peopleSesso"));
	      userRegistrationData.setTelefono(user.getAttributeValue("peopleTelefono"));
	      userRegistrationData.setCellulare(user.getAttributeValue("peopleCellulare"));
	      userRegistrationData.setTitolo(user.getAttributeValue("peopleTitolo"));
	      userRegistrationData.setIndirizzoResidenza(user.getAttributeValue("peopleIndirizzoResidenza"));
	      userRegistrationData.setIndirizzoDomicilio(user.getAttributeValue("peopleIndirizzoDomicilio"));
	      userRegistrationData.setCittaResidenza(user.getAttributeValue("peopleCittaResidenza"));
	      userRegistrationData.setCittaDomicilio(user.getAttributeValue("peopleCittaDomicilio"));
	      userRegistrationData.setCapResidenza(user.getAttributeValue("peopleCapResidenza"));
	      userRegistrationData.setCapDomicilio(user.getAttributeValue("peopleCapDomicilio"));
	      userRegistrationData.setProvinciaResidenza(user.getAttributeValue("peopleProvinciaResidenza"));
	      userRegistrationData.setProvinciaDomicilio(user.getAttributeValue("peopleProvinciaDomicilio"));
	      userRegistrationData.setStatoResidenza(user.getAttributeValue("peopleStatoResidenza"));
	      userRegistrationData.setStatoDomicilio(user.getAttributeValue("peopleStatoDomicilio"));
	      userRegistrationData.setDomicilioElettronico(user.getAttributeValue("peopleDomicilioElettronico"));
	      userRegistrationData.setIdComune(user.getAttributeValue("peopleIdComuneRegistrazione"));
	      userRegistrationData.setCartaIdentita(user.getAttributeValue("peopleCodiceCarta"));
	      userRegistrationData.setStatus(user.getAttributeValue("peopleStatus"));
	      userRegistrationData.setPassword(newPassword);
	      userRegistrationData.setPin(user.getAttributeValue("peoplePin"));
	      userRegistrationData.setTimestampRegistrazione(user.getAttributeValue("peopleTimestampRegistrazione"));
	     	userRegistrationData.setTimestampAttivazione(user.getAttributeValue("peopleTimestampAttivazione"));
	      
		    Hashtable userAtt = new Hashtable();	
		    
		    userAtt.put("peopleNome",userRegistrationData.getNome());
		    userAtt.put("peopleCognome",userRegistrationData.getCognome());
		    userAtt.put("peopleCodiceFiscale",userRegistrationData.getCodiceFiscale().toUpperCase());
		    userAtt.put("peopleEmailAddressPersonale",userRegistrationData.getEmail());
		    userAtt.put("peopleIndirizzoResidenza",userRegistrationData.getIndirizzoResidenza());
		    userAtt.put("peopleCapResidenza",userRegistrationData.getCapResidenza());
		    userAtt.put("peopleCittaResidenza",userRegistrationData.getCittaResidenza());
		    userAtt.put("peopleProvinciaResidenza",userRegistrationData.getProvinciaResidenza());
		    userAtt.put("peopleStatoResidenza",userRegistrationData.getStatoResidenza());
		    userAtt.put("peopleLavoro",userRegistrationData.getLavoro());
		    userAtt.put("peopleIndirizzoDomicilio",userRegistrationData.getIndirizzoDomicilio());
		    userAtt.put("peopleCapDomicilio",userRegistrationData.getCapDomicilio());
		    userAtt.put("peopleCittaDomicilio",userRegistrationData.getCittaDomicilio());
		    userAtt.put("peopleProvinciaDomicilio",userRegistrationData.getProvinciaDomicilio());
		    userAtt.put("peopleStatoDomicilio",userRegistrationData.getStatoDomicilio());
		    userAtt.put("peopleDataNascita",userRegistrationData.getDataNascita());
		    userAtt.put("peopleLuogoNascita",userRegistrationData.getLuogoNascita());    			
		    userAtt.put("peopleProvinciaNascita",userRegistrationData.getProvinciaNascita());
		    userAtt.put("peopleStatoNascita",userRegistrationData.getStatoNascita());
		    userAtt.put("peopleSesso",userRegistrationData.getSesso());
		    userAtt.put("peopleTelefono",userRegistrationData.getTelefono());
		    userAtt.put("peopleCellulare",userRegistrationData.getCellulare());
		    userAtt.put("peopleTitolo",userRegistrationData.getTitolo());
		    userAtt.put("peoplePassword",userRegistrationData.getPassword());
		    userAtt.put("peoplePin",userRegistrationData.getPin());
		    userAtt.put("peopleDomicilioElettronico",userRegistrationData.getDomicilioElettronico());
		    userAtt.put("peopleIdComuneRegistrazione", userRegistrationData.getIdComune());	  
		    userAtt.put("peopleCodiceCarta", userRegistrationData.getCartaIdentita());
		    userAtt.put("peopleStatus", userRegistrationData.getStatus());
		    userAtt.put("peopleTimestampRegistrazione", userRegistrationData.getTimestampRegistrazione());
		    userAtt.put("peopleTimestampAttivazione", userRegistrationData.getTimestampAttivazione());
		    
		    try {
		    	if(logger.isDebugEnabled())
		    		logger.debug("Create PeopleUser");
		    	
		    	String uClass = getEnvVar("LDAPUserClass");
		    	
		    	if(logger.isDebugEnabled())
		    		logger.debug("User class = " + uClass);
		    	
		    	userModified = users.createUser(userRegistrationData.getCodiceFiscale().toUpperCase(), uClass, userAtt);
		    } catch (Exception e) {
		      logger.error("changePassword() - exception in createUser: "+e.getMessage());
		
		  		if(logger.isDebugEnabled())
		      {
		      	StringWriter w = new StringWriter();
		      	e.printStackTrace(new PrintWriter(w));
		      	
		      	logger.error("Exception stack:\n"+w);
		      }
		    	
		      response.setEsito("FAILED");
		    	response.setMessaggio(MSG_PASSWORDCHANGEFAILED);
		    	response.setErrorCode(ECODE_PASSWORDCHANGEFAILED);
		      	
		      return response;
		   
		    }
	    
		    if(userModified==null)
		    {
		    	logger.error("Strange error: PeopleUser = null. Please check your code....");
		    	
		    	response.setEsito("FAILED");
		    	response.setMessaggio("This sould not have happened.");
		    	response.setErrorCode("STRANGE.002");
		    	return response;
		    } 
		
		    if(logger.isDebugEnabled())
				logger.debug("changePassword() - Cambio password - Codice fiscale = "+user.getAttributeValue("peopleCodiceFiscale")+", Nome = "+user.getAttributeValue("peopleNome")+", Cognome = "+user.getAttributeValue("peopleCognome")+", Nascita = "+user.getAttributeValue("peopleDataNascita"));
		    
		    try {
		    	users.saveUser(userModified, true);
		    } catch (Exception e) {
		      logger.error("changePassword() - exception in createUser: "+e.getMessage());
		
		      if(logger.isDebugEnabled())
		    	{
		    		StringWriter w = new StringWriter();
		    		e.printStackTrace(new PrintWriter(w));
		    		
		    		logger.error("Exception stack:\n");
		    	}
		    	
		    	response.setEsito("FAILED");
		    	response.setMessaggio(MSG_PASSWORDCHANGEFAILED);
		    	response.setErrorCode(ECODE_PASSWORDCHANGEFAILED);
		    	
		    	return response;
		    }   
		    
		    if(logger.isDebugEnabled())
		    	logger.debug("changePassword() - Password cambiata.");
		    
		    response.setEsito("OK");
		    response.setMessaggio(MSG_PASSWORDCHANGESUCCESS);
		    response.setErrorCode(ECODE_PASSWORDCHANGESUCCESS);
		    response.setCodiceFiscale(codiceFiscale.toUpperCase());
		    response.setUserId(codiceFiscale.toUpperCase());
		
		    return response;
    	} else {
    		if(logger.isDebugEnabled())
    			logger.debug("changePassword() - Vecchia password errata.");
    		
    		response.setEsito("FAILED");
    		response.setMessaggio(MSG_PASSWORDCHANGEFAILED);
    		response.setErrorCode(ECODE_PASSWORDCHANGEFAILED);
    		return response;
    	} 
    }
  }
  	
  /*
   * submit a User removal request to the Registration service
   * Returns a ResRegBean object containing the response
  */
  public ResRegBean deleteRegistration(String codiceFiscale) throws RemoteException {
	  ResRegBean response = new ResRegBean();

	  if(logger.isDebugEnabled())
	    {
	    	logger.debug("deleteRegistration() - START");
	    	logger.debug("deleteRegistration() - Codice fiscale = " + codiceFiscale);
	    }
	    
	    PeopleUsers users = null;
	    try {
	    	users = createPeopleUsers();
	    } catch (NamingException e) {
			  logger.error("deleteRegistration() - exception in createUser: "+e.getMessage());
	    	if(logger.isDebugEnabled())
	    	{
	    		StringWriter w = new StringWriter();
	    		e.printStackTrace(new PrintWriter(w));
	    		logger.error("Exception stack:\n"+w);			
	    	}
	    	
	    	response.setEsito("FAILED");
	    	response.setMessaggio(MSG_INITFAILED);
	    	response.setErrorCode(ECODE_INITFAILED);
	    	
	    	return response;
	    }
	    
	    if(users == null)
	    {
	    	logger.error("Strange error: PeopleUsers == null. Check your code, please...");
	    
	    	response.setEsito("FAILED");
	    	response.setMessaggio("This should not have happened...");
	    	response.setErrorCode("STRANGE.001");
	    	return response;
	    } 
	    
	    try {
	    	users.removeUser(codiceFiscale.toUpperCase());
	    } catch (Exception e) {
	      logger.error("deleteRegistration() - exception in removeUser: "+e.getMessage());

	      if(logger.isDebugEnabled())
	    	{
	    		StringWriter w = new StringWriter();
	    		e.printStackTrace(new PrintWriter(w));
	    		
	    		logger.error("Exception stack:\n");
	    	}
	    	
	    	response.setEsito("FAILED");
	    	response.setMessaggio(MSG_DELETEFAILED);
	    	response.setErrorCode(ECODE_DELETEFAILED);
	    	
	    	return response;
	    }   
	    
	    if(logger.isDebugEnabled())
	    	logger.debug("deleteRegistration() - Utente rimosso.");
	    
	    response.setEsito("OK");
	    response.setMessaggio(MSG_DELETESUCCESS);
	    response.setErrorCode(ECODE_DELETESUCCESS);
	    response.setCodiceFiscale(codiceFiscale.toUpperCase());
	    response.setUserId(codiceFiscale.toUpperCase());

	    return response;
	    
  }
  
  public boolean isUserRegistered(String codiceFiscale) throws RemoteException {
	  
	  if (codiceFiscale == null) {
	      throw new RemoteException("isUserRegistered(). Codice fiscale non valido: " + codiceFiscale);
	    }
	  
	  try {
	     PeopleUsers users = createPeopleUsers();
	     PeopleUser user = users.getUser("peopleCodiceFiscale", codiceFiscale.toUpperCase());

	     if(user == null){
	      return false;
	     } else{
	      return true;
	     }
	  } catch(Exception e) {
	      if (logger.isDebugEnabled()) {
	        logger.debug("isUserRegistered() - Exception: " + e.getMessage());
	      }
	      logger.error("isUserRegistered(COD_FIS = " + codiceFiscale + ") - Exception:", e);
	      throw new RemoteException("isUserRegistered() - Exception: "  + e.getMessage());
	  }
	  
  }
  
  public ComuneBean getComuneByCodiceBelfiore(String codiceBelfiore) throws RemoteException {
	  ComuneBean comuneBean = new ComuneBean();

	    if (codiceBelfiore == null || "".equalsIgnoreCase(codiceBelfiore)) {
	      throw new RemoteException(
	          "getComuneFromCodiceBelfiore(). Codice belfiore non valido: " + codiceBelfiore);
	    }

	    try {
	      PeopleComuni comuni = createPeopleComuni();
	      
	      if (logger.isDebugEnabled()) {
	        logger.debug("getComuneByCodiceBelfiore() - DEBUG: DOPO CONNESSIONE");
	      }

	      PeopleComune comune = comuni.getComune("codiceBelfioreComune", codiceBelfiore.toUpperCase());
	      if (comune!= null) {
	      	comuneBean.setNome(comune.getAttributeValue("nomeComune"));
	      	comuneBean.setProvincia(comune.getAttributeValue("provinciaComune"));
	      	comuneBean.setRegione(comune.getAttributeValue("regioneComune"));
	      	comuneBean.setCap(comune.getAttributeValue("capComune"));
	      	comuneBean.setPrefisso(comune.getAttributeValue("prefissoComune"));
        	comuneBean.setCodiceComune(comune.getAttributeValue("codiceBelfioreComune"));
        	comuneBean.setCodiceIstat(comune.getAttributeValue("codiceIstatComune"));
	        } else{
	            throw new RemoteException(
	                "getComuneByCodiceBelfiore(). Codice Belfiore " + codiceBelfiore
	                    + " non trovato");
	        }
	      
	    } catch (Exception e) {
	      logger.error("getComuneByCodiceBelfiore(): " + e.getMessage());
	      if(logger.isDebugEnabled())
	      {
	        StringWriter w = new StringWriter();
	        e.printStackTrace(new PrintWriter(w));
	        logger.error("Exception stack:\n"+w);     
	        throw new RemoteException(e.getMessage());
	      }
	    }

	    return comuneBean;
	  
  }
  
  public ComuneBean getComuneByCodiceIstat(String codiceIstat) throws RemoteException {
	  ComuneBean comuneBean = new ComuneBean();

	    if (codiceIstat == null || "".equalsIgnoreCase(codiceIstat)) {
	      throw new RemoteException(
	          "getComuneFromCodiceIstat(). Codice Istat non valido: " + codiceIstat);
	    }

	    try {
	      PeopleComuni comuni = createPeopleComuni();
	      
	      if (logger.isDebugEnabled()) {
	        logger.debug("getComuneByCodiceIstat() - DEBUG: DOPO CONNESSIONE");
	      }

	      PeopleComune user = comuni.getComune("codiceIstatComune", codiceIstat);
	      if (user!= null) {
	      	comuneBean.setNome(user.getAttributeValue("nomeComune"));
	      	comuneBean.setProvincia(user.getAttributeValue("provinciaComune"));
	      	comuneBean.setRegione(user.getAttributeValue("regioneComune"));
	      	comuneBean.setCap(user.getAttributeValue("capComune"));
	      	comuneBean.setPrefisso(user.getAttributeValue("prefissoComune"));
	      	comuneBean.setCodiceComune(user.getAttributeValue("codiceBelfioreComune"));
	      	comuneBean.setCodiceIstat(user.getAttributeValue("codiceIstatComune"));
	        } else{
	            throw new RemoteException(
	                "getComuneByCodiceIstat(). Codice Istat " + codiceIstat
	                    + " non trovato");
	        }
	      
	    } catch (Exception e) {
	      logger.error("getComuneByCodiceIstat(): " + e.getMessage());
	      if(logger.isDebugEnabled())
	      {
	        StringWriter w = new StringWriter();
	        e.printStackTrace(new PrintWriter(w));
	        logger.error("Exception stack:\n"+w);     
	        throw new RemoteException(e.getMessage());
	      }
	    }

	    return comuneBean;
	  
  }
}
