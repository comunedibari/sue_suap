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
 * StatusHelper.java
 *
 * Created on 15 febbraio 2005, 18.17
 */

package it.people.util.status;

import it.people.City;
import it.people.core.CommuneManager;
import it.people.core.exception.InvalidArgumentException;
import it.people.core.persistence.exception.peopleException;
import it.people.db.DBConnector;
import it.people.exceptions.PeopleDBException;
import it.people.util.payment.EsitoPagamento;
import it.people.util.payment.PaymentException;
import it.people.util.payment.request.ObserverData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;

/**
 * Helper class per gestire lo stato delle pratiche su people
 * 
 * @author manelli
 */
public class StatusHelper {

    private static final Logger logger = Logger.getLogger(StatusHelper.class);

    protected static final String UNDEFINED_OBSERVER = "undefined";

    /** Creates a new instance of StatusHelper */
    public StatusHelper() {

    }

    /**
     * Salva lo stato di un procedimento esistente
     */
    public static void updateProcessStatus(String operationId,
	    ProcessStatus status) throws PeopleDBException {
	updateProcessStatus(operationId, status, null);
    }

    /**
     * Salva lo stato di un procedimento esistente
     */
    public static void updateProcessStatus(String operationId,
	    ProcessStatus status, EsitoPagamento esitoPagamento)
	    throws PeopleDBException {
	Connection conn = DBConnector.getInstance().connect(DBConnector.FEDB);

	try {
	    PreparedStatement statement;

	    if (esitoPagamento != null) {
		String xmlData = null;
		try {
		    xmlData = esitoPagamento.marshall();
		} catch (Exception ex) {
		    throw new PeopleDBException(
			    "Il contenuto di EsitoPagamento non e' valido.");
		}

		String updateQuery = "UPDATE process_status SET status = ?, paymentresult = ? WHERE id = ?";
		statement = conn.prepareStatement(updateQuery);
		statement.setInt(1, status.getStatusCode());
		statement.setString(2, xmlData);
		statement.setString(3, operationId);
	    } else {
		String updateQuery = "UPDATE process_status SET status = ? WHERE id = ?";
		statement = conn.prepareStatement(updateQuery);
		statement.setInt(1, status.getStatusCode());
		statement.setString(2, operationId);
	    }

	    if (statement.executeUpdate() == 0)
		throw new PeopleDBException(
			"Il record relativo allo stato del processo non esiste.");

	    statement.close();
	    conn.close();
	} catch (SQLException e) {
	    throw new PeopleDBException(e.getMessage());
	}
    }

    /**
     * Aggiorna lo stato di un procedimento esistente non andato a buon fine
     * oppure con pagamento pendente per cui si ritenta il pagamento
     */
    // public static void updateProcessStatusForRetry(
    // String operationId,
    // ProcessStatus status
    // )
    // throws PeopleDBException
    // {
    // Connection conn = DBConnector.getInstance().connect(DBConnector.FEDB);
    //
    // try {
    // PreparedStatement statement;
    //
    // saveProcessStatusHistory(conn, operationId);
    //
    // String updateQuery = "UPDATE process_status SET status = ? WHERE id = ?";
    // statement = conn.prepareStatement(updateQuery);
    // statement.setInt(1, status.getStatusCode());
    // statement.setString(2, operationId);
    //
    // if (statement.executeUpdate() == 0)
    // throw new
    // PeopleDBException("Il record relativo allo stato del processo non esiste.");
    //
    // statement.close();
    // conn.close();
    // } catch (SQLException e) {
    // throw new PeopleDBException(e.getMessage());
    // }
    // }

