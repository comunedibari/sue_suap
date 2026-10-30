/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.commons.beans;

import java.util.Date;

/**
 *
 * @author giuseppe
 */
public class Anagrafica {

    private String tipoAnagrafica;
    private String cognome;
    private String nome;
    private String codiceFiscale;
    private String denominazione;
    private String partitaIva;
    private Boolean isDittaIndividuale;
    private Date dataNascita;
    private String cittadinanza;
    private String comuneNascita;
    private String localitaNascita;
    private String sesso;
    //Dati professionista
    private String collegio;
    private Date dataIscrizione;
    private String provinciaIscrizione;
    private String numeroIscrizione;
    //Registro imprese
    private String provinciaRI;
    private Boolean inAttesaIscrizioneRI;
    private String numeroIscrizioneRI;
    private Date dataIscrizioneRI;
    //REA
    private String numeroIscrizioneRea;
    private Boolean inAttesaIscrizioneRea;
    private Date dataIscrizioneRea;
    private String formaGiuridica;
    //Recapito
    private Recapito recapito;

    public String getTipoAnagrafica() {
        return tipoAnagrafica;
    }

    public void setTipoAnagrafica(String tipoAnagrafica) {
        this.tipoAnagrafica = tipoAnagrafica;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public Boolean getIsDittaIndividuale() {
        return isDittaIndividuale;
    }

    public void setIsDittaIndividuale(Boolean isDittaIndividuale) {
        this.isDittaIndividuale = isDittaIndividuale;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getCittadinanza() {
        return cittadinanza;
    }

    public void setCittadinanza(String cittadinanza) {
        this.cittadinanza = cittadinanza;
    }

    public String getComuneNascita() {
        return comuneNascita;
    }

    public void setComuneNascita(String comuneNascita) {
        this.comuneNascita = comuneNascita;
    }

    public String getLocalitaNascita() {
        return localitaNascita;
    }

    public void setLocalitaNascita(String localitaNascita) {
        this.localitaNascita = localitaNascita;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getCollegio() {
        return collegio;
    }

    public void setCollegio(String collegio) {
        this.collegio = collegio;
    }

    public String getNumeroIscrizione() {
        return numeroIscrizione;
    }

    public void setNumeroIscrizione(String numeroIscrizione) {
        this.numeroIscrizione = numeroIscrizione;
    }

    public Date getDataIscrizione() {
        return dataIscrizione;
    }

    public void setDataIscrizione(Date dataIscrizione) {
        this.dataIscrizione = dataIscrizione;
    }

    public String getProvinciaIscrizione() {
        return provinciaIscrizione;
    }

    public void setProvinciaIscrizione(String provinciaIscrizione) {
        this.provinciaIscrizione = provinciaIscrizione;
    }

    public String getProvinciaRI() {
        return provinciaRI;
    }

    public void setProvinciaRI(String provinciaCCIAA) {
        this.provinciaRI = provinciaCCIAA;
    }

    public Boolean getInAttesaIscrizioneRI() {
        return inAttesaIscrizioneRI;
    }

    public Date getDataIscrizioneRI() {
        return dataIscrizioneRI;
    }

    public void setDataIscrizioneRI(Date dataIscrizioneRI) {
        this.dataIscrizioneRI = dataIscrizioneRI;
    }

    public void setInAttesaIscrizioneRI(Boolean inAttesaIscrizioneRI) {
        this.inAttesaIscrizioneRI = inAttesaIscrizioneRI;
    }

    public String getNumeroIscrizioneRI() {
        return numeroIscrizioneRI;
    }

    public void setNumeroIscrizioneRI(String numeroIscrizioneRI) {
        this.numeroIscrizioneRI = numeroIscrizioneRI;
    }

    public String getNumeroIscrizioneRea() {
        return numeroIscrizioneRea;
    }

    public void setNumeroIscrizioneRea(String numeroIscrizioneRea) {
        this.numeroIscrizioneRea = numeroIscrizioneRea;
    }

    public Boolean getInAttesaIscrizioneRea() {
        return inAttesaIscrizioneRea;
    }

    public void setInAttesaIscrizioneRea(Boolean inAttesaIscrizioneRea) {
        this.inAttesaIscrizioneRea = inAttesaIscrizioneRea;
    }

    public Date getDataIscrizioneRea() {
        return dataIscrizioneRea;
    }

    public void setDataIscrizioneRea(Date dataIscrizioneRea) {
        this.dataIscrizioneRea = dataIscrizioneRea;
    }

    public String getFormaGiuridica() {
        return formaGiuridica;
    }

    public void setFormaGiuridica(String formaGiuridica) {
        this.formaGiuridica = formaGiuridica;
    }

    public Recapito getRecapito() {
        return recapito;
    }

    public void setRecapito(Recapito recapito) {
        this.recapito = recapito;
    }
}
