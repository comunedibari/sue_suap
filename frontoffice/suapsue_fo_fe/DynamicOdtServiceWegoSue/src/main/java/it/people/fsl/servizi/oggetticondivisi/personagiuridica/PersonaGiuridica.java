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

import it.people.fsl.servizi.oggetticondivisi.Persona;
import it.people.fsl.servizi.oggetticondivisi.PersonaFisica;

public class PersonaGiuridica extends Persona {
	
    public String getPartitaIVA(){ return partitaIVA; }

    public void setPartitaIVA(String partitaIva){ this.partitaIVA = partitaIva; }

    public String getRagioneSociale(){ return ragioneSociale; }

    public void setRagioneSociale(String ragioneSociale){ this.ragioneSociale = ragioneSociale; }

	private String tipoPersonaGiuridica;

	private IscrizioneallAlboProfessionale iscrizioneallAlboProfessionale;

	private IscrizioneallaCameradiCommercio iscrizioneallaCameradiCommercio;

	private String codiceAmministrazione;

	private String codiceFiscale;

	private Sede sedeOperativa;

	private Sede sedeLegale;

	private String denominazione;
	private PersonaFisica rappresentanteLegale;
	private PersonaFisica procuratore;
	private PersonaFisica rappresentante;

    private String partitaIVA;
    private String ragioneSociale;
	/**
	 * @return
	 */
	public String getDenominazione() {
		return denominazione;
	}

	/**
	 * @param i
	 */
	public void setDenominazione(String i) {
		denominazione = i;
	}

	/**
	 * @return
	 */
	public Sede getSedeLegale() {
		return sedeLegale;
	}

	/**
	 * @param i
	 */
	public void setSedeLegale(Sede i) {
		sedeLegale = i;
	}

	/**
	 * @return
	 */
	public Sede getSedeOperativa() {
		return sedeOperativa;
	}

	/**
	 * @param i
	 */
	public void setSedeOperativa(Sede i) {
		sedeOperativa = i;
	}

	/**
	 * @return
	 */
	public PersonaFisica getRappresentanteLegale() {
		return rappresentanteLegale;
	}

	/**
	 * @param i
	 */
	public void setRappresentanteLegale(PersonaFisica i) {
		rappresentanteLegale = i;
	}

	/**
	 * @return
	 */
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	/**
	 * @param i
	 */
	public void setCodiceFiscale(String i) {
		codiceFiscale = i;
	}

	/**
	 * @return
	 */
	public String getCodiceAmministrazione() {
		return codiceAmministrazione;
	}

	/**
	 * @param i
	 */
	public void setCodiceAmministrazione(String i) {
		codiceAmministrazione = i;
	}

	/**
	 * @return
	 */
	public IscrizioneallaCameradiCommercio getIscrizioneallaCameradiCommercio() {
		return iscrizioneallaCameradiCommercio;
	}

	/**
	 * @param i
	 */
	public void setIscrizioneallaCameradiCommercio(IscrizioneallaCameradiCommercio i) {
		iscrizioneallaCameradiCommercio = i;
	}

	/**
	 * @return
	 */
	public IscrizioneallAlboProfessionale getIscrizioneallAlboProfessionale() {
		return iscrizioneallAlboProfessionale;
	}

	/**
	 * @param i
	 */
	public void setIscrizioneallAlboProfessionale(IscrizioneallAlboProfessionale i) {
		iscrizioneallAlboProfessionale = i;
	}

	/**
	 * @return
	 */
	public PersonaFisica getProcuratore() {
		return procuratore;
	}

	/**
	 * @param i
	 */
	public void setProcuratore(PersonaFisica i) {
		procuratore = i;
	}

	/**
	 * @return
	 */
	public PersonaFisica getRappresentante() {
		return rappresentante;
	}

	/**
	 * @param i
	 */
	public void setRappresentante(PersonaFisica i) {
		rappresentante = i;
	}

	/**
	 * @return
	 */
	public String getTipoPersonaGiuridica() {
		return tipoPersonaGiuridica;
	}

	/**
	 * @param i
	 */
	public void setTipoPersonaGiuridica(String i) {
		tipoPersonaGiuridica = i;
	}

}
