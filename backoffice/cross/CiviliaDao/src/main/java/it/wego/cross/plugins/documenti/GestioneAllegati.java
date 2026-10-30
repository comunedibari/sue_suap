/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.documenti;

import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.plugins.commons.beans.Allegato;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public interface GestioneAllegati {

    /**
     * Carica l'allegato all'interno del sistema di gestione documentale
     *
     * @param allegato Oggetto rappresentante l'allegato
     * @param ente
     * @param comune
     * @throws java.lang.Exception
     */
    public abstract void add(Allegati allegato, Enti ente, LkComuni comune) throws Exception;

    /**
     * Ritorna il file fisico relativo all'allegato passato
     *
     * @param allegato L'oggetto salvato in banca dati
     * @param ente
     * @param comune
     * @return il contenuto del file
     * @throws java.lang.Exception
     */
    public abstract byte[] getFileContent(Allegati allegato, Enti ente, LkComuni comune) throws Exception;
    
    public Allegato getFile(String idFileEsterno, Enti ente, LkComuni comune) throws Exception;
    
    /**
     * Esegue il caricamento del file su un sistema documentale. Il metodo viene utilizzato dall'evento di upload file.
     * 
     * @param pratica la pratica a cui gli allegati saranno collegati
     * @param allegati I file da salvare
     * @return 
     * @throws Exception 
     */
    public String uploadFile(Pratica pratica, List<Allegati> allegati) throws Exception;
 
    /**
     * Carica l'allegato su u servizio dedicato alla visualizzazione dello stato di avanzamento delle pratiche
     * 
     * @param pratica La pratica a cui l'allegato fa riferimento
     * @param allegati Gli allegati da caricare
     * @return L'esito della operazione
     * @throws Exception 
     */
    public String uploadAttachmentsOnMyPage(Pratica pratica, List<it.wego.cross.entity.Allegati> allegati) throws Exception;
}
