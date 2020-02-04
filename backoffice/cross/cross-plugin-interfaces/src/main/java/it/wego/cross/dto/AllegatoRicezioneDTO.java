/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto;

import it.wego.cross.entity.Allegati;
import java.io.Serializable;

/**
 *
 * @author giuseppe
 */
public class AllegatoRicezioneDTO implements Serializable {

    private Boolean modelloDomanda;
    private Allegati allegato;
    private Boolean send = Boolean.FALSE;

    public Boolean isModelloDomanda() {
        return modelloDomanda;
    }

    public void setModelloDomanda(String modelloDomanda) {
        this.modelloDomanda = modelloDomanda != null && modelloDomanda.equalsIgnoreCase("S");
    }

    public Allegati getAllegato() {
        return allegato;
    }

    public void setAllegato(Allegati allegato) {
        this.allegato = allegato;
    }

    public Boolean isSend() {
        return send;
    }

    public void setSend(Boolean send) {
        this.send = send;
    }

}
