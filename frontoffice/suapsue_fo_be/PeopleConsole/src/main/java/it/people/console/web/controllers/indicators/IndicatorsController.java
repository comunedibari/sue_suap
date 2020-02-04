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
package it.people.console.web.controllers.indicators;

import static it.people.console.web.servlet.tags.TagsConstants.LIST_HOLDER_TABLE_PREFIX;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.people.console.beans.support.IFilters;
import it.people.console.config.ConsoleConfiguration;
import it.people.console.domain.MonitoringIndicator;
import it.people.console.domain.PairElement;
import it.people.console.domain.exceptions.PagedListHoldersCacheException;
import it.people.console.dto.FENodeDTO;
import it.people.console.dto.FTPTransferResult;
import it.people.console.persistence.IPersistenceBroker;
import it.people.console.persistence.beans.support.EditableRowInputData;
import it.people.console.persistence.beans.support.ILazyPagedListHolder;
import it.people.console.persistence.beans.support.LazyPagedListHolderFactory;
import it.people.console.persistence.exceptions.LazyPagedListHolderException;
import it.people.console.persistence.exceptions.PersistenceBrokerException;
import it.people.console.security.AbstractCommand.CommandActions;
import it.people.console.utils.Constants;
import it.people.console.utils.FTPUtils;
import it.people.console.web.client.exceptions.FeServiceReferenceException;
import it.people.console.web.controllers.validator.IndicatorsValidator;
import it.people.console.web.servlet.mvc.AbstractListableController;
import it.people.console.web.utils.WebUtils;
import it.people.core.PplUserData;
import it.people.feservice.FEInterface;
import it.people.feservice.beans.IndicatorBean;
import it.people.feservice.beans.IndicatorFilter;
import it.people.feservice.beans.IndicatorsVO;

/**
 * @author Andrea Piemontese - Engineering Ingegneria Informatica S.p.A.
 * @created 20/lug/2012
 *
 */
@Controller
@RequestMapping("/Monitoraggio/Indicatori")
@SessionAttributes({Constants.ControllerUtils.DETAILS_STATUSES_KEY, "indicator"})
public class IndicatorsController extends AbstractListableController  {

	private IndicatorsValidator validator;
	
	@Autowired
	private DataSource dataSourcePeopleDB;
	
	@Autowired
	private IPersistenceBroker persistenceBroker;

	private int defaultPageSize = 10;
	private int defaultLowerPageLimit = 0;
	private DateFormat defaultDateFormat = new SimpleDateFormat("yyyy/MM/dd");
	private DateFormat defaultTimeFormat = new SimpleDateFormat("HH:mm");
	
	private DateFormat compactDateFormat = new SimpleDateFormat("yyyyMMdd");
	
	@Autowired
	public IndicatorsController(IndicatorsValidator validator) {
		this.validator = validator;
		this.setCommandObjectName("indicator");
	}
	
    @RequestMapping(value = "/indicatori.do", method = RequestMethod.GET)
    public String setupForm(ModelMap model, HttpServletRequest request) {

    	
    	MonitoringIndicator indicator = new MonitoringIndicator();
    	
    	//Ignore PagedListHolder sort requests
    	if (!(request.getQueryString() != null && request.getQueryString().startsWith("action=sort"))) {
    		
	    	//Set page properties
	    	this.setPageInfo(model, "monitoraggio.indicatori.title", null, "consoleS");
	
	    	//Empty model bean and filter
	    	IndicatorFilter defaultFilter = new IndicatorFilter();
	    	indicator = new MonitoringIndicator();
	   
	    	//init business hours
	    	setOfficeBusinessHours(indicator);
	    	
	    	try {
	    		if (!indicator.getPagedListHolders().containsKey(Constants.PagedListHoldersIds.MONITORING_INDICATORS_LIST)) {
	    			indicator.addPagedListHolder(prepareIndicatorsPagedList(defaultFilter, defaultLowerPageLimit, defaultPageSize, 
	    					indicator.getSelectedEnti(), indicator.getSelectedAttivita()));
	    		}
	    		else {
	    			indicator.updatePagedListHolder(prepareIndicatorsPagedList(defaultFilter, defaultLowerPageLimit, defaultPageSize,
	    					indicator.getSelectedEnti(), indicator.getSelectedAttivita()));  
	    		}
	    		
			} catch (LazyPagedListHolderException e) {
				logger.error("LazyPagedListHolderException adding paged list holder");
			} catch (PagedListHoldersCacheException e) {
				logger.error("PagedListHoldersCacheException adding paged list holder");
			}
    	}
    	
    	
    	//Populate model
    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/admin/sidebar.jsp");
   
    	model.put("indicator", indicator);

    	model.put("entiList", getEntiList());
    	
    	model.put("attivitaList", getAttivitaList());
    	
    	model.put("isSend", isPrefixParamInRequest(request, "send"));
    	
    	//Return control to some View
    	return getStaticProperty("monitoraggio.indicatori.view");	
    }
  

