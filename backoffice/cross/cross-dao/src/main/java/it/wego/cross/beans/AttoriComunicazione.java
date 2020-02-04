/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans;

import it.wego.cross.entity.AnagraficaRecapiti;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriele
 */
public class AttoriComunicazione {

    private List<Integer> idAnagraficheRecapiti = new ArrayList<Integer>();
    private List<Integer> idEnti = new ArrayList<Integer>();
    private Integer idRecapitoNotifica;

    public void addIdAnagraficheRecapiti(List<Integer> anagrafiche) {
        for (Integer anagrafica : anagrafiche) {
            addAnagrafica(anagrafica);
        }
    }

    public void addEnti(List<Integer> enti) {
        for (Integer ente : enti) {
            addEnte(ente);
        }
    }

    public void addEnte(Integer ente) {
        if (this.idEnti == null) {
            this.idEnti = new ArrayList<Integer>();
        }
        if (ente != null && !this.idEnti.contains(ente)) {
            this.idEnti.add(ente);
        }
    }

    public void addAnagrafica(Integer anagrafica) {

        if (this.idAnagraficheRecapiti == null) {
            this.idAnagraficheRecapiti = new ArrayList<Integer>();
        }
        if (anagrafica != null && !this.idAnagraficheRecapiti.contains(anagrafica)) {
            this.idAnagraficheRecapiti.add(anagrafica);
        }
    }
    
    public void addAnagrafica(AnagraficaRecapiti anagraficaRecapiti) {

        if (this.idAnagraficheRecapiti == null) {
            this.idAnagraficheRecapiti = new ArrayList<Integer>();
        }
        if (anagraficaRecapiti != null && !this.idAnagraficheRecapiti.contains(anagraficaRecapiti.getIdAnagraficaRecapito())) {
            this.idAnagraficheRecapiti.add(anagraficaRecapiti.getIdAnagraficaRecapito());
        }
    }

    public List<Integer> getIdAnagraficheRecapiti() {
        return idAnagraficheRecapiti;
    }

    public void setIdAnagraficheRecapiti(List<Integer> idAnagraficheRecapiti) {
        this.idAnagraficheRecapiti = idAnagraficheRecapiti;
    }

    public List<Integer> getIdEnti() {
        return idEnti;
    }

    public void setIdEnti(List<Integer> idEnti) {
        this.idEnti = idEnti;
    }

    public Integer getIdRecapitoNotifica() {
        return idRecapitoNotifica;
    }

    public void setIdRecapitoNotifica(Integer idRecapitoNotifica) {
        this.idRecapitoNotifica = idRecapitoNotifica;
    }

}
