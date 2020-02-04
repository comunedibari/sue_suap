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

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Oct 8, 2003 Time: 2:17:12 PM To
 * change this template use Options | File Templates.
 */
public class UnsignedPrintHandler extends PipelineGUIHandlerImpl {

    public String getName() {
	return "Presa in carico dei documenti";
    }

    protected void process(HttpServletRequest p_obj_request,
	    HttpServletResponse p_obj_response,
	    PipelineDataHolder p_obj_dataHolder) {
	PipelineData pd = p_obj_dataHolder.getPlineData();

	String peopleProtocollID = Long.toString((new Date()).getTime());
	p_obj_dataHolder.getPlineData().setAttribute(
		PipelineDataImpl.PEOPLE_PROTOCOLL_ID_PARAMNAME,
		peopleProtocollID);
	p_obj_request.setAttribute("PeopleProtocollID", peopleProtocollID);

	p_obj_dataHolder.setStatusCompleted();
	p_obj_request.setAttribute("redirectPage", "completed");
    }
}
