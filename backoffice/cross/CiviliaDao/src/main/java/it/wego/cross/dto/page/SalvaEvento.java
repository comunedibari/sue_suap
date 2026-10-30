/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.page;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author CS
 */
public class SalvaEvento {
    private List<MultipartFile> files;
    private List<String> allegatoEmail;
    private List<String> descrizioneFile;
    private List<String> allegatoNuovoEmail;
    private AllegatoDaAggiungereDTO allegatoDaAggiungere;
    private Boolean inviaEmail;
    private Boolean visualizzaEvento;
    private Boolean visualizzaPortale;
    private Integer idEvento;
    private String destinatari;
    private String oggetto;
    private String contenuto;
    private String note;
    private String numeroprotocollo;
    private String procedimentoRiferimento;
    
    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public List<String> getDescrizioneFile() {
        return descrizioneFile;
    }

    public void setDescrizioneFile(List<String> descrizioneFile) {
        this.descrizioneFile = descrizioneFile;
    }

    public List<String> getAllegatoNuovoEmail() {
        return allegatoNuovoEmail;
    }

    public void setAllegatoNuovoEmail(List<String> allegatoNuovoEmail) {
        this.allegatoNuovoEmail = allegatoNuovoEmail;
    }

    public AllegatoDaAggiungereDTO getAllegatoDaAggiungere() {
        return allegatoDaAggiungere;
    }

    public void setAllegatoDaAggiungere(AllegatoDaAggiungereDTO allegatoDaAggiungere) {
        this.allegatoDaAggiungere = allegatoDaAggiungere;
    }

    public Boolean getInviaEmail() {
        return inviaEmail;
    }

    public void setInviaEmail(Boolean inviaEmail) {
        this.inviaEmail = inviaEmail;
    }

    public Boolean getVisualizzaEvento() {
        return visualizzaEvento;
    }

    public void setVisualizzaEvento(Boolean visualizzaEvento) {
        this.visualizzaEvento = visualizzaEvento;
    }

    public Boolean getVisualizzaPortale() {
        return visualizzaPortale;
    }

    public void setVisualizzaPortale(Boolean visualizzaPortale) {
        this.visualizzaPortale = visualizzaPortale;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getDestinatari() {
        return destinatari;
    }

    public void setDestinatari(String destinatari) {
        this.destinatari = destinatari;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getContenuto() {
        return contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNumeroprotocollo() {
        return numeroprotocollo;
    }

    public void setNumeroprotocollo(String numeroprotocollo) {
        this.numeroprotocollo = numeroprotocollo;
    }

    public String getProcedimentoRiferimento() {
        return procedimentoRiferimento;
    }

    public void setProcedimentoRiferimento(String procedimentoRiferimento) {
        this.procedimentoRiferimento = procedimentoRiferimento;
    }

    public List<String> getAllegatoEmail() {
        return allegatoEmail;
    }

    public void setAllegatoEmail(List<String> allegatoEmail) {
        this.allegatoEmail = allegatoEmail;
    }


    
}