    /**
     * Rimuove dalla process status i riferimenti al servizio che si trovano
     * nello stato pendente.
     * 
     * @param processId
     *            identificativo della pratica (es. FBBMHL....)
     * @throws PeopleDBException
     */
    public static void deletePendingProcessStatus(String processId)
	    throws PeopleDBException {
	Connection connection = null;
	PreparedStatement statement = null;

	try {
	    connection = DBConnector.getInstance().connect(DBConnector.FEDB);
	    String deleteQuery = "DELETE FROM process_status WHERE peopleid = ? AND status = ?";
	    statement = connection.prepareStatement(deleteQuery);
	    statement.setString(1, processId);
	    statement.setInt(2, ProcessStatus.PAYMENT_PENDING.getStatusCode());
	    statement.executeUpdate();
	} catch (SQLException e) {
	    throw new PeopleDBException(e.getMessage());
	} finally {
	    if (statement != null)
		try {
		    statement.close();
		} catch (Exception ex) {
		}
	    if (connection != null)
		try {
		    connection.close();
		} catch (Exception ex) {
		}
	}
    }

    /**
     * Crea l'identificativo del pagamento
     * 
     * @param peopleId
     * @return
     */
    protected static String createId(String peopleId) {
	return peopleId + System.currentTimeMillis();
    }

    /**
     * Inserisce un nuovo stato per una nuova operazione di pagamento
     * 
     * @return ritorna l'identificativo del pagamento
     */
    public static String insertProcessStatus(String peopleId,
	    ProcessStatus status, InternetAddress emailAddress,
	    String paymentObserverClassName, ObserverData observerData,
	    City commune, String processName, String userId)
	    throws PeopleDBException {
	return insertStatus(peopleId, null, status, emailAddress,
		paymentObserverClassName, observerData, commune, processName,
		userId);
    }

    /**
     * Inserisce un nuovo stato per una nuova operazione di pagamento
     * 
     * @return ritorna l'identificativo del pagamento
     */
    public static String insertProcessStatus(String peopleId, Long oid,
	    ProcessStatus status, InternetAddress emailAddress,
	    String paymentObserverClassName, ObserverData observerData,
	    City commune, String processName, String userId)
	    throws PeopleDBException {
	if (oid == null)
	    throw new PeopleDBException(
		    "Il paramtro OID non pu� essere 'null'");

	return insertStatus(peopleId, oid, status, emailAddress,
		paymentObserverClassName, observerData, commune, processName,
		userId);
    }

    /**
     * 
     * @param peopleId
     * @param oid
     * @param status
     * @param emailAddress
     * @param paymentObserverClassName
     * @return ritorna l'identificativo del pagamento
     * @throws PeopleDBException
     */
    protected static String insertStatus(String peopleId, Long oid,
	    ProcessStatus status, InternetAddress emailAddress,
	    String paymentObserverClassName, ObserverData observerData,
	    City commune, String processName, String userId)
	    throws PeopleDBException {
	Connection conn = null;
	Statement statement = null;
	PreparedStatement preparedStatement = null;
	try {
	    if (paymentObserverClassName == null)
		paymentObserverClassName = UNDEFINED_OBSERVER;

	    conn = DBConnector.getInstance().connect(DBConnector.FEDB);
	    conn.setAutoCommit(false);

	    saveProcessStatusHistory(conn, peopleId);

	    // elimina la precedente operazione, perch� l'utente re-inizia il
	    // pagamento della stessa pratica
	    String queryDelete = "DELETE FROM process_status WHERE peopleid = '"
		    + peopleId + "'";
	    // elimina il vecchio stato
	    statement = conn.createStatement();
	    statement.execute(queryDelete);

	    String paymentId = createId(peopleId);

	    HashMap values = new HashMap();
	    values.put("peopleid", peopleId);
	    values.put("status", "" + status.getStatusCode());
	    values.put("id", paymentId);
	    values.put("email", emailAddress.toUnicodeString());
	    values.put("observerclassname", paymentObserverClassName);
	    values.put("communeid", commune.getKey());
	    values.put("processname", processName);
	    values.put("userid", userId);

	    if (oid != null)
		values.put("savedprocessid", oid);

	    if (observerData.getData() != null)
		values.put("observerdata", observerData.getData());

	    preparedStatement = createInsertStatement(values, conn);

	    // salva il nuovo stato
	    preparedStatement.executeUpdate();

	    conn.commit();
	    return paymentId;

	} catch (SQLException sqlEx) {
	    if (conn != null)
		try {
		    conn.rollback();
		} catch (Exception ex) {
		}
	    ;
	    throw new PeopleDBException(sqlEx.getMessage());
	} catch (InvalidArgumentException iaEx) {
	    if (conn != null)
		try {
		    conn.rollback();
		} catch (Exception ex) {
		}
	    ;
	    throw new PeopleDBException(iaEx.getMessage());
	} finally {
	    if (statement != null)
		try {
		    statement.close();
		} catch (Exception ex) {
		}
	    ;
	    if (preparedStatement != null)
		try {
		    preparedStatement.close();
		} catch (Exception ex) {
		}
	    ;
	    if (conn != null)
		try {
		    conn.close();
		} catch (Exception ex) {
		}
	    ;
	}
    }

