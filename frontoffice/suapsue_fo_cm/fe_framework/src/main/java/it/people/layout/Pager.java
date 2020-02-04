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
 * Created on 3-mag-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.layout;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import it.people.Activity;
import it.people.core.PeopleContext;
import it.people.process.AbstractPplProcess;
import it.people.util.NavigatorHelper;

/**
 * @author fabmi
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class Pager {
    private String cssButtonNoDisplay = "btnNoDisplay";
    private String cssButtonDisplay = "btn";

    private Activity currentActivity;
    private AbstractPplProcess pplProcess;
    private ArrayList bottoniVisibili;
    private ArrayList bottoniNascosti;

    private boolean dataValidate;
    private boolean signEnabled;
    private boolean saveEnabled;

    // Proprieta' relative al pulsante next
    private String nextLabel = "";
    private String nextOnClick = "";
    private String nextCssClass = "";

    // Proprieta' relative al pulsante previous
    private String previousLabel = "";
    private String previousOnClick = "";
    private String previousCssClass = "";

    private PeopleContext peopleContext = null;

    public Pager(HttpServletRequest request, AbstractPplProcess pplProcess,
	    Activity currentActivity, ArrayList bottoniVisibili,
	    ArrayList bottoniNascosti) {

	this.peopleContext = PeopleContext.create(request);

	this.pplProcess = pplProcess;
	this.currentActivity = currentActivity;
	this.bottoniVisibili = bottoniVisibili;
	this.bottoniNascosti = bottoniNascosti;

	this.dataValidate = pplProcess.getData().validate();
	this.signEnabled = pplProcess.isSignEnabled();
	this.saveEnabled = pplProcess.getProcessWorkflow().getProcessView()
		.isBottomSaveBarEnabled();

	InitNextSettings();
	InitPreviousSettings();
    }

    // eventualemente rinominare in GetNextKey()
    public String GetNextLabel() {
	return this.nextLabel;
    }

    public String GetNextOnClick() {
	return this.nextOnClick;
    }

    public String GetNextCssClass() {
	return this.nextCssClass;
    }

    public String GetPreviousLabel() {
	return this.previousLabel;
    }

    public String GetPreviousOnClick() {
	return this.previousOnClick;
    }

    public String GetPreviousCssClass() {
	return this.previousCssClass;
    }

    private void InitNextSettings() {
	this.nextLabel = ButtonKey.NEXT;
	this.nextCssClass = cssButtonDisplay;
	this.nextOnClick = "javascript:executeSubmit('nextStepProcess.do')";

	// Il pulsante salva e firma compare se sono verificate tutte queste
	// condizioni
	// 1) pplProcess.getData().validate() == true ( Di default lo � questa
	// � una cosa che ci portiamo dietro dal framework vecchio )
	// 2) Mi trovo nell'ultimo step dell'ultima attivita
	// 3) La firma � abilitata
	try {
	    if (this.currentActivity.getCurrentStep().getId()
		    .equalsIgnoreCase("RIMP")) {
		this.nextOnClick = "javascript:executeSubmit('startPayment.do');";
		// this.nextLabel = "Pagamento";
		this.nextLabel = ButtonKey.START_PAYMENT;
	    } else if (dataValidate
		    && (NavigatorHelper.isLastActivity(this.currentActivity,
			    pplProcess))
		    && NavigatorHelper.isLastStepValidInActivity(
			    this.currentActivity, pplProcess)
		    && !this.peopleContext.getUser().isAnonymous()) {
		if (signEnabled) {
		    // Firma Digitale
		    this.nextLabel = ButtonKey.SIGN;
		    this.nextOnClick = "javascript:executeSubmit('signProcess.do');";
		} else {
		    // Salva e Invia
		    this.nextLabel = ButtonKey.SAVE_AND_SEND;
		    this.nextOnClick = "javascript:executeSubmit('signProcess.do');";
		}
	    } else if ((NavigatorHelper.isLastStepValidInActivity(
		    this.currentActivity, pplProcess))
		    && (!NavigatorHelper.isLastActivity(this.currentActivity,
			    pplProcess))) {
		// Modulo Successivo
		this.nextLabel = ButtonKey.NEXT_MODULE;
	    } else if (this.currentActivity.getCurrentStepIndex() == this.currentActivity
		    .getStepCount() - 1) {
		this.nextCssClass = "btnNoDisplay";
	    }

	    // Disabilita il pulsante se � tra quelli nascosti
	    if (bottoniNascosti.contains(NavigatorHelper.BOTTONE_AVANTI)) {
		this.nextCssClass = "btnNoDisplay";
	    }

	    if (bottoniNascosti.contains(NavigatorHelper.BOTTONE_PAGAMENTO)) {
		this.nextCssClass = "btnNoDisplay";
	    }

	    // La collezione di bottoni visibili ha la precedenza
	    if (bottoniVisibili.contains(NavigatorHelper.BOTTONE_AVANTI)) {
		this.nextCssClass = "btn";
	    }
	} catch (Throwable t) {
	    t.printStackTrace();
	}
    }

    private void InitPreviousSettings() {
	// Impostazioni dello stile
	this.previousCssClass = cssButtonDisplay;

	if ((this.currentActivity.getCurrentStepIndex() == 0)
		&& (NavigatorHelper.isFirstActivity(this.currentActivity,
			pplProcess))) {
	    this.previousCssClass = cssButtonNoDisplay;
	} else if (this.currentActivity.getCurrentStep().getId()
		.equalsIgnoreCase("EPAG"))
	    this.previousCssClass = cssButtonNoDisplay;

	if (bottoniNascosti.contains(NavigatorHelper.BOTTONE_INDIETRO))
	    this.previousCssClass = cssButtonNoDisplay;

	if (bottoniVisibili.contains(NavigatorHelper.BOTTONE_INDIETRO))
	    this.previousCssClass = cssButtonDisplay;

	// Impostazioni del command
	this.previousOnClick = "javascript:executeSubmit('prevStepProcess.do')";

	// Impostazioni della Label
	// this.previousLabel = "<< Schermata Precedente";
	this.previousLabel = ButtonKey.PREVIOUS;

	boolean isFirstStepAndFirstActivity = (this.currentActivity
		.getCurrentStepIndex() == 0 && NavigatorHelper.isFirstActivity(
		this.currentActivity, pplProcess));

	boolean isEPAGStep = this.currentActivity.getCurrentStep().getId()
		.equalsIgnoreCase("EPAG");

	if (!isFirstStepAndFirstActivity && !isEPAGStep
		&& NavigatorHelper.isFirstStepInActivity(this.currentActivity)) {
	    // this.previousLabel = "<< Modulo Precedente";
	    this.previousLabel = ButtonKey.PREVIOUS_MODULE;
	}
    }

}
