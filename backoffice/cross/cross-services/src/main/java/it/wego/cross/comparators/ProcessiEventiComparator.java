/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.wego.cross.comparators;

import it.wego.cross.entity.ProcessiEventi;
import java.util.Comparator;

/**
 *
 * @author Giuseppe
 */
public class ProcessiEventiComparator implements Comparator<ProcessiEventi> {

    @Override
    public int compare(ProcessiEventi pe1, ProcessiEventi pe2) {
        String evento1 = pe1.getDesEvento();
        String evento2 = pe2.getDesEvento();
        return evento1.compareTo(evento2);
    }
    
}
