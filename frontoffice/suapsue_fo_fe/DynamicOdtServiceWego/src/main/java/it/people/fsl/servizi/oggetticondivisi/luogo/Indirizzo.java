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
package it.people.fsl.servizi.oggetticondivisi.luogo;

import it.people.process.common.entity.AbstractEntity;

/**
*
* To change the template for this generated type comment go to
* Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
* @author locastro
*/
public class Indirizzo extends AbstractEntity {

	public Indirizzo(){
		this.luogo = new Luogo();
	}
	/**
	 * 
	 * @return
	 *     possible object is
	 *     {@link java.math.BigInteger}
	 */
	private String CAP;


	/**
	 * 
	 * @return
	 *     possible object is
	 *     {@link it.diviana.egov.oggetticondivisiegovluogo.Toponimo}
	 */
	private String toponimo;


	/**
	 * 
	 * @return
	 *     possible object is
	 *     {@link it.diviana.egov.oggetticondivisiegovluogo.NumeroCivico}
	 */
	private String numeroCivico;

	private String esponenteCivico;
    
	private String coloreCivico;
	
	private String scala;
	
	private String interno;

	private String esponenteInterno;
	
	private int  piano;
	
	private Luogo luogo;
	
	String via;

	String codiceVia;

	/**
	 * @return
	 */
	public String getCAP() {
		return CAP;
	}

	/**
	 * @return
	 */
	public String getCodiceVia() {
		return codiceVia;
	}

	/**
	 * @return
	 */
	public String getColoreCivico() {
		return coloreCivico;
	}

	/**
	 * @return
	 */
	public String getEsponenteCivico() {
		return esponenteCivico;
	}

	/**
	 * @return
	 */
	public String getEsponenteInterno() {
		return esponenteInterno;
	}

	/**
	 * @return
	 */
	public String getInterno() {
		return interno;
	}

	/**
	 * @return
	 */
	public Luogo getLuogo() {
		return luogo;
	}

	/**
	 * @return
	 */
	public String getNumeroCivico() {
		return numeroCivico;
	}

	/**
	 * @return
	 */
	public int getPiano() {
		return piano;
	}

	/**
	 * @return
	 */
	public String getScala() {
		return scala;
	}

	/**
	 * @return
	 */
	public String getToponimo() {
		return toponimo;
	}

	/**
	 * @return
	 */
	public String getVia() {
		return via;
	}

	/**
	 * @param string
	 */
	public void setCAP(String string) {
		CAP = string;
	}

	/**
	 * @param string
	 */
	public void setCodiceVia(String string) {
		codiceVia = string;
	}

	/**
	 * @param string
	 */
	public void setColoreCivico(String string) {
		coloreCivico = string;
	}

	/**
	 * @param string
	 */
	public void setEsponenteCivico(String string) {
		esponenteCivico = string;
	}

	/**
	 * @param string
	 */
	public void setEsponenteInterno(String string) {
		esponenteInterno = string;
	}

	/**
	 * @param string
	 */
	public void setInterno(String string) {
		interno = string;
	}

	/**
	 * @param luogo
	 */
	public void setLuogo(Luogo luogo) {
		this.luogo = luogo;
	}

	/**
	 * @param string
	 */
	public void setNumeroCivico(String string) {
		numeroCivico = string;
	}

	/**
	 * @param i
	 */
	public void setPiano(int i) {
		piano = i;
	}

	/**
	 * @param string
	 */
	public void setScala(String string) {
		scala = string;
	}

	/**
	 * @param string
	 */
	public void setToponimo(String string) {
		toponimo = string;
	}

	/**
	 * @param string
	 */
	public void setVia(String string) {
		via = string;
	}

}
