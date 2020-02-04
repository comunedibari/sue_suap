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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.people.console.beans.support.AccreditamentiManagerBean;
import it.people.console.beans.support.PageNavigationSettings;
import it.people.console.domain.AccreditamentiManagementFilter;
import it.people.console.domain.PairElement;
import it.people.console.utils.Base64;
import it.people.console.utils.Constants;
import it.people.console.web.controllers.utils.AccreditamentiManagementViewer;
import it.people.console.web.controllers.utils.AccreditamentiManagementViewer.AccreditamentiManagementViewerException;
import it.people.console.web.servlet.mvc.MessageSourceAwareController;
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.ProfiloAccreditamento;
import it.people.sirac.accr.beans.Qualifica;

/**
 * @author Luca Barbieri - Pradac Informatica S.r.l.
 * @created 28/giu/2011 15.04.45
 * 
 */
@Controller
@RequestMapping("/Accreditamenti")
@SessionAttributes({ Constants.ControllerUtils.DETAILS_STATUSES_KEY })
public class AccreditamentiManagementController extends
		MessageSourceAwareController {

	@Autowired
	private DataSource dataSourcePeopleDB;
	
	//private HashMap<Integer, CheckboxElement> checkValues = new HashMap<Integer, CheckboxElement>();
	
	private Map<String, String> detailsStatuses = new HashMap<String, String>();
	
	private AccreditamentiManagementViewer viewer = null;


	public AccreditamentiManagementController() {
		this.setCommandObjectName("accreditamentiManagementFilter");
	}
	
	@RequestMapping(value = "/accreditamentiManagement.do", method = RequestMethod.GET)
	public String setupForm(ModelMap model, HttpServletRequest request) {

		model.put("includeTopbarLinks", true);

		model.put("sidebar", "/WEB-INF/jsp/accreditamenti/sidebar.jsp");
		model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);

		// gestione del filtro numero righe nel cambio pagina
		AccreditamentiManagementFilter filter = null;
		if (request.getParameter("detail") != null) {
			// se ritorno dal dettaglio recupero il filtro precedente
			filter = (AccreditamentiManagementFilter) 
				request.getSession().getAttribute("accrManagementFilter");
		}
		
		//crea l' AccreditamentiManagementViewer
		// valori di default, si potrebbero recuperare da una configurazioni (db o properties)
    	int startingPoint = 0; 
    	int pageSize = 10; 
		
		// se filtro già presente recupero le impostazioni
    	if(filter!=null){
			pageSize = filter.getPageSize();
    		startingPoint = (filter.getActualPage()-1) * pageSize;
    	}
    	createAccreditamentiManagementViewer(request, startingPoint, pageSize); 
	   			
		//numero di risultati per pagina	
		PageNavigationSettings pageNavigationSettings = new PageNavigationSettings(); 
		model.addAttribute("resultForPageList", pageNavigationSettings.getResultsForPage());
		
		//popola combo
    	model.addAttribute("qualifiche", getQualifiche()); 						//qualifiche
    	model.addAttribute("comuni", getComuni()); 								//comuni
    	model.addAttribute("tipiQualifica", getTipiQualifica()); 				//tipi qualifica
    	model.addAttribute("statoAccreditamento", getStatoAccreditamento()); 	//stato accreditamento

    	// gestione del filtro numero righe nel cambio pagina
    	AccreditamentiManagementFilter defaultFilter; 
    	if(filter!=null){
    		defaultFilter = filter;
    	} else {
    		defaultFilter = getDefaultAccreditamentiManagementFilter(pageSize);
    	}
    	
		model.addAttribute("accreditamentiManagementFilter", defaultFilter);
    	
    	List<AccreditamentiManagerBean> accreditamenti = getAccreditamentiManagementBeans(defaultFilter);
    	model.addAttribute("accreditamentiManagementBeans", accreditamenti);
    	

    	//salvo il filtro per averlo a disposizione dopo aver visionato un dettaglio
    	request.getSession().setAttribute("accrManagementFilter", defaultFilter);
		
    	this.setPageInfo(model, "accreditamentiManagement.title", null, "accreditamentiM");
    	
		return getStaticProperty("accreditamenti.accreditamentiManagement.view");
	}

	
	@RequestMapping(value = "/accreditamentiManagement.do", method = RequestMethod.POST)
	public String processSubmit(
			ModelMap model,
			@ModelAttribute("accreditamentiManagementFilter") AccreditamentiManagementFilter filter,      		
    		BindingResult result,
			HttpServletRequest request, 
			HttpServletResponse response) {

		boolean isUpdate = isPrefixParamInRequest(request, "update"); //salva
    	boolean isView = isPrefixParamInRequest(request, "view"); //visualizza e prima pagina
    	boolean isPrev = isPrefixParamInRequest(request, "prev"); //pagina precedente
    	boolean isNext = isPrefixParamInRequest(request, "next"); //pagina successiva
    	boolean isLast = isPrefixParamInRequest(request, "last"); //ultima pagina
    	boolean isDownloadAutocertificazione = isPrefixParamInRequest(request, "downloadAutocertificazione"); //download file autocertificazione

    	//Imposta paginazione
		if(filter.getPageNavigationSettings().getResultsForPage() == null){
			filter.getPageNavigationSettings().setResultsForPage(Integer.toString(filter.getPageSize()));
		} else {
			filter.setPageSize(Integer.valueOf(filter.getPageNavigationSettings().getResultsForPage()));
		}
		viewer.setPageSize(filter.getPageSize());
    	
		//Gestisci casi navigazione
		if (isLast) {
			int lastPage = filter.getTotalPages();
			viewer.goToLastPage(lastPage);
			//isView = true;
		}
    	if (isNext){ 
    		int res_count = filter.getRes_count();
			viewer.increaseStartingPoint(res_count); 
			//isView = true;
		}
    	if (isPrev){ 
    		viewer.decreaseStartingPoint(); 
    		//isView = true;
    	}
    	if (isDownloadAutocertificazione) {
    		//individuo l'accreditamento di cui recuperare l'autocertificazione
    		String idAccreditamento = getIdAccreditamentoToDownloadCertificate(request);
    		
    		try{
    			String[] autocertificazione =  viewer.getAutocertificazione(idAccreditamento);
    			//nome del file
				String fileName = autocertificazione[0];
				//contenuto del file
				String fileContent = autocertificazione[1];

				Base64 base64 = new Base64();
				
				byte[] output = null;
				
				try {
					//il file dovrebbe essere in Base64 
					output = base64.decode(fileContent);
				} catch (ArrayIndexOutOfBoundsException e) {
					//se il certificato non è stato salvato in Base64 provo a leggerlo in formato testuale
					output = fileContent.getBytes();
				}

				InputStream inStream = new ByteArrayInputStream(output);

				response.setHeader("Pragma", "private");
				response.setHeader("Cache-Control", "private, must-revalidate");
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + fileName + "\"");
				
				ServletOutputStream out = response.getOutputStream();

				while (inStream.read(output) != -1) {
					out.write(output);
				}
				inStream.close();
				out.flush();
				out.close();

    		} catch (AccreditamentiManagementViewerException e) {
    			logger.error("Non è stato possibile recuperare l'autocertificazione per l'accreditamento selezionato. " + e.getMessage());
    		} catch (Exception e) {
				e.printStackTrace();
			}
    		//isView = true;
    	}
    	
		if (isUpdate) {
			
			model.put("includeTopbarLinks", true);

			model.put("sidebar", "/WEB-INF/jsp/accreditamenti/sidebar.jsp");
			model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
	    	
	    	model.addAttribute("qualifiche", getQualifiche()); 						//qualifiche
	    	model.addAttribute("comuni", getComuni()); 								//comuni
	    	model.addAttribute("tipiQualifica", getTipiQualifica()); 				//tipi qualifica
	    	model.addAttribute("statoAccreditamento", getStatoAccreditamento()); 	//stato accreditamento
	    	
			if (!result.hasErrors()) {
				if (isUpdate) {

					// recupero l'idAccreditamento e lo stato da impostare
					String[] idAccreditamento_status = getIdAccreditamento_Status(request);
					// aggiorno lo stato dell'accreditamento
					updateAccreditamento(idAccreditamento_status[0], idAccreditamento_status[1]);
				}
				
				List<AccreditamentiManagerBean> accreditamenti = getAccreditamentiManagementBeans(filter);
				model.addAttribute("accreditamentiManagementBeans", accreditamenti);

			}
			model.addAttribute("accreditamentiManagementFilter", filter);
			PageNavigationSettings pageNavigationSettings = new PageNavigationSettings(); 
			model.addAttribute("resultForPageList", pageNavigationSettings.getResultsForPage());

			// salvo il filtro per averlo a disposizione dopo aver visionato un dettaglio
			request.getSession().setAttribute("accrManagementFilter", filter);

	    	this.setPageInfo(model, "accreditamentiManagement.title", null, "accreditamentiM");

			return getStaticProperty("accreditamenti.accreditamentiManagement.view");

		} 
		
		else {
			
			if (!isNext && !isPrev && !isLast && !isUpdate && isView) {
				viewer.setStartingPoint(0);
			}
			
			PageNavigationSettings pageNavigationSettings = new PageNavigationSettings(); 
			model.addAttribute("resultForPageList", pageNavigationSettings.getResultsForPage());
			
			model.put("includeTopbarLinks", true);

			model.put("sidebar", "/WEB-INF/jsp/accreditamenti/sidebar.jsp");
			model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
			
			// salvo il filtro per averlo a disposizione dopo aver visionato un dettaglio
			request.getSession().setAttribute("accrManagementFilter", filter);
			
			List<AccreditamentiManagerBean> accreditamenti = getAccreditamentiManagementBeans(filter);
			model.addAttribute("accreditamentiManagementBeans", accreditamenti);
			
			model.addAttribute("qualifiche", getQualifiche()); 						//qualifiche
	    	model.addAttribute("comuni", getComuni()); 								//comuni
	    	model.addAttribute("tipiQualifica", getTipiQualifica()); 				//tipi qualifica
	    	model.addAttribute("statoAccreditamento", getStatoAccreditamento()); 	//stato accreditamento
	    	
			return getStaticProperty("accreditamenti.accreditamentiManagement.view");
		}
    	

	}
	
	/**
	 * Recupero l'identificativo dell'accreditamento per il quale recuperare l'autocertificazione
	 */
	@SuppressWarnings("unchecked")
	private String getIdAccreditamentoToDownloadCertificate(HttpServletRequest request) {
		String idAccreditamento = "";
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			if (paramName.startsWith("downloadAutocertificazione_")) {
				idAccreditamento = paramName.split("\\_")[1].split("\\.")[0];
				break;
			}
		}
		return idAccreditamento;
	}
	
	/**
	 * Recupero l'identificativo dell'accreditamento per il quale cambiare stato
	 */
	@SuppressWarnings("unchecked")
	private String[] getIdAccreditamento_Status(HttpServletRequest request) {
		String idAccreditamento = "";
		String status = "";
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			if (paramName.startsWith("update_")) {
				idAccreditamento = paramName.split("\\_")[2];
				status = paramName.split("\\_")[1];
				break;
			}
		}
		return new String[]{idAccreditamento,status};
	}
	
	/**
	 * Salva i valori degli attributi 'attivo' e 'deleted' per gli accreditamenti visionati 
	 */
