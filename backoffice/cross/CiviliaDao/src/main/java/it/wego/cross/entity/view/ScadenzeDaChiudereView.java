/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity.view;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "scadenze_da_chiudere_view")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ScadenzeDaChiudereView.findAll", query = "SELECT s FROM ScadenzeDaChiudereView s"),
    @NamedQuery(name = "ScadenzeDaChiudereView.findByIdScadenza", query = "SELECT s FROM ScadenzeDaChiudereView s WHERE s.idScadenza = :idScadenza"),
    @NamedQuery(name = "ScadenzeDaChiudereView.findByIdEvento", query = "SELECT s FROM ScadenzeDaChiudereView s WHERE s.idEvento = :idEvento"),
    @NamedQuery(name = "ScadenzeDaChiudereView.findByIdPratica", query = "SELECT s FROM ScadenzeDaChiudereView s WHERE s.idPratica = :idPratica"),
    @NamedQuery(name = "ScadenzeDaChiudereView.findByIdAnaScadenza", query = "SELECT s FROM ScadenzeDaChiudereView s WHERE s.idAnaScadenza = :idAnaScadenza"),
    @NamedQuery(name = "ScadenzeDaChiudereView.findByScriptScadenza", query = "SELECT s FROM ScadenzeDaChiudereView s WHERE s.scriptScadenza = :scriptScadenza"),
    @NamedQuery(name = "ScadenzeDaChiudereView.findByDataEventoDaChiudere", query = "SELECT s FROM ScadenzeDaChiudereView s WHERE s.dataEventoDaChiudere = :dataEventoDaChiudere"),
    @NamedQuery(name = "ScadenzeDaChiudereView.findByNote", query = "SELECT s FROM ScadenzeDaChiudereView s WHERE s.note = :note"),
    @NamedQuery(name = "ScadenzeDaChiudereView.findByNumeroProtocollo", query = "SELECT s FROM ScadenzeDaChiudereView s WHERE s.numeroProtocollo = :numeroProtocollo"),
    @NamedQuery(name = "ScadenzeDaChiudereView.findByDataInizioScadenza", query = "SELECT s FROM ScadenzeDaChiudereView s WHERE s.dataInizioScadenza = :dataInizioScadenza"),
    @NamedQuery(name = "ScadenzeDaChiudereView.findByDataFineScadenza", query = "SELECT s FROM ScadenzeDaChiudereView s WHERE s.dataFineScadenza = :dataFineScadenza"),
    @NamedQuery(name = "ScadenzeDaChiudereView.findByDesScadenza", query = "SELECT s FROM ScadenzeDaChiudereView s WHERE s.desScadenza = :desScadenza"),
    @NamedQuery(name = "ScadenzeDaChiudereView.findByDesEventoOrigine", query = "SELECT s FROM ScadenzeDaChiudereView s WHERE s.desEventoOrigine = :desEventoOrigine"),
    @NamedQuery(name = "ScadenzeDaChiudereView.findByDesEnte", query = "SELECT s FROM ScadenzeDaChiudereView s WHERE s.desEnte = :desEnte")})
public class ScadenzeDaChiudereView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "id_scadenza")
    @Id
    private int idScadenza;
    @Basic(optional = false)
    @Column(name = "id_evento")
    private int idEvento;
    @Basic(optional = false)
    @Column(name = "id_pratica")
    private int idPratica;
    @Basic(optional = false)
    @Column(name = "id_ana_scadenza")
    private String idAnaScadenza;
    @Column(name = "script_scadenza")
    private String scriptScadenza;
    @Column(name = "data_evento_da_chiudere")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEventoDaChiudere;
    @Column(name = "note")
    private String note;
    @Column(name = "numero_protocollo")
    private String numeroProtocollo;
    @Basic(optional = false)
    @Column(name = "data_inizio_scadenza")
    @Temporal(TemporalType.DATE)
    private Date dataInizioScadenza;
    @Column(name = "data_fine_scadenza")
    @Temporal(TemporalType.DATE)
    private Date dataFineScadenza;
    @Column(name = "des_scadenza")
    private String desScadenza;
    @Column(name = "des_evento_origine")
    private String desEventoOrigine;
    @Column(name = "des_ente")
    private String desEnte;

    public ScadenzeDaChiudereView() {
    }

    public int getIdScadenza() {
        return idScadenza;
    }

    public void setIdScadenza(int idScadenza) {
        this.idScadenza = idScadenza;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public int getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(int idPratica) {
        this.idPratica = idPratica;
    }

    public String getIdAnaScadenza() {
        return idAnaScadenza;
    }

    public void setIdAnaScadenza(String idAnaScadenza) {
        this.idAnaScadenza = idAnaScadenza;
    }

    public String getScriptScadenza() {
        return scriptScadenza;
    }

    public void setScriptScadenza(String scriptScadenza) {
        this.scriptScadenza = scriptScadenza;
    }

    public Date getDataEventoDaChiudere() {
        return dataEventoDaChiudere;
    }

    public void setDataEventoDaChiudere(Date dataEventoDaChiudere) {
        this.dataEventoDaChiudere = dataEventoDaChiudere;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNumeroProtocollo() {
        return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {
        this.numeroProtocollo = numeroProtocollo;
    }

    public Date getDataInizioScadenza() {
        return dataInizioScadenza;
    }

    public void setDataInizioScadenza(Date dataInizioScadenza) {
        this.dataInizioScadenza = dataInizioScadenza;
    }

    public Date getDataFineScadenza() {
        return dataFineScadenza;
    }

    public void setDataFineScadenza(Date dataFineScadenza) {
        this.dataFineScadenza = dataFineScadenza;
    }

    public String getDesScadenza() {
        return desScadenza;
    }

    public void setDesScadenza(String desScadenza) {
        this.desScadenza = desScadenza;
    }

    public String getDesEventoOrigine() {
        return desEventoOrigine;
    }

    public void setDesEventoOrigine(String desEventoOrigine) {
        this.desEventoOrigine = desEventoOrigine;
    }

    public String getDesEnte() {
        return desEnte;
    }

    public void setDesEnte(String desEnte) {
        this.desEnte = desEnte;
    }
    
}
