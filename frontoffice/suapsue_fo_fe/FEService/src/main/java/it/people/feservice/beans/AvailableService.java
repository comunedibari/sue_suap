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
public class AvailableService implements Serializable {

	private static final long serialVersionUID = 6147572363658031683L;

	private long serviceId;
	
	private String serviceName;
	
	private String activity;
	
	private String subActivity;
	
	private String serviceConfigFilePath;
	
	private String _package;
	
	private boolean alreadyRegistered;
	
	public AvailableService() {
		this.setServiceName("");
		this.setActivity("");
		this.setSubActivity("");
		this.setServiceConfigFilePath("");
		this.set_package("");
		this.setAlreadyRegistered(false);
	}

	public AvailableService(final String serviceName, final String activity, 
			final String subActivity, final String serviceConfigFilePath) {
		this.setServiceName(serviceName);
		this.setActivity(activity);
		this.setSubActivity(subActivity);
		this.setServiceConfigFilePath(serviceConfigFilePath);
		this.set_package("");
		this.setAlreadyRegistered(false);
	}
	
	/**
	 * @return the serviceId
	 */
	public final long getServiceId() {
		return serviceId;
	}

	/**
	 * @param serviceId the serviceId to set
	 */
	public final void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public final String getServiceName() {
		return serviceName;
	}

	public final void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public final String getServiceConfigFilePath() {
		return serviceConfigFilePath;
	}

	public final void setServiceConfigFilePath(String serviceConfigFilePath) {
		this.serviceConfigFilePath = serviceConfigFilePath;
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

	/**
	 * @return the alreadyRegistered
	 */
	public final boolean isAlreadyRegistered() {
		return alreadyRegistered;
	}

	/**
	 * @param alreadyRegistered the alreadyRegistered to set
	 */
	public final void setAlreadyRegistered(boolean alreadyRegistered) {
		this.alreadyRegistered = alreadyRegistered;
	}

	/**
	 * @return the _package
	 */
	public final String get_package() {
		return _package;
	}

	/**
	 * @param package1 the _package to set
	 */
	public final void set_package(String _package) {
		this._package = _package;
	}
	
}
