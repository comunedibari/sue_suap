package it.wego.cross.dto;

import it.wego.cross.validator.AlphaNumeric;
import it.wego.cross.validator.Alphabetic;
import it.wego.cross.validator.Numeric;
import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author CS
 */
public class RecapitoDTO extends AbstractDTO implements Serializable {

    private String presso;
    @NotNull(message = "{error.counterNULL}")
    @Min(value = 1, message = "{error.counter}")
    private Integer counter;
    private Integer codStato;
    private Integer dug;
    @NotNull(message = "{error.idAnagrafica}")
    @Min(value = 1, message = "{error.idAnagrafica}")
    private Integer idAnagrafica;
    private Integer idComune;
    private Integer idDug;
    private Integer idProvincia;
    private Integer idRecapito;
    private Integer idStato;
    private Integer idTipoIndirizzo;
    private String codTipoIndirizzo;
    private String altreInfoIndirizzo;
    @NotEmpty(message = "{error.cap}")
    @Size(min = 5, max = 5, message = "{error.cap5}")
    private String cap;
    private String casellaPostale;
    @Numeric(message = "{error.cellulare}")
    private String cellulare;
    private String descComune;
    private String descDug;
    private String descProvincia;
    private String descStato;
    private String descTipoIndirizzo;
    @Email(message = "{error.email}")
    private String email;
    @Numeric(message = "{error.fax}")
    private String fax;
    private String indirizzo;
    private String localita;
    private String nCivico;
    @Email(message = "{error.pec}")
    private String pec;
    @Numeric(message = "{error.telefono}")
    private String telefono;
    private String codCivico; 
    private String codVia;
    private String internoNumero;
    private String internoLettera;
    private String internoScala;
    private String lettera;
    private String colore;    
//  private List<NotificaAnagrafica> notificaAnagraficaList;

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Integer getCodStato() {
        return codStato;
    }

    public void setCodStato(Integer codStato) {
        this.codStato = codStato;
    }

    public Integer getDug() {
        return dug;
    }

    public void setDug(Integer dug) {
        this.dug = dug;
    }

    public Integer getIdAnagrafica() {
        return idAnagrafica;
    }

    public void setIdAnagrafica(Integer idAnagrafica) {
        this.idAnagrafica = idAnagrafica;
    }

    public Integer getIdComune() {
        return idComune;
    }

    public void setIdComune(Integer idComune) {
        this.idComune = idComune;
    }

    public Integer getIdDug() {
        return idDug;
    }

    public void setIdDug(Integer idDug) {
        this.idDug = idDug;
    }

    public Integer getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(Integer idProvincia) {
        this.idProvincia = idProvincia;
    }

    public Integer getIdRecapito() {
        return idRecapito;
    }

    public void setIdRecapito(Integer idRecapito) {
        this.idRecapito = idRecapito;
    }

    public Integer getIdStato() {
        return idStato;
    }

    public void setIdStato(Integer idStato) {
        this.idStato = idStato;
    }

    public String getCodTipoIndirizzo() {
        return codTipoIndirizzo;
    }

    public void setCodTipoIndirizzo(String codTipoIndirizzo) {
        this.codTipoIndirizzo = codTipoIndirizzo;
    }

    public Integer getIdTipoIndirizzo() {
        return idTipoIndirizzo;
    }

    public void setIdTipoIndirizzo(Integer idTipoIndirizzo) {
        this.idTipoIndirizzo = idTipoIndirizzo;
    }

    public String getAltreInfoIndirizzo() {
        return altreInfoIndirizzo;
    }

    public void setAltreInfoIndirizzo(String altreInfoIndirizzo) {
        this.altreInfoIndirizzo = altreInfoIndirizzo;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCasellaPostale() {
        return casellaPostale;
    }

    public void setCasellaPostale(String casellaPostale) {
        this.casellaPostale = casellaPostale;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public String getDescComune() {
        return descComune;
    }

    public void setDescComune(String descComune) {
        this.descComune = descComune;
    }

    public String getDescDug() {
        return descDug;
    }

    public void setDescDug(String descDug) {
        this.descDug = descDug;
    }

    public String getDescProvincia() {
        return descProvincia;
    }

    public void setDescProvincia(String descProvincia) {
        this.descProvincia = descProvincia;
    }

    public String getDescStato() {
        return descStato;
    }

    public void setDescStato(String descStato) {
        this.descStato = descStato;
    }

    public String getDescTipoIndirizzo() {
        return descTipoIndirizzo;
    }

    public void setDescTipoIndirizzo(String descTipoIndirizzo) {
        this.descTipoIndirizzo = descTipoIndirizzo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getLocalita() {
        return localita;
    }

    public void setLocalita(String localita) {
        this.localita = localita;
    }

    public String getnCivico() {
        return nCivico;
    }

    public void setnCivico(String nCivico) {
        this.nCivico = nCivico;
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

    public String getPresso() {
        return presso;
    }

    public void setPresso(String presso) {
        this.presso = presso;
    }

    public String getCodCivico() {
        return codCivico;
    }

    public void setCodCivico(String codCivico) {
        this.codCivico = codCivico;
    }

    public String getCodVia() {
        return codVia;
    }

    public void setCodVia(String codVia) {
        this.codVia = codVia;
    }

    public String getInternoNumero() {
        return internoNumero;
    }

    public void setInternoNumero(String internoNumero) {
        this.internoNumero = internoNumero;
    }

    public String getInternoLettera() {
        return internoLettera;
    }

    public void setInternoLettera(String internoLettera) {
        this.internoLettera = internoLettera;
    }

    public String getInternoScala() {
        return internoScala;
    }

    public void setInternoScala(String internoScala) {
        this.internoScala = internoScala;
    }

    public String getLettera() {
        return lettera;
    }

    public void setLettera(String lettera) {
        this.lettera = lettera;
    }

    public String getColore() {
        return colore;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }
}
