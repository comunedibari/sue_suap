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
 * Created on 2-lug-2004
 *
 */
package it.people.util;

import it.people.Activity;
import it.people.IStep;
import it.people.core.Logger;
import it.people.process.AbstractPplProcess;
import it.people.util.frontend.WorkflowController;

import java.util.List;

/**
 * @author Zoppello
 * 
 */
public class NavigatorHelper {

    public static final String BOTTONE_AVANTI = "BOTTONE_AVANTI";
    public static final String BOTTONE_INDIETRO = "BOTTONE_INDIETRO";
    public static final String BOTTONE_SALVA = "BOTTONE_SALVA";
    public static final String BOTTONE_PAGAMENTO = "BOTTONE_PAGAMENTO";

    /*
     * Ritorna true se sono nel primo step
     */
    public static boolean isFirstStepInActivity(Activity activity) {

	IStep currentStep = activity.getCurrentStep();

	List stepList = activity.getStepList();

	Logger.debug("====> CURRENT STEP id[" + currentStep.getId()
		+ "] name [" + currentStep.getName() + "]");
	IStep firstStep = (IStep) stepList.get(0);
	Logger.debug("====> FIRST STEP id[" + firstStep.getId() + "] name ["
		+ firstStep.getName() + "]");
	return firstStep.getId().equals(currentStep.getId());
    }

    public static boolean isFirstActivity(Activity activity,
	    AbstractPplProcess process) {

	Activity firstActivity = process.getView().getActivities()[0];

	Logger.debug("====> CURRENT ACTIVITY id[" + activity.getId()
		+ "] name [" + activity.getName() + "]");
	Logger.debug("====> FIRST ACTIVITY id[" + firstActivity.getId()
		+ "] name [" + firstActivity.getName() + "]");
	return firstActivity.getId().equals(activity.getId());
    }

    public static boolean isLastActivity(Activity activity,
	    AbstractPplProcess process) {

	Activity[] activities = process.getView().getActivities();

	Activity lastActivity = activities[activities.length - 1];

	Logger.debug("====> CURRENT ACTIVITY id[" + activity.getId()
		+ "] name [" + activity.getName() + "]");
	Logger.debug("====> LAST ACTIVITY id[" + lastActivity.getId()
		+ "] name [" + lastActivity.getName() + "]");
	return lastActivity.getId().equals(activity.getId());

    }

    // Ritorna l'utlimo step valido nell'attivit?
    public static IStep getLastStepValidInActivity(Activity activity,
	    AbstractPplProcess process) {
	String[] stepOrder = activity.getStepOrder(); // Mi da l'array degli id
						      // degli step in ordine di
						      // esecuzione

	IStep tmpStep = null;
	IStep lastValid = null;

	for (int i = 0; i < stepOrder.length; i++) {
	    Logger.debug("Cerco lo step con id  [" + stepOrder[i] + "]");
	    tmpStep = activity.getStepById(stepOrder[i]);

	    if (tmpStep == null) {
		Logger.debug("Activity con nome  [" + activity.getName()
			+ "] non ha lo step con id [" + stepOrder[i] + "]");
	    }

	    if (isStepEnabled(tmpStep, process)) {
		lastValid = tmpStep;
		Logger.debug("Ultimo Step Valido in actvivity ["
			+ activity.getName() + "] e lo step ["
			+ lastValid.getName() + "]");
	    }
	}
	return lastValid;
    }

    public static boolean isStepEnabled(IStep step, AbstractPplProcess process) {
	WorkflowController wfc = step.getAccessController();
	Logger.debug("isStepEnabled with id[" + step.getName() + "]");
	if (wfc == null) {
	    Logger.debug("Step with id[" + step.getName() + "] IS ENABLED ");
	    return true;
	} else {
	    Logger.debug("Step with id[" + step.getName()
		    + "] FOUND ACCESS CONTROLLER ");
	    return wfc.canEnter(process.getData());
	}
    }

    public static boolean isLastStepValidInActivity(Activity activity,
	    AbstractPplProcess process) {
	IStep currentStep = activity.getCurrentStep();

	IStep lastStepValidInActivity = getLastStepValidInActivity(activity,
		process);
	Logger.debug("isLastStepValidInActivity currentStep ["
		+ currentStep.getName() + "] lastStepValidInActivity ["
		+ lastStepValidInActivity.getName() + "]");

	return lastStepValidInActivity.getId().equals(currentStep.getId());
    }

    /*
     * Ritorna true se sono nell'ultimo step dell'activity
     */
    public static boolean isLastStepInActivity(Activity activity) {
	IStep currentStep = activity.getCurrentStep();

	List stepList = activity.getStepList();

	Logger.debug("====> CURRENT STEP id[" + currentStep.getId()
		+ "] name [" + currentStep.getName() + "]");
	IStep lastStep = (IStep) stepList.get(stepList.size() - 1);
	Logger.debug("====> LAST STEP id[" + lastStep.getId() + "] name ["
		+ lastStep.getName() + "]");
	return lastStep.getId().equals(currentStep.getId());
    }
}
