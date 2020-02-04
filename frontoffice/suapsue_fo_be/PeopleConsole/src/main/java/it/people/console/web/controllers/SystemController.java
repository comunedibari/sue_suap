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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.people.console.utils.Constants;
import it.people.console.web.controllers.utils.IActionsViewsCache;
import it.people.console.web.servlet.mvc.MessageSourceAwareController;
import it.people.console.web.utils.WebUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 24/giu/2011 08.36.55
 *
 */
@Controller
@RequestMapping("/")
public class SystemController extends MessageSourceAwareController {

	@Autowired
	private DataSource dataSourcePeopleDB;
	
	@Autowired
	private IActionsViewsCache actionsViewsCache;
	
	public SystemController() {
		this.setCommandObjectName("commandObject");
	}
		
    @RequestMapping(value = "accessoNonConsentito.nldo", method = RequestMethod.GET)
    public String setupAccessDenied(ModelMap model, HttpServletRequest request) {

    	model.put("sidebar", "/WEB-INF/jsp/mainSidebar.jsp");
    	
    	model.put("includeTopbarLinks", false);
    	
    	model.put("message", this.getProperty("error.notauthorized", new String[] {"<a href=\"/PeopleConsole/paginaPrincipale.mdo\">Login</a>"}));
    	
    	return getStaticProperty("system.error.view");
    	
    }

    @RequestMapping(value = "accessoNonConsentito.nldo", method = RequestMethod.POST)
    public String submitAccessDenied(ModelMap model, HttpServletRequest request) {

    	model.put("sidebar", "/WEB-INF/jsp/mainSidebar.jsp");
    	
    	model.put("includeTopbarLinks", false);
    	
    	WebUtils.logout(request);
    	
    	return "redirect:paginaPrincipale.mdo";
    	
    }
    
    @RequestMapping(value = "errore.do", method = RequestMethod.GET)
    public String showError(ModelMap model, HttpServletRequest request) {

    	model.put("sidebar", "/WEB-INF/jsp/mainSidebar.jsp");
    	
    	model.put("includeTopbarLinks", false);
    	
    	return getStaticProperty("system.error.view");
    	
    }

    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    public String setupLogout(ModelMap model, HttpServletRequest request) {

    	model.put("sidebar", "/WEB-INF/jsp/mainSidebar.jsp");
    	
    	model.put("includeTopbarLinks", false);

    	model.put(Constants.ControllerUtils.SHOW_LOGOUT_MODEL_ATTRIBUTE_KEY, false);
    	
    	model.put("message", this.getProperty("message.logout"));
    	
//    	request.getSession().setAttribute("returnView", this.actionsViewsCache.getView(WebUtils.getAction(request)));
    	request.getSession().setAttribute("returnView", WebUtils.getAction(request));
    	
    	return getStaticProperty("logout.view");
    	
    }

    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    public String submitLogout(ModelMap model, HttpServletRequest request) {

    	String returnView = String.valueOf(request.getSession().getAttribute("returnView"));
    	
    	boolean isLogout = isParamInRequest(request, "doLogout");

    	if (!isLogout) {
    		
    		this.restoreControllerModel(model, request);
    		return "redirect:" + returnView;
    		
    	} else {

			return "redirect:" + getStaticProperty("logout.success.action");
    		
    	}
    	
    }
    
}
