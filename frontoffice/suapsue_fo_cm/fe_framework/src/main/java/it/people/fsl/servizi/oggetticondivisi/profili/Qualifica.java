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

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         24/ago/2012 18:29:22
 */
public class Qualifica implements java.io.Serializable {

    private static final long serialVersionUID = -5700877025504170164L;

    /** identificativo univoco della qualifica */
    private String idQualifica;

    /** tipologia qualifica (professionista, intermediario, carica sociale, etc) */
    private String tipoQualifica;

    /**
     * flag per indicare se per gli accreditamenti associati alla qualifica �
     * prevista la definizione di un rappresentante legale
     */
    private boolean hasRappresentanteLegale;

    /** descrizione libera */
    private String descrizione;

    /**
     * Inizializzazione di default degli attributi del bean.
     */
    public Qualifica() {
	idQualifica = "";
	tipoQualifica = "";
	descrizione = "";
	hasRappresentanteLegale = false;
    }

    /**
     * @return Returns the descrizione.
     */
    public String getDescrizione() {
	return descrizione;
    }

    /**
     * @param descrizione
     *            The descrizione to set.
     */
    public void setDescrizione(String descrizione) {
	this.descrizione = descrizione;
    }

    /**
     * @return Returns the idQualifica.
     */
    public String getIdQualifica() {
	return idQualifica;
    }

    /**
     * @param idQualifica
     *            The idQualifica to set.
     */
    public void setIdQualifica(String idQualifica) {
	this.idQualifica = idQualifica;
    }

    public String getTipoQualifica() {
	return tipoQualifica;
    }

    public void setTipoQualifica(String tipoQualifica) {
	this.tipoQualifica = tipoQualifica;
    }

    /**
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
	return "Qualifica [ idQualifica = " + idQualifica + ", descrizione = "
		+ descrizione + ", type = " + tipoQualifica
		+ ", hasRappresentanteLegale = " + hasRappresentanteLegale
		+ " ]";
    }

    /**
     * @return Returns the hasRappresentanteLegale.
     */
    public boolean getHasRappresentanteLegale() {
	return hasRappresentanteLegale;
    }

    /**
     * @param hasRappresentanteLegale
     *            The hasRappresentanteLegale to set.
     */
    public void setHasRappresentanteLegale(boolean hasRappresentanteLegale) {
	this.hasRappresentanteLegale = hasRappresentanteLegale;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
	if (!(obj instanceof Qualifica))
	    return false;
	Qualifica other = (Qualifica) obj;
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
		&& ((this.descrizione == null && other.getDescrizione() == null) || (this.descrizione != null && this.descrizione
			.equals(other.getDescrizione())))
		&& this.hasRappresentanteLegale == other
			.getHasRappresentanteLegale()
		&& ((this.idQualifica == null && other.getIdQualifica() == null) || (this.idQualifica != null && this.idQualifica
			.equals(other.getIdQualifica())))
		&& ((this.tipoQualifica == null && other.getTipoQualifica() == null) || (this.tipoQualifica != null && this.tipoQualifica
			.equals(other.getTipoQualifica())));
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
	if (getDescrizione() != null) {
	    _hashCode += getDescrizione().hashCode();
	}
	_hashCode += new Boolean(getHasRappresentanteLegale()).hashCode();
	if (getIdQualifica() != null) {
	    _hashCode += getIdQualifica().hashCode();
	}
	if (getTipoQualifica() != null) {
	    _hashCode += getTipoQualifica().hashCode();
	}
	__hashCodeCalc = false;
	return _hashCode;
    }

}
