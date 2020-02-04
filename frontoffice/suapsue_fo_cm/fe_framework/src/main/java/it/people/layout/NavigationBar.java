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
 * NavigationBar.java
 *
 * Created on November 9, 2004, 4:34 PM
 */

package it.people.layout;

import it.people.Activity;
import it.people.IActivity;
import it.people.IStep;
import it.people.content.ContentImpl;
import it.people.core.ContentManager;
import it.people.core.persistence.exception.peopleException;
import it.people.process.view.ConcreteView;
import java.util.ArrayList;

/**
 * 
 * @author manelli
 */
public class NavigationBar extends LayoutObject {
    /**
     * Lista del path delle attivita' svolte sin'ora l'ultima e' quella corrente
     */
    private ArrayList activityPath;

    /** Creates a new instance of NavigationBar */
    public NavigationBar(ConcreteView view) {
	setActivityPath(new ArrayList());
	update(view);
    }

    /**
     * Aggiorna la barra di navgazione ri-enumerando le actvity contenute nel
     * processo e creando il path connesso alla barra.
     * 
     * @param la
     *            view associata al processo
     */
    public void update(ConcreteView view) {
	super.update(view);

	getActivityPath().clear();

	Activity[] activityArray = view.getActivities();
	PathElement element;

	for (int i = 0; i < activityArray.length; i++) {
	    Activity temp = activityArray[i];

	    if (temp == view.getCurrentActivity()) {
		// do nothing.
	    } else if (temp.isCompleted()) {
		element = new PathElement();
		element.setName(temp.getName());
		element.setId(temp.getId());
		getActivityPath().add(element);
	    }
	}

	element = new PathElement();
	element.setName(view.getCurrentActivity().getName());
	element.setId(view.getCurrentActivity().getId());
	getActivityPath().add(element);
    }

    /**
     * ritorna il percorso delle attivita' svolte. L'attivita' corrente e'
     * sempre l'ultima della lista.
     * 
     * @return una lista di PathElement
     */
    public ArrayList getActivityPath() {
	return activityPath;
    }

    /**
     * Setta la lista dei PathElement a quella specificata
     * 
     * @param activityPath
     *            il nuovo path della barra di navigazione
     */
    public void setActivityPath(ArrayList activityPath) {
	this.activityPath = activityPath;
    }

    public void setView(ConcreteView view) {
	update(view);
    }

    /**
     * Ritorna l'attivit� corrente
     * 
     * @return Attivit� corrente
     */
    public IActivity getCurrentActivity() {
	return view.getCurrentActivity();
    }

    /**
     * Ritorna il nome dell'attivit� corrente
     * 
     * @return Stringa con il nome
     */
    public String getCurrentActivityName() {
	return getCurrentActivity().getName();
    }

    /**
     * Ritorna lo step corrente
     * 
     * @return Step corrente
     */
    public IStep getCurrentStep() {
	return view.getCurrentActivity().getCurrentStep();
    }

    public String getCurrentStepName() {
	return getCurrentStep().getName();
    }

    public String getServiceDescription() {
	try {
	    ContentImpl content = ContentManager.getInstance()
		    .getForProcessName(view.getProcessName());
	    return (content.getName() != null ? content.getName() : "");
	} catch (peopleException ex) {
	    return "";
	}
    }

}
