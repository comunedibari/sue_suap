/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans;

/**
 *
 * @author Giuseppe
 */
public class AllegatoBean {

    private String idAllegato;
    private String nomeFile;
    private String descrizioneOggetto;
    private String versione;
    private Boolean primario = Boolean.FALSE;

    public String getIdAllegato() {
        return idAllegato;
    }

    public void setIdAllegato(String idAllegato) {
        this.idAllegato = idAllegato;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public String getDescrizioneOggetto() {
        return descrizioneOggetto;
    }

    public void setDescrizioneOggetto(String descrizioneOggetto) {
        this.descrizioneOggetto = descrizioneOggetto;
    }

    public String getVersione() {
        return versione;
    }

    public void setVersione(String versione) {
        this.versione = versione;
    }

    public Boolean isPrimario() {
        return primario;
    }

    public void setPrimario(Boolean primario) {
        this.primario = primario;
    }

}
