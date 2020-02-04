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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.people.console.beans.support.JasperReportSettings;
import it.people.console.beans.support.PageNavigationSettings;
import it.people.console.domain.AuditConversationFilter;
import it.people.console.domain.PairElement;
import it.people.console.jasper.IJasperReportView;
import it.people.console.utils.CastUtils;
import it.people.console.utils.Constants;
import it.people.console.web.controllers.utils.AuditConversationsViewer;
import it.people.console.web.controllers.utils.AuditConversationsViewer.AuditConversationsViewerException;
import it.people.console.web.controllers.validator.FEViewAuditConversationsValidator;
import it.people.console.web.servlet.mvc.MessageSourceAwareController;
import it.people.feservice.beans.AuditConversationsBean;

/**
 * @author Luca Barbieri - Pradac Informatica S.r.l.
 * @created 04/mag/2011 11.23.58
 *
 */
@Controller
@RequestMapping("/ServiziFe")
@SessionAttributes({Constants.ControllerUtils.DETAILS_STATUSES_KEY, "feService"})
public class FEServiceViewAuditConversationsController extends MessageSourceAwareController {
	
	@Autowired
	private DataSource dataSourcePeopleDB;
	
	@Autowired
	private IJasperReportView jasperGenericReport;
	
	private Map<String, String> detailsStatuses = new HashMap<String, String>();
	private AuditConversationsViewer viewer = null;
	
	private FEViewAuditConversationsValidator validator;
	
	protected static final String ANONYMOUS_USER = "NNMNNM70A01H536W";
	protected static final String ANONYMOUS_USER_TITLE = "Utente anonimo";
	
	
	@Autowired
	public FEServiceViewAuditConversationsController(FEViewAuditConversationsValidator validator) {
		this.validator = validator;
		this.setCommandObjectName("auditConversationFilter");
	}
	
	
    @RequestMapping(value = "/viewAuditConversations.do", method = RequestMethod.GET)
	public String setupForm(
			@RequestParam String action,
			@RequestParam String id, 
			@RequestParam String plhId,
			ModelMap model, HttpServletRequest request) {

    	AuditConversationFilter filter = null;
		if (request.getParameter("detail") != null) {
			// se ritorno dal dettaglio recupero il filtro precedente
			filter = (AuditConversationFilter) request.getSession().getAttribute("auditConversationsFilter");
		}
		
    	model.put("includeTopbarLinks", true);
    	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
    	
    	//crea l'AuditConversationsViewer
		// valori di default, si potrebbero recuperare da una configurazioni (db o properties)
    	int startingPoint = 0; 
    	int pageSize = 10;
		// se filtro già presente recupero le impostazioni
    	if(filter!=null){
			pageSize = filter.getPageSize();
    		startingPoint = (filter.getActualPage()-1) * pageSize;
    	}
    	String communeId = getCommuneIdFromService(id);
    	model.addAttribute("communeId", communeId);
    	createAuditConversationsViewer(communeId, startingPoint, pageSize); 

		//numero di risultati per pagina	
		PageNavigationSettings pageNavigationSettings = new PageNavigationSettings(); 
		model.addAttribute("resultForPageList", pageNavigationSettings.getResultsForPage());
    	model.addAttribute("auditUsersList", getAuditUsersList()); // utenti
    	
    	AuditConversationFilter defaultFilter; 
    	if(filter!=null){
    		defaultFilter = filter;
    	} else {
    		defaultFilter = getDefaultAuditConversationFilter(id, pageSize);
    	}
    	
    	model.addAttribute("auditConversationsBeans", getAuditConversationsBeans(id, communeId, defaultFilter));
    	model.addAttribute("auditConversationFilter", defaultFilter );

    	JasperReportSettings jasperReportSettings = new JasperReportSettings(); 	
    	model.addAttribute("reportTypes", jasperReportSettings.getReportTypes());
		
    	// salvo il filtro per averlo a disposizione dopo aver visionato un dettaglio
    	request.getSession().setAttribute("auditConversationsFilter", defaultFilter);
    	
    	this.setPageInfo(model, "nodeAuditConversations.title", null, "feServiceVAC");
    	
		return getStaticProperty("feservices.serviceAuditConversations.view");
    	
    }

	
    
