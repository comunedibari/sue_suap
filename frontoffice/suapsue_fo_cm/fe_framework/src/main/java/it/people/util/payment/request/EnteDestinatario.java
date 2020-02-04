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
 * Created on 20-apr-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.util.payment.request;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class EnteDestinatario {
    String identificativo;
    Long valore;
    String causale;
    String importoContabileIngresso;
    String importoContabileUscita;

    public EnteDestinatario() {
    }

    /***
     * Costruttore che inizializza i membri dell'oggetto
     * 
     * @author Michele Fabbri - Cedaf s.r.l.
     */
    public EnteDestinatario(String identificativo, Long valore, String causale,
	    String importoContabileIngresso, String importoContabileUscita) {
	this.identificativo = identificativo;
	this.valore = valore;
	this.causale = causale;
	this.importoContabileIngresso = importoContabileIngresso;
	this.importoContabileUscita = importoContabileUscita;
    }

    /**
     * @return Returns the causale.
     */
    public String getCausale() {
	return causale;
    }

    /**
     * @param causale
     *            The causale to set.
     */
    public void setCausale(String causale) {
	this.causale = causale;
    }

    /**
     * @return Returns the identificativo.
     */
    public String getIdentificativo() {
	return identificativo;
    }

    /**
     * @param identificativo
     *            The identificativo to set.
     */
    public void setIdentificativo(String identificativo) {
	this.identificativo = identificativo;
    }

    /**
     * @return Returns the importoContabileIngresso.
     */
    public String getImportoContabileIngresso() {
	return importoContabileIngresso;
    }

    /**
     * @param importoContabileIngresso
     *            The importoContabileIngresso to set.
     */
    public void setImportoContabileIngresso(String importoContabileIngresso) {
	this.importoContabileIngresso = importoContabileIngresso;
    }

    /**
     * @return Returns the importoContabileUscita.
     */
    public String getImportoContabileUscita() {
	return importoContabileUscita;
    }

    /**
     * @param importoContabileUscita
     *            The importoContabileUscita to set.
     */
    public void setImportoContabileUscita(String importoContabileUscita) {
	this.importoContabileUscita = importoContabileUscita;
    }

    /**
     * @return Returns the valore.
     */
    public Long getValore() {
	return valore;
    }

    /**
     * @param valore
     *            The valore to set.
     */
    public void setValore(Long valore) {
	this.valore = valore;
    }
}
