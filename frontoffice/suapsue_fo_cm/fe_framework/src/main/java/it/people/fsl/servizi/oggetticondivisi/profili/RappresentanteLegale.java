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

import java.util.Date;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         24/ago/2012 18:31:40
 */
public class RappresentanteLegale implements java.io.Serializable {

    private static final long serialVersionUID = 3322056991507466589L;

    int idAccreditamento;
    String nome;
    String cognome;
    String codiceFiscale;
    String sesso;
    Date dataNascita;
    String luogoNascita;
    String provinciaNascita;
    String indirizzoResidenza;
    String codiceFiscaleIntermediario;
    String partitaIvaIntermediario;
    String domicilioElettronico;

    /**
	 * 
	 */
    public RappresentanteLegale() {
	super();
	// TODO Auto-generated constructor stub
    }

    /**
     * @return Returns the idAccreditamento.
     */
    public int getIdAccreditamento() {
	return idAccreditamento;
    }

    /**
     * @param idAccreditamento
     *            The idAccreditamento to set.
     */
    public void setIdAccreditamento(int idAccreditamento) {
	this.idAccreditamento = idAccreditamento;
    }

    /**
     * @return Returns the codiceFiscale.
     */
    public String getCodiceFiscale() {
	return codiceFiscale;
    }

