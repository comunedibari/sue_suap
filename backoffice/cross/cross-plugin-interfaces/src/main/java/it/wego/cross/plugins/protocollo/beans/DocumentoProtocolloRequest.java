/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.protocollo.beans;

import it.wego.cross.plugins.commons.beans.Allegato;
import java.util.List;

/**
 *
 * @author Gabriele
 */
public class DocumentoProtocolloRequest {

    private List<SoggettoProtocollo> soggetti;
    private List<Allegato> allegati;
    private Allegato allegatoOriginale;
    private String tipoDoc;
    private String oggetto;
    private String source;
    private String aoo;
    private String direzione;
    private String uo;
    private String titolario;
    private String classifica;
    private Integer annoFascicolo;
    private String codiceFascicolo;
    private String identificativoPratica;
    private Integer idPratica;
    private Integer idPraticaEvento;
    private Integer idEnte;
    private Integer idComune;
    private String amministrazione;

    public String getCodiceFascicolo() {
        return codiceFascicolo;
    }

    public void setCodiceFascicolo(String codiceFascicolo) {
        this.codiceFascicolo = codiceFascicolo;
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

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAoo() {
        return aoo;
    }

    public void setAoo(String aoo) {
        this.aoo = aoo;
    }

    public String getDirezione() {
        return direzione;
    }

    public void setDirezione(String direzione) {
        this.direzione = direzione;
    }

    public String getUo() {
        return uo;
    }

    public void setUo(String uo) {
        this.uo = uo;
    }

    public String getTitolario() {
        return titolario;
    }

    public void setTitolario(String titolario) {
        this.titolario = titolario;
    }

    public String getClassifica() {
        return classifica;
    }

    public void setClassifica(String classifica) {
        this.classifica = classifica;
    }

    public Integer getAnnoFascicolo() {
        return annoFascicolo;
    }

    public void setAnnoFascicolo(Integer annoFascicolo) {
        this.annoFascicolo = annoFascicolo;
    }

    public Allegato getAllegatoOriginale() {
        return allegatoOriginale;
    }

    public void setAllegatoOriginale(Allegato allegatoOriginale) {
        this.allegatoOriginale = allegatoOriginale;
    }

    public String getIdentificativoPratica() {
        return identificativoPratica;
    }

    public void setIdentificativoPratica(String identificativoPratica) {
        this.identificativoPratica = identificativoPratica;
    }

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public Integer getIdPraticaEvento() {
        return idPraticaEvento;
    }

    public void setIdPraticaEvento(Integer idPraticaEvento) {
        this.idPraticaEvento = idPraticaEvento;
    }

    public Integer getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(Integer idEnte) {
        this.idEnte = idEnte;
    }

    public Integer getIdComune() {
        return idComune;
    }

    public void setIdComune(Integer idComune) {
        this.idComune = idComune;
    }

    public String getAmministrazione() {
        return amministrazione;
    }

    public void setAmministrazione(String amministrazione) {
        this.amministrazione = amministrazione;
    }

}
