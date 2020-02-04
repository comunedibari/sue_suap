package it.wego.cross.dto;

import java.util.ArrayList;
import java.util.List;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author piergiorgio
 */
public class AttoriComunicazioneDTO {

    private List<AnagraficaRecapitoDTO> anagrafiche = new ArrayList<AnagraficaRecapitoDTO>();
    private List<EnteDTO> enti = new ArrayList<EnteDTO>();
    private RecapitoDTO notifica;

    public List<AnagraficaRecapitoDTO> getAnagrafiche() {
        return anagrafiche;
    }

    public void setAnagrafiche(List<AnagraficaRecapitoDTO> anagrafiche) {
        this.anagrafiche = anagrafiche;
    }

    public List<EnteDTO> getEnti() {
        return enti;
    }

    public void setEnti(List<EnteDTO> enti) {
        this.enti = enti;
    }

    public RecapitoDTO getNotifica() {
        return notifica;
    }

    public void setNotifica(RecapitoDTO notifica) {
        this.notifica = notifica;
    }
}
