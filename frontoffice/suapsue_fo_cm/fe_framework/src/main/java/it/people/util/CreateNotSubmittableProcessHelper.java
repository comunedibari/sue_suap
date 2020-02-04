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
package it.people.util;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import it.people.core.NotSubmittableProcessManager;
import it.people.core.PeopleContext;
import it.people.core.persistence.exception.peopleException;
import it.people.process.AbstractPplProcess;
import it.people.process.NotSubmittableProcess;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         19/lug/2012 16:55:44
 */
public class CreateNotSubmittableProcessHelper {

    public static void addNotSubmittableProcess(HttpServletRequest request,
	    AbstractPplProcess pplProcess) {

	boolean isNotSubmittableProcess = pplProcess.getView()
		.findSummaryActivity() == null;

	if (isNotSubmittableProcess) {
	    NotSubmittableProcessManager manager = NotSubmittableProcessManager
		    .getInstance();
	    try {
		NotSubmittableProcess existingNotSubmittableProcess = manager
			.get(pplProcess.getContext(),
				pplProcess.getCurrentOperationId());
		if (existingNotSubmittableProcess == null
			&& pplProcess.getProcessName() != null
			&& !pplProcess
				.getProcessName()
				.equalsIgnoreCase(
					"it.people.fsl.servizi.admin.sirac.accreditamento")) {
		    PeopleContext peopleContext = pplProcess.getContext();
		    Timestamp creationTime = new Timestamp(new Date().getTime());
		    NotSubmittableProcess notSubmittableProcess = new NotSubmittableProcess();
		    notSubmittableProcess.setUserID(peopleContext.getUser()
			    .getUserID());
		    notSubmittableProcess.setCommune(pplProcess.getCommune());
		    notSubmittableProcess
			    .setProcessClass(pplProcess.getClass());
		    notSubmittableProcess.setCreationTime(creationTime);
		    notSubmittableProcess.setContentName(pplProcess
			    .getCurrentContent());
		    notSubmittableProcess.setContentID(pplProcess
			    .getCurrentOperationId());
		    notSubmittableProcess.setProcessName(pplProcess
			    .getProcessName());
		    notSubmittableProcess.setDelegate(ProcessUtils
			    .isDelegate(request));
		    manager.set(peopleContext, notSubmittableProcess);
		}
	    } catch (peopleException e) {
		e.printStackTrace();
	    }
	}

    }

}
