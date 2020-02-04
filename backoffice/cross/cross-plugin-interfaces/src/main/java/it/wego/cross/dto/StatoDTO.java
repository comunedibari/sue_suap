package it.wego.cross.dto;

import it.wego.cross.validator.Alphabetic;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;


/**
 *
 * @author CS
 */
public class StatoDTO extends AbstractDTO implements Serializable{
    private Date dataFine;
    private Date dataInizio;
    @NotNull(message="{error.idProvincie}")
    private Integer idStato;
    private String codIstat;
    private String descrizione;
    
/* ^^CS giuseppe non eliminare
    public String getCodIstat() {
        return codIstat;
    }

    public void setCodIstat(String codIstat) {
        if(this.codIstat!=null && codIstat==null)
        { 
            return;
        }
        this.codIstat = codIstat;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        if(this.dataFine!=null && dataFine==null)
        { 
            return;
        }
        this.dataFine = dataFine;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        if(this.dataInizio!=null && dataInizio==null)
        { 
            return;
        }
        this.dataInizio = dataInizio;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        if(this.descrizione!=null && descrizione==null)
        { 
            return;
        }
        this.descrizione = descrizione;
    }

    public Integer getIdStato() {
        return idStato;
    }

    public void setIdStato(Integer idStato) {
        if(this.idStato!=null && idStato==null)
        { 
            return;
        }
        this.idStato = idStato;
    }*/

    public String getCodIstat() {
        return codIstat;
    }

    public void setCodIstat(String codIstat) {
        this.codIstat = codIstat;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Integer getIdStato() {
        return idStato;
    }

    public void setIdStato(Integer idStato) {
        this.idStato = idStato;
    }
    
}
