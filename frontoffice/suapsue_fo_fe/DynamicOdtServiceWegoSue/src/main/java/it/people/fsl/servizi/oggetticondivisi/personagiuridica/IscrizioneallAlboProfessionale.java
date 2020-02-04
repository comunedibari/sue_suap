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
package it.people.fsl.servizi.oggetticondivisi.personagiuridica;

import it.people.fsl.servizi.oggetticondivisi.luogo.Provincia;
import it.people.fsl.servizi.oggetticondivisi.tipibase.Data;
import it.people.process.common.entity.AbstractEntity;

public class IscrizioneallAlboProfessionale extends AbstractEntity {

	/**
	*
	* @return
	*     possible object is
	*     {@link it.diviana.egov.oggetticondivisiegovpersonagiuridica.AlboProfessionale}
	*/
	private AlboProfessionale alboProfessionale;

	
	/**
	*
	* @return
	*     possible object is
	*     {@link java.util.Calendar}
	*/
	private Data data;


	/**
	*
	* @return
	*     possible object is
	*     {@link java.lang.String}
	*/
	private java.lang.String numero;

	
	/**
	*
	* @return
	*     possible object is
	*     {@link it.diviana.egov.oggetticondivisiegovluogo.Provincia}
	*/
	private Provincia provincia;


	/**
	* Gets the value of the Categoria property.
	*
	* This accessor method returns a reference to the live list,
	* not a snapshot. Therefore any modification you make to the
	* returned list will be present inside the JAXB object.
	* This is why there is not a <CODE>set</CODE> method for the Categoria property.
	*
	* For example, to add a new item, do as follows:
	* <pre>
	*    getCategoria().add(newItem);
	* </pre>
	*
	*
	* Objects of the following type(s) are allowed in the list
	* {@link java.lang.String}
	*
	*/
	private java.util.List categoria;

	/**
	 * @return
	 */
	public AlboProfessionale getAlboProfessionale() {
		return alboProfessionale;
	}

	/**
	 * @return
	 */
	public java.util.List getCategoria() {
		return categoria;
	}

	/**
	 * @return
	 */
	public Data getData() {
		return data;
	}

	/**
	 * @return
	 */
	public java.lang.String getNumero() {
		return numero;
	}

	/**
	 * @return
	 */
	public Provincia getProvincia() {
		return provincia;
	}

	/**
	 * @param professionale
	 */
	public void setAlboProfessionale(AlboProfessionale professionale) {
		alboProfessionale = professionale;
	}

	/**
	 * @param list
	 */
	public void setCategoria(java.util.List list) {
		categoria = list;
	}

	/**
	 * @param calendar
	 */
	public void setData(Data value) {
		data = value;
	}

	/**
	 * @param string
	 */
	public void setNumero(java.lang.String string) {
		numero = string;
	}

	/**
	 * @param provincia
	 */
	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

}
