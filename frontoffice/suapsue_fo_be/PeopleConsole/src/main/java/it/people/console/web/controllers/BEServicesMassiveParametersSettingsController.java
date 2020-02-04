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

import static it.people.console.web.servlet.tags.TagsConstants.LIST_HOLDER_TABLE_PREFIX;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
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

import it.people.console.beans.ColumnsFilters;
import it.people.console.beans.Option;
import it.people.console.beans.support.IFilter;
import it.people.console.beans.support.IFilters;
import it.people.console.beans.support.ListColumnFilter;
import it.people.console.beans.support.TextColumnFilter;
import it.people.console.domain.BEService;
import it.people.console.domain.BEServicesMassiveParametersSettings;
import it.people.console.domain.BaseBean;
import it.people.console.domain.FENode;
import it.people.console.domain.FENodeToNodeCopy;
import it.people.console.domain.PairElement;
import it.people.console.domain.TripleElement;
import it.people.console.domain.exceptions.PagedListHoldersCacheException;
import it.people.console.enumerations.EqualityOperators;
import it.people.console.enumerations.IOperatorsEnum;
import it.people.console.enumerations.LogicalOperators;
import it.people.console.enumerations.Types;
import it.people.console.persistence.IPersistenceBroker;
import it.people.console.persistence.beans.support.EditableRowInputData;
import it.people.console.persistence.beans.support.FilterProperties;
import it.people.console.persistence.beans.support.ILazyPagedListHolder;
import it.people.console.persistence.beans.support.LazyPagedListHolderFactory;
import it.people.console.persistence.exceptions.LazyPagedListHolderException;
import it.people.console.persistence.exceptions.PersistenceBrokerException;
import it.people.console.persistence.jdbc.core.EditableRow;
import it.people.console.persistence.jdbc.support.BooleanConverter;
import it.people.console.persistence.jdbc.support.Decodable;
import it.people.console.persistence.jdbc.support.NodeNameConverter;
import it.people.console.security.AbstractCommand;
import it.people.console.security.Command;
import it.people.console.security.InputCommand;
import it.people.console.security.LinkCommand;
import it.people.console.utils.CastUtils;
import it.people.console.web.controllers.utils.NodeToNodeCopier;
import it.people.console.web.controllers.validator.BEServicesMPSValidator;
import it.people.console.web.controllers.validator.BeServiceValidator;
import it.people.console.web.controllers.validator.FeNodeValidator;
import it.people.console.web.servlet.mvc.AbstractListableController;
import it.people.console.web.servlet.mvc.MessageSourceAwareController;
import it.people.console.web.utils.WebUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 29/nov/2010 22.12.30
 *
 */
@Controller
@RequestMapping("/ServiziBe")
@SessionAttributes({"beServicesMassiveParametersSettings"})
public class BEServicesMassiveParametersSettingsController extends MessageSourceAwareController {

	private List<PairElement<String, String>> allowedSchemas = new ArrayList<PairElement<String, String>>();
	
	@Autowired
	private DataSource dataSourcePeopleDB;

	@Autowired
	private IPersistenceBroker persistenceBroker;

	private BEServicesMPSValidator validator;	

	@Autowired
	public BEServicesMassiveParametersSettingsController(BEServicesMPSValidator validator) {
		this.validator = validator;
		this.setCommandObjectName("beServicesMassiveParametersSettings");
	}
	
