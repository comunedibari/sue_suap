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
package it.people.fsl.servizi.admin.sirac.accreditamento.steps;

import it.people.Activity;
import it.people.ActivityState;
import it.people.IActivity;
import it.people.Step;
import it.people.process.AbstractPplProcess;
import it.people.wrappers.HttpServletRequestDelegateWrapperHelper;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;

import javax.servlet.ServletException;

/**
 * @author max
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CreaOACIntroStep extends Step {

	public void service(AbstractPplProcess process, IRequestWrapper request)
      throws IOException, ServletException {

		super.service(process, request);
		HttpServletRequestDelegateWrapperHelper rwh = new HttpServletRequestDelegateWrapperHelper(request);
		updateWorkflow(process, rwh);
	}
	  
  public void updateWorkflow(AbstractPplProcess process, HttpServletRequestDelegateWrapperHelper rwh) {
    Activity[] activities = process.getView().getActivities();
    Activity currentActivity = process.getView().getCurrentActivity();
    String currentActivityName = currentActivity.getName();

    IActivity firstActivity = process.getView().getActivityById("0");
    
    if (!rwh.getSiracErrors().isEmpty()) {
      process.getView().setBottomNavigationBarEnabled(false);
    } else {
      process.getView().setBottomNavigationBarEnabled(true);
    }

    for (int i=0; i< activities.length; i++) {
      String activityName = activities[i].getName(); 
      if (!activityName.equals(firstActivity.getName()) 
          && !activityName.equals(currentActivityName)) {
        activities[i].setState(ActivityState.INACTIVE);
      }
      activities[i].setCurrentStepIndex(0);
    }
  }

}