	@RequestMapping(value = "/indicatori.do", method = RequestMethod.POST)
    public String processSubmit(ModelMap model,
    		@ModelAttribute("indicator") MonitoringIndicator indicator, BindingResult result,
    		HttpServletRequest request) {

		//Navigation actions
		boolean isGetResult = this.isParamInRequest(request, "getResult");	
		boolean isSend = isPrefixParamInRequest(request, "send");
		
		//Handle GET RESULT
		if (isGetResult) {
			validator.validate(indicator, result);
			
			if (!result.hasErrors()) {
				processGetResult(indicator);
				
		    	//Store last calculated indicator to be sent
				indicator.setLastFrom(indicator.getFrom());
				indicator.setLastTo(indicator.getTo());
				indicator.setLastSelectedEnti(indicator.getSelectedEnti().clone());
				indicator.setLastSelectedAttivita(indicator.getSelectedAttivita().clone());
			}
		}
		
		//Handle SEND
		if (isSend) {
			processSendToFTP(indicator, model, request);
		}
	
		
    	//Set page properties
    	this.setPageInfo(model, "monitoraggio.indicatori.title", null, "consoleS");
		
		//Populate the Model  	
    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/admin/sidebar.jsp");
    	
    	model.put("indicator", indicator);

    	//Populate model for page components
    	model.put("entiList", getEntiList());
    	model.put("attivitaList", getAttivitaList());

    	model.put("isSend", isSend);
    	
    	if (this.isPrefixParamInRequest(request, LIST_HOLDER_TABLE_PREFIX)) {
    		processListHoldersRequests(request, indicator, model);
    	}
    	
    	//return control to some View
		return getStaticProperty("monitoraggio.indicatori.view");
    }
        

