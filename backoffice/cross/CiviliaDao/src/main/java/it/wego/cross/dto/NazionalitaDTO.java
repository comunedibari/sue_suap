/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto;

/**
 *
 * @author giuseppe
 */
public class NazionalitaDTO {

    private Integer idNazionalita;
    private String codNazionalita;
    private String descrizione;

    public String getCodNazionalita() {
        return codNazionalita;
    }

    public void setCodNazionalita(String codNazionalita) {
        this.codNazionalita = codNazionalita;
    }

    public Integer getIdNazionalita() {
        return idNazionalita;
    }

    public void setIdNazionalita(Integer idNazionalita) {
        this.idNazionalita = idNazionalita;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
