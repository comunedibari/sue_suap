/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.page;

import java.util.ArrayList;
import java.util.List;

/**
 * ^^CS ELIMINA
 * @author CS
 */
abstract class Page {
    private List<String> messaggiErrore = new ArrayList<String>();
    private List<String> messaggiOK = new ArrayList<String>();

    public List<String> getMessaggiErrore() {
        return messaggiErrore;
    }

    public void setMessaggiErrore(List<String> messaggiErrore) {
        this.messaggiErrore = messaggiErrore;
    }

    public List<String> getMessaggiOK() {
        return messaggiOK;
    }

    public void setMessaggiOK(List<String> messaggiOK) {
        this.messaggiOK = messaggiOK;
    }
    
    /**
     * 
     * @aythor CS 
     */
    public void addMessaggioOK(String ok) {
        this.getMessaggiOK().add(ok);
    }
    public void addMessaggioErrore(String errore) {
        this.getMessaggiErrore().add(errore);
    }

}
