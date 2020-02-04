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
 * Created on 28-apr-2006
 */
package it.people.fsl.servizi.esempi.tutorial.statoattivita.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 */
public class Activity {
    protected String id;
    protected String statusCode;
    protected List steps = new ArrayList();

    public Activity(String id, String statusCode) {
        this.id = id;
        this.statusCode = statusCode;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    
	public List getStepList() {
		return this.steps;
	}

	public void setStepList(List steps) {
		this.steps = steps;
	}	
	
	public void setStep(int index, Step step) {
		this.steps.set(index, step);
	}	

	public Step getStep(int index) {
		return (Step) this.steps.get(index);
	}		

}
