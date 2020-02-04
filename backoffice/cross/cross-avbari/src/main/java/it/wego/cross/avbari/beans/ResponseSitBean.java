/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.avbari.beans;

/**
 *
 * @author piergiorgio
 */
public class ResponseSitBean {

    private String operazione;
    private String esito;
    private String messaggio;
    private Boolean operazioneOK;

    public String getOperazione() {
        return operazione;
    }

    public void setOperazione(String operazione) {
        this.operazione = operazione;
    }

    public String getEsito() {
        return esito;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

    public Boolean isOperazioneOK() {
        return operazioneOK;
    }

    public void setOperazioneOK(Boolean operazioneOK) {
        this.operazioneOK = operazioneOK;
    }
    
    
}
