/**
 * ServiceSoapImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package it.people.feservice;

import it.people.City;
import it.people.core.CommuneManager;
import it.people.core.persistence.exception.peopleException;
import it.people.feservice.beans.AuditConversationsBean;
import it.people.feservice.beans.AuditFeBeXmlBean;
import it.people.feservice.beans.AuditStatisticheBean;
import it.people.feservice.beans.AuditUserBean;
import it.people.feservice.beans.ConfigParameter;
import it.people.feservice.beans.DependentModule;
import it.people.feservice.beans.FeServiceChangeResult;
import it.people.feservice.beans.IndicatorBean;
import it.people.feservice.beans.IndicatorFilter;
import it.people.feservice.beans.IndicatorsVO;
import it.people.feservice.beans.LogBean;
import it.people.feservice.beans.NodeDeployedServices;
import it.people.feservice.beans.PeopleAdministratorVO;
import it.people.feservice.beans.ProcessBean;
import it.people.feservice.beans.ProcessFilter;
import it.people.feservice.beans.ProcessesDeletionResultVO;
import it.people.feservice.beans.ProcessesVO;
import it.people.feservice.beans.ServiceOnlineHelpWorkflowElements;
import it.people.feservice.beans.ServiceVO;
import it.people.feservice.beans.UserNotificationBean;
import it.people.feservice.beans.UserNotificationDataVO;
import it.people.feservice.beans.VelocityTemplateDataVO;
import it.people.feservice.exceptions.DumpFileWritingException;
import it.people.feservice.utils.FEInterfaceConstants;
import it.people.feservice.utils.QueryUtils;
import it.people.feservice.utils.ZipUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class FEInterfaceImpl implements FEInterface {
    protected static final String DEFAULT_COMMUNE_ID = "000000";
	protected static final String SERVICE_PACKAGE_START_PATTERN = "it.people.fsl.servizi.";
	protected static final String ANONYMOUS_USER = "NNMNNM70A01H536W";
	protected static final String SUBMIT_PROCESS_ID = "SUBMIT_PROCESS";
	protected static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";
	
	protected static final String ORDER_BY_DATE = "date";
	protected static final String ORDER_BY_LOG = "log";
	protected static final String ORDER_BY_SERVICE = "service";
	protected static final String ORDER_BY_MESSAGE = "message";
	
	private static Logger logger = LoggerFactory.getLogger(FEInterfaceImpl.class);
    private static DataSource ds;  
    private static DataSource peopleDs;
    private static String docbase;
    
    private NodeList contextElements;
    private NodeList connectedServices;
    
    public FEInterfaceImpl() 
    throws java.rmi.RemoteException{
        try {
        	if (ds == null) {
                InitialContext ic = new InitialContext();
                ds = (DataSource) ic.lookup("java:comp/env/jdbc/FEDB");
        	}
        	
        	if (peopleDs == null) {
                InitialContext ic = new InitialContext();
                peopleDs = (DataSource) ic.lookup("java:comp/env/jdbc/LegacyDB");
        	}
        	
        	if (docbase == null) {
                InitialContext ic = new InitialContext();
                docbase = (String) ic.lookup("java:comp/env/documentbase");
        	}

        } catch (Exception ex) {
        	logger.error("", ex);
        }
    }

    /********************************************************************/
    /* Metodi relativi al Log											*/
    /********************************************************************/
    /**
     * Ritorna tutti i log.
     */
    public it.people.feservice.beans.LogBean[] getAllLogs(String communeId) 
    throws java.rmi.RemoteException {
        if (logger.isDebugEnabled()) 
        	logger.debug("Richiesti tutti i messaggi di log.");

        return retrieveLog(communeId, null, null, null, null, null, null);
    }

    /**
     * Ritorna il log filtrando per il servizio <code>serviceName</code> 
     * e per il livello di log <code>logLevel</code>, i parametri possono
     * essere vuoti, in tal caso la ricerca dei log � fatta senza filtrare
     * sul parametro corrispondente.
     */
    public it.people.feservice.beans.LogBean[] getLogsForService(String communeId, String serviceName, String logLevel) 
    throws java.rmi.RemoteException {
    	
    	if (serviceName != null && !validServiceName(serviceName) && !"".equals(serviceName))
    		throw new RemoteException("Nome servizio non valido");

        return retrieveLog(communeId, serviceName, logLevel, null, null, null, null);
    }

    /**
     * Ritorna i log dalla data <code>from</code> alla data 
     * <code>to</code> di tutti i servizi.
     */
    public it.people.feservice.beans.LogBean[] getLogsForDate(String communeId, Calendar from, Calendar to) 
    throws java.rmi.RemoteException {
    	    	
        if (logger.isDebugEnabled()) {
	    	logger.debug("Richiesti i messaggi di log filtrati dalla data = '" + formatCalendar(from) 
	    			+ "' alla data '" + formatCalendar(to) + "'");    	
        }
        
        return retrieveLog(communeId, "", "", from, to, null, null);
    }
    
    public it.people.feservice.beans.LogBean[] getLogsForDateAndService(String communeId, String serviceName, String logLevel, Calendar from, Calendar to) 
    throws java.rmi.RemoteException {

    	if (serviceName != null && !validServiceName(serviceName) && !"".equals(serviceName))
    		throw new RemoteException("Nome servizio non valido");
    	
        if (logger.isDebugEnabled()) {
	    	logger.debug("Richiesti i messaggi di log filtrati dalla data = '" + formatCalendar(from) 
	    			+ "' alla data '" + formatCalendar(to) + "'"
					+ " per il servizio = '" + serviceName + "'"
					+ " per il livello di log = '" + logLevel + "'");
        }
        
        return retrieveLog(communeId, serviceName, logLevel, from, to, null, null);
    }

	public it.people.feservice.beans.LogBean[] getOrderedLogs(String communeId,
			String serviceName, String logLevel, Calendar from, Calendar to,
			String orderBy, String orderType)
    throws java.rmi.RemoteException {

        if (logger.isDebugEnabled()) {
	    	logger.debug("Richiesti i messaggi di log filtrati dalla data = '" + formatCalendar(from) 
	    			+ "' alla data '" + formatCalendar(to) + "'"
					+ " per il servizio = '" + serviceName + "'"
					+ " per il livello di log = '" + logLevel + "'"
					+ " ordinati per  '" + orderBy + "'");
        }
        
        return retrieveLog(communeId, serviceName, logLevel, from, to, orderBy, orderType);
    }
    
    private boolean validServiceName(String serviceName) {
    	return serviceName.startsWith(SERVICE_PACKAGE_START_PATTERN);
    }

    protected it.people.feservice.beans.LogBean[] retrieveLog(
            String communeId,
            String serviceName, 
    		String logName, 
    		Calendar from, 
    		Calendar to, String orderBy, String orderType) 
    throws java.rmi.RemoteException {

    	boolean isLogLevelSet = logName != null && !"".equals(logName);
    	boolean isServiceNameSet = serviceName != null && !"".equals(serviceName); 
    	boolean isOrderSet = orderBy != null && !"".equals(orderBy); 
    	
    	// Query per la determinazione della priorita' del log
    	String priorityQuery = null;
    	if (isLogLevelSet)
    		priorityQuery = "SELECT id FROM log WHERE value = ?";  
    	
    	// Query per la determinazione dei messaggi di log
    	// n.b. fino a che tutti i servizi non saranno adeguati alla nuova
    	// gestione del log, � necessario mostrare il log dei messaggi
    	// relativo al comune di default che � registrato indistintamente
    	// per tutti i comuni
    	String messageQuery = "SELECT l_m.id, l_m.idloglevel, "
			+ "l_m.servicepackage, l_m.data, l_m.messaggio, l.value "
			+ "FROM log_messages l_m, log l "
			+ "WHERE l_m.idloglevel = l.id "
    		+ "AND (l_m.communeid = ? "
    		+ "OR l_m.communeid = ?) ";

		if (from != null && to != null)
			messageQuery += " AND (data BETWEEN ? AND ?)";
		else if (from != null && to == null)
			messageQuery += " AND data >= ? ";
		else if (from == null && to != null)
			messageQuery += " AND data <= ? ";
		
		if (isServiceNameSet)
			messageQuery += " AND l_m.servicepackage = ?";

        if (isLogLevelSet)
        	messageQuery += " AND l.id >= ?";

        if (isOrderSet){
        	if(orderBy.equals(ORDER_BY_DATE)) {
        		messageQuery += " ORDER BY l_m.data "+orderType+", l_m.id "+orderType;
        	} else
    		if(orderBy.equals(ORDER_BY_LOG)) {
    			messageQuery += " ORDER BY l_m.idloglevel "+orderType+", l_m.id "+orderType;
    		} else
			if(orderBy.equals(ORDER_BY_MESSAGE)) {
				messageQuery += " ORDER BY l_m.messaggio "+orderType+", l_m.id "+orderType;
			} else
			if(orderBy.equals(ORDER_BY_SERVICE)) {
					messageQuery += " ORDER BY l_m.servicepackage "+orderType+", l_m.id "+orderType;
    		} 
        }

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        ArrayList<LogBean> results = new ArrayList<LogBean>();
        try {
            connection = ds.getConnection(); 
            
            // Determinazione della priorita del log
            int logId = -1;
            if (isLogLevelSet) {
	            ps = connection.prepareStatement(priorityQuery);
	            ps.setString(1, logName);
	            res = ps.executeQuery();
	            if (res.next())
	            	logId = res.getInt(1);
	            else
	            	throw new java.rmi.RemoteException("Livello di log non valido");
            }
            
            // Determinazione dei messaggi di log
	        ps = connection.prepareStatement(messageQuery);
	        
	        int i = 0;
	        ps.setString(++i, communeId);
	        ps.setString(++i, DEFAULT_COMMUNE_ID);
	        
			if (from != null && to != null) {
				// Il primo GetTime() ritorna un java.util.date dal Calendar
				// Il secondo GetTime() ritorna un long
				ps.setDate(++i, new java.sql.Date(from.getTime().getTime()));
				ps.setDate(++i, new java.sql.Date(to.getTime().getTime()));
			}
			else if (from != null && to == null)
				ps.setDate(++i, new java.sql.Date(from.getTime().getTime()));
			else if (from == null && to != null)
				ps.setDate(++i, new java.sql.Date(to.getTime().getTime()));

			if (isServiceNameSet)
				ps.setString(++i, serviceName);
        
	        if (isLogLevelSet)
	        	ps.setInt(++i, logId);
	        
	        res = ps.executeQuery();
            while (res.next()) {
                LogBean b = new LogBean();
                b.setId(res.getInt(1));
                b.setIdloglevel(res.getInt(2));
                b.setServizio(res.getString(3));
                
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(res.getDate(4));
                b.setDate(calendar);
                b.setMessaggio(res.getString(5));
                b.setLogLevel(res.getString(6));
                results.add(b);
            }
        } catch (Exception e) {
        	logger.error("", e);
        } finally {
    		if (res != null)
    			try { res.close(); } catch(Exception ex) {}
    		if (ps != null)
    			try { ps.close(); } catch(Exception ex) {}
       		if (connection != null)
       			try { connection.close(); } catch(Exception ex) {}    		
        }
        
        LogBean[] logs = new LogBean[results.size()];
        return (LogBean[]) results.toArray(logs);
    }
    
    protected String formatCalendar(Calendar calendar) {
    	if (calendar != null) {
	    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	    	return format.format(calendar.getTime());
    	}
    	else
    		return "";
    }    
    
    /********************************************************************/
    /* Metodi relativi al log delle Audit Conversations					*/
    /********************************************************************/
    /**
     * Ritorna tutti i log delle Audit Conversations	
     */
    public it.people.feservice.beans.AuditConversationsBean[] getAllAuditConversationsForComune(String communeId, String startingPoint, String pageSize) 
    throws java.rmi.RemoteException {
        if (logger.isDebugEnabled()) {
        	logger.debug("Richiesti "+pageSize+" messaggi di log delle Audit Conversations a partire dalla posizione "+startingPoint+".");
        }
        return retrieveAuditConversation(communeId, null, null, null, null, startingPoint, pageSize);
    }
    
	public it.people.feservice.beans.AuditConversationsBean[] getAuditConversationsForAllParameters(
			String communeId, String taxCode, String processName,
			Calendar from, Calendar to, String startingPoint, String pageSize)
    throws java.rmi.RemoteException {
    	
        if (logger.isDebugEnabled()) {
	    	logger.debug("Richiesti i messaggi di log delle Audit Conversations filtrati dalla data = '" + formatCalendar(from) 
	    			+ "' alla data '" + formatCalendar(to) + "'"
					+ " per il servizio = '" + processName + "'"
					+ " per l'utente = '" + taxCode + "'");
        }
        
        return retrieveAuditConversation(communeId, taxCode, processName, from, to, startingPoint, pageSize);
    }
	

	public it.people.feservice.beans.AuditConversationsBean[] getAuditConversationsForService(
			String communeId, String taxCode, String processName,
			String startingPoint, String pageSize)
    throws java.rmi.RemoteException {
    	
    	
    	return retrieveAuditConversation(communeId, taxCode, processName, null, null, startingPoint, pageSize);
    }
	
	public it.people.feservice.beans.AuditConversationsBean[] getAuditConversationsForDate(
			String communeId, Calendar from, Calendar to, String startingPoint,
			String pageSize)    
	throws java.rmi.RemoteException {
    	    	
        if (logger.isDebugEnabled()) {
	    	logger.debug("Richiesti i messaggi di log delle audit conversations filtrati dalla data = '" + formatCalendar(from) 
	    			+ "' alla data '" + formatCalendar(to) + "'");    	
        }
        
        return retrieveAuditConversation(communeId, null, null, from, to, startingPoint, pageSize);
    }
    
    protected it.people.feservice.beans.AuditConversationsBean[] retrieveAuditConversation(
            String communeId,
            String taxCode, 
            String processName, 
    		Calendar from, 
    		Calendar to,
    		String startingPoint,
    		String pageSize
    		) 
    throws java.rmi.RemoteException {

    	boolean isCommuneKeySet = communeId != null && !"".equals(communeId);
    	boolean isTaxCodeSet = taxCode != null && !"".equals(taxCode) && !"0".equals(taxCode);
    	boolean isProcessNameSet = processName != null && !"".equals(processName);// && !"Tutti i servizi".equals(processName); 
    	
    	String selectCountQuery ="SELECT COUNT(*)";
    	String selectMessageQuery ="SELECT ac.id, ac.audit_users_ref, au.np_tax_code, au.commune_key, ac.pjp_time_stamp, " +
    			"ac.audit_timestamp, ac.process_name, ac.action_name, ac.message, ac.includexml ";
    	String messageQuery = 
			"FROM audit_conversations AS ac JOIN audit_users AS au ON ac.audit_users_ref = au.id " 
			+ "WHERE 1=1 ";
    	
    	if (isCommuneKeySet)
    		messageQuery += " AND au.commune_key = ?";
    	if (isTaxCodeSet)
    		messageQuery += " AND au.np_tax_code = ?";
    	if (isProcessNameSet)
    		messageQuery += " AND ac.process_name = ?";

    	if (from != null && to != null){
			messageQuery += " AND (ac.pjp_time_stamp BETWEEN ? AND ?)";
    	}
		else if (from != null && to == null){
			messageQuery += " AND ac.pjp_time_stamp >= ? ";
		}
		else if (from == null && to != null){
			messageQuery += " AND ac.pjp_time_stamp <= ? ";
		}
        
        messageQuery += " ORDER BY ac.audit_users_ref DESC, ac.pjp_time_stamp DESC, ac.audit_timestamp DESC ";
        
        selectCountQuery += messageQuery;
        
        selectMessageQuery += messageQuery;
        if (Integer.valueOf(pageSize)!=0){
        	selectMessageQuery += "  LIMIT ?, ?";
        }
    	
    	Connection connection = null;
        PreparedStatement ps_message = null;
        PreparedStatement ps_count = null;
        ResultSet res_message = null;
        ResultSet res_count = null;
        
        ArrayList<AuditConversationsBean> results = new ArrayList<AuditConversationsBean>();
        try {
            connection = ds.getConnection();
            // Determinazione dei messaggi di log
	        ps_message = connection.prepareStatement(selectMessageQuery);
	        ps_count = connection.prepareStatement(selectCountQuery);
	        
	        int i = 0;
	        if (isCommuneKeySet){
	        	ps_message.setString(++i, communeId);
	        	ps_count.setString(i, communeId);
	        }
			if (isTaxCodeSet){
				ps_message.setString(++i, taxCode);
				ps_count.setString(i, taxCode);
			}
	        if (isProcessNameSet){
	        	ps_message.setString(++i, processName);
	        	ps_count.setString(i, processName);
	        }
			if (from != null && to != null) {
				// Il primo GetTime() ritorna un java.util.date dal Calendar
				// Il secondo GetTime() ritorna un long
				ps_message.setDate(++i, new java.sql.Date(from.getTime().getTime()));
				ps_count.setDate(i, new java.sql.Date(from.getTime().getTime()));
				ps_message.setDate(++i, new java.sql.Date(to.getTime().getTime()));
				ps_count.setDate(i, new java.sql.Date(to.getTime().getTime()));
			}
			else if (from != null && to == null){
				ps_message.setDate(++i, new java.sql.Date(from.getTime().getTime()));
				ps_count.setDate(i, new java.sql.Date(from.getTime().getTime()));
			}
			else if (from == null && to != null){
				ps_message.setDate(++i, new java.sql.Date(to.getTime().getTime()));
				ps_count.setDate(i, new java.sql.Date(to.getTime().getTime()));
			}

			if (Integer.valueOf(pageSize)!=0){
				ps_message.setInt(++i, Integer.valueOf(startingPoint));
				ps_message.setInt(++i, Integer.valueOf(pageSize));
			}
			
	        res_message = ps_message.executeQuery();
	        res_count = ps_count.executeQuery();
			if (res_count.next()) {
				//il primo bean porta l'informazione dei risultati
	        	AuditConversationsBean b = new AuditConversationsBean();
                b.setId(res_count.getInt(1));
                results.add(b);
			}
            
	        while (res_message.next()) {
	        	AuditConversationsBean b = new AuditConversationsBean();
                b.setId(res_message.getInt(1));
                b.setAudit_users_ref(res_message.getString(2)); 
                
        		String username = res_message.getString(3);
                username = checkAnonymousUser(username);
                b.setTax_code(username);

                b.setCommuneid(res_message.getString(4));
                
//              Calendar calendar = Calendar.getInstance();
//              calendar.setTime(res_message.getDate(5));
//              b.setPjp_time_stamp(calendar);
                b.setPjp_time_stamp(res_message.getDate(5));

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(res_message.getTimestamp(6));
                b.setAudit_timestamp(calendar);
//              b.setAudit_timestamp(res_message.getDate(6));

                //recupero il nome del servizio
    			String serviceName = getServiceName(res_message.getString(7), connection);
                b.setProcess_name(serviceName);

//              b.setProcess_name(res_message.getString(7));
                
                b.setAction_name(res_message.getString(8));
                b.setMessage(res_message.getString(9));
                b.setInclude_audit_febe_xml(res_message.getInt(10));
                results.add(b);
            }
	        
        } catch (Exception e) {
            	logger.error("", e);
            } finally {
        		if (res_message != null)
        			try { res_message.close(); } catch(Exception ex) {}
        		if (ps_message != null)
        			try { ps_message.close(); } catch(Exception ex) {}
           		if (connection != null)
           			try { connection.close(); } catch(Exception ex) {}    		
            }
            
            AuditConversationsBean[] beans = new AuditConversationsBean[results.size()];
            return (AuditConversationsBean[]) results.toArray(beans);
    }

    
    public it.people.feservice.beans.AuditConversationsBean[] getAuditConversations(String query, String queryCount) 
    throws java.rmi.RemoteException {
    	/*
    	"SELECT 
    	 1. ac.id, 
    	 2. ac.audit_users_ref, 
    	 3. au.np_tax_code, 
    	 4. au.np_first_name, 
    	 5. au.np_last_name, 
    	 6. au.commune_key, 
		 7. ac.pjp_time_stamp, 
		 8. ac.audit_timestamp, 
	 	 9. ac.process_name, 
		10. ac.action_name, 
		11. ac.message, 
		12. ac.includexml, 
		13. aua.tipo_qualifica, 
		14. aua.lp_first_name, 
		15. aua.lp_last_name, 
		16. aua.lp_business_name 
    	*/
    	
    	Connection connection = null;
        PreparedStatement ps_message = null;
        PreparedStatement ps_count = null;
        ResultSet res_message = null;
        ResultSet res_count = null;
        
        ArrayList<AuditConversationsBean> results = new ArrayList<AuditConversationsBean>();
        try {
            connection = ds.getConnection();
            
	        ps_message = connection.prepareStatement(query);
	        ps_count = connection.prepareStatement(queryCount);
	        
	        res_message = ps_message.executeQuery();
	        res_count = ps_count.executeQuery();
			if (res_count.next()) {
				//il primo bean porta l'informazione dei risultati
	        	AuditConversationsBean b = new AuditConversationsBean();
                b.setId(res_count.getInt(1));
                results.add(b);
			}
            
	        while (res_message.next()) {
	        	AuditConversationsBean b = new AuditConversationsBean();
                b.setId(res_message.getInt(1));
                b.setAudit_users_ref(res_message.getString(2)); 
                
        		String username = res_message.getString(3);
                username = checkAnonymousUser(username);
                b.setTax_code(username);
//              4. au.np_first_name, 
//            	5. au.np_last_name, 
                b.setCommuneid(res_message.getString(6));
                b.setPjp_time_stamp(res_message.getDate(7));
                
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(res_message.getTimestamp(8));
                b.setAudit_timestamp(calendar);
//              b.setAudit_timestamp(res_message.getDate(8));
                
                //recupero il nome del servizio
    			String serviceName = getServiceName(res_message.getString(9), connection);
                b.setProcess_name(serviceName);
                
                b.setAction_name(res_message.getString(10));
                b.setMessage(res_message.getString(11));
                b.setInclude_audit_febe_xml(res_message.getInt(12));
//        		13. aua.tipo_qualifica, 
//        		14. aua.lp_first_name, 
//        		15. aua.lp_last_name, 
//        		16. aua.lp_business_name 
                results.add(b);
            }
	        
        } catch (Exception e) {
            	logger.error("", e);
            } finally {
        		if (res_message != null)
        			try { res_message.close(); } catch(Exception ex) {}
        		if (ps_message != null)
        			try { ps_message.close(); } catch(Exception ex) {}
           		if (connection != null)
           			try { connection.close(); } catch(Exception ex) {}    		
            }
            
            AuditConversationsBean[] beans = new AuditConversationsBean[results.size()];
            return (AuditConversationsBean[]) results.toArray(beans);
    }
    
	private String checkAnonymousUser(String username) {
		if (username.equalsIgnoreCase(ANONYMOUS_USER)) {
			username = "Utente anonimo";
		}
		return username;
	}
    
	
	private String getServiceName(String process_name, Connection connection)
			throws SQLException {

		PreparedStatement ps = null;
		ResultSet res = null;

		String selectServiceName = "SELECT nome FROM service WHERE package = '"
				+ process_name + "'";
		String serviceName = "";
		ps = connection.prepareStatement(selectServiceName);
		res = ps.executeQuery();

		if (res.next()) {
			serviceName = res.getString(1);
		}

		try {

			if (res != null) {
				res.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (Exception ex) {
		}

		return serviceName;
	}

	public java.lang.String[] getAuditUsersForComune(String communeId)    
	throws java.rmi.RemoteException {
    	    	
        if (logger.isDebugEnabled()) {
	    	logger.debug("Richiesti gli utenti presenti nei log delle audit conversations.");    	
        }
        
        String messageQuery = "SELECT DISTINCT(np_tax_code) FROM audit_users WHERE commune_key = " + communeId + " ORDER BY 1";
        
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        ArrayList<String> results = new ArrayList<String>();
        try {
            connection = ds.getConnection();
	        ps = connection.prepareStatement(messageQuery);
	        res = ps.executeQuery();
	        
	        while (res.next()) {
	        	String username = res.getString(1);
	        	username = checkAnonymousUser(username);
                results.add(username);
            }
        } catch (Exception e) {
        	logger.error("", e);
        } finally {
    		if (res != null)
    			try { res.close(); } catch(Exception ex) {}
    		if (ps != null)
    			try { ps.close(); } catch(Exception ex) {}
       		if (connection != null)
       			try { connection.close(); } catch(Exception ex) {}    		
        }
        
        return (String[]) results.toArray(new String[results.size()]);
        
    }
	
	public AuditUserBean getAuditUser(String userId, String userAccrId)    
	throws java.rmi.RemoteException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Richiesti i dati dell'utente dell'audit.");    	
		}
		
		String queryUser = "SELECT * from audit_users where id ="  + userId;
		String queryAccr = "SELECT * from audit_users_accr where id ="  + userAccrId;
		 
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet res = null;
		AuditUserBean auditUser = new AuditUserBean();
		try {
			connection = ds.getConnection();
			ps = connection.prepareStatement(queryUser);
			res = ps.executeQuery();
			
			while (res.next()) {
				auditUser.setNp_first_name(res.getString("np_first_name"));
				auditUser.setNp_last_name(res.getString("np_last_name"));
				auditUser.setId(res.getInt("id"));
				auditUser.setSessionid(res.getString("sessionid"));
                
				Calendar calendar = Calendar.getInstance();
                calendar.setTime(res.getDate("pjp_time_stamp"));
				auditUser.setPjp_time_stamp(calendar);
				auditUser.setNp_first_name(res.getString("np_first_name"));
				auditUser.setNp_last_name(res.getString("np_last_name"));
				auditUser.setNp_tax_code(res.getString("np_tax_code"));
				auditUser.setNp_address(res.getString("np_address"));
				auditUser.setNp_e_address(res.getString("np_e_address"));
				
				auditUser.setAnon_user(res.getInt("anon_user"));
				auditUser.setCommune_key(res.getString("commune_key"));
			}
				
			ps = connection.prepareStatement(queryAccr);
			res = ps.executeQuery();
			while (res.next()) {
				auditUser.setTipo_qualifica(res.getString("tipo_qualifica"));
				auditUser.setDescr_qualifica(res.getString("descr_qualifica"));
				auditUser.setOperatore(res.getString("operatore"));
				auditUser.setRichiedente(res.getString("richiedente"));
//				byte[] buf = res.getBytes("titolare");
//				ProfiloTitolare titolare;
//				if (buf != null) {
//				      ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
//				      titolare =  (ProfiloTitolare) objectIn.readObject();
//				    }
//				auditUser.setTitolare(titolare);
				auditUser.setTitolare(res.getString("titolare"));
			}
				
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (res != null)
				try { res.close(); } catch(Exception ex) {}
				if (ps != null)
					try { ps.close(); } catch(Exception ex) {}
					if (connection != null)
						try { connection.close(); } catch(Exception ex) {}    		
		}
		
		return auditUser;
		
	}
	
	
	public AuditConversationsBean getAuditConversation(String id)    
	throws java.rmi.RemoteException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Richiesti i dati della conversazione.");    	
		}
		
		String query ="SELECT ac.id, ac.audit_users_ref, au.commune_key, ac.pjp_time_stamp, " +
							"ac.audit_timestamp, ac.process_name, ac.action_name, ac.message, ac.includexml, ac.audit_users_accr " +
						"FROM audit_conversations AS ac JOIN audit_users AS au ON ac.audit_users_ref = au.id " +
						"WHERE ac.id ="  + id;
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet res = null;
		AuditConversationsBean bean = new AuditConversationsBean();
		try {
			connection = ds.getConnection();
			ps = connection.prepareStatement(query);
			res = ps.executeQuery();
			
			while (res.next()) {
                bean.setId(res.getInt(1));
                bean.setAudit_users_ref(res.getString(2)); 
                
                bean.setCommuneid(res.getString(3));
                
//              Calendar calendar = Calendar.getInstance();
//              calendar.setTime(res.getDate(4));
//              bean.setPjp_time_stamp(calendar);
                
                bean.setPjp_time_stamp(res.getDate(4));
                
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(res.getTimestamp(5));
                bean.setAudit_timestamp(calendar);
                
//              bean.setAudit_timestamp(res.getDate(5));
                
                //recupero il nome del servizio
    			String serviceName = getServiceName(res.getString(6), connection);
                bean.setProcess_name(serviceName);

//              b.setProcess_name(res_message.getString(7));
                
                bean.setAction_name(res.getString(7));
                bean.setMessage(res.getString(8));
                bean.setInclude_audit_febe_xml(res.getInt(9));
                bean.setAudit_users_accr(res.getString(10));
				
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (res != null)
				try { res.close(); } catch(Exception ex) {}
				if (ps != null)
					try { ps.close(); } catch(Exception ex) {}
					if (connection != null)
						try { connection.close(); } catch(Exception ex) {}    		
		}
		
		return bean;
		
	}
    
	public AuditFeBeXmlBean getAuditFeBeXml(String id)    
	throws java.rmi.RemoteException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Richiesti i dati dello scambio xml.");    	
		}
		
		String query ="SELECT id, xmlIn, xmlOut, time_stamp, userid FROM audit_febe_xml WHERE id = "  + id;
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet res = null;
		AuditFeBeXmlBean bean = new AuditFeBeXmlBean();
		try {
			connection = ds.getConnection();
			ps = connection.prepareStatement(query);
			res = ps.executeQuery();
			
			while (res.next()) {
				bean.setId(res.getInt(1));
				bean.setXmlIn(res.getString(2)); 
				bean.setXmlOut(res.getString(3)); 
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(res.getDate(4));
				bean.setTime_stamp(calendar);
				
				bean.setUserid(res.getString(5));

			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (res != null)
				try { res.close(); } catch(Exception ex) {}
				if (ps != null)
					try { ps.close(); } catch(Exception ex) {}
					if (connection != null)
						try { connection.close(); } catch(Exception ex) {}    		
		}
		
		return bean;
		
	}
    
    /********************************************************************/
    /* Metodi Generali													*/
    /********************************************************************/
    
    protected void registerNode(String comune, String codice, String descrizione, String aooPrefix, boolean insertAooPrefix, String announcementMessage, 
    		boolean showAnnouncement, boolean onlineSign, boolean offlineSign) 
    throws RemoteException {
        try {                
            City commune = CommuneManager.getInstance().get(codice);
            if (commune == null) {
                // necessario inserire un nuovo comune nel db People
                commune = new City();
            }
                        
            commune.setKey(codice);
            commune.setName(comune);
            commune.setLabel(descrizione);
            //Set AnnouncementMessage
            commune.setAnnouncementMessage(announcementMessage);
            commune.setShowAnnouncement(showAnnouncement);
            
            //Set firma on-line off-line
            commune.setOnlineSign(onlineSign);
            commune.setOfflineSign(offlineSign);
            
            if (insertAooPrefix)
                commune.setAooPrefix(aooPrefix);
            CommuneManager.getInstance().set(commune);
        } catch (Exception ex) {
        	logger.error("", ex);
        	throw new RemoteException(ex.getMessage());
        } catch (peopleException ex) {
        	logger.error("", ex);
        	throw new RemoteException(ex.getMessage());
        }        
    }
    
    public void registerNodeWithAoo(java.lang.String comune, java.lang.String codice, java.lang.String descrizione, java.lang.String aooPrefix, 
    		java.lang.String announcementMessage, java.lang.Boolean showAnnouncement , java.lang.Boolean onlineSign, java.lang.Boolean offlineSign)
    throws java.rmi.RemoteException {
        registerNode(comune, codice, descrizione, aooPrefix, true, announcementMessage, showAnnouncement, onlineSign, offlineSign);
	}    
    
    public void registerNode(java.lang.String comune, java.lang.String codice, java.lang.String descrizione, java.lang.String announcementMessage, 
    		java.lang.Boolean showAnnouncement, java.lang.Boolean onlineSign, java.lang.Boolean offlineSign)    
    throws java.rmi.RemoteException {
        registerNode(comune, codice, descrizione, null, false, announcementMessage, showAnnouncement, onlineSign, offlineSign);
    }
    
    public java.lang.String echo(java.lang.String word) throws java.rmi.RemoteException {
        return word;
    }
    
    public it.people.feservice.beans.ServiceVO registerService(java.lang.String communeId, java.lang.String packageName) 
    throws java.rmi.RemoteException {
        ServiceVO svo;
        ArrayList cplist, dmlist;
        
        Statement stat = null;
        ResultSet rs = null;
        Connection conn = null;
        
        try {
            conn = ds.getConnection();            
            stat = conn.createStatement();
            int id;
            
            String query = "SELECT * FROM service " 
                	+ "WHERE package = '" + packageName + "'"
                	+ "AND communeid = '" + communeId + "'";
                                
            rs = stat.executeQuery(query);
            svo = new ServiceVO();
            if (rs.next()) {
                id = rs.getInt("id");
                svo.setAttivita(rs.getString("attivita"));
                svo.setSottoattivita(rs.getString("sottoattivita"));
                svo.setNome(rs.getString("nome"));
                svo.setLogLevel(rs.getInt("logid"));
                svo.setStato(rs.getInt("statusid"));
                svo.setServicePackage(rs.getString("package"));
                svo.setProcess(rs.getString("process"));
                svo.setCommuneId(rs.getString("communeid"));
                svo.setSendmailtoowner(rs.getInt("sendmailtoowner"));
                svo.setEmbedAttachmentInXml(rs.getInt("embedattachmentinxml"));
                //Privacy
                svo.setPrivacyDisclaimerRequireAcceptance(rs.getInt("privacydisclaimerrequireacceptance"));
                svo.setShowPrivacyDisclaimer(rs.getInt("showprivacydisclaimer"));
                //Firma online - offline
                svo.setOnlineSign(rs.getInt("firmaOnLine"));
                svo.setOfflineSign(rs.getInt("firmaOffLine"));
                
                
                
                String query2 = "SELECT * FROM reference WHERE serviceid = " + id;
                
                rs = stat.executeQuery(query2);
                dmlist = new ArrayList();
                
                while (rs.next()) {
                    DependentModule dm = new DependentModule();
                    dm.setName(rs.getString("name"));
                    dm.setValore(rs.getString("value"));
                    dm.setMailAddress(rs.getString("address"));
                    dmlist.add(dm);
                }
                
                svo.setDependentModules((DependentModule[]) 
                        dmlist.toArray(new DependentModule[dmlist.size()]));

                String query3 = "SELECT * FROM configuration " +
                        "WHERE serviceid = " + id;
                
                rs = stat.executeQuery(query3);
                cplist = new ArrayList();
                
                while (rs.next()) {
                    ConfigParameter cp = new ConfigParameter();
                    cp.setParameterName(rs.getString("name"));
                    cp.setParameterValue(rs.getString("value"));
                    cplist.add(cp);
                }
                
                svo.setConfigParameters((ConfigParameter[]) 
                        cplist.toArray(new ConfigParameter[cplist.size()]));
                
                return svo;
            } else {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                String path;
                path = pathBuilder(packageName);
                DocumentBuilder db = dbf.newDocumentBuilder();
                
                String str = docbase + path + "config.xml";
                
                Document doc = db.parse(str);
                
                Node nome;
                Node attivita;
                Node sottoattivita;
                Node process;
                Node pack;
                NodeList parameter;
                NodeList references;
                
                nome = doc.getElementsByTagName("nome").item(0).getFirstChild();
                attivita = doc.getElementsByTagName("attivita").item(0).getFirstChild();;
                sottoattivita = doc.getElementsByTagName("sottoattivita").item(0).getFirstChild();;
                process = doc.getElementsByTagName("process").item(0).getFirstChild();;
                pack = doc.getElementsByTagName("package").item(0).getFirstChild();;
                parameter = doc.getElementsByTagName("parameter");
                references = doc.getElementsByTagName("references");
                contextElements = doc.getElementsByTagName("context-element");
                connectedServices = doc.getElementsByTagName("connected-service");
                
                
                
                if (logger.isDebugEnabled()) 
                	logger.debug("Registrazione servizio: '" + pack.getNodeValue() + "' ");
                
                //Ritorna un ServiceVO di default
                svo = new ServiceVO();
                svo.setNome(nome.getNodeValue());
                svo.setAttivita(attivita.getNodeValue());
                svo.setSottoattivita(sottoattivita.getNodeValue());
                svo.setLogLevel(1);
                svo.setStato(1);
                svo.setProcess(process.getNodeValue());
                svo.setServicePackage(pack.getNodeValue());
                svo.setCommuneId(communeId);
                svo.setReceiptMailIncludeAttachment(1); // di default include gli allegati
                svo.setSendmailtoowner(1); // di default la mail di ricevuta viene inviata
                svo.setEmbedAttachmentInXml(1); // di default la allegati è integrati nell'XML
                svo.setShowPrivacyDisclaimer(0); // di default false
                svo.setPrivacyDisclaimerRequireAcceptance(0);  // di default false
                svo.setOnlineSign(1); // di default true
                svo.setOfflineSign(0); // di default false
                
                cplist = new ArrayList();
                for (int i = 0; i < parameter.getLength(); i++) {
                    Node pn = parameter.item(i);
                    NamedNodeMap nnm = pn.getAttributes();
                    ConfigParameter cp = new ConfigParameter();
                    cp.setParameterName(nnm.getNamedItem("name").getNodeValue());
                    cp.setParameterValue(nnm.getNamedItem("value").getNodeValue());
                    cplist.add(cp);
                }

                svo.setConfigParameters((ConfigParameter[]) 
                        cplist.toArray(new ConfigParameter[cplist.size()]));

                dmlist = new ArrayList();
                for (int i = 0; i < references.getLength(); i++) {
                    Node rn = references.item(i);
                    NamedNodeMap nnm = rn.getAttributes();
                    DependentModule dm = new DependentModule();
                    dm.setName(nnm.getNamedItem("name").getNodeValue());
                    dm.setValore(nnm.getNamedItem("value").getNodeValue());
                    dm.setMailAddress(nnm.getNamedItem("address").getNodeValue());
                    dmlist.add(dm);
                }
                
                svo.setDependentModules((DependentModule[]) 
                        dmlist.toArray(new DependentModule[dmlist.size()]));

                storeService(svo);
                
                return svo;
            }
            
        } catch(Exception ex) {
            logger.error("", ex);
        } finally {
            if (rs != null)
                try {rs.close();} catch (SQLException ex) {}

            if (stat != null)
                try {stat.close();} catch (SQLException ex) {}
            
            if (conn != null) 
                try {conn.close();} catch (SQLException ex) {}
        }
        
        return null;
    }
    
    public void configureServiceParameter(it.people.feservice.beans.ConfigParameterVO parameterVO)
    throws java.rmi.RemoteException {
        String queryService = "SELECT * FROM service " 
            + "WHERE package = '" + parameterVO.getServicePackage() + "'"
            + "AND communeid = '" + parameterVO.getCommuneId() + "'";
                
        String queryUpdateConfiguration = "UPDATE configuration SET name = ?, value = ? WHERE serviceid = ? AND name = ? AND value = ? ";

        String queryInsertConfiguration = "INSERT INTO configuration (serviceid, name, value) VALUES (?,?,?) ";
        
        String queryDeleteConfiguration = "DELETE FROM configuration WHERE serviceid = ? AND name = ? AND value = ? ";
        
        Statement stat = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement ps = null; 
        String action = parameterVO.getAction();
        
        try {
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            stat = conn.createStatement();
            
            // Ricava l'id del servizio
            int serviceid = 0;
            rs = stat.executeQuery(queryService);
            boolean serviceFound = rs.next();
            if (serviceFound)
            	serviceid = rs.getInt("id");                             
            rs.close();
            stat.close();

            if(serviceFound) {  
            	if(action.equalsIgnoreCase("save")){
	                ps = conn.prepareStatement(queryUpdateConfiguration);
	                ConfigParameter newConfig = parameterVO.getConfigParameter();
	                ConfigParameter oldConfig = parameterVO.getOldConfigParameter();
	                
    				/* se modifico il nome devo verificare che non sia già presente */
					if (!newConfig.getParameterName().equalsIgnoreCase(oldConfig.getParameterName()))
						checkParameter(conn, serviceid, newConfig);
					
	                ps.setString(1, newConfig.getParameterName());
	                ps.setString(2, newConfig.getParameterValue());
	                ps.setInt(3, serviceid);
	                ps.setString(4, oldConfig.getParameterName());
	                ps.setString(5, oldConfig.getParameterValue());
            	} 
            	else if(action.equalsIgnoreCase("saveNew")){
            		ConfigParameter newConfig = parameterVO.getConfigParameter();
            		
            		checkParameter(conn, serviceid, newConfig);
    	            
					ps = conn.prepareStatement(queryInsertConfiguration, Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, serviceid);
					ps.setString(2, newConfig.getParameterName());
					ps.setString(3, newConfig.getParameterValue());
            	}
            	else if(action.equalsIgnoreCase("delete")){
            		ConfigParameter oldConfig = parameterVO.getOldConfigParameter();
            		ps = conn.prepareStatement(queryDeleteConfiguration);
            		ps.setInt(1, serviceid);
            		ps.setString(2, oldConfig.getParameterName());
            		ps.setString(3, oldConfig.getParameterValue());
            	}
                ps.execute(); 
                ps.close();
                
            }
            
            conn.commit();
            
        }catch(Exception ex) {
            if (conn != null) {
                try { conn.rollback(); } 
                catch (SQLException rollbackEx) { logger.error("", rollbackEx); }
            }                            
            logger.error("", ex);
            throw new java.rmi.RemoteException("configureServiceParameter", ex);
        } finally {
            if (rs != null)
                try {rs.close();} catch (SQLException ex) {}

            if (ps != null)
            	try {ps.close();} catch (SQLException ex) {}

            if (stat != null)
                try {stat.close();} catch (SQLException ex) {}
            
            if (conn != null) 
                try {conn.close();} catch (SQLException ex) {}
        }   
    }

	/**
	 * @param conn
	 * @param serviceid
	 * @param newConfig
	 * @throws SQLException
	 * @throws Exception
	 */
	private void checkParameter(Connection conn, int serviceid,
			ConfigParameter newConfig) throws SQLException, Exception {
		String check ="SELECT * FROM configuration "
			+ "WHERE serviceid = " + serviceid + " "
			+ "AND name = '" + newConfig.getParameterName() + "'";
		            
		Statement stmt = conn.createStatement();
		ResultSet res = stmt.executeQuery(check);
		if(res.next()) {
			//TODO gestire l'errore in caso di parametri duplicati
			throw new Exception("Non si possono avere parametri di configurazione duplicati (cambiare il nome del parametro)");
		}
		res.close();
		stmt.close();
	}
    
    
    public void configureServiceReference(it.people.feservice.beans.DependentModuleVO referenceVO)
    throws java.rmi.RemoteException {
    	String queryService = "SELECT * FROM service " 
    		+ "WHERE package = '" + referenceVO.getServicePackage() + "'"
    		+ " AND communeid = '" + referenceVO.getCommuneId() + "'";
    	
    	String updateReferenceQuery = "UPDATE reference SET name = ?, value = ? WHERE serviceid = ? AND name = ? AND value = ? ";
    	
    	String insertReferenceQuery = "INSERT INTO reference (serviceid, name, value) VALUES (?,?,?) ";
    	
    	String deleteReferenceQuery = "DELETE FROM reference WHERE serviceid = ? AND name = ? AND value = ? ";
    	
    	Statement stat = null;
    	ResultSet rs = null;
    	Connection conn = null;
    	PreparedStatement ps = null; 
    	String action = referenceVO.getAction();
    	
    	try {
    		conn = ds.getConnection();
    		conn.setAutoCommit(false);
    		stat = conn.createStatement();
    		
    		// Ricava l'id del servizio
    		int serviceid = 0;
    		rs = stat.executeQuery(queryService);
    		boolean serviceFound = rs.next();
    		if (serviceFound)
    			serviceid = rs.getInt("id");                             
    		rs.close();
    		stat.close();
    		
    		if(serviceFound) {  
    			if(action.equalsIgnoreCase("save")){
    				ps = conn.prepareStatement(updateReferenceQuery);
    				DependentModule newReference = referenceVO.getNewReference();
    				DependentModule oldReference = referenceVO.getOldReference();
    				
    				/* se modifico il nome devo verificare che non sia già presente */
					if (!newReference.getName().equalsIgnoreCase(oldReference.getName()))
						checkReference(conn, serviceid, newReference);
    				
    				ps.setString(1, newReference.getName());
    				ps.setString(2, newReference.getValore());
    				ps.setInt(3, serviceid);
    				ps.setString(4, oldReference.getName());
    				ps.setString(5, oldReference.getValore());
    			} 
    			else if(action.equalsIgnoreCase("saveNew")){
    				DependentModule newReference = referenceVO.getNewReference();

    				checkReference(conn, serviceid, newReference);
    				
    				ps = conn.prepareStatement(insertReferenceQuery, Statement.RETURN_GENERATED_KEYS);
    				ps.setInt(1, serviceid);
    				ps.setString(2, newReference.getName());
    				ps.setString(3, newReference.getValore());
    			}
    			else if(action.equalsIgnoreCase("delete")){
    				DependentModule oldReference = referenceVO.getOldReference();
    				ps = conn.prepareStatement(deleteReferenceQuery);
    				ps.setInt(1, serviceid);
    				ps.setString(2, oldReference.getName());
    				ps.setString(3, oldReference.getValore());
    			}
    			ps.execute(); 
    			ps.close();
    			
    		}
    		
    		conn.commit();
    		
    	}catch(Exception ex) {
    		if (conn != null) {
    			try { conn.rollback(); } 
    			catch (SQLException rollbackEx) { logger.error("", rollbackEx); }
    		}                            
    		logger.error("", ex);
    		throw new java.rmi.RemoteException("configureServiceReference", ex);
    	} finally {
    		if (rs != null)
    			try {rs.close();} catch (SQLException ex) {}
    			
    			if (ps != null)
    				try {ps.close();} catch (SQLException ex) {}
    				
    				if (stat != null)
    					try {stat.close();} catch (SQLException ex) {}
    					
    					if (conn != null) 
    						try {conn.close();} catch (SQLException ex) {}
    	}   
    }

	/**
	 * @param conn
	 * @param serviceid
	 * @param newReference
	 * @throws SQLException
	 * @throws Exception
	 */
	private void checkReference(Connection conn, int serviceid,
			DependentModule newReference) throws SQLException, Exception {
		String check ="SELECT * FROM reference "
			+ "WHERE serviceid = " + serviceid + " "
			+ "AND name = '" + newReference.getName() + "'";
		            
		Statement stmt = conn.createStatement();
		ResultSet res = stmt.executeQuery(check);
		if(res.next()) {
			//TODO gestire l'errore in caso di moduli duplicati
			throw new Exception("Non si possono avere moduli duplicati (cambiare il nome del modulo)");
		}
		res.close();
		stmt.close();
	}
    
    
    public void configureService(it.people.feservice.beans.ServiceVO theService)
    throws java.rmi.RemoteException {
    	try {
    		storeService(theService);
    	} catch(Exception ex) {
    		logger.error("", "Errore nell'aggiornamento del servizio.", ex);
    	}
    }

    public void updateService(it.people.feservice.beans.ServiceVO theService)
    throws java.rmi.RemoteException {

    	String queryService = "SELECT * FROM service " 
    		+ "WHERE package = '" + theService.getServicePackage() + "'"
    		+ " AND communeid = '" + theService.getCommuneId() + "'";
    	
    	//Local DB update Service Query
    	String updateService = "update service set nome = ?, logid = ?, statusid = ?, signenabled = ?, receiptmailattachment = ?, sendmailtoowner = ?," +
    			" embedattachmentinxml = ?, showprivacydisclaimer = ?, privacydisclaimerrequireacceptance = ?, firmaOnLine = ?, firmaOffLine = ? where package = ? and communeid = ?";
    	String updateReference = "update reference set address = ? where serviceid = ? and name = ?";
    	
    	Connection conn = null;
    	Statement stat = null;
    	PreparedStatement ps = null; 
    	ResultSet rs = null;
    	try {
    		conn = ds.getConnection();
    		conn.setAutoCommit(false);
    		stat = conn.createStatement();
    		// Ricava l'id del servizio
    		int serviceid = 0;
    		rs = stat.executeQuery(queryService);
    		boolean serviceFound = rs.next();
    		if (serviceFound)
    			serviceid = rs.getInt("id");                             
    		rs.close();
    		stat.close();
    		if(serviceFound) {
    			ps = conn.prepareStatement(updateService);
    			ps.setString(1, theService.getNome());
    			ps.setInt(2, theService.getLogLevel());
    			ps.setInt(3, theService.getStato());
    			ps.setInt(4, theService.getSignEnabled());
    			ps.setInt(5, theService.getReceiptMailIncludeAttachment());
    			ps.setInt(6, theService.getSendmailtoowner());
    			ps.setInt(7, theService.getEmbedAttachmentInXml());
    			//Privacy
    			ps.setInt(8, theService.getShowPrivacyDisclaimer());
    			ps.setInt(9, theService.getPrivacyDisclaimerRequireAcceptance());
    			//Firma
    			ps.setInt(10, theService.getOnlineSign());
    			ps.setInt(11, theService.getOfflineSign());
    			
    			//where clause
    			ps.setString(12, theService.getServicePackage());
    			ps.setString(13, theService.getCommuneId());
    			ps.executeUpdate();

    			if (theService.getDependentModules() != null) {
	    			ps = conn.prepareStatement(updateReference);
	    			ps.setString(1, theService.getDependentModules()[0].getMailAddress());
	    			ps.setInt(2, serviceid);
	    			ps.setString(3, SUBMIT_PROCESS_ID);
	    			ps.executeUpdate();
    			}
    			
    		}

    		conn.commit();
    	} catch(Exception ex) {
    		if (conn != null) {
    			try { conn.rollback(); } 
    			catch (SQLException rollbackEx) { logger.error("", rollbackEx); }
    		}                            
    		logger.error("", ex);
    		throw new RemoteException();
    	} finally {
    		try {
    			if (rs != null) {
    				rs.close();
    			}
    			if (stat != null) {
    				stat.close();
    			}
    			if (ps != null) {
    				ps.close();
    			}
    			if (conn != null) {
    				conn.close();
    			}
    		} catch(Exception ignore) {
    			
    		}
    	}
    }
    
    private void storeService(ServiceVO service) 
    throws Exception{
        String query1 = "SELECT * FROM service " 
            + "WHERE package = '" + service.getServicePackage() + "'"
            + "AND communeid = '" + service.getCommuneId() + "'";
                
        
        String queryInsert = "INSERT INTO service "
            + "(attivita, sottoattivita, nome, logid, statusid, package, process, communeid, receiptmailattachment, " +
            "signenabled, sendmailtoowner, embedattachmentinxml, showprivacydisclaimer, privacydisclaimerrequireacceptance," +
            "firmaOnLine, firmaOffLine)" 
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        String queryInsertReference = "INSERT INTO reference (serviceid, name, value, " +
                " address) VALUES (?,?,?,?)";
        
        String queryInsertConfiguration = "INSERT INTO configuration (serviceid, name, value) " +
                "VALUES (?,?,?)";
        
        Statement stat = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            stat = conn.createStatement();
            
            // Ricava l'id del servizio
            int id = 0;
            rs = stat.executeQuery(query1);
            boolean serviceFound = rs.next();
            if (serviceFound)
            	id = rs.getInt("id");                             
            rs.close();
            stat.close();

            if(serviceFound) {
            	// Rimuove tutte le dipendenze dal servizio
            	stat = conn.createStatement();                
            	
                String query;
                query = "DELETE FROM service WHERE id = " + id;                
                stat.addBatch(query);
                
                query = "DELETE FROM reference WHERE serviceid =" + id;                
                stat.addBatch(query);
                
                query = "DELETE FROM configuration WHERE serviceid =" + id;                
                stat.addBatch(query);
                
                query = "DELETE FROM context_elements WHERE serviceid =" + id;                
                stat.addBatch(query);
                
                query = "DELETE FROM connected_services WHERE serviceid =" + id;                
                stat.addBatch(query);
                                
                stat.executeBatch();
                stat.close();
            }
            
            // Inserisce il servizio (se esisteva è stato rimosso)
            ps = conn.prepareStatement(queryInsert);            
            ps.setString(1, service.getAttivita());
            ps.setString(2, service.getSottoattivita());
            ps.setString(3, service.getNome());
            ps.setInt(4, service.getLogLevel());
            ps.setInt(5, service.getStato());
            ps.setString(6, service.getServicePackage());
            ps.setString(7, service.getProcess());
            ps.setString(8, service.getCommuneId());
            ps.setInt(9, service.getReceiptMailIncludeAttachment());
            ps.setInt(10, service.getSignEnabled());
            ps.setInt(11, service.getSendmailtoowner());
            ps.setInt(12, service.getEmbedAttachmentInXml());
            //Privacy
            ps.setInt(13, service.getShowPrivacyDisclaimer());
            ps.setInt(14, service.getPrivacyDisclaimerRequireAcceptance());
            //Firma online-offline
            ps.setInt(15, service.getOnlineSign());
            ps.setInt(16, service.getOfflineSign());
            ps.execute();
            ps.close();
            
            //recupera l'ID del nuovo servizio
            stat = conn.createStatement();
            
            //select servizio
            rs = stat.executeQuery(query1);
            boolean newServiceFound = rs.next();
            if (newServiceFound)
            	id = rs.getInt("id");                             
            rs.close();
            stat.close();
            
            if(newServiceFound) {                                
                ps = conn.prepareStatement(queryInsertReference);
                DependentModule[] dipendenze = service.getDependentModules();
                if (dipendenze != null) {
                    for(int i = 0 ; i < dipendenze.length ; i++) {
                        ps.setInt(1, id);
                        ps.setString(2, dipendenze[i].getName());
                        ps.setString(3, dipendenze[i].getValore());
                        ps.setString(4, dipendenze[i].getMailAddress());
                        ps.execute();
                    }
                }
                ps.close();
                
                ps = conn.prepareStatement(queryInsertConfiguration);
                ConfigParameter[] configurazioni = service.getConfigParameters();
                if (configurazioni != null) {
                    for(int i = 0 ; i < configurazioni.length ; i++) {
                        ps.setInt(1, id);
                        ps.setString(2, configurazioni[i].getParameterName());
                        ps.setString(3, configurazioni[i].getParameterValue());
                        ps.execute();
                    }
                }
                ps.close();
                
            }
            rs.close();
            stat.close();
            
            storeContextElements(contextElements, id, conn);
            storeConnectedServices(connectedServices, id, conn);
            
            conn.commit();
            
        }catch(Exception ex) {
            if (conn != null) {
                try { conn.rollback(); } 
                catch (SQLException rollbackEx) { logger.error("", rollbackEx); }
            }                            
            logger.error("", ex);
            throw ex;
        } finally {
            if (rs != null)
                try {rs.close();} catch (SQLException ex) {}

            if (ps != null)
            	try {ps.close();} catch (SQLException ex) {}

            if (stat != null)
                try {stat.close();} catch (SQLException ex) {}
            
            if (conn != null) 
                try {conn.close();} catch (SQLException ex) {}
        }        
    }
    
    private String pathBuilder(String path) {
        String newPath;
        
        newPath = path.replace('.', File.separatorChar);
        newPath += File.separator + "risorse" + File.separator;
        
        return newPath;
    }
    
    private void storeConnectedServices(NodeList services, int id, Connection conn) 
    	throws Exception {
        Statement stat = null;
        
        try {
            stat = conn.createStatement();
            
            if (services != null) {
                for (int i = 0; i < services.getLength(); i++) {
                    Node nodo = services.item(i);
                    NamedNodeMap nnm = nodo.getAttributes();
                    
                    String query = "insert into connected_services (label, uri, serviceid) " +
                            "values ( " +
                            "'" + nnm.getNamedItem("label").getNodeValue() + "'," +
                            "'" + nnm.getNamedItem("uri").getNodeValue() + "'," +
                            id + ")";
                    
                    stat.addBatch(query);
                }
            }
            
            stat.executeBatch();
        } catch(Exception ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (stat != null)
                try {stat.close();} catch (SQLException ex) {}            
        }
    }
    
    private void storeContextElements(NodeList elements, int id, Connection conn) 
		throws Exception {
        Statement stat = null;
        
        try {
            stat = conn.createStatement();
            
            if (elements != null) {
                for (int i = 0; i < elements.getLength(); i++) {
                    Node nodo = elements.item(i);
                    NamedNodeMap nnm = nodo.getAttributes();
                    
                    String query = "insert into context_elements (name, serviceid) " +
                            "values ( " +
                            "'" + nnm.getNamedItem("name").getNodeValue() + "'," +
                            id + ")";
                    
                    stat.addBatch(query);
                }
                stat.executeBatch();
            }
        } catch(Exception ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (stat != null)
                try {stat.close();} catch (SQLException ex) {}
        }
    }
    
    /* (non-Javadoc)
     * @see it.people.feservice.FEInterface#deleteServiceByPackage(java.lang.String, java.lang.String)
     */
    public boolean deleteServiceByPackage(java.lang.String communeId, String packageName) throws java.rmi.RemoteException {
    	
    	boolean result = true;
    	
        Connection conn = null;
        Statement stat = null;
        
        try {                
            conn = ds.getConnection();
            
            // Determina l'id del servizio
            stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(
                    "SELECT id FROM service " 
                    + " WHERE package = '" + packageName + "'"
                    + " AND communeid = '" + communeId + "'");
            if (!rs.next()) {
                logger.warn("Tentata elminazione del servizio '" + packageName + "' che risulta inesistente.");
                rs.close();
                return result;
            }
            String serviceId = rs.getString(1);            
            stat.close();
            
            // Elimina il servizio
            conn.setAutoCommit(false);            
            stat = conn.createStatement();            
            
            stat.addBatch("DELETE FROM configuration WHERE serviceid = " + serviceId);
            stat.addBatch("DELETE FROM connected_services WHERE serviceid = " + serviceId);
            stat.addBatch("DELETE FROM context_elements WHERE serviceid = " + serviceId);
            stat.addBatch("DELETE FROM reference WHERE serviceid = " + serviceId);
            stat.addBatch("DELETE FROM log_messages WHERE servicepackage = '" +packageName + "' AND communeid = '" + communeId + "'");                        
            stat.addBatch("DELETE FROM service WHERE id = " + serviceId);            
            
            stat.executeBatch();            
            conn.commit();
            
            
        } catch (SQLException ex) {            
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    logger.error("", rollbackEx);
                }
            }                
        	logger.error("", ex);
        	result = false;
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
            	    logger.error("", ex);
            	}
            }
            if (conn != null) 
                try {
                    conn.close();
		        } catch (SQLException ex) {
		    	    logger.error("", ex);
		    	}
        }
        
        return result;
        
    }
    
    protected String stringReplacePlaceholder(String sourceString, String[] values) {
        /*
        for (int i = 0; i < values.length; i++)
            sourceString = sourceString.replaceAll("{" + i + "}", values[i]);
            */
        return MessageFormat.format(sourceString, values);        
    }

    /**
     * Ritorna la query di eliminazione di tutti i record relativi ad un comune
     * @param communeId nome della tabella da cui eliminare i record
     * @param table tabella da cui rimuovere i record
     * @param foreignKey nome del campo dell'id del servizio nella tabella da cui rimuovere i record 
     * @return
     */
    protected String getDeleteAllServiceQuery(String communeId, String table, String foreignKey) {
        String deleteQuery = "DELETE FROM {0} WHERE EXISTS "
            + "(SELECT * FROM service "
            + "WHERE {0}.{1} = service.id "
            + "AND service.communeid = ''" + communeId + "'')";
        return MessageFormat.format(deleteQuery, new String[] {table, foreignKey});
    }
    
    public boolean deleteAllServices(java.lang.String communeId)    
    throws java.rmi.RemoteException {
        Connection conn = null;
        Statement stat = null;
                
        try {
            // Rimuove il comune
            CommuneManager.getInstance().delete(communeId);            
            
            // Rimuove i servizi collegati al comune
            
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            
            stat = conn.createStatement();  
            
            stat.addBatch(getDeleteAllServiceQuery(
                    communeId, "configuration", "serviceid"));
                    
            stat.addBatch(getDeleteAllServiceQuery(
                    communeId, "connected_services", "serviceid"));

            stat.addBatch(getDeleteAllServiceQuery(
                    communeId, "context_elements", "serviceid"));

            stat.addBatch(getDeleteAllServiceQuery(
                    communeId, "reference", "serviceid"));
            
            stat.addBatch("DELETE FROM log_messages WHERE communeid = '" + communeId+ "'");                        
            stat.addBatch("DELETE FROM service WHERE communeid = '" + communeId+ "'");            
            stat.executeBatch();
            
            conn.commit();
            return true;
            
        } catch (SQLException ex) {            
            if (conn != null) {
                try { conn.rollback(); } 
                catch (SQLException rollbackEx) {
                    logger.error("", rollbackEx);
                }
            }                
        	logger.error("", ex);
        	return false;
        } finally {
            if (stat != null) {
                try { stat.close(); } 
                catch (SQLException ex) {
            	    logger.error("", ex);
            	}
            }
            if (conn != null) { 
                try { conn.close(); } 
            	catch (SQLException ex) {
		    	    logger.error("", ex);
		    	}
            }
        }
    }    

    /**
     * @param communeId
     * @return
     * @throws java.rmi.RemoteException
     */
    public it.people.feservice.beans.NodeDeployedServices getNodeDeployedServices(String communeId) 
    	throws java.rmi.RemoteException {

    	NodeDeployedServices result = null;
    	
		FEInterfaceExt feInterfaceExt = null;
    	
		feInterfaceExt = new FEInterfaceExtImpl(ds);

		URI docBaseURI = null;
		try {
			docBaseURI = new URI(docbase);
		} catch (URISyntaxException e) {
			throw new RemoteException("Error while getting docBase URI.", e);
		}
		
		if (feInterfaceExt != null && docBaseURI != null) {
			result = feInterfaceExt.getNodeDeployedServices(docBaseURI.getSchemeSpecificPart());
		}
		else {
			
			String exception = feInterfaceExt == null ? "Front end extended interface is null" : "";

			exception += docBaseURI == null ? "Doc base URI of people deployed services is null" : "";
			
			throw new java.rmi.RemoteException(exception);
			
		}

		return result;
		
	}

    public boolean nodeCopy(String[] selectedServices, String[] areasLogicalNamesPrefix,
    		String[] areasLogicalNamesSuffix, String[] servicesLogicalNamesPrefix,
    		String[] servicesLogicalNamesSuffix, String fromCommuneId, String toCommuneId) 
	throws java.rmi.RemoteException {

	boolean result = false;
	
	FEInterfaceExt feInterfaceExt = new FEInterfaceExtImpl(ds);
	
	if (feInterfaceExt != null) {
		result = feInterfaceExt.nodeCopy(selectedServices, areasLogicalNamesPrefix,
				areasLogicalNamesSuffix, 
				servicesLogicalNamesPrefix,
				servicesLogicalNamesSuffix, fromCommuneId, toCommuneId);
	}

	return result;
	
    }
    
    /**
     * @param 
     * SELECT communeid, nomeEnte, process_name ,nomeServizio, action_name, audit_timestamp, risultati;
     **/
	public it.people.feservice.beans.AuditStatisticheBean[] getStatistiche(
			java.lang.String query) throws java.rmi.RemoteException {

		
		Connection connection = null;
		PreparedStatement ps_message = null;
		ResultSet result = null;

		ArrayList<AuditStatisticheBean> results = new ArrayList<AuditStatisticheBean>();
		try {
			connection = ds.getConnection();
			// Determinazione delle statistiche
			ps_message = connection.prepareStatement(query);

			result = ps_message.executeQuery();

			while (result.next()) {
				AuditStatisticheBean b = new AuditStatisticheBean();
				/*
				String communeid;
				String nomeEnte;
				String process_name;
				String nomeServizio;
				String action_name;
				Calendar audit_timestamp;
				private int risultati;
				*/
				b.setCommuneid(result.getString(1));
				b.setNomeEnte(result.getString(2));
				b.setProcess_name(result.getString(3));
				b.setNomeServizio(result.getString(4));
				b.setAction_name(result.getString(5));
				
				Calendar calendar = Calendar.getInstance();
				if(result.getDate(6)!=null){
					calendar.setTime(result.getDate(6));
					b.setAudit_timestamp(calendar);
				}
				b.setRisultati(result.getInt(7));
				
				results.add(b);
			}

		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (result != null)
				try {
					result.close();
				} catch (Exception ex) {
				}
			if (ps_message != null)
				try {
					ps_message.close();
				} catch (Exception ex) {
				}
			if (connection != null)
				try {
					connection.close();
				} catch (Exception ex) {
				}
		}

		AuditStatisticheBean[] beans = new AuditStatisticheBean[results.size()];
		return (AuditStatisticheBean[]) results.toArray(beans);
	}

    public it.people.feservice.beans.ServiceOnlineHelpWorkflowElements getServiceOnlineHelpWorkflowElements(String servicePackage) 
	throws java.rmi.RemoteException {

    	ServiceOnlineHelpWorkflowElements result = null;
	
	FEInterfaceExt feInterfaceExt = null;
	
	feInterfaceExt = new FEInterfaceExtImpl(ds);

	URI docBaseURI = null;
	try {
		docBaseURI = new URI(docbase);
	} catch (URISyntaxException e) {
		throw new RemoteException("Error while getting docBase URI.", e);
	}
	
	if (feInterfaceExt != null && docBaseURI != null) {
		result = feInterfaceExt.getServiceOnlineHelpWorkflowElements(servicePackage, docBaseURI.getSchemeSpecificPart());
	}
	else {
		
		String exception = feInterfaceExt == null ? "Front end extended interface is null" : "";

		exception += docBaseURI == null ? "Doc base URI of people deployed services is null" : "";
		
		throw new java.rmi.RemoteException(exception);
		
	}

	return result;
	
}

    /**
     * @param bundleRef
     * @param key
     * @param value
     * @param active
     * @param group
     * @throws java.rmi.RemoteException
     */
    public void updateBundle(java.lang.String bundle, java.lang.String nodeId, java.lang.String locale, 
    		java.lang.String key, java.lang.String value, 
    		java.lang.String active, java.lang.String group) throws java.rmi.RemoteException {

		FEInterfaceExt feInterfaceExt = null;
    	
		feInterfaceExt = new FEInterfaceExtImpl(ds);
		
		feInterfaceExt.updateBundle(bundle, nodeId, locale, key, value, active, group);
    	
    }

    /**
     * @param bundle
     * @param nodeId
     * @param locale
     * @param active
     * @param group
     * @throws java.rmi.RemoteException
     */
    public void registerBundle(java.lang.String bundle, java.lang.String nodeId, java.lang.String locale, 
    		java.lang.String active, java.lang.String group) throws java.rmi.RemoteException {

		FEInterfaceExt feInterfaceExt = null;
    	
		feInterfaceExt = new FEInterfaceExtImpl(ds);
		
		feInterfaceExt.registerBundle(bundle, nodeId, locale, active, group);
    	
    }

    
    /**
     * @param services
     * @throws java.rmi.RemoteException
     */
    public void updateFeServices(it.people.feservice.beans.ServiceVO[] services) throws java.rmi.RemoteException {

    	if (services != null && services.length > 0) {
    		
    		for(int index = 0; index < services.length; index++) {
    			it.people.feservice.beans.ServiceVO serviceVO = services[index];
    			this.updateService(serviceVO);
    		}
    		
    	}
    	
    }
    
    /**
     * @param servicesCommunePackage
     * @return
     * @throws java.rmi.RemoteException
     */
    public FeServiceChangeResult[] deleteFeServicesByPackages(it.people.feservice.beans.CommunePackageVO[] servicesCommunePackage) throws java.rmi.RemoteException {

    	FeServiceChangeResult[] result = null;
    	
    	Vector<FeServiceChangeResult> buffer = new Vector<FeServiceChangeResult>();
    	
    	if (servicesCommunePackage != null && servicesCommunePackage.length > 0) {
    		for(int index = 0; index < servicesCommunePackage.length; index++) {
    			it.people.feservice.beans.CommunePackageVO communePackageVO = servicesCommunePackage[index];
    			boolean deleted = this.deleteServiceByPackage(communePackageVO.getCommuneId(), communePackageVO.getServicePackage());
    			FeServiceChangeResult feServiceChangeResult = new FeServiceChangeResult();
    			feServiceChangeResult.setServiceId(communePackageVO.getServiceId());
    			feServiceChangeResult.setCommuneId(communePackageVO.getCommuneId());
    			feServiceChangeResult.set_package(communePackageVO.getServicePackage());
    			feServiceChangeResult.setError(!deleted);
    			buffer.add(feServiceChangeResult);
    		}
    	}

    	result = (FeServiceChangeResult[])buffer.toArray(new FeServiceChangeResult[buffer.size()]);
    	
    	return result;
    	
    }

    /**
     * @param servicesReferences
     * @return
     * @throws java.rmi.RemoteException
     */
    public boolean deleteBeServicesReferencesByPackages(it.people.feservice.beans.FEServiceReferenceVO[] servicesReferences) throws java.rmi.RemoteException {

		FEInterfaceExt feInterfaceExt = null;
    	
		feInterfaceExt = new FEInterfaceExtImpl(ds);
		
		return feInterfaceExt.deleteBeServicesReferencesByPackages(servicesReferences);
    	
    }
    
    /**
     * @param bundle
     * @param nodeId
     * @param locale
     * @throws java.rmi.RemoteException
     */
    public void deleteBundle(java.lang.String bundle, java.lang.String nodeId, java.lang.String locale) throws java.rmi.RemoteException {

		FEInterfaceExt feInterfaceExt = null;
    	
		feInterfaceExt = new FEInterfaceExtImpl(ds);
		
		feInterfaceExt.deleteBundle(bundle, nodeId, locale);
    	
    }

	@Override
	public void configureTableValueProperty(it.people.feservice.beans.TableValuePropertyVO tableValueProperty) throws java.rmi.RemoteException {
		
		FEInterfaceExt feInterfaceExt = null;
    	
		feInterfaceExt = new FEInterfaceExtImpl(ds);
		
		feInterfaceExt.configureTableValueProperty(tableValueProperty);
		
	}
	
	@Override
	public void configureServiceAuditProcessor(it.people.feservice.beans.ServiceAuditProcessorVO serviceAuditProcessor) throws java.rmi.RemoteException {
		
        //Queries
		String checkExixtsQuery = "select * from service_audit_processors where serviceId = ? and auditProcessor = ?";
    	String updateQuery = "update service_audit_processors set active = ? where serviceId = ? and auditProcessor = ?";
    	String insertQuery = "insert into service_audit_processors (serviceId, auditProcessor, active) values (?, ?, ?)";
		
		Connection conn = null;
        Statement stat = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

        try {                
            conn = ds.getConnection();
            conn.setAutoCommit(false);
                       
            // Determina l'id del servizio
            int serviceid = 0;
            stat = conn.createStatement();
            rs = stat.executeQuery(
                    "SELECT id FROM service WHERE" 
                    + " package = '" + serviceAuditProcessor.getServicePackage() + "'"
                    + " AND communeid = '" + serviceAuditProcessor.getCommuneId() + "'");
            
    		boolean serviceFound = rs.next();

    		if (logger.isDebugEnabled()) {
    			logger.debug("Service found? " + serviceFound);
    		}
    		
    		if (serviceFound) {
    			serviceid = rs.getInt("id");
        		rs.close();
        		stat.close();

        		if (logger.isDebugEnabled()) {
        			logger.debug("Service id = " + serviceid);
        		}
        		
        		//Check if exists, then update or insert new
        		ps = conn.prepareStatement(checkExixtsQuery);
        		ps.setLong(1, serviceid);
        		ps.setString(2, serviceAuditProcessor.getAuditProcessor());
    			rs = ps.executeQuery();
    			
    			if (rs.next()) {
            		if (logger.isDebugEnabled()) {
            			logger.debug("Updating audit processor for service id = " + serviceid);
            			logger.debug("Updating audit processor = " + serviceAuditProcessor.getAuditProcessor());
            		}
    				//Update
    				ps = conn.prepareStatement(updateQuery);
    				ps.setInt(1, serviceAuditProcessor.getActive());
    				ps.setLong(2, serviceid);
    				ps.setString(3, serviceAuditProcessor.getAuditProcessor());
    			} else {
            		if (logger.isDebugEnabled()) {
            			logger.debug("Inserting audit processor for service id = " + serviceid);
            			logger.debug("Inserting audit processor = " + serviceAuditProcessor.getAuditProcessor());
            		}
    				//Insert new
    				ps = conn.prepareStatement(insertQuery);
    				ps.setLong(1, serviceid);
    				ps.setString(2, serviceAuditProcessor.getAuditProcessor());
    				ps.setInt(3, serviceAuditProcessor.getActive());
    			}
				ps.execute();
				conn.commit();
    		}
    		else {
    			throw new java.rmi.RemoteException("Unable to find service ID to configure Audit Processor");
    		}
            
        }catch(Exception ex) {
            if (conn != null) {
                try { conn.rollback(); } 
                catch (SQLException rollbackEx) { logger.error("", rollbackEx); }
            }                            
            logger.error("", ex);
            throw new java.rmi.RemoteException("configureServiceAuditProcessor", ex);
            
        } finally {
        	try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stat != null) { 
	                stat.close();
	            }
	            
	            if (conn != null) {
	            	conn.close();
	            }
        	} catch (SQLException ex) {}
        }   
	}

	
	// MONITORING INDICATORS
	
	@Override
	public IndicatorsVO getMonitoringIndicators(IndicatorFilter indicatorFilter, int lowerPageLimit, int pageSize, 
			String[] selectedEnti, String[] selectedAttivita, boolean retrieveAll)
			throws RemoteException {
	
		IndicatorsVO result = new IndicatorsVO(0, new IndicatorBean[] {new IndicatorBean("" , "", "" ,"" ,"" ,"" ,"" ,null ,"" ,"")});
	
		//if not empty filter
		if ((!indicatorFilter.equals(new IndicatorFilter())) && selectedEnti != null && selectedAttivita != null) {
			if (retrieveAll) {
				result = getAllIndicators(indicatorFilter, selectedEnti, selectedAttivita);
			}
			else {
				result = getPagedIndicators(indicatorFilter, lowerPageLimit, pageSize, selectedEnti, selectedAttivita);
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param indicatorFilter
	 * @param lowerPageLimit
	 * @param pageSize
	 * @return
	 */
	private IndicatorsVO getPagedIndicators(IndicatorFilter indicatorFilter, int lowerPageLimit, int pageSize, String[] selectedEnti, String[] selectedAttivita) 
			throws RemoteException {
		
		String query = FEInterfaceConstants.PAGED_INDICATORS_QUERY;
		String countQuery =  FEInterfaceConstants.COUNT_TOTAL_PAGED_INDICATORS_QUERY;
		
	    //Connection for "people" db (LEGACY)
		Connection connection = null;
	    PreparedStatement ps = null;
	    PreparedStatement countPs = null;
	    ResultSet res = null;
	    ResultSet countRes = null;
	   
	    //init result
	    IndicatorsVO result = new IndicatorsVO(0, new IndicatorBean[] {new IndicatorBean()});
	     
	    List <IndicatorBean> indicators = new ArrayList<IndicatorBean>();
	    
		//Customize query to match prepareStatement placeholder with selected parmas 
	    query = QueryUtils.buildIndicatorsQueryPlaceholders(query, selectedEnti, selectedAttivita);
	    countQuery = QueryUtils.buildIndicatorsQueryPlaceholders(countQuery, selectedEnti, selectedAttivita);
	    		
		try {
			//Prepare Queries
			connection = peopleDs.getConnection();
			ps = connection.prepareStatement(query);
			countPs = connection.prepareStatement(countQuery);
			
			//Set params in queries
	        QueryUtils.setIndicatorsQueryParams(ps, indicatorFilter, lowerPageLimit, pageSize, selectedEnti, selectedAttivita, true);
	        QueryUtils.setIndicatorsQueryParams(countPs, indicatorFilter, lowerPageLimit, pageSize, selectedEnti, selectedAttivita, false);
			
	        //Execute
	        res = ps.executeQuery();
			countRes = countPs.executeQuery();

			//Populate result
			 while (res.next()) {
				indicators.add(new IndicatorBean(res.getString("ente"), res.getString("nome"),
						res.getString("attivita"), res.getString("procedimento"), 
						res.getString("procedimento_id"), res.getString("NTC"), res.getString("NTR"), 
						null, res.getString("NAD"), ""));
			}
			
			 //GetCount
			int totalIndicatorsCount = 0;
		    if (countRes.next()) {
		    	totalIndicatorsCount = countRes.getInt("totalCount");
		    }
			
		    //Result
			result = new IndicatorsVO(totalIndicatorsCount, (IndicatorBean[]) indicators.toArray(new IndicatorBean[indicators.size()]));

	     } catch(Exception ex) {
	            if (connection != null) {
	                try { connection.rollback(); } 
	                catch (SQLException rollbackEx) { logger.error("", rollbackEx); }
	            }                            
	            logger.error("", ex);
	            throw new java.rmi.RemoteException("Exception executing queries for getPagedIndicators", ex);    
		} finally {
			if (res != null) {
				try {
					res.close();
				} catch (SQLException ex) {}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException ex) {}
			}
			if (countRes != null) {
				try {
					res.close();
				} catch (SQLException ex) {}
			}
			if (countPs != null) {
				try {
					ps.close();
				} catch (SQLException ex) {}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException ex) {}
			}
		} 
		return result;
	}

	
	/**
	 * Get all monitoring indicators to send to osservatorio 
	 * 
	 * @param indicatorFilter
	 * @param lowerPageLimit
	 * @param pageSize
	 * @return
	 */
	private IndicatorsVO getAllIndicators(IndicatorFilter indicatorFilter, String[] selectedEnti, String[] selectedAttivita) 
			throws RemoteException {
		
		String query = FEInterfaceConstants.INDICATORS_TO_SEND_QUERY;
		
	    //Connection for "people" db (LEGACY)
		Connection connection = null;
	    PreparedStatement ps = null;
	    ResultSet res = null;
	   
	    //init result
	    IndicatorsVO result = new IndicatorsVO(0, new IndicatorBean[] {new IndicatorBean()});
	    List <IndicatorBean> indicators = new ArrayList<IndicatorBean>();
	    
		//Customize query to match prepareStatement placeholder with selected parmas 
	    query = QueryUtils.buildIndicatorsQueryPlaceholders(query, selectedEnti, selectedAttivita);
	    		
		try {
			//Prepare Queries
			connection = peopleDs.getConnection();
			ps = connection.prepareStatement(query);
			
			//Set params in queries
			QueryUtils.setIndicatorsQueryParams(ps, indicatorFilter, 0, 0, selectedEnti, selectedAttivita, false);
			
	        //Execute
	        res = ps.executeQuery();

			//Populate result
	        int totalIndicatorsCount = 0;
			while (res.next()) {
				 
				totalIndicatorsCount+=1;
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(res.getDate("submit_date"));
				
				indicators.add(new IndicatorBean(res.getString("ente"), "",
						res.getString("attivita"), res.getString("procedimento"), 
						res.getString("procedimento_id"), res.getString("NTC"), res.getString("NTR"), 
						cal, res.getString("NAD"), res.getString("delegate")));
			}		
		    //Result
			result = new IndicatorsVO(totalIndicatorsCount, (IndicatorBean[]) indicators.toArray(new IndicatorBean[indicators.size()]));

	     } catch(Exception ex) {
	            if (connection != null) {
	                try { connection.rollback(); } 
	                catch (SQLException rollbackEx) { logger.error("", rollbackEx); }
	            }                            
	            logger.error("", ex);
	            throw new java.rmi.RemoteException("Exception executing queries for getPagedIndicators", ex);    
		} finally {
			if (res != null) {
				try {
					res.close();
				} catch (SQLException ex) {}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException ex) {}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException ex) {}
			}
		} 
		return result;
	}

	
	// PROCESSES (a.k.a. PRATICHE)

	@Override
	public ProcessesDeletionResultVO deleteProcesses(ProcessFilter processFilter, String[] selectedUsers, String[] selectedNodes,
			boolean archiveInFile) throws RemoteException {

		int deletedRows = 0;
		String pathToReturn = "";
		ProcessesDeletionResultVO result = new ProcessesDeletionResultVO(deletedRows, pathToReturn, false, "");
		
		// if not empty filter
		if ((!processFilter.equals(new ProcessFilter())) && selectedUsers != null) {

			if (archiveInFile) {
				try {
					pathToReturn = createProcessesDumpFile(processFilter, selectedUsers, selectedNodes);
					result.setBackupFilePath(pathToReturn);
				} catch (DumpFileWritingException e1) {
					logger.error("Error creating processes dump file: processes deletion aborted.", e1);
					result.setError(true);
					result.setErrorMessage("Errore nella generazione del file di backup pratiche: cancellazione annullata.");
				}
			}
			
			if (!result.isError()) {
				try {
					deletedRows = deleteProcesses(processFilter, selectedUsers, selectedNodes);
					result.setDeletedRows(deletedRows);
				} catch (RemoteException e) {
					logger.error("Error deleting processes.", e);
					result.setError(true);
					result.setErrorMessage("Errore nella cancellazione delle pratiche.");
				}
			}
		}
		
		return  result;
	}
	

	/**
	 * 
	 * @param processFilter
	 * @param selectedUsers
	 * @param selectedNodes
	 * @return number of deleted rows
	 * @throws RemoteException
	 */
	private int deleteProcesses(ProcessFilter processFilter, String[] selectedUsers, String[] selectedNodes) throws RemoteException {
		
		int deletedRows= 0;
		
	    //Connection for "people" db (LEGACY)
		Connection connection = null;
	    PreparedStatement ps = null;
	    ResultSet res = null;

	    try {
	    	
	    	//Open connection
	    	connection = peopleDs.getConnection();
	    	
			//DELETE_SUBMITTED_PROCESSES_HISTORY
			String spiQuery = FEInterfaceConstants.DELETE_SUBMITTED_PROCESSES_INFO_QUERY;
			spiQuery = QueryUtils.buildProcessesQueryPlaceholders(spiQuery, selectedUsers, selectedNodes, true);
			ps = connection.prepareStatement(spiQuery);
			QueryUtils.setProcessesQueryParams(ps, processFilter, 0, 0, selectedUsers, selectedNodes, false,
					FEInterfaceConstants.SUBMITTED_PROCESSES_QUERY_ID);
			ps.executeUpdate();
			ps.close();
			
			//DELETE_SUBMITTED_PROCESSES_INFO
			String sphQuery = FEInterfaceConstants.DELETE_SUBMITTED_PROCESSES_HISTORY_QUERY;
			sphQuery = QueryUtils.buildProcessesQueryPlaceholders(sphQuery, selectedUsers, selectedNodes, true);
			ps = connection.prepareStatement(sphQuery);
			QueryUtils.setProcessesQueryParams(ps, processFilter, 0, 0, selectedUsers, selectedNodes, false,
					FEInterfaceConstants.SUBMITTED_PROCESSES_QUERY_ID);
			ps.executeUpdate();
			ps.close();
			
			//DELETE_SUBMITTED_PROCESSES_QUERY
		    String spQuery = FEInterfaceConstants.DELETE_SUBMITTED_PROCESSES_QUERY;
		    spQuery = QueryUtils.buildProcessesQueryPlaceholders(spQuery, selectedUsers, selectedNodes, true);
			ps = connection.prepareStatement(spQuery);
			QueryUtils.setProcessesQueryParams(ps, processFilter, 0, 0, selectedUsers, selectedNodes, false,
					FEInterfaceConstants.SUBMITTED_PROCESSES_QUERY_ID);
			deletedRows += ps.executeUpdate();
			ps.close();
			
			//DELETE_NOT_SUBMITTABLE_PROCESSES
			String nspQuery = FEInterfaceConstants.DELETE_NOT_SUBMITTABLE_PROCESSES_QUERY;
			nspQuery = QueryUtils.buildProcessesQueryPlaceholders(nspQuery, selectedUsers, selectedNodes, true);
			ps = connection.prepareStatement(nspQuery);
			QueryUtils.setProcessesQueryParams(ps, processFilter, 0, 0, selectedUsers, selectedNodes, false,
					FEInterfaceConstants.NOT_SUBMITTABLE_PROCESSES_QUERY_ID);
			deletedRows += ps.executeUpdate();
			ps.close();
			
			//DELETE_PENDING_PROCESSES_ACL
			String ppaQuery = FEInterfaceConstants.DELETE_PENDING_PROCESSES_ACL;
			ppaQuery = QueryUtils.buildProcessesQueryPlaceholders(ppaQuery, selectedUsers, selectedNodes, true);
			ps = connection.prepareStatement(ppaQuery);
			QueryUtils.setProcessesQueryParams(ps, processFilter, 0, 0, selectedUsers, selectedNodes, false,
					FEInterfaceConstants.PENDING_PROCESSES_QUERY_ID);
			ps.executeUpdate();
			ps.close();
			
			//DELETE_PENDING_PROCESSES_DELEGATE
			String ppdQuery = FEInterfaceConstants.DELETE_PENDING_PROCESSES_DELEGATE;
			ppdQuery = QueryUtils.buildProcessesQueryPlaceholders(ppdQuery, selectedUsers, selectedNodes, true);
			ps = connection.prepareStatement(ppdQuery);
			QueryUtils.setProcessesQueryParams(ps, processFilter, 0, 0, selectedUsers, selectedNodes, false,
					FEInterfaceConstants.PENDING_PROCESSES_QUERY_ID);
			ps.executeUpdate();
			ps.close();
			
			//DELETE_PENDING_PROCESSES
			String ppQuery = FEInterfaceConstants.DELETE_PENDING_PROCESSES_QUERY;
			ppQuery = QueryUtils.buildProcessesQueryPlaceholders(ppQuery, selectedUsers, selectedNodes, true);
			ps = connection.prepareStatement(ppQuery);
			QueryUtils.setProcessesQueryParams(ps, processFilter, 0, 0, selectedUsers, selectedNodes, false,
					FEInterfaceConstants.PENDING_PROCESSES_QUERY_ID);
			deletedRows += ps.executeUpdate();
			ps.close();
			
	    } catch (SQLException e) {
	    	
	    	if (connection != null) {
				try { connection.rollback(); } 
	            catch (SQLException rollbackEx) { logger.error("", rollbackEx); }
			}  
			logger.error("", e);
			throw new RemoteException("Exception deleting processes from DB", e);  
        
		} finally {
			if (res != null) {
				try {
					res.close();
				} catch (SQLException ex) {}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException ex) {}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException ex) {}
			}
		} 
	    
	    return deletedRows;
	}

	@Override
	public ProcessesVO getProcesses(ProcessFilter processFilter, int lowerPageLimit, int pageSize,
			String[] selectedUsers, String[] selectedNodes, boolean retrieveAll) throws RemoteException {

		ProcessesVO result = new ProcessesVO(0, new ProcessBean[] {new ProcessBean("", "", "", "", null, "","", "")});

		// if not empty filter
		if ((!processFilter.equals(new ProcessFilter())) && selectedUsers != null) {
			if (retrieveAll) {
				// Bypass pagination params
				result = getPagedProcesses(processFilter, 0, FEInterfaceConstants.LIMIT_MAX_UPPERBOUND,
						selectedUsers, selectedNodes);
			} else {
				result = getPagedProcesses(processFilter, lowerPageLimit, pageSize, selectedUsers,
						selectedNodes);
			}
		}
		return result;
	}

	
	/**
	 *  Get all processes and return a SQL dumpFile 
	 *  
	 * @param processFilter
	 * @param selectedUsers
	 * @param selectedNodes
	 * @return path of compressed dump file
	 * @throws RemoteException
	 */
	private String createProcessesDumpFile(ProcessFilter processFilter, String[] selectedUsers,
			String[] selectedNodes) throws DumpFileWritingException {
		
		
	    //Connection for "people" db (LEGACY)
		Connection connection = null;
	    PreparedStatement ps = null;
	    ResultSet res = null;
	    
		// Get dump directory
		String uploadDirQuery = "SELECT nc.value FROM nodeconfiguration nc WHERE NAME=\'upload.directory\' " +
				"AND  communeid IS NULL";
		
		String uploadPath = "";
	    try {
		    connection = ds.getConnection();
			ps = connection.prepareStatement(uploadDirQuery);
		    res = ps.executeQuery();
		    if (res.next()) {
		    	uploadPath = res.getString(1);
		    }
		    else {
		    	throw new DumpFileWritingException("Unable to retrieve upload.directroy property " +
		    			"from DB in nodeconfiguration table");
		    }
		} catch (SQLException e1) {
			logger.error("", e1);
			throw new DumpFileWritingException("Unable to get upload directory from DB properties," +
					" processes dump file cannot be saved");
		}

	    //DumpFile upload directory
    	BufferedWriter writer = null;
 	    File dumpTempFile = null;
		File uploadDir = new File(uploadPath,"backup_pratiche");
		uploadDir.mkdir();
		
		if (uploadDir.canWrite()) {
			try {
	 		    dumpTempFile = File.createTempFile("dmp_", ".sql", uploadDir);
	 		    dumpTempFile.deleteOnExit();
	 			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dumpTempFile), "UTF-8"));
	 			
	 		} catch (IOException e) {
	 			logger.error("", e);
	 			throw new DumpFileWritingException("Exception writing processes dump file: " +
	 					"unable to create file or directory.");
	 		}
	    }
	    else {
	    	throw new DumpFileWritingException("Upload directory for dump file do not exists or not writable");
	    }
		
	    		
		try {
			//Write submitted process 
			String spQuery = FEInterfaceConstants.SUBMITTED_PROCESSES_QUERY;
			spQuery = QueryUtils.buildProcessesQueryPlaceholders(spQuery, selectedUsers, selectedNodes, true);
			connection = peopleDs.getConnection();
			ps= connection.prepareStatement(spQuery);
			// Set params in queries for submitted process 
			QueryUtils.setProcessesQueryParams(ps, processFilter, 0, 0, selectedUsers, selectedNodes, false,
					FEInterfaceConstants.SUBMITTED_PROCESSES_QUERY_ID);
			res = ps.executeQuery();
			QueryUtils.writeSubmittedProcessesDump(writer, res, true, false);
			ps.close();
			
	        //Write not submittable process 
			String nspQuery = FEInterfaceConstants.NOT_SUBMITTABLE_PROCESSES_QUERY;
			nspQuery = QueryUtils.buildProcessesQueryPlaceholders(nspQuery, selectedUsers, selectedNodes, true);
			ps= connection.prepareStatement(nspQuery);
			// Set params in queries for not submittable process 
			QueryUtils.setProcessesQueryParams(ps, processFilter, 0, 0, selectedUsers, selectedNodes, false,
					FEInterfaceConstants.NOT_SUBMITTABLE_PROCESSES_QUERY_ID);
			res = ps.executeQuery();	        
			QueryUtils.writeNotSubmittableProcessesDump(writer, res, false, false);
			ps.close();
			
			//Write pending  process
			String ppQuery = FEInterfaceConstants.PENDING_PROCESSES_QUERY;
			ppQuery = QueryUtils.buildProcessesQueryPlaceholders(ppQuery, selectedUsers, selectedNodes, true);
			ps= connection.prepareStatement(ppQuery);
			// Set params in queries for not submittable process 
			QueryUtils.setProcessesQueryParams(ps, processFilter, 0, 0, selectedUsers, selectedNodes, false,
					FEInterfaceConstants.PENDING_PROCESSES_QUERY_ID);
			res = ps.executeQuery();
			QueryUtils.writePendingProcessesDump(writer, res, false, false);
			ps.close();
			
			//Write pending processes acl
			String ppaclQuery = FEInterfaceConstants.PENDING_PROCESSES_ACL_QUERY;
			ppaclQuery = QueryUtils.buildProcessesQueryPlaceholders(ppaclQuery, selectedUsers, selectedNodes, true);
			ps= connection.prepareStatement(ppaclQuery);
			QueryUtils.setProcessesQueryParams(ps, processFilter, 0, 0, selectedUsers, selectedNodes, false,
					FEInterfaceConstants.PENDING_PROCESSES_QUERY_ID);
			res = ps.executeQuery();
			QueryUtils.writePendingProcessesAclDump(writer, res, false, false);
			ps.close();
			
			//write pending processes delegate
			String ppdQuery = FEInterfaceConstants.PENDING_PROCESSES_DELEGATE_QUERY;
			ppdQuery = QueryUtils.buildProcessesQueryPlaceholders(ppdQuery, selectedUsers, selectedNodes, true);
			ps= connection.prepareStatement(ppdQuery);
			QueryUtils.setProcessesQueryParams(ps, processFilter, 0, 0, selectedUsers, selectedNodes, false,
					FEInterfaceConstants.PENDING_PROCESSES_QUERY_ID);
			res = ps.executeQuery();
			QueryUtils.writePendingProcessesDelegateDump(writer, res, false, false);
			ps.close();
			
			//write submitted processes history
			String sphQuery = FEInterfaceConstants.SUBMITTED_PROCESSES_HISTORY_QUERY;
			sphQuery = QueryUtils.buildProcessesQueryPlaceholders(sphQuery, selectedUsers, selectedNodes, true);
			ps= connection.prepareStatement(sphQuery);
			QueryUtils.setProcessesQueryParams(ps, processFilter, 0, 0, selectedUsers, selectedNodes, false,
					FEInterfaceConstants.SUBMITTED_PROCESSES_QUERY_ID);
			res = ps.executeQuery();
			QueryUtils.writeSubmittedProcessesHistoryDump(writer, res, false, false);
			ps.close();
			
			//write submitted processes info
			String spiQuery = FEInterfaceConstants.SUBMITTED_PROCESSES_INFO_QUERY;
			spiQuery = QueryUtils.buildProcessesQueryPlaceholders(spiQuery, selectedUsers, selectedNodes, true);
			ps= connection.prepareStatement(spiQuery);
			QueryUtils.setProcessesQueryParams(ps, processFilter, 0, 0, selectedUsers, selectedNodes, false,
					FEInterfaceConstants.SUBMITTED_PROCESSES_QUERY_ID);
			res = ps.executeQuery();
			QueryUtils.writeSubmittedProcessesInfoDump(writer, res, false, true);
			ps.close();
			
			//write submitted processes info
			
		} catch(Exception ex) {
			
			if (connection != null) {
				try { connection.rollback(); } 
	            catch (SQLException rollbackEx) { logger.error("", rollbackEx); }
			}  
			logger.error("", ex);
			throw new DumpFileWritingException("Exception executing queries for getPagedProcesses", ex);  
        
		} finally {
			if (res != null) {
				try {
					res.close();
				} catch (SQLException ex) {}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException ex) {}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException ex) {}
			}
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException ex) {}
			} 
		} 
		
		//Zip file and return
		java.util.Date date = new java.util.Date();
		String nowTimestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
		File dumpZipFile = new File(uploadDir, "pratiche_dump_" + nowTimestamp + ".zip");
		String returnPath = "";
		
		try {
			returnPath = ZipUtils.zipSingleFile(dumpTempFile, dumpZipFile); 
		} catch (IOException e) {
			logger.error("Exception zipping processes dump file.", e);
		}
			
		//TODO RTETURN
		return returnPath;
	}
	
	
	private ProcessesVO getPagedProcesses(ProcessFilter processFilter,
			int lowerPageLimit, int pageSize, String[] selectedUsers, String[] selectedNodes) throws RemoteException {
		
		String query = FEInterfaceConstants.PAGED_PROCESSES_QUERY;
		String countQuery =  FEInterfaceConstants.COUNT_PROCESSES_QUERY;
		
	    //Connection for "people" db (LEGACY)
		Connection connection = null;
	    PreparedStatement ps = null;
	    PreparedStatement countPs = null;
	    ResultSet res = null;
	    ResultSet countRes = null;
	   
	    //init result
	    ProcessesVO result = new ProcessesVO(0, new ProcessBean[]{new ProcessBean()}); 
	    List <ProcessBean> processes = new ArrayList<ProcessBean>();
	    
		//Customize query to match prepareStatement placeholder with selected parmas 
	    query = QueryUtils.buildProcessesQueryPlaceholders(query, selectedUsers, selectedNodes, false);
	    countQuery = QueryUtils.buildProcessesQueryPlaceholders(countQuery, selectedUsers, selectedNodes, false);
	    		
		try {
			//Prepare Queries
			connection = peopleDs.getConnection();
			ps = connection.prepareStatement(query);
			countPs = connection.prepareStatement(countQuery);
			
			//Set params in queries
			QueryUtils.setProcessesQueryParams(ps, processFilter, lowerPageLimit, pageSize, selectedUsers, selectedNodes, true, FEInterfaceConstants.PAGED_PROCESSES_QUERY_ID);
			QueryUtils.setProcessesQueryParams(countPs, processFilter, lowerPageLimit, pageSize, selectedUsers, selectedNodes, false, FEInterfaceConstants.COUNT_PROCESSES_QUERY_ID);
			
	        //Execute
	        res = ps.executeQuery();
			countRes = countPs.executeQuery();

			//Populate result
			DateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
			
			while (res.next()) {
				 
				Calendar cal = Calendar.getInstance();
				cal.setTime(res.getDate("creation_date"));

				String attivita = (res.getString("attivita") == null) ? "nd": res.getString("attivita");
				String sottoAttivita = (res.getString("sottoattivita") == null) ? "nd": res.getString("sottoattivita"); 
				String procedimento = (res.getString("procedimento") == null) ? "nd": res.getString("procedimento");
							
				processes.add(new ProcessBean(
						res.getString("commune_id"),
						attivita , 
						sottoAttivita, 
						procedimento,
						cal, dateFormat.format(cal.getTime()), 
						res.getString("process_type"), res.getString("user_id")));
			}
			
			//GetCount
			int totalIndicatorsCount = 0;
		    if (countRes.next()) {
		    	totalIndicatorsCount = countRes.getInt("totalCount");
		    }
			
		    //Result
			result = new ProcessesVO(totalIndicatorsCount, (ProcessBean[]) processes.toArray(new ProcessBean[processes.size()]));

	     } catch(Exception ex) {
	            if (connection != null) {
	                try { connection.rollback(); } 
	                catch (SQLException rollbackEx) { logger.error("", rollbackEx); }
	            }                            
	            logger.error("", ex);
	            throw new java.rmi.RemoteException("Exception executing queries for getPagedProcesses", ex);    
		} finally {
			if (res != null) {
				try {
					res.close();
				} catch (SQLException ex) {}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException ex) {}
			}
			if (countRes != null) {
				try {
					res.close();
				} catch (SQLException ex) {}
			}
			if (countPs != null) {
				try {
					ps.close();
				} catch (SQLException ex) {}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException ex) {}
			}
		} 
		return result;
		
	}

	/* (non-Javadoc)
	 * @see it.people.feservice.FEInterface#setAsPeopleAdministrator(java.lang.String, java.lang.String, java.lang.String, java.lang.String[], java.lang.String)
	 */
	public void setAsPeopleAdministrator(String userId, java.lang.String eMail, java.lang.String userName, String[] allowedCommune, String mailReceiverTypeFlags) throws RemoteException {

		FEInterfaceExt feInterfaceExt = null;
    	
		feInterfaceExt = new FEInterfaceExtImpl(ds, peopleDs);
		
		feInterfaceExt.setAsPeopleAdministrator(userId, eMail, userName, allowedCommune, mailReceiverTypeFlags);
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.feservice.FEInterface#removeFromPeopleAdministrator(java.lang.String)
	 */
	public void removeFromPeopleAdministrator(java.lang.String userId) throws java.rmi.RemoteException {

		FEInterfaceExt feInterfaceExt = null;
    	
		feInterfaceExt = new FEInterfaceExtImpl(ds, peopleDs);
		
		feInterfaceExt.removeFromPeopleAdministrator(userId);
		
	}

	@Override
	public String[] getProcessUsers() throws RemoteException {
		
		List <String> result = new ArrayList <String>();
		String query = FEInterfaceConstants.PROCESSES_USERS_QUERY;
		
	    //Connection for "people" db (LEGACY)
		Connection connection = null;
	    PreparedStatement ps = null;
	    ResultSet res = null;	
	    
	    try {
			//Prepare Queries
			connection = peopleDs.getConnection();
			ps = connection.prepareStatement(query);
			
	        //Execute
	        res = ps.executeQuery();
	        while (res.next()) {
				result.add((String) res.getString(1));
			}		
	        
	    } catch(Exception ex) {
            if (connection != null) {
                try { connection.rollback(); } 
                catch (SQLException rollbackEx) { logger.error("", rollbackEx); }
            }                            
            logger.error("", ex);
            throw new java.rmi.RemoteException("Exception executing queries for getProcessUsers", ex);    
	    } finally {
			if (res != null) {
				try {
					res.close();
				} catch (SQLException ex) {}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException ex) {}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException ex) {}
			}
		} 
		return result.toArray(new String[result.size()]);
	}
	
	/* (non-Javadoc)
	 * @see it.people.feservice.FEInterface#getPeopleAdministrator(java.lang.String)
	 */
	public PeopleAdministratorVO getPeopleAdministrator(java.lang.String userId) throws java.rmi.RemoteException {

		FEInterfaceExt feInterfaceExt = null;
    	
		feInterfaceExt = new FEInterfaceExtImpl(ds, peopleDs);
		
		return feInterfaceExt.getPeopleAdministrator(userId);
		
	}

	@Override
	public VelocityTemplateDataVO getVelocityTemplatesData(String communeId, String servicePackage,
			boolean retrieveAll) throws RemoteException {

		FEInterfaceExt feInterfaceExt = null;
		feInterfaceExt = new FEInterfaceExtImpl(ds, peopleDs);

		return feInterfaceExt.getVelocityTemplatesData(communeId, servicePackage, retrieveAll);
	}
	
	@Override
	public boolean updateVelocityTemplatesData(VelocityTemplateDataVO templateDataVO, boolean delete)
			throws RemoteException {
		
		FEInterfaceExt feInterfaceExt = null;
		feInterfaceExt = new FEInterfaceExtImpl(ds, peopleDs);
		
		return feInterfaceExt.updateVelocityTemplatesData(templateDataVO, delete);
	}

	@Override
	public UserNotificationDataVO getUserNotifications(
			ProcessFilter processFilter, int lowerPageLimit, int pageSize,
			String type, String userId, String firstname, String lastname,
			String email, String communeId, Calendar from, Calendar to,
			String sortType, String sortColumn) throws RemoteException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Richiesti i dati dello scambio xml.");
		}

		String countQuery = "SELECT count(*) totalCount";
		String query = "SELECT id,subject,description,userid,firstname,lastname,email,commune_id,receiveddate";
		String query2 = " FROM "
				+ (UserNotificationBean.USER_NOTIFICATION_TYPE_SUGGESTION
						.equals(type) ? "user_suggestions"
						: "user_signalled_bugs")
				//+ " as tablequery"
				+ " WHERE 1=1";

		if (firstname != null && !"".equals(firstname.trim())) {
			firstname = firstname.trim();
			query2 += " AND (firstname LIKE ?)";
		}
		if (lastname != null && !"".equals(lastname.trim())) {
			lastname = lastname.trim();
			query2 += " AND (lastname LIKE ?)";
		}
		if (email != null && !"".equals(email.trim())) {
			email = email.trim();
			query2 += " AND (email = ?)";
		}
		if (communeId != null && !"".equals(communeId.trim())) {
			communeId = communeId.trim();
			query2 += " AND (commune_id = ?)";
		}

		if (from != null && to != null) {
			query2 += " AND (receiveddate BETWEEN ? AND ?)";
		} else if (from != null && to == null) {
			query2 += " AND receiveddate >= ?";
		} else if (from == null && to != null) {
			query2 += " AND receiveddate <= ?";
		}
		query2 += " ORDER BY "
				+(sortColumn!=null && !"".equals(sortColumn)?sortColumn:"receiveddate")
				+" "+(sortType!=null && !"".equals(sortType)?sortType:"DESC");
		// query += " LIMIT "+lowerPageLimit+", "+pageSize;
		
		query += query2;
		countQuery += query2;
		
		// pageSize == 1 ("Tutte")
		if (pageSize>1) {
			query += " LIMIT ?,?";
		}

		Connection connection = null;
		PreparedStatement ps = null, countPs = null;
		ResultSet res = null, countRes = null;
		UserNotificationDataVO beans = null;
		ArrayList<UserNotificationBean> beanArray = new ArrayList<UserNotificationBean>();
		UserNotificationBean bean;
		try {
			connection = peopleDs.getConnection();
			ps = connection.prepareStatement(query);
			countPs = connection.prepareStatement(countQuery);

			int i = 0;
			if (firstname != null && !"".equals(firstname)) {
				ps.setString(++i, "%" + firstname + "%");
				countPs.setString(i, "%" + firstname + "%");
			}
			if (lastname != null && !"".equals(lastname)) {
				ps.setString(++i, "%" + lastname + "%");
				countPs.setString(i, "%" + lastname + "%");
			}
			if (email != null && !"".equals(email)) {
				ps.setString(++i, email);
				countPs.setString(i, email);
			}
			if (communeId != null && !"".equals(communeId.trim())) {
				ps.setString(++i, communeId);
				countPs.setString(i, communeId);
			}
			if (from != null) {
				// Il primo GetTime() ritorna un java.util.date dal Calendar
				// Il secondo GetTime() ritorna un long
				ps.setDate(++i, new java.sql.Date(from.getTime().getTime()));
				countPs.setDate(i, new java.sql.Date(from.getTime().getTime()));
			}
			if (to != null) {
				ps.setDate(++i, new java.sql.Date(to.getTime().getTime()));
				countPs.setDate(i, new java.sql.Date(to.getTime().getTime()));
			}
			if (pageSize>1) {
    			ps.setInt(++i, lowerPageLimit);
    			ps.setInt(++i, pageSize);
			}
//			else {
//				pageSize = 1000000;
//			}

			res = ps.executeQuery();
			countRes = countPs.executeQuery();
			
			// GetCount
			int totalIndicatorsCount = 0;
			if (countRes.next()) {
				totalIndicatorsCount = countRes.getInt("totalCount");
			}

			while (res.next()) {
				bean = new UserNotificationBean();
				bean.setId(res.getString(1));
				bean.setSubject(res.getString(2));
				bean.setDescription(res.getString(3));
				bean.setUserId(res.getString(4));
				bean.setFirstName(res.getString(5));
				bean.setLastName(res.getString(6));
				bean.setEmail(res.getString(7));
				bean.setCommuneId(res.getString(8));

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(res.getDate(9));
				bean.setReceivedDate(calendar);

				beanArray.add(bean);
			}
			
			// beans = new UserNotificationDataVO(userNotifications);
			if (beanArray.size() > 0) {
				beans = new UserNotificationDataVO(
						beanArray
						.toArray(new UserNotificationBean[beanArray.size()]),
						totalIndicatorsCount
						//pageSize
						);
			}
			else {
				//UserNotificationBean[] userNotificationsEmpty = new UserNotificationBean[0];
				beans = new UserNotificationDataVO(
						//userNotificationsEmpty,
						new UserNotificationBean[0],
						0
						//pageSize
						);
			}
			
			
			
		} catch (Exception e) {
			logger.error("", e);
			e.printStackTrace();
		} finally {
			if (res != null)
				try {
					res.close();
				} catch (Exception ex) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception ex) {
				}
			if (connection != null)
				try {
					connection.close();
				} catch (Exception ex) {
				}
		}


		return beans;
	}
	
}
