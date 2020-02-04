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
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.people.console.web.servlet.mvc.MessageSourceAwareController;
import it.people.console.web.utils.WebUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 24/giu/2011 08.36.55
 *
 */
@Controller
@RequestMapping("/")
public class NewLoginController extends MessageSourceAwareController {

	@Autowired
	private DataSource dataSourcePeopleDB;
	
    @RequestMapping(value = "newlogin.nldo", method = RequestMethod.GET)
    public String setupDoLogout(ModelMap model, HttpServletRequest request) {

		WebUtils.logout(request);
		
    	model.put("sidebar", "/WEB-INF/jsp/mainSidebar.jsp");
    	
    	model.put("includeTopbarLinks", false);
    	
    	model.put("message", this.getProperty("message.logout.success", 
    			new String[] {"E' possibile <a href=\"paginaPrincipale.mdo\">accedere nuovamente al sistema</a>."}));
		return getStaticProperty("logout.success.view");
    	
    }

}
