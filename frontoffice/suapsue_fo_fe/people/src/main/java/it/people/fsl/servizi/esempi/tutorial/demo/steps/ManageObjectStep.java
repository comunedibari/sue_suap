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
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;

import it.people.IValidationErrors;
import it.people.Step;
import it.people.fsl.servizi.oggetticondivisi.tipibase.Data;
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
public class ManageObjectStep extends Step {
	
	public void defineControl(AbstractPplProcess process,
			IRequestWrapper request) {
	}
	
	public void service(AbstractPplProcess process, IRequestWrapper request)
			throws IOException, ServletException {
		if (request.getPplUser().isPeopleAdmin())
			System.out.println("Utente Amministratore");
		else
			System.out.println("Utente Semplice");
		
		// Test di ritorno a step precedente
		ProcessData processData = (ProcessData) process.getData(); 
		if (processData.getDataDiProva() == null) {
			Calendar date = new GregorianCalendar(2005, 0, 31);
			processData.setDataDiProva(new Data(date.getTime()));
		}
		
	}

	protected void printSurfDirection(AbstractPplProcess process) {
		ConcreteView concreteView = process.getProcessWorkflow().getProcessView(); 
		SurfDirection direction = concreteView.getSurfDirection();
		
		String description = "other";
		if (direction == SurfDirection.forward)
			description = "forward";
		else if (direction == SurfDirection.backward)
			description = "backward";
			
		System.out.println(description);		
	}
	
	public boolean logicalValidate(AbstractPplProcess process,
			IRequestWrapper request, IValidationErrors errors)
			throws ParserException {
		return true;
	}
	
	public void loopBack(AbstractPplProcess process, IRequestWrapper request,
			String propertyName, int index) throws IOException,
			ServletException {
	}
}
