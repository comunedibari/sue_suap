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
package it.people.fsl.servizi.oggetticondivisi.luogo;

import it.people.process.common.entity.AbstractEntity;

public class CasellaPostale extends AbstractEntity {

    /**
     * 
     * @return possible object is
     *         {@link it.diviana.egov.oggetticondivisiegovluogo.UfficioPostale}
     */
    private UfficioPostale ufficioPostale;

    /**
     * 
     * @return possible object is {@link java.lang.String}
     */
    private String identificativo;

    /**
     * @return
     */
    public java.lang.String getIdentificativo() {
	return identificativo;
    }

    /**
     * @return
     */
    public UfficioPostale getUfficioPostale() {
	return ufficioPostale;
    }

    /**
     * @param string
     */
    public void setIdentificativo(java.lang.String string) {
	identificativo = string;
    }

    /**
     * @param postale
     */
    public void setUfficioPostale(UfficioPostale postale) {
	ufficioPostale = postale;
    }

}
