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

import java.io.Serializable;

public class DestinatarioBean  extends IndirizzoBean implements Serializable {

	private static final long serialVersionUID = -7395443371325531943L;
	
	private String cod_dest;
	private String intestazione;
	private String nome_dest;
	private String cod_ente;
	
	public String getCod_dest() {
		return cod_dest;
	}
	public void setCod_dest(String cod_dest) {
		this.cod_dest = cod_dest;
	}
	public String getIntestazione() {
		return intestazione;
	}
	public void setIntestazione(String intestazione) {
		this.intestazione = intestazione;
	}
	public String getNome_dest() {
		return nome_dest;
	}
	public void setNome_dest(String nome_dest) {
		this.nome_dest = nome_dest;
	}
	public String getCod_ente() {
		return cod_ente;
	}
	public void setCod_ente(String cod_ente) {
		this.cod_ente = cod_ente;
	}
	
}
