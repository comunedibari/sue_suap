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
package it.people.console.dto;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import it.people.console.domain.BaseBean;

/**
 * 
 * @author Andrea Piemontese - Engineering Ingegneria Informatica
 * @version 1.0
 * @created 25-ott-2012
 */

@JsonIgnoreProperties({ "id", "serialVersionUID", "pagedListHolders"})
public class UnavailableBEServiceDTO extends BaseBean {

	@JsonIgnore
	private static final long serialVersionUID = 2491124146860470098L;

	private String logicalServiceName;
	
	private String backEndURL;
	
	private List <String> affectedNodes = new ArrayList<String>();

	@JsonIgnore
	public UnavailableBEServiceDTO() {
		super();
	}

	/**
	 * @return the logicalServiceName
	 */
	public String getLogicalServiceName() {
		return logicalServiceName;
	}

	/**
	 * @param logicalServiceName the logicalServiceName to set
	 */
	public void setLogicalServiceName(String logicalServiceName) {
		this.logicalServiceName = logicalServiceName;
	}

	/**
	 * @return the backEndURL
	 */
	public String getBackEndURL() {
		return backEndURL;
	}

	/**
	 * @param backEndURL the backEndURL to set
	 */
	public void setBackEndURL(String backEndURL) {
		this.backEndURL = backEndURL;
	}

	/**
	 * @return the affectedNodes
	 */
	public List<String> getAffectedNodes() {
		return affectedNodes;
	}

	/**
	 * @param affectedNodes the affectedNodes to set
	 */
	public void setAffectedNodes(List<String> affectedNodes) {
		this.affectedNodes = affectedNodes;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@JsonIgnore
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((affectedNodes == null) ? 0 : affectedNodes.hashCode());
		result = prime * result + ((backEndURL == null) ? 0 : backEndURL.hashCode());
		result = prime * result + ((logicalServiceName == null) ? 0 : logicalServiceName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@JsonIgnore
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UnavailableBEServiceDTO other = (UnavailableBEServiceDTO) obj;
		if (affectedNodes == null) {
			if (other.affectedNodes != null)
				return false;
		} else if (!affectedNodes.equals(other.affectedNodes))
			return false;
		if (backEndURL == null) {
			if (other.backEndURL != null)
				return false;
		} else if (!backEndURL.equals(other.backEndURL))
			return false;
		if (logicalServiceName == null) {
			if (other.logicalServiceName != null)
				return false;
		} else if (!logicalServiceName.equals(other.logicalServiceName))
			return false;
		return true;
	}
	

}
