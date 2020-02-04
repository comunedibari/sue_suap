/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans;

import it.eng.people.connects.interfaces.protocollo.beans.IdentificatoreDiProtocollo;

/**
 *
 * @author Giuseppe
 */
public class EGrammataIdentificatoreDiProtocollo {

    private String message;
    private IdentificatoreDiProtocollo identificatoreDiProtocollo;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public IdentificatoreDiProtocollo getIdentificatoreDiProtocollo() {
        return identificatoreDiProtocollo;
    }

    public void setIdentificatoreDiProtocollo(IdentificatoreDiProtocollo identificatoreDiProtocollo) {
        this.identificatoreDiProtocollo = identificatoreDiProtocollo;
    }

}
