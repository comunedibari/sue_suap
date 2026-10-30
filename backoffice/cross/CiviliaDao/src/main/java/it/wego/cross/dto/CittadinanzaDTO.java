package it.wego.cross.dto;

import java.io.Serializable;

/**
 *
 * @author CS
 */
public class CittadinanzaDTO extends AbstractDTO implements Serializable {

    private Integer idCittadinanza;
    private String codCittadinanza;
    private String descrizione;

    public String getCodCittadinanza() {
        return codCittadinanza;
    }

    public void setCodCittadinanza(String codCittadinanza) {
        this.codCittadinanza = codCittadinanza;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Integer getIdCittadinanza() {
        return idCittadinanza;
    }

    public void setIdCittadinanza(Integer idCittadinanza) {
        this.idCittadinanza = idCittadinanza;
    }
}
