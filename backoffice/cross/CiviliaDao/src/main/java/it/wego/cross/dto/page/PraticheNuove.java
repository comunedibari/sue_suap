/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.page;

import it.wego.cross.dto.PraticaNuova;
import java.util.ArrayList;
import java.util.List;

/**
 * ^^CS ELIMINA
 * @author CS
 */
public class PraticheNuove extends Page
{
    private List <PraticaNuova> pratiche = new ArrayList <PraticaNuova>();

    public List<PraticaNuova> getPratiche() {
        return pratiche;
    }

    public void setPratiche(List<PraticaNuova> pratiche) {
        this.pratiche = pratiche;
    }
}