    private static PreparedStatement createInsertStatement(HashMap map,
	    Connection connection) throws SQLException,
	    InvalidArgumentException {
	Set mapEntries = map.entrySet();

	String fieldString = "";
	String valueString = "";
	int i = 0;
	for (Iterator entries = mapEntries.iterator(); entries.hasNext();) {
	    Map.Entry entry = (Map.Entry) entries.next();

	    if (i > 0) {
		fieldString += ", ";
		valueString += ", ";
	    }

	    fieldString += (String) entry.getKey();
	    valueString += "?";
	    i++;
	}

	String queryInsert = "INSERT INTO process_status " + "(" + fieldString
		+ ") " + "VALUES (" + valueString + ")";

	PreparedStatement preparedStatement = connection
		.prepareStatement(queryInsert);
	i = 0;
	for (Iterator entries = mapEntries.iterator(); entries.hasNext();) {
	    i++;
	    Map.Entry entry = (Map.Entry) entries.next();
	    if (entry.getValue() instanceof String)
		preparedStatement.setString(i, (String) entry.getValue());
	    else if (entry.getValue() instanceof Long)
		preparedStatement.setLong(i,
			((Long) entry.getValue()).longValue());
	    else
		throw new InvalidArgumentException(
			"Il tipo del parametro passato non � previsto.");
	}

	return preparedStatement;
    }

    public static ProcessStatusTable getProcessStatusTableFromPaymentId(
	    String paymentId) throws PeopleDBException, PaymentException {
	return getProcessStatusTable(paymentId, null);
    }

    public static ProcessStatusTable getProcessStatusTableFromProcessId(
	    String processId) throws PeopleDBException, PaymentException {
	return getProcessStatusTable(null, processId);
    }

