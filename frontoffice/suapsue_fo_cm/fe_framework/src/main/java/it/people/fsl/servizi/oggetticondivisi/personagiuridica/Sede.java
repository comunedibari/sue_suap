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
package it.people.fsl.servizi.oggetticondivisi.personagiuridica;

import it.people.fsl.servizi.oggetticondivisi.luogo.CasellaPostale;
import it.people.fsl.servizi.oggetticondivisi.luogo.Indirizzo;
import it.people.process.common.entity.AbstractEntity;

public class Sede extends AbstractEntity {

    /**
     * 
     * @return possible object is {@link java.lang.String}
     */
    private java.lang.String indirizzoTestuale;

    /**
     * 
     * @return possible object is
     *         {@link it.diviana.egov.oggetticondivisiegovluogo.IndirizzoStrutturato}
     */
    private Indirizzo indirizzoStrutturato;

    /**
     * Gets the value of the Recapito property.
     * 
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the Recapito property.
     * 
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getRecapito().add(newItem);
     * </pre>
     * 
     * 
     * Objects of the following type(s) are allowed in the list
     * {@link it.diviana.egov.oggetticondivisiegovrecapito.WebWeb}
     * {@link it.diviana.egov.oggetticondivisiegovrecapito.RecapitoPostaleRecapitoPostale}
     * {@link it.diviana.egov.oggetticondivisiegovrecapito.FaxFax}
     * {@link it.diviana.egov.oggetticondivisiegovrecapito.InternetInternet}
     * {@link it.diviana.egov.oggetticondivisiegovrecapito.RecapitoRecapito}
     * {@link it.diviana.egov.oggetticondivisiegovrecapito.TelefonoTelefono}
     * {@link it.diviana.egov.oggetticondivisiegovrecapito.CellulareCellulare}
     * {@link it.diviana.egov.oggetticondivisiegovrecapito.EmailEmail}
     * 
     */
    private java.util.List recapito;

    /**
     * 
     * @return possible object is
     *         {@link it.diviana.egov.oggetticondivisiegovluogo.CasellaPostale}
     */
    private CasellaPostale casellaPostale;

    /**
     * @return
     */
    public CasellaPostale getCasellaPostale() {
	return casellaPostale;
    }

    /**
     * @return
     */
    public Indirizzo getIndirizzoStrutturato() {
	return indirizzoStrutturato;
    }

    /**
     * @return
     */
    public java.lang.String getIndirizzoTestuale() {
	return indirizzoTestuale;
    }

    /**
     * @return
     */
    public java.util.List getRecapito() {
	return recapito;
    }

    /**
     * @param postale
     */
    public void setCasellaPostale(CasellaPostale postale) {
	casellaPostale = postale;
    }

    /**
     * @param indirizzo
     */
    public void setIndirizzoStrutturato(Indirizzo indirizzo) {
	indirizzoStrutturato = indirizzo;
    }

    /**
     * @param string
     */
    public void setIndirizzoTestuale(java.lang.String string) {
	indirizzoTestuale = string;
    }

    /**
     * @param list
     */
    public void setRecapito(java.util.List list) {
	recapito = list;
    }

}
