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

import it.people.console.domain.AuditConversationFilter;
import it.people.feservice.FEInterface;
import it.people.feservice.beans.AuditConversationsBean;
import it.people.feservice.beans.AuditFeBeXmlBean;
import it.people.feservice.beans.AuditUserBean;
import it.people.feservice.client.FEInterfaceServiceLocator;

/**
 * @author Luca Barbieri - Pradac Informatica S.r.l.
 * @created 05/mag/2011 11.29.50
 * 
 */

public class AuditConversationsViewer {
	
	private static Logger logger = LoggerFactory.getLogger(AuditConversationsViewer.class);

    private URL reference;
    protected String codiceComune;
    private DataSource dataSourcePeopleDB;
    protected int startingPoint;
    protected int pageSize;
    
	public void goToLastPage(int lastPage) {
		if (lastPage == 0) {
			this.startingPoint = 0;
		} else {
			// (lastPage -1 ) * pageSize
			this.startingPoint = (lastPage - 1) * pageSize;
		}
	}
    
	public void increaseStartingPoint(int res_count) {
		if ((startingPoint + pageSize) < res_count) {
			this.startingPoint = startingPoint + pageSize;
		}
	}

	public void decreaseStartingPoint() {
		if (startingPoint >= pageSize) {
			this.startingPoint = startingPoint - pageSize;
		}
	}

