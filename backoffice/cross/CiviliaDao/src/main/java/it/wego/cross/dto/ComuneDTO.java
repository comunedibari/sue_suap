package it.wego.cross.dto;

import it.wego.cross.validator.AlphaNumeric;
import java.io.Serializable;
import java.util.Date;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author CS
 */
public class ComuneDTO extends AbstractDTO implements Serializable {

    private Date dataFineValidita;
    @NotNull(message = "{error.codCatastale}")
    private Integer idComune;
    private Integer idEnte;

    private String codCatastale;

    @NotEmpty(message = "{error.descrizione}")
    @NotNull(message = "{error.descrizione}")
    private String descrizione;

    @Valid
    private ProvinciaDTO provincia;
    @Valid
    private StatoDTO stato;
    
    private String flgTavolare;

    public ComuneDTO() {
    }

    public ComuneDTO(Integer idComune) {
        this.idComune = idComune;
    }
    public ComuneDTO(Integer idComune,String descrizione) {
        this.idComune = idComune;
        this.descrizione = descrizione;
    }

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

    public Integer getIdComune() {
        return idComune;
    }

    public void setIdComune(Integer idComune) {
        this.idComune = idComune;
    }

    public ProvinciaDTO getProvincia() {
        return provincia;
    }

    public void setProvincia(ProvinciaDTO provincia) {
        this.provincia = provincia;
    }

    public StatoDTO getStato() {
        return stato;
    }

    public void setStato(StatoDTO stato) {
        this.stato = stato;
    }

    public Boolean equals(ComuneDTO comune) {
        if (comune != null) {
            if (this.getIdComune() != null && this.getIdComune().equals(comune.getIdComune())) {
                return true;
            }
        }
        return false;
    }

    public Integer getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(Integer idEnte) {
        this.idEnte = idEnte;
    }

    public String getFlgTavolare() {
        return flgTavolare;
    }

    public void setFlgTavolare(String flgTavolare) {
        this.flgTavolare = flgTavolare;
    }

}
