/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.aec;

import java.util.List;

/**
 *
 * @author giuseppe
 */
public class ConfigurazioneAnagraficheMultiple {

    private List<String> codiceDichiarazione;
    private List<Anagrafica> anagrafiche;

    public List<String> getCodiceDichiarazione() {
        return codiceDichiarazione;
    }

    public void setCodiceDichiarazione(List<String> codiceDichiarazione) {
        this.codiceDichiarazione = codiceDichiarazione;
    }

    public List<Anagrafica> getAnagrafiche() {
        return anagrafiche;
    }

    public void setAnagrafiche(List<Anagrafica> anagrafiche) {
        this.anagrafiche = anagrafiche;
    }
}
