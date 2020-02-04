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
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

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
import org.springframework.web.bind.annotation.SessionAttributes;

import it.people.console.beans.support.JasperReportSettings;
import it.people.console.domain.AuditStatistica;
import it.people.console.domain.AuditStatistiche;
import it.people.console.domain.AuditStatisticheFilter;
import it.people.console.domain.AuditStatisticheReport;
import it.people.console.jasper.IJasperReportView;
import it.people.console.utils.CastUtils;
import it.people.console.utils.Constants;
import it.people.console.web.client.exceptions.FeServiceReferenceException;
import it.people.console.web.controllers.validator.AuditStatisticheValidator;
import it.people.console.web.servlet.mvc.MessageSourceAwareController;
import it.people.feservice.FEInterface;
import it.people.feservice.beans.AuditStatisticheBean;

/**
 * @author Luca Barbieri - Pradac Informatica S.r.l.
 * @created 13/lug/2011 12.04.45
 * 
 */
@Controller
@RequestMapping("/NodiFe")
@SessionAttributes({ Constants.ControllerUtils.DETAILS_STATUSES_KEY })
public class FENodesAuditStatisticheController extends
		MessageSourceAwareController {


	@Autowired
	private DataSource dataSourcePeopleDB;
	
	@Autowired
	private IJasperReportView jasperGenericReport;
	
	private Map<String, String> detailsStatuses = new HashMap<String, String>();
	
	private AuditStatisticheValidator validator;
	
	@Autowired
	public FENodesAuditStatisticheController(AuditStatisticheValidator validator) {
		this.validator = validator;
		this.setCommandObjectName("auditStatisticheFilter");
	}
	
	
	@RequestMapping(value = "/auditStatistiche.do", method = RequestMethod.GET)
	public String setupForm(ModelMap model, HttpServletRequest request) {

		model.put("includeTopbarLinks", true);

		model.put("sidebar", "/WEB-INF/jsp/fenodes/sidebar.jsp");
		model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);

		// Insieme iniziali nodi e prima lettera
		SortedSet<Character> letters = getInitialLetters();
		
		if(!letters.isEmpty()){
			char firstLetter = (Character) letters.first();

			// Mappa con lettere dell'alfabeto e informazione sulle presenti
			SortedMap<Character, Boolean> alphabet = getAlphabet(letters);
			
			model.addAttribute("alphabet", alphabet);
			
			AuditStatisticheFilter filter = new AuditStatisticheFilter(firstLetter);
			model.addAttribute("auditStatisticheFilter", filter );
			
			List<AuditStatistiche> statistiche = getStatistiche(filter);
			model.addAttribute("statistichePerNodo", statistiche);
			
			// salvo il filtro per averlo a disposizione dopo aver visionato un report
			request.getSession().setAttribute("auditStatisticheFilter", filter);
		}

    	JasperReportSettings jasperReportSettings = new JasperReportSettings(); 	
    	model.addAttribute("reportTypes", jasperReportSettings.getReportTypes());	
		
		this.setPageInfo(model, "auditStatistiche.title", null, "feNodesAS");
		
		return getStaticProperty("fenodes.auditStatistiche.view");

	}

	
	@RequestMapping(value = "/auditStatistiche.do", method = RequestMethod.POST)
	public String processSubmit(
			ModelMap model,
			@ModelAttribute("auditStatisticheFilter") AuditStatisticheFilter filter,
			BindingResult result, HttpServletRequest request, 
			HttpServletResponse response) {

		boolean isBack = isPrefixParamInRequest(request, "back"); //torna alla pagina da report
		boolean isGenerateReport = this.isParamInRequest(request, "generateReport");
		model.addAttribute("reportGenerationErrorMessage", null);
		model.addAttribute("reportGenerationErrorException", null);
		
		boolean isView = isPrefixParamInRequest(request, "view");
		
		boolean isLetter = isPrefixParamInRequest(request, "selectedLetter");
		
		JasperReportSettings jasperReportSettings = new JasperReportSettings();
    	model.addAttribute("reportTypes", jasperReportSettings.getReportTypes());

    	if (isLetter){
    		String initialLetter = request.getParameter("selectedLetter");
    		filter.setInitialLetter(initialLetter);
    	}
    	
		if (isBack) {
			// se ritorno dalla generazione report recupero il filtro precedente
			filter = (AuditStatisticheFilter) request.getSession().getAttribute("auditStatisticheFilter");
			model.addAttribute("auditStatisticheFilter", filter);
			isView = true;
		}
		
    	if (isView||isLetter) {
			
			validator.validate(filter, result);
			
			model.put("includeTopbarLinks", true);
	    	model.put("sidebar", "/WEB-INF/jsp/fenodes/sidebar.jsp");

	    	// Insieme iniziali nodi 
			SortedSet<Character> letters = getInitialLetters();

			// Mappa con lettere dell'alfabeto e informazione sulle presenti
			SortedMap<Character, Boolean> alphabet = getAlphabet(letters);
			
			model.addAttribute("alphabet", alphabet);
	    	
//	    	if(!result.hasErrors()){
	    		List<AuditStatistiche> statistiche = getStatistiche(filter);
	    		model.addAttribute("statistichePerNodo", statistiche);
//	    	}
			model.addAttribute("auditStatisticheFilter", filter);
			// salvo il filtro per averlo a disposizione dopo aver visionato un report
			request.getSession().setAttribute("auditStatisticheFilter", filter);
			
			this.setPageInfo(model, "auditStatistiche.title", null, "feNodesAS");
			
			return getStaticProperty("fenodes.auditStatistiche.view");
		} else if (isGenerateReport) {
			String reportType = request.getParameter("reportSettings.reportType");
			
			if (reportType.equalsIgnoreCase(JasperReportSettings.ReportTypes.PDF_TYPE_KEY)) {
				//PDF
				try {
					jasperGenericReport.renderReport(reportType, 
							"auditStatistiche", 
							getJRDataSource(filter), null, "audit", request, response);
				} catch (Exception e) {
					model.addAttribute("reportTypes", jasperReportSettings.getReportTypes());
					model.put("includeTopbarLinks", true);
					model.put("sidebar", "/WEB-INF/jsp/fenodes/sidebar.jsp");
					
		    		List<AuditStatistiche> statistiche = getStatistiche(filter);
		    		model.addAttribute("statistichePerNodo", statistiche);
		    		model.addAttribute("auditStatisticheFilter", filter);
				
					model.addAttribute("reportGenerationErrorMessage", "Si è verificato un errore durante la generazione del report.");
					model.addAttribute("reportGenerationErrorException", CastUtils.exceptionToString(e));
					
					this.setPageInfo(model, "auditStatistiche.title", null, "feNodesAS");
					
		    		return getStaticProperty("fenodes.auditStatistiche.view");
				}
				
				model.put("includeTopbarLinks", true);
				model.put("sidebar", "/WEB-INF/jsp/fenodes/sidebar.jsp");
				model.addAttribute("pdf_complete_path", 
						String.valueOf(request.getSession().
								getAttribute(Constants.Reports.REPORT_FILE_PREVIEW_SESSION_KEY)));
				return getStaticProperty("reports.page.view");
			}

			else  { 
				
				//CSV - HTML - XLS
				try{
					String fileName = "statistiche";
					
					if (reportType.equalsIgnoreCase(JasperReportSettings.ReportTypes.CSV_TYPE_KEY) ||
						reportType.equalsIgnoreCase(JasperReportSettings.ReportTypes.HTML_TYPE_KEY) ||
						reportType.equalsIgnoreCase(JasperReportSettings.ReportTypes.XLS_TYPE_KEY) ){

						fileName+="."+reportType.toLowerCase();
					}
					
					byte[] output = jasperGenericReport.saveReport(reportType, 
							"auditStatistiche", 
							getJRDataSource(filter), null, "audit", request, response);
					
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
				model.put("sidebar", "/WEB-INF/jsp/fenodes/sidebar.jsp");
				
	    		List<AuditStatistiche> statistiche = getStatistiche(filter);
	    		model.addAttribute("statistichePerNodo", statistiche);
	    		model.addAttribute("auditStatisticheFilter", filter);
			
	    		this.setPageInfo(model, "auditStatistiche.title", null, "feNodesAS");
	    		
	    		return getStaticProperty("fenodes.auditStatistiche.view");
	    		
			} 

		} else {
			request.getSession().removeAttribute("auditStatisticheFilter");
			return "redirect:elenco.do";
		}
	}
	
	
	/**
	 *  Restituisce una mappa con le lettere dell'alfabeto e informazione sulla presenza
	 */
	private SortedMap<Character, Boolean> getAlphabet(SortedSet<Character> letters) {
		char letter;

		SortedMap<Character, Boolean> alphabet = new TreeMap<Character, Boolean>();

		for (letter = 'A'; letter <= 'Z'; letter++) {
			if (letters.contains(letter)) {
				alphabet.put(letter, true);
			} else {
				alphabet.put(letter, false);
			}
		}
		return alphabet;
	}

	/**
	 * @return
	 */
	private List<AuditStatistiche> getStatistiche(AuditStatisticheFilter filter) {
		
		// recupero i valori del filtro per impostare la selezione
		String filterQuery = getFilterQuery(filter);
		
		List<AuditStatistiche> statistiche = new ArrayList<AuditStatistiche>();
		
		//recupero codicecomune, comune e reference per ogni nodo
		List<String[]> references = getReferences(filter);
		String serviceUrl; 
		String query;
		
		for (String[] ref : references) {
			
			AuditStatistiche statPerNodo = new AuditStatistiche();
			statPerNodo.setCommuneid(ref[0]);
			statPerNodo.setNomeNodo(ref[1]);
			serviceUrl = ref[2];
			
			//List<AuditStatistica> statistichePerNodo = new ArrayList<AuditStatistica>();
			Map<String, AuditStatistica> map = new HashMap<String, AuditStatistica>();

			// 1. NumeroTotaleAccessiPerSingoloServizio
			AuditStatistica singola = new AuditStatistica();
			singola.setNomeStatistica("auditStatistiche.totaleAccessiPerServizio.title");
			query = prepareQueryNumeroTotaleAccessiPerSingoloServizio(ref[0], filterQuery);
			AuditStatisticheBean[] risultati = getStatistica(serviceUrl, query);
			if(risultati!=null){
				singola.setRisultati(risultati);
				//statistichePerNodo.add(singola);
				map.put("accessiPerServizio", singola);

			}
			
			// 2. ServiziTopTen
			singola = new AuditStatistica();
			singola.setNomeStatistica("auditStatistiche.serviziTopTen.title"); 
			query = prepareQueryServiziTopTen(ref[0], filterQuery); 
			risultati = getStatistica(serviceUrl, query);
			if(risultati!=null){
				singola.setRisultati(risultati);
				//statistichePerNodo.add(singola);
				map.put("serviziTopTen", singola);

			}
			
			// 3. AbbandonoServiziPerSingoloServizio
			String beReferences = getBeReferences(ref[3]);
			singola = new AuditStatistica();
			singola.setNomeStatistica("auditStatistiche.abbandonoServizi.title"); 
			query = prepareQueryAbbandonoServiziPerSingoloServizio(ref[0], filterQuery, beReferences ); 
			risultati = getStatistica(serviceUrl, query);
			if(risultati!=null){
				singola.setRisultati(risultati);
				//statistichePerNodo.add(singola);
				map.put("abbandonoServizi", singola);

			}
			
			
			//se un nodo non ha statistiche non lo mostro
//			if(!statistichePerNodo.isEmpty()){
			if(!map.isEmpty()){				
//				statPerNodo.setStatistiche(statistichePerNodo);
				statPerNodo.setStatistiche(map);

				statistiche.add(statPerNodo);
			}
			
		}
		
		return statistiche;
	}


	private String getFilterQuery(AuditStatisticheFilter filter) {
		
		Calendar from = filter.getFromDate(); 
		Calendar to = filter.getToDate();
		String fromDate = null;
		String toDate = null;
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (from != null) {
			from.set(Calendar.HOUR_OF_DAY, 0);
			from.set(Calendar.MINUTE,      0);
			from.set(Calendar.SECOND,      0);
			fromDate = df.format(from.getTime());
		}
		if (to != null) {
			to.set(Calendar.HOUR_OF_DAY, to.getMaximum(Calendar.HOUR_OF_DAY));
			to.set(Calendar.MINUTE,      to.getMaximum(Calendar.MINUTE));
			to.set(Calendar.SECOND,      to.getMaximum(Calendar.SECOND));
			toDate = df.format(to.getTime());
		}


		String filterQuery = "";
		
		if (from != null && to != null) {
			filterQuery += " AND (audit_timestamp BETWEEN '" + fromDate + "' AND '" + toDate + "' )";
		} else if (from != null && to == null) {
			filterQuery += " AND audit_timestamp >= '" + fromDate + "' ";
		} else if (from == null && to != null) {
			filterQuery += " AND audit_timestamp <= '" + toDate + "' ";
		}
		
		return filterQuery;
	}


	private String prepareQueryNumeroTotaleAccessiPerSingoloServizio(String idComune, String filterQuery){
		
		String query = 
			"SELECT communeid, NULL AS nomeEnte, package AS process_name, nome AS nomeServizio, " +
				"NULL AS action_name, audit_timestamp, IFNULL(accessi,0) AS risultati " +
			"FROM service AS s LEFT JOIN (" +
				"SELECT DISTINCT au.commune_key, ac.process_name, ac.audit_timestamp, COUNT(*) AS accessi " +
				"FROM audit_conversations AS ac " +
				"JOIN audit_users AS au ON ac.audit_users_ref = au.id " +
				"WHERE action_name LIKE ('caricamento servizio%') " +
				filterQuery +
				"GROUP BY commune_key, ac.process_name, ac.action_name) AS stat " +
				"ON s.package = stat.process_name AND s.communeid = stat.commune_key " +
			"WHERE communeid = '"+idComune+"' "+
				
			"ORDER BY communeid, nome";
		
		return query;
	}
	
	private String prepareQueryServiziTopTen(String idComune, String filterQuery){
		
		String query = 
			"SELECT communeid, NULL AS nomeEnte, package AS process_name, nome AS nomeServizio, " +
				"NULL AS action_name, NULL AS audit_timestamp, IFNULL(accessi,0) AS risultati " +
			"FROM service AS s LEFT JOIN (" +
				"SELECT DISTINCT au.commune_key, ac.process_name, COUNT(*) AS accessi " +
				"FROM audit_conversations AS ac " +
				"JOIN audit_users AS au ON ac.audit_users_ref = au.id " +
				"WHERE action_name LIKE ('caricamento servizio%') " +
				filterQuery +
				"GROUP BY commune_key, ac.process_name, ac.action_name) AS stat " +
				"ON s.package = stat.process_name AND s.communeid = stat.commune_key " +
			"WHERE communeid = '"+idComune+"' "+
			"ORDER BY risultati DESC, communeid, nome "+
			"LIMIT 0,10";
		
		return query;
	}
	
	private String prepareQueryAbbandonoServiziPerSingoloServizio(String idComune, String filterQuery, String beReferences){
		String query = 
			"SELECT communeid, NULL AS nomeEnte, package AS process_name, nome AS nomeServizio, " +
			"NULL AS action_name, audit_timestamp, " +
			"IFNULL(100-(IFNULL((inviati) ,0)*100)/accessi,0) AS risultati "+
			"FROM service AS s LEFT JOIN (" +
				"SELECT DISTINCT au.commune_key, ac.process_name, ac.audit_timestamp, COUNT(*) AS accessi, " +
					"(SELECT COUNT(*) " +
						"FROM audit_conversations ac2 JOIN audit_users AS au2 ON ac2.audit_users_ref = au2.id " +
						"WHERE ac2.process_name = ac.process_name AND au.commune_key = au2.commune_key " +
						filterQuery +
						"AND action_name LIKE 'invio pratica' ) AS inviati " +
				"FROM audit_conversations AS ac " +
				"JOIN audit_users AS au ON ac.audit_users_ref = au.id " +
				"WHERE action_name LIKE ('caricamento servizio%') " +
				filterQuery +
				"GROUP BY commune_key, ac.process_name, ac.action_name) AS stat " +
			"ON s.package = stat.process_name AND s.communeid = stat.commune_key "+
			"WHERE communeid = '"+idComune+"' "+
			" AND s.id IN ( SELECT serviceid FROM reference WHERE NAME = 'SUBMIT_PROCESS' AND VALUE IN ('"+ beReferences +"') ) "+
			"ORDER BY risultati DESC, communeid, nome ";
		
		return query;
	}

	/**
	 * @return elenco di reference nodobe per il nodo nodeid
	 */
	private String getBeReferences(String nodeid) {

		String beReferences = "";
        String query = "SELECT nodobe FROM benode WHERE nodeid = " + nodeid;
        
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				String reference = resultSet.getString("nodobe");
				beReferences += reference + "','";
			}
			if (!beReferences.equals("")){
				beReferences = beReferences.substring(0, beReferences.length()-3);
			}
			resultSet.close();
			
		} catch (SQLException e) {
			logger.error("Errore nel recupero delle reference ai nodobe dalla tabella benode."+e);
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
		
		return beReferences;
	}
	
	/**
	 * @return codicecomune, comune, reference e id per ogni nodo
	 */
	private List<String[]> getReferences(AuditStatisticheFilter filter) {
		List<String[]> references = new ArrayList<String[]>();

        String query = "SELECT codicecomune, comune, reference, id FROM fenode " +
        		"WHERE comune LIKE '"+ filter.getInitialLetter()+ "%' ORDER BY comune";
        
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				String[] reference = new String[4];
				reference[0] = resultSet.getString("codicecomune");
				reference[1] = resultSet.getString("comune");
				reference[2] = resultSet.getString("reference");
				reference[3] = resultSet.getString("id");
				references.add(reference);
			}
			resultSet.close();
			
		} catch (SQLException e) {
			logger.error("Errore nel recupero delle reference dalla tabella fenode."+e);
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
	 * @return codicecomune, comune e reference per ogni nodo
	 */
	private SortedSet<Character> getInitialLetters() {
		SortedSet<Character> letters = new TreeSet<Character>();
		
		String query = "SELECT DISTINCT comune FROM fenode, service WHERE service.nodeid = fenode.id ORDER BY comune";
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			while (resultSet.next()) {
				String comune = resultSet.getString("comune");
				letters.add(new Character(comune.substring(0, 1).toCharArray()[0]));
			}
			resultSet.close();
			
		} catch (SQLException e) {
			logger.error("Errore nel recupero delle reference dalla tabella fenode."+e);
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
		
		return letters;
	}


	/**
	 *  @param
	 *  Invoca il servizio a cui richiedere l'esecuzione della query per una statistica
	 **/ 
	private AuditStatisticheBean[] getStatistica(String serviceUrl, String query) {
		AuditStatisticheBean[] risultato = null;
		try {
			FEInterface feInterface = this.getFEInterface(serviceUrl);
			risultato = feInterface.getStatistiche(query);

		} catch (FeServiceReferenceException e) {
			logger.error("Errore nel recupero del servizio all'indirizzo"+serviceUrl , e);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return risultato;
	}
	
	/**
	 * @return
	 */
	private List<AuditStatisticheReport> getStatisticheForReport(AuditStatisticheFilter filter) {
		
		// recupero i valori del filtro per impostare la selezione
		String filterQuery = getFilterQuery(filter);
		
		List<AuditStatisticheReport> statistiche = new ArrayList<AuditStatisticheReport>();
		
		//recupero codicecomune, comune e reference per ogni nodo
		List<String[]> references = getReferences(filter);
		String serviceUrl; 
		
		for (String[] ref : references) {
			
			AuditStatistiche statPerNodo = new AuditStatistiche();
			statPerNodo.setCommuneid(ref[0]);
			statPerNodo.setNomeNodo(ref[1]);
			serviceUrl = ref[2];
			
			statistiche = reportAccessiPerServizio(filterQuery, statistiche, serviceUrl, ref);
			statistiche = reportServiziTopTen(filterQuery, statistiche, serviceUrl, ref);
			statistiche = reportAbbandonoServizi(filterQuery, statistiche, serviceUrl, ref);
						
		}
		
		return statistiche;
	}


	private List<AuditStatisticheReport> reportAbbandonoServizi(String filterQuery,
			List<AuditStatisticheReport> statistiche, String serviceUrl,
			String[] ref) {
		String query;
		AuditStatistica singola;
		AuditStatisticheBean[] risultati;
		// 3. AbbandonoServiziPerSingoloServizio
		String beReferences = getBeReferences(ref[3]);
		singola = new AuditStatistica();
		singola.setNomeStatistica("auditStatistiche.abbandonoServizi.title"); 
		query = prepareQueryAbbandonoServiziPerSingoloServizio(ref[0], filterQuery, beReferences); 
		risultati = getStatistica(serviceUrl, query);
		if(risultati!=null){
			singola.setRisultati(risultati);
			//statistichePerNodo.add(singola);
			for (AuditStatisticheBean bean : risultati) {
				AuditStatisticheReport row = new AuditStatisticheReport();
				row.setCommuneid(ref[0]);
				row.setNomeNodo(ref[1]);
				row.setOrdine(3);
				row.setNomeServizio(bean.getNomeServizio());
				row.setNomeStatistica("Percentuale di abbandono servizi per singolo servizio");
				row.setRisultati(Integer.toString(bean.getRisultati()));
				statistiche.add(row);
			}
			//map.put("abbandonoServizi", singola);

		}
		
		return statistiche;
	}


	private List<AuditStatisticheReport> reportServiziTopTen(String filterQuery,
			List<AuditStatisticheReport> statistiche, String serviceUrl,
			String[] ref) {
		String query;
		AuditStatistica singola;
		AuditStatisticheBean[] risultati;
		// 2. ServiziTopTen
		singola = new AuditStatistica();
		singola.setNomeStatistica("auditStatistiche.serviziTopTen.title"); 
		query = prepareQueryServiziTopTen(ref[0], filterQuery); 
		risultati = getStatistica(serviceUrl, query);
		if(risultati!=null){
			singola.setRisultati(risultati);
			//statistichePerNodo.add(singola);
			for (AuditStatisticheBean bean : risultati) {
				AuditStatisticheReport row = new AuditStatisticheReport();
				row.setCommuneid(ref[0]);
				row.setNomeNodo(ref[1]);
				row.setOrdine(2);
				row.setNomeServizio(bean.getNomeServizio());
				row.setNomeStatistica("Top ten servizi");
				row.setRisultati(Integer.toString(bean.getRisultati()));
				statistiche.add(row);
			}
			//map.put("serviziTopTen", singola);

		}
		
		return statistiche;
	}


	private List<AuditStatisticheReport> reportAccessiPerServizio(String filterQuery,
			List<AuditStatisticheReport> statistiche, String serviceUrl,
			String[] ref) {
		String query;
		// 1. NumeroTotaleAccessiPerSingoloServizio
		AuditStatistica singola = new AuditStatistica();
		singola.setNomeStatistica("auditStatistiche.totaleAccessiPerServizio.title");
		query = prepareQueryNumeroTotaleAccessiPerSingoloServizio(ref[0], filterQuery);
		AuditStatisticheBean[] risultati = getStatistica(serviceUrl, query);
		if(risultati!=null){
			singola.setRisultati(risultati);
			//statistichePerNodo.add(singola);
			for (AuditStatisticheBean bean : risultati) {
				AuditStatisticheReport row = new AuditStatisticheReport();
				row.setCommuneid(ref[0]);
				row.setNomeNodo(ref[1]);
				row.setOrdine(1);
				row.setNomeServizio(bean.getNomeServizio());
				row.setNomeStatistica("Numero di accessi ai servizi per singolo servizio");
				row.setRisultati(Integer.toString(bean.getRisultati()));
				statistiche.add(row);
			}
			//map.put("accessiPerServizio", singola);

		}
		
		return statistiche;
	}
	
	private JRDataSource getJRDataSource(AuditStatisticheFilter filter) {
		
		List<AuditStatisticheReport> statisticheReport = getStatisticheForReport(filter);
		return new JRBeanCollectionDataSource(statisticheReport);
		
	}
	
}
