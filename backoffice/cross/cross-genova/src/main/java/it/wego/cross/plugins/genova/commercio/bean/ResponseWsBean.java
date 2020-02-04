/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.genova.commercio.bean;

/**
 *
 * @author piergiorgio
 */
public class ResponseWsBean {
    
    private Boolean operazioneOK;
    private String messaggio;
    private String idPraticaBO;

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

    public String getIdPraticaBO() {
        return idPraticaBO;
    }

    public void setIdPraticaBO(String idPraticaBO) {
        this.idPraticaBO = idPraticaBO;
    }
    
    
}