    /**
     * Carica il record dalla tabella process_status, se non trova niente
     * ritorna null
     * 
     * @author Michele Fabbri Cedaf s.r.l.
     * @param paymentId
     * @return
     * @throws PeopleDBException
     * @throws PaymentException
     */
    protected static ProcessStatusTable getProcessStatusTable(
	    String queryPaymentId, String queryPeopleId)
	    throws PeopleDBException, PaymentException {
	Connection conn = null;
	Statement stat = null;
	ResultSet rs = null;
	ProcessStatusTable processStatusTable;
	String peopleId;
	String email;
	String communeId;
	ObserverData observerData;
	String observerClassName;
	Long savedProcessId;
	String processName;
	String userId;
	City commune;
	EsitoPagamento paymentResult = null;
	int status;

	String querySelect = "SELECT * FROM process_status ";

	String queryId = "";
	if (queryPaymentId != null) {
	    querySelect += " WHERE id = '" + queryPaymentId + "'";
	    queryId = queryPaymentId;
	} else if (queryPeopleId != null) {
	    querySelect += " WHERE peopleid = '" + queryPeopleId + "'";
	    queryId = queryPeopleId;
	} else {
	    logger.error("L'id di ricerca passato non pu� essere nullo");
	    throw new PaymentException(
		    "L'id di ricerca passato non pu� essere nullo");
	}

	try {
	    conn = DBConnector.getInstance().connect(DBConnector.FEDB);
	    stat = conn.createStatement();
	    rs = stat.executeQuery(querySelect);

	    if (!rs.next()) {
		String message = "Impossibile trovare il record relativo all'id = '"
			+ queryId + "'";
		logger.info(message);
		return null;
	    }

	    peopleId = rs.getString("peopleid");
	    email = rs.getString("email");
	    if (email == null)
		email = "";

	    communeId = rs.getString("communeid");
	    observerData = new ObserverData();
	    observerData.setData(rs.getString("observerdata"));
	    observerClassName = rs.getString("observerclassname");
	    savedProcessId = new Long((long) rs.getInt("savedprocessid"));
	    processName = rs.getString("processname");
	    userId = rs.getString("userid");
	    status = rs.getInt("status");

	    String esitoString = rs.getString("paymentresult");
	    if (esitoString != null)
		paymentResult = new EsitoPagamento(esitoString);

	} catch (SQLException e) {
	    String errorMessage = "Errore nel caricameto del record di stato dalla tabella 'process_status' per l'id = '"
		    + queryId + "'";
	    logger.error(errorMessage, e);
	    throw new PeopleDBException(e.getMessage());
	} finally {
	    try {
		if (rs != null)
		    rs.close();
	    } catch (Exception ex) {
	    }
	    try {
		if (stat != null)
		    stat.close();
	    } catch (Exception ex) {
	    }
	    try {
		if (conn != null)
		    conn.close();
	    } catch (Exception ex) {
	    }
	}

	try {
	    commune = CommuneManager.getInstance().get(communeId);
	} catch (peopleException pEx) {
	    String errorMessage = "Errore nel caricameto del comune per l'id = '"
		    + queryId + "'";
	    logger.error(errorMessage, pEx);
	    throw new PeopleDBException(errorMessage);
	}

	// N.B. il comune � determinato all'esterno della connessione
	// per non avere due connessioni aperte contemporaneamente
	// quella di OJB e quella di jdbc
	processStatusTable = new ProcessStatusTable(peopleId,
		observerClassName, observerData, savedProcessId, email,
		commune, processName, userId, status, paymentResult);

	return processStatusTable;
    }

    /**
     * @deprecated non � pi� necessario perch� il metodo
     *             getProcessStatusTableFromPaymentId accetta direttamente l'id
     *             del pagamento.
     * @param id
     * @return
     * @throws PeopleDBException
     */
    public static String id2PeopleId(String id) throws PeopleDBException {

	String ret = null;
	try {
	    Connection conn = DBConnector.getInstance().connect(
		    DBConnector.FEDB);

	    String querySelect = "SELECT * FROM process_status WHERE id = '"
		    + id + "'";

	    Statement stat = conn.createStatement();
	    ResultSet rs = stat.executeQuery(querySelect);

	    if (rs.next()) {
		ret = rs.getString("peopleid");
	    }

	    try {
		rs.close();
		stat.close();
		conn.close();
	    } catch (Exception e) {
		logger.error("Errore nella determinazione del peopleId", e);
	    }
	    return ret;
	} catch (SQLException e) {
	    throw new PeopleDBException(e.getMessage());
	}
    }

    /**
     * @deprecated � utilizzato solo dai metodi deprecati.
     * @param peopleId
     * @param fieldName
     * @return
     * @throws PeopleDBException
     */
    private static String getScalar(String peopleId, String fieldName)
	    throws PeopleDBException {
	String retValue = null;
	Connection conn = null;
	Statement stat = null;
	ResultSet rs = null;

	try {
	    conn = DBConnector.getInstance().connect(DBConnector.FEDB);

	    String querySelect = "SELECT " + fieldName
		    + " FROM process_status " + " WHERE peopleid = '"
		    + peopleId + "'";

	    stat = conn.createStatement();
	    rs = stat.executeQuery(querySelect);

	    if (rs.next())
		retValue = rs.getString(fieldName);

	    return retValue;

	} catch (SQLException e) {
	    throw new PeopleDBException(e.getMessage());
	} finally {
	    try {
		if (rs != null)
		    rs.close();
	    } catch (Exception ex) {
	    }
	    try {
		if (stat != null)
		    stat.close();
	    } catch (Exception ex) {
	    }
	    try {
		if (conn != null)
		    conn.close();
	    } catch (Exception ex) {
	    }
	}
    }

