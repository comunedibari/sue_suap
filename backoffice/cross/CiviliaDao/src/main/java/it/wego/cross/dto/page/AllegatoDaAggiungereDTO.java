/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.page;

/**
 * serve a SalvaEvento
 * @author CS
 */
public class AllegatoDaAggiungereDTO {
    private String tipoFile;
    private String idFileEsterno;
    private String nomeFile;
    private  String descrizione;

    public String getTipoFile() {
        return tipoFile;
    }

    public void setTipoFile(String tipoFile) {
        this.tipoFile = tipoFile;
    }

    public String getIdFileEsterno() {
        return idFileEsterno;
    }

    public void setIdFileEsterno(String idFileEsterno) {
        this.idFileEsterno = idFileEsterno;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    
}
