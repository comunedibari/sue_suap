/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans;

import it.wego.cross.entity.Errori;

/**
 *
 * @author giuseppe
 */
public class ErroreBean {

    private boolean correct;
    private Errori errore;

    public ErroreBean() {
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Errori getErrore() {
        return errore;
    }

    public void setErrore(Errori errore) {
        this.errore = errore;
    }
}
