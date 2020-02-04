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
package it.people;

import it.people.annotations.DeveloperTaskStart;
import it.people.annotations.DeveloperTaskEnd;
import it.people.core.exception.MappingException;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.process.PplData;
import it.people.wrappers.HttpServletRequestDelegateWrapper;

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Category;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Sep 3, 2003 Time: 2:00:20 PM To
 * change this template use Options | File Templates.
 */
public class Activity implements IActivity, java.io.Serializable {

    private Category cat = Category.getInstance(Activity.class.getName());

    public Activity() {
	m_state = ActivityState.INACTIVE;
	m_currentStep = 0;
	m_steps = new ArrayList();
	m_showCheck = true;
    }

    /*
     * public Activity(Properties props) { this(); String activityName =
     * props.getProperty("Activity.Name"); setName( activityName );
     * 
     * boolean exit=false; for (int count=1; !exit; count++) { String
     * propertyJsp = props.getProperty("Step" + count + ".jspPath"); String
     * propertyHelp = props.getProperty("Step" + count + ".helpUrl"); String
     * stepAccessControllerName = props.getProperty("Step" + count +
     * ".accessController"); if (propertyJsp != null && propertyHelp != null)
     * m_steps.add(new Step(propertyJsp, propertyHelp,
     * stepAccessControllerName)); else exit = true; } }
     */

    public Activity(String name, Step[] steps) {
	this();
	setName(name);

	for (int count = 0; count < steps.length; count++) {
	    m_steps.add(steps[count]);
	}
    }

    public void setStepList(ArrayList coll) {
	m_steps = coll;
    }

    public ArrayList getStepList() {
	return m_steps;
    }

    public ActivityState getState() {
	return m_state;
    }

    public boolean isCompleted() {
	return this.getState().equals(ActivityState.COMPLETED);
    }

    public boolean isInactive() {
	return this.getState().equals(ActivityState.INACTIVE);
    }

    public boolean isActive() {
	return this.getState().equals(ActivityState.ACTIVE);
    }

    @DeveloperTaskStart(name = "Riccardo Foraf�", date = "14.05.2011", bugDescription = "Nel caso di workflow con steps differenti come logica, ma con lo stesso nome, "
	    + "i nomi nella barra delle attivit� compaiono duplicati.", description = "Aggiunta la propriet� HIDDEN alle attivit�"
	    + " per nasconderle nella barra delle attivit�.")
    public boolean isHidden() {
	return this.getState().equals(ActivityState.HIDDEN);
    }

    @DeveloperTaskEnd(name = "Riccardo Foraf�", date = "14.05.2011")
    public void setState(ActivityState m_state) {
	this.m_state = m_state;
    }

    public IStep getCurrentStep() {
	return (Step) m_steps.get(getCurrentStepIndex());
    }

    public int getCurrentStepIndex() {
	return m_currentStep;
    }

    public void setCurrentStepIndex(int m_currentStep) {
	this.m_currentStep = m_currentStep;
    }

    public int getStepCount() {
	return m_steps.size();
    }

    public Step getStep(int index) {
	return (Step) m_steps.get(index);
    }

    public String getName() {
	return m_name;
    }

    public void setName(String m_name) {
	this.m_name = m_name;
    }

    public boolean isShowCheck() {
	return m_showCheck;
    }

    public void setShowCheck(boolean p_showCheck) {
	m_showCheck = p_showCheck;
    }

    public String getId() {
	return m_id;
    }

    public void setId(String id) {
	m_id = id;
    }

    public void setStepOrder(String stepOrder) {
	m_stepOrder = stepOrder;

	StringTokenizer tokenizer = new StringTokenizer(stepOrder, ",");
	m_stepOrderArray = new String[tokenizer.countTokens()];
	int idx = 0;
	while (tokenizer.hasMoreTokens()) {
	    String token = tokenizer.nextToken();
	    m_stepOrderArray[idx] = token.trim();
	    idx++;
	}
    }

    public String[] getStepOrder() {
	return m_stepOrderArray;
    }

    public IStep getStepById(String id) {
	for (int i = 0; i < m_steps.size(); i++) {
	    IStep s = (IStep) m_steps.get(i);
	    if (s.getId().equals(id))
		return s;
	}
	return null;
    }

    public boolean validate(
	    // PplData data,
	    AbstractPplProcess process, ServletContext application,
	    HttpServletRequest request, IValidationErrors errors, String path)
	    throws ParserException {

	PplData data = process.getData();
	getCurrentStep().validate(process, application, request, errors);

	// Nel caso stiamo tornando indietro devo evitare che gli errori
	// "errors.required"
	// siano bloccanti --> li tolgo dagli errori...
	if (errors.getSize() > 0 && path != null
		&& path.equalsIgnoreCase("/prevStepProcess"))
	    errors.removeRequired();

	if (errors.getSize() == 0) {
	    // QUESTE OPERAZIONI DEVONO ESSERE EVITATE IN CASO DI PRESENZA DI
	    // ERRORI
	    // PERCHE' VIENE RIPRESENTATA LA PAGINA PRECEDENTE ALL'UTENTE
	    try {
		// LEGGO I VALORI DEL dto E LI METTO NEGLI OGGETTI BO
		// in caso di MappingException aggiungo la key presente nella
		// MappingException
		// negli errors in modo da visualizare il messaggio nella pagina
		// e
		// bloccare il flusso evitando di fare il logical validate e il
		// defineControl
		process.readDto();
		if (path != null && !path.equalsIgnoreCase("/prevStepProcess")) {
		    // passo alla validazione logica se la validazione formale
		    // non ha
		    // dato problemi
		    getCurrentStep().logicalValidate(process,
			    new HttpServletRequestDelegateWrapper(request),
			    errors);
		    // Anche la logicalValidate non ha avuto problemi
		    // Chiamo il define control
		    getCurrentStep().defineControl(process,
			    new HttpServletRequestDelegateWrapper(request));
		}

	    } catch (MappingException e) {
		cat.error("ERRORE DI LETTURA DEI DATI DEL DTO -> aggiungo negli errori la KEY:"
			+ e.getKey());
		errors.add(e.getKey());
	    }

	}

	boolean activityCompleted = true;
	for (int i = 0; i < m_steps.size() && activityCompleted; i++) {
	    Step tmpStep = (Step) m_steps.get(i);

	    if (tmpStep.getAccessController() == null
		    || tmpStep.getAccessController().canEnter(data)) {
		if (!tmpStep.getState().equals(StepState.COMPLETED))
		    activityCompleted = false;
	    }
	}
	if (activityCompleted) {
	    // todo Chiamare codice per fare la verifica complessiva di tutti
	    // gli step
	}

	if (activityCompleted)
	    setState(ActivityState.COMPLETED);
	else
	    setState(ActivityState.ACTIVE);
	return activityCompleted;
    }

    private String m_name;
    private String m_id;
    private String m_stepOrder;
    private String[] m_stepOrderArray;

    private ActivityState m_state;
    private int m_currentStep;
    private ArrayList m_steps;
    private boolean m_showCheck;

}
