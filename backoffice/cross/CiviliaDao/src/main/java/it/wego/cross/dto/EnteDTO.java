package it.wego.cross.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class EnteDTO extends AbstractDTO implements Serializable {

    @NotNull(message = "{error.EnteDTO.idEnte}")
    private Integer idEnte;
    @NotNull(message = "{error.EnteDTO.codEnte}")
    @NotEmpty(message = "{error.EnteDTO.codEnte}")
    private String codEnte;
    @NotNull(message = "{error.EnteDTO.descrizione}")
    @NotEmpty(message = "{error.EnteDTO.descrizione}")
    private String descrizione;
    @NotNull(message = "{error.EnteDTO.citta}")
    @NotEmpty(message = "{error.EnteDTO.citta}")
    private String citta;
    @NotNull(message = "{error.provincia}")
    @NotEmpty(message = "{error.provincia}")
    private String provincia;
    @NotNull(message = "{error.email}")
    @NotEmpty(message = "{error.email}")
    @Email(message = "{error.email}")
    private String email;
    @NotNull(message = "{error.pec}")
    @NotEmpty(message = "{error.pec}")
    @Email(message = "{error.pec}")
    private String pec;
    private String telefono;
    private String codiceIstat;
    private String codiceCatastale;
    private String identificativoSuap;
    private String codiceFiscale;
    private String partitaIva;
    private String indirizzo;
    private Integer cap;
    private String fax;
    @NotNull(message = "{error.EnteDTO.unitaOrganizzativa}")
    @NotEmpty(message = "{error.EnteDTO.unitaOrganizzativa}")
    private String unitaOrganizzativa;
    @NotNull(message = "{error.EnteDTO.tipo}")
    @NotEmpty(message = "{error.EnteDTO.tipo}")
    private String tipoEnte;
    private Boolean checked = false;
    @NotNull(message = "{error.EnteDTO.codaoo}")
    @NotEmpty(message = "{error.EnteDTO.codaoo}")
    private String codiceAoo;
    private String codiceAmministrazione;

    public String getUnitaOrganizzativa() {
        return unitaOrganizzativa;
    }

    public void setUnitaOrganizzativa(String unitaOrganizzativa) {
        this.unitaOrganizzativa = unitaOrganizzativa;
    }

    public String getCodiceAoo() {
        return codiceAoo;
    }

    public void setCodiceAoo(String codiceAoo) {
        this.codiceAoo = codiceAoo;
    }

    public Integer getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(Integer idEnte) {
        this.idEnte = idEnte;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPec() {
        return pec;
    }

    public void setPec(String pec) {
        this.pec = pec;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getCodiceIstat() {
        return codiceIstat;
    }

    public void setCodiceIstat(String codiceIstat) {
        this.codiceIstat = codiceIstat;
    }

    public String getCodiceCatastale() {
        return codiceCatastale;
    }

    public void setCodiceCatastale(String codiceCatastale) {
        this.codiceCatastale = codiceCatastale;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public String getCodEnte() {
        return codEnte;
    }

    public void setCodEnte(String codEnte) {
        this.codEnte = codEnte;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public Integer getCap() {
        return cap;
    }

    public void setCap(Integer cap) {
        this.cap = cap;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTipoEnte() {
        return tipoEnte;
    }

    public void setTipoEnte(String tipoEnte) {
        this.tipoEnte = tipoEnte;
    }

    public String getIdentificativoSuap() {
        return identificativoSuap;
    }

    public void setIdentificativoSuap(String identificativoSuap) {
        this.identificativoSuap = identificativoSuap;
    }

    public String getCodiceAmministrazione() {
        return codiceAmministrazione;
    }

    public void setCodiceAmministrazione(String codiceAmministrazione) {
        this.codiceAmministrazione = codiceAmministrazione;
    }
}
