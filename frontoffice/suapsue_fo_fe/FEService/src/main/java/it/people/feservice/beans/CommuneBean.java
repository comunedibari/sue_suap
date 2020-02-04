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

/**
 * @author Riccardo Foraf� - Engineering Ingegneria Informatica S.p.A. - Sede di Genova
 *
 */
public class CommuneBean implements java.io.Serializable {
	
	private static final long serialVersionUID = 6572900211087803728L;

	private String communeId;
	
	private String communeName;

	private String communeLabel;
	
	private String communeProv;

	private String communeAOOPrefix;

	public CommuneBean() {
		this.setCommuneId("");
		this.setCommuneName("");
		this.setCommuneLabel("");
		this.setCommuneProv("");
		this.setCommuneAOOPrefix("");
	}

	public CommuneBean(final String communeId, final String communeName) {
		super();
		this.setCommuneId(communeId);
		this.setCommuneName(communeName);
	}

	public CommuneBean(final String communeId, final String communeName, final String communeLabel, 
			final String communeProv, final String communeAOOPrefix) {
		this.setCommuneId(communeId);
		this.setCommuneName(communeName);
		this.setCommuneLabel(communeLabel);
		this.setCommuneProv(communeProv);
		this.setCommuneAOOPrefix(communeAOOPrefix);
	}
	
	public final String getCommuneId() {
		return communeId;
	}

	public final void setCommuneId(String communeId) {
		this.communeId = communeId;
	}

	public final String getCommuneName() {
		return communeName;
	}

	public final void setCommuneName(String communeName) {
		this.communeName = communeName;
	}

	public final String getCommuneLabel() {
		return communeLabel;
	}

	public final void setCommuneLabel(String communeLabel) {
		this.communeLabel = communeLabel;
	}

	public final String getCommuneProv() {
		return communeProv;
	}

	public final void setCommuneProv(String communeProv) {
		this.communeProv = communeProv;
	}

	public final String getCommuneAOOPrefix() {
		return communeAOOPrefix;
	}

	public final void setCommuneAOOPrefix(String communeAOOPrefix) {
		this.communeAOOPrefix = communeAOOPrefix;
	}
	
}
