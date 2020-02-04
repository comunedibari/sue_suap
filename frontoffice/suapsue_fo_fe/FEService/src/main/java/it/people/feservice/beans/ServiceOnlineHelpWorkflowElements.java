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
package it.people.feservice.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 * 01/set/2011 22.30.13
 */
public class ServiceOnlineHelpWorkflowElements implements Serializable {

	private OnlineHelpViewData[] views;
	
	public ServiceOnlineHelpWorkflowElements() {
	}

	/**
	 * @param views the views to set
	 */
	public final void setViews(OnlineHelpViewData[] views) {
		this.views = views;
	}

	/**
	 * @return the views
	 */
	public final OnlineHelpViewData[] getViews() {
		return this.views;
	}
	
}
