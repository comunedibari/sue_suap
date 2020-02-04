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

import it.intersail.people.siracl.ldap.PeopleUser;
import it.intersail.people.siracl.ldap.PeopleUsers;
import it.people.sirac.idp.beans.ResAuthBean;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationImplLDAP implements AuthenticationInterface {
  /**
   * Logger for this class
   */
  private static final Logger logger = LoggerFactory.getLogger(AuthenticationImplLDAP.class);

  private final String APPLICATION4000 = "APPLICATION.4000"; //Autenticazione
                                                             // Fallita
                                                             // (Generico codice
                                                             // di errore di
                                                             // autenticazione)

  private final String APPLICATION4100 = "APPLICATION.4100"; //Utente/Codice
                                                             // Fiscale
                                                             // sconosciuto e/o 
                                                             // password non valida

  private final String APPLICATION4200 = "APPLICATION.4200"; //Utente/Codice
                                                             // Fiscale
                                                             // sconosciuto e/o 
                                                             // PIN non valida

  private static final String MSG_SUCCESS = "SUCCESSFUL AUTHENTICATION";
  private static final String MSG_CF_PWD = "CODICE FISCALE E/O PASSWORD ERRATI";
  private static final String MSG_CF_PIN = "CODICE FISCALE E/O PIN ERRATI";
  private static final String MSG_INITFAILED = "ERRORE DURANTE INIZIALIZZAZIONE DELLA USER FACTORY";
  private static final String MSG_CONTROLLI = "CODICE FISCALE E/O PASSWORD NULLI";
  
  private static final String ECODE_SUCCESS = "WS_AUTH.000";
  private static final String ECODE_CONTROLLI = "WS_AUTH.001";
  private static final String ECODE_INITFAILED = "WS_AUTH.003";

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
  
  public ResAuthBean executeBasicAuthentication(String userID, String password) {
    ResAuthBean response = new ResAuthBean();

    Date now = new Date();

    response.setTimestamp(now.toString());

    if (userID == null || password == null) {
      response.setEsito("FAILED");
      response.setMessaggio(MSG_CONTROLLI);
      response.setErrorCode(ECODE_CONTROLLI);
      if (logger.isDebugEnabled()) {
        logger
            .debug("executeBasicAuthentication() - Autenticazione Fallita. UserID="
                + userID + "Password=" + password);
      }
      return response;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("executeBasicAuthentication() - DEBUG: PRIMA CONNESSIONE");
    }

    PeopleUsers users = null;
    try {
      users = createPeopleUsers();
    } catch (NamingException e) {
      logger.error("executeBasicAuthentication: " + e.getMessage());
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
    
    if (logger.isDebugEnabled()) {
      logger.debug("executeBasicAuthentication() - DEBUG: DOPO CONNESSIONE");
    }

    if(users == null)
    {
      logger.error("Strange error: PeopleUsers == null. Check your code, please...");
    
      response.setEsito("FAILED");
      response.setMessaggio("This shouldn't have happened...");
      response.setErrorCode("STRANGE.001");
      return response;
    }
      
    PeopleUser user = null; 
    try {
      if(logger.isDebugEnabled())
        logger.debug("Authenticate PeopleUser");
      
      String uClass = getEnvVar("LDAPUserClass");
      
      if(logger.isDebugEnabled())
        logger.debug("User class = " + uClass);
   
      user = users.getUser(userID.toUpperCase());
        
      String resCodiceFiscale = user.getAttributeValue("peopleCodiceFiscale").trim();
      String resPassword = user.getAttributeValue("peoplePassword").trim();
      
      if (resCodiceFiscale != null && resPassword != null) {
        if (password.equals(resPassword)) {
          response.setEsito("OK");
          response.setErrorCode(APPLICATION4100);
          response.setMessaggio(MSG_CF_PWD);
          response.setCodiceFiscale(resCodiceFiscale.toUpperCase());
          response.setUserId(resCodiceFiscale.toUpperCase());
        }
      } else {
        response.setEsito("FAILED");
        response.setErrorCode(APPLICATION4000);
        if (logger.isDebugEnabled()) {
          logger
              .debug("executeBasicAuthentication() - Autenticazione Fallita. UserID="
                  + userID + "Password=" + password);
        }
      }
    } catch (Exception e) {
      logger.error("executeRegistration - exception in createUser: "+e.getMessage());

      if(logger.isDebugEnabled())
      {
        StringWriter w = new StringWriter();
        e.printStackTrace(new PrintWriter(w));
          
        logger.error("Exception stack:\n"+w);
      }
        
      response.setEsito("FAILED");
      response.setErrorCode(APPLICATION4000);
      if (logger.isDebugEnabled()) {
        logger .debug("executeBasicAuthentication() - Autenticazione Fallita. UserID="
              + userID + "Password=" + password);
      }
    }
      
    if (logger.isDebugEnabled()) {
      logger.debug("executeBasicAuthentication() - DEBUG: FINE");
    }
    
    return response;
  }

  public it.people.sirac.idp.beans.ResAuthBean executePINAuthentication(String userID, String pin) {
    it.people.sirac.idp.beans.ResAuthBean response = new it.people.sirac.idp.beans.ResAuthBean();

    Date now = new Date();

    response.setTimestamp(now.toString());

    if (userID == null || pin == null) {
      response.setEsito("FAILED");
      response.setMessaggio(MSG_CONTROLLI);
      response.setErrorCode(ECODE_CONTROLLI);
      if (logger.isDebugEnabled()) {
        logger
            .debug("executePINAuthentication() - Autenticazione Fallita. UserID="
                + userID + "PIN=" + pin);
      }
      return response;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("executePINAuthentication() - DEBUG: PRIMA CONNESSIONE");
    }

    PeopleUsers users = null;
    try {
      users = createPeopleUsers();
    } catch (NamingException e) {
      logger.error("executePINAuthentication: " + e.getMessage());
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
    
    if (logger.isDebugEnabled()) {
      logger.debug("executePINAuthentication() - DEBUG: DOPO CONNESSIONE");
    }

    if(users == null)
    {
      logger.error("Strange error: PeopleUsers == null. Check your code, please...");
    
      response.setEsito("FAILED");
      response.setMessaggio("This shouldn't have happened...");
      response.setErrorCode("STRANGE.001");
      return response;
    }
      
    PeopleUser user = null; 
    try {
      if(logger.isDebugEnabled())
        logger.debug("Authenticate PeopleUser");
      
      String uClass = getEnvVar("LDAPUserClass");
      
      if(logger.isDebugEnabled())
        logger.debug("User class = " + uClass);
   
      user = users.getUser(userID.toUpperCase());
        
      String resCodiceFiscale = user.getAttributeValue("peopleCodiceFiscale").trim();
      String resPin = user.getAttributeValue("peoplePin").trim();
      
      if (resCodiceFiscale != null && resPin != null) {
        if (pin.equals(resPin)) {
          response.setEsito("OK");
          response.setMessaggio(MSG_SUCCESS);
          response.setErrorCode(ECODE_SUCCESS);
          response.setCodiceFiscale(resCodiceFiscale.toUpperCase());
          response.setUserId(resCodiceFiscale.toUpperCase());
        }
      } else {
        response.setEsito("FAILED");
        response.setMessaggio(MSG_CF_PIN);
        response.setErrorCode(APPLICATION4200);
        if (logger.isDebugEnabled()) {
          logger
              .debug("executeBasicAuthentication() - Autenticazione Fallita. UserID="
                  + userID + "Password=" + pin);
        }
      }
    } catch (Exception e) {
      logger.error("executeRegistration - exception in createUser: "+e.getMessage());

      if(logger.isDebugEnabled())
      {
        StringWriter w = new StringWriter();
        e.printStackTrace(new PrintWriter(w));
          
        logger.error("Exception stack:\n"+w);
      }
        
      response.setEsito("FAILED");
      response.setErrorCode(APPLICATION4000);
      if (logger.isDebugEnabled()) {
        logger .debug("executeBasicAuthentication() - Autenticazione Fallita. UserID="
              + userID + "Password=" + pin);
      }
    }
      
    if (logger.isDebugEnabled()) {
      logger.debug("executeBasicAuthentication() - DEBUG: FINE");
    }
    
    return response;  
  }

//  public it.people.sirac.idp.beans.RegBean getUserData(
//      String userID, String password) throws java.rmi.RemoteException {
//    logger.debug("DENTRO WEBSERVICE - getUserData");
//    it.people.sirac.idp.beans.RegBean response = new it.people.sirac.idp.beans.RegBean();
//
//    if (userID == null || password == null) {
//      throw new RemoteException(
//          "WS getUserData(). Recupero dati utente fallita. UserID=" + userID
//              + "Password=" + password);
//    }
//    
//    Connection conn = null;
//    
//    try {
//     
//      Class.forName("com.novell.sql.LDAPDriver");
//      Driver driver = DriverManager.getDriver("jdbc:ldap");
//
//      String server = "localhost";
//      String rootUser = "cn=manager,dc=cefriel,dc=it";
//      String rootPassword = "people";
//      String baseDN = "dc=ca-people,dc=cefriel,dc=it";
//      
//      String url = "jdbc:ldap://" + server +
//            ";user=" + rootUser +
//            ";password=" + rootPassword +
//            ";baseDN=" + baseDN +
//            ";useCleartext=true";
//
//      conn = driver.connect(url, null);
//      
//      String userTableFields = "peopleCodiceFiscale,peopleEmailAddress,peopleNome,peopleCognome,peopleDataNascita,"
//        + "peopleCapDomicilio,peopleCapResidenza,peopleCittaDomicilio,peopleCittaResidenza,"
//        + "peopleIndirizzoDomicilio,peopleIndirizzoResidenza,peopleLavoro,peopleLuogoNascita,"
//        + "peopleProvinciaDomicilio,peopleProvinciaNascita,peopleProvinciaResidenza,peopleSesso,"
//        + "peopleStatoDomicilio,peopleStatoResidenza,peopleTelefono,peopleTitolo,peopleIdComuneRegistrazione,peoplePassword,peoplePin";
//      String dbQuery = "SELECT " + userTableFields
//        + " FROM UtentePeople WHERE peopleCodiceFiscale='" + userID + "' AND peoplePassword='"+ password +"'";
//      Statement statement = conn.createStatement();
//      ResultSet userdbResultset = null;
//      userdbResultset = statement.executeQuery(dbQuery);
//      //Controllo che la query abbia restituito l'utente cercato
//      if (userdbResultset.next()) {
//        String resCodiceFiscale = userdbResultset.getString("peopleCodiceFiscale")
//            .trim();
//        String resPassword = userdbResultset.getString("peoplePassword").trim();
//        if (resCodiceFiscale != null && resPassword != null) {
//          if (userID.equalsIgnoreCase(resCodiceFiscale) && password.equals(resPassword)) {
//            // Preparazione resRegBean di risposta
//            response.setGivenName(userdbResultset.getString("peopleNome"));
//            response.setSn(userdbResultset.getString("peopleCognome"));
//            response.setPiCodiceFiscale(userdbResultset.getString("peopleCodiceFiscale"));
//            response.setEmailaddress(userdbResultset.getString("peopleEmailAddress"));
//            response.setPiDataNascita(userdbResultset.getString("peopleDataNascita"));
//            response.setPiLuogoNascita(userdbResultset.getString("peopleLuogoNascita"));
//            response.setPiProvinciaNascita(userdbResultset.getString("peopleProvinciaNascita"));
//            response.setPiLavoro(userdbResultset.getString("peopleLavoro"));
//            response.setPiSesso(userdbResultset.getString("peopleSesso"));
//            response.setPiTelefono(userdbResultset.getString("peopleTelefono"));
//            response.setPiTitolo(userdbResultset.getString("peopleTitolo"));
//            response.setPiResidenza(userdbResultset.getString("peopleIndirizzoResidenza"));
//            response.setPiPostalAddress(userdbResultset.getString("peopleIndirizzoDomicilio"));
//            response.setPiCitta(userdbResultset.getString("peopleCittaResidenza"));
//            response.setLocalityName(userdbResultset.getString("peopleCittaDomicilio"));
//            response.setPiCap(userdbResultset.getString("peopleCapResidenza"));
//            response.setPostalCode(userdbResultset.getString("peopleCapDomicilio"));
//            response.setPiProvincia(userdbResultset.getString("peopleProvinciaResidenza"));
//            response.setStateProvinceName(userdbResultset.getString("peopleProvinciaDomicilio"));
//            response.setPiStato(userdbResultset.getString("peopleStatoResidenza"));
//            response.setPiStatoPos(userdbResultset.getString("peopleStatoDomicilio"));
//            response.setIdComuneRegistrazione(userdbResultset.getString("peopleIdComuneRegistrazione"));
//          }
//        } else {
//          throw new RemoteException(
//              "WS getUserData(). Autenticazione Fallita. UserID=" + userID
//                  + " Password=" + password);
//        }
//      } else {
//        throw new RemoteException(
//            "WS getUserData(). Autenticazione Fallita. UserID=" + userID
//                + " Password=" + password);
//      }
//      statement.close();
//
//    } catch (SQLException e) {
//      if (logger.isDebugEnabled()) {
//        logger
//            .debug("getUserData() - WS getUserData(). Error creating connection: "
//                + e.getMessage());
//      }
//      logger.error("getUserData(userID = " + userID + ", password = "
//          + password + ") - exception", e);
//      throw new RemoteException("WS getUserData(). Error creating connection: "
//          + e.getMessage());
//    } catch (Exception e) {
//      if (logger.isDebugEnabled()) {
//        logger.debug("getUserData() - WS getUserData(). Exception: "
//            + e.getMessage());
//      }
//      logger.error("getUserData(userID = " + userID + ", password = "
//          + password + ") - exception", e);
//      throw new RemoteException("WS getUserData(). Exception: "
//          + e.getMessage());
//    } finally {
//      try {
//        conn.close();
//      } catch (SQLException e) {
//        logger.error("getUserData(userID = " + userID + ", password = "
//            + password + ") - exception", e);
//        if (logger.isDebugEnabled()) {
//          logger
//              .debug("getUserData() - WS getUserData(). Can't close connection: "
//                  + e.getMessage());
//        }
//      }
//    }
//
//    return response;
//  }
  
  
  public it.people.sirac.idp.beans.RegBean getUserData(
      String codiceFiscale, String password) throws java.rmi.RemoteException {
    
    it.people.sirac.idp.beans.RegBean response = new it.people.sirac.idp.beans.RegBean();

    if (codiceFiscale == null || password == null) {
      throw new RemoteException(
          "getUserData(). Impossibile accedere ai dati utente. UserID=" + codiceFiscale + "Password=" + password);
    }

    try {
      PeopleUsers users = createPeopleUsers();
      
      if (logger.isDebugEnabled()) {
        logger.debug("getUserData() - DEBUG: DOPO CONNESSIONE");
      }

      PeopleUser user = users.getUser(codiceFiscale.toUpperCase());
      String resCodiceFiscale = user.getAttributeValue("peopleCodiceFiscale").trim();
      String resPassword = user.getAttributeValue("peoplePassword").trim();
      if (resCodiceFiscale != null && resPassword != null) {
        if (codiceFiscale.equalsIgnoreCase(resCodiceFiscale) && password.equals(resPassword)) {
          response.setNome(user.getAttributeValue("peopleNome"));
          response.setCognome(user.getAttributeValue("peopleCognome"));
          response.setCodiceFiscale(user.getAttributeValue("peopleCodiceFiscale").toUpperCase());
          response.setEmail(user.getAttributeValue("peopleEmailAddressPersonale"));
          response.setDataNascita(user.getAttributeValue("peopleDataNascita"));
          response.setLuogoNascita(user.getAttributeValue("peopleLuogoNascita"));
          response.setProvinciaNascita(user.getAttributeValue("peopleProvinciaNascita"));
          response.setStatoNascita(user.getAttributeValue("peopleStatoNascita"));
          response.setLavoro(user.getAttributeValue("peopleLavoro"));
          response.setSesso(user.getAttributeValue("peopleSesso"));
          response.setTelefono(user.getAttributeValue("peopleTelefono"));
          response.setTitolo(user.getAttributeValue("peopleTitolo"));
          response.setIndirizzoResidenza(user.getAttributeValue("peopleIndirizzoResidenza"));
          response.setIndirizzoDomicilio(user.getAttributeValue("peopleIndirizzoDomicilio"));
          response.setCittaResidenza(user.getAttributeValue("peopleCittaResidenza"));
          response.setCittaDomicilio(user.getAttributeValue("peopleCittaDomicilio"));
          response.setCapResidenza(user.getAttributeValue("peopleCapResidenza"));
          response.setCapDomicilio(user.getAttributeValue("peopleCapDomicilio"));
          response.setProvinciaResidenza(user.getAttributeValue("peopleProvinciaResidenza"));
          response.setProvinciaDomicilio(user.getAttributeValue("peopleProvinciaDomicilio"));
          response.setStatoResidenza(user.getAttributeValue("peopleStatoResidenza"));
          response.setStatoDomicilio(user.getAttributeValue("peopleStatoDomicilio"));
          response.setDomicilioElettronico(user.getAttributeValue("peopleDomicilioElettronico"));
          response.setIdComune(user.getAttributeValue("peopleIdComuneRegistrazione"));
          response.setCartaIdentita(user.getAttributeValue("peopleCodiceCarta"));
          response.setPassword(user.getAttributeValue("peoplePassword"));
          response.setPin(user.getAttributeValue("peoplePin"));
          
       } else{
            throw new RemoteException(
                "getUserData(). Autenticazione Fallita. Codice Fiscale=" + codiceFiscale
                    + " Password=" + password);
        }
      } else {
        throw new RemoteException(
            "getUserData(). Autenticazione Fallita. Codice Fiscale=" + codiceFiscale 
                + " Password=" + password);
      }
    } catch (Exception e) {
      logger.error("getUserData(): " + e.getMessage());
      if(logger.isDebugEnabled())
      {
        StringWriter w = new StringWriter();
        e.printStackTrace(new PrintWriter(w));
        logger.error("Exception stack:\n"+w);     
        throw new RemoteException(e.getMessage());
      }
    }

    return response;
  }

  public it.people.sirac.idp.beans.RegBean getUserDataWithCIE(
      String CIE_ID) throws java.rmi.RemoteException {
    
    it.people.sirac.idp.beans.RegBean response = null;
    if (CIE_ID == null) {
      throw new RemoteException(
          "getUserDataWithCIE(). Unable to read user data. CIE card number = " + CIE_ID);
    }
    
    String userID = null;
    String password = null;
    
    try {
      PeopleUsers users = createPeopleUsers();
      PeopleUser user = users.getUser("peopleCodiceCarta", CIE_ID);

      if(user == null){
        throw new RemoteException(
            "getUserDataWithCIE - Nessun utente trovato corrispondente al numero di carta d'identit� " + CIE_ID);
      } else {
        userID = user.getAttributeValue("peopleCodiceFiscale").trim();
        password = user.getAttributeValue("peoplePassword").trim();
        response = getUserData(userID, password);   
      }
    } catch (Exception e) {
      if (logger.isDebugEnabled()) {
        logger.debug("getUserDataWithCIE() - Exception: "
            + e.getMessage());
      }
      logger.error("getUserDataWithCIE(userID = " + userID + ", password = "
          + password + ") - exception", e);
      throw new RemoteException("getUserDatWithCIE() - Exception: "
          + e.getMessage());
    }
    
    return response;
    
  }
  
//  public it.people.sirac.idp.beans.RegBean getUserDataWithCIE(
//      String CIE_ID) throws java.rmi.RemoteException {
//    
//    it.people.sirac.idp.beans.RegBean response = null;
//    if (CIE_ID == null) {
//      throw new RemoteException(
//          "ws_auth - getUserDataWithCIE(). Unable to read user data. CIE card number = " + CIE_ID);
//    }
//    
//    String userID = null;
//    String password = null;
//    Connection conn = null;
//    
//    try {
//     
//      Class.forName("com.novell.sql.LDAPDriver");
//      Driver driver = DriverManager.getDriver("jdbc:ldap");
//
//      String server = "localhost";
//      String rootUser = "cn=manager,dc=cefriel,dc=it";
//      String rootPassword = "people";
//      String baseDN = "dc=ca-people,dc=cefriel,dc=it";
//      
//      String url = "jdbc:ldap://" + server +
//            ";user=" + rootUser +
//            ";password=" + rootPassword +
//            ";baseDN=" + baseDN +
//            ";useCleartext=true";
//
//      conn = driver.connect(url, null);
//
//      String query = "SELECT peopleCodiceFiscale, peoplePassword FROM UtentePeople WHERE peopleCodiceCarta='"+CIE_ID+"'";
//      Statement statement = conn.createStatement();
//      ResultSet result = statement.executeQuery(query);
//      if (result.next()) {
//       userID = result.getString("peopleCodiceFiscale").trim();
//       password = result.getString("peoplePassword").trim();
//       response = getUserData(userID, password);   
//      } else {
//        // NO CARTA D'IDENTITA' NEL DB!!!
//        throw new RemoteException(
//            "WS getUserDataWithCIE - The provided CIE card number couldn't be found in the DB");
//      }
//      statement.close();
//    } catch (SQLException e) {
//      if (logger.isDebugEnabled()) {
//        logger
//            .debug("getUserDataWithCIE() - WS getUserDataWithCIE(). Error creating connection: "
//                + e.getMessage());
//      }
//      logger.error("getUserDataWithCIE(userID = " + userID + ", password = "
//          + password + ") - exception", e);
//      throw new RemoteException("WS getUserDataWithCIE(). Error creating connection: "
//          + e.getMessage());
//    } catch (Exception e) {
//      if (logger.isDebugEnabled()) {
//        logger.debug("getUserDataWithCIE() - WS getUserDataWithCIE(). Exception: "
//            + e.getMessage());
//      }
//      logger.error("getUserDataWithCIE(userID = " + userID + ", password = "
//          + password + ") - exception", e);
//      throw new RemoteException("WS getUserDatWithCIE(). Exception: "
//          + e.getMessage());
//    } finally {
//      try {
//        conn.close();
//      } catch (SQLException e) {
//        logger.error("getUserDataWithCIE(userID = " + userID + ", password = "
//            + password + ") - exception", e);
//        if (logger.isDebugEnabled()) {
//          logger
//              .debug("getUserDataWithCIE() - WS getUserDataWithCIE(). Can't close connection: "
//                  + e.getMessage());
//        }
//      }
//    }      
//    
//    return response;
//    
//  }
  
  public it.people.sirac.idp.beans.RegBean getUserDataWithCodiceFiscale(
      String COD_FIS) throws java.rmi.RemoteException {
    
    it.people.sirac.idp.beans.RegBean response = null;
    if (COD_FIS == null) {
      throw new RemoteException(
          "getUserDataWithCodiceFiscale(). Recupero dati utente fallita. CodiceFiscale = " + COD_FIS);
    }
    
    String userID = null;
    String password = null;
    
    try {
      PeopleUsers users = createPeopleUsers();
      PeopleUser user = users.getUser("peopleCodiceFiscale", COD_FIS.toUpperCase());

      if(user == null){
        throw new RemoteException(
            "getUserDataWithCodiceFiscale - Nessun utente trovato corrispondente al codice fiscale: " + COD_FIS);
      } else {
        userID = user.getAttributeValue("peopleCodiceFiscale").trim();
        password = user.getAttributeValue("peoplePassword").trim();
        response = getUserData(userID, password);   
      }
    } catch (Exception e) {
      if (logger.isDebugEnabled()) {
        logger.debug("getUserDataWithCodiceFiscale() - Exception: "
            + e.getMessage());
      }
      logger.error("getUserDataWithCodiceFiscale(userID = " + userID + ", password = "
          + password + ") - Exception:", e);
      throw new RemoteException("getUserDatWithCodiceFiscale() - Exception: "
          + e.getMessage());
    }
    return response;
  }
  
}