    /**
     * @deprecated Utilizzare getProcessStatusTable.
     * @param peopleId
     * @return
     * @throws PeopleDBException
     */
    public static City getCommune(String peopleId) throws PeopleDBException {
	String communeId = getScalar(peopleId, "communeid");

	try {
	    return CommuneManager.getInstance().get(communeId);
	} catch (peopleException pex) {
	    throw new PeopleDBException("Impossibile caricare il comune");
	}
    }

    /**
     * @deprecated Utilizzare getProcessStatusTable.
     * @param peopleId
     * @return
     * @throws PeopleDBException
     */
    public static String getProcessName(String peopleId)
	    throws PeopleDBException {
	return getScalar(peopleId, "processname");
    }

    /**
     * @deprecated Utilizzare getProcessStatusTable.
     * @param peopleId
     * @return
     * @throws PeopleDBException
     */
    public static String getUserId(String peopleId) throws PeopleDBException {
	return getScalar(peopleId, "userid");
    }

    /**
     * @deprecated Utilizzare getProcessStatusTable.
     * @param peopleId
     * @return
     * @throws PeopleDBException
     */
    public static EsitoPagamento getPaymentResult(String peopleId)
	    throws PeopleDBException {
	String xmlData = (String) getScalar(peopleId, "paymentresult");

	try {
	    return new EsitoPagamento(xmlData);
	} catch (Exception ex) {
	    throw new PeopleDBException(
		    "Il contenuto di EsitoPagamento non e' valido.");
	}
    }

    /**
     * @deprecated Utilizzare getProcessStatusTable.
     * @param peopleId
     * @return
     * @throws PeopleDBException
     */
    public static String getPaymentObserverClassName(String peopleId)
	    throws PeopleDBException {

	return (String) getScalar(peopleId, "observerclassname");
    }

    /**
     * @deprecated Utilizzare getProcessStatusTable.
     * @param peopleId
     * @return
     * @throws PeopleDBException
     */
    public static int getProcessStatus(String peopleId)
	    throws PeopleDBException {

	String stringValue = getScalar(peopleId, "status");
	int retValue = -1;
	try {
	    retValue = Integer.parseInt(stringValue);
	} catch (Exception ex) {
	}

	return retValue;
    }

    /**
     * @deprecated Utilizzare getProcessStatusTable.
     * @param peopleId
     * @return
     * @throws PeopleDBException
     */
    public static String getProcessEMail(String peopleId)
	    throws PeopleDBException {
	return getScalar(peopleId, "email");
    }

    /**
     * @deprecated Utilizzare getProcessStatusTable.
     * @param peopleId
     * @return
     * @throws PeopleDBException
     */
    public static String getId(String peopleId) throws PeopleDBException {
	return getScalar(peopleId, "id");
    }

    /**
     * @deprecated Utilizzare getProcessStatusTable.
     * @param peopleId
     * @return
     * @throws PeopleDBException
     */
    public static ObserverData getObserverData(String peopleId)
	    throws PeopleDBException {
	String data = getScalar(peopleId, "observerdata");
	return new ObserverData(data);
    }

    /**
     * @param peopleId
     * @param paymentId
     * @param firstName
     * @param lastName
     * @param taxCode
     * @param businessName
     * @param vatCode
     * @throws PeopleDBException
     */
    public static void insertProcessStatusOwner(String peopleId,
	    String paymentId, String firstName, String lastName,
	    String taxCode, String businessName, String vatCode)
	    throws PeopleDBException {
	insertProcessStatusOwner(peopleId, paymentId, firstName, lastName,
		taxCode, businessName, vatCode, "", "");
    }

    /**
     * @param peopleId
     * @param paymentId
     * @param firstName
     * @param lastName
     * @param taxCode
     * @param businessName
     * @param vatCode
     * @param info1
     * @throws PeopleDBException
     */
    public static void insertProcessStatusOwner(String peopleId,
	    String paymentId, String firstName, String lastName,
	    String taxCode, String businessName, String vatCode, String info1)
	    throws PeopleDBException {
	insertProcessStatusOwner(peopleId, paymentId, firstName, lastName,
		taxCode, businessName, vatCode, info1, "");
    }

