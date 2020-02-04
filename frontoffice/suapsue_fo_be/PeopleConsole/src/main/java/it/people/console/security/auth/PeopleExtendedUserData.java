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
package it.people.console.security.auth;

import it.people.core.PplUserData;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created Feb 7, 2013 9:22:48 AM
 *
 */
public class PeopleExtendedUserData extends PplUserData {

	private static final long serialVersionUID = 1285396286137313014L;

	private PplUserAllowedData nodesSecuritySettings;

	private PplUserAllowedData feServicesSecuritySettings;

	private PplUserAllowedData beServicesSecuritySettings;
	
	public PeopleExtendedUserData(final PplUserData pplUserData, final PplUserAllowedData nodesSecuritySettings, 
			final PplUserAllowedData feServicesSecuritySettings, final PplUserAllowedData beServicesSecuritySettings) {
		
		this.setCapDomicilio(pplUserData.getCapDomicilio());
		this.setCapResidenza(pplUserData.getCapResidenza());
		this.setCittaDomicilio(pplUserData.getCittaDomicilio());
		this.setCittaResidenza(pplUserData.getCittaResidenza());
		this.setCodiceFiscale(pplUserData.getCodiceFiscale());
		this.setCognome(pplUserData.getCognome());
		this.setDataNascita(pplUserData.getDataNascita());
		this.setEmailaddress(pplUserData.getEmailaddress());
		this.setIndirizzoDomicilio(pplUserData.getIndirizzoDomicilio());
		this.setIndirizzoResidenza(pplUserData.getIndirizzoResidenza());
		this.setLavoro(pplUserData.getLavoro());
		this.setLuogoNascita(pplUserData.getLuogoNascita());
		this.setNome(pplUserData.getNome());
		this.setProvinciaDomicilio(pplUserData.getProvinciaDomicilio());
		this.setProvinciaNascita(pplUserData.getProvinciaNascita());
		this.setProvinciaResidenza(pplUserData.getProvinciaResidenza());
		this.setRuolo(pplUserData.getRuolo());
		this.setSesso(pplUserData.getSesso());
		this.setStatoDomicilio(pplUserData.getStatoDomicilio());
		this.setStatoResidenza(pplUserData.getStatoResidenza());
		this.setTelefono(pplUserData.getTelefono());
		this.setTerritorio(pplUserData.getTerritorio());
		this.setTitolo(pplUserData.getTitolo());
		this.setUserPassword(pplUserData.getUserPassword());
		this.setUserPIN(pplUserData.getUserPIN());
		
		this.setNodesSecuritySettings(nodesSecuritySettings);
		this.setFeServicesSecuritySettings(feServicesSecuritySettings);
		this.setBeServicesSecuritySettings(beServicesSecuritySettings);
		
	}

	private void setNodesSecuritySettings(PplUserAllowedData nodesSecuritySettings) {
		this.nodesSecuritySettings = nodesSecuritySettings;
	}

	private void setFeServicesSecuritySettings(
			PplUserAllowedData feServicesSecuritySettings) {
		this.feServicesSecuritySettings = feServicesSecuritySettings;
	}

	private void setBeServicesSecuritySettings(
			PplUserAllowedData beServicesSecuritySettings) {
		this.beServicesSecuritySettings = beServicesSecuritySettings;
	}

	public final PplUserAllowedData getNodesSecuritySettings() {
		return nodesSecuritySettings;
	}

	public final PplUserAllowedData getFeServicesSecuritySettings() {
		return feServicesSecuritySettings;
	}

	public final PplUserAllowedData getBeServicesSecuritySettings() {
		return beServicesSecuritySettings;
	}

}
