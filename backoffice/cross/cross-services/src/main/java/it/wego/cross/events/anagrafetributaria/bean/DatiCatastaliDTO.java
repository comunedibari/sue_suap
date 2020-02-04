/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.anagrafetributaria.bean;

import java.io.Serializable;

/**
 *
 * @author Gabriele
 */
public class DatiCatastaliDTO implements Serializable {

    private Integer idTipoUnita;
    private String desTipoCatasto;
    private String sezione;
    private String foglio;
    private String particella;
    private String estensioneParticella;
    private Integer idTipologiaParticella;
    private String subalterno;
    private String mappale;

    public Integer getIdTipoUnita() {
        return idTipoUnita;
    }

    public void setIdTipoUnita(Integer idTipoUnita) {
        this.idTipoUnita = idTipoUnita;
    }

    public String getDesTipoCatasto() {
        return desTipoCatasto;
    }

    public void setDesTipoCatasto(String desTipoCatasto) {
        this.desTipoCatasto = desTipoCatasto;
    }

    public String getSezione() {
        return sezione;
    }

    public void setSezione(String sezione) {
        this.sezione = sezione;
    }

    public String getFoglio() {
        return foglio;
    }

    public void setFoglio(String foglio) {
        this.foglio = foglio;
    }

    public String getParticella() {
        return particella;
    }

    public void setParticella(String particella) {
        this.particella = particella;
    }

    public String getEstensioneParticella() {
        return estensioneParticella;
    }

    public void setEstensioneParticella(String estensioneParticella) {
        this.estensioneParticella = estensioneParticella;
    }

    public Integer getIdTipologiaParticella() {
        return idTipologiaParticella;
    }

    public void setIdTipologiaParticella(Integer idTipologiaParticella) {
        this.idTipologiaParticella = idTipologiaParticella;
    }

    public String getSubalterno() {
        return subalterno;
    }

    public void setSubalterno(String subalterno) {
        this.subalterno = subalterno;
    }

    public String getMappale() {
        return mappale;
    }

    public void setMappale(String mappale) {
        this.mappale = mappale;
    }

}
