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

import java.util.ArrayList;

import it.people.fsl.servizi.oggetticondivisi.luogo.Indirizzo;
import it.people.fsl.servizi.oggetticondivisi.luogo.Luogo;
import it.people.fsl.servizi.oggetticondivisi.tipibase.Data;

public class PersonaFisica extends Persona {

    public PersonaFisica() {
	this.recapito = new ArrayList();
	this.titoloPersonale = new ArrayList();
	this.luogodiNascita = new Luogo();
    }

    /**
     * 
     * @return possible object is {@link java.lang.String}
     */
    private java.lang.String sesso;

    /**
     * 
     * @return possible object is {@link java.lang.String}
     */
    private java.lang.String nomeSecondario;

    /**
     * 
     * @return possible object is {@link java.lang.String}
     */
    private java.lang.String cognome;

    /**
     * 
     * @return possible object is
     *         {@link it.diviana.egov.oggetticondivisiegov.Persona.LuogodiNascitaType}
     */
    private Luogo luogodiNascita;

    /**
     * 
     * @return possible object is {@link java.lang.String}
     */
    private java.lang.String codiceFiscale;

    /**
     * 
     * @return possible object is {@link java.util.Calendar}
     */
    private Data datadiNascita;

    /**
     * 
     * @return possible object is {@link java.lang.String}
     */
    private java.lang.String nome;

    /**
     * Gets the value of the TitoloPersonale property.
     * 
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the TitoloPersonale property.
     * 
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTitoloPersonale().add(newItem);
     * </pre>
     * 
     * 
     * Objects of the following type(s) are allowed in the list
     * {@link java.lang.String}
     * 
     */
    private java.util.List titoloPersonale;

    /**
     * 
     * @return possible object is
     *         {@link it.diviana.egov.oggetticondivisiegov.PersonaFisica.ResidenzaType}
     */
    private Indirizzo residenza;

    /**
     * Gets the value of the Recapito property.
     * 
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the Recapito property.
     * 
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getRecapito().add(newItem);
     * </pre>
     * 
     * 
     * Objects of the following type(s) are allowed in the list
     * {@link it.diviana.egov.oggetticondivisiegovrecapito.WebWeb}
     * {@link it.diviana.egov.oggetticondivisiegovrecapito.RecapitoPostaleRecapitoPostale}
     * {@link it.diviana.egov.oggetticondivisiegovrecapito.FaxFax}
     * {@link it.diviana.egov.oggetticondivisiegovrecapito.InternetInternet}
     * {@link it.diviana.egov.oggetticondivisiegovrecapito.RecapitoRecapito}
     * {@link it.diviana.egov.oggetticondivisiegovrecapito.TelefonoTelefono}
     * {@link it.diviana.egov.oggetticondivisiegovrecapito.CellulareCellulare}
     * {@link it.diviana.egov.oggetticondivisiegovrecapito.EmailEmail}
     * 
     */
    private java.util.List recapito;

    /**
     * 
     * @return possible object is
     *         {@link it.diviana.egov.oggetticondivisiegov.PersonaFisica.DomicilioType}
     */
    private Indirizzo domicilio;

    /**
     * 
     * @return possible object is
     *         {@link it.diviana.egov.oggetticondivisiegov.Cittadinanza}
     */
    private String cittadinanza;

    public String gradodiParentela;

    /**
     * @return
     */
    public String getCittadinanza() {
	return cittadinanza;
    }

    /**
     * @return
     */
    public java.lang.String getCodiceFiscale() {
	return codiceFiscale;
    }

    /**
     * @return
     */
    public java.lang.String getCognome() {
	return cognome;
    }

    /**
     * @return
     */
    public Data getDatadiNascita() {
	return datadiNascita;
    }

    /**
     * @return
     */
    public Indirizzo getDomicilio() {
	return domicilio;
    }

    /**
     * @return
     */
    public Luogo getLuogodiNascita() {
	return luogodiNascita;
    }

    /**
     * @return
     */
    public java.lang.String getNome() {
	return nome;
    }

    /**
     * @return
     */
    public java.lang.String getNomeSecondario() {
	return nomeSecondario;
    }

    /**
     * @return
     */
    public java.util.List getRecapito() {
	return recapito;
    }

    /**
     * @return
     */
    public Indirizzo getResidenza() {
	return residenza;
    }

    /**
     * @return
     */
    public java.lang.String getSesso() {
	return sesso;
    }

    /**
     * @return
     */
    public java.util.List getTitoloPersonale() {
	return titoloPersonale;
    }

    /**
     * @param string
     */
    public void setCittadinanza(String string) {
	cittadinanza = string;
    }

    /**
     * @param string
     */
    public void setCodiceFiscale(java.lang.String string) {
	codiceFiscale = string;
    }

    /**
     * @param string
     */
    public void setCognome(java.lang.String string) {
	cognome = string;
    }

    /**
     * @param value
     */
    public void setDatadiNascita(Data value) {
	datadiNascita = value;
    }

    /**
     * @param indirizzo
     */
    public void setDomicilio(Indirizzo indirizzo) {
	domicilio = indirizzo;
    }

    /**
     * @param luogo
     */
    public void setLuogodiNascita(Luogo luogo) {
	luogodiNascita = luogo;
    }

    /**
     * @param string
     */
    public void setNome(java.lang.String string) {
	nome = string;
    }

    /**
     * @param string
     */
    public void setNomeSecondario(java.lang.String string) {
	nomeSecondario = string;
    }

    /**
     * @param list
     */
    public void setRecapito(java.util.List list) {
	recapito = list;
    }

    public void addRecapito(String r) {
	recapito.add(r);
    }

    /**
     * @param indirizzo
     */
    public void setResidenza(Indirizzo indirizzo) {
	residenza = indirizzo;
    }

    /**
     * @param string
     */
    public void setSesso(java.lang.String string) {
	sesso = string;
    }

    /**
     * @param list
     */
    public void setTitoloPersonale(java.util.List list) {
	titoloPersonale = list;
    }

    public void addTitoloPersonale(String t) {
	titoloPersonale.add(t);
    }

    public String getGradodiParentela() {
	return gradodiParentela;
    }

    public void setGradodiParentela(String gradodiParentela) {
	this.gradodiParentela = gradodiParentela;
    }

    @Override
    public String toString() {

	return new StringBuilder().append("Nome: " + this.getNome())
		.append("Cognome: " + this.getCognome())
		.append("Codice fiscale: " + this.getCodiceFiscale())
		.toString();

    }

}
