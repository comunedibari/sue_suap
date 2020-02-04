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
package it.people.console.web.servlet.mvc.annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.view.RedirectView;

import it.people.console.utils.Constants;
import it.people.console.web.utils.WebUtils;
import it.people.console.web.servlet.mvc.IController;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 17/lug/2011 18.21.19
 *
 */
public class ExtendedAnnotationMethodHandlerAdapter extends
		AnnotationMethodHandlerAdapter {

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter#handle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		ModelAndView result = super.handle(request, response, handler);
		if (result.getModelMap().get(Constants.ControllerUtils.SHOW_LOGOUT_MODEL_ATTRIBUTE_KEY) == null) {
			result.getModelMap().addAttribute(Constants.ControllerUtils.SHOW_LOGOUT_MODEL_ATTRIBUTE_KEY, true);
		}
		
		if (WebUtils.isParamInRequest(request, "logout")) {
	    	WebUtils.holdControllerModel(result.getModelMap(), request);
			return new ModelAndView(new RedirectView(request.getContextPath() + "/logout.do"));
		} else {
			return result;
		}
		
	}
	
}
