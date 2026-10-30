/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gabriele
 */
@Entity
@Table(name = "pratiche_diritti_segreteria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PraticheDirittiSegreteria.findAll", query = "SELECT p FROM PraticheDirittiSegreteria p"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByIdPratica", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.idPratica = :idPratica"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByUrbPagamentoUnicaSoluzione", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.urbPagamentoUnicaSoluzione = :urbPagamentoUnicaSoluzione"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByUrbTotale", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.urbTotale = :urbTotale"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByUrbBanca", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.urbBanca = :urbBanca"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByUrbData", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.urbData = :urbData"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByUrbImporto", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.urbImporto = :urbImporto"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByUrbRataunoImporto", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.urbRataunoImporto = :urbRataunoImporto"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByUrbRataunoDataPrevista", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.urbRataunoDataPrevista = :urbRataunoDataPrevista"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByUrbRataunoData", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.urbRataunoData = :urbRataunoData"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByUrbRatadueImporto", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.urbRatadueImporto = :urbRatadueImporto"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByUrbRatadueDataPrevista", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.urbRatadueDataPrevista = :urbRatadueDataPrevista"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByUrbRatadueData", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.urbRatadueData = :urbRatadueData"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByUrbRatatreImporto", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.urbRatatreImporto = :urbRatatreImporto"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByUrbRatatreDataPrevista", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.urbRatatreDataPrevista = :urbRatatreDataPrevista"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByUrbRatatreData", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.urbRatatreData = :urbRatatreData"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByConTotale", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.conTotale = :conTotale"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByConBanca", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.conBanca = :conBanca"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByConData", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.conData = :conData"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByConImporto", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.conImporto = :conImporto"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByConRataunoImporto", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.conRataunoImporto = :conRataunoImporto"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByConRataunoDataPrevista", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.conRataunoDataPrevista = :conRataunoDataPrevista"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByConRataunoData", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.conRataunoData = :conRataunoData"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByConRatadueImporto", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.conRatadueImporto = :conRatadueImporto"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByConRatadueDataPrevista", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.conRatadueDataPrevista = :conRatadueDataPrevista"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByConRatadueData", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.conRatadueData = :conRatadueData"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByTarImportoDovuto", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.tarImportoDovuto = :tarImportoDovuto"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByTarImportoPagato", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.tarImportoPagato = :tarImportoPagato"),
    @NamedQuery(name = "PraticheDirittiSegreteria.findByTarImportoConguaglio", query = "SELECT p FROM PraticheDirittiSegreteria p WHERE p.tarImportoConguaglio = :tarImportoConguaglio")})
