/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "pratiche_protocollo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PraticheProtocollo.findAll", query = "SELECT p FROM PraticheProtocollo p"),
    @NamedQuery(name = "PraticheProtocollo.findByIdProtocollo", query = "SELECT p FROM PraticheProtocollo p WHERE p.idProtocollo = :idProtocollo"),
    @NamedQuery(name = "PraticheProtocollo.findByAnnoRiferimento", query = "SELECT p FROM PraticheProtocollo p WHERE p.annoRiferimento = :annoRiferimento"),
    @NamedQuery(name = "PraticheProtocollo.findByCodRegistro", query = "SELECT p FROM PraticheProtocollo p WHERE p.codRegistro = :codRegistro"),
    @NamedQuery(name = "PraticheProtocollo.findByNProtocollo", query = "SELECT p FROM PraticheProtocollo p WHERE p.nProtocollo = :nProtocollo"),
    @NamedQuery(name = "PraticheProtocollo.findByOggetto", query = "SELECT p FROM PraticheProtocollo p WHERE p.oggetto = :oggetto"),
    @NamedQuery(name = "PraticheProtocollo.findByIdentificativoPratica", query = "SELECT p FROM PraticheProtocollo p WHERE p.identificativoPratica = :identificativoPratica"),
    @NamedQuery(name = "PraticheProtocollo.findByTipoDocumento", query = "SELECT p FROM PraticheProtocollo p WHERE p.tipoDocumento = :tipoDocumento"),
    @NamedQuery(name = "PraticheProtocollo.findByCodDocumento", query = "SELECT p FROM PraticheProtocollo p WHERE p.codDocumento = :codDocumento"),
    @NamedQuery(name = "PraticheProtocollo.findByEnteRiferimento", query = "SELECT p FROM PraticheProtocollo p WHERE p.enteRiferimento = :enteRiferimento"),
    @NamedQuery(name = "PraticheProtocollo.findByDataRicezione", query = "SELECT p FROM PraticheProtocollo p WHERE p.dataRicezione = :dataRicezione"),
    @NamedQuery(name = "PraticheProtocollo.findByDataProtocollazione", query = "SELECT p FROM PraticheProtocollo p WHERE p.dataProtocollazione = :dataProtocollazione"),
    @NamedQuery(name = "PraticheProtocollo.findByDataPresaInCaricoCross", query = "SELECT p FROM PraticheProtocollo p WHERE p.dataPresaInCaricoCross = :dataPresaInCaricoCross"),
    @NamedQuery(name = "PraticheProtocollo.findByStato", query = "SELECT p FROM PraticheProtocollo p WHERE p.stato = :stato"),
    @NamedQuery(name = "PraticheProtocollo.findByDestinatario", query = "SELECT p FROM PraticheProtocollo p WHERE p.destinatario = :destinatario"),
    @NamedQuery(name = "PraticheProtocollo.findByMittente", query = "SELECT p FROM PraticheProtocollo p WHERE p.mittente = :mittente"),
    @NamedQuery(name = "PraticheProtocollo.findByDataSincronizzazione", query = "SELECT p FROM PraticheProtocollo p WHERE p.dataSincronizzazione = :dataSincronizzazione"),
    @NamedQuery(name = "PraticheProtocollo.findByModalita", query = "SELECT p FROM PraticheProtocollo p WHERE p.modalita = :modalita")})
