/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

/**
 *
 * @author Giuseppe
 */
public class LkNazionalitaDTO {

    private Integer idNazionalita;
    private String codNazionalita;
    private String descrizione;

    public Integer getIdNazionalita() {
        return idNazionalita;
    }

    public void setIdNazionalita(Integer idNazionalita) {
        this.idNazionalita = idNazionalita;
    }

    public String getCodNazionalita() {
        return codNazionalita;
    }

    public void setCodNazionalita(String codNazionalita) {
        this.codNazionalita = codNazionalita;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

}