public class PraticheDirittiSegreteria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_pratica")
    private Integer idPratica;
    @Column(name = "urb_pagamento_unica_soluzione")
    private String urbPagamentoUnicaSoluzione;
    @Column(name = "urb_totale")
    private String urbTotale;
    @Column(name = "urb_banca")
    private String urbBanca;
    @Column(name = "urb_data")
    private String urbData;
    @Column(name = "urb_importo")
    private String urbImporto;
    @Column(name = "urb_ratauno_importo")
    private String urbRataunoImporto;
    @Column(name = "urb_ratauno_data_prevista")
    private String urbRataunoDataPrevista;
    @Column(name = "urb_ratauno_data")
    private String urbRataunoData;
    @Column(name = "urb_ratadue_importo")
    private String urbRatadueImporto;
    @Column(name = "urb_ratadue_data_prevista")
    private String urbRatadueDataPrevista;
    @Column(name = "urb_ratadue_data")
    private String urbRatadueData;
    @Column(name = "urb_ratatre_importo")
    private String urbRatatreImporto;
    @Column(name = "urb_ratatre_data_prevista")
    private String urbRatatreDataPrevista;
    @Column(name = "urb_ratatre_data")
    private String urbRatatreData;
    @Column(name = "con_pagamento_unica_soluzione")
    private String conPagamentoUnicaSoluzione;
    @Column(name = "con_totale")
    private String conTotale;
    @Column(name = "con_banca")
    private String conBanca;
    @Column(name = "con_data")
    private String conData;
    @Column(name = "con_importo")
    private String conImporto;
    @Column(name = "con_ratauno_importo")
    private String conRataunoImporto;
    @Column(name = "con_ratauno_data_prevista")
    private String conRataunoDataPrevista;
    @Column(name = "con_ratauno_data")
    private String conRataunoData;
    @Column(name = "con_ratadue_importo")
    private String conRatadueImporto;
    @Column(name = "con_ratadue_data_prevista")
    private String conRatadueDataPrevista;
    @Column(name = "con_ratadue_data")
    private String conRatadueData;
    @Column(name = "tar_importo_dovuto")
    private String tarImportoDovuto;
    @Column(name = "tar_importo_pagato")
    private String tarImportoPagato;
    @Column(name = "tar_importo_conguaglio")
    private String tarImportoConguaglio;
    @JoinColumn(name = "id_pratica", referencedColumnName = "id_pratica", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Pratica pratica;

    public PraticheDirittiSegreteria() {
    }

    public PraticheDirittiSegreteria(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public String getUrbPagamentoUnicaSoluzione() {
        return urbPagamentoUnicaSoluzione;
    }

    public void setUrbPagamentoUnicaSoluzione(String urbPagamentoUnicaSoluzione) {
        this.urbPagamentoUnicaSoluzione = urbPagamentoUnicaSoluzione;
    }

    public String getUrbTotale() {
        return urbTotale;
    }

    public void setUrbTotale(String urbTotale) {
        this.urbTotale = urbTotale;
    }

    public String getUrbBanca() {
        return urbBanca;
    }

    public void setUrbBanca(String urbBanca) {
        this.urbBanca = urbBanca;
    }

    public String getUrbData() {
        return urbData;
    }

    public void setUrbData(String urbData) {
        this.urbData = urbData;
    }

    public String getUrbImporto() {
        return urbImporto;
    }

    public void setUrbImporto(String urbImporto) {
        this.urbImporto = urbImporto;
    }

    public String getUrbRataunoImporto() {
        return urbRataunoImporto;
    }

    public void setUrbRataunoImporto(String urbRataunoImporto) {
        this.urbRataunoImporto = urbRataunoImporto;
    }

    public String getUrbRataunoDataPrevista() {
        return urbRataunoDataPrevista;
    }

    public void setUrbRataunoDataPrevista(String urbRataunoDataPrevista) {
        this.urbRataunoDataPrevista = urbRataunoDataPrevista;
    }

    public String getUrbRataunoData() {
        return urbRataunoData;
    }

    public void setUrbRataunoData(String urbRataunoData) {
        this.urbRataunoData = urbRataunoData;
    }

    public String getUrbRatadueImporto() {
        return urbRatadueImporto;
    }

    public void setUrbRatadueImporto(String urbRatadueImporto) {
        this.urbRatadueImporto = urbRatadueImporto;
    }

    public String getUrbRatadueDataPrevista() {
        return urbRatadueDataPrevista;
    }

    public void setUrbRatadueDataPrevista(String urbRatadueDataPrevista) {
        this.urbRatadueDataPrevista = urbRatadueDataPrevista;
    }

    public String getUrbRatadueData() {
        return urbRatadueData;
    }

    public void setUrbRatadueData(String urbRatadueData) {
        this.urbRatadueData = urbRatadueData;
    }

    public String getUrbRatatreImporto() {
        return urbRatatreImporto;
    }

    public void setUrbRatatreImporto(String urbRatatreImporto) {
        this.urbRatatreImporto = urbRatatreImporto;
    }

    public String getUrbRatatreDataPrevista() {
        return urbRatatreDataPrevista;
    }

    public void setUrbRatatreDataPrevista(String urbRatatreDataPrevista) {
        this.urbRatatreDataPrevista = urbRatatreDataPrevista;
    }

    public String getUrbRatatreData() {
        return urbRatatreData;
    }

    public void setUrbRatatreData(String urbRatatreData) {
        this.urbRatatreData = urbRatatreData;
    }

    public String getConPagamentoUnicaSoluzione() {
        return conPagamentoUnicaSoluzione;
    }

    public void setConPagamentoUnicaSoluzione(String conPagamentoUnicaSoluzione) {
        this.conPagamentoUnicaSoluzione = conPagamentoUnicaSoluzione;
    }

    public String getConTotale() {
        return conTotale;
    }

    public void setConTotale(String conTotale) {
        this.conTotale = conTotale;
    }

    public String getConBanca() {
        return conBanca;
    }

    public void setConBanca(String conBanca) {
        this.conBanca = conBanca;
    }

    public String getConData() {
        return conData;
    }

    public void setConData(String conData) {
        this.conData = conData;
    }

    public String getConImporto() {
        return conImporto;
    }

    public void setConImporto(String conImporto) {
        this.conImporto = conImporto;
    }

    public String getConRataunoImporto() {
        return conRataunoImporto;
    }

    public void setConRataunoImporto(String conRataunoImporto) {
        this.conRataunoImporto = conRataunoImporto;
    }

    public String getConRataunoDataPrevista() {
        return conRataunoDataPrevista;
    }

    public void setConRataunoDataPrevista(String conRataunoDataPrevista) {
        this.conRataunoDataPrevista = conRataunoDataPrevista;
    }

    public String getConRataunoData() {
        return conRataunoData;
    }

    public void setConRataunoData(String conRataunoData) {
        this.conRataunoData = conRataunoData;
    }

    public String getConRatadueImporto() {
        return conRatadueImporto;
    }

    public void setConRatadueImporto(String conRatadueImporto) {
        this.conRatadueImporto = conRatadueImporto;
    }

    public String getConRatadueDataPrevista() {
        return conRatadueDataPrevista;
    }

    public void setConRatadueDataPrevista(String conRatadueDataPrevista) {
        this.conRatadueDataPrevista = conRatadueDataPrevista;
    }

    public String getConRatadueData() {
        return conRatadueData;
    }

    public void setConRatadueData(String conRatadueData) {
        this.conRatadueData = conRatadueData;
    }

    public String getTarImportoDovuto() {
        return tarImportoDovuto;
    }

    public void setTarImportoDovuto(String tarImportoDovuto) {
        this.tarImportoDovuto = tarImportoDovuto;
    }

    public String getTarImportoPagato() {
        return tarImportoPagato;
    }

    public void setTarImportoPagato(String tarImportoPagato) {
        this.tarImportoPagato = tarImportoPagato;
    }

    public String getTarImportoConguaglio() {
        return tarImportoConguaglio;
    }

    public void setTarImportoConguaglio(String tarImportoConguaglio) {
        this.tarImportoConguaglio = tarImportoConguaglio;
    }

    public Pratica getPratica() {
        return pratica;
    }

    public void setPratica(Pratica pratica) {
        this.pratica = pratica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPratica != null ? idPratica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PraticheDirittiSegreteria)) {
            return false;
        }
        PraticheDirittiSegreteria other = (PraticheDirittiSegreteria) object;
        if ((this.idPratica == null && other.idPratica != null) || (this.idPratica != null && !this.idPratica.equals(other.idPratica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.PraticheDirittiSegreteria[ idPratica=" + idPratica + " ]";
    }

}
