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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.people.console.beservices.BeServicesChecker;
import it.people.console.domain.ConsoleInfoBean;
import it.people.console.dto.UnavailableBEServiceDTO;
import it.people.console.web.servlet.mvc.MessageSourceAwareController;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 29/nov/2010 22.12.30
 *
 */
@Controller
@RequestMapping("/")
@SessionAttributes({"consoleInfo"})
public class MainController extends MessageSourceAwareController {

	@Autowired
	BeServicesChecker beServiceChecker;
	
	public MainController() {
		this.setCommandObjectName("consoleInfo");
	}
	
    @RequestMapping(value = "paginaPrincipale.mdo", method = RequestMethod.GET)
    public String setupForm(ModelMap model, HttpServletRequest request) {

    	//Update Console Info
    	ConsoleInfoBean consoleInfo = new ConsoleInfoBean();
    	updateConsoleInfo(consoleInfo);
    	
    	model.put("sidebar", "/WEB-INF/jsp/mainSidebar.jsp");
    	
    	model.put("includeTopbarLinks", false);
    	
    	model.put("consoleInfo", consoleInfo);
    	
    	return getStaticProperty("mainpage.view");
    }
    
    @RequestMapping(value = "paginaPrincipale.mdo", method = RequestMethod.POST)
    public String processSubmit(ModelMap model, 
    		@ModelAttribute("consoleInfo") ConsoleInfoBean consoleInfo,
    		HttpServletRequest request) {

    	updateConsoleInfo(consoleInfo);
    	
    	model.put("sidebar", "/WEB-INF/jsp/mainSidebar.jsp");
    	
    	model.put("includeTopbarLinks", false);
    	
    	model.put("consoleInfo", consoleInfo);
    	
    	return getStaticProperty("mainpage.view");
    }
    
    
    /**
     * Return a the updated unavailable services in JSON.
     * 
     * @param model
     * @param consoleInfo
     * @return
     */
    @RequestMapping(value="json/updateBe.json", method=RequestMethod.GET, produces="application/json" )
    public @ResponseBody List <UnavailableBEServiceDTO> getUpdatedBeInfo() {

		return beServiceChecker.getUnavailableBeServices();
    }

    
    private boolean updateConsoleInfo(ConsoleInfoBean consoleInfo) {
    	
    	boolean updated = false;
    	//Update unavailableBeService
    	List<UnavailableBEServiceDTO> unavailableBeServices = beServiceChecker.getUnavailableBeServices();
    	
    	if(!consoleInfo.getBeServicesDown().equals(unavailableBeServices)) {
    		consoleInfo.setBeServicesDown(unavailableBeServices);
    		updated = true;
    	}
    	//Other update of main page here.
    	return updated;
    }
    
    
    
}
