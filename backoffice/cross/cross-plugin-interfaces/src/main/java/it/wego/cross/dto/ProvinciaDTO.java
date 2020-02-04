package it.wego.cross.dto;

import it.wego.cross.validator.Alphabetic;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author CS
 */
public class ProvinciaDTO extends AbstractDTO implements Serializable {

    private Date dataFineValidita;
    @NotNull(message="{error.idProvincie}")
    @Max(value = 1,message="{error.idProvincie}")
    private Integer idProvincie;

    @NotEmpty(message = "{error.codCatastaleNull}")
    @Max(value = 1,message="{error.codCatastale}")
    private String codCatastale;
    
    private String descrizione;
    
    @NotEmpty(message = "{error.flgInfocamere}")
    @Max(value = 1,message="{error.flgInfocamere}")
    private String flgInfocamere;

    public String getCodCatastale() {
        return codCatastale;
    }

    public void setCodCatastale(String codCatastale) {
        this.codCatastale = codCatastale;
    }

    public Date getDataFineValidita() {
        return dataFineValidita;
    }

    public void setDataFineValidita(Date dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getFlgInfocamere() {
        return flgInfocamere;
    }

    public void setFlgInfocamere(String flgInfocamere) {
        this.flgInfocamere = flgInfocamere;
    }

    public Integer getIdProvincie() {
        return idProvincie;
    }

    public void setIdProvincie(Integer idProvincie) {
        this.idProvincie = idProvincie;
    }

    /** ^^CS giuseppe non eliminare
    public String getCodCatastale() {
        return codCatastale;
    }

    public void setCodCatastale(String codCatastale) {
        if (this.codCatastale != null && codCatastale == null) {
            return;
        }
        this.codCatastale = codCatastale;
    }

    public Date getDataFineValidita() {
        return dataFineValidita;
    }

    public void setDataFineValidita(Date dataFineValidita) {
        if (this.dataFineValidita != null && dataFineValidita == null) {
            return;
        }
        this.dataFineValidita = dataFineValidita;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        if (this.flgInfocamere != null && flgInfocamere == null) {
            return;
        }
        this.descrizione = descrizione;
    }

    public String getFlgInfocamere() {
        return flgInfocamere;
    }

    public void setFlgInfocamere(String flgInfocamere) {
        if (this.flgInfocamere != null && flgInfocamere == null) {
            return;
        }
        this.flgInfocamere = flgInfocamere;
    }

    public Integer getIdProvincie() {
        return idProvincie;
    }

    public void setIdProvincie(Integer idProvincie) {
        if (this.idProvincie != null && idProvincie == null) {
            return;
        }
        this.idProvincie = idProvincie;
    }*/
}
