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
/*
 * Created on 18-apr-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.fsl.servizi.esempi.tutorial.demo.steps;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import it.people.IValidationErrors;
import it.people.Step;
import it.people.core.PeopleContext;
import it.people.fsl.servizi.esempi.tutorial.demo.model.ProcessData;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.process.SurfDirection;
import it.people.process.view.ConcreteView;
import it.people.wrappers.IRequestWrapper;

/**
 * @author fabmi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LoopbackStep extends Step {
    public static final String PULSANTE_A = "LoopbackA";
    public static final String PULSANTE_B = "LoopbackB";
    public static final String PULSANTE_C = "LoopbackC";

	/* (non-Javadoc)
	 * @see it.people.IStep#loopBack(it.people.process.AbstractPplProcess, it.people.wrappers.IRequestWrapper, java.lang.String, int)
	 */
	public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException,
	ServletException {
		ProcessData data = (ProcessData) process.getData();
		data.setProva(data.getProva());
		
		if (PULSANTE_A.equals(propertyName)) {
		    data.setPulsantePremuto("A");
		} else if (PULSANTE_B.equals(propertyName)) {
		    data.setPulsantePremuto("B");
		} else if (PULSANTE_C.equals(propertyName)) {
		    data.setPulsantePremuto("C");
		} else
		    data.setPulsantePremuto("");
		
		data.setPulsantePremutoIndice(index);
	}		
	
	public void service(AbstractPplProcess process, IRequestWrapper request)
			throws IOException, ServletException {
		
	    String codiceIstatEnte = process.getCommune().getKey();
	    String codiceIstatEnte2 = PeopleContext.create((HttpServletRequest)request).getCommune().getKey(); 
	    
	    
		ConcreteView concreteView = process.getProcessWorkflow().getProcessView(); 
		SurfDirection direction = concreteView.getSurfDirection();
		
		String description = "other";
		if (direction == SurfDirection.forward)
			description = "forward";
		else if (direction == SurfDirection.backward)
			description = "backward";
			
		System.out.println(description);
		
		// Stampa dei parametri
		printRequestParameter(request);
	}

	/**
	 * Stampa i parametri della request
	 * @param request
	 */
	protected void printRequestParameter(IRequestWrapper request) {
		 for (Enumeration paramNameEnum = request.getParameterNames(); paramNameEnum.hasMoreElements() ;) {
		 	String paramName = (String)paramNameEnum.nextElement();
		 	String values[] = request.getParameterValues(paramName);
		 	for (int i = 0; i < values.length; i++)
		 		System.out.println(paramName + "[" + i + "] = '" + values[i] + "'");
	     }
	}
	
	public void defineControl(AbstractPplProcess process, IRequestWrapper request) {
	}
	
	public boolean logicalValidate(
			AbstractPplProcess process,
	        IRequestWrapper request,
			IValidationErrors errors)
		throws ParserException {
		
		boolean isLoopback = request.getUnwrappedRequest().getServletPath().equalsIgnoreCase("/loopBackValidate.do");
		
		ProcessData data = (ProcessData) process.getData();
		if (!"OK".equals(data.getProva())) {
			errors.add("error.scrivereok");
			return false;
		}
		
		return true;		
	}
}
