/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author piergiorgio
 */
@Entity
@Table(name = "indirizzi_intervento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IndirizziIntervento.findAll", query = "SELECT i FROM IndirizziIntervento i"),
    @NamedQuery(name = "IndirizziIntervento.findByIdIndirizzoIntervento", query = "SELECT i FROM IndirizziIntervento i WHERE i.idIndirizzoIntervento = :idIndirizzoIntervento"),
    @NamedQuery(name = "IndirizziIntervento.findByLocalita", query = "SELECT i FROM IndirizziIntervento i WHERE i.localita = :localita"),
    @NamedQuery(name = "IndirizziIntervento.findByIndirizzo", query = "SELECT i FROM IndirizziIntervento i WHERE i.indirizzo = :indirizzo"),
    @NamedQuery(name = "IndirizziIntervento.findByCivico", query = "SELECT i FROM IndirizziIntervento i WHERE i.civico = :civico"),
    @NamedQuery(name = "IndirizziIntervento.findByCap", query = "SELECT i FROM IndirizziIntervento i WHERE i.cap = :cap"),
    @NamedQuery(name = "IndirizziIntervento.findByAltreInformazioniIndirizzo", query = "SELECT i FROM IndirizziIntervento i WHERE i.altreInformazioniIndirizzo = :altreInformazioniIndirizzo"),
    @NamedQuery(name = "IndirizziIntervento.findByCodCivico", query = "SELECT i FROM IndirizziIntervento i WHERE i.codCivico = :codCivico"),
    @NamedQuery(name = "IndirizziIntervento.findByCodVia", query = "SELECT i FROM IndirizziIntervento i WHERE i.codVia = :codVia"),
    @NamedQuery(name = "IndirizziIntervento.findByInternoNumero", query = "SELECT i FROM IndirizziIntervento i WHERE i.internoNumero = :internoNumero"),
    @NamedQuery(name = "IndirizziIntervento.findByInternoLettera", query = "SELECT i FROM IndirizziIntervento i WHERE i.internoLettera = :internoLettera"),
    @NamedQuery(name = "IndirizziIntervento.findByInternoScala", query = "SELECT i FROM IndirizziIntervento i WHERE i.internoScala = :internoScala"),
    @NamedQuery(name = "IndirizziIntervento.findByLettera", query = "SELECT i FROM IndirizziIntervento i WHERE i.lettera = :lettera"),
    @NamedQuery(name = "IndirizziIntervento.findByColore", query = "SELECT i FROM IndirizziIntervento i WHERE i.colore = :colore"),
    @NamedQuery(name = "IndirizziIntervento.findByLatitudine", query = "SELECT i FROM IndirizziIntervento i WHERE i.latitudine = :latitudine"),
    @NamedQuery(name = "IndirizziIntervento.findByLongitudine", query = "SELECT i FROM IndirizziIntervento i WHERE i.longitudine = :longitudine"),
    @NamedQuery(name = "IndirizziIntervento.findByDatoEsteso1", query = "SELECT i FROM IndirizziIntervento i WHERE i.datoEsteso1 = :datoEsteso1"),
    @NamedQuery(name = "IndirizziIntervento.findByDatoEsteso2", query = "SELECT i FROM IndirizziIntervento i WHERE i.datoEsteso2 = :datoEsteso2"),
    @NamedQuery(name = "IndirizziIntervento.findByPiano", query = "SELECT i FROM IndirizziIntervento i WHERE i.piano = :piano")})
public class IndirizziIntervento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_indirizzo_intervento")
    private Integer idIndirizzoIntervento;
    @Size(max = 255)
    @Column(name = "localita")
    private String localita;
    @Size(max = 255)
    @Column(name = "indirizzo")
    private String indirizzo;
    @Size(max = 45)
    @Column(name = "civico")
    private String civico;
    @Size(max = 20)
    @Column(name = "cap")
    private String cap;
    @Size(max = 255)
    @Column(name = "altre_informazioni_indirizzo")
    private String altreInformazioniIndirizzo;
    @Size(max = 15)
    @Column(name = "cod_civico")
    private String codCivico;
    @Size(max = 15)
    @Column(name = "cod_via")
    private String codVia;
    @Size(max = 15)
    @Column(name = "interno_numero")
    private String internoNumero;
    @Size(max = 15)
    @Column(name = "interno_lettera")
    private String internoLettera;
    @Size(max = 15)
    @Column(name = "interno_scala")
    private String internoScala;
    @Size(max = 15)
    @Column(name = "lettera")
    private String lettera;
    @Size(max = 15)
    @Column(name = "colore")
    private String colore;
    @Column(name = "latitudine")
    private String latitudine;
    @Column(name = "longitudine")
    private String longitudine;
    @Column(name = "dato_esteso_1")
    private String datoEsteso1;
    @Column(name = "dato_esteso_2")
    private String datoEsteso2;
    @Column(name = "piano")
    private String piano;
    @JoinColumn(name = "id_dug", referencedColumnName = "id_dug")
    @ManyToOne
    private LkDug idDug;
    @JoinColumn(name = "id_pratica", referencedColumnName = "id_pratica")
    @ManyToOne(optional = false)
    private Pratica idPratica;

    public IndirizziIntervento() {
    }

    public IndirizziIntervento(Integer idIndirizzoIntervento) {
        this.idIndirizzoIntervento = idIndirizzoIntervento;
    }

    public Integer getIdIndirizzoIntervento() {
        return idIndirizzoIntervento;
    }

    public void setIdIndirizzoIntervento(Integer idIndirizzoIntervento) {
        this.idIndirizzoIntervento = idIndirizzoIntervento;
    }

    public String getLocalita() {
        return localita;
    }

    public void setLocalita(String localita) {
        this.localita = localita;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getAltreInformazioniIndirizzo() {
        return altreInformazioniIndirizzo;
    }

    public void setAltreInformazioniIndirizzo(String altreInformazioniIndirizzo) {
        this.altreInformazioniIndirizzo = altreInformazioniIndirizzo;
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

    public LkDug getIdDug() {
        return idDug;
    }

    public void setIdDug(LkDug idDug) {
        this.idDug = idDug;
    }

    public Pratica getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Pratica idPratica) {
        this.idPratica = idPratica;
    }

    public String getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(String latitudine) {
        this.latitudine = latitudine;
    }

    public String getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(String longitudine) {
        this.longitudine = longitudine;
    }

    public String getDatoEsteso1() {
        return datoEsteso1;
    }

    public void setDatoEsteso1(String datoEsteso1) {
        this.datoEsteso1 = datoEsteso1;
    }

    public String getDatoEsteso2() {
        return datoEsteso2;
    }

    public void setDatoEsteso2(String datoEsteso2) {
        this.datoEsteso2 = datoEsteso2;
    }

    public String getPiano() {
        return piano;
    }

    public void setPiano(String piano) {
        this.piano = piano;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIndirizzoIntervento != null ? idIndirizzoIntervento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IndirizziIntervento)) {
            return false;
        }
        IndirizziIntervento other = (IndirizziIntervento) object;
        if ((this.idIndirizzoIntervento == null && other.idIndirizzoIntervento != null) || (this.idIndirizzoIntervento != null && !this.idIndirizzoIntervento.equals(other.idIndirizzoIntervento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.IndirizziIntervento[ idIndirizzoIntervento=" + idIndirizzoIntervento + " ]";
    }
}
