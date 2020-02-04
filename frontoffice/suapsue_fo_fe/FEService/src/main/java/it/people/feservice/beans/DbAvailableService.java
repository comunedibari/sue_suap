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

/**
 * @author Riccardo Foraf� - Engineering Ingegneria Informatica S.p.A. - Sede di Genova
 *
 */
public class DbAvailableService implements Serializable {

	private static final long serialVersionUID = 2751335245134775044L;

	private int id;
	
	private String name;
	
	private String activity;
	
	private String subActivity;
		
	public DbAvailableService() {
		this.setId(0);
		this.setName("");
		this.setActivity("");
		this.setSubActivity("");
	}

	public DbAvailableService(final int id, final String name, final String activity, 
			final String subActivity) {
		this.setId(id);
		this.setName(name);
		this.setActivity(activity);
		this.setSubActivity(subActivity);
	}
	
	public final int getId() {
		return id;
	}

	public final void setId(int id) {
		this.id = id;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getActivity() {
		return activity;
	}

	public final void setActivity(String activity) {
		this.activity = activity;
	}

	public final String getSubActivity() {
		return subActivity;
	}

	public final void setSubActivity(String subActivity) {
		this.subActivity = subActivity;
	}
	
}
