/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.pratica;

import it.wego.cross.dto.dozer.DatiCatastaliDTO;
import it.wego.cross.dto.dozer.IndirizzoInterventoDTO;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Procedimenti;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public interface GestionePratica {

    /**
     * Genera l'oggetto Pratica, rappresentante la richiesta gestita da CROSS
     *
     * @param pratica Oggetto rappresentante la pratica. Verrà gestito
     * direttamente dall'implementazione
     * @return
     * @throws java.lang.Exception
     */
    public it.wego.cross.xml.Pratica execute(Object pratica) throws Exception;

    /**
     * Ritorna l'oggetto della pratica
     *
     * @param pratica Oggetto rappresentante la pratica. Verrà gestito
     * direttamente dall'implementazione
     * @return La stringa contenente l'oggetto della pratica
     * @throws Exception
     */
    public String getOggettoPratica(Object pratica) throws Exception;

    /**
     * Ritorna l'identificativo della pratica generato dal front end
     *
     * @param pratica Oggetto rappresentante la pratica. Verrà gestito
     * direttamente dall'implementazione
     * @return
     * @returnIdentificativo assegnato dal front
     * @throws Exception
     */
    public String getIdentificativoPratica(Object pratica) throws Exception;

    /**
     * Ritorna gli allegati presenti nell'XML della pratica ricevuta
     *
     * @param pratica La pratica ricevuta dal sistema di front
     * @return Gli allegati inviati con la pratica
     * @throws Exception
     */
    public it.wego.cross.xml.Allegati getAllegati(Object pratica) throws Exception;

    /**
     * Ritorna il procedimento di riferimento della pratica
     *
     * @param procedimenti I procedimenti collegati alla singola pratica
     * @return Il procedimenti di riferimento (SUAP, Sportello, ...) a cui
     * collegare la pratica
     */
    public it.wego.cross.entity.Procedimenti getProcedimentoRiferimento(List<Procedimenti> procedimenti);

    public String notifica(Pratica pratica, String descrizioneEvento) throws Exception;

    public String getUrlCatasto(Object dato, Pratica pratica) throws Exception;

    public String getUrlCatastoIndirizzo(Object dato, Pratica pratica) throws Exception;

    public List<IndirizzoInterventoDTO> getDatiToponomastica(IndirizzoInterventoDTO indirizzoInterventoDTO, Pratica pratica) throws Exception;
    
    public List<DatiCatastaliDTO> getDatiCatastali(DatiCatastaliDTO datiCatastaliDTO, Pratica pratica) throws Exception;

    public List<String> controllaDatoCatastale(Object datoInput, Integer idEnte) throws Exception;

    public List<String> controllaIndirizzoIntervento(Object datoInput, Integer idEnte) throws Exception;

    public String getEsistenzaStradario(Pratica pratica) throws Exception;
    
    public String getEsistenzaRicercaCatasto(Pratica pratica) throws Exception;

    public void postCreazionePratica(Pratica pratica, String data) throws Exception;

}
