/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.protocollo.beans;

import it.wego.cross.plugins.commons.beans.Allegato;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Gabriele
 */
public class DocumentoProtocolloResponse {

    private String idDocumento;
    private String codRegistro;
    private String annoProtocollo;
    private String annoFascicolo;
    private String numeroProtocollo;
    private Date dataProtocollo;
    private Date dataCreazioneFascicolo;
    private String fascicolo;
    private String oggetto;
    private String tipoDocumento;
    private String mittenti;
    private String destinatario;
    //Soggetti "richiedenti"
    private List<SoggettoProtocollo> soggetti;
    //allegati caricati e collegati alla pratica
    private List<Allegato> allegati;
    //è il modello unico
    private Allegato allegatoOriginale;
    private String note;

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getCodRegistro() {
        return codRegistro;
    }

    public void setCodRegistro(String codRegistro) {
        this.codRegistro = codRegistro;
    }

    public String getAnnoProtocollo() {
        return annoProtocollo;
    }

    public void setAnnoProtocollo(String annoProtocollo) {
        this.annoProtocollo = annoProtocollo;
    }

    public String getAnnoFascicolo() {
        return annoFascicolo;
    }

    public void setAnnoFascicolo(String annoFascicolo) {
        this.annoFascicolo = annoFascicolo;
    }

    public String getNumeroProtocollo() {
        return numeroProtocollo;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {
        this.numeroProtocollo = numeroProtocollo;
    }

    public Date getDataProtocollo() {
        return dataProtocollo;
    }

    public void setDataProtocollo(Date dataProtocollo) {
        this.dataProtocollo = dataProtocollo;
    }

    public String getFascicolo() {
        return fascicolo;
    }

    public void setFascicolo(String fascicolo) {
        this.fascicolo = fascicolo;
    }

    public List<SoggettoProtocollo> getSoggetti() {
        return soggetti;
    }

    public void setSoggetti(List<SoggettoProtocollo> soggetti) {
        this.soggetti = soggetti;
    }

    public List<Allegato> getAllegati() {
        return allegati;
    }

    public void setAllegati(List<Allegato> allegati) {
        this.allegati = allegati;
    }

    public Allegato getAllegatoOriginale() {
        return allegatoOriginale;
    }

    public void setAllegatoOriginale(Allegato allegatoOriginale) {
        this.allegatoOriginale = allegatoOriginale;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getMittenti() {
        return mittenti;
    }

    public void setMittenti(String mittenti) {
        this.mittenti = mittenti;
    }

    public Date getDataCreazioneFascicolo() {
        return dataCreazioneFascicolo;
    }

    public void setDataCreazioneFascicolo(Date dataCreazioneFascicolo) {
        this.dataCreazioneFascicolo = dataCreazioneFascicolo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
