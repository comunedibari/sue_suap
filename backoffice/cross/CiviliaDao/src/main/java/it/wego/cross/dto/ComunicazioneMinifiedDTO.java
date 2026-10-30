/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto;

/**
 *
 * @author giuseppe
 */
public class ComunicazioneMinifiedDTO {

    private Integer idComunicazione;
    private String descrizione;
    private String ente;
    private String pratica;
    private String utente;
    private String dataComunicazione;

    public Integer getIdComunicazione() {
        return idComunicazione;
    }

    public void setIdComunicazione(Integer idComunicazione) {
        this.idComunicazione = idComunicazione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }

    public String getPratica() {
        return pratica;
    }

    public void setPratica(String pratica) {
        this.pratica = pratica;
    }

    public String getUtente() {
        return utente;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }

    public String getDataComunicazione() {
        return dataComunicazione;
    }

    public void setDataComunicazione(String dataComunicazione) {
        this.dataComunicazione = dataComunicazione;
    }
}