//	private boolean updateAccreditamenti() {
//		boolean success = true;
//		
//		List<String> attivi = new ArrayList<String>();
//		List<String> non_attivi = new ArrayList<String>();
//		List<String> deleted = new ArrayList<String>();
//		List<String> non_deleted = new ArrayList<String>();
//		
//		Set<Integer> chiavi = checkValues.keySet();
//		for (Integer i : chiavi) {
//			CheckboxElement coppia = checkValues.get(i);
//			//attivo
//			if(coppia.getAttivo()){ 
//				attivi.add(Integer.toString(i));
//			} else {
//				non_attivi.add(Integer.toString(i));
//			}
//			//deleted
//			if(coppia.getDeleted()){ 
//				deleted.add(Integer.toString(i));
//			} else {
//				non_deleted.add(Integer.toString(i));
//			}
//		
//		}
//		
//		String[] accreditamenti_attivo = attivi.toArray(new String[attivi.size()]);
//		String[] accreditamenti_non_attivo = non_attivi.toArray(new String[non_attivi.size()]);
//		String[] accreditamenti_deleted = deleted.toArray(new String[deleted.size()]);
//		String[] accreditamenti_non_deleted = non_deleted.toArray(new String[non_deleted.size()]);
//		
//		try{
//			viewer.setAccreditamenti_Attivo(accreditamenti_attivo, accreditamenti_non_attivo);
//		} catch (AccreditamentiManagementViewerException e) {
//			success = false;
//			logger.error("Non è stato possibile impostare il valore dell'attributo 'attivo' per gli accreditamenti selezionati. " + e.getMessage());
//		}
//		try{ 
//			viewer.setAccreditamenti_Deleted(accreditamenti_deleted, accreditamenti_non_deleted);
//		} catch (AccreditamentiManagementViewerException e) {
//			success = false;
//			logger.error("Non è stato possibile impostare il valore dell'attributo 'deleted' per gli accreditamenti selezionati. " + e.getMessage());
//		}
//		return success;
//		
//	}
	
	/**
	 * Imposta il valore dell'attributo 'attivo' per l'accreditamento selezionato 
	 */
	private void updateAccreditamento(String idAccreditamento, String status) {
		
		try{
			viewer.setStatoAccreditamento(idAccreditamento, status);
		} catch (AccreditamentiManagementViewerException e) {
			logger.error("Non è stato possibile impostare il valore dell'attributo 'attivo' per l'accreditamento selezionato." + e.getMessage());
		}
		
	}
	
	/**
	 * @param startingPoint
	 * @param pageSize
	 * @return l' AccreditamentiManagementViewer
	 */
	private void createAccreditamentiManagementViewer(HttpServletRequest request, int startingPoint, int pageSize) {

		viewer = new AccreditamentiManagementViewer(dataSourcePeopleDB, request, startingPoint, pageSize);
	}
	
	
	/**
	 * @return la lista delle qualifiche presenti
	 */
	private List<PairElement<String, String>> getQualifiche() {
		
		List<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
		Qualifica[] qualifiche = null;
		try {
			qualifiche = viewer.getQualifiche();

			// Tutte le qualifiche
			PairElement<String, String> elem = new PairElement<String, String>("0", "Tutti");
			result.add(elem);

			for (Qualifica qualifica : qualifiche) {
				if(!qualifica.getIdQualifica().equalsIgnoreCase("OAC")){
					//non mostro la qualifica 'Operatore Associazione di Categoria'
					result.add(new PairElement<String, String>(
							qualifica.getIdQualifica(), qualifica.getDescrizione()));
				}
			}

		} catch (AccreditamentiManagementViewerException e) {
			logger.error("Non è stato possibile recuperare la lista delle qualifiche. " + e.getMessage());
		}

		
		return result;
	}
	
	
	
	/**
	 * @return la lista degli enti presenti
	 */
	private List<PairElement<String, String>> getComuni() {
		
		List<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
		String[] comuni = null;
		try {
			comuni = viewer.getComuni();
			
			if(comuni!=null) {
				// Tutti gli enti
				PairElement<String, String> elem = new PairElement<String, String>("0", "Tutti");
				result.add(elem);
				
				for (String comune : comuni) {
					result.add(new PairElement<String, String>(comune, getNomeEnte(comune)));
				}
			}
			
		} catch (AccreditamentiManagementViewerException e) {
			logger.error("Non è stato possibile recuperare la lista degli enti. " + e.getMessage());
		}
		
		return result;
	}
	
	
	/**
	 * @param id
	 * @return reference e codicecomune, parametri per la connessione
	 */
	private String getNomeEnte(String id_comune) {

		String nomeEnte = "";

        String query = "SELECT comune FROM fenode WHERE codicecomune = " + id_comune;
        
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				nomeEnte = resultSet.getString("comune");
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
		
		return nomeEnte;
	}
	
	
	/**
	 * @return la lista dei tipi qualifica (Tipologia Intermediario) presenti
	 */
	private List<PairElement<String, String>> getTipiQualifica() {
		
		List<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
		String[] tipiQualifica = null;
		try {
			tipiQualifica = viewer.getTipiQualifica();
			
			// Tutti i tipi qualifica
			PairElement<String, String> elem = new PairElement<String, String>("0", "Tutti");
			result.add(elem);
			
			for (String tipoQualifica : tipiQualifica) {
				result.add(new PairElement<String, String>(tipoQualifica, tipoQualifica));
			}
			
		} catch (AccreditamentiManagementViewerException e) {
			logger.error("Non è stato possibile recuperare la lista dei tipi qualifica. " + e.getMessage());
		}
		
		return result;
	}
	
	
	/**
	 * @return la lista degli stati accreditamento
	 */
	private List<PairElement<String, String>> getStatoAccreditamento() {
		
		List<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
		PairElement<String, String> elem;

		// Tutti i tipi qualifica
		elem = new PairElement<String, String>("", "Qualsiasi");
		result.add(elem);
		elem = new PairElement<String, String>("1", "Attivo");
		result.add(elem);
		elem = new PairElement<String, String>("0", "Disattivato");
		result.add(elem);
		elem = new PairElement<String, String>("2", "Eliminato");
		result.add(elem);
			
		return result;
	}
	
	
    /**
	 * @return il Filtro impostato di default
	 */
	private AccreditamentiManagementFilter getDefaultAccreditamentiManagementFilter(int pageSize) {
		
		AccreditamentiManagementFilter filter = new AccreditamentiManagementFilter();
		
		filter.setTipoQualifica("0");
		filter.setNomeEnte("0");
		filter.setTipologiaIntermediario("0"); 
		filter.setPageSize(pageSize);
		filter.getPageNavigationSettings().setResultsForPage(Integer.toString(pageSize));
		
		return filter;
	}
	
	
	/**
	 * @param filtro
	 * @return
	 * @throws  
	 */
	private List<AccreditamentiManagerBean> getAccreditamentiManagementBeans(AccreditamentiManagementFilter filter)  {
//		List<Accreditamento> accreditamenti = new ArrayList<Accreditamento>(); 
		
		List<AccreditamentiManagerBean> accreditamenti = new ArrayList<AccreditamentiManagerBean>();
		
		try {
			Accreditamento[] accr = viewer.getAccreditamenti(filter);
			if(accr!=null) {
				for (Accreditamento accreditamento : accr) {
					AccreditamentiManagerBean bean = new AccreditamentiManagerBean();
					
					bean.setId(accreditamento.getId());
					bean.setTaxcodeUtente(accreditamento.getCodiceFiscale().toUpperCase());
					bean.setNomeEnte(getNomeEnte(accreditamento.getIdComune()));
					
					ProfiloAccreditamento profilo = accreditamento.getProfilo();
					bean.setTaxcodeIntermediario(profilo.getCodiceFiscaleIntermediario().toUpperCase());
					bean.setVatnumberIntermediario(profilo.getPartitaIvaIntermediario());
					bean.setTipoQualifica(accreditamento.getQualifica().getDescrizione());
					bean.setE_addressIntermediario(profilo.getDomicilioElettronico());
					bean.setTipologiaIntermediario(accreditamento.getQualifica().getTipoQualifica());
					bean.setSedeLegale(profilo.getSedeLegale());
					bean.setAttivo(accreditamento.isAttivo());
					
					boolean deleted = viewer.isAccreditamentoDeleted(accreditamento.getId());
					bean.setDeleted(deleted);
					bean.setAuto_certificazione_filename(accreditamento.getProfilo().getAutoCertFilename());
					bean.setDetailPagelink(createDetailLink(accreditamento.getId()));
//					
//					//verifico se i checkbox sono già stati memorizzati e recupero i valori
//					CheckboxElement elem = checkValues.get(new Integer(accreditamento.getId()));
//					if(elem!=null){
//						bean.setAttivo(elem.getAttivo());
//						bean.setDeleted(elem.getDeleted());
//					}
//					//memorizzo i valori dei checkbox
//					checkValues.put(bean.getId(), new CheckboxElement(bean.isAttivo(), bean.isDeleted()));

					accreditamenti.add(bean);
				}
			
				// numero risultati
				String numAccreditamenti = viewer.getNumAccreditamenti(filter);
				filter.setRes_count(Integer.parseInt(numAccreditamenti)); 
				// numero di pagina attuale/totale
				filter.setPages(viewer.getStartingPoint(), viewer.getPageSize());
			}
			
		} catch (AccreditamentiManagementViewerException e) {
			logger.error("Non è stato possibile recuperare la lista dei tipi qualifica. " + e.getMessage());
		}
		return accreditamenti;
	}

	/**
	 * @param id
	 * @return
	 */
	private String createDetailLink(int id) {
		String link = "accreditamentiManagementDetails.do?action=accreditamentiManagement&amp;id=" + Integer.toString(id);
		return link;
	}
	
	
