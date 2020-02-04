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

package it.people.sirac.idp.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import it.people.core.PplUserData;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AuthenticationImplDB implements AuthenticationInterface {
  /**
   * Logger for this class
   */
  private static final Logger logger = LoggerFactory.getLogger(AuthenticationImplDB.class);

  private final String DB4000 = "DB.4000";

  private final String CONTROLLI2000 = "CONTROLLI.2000";

  private final String SUCCESS0000 = "SUCCESS.0000";

  private final String APPLICATION4000 = "APPLICATION.4000"; //Autenticazione
                                                             // Fallita
                                                             // (Generico codice
                                                             // di errore di
                                                             // autenticazione)

  private final String APPLICATION4100 = "APPLICATION.4100"; // Codice Fiscale sconosciuto
                                                             // e/o password non corretta

  private final String APPLICATION4200 = "APPLICATION.4200"; // Codice Fiscale sconosciuto
                                                             // e/o PIN non corretto

  //private String connectionString = "jdbc:mysql://localhost:3306/people?user=people_demo&password=people_demo";
  //private String driverClassName = "oracle.jdbc.OracleDriver";
  //private String driverClassName = "org.gjt.mm.mysql.Driver";
  
  private static DataSource dataSources = null;
  
  private Connection getConnection() throws NamingException, SQLException
  {
  	if (dataSources == null)
  	{
  	    Context initContext = new InitialContext();
  	    dataSources = (DataSource)initContext.lookup("java:/comp/env/jdbc/WSAuthDB");  		
  	}
  	
  	return dataSources.getConnection();
  	/*
  	   Class.forName(driverClassName);
  	   return DriverManager.getConnection("jdbc:mysql://localhost:3306/people?user=people_demo&password=people_demo");
	  */  	
  }
  
  public it.people.sirac.idp.beans.ResAuthBean executeBasicAuthentication(String userID, String password) {
    it.people.sirac.idp.beans.ResAuthBean response = new it.people.sirac.idp.beans.ResAuthBean();

    Date now = new Date();

    response.setTimestamp(now.toString());

    if (userID == null || password == null) {
      response.setEsito("FAILED");
      response.setErrorCode(CONTROLLI2000);
      if (logger.isDebugEnabled()) {
        logger.debug("executeBasicAuthentication() - Authentication failed for user " + userID + " using password " + password);
      }
      return response;
    }
    Connection userdbConn = null;
    PreparedStatement statement = null;
    ResultSet userdbResultset = null;
    String dbQuery = "SELECT * FROM USERDATA WHERE CODICE_FISCALE=?";

    try {
	    if (logger.isDebugEnabled()) {
	    	logger.debug("executeBasicAuthentication() - Retrieving user repository connection...");
	    }
	    userdbConn = getConnection();
	    if (logger.isDebugEnabled()) {
	    	logger.debug("executeBasicAuthentication() - Connection retrieved");
	    }
	    statement = userdbConn.prepareStatement(dbQuery);
	    statement.setString(1, userID.toUpperCase());
	    if (logger.isDebugEnabled()) {
	    	logger.debug("executeBasicAuthentication() - Executing query...");
	    }
	    userdbResultset = statement.executeQuery();
	    if (userdbResultset.next()) {
	    	boolean isUserActive = ("ATTIVO".equalsIgnoreCase(userdbResultset.getString("STATUS")));
	    	if(isUserActive){
	    		String resCodiceFiscale = userdbResultset.getString("CODICE_FISCALE").trim();
	    		String resPassword = userdbResultset.getString("PASSWORD").trim();
	    		if (password.equals(resPassword)) {
	    			response.setEsito("OK");
	    			response.setErrorCode(SUCCESS0000);
	    			response.setCodiceFiscale(resCodiceFiscale.toUpperCase());
	    			response.setUserId(resCodiceFiscale.toUpperCase());
	    			if (logger.isDebugEnabled()) {
	    				logger.debug("executeBasicAuthentication() - Authentication successful for user " + userID);
	    			}
	    		} else {
	    			response.setEsito("FAILED");
	    			response.setErrorCode(APPLICATION4100);
	    			if (logger.isDebugEnabled()) {
	    				logger.debug("executeBasicAuthentication() - Authentication failed: invalid password for user " + userID);
	    			}
	    		}
	    	} else {
	    		response.setEsito("FAILED");
	    		response.setErrorCode(APPLICATION4000);
	    		if (logger.isDebugEnabled()) {
	    			logger.debug("executeBasicAuthentication() - Authentication failed: the user " + userID + " is not active.");
	    		}
	    	}
	    } else {
	    	response.setEsito("FAILED");
	    	response.setErrorCode(APPLICATION4000);
	    	if (logger.isDebugEnabled()) {
	    		logger.debug("executeBasicAuthentication() - Authentication failed: the user " + userID + " is unknown.");
	    	}
	    }
    } catch (NamingException e) {
        logger.error("executeBasicAuthentication() - Error reading datasources: " + e.getMessage());
        response.setEsito("FAILED");
        response.setErrorCode(DB4000);
        return response;      
    } catch (Exception e) {
    	logger.error("executeBasicAuthentication() - Generic error: " + e.getMessage());
    	response.setEsito("FAILED");
    	response.setErrorCode(DB4000);
    } finally {
    	try {
    		userdbResultset.close();
    		statement.close();
    		userdbConn.close();
    	} catch (SQLException e) {
    		logger.error("executeBasicAuthentication() - Error while closing the connection: " + e.getMessage());
    	}
    }
    if (logger.isDebugEnabled()) {
      logger.debug("executeBasicAuthentication() - Authentication process ended.");
    }
    return response;
  }

  public it.people.sirac.idp.beans.ResAuthBean executePINAuthentication(String userID, String pin) {
    it.people.sirac.idp.beans.ResAuthBean response = new it.people.sirac.idp.beans.ResAuthBean();

    Date now = new Date();

    response.setTimestamp(now.toString());

    if (userID == null || pin == null) {
    	response.setEsito("FAILED");
    	response.setErrorCode(CONTROLLI2000);
    	if (logger.isDebugEnabled()) {
    		logger.debug("executeBasicAuthentication() - Authentication failed for user " + userID + " using pin " + pin);
    	}
    	return response;
    }
    Connection userdbConn = null;
    PreparedStatement statement = null;
    ResultSet userdbResultset = null;
    String dbQuery = "SELECT * FROM USERDATA WHERE CODICE_FISCALE=?";

    try {
    	if (logger.isDebugEnabled()) {
            logger.debug("executeBasicAuthentication() - Retrieving user repository connection...");
        }
        userdbConn = getConnection();
        if (logger.isDebugEnabled()) {
        	logger.debug("executeBasicAuthentication() - Connection retrieved");
        }
        statement = userdbConn.prepareStatement(dbQuery);
        statement.setString(1, userID.toUpperCase());
        if (logger.isDebugEnabled()) {
            logger.debug("executeBasicAuthentication() - Executing query...");
        }
        userdbResultset = statement.executeQuery();
	    if (userdbResultset.next()) {
	    	boolean isUserActive = ("ATTIVO".equalsIgnoreCase(userdbResultset.getString("STATUS")));
	    	if(isUserActive){
	    		String resCodiceFiscale = userdbResultset.getString("CODICE_FISCALE").trim();
	    		String resPIN = userdbResultset.getString("PIN").trim();
	    		if (pin.equals(resPIN)) {
	    			response.setEsito("OK");
	    			response.setErrorCode(SUCCESS0000);
	    			response.setCodiceFiscale(resCodiceFiscale.toUpperCase());
	    			response.setUserId(resCodiceFiscale.toUpperCase());
	    			if (logger.isDebugEnabled()) {
	    				logger.debug("executeBasicAuthentication() - Authentication successful for user " + userID);
	    			}
	    		 } else {
	    			 response.setEsito("FAILED");
	    			 response.setErrorCode(APPLICATION4200);
	    			 if (logger.isDebugEnabled()) {
	    				 logger.debug("executeBasicAuthentication() - Authentication failed: invalid pin for user " + userID);
	    			 }
	    		 }
	    	} else {
	    		response.setEsito("FAILED");
	    		response.setErrorCode(APPLICATION4000);
	    		if (logger.isDebugEnabled()) {
	    			logger.debug("executeBasicAuthentication() - Authentication failed: the user " + userID + " is not active.");
	    		}
	    	}
		} else {
			response.setEsito("FAILED");
			response.setErrorCode(APPLICATION4000);
			if (logger.isDebugEnabled()) {
				logger.debug("executeBasicAuthentication() - Authentication failed: the user " + userID + " is unknown.");
			}
		}
    } catch (NamingException e) {
        logger.error("executeBasicAuthentication() - Error reading datasources: " + e.getMessage());
        response.setEsito("FAILED");
        response.setErrorCode(DB4000);
        return response;
    } catch (Exception e) {
        logger.error("executePINAuthentication() - Generic error: " + e.getMessage());
      response.setEsito("FAILED");
      response.setErrorCode(DB4000);
    } finally {
      try {
      	userdbResultset.close();
      	statement.close();
        userdbConn.close();
      } catch (SQLException e) {
          logger.error("executePINAuthentication() - Error while closing the connection: " + e.getMessage());
      }
    }

    if (logger.isDebugEnabled()) {
        logger.debug("executeBasicAuthentication() - Authentication process ended.");
      }
    return response;
  }

  public it.people.sirac.idp.beans.RegBean getUserData(String userID, String password) throws java.rmi.RemoteException {
    it.people.sirac.idp.beans.RegBean response = new it.people.sirac.idp.beans.RegBean();

    if (userID == null || password == null) {
      throw new RemoteException("getUserData() - Failed to retrieve data for user " + userID + "using password " + password);
    }
    Connection userdbConn = null;
    PreparedStatement statement = null;
    ResultSet userdbResultset = null;

/*    String userTableFields = "NOME,COGNOME,CODICE_FISCALE,E_MAIL,"
        + "INDIRIZZO_RESIDENZA,CAP_RESIDENZA,CITTA_RESIDENZA,PROVINCIA_RESIDENZA,STATO_RESIDENZA,"
        + "LAVORO,INDIRIZZO_DOMICILIO,CAP_DOMICILIO,CITTA_DOMICILIO,PROVINCIA_DOMICILIO,STATO_DOMICILIO,"
        + "DATA_NASCITA,LUOGO_NASCITA,PROVINCIA_NASCITA,STATO_NASCITA,"
        + "SESSO,TELEFONO,CELLULARE,TITOLO,"
        + "DOMICILIO_ELETTRONICO,ID_COMUNE,CARTA_IDENTITA,PASSWORD,PIN,STATUS,DATA_REGISTRAZIONE,DATA_ATTIVAZIONE";
    String dbQuery = "SELECT " + userTableFields
        + " FROM USERDATA WHERE CODICE_FISCALE=? AND PASSWORD=?";*/
    String dbQuery = "SELECT * FROM USERDATA WHERE CODICE_FISCALE=? AND PASSWORD=?";

    try {
	  userdbConn = getConnection();
	  statement = userdbConn.prepareStatement(dbQuery);
	  statement.setString(1, userID.toUpperCase());
      statement.setString(2, password);
      userdbResultset = statement.executeQuery();
      if (userdbResultset.next()) {
    	  // Preparazione resRegBean di risposta
    	  response.setNome(userdbResultset.getString("NOME"));
    	  response.setCognome(userdbResultset.getString("COGNOME"));
    	  response.setCodiceFiscale(userdbResultset.getString("CODICE_FISCALE"));
    	  response.setEmail(userdbResultset.getString("E_MAIL"));
    	  response.setIndirizzoResidenza(userdbResultset.getString("INDIRIZZO_RESIDENZA"));
    	  response.setCapResidenza(userdbResultset.getString("CAP_RESIDENZA"));
    	  response.setCittaResidenza(userdbResultset.getString("CITTA_RESIDENZA"));
    	  response.setProvinciaResidenza(userdbResultset.getString("PROVINCIA_RESIDENZA"));
    	  response.setStatoResidenza(userdbResultset.getString("STATO_RESIDENZA"));
    	  response.setLavoro(userdbResultset.getString("LAVORO"));
    	  response.setIndirizzoDomicilio(userdbResultset.getString("INDIRIZZO_DOMICILIO"));
    	  response.setCapDomicilio(userdbResultset.getString("CAP_DOMICILIO"));
    	  response.setCittaDomicilio(userdbResultset.getString("CITTA_DOMICILIO"));
    	  response.setProvinciaDomicilio(userdbResultset.getString("PROVINCIA_DOMICILIO"));
    	  response.setStatoDomicilio(userdbResultset.getString("STATO_DOMICILIO"));
    	  response.setDataNascita(userdbResultset.getString("DATA_NASCITA"));
    	  response.setLuogoNascita(userdbResultset.getString("LUOGO_NASCITA"));
    	  response.setProvinciaNascita(userdbResultset.getString("PROVINCIA_NASCITA"));
    	  response.setStatoNascita(userdbResultset.getString("STATO_NASCITA"));
    	  response.setSesso(userdbResultset.getString("SESSO"));
    	  response.setTelefono(userdbResultset.getString("TELEFONO"));
    	  response.setCellulare(userdbResultset.getString("CELLULARE"));
    	  response.setTitolo(userdbResultset.getString("TITOLO"));
    	  response.setDomicilioElettronico(userdbResultset.getString("DOMICILIO_ELETTRONICO"));
    	  response.setIdComune(userdbResultset.getString("ID_COMUNE"));
    	  response.setCartaIdentita(userdbResultset.getString("CARTA_IDENTITA"));
    	  response.setStatus(userdbResultset.getString("STATUS"));
    	  response.setRuolo(userdbResultset.getString("RUOLO"));
    	  response.setTerritorio(userdbResultset.getString("TERRITORIO"));
    	  Date dataRegistrazione = userdbResultset.getDate("DATA_REGISTRAZIONE");
    	  Date dataAttivazione = userdbResultset.getDate("DATA_ATTIVAZIONE");
    	  if(dataAttivazione!=null){
    		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		  response.setTimestampRegistrazione(sdf.format(dataRegistrazione));
    	  } else {
    		  response.setTimestampRegistrazione("");
    	  }
    	  response.setPassword(userdbResultset.getString("PASSWORD"));
    	  response.setPin(userdbResultset.getString("PIN"));
      }
    } catch (NamingException e) {
        logger.error("getUserData() - Error reading datasources.", e);
        throw new RemoteException("getUserData() - Error reading datasources: " + e.getMessage());      
    } catch (Exception e) {
    	logger.error("getUserData() - Generic error: " + e.getMessage());
    	throw new RemoteException("getUserData() - Generic error: " + e.getMessage());
    } finally {
	    try {
	      	userdbResultset.close();
	      	statement.close();
	        userdbConn.close();
	    } catch (SQLException e) {
	        logger.error("getUserData() - Error while closing the connection: " + e.getMessage());
	    }
    }
    return response;
  }

  public it.people.sirac.idp.beans.RegBean getUserDataWithCIE(String CIE_ID) throws java.rmi.RemoteException {
    
    it.people.sirac.idp.beans.RegBean response = null;
    if (CIE_ID == null) {
      throw new RemoteException("getUserDataWithCIE() - Failed to retrieve data for CIE #" + CIE_ID);
    }
    
    Connection userdbConn = null;
    PreparedStatement statement = null;
    ResultSet userdbResultset = null;
    String dbQuery = "SELECT CODICE_FISCALE,PASSWORD FROM USERDATA WHERE CARTA_IDENTITA=?";

    String userID = null;
    String password = null;
    
    try {
    	userdbConn = getConnection();
    	statement = userdbConn.prepareStatement(dbQuery);
    	statement.setString(1, CIE_ID);
    	userdbResultset = statement.executeQuery();
    	if (userdbResultset.next()) {
    		userID = userdbResultset.getString("CODICE_FISCALE").trim();
    		password = userdbResultset.getString("PASSWORD").trim();
    		response = getUserData(userID, password);   
    	} else {
    		throw new RemoteException("getUserDataWithCIE - The provided CIE card number could not be found.");
    	}
    } catch (NamingException e) {
        logger.error("getUserDataWithCIE() - Error reading datasources: " + e.getMessage());
        throw new RemoteException("getUserDataWithCIE() - Error reading datasources: " + e.getMessage());      
    } catch (Exception e) {
    	logger.error("getUserDataWithCIE() - Generic error: " + e.getMessage());
    	throw new RemoteException("getUserDatWithCIE() - Generic error: " + e.getMessage());
    } finally {
    	try {
    		userdbResultset.close();
    		statement.close();
    		userdbConn.close();
    	} catch (SQLException e) {
    		logger.error("getUserDataWithCIE(userID = " + userID + ", password = " + password + ") - exception", e);
    	}
    }
    return response;
  }
  
  public it.people.sirac.idp.beans.RegBean getUserDataWithCodiceFiscale(String codiceFiscale) throws java.rmi.RemoteException {
    it.people.sirac.idp.beans.RegBean response = null;
    if (codiceFiscale == null) {
      throw new RemoteException("getUserDataWithCodiceFiscale() - Failed to retrieve data for user " + codiceFiscale);
    }
    
    Connection userdbConn = null;
    PreparedStatement statement = null;
    ResultSet userdbResultset = null;
    String dbQuery = "SELECT PASSWORD FROM USERDATA WHERE CODICE_FISCALE=?";

    String password = null;
    
    try {
      userdbConn = getConnection();
      statement = userdbConn.prepareStatement(dbQuery);
      statement.setString(1, codiceFiscale.toUpperCase());
      userdbResultset = statement.executeQuery();
      if (userdbResultset.next()) {
       password = userdbResultset.getString("PASSWORD").trim();
       response = getUserData(codiceFiscale, password);   
      } else {
        throw new RemoteException("getUserDataWithCodiceFiscale - The provided user code could not be found.");
      }
    } catch (NamingException e) {
        logger.error("getUserDataWithCodiceFiscale() - exception", e);
        throw new RemoteException("getUserDataWithCodiceFiscale(). Error creating driver class: " + e.getMessage());      
    } catch (Exception e) {
      logger.error("getUserDataWithCodiceFiscale() - Generic error " + e.getMessage());
      throw new RemoteException("getUserDataWithCodiceFiscale(). Generic error: " + e.getMessage());
    } finally {
      try {
      	userdbResultset.close();
      	statement.close();
        userdbConn.close();
      } catch (SQLException e) {
        logger.error("getUserDataWithCodiceFiscale() - Error while closing the connection: " + e.getMessage());
      }
    }
    return response;
  }
  
}
