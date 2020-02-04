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
package it.people.fsl.servizi.oggetticondivisi.profili;

import it.people.fsl.servizi.oggetticondivisi.profili.RappresentanteLegale;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         24/ago/2012 18:30:54
 */
public class ProfiloAccreditamento implements java.io.Serializable {

    private static final long serialVersionUID = 6113807677927199734L;

    /** codice fiscale intermediario */
    private String codiceFiscaleIntermediario;

    /** partita iva intermediario */
    private String partitaIvaIntermediario;

    /** libero */
    private String descrizione;

    /** domicilio elettronico accreditamento */
    private String domicilioElettronico;

    /** autocertificazione firmata */
    private transient byte[] autoCert;

    /** filename BLOB autocertificazione firmata */
    private String autoCertFilename;

    /**
     * identificativo unico nel tempo dell'autocertificazione (ms dall'epoque)
     **/
    private String timestampAutoCert;

    /**
     * denominazione o ragione sociale intermediario (usato solo per alcune
     * qualifiche)
     */
    private String denominazione;

    /** sede legale intermediario (usato solo per alcune qualifiche) */
    private String sedeLegale;

    private RappresentanteLegale rappresentanteLegale;

    public ProfiloAccreditamento() {
	clear();
    }

    public void clear() {
	byte[] autocert = new byte[0];
	autoCertFilename = "";
	codiceFiscaleIntermediario = "";
	partitaIvaIntermediario = "";
	timestampAutoCert = "";
	denominazione = "";
	sedeLegale = "";
	domicilioElettronico = "";
	descrizione = "";
	rappresentanteLegale = null;
    }

    /**
     * @return Returns the autoCertFilename.
     */
    public String getAutoCertFilename() {
	return autoCertFilename;
    }

    /**
     * @param autoCertFileName
     *            The autoCertFileName to set.
     */
    public void setAutoCertFilename(String autoCertFilename) {
	this.autoCertFilename = autoCertFilename;
    }

    /**
     * @return Returns the codiceFiscaleIntermediario.
     */
    public String getCodiceFiscaleIntermediario() {
	return codiceFiscaleIntermediario;
    }

    /**
     * @param codiceFiscaleIntermediario
     *            The codiceFiscaleIntermediario to set.
     */
    public void setCodiceFiscaleIntermediario(String codiceFiscaleIntermediario) {
	this.codiceFiscaleIntermediario = codiceFiscaleIntermediario;
    }

    /**
     * @return Returns the denominazione.
     */
    public String getDenominazione() {
	return denominazione;
    }

    /**
     * @param denominazione
     *            The denominazione to set.
     */
    public void setDenominazione(String denominazione) {
	this.denominazione = denominazione;
    }

    /**
     * @return Returns the partitaIvaIntermediario.
     */
    public String getPartitaIvaIntermediario() {
	return partitaIvaIntermediario;
    }

    /**
     * @param partitaIvaIntermediario
     *            The partitaIvaIntermediario to set.
     */
    public void setPartitaIvaIntermediario(String partitaIvaIntermediario) {
	this.partitaIvaIntermediario = partitaIvaIntermediario;
    }

    public String getDescrizione() {
	return descrizione;
    }

    public void setDescrizione(String descrizione) {
	this.descrizione = descrizione;
    }

    public byte[] getAutoCert() {
	return autoCert;
    }

    public void setAutoCert(byte[] procura) {
	this.autoCert = procura;
    }

    /**
     * @return Returns the timestampAutoCert.
     */
    public String getTimestampAutoCert() {
	return timestampAutoCert;
    }

    /**
     * @param timestampAutoCert
     *            The timestampAutoCert to set.
     */
    public void setTimestampAutoCert(String timestampAutoCert) {
	this.timestampAutoCert = timestampAutoCert;
    }

    /**
     * @return Returns the domicilioElettronico.
     */
    public String getDomicilioElettronico() {
	return domicilioElettronico;
    }

    /**
     * @param domicilioElettronico
     *            The domicilioElettronico to set.
     */
    public void setDomicilioElettronico(String domicilioElettronico) {
	this.domicilioElettronico = domicilioElettronico;
    }

    public String getSedeLegale() {
	return sedeLegale;
    }

    /**
     * 
     * @param sedeLegale
     */
    public void setSedeLegale(String sedeLegale) {
	this.sedeLegale = sedeLegale;
    }

    /**
     * @return Returns the rappresentanteLegale.
     */
    public RappresentanteLegale getRappresentanteLegale() {
	return rappresentanteLegale;
    }

    /**
     * @param rappresentanteLegale
     *            The rappresentanteLegale to set.
     */
    public void setRappresentanteLegale(
	    RappresentanteLegale rappresentanteLegale) {
	this.rappresentanteLegale = rappresentanteLegale;
    }