    /**
     * @param peopleId
     * @param firstName
     * @param lastName
     * @param taxCode
     * @param businessName
     * @param vatCode
     * @throws PeopleDBException
     */
    public static void insertProcessStatusOwner(String peopleId,
	    String paymentId, String firstName, String lastName,
	    String taxCode, String businessName, String vatCode, String info1,
	    String info2) throws PeopleDBException {

	Connection conn = null;
	PreparedStatement preparedStatement = null;
	Statement statement = null;

	try {

	    conn = DBConnector.getInstance().connect(DBConnector.FEDB);
	    conn.setAutoCommit(false);

	    // elimina la precedente operazione, perch� l'utente re-inizia il
	    // pagamento della stessa pratica
	    String queryDelete = "DELETE FROM process_status_owner WHERE peopleid = '"
		    + peopleId + "'";

	    // elimina il vecchio stato
	    statement = conn.createStatement();
	    statement.execute(queryDelete);

	    String queryInsert = "INSERT INTO process_status_owner(peopleid, id, firstname, lastname, taxcode, businessname, vatcode, info1, info2) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    preparedStatement = conn.prepareStatement(queryInsert);
	    preparedStatement.setString(1, peopleId);
	    preparedStatement.setString(2, paymentId);
	    preparedStatement.setString(3, firstName);
	    preparedStatement.setString(4, lastName);
	    preparedStatement.setString(5, taxCode);
	    preparedStatement.setString(6, businessName);
	    preparedStatement.setString(7, vatCode);
	    preparedStatement.setString(8, (info1 == null ? "" : info1));
	    preparedStatement.setString(9, (info2 == null ? "" : info2));

	    // salva il nuovo stato
	    preparedStatement.executeUpdate();

	    conn.commit();

	} catch (SQLException sqlEx) {
	    if (conn != null)
		try {
		    conn.rollback();
		} catch (Exception ex) {
		}
	    ;
	    throw new PeopleDBException(sqlEx.getMessage());
	} finally {
	    if (statement != null)
		try {
		    statement.close();
		} catch (Exception ex) {
		}
	    ;
	    if (preparedStatement != null)
		try {
		    preparedStatement.close();
		} catch (Exception ex) {
		}
	    ;
	    if (conn != null)
		try {
		    conn.close();
		} catch (Exception ex) {
		}
	    ;
	}

    }

    /**
     * @param paymentId
     * @return
     */
    public static ProcessStatusOwnerTable getProcessStatusOwnerTableFromPaymentId(
	    String paymentId) {
	return getProcessStatusOwnerTable(paymentId, null);
    }

    /**
     * @param processId
     * @return
     */
    public static ProcessStatusOwnerTable getProcessStatusOwnerTableFromProcessId(
	    String processId) {
	return getProcessStatusOwnerTable(null, processId);
    }

