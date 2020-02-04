/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author giuseppe
 */
public class MessaggioDTO implements Serializable {

    private Integer idMessaggio;
    private Integer idMittente;
    private Integer idDestinatario;
    private String nomeMittente;
    private String cognomeMittente;
    private String testo;
    private Date timestamp;

    public Integer getIdMessaggio() {
        return idMessaggio;
    }

    public void setIdMessaggio(Integer idMessaggio) {
        this.idMessaggio = idMessaggio;
    }

    public Integer getIdMittente() {
        return idMittente;
    }

    public void setIdMittente(Integer idMittente) {
        this.idMittente = idMittente;
    }

    public Integer getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(Integer idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public String getNomeMittente() {
        return nomeMittente;
    }

    public void setNomeMittente(String nomeMittente) {
        this.nomeMittente = nomeMittente;
    }

    public String getCognomeMittente() {
        return cognomeMittente;
    }

    public void setCognomeMittente(String cognomeMittente) {
        this.cognomeMittente = cognomeMittente;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
