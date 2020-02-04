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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RegistrationImplDB implements RegistrationInterface {

	private static final Logger logger = LoggerFactory.getLogger(RegistrationImplDB.class);

	protected static final String MSG_CREATESUCCESS = "Registrazione completata correttamente.";
	protected static final String MSG_UPDATESUCCESS = "Agggiornamento completato correttamente.";
	protected static final String MSG_DELETESUCCESS = "Cancellazione completata correttamente.";
	protected static final String MSG_ACTIVATIONSUCCESS = "Attivazione completata correttamente.";
	protected static final String MSG_CREATEKEYSTORESUCCESS = "Creazione del keystore per la firma remota completata correttamente.";
	protected static final String MSG_PASSWORDCHANGESUCCESS = "Password cambiata correttamente.";
	protected static final String MSG_USERALREADYPRESENT = "L'utenza specificata � gi� registrata.";
	protected static final String MSG_USERNOTEXISTENT = "Nel database non � presente l'utenza specificata.";
	protected static final String MSG_CREATEFAILED = "Errore durante la creazione dell'utenza nel database.";
	protected static final String MSG_UPDATEFAILED = "Errore durante l'aggiornamento dell'utenza nel database.";
	protected static final String MSG_DELETEFAILED = "Errore durante la cancellazione dell'utenza dal database.";
	protected static final String MSG_ACTIVATIONFAILED = "Errore durante l'attivazione dell'utenza specificata.";
	protected static final String MSG_CREATEKEYSTOREFAILED = "Errore durante la creazione del keystore per la firma remota.";
	protected static final String MSG_PASSWORDCHANGEFAIL = "Errore durante il cambio password.";
	protected static final String MSG_OLDPASSWORDINCORRECT = "La vecchia password non � corretta.";
	
	protected static final String ECODE_CREATESUCCESS = "APPLICATION.000";
	protected static final String ECODE_UPDATESUCCESS = "APPLICATION.001";
	protected static final String ECODE_DELETESUCCESS = "APPLICATION.002";
	protected static final String ECODE_ACTIVATIONSUCCESS = "APPLICATION.003";
	protected static final String ECODE_CREATEKEYSTORESUCCESS = "APPLICATION.004";
	protected static final String ECODE_PASSWORDCHANGESUCCESS = "APPLICATION.005";
	protected static final String ECODE_USERALREADYPRESENT  = "APPLICATION.100";
	protected static final String ECODE_USERNOTEXISTENT  = "APPLICATION.101";
	protected static final String ECODE_CREATEFAILED = "APPLICATION.102";
	protected static final String ECODE_UPDATEFAILED = "APPLICATION.103";
	protected static final String ECODE_DELETEFAILED = "APPLICATION.104";
	protected static final String ECODE_ACTIVATIONFAILED = "APPLICATION.105";
	protected static final String ECODE_CREATEKEYSTOREFAILED = "APPLICATION.106";
	protected static final String ECODE_PASSWORDCHANGEFAILED = "APPLICATION.107";

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

  abstract public ResRegBean executeRegistration(RegBean userRegistrationData) throws RemoteException;
  
  abstract public ResRegBean updateRegistration(RegBean userRegistrationData) throws RemoteException;
  
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
  
  public ResRegBean deleteRegistration(String codiceFiscale) throws RemoteException {
	  ResRegBean response = new ResRegBean();
	  
	  if (codiceFiscale == null) {
	      throw new RemoteException("deleteRegistration(). Codice fiscale non valido: " + codiceFiscale);
	  }
	    
	  if(isUserRegistered(codiceFiscale)){
		  
		  Connection conn = null;
		  Statement statement = null;
		  String dbQuery = "DELETE FROM USERDATA WHERE CODICE_FISCALE='" + codiceFiscale +"'";
	
		  try {
		      conn = getConnection();
		      statement = conn.createStatement();
		      int result = statement.executeUpdate(dbQuery);
	          if(result == 1){
		        
		          if(logger.isDebugEnabled())
		            logger.debug("Utente rimosso.");
		          
		          response.setEsito("OK");
		          response.setMessaggio(MSG_DELETESUCCESS);
		          response.setErrorCode(ECODE_DELETESUCCESS);
		          response.setCodiceFiscale(codiceFiscale.toUpperCase());
		          response.setUserId(codiceFiscale.toUpperCase());
		          
		          statement.close();
		          conn.close();
		          
		          return response;
		       } else{
		    	
		    	  response.setEsito("FAILED");
		       	  response.setMessaggio(MSG_DELETEFAILED);
		       	  response.setErrorCode(ECODE_DELETEFAILED);
		          return response;
		       }
	     
		  } catch (NamingException e) {
			    if (logger.isDebugEnabled()) {
			      logger.debug("deleteRegistration(). Error reading datasources: " + e.getMessage());
			    }
			    logger.error("deleteRegistration(Codice fiscale = " + codiceFiscale + ") - exception", e);
		        throw new RemoteException("deleteRegistration(). Exception: " + e.getMessage());

		  } catch (SQLException e) {
		  	    if (logger.isDebugEnabled()) {
			      logger.debug("deleteRegistration() - Error creating connection: " + e.getMessage());
			    }
		        throw new RemoteException("deleteRegistration(). Exception: " + e.getMessage());
		  } catch (Exception e) {
		        if (logger.isDebugEnabled()) {
		          logger.debug("deleteRegistration(). Exception: " + e.getMessage());
		        }
		        throw new RemoteException("deleteRegistration(). Exception: " + e.getMessage());
		  } finally {
		      try {
  		    	statement.close();
		        conn.close();
		      } catch (SQLException e) {
		        if (logger.isDebugEnabled()) {
		          logger.debug("deleteRegistration(). Can't close connection: " + e.getMessage());
		        }
		      }
		  }
	  } else {
		  response.setEsito("FAILED");
       	  response.setMessaggio(MSG_DELETEFAILED);
       	  response.setErrorCode(ECODE_DELETEFAILED);
          return response;
	  }
  }
  
  public boolean isUserRegistered(String codiceFiscale) throws RemoteException {
		  
    boolean response = false;
	  
    if (codiceFiscale == null) {
      throw new RemoteException("WS isUserRegistered(). Codice fiscale non valido: " + codiceFiscale);
    }
    
    Connection userdbConn = null;
    PreparedStatement statement = null;
    ResultSet userdbResultset = null;
    String dbQuery = "SELECT CODICE_FISCALE FROM USERDATA WHERE CODICE_FISCALE=?";

    try {
      userdbConn = getConnection();
      statement = userdbConn.prepareStatement(dbQuery);
      statement.setString(1, codiceFiscale.toUpperCase());
      userdbResultset = statement.executeQuery();
      if (userdbResultset.next()) {
    	  String cf = userdbResultset.getString("CODICE_FISCALE").trim();
    	  if(cf==null){
    		  response = false;
    	  } else {
    		  response = true;
    	  }
      } else {
    	  response = false;
      }
    } catch (NamingException e) {
	    if (logger.isDebugEnabled()) {
	      logger.debug("WS isUserRegistered(). Error reading datasources: " + e.getMessage());
	    }
	    logger.error("isUserRegistered(Codice Fiscale = " + codiceFiscale + ") - exception", e);
	    throw new RemoteException("WS isUserRegistered(). Error creating driver class: " + e.getMessage());      
    } catch (SQLException e) {
  	    if (logger.isDebugEnabled()) {
	      logger.debug("isUserRegistered() - Error creating connection: " + e.getMessage());
	    }
	    throw new RemoteException("WS isUserRegistered(). Error creating connection: " + e.getMessage());
    } catch (Exception e) {
        if (logger.isDebugEnabled()) {
          logger.debug("WS isUserRegistered(). Exception: " + e.getMessage());
        }
        throw new RemoteException("WS isUserRegistered(). Exception: " + e.getMessage());
    } finally {
      try {
      	userdbResultset.close();
        statement.close();
      	userdbConn.close();
      } catch (SQLException e) {
        if (logger.isDebugEnabled()) {
          logger.debug("WS isUserRegistered(). Can't close connection: " + e.getMessage());
        }
      }
    }
    
    return response;
  }

  public ResRegBean changePassword(String codiceFiscale, String oldPassword, String newPassword) throws RemoteException {
	  
  	if(codiceFiscale==null || oldPassword == null || newPassword == null || "".equalsIgnoreCase(codiceFiscale) || "".equalsIgnoreCase(oldPassword) || "".equalsIgnoreCase(newPassword)){
  		throw new RemoteException("changePassword() - parametri di ingresso non validi");
  	}
  	
  	ResRegBean response = new ResRegBean();
    Connection userdbConn = null;
    ResultSet userdbResultset = null;
    PreparedStatement statement = null;
    Statement statement2 = null;
    String dbQuery = "SELECT PASSWORD FROM USERDATA WHERE CODICE_FISCALE=?";

    try {
      userdbConn = getConnection();
      statement = userdbConn.prepareStatement(dbQuery);
      statement.setString(1, codiceFiscale.toUpperCase());
      userdbResultset = statement.executeQuery();
      if (userdbResultset.next()) {
      	String password = userdbResultset.getString("PASSWORD");
      	if(oldPassword.equals(password)){
      		statement2 = userdbConn.createStatement();
          dbQuery = "UPDATE USERDATA SET PASSWORD = '" + newPassword + "' WHERE CODICE_FISCALE = '" + codiceFiscale + "'";
          
          int result = statement2.executeUpdate(dbQuery);

          if(result == 1) {
          
            if(logger.isDebugEnabled())
              logger.debug("changePassword() - Password cambiata.");
            
            response.setEsito("OK");
            response.setMessaggio(MSG_PASSWORDCHANGESUCCESS);
            response.setErrorCode(ECODE_PASSWORDCHANGESUCCESS);
            
            return response;
          } else {

            if(logger.isDebugEnabled())
          	  logger.debug("changePassword() - Errore durante l'aggiornamento dell'utente.");
          	
         	  response.setEsito("FAILED");
         	  response.setMessaggio(MSG_USERNOTEXISTENT);
         	  response.setErrorCode(ECODE_PASSWORDCHANGEFAILED);
         	  return response;
          }
      	} else {
      		
      		if(logger.isDebugEnabled())
        	  logger.debug("changePassword() - Errore durante l'aggiornamento dell'utente: la vecchia password non � corretta.");

      		response.setEsito("FAILED");
       	  response.setMessaggio(MSG_OLDPASSWORDINCORRECT);
       	  response.setErrorCode(ECODE_PASSWORDCHANGEFAILED);
       	  return response;
      	}
      } else {
      	if(logger.isDebugEnabled())
      	  logger.debug("changePassword() - Errore durante l'aggiornamento dell'utente: codice fiscale = " + codiceFiscale + " non trovato.");

    		response.setEsito("FAILED");
     	  response.setMessaggio(MSG_PASSWORDCHANGEFAIL);
     	  response.setErrorCode(ECODE_PASSWORDCHANGEFAILED);
     	  return response;
      }
    } catch (Exception ex){
    	if(logger.isDebugEnabled())
    	  logger.debug("changePassword() - Errore durante l'aggiornamento dell'utente: la vecchia password non � corretta.");

  		response.setEsito("FAILED");
   	  response.setMessaggio(MSG_PASSWORDCHANGEFAIL);
   	  response.setErrorCode(ECODE_PASSWORDCHANGEFAILED);
   	  return response;
    } finally {
    	try{
    		userdbResultset.close();
    		statement.close();
    		statement2.close();
    		userdbConn.close();
    	} catch(Exception e){
    	  logger.debug("changePassword() - Errore durante la chiusura della connessione.");
    	}
    }
    
  }
  
  public ComuneBean getComuneByCodiceBelfiore(String codiceBelfiore) throws RemoteException {
		
  	if (codiceBelfiore == null) {
      throw new RemoteException("getComuneByCodiceBelfiore(). Codice belfiore non valido: " + codiceBelfiore);
    }
    
  	ComuneBean comuneBean = new ComuneBean();
  	
    Connection userdbConn = null;
    PreparedStatement statement = null;
    ResultSet userdbResultset = null;
    String dbQuery = "SELECT * FROM COMUNI WHERE CODICE_COMUNE=?";

    try {
      userdbConn = getConnection();
      statement = userdbConn.prepareStatement(dbQuery);
      statement.setString(1, codiceBelfiore);
      userdbResultset = statement.executeQuery();
      if (userdbResultset.next()) {
    	 comuneBean.setNome(userdbResultset.getString("COMUNE"));
    	 comuneBean.setProvincia(userdbResultset.getString("PROVINCIA"));
    	 comuneBean.setRegione(userdbResultset.getString("REGIONE"));
    	 comuneBean.setCap(userdbResultset.getString("CAP"));
    	 comuneBean.setCodiceComune(userdbResultset.getString("CODICE_COMUNE"));
    	 comuneBean.setCodiceIstat(userdbResultset.getString("CODICE_ISTAT"));
      }
    } catch (NamingException e) {
	    if (logger.isDebugEnabled()) {
	      logger.debug("getComuneByCodiceBelfiore(). Error reading datasources: " + e.getMessage());
	    }
	    logger.error("getComuneByCodiceBelfiore(Codice Belfiore = " + codiceBelfiore + ") - exception", e);
	    throw new RemoteException("getComuneByCodiceBelfiore(). Error creating driver class: " + e.getMessage());      
    } catch (SQLException e) {
  	    if (logger.isDebugEnabled()) {
	      logger.debug("getComuneByCodiceBelfiore() - Error creating connection: " + e.getMessage());
	    }
	    throw new RemoteException("getComuneByCodiceBelfiore(). Error creating connection: " + e.getMessage());
    } catch (Exception e) {
        if (logger.isDebugEnabled()) {
          logger.debug("getComuneByCodiceBelfiore(). Exception: " + e.getMessage());
        }
        throw new RemoteException("getComuneByCodiceBelfiore(). Exception: " + e.getMessage());
    } finally {
      try {
      	userdbResultset.close();
        statement.close();
        userdbConn.close();
      } catch (SQLException e) {
        if (logger.isDebugEnabled()) {
          logger.debug("getComuneByCodiceBelfiore(). Can't close connection: " + e.getMessage());
        }
      }
    }
  
    return comuneBean;
	  
  }
  
  public ComuneBean getComuneByCodiceIstat(String codiceIstat) throws RemoteException {
		
  	if (codiceIstat == null) {
      throw new RemoteException("getComuneByCodiceIstat(). Codice Istat non valido: " + codiceIstat);
    }
    
  	ComuneBean comuneBean = new ComuneBean();
  	
    Connection userdbConn = null;
    PreparedStatement statement = null;
    ResultSet userdbResultset = null;
    String dbQuery = "SELECT * FROM COMUNI WHERE CODICE_ISTAT=?";

    try {
      userdbConn = getConnection();
      statement = userdbConn.prepareStatement(dbQuery);
      statement.setString(1, codiceIstat);
      userdbResultset = statement.executeQuery();
      if (userdbResultset.next()) {
    	 comuneBean.setNome(userdbResultset.getString("COMUNE"));
    	 comuneBean.setProvincia(userdbResultset.getString("PROVINCIA"));
    	 comuneBean.setRegione(userdbResultset.getString("REGIONE"));
    	 comuneBean.setCap(userdbResultset.getString("CAP"));
    	 comuneBean.setCodiceComune(userdbResultset.getString("CODICE_COMUNE"));
    	 comuneBean.setCodiceIstat(userdbResultset.getString("CODICE_ISTAT"));
      }
    } catch (NamingException e) {
	    if (logger.isDebugEnabled()) {
	      logger.debug("getComuneByCodiceBelfiore(). Error reading datasources: " + e.getMessage());
	    }
	    logger.error("getComuneByCodiceBelfiore(Codice Belfiore = " + codiceIstat + ") - exception", e);
	    throw new RemoteException("getComuneByCodiceBelfiore(). Error creating driver class: " + e.getMessage());      
    } catch (SQLException e) {
  	    if (logger.isDebugEnabled()) {
	      logger.debug("getComuneByCodiceBelfiore() - Error creating connection: " + e.getMessage());
	    }
	    throw new RemoteException("getComuneByCodiceBelfiore(). Error creating connection: " + e.getMessage());
    } catch (Exception e) {
        if (logger.isDebugEnabled()) {
          logger.debug("getComuneByCodiceBelfiore(). Exception: " + e.getMessage());
        }
        throw new RemoteException("getComuneByCodiceBelfiore(). Exception: " + e.getMessage());
    } finally {
      try {
        userdbResultset.close();
        statement.close();
        userdbConn.close();
      } catch (SQLException e) {
        if (logger.isDebugEnabled()) {
          logger.debug("getComuneByCodiceBelfiore(). Can't close connection: " + e.getMessage());
        }
      }
    }
  
    return comuneBean;
	  
  }
}
