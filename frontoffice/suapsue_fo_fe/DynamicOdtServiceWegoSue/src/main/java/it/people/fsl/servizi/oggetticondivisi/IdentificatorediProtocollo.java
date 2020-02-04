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

import it.people.fsl.servizi.oggetticondivisi.tipibase.*;

public class IdentificatorediProtocollo extends Identificatore
{

    private String numerodiRegistrazione;

    private java.lang.String codiceAmministrazione;

    private Data datadiRegistrazione;

    private java.lang.String codiceAOO;

	/**
	 * @return Returns the codiceAmministrazione.
	 */
	public java.lang.String getCodiceAmministrazione() {
		return codiceAmministrazione;
	}
	/**
	 * @param codiceAmministrazione The codiceAmministrazione to set.
	 */
	public void setCodiceAmministrazione(java.lang.String codiceAmministrazione) {
		this.codiceAmministrazione = codiceAmministrazione;
	}
	/**
	 * @return Returns the codiceAOO.
	 */
	public java.lang.String getCodiceAOO() {
		return codiceAOO;
	}
	/**
	 * @param codiceAOO The codiceAOO to set.
	 */
	public void setCodiceAOO(java.lang.String codiceAOO) {
		this.codiceAOO = codiceAOO;
	}
	/**
	 * @return Returns the datadiRegistrazione.
	 */
	public Data getDatadiRegistrazione() {
		return datadiRegistrazione;
	}
	/**
	 * @param datadiRegistrazione The datadiRegistrazione to set.
	 */
	public void setDatadiRegistrazione(Data datadiRegistrazione) {
		this.datadiRegistrazione = datadiRegistrazione;
	}
	/**
	 * @return Returns the numerodiRegistrazione.
	 */
	public String getNumerodiRegistrazione() {
		return numerodiRegistrazione;
	}
	/**
	 * @param numerodiRegistrazione The numerodiRegistrazione to set.
	 */
	public void setNumerodiRegistrazione(String numerodiRegistrazione) {
		this.numerodiRegistrazione = numerodiRegistrazione;
	}
}