	/**
     * Process get result
     * @param indicatorFilter the indicator filter to get result
     */
	private void processGetResult(MonitoringIndicator indicator) {

		IndicatorFilter filter = new IndicatorFilter(
				defaultDateFormat.format(indicator.getFromDate().getTime()),
				defaultDateFormat.format(indicator.getToDate().getTime()),
				defaultTimeFormat.format(indicator.getOfficeMorningOpeningTime()),
				defaultTimeFormat.format(indicator.getOfficeMorningClosingTime()),
				null, null);

		try {
    		indicator.updatePagedListHolder(prepareIndicatorsPagedList(filter, defaultLowerPageLimit, defaultPageSize, indicator.getSelectedEnti(), indicator.getSelectedAttivita()));
		} catch (LazyPagedListHolderException e) {
			logger.error("LazyPagedListHolderException adding paged list holder");
		} catch (PagedListHoldersCacheException e) {
			logger.error("PagedListHoldersCacheException adding paged list holder");
		}
	}

	
    /**
	 * @param indicator
	 */
	private void processSendToFTP(MonitoringIndicator indicator, ModelMap model, HttpServletRequest request) {
		
		//Build a filter using last 
		IndicatorFilter filter = new IndicatorFilter(
				defaultDateFormat.format(indicator.getLastFromDate().getTime()),
				defaultDateFormat.format(indicator.getLastToDate().getTime()),
				defaultTimeFormat.format(indicator.getOfficeMorningOpeningTime()),
				defaultTimeFormat.format(indicator.getOfficeMorningClosingTime()),
				null, null);
		
		//TODO this operation should be executed for every FEinterface.
		FEInterface feInterface = null;
		try {
			feInterface = getFEInterfaceFromFirstRegisteredNode();
		} catch (PersistenceBrokerException e) {
			logger.error("Exception while getting FEInterface.");
		} catch (FeServiceReferenceException e) {
			logger.error("FeServiceReferenceException while getting FEInterface.");
		}
		
		IndicatorBean[] indicators = null;
		IndicatorsVO monitoringIndicators = null;
		
		try {
			monitoringIndicators = feInterface.getMonitoringIndicators(filter, defaultLowerPageLimit, 
					defaultPageSize, indicator.getLastSelectedEnti(), indicator.getLastSelectedAttivita(), true);
			
			indicators = monitoringIndicators.getIndicators();
			
		} catch (RemoteException e) {
			logger.error("PagedListHoldersCacheException adding paged list holder");
		}
		
		//Write data file and SEND TO FTP
		FTPTransferResult transferResult = new FTPTransferResult();
		try {
			File tmpDir = WebUtils.getTempDir(request.getSession().getServletContext());
    		if (logger.isDebugEnabled()) {
    			logger.debug("Temp file for FTP transfer = " + tmpDir.getAbsolutePath());
    		}
			File monitoringFile = writeMonitoringFile(monitoringIndicators, tmpDir);
			
			//Get console configuration
			ConsoleConfiguration consConf = (ConsoleConfiguration) request.getSession().getServletContext().getAttribute(Constants.System.SERVLET_CONTEXT_CONSOLE_CONFIGURATION_PROPERTIES);
			
			String fileName = buildMonitoringFilename(indicator, request);
			
			transferResult = FTPUtils.uploadFile(monitoringFile,  fileName, consConf.getMonitoringFTPHost(),
					consConf.getMonitoringFTPPort(), consConf.getMonitoringFTPUser(), consConf.getMonitoringFTPPassword());
			
			if (!transferResult.isError()) {
				transferResult.setMessage(this.getProperty("message.ftpUpload.success"));
			} else {
				transferResult.setMessage(this.getProperty("message.ftpUpload.fail"));
			}
						
		} catch (IOException e) {
			transferResult = new FTPTransferResult();
			transferResult.setError(true);
			transferResult.setErrorMessage(this.getProperty("message.ftpUpload.errorMessage.ioException"));
			transferResult.setMessage(this.getProperty("message.ftpUpload.fail"));
		}

		model.addAttribute("transferResult", transferResult);
		
	}
	
	
	/**
	 * Build monitoring indicator 
	 * 
	 * @param senderCode
	 * @param indicator
	 * @return
	 */
	private String buildMonitoringFilename(MonitoringIndicator indicator, HttpServletRequest request) {
		
		String fromDate = compactDateFormat.format(indicator.getFromDate().getTime());
		String toDate = compactDateFormat.format(indicator.getToDate().getTime());
		
		//Get user authentication from request (CODICE FISCALE)
		PplUserData pplUserDate = this.getLoggedUserData(request);
		String userCodeFis = pplUserDate.getCodiceFiscale();
		
		String fileName = userCodeFis + "_" +fromDate + "_" + toDate + "_" + getCurrentTimestamp("yyyyMMddHHmmss") + ".dat";
		return fileName;
	}

