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

import java.io.Serializable;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 02/feb/2011 22.46.46
 *
 */
public class ExtendedDependentModule implements Clearable, Serializable  {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6433413453966103801L;

	private String valore;
    
    private Integer attachXmlInMail;
    
    private String name;
    
    private String mailAddress;
	
    private Integer dumpActive;
    
    private BEService beService;
    
    public ExtendedDependentModule() {
    	this.setBeService(new BEService());
    }

	/**
	 * @return the valore
	 */
	public final String getValore() {
		return valore;
	}

	/**
	 * @param valore the valore to set
	 */
	public final void setValore(String valore) {
		this.valore = valore;
	}

	/**
	 * @return the attachXmlInMail
	 */
	public final Integer getAttachXmlInMail() {
		return attachXmlInMail;
	}

	/**
	 * @param attachXmlInMail the attachXmlInMail to set
	 */
	public final void setAttachXmlInMail(Integer attachXmlInMail) {
		this.attachXmlInMail = attachXmlInMail;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the mailAddress
	 */
	public final String getMailAddress() {
		return mailAddress;
	}

	/**
	 * @param mailAddress the mailAddress to set
	 */
	public final void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * @return the dumpActive
	 */
	public final Integer getDumpActive() {
		return dumpActive;
	}

	/**
	 * @param dumpActive the dumpActive to set
	 */
	public final void setDumpActive(Integer dumpActive) {
		this.dumpActive = dumpActive;
	}

	/**
	 * @return the beService
	 */
	public final BEService getBeService() {
		return beService;
	}

	/**
	 * @param beService the beService to set
	 */
	public final void setBeService(BEService beService) {
		this.beService = beService;
	}

	/* (non-Javadoc)
	 * @see it.people.console.domain.Clearable#clear()
	 */
	public void clear() {
		this.setAttachXmlInMail(null);
		this.setBeService(null);
		this.setDumpActive(null);
		this.setMailAddress(null);
		this.setName(null);
		this.setValore(null);
	}
    
}
