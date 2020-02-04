/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.webservices.cxf.interoperability;

/**
 *
 * @author giuseppe
 */
public class Soggetto {

    private String codiceFiscale;
    private String codice;
    private TipoSoggetto tipoSoggetto;

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public TipoSoggetto getTipoSoggetto() {
        return tipoSoggetto;
    }

    public void setTipoSoggetto(TipoSoggetto tipoSoggetto) {
        this.tipoSoggetto = tipoSoggetto;
    }
}
