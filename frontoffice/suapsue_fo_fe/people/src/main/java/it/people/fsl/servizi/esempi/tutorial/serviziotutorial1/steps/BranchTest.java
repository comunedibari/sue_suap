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
 * branchTest.java
 *
 * Created on 26 dicembre 2004, 19.09
 */

package it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.steps;

import it.people.ActivityState;
import it.people.Step;
import it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.model.ProcessData;
import it.people.process.AbstractPplProcess;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;
import javax.servlet.ServletException;

/**
 *
 * @author zero
 */
public class BranchTest extends Step{
    public void service(AbstractPplProcess process, IRequestWrapper request)
    throws IOException, ServletException {
        super.service(process, request);
    }
    
    /**
     * @see it.people.IStep#defineControl(it.people.process.AbstractPplProcess, it.people.wrappers.IRequestWrapper)
     */
    public void defineControl(AbstractPplProcess process,
            IRequestWrapper request) {
        
        ProcessData aData = (ProcessData)process.getData();
        System.out.println("BRANCHTEST Sto qui");
        // Controllo sugli allegati
        if (aData.isPaginaIntermedia()){
            process.getView().getActivities()[1].setState(ActivityState.ACTIVE);
        }else{
            process.getView().getActivities()[1].setState(ActivityState.INACTIVE);
        }
    }
}