	public void setStartingPoint(int startingPoint) {
		this.startingPoint = startingPoint;
	}
	public int getStartingPoint() {
		return this.startingPoint;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageSize() {
		return this.pageSize;
	}

	protected FEInterface getFEInterface() 
    throws ServiceException{
    	FEInterfaceServiceLocator locator = new FEInterfaceServiceLocator();
        FEInterface feInterface = locator.getFEInterface(reference);
    	return feInterface;
    }
    
    /** Creates a new instance of AuditConversationsViewer */
    public AuditConversationsViewer(URL reference, String codiceComune, DataSource dataSourcePeopleDB, int startingPoint, int pageSize) {
        this.reference = reference;
        this.codiceComune = codiceComune;
        this.dataSourcePeopleDB = dataSourcePeopleDB;
        this.startingPoint = startingPoint;
        this.pageSize = pageSize;
    }
    
    /** Creates a new instance of AuditConversationsViewer */
    public AuditConversationsViewer(URL reference, String codiceComune, DataSource dataSourcePeopleDB) {
    	this.reference = reference;
    	this.codiceComune = codiceComune;
    	this.dataSourcePeopleDB = dataSourcePeopleDB;
    }
    
    
    // Lista Utenti per Comune
    public ArrayList<String> getAuditUsersForComune() 
    throws AuditConversationsViewerException {
        try {                       
			return getAuditUsersList(getFEInterface()
					.getAuditUsersForComune(this.codiceComune));
        } catch (Exception e) {
        	logger.error("", e);
        	throw new AuditConversationsViewerException("Impossibile determinare il log delle audit conversations.", e);
        }
    } 
    
    
    // utente AuditUsers
    public AuditUserBean getAuditUser(String auditUserId, String userAccrId) 
    throws AuditConversationsViewerException {
    	try {                       
    		return getFEInterface().getAuditUser(auditUserId, userAccrId);
    	} catch (Exception e) {
    		logger.error("", e);
    		throw new AuditConversationsViewerException("Impossibile determinare i dati dell'utente.", e);
    	}
    } 
    // conversation AuditConversations
    public AuditConversationsBean getAuditConversation(String conversationId) 
    throws AuditConversationsViewerException {
    	try {                       
    		return getFEInterface().getAuditConversation(conversationId);
    	} catch (Exception e) { 
    		logger.error("", e);
    		throw new AuditConversationsViewerException("Impossibile determinare i dati della conversazione.", e);
    	}
    } 
    
    // AuditFeBeXml
    public AuditFeBeXmlBean getAuditFeBeXml(String xmlId) 
    throws AuditConversationsViewerException {
    	try {                       
    		return getFEInterface().getAuditFeBeXml(xmlId);
    	} catch (Exception e) {
    		logger.error("", e);
    		throw new AuditConversationsViewerException("Impossibile recuperare i file xml scambiati.", e);
    	}
    } 
    
    // Chiamata di default all'apertura della pagina di gestione dei log delle audit conversations.
//    public ArrayList<AuditConversationsBean> getAllAuditConversationsForComune() 
//    throws AuditConversationsViewerException {
//        try {                       
//			return getAuditConversationsList(getFEInterface()
//					.getAllAuditConversationsForComune(this.codiceComune,
//							Integer.toString(this.startingPoint),
//							Integer.toString(this.pageSize)));
//        } catch (Exception e) {
//        	logger.error("", e);
//        	throw new AuditConversationsViewerException("Impossibile determinare il log delle audit conversations.", e);
//        }
//    } 
    
    // ritorna tutti i log delle audit conversations del SERVIZIO 'idServizio'
//    public ArrayList<AuditConversationsBean> getAuditConversations(int idServizio, String auditUserTaxCode) 
//    throws AuditConversationsViewerException {
//        try {
//        	String serviceName = getServiceName(idServizio);
//
//			return getAuditConversationsList(getFEInterface()
//					.getAuditConversationsForService(this.codiceComune,
//							auditUserTaxCode, serviceName,
//							Integer.toString(this.startingPoint), 
//							Integer.toString(this.pageSize)));
//        } catch (Exception e) {
//        	logger.error("", e);
//        	throw new AuditConversationsViewerException("Impossibile determinare il log delle audit conversations.", e);
//        }    	
//    }

    /**
     * Ritorna il log delle audit conversations di tutti i servizi e di tutti gli utenti
     * compresi tra le date <code>from</code> e <code>to</code>.
     * @param from
     * @param to
     * @return
     * @throws AuditConversationsViewerException
     */
//    public ArrayList<AuditConversationsBean> getAuditConversations(Calendar from, Calendar to)
//    throws AuditConversationsViewerException {
//        try {           
//			return getAuditConversationsList(getFEInterface()
//					.getAuditConversationsForDate(this.codiceComune, from, to,
//							Integer.toString(this.startingPoint),
//							Integer.toString(this.pageSize)));
//        } catch (Exception e) {
//        	logger.error("", e);
//        	throw new AuditConversationsViewerException("Impossibile determinare il log delle audit conversations.", e);
//        }    	
//    }
           
    // filtra sia con le date che con l'utente e il nome del servizio.    
    public ArrayList<AuditConversationsBean> getAuditConversations(int idServizio, String auditUserTaxCode, Calendar from, Calendar to) 
    throws AuditConversationsViewerException {
        try {
        	String serviceName = getServiceName(idServizio);
        
			return getAuditConversationsList(getFEInterface()
					.getAuditConversationsForAllParameters(this.codiceComune,
							auditUserTaxCode, serviceName, from, to,
							Integer.toString(this.startingPoint),
							Integer.toString(this.pageSize)));
        } catch (Exception e) {
        	logger.error("", e);
        	throw new AuditConversationsViewerException("Impossibile determinare il log.", e);
        }    	
    }
    
    // filtra sia con le date che con l'utente e il nome del servizio.    
    public ArrayList<AuditConversationsBean> getAuditConversations(AuditConversationFilter filter) 
    throws AuditConversationsViewerException {
    	try {
    		// recupero i valori del filtro per impostare la selezione della query
    		boolean isUtente = (filter.getTipoUtente() == 0) ? true : false;
    		String firstName = "";
    		String lastName = "";
    		String businessName = "";
    		String taxCode = "";
    		if(isUtente){
    			//cittadini
    			taxCode = filter.getTaxCode();
    			firstName = filter.getFirstName();
    			lastName = filter.getLastName();
    		}else{
    			//intermediari
    			businessName = filter.getBusinessName();
    			firstName = filter.getFirstNameNp();
    			lastName = filter.getLastNameNp();			
    		}
    		int serviceId = filter.getServiceId();
    		String serviceName = getServiceName(serviceId);

    		Calendar fromDate = filter.getFromDate();
    		Calendar toDate = filter.getToDate();
    		
    		String[] queries = prepareAuditConversationsQuery(
    				this.codiceComune, isUtente, firstName, lastName, taxCode, 
    				businessName, serviceName, fromDate, toDate,
					Integer.toString(this.startingPoint),
					Integer.toString(this.pageSize));
    		
    		String query = queries[0]; 
    		String queryCount = queries[1]; 
					
    		
    		return getAuditConversationsList(getFEInterface()
    				.getAuditConversations(query, queryCount)); //TODO
    		
    	} catch (Exception e) {
    		logger.error("", e);
    		throw new AuditConversationsViewerException("Impossibile determinare il log.", e);
    	}    	
    }
    
    
    
    /**
	 * @param communeId
	 * @param tipoUtente
	 * @param firstName
	 * @param lastName
	 * @param taxCode
	 * @param businessName
	 * @param serviceName
	 * @param from
	 * @param to
	 * @param startingPoint
	 * @param pageSize
	 * @return
	 */
	private String[] prepareAuditConversationsQuery(String communeId,
			boolean isUtente, String firstName, String lastName, String taxCode,
			String businessName, String serviceName, Calendar from,
			Calendar to, String startingPoint, String pageSize) {
		
		// TODO togliere riferimenti a idcommune startingPoint e pageSize come parametri, sono globali
		
    	boolean isFirstNameSet  = firstName != null && !"".equals(firstName);
    	boolean isLastNameSet = lastName != null && !"".equals(lastName);
    	boolean isTaxCodeSet = taxCode != null && !"".equals(taxCode);
    	boolean isBusinessNameSet = businessName != null && !"".equals(businessName);
    	boolean isServiceNameSet = serviceName != null && !"".equals(serviceName); 

    	String selectCountQuery =
    		"SELECT COUNT(*)";
    	
    	String selectMessageQuery =
    		"SELECT ac.id, ac.audit_users_ref, au.np_tax_code, au.np_first_name, au.np_last_name, au.commune_key, " +
    			"ac.pjp_time_stamp, ac.audit_timestamp, ac.process_name, ac.action_name, ac.message, ac.includexml, " +
    			"aua.tipo_qualifica, aua.lp_first_name, aua.lp_last_name, aua.lp_business_name ";
    	
    	String messageQuery = 
    		"FROM audit_conversations AS ac " +
    			"JOIN audit_users AS au ON ac.audit_users_ref = au.id " +
    			"JOIN audit_users_accr AS aua ON ac.audit_users_accr = aua.id " +
    		"WHERE au.commune_key = " + communeId;
    	
    	if (isUtente) {
    		messageQuery += " AND aua.tipo_qualifica = 'utente'";
    		if(isFirstNameSet){
    			messageQuery += " AND UPPER(au.np_first_name) LIKE UPPER(\"%"+firstName+"%\") ";
    		}
    		if(isLastNameSet){
    			messageQuery += " AND UPPER(au.np_last_name) LIKE UPPER(\"%"+lastName+"%\") ";
    		}
    		if(isTaxCodeSet){
    			messageQuery += " AND UPPER(au.np_tax_code) LIKE UPPER(\"%"+taxCode+"%\") ";
    			
    		}
    	} else {
    		messageQuery += " AND aua.tipo_qualifica != 'utente' ";
    		if(isFirstNameSet){
    			messageQuery += " AND UPPER(aua.lp_first_name) LIKE UPPER(\"%"+firstName+"%\") ";
    		}
    		if(isLastNameSet){
    			messageQuery += " AND UPPER(aua.lp_last_name) LIKE UPPER(\"%"+lastName+"%\") ";
    		}
    		if(isBusinessNameSet){
    			messageQuery += " AND UPPER(aua.lp_business_name) LIKE UPPER(\"%"+businessName+"%\") ";
    		}
    	}
    	
    	if (isServiceNameSet)
    		messageQuery += " AND ac.process_name = '" + serviceName + "'";

       	if (from != null && to != null){ 
			messageQuery += " AND (ac.pjp_time_stamp BETWEEN '"+new java.sql.Date(from.getTime().getTime())+"' AND '"+new java.sql.Date(to.getTime().getTime())+"')";
    	}
		else if (from != null && to == null){
			messageQuery += " AND ac.pjp_time_stamp >= '"+new java.sql.Date(from.getTime().getTime())+"' ";
		}
		else if (from == null && to != null){
			messageQuery += " AND ac.pjp_time_stamp <= '"+new java.sql.Date(to.getTime().getTime())+"' ";
		}
        
        messageQuery += " ORDER BY ac.audit_users_ref DESC, ac.pjp_time_stamp DESC, ac.audit_timestamp DESC ";
        selectCountQuery += messageQuery;

        selectMessageQuery += messageQuery;
        if (Integer.valueOf(pageSize)!=0){
			selectMessageQuery += " LIMIT " + startingPoint + " ," + pageSize;
        }
    	
		return new String[]{selectMessageQuery, selectCountQuery};
	}

	protected ArrayList<AuditConversationsBean> getAuditConversationsList(AuditConversationsBean[] auditConvArray){
    	ArrayList<AuditConversationsBean> auditConvsList = new ArrayList<AuditConversationsBean>();
    	if (auditConvArray == null)
    		return auditConvsList;
    	
    	for(int i = 0; i < auditConvArray.length; i++) {
    		auditConvsList.add(auditConvArray[i]);
    	}
    	return auditConvsList;
    }
    
    protected ArrayList<String> getAuditUsersList(String[] auditUsersArray){
    	ArrayList<String> auditUsersList = new ArrayList<String>();
    	if (auditUsersArray == null)
    		return auditUsersList;
    	
    	for(int i = 0; i < auditUsersArray.length; i++) {
    		auditUsersList.add(auditUsersArray[i]);
    	}
    	return auditUsersList;
    }
    
    
    protected String getServiceName(int idServizio) 
    throws SQLException, Exception {
    	Connection connection = null;
    	Statement stmt = null;
    	String serviceName="";
    	try {
    		connection = dataSourcePeopleDB.getConnection();
    		stmt = connection.createStatement();
    		ResultSet res;
    		
    		res = stmt.executeQuery("SELECT package FROM service WHERE id = " + idServizio);
    		if (res.next())
				serviceName = res.getString("package");    		
    		
    	} finally {
    		try { if (stmt != null) connection.close();} catch (SQLException e) {}
    		try { if (connection != null) connection.close();} catch (SQLException e) {}
    	}
    	return serviceName;    	
    }
    
//    protected AuditUserAndServiceName getAuditUserAndServiceName(String serviceName, String auditUserTaxCode) 
//    throws SQLException, Exception {
//    	AuditUserAndServiceName value = new AuditUserAndServiceName(); 
//    		
//		value.setServiceName(serviceName);
//		value.setAuditUserTaxCode(auditUserTaxCode);
//    		
//    	return value;    	
//    }
//    
//    protected class AuditUserAndServiceName { 
//    	protected String serviceName = "";
//    	protected String auditUserTaxCode = "";
//    	
//		public String getAuditUserTaxCode() { return auditUserTaxCode; }
//		public void setAuditUserTaxCode(String auditUserTaxCode) { this.auditUserTaxCode = auditUserTaxCode; }
//		public String getServiceName() { return serviceName; }
//		public void setServiceName(String serviceName) { this.serviceName = serviceName; }
//    }
    
    
    public class AuditConversationsViewerException extends Exception {
		private static final long serialVersionUID = -1038008912855306410L;

		public AuditConversationsViewerException(String message) {
    		super(message);
    	}
    	
    	public AuditConversationsViewerException(String message, Throwable inner) {
    		super(message, inner);
    	}

    }


}
