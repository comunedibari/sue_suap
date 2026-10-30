/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.comunica;

import java.io.Serializable;

/**
 *
 * @author giuseppe
 */
public class LegaleRappresentanteDTO implements Serializable {

    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String partitaIva;
    private String pec;
    private String nazionalita;
    private String nazionalitaOrigine;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getPec() {
        return pec;
    }

    public void setPec(String pec) {
        this.pec = pec;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public String getNazionalita() {
        return nazionalita;
    }

    public void setNazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
    }

    public String getNazionalitaOrigine() {
        return nazionalitaOrigine;
    }

    public void setNazionalitaOrigine(String nazionalitaOrigine) {
        this.nazionalitaOrigine = nazionalitaOrigine;
    }
}
