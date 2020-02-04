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
package it.people.console.web.controllers;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.people.console.domain.FENodeLogFilter;
import it.people.console.domain.FEServiceListElement;
import it.people.console.domain.PairElement;
import it.people.console.utils.Constants;
import it.people.console.web.controllers.utils.LoggerViewer;
import it.people.console.web.controllers.utils.LoggerViewer.LoggerViewerException;
import it.people.console.web.controllers.validator.FENodeViewLogsValidator;
import it.people.console.web.servlet.mvc.MessageSourceAwareController;
import it.people.feservice.beans.LogBean;

/**
 * @author Luca Barbieri - Pradac Informatica S.r.l.
 * @created 04/mag/2011 11.23.58
 *
 */
@Controller
@RequestMapping("/NodiFe")
@SessionAttributes({Constants.ControllerUtils.DETAILS_STATUSES_KEY, "feNode"})
public class FENodeViewLogsController extends MessageSourceAwareController {
	
	@Autowired
	private DataSource dataSourcePeopleDB;
	
	private List<PairElement<String, String>> logLevels = null;
	private Map<String, String> detailsStatuses = new HashMap<String, String>();
	private LoggerViewer viewer = null;
	
	private FENodeViewLogsValidator validator;
	
	@Autowired
	public FENodeViewLogsController(FENodeViewLogsValidator validator) {
		this.validator = validator;
		this.setCommandObjectName("feNode");
	}
	
	
	
    @RequestMapping(value = "/viewLog.do", method = RequestMethod.GET)
	public String setupForm(@RequestParam String action,
			@RequestParam String id, @RequestParam String plhId,
			ModelMap model, HttpServletRequest request) {


    	if (this.getLogLevels() == null) {
    		if (logger.isDebugEnabled()) {
    			logger.debug("Initializing log levels list...");
    		}
    		this.setLogLevels(this.getLogLevelsList());
    		if (logger.isDebugEnabled()) {
    			logger.debug("Log levels list initialized.");
    		}
    	}
    	
    	model.put("includeTopbarLinks", true);
    	model.put("sidebar", "/WEB-INF/jsp/fenodes/sidebar.jsp");

    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
    	model.addAttribute("logLevels", this.getLogLevels());
    	model.addAttribute("feServicesList", getFEServicesList(id));
    	
    	FENodeLogFilter defaultFilter = getDefaultFENodeLogFilter();
    	model.addAttribute("feNodeLogFilter", defaultFilter );
    	
		//crea il LoggerViewer
    	createLoggerViewer(id);
    	
    	model.addAttribute("logBeans", getLogBeans(id, defaultFilter));
    	
    	this.setPageInfo(model, "fenodes.nodeLogs.title", null, "feNodeWL");
    	
		return getStaticProperty("fenodes.nodeLogs.view");
    	
    }

	
    