	/**
	 * Write monitoring indicator file in a temporary directory
	 * @param monitoringIndicators
	 * @return
	 * @throws IOException
	 */
	private File writeMonitoringFile(IndicatorsVO monitoringIndicators, File dir) throws IOException {
		
		//Create a temp file and delete on exit
		File file = File.createTempFile("tempIndicators" ,".dat", dir);
		file.deleteOnExit();
		FileWriter fw;
		BufferedWriter writer = null;
		
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		
		try {
			fw = new FileWriter(file);
			writer  = new BufferedWriter(fw);
			
			for (int i = 0; i < monitoringIndicators.getTotalResultCount(); i++) {
				
				String date = dateFormat.format(monitoringIndicators.getIndicators()[i].getTimestamp().getTime());
				
				writer.write(monitoringIndicators.getIndicators()[i].getCodiceEnte());
				writer.write(date);
				writer.write(monitoringIndicators.getIndicators()[i].getAttivitaName());
				writer.write(monitoringIndicators.getIndicators()[i].getIntermediaryUser());
				writer.write(monitoringIndicators.getIndicators()[i].getProcessName());
				writer.write(monitoringIndicators.getIndicators()[i].getServiceName());
				writer.write(monitoringIndicators.getIndicators()[i].getNumOfTransactions());
				writer.write(monitoringIndicators.getIndicators()[i].getNumOfTransactionsOutOfOpeningHours());
				writer.write(monitoringIndicators.getIndicators()[i].getExchangedDocs());
				writer.newLine();
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		finally {
		    if (writer != null) { 
		        try {
					writer.close();
				} catch (IOException e) {
				} 
		    }
		} 
		return file;
	}
	
	
	
	/**
	 * @return
	 * @throws PersistenceBrokerException
	 * @throws FeServiceReferenceException
	 */
	private FEInterface getFEInterfaceFromFirstRegisteredNode()
			throws PersistenceBrokerException, FeServiceReferenceException {
		
		boolean interfaceFound = false;
		FEInterface feInterface = null;
		
		Map<Integer, FENodeDTO> registeredNodesWithBEServices = persistenceBroker.getRegisteredNodesWithBEServices();
		Iterator<Entry<Integer, FENodeDTO>> iterator = registeredNodesWithBEServices.entrySet().iterator();
		
		while (iterator.hasNext() && !interfaceFound) {
			Entry<Integer, FENodeDTO> current = iterator.next();
			FENodeDTO feNode = current.getValue();
			
			try {
				feInterface = this.getFEInterface(feNode.getFeServiceURL());
				feInterface.echo("test");
				interfaceFound = true;
			}
			catch (FeServiceReferenceException e) {
				//Do nothing
			} catch (RemoteException e) {
				//Do nothing
			}
		}
		
		return feInterface;
	}


	/**
	 * Get nodes (enti) list from DB
	 * @return
	 */
    private List <PairElement<String, String>> getEntiList() {
		
    	List <PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
    	result.add(new PairElement<String, String>("*", "Tutti"));
    	
    	try {
			Map<Integer, FENodeDTO> registeredNodes = this.persistenceBroker.getRegisteredNodesWithBEServices();
			Iterator<Entry<Integer, FENodeDTO>> nodesIter = registeredNodes.entrySet().iterator();
			while(nodesIter.hasNext()) {
				FENodeDTO node = nodesIter.next().getValue();
				result.add(new PairElement<String, String>(node.getMunicipalityCode(), node.getName()));
			}
			
		} catch (PersistenceBrokerException e) {
				logger.error("Unable to fetch registered nodes.", e);
		}
		return result;
    }
    

    /**
     * Get solutions list from DB
     * @return
     */
    private List <PairElement<String, String>> getAttivitaList() {
		
       	List <PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
       	result.add(new PairElement<String, String>("*", "Tutti"));;
       	
    		String attivita = "";
    		
    	    String query = "SELECT DISTINCT attivita FROM service WHERE package NOT LIKE \'%%admin%%\' AND package NOT LIKE \'%%praticheOnLine%%\'";
    	    		
    	    Connection connection = null;
    		Statement statement = null;
    		ResultSet resultSet = null;
    		try {
    			connection = dataSourcePeopleDB.getConnection();
    			statement = connection.createStatement();
    			resultSet = statement.executeQuery(query);

    			while(resultSet.next()) {
    				attivita = resultSet.getString("attivita");
    				result.add(new PairElement<String, String>(attivita, attivita));
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
    		return result;
    }

  
    /**
     * @param filter the filter object to get filtered results
     * @param lowerPageLimit default starting lower page limit: 0 is fine; 
     * 		  (the pagination will be managed by a LazyPagedListHolder)
     * @param pageSize default page size
     * @return
     * @throws LazyPagedListHolderException
     */
    private ILazyPagedListHolder prepareIndicatorsPagedList(IndicatorFilter filter, int lowerPageLimit, int pageSize, String[] selectedEnti, String[] selectedAttivita) throws LazyPagedListHolderException {
		
    	ILazyPagedListHolder indicatorsPagedListHolder = null;
		
		//Columns to extract
        List <String> rowColumnsIdentifiers = new ArrayList <String>();
        rowColumnsIdentifiers.add("nomeEnte");
        rowColumnsIdentifiers.add("codiceEnte");
        rowColumnsIdentifiers.add("attivitaName");
        rowColumnsIdentifiers.add("serviceName");
        rowColumnsIdentifiers.add("numOfTransactions");
        rowColumnsIdentifiers.add("numOfTransactionsOutOfOpeningHours");
        rowColumnsIdentifiers.add("exchangedDocs");
        
        
		//Visible Columns
		List<String> visibleColumnsIdentifiers = new ArrayList<String>();
		visibleColumnsIdentifiers.add("nomeEnte");
		visibleColumnsIdentifiers.add("codiceEnte");
		visibleColumnsIdentifiers.add("attivitaName");
		visibleColumnsIdentifiers.add("serviceName");
		visibleColumnsIdentifiers.add("numOfTransactions");
		visibleColumnsIdentifiers.add("numOfTransactionsOutOfOpeningHours");
		visibleColumnsIdentifiers.add("exchangedDocs");
        
		
        //Visible columns labels
		List<String> visibleColumnsLabels = new ArrayList<String>();
		visibleColumnsLabels.add("Nome ente");
		visibleColumnsLabels.add("Istat ente");
		visibleColumnsLabels.add("Attità");
		visibleColumnsLabels.add("Procedimento");
		visibleColumnsLabels.add("NTC");
		visibleColumnsLabels.add("NTR");
		visibleColumnsLabels.add("NAD");
        
        
		//Parms to call method on source object
		Class paramtypes[] = new Class[6];
		paramtypes[0] = IndicatorFilter.class;
		paramtypes[1] = Integer.TYPE;
		paramtypes[2] = Integer.TYPE;
		paramtypes[3] = String[].class;
		paramtypes[4] = String[].class;
		paramtypes[5] = Boolean.TYPE;
			
		//Paging params
        Object arglist[] = new Object[6];
        arglist[0] = filter;
        arglist[1] = new Integer(lowerPageLimit);
        arglist[2] = new Integer(pageSize);
        arglist[3] = selectedEnti;
        arglist[4] = selectedAttivita;
        arglist[5] = false;
		
        FEInterface feInterface = null;
        
        try {
        	//TODO this operation should be executed for every FEinterface.
			feInterface = getFEInterfaceFromFirstRegisteredNode();

		} catch (FeServiceReferenceException e) {
			logger.error("FeServiceReferenceException while preparing IndicatorsPagedList", e);	
		} catch (PersistenceBrokerException e) {
			logger.error("PersistenceBrokerException while preparing IndicatorsPagedList", e);
		}
			
		indicatorsPagedListHolder = LazyPagedListHolderFactory.getLazyPagedListHolder(
				Constants.PagedListHoldersIds.MONITORING_INDICATORS_LIST, feInterface,
				"getMonitoringIndicators", paramtypes, arglist, 1, 2, rowColumnsIdentifiers,
				visibleColumnsIdentifiers, visibleColumnsLabels);

		return indicatorsPagedListHolder;
	}
    
    /**
     * Parse from properties and set Office Business hours into a MonitoringIndicator bean.
     * 
	 * @param indicator 
	 */
	private void setOfficeBusinessHours(MonitoringIndicator indicator) {
    	
    	SimpleDateFormat df = new SimpleDateFormat("HH:mm");
    	
    	try {
	    	indicator.setOfficeMorningOpeningTime(
	    			df.parse(this.getProperty("monitoring.indicators.office.morningOpeningTime")));
	    	
	    	indicator.setOfficeMorningClosingTime(
	    			df.parse(this.getProperty("monitoring.indicators.office.morningClosingTime")));
	    	
	    	indicator.setOfficeAfternoonOpeningTime(
	    			df.parse(this.getProperty("monitoring.indicators.office.afternoonOpeningTime")));
	    	
	    	indicator.setOfficeAfternoonClosingTime(
	    			df.parse(this.getProperty("monitoring.indicators.office.afternoonClosingTime")));
	    	
		} catch (ParseException e) {
			logger.error("Unable to parse office business hours from system.xml: parse exception.", e);
		} 
	}
    

	/* (non-Javadoc)
	 * @see it.people.console.web.servlet.mvc.AbstractListableController#prepareFilters()
	 */
	@Override
	protected IFilters prepareFilters() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.people.console.web.servlet.mvc.AbstractListableController#processAction(java.lang.String, it.people.console.security.AbstractCommand.CommandActions, it.people.console.persistence.beans.support.EditableRowInputData, javax.servlet.http.HttpServletRequest, org.springframework.ui.ModelMap)
	 */
	@Override
	protected void processAction(String pagedListHolderId,
			CommandActions action, EditableRowInputData editableRowInputData,
			HttpServletRequest request, ModelMap modelMap) {
	}

	/**
	 * Get current timestamp in String format
	 * 
	 * @param format the format ex. "yyyy-MM-dd-HH:mm:ss"
	 * @return
	 */
	private String getCurrentTimestamp(String format) {
		
	    DateFormat dateFormat = new SimpleDateFormat(format);
	    Date date = new Date();
	    return dateFormat.format(date);
	}


    
}
