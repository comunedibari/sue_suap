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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.people.console.beans.support.PageNavigationSettings;
import it.people.console.domain.AccreditamentiQualifica;
import it.people.console.domain.AccreditamentiQualificheFilter;
import it.people.console.domain.PairElement;
import it.people.console.utils.Constants;
import it.people.console.web.controllers.utils.AccreditamentiManagementViewer;
import it.people.console.web.controllers.utils.AccreditamentiManagementViewer.AccreditamentiManagementViewerException;
import it.people.console.web.controllers.validator.AccreditamentiQualificheValidator;
import it.people.console.web.servlet.mvc.MessageSourceAwareController;
import it.people.sirac.accr.beans.Qualifica;

/**
 * @author Luca Barbieri - Pradac Informatica S.r.l.
 * @created 15/nov/2011 15.28.43
 *
 */
@Controller
@RequestMapping("/Accreditamenti")
@SessionAttributes({ Constants.ControllerUtils.DETAILS_STATUSES_KEY, "idQualificaPresenti" })
public class AccreditamentiQualificheController extends
		MessageSourceAwareController {
	
	private AccreditamentiQualificheValidator validator;
	
	private Map<String, String> detailsStatuses = new HashMap<String, String>();
	
	private AccreditamentiManagementViewer viewer = null;
	
	final static String RAPPRESENTANTE_PERSONA_GIURIDICA = "Rappresentante Persona Giuridica";
	
	@Autowired
	public AccreditamentiQualificheController(AccreditamentiQualificheValidator validator) {
		this.validator = validator;
		this.setCommandObjectName("accreditamentiQualificheFilter");
	}

	
	@RequestMapping(value = "/accreditamentiQualifiche.do", method = RequestMethod.GET)
	public String setupForm(ModelMap model, HttpServletRequest request) {

		model.put("includeTopbarLinks", true);

		model.put("sidebar", "/WEB-INF/jsp/accreditamenti/sidebar.jsp");
		model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
		
		
    	// gestione del filtro numero righe nel cambio pagina  	
    	AccreditamentiQualificheFilter filter = null;
		if (request.getParameter("detail") != null) {
			// se ritorno dal dettaglio recupero il filtro precedente
			filter = (AccreditamentiQualificheFilter) 
				request.getSession().getAttribute("accrQualificheFilter");
		}
    	
		//crea l' AccreditamentiManagementViewer
		int startingPoint = 0; 
		int pageSize = 10; 
		
		// se filtro già presente recupero le impostazioni
    	if(filter!=null){
			pageSize = filter.getPageSize();
    		startingPoint = (filter.getActualPage()-1) * pageSize;
    	}		createAccreditamentiManagementViewer(request, startingPoint, pageSize); 
		
		// numero di risultati per pagina	
		PageNavigationSettings pageNavigationSettings = new PageNavigationSettings(); 
		model.addAttribute("resultForPageList", pageNavigationSettings.getResultsForPage());
		
    	// gestione del filtro numero righe nel cambio pagina
    	
		AccreditamentiQualificheFilter defaultFilter; 
    	if(filter!=null){
    		defaultFilter = filter;
    	} else {
    		defaultFilter = getDefaultAccreditamentiQualificheFilter(pageSize);
    	}
    	
    	//AccreditamentiQualificheFilter defaultFilter = getDefaultAccreditamentiQualificheFilter(pageSize);
    	model.addAttribute("accreditamentiQualificheFilter", defaultFilter );
		
    	List<AccreditamentiQualifica> qualifiche = getAccreditamentiQualifiche(request, defaultFilter); 
    	model.addAttribute("accreditamentiQualifiche", qualifiche);
    	
    	model.addAttribute("tipiQualifica", getTipiQualifica()); 	//tipi qualifica
    	model.addAttribute("tipiTitolare", getTipiTitolare()); 		//tipi titolare
    	
    	model.addAttribute("titolareVisible", true);
    	
    	// gestione del filtro numero righe nel cambio pagina
    	// salvo il filtro per averlo a disposizione dopo aver visionato un dettaglio
    	request.getSession().setAttribute("accrQualificheFilter", defaultFilter);
		
    	this.setPageInfo(model, "accreditamentiQualifiche.title", null, "accreditamentiQ"); 
    	
		return getStaticProperty("accreditamenti.accreditamentiQualifiche.view");
	}
	
	
    @RequestMapping(value = "/accreditamentiQualifiche.do", method = RequestMethod.POST)
    public String processSubmit(ModelMap model, 
    		@ModelAttribute("accreditamentiQualificheFilter") AccreditamentiQualificheFilter filter, BindingResult result, 
    		HttpServletRequest request) {

    	boolean isSaveInRequest = isParamInRequest(request, "saveNewQualifica");
    	boolean isPrev = isPrefixParamInRequest(request, "prev"); //pagina precedente
    	boolean isNext = isPrefixParamInRequest(request, "next"); //pagina successiva
    	boolean isLast = isPrefixParamInRequest(request, "last"); //ultima pagina
    	
    	if (isSaveInRequest) {
        	validator.validateNew(filter, result);
    	}
    	
		if(filter.getPageNavigationSettings().getResultsForPage()==null){
			filter.getPageNavigationSettings().setResultsForPage(Integer.toString(filter.getPageSize()));
		} else {
			filter.setPageSize(Integer.valueOf(filter.getPageNavigationSettings().getResultsForPage()));
		}
		viewer.setPageSize(filter.getPageSize());
    	
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
    	
    	if (isSaveInRequest && !result.hasErrors()) {
    		boolean salvataggio = saveQualifica(request, filter.getQualifica(), result);
    		if (salvataggio) {
        		filter.getQualifica().clear();
        		
        		return  "redirect:accreditamentiQualifiche.do";
    		} else {
    			
    			PageNavigationSettings pageNavigationSettings = new PageNavigationSettings(); 
    			model.addAttribute("resultForPageList", pageNavigationSettings.getResultsForPage());

    			model.put("includeTopbarLinks", true);
            	model.put("sidebar", "/WEB-INF/jsp/accreditamenti/sidebar.jsp");
            	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
            	
            	List<AccreditamentiQualifica> qualifiche = getAccreditamentiQualifiche(request, filter); 
            	model.addAttribute("accreditamentiQualifiche", qualifiche);
            	

            	model.addAttribute("accreditamentiQualificheFilter", filter );
    			
        		model.addAttribute("tipiQualifica", getTipiQualifica()); 	//tipi qualifica
        		model.addAttribute("tipiTitolare", getTipiTitolare()); 		//tipi titolare

            	if(filter.getQualifica().getTipo_qualifica().equalsIgnoreCase(RAPPRESENTANTE_PERSONA_GIURIDICA)){
        			model.addAttribute("titolareVisible",false);
        		} else {
        			model.addAttribute("titolareVisible",true);
        		}
            	
            	this.setPageInfo(model, "accreditamentiQualifiche.title", null, "accreditamentiQ"); 
            	
            	return getStaticProperty("accreditamenti.accreditamentiQualifiche.view");
    		}
    	}
    	else {
    		
    		if (!isNext && !isPrev && !isLast) {
				viewer.setStartingPoint(0);
			}
    		
    		PageNavigationSettings pageNavigationSettings = new PageNavigationSettings(); 
    		model.addAttribute("resultForPageList", pageNavigationSettings.getResultsForPage());
    		
        	model.put("includeTopbarLinks", true);
        	model.put("sidebar", "/WEB-INF/jsp/accreditamenti/sidebar.jsp");
        	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
        	
        	List<AccreditamentiQualifica> qualifiche = getAccreditamentiQualifiche(request, filter); 
        	model.addAttribute("accreditamentiQualifiche", qualifiche);
    		
    		model.addAttribute("accreditamentiQualificheFilter", filter );
			
    		model.addAttribute("tipiQualifica", getTipiQualifica()); 	//tipi qualifica
    		model.addAttribute("tipiTitolare", getTipiTitolare()); 		//tipi titolare

    		if(filter.getQualifica().getTipo_qualifica().equalsIgnoreCase(RAPPRESENTANTE_PERSONA_GIURIDICA)){
    			model.addAttribute("titolareVisible",false);
    		} else {
    			model.addAttribute("titolareVisible",true);
    		}
    		
        	// salvo il filtro per averlo a disposizione dopo aver visionato un dettaglio
        	request.getSession().setAttribute("accrQualificheFilter", filter);
    		
        	this.setPageInfo(model, "accreditamentiQualifiche.title", null, "accreditamentiQ"); 
    		
        	return getStaticProperty("accreditamenti.accreditamentiQualifiche.view");    		
    		
    	}
    }
    
	@RequestMapping(value = "/accreditamentiQualificheDetails.do", method = RequestMethod.GET)
    public String setupEditForm(@RequestParam String idQualifica, @RequestParam String autoc, ModelMap model, HttpServletRequest request) {
		
    	model.put("includeTopbarLinks", true);
    	model.put("sidebar", "/WEB-INF/jsp/accreditamenti/sidebar.jsp");
    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
    	
    	try {
			Qualifica qualifica = viewer.getQualifica(idQualifica);
			AccreditamentiQualifica qualificaBean = new AccreditamentiQualifica(qualifica);
			
			if (qualifica.getTipoQualifica().equalsIgnoreCase(RAPPRESENTANTE_PERSONA_GIURIDICA)) {
				model.addAttribute("titolareVisible",false);
			} else {
				model.addAttribute("titolareVisible",true);
				if (qualifica.getHasRappresentanteLegale()) {
					qualificaBean.setTitolare("1");
				}
			}
			
			qualificaBean.setAuto_certificabile(Boolean.valueOf(autoc));
			model.addAttribute("qualifica", qualificaBean );
    		
		} catch (AccreditamentiManagementViewerException e) {
			e.printStackTrace();
		}
    	
		model.addAttribute("tipiQualifica", getTipiQualifica()); 	//tipi qualifica
		model.addAttribute("tipiTitolare", getTipiTitolare()); 		//tipi titolare
		
		this.setPageInfo(model, "accreditamentiQualificheDetail.title", null, "accreditamentiQ"); 
		
    	return getStaticProperty("accreditamenti.accreditamentiQualificheDetails.view");    
	}
    
	
	@RequestMapping(value = "/accreditamentiQualificheDetails.do", method = RequestMethod.POST)
	public String processEditSubmit(ModelMap model,
			@ModelAttribute("qualifica") AccreditamentiQualifica qualifica,
			BindingResult result, HttpServletRequest request) {

		boolean isUpdateQualifica = isParamInRequest(request, "updateQualifica");
		boolean isCancel = isParamInRequest(request, "cancel");

		if (isUpdateQualifica) {
			validator.validateUpdate(qualifica, result);

			if (!result.hasErrors()) {

				updateQualifica(qualifica, result);
				qualifica.clear();
				model.addAttribute("detail", true);
				return "redirect:accreditamentiQualifiche.do";
			}

		}

		if (isCancel) {
			model.addAttribute("detail", true);
			return "redirect:accreditamentiQualifiche.do";
		}
		
    	model.put("includeTopbarLinks", true);
    	model.put("sidebar", "/WEB-INF/jsp/accreditamenti/sidebar.jsp");
    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
    	
		
		if (qualifica.getTipo_qualifica().equalsIgnoreCase(RAPPRESENTANTE_PERSONA_GIURIDICA)) {
			model.addAttribute("titolareVisible",false);
		} else {
			model.addAttribute("titolareVisible",true);
		}
    	
    	model.addAttribute("tipiQualifica", getTipiQualifica()); 	//tipi qualifica
		model.addAttribute("tipiTitolare", getTipiTitolare()); 		//tipi titolare
		
		this.setPageInfo(model, "accreditamentiQualificheDetail.title", null, "accreditamentiQ"); 

		return getStaticProperty("accreditamenti.accreditamentiQualificheDetails.view");    
	}
    
	
	@RequestMapping(value = "/conferma.do", method = RequestMethod.GET)
    public String setupConferma(@RequestParam String idQualifica, ModelMap model,
    		@ModelAttribute("qualifica") AccreditamentiQualifica qualifica, BindingResult result,
    		HttpServletRequest request) {

		if(idQualifica.endsWith("_js")){
			idQualifica = idQualifica.substring(0, idQualifica.length()-3);
			deleteQualifica(idQualifica, result);
			
			return "redirect:accreditamentiQualifiche.do";
		} else {
		
			model.put("includeTopbarLinks", true);
			model.put("sidebar", "/WEB-INF/jsp/accreditamenti/sidebar.jsp");
			model.put("message", "Si desidera eliminare l'elemento?");

			return getStaticProperty("confirm.view");
		}
    	
    }
    
    @RequestMapping(value = "/conferma.do", method = RequestMethod.POST)
    public String processConferma(@RequestParam String idQualifica, ModelMap model,  
    		@ModelAttribute("qualifica") AccreditamentiQualifica qualifica, BindingResult result, 
    		HttpServletRequest request) {
    	
    	boolean isConfirmAction = isParamInRequest(request, "confirmAction");
    	
    	if (isConfirmAction) {
    		if (logger.isDebugEnabled()) {
    			logger.debug("Action confirmed.");
    		}
    		deleteQualifica(idQualifica, result);
 
    	} else {
    		if (logger.isDebugEnabled()) {
    			logger.debug("Action canceled.");
    		}
    	}
    	
		return "redirect:accreditamentiQualifiche.do";
    }

    
	/**
	 * @return l' AccreditamentiManagementViewer
	 */
	private void createAccreditamentiManagementViewer(HttpServletRequest request, int startingPoint, int pageSize) {

		viewer = new AccreditamentiManagementViewer(request, startingPoint, pageSize);
	}
    
	
    /**
	 * @return il Filtro impostato di default
	 */
	private AccreditamentiQualificheFilter getDefaultAccreditamentiQualificheFilter(int pageSize) {
		
		AccreditamentiQualificheFilter filter = new AccreditamentiQualificheFilter();

		filter.setPageSize(pageSize);
		filter.getPageNavigationSettings().setResultsForPage(Integer.toString(pageSize));
		
		return filter;
	}
	
	/**
	 * @return la lista dei tipi qualifica (Tipologia Intermediario) presenti
	 */
	private List<PairElement<String, String>> getTipiQualifica() {
		
		List<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
		String[] tipiQualifica = null;
		try {
			tipiQualifica = viewer.getTipiQualifica();
			
			for (String tipoQualifica : tipiQualifica) {
				result.add(new PairElement<String, String>(tipoQualifica, tipoQualifica));
			}
			
		} catch (AccreditamentiManagementViewerException e) {
			logger.error("Non è stato possibile recuperare la lista dei tipi qualifica. " + e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * @return la lista dei tipi di titolare
	 */
	private List<PairElement<String, String>> getTipiTitolare() {
		
		List<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
		
		result.add(new PairElement<String, String>("0", "Persona Fisica"));
		result.add(new PairElement<String, String>("1", "Persona Giuridica"));
		
		return result;
	}
	
	
	/**
	 * @param filtro
	 * @return
	 * @throws  
	 */
	private List<AccreditamentiQualifica> getAccreditamentiQualifiche(HttpServletRequest request, AccreditamentiQualificheFilter defaultFilter)  {
		
		List<AccreditamentiQualifica> qualifiche = new ArrayList<AccreditamentiQualifica>();
		
		try {
			Qualifica[] res = viewer.getQualifiche(defaultFilter);
			if(res!=null) {
				for (Qualifica qualifica : res) {
					AccreditamentiQualifica bean = new AccreditamentiQualifica();
					
					bean.setId_qualifica(qualifica.getIdQualifica());
					bean.setDescrizione(qualifica.getDescrizione());
					bean.setTipo_qualifica(qualifica.getTipoQualifica());	
					
					String[] info = viewer.getInfoQualifica(qualifica.getIdQualifica());
					boolean auto_certificabile = Integer.valueOf(info[0])>0;
					boolean qualifica_utilizzata = Integer.valueOf(info[1])>0;
					bean.setAuto_certificabile(auto_certificabile);
					bean.setQualifica_utilizzata(qualifica_utilizzata);

					bean.setHas_rappresentante_legale(qualifica.getHasRappresentanteLegale());
					bean.setEditLink(createQualificaEditLink(qualifica.getIdQualifica(), auto_certificabile));
					bean.setDeleteLink(createQualificaDeleteLink(qualifica.getIdQualifica()));
					bean.setDeleteLinkJS(createQualificaDeleteLinkJS(qualifica.getIdQualifica()));
					
					qualifiche.add(bean);
				}
			
				// numero risultati
				Qualifica[] qualifichePresenti = viewer.getQualifiche(); 
				int numeroQualifichePresenti = qualifichePresenti.length;
				defaultFilter.setRes_count(numeroQualifichePresenti); 

				// salvo le qualifiche già presenti
				setIdQualificaPresentiInSession(request, qualifichePresenti);
				
				// numero di pagina attuale/totale
				defaultFilter.setPages(viewer.getStartingPoint(), viewer.getPageSize());
			}
			
		} catch (AccreditamentiManagementViewerException e) {
			logger.error("Non è stato possibile recuperare la lista delle qualifiche. " + e.getMessage());
		}
		return qualifiche;
	}


	/**
	 * @param request
	 * @param qualifichePresenti
	 */
	private void setIdQualificaPresentiInSession(HttpServletRequest request,
			Qualifica[] qualifichePresenti) {
		List<String> idQualificaPresenti = new ArrayList<String>();
		for (Qualifica qualifica : qualifichePresenti) {
			idQualificaPresenti.add(qualifica.getIdQualifica());
		}
		request.getSession().setAttribute("idQualificaPresenti", idQualificaPresenti);
	}
	
	
    private boolean saveQualifica(HttpServletRequest request, AccreditamentiQualifica qualifica, BindingResult bindingResult) {
    	
    	boolean result = true;
    	
    	String idQualifica = qualifica.getId_qualifica().toUpperCase();
    	@SuppressWarnings("unchecked")
		ArrayList<String> idQualificaPresenti = (ArrayList<String>) request.getSession().getAttribute("idQualificaPresenti");

    	if(idQualifica.length()!=3){
			logger.error("ID Qualifica deve essere una sigla composta da tre caratteri.");
			result = false;
			bindingResult.rejectValue("qualifica.id_qualifica", "error.accreditamenti.qualifiche.invalid");
    	} else if(idQualificaPresenti.contains(idQualifica)){
			logger.error("La qualifica "+idQualifica+" è già presente.");
			result = false;
			bindingResult.rejectValue("qualifica.id_qualifica", "error.accreditamenti.qualifiche.exists");
    	} else {
    		
    		String auto_certificabile = qualifica.isAuto_certificabile()?"1":"0";
			String has_rappresentante_legale = getHasRappresentanteLegale(qualifica);
    		
			try {
				viewer.insertQualifica(idQualifica, qualifica.getDescrizione(),
						qualifica.getTipo_qualifica(), auto_certificabile,
						has_rappresentante_legale);
			} catch (AccreditamentiManagementViewerException e1) {
				logger.error("Errore durante il salvataggio di una nuova qualifica.", e1);
				result = false;
				bindingResult.rejectValue("error", "error.generic");
			}
    		
    	}
    	
		return result;
		
    }
    
    
    private void updateQualifica(AccreditamentiQualifica qualifica, BindingResult result) {

		String auto_certificabile = qualifica.isAuto_certificabile()?"1":"0";
		String has_rappresentante_legale = getHasRappresentanteLegale(qualifica);
		
		try {
			viewer.updateQualifica(qualifica.getId_qualifica(), qualifica.getDescrizione(),
					qualifica.getTipo_qualifica(), auto_certificabile,
					has_rappresentante_legale);
		} catch (AccreditamentiManagementViewerException e1) {
			logger.error("Errore durante l'aggiornamento della qualifica.", e1);
			result.rejectValue("error", "error.generic");
		}
    	
    }
    
    private void deleteQualifica(String idQualifica, BindingResult result) {
    	
    	try {
    		viewer.deleteQualifica(idQualifica);
    	} catch (AccreditamentiManagementViewerException e1) {
    		logger.error("Errore durante l'eliminazione della qualifica.", e1);
    		result.rejectValue("error", "error.generic");
    	}
    	
    }


	/**
	 * @param qualifica
	 * @return
	 */
	private String getHasRappresentanteLegale(AccreditamentiQualifica qualifica) {

		qualifica.setHas_rappresentante_legale(false);

		if (qualifica.getTipo_qualifica().equalsIgnoreCase(RAPPRESENTANTE_PERSONA_GIURIDICA)) {
			qualifica.setHas_rappresentante_legale(true);
		} else {
			if(qualifica.getTitolare()==null){
				qualifica.setTitolare("0");
			}
			if (qualifica.getTitolare().equalsIgnoreCase("1")) {
				qualifica.setHas_rappresentante_legale(true);
			}
		}
		return qualifica.isHas_rappresentante_legale() ? "1" : "0";
	}
	
	/**
	 * @param idQualifica
	 * @param auto_certificabile 
	 * @return
	 */
	private String createQualificaEditLink(String idQualifica, boolean auto_certificabile) {
		String link = "accreditamentiQualificheDetails.do?idQualifica=" + idQualifica +"&amp;autoc="+auto_certificabile;
		return link;
	}
	
	/**
	 * @param idQualifica
	 * @return
	 */
	private String createQualificaDeleteLink(String idQualifica) {
		String link = "conferma.do?idQualifica=" + idQualifica;
		return link;
	}
	/**
	 * @param idQualifica
	 * @return
	 */
	private String createQualificaDeleteLinkJS(String idQualifica) {
		String link = "conferma.do?idQualifica=" + idQualifica+ "_js";
		return link;
	}
	
}
