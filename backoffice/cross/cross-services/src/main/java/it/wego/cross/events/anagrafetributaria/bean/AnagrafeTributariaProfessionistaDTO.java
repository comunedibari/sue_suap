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
public class AnagrafeTributariaProfessionistaDTO implements Serializable {

    private Integer idAnagrafica;
    private String alboProfessionale;
    private String provinciaAlbo;
    private String numeroIscrizioneAlbo;
    private String qualificaProfessionista;

    public Integer getIdAnagrafica() {
        return idAnagrafica;
    }

    public void setIdAnagrafica(Integer idAnagrafica) {
        this.idAnagrafica = idAnagrafica;
    }

    public String getAlboProfessionale() {
        return alboProfessionale;
    }

    public void setAlboProfessionale(String alboProfessionale) {
        this.alboProfessionale = alboProfessionale;
    }

    public String getProvinciaAlbo() {
        return provinciaAlbo;
    }

    public void setProvinciaAlbo(String provinciaAlbo) {
        this.provinciaAlbo = provinciaAlbo;
    }

    public String getNumeroIscrizioneAlbo() {
        return numeroIscrizioneAlbo;
    }

    public void setNumeroIscrizioneAlbo(String numeroIscrizioneAlbo) {
        this.numeroIscrizioneAlbo = numeroIscrizioneAlbo;
    }

    public String getQualificaProfessionista() {
        return qualificaProfessionista;
    }

    public void setQualificaProfessionista(String qualificaProfessionista) {
        this.qualificaProfessionista = qualificaProfessionista;
    }
}
