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
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.people.console.beans.support.AccreditamentiManagerBean;
import it.people.console.beans.support.PageNavigationSettings;
import it.people.console.domain.AccreditamentiManagementFilter;
import it.people.console.utils.Base64;
import it.people.console.utils.Constants;
import it.people.console.web.controllers.utils.AccreditamentiManagementViewer;
import it.people.console.web.controllers.utils.AccreditamentiManagementViewer.AccreditamentiManagementViewerException;
import it.people.console.web.servlet.mvc.MessageSourceAwareController;
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.ProfiloAccreditamento;
import it.people.sirac.accr.beans.Qualifica;
import it.people.sirac.accr.beans.RappresentanteLegale;

/**
 * @author Luca Barbieri - Pradac Informatica S.r.l.
 * @created 04/lug/2011 17.59.49
 *
 */

@Controller
@RequestMapping("/Accreditamenti")
@SessionAttributes({Constants.ControllerUtils.DETAILS_STATUSES_KEY})
public class AccreditamentiManagementDetailsController extends
		MessageSourceAwareController {

	private static final String ASSOCIAZIONE_DI_CATEGORIA = "RCT";
	private static final String OPERATORE_ASSOCIAZIONE_DI_CATEGORIA = "OAC";

	@Autowired
	private DataSource dataSourcePeopleDB;
	
//	private HashMap<Integer, CheckboxElement> checkValues = new HashMap<Integer, CheckboxElement>();

	private Map<String, String> detailsStatuses = new HashMap<String, String>();
	
	private AccreditamentiManagementViewer viewer = null;
	
	public AccreditamentiManagementDetailsController() {
		this.setCommandObjectName("bean");
	}
	
	@RequestMapping(value = "/accreditamentiManagementDetails.do", method = RequestMethod.GET)
	public String setupForm(@RequestParam String action, 
			@RequestParam String id, 
			ModelMap model,
			HttpServletRequest request) {

    	model.put("includeTopbarLinks", true);
    	model.put("sidebar", "/WEB-INF/jsp/accreditamenti/sidebar.jsp");

    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);	    	
    	
//    	checkValues.clear();
		
		// valori di default, si potrebbero recuperare da una configurazioni (db o properties)
    	int startingPoint = 0; 
    	int pageSize = 5;
		
    	createAccreditamentiManagementViewer(request, startingPoint, pageSize); 
    	
		//numero di risultati per pagina	
		PageNavigationSettings pageNavigationSettings = new PageNavigationSettings(); 
		model.addAttribute("resultForPageList", pageNavigationSettings.getResultsForPage());

		//Filtro operatori
     	AccreditamentiManagementFilter operatoriFilter = new AccreditamentiManagementFilter();
    	operatoriFilter.setPageSize(pageSize);
		operatoriFilter.getPageNavigationSettings().setResultsForPage(Integer.toString(pageSize));

		
    	AccreditamentiManagerBean bean = getAccreditamentiManagementBean(id); 
    	model.addAttribute("bean", bean);
    	model.addAttribute("operatori", getOperatoriAssociazione(bean, operatoriFilter));
    	model.addAttribute("operatoriFilter", operatoriFilter );
    	
    	this.setPageInfo(model, "accreditamentiManagementDetails.title", null, "accreditamentiMD");
    	
		return getStaticProperty("accreditamenti.accreditamentiManagementDetails.view");
    	
    }
	
	@RequestMapping(value = "/accreditamentiManagementDetails.do", method = RequestMethod.POST )
    public String processSubmit(
    		@RequestParam String id, 
    		ModelMap model, 
    		@ModelAttribute("operatoriFilter") AccreditamentiManagementFilter filter,
    		BindingResult result,
    		HttpServletRequest request, 
			HttpServletResponse response) {
    	
//    	saveCheckValues(request);
		
		boolean isCancel = isPrefixParamInRequest(request, "cancel");
		boolean isUpdate = isPrefixParamInRequest(request, "update"); //salva
    	boolean isPrev = isPrefixParamInRequest(request, "prev");  //pagina precedente
    	boolean isNext = isPrefixParamInRequest(request, "next");  //pagina successiva
    	boolean isFirst = isPrefixParamInRequest(request, "view"); //prima pagina
    	boolean isLast = isPrefixParamInRequest(request, "last");  //ultima pagina
    	//boolean isResultsForPage = isPrefixParamInRequest(request, "resultsForPage");  //aggiorna il numero dei risultati per pagina
    	boolean isDownloadAutocertificazione = isPrefixParamInRequest(request, "downloadAutocertificazione"); //download file autocertificazione

    	if(!isCancel){
			if(filter.getPageNavigationSettings().getResultsForPage()==null){
				filter.getPageNavigationSettings().setResultsForPage(Integer.toString(filter.getPageSize()));
			} else {
				filter.setPageSize(Integer.valueOf(filter.getPageNavigationSettings().getResultsForPage()));
			}
		}
		viewer.setPageSize(filter.getPageSize());

    	if(isFirst) {
    		viewer.setStartingPoint(0);
    	}
		if (isLast) {
			int lastPage = filter.getTotalPages();
			viewer.goToLastPage(lastPage);
		}
    	if (isNext){ 
    		int res_count = filter.getRes_count();
			viewer.increaseStartingPoint(res_count); 
		}
    	if (isPrev){ 
    		viewer.decreaseStartingPoint(); 
    	}
		
    	if(isCancel){
    		model.addAttribute("action", "accreditamentiManagement");
    		model.addAttribute("id", id);
    		model.addAttribute("plhId", "nodesList");
    		model.addAttribute("detail", true);
    		return "redirect:accreditamentiManagement.do";
    		
    	} else {
    		if (isUpdate) {
//				//aggiorno a db con i valori dei checkbox
//				boolean success = updateAccreditamenti();
//				if(success) {
//					result.addError(new ObjectError("update", 
//							this.getProperty("accreditamenti.update.success")));
//				} else {
//					result.addError(new ObjectError("update", 
//							this.getProperty("accreditamenti.update.error")));
//				}
    			
				// recupero l'idAccreditamento e lo stato da impostare
				String[] idAccreditamento_status = getIdAccreditamento_Status(request);
				// aggiorno lo stato dell'accreditamento
				updateAccreditamento(idAccreditamento_status[0], idAccreditamento_status[1]);
				
			}
    		
        	if (isDownloadAutocertificazione){
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
        		
        		
        		
        	}
    		model.put("includeTopbarLinks", true);
        	model.put("sidebar", "/WEB-INF/jsp/accreditamenti/sidebar.jsp");

        	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);	    	
        	
        	AccreditamentiManagerBean bean = getAccreditamentiManagementBean(id); 
        	model.addAttribute("bean", bean);
        	model.addAttribute("operatori", getOperatoriAssociazione(bean, filter));
        	
        	//numero di risultati per pagina	
    		PageNavigationSettings pageNavigationSettings = new PageNavigationSettings(); 
    		model.addAttribute("resultForPageList", pageNavigationSettings.getResultsForPage());
    		
    		this.setPageInfo(model, "accreditamentiManagementDetails.title", null, "accreditamentiMD");
    		
    		return getStaticProperty("accreditamenti.accreditamentiManagementDetails.view");

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
	 * @return l' AccreditamentiManagementViewer
	 */
	private void createAccreditamentiManagementViewer(HttpServletRequest request, int startingPoint, int pageSize) {

		viewer = new AccreditamentiManagementViewer(dataSourcePeopleDB, request, startingPoint, pageSize);
	}
	
	
	private AccreditamentiManagerBean getAccreditamentiManagementBean(String idAccreditamento) {
		
		Accreditamento accreditamento = new Accreditamento();
		AccreditamentiManagerBean bean = new AccreditamentiManagerBean();
		try {
			accreditamento = viewer.getAccreditamentoById(idAccreditamento);
			
			bean.setId(accreditamento.getId());
			bean.setTaxcodeUtente(accreditamento.getCodiceFiscale().toUpperCase());
			bean.setIdComune(accreditamento.getIdComune());
			bean.setNomeEnte(getNomeEnte(accreditamento.getIdComune()));
			
			ProfiloAccreditamento profilo = accreditamento.getProfilo();
			Qualifica qualifica = accreditamento.getQualifica();
	
			bean.setTaxcodeIntermediario(profilo.getCodiceFiscaleIntermediario().toUpperCase());
			bean.setVatnumberIntermediario(profilo.getPartitaIvaIntermediario());
			bean.setE_addressIntermediario(profilo.getDomicilioElettronico());
			bean.setSedeLegale(profilo.getSedeLegale());
			bean.setTipoQualifica(qualifica.getDescrizione());
			bean.setIdQualifica(qualifica.getIdQualifica());
			bean.setTipologiaIntermediario(qualifica.getTipoQualifica());
			bean.setAttivo(accreditamento.isAttivo());
			
			boolean hasRappLegale = qualifica.getHasRappresentanteLegale();
			bean.setHasRappresentanteLegale(hasRappLegale);
			if(hasRappLegale){
				RappresentanteLegale rappLegale = profilo.getRappresentanteLegale();
				bean.setRappLegale_first_name(rappLegale.getNome());
				bean.setRappLegale_last_name(rappLegale.getCognome());
				bean.setRappLegale_tax_code(rappLegale.getCodiceFiscale());
				bean.setRappLegale_address(rappLegale.getIndirizzoResidenza());
			}
			
			
		} catch (AccreditamentiManagementViewerException e) {
			logger.error("Non è stato possibile recuperare l'accreditamento con id: "
					+ idAccreditamento + " . " + e.getMessage());
		}
		
		return bean;
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
	 * @param AccreditamentiManagerBean
	 * @return operatori
	 * @throws  
	 */
	private List<AccreditamentiManagerBean> getOperatoriAssociazione(AccreditamentiManagerBean accrManBean, AccreditamentiManagementFilter operatoriFilter)  {
		
		List<AccreditamentiManagerBean> operatori = new ArrayList<AccreditamentiManagerBean>();
		if(accrManBean.getIdQualifica().equalsIgnoreCase(ASSOCIAZIONE_DI_CATEGORIA)){
				
			String idComune = accrManBean.getIdComune();
			String codiceFiscaleIntermediario = accrManBean.getTaxcodeIntermediario();
			String partitaIvaIntermediario = accrManBean.getVatnumberIntermediario();
			
			try {
				Accreditamento[] accr = viewer.getOperatoriAssociazione(idComune, codiceFiscaleIntermediario, partitaIvaIntermediario);
				
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
	
//						//verifico se i checkbox sono già stati memorizzati e recupero i valori
//						CheckboxElement elem = checkValues.get(new Integer(accreditamento.getId()));
//						if(elem!=null){
//							bean.setAttivo(elem.getAttivo());
//							bean.setDeleted(elem.getDeleted());
//						}
//						//memorizzo i valori dei checkbox
//						checkValues.put(bean.getId(), new CheckboxElement(bean.isAttivo(), bean.isDeleted()));
						
						operatori.add(bean);
					}
				
					operatoriFilter.setTaxcodeIntermediario(codiceFiscaleIntermediario);
					operatoriFilter.setVatnumberIntermediario(partitaIvaIntermediario);
					operatoriFilter.setNomeEnte(idComune);
					operatoriFilter.setTipoQualifica(OPERATORE_ASSOCIAZIONE_DI_CATEGORIA);
					
					// numero risultati
					String numAccreditamenti = viewer.getNumAccreditamenti(operatoriFilter);
					operatoriFilter.setRes_count(Integer.parseInt(numAccreditamenti)); 
					// numero di pagina attuale/totale
					operatoriFilter.setPages(viewer.getStartingPoint(), viewer.getPageSize());
				}
				
			} catch (AccreditamentiManagementViewerException e) {
				logger.error("Non è stato possibile recuperare la lista degli operatori. " + e.getMessage());
			}
			
		}
		return operatori;
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
//			if(coppia.getAttivo()){ //.equals("1")
//				attivi.add(Integer.toString(i));
//			} else {
//				non_attivi.add(Integer.toString(i));
//			}
//			//deleted
//			if(coppia.getDeleted()){ //.equals("1")
//				deleted.add(Integer.toString(i));
//			} else {
//				non_deleted.add(Integer.toString(i));
//			}
//		
//		}
//		
//		String[] accreditamenti_attivo = (String[])attivi.toArray(new String[attivi.size()]);
//		String[] accreditamenti_non_attivo = (String[])non_attivi.toArray(new String[non_attivi.size()]);
//		String[] accreditamenti_deleted = (String[])deleted.toArray(new String[deleted.size()]);
//		String[] accreditamenti_non_deleted = (String[])non_deleted.toArray(new String[non_deleted.size()]);
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
