package it.wego.cross.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author CS
 */
public class NotaDTO extends AbstractDTO implements Serializable {
    private Integer idPratica; 
    private Integer idNota; 
    private Integer idUtente; 
    private Date  dataInserimento; 
    private String  testo; 
    private String desUtente; 
    private String testoBreve; 

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public Integer getIdNota() {
        return idNota;
    }

    public void setIdNota(Integer idNota) {
        this.idNota = idNota;
    }

    public Integer getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
    }

    public Date getDataInserimento() {
        return dataInserimento;
    }

    public void setDataInserimento(Date dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getDesUtente() {
        return desUtente;
    }

    public void setDesUtente(String desUtente) {
        this.desUtente = desUtente;
    }

    public String getTestoBreve() {
        return testoBreve;
    }

    public void setTestoBreve(String testoBreve) {
        this.testoBreve = testoBreve;
    }
}