    public String toString() {
	StringBuffer sb = new StringBuffer("ProfiloAccreditamento [ ");
	sb.append(" codiceFiscaleIntermediario = " + codiceFiscaleIntermediario);
	sb.append(" partitaIvaIntermediario = " + partitaIvaIntermediario);
	sb.append(" denominazione = " + denominazione);
	sb.append(" sedeLegale = " + sedeLegale);
	sb.append(" domicilioElettronico = " + domicilioElettronico);
	sb.append(" autoCertificazione = (Non visualizzato)");
	sb.append(" timestampAutoCert = " + timestampAutoCert);
	sb.append(" rappresentanteLegale = " + rappresentanteLegale);
	sb.append(" ]");

	return sb.toString();
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
	if (!(obj instanceof ProfiloAccreditamento))
	    return false;
	ProfiloAccreditamento other = (ProfiloAccreditamento) obj;
	if (obj == null)
	    return false;
	if (this == obj)
	    return true;
	if (__equalsCalc != null) {
	    return (__equalsCalc == obj);
	}
	__equalsCalc = obj;
	boolean _equals;
	_equals = true
		&& ((this.autoCert == null && other.getAutoCert() == null) || (this.autoCert != null && java.util.Arrays
			.equals(this.autoCert, other.getAutoCert())))
		&& ((this.codiceFiscaleIntermediario == null && other
			.getCodiceFiscaleIntermediario() == null) || (this.codiceFiscaleIntermediario != null && this.codiceFiscaleIntermediario
			.equals(other.getCodiceFiscaleIntermediario())))
		&& ((this.denominazione == null && other.getDenominazione() == null) || (this.denominazione != null && this.denominazione
			.equals(other.getDenominazione())))
		&& ((this.descrizione == null && other.getDescrizione() == null) || (this.descrizione != null && this.descrizione
			.equals(other.getDescrizione())))
		&& ((this.domicilioElettronico == null && other
			.getDomicilioElettronico() == null) || (this.domicilioElettronico != null && this.domicilioElettronico
			.equals(other.getDomicilioElettronico())))
		&& ((this.partitaIvaIntermediario == null && other
			.getPartitaIvaIntermediario() == null) || (this.partitaIvaIntermediario != null && this.partitaIvaIntermediario
			.equals(other.getPartitaIvaIntermediario())))
		&& ((this.rappresentanteLegale == null && other
			.getRappresentanteLegale() == null) || (this.rappresentanteLegale != null && this.rappresentanteLegale
			.equals(other.getRappresentanteLegale())))
		&& ((this.sedeLegale == null && other.getSedeLegale() == null) || (this.sedeLegale != null && this.sedeLegale
			.equals(other.getSedeLegale())))
		&& ((this.timestampAutoCert == null && other
			.getTimestampAutoCert() == null) || (this.timestampAutoCert != null && this.timestampAutoCert
			.equals(other.getTimestampAutoCert())));
	__equalsCalc = null;
	return _equals;
    }

    private boolean __hashCodeCalc = false;

    public synchronized int hashCode() {
	if (__hashCodeCalc) {
	    return 0;
	}
	__hashCodeCalc = true;
	int _hashCode = 1;
	if (getAutoCert() != null) {
	    for (int i = 0; i < java.lang.reflect.Array
		    .getLength(getAutoCert()); i++) {
		java.lang.Object obj = java.lang.reflect.Array.get(
			getAutoCert(), i);
		if (obj != null && !obj.getClass().isArray()) {
		    _hashCode += obj.hashCode();
		}
	    }
	}
	if (getCodiceFiscaleIntermediario() != null) {
	    _hashCode += getCodiceFiscaleIntermediario().hashCode();
	}
	if (getDenominazione() != null) {
	    _hashCode += getDenominazione().hashCode();
	}
	if (getDescrizione() != null) {
	    _hashCode += getDescrizione().hashCode();
	}
	if (getDomicilioElettronico() != null) {
	    _hashCode += getDomicilioElettronico().hashCode();
	}
	if (getPartitaIvaIntermediario() != null) {
	    _hashCode += getPartitaIvaIntermediario().hashCode();
	}
	if (getRappresentanteLegale() != null) {
	    _hashCode += getRappresentanteLegale().hashCode();
	}
	if (getSedeLegale() != null) {
	    _hashCode += getSedeLegale().hashCode();
	}
	if (getTimestampAutoCert() != null) {
	    _hashCode += getTimestampAutoCert().hashCode();
	}
	__hashCodeCalc = false;
	return _hashCode;
    }

}
