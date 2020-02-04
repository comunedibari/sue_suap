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
package it.people.dbm.model;

import java.util.List;

/**
 * 
 * @author Piergiorgio
 */
public class Utente implements java.io.Serializable {
	public String cod_utente;
	public String ruolo;
	public String cognome_nome;
	public String email;
	public String cod_utente_padre;
	public String tip_aggregazione;
	public List<String> lingue;

	public List<String> getLingue() {
		return lingue;
	}

	public void setLingue(List<String> lingue) {
		this.lingue = lingue;
	}

	public String getTipAggregazione() {
		return tip_aggregazione;
	}

	public void setTipAggregazione(String tip_aggregazione) {
		this.tip_aggregazione = tip_aggregazione;
	}

	public String getCodUtente() {
		return cod_utente;
	}

	public void setCodUtente(String cod_utente) {
		this.cod_utente = cod_utente;
	}

	public String getCodUtentePadre() {
		return cod_utente_padre;
	}

	public void setCodUtentePadre(String cod_utente_padre) {
		this.cod_utente_padre = cod_utente_padre;
	}

	public String getCognomeNome() {
		return cognome_nome;
	}

	public void setCognomeNome(String cognome_nome) {
		this.cognome_nome = cognome_nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

}