    /**
     * @param model
     * @param id
     * @param filter
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "/viewAuditConversations.do", method = RequestMethod.POST )
    public String processSubmit(
			ModelMap model, 
			@RequestParam String id, 
			@ModelAttribute("auditConversationFilter") AuditConversationFilter filter,
    		BindingResult result, 
    		HttpServletRequest request, 
			HttpServletResponse response) {
    	
		boolean isView = isPrefixParamInRequest(request, "viewPage"); //visualizza e prima pagina
		boolean isPrev = isPrefixParamInRequest(request, "prevPage"); //pagina precedente
		boolean isNext = isPrefixParamInRequest(request, "nextPage"); //pagina successiva
		boolean isLast = isPrefixParamInRequest(request, "lastPage"); //ultima pagina

		boolean isBack = isPrefixParamInRequest(request, "back"); //torna alla pagina da report
		boolean isGenerateReport = this.isParamInRequest(request, "generateReport");
		model.addAttribute("reportGenerationErrorMessage", null);
		model.addAttribute("reportGenerationErrorException", null);

		if (isBack) {
			// se ritorno dalla generazione report recupero il filtro precedente
			filter = (AuditConversationFilter) request.getSession().getAttribute("auditConversationsFilter");
			model.addAttribute("auditConversationFilter", filter);
			isView = true;
		}
		
		//numero di risultati per pagina
		if(filter.getPageNavigationSettings().getResultsForPage()==null){
			filter.getPageNavigationSettings().setResultsForPage(Integer.toString(filter.getPageSize()));
		} else {
			filter.setPageSize(Integer.valueOf(filter.getPageNavigationSettings().getResultsForPage()));
		}
		
		viewer.setPageSize(filter.getPageSize());
		if (isLast) {
			int lastPage = filter.getTotalPages();
			viewer.goToLastPage(lastPage);
			isView = true;
		}
    	if (isNext){ 
    		int res_count = filter.getRes_count();
			viewer.increaseStartingPoint(res_count); 
			isView = true;
		}
    	if (isPrev){ 
    		viewer.decreaseStartingPoint(); 
    		isView = true;
    	}
		

		
    	JasperReportSettings jasperReportSettings = new JasperReportSettings();
    	
    	model.addAttribute("reportTypes", jasperReportSettings.getReportTypes());

		if (isView)  {
			if (!isNext && !isPrev && !isLast && !isBack) {
				viewer.setStartingPoint(0);
			}
			
			validator.validate(filter, result);
			model.put("includeTopbarLinks", true);
	    	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
	    	
	    	model.addAttribute("auditUsersList", getAuditUsersList()); // utenti
	    	
	    	if(!result.hasErrors()){
	    		String communeId = getCommuneIdFromService(id);
	    		ArrayList<AuditConversationsBean> list = getAuditConversationsBeans(id, communeId, filter);
	    		model.addAttribute("auditConversationsBeans", list);
	    	}
	    	model.addAttribute("auditConversationFilter", filter );
				
			PageNavigationSettings pageNavigationSettings = new PageNavigationSettings(); 
			model.addAttribute("resultForPageList", pageNavigationSettings.getResultsForPage());

			// salvo il filtro per averlo a disposizione dopo aver visionato un dettaglio
	    	request.getSession().setAttribute("auditConversationsFilter", filter);
			
	    	this.setPageInfo(model, "nodeAuditConversations.title", null, "feServiceVAC");
	    	
	    	return getStaticProperty("feservices.serviceAuditConversations.view"); 
			
		} else if (isGenerateReport) {
			
			String reportType = request.getParameter("reportSettings.reportType");
			
			if (reportType.equalsIgnoreCase(JasperReportSettings.ReportTypes.PDF_TYPE_KEY)) {
				//PDF
				try {
					String communeId = getCommuneIdFromService(id);
					jasperGenericReport.renderReport("PDF", 
							"audit", 
							getJRDataSource(id, communeId, filter, true), null, "audit", request, response);
				} catch (Exception e) {
					model.addAttribute("reportTypes", jasperReportSettings.getReportTypes());
					model.put("includeTopbarLinks", true);
					model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");

					model.addAttribute("auditUsersList", getAuditUsersList()); // utenti
					if (!result.hasErrors()) {
						String communeId = getCommuneIdFromService(id);
						ArrayList<AuditConversationsBean> list = getAuditConversationsBeans(id, communeId, filter);
						model.addAttribute("auditConversationsBeans", list);
					}
					model.addAttribute("auditConversationsFilter", filter);		
					
					PageNavigationSettings pageNavigationSettings = new PageNavigationSettings(); 
					model.addAttribute("resultForPageList", pageNavigationSettings.getResultsForPage());

					model.addAttribute("reportGenerationErrorMessage", "Si è verificato un errore durante la generazione del report.");
					model.addAttribute("reportGenerationErrorException", CastUtils.exceptionToString(e));
					
					this.setPageInfo(model, "nodeAuditConversations.title", null, "feServiceVAC");
					
					return getStaticProperty("feservices.serviceAuditConversations.view");
				}
				model.put("includeTopbarLinks", true);
				model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");

				model.addAttribute("pdf_complete_path", 
						String.valueOf(request.getSession().
								getAttribute(Constants.Reports.REPORT_FILE_PREVIEW_SESSION_KEY)));
				return getStaticProperty("reports.page.view");
		}
		else {	
					
				//CSV - HTML - XLS
				try{
					String fileName = "conversazioni";
					
					if (reportType.equalsIgnoreCase(JasperReportSettings.ReportTypes.CSV_TYPE_KEY) ||
						reportType.equalsIgnoreCase(JasperReportSettings.ReportTypes.HTML_TYPE_KEY) ||
						reportType.equalsIgnoreCase(JasperReportSettings.ReportTypes.XLS_TYPE_KEY) ){

						fileName+="."+reportType.toLowerCase();
					}
			
					String communeId = getCommuneIdFromService(id);

					byte[] output = jasperGenericReport.saveReport(reportType, 
							"audit", 
							getJRDataSource(id, communeId, filter, true), null, "audit", request, response);
					
					InputStream inStream = new ByteArrayInputStream(output);
					response.setHeader("Pragma", "private");
					response.setHeader("Cache-Control", "private, must-revalidate");
					response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
					ServletOutputStream out = response.getOutputStream();
					 
					while(inStream.read(output) != -1) {
						out.write(output);
					}
					inStream.close();
					out.flush();
					out.close();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				model.addAttribute("reportTypes", jasperReportSettings.getReportTypes());
				model.put("includeTopbarLinks", true);
				model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");

//				model.addAttribute("auditUsersList", getAuditUsersList()); // utenti
				

				if (!result.hasErrors()) {
					String communeId = getCommuneIdFromService(id);
					ArrayList<AuditConversationsBean> list = getAuditConversationsBeans(id, communeId, filter);
					model.addAttribute("auditConversationsBeans", list);
				}
				model.addAttribute("auditConversationsFilter", filter);	

				PageNavigationSettings pageNavigationSettings = new PageNavigationSettings(); 
				model.addAttribute("resultForPageList", pageNavigationSettings.getResultsForPage());

				this.setPageInfo(model, "nodeAuditConversations.title", null, "feServiceVAC");
				
				return getStaticProperty("feservices.serviceAuditConversations.view");
				
			} 	
		} else {
			// rimuovo il filtro
			request.getSession().removeAttribute("auditConversationsFilter");
			return "redirect:elenco.do";
		}
		
    }
    
    
    /**
	 * @return il Filtro impostato di default: servizio attuale, tutti gli utenti, oggi
	 */
	private AuditConversationFilter getDefaultAuditConversationFilter(String idservice, int pageSize) {
		
		AuditConversationFilter filter = new AuditConversationFilter();
		
		filter.setTipoUtente(0); //cittadini
		filter.setServiceId(Integer.valueOf(idservice)); //servizio attuale
//		filter.setTaxCode("0"); //tutti gli utenti
		filter.setFromDate(Calendar.getInstance());
		filter.setToDate(Calendar.getInstance());
		filter.setPageSize(pageSize);
		filter.getPageNavigationSettings().setResultsForPage(Integer.toString(pageSize));
		
		return filter;
	}

	
	/**
	 * @param id
	 * @return l'AuditConversationsViewer per il nodo id
	 */
	private void createAuditConversationsViewer(String id, int startingPoint, int pageSize ) {
		
		/* Trovo il reference del web service */
		String[] reference_communeid = getReferenceCommuneid(id);
		try {
			viewer = new AuditConversationsViewer(new URL(
					reference_communeid[0]), reference_communeid[1],
					dataSourcePeopleDB, startingPoint, pageSize);
		} catch (MalformedURLException e) {
			logger.error("Il formato dell'url del web-service non e' valido:" + e.getMessage());
		}
	}
	
	
	/**
	 * @param 
	 * @return
	 * @throws  
	 */
	private ArrayList<AuditConversationsBean> getAuditConversationsBeans(String idservice, String idcommune, AuditConversationFilter filter)  {
		
		ArrayList<AuditConversationsBean> auditConversationsBeans = new ArrayList<AuditConversationsBean>(); 
		
		try {	
			//in base ai valori impostati nel filtro viene chiamato il servizio che restituisce le conversazioni 
			auditConversationsBeans = callService(filter);

			// numero risultati
			if (!auditConversationsBeans.isEmpty()) {
				filter.setRes_count(auditConversationsBeans.remove(0).getId());
			}
			
			if(filter.getRes_count()>0 && auditConversationsBeans.isEmpty()){
				//riparto da zero
				viewer.setStartingPoint(0);
				auditConversationsBeans = callService(filter);
				// numero risultati 
				filter.setRes_count(auditConversationsBeans.remove(0).getId());
			}
			// numero di pagina attuale/totale
			filter.setPages(viewer.getStartingPoint(), viewer.getPageSize());
			
			for (AuditConversationsBean bean : auditConversationsBeans) {
				String link = createAuditUsersLink(idcommune, bean.getId(), idservice);
				bean.setAudit_users_link(link);
				bean.setAudit_timestamp_date(bean.getAudit_timestamp().getTime());
			}
			
		} catch (AuditConversationsViewerException e) {
			logger.error("impossibile determinare il log.");
		}
		
		return auditConversationsBeans;
	}
	