    @RequestMapping(value = "/modificaMassivaParametri.do", method = RequestMethod.GET)
    public String setupForm(ModelMap model, HttpServletRequest request) {

    	if (allowedSchemas.isEmpty()) {
    		allowedSchemas = getAllowedSchemas();
    	}
    	
    	BEServicesMassiveParametersSettings beServicesMassiveParametersSettings = null;
    	
    	if (request.getSession().getAttribute("beServicesMassiveParametersSettings") == null || 
    			!WebUtils.getReferer(request).toLowerCase().contains("modificaMassivaParametri.do")) {
    		beServicesMassiveParametersSettings = new BEServicesMassiveParametersSettings(4);
    		try {
				beServicesMassiveParametersSettings.setNodesList(persistenceBroker.getRegisteredNodesWithBEServices());
			} catch (PersistenceBrokerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else {

    		beServicesMassiveParametersSettings = (BEServicesMassiveParametersSettings)request
    			.getSession().getAttribute("beServicesMassiveParametersSettings");
    		
    	}

    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/beservices/sidebar.jsp");
    	
    	model.addAttribute("feNodesList", getFeNodesList());
    	
    	model.put("allowedSchemas", allowedSchemas);
    	
    	model.addAttribute("beServicesMassiveParametersSettings", beServicesMassiveParametersSettings);

    	model.put("page", beServicesMassiveParametersSettings.getPage());
    	
    	return getStaticProperty("beservices.massiveParametersSettings.page1");
    	
    }
    

    @RequestMapping(value = "/modificaMassivaParametri.do", method = RequestMethod.POST)
    public String processSubmit(ModelMap model,  
    		@ModelAttribute("beServicesMassiveParametersSettings") BEServicesMassiveParametersSettings 
    		beServicesMassiveParametersSettings, BindingResult result, 
    		HttpServletRequest request) {

    	boolean isNextPage = isPrefixParamInRequest(request, "nextPage");
    	boolean isPreviousPage = isPrefixParamInRequest(request, "previousPage");
    	boolean isCancel = isPrefixParamInRequest(request, "cancel");
    	boolean isClose = isPrefixParamInRequest(request, "close");
    	
    	validator.validate(beServicesMassiveParametersSettings, result);
    	
		if (!result.hasErrors()) {
	    	if (isNextPage) {
	        	//validator.validate(feServiceRegistration, result);
	    		beServicesMassiveParametersSettings.nextPage();    		
	    		if (beServicesMassiveParametersSettings.getPage() == 2 
	    				&& !beServicesMassiveParametersSettings.isSelectSingleServices()) {
	    			beServicesMassiveParametersSettings.nextPage();
	    		}
	    	}

	    	if (isPreviousPage) {
	        	//validator.validate(feServiceRegistration, result);
	    		beServicesMassiveParametersSettings.previousPage();
	    		if (beServicesMassiveParametersSettings.getPage() == 2 
	    				&& !beServicesMassiveParametersSettings.isSelectSingleServices()) {
	    			beServicesMassiveParametersSettings.previousPage();
	    		}
	    	}
	    	
		}

    	validateAndPreparePage(beServicesMassiveParametersSettings.getPage(), model, 
    			beServicesMassiveParametersSettings, result, request);
    	
    	if (isCancel || isClose) {
    		return  "redirect:" + getStaticProperty("beservices.listAndAdd.action");
    	}
    	
		return getStaticProperty("beservices.massiveParametersSettings.page" + beServicesMassiveParametersSettings.getPage());
		
    }

    private void validateAndPreparePage(int pageNumber, ModelMap model, 
    		BEServicesMassiveParametersSettings beServicesMassiveParametersSettings, 
    		BindingResult result, 
    		HttpServletRequest request) {
    	
    	switch(pageNumber) {
    		case 1:

            	model.put("includeTopbarLinks", true);
            	
            	model.put("sidebar", "/WEB-INF/jsp/beservices/sidebar.jsp");
            	
            	model.addAttribute("beServicesMassiveParametersSettings", beServicesMassiveParametersSettings);
            	
            	model.put("page", beServicesMassiveParametersSettings.getPage());
            	
            	break;
    			
    		case 2:

            	model.addAttribute("beServicesMassiveParametersSettings", beServicesMassiveParametersSettings);
            	
    			break;
    			
    		case 3:

            	model.addAttribute("beServicesMassiveParametersSettings", beServicesMassiveParametersSettings);
            	
    			break;
    			
    		case 4:

    			
            	
    			break;
    			
    	}

    	model.put("sidebar", "/WEB-INF/jsp/beservices/sidebar.jsp");
    	model.put("page", beServicesMassiveParametersSettings.getPage());
    	model.put("includeTopbarLinks", true);
    	model.put("allowedSchemas", allowedSchemas);
    	
    }
    
	private List<PairElement<String, String>> getFeNodesList() {

		String queryNodesList = "SELECT id, comune FROM fenode";

		List<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
		Connection connection = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(queryNodesList);
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

	private List<PairElement<String, String>> getAllowedSchemas() {
		
		List<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
		
		result.add(new PairElement<String, String>("http", "http"));
		result.add(new PairElement<String, String>("https", "https"));
		
		return result;
		
	}
	
}
