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
package it.people.console.web.controllers.utils;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import javax.sql.DataSource;
import javax.xml.rpc.ServiceException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import it.people.feservice.FEInterface;
import it.people.feservice.beans.LogBean;
import it.people.feservice.client.FEInterfaceServiceLocator;

/**
 * @author Luca Barbieri - Pradac Informatica S.r.l.
 * @created 05/mag/2011 11.29.50
 * @see OldPeopleConsole/com.util.logviewer.LoggerViewer.java
 * 
 */

public class LoggerViewer {
	
	private static Logger logger = LoggerFactory.getLogger(LoggerViewer.class);

    private URL reference;
    protected String codiceComune;
    private DataSource dataSourcePeopleDB;
    
    protected FEInterface getFEInterface() 
    throws ServiceException{
    	FEInterfaceServiceLocator locator = new FEInterfaceServiceLocator();
        FEInterface feInterface = locator.getFEInterface(reference);
    	return feInterface;
    }
    
    /** Creates a new instance of LoggerViewer */
    public LoggerViewer(URL reference, String codiceComune, DataSource dataSourcePeopleDB) {
        this.reference = reference;
        this.codiceComune = codiceComune;
        this.dataSourcePeopleDB = dataSourcePeopleDB;
    }
    
    // Chiamata di default all'apertura della pagina di gestione dei log.
    public ArrayList<LogBean> getAllLogs() 
    throws LoggerViewerException {
        try {                       
            return getLogsList(getFEInterface().getAllLogs(this.codiceComune));
        } catch (Exception e) {
        	logger.error("", e);
        	throw new LoggerViewerException("Impossibile determinare il log.", e);
        }
    } 
    
    // ritorna tutti i log del SERVIZIO 'idServizio'
    public ArrayList<LogBean> getLogs(int idServizio, int logId) 
    throws LoggerViewerException {
        try {

	    	LogLevelAndServiceName id = getLogLevelAndServiceName(idServizio, logId);
	    	return getLogsList(getFEInterface().getLogsForService(this.codiceComune, id.getServiceName(), id.getLogLevel()));
        } catch (Exception e) {
        	logger.error("", e);
        	throw new LoggerViewerException("Impossibile determinare il log.", e);
        }    	
    }

    /**
     * Ritorna il log di tutti i servizi e di tutti i livelli
     * compresi tra le date <code>from</code> e <code>to</code>.
     * @param from
     * @param to
     * @return
     * @throws LoggerViewerException
     */
    public ArrayList<LogBean> getLogs(Calendar from, Calendar to, String orderBy, String orderType)
    throws LoggerViewerException {
        try {           
			return getLogsList(getFEInterface().getOrderedLogs(this.codiceComune, null, null, from, to, orderBy, orderType));
        } catch (Exception e) {
        	logger.error("", e);
        	throw new LoggerViewerException("Impossibile determinare il log.", e);
        }    	
    }
           
    // filtra sia con le date che con il livello di log e il nome del servizio.    
    public ArrayList<LogBean> getLogs(int idServizio, int logId, Calendar from, Calendar to, String orderBy, String orderType) 
    throws LoggerViewerException {
        try {
	    	LogLevelAndServiceName id = getLogLevelAndServiceName(idServizio, logId);	        
            return getLogsList(getFEInterface().getOrderedLogs(
                    this.codiceComune,
	        		id.getServiceName(), 
					id.getLogLevel(),
					from, to, orderBy, orderType));
        } catch (Exception e) {
        	logger.error("", e);
        	throw new LoggerViewerException("Impossibile determinare il log.", e);
        }    	
    }
    
    
    protected ArrayList<LogBean> getLogsList(LogBean[] logArray){
    	ArrayList<LogBean> logsList = new ArrayList<LogBean>();
    	if (logArray == null)
    		return logsList;
    	
    	for(int i = 0; i < logArray.length; i++) {
    		logsList.add(logArray[i]);
    	}
    	return logsList;
    }
    
    protected LogLevelAndServiceName getLogLevelAndServiceName(int idServizio, int logId) 
    throws SQLException, Exception {
        Connection connection = null;
        Statement stmt = null;
        LogLevelAndServiceName value = new LogLevelAndServiceName(); 

        try {
        	connection = dataSourcePeopleDB.getConnection();
            stmt = connection.createStatement();
            ResultSet res;
            
            res = stmt.executeQuery("SELECT package FROM service WHERE id = " + idServizio);
            if (res.next())
            	value.setServiceName(res.getString("package"));
            
            res = stmt.executeQuery("SELECT value FROM log WHERE id = " + logId);
            if (res.next())
            	value.setLogLevel(res.getString("value"));
            
        } finally {
            try { if (stmt != null) connection.close();} catch (SQLException e) {}
            try { if (connection != null) connection.close();} catch (SQLException e) {}
        }
        return value;    	
    }
    
    protected class LogLevelAndServiceName {
    	protected String serviceName = "";
    	protected String logLevel = "";
    	
		public String getLogLevel() { return logLevel; }
		public void setLogLevel(String logLevel) { this.logLevel = logLevel; }
		public String getServiceName() { return serviceName; }
		public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    }
    
    
    public class LoggerViewerException extends Exception {
		private static final long serialVersionUID = -1038008912855306410L;

		public LoggerViewerException(String message) {
    		super(message);
    	}
    	
    	public LoggerViewerException(String message, Throwable inner) {
    		super(message, inner);
    	}

    }

}
