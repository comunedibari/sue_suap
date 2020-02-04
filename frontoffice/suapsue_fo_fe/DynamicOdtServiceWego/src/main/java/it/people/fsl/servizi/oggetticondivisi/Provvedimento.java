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
import it.people.process.common.entity.AbstractEntity;

public class Provvedimento extends AbstractEntity {

    private double spese;
    private double differenzatraDovutoeVersato;
    private java.lang.String stato;
    private double importoVersato;
    private Data datadelProvvedimento;
    private double sanzioni;
    private double interessi;
    private double importoTotale;
    private java.lang.String codiceFiscale;
    private int annodiApplicazione;

    public static class StatoProvvedimento {
	   	public static final String EMESSO = "Emesso";
	   	public static final String NOTIFICATO = "Notificato";
	   	public static final String SOSPESO = "Sospeso";
	   	public static final String PAGATO =	"Pagato";
	   	public static final String ANNULLATO = "Annullato";
	   	public static final String A_RUOLO_COATTIVO = "A Ruolo Coattivo";   	
    }
    
    
	/**
	 * @return Returns the annodiApplicazione.
	 */
	public int getAnnodiApplicazione() {
		return annodiApplicazione;
	}
	/**
	 * @param annodiApplicazione The annodiApplicazione to set.
	 */
	public void setAnnodiApplicazione(int annodiApplicazione) {
		this.annodiApplicazione = annodiApplicazione;
	}
	/**
	 * @return Returns the codiceFiscale.
	 */
	public java.lang.String getCodiceFiscale() {
		return codiceFiscale;
	}
	/**
	 * @param codiceFiscale The codiceFiscale to set.
	 */
	public void setCodiceFiscale(java.lang.String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	/**
	 * @return Returns the datadelProvvedimento.
	 */
	public Data getDatadelProvvedimento() {
		return datadelProvvedimento;
	}
	/**
	 * @param datadelProvvedimento The datadelProvvedimento to set.
	 */
	public void setDatadelProvvedimento(Data datadelProvvedimento) {
		this.datadelProvvedimento = datadelProvvedimento;
	}
	/**
	 * @return Returns the differenzatraDovutoeVersato.
	 */
	public double getDifferenzatraDovutoeVersato() {
		return differenzatraDovutoeVersato;
	}
	/**
	 * @param differenzatraDovutoeVersato The differenzatraDovutoeVersato to set.
	 */
	public void setDifferenzatraDovutoeVersato(
			double differenzatraDovutoeVersato) {
		this.differenzatraDovutoeVersato = differenzatraDovutoeVersato;
	}
	/**
	 * @return Returns the importoTotale.
	 */
	public double getImportoTotale() {
		return importoTotale;
	}
	/**
	 * @param importoTotale The importoTotale to set.
	 */
	public void setImportoTotale(double importoTotale) {
		this.importoTotale = importoTotale;
	}
	/**
	 * @return Returns the importoVersato.
	 */
	public double getImportoVersato() {
		return importoVersato;
	}
	/**
	 * @param importoVersato The importoVersato to set.
	 */
	public void setImportoVersato(double importoVersato) {
		this.importoVersato = importoVersato;
	}
	/**
	 * @return Returns the interessi.
	 */
	public double getInteressi() {
		return interessi;
	}
	/**
	 * @param interessi The interessi to set.
	 */
	public void setInteressi(double interessi) {
		this.interessi = interessi;
	}
	/**
	 * @return Returns the sanzioni.
	 */
	public double getSanzioni() {
		return sanzioni;
	}
	/**
	 * @param sanzioni The sanzioni to set.
	 */
	public void setSanzioni(double sanzioni) {
		this.sanzioni = sanzioni;
	}
	/**
	 * @return Returns the spese.
	 */
	public double getSpese() {
		return spese;
	}
	/**
	 * @param spese The spese to set.
	 */
	public void setSpese(double spese) {
		this.spese = spese;
	}
	/**
	 * @return Returns the stato.
	 * 
	 *  Returns one of the following values:
	 * 
	 * 	"Emesso"
	 *  "Notificato"
	 *	"Sospeso"
     *	"Pagato"
	 *	"Annullato
	 *	"A Ruolo Coattivo"
	 *
	 *	These values are declared in the inner class StatoProvvedimento.
	 */
	public java.lang.String getStato() {
		return stato;
	}
	
	/**
	 * @param stato The stato to set.
	 * 
	 * Only the following values are allowed:
	 * 
	 * 	"Emesso"
	 *  "Notificato"
	 *	"Sospeso"
     *	"Pagato"
	 *	"Annullato
	 *	"A Ruolo Coattivo"
	 *
	 *  These values are declared in the inner class StatoProvvedimento.
	 */
	public void setStato(java.lang.String stato) {
		this.stato = stato;
	}
	
}
