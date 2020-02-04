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
 * Created on 18-apr-2005
 */
package it.people.fsl.servizi.esempi.tutorial.statoattivita.model;

import java.util.ArrayList;
import java.util.List;

import it.people.core.PeopleContext;
import it.people.process.AbstractPplProcess;
import it.people.process.data.AbstractData;
import it.people.vsl.PipelineData;

/**
 * @author fabmi
 */
public class ProcessData extends AbstractData {
    List activities = new ArrayList();
    
	protected void doDefineValidators() { }

	public void exportToPipeline(PipelineData pd) { }

	public void initialize(PeopleContext context, AbstractPplProcess pplProcess) {
	}
		
	public boolean validate() {
		return false;
	}

	public List getActivityList() {
		return this.activities;
	}

	public void setActivityList(List activities) {
		this.activities = activities;
	}	
	
	public void setActivity(int index, Activity activity) {
		this.activities.set(index, activity);
	}	

	public Activity getActivity(int index) {
		return (Activity) this.activities.get(index);
	}	
}
