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
 * Created on 14-feb-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people;

import java.util.ArrayList;

import it.people.process.SummaryState;
import it.people.util.frontend.NeverForbidWorkflowController;

/**
 * @author fabmi
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SummaryActivity extends Activity {
    private static final String DEFAULT_SUMMARY_STEPID = "DEFAULT_SUMMARY_S";
    private static final String DEFAULT_SUMMARY_ACTIVITYID = "DEFAULT_SUMMARY_A";
    private static final String DEFAULT_SUMMARY_JSP = "/framework/view/generic/default/html/summary.jsp";

    private SummaryState summaryState;
    private boolean signEnabled;

    public SummaryActivity(boolean signEnabled, SummaryState summaryState) {
	this.signEnabled = signEnabled;
	this.summaryState = summaryState;
    }

    public boolean isSignEnabled() {
	return signEnabled;
    }

    public void setSignEnabled(boolean signEnabled) {
	this.signEnabled = signEnabled;
    }

    public SummaryState getSummaryState() {
	return summaryState;
    }

    public void setSummaryState(SummaryState summaryState) {
	this.summaryState = summaryState;
    }

    /**
     * Crea un riepilogo
     * 
     * @param signEnabled
     *            � abilitata la firma
     * @param summaryState
     *            stato del riepilogo
     * @param jspPath
     *            percorso della jsp di riepilogo
     */
    static public SummaryActivity create(boolean signEnabled,
	    SummaryState summaryState, IView view) {
	ActivityState activityState = null;

	if (summaryState.equals(SummaryState.ALWAYS))
	    activityState = ActivityState.ACTIVE;
	else if (summaryState.equals(SummaryState.FINALLY))
	    activityState = ActivityState.INACTIVE;
	else
	    return null;

	Step stepRiepilogo = new SummaryStep();
	stepRiepilogo.setId(DEFAULT_SUMMARY_STEPID);
	stepRiepilogo.setParentView(view);
	stepRiepilogo.setName("Riepilogo");

	stepRiepilogo.setJspPath(DEFAULT_SUMMARY_JSP);
	stepRiepilogo.setState(StepState.COMPLETED);
	stepRiepilogo.setHelpUrl("");

	// Il workflow va creato per essere funzione dello SummaryState
	stepRiepilogo.setAccessController(new NeverForbidWorkflowController());

	ArrayList stepList = new ArrayList();
	stepList.add(stepRiepilogo);

	SummaryActivity activityRiepilogo = new SummaryActivity(signEnabled,
		summaryState);
	activityRiepilogo.setId(DEFAULT_SUMMARY_ACTIVITYID);
	// TODO: prevedere la localizzazione di "Riepilogo"
	activityRiepilogo.setName("Riepilogo");
	activityRiepilogo.setState(ActivityState.COMPLETED);
	activityRiepilogo.setShowCheck(false);
	activityRiepilogo.setStepOrder(DEFAULT_SUMMARY_STEPID);
	activityRiepilogo.setCurrentStepIndex(0);
	activityRiepilogo.setStepList(stepList);
	activityRiepilogo.setState(activityState);

	return activityRiepilogo;
    }

}