    /**
     * @param codiceFiscale
     *            The codiceFiscale to set.
     */
    public void setCodiceFiscale(String codiceFiscale) {
	this.codiceFiscale = codiceFiscale;
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
     * @return Returns the cognome.
     */
    public String getCognome() {
	return cognome;
    }

    /**
     * @param cognome
     *            The cognome to set.
     */
    public void setCognome(String cognome) {
	this.cognome = cognome;
    }

    /**
     * @return Returns the dataNascita.
     */
    public Date getDataNascita() {
	return dataNascita;
    }

    /**
     * @param dataNascita
     *            The dataNascita to set.
     */
    public void setDataNascita(Date dataNascita) {
	this.dataNascita = dataNascita;
    }

    /**
     * @return Returns the luogoNascita.
     */
    public String getLuogoNascita() {
	return luogoNascita;
    }

    /**
     * @param luogoNascita
     *            The luogoNascita to set.
     */
    public void setLuogoNascita(String luogoNascita) {
	this.luogoNascita = luogoNascita;
    }

    /**
     * @return Returns the nome.
     */
    public String getNome() {
	return nome;
    }

    /**
     * @param nome
     *            The nome to set.
     */
    public void setNome(String nome) {
	this.nome = nome;
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

    /**
     * @return Returns the provinciaNascita.
     */
    public String getProvinciaNascita() {
	return provinciaNascita;
    }

    /**
     * @param provinciaNascita
     *            The provinciaNascita to set.
     */
    public void setProvinciaNascita(String provinciaNascita) {
	this.provinciaNascita = provinciaNascita;
    }

    /**
     * @return Returns the sesso.
     */
    public String getSesso() {
	return sesso;
    }

    /**
     * @param sesso
     *            The sesso to set.
     */
    public void setSesso(String sesso) {
	this.sesso = sesso;
    }

    /**
     * @return Returns the indirizzoResidenza.
     */
    public String getIndirizzoResidenza() {
	return indirizzoResidenza;
    }

    /**
     * @param indirizzoResidenza
     *            The indirizzoResidenza to set.
     */
    public void setIndirizzoResidenza(String indirizzoResidenza) {
	this.indirizzoResidenza = indirizzoResidenza;
    }

    /**
     * @return the domicilioElettronico
     */
    public final String getDomicilioElettronico() {
	return this.domicilioElettronico;
    }

    /**
     * @param domicilioElettronico
     *            the domicilioElettronico to set
     */
    public final void setDomicilioElettronico(String domicilioElettronico) {
	this.domicilioElettronico = domicilioElettronico;
    }

    /**
     * toString methode: creates a String representation of the object
     * 
     * @return the String representation
     * @author info.vancauwenberge.tostring plugin
     */
    public String toString() {
	StringBuffer buffer = new StringBuffer();
	buffer.append("RappresentanteLegale[");
	buffer.append("idAccreditamento = ").append(idAccreditamento);
	buffer.append(", nome = ").append(nome);
	buffer.append(", cognome = ").append(cognome);
	buffer.append(", codiceFiscale = ").append(codiceFiscale);
	buffer.append(", domicilioElettronico = ").append(domicilioElettronico);
	buffer.append(", sesso = ").append(sesso);
	buffer.append(", dataNascita = ").append(dataNascita);
	buffer.append(", luogoNascita = ").append(luogoNascita);
	buffer.append(", provinciaNascita = ").append(provinciaNascita);
	buffer.append(", indirizzoResidenza = ").append(indirizzoResidenza);
	buffer.append(", codiceFiscaleIntermediario = ").append(
		codiceFiscaleIntermediario);
	buffer.append(", partitaIvaIntermediario = ").append(
		partitaIvaIntermediario);
	buffer.append("]");
	return buffer.toString();
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
	if (!(obj instanceof RappresentanteLegale))
	    return false;
	RappresentanteLegale other = (RappresentanteLegale) obj;
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
		&& ((this.codiceFiscale == null && other.getCodiceFiscale() == null) || (this.codiceFiscale != null && this.codiceFiscale
			.equals(other.getCodiceFiscale())))
		&& ((this.codiceFiscaleIntermediario == null && other
			.getCodiceFiscaleIntermediario() == null) || (this.codiceFiscaleIntermediario != null && this.codiceFiscaleIntermediario
			.equals(other.getCodiceFiscaleIntermediario())))
		&& ((this.cognome == null && other.getCognome() == null) || (this.cognome != null && this.cognome
			.equals(other.getCognome())))
		&& ((this.dataNascita == null && other.getDataNascita() == null) || (this.dataNascita != null && this.dataNascita
			.equals(other.getDataNascita())))
		&& this.idAccreditamento == other.getIdAccreditamento()
		&& ((this.indirizzoResidenza == null && other
			.getIndirizzoResidenza() == null) || (this.indirizzoResidenza != null && this.indirizzoResidenza
			.equals(other.getIndirizzoResidenza())))
		&& ((this.luogoNascita == null && other.getLuogoNascita() == null) || (this.luogoNascita != null && this.luogoNascita
			.equals(other.getLuogoNascita())))
		&& ((this.nome == null && other.getNome() == null) || (this.nome != null && this.nome
			.equals(other.getNome())))
		&& ((this.partitaIvaIntermediario == null && other
			.getPartitaIvaIntermediario() == null) || (this.partitaIvaIntermediario != null && this.partitaIvaIntermediario
			.equals(other.getPartitaIvaIntermediario())))
		&& ((this.provinciaNascita == null && other
			.getProvinciaNascita() == null) || (this.provinciaNascita != null && this.provinciaNascita
			.equals(other.getProvinciaNascita())))
		&& ((this.domicilioElettronico == null && other
			.getDomicilioElettronico() == null) || (this.domicilioElettronico != null && this.domicilioElettronico
			.equals(other.getDomicilioElettronico())))
		&& ((this.sesso == null && other.getSesso() == null) || (this.sesso != null && this.sesso
			.equals(other.getSesso())));
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
	if (getCodiceFiscale() != null) {
	    _hashCode += getCodiceFiscale().hashCode();
	}
	if (getCodiceFiscaleIntermediario() != null) {
	    _hashCode += getCodiceFiscaleIntermediario().hashCode();
	}
	if (getCognome() != null) {
	    _hashCode += getCognome().hashCode();
	}
	if (getDataNascita() != null) {
	    _hashCode += getDataNascita().hashCode();
	}
	_hashCode += getIdAccreditamento();
	if (getIndirizzoResidenza() != null) {
	    _hashCode += getIndirizzoResidenza().hashCode();
	}
	if (getLuogoNascita() != null) {
	    _hashCode += getLuogoNascita().hashCode();
	}
	if (getNome() != null) {
	    _hashCode += getNome().hashCode();
	}
	if (getPartitaIvaIntermediario() != null) {
	    _hashCode += getPartitaIvaIntermediario().hashCode();
	}
	if (getProvinciaNascita() != null) {
	    _hashCode += getProvinciaNascita().hashCode();
	}
	if (getDomicilioElettronico() != null) {
	    _hashCode += getDomicilioElettronico().hashCode();
	}
	if (getSesso() != null) {
	    _hashCode += getSesso().hashCode();
	}
	__hashCodeCalc = false;
	return _hashCode;
    }
}