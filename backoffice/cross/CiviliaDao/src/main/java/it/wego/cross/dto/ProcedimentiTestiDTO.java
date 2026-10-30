package it.wego.cross.dto;

import java.io.Serializable;

/**
 *
 * @author CS
 */
public class ProcedimentiTestiDTO implements Serializable {
    Integer idProcedimento;
    Integer idLingua;
    String codLingua;
    String descrizione;

    public Integer getIdProcedimento() {
        return idProcedimento;
    }

    public void setIdProcedimento(Integer idProcedimento) {
        this.idProcedimento = idProcedimento;
    }

    public Integer getIdLingua() {
        return idLingua;
    }

    public void setIdLingua(Integer idLingua) {
        this.idLingua = idLingua;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }   

    public String getCodLingua() {
        return codLingua;
    }

    public void setCodLingua(String codLingua) {
        this.codLingua = codLingua;
    }
    
}