	private String createAuditUsersLink(String idcommune, int idService, String id) {
		String link = "viewAuditConversation.do?action=viewAuditConversation&amp;id=" + Integer.toString(idService) + "&amp;communeid=" + idcommune + "&amp;serviceid=" + id;
		return link;
	}
	

	/**
	 * @param idComune
	 * @return la lista degli utenti presenti nelle audit conversations
	 */
	private List<PairElement<String, String>> getAuditUsersList() {
		
		List<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
		ArrayList<String> users = new ArrayList<String>();
		try {
			users = viewer.getAuditUsersForComune();
		} catch (AuditConversationsViewerException e) {
			logger.error("Non è stato possibile recuperare la lista degli utenti. " + e.getMessage());
		}
		
		// Tutti gli utenti
		PairElement<String, String> elem = new PairElement<String, String>("0", "Tutti gli utenti");
		result.add(elem);

		for (String user : users) {
			String userName = user;
			if (user.equalsIgnoreCase(ANONYMOUS_USER_TITLE)) {
				userName = ANONYMOUS_USER ;
			}
			result.add(new PairElement<String, String>(userName, user));
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
	 * @param idService
	 * @return communeId 
	 */
	private String getCommuneIdFromService(String idService) {
		
		String query = "SELECT nodeid FROM service WHERE id =  " + idService;
		String communeId = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			if (resultSet.next()) {
				communeId = resultSet.getString(1);
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
		
		return communeId;
	}
	
	
	/**
	 * @param idcommune
	 * @param codicecomune
	 * @param filter
	 * @param allRows
	 * @return
	 */
	private JRDataSource getJRDataSource(String idcommune, 
			String codicecomune, AuditConversationFilter filter, boolean allRows) {
		
		List<AuditConversationsBean> auditConversationsBeans = getJRAuditConversationsBeansAllRows(idcommune, codicecomune, filter);

		// conversione data per la stampa
		for (AuditConversationsBean bean : auditConversationsBeans) {
			bean.setAudit_timestamp_date(bean.getAudit_timestamp().getTime());
		}
		
		return new JRBeanCollectionDataSource(auditConversationsBeans);

	}
	
	
	/**
	 * @param id
	 *            Nodo di cui recuperare il Log delle Audit Conversations
	 * @return
	 * @throws
	 */
	private ArrayList<AuditConversationsBean> getJRAuditConversationsBeansAllRows(String idcommune, 
			String codicecomune, AuditConversationFilter filter) {

		ArrayList<AuditConversationsBean> auditConversationsBeans = new ArrayList<AuditConversationsBean>();

		try {
			//salvo i valori del viewer
			int oldPagesize = viewer.getPageSize();
			int oldStartingPoint = viewer.getStartingPoint();
			viewer.setPageSize(0);
			viewer.setStartingPoint(0);
			
			//in base ai valori impostati nel filtro viene chiamato il servizio che restituisce le conversazioni 
			auditConversationsBeans = callService(filter);

			// il numero risultati deve essere estratto 
			auditConversationsBeans.remove(0);
			
			// ripristino i valori precedenti
			viewer.setPageSize(oldPagesize);
			viewer.setStartingPoint(oldStartingPoint);

		} catch (AuditConversationsViewerException e) {
			logger.error("impossibile determinare il log.");

		}

		return auditConversationsBeans;

	}
	
	
	private ArrayList<AuditConversationsBean> callService(
			AuditConversationFilter filter)
			throws AuditConversationsViewerException {

		ArrayList<AuditConversationsBean> auditConversationsBeans;
		
		auditConversationsBeans = viewer.getAuditConversations(filter);

		return auditConversationsBeans;
	}
	

}

