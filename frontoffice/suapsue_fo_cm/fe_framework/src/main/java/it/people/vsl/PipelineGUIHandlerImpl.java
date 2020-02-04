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
package it.people.vsl;

import it.people.util.DataHolderStatus;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Category;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Sep 17, 2003 Time: 7:26:38 AM To
 * change this template use Options | File Templates.
 */
public abstract class PipelineGUIHandlerImpl extends PipelineHandlerImpl {

    private Category cat = Category.getInstance(PipelineGUIHandlerImpl.class
	    .getName());

    public final void process(Collection holders) {
	Iterator iter = holders.iterator();
	while (iter.hasNext()) {
	    PipelineDataHolder pdh = (PipelineDataHolder) iter.next();
	    PipelineData pd = pdh.getPlineData();
	    if (pd != null
		    && !DataHolderStatus.COMPLETED.equals(pdh.getStatus())) {
		HttpServletRequest request = (HttpServletRequest) pdh
			.getPlineData().getAttribute(
				PipelineDataImpl.HTTP_REQUEST_PARAMNAME);
		HttpServletResponse response = (HttpServletResponse) pdh
			.getPlineData().getAttribute(
				PipelineDataImpl.HTTP_RESPONSE_PARAMNAME);
		if (request != null && response != null) {
		    try {
			pdh.setStatusWorking();
			process(request, response, pdh);
		    } catch (Exception ex) {
			cat.error(ex);
		    }
		}
	    }
	}
    }

    public final boolean isGuiHandler() {
	return true;
    }

    protected abstract void process(HttpServletRequest p_obj_request,
	    HttpServletResponse p_obj_response,
	    PipelineDataHolder p_obj_dataHolder);
}
