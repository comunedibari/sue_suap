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

import it.people.fsl.servizi.oggetticondivisi.profili.ProfiloAccreditamento;
import it.people.fsl.servizi.oggetticondivisi.profili.Qualifica;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         24/ago/2012 18:24:22
 */
public class AccreditamentoCorrente implements java.io.Serializable {

    private static final long serialVersionUID = -4615804335153058512L;

    /** l'utente people */
    private String codiceFiscale;

    /**
     * l'id del comune presso cui vale questo accreditamento (lo stesso utente
     * pu� accreditarsi presso diversi comuni, e un <i>Sirac</i> pu� gestire
     * pi� comuni)
     */
    private String idComune;

    /** la qualifica */
    private Qualifica qualifica;

    /** id univoco */
    private int id;

    /** attivo/non attivo */
    private boolean attivo;

    /** il profilo di accreditamento */
    private ProfiloAccreditamento profilo;

    public AccreditamentoCorrente() {
    }

    /**
     * @return la qualifica.
     */
    public Qualifica getQualifica() {
	return qualifica;
    }

    /**
     * @param qualifica
     *            la qualifica
     */
    public void setQualifica(Qualifica qualifica) {
	this.qualifica = qualifica;
    }

    /**
     * @return restituisce il codiceFiscale.
     */
    public String getCodiceFiscale() {
	return codiceFiscale;
    }

    /**
     * @param codiceFiscale
     *            il codice fiscale
     */
    public void setCodiceFiscale(String codiceFiscale) {
	this.codiceFiscale = codiceFiscale;
    }

    /**
     * @return l'id del comune
     */
    public String getIdComune() {
	return idComune;
    }

    /**
     * @param idComune
     *            l'id del comune
     */
    public void setIdComune(String idComune) {
	this.idComune = idComune;
    }

    /**
     * @return Returns the profilo.
     */
    public ProfiloAccreditamento getProfilo() {
	return profilo;
    }

    /**
     * @param profilo
     *            The profilo to set.
     */
    public void setProfilo(ProfiloAccreditamento profilo) {
	this.profilo = profilo;
    }

    /**
     * @return attivo.
     */
    public boolean isAttivo() {
	return attivo;
    }

    /**
     * @param attivo
     *            modifica il campo attivo
     */
    public void setAttivo(boolean attivo) {
	this.attivo = attivo;
    }

    /**
     * @return id.
     */
    public int getId() {
	return id;
    }

    /**
     * @param id
     *            modifica il campo id
     */
    public void setId(int id) {
	this.id = id;
    }

    /*
     * public String toString() { return "Accreditamento [ id = " + id +
     * ", codiceFiscale = " + codiceFiscale + ", " + "idComune = " + idComune +
     * ", qualifica = " + qualifica.toString() + " ]"; }
     */
    /**
     * toString methode: creates a String representation of the object
     * 
     * @return the String representation
     * @author info.vancauwenberge.tostring plugin
     */
    public String toString() {
	StringBuffer buffer = new StringBuffer();
	buffer.append("Accreditamento[");
	buffer.append("codiceFiscale = ").append(codiceFiscale);
	buffer.append(", idComune = ").append(idComune);
	buffer.append(", qualifica = ").append(qualifica);
	buffer.append(", id = ").append(id);
	buffer.append(", attivo = ").append(attivo);
	buffer.append(", profilo = ").append(profilo);
	buffer.append("]");
	return buffer.toString();
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
	if (!(obj instanceof AccreditamentoCorrente))
	    return false;
	AccreditamentoCorrente other = (AccreditamentoCorrente) obj;
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
		&& this.attivo == other.isAttivo()
		&& ((this.codiceFiscale == null && other.getCodiceFiscale() == null) || (this.codiceFiscale != null && this.codiceFiscale
			.equals(other.getCodiceFiscale())))
		&& this.id == other.getId()
		&& ((this.idComune == null && other.getIdComune() == null) || (this.idComune != null && this.idComune
			.equals(other.getIdComune())))
		&& ((this.profilo == null && other.getProfilo() == null) || (this.profilo != null && this.profilo
			.equals(other.getProfilo())))
		&& ((this.qualifica == null && other.getQualifica() == null) || (this.qualifica != null && this.qualifica
			.equals(other.getQualifica())));
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
	_hashCode += new Boolean(isAttivo()).hashCode();
	if (getCodiceFiscale() != null) {
	    _hashCode += getCodiceFiscale().hashCode();
	}
	_hashCode += getId();
	if (getIdComune() != null) {
	    _hashCode += getIdComune().hashCode();
	}
	if (getProfilo() != null) {
	    _hashCode += getProfilo().hashCode();
	}
	if (getQualifica() != null) {
	    _hashCode += getQualifica().hashCode();
	}
	__hashCodeCalc = false;
	return _hashCode;
    }

}
