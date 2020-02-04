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

public class Provincia extends AbstractEntity {

    /**
     * 
     * @return possible object is {@link java.math.BigInteger}
     */
    private int codicediProvincia;

    /**
     * 
     * @return possible object is {@link java.lang.String}
     */
    private java.lang.String sigladiProvinciaEstesa;

    /**
     * 
     * @return possible object is {@link java.lang.String}
     */
    private java.lang.String sigladiProvinciaISTAT;

    /**
     * Gets the value of the Comune property.
     * 
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the Comune property.
     * 
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getComune().add(newItem);
     * </pre>
     * 
     * 
     * Objects of the following type(s) are allowed in the list
     * {@link it.diviana.egov.oggetticondivisiegovluogo.ComuneComune}
     * 
     */
    private java.util.List comune;

    /**
     * 
     * @return possible object is {@link java.lang.String}
     */
    private java.lang.String nome;

    /**
     * @return
     */
    public int getCodicediProvincia() {
	return codicediProvincia;
    }

    /**
     * @return
     */
    public java.util.List getComune() {
	return comune;
    }

    /**
     * @return
     */
    public java.lang.String getNome() {
	return nome;
    }

    /**
     * @return
     */
    public java.lang.String getSigladiProvinciaEstesa() {
	return sigladiProvinciaEstesa;
    }

    /**
     * @return
     */
    public java.lang.String getSigladiProvinciaISTAT() {
	return sigladiProvinciaISTAT;
    }

    /**
     * @param i
     */
    public void setCodicediProvincia(int i) {
	codicediProvincia = i;
    }

    /**
     * @param list
     */
    public void setComune(java.util.List list) {
	comune = list;
    }

    public void addComune(Comune c) {
	comune.add(c);
    }

    /**
     * @param string
     */
    public void setNome(java.lang.String string) {
	nome = string;
    }

    /**
     * @param string
     */
    public void setSigladiProvinciaEstesa(java.lang.String string) {
	sigladiProvinciaEstesa = string;
    }

    /**
     * @param string
     */
    public void setSigladiProvinciaISTAT(java.lang.String string) {
	sigladiProvinciaISTAT = string;
    }

}
