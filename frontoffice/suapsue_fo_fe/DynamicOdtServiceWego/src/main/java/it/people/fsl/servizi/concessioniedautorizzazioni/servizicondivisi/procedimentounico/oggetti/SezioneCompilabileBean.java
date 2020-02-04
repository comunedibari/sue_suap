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
import java.util.ArrayList;
import java.util.List;

/**
 * @author federicog
 * 
 * SezioneCompilabileBean.java
 * 
 * @date 19-set-2005
 * 
 */
public class SezioneCompilabileBean extends BaseBean implements Serializable {
    private String titolo;
    private String href;
    private boolean complete;
    private String link;
    private int tdCount; 
	private List campi;//list di HrefCampiBean
    private int rowCount;
    private String piedeHref;
    private boolean processed;
    private String html;
    private String htmlEditable;
    private String intervento;
    private boolean visible;
    /**
     * 
     */
    public SezioneCompilabileBean() {
       titolo=href=link="";
       complete=false;
       tdCount=0;
       rowCount=0;
       campi = new ArrayList();
       piedeHref="";
       visible = true;
    }
    /**
     * @return Returns the complete.
     */
    public boolean isComplete() {
        return complete;
    }
    /**
     * @param complete The complete to set.
     */
    public void setComplete(boolean complete) {
        this.complete = complete;
    }
    /**
     * @return Returns the href.
     */
    public String getHref() {
        return href;
    }
    /**
     * @param href The href to set.
     */
    public void setHref(String href) {
        this.href = href;
    }
    /**
     * @return Returns the link.
     */
    public String getLink() {
        return link;
    }
    /**
     * @param link The link to set.
     */
    public void setLink(String link) {
        this.link = link;
    }
    /**
     * @return Returns the titolo.
     */
    public String getTitolo() {
        return titolo;
    }
    /**
     * @param titolo The titolo to set.
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
	
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getTdCount() {
		return tdCount;
	}
	public void setTdCount(int tdCount) {
		this.tdCount = tdCount;
	}
    /**
     * @return Returns the campi.
     */
    public List getCampi() {
        return campi;
    }
    /**
     * @param campi The campi to set.
     */
    public void setCampi(List campi) {
        this.campi = campi;
    }
    
    public void addCampi(HrefCampiBean bean) {
        try {
            campi.add(bean);
        } catch (Exception e) {
        }
    }

    
    /**
     * @return Returns the piedeHref.
     */
    public String getPiedeHref() {
        return piedeHref;
    }
    /**
     * @param piedeHref The piedeHref to set.
     */
    public void setPiedeHref(String piedeHref) {
        this.piedeHref = piedeHref;
    }
    /**
     * @return Returns the processed.
     */
    public boolean isProcessed() {
        return processed;
    }
    /**
     * @param processed The processed to set.
     */
    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
    /**
     * @return Returns the html.
     */
    public String getHtml() {
        return html;
    }
    /**
     * @param html The html to set.
     */
    public void setHtml(String html) {
        this.html = html;
    }
    /**
     * @return Returns the htmlEditable.
     */
    public String getHtmlEditable() {
        return htmlEditable;
    }
    /**
     * @param htmlEditable The htmlEditable to set.
     */
    public void setHtmlEditable(String htmlEditable) {
        this.htmlEditable = htmlEditable;
    }
    /**
     * @return Returns the intervento.
     */
    public String getIntervento() {
        return intervento;
    }
    /**
     * @param intervento The intervento to set.
     */
    public void setIntervento(String intervento) {
        this.intervento = intervento;
    }
    /**
     * @return Returns the visible.
     */
    public boolean isVisible() {
        return visible;
    }
    /**
     * @param visible The visible to set.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
