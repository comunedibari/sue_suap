/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.anagrafe;

import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.plugins.commons.beans.Anagrafica;

/**
 *
 * @author giuseppe
 */
public interface GestioneAnagrafePersonaFisica {

    public Anagrafica search(String codiceFiscale, Enti ente, LkComuni comune);
    
    public Boolean existRicercaAnagraficaPersonaFisica ( Pratica pratica);
}
