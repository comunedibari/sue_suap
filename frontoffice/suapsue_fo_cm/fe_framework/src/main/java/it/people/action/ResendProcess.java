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
package it.people.action;

import it.people.City;
import it.people.core.PeopleContext;
import it.people.core.SubmittedProcessManager;
import it.people.core.persistence.exception.peopleException;
import it.people.fsl.servizi.oggetticondivisi.IdentificatorePeople;
import it.people.process.AbstractPplProcess;
import it.people.process.data.AbstractData;
import it.people.vsl.*;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts.action.*;

public class ResendProcess extends Action {

    private static final Logger logger = LogManager
	    .getLogger(ResendProcess.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception {
	super.execute(mapping, form, request, response);
	try {
	    Long sbmtProcessId = Long
		    .valueOf(Long.parseLong(String.valueOf(request.getSession()
			    .getAttribute("sbmtProcessId"))));
	    AbstractPplProcess pplProcess = (AbstractPplProcess) request
		    .getSession().getAttribute("pplProcess");
	    boolean isSendError = SubmittedProcessManager.getInstance()
		    .isSendError(sbmtProcessId);
	    if (!isSendError)
		throw new peopleException((new StringBuilder(
			"Submitted process ")).append(sbmtProcessId)
			.append(" not found in error state.").toString());
	    PeopleContext peopleContext = PeopleContext.create(request);
	    UnsentProcessPipelineData unsentProcessPipelineData = PipelineDataDumpManager
		    .getPipelineDataDump(sbmtProcessId.longValue());
	    PipelineData pipelineData = getUnsentProcessPipelineData(unsentProcessPipelineData);
	    pipelineData.setAttribute(
		    PipelineDataImpl.EDITABLEPROCESS_PARAMNAME, pplProcess);
	    PipelineFactory
		    .getInstance()
		    .getPipelineForName(
			    unsentProcessPipelineData.getCommune().getOid(),
			    pplProcess.getProcessName())
		    .put(peopleContext, pipelineData);
	    AbstractData data = (AbstractData) pplProcess.getData();
	    String peopleProtocolID = data.getIdentificatorePeople()
		    .getIdentificatoreProcedimento();
	    request.setAttribute("PeopleProtocollID", peopleProtocolID);
	} catch (peopleException e) {
	    logger.error(e);
	    return mapping.findForward("error");
	}
	return mapping.findForward("completed");
    }

    private PipelineData getUnsentProcessPipelineData(
	    UnsentProcessPipelineData unsentProcessPipelineData) {
	PipelineData result = new PipelineDataImpl();
	String key;
	for (Iterator iterator = unsentProcessPipelineData.getPipelineData()
		.getKeySet().iterator(); iterator.hasNext(); result
		.setAttribute(key, unsentProcessPipelineData.getPipelineData()
			.getAttribute(key)))
	    key = (String) iterator.next();

	result.setAttribute(PipelineDataImpl.IS_RESEND, new Boolean(true));
	return result;
    }
}
