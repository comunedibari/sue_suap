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

import it.people.fsl.servizi.oggetticondivisi.tipibase.Data;
import it.people.process.common.entity.AbstractEntity;

public class IscrizioneallaCameradiCommercio extends AbstractEntity {

    /**
     * 
     * @return possible object is {@link java.util.Calendar}
     */
    private Data datadiIscrizione;

    /**
     * 
     * @return possible object is {@link java.lang.String}
     */
    private java.lang.String numerodiIscrizione;

    /**
     * Gets the value of the CategoriadiIscrizione property.
     * 
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the CategoriadiIscrizione property.
     * 
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getCategoriadiIscrizione().add(newItem);
     * </pre>
     * 
     * 
     * Objects of the following type(s) are allowed in the list
     * {@link java.lang.String}
     * 
     */
    private java.util.List categoriadiIscrizione;

    /**
     * 
     * @return possible object is
     *         {@link it.diviana.egov.oggetticondivisiegovpersonagiuridica.CameradiCommercio}
     */
    private CameradiCommercio cameradiCommercio;

    /**
     * @return
     */
    public CameradiCommercio getCameradiCommercio() {
	return cameradiCommercio;
    }

    /**
     * @return
     */
    public java.util.List getCategoriadiIscrizione() {
	return categoriadiIscrizione;
    }

    /**
     * @return
     */
    public Data getDatadiIscrizione() {
	return datadiIscrizione;
    }

    /**
     * @return
     */
    public java.lang.String getNumerodiIscrizione() {
	return numerodiIscrizione;
    }

    /**
     * @param commercio
     */
    public void setCameradiCommercio(CameradiCommercio commercio) {
	cameradiCommercio = commercio;
    }

    /**
     * @param list
     */
    public void setCategoriadiIscrizione(java.util.List list) {
	categoriadiIscrizione = list;
    }

    /**
     * @param calendar
     */
    public void setDatadiIscrizione(Data data) {
	datadiIscrizione = data;
    }

    /**
     * @param string
     */
    public void setNumerodiIscrizione(java.lang.String string) {
	numerodiIscrizione = string;
    }

}
