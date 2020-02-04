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
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.fsl.servizi.oggetticondivisi;


/**
 * E' una risposta sincrona restituita da un sistema applicativo di Back End verso il VSL o dal VSL verso un'applicazione di Front End, in replica ad una domanda.
 * Java content class for Risposta complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/DATI/engineering/People/xsd/work/provvedimentiICI/OggettiCondivisiBase.xsd line 5)
 * <p>
 * <pre>
 * &lt;complexType name="Risposta">
 *   &lt;complexContent>
 *     &lt;extension base="{http://egov.diviana.it/OggettiCondivisiBase}Documento">
 *       &lt;sequence>
 *         &lt;element name="DocumentoUfficiale" type="{http://egov.diviana.it/OggettiCondivisiBaseDU}DocumentoUfficiale" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Richiesta" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface Risposta
{


    /**
     * 
     * @return
     *     possible object is
     *     {@link java.lang.Object}
     */
    java.lang.Object getRichiesta();

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.Object}
     */
    void setRichiesta(java.lang.Object value);

    /**
     * Gets the value of the DocumentoUfficiale property.
     * 
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the DocumentoUfficiale property.
     * 
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocumentoUfficiale().add(newItem);
     * </pre>
     * 
     * 
     * Objects of the following type(s) are allowed in the list
     * {@link it.diviana.egov.oggetticondivisibasedu.DocumentoUfficiale}
     * 
     */
    java.util.List getDocumentoUfficiale();

}
