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
 *
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.people.core;

import java.io.Serializable;

/**
 * @author piancia
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PplUserData implements Serializable {

    private java.lang.String nome;
    private java.lang.String cognome;
    private java.lang.String userPassword = "";
    private java.lang.String userPIN = "";
    private java.lang.String codiceFiscale;
    private java.lang.String indirizzoResidenza;
    private java.lang.String capResidenza;
    private java.lang.String cittaResidenza;
    private java.lang.String provinciaResidenza;
    private java.lang.String statoResidenza;
    private java.lang.String indirizzoDomicilio;
    private java.lang.String capDomicilio;
    private java.lang.String cittaDomicilio;
    private java.lang.String provinciaDomicilio;
    private java.lang.String statoDomicilio;

    private java.lang.String dataNascita;
    private java.lang.String lavoro;
    private java.lang.String luogoNascita;
    private java.lang.String provinciaNascita;
    private java.lang.String sesso;
    private java.lang.String telefono;
    private java.lang.String titolo;
    private java.lang.String emailaddress;

    // Aggiunti per estensioni IDP utilizzate per la definizione accreditamenti
    // dinamici in base al gruppo

    /**
     * Role as specified by the IDP. Can be null.
     */
    private java.lang.String ruolo;

    /**
     * ISTAT code who the role belong. Can be null.
     */
    private java.lang.String territorio;

    /**
     * Flag that state if for the role exists a qualification.
     */
    private boolean ruoloAbilitato = false;

    public java.lang.String getNome() {
	return nome;
    }

    public void setNome(java.lang.String givenName) {
	this.nome = givenName;
    }

    public java.lang.String getCittaDomicilio() {
	return cittaDomicilio;
    }

    public void setCittaDomicilio(java.lang.String localityName) {
	this.cittaDomicilio = localityName;
    }

    public java.lang.String getCapResidenza() {
	return capResidenza;
    }

    public void setCapResidenza(java.lang.String piCap) {
	this.capResidenza = piCap;
    }

    public java.lang.String getCittaResidenza() {
	return cittaResidenza;
    }

    public void setCittaResidenza(java.lang.String piCitta) {
	this.cittaResidenza = piCitta;
    }

    public java.lang.String getCodiceFiscale() {
	return codiceFiscale;
    }

    public void setCodiceFiscale(java.lang.String piCodiceFiscale) {
	this.codiceFiscale = piCodiceFiscale;
    }

    public java.lang.String getDataNascita() {
	return dataNascita;
    }

    public void setDataNascita(java.lang.String dataNascita) {
	this.dataNascita = dataNascita;
    }

    public java.lang.String getLavoro() {
	return lavoro;
    }

    public void setLavoro(java.lang.String piLavoro) {
	this.lavoro = piLavoro;
    }

    public java.lang.String getLuogoNascita() {
	return luogoNascita;
    }

    public void setLuogoNascita(java.lang.String piLuogoNascita) {
	this.luogoNascita = piLuogoNascita;
    }

    public java.lang.String getIndirizzoDomicilio() {
	return indirizzoDomicilio;
    }

    public void setIndirizzoDomicilio(java.lang.String piPostalAddress) {
	this.indirizzoDomicilio = piPostalAddress;
    }

    public java.lang.String getProvinciaResidenza() {
	return provinciaResidenza;
    }

    public void setProvinciaResidenza(java.lang.String piProvincia) {
	this.provinciaResidenza = piProvincia;
    }

    public java.lang.String getProvinciaNascita() {
	return provinciaNascita;
    }

    public void setProvinciaNascita(java.lang.String piProvinciaNascita) {
	this.provinciaNascita = piProvinciaNascita;
    }

    public java.lang.String getIndirizzoResidenza() {
	return indirizzoResidenza;
    }

    public void setIndirizzoResidenza(java.lang.String piResidenza) {
	this.indirizzoResidenza = piResidenza;
    }

    public java.lang.String getSesso() {
	return sesso;
    }

    public void setSesso(java.lang.String piSesso) {
	this.sesso = piSesso;
    }

    public java.lang.String getStatoResidenza() {
	return statoResidenza;
    }

    public void setStatoResidenza(java.lang.String piStato) {
	this.statoResidenza = piStato;
    }

    public java.lang.String getTelefono() {
	return telefono;
    }

    public void setTelefono(java.lang.String piTelefono) {
	this.telefono = piTelefono;
    }

    public java.lang.String getTitolo() {
	return titolo;
    }

    public void setTitolo(java.lang.String piTitolo) {
	this.titolo = piTitolo;
    }

    public java.lang.String getCapDomicilio() {
	return capDomicilio;
    }

    public void setCapDomicilio(java.lang.String postalCode) {
	this.capDomicilio = postalCode;
    }

    public java.lang.String getCognome() {
	return cognome;
    }

    public void setCognome(java.lang.String sn) {
	this.cognome = sn;
    }

    public java.lang.String getProvinciaDomicilio() {
	return provinciaDomicilio;
    }

    public void setProvinciaDomicilio(java.lang.String stateProvinceName) {
	this.provinciaDomicilio = stateProvinceName;
    }

    public java.lang.String getStatoDomicilio() {
	return statoDomicilio;
    }

    public void setStatoDomicilio(java.lang.String piStatoPos) {
	this.statoDomicilio = piStatoPos;
    }

    public String getEmailaddress() {
	return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
	this.emailaddress = emailaddress;
    }

    public java.lang.String getUserPassword() {
	return userPassword;
    }

    public void setUserPassword(java.lang.String userPassword) {
	this.userPassword = userPassword;
    }

    public String getUserPIN() {
	return userPIN;
    }

    public void setUserPIN(String userPIN) {
	this.userPIN = userPIN;
    }

    /**
     * @return the ruolo
     */
    public java.lang.String getRuolo() {
	return this.ruolo;
    }

    /**
     * @param ruolo
     *            the ruolo to set
     */
    public void setRuolo(java.lang.String ruolo) {
	this.ruolo = ruolo;
    }

    /**
     * @return the territorio
     */
    public java.lang.String getTerritorio() {
	return this.territorio;
    }

    /**
     * @param territorio
     *            the territorio to set
     */
    public void setTerritorio(java.lang.String territorio) {
	this.territorio = territorio;
    }

    /**
     * @return the ruoloDefinito
     */
    public boolean isRuoloDefinito() {
	return this.ruolo != null && this.ruolo.length() > 0;
    }

    /**
     * @return the ruoloAbilitato
     */
    public boolean isRuoloAbilitato() {
	return this.ruoloAbilitato;
    }

    /**
     * @param ruoloAbilitato
     *            the ruoloAbilitato to set
     */
    public void setRuoloAbilitato(boolean ruoloAbilitato) {
	this.ruoloAbilitato = ruoloAbilitato;
    }

    public static PplUserData createTestUserData() {
	PplUserData testUserData = new PplUserData();

	testUserData.setNome("nome_utente_test");
	testUserData.setCognome("cognome_utente_test");
	testUserData.setDataNascita("25/06/1970");
	testUserData.setCodiceFiscale("QWEASD70F26F065N");
	testUserData.setEmailaddress("utente_test@peopletest.it");
	testUserData.setCapDomicilio("20100");
	testUserData.setCapResidenza("20101");
	testUserData.setCittaDomicilio("Milano");
	testUserData.setCittaResidenza("Genova");
	testUserData.setIndirizzoDomicilio("Via Milano 1");
	testUserData.setIndirizzoResidenza("Via Genova 1");
	testUserData.setLavoro("Ingegnere");
	testUserData.setLuogoNascita("Milano");
	testUserData.setProvinciaDomicilio("MI");
	testUserData.setProvinciaNascita("MI");
	testUserData.setProvinciaResidenza("GE");
	testUserData.setSesso("M");
	testUserData.setStatoDomicilio("IT");
	testUserData.setStatoResidenza("IT");
	testUserData.setTelefono("022341345");
	testUserData.setTitolo("Ing");
	testUserData.setRuolo("Avvocato");
	testUserData.setTerritorio("000001");

	return testUserData;

    }

    /**
     * toString methode: creates a String representation of the object
     * 
     * @return the String representation
     * @author info.vancauwenberge.tostring plugin
     */
    public String toString() {
	StringBuffer buffer = new StringBuffer();
	buffer.append("PplUserData[");
	buffer.append("nome = ").append(nome);
	buffer.append(", cognome = ").append(cognome);
	buffer.append(", userPassword = ").append(userPassword);
	buffer.append(", userPIN = ").append(userPIN);
	buffer.append(", codiceFiscale = ").append(codiceFiscale);
	buffer.append(", indirizzoResidenza = ").append(indirizzoResidenza);
	buffer.append(", capResidenza = ").append(capResidenza);
	buffer.append(", cittaResidenza = ").append(cittaResidenza);
	buffer.append(", provinciaResidenza = ").append(provinciaResidenza);
	buffer.append(", statoResidenza = ").append(statoResidenza);
	buffer.append(", indirizzoDomicilio = ").append(indirizzoDomicilio);
	buffer.append(", capDomicilio = ").append(capDomicilio);
	buffer.append(", cittaDomicilio = ").append(cittaDomicilio);
	buffer.append(", provinciaDomicilio = ").append(provinciaDomicilio);
	buffer.append(", statoDomicilio = ").append(statoDomicilio);
	buffer.append(", dataNascita = ").append(dataNascita);
	buffer.append(", lavoro = ").append(lavoro);
	buffer.append(", luogoNascita = ").append(luogoNascita);
	buffer.append(", provinciaNascita = ").append(provinciaNascita);
	buffer.append(", sesso = ").append(sesso);
	buffer.append(", telefono = ").append(telefono);
	buffer.append(", titolo = ").append(titolo);
	buffer.append(", emailaddress = ").append(emailaddress);
	buffer.append(", ruolo = ").append(ruolo);
	buffer.append(", territorio = ").append(territorio);
	buffer.append(", ruoloDefinito = ").append(isRuoloDefinito());
	buffer.append(", ruoloAbilitato = ").append(ruoloAbilitato);
	buffer.append("]");
	return buffer.toString();
    }
}
