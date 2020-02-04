/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "recapiti")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recapiti.findAll", query = "SELECT r FROM Recapiti r"),
    @NamedQuery(name = "Recapiti.findByIdRecapito", query = "SELECT r FROM Recapiti r WHERE r.idRecapito = :idRecapito"),
    @NamedQuery(name = "Recapiti.findByLocalita", query = "SELECT r FROM Recapiti r WHERE r.localita = :localita"),
    @NamedQuery(name = "Recapiti.findByPresso", query = "SELECT r FROM Recapiti r WHERE r.presso = :presso"),
    @NamedQuery(name = "Recapiti.findByIndirizzo", query = "SELECT r FROM Recapiti r WHERE r.indirizzo = :indirizzo"),
    @NamedQuery(name = "Recapiti.findByNCivico", query = "SELECT r FROM Recapiti r WHERE r.nCivico = :nCivico"),
    @NamedQuery(name = "Recapiti.findByCap", query = "SELECT r FROM Recapiti r WHERE r.cap = :cap"),
    @NamedQuery(name = "Recapiti.findByCasellaPostale", query = "SELECT r FROM Recapiti r WHERE r.casellaPostale = :casellaPostale"),
    @NamedQuery(name = "Recapiti.findByAltreInfoIndirizzo", query = "SELECT r FROM Recapiti r WHERE r.altreInfoIndirizzo = :altreInfoIndirizzo"),
    @NamedQuery(name = "Recapiti.findByTelefono", query = "SELECT r FROM Recapiti r WHERE r.telefono = :telefono"),
    @NamedQuery(name = "Recapiti.findByCellulare", query = "SELECT r FROM Recapiti r WHERE r.cellulare = :cellulare"),
    @NamedQuery(name = "Recapiti.findByFax", query = "SELECT r FROM Recapiti r WHERE r.fax = :fax"),
    @NamedQuery(name = "Recapiti.findByEmail", query = "SELECT r FROM Recapiti r WHERE r.email = :email"),
    @NamedQuery(name = "Recapiti.findByPec", query = "SELECT r FROM Recapiti r WHERE r.pec = :pec"),
    @NamedQuery(name = "Recapiti.findByCodCivico", query = "SELECT r FROM Recapiti r WHERE r.codCivico = :codCivico"),
    @NamedQuery(name = "Recapiti.findByCodVia", query = "SELECT r FROM Recapiti r WHERE r.codVia = :codVia"),
    @NamedQuery(name = "Recapiti.findByInternoNumero", query = "SELECT r FROM Recapiti r WHERE r.internoNumero = :internoNumero"),
    @NamedQuery(name = "Recapiti.findByInternoLettera", query = "SELECT r FROM Recapiti r WHERE r.internoLettera = :internoLettera"),
    @NamedQuery(name = "Recapiti.findByInternoScala", query = "SELECT r FROM Recapiti r WHERE r.internoScala = :internoScala"),
    @NamedQuery(name = "Recapiti.findByLettera", query = "SELECT r FROM Recapiti r WHERE r.lettera = :lettera"),
    @NamedQuery(name = "Recapiti.findByColore", query = "SELECT r FROM Recapiti r WHERE r.colore = :colore")})
public class Recapiti implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_recapito")
    private Integer idRecapito;
    @Column(name = "localita")
    private String localita;
    @Column(name = "presso")
    private String presso;
    @Column(name = "indirizzo")
    private String indirizzo;
    @Column(name = "n_civico")
    private String nCivico;
    @Column(name = "cap")
    private String cap;
    @Column(name = "casella_postale")
    private String casellaPostale;
    @Column(name = "altre_info_indirizzo")
    private String altreInfoIndirizzo;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "cellulare")
    private String cellulare;
    @Column(name = "fax")
    private String fax;
    @Column(name = "email")
    private String email;
    @Column(name = "pec")
    private String pec;
    @Column(name = "cod_civico")
    private String codCivico;
    @Column(name = "cod_via")
    private String codVia;
    @Column(name = "interno_numero")
    private String internoNumero;
    @Column(name = "interno_lettera")
    private String internoLettera;
    @Column(name = "interno_scala")
    private String internoScala;
    @Column(name = "lettera")
    private String lettera;
    @Column(name = "colore")
    private String colore;
    @OneToMany(mappedBy = "idRecapito")
    private List<Pratica> praticaList;
    @OneToMany(mappedBy = "idRecapitoNotifica")
    private List<PraticaAnagrafica> praticaAnagraficaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRecapito")
    private List<AnagraficaRecapiti> anagraficaRecapitiList;
    @JoinColumn(name = "id_dug", referencedColumnName = "id_dug")
    @ManyToOne
    private LkDug idDug;
    @JoinColumn(name = "id_comune", referencedColumnName = "id_comune")
    @ManyToOne
    private LkComuni idComune;
    @OneToMany(mappedBy = "idRecapito")
    private List<PraticheEventiAnagrafiche> praticheEventiAnagraficheList;
    @OneToMany(mappedBy = "idRecapitoNotifica")
    private List<PraticheEventi> praticheEventiList;

    public Recapiti() {
    }

    public Recapiti(Integer idRecapito) {
        this.idRecapito = idRecapito;
    }

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

    public String getNCivico() {
        return nCivico;
    }

    public void setNCivico(String nCivico) {
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

    @XmlTransient
    public List<Pratica> getPraticaList() {
        return praticaList;
    }

    public void setPraticaList(List<Pratica> praticaList) {
        this.praticaList = praticaList;
    }

    @XmlTransient
    public List<PraticaAnagrafica> getPraticaAnagraficaList() {
        return praticaAnagraficaList;
    }

    public void setPraticaAnagraficaList(List<PraticaAnagrafica> praticaAnagraficaList) {
        this.praticaAnagraficaList = praticaAnagraficaList;
    }

    @XmlTransient
    public List<AnagraficaRecapiti> getAnagraficaRecapitiList() {
        return anagraficaRecapitiList;
    }

    public void setAnagraficaRecapitiList(List<AnagraficaRecapiti> anagraficaRecapitiList) {
        this.anagraficaRecapitiList = anagraficaRecapitiList;
    }

    public LkDug getIdDug() {
        return idDug;
    }

    public void setIdDug(LkDug idDug) {
        this.idDug = idDug;
    }

    public LkComuni getIdComune() {
        return idComune;
    }

    public void setIdComune(LkComuni idComune) {
        this.idComune = idComune;
    }

    @XmlTransient
    public List<PraticheEventiAnagrafiche> getPraticheEventiAnagraficheList() {
        return praticheEventiAnagraficheList;
    }

    public void setPraticheEventiAnagraficheList(List<PraticheEventiAnagrafiche> praticheEventiAnagraficheList) {
        this.praticheEventiAnagraficheList = praticheEventiAnagraficheList;
    }

    @XmlTransient
    public List<PraticheEventi> getPraticheEventiList() {
        return praticheEventiList;
    }

    public void setPraticheEventiList(List<PraticheEventi> praticheEventiList) {
        this.praticheEventiList = praticheEventiList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRecapito != null ? idRecapito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recapiti)) {
            return false;
        }
        Recapiti other = (Recapiti) object;
        if ((this.idRecapito == null && other.idRecapito != null) || (this.idRecapito != null && !this.idRecapito.equals(other.idRecapito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.Recapiti[ idRecapito=" + idRecapito + " ]";
    }
    
}
