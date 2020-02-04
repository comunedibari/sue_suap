/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.anagrafe;

import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.entity.Pratica;
import it.wego.cross.plugins.commons.beans.Anagrafica;

/**
 *
 * @author giuseppe
 */
public interface GestioneAnagrafePersonaGiuridica {
    
    public Anagrafica search(String codiceFiscale, String partitaIva);
    
    public Boolean existRicercaAnagraficaPersonaGiuridica ( Pratica pratica);
}
