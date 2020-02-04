/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 * 
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.BeanUtils;

public class SezioneCompilabileBean extends BaseBean implements Serializable {

    private static final long serialVersionUID = -1065355482566463459L;
    private String titolo;
    private String href;
    private boolean complete;
    private String link;
    private int tdCount;
    private ArrayList campi;//list di HrefCampiBean
    private int rowCount;
    private String piedeHref;
    //private boolean processed;
    private String html;
    private String htmlEditable;
    private boolean visible;
    private int lastRowCampoMultiplo;
    private int firstRowCampoMultiplo;
    private int numSezioniMultiple;
    // PC - ordinamento allegato
    private Integer ordinamento;
    private Integer ordinamentoIntervento;
    // PC - ordinamento allegato

    public SezioneCompilabileBean() {
        this.titolo = "";
        this.href = "";
        this.complete = false;
        this.link = "";
        this.tdCount = 0;
        this.campi = new ArrayList();
        this.rowCount = 0;
        this.piedeHref = "";
        this.html = "";
        this.htmlEditable = "";
        this.visible = false;
        this.numSezioniMultiple = 0;
        this.lastRowCampoMultiplo = 0;
        this.firstRowCampoMultiplo = 0;
        // PC - ordinamento allegato
        this.ordinamento = 0;
        this.ordinamentoIntervento = 0;
        // PC - ordinamento allegato
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getTdCount() {
        return tdCount;
    }

    public void setTdCount(int tdCount) {
        this.tdCount = tdCount;
    }

    public ArrayList getCampi() {
        return campi;
    }

    public void setCampi(ArrayList campi) {
        this.campi = campi;
    }

    public void addCampi(HrefCampiBean campi) {
        this.campi.add(campi);
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public String getPiedeHref() {
        return piedeHref;
    }

    public void setPiedeHref(String piedeHref) {
        this.piedeHref = piedeHref;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getHtmlEditable() {
        return htmlEditable;
    }
    // PC - ordinamento allegato

    public void setOrdinamento(Integer ordinamento) {
        this.ordinamento = ordinamento;
    }

    public Integer getOrdinamento() {
        return ordinamento;
    }

    public void setOrdinamentoIntervento(Integer ordinamentoIntervento) {
        this.ordinamentoIntervento = ordinamentoIntervento;
    }

    public Integer getOrdinamentoIntervento() {
        return ordinamentoIntervento;
    }
    // PC - ordinamento allegato

    public void setHtmlEditable(String htmlEditable) {
        this.htmlEditable = htmlEditable;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getLastRowCampoMultiplo() {
        return lastRowCampoMultiplo;
    }

    public void setLastRowCampoMultiplo(int lastRowCampoMultiplo) {
        this.lastRowCampoMultiplo = lastRowCampoMultiplo;
    }

    public int getFirstRowCampoMultiplo() {
        return firstRowCampoMultiplo;
    }

    public void setFirstRowCampoMultiplo(int firstRowCampoMultiplo) {
        this.firstRowCampoMultiplo = firstRowCampoMultiplo;
    }

    public int getNumSezioniMultiple() {
        return numSezioniMultiple;
    }

    public void setNumSezioniMultiple(int numSezioniMultiple) {
        this.numSezioniMultiple = numSezioniMultiple;
    }
    @Override
    public SezioneCompilabileBean clone(){
        try {
            return (SezioneCompilabileBean) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
            
            
}