//	private void saveCheckValues(HttpServletRequest req) {
//		if (req != null) {
//			@SuppressWarnings("unchecked")
//			Enumeration<String> en = req.getParameterNames();
//			for (; en.hasMoreElements();) {
//				CheckboxElement coppia;
//				
//				String param = en.nextElement();
//				if(param.startsWith("attivo_")){
//					String id = param.split("\\_")[1];
//					coppia = checkValues.get(new Integer(id));
//					if(coppia!=null){
//						coppia.setAttivo(req.getParameter(param));
//					} else {
//						coppia = new CheckboxElement(req.getParameter(param), "");
//					}
//					checkValues.put(new Integer(id), coppia);
//				}
//				else if(param.startsWith("deleted_")){
//					String id = param.split("\\_")[1];
//					coppia = checkValues.get(new Integer(id));
//					if(coppia!=null){
//						coppia.setDeleted(req.getParameter(param));
//					} else {
//						coppia = new CheckboxElement("", req.getParameter(param));
//					}
//					checkValues.put(new Integer(id), coppia);
//				}
//			}
//			
//		}
//	}
	
	
//	private void printCheckValues(){
//		Set<Integer> chiavi = checkValues.keySet();
//		for (Integer i : chiavi) {
//			CheckboxElement coppia = checkValues.get(i);
//			System.out.println(i+" : " +
//					" attivo: "+ coppia.getAttivo() +
//					" deleted: "+ coppia.getDeleted());
//		}
//		System.out.println("");
//	}
	
	
//	
//	private class CheckboxElement implements Serializable {
//
//		private static final long serialVersionUID = -8785844993061925064L;
//
//		private Boolean attivo;
//		private Boolean deleted;
//
//		public CheckboxElement(Boolean attivo, Boolean deleted) {
//			this.setAttivo(attivo);
//			this.setDeleted(deleted);
//		}
//
//		public CheckboxElement(String attivo, String deleted) {
//			this.setAttivo(convertValue(attivo));
//			this.setDeleted(convertValue(deleted));
//		}
//
//		public Boolean getAttivo() {
//			return attivo;
//		}
//
//		public void setAttivo(Boolean attivo) {
//			this.attivo = attivo;
//		}
//		public void setAttivo(String attivo) {
//			this.attivo = convertValue(attivo);
//		}
//
//		public Boolean getDeleted() {
//			return deleted;
//		}
//
//		public void setDeleted(Boolean deleted) {
//			this.deleted = deleted;
//		}
//		public void setDeleted(String deleted) {
//			this.deleted = convertValue(deleted);
//		}
//
//		private Boolean convertValue(String value) {
//			if (value.equals("on") || value.equals("true")) {
//				return true; 
//			} else {
//				return false;
//			}
//		}
//		
//	}
	
	
}
