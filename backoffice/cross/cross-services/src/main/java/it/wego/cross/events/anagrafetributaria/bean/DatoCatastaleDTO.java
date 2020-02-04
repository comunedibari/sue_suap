/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.anagrafetributaria.bean;

/**
 *
 * @author Giuseppe
 */
public class DatoCatastaleDTO {

    private Integer tipoCatasto;
    private Integer tipoUnita;
    private Integer tipoParticella;
    private String sezione;
    private String foglio;
    private String particella;
    private String estensioneParticella;
    private String subalterno;

    public Integer getTipoCatasto() {
        return tipoCatasto;
    }

    public void setTipoCatasto(Integer tipoCatasto) {
        this.tipoCatasto = tipoCatasto;
    }

    public Integer getTipoUnita() {
        return tipoUnita;
    }

    public void setTipoUnita(Integer tipoUnita) {
        this.tipoUnita = tipoUnita;
    }

    public Integer getTipoParticella() {
        return tipoParticella;
    }

    public void setTipoParticella(Integer tipoParticella) {
        this.tipoParticella = tipoParticella;
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

    public String getSubalterno() {
        return subalterno;
    }

    public void setSubalterno(String subalterno) {
        this.subalterno = subalterno;
    }

}
