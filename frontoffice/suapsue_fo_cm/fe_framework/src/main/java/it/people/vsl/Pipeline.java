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

import it.people.core.PeopleContext;
import it.people.core.persistence.exception.peopleException;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Sep 11, 2003 Time: 2:34:17 PM To
 * change this template use Options | File Templates.
 */
public interface Pipeline extends Runnable {

    public void savePipeline(); // persistenza dei dati che la pipeline sta
				// elaborando

    public void loadPipeline(); // serve per caricare la pipeline dei dati da
				// elaborare (in fase di start)

    public void addHandler(PipelineHandler p_PLHandler);

    public void removeHandler(int p_position);

    // public void isEmpty();
    public void process(HttpServletRequest p_obj_request,
	    HttpServletResponse p_obj_response, String key)
	    throws peopleException;

    public String put(PeopleContext context, PipelineData p_PLData);

    public int getQueueLength();

    public ArrayList getQueue();

    public PipelineHandler[] getHandlers();

    public Collection getQueueItem(int handlerIndex);

    public int getPipelineLength();

    public String getProcessName();

    public void setProcessName(String processName);

    public void write();
    // public boolean hasHandlers();
}
