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

public class NormativaBean implements Serializable {

	private static final long serialVersionUID = -371556375584903659L;
	private String nomeRiferimento;
	private String titoloRiferimento;
	private String nomeFile;
	private String codRif;
	private int index;

	public NormativaBean(){
		this.nomeRiferimento="";
		this.titoloRiferimento="";
		this.nomeFile="";
		this.codRif="";
		this.index=0;
	}

	
	public void setNomeRiferimento(String nomeRiferimento) {
		this.nomeRiferimento = nomeRiferimento;
	}

	public String getNomeRiferimento() {
		return nomeRiferimento;
	}

	public void setTitoloRiferimento(String titoloRiferimento) {
		this.titoloRiferimento = titoloRiferimento;
	}

	public String getTitoloRiferimento() {
		return titoloRiferimento;
	}
	
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getNomeFile() {
		return nomeFile;
	}
  
	public String getCodRif() {
		return codRif;
	}
	
	public void setCodRif(String codRif) {
		this.codRif = codRif;
	}


	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}
}
