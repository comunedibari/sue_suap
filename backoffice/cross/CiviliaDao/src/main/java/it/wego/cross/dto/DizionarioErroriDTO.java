/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto;

/**
 *
 * @author CS
 */
public class DizionarioErroriDTO {
    private String codErrore;
    private String descrizione;
    private String note;

    public String getCodErrore() {
        return codErrore;
    }

    public void setCodErrore(String codErrore) {
        this.codErrore = codErrore;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
}
