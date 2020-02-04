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
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti;

public class IntermediariBean {
	
	private String cfIntermediario;
	private String ragSocIntermediario;
	private String indirizzoIntermediario;
	private String cfUtenteIntermediario;
	private String nomeUtenteIntermediario;
	private String cognomeUtenteIntermediario;
	
	public IntermediariBean(){
		this.cfIntermediario="";
		this.cfUtenteIntermediario="";
		this.cognomeUtenteIntermediario="";
		this.indirizzoIntermediario="";
		this.nomeUtenteIntermediario="";
		this.ragSocIntermediario="";
	}

	public String getCfIntermediario() {
		return cfIntermediario;
	}

	public void setCfIntermediario(String cfIntermediario) {
		this.cfIntermediario = cfIntermediario;
	}

	public String getRagSocIntermediario() {
		return ragSocIntermediario;
	}

	public void setRagSocIntermediario(String ragSocIntermediario) {
		this.ragSocIntermediario = ragSocIntermediario;
	}

	public String getIndirizzoIntermediario() {
		return indirizzoIntermediario;
	}

	public void setIndirizzoIntermediario(String indirizzoIntermediario) {
		this.indirizzoIntermediario = indirizzoIntermediario;
	}

	public String getCfUtenteIntermediario() {
		return cfUtenteIntermediario;
	}

	public void setCfUtenteIntermediario(String cfUtenteIntermediario) {
		this.cfUtenteIntermediario = cfUtenteIntermediario;
	}

	public String getNomeUtenteIntermediario() {
		return nomeUtenteIntermediario;
	}

	public void setNomeUtenteIntermediario(String nomeUtenteIntermediario) {
		this.nomeUtenteIntermediario = nomeUtenteIntermediario;
	}

	public String getCognomeUtenteIntermediario() {
		return cognomeUtenteIntermediario;
	}

	public void setCognomeUtenteIntermediario(String cognomeUtenteIntermediario) {
		this.cognomeUtenteIntermediario = cognomeUtenteIntermediario;
	}

}
