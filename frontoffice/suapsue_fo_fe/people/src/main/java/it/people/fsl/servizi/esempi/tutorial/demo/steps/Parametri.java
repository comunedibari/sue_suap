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
 * Created on 3-nov-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.fsl.servizi.esempi.tutorial.demo.steps;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;

import it.people.Step;
import it.people.fsl.servizi.esempi.tutorial.demo.model.ProcessData;
import it.people.process.AbstractPplProcess;
import it.people.util.params.ParamNotFoundException;
import it.people.util.table.OptionBean;
import it.people.util.table.TableNotFoundException;
import it.people.wrappers.IRequestWrapper;

/**
 * @author FabMi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Parametri extends Step {
	
	public void service(AbstractPplProcess process, IRequestWrapper request)
			throws IOException, ServletException {
		try {
			String parametro1 = process.getParameter("parametro1");
			String parametro2 = process.getParameter("parametro2");
			System.out.println("parametro1 = " + parametro1);
			System.out.println("parametro2 = " + parametro2);
			
			try {
			    Collection options = process.getTableOptions("prova");
			    int i = 0;
			    for (Iterator iter = options.iterator(); iter.hasNext();) {
                    OptionBean option = (OptionBean) iter.next();
			        if (i++ == 0)
			            ((ProcessData) process.getData()).setValore1(option.getValue());
                    System.out.println("label: " + option.getLabel() + " value:" + option.getValue());
                    
                }			    			    
			} catch(TableNotFoundException tnfex) {}
		} catch(ParamNotFoundException pnfe) {
			System.out.println(pnfe);
		}		
	}
}
