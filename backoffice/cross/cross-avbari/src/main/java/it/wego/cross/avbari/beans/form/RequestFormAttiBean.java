/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.avbari.beans.form;

import it.wego.cross.avbari.beans.*;

/**
 *
 * @author piergiorgio
 */
public class RequestFormAttiBean {

    private String annoRiferimento;
    private String numeroRiferimento;
    private Integer idPratica;
    private Integer idEvento;

    public String getAnnoRiferimento() {
        return annoRiferimento;
    }

    public void setAnnoRiferimento(String annoRiferimento) {
        this.annoRiferimento = annoRiferimento;
    }

    public String getNumeroRiferimento() {
        return numeroRiferimento;
    }

    public void setNumeroRiferimento(String numeroRiferimento) {
        this.numeroRiferimento = numeroRiferimento;
    }

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }
    
}
