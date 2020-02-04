/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

/**
 *
 * @author Gabriele
 */
public class RecapitoDTO {

    private Integer idRecapito;
    private String localita;
    private String presso;
    private String indirizzo;
    private String nCivico;
    private String cap;
    private String casellaPostale;
    private String altreInfoIndirizzo;
    private String telefono;
    private String cellulare;
    private String fax;
    private String email;
    private String pec;
    private String codCivico;
    private String codVia;
    private String internoNumero;
    private String internoLettera;
    private String internoScala;
    private String lettera;
    private String colore;
    private DugDTO idDug;
    private ComuneDTO idComune;

    public Integer getIdRecapito() {
        return idRecapito;
    }

    public void setIdRecapito(Integer idRecapito) {
        this.idRecapito = idRecapito;
    }

    public String getLocalita() {
        return localita;
    }

    public void setLocalita(String localita) {
        this.localita = localita;
    }

    public String getPresso() {
        return presso;
    }

    public void setPresso(String presso) {
        this.presso = presso;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getnCivico() {
        return nCivico;
    }

    public void setnCivico(String nCivico) {
        this.nCivico = nCivico;
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

    public String getAltreInfoIndirizzo() {
        return altreInfoIndirizzo;
    }

    public void setAltreInfoIndirizzo(String altreInfoIndirizzo) {
        this.altreInfoIndirizzo = altreInfoIndirizzo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
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

    public DugDTO getIdDug() {
        return idDug;
    }

    public void setIdDug(DugDTO idDug) {
        this.idDug = idDug;
    }

    public ComuneDTO getIdComune() {
        return idComune;
    }

    public void setIdComune(ComuneDTO idComune) {
        this.idComune = idComune;
    }

    public static class DugDTO {

        private Integer idDug;
        private String descrizione;
        private int codDug;

        public Integer getIdDug() {
            return idDug;
        }

        public void setIdDug(Integer idDug) {
            this.idDug = idDug;
        }

        public String getDescrizione() {
            return descrizione;
        }

        public void setDescrizione(String descrizione) {
            this.descrizione = descrizione;
        }

        public int getCodDug() {
            return codDug;
        }

        public void setCodDug(int codDug) {
            this.codDug = codDug;
        }
    }
}