    /**
     * @param model
     * @param id
     * @param feNodeLogFilter
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "/viewLog.do", method = RequestMethod.POST )
    public String processSubmit(ModelMap model, @RequestParam String id, @ModelAttribute("feNodeLogFilter")  
    		FENodeLogFilter feNodeLogFilter,      		
    		BindingResult result, 
    		HttpServletRequest request) {
    	
    	boolean isView = isPrefixParamInRequest(request, "view");
    	boolean isOrderByDate = isPrefixParamInRequest(request, "orderByDate");
    	boolean isOrderByLog = isPrefixParamInRequest(request, "orderByLog");
    	boolean isOrderByService = isPrefixParamInRequest(request, "orderByService");
    	boolean isOrderByMessage = isPrefixParamInRequest(request, "orderByMessage");
    	
   		//default orderType
		String orderType = FENodeLogFilter.ORDER_DESC;
		
    	if(isOrderByDate){
    		//se l'ordinamento richiesto è sulla stessa colonna inverto la direzione  
			if (feNodeLogFilter.getOrderBy().equals(FENodeLogFilter.ORDER_BY_DATE)) {
				orderType = feNodeLogFilter.getOrderType().equals(
						FENodeLogFilter.ORDER_DESC) ? 
						FENodeLogFilter.ORDER_ASC : FENodeLogFilter.ORDER_DESC;
			}
    		feNodeLogFilter.setOrderBy(FENodeLogFilter.ORDER_BY_DATE);
    		feNodeLogFilter.setOrderType(orderType);
    		isView = true;
    	}
    	
    	if(isOrderByLog){
    		//se l'ordinamento richiesto è sulla stessa colonna inverto la direzione  
			if (feNodeLogFilter.getOrderBy().equals(FENodeLogFilter.ORDER_BY_LOG)) {
				orderType = feNodeLogFilter.getOrderType().equals(
						FENodeLogFilter.ORDER_DESC) ? 
						FENodeLogFilter.ORDER_ASC : FENodeLogFilter.ORDER_DESC;
			}
    		feNodeLogFilter.setOrderBy(FENodeLogFilter.ORDER_BY_LOG);  		
    		feNodeLogFilter.setOrderType(orderType);
    		isView = true;
    	}
    	
    	if(isOrderByService){
    		//se l'ordinamento richiesto è sulla stessa colonna inverto la direzione  
    		if (feNodeLogFilter.getOrderBy().equals(FENodeLogFilter.ORDER_BY_SERVICE)) {
    			orderType = feNodeLogFilter.getOrderType().equals(
    					FENodeLogFilter.ORDER_DESC) ? 
    							FENodeLogFilter.ORDER_ASC : FENodeLogFilter.ORDER_DESC;
    		}
    		feNodeLogFilter.setOrderBy(FENodeLogFilter.ORDER_BY_SERVICE);  		
    		feNodeLogFilter.setOrderType(orderType);
    		isView = true;
    	}
    	
    	if(isOrderByMessage){
    		//se l'ordinamento richiesto è sulla stessa colonna inverto la direzione  
    		if (feNodeLogFilter.getOrderBy().equals(FENodeLogFilter.ORDER_BY_MESSAGE)) {
    			orderType = feNodeLogFilter.getOrderType().equals(
    					FENodeLogFilter.ORDER_DESC) ? 
    							FENodeLogFilter.ORDER_ASC : FENodeLogFilter.ORDER_DESC;
    		}
    		feNodeLogFilter.setOrderBy(FENodeLogFilter.ORDER_BY_MESSAGE);  		
    		feNodeLogFilter.setOrderType(orderType);
    		isView = true;
    	}
    	
		if (isView)  { 
			
			validator.validate(feNodeLogFilter, result);
			
			model.put("includeTopbarLinks", true);
	    	model.put("sidebar", "/WEB-INF/jsp/fenodes/sidebar.jsp");

	    	model.addAttribute("logLevels", this.getLogLevels());
	    	model.addAttribute("feServicesList", getFEServicesList(id));
	    	
	    	if(!result.hasErrors()){
	    		model.addAttribute("logBeans", getLogBeans(id, feNodeLogFilter));
	    	}
	    	this.setPageInfo(model, "fenodes.nodeLogs.title", null, "feNodeWL");
	    	
			return getStaticProperty("fenodes.nodeLogs.view");
		} 
		else return "redirect:elenco.do";
    }
    
    
    /**
	 * @return il Filtro impostato di default: tutti i servizi, livello di log Error, oggi
	 */
	private FENodeLogFilter getDefaultFENodeLogFilter() {
		
		FENodeLogFilter feNodeLogFilter = new FENodeLogFilter();
		
		feNodeLogFilter.setServiceId(0); 
		feNodeLogFilter.setLogLevel(4);
		feNodeLogFilter.setFromDate(Calendar.getInstance());
		feNodeLogFilter.setToDate(Calendar.getInstance());
		feNodeLogFilter.setOrder(FENodeLogFilter.ORDER_BY_DATE, FENodeLogFilter.ORDER_DESC);
		
		return feNodeLogFilter;
	}


