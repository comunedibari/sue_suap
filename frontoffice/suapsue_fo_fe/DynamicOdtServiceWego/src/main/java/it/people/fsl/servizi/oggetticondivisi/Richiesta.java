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
 * @author locastro
 *
 */
public interface Richiesta {
	/**
	 * @return Returns the identificatorediProtocollo.
	 */
	public IdentificatorediProtocollo getIdentificatorediProtocollo();

	/**
	 * @param identificatorediProtocollo The identificatorediProtocollo to set.
	 */
	public void setIdentificatorediProtocollo(
			IdentificatorediProtocollo identificatorediProtocollo);

	/**
	 * @return Returns the identificatorePeople.
	 */
	public IdentificatorePeople getIdentificatorePeople();

	/**
	 * @param identificatorePeople The identificatorePeople to set.
	 */
	public void setIdentificatorePeople(
			IdentificatorePeople identificatorePeople);

	/**
	 * @return Returns the richiedente.
	 */
	public Richiedente getRichiedente();

	/**
	 * @param richiedente The richiedente to set.
	 */
	public void setRichiedente(Richiedente richiedente);

	/**
	 * @return Returns the titolare.
	 */
	public Titolare getTitolare();

	/**
	 * @param titolare The titolare to set.
	 */
	public void setTitolare(Titolare titolare);
}