public class PraticheProtocollo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_protocollo")
    private Integer idProtocollo;
    @Column(name = "anno_riferimento")
    private Integer annoRiferimento;
    @Column(name = "cod_registro")
    private String codRegistro;
    @Column(name = "anno_fascicolo")
    private Integer annoFascicolo;
    @Column(name = "n_protocollo")
    private String nProtocollo;
    @Column(name = "data_fascicolo")
    @Temporal(TemporalType.DATE)
    private Date dataFascicolo;
    @Column(name = "n_fascicolo")
    private String nFascicolo;
    @Column(name = "oggetto")
    private String oggetto;
    @Column(name = "identificativo_pratica")
    private String identificativoPratica;
    @Column(name = "tipo_documento")
    private String tipoDocumento;
    @Column(name = "cod_documento")
    private String codDocumento;
    @Column(name = "ente_riferimento")
    private String enteRiferimento;
    @Column(name = "data_ricezione")
    @Temporal(TemporalType.DATE)
    private Date dataRicezione;
    @Column(name = "data_protocollazione")
    @Temporal(TemporalType.DATE)
    private Date dataProtocollazione;
    @Column(name = "data_presa_in_carico_cross")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPresaInCaricoCross;
    @Column(name = "stato")
    private String stato;
    @Column(name = "destinatario")
    private String destinatario;
    @Column(name = "mittente")
    private String mittente;
    @Column(name = "data_sincronizzazione")
    @Temporal(TemporalType.DATE)
    private Date dataSincronizzazione;
    @Column(name = "modalita")
    private String modalita;
    @JoinColumn(name = "id_utente_presa_in_carico", referencedColumnName = "id_utente")
    @ManyToOne
    private Utente idUtentePresaInCarico;
    @JoinColumn(name = "id_staging", referencedColumnName = "id_staging")
    @ManyToOne
    private Staging idStaging;
    @JoinColumn(name = "id_pratica", referencedColumnName = "id_pratica")
    @ManyToOne
    private Pratica idPratica;
    @JoinColumn(name = "id_mittente_ente", referencedColumnName = "id_ente")
    @ManyToOne
    private Enti idMittenteEnte;
    @JoinColumn(name = "id_mittente_anagrafica", referencedColumnName = "id_anagrafica")
    @ManyToOne
    private Anagrafica idMittenteAnagrafica;
    @JoinColumn(name = "id_pratica_evento", referencedColumnName = "id_pratica_evento")
    @ManyToOne
    private PraticheEventi idPraticaEventoSorgente;

    public PraticheProtocollo() {
    }

    public PraticheProtocollo(Integer idProtocollo) {
        this.idProtocollo = idProtocollo;
    }

    public Integer getIdProtocollo() {
        return idProtocollo;
    }

    public void setIdProtocollo(Integer idProtocollo) {
        this.idProtocollo = idProtocollo;
    }

    public Integer getAnnoRiferimento() {
        return annoRiferimento;
    }

    public void setAnnoRiferimento(Integer annoRiferimento) {
        this.annoRiferimento = annoRiferimento;
    }

    public String getCodRegistro() {
        return codRegistro;
    }

    public void setCodRegistro(String codRegistro) {
        this.codRegistro = codRegistro;
    }

    public Integer getAnnoFascicolo() {
        return annoFascicolo;
    }

    public void setAnnoFascicolo(Integer annoFascicolo) {
        this.annoFascicolo = annoFascicolo;
    }

    public String getNProtocollo() {
        return nProtocollo;
    }

    public void setNProtocollo(String nProtocollo) {
        this.nProtocollo = nProtocollo;
    }

    public String getNFascicolo() {
        return nFascicolo;
    }

    public void setNFascicolo(String nFascicolo) {
        this.nFascicolo = nFascicolo;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getIdentificativoPratica() {
        return identificativoPratica;
    }

    public void setIdentificativoPratica(String identificativoPratica) {
        this.identificativoPratica = identificativoPratica;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    public String getEnteRiferimento() {
        return enteRiferimento;
    }

    public void setEnteRiferimento(String enteRiferimento) {
        this.enteRiferimento = enteRiferimento;
    }

    public Date getDataRicezione() {
        return dataRicezione;
    }

    public void setDataRicezione(Date dataRicezione) {
        this.dataRicezione = dataRicezione;
    }

    public Date getDataProtocollazione() {
        return dataProtocollazione;
    }

    public void setDataProtocollazione(Date dataProtocollazione) {
        this.dataProtocollazione = dataProtocollazione;
    }

    public Date getDataPresaInCaricoCross() {
        return dataPresaInCaricoCross;
    }

    public void setDataPresaInCaricoCross(Date dataPresaInCaricoCross) {
        this.dataPresaInCaricoCross = dataPresaInCaricoCross;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getMittente() {
        return mittente;
    }

    public void setMittente(String mittente) {
        this.mittente = mittente;
    }

    public Date getDataSincronizzazione() {
        return dataSincronizzazione;
    }

    public void setDataSincronizzazione(Date dataSincronizzazione) {
        this.dataSincronizzazione = dataSincronizzazione;
    }

    public String getModalita() {
        return modalita;
    }

    public void setModalita(String modalita) {
        this.modalita = modalita;
    }

    public Utente getIdUtentePresaInCarico() {
        return idUtentePresaInCarico;
    }

    public void setIdUtentePresaInCarico(Utente idUtentePresaInCarico) {
        this.idUtentePresaInCarico = idUtentePresaInCarico;
    }

    public Staging getIdStaging() {
        return idStaging;
    }

    public void setIdStaging(Staging idStaging) {
        this.idStaging = idStaging;
    }

    public Pratica getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Pratica idPratica) {
        this.idPratica = idPratica;
    }

    public Enti getIdMittenteEnte() {
        return idMittenteEnte;
    }

    public void setIdMittenteEnte(Enti idMittenteEnte) {
        this.idMittenteEnte = idMittenteEnte;
    }

    public Anagrafica getIdMittenteAnagrafica() {
        return idMittenteAnagrafica;
    }

    public void setIdMittenteAnagrafica(Anagrafica idMittenteAnagrafica) {
        this.idMittenteAnagrafica = idMittenteAnagrafica;
    }

    public Date getDataFascicolo() {
        return dataFascicolo;
    }

    public void setDataFascicolo(Date dataFascicolo) {
        this.dataFascicolo = dataFascicolo;
    }

    public PraticheEventi getIdPraticaEventoSorgente() {
        return idPraticaEventoSorgente;
    }

    public void setIdPraticaEventoSorgente(PraticheEventi idPraticaEventoSorgente) {
        this.idPraticaEventoSorgente = idPraticaEventoSorgente;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProtocollo != null ? idProtocollo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PraticheProtocollo)) {
            return false;
        }
        PraticheProtocollo other = (PraticheProtocollo) object;
        if ((this.idProtocollo == null && other.idProtocollo != null) || (this.idProtocollo != null && !this.idProtocollo.equals(other.idProtocollo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.PraticheProtocollo[ idProtocollo=" + idProtocollo + " ]";
    }
}