	/**
	 * @param id
	 * @return il LoggerViewer per il nodo id
	 */
	private void createLoggerViewer(String id) {
		
		/* Trovo il reference del web service */
		String[] reference_communeid = getReferenceCommuneid(id);
		try {
			viewer = new LoggerViewer(new URL(reference_communeid[0]), 
					reference_communeid[1], dataSourcePeopleDB);
		} catch (MalformedURLException e) {
			logger.error("Il formato dell'url del web-service non e' valido:" + e.getMessage());
		}
	}
	
	
	/**
	 * @param id Nodo di cui recuperare il Log 
	 * @return
	 * @throws  
	 */
	private ArrayList<LogBean> getLogBeans(String id, FENodeLogFilter feNodeLogFilter)  {
		
		ArrayList<LogBean> logBeans = new ArrayList<LogBean>(); 
		
		try {
			// recupero i valori del filtro per impostare la selezione
			int logLevel = feNodeLogFilter.getLogLevel();
			int serviceId = feNodeLogFilter.getServiceId();
			Calendar fromDate = feNodeLogFilter.getFromDate();
			Calendar toDate = feNodeLogFilter.getToDate();
			String orderBy = feNodeLogFilter.getOrderBy();
			String orderType = feNodeLogFilter.getOrderType();
			
			if ((fromDate != null || toDate != null) && logLevel==0 && serviceId==0) {
	        	// selezione delle sole date
				logBeans = viewer.getLogs(fromDate, toDate, orderBy, orderType);
	        } else if (fromDate == null && toDate == null && (logLevel!=0 || serviceId !=0)) {            
	        	// selezione dei soli service e log level
	        	logBeans = viewer.getLogs(serviceId, logLevel);
	        } else if ((fromDate != null || toDate != null) && (logLevel != 0 || serviceId !=0)) {
	        	// sono indicati almeno una data e livello o servizio
	        	logBeans = viewer.getLogs(serviceId, logLevel, fromDate, toDate, orderBy, orderType);
	        } else {            
	        	logBeans = viewer.getAllLogs();
	        }
			
		} catch (LoggerViewerException e) {
			logger.error("impossibile determinare il log.");
		}
		
		return logBeans;
	}
	


	
	/**
	 * @param idComune
	 * @return la lista dei Servizi associati al nodo
	 */
	private List<FEServiceListElement> getFEServicesList(String idComune) {

        String queryServicesList = "SELECT id, nome FROM service WHERE nodeid = " + idComune;
        
		List<FEServiceListElement> result = new ArrayList<FEServiceListElement>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(queryServicesList);
            //Tutti i servizi
			FEServiceListElement s = new FEServiceListElement("0","Tutti i servizi");
            result.add(s);
			while(resultSet.next()) {
				result.add(new FEServiceListElement(resultSet.getString(1), resultSet.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}


	/**
	 * @param id
	 * @return reference e codicecomune, parametri per la connessione
	 */
	private String[] getReferenceCommuneid(String id) {

		String[] references = new String[2];

        String query = "SELECT codicecomune, reference FROM fenode WHERE id= " + id;
        
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				references[0] = resultSet.getString("reference");
				references[1] = resultSet.getString("codicecomune");
			}
			resultSet.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return references;
	}
	
	
    /**
     * @return Lista dei Livelli di log
     */
    private List<PairElement<String, String>> getLogLevelsList() {

		String queryLogLevelsList = "SELECT * FROM LOG ORDER BY 1";

		List<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
		Connection connection = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(queryLogLevelsList);
			while(resultSet.next()) {
				result.add(new PairElement<String, String>(resultSet.getString(1), resultSet.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
		
	}
    
	/**
	 * @return the logLevels
	 */
	private List<PairElement<String, String>> getLogLevels() {
		return logLevels;
	}

	/**
	 * @param logLevels the logLevels to set
	 */
	private void setLogLevels(List<PairElement<String, String>> logLevels) {
		this.logLevels = logLevels;
	}

}

