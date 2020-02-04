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
package it.people.console.web.servlet.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/onlinehelp")
public class OnLineHelpController {

    @RequestMapping(value = "/getTooltipPage.do", method = RequestMethod.GET)
    public @ResponseBody String getTooltipPage() {

	// @ResponseBody will automatically convert the returned value into JSON
	// format
	// You must have Jackson in your classpath
	return "Test string";
	
    }
    
    @RequestMapping(value = "/getTooltip.do", method = RequestMethod.POST)
    public @ResponseBody String getTooltip(@RequestParam(value = "tooltipcode", required = true) String tooltipCode, Model model) {

	// @ResponseBody will automatically convert the returned value into JSON
	// format
	// You must have Jackson in your classpath
	return "Test string";
	
    }

}
