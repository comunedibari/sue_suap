/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.anagrafetributaria.bean;

import java.io.Serializable;

/**
 *
 * @author giuseppe
 */
public class AnagraficaDTO implements Serializable {

    private Integer idAnagrafica;
    private String descrizione;
    private Integer idQualifica;
    private Integer idTipoCollegio;
    private String codProvinciaIscrizione;
    private String numeroIscrizioneAlbo;

    public Integer getIdAnagrafica() {
        return idAnagrafica;
    }

    public void setIdAnagrafica(Integer idAnagrafica) {
        this.idAnagrafica = idAnagrafica;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Integer getIdQualifica() {
        return idQualifica;
    }

    public void setIdQualifica(Integer idQualifica) {
        this.idQualifica = idQualifica;
    }

    public Integer getIdTipoCollegio() {
        return idTipoCollegio;
    }

    public void setIdTipoCollegio(Integer idTipoCollegio) {
        this.idTipoCollegio = idTipoCollegio;
    }

    public String getNumeroIscrizioneAlbo() {
        return numeroIscrizioneAlbo;
    }

    public void setNumeroIscrizioneAlbo(String numeroIscrizioneAlbo) {
        this.numeroIscrizioneAlbo = numeroIscrizioneAlbo;
    }

    public String getCodProvinciaIscrizione() {
        return codProvinciaIscrizione;
    }

    public void setCodProvinciaIscrizione(String codProvinciaIscrizione) {
        this.codProvinciaIscrizione = codProvinciaIscrizione;
    }

}
