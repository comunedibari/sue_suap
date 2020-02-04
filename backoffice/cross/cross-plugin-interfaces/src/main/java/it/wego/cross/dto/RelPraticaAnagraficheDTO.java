/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author CS
 */
class RelPraticaAnagraficheDTO extends AbstractDTO implements Serializable{
    private int codPratica;
    private int codAna1;
    private int codAna2;
    private int codTipoRuolo;
    private Date dataInizioValidita;
    private String descrizioneTitolarita;
    private Character assensoUsoPec;
    private String pec;
    private String pratica;
    private AnagraficaDTO anagrafica;
    private AnagraficaDTO anagrafica1;
//    private LkTipoQualifica codTipoQualifica;

    public AnagraficaDTO getAnagrafica() {
        return anagrafica;
    }

    public void setAnagrafica(AnagraficaDTO anagrafica) {
        this.anagrafica = anagrafica;
    }

    public AnagraficaDTO getAnagrafica1() {
        return anagrafica1;
    }

    public void setAnagrafica1(AnagraficaDTO anagrafica1) {
        this.anagrafica1 = anagrafica1;
    }

    public Character getAssensoUsoPec() {
        return assensoUsoPec;
    }

    public void setAssensoUsoPec(Character assensoUsoPec) {
        this.assensoUsoPec = assensoUsoPec;
    }

    public int getCodAna1() {
        return codAna1;
    }

    public void setCodAna1(int codAna1) {
        this.codAna1 = codAna1;
    }

    public int getCodAna2() {
        return codAna2;
    }

    public void setCodAna2(int codAna2) {
        this.codAna2 = codAna2;
    }

    public int getCodPratica() {
        return codPratica;
    }

    public void setCodPratica(int codPratica) {
        this.codPratica = codPratica;
    }

    public int getCodTipoRuolo() {
        return codTipoRuolo;
    }

    public void setCodTipoRuolo(int codTipoRuolo) {
        this.codTipoRuolo = codTipoRuolo;
    }

    public Date getDataInizioValidita() {
        return dataInizioValidita;
    }

    public void setDataInizioValidita(Date dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }

    public String getDescrizioneTitolarita() {
        return descrizioneTitolarita;
    }

    public void setDescrizioneTitolarita(String descrizioneTitolarita) {
        this.descrizioneTitolarita = descrizioneTitolarita;
    }

    public String getPec() {
        return pec;
    }

    public void setPec(String pec) {
        this.pec = pec;
    }

    public String getPratica() {
        return pratica;
    }

    public void setPratica(String pratica) {
        this.pratica = pratica;
    }
    
    
}