    /**
     * Carica il record dalla tabella process_status_owner, se non trova niente
     * ritorna un oggetto <code>ProcessStatusOwnerTable</code> con tutti i campi
     * a <code>null</code>
     * 
     * @param queryPaymentId
     * @param queryPeopleId
     * @return
     */
    protected static ProcessStatusOwnerTable getProcessStatusOwnerTable(
	    String queryPaymentId, String queryPeopleId) {
	Connection conn = null;
	Statement stat = null;
	ResultSet rs = null;
	ProcessStatusOwnerTable processStatusOwnerTable;

	String peopleId = null;
	String firstName = null;
	String lastName = null;
	String taxCode = null;
	String businessName = null;
	String vatCode = null;
	String info1 = "";
	String info2 = "";

	String querySelect = "SELECT * FROM process_status_owner ";

	String queryId = "";
	if (queryPaymentId != null) {
	    querySelect += " WHERE id = '" + queryPaymentId + "'";
	    queryId = queryPaymentId;
	} else if (queryPeopleId != null) {
	    querySelect += " WHERE peopleid = '" + queryPeopleId + "'";
	    queryId = queryPeopleId;
	} else {
	    logger.error("L'id di ricerca passato non pu� essere nullo");
	}

	try {
	    conn = DBConnector.getInstance().connect(DBConnector.FEDB);
	    stat = conn.createStatement();
	    rs = stat.executeQuery(querySelect);

	    if (!rs.next()) {
		String message = "Impossibile trovare il record relativo all'id = '"
			+ queryId + "'";
		logger.info(message);
		return null;
	    }

	    peopleId = rs.getString("peopleid");
	    firstName = rs.getString("firstname");
	    lastName = rs.getString("lastname");
	    taxCode = rs.getString("taxcode");
	    businessName = rs.getString("businessname");
	    vatCode = rs.getString("vatcode");
	    info1 = rs.getString("info1");
	    info2 = rs.getString("info2");

	} catch (SQLException e) {
	    String errorMessage = "Errore nel caricameto del record di stato dalla tabella 'process_status_owner' per l'id = '"
		    + queryId + "'";
	    logger.error(errorMessage, e);
	} catch (PeopleDBException e) {
	    String errorMessage = "Errore nel caricameto del record di stato dalla tabella 'process_status_owner' per l'id = '"
		    + queryId + "'";
	    logger.error(errorMessage, e);
	} finally {
	    try {
		if (rs != null)
		    rs.close();
	    } catch (Exception ex) {
	    }
	    try {
		if (stat != null)
		    stat.close();
	    } catch (Exception ex) {
	    }
	    try {
		if (conn != null)
		    conn.close();
	    } catch (Exception ex) {
	    }
	}

	processStatusOwnerTable = new ProcessStatusOwnerTable(peopleId,
		firstName, lastName, taxCode, businessName, vatCode, info1,
		info2);

	return processStatusOwnerTable;
    }

    private static void saveProcessStatusHistory(Connection connection,
	    String peopleId) {

	ResultSet resultSet = null;
	PreparedStatement selectPreparedStatement = null;
	PreparedStatement insertPreparedStatement = null;

	try {
	    String selectProcessStatusDataQuery = "select * from process_status where peopleid = ?";

	    String insertProcessStatusHistoryDataQuery = "insert into process_status_history(savedprocessid, peopleid, status, id, email, observerclassname";
	    insertProcessStatusHistoryDataQuery += ", paymentresult, communeid, processname, userid, observerdata) values(?,?,?,?,?,?,?,?,?,?,?);";

	    selectPreparedStatement = connection
		    .prepareStatement(selectProcessStatusDataQuery);
	    selectPreparedStatement.setString(1, peopleId);
	    resultSet = selectPreparedStatement.executeQuery();
	    if (resultSet.next()) {
		insertPreparedStatement = connection
			.prepareStatement(insertProcessStatusHistoryDataQuery);
		insertPreparedStatement.setInt(1,
			resultSet.getInt("savedprocessid"));
		insertPreparedStatement.setString(2,
			resultSet.getString("peopleid"));
		insertPreparedStatement.setString(3,
			resultSet.getString("status"));
		insertPreparedStatement.setString(4, resultSet.getString("id"));
		insertPreparedStatement.setString(5,
			resultSet.getString("email"));
		insertPreparedStatement.setString(6,
			resultSet.getString("observerclassname"));
		insertPreparedStatement.setString(7,
			resultSet.getString("paymentresult"));
		insertPreparedStatement.setString(8,
			resultSet.getString("communeid"));
		insertPreparedStatement.setString(9,
			resultSet.getString("processname"));
		insertPreparedStatement.setString(10,
			resultSet.getString("userid"));
		insertPreparedStatement.setString(11,
			resultSet.getString("observerdata"));
		if (insertPreparedStatement.executeUpdate() == 0) {
		    throw new Exception();
		}
	    }
	} catch (Exception e) {
	    logger.error(
		    "Errore nella scrittura dello storico per il process_status '"
			    + peopleId + "'.", e);
	} finally {
	    try {
		if (resultSet != null) {
		    resultSet.close();
		}
		if (selectPreparedStatement != null) {
		    selectPreparedStatement.close();
		}
		if (insertPreparedStatement != null) {
		    insertPreparedStatement.close();
		}
	    } catch (Exception ignore) {
	    }
	}

    }

}
