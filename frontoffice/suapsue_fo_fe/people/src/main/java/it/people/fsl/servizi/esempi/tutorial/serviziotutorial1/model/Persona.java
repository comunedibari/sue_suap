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
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
/*
 * Creato il 5-lug-2007 da Cedaf s.r.l.
 *
 */
package it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.model;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 *
 */
public class Persona {
	// Dati utilizzati per la comunicazione con il Back-End
    private String cognome;
    private String nome;    
    private int anno;
    private int eta;

	/**
	 * @return Returns the anno.
	 */
	public int getAnno() {
		return anno;
	}
	/**
	 * @param anno The anno to set.
	 */
	public void setAnno(int anno) {
		this.anno = anno;
	}
	/**
	 * @return Returns the cognome.
	 */
	public String getCognome() {
		return cognome;
	}
	/**
	 * @param cognome The cognome to set.
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	/**
	 * @return Returns the eta.
	 */
	public int getEta() {
		return eta;
	}
	/**
	 * @param eta The eta to set.
	 */
	public void setEta(int eta) {
		this.eta = eta;
	}
	/**
	 * @return Returns the nome.
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome The nome to set.
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
}
