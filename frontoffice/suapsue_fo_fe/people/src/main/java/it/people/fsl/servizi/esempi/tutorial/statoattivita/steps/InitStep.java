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
 * Created on 27-apr-2006
 *
 */
package it.people.fsl.servizi.esempi.tutorial.statoattivita.steps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;

import it.people.ActivityState;
import it.people.Step;
import it.people.StepState;
import it.people.fsl.servizi.esempi.tutorial.statoattivita.model.Activity;
import it.people.fsl.servizi.esempi.tutorial.statoattivita.model.ProcessData;
import it.people.process.AbstractPplProcess;
import it.people.wrappers.IRequestWrapper;

/**
 * @author Fabbri Michele - Cedaf s.r.l.
 *
 */
public class InitStep extends Step {
    public void service( AbstractPplProcess process, IRequestWrapper request)
    throws IOException, ServletException {
        ProcessData processData = (ProcessData) process.getData();
        ArrayList activities = new ArrayList();  
	    it.people.Activity[] processActivity = process.getView().getActivities();
		for (int i = 1; i < processActivity.length; i++) {
		    String id = processActivity[i].getId();
		    String stateCode = "" + processActivity[i].getState().getCode();
		    Activity activity = new Activity(id, stateCode);
		    
		    ArrayList steps = processActivity[i].getStepList();
		    for (Iterator iter = steps.iterator(); iter.hasNext();) {
                Step processStep = (Step) iter.next();
                activity.getStepList().add(new it.people.fsl.servizi.esempi.tutorial.statoattivita.model.Step(
                        processStep.getId(), "" + processStep.getState().getCode())
                        );                
            }
		    
		    activities.add(activity);
		}
		processData.setActivityList(activities);
	}
	
	public void loopBack(AbstractPplProcess process, IRequestWrapper request,
			String propertyName, int index) throws IOException,
			ServletException {

		ProcessData data = (ProcessData)process.getData();
		
		// Imposta lo stato di attivit� e step
		it.people.Activity[] processActivity = process.getView().getActivities();
		// n.b. lo stato della prima attivit� � saltato di proposito
		for (int i = 1; i < processActivity.length; i++) {
		    Activity activity = data.getActivity(i - 1);
		    int stateCode = Integer.parseInt(activity.getStatusCode());
		    processActivity[i].setState(ActivityState.getState(stateCode));
		    
		    for (int j = 0; j < processActivity[i].getStepCount(); j++) {
		    	it.people.fsl.servizi.esempi.tutorial.statoattivita.model.Step step = activity.getStep(j);
		        int stepStateCode = Integer.parseInt(step.getStatusCode());
		        processActivity[i].getStep(j).setState(StepState.getState(stepStateCode));
		    }
		}
	}
	
}
