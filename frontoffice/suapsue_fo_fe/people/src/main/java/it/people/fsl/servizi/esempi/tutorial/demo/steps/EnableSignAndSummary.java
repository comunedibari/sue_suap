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
 * Created on 9-nov-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.fsl.servizi.esempi.tutorial.demo.steps;

import java.io.IOException;

import javax.servlet.ServletException;

import it.people.IValidationErrors;
import it.people.Step;
import it.people.fsl.servizi.esempi.tutorial.demo.model.ProcessData;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.process.SummaryState;
import it.people.wrappers.IRequestWrapper;

/**
 * @author FabMi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EnableSignAndSummary extends Step {

	public void service(AbstractPplProcess process, IRequestWrapper request)
	throws IOException, ServletException {
		System.out.println(process.isSignEnabled());
	}
	
    public void defineControl(AbstractPplProcess process, IRequestWrapper request) {
		ProcessData data = (ProcessData) process.getData();
		process.setSignEnabled(data.isAbilitaFirma());
		
		if ("always".equals(data.getAbilitaRiepilogo()))
			process.setSummaryState(SummaryState.ALWAYS);
		else if ("finally".equals(data.getAbilitaRiepilogo()))
			process.setSummaryState(SummaryState.FINALLY);
		else if ("none".equals(data.getAbilitaRiepilogo()))
			process.setSummaryState(SummaryState.NONE);
    }
    
    public boolean logicalValidate(AbstractPplProcess process,
			IRequestWrapper request, IValidationErrors errors)
			throws ParserException {
		return true;
	}
}
