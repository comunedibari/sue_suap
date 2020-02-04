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
package it.people.fsl.servizi.oggetticondivisi;

import it.people.process.common.entity.AbstractEntity;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica
 * 
 */
public class IndirizzodiNotificaComunicazioni extends AbstractEntity {

    private String OID;

    private String via;
    private String numeroCivico;
    private String esponenteCivico;
    private String numeroInterno;
    private String esponenteInterno;
    private String scala;
    private String coloreCivico;
    private String cap;
    private String comune;
    private String provincia;
    private String piano;

    private String telefono;
    private String cellulare;
    private String email;
    private String fax;

    private String nazioneCodiceIstat;
    private String nazioneDescrizione;
    private String capEstero;

    public IndirizzodiNotificaComunicazioni() {

    }

    /**
     * @return the via
     */
    public String getVia() {
	return via;
    }

    /**
     * @param via
     *            the via to set
     */
    public void setVia(String via) {
	this.via = via;
    }

    /**
     * @return the numeroCivico
     */
    public String getNumeroCivico() {
	return numeroCivico;
    }

    /**
     * @param numeroCivico
     *            the numeroCivico to set
     */
    public void setNumeroCivico(String numeroCivico) {
	this.numeroCivico = numeroCivico;
    }

    /**
     * @return the esponenteCivico
     */
    public String getEsponenteCivico() {
	return esponenteCivico;
    }

    /**
     * @param esponenteCivico
     *            the esponenteCivico to set
     */
    public void setEsponenteCivico(String esponenteCivico) {
	this.esponenteCivico = esponenteCivico;
    }

    /**
     * @return the numeroInterno
     */
    public String getNumeroInterno() {
	return numeroInterno;
    }

    /**
     * @param numeroInterno
     *            the numeroInterno to set
     */
    public void setNumeroInterno(String numeroInterno) {
	this.numeroInterno = numeroInterno;
    }

    /**
     * @return the esponenteInterno
     */
    public String getEsponenteInterno() {
	return esponenteInterno;
    }

    /**
     * @param esponenteInterno
     *            the esponenteInterno to set
     */
    public void setEsponenteInterno(String esponenteInterno) {
	this.esponenteInterno = esponenteInterno;
    }

    /**
     * @return the scala
     */
    public String getScala() {
	return scala;
    }

    /**
     * @param scala
     *            the scala to set
     */
    public void setScala(String scala) {
	this.scala = scala;
    }

    /**
     * @return the coloreCivico
     */
    public String getColoreCivico() {
	return coloreCivico;
    }

    /**
     * @param coloreCivico
     *            the coloreCivico to set
     */
    public void setColoreCivico(String coloreCivico) {
	this.coloreCivico = coloreCivico;
    }

    /**
     * @return the cap
     */
    public String getCap() {
	return cap;
    }

    /**
     * @param cap
     *            the cap to set
     */
    public void setCap(String cap) {
	this.cap = cap;
    }

    /**
     * @return the comune
     */
    public String getComune() {
	return comune;
    }

    /**
     * @param comune
     *            the comune to set
     */
    public void setComune(String comune) {
	this.comune = comune;
    }

    /**
     * @return the provincia
     */
    public String getProvincia() {
	return provincia;
    }

    /**
     * @param provincia
     *            the provincia to set
     */
    public void setProvincia(String provincia) {
	this.provincia = provincia;
    }

    /**
     * @return the nazioneCodiceIstat
     */
    public String getNazioneCodiceIstat() {
	return nazioneCodiceIstat;
    }

    /**
     * @param nazioneCodiceIstat
     *            the nazioneCodiceIstat to set
     */
    public void setNazioneCodiceIstat(String nazioneCodiceIstat) {
	this.nazioneCodiceIstat = nazioneCodiceIstat;
    }

    /**
     * @return the nazioneDescrizione
     */
    public String getNazioneDescrizione() {
	return nazioneDescrizione;
    }

    /**
     * @param nazioneDescrizione
     *            the nazioneDescrizione to set
     */
    public void setNazioneDescrizione(String nazioneDescrizione) {
	this.nazioneDescrizione = nazioneDescrizione;
    }

    /**
     * @return the capEstero
     */
    public String getCapEstero() {
	return capEstero;
    }

    /**
     * @param capEstero
     *            the capEstero to set
     */
    public void setCapEstero(String capEstero) {
	this.capEstero = capEstero;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
	return telefono;
    }

    /**
     * @param telefono
     *            the telefono to set
     */
    public void setTelefono(String telefono) {
	this.telefono = telefono;
    }

    /**
     * @return the cellulare
     */
    public String getCellulare() {
	return cellulare;
    }

    /**
     * @param cellulare
     *            the cellulare to set
     */
    public void setCellulare(String cellulare) {
	this.cellulare = cellulare;
    }

    /**
     * @return the email
     */
    public String getEmail() {
	return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
	this.email = email;
    }

    /**
     * @return the fax
     */
    public String getFax() {
	return fax;
    }

    /**
     * @param fax
     *            the fax to set
     */
    public void setFax(String fax) {
	this.fax = fax;
    }

    public String getOID() {
	return OID;
    }

    public void setOID(String oid) {
	OID = oid;
    }

    /**
     * @return the piano
     */
    public String getPiano() {
	return piano;
    }

    /**
     * @param piano
     *            the piano to set
     */
    public void setPiano(String piano) {
	this.piano = piano;
    }

}
