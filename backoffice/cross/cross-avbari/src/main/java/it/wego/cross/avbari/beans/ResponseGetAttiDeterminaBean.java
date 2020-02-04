/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.avbari.beans;

/**
 *
 * @author piergiorgio
 */
public class ResponseGetAttiDeterminaBean {

    private Boolean operazioneOK;
    private String messaggio;

       
    public Boolean isOperazioneOK() {
        return operazioneOK;
    }

    public void setOperazioneOK(Boolean operazioneOK) {
        this.operazioneOK = operazioneOK;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }
    
    
}
