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
package it.people.console.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 02/feb/2011 22.42.38
 *
 */
public class ExtendedAvailableService extends FEService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4342071641517728046L;

	private List<ConfigParameter> configParameters;
	
	private List<ExtendedDependentModule> dependentModules;
	
	/**
	 * 
	 */
	public ExtendedAvailableService() {
		this.setConfigParameters(new ArrayList<ConfigParameter>());
		this.setDependentModules(new ArrayList<ExtendedDependentModule>());
	}

	/**
	 * @return the configParameters
	 */
	public final List<ConfigParameter> getConfigParameters() {
		return configParameters;
	}

	/**
	 * @param configParameters the configParameters to set
	 */
	public final void setConfigParameters(List<ConfigParameter> configParameters) {
		this.configParameters = configParameters;
	}

	/**
	 * @param configParameters
	 */
	public final void addConfigParameters(ConfigParameter configParameters) {
		if (this.getConfigParameters() == null) {
			this.setConfigParameters(new ArrayList<ConfigParameter>());
		}
		this.getConfigParameters().add(configParameters);
	}
	
	/**
	 * @return the dependentModules
	 */
	public final List<ExtendedDependentModule> getDependentModules() {
		return dependentModules;
	}

	/**
	 * @param dependentModules the dependentModules to set
	 */
	public final void setDependentModules(
			List<ExtendedDependentModule> dependentModules) {
		this.dependentModules = dependentModules;
	}

	/**
	 * @param dependentModules
	 */
	public final void addDependentModules(ExtendedDependentModule dependentModules) {
		if (this.getDependentModules() == null) {
			this.setDependentModules(new ArrayList<ExtendedDependentModule>());
		}
		this.getDependentModules().add(dependentModules);
	}

	/* (non-Javadoc)
	 * @see it.people.console.domain.FEService#clear()
	 */
	@Override
	public void clear() {
		this.setNodeId(null);
		this.set_package(null);
		this.setArea(null);
		this.setAttachmentsInCitizenReceipt(null);
		this.setConfigParameters(null);
		this.setDependentModules(null);
		this.setId(null);
		this.setLogLevel(null);
		this.setMunicipality(null);
		this.setNodeName(null);
		this.setProcess(null);
		this.setProcessSignEnabled(null);
		this.setServiceName(null);
		this.setServiceStatus(null);
		this.setSubArea(null);
	}
	
}
