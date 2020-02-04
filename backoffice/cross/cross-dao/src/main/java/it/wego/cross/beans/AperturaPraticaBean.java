/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabri
 */
public class AperturaPraticaBean extends WorkFlowBean {

    String note = "";
    String valutazione = "";
    boolean corretto = false;
    List allegatiCheck = new ArrayList();

    public boolean isCorretto() {
        return corretto;
    }

    public void setCorretto(boolean corretto) {
        this.corretto = corretto;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getValutazione() {
        return valutazione;
    }

    public void setValutazione(String valutazione) {
        this.valutazione = valutazione;
    }

    public boolean getCheck(String check) {
        return allegatiCheck.contains(check);
    }

    public void addCheck(String check) {
        allegatiCheck.add(check);
    }

    public List getAllegatiCheck() {
        return allegatiCheck;
    }

    public void setAllegatiCheck(List allegatiCheck) {
        this.allegatiCheck = allegatiCheck;
    }

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("----PARAMETRI PRATICA----\n");
//        sb.append("CodSportello = ").append(this.getCodSportello()).append("\r\n");
//        sb.append("IdPratica = ").append(this.getIdPratica()).append("\r\n");
//        sb.append("Aoo = ").append(this.getAoo()).append("\r\n");
//        sb.append("Numero = ").append(this.getNumero()).append("\r\n");
//        sb.append("Comune = ").append(this.getComune()).append("\r\n");
//        sb.append("Data = ").append(this.getData()).append("\r\n");
////        sb.append("Documento = ").append(this.getDocumento()).append("\r\n");
//        sb.append("DocumentoFileName = ").append(this.getDocumentoFileName()).append("\r\n");
//        sb.append("DocumentoType = ").append(this.getDocumentoType()).append("\r\n");
////        sb.append("Visibilita = ").append(this.getVisibilita()).append("\r\n");
////        sb.append("CodiceProcedimento = ").append(this.getCodiceProcedimento()).append("\r\n");
//        sb.append("DataAvvioEvento = ").append(this.getDataAvvioEvento()).append("\r\n");
//        sb.append("Note = ").append(this.getNote()).append("\r\n");
//        sb.append("Valutazione = ").append(this.getValutazione()).append("\r\n");
//        sb.append("IsCorretto = ").append(this.isCorretto()).append("\r\n");
//
//        return sb.toString();
//    }
}
