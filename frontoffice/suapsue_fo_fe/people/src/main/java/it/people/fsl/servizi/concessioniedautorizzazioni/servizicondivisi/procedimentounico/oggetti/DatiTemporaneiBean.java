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

import java.util.ArrayList;
import java.util.List;

import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Costant;

public class DatiTemporaneiBean {
	
    private List servizi;
    private List eventiVita;
    private String jspChiamante;
    private String eventoVitaSel;
    private String servizioSel;
    
    private String[] comuniBookmarkSel;
    private ArrayList comuniBookmark;
    
    private List tmpCodiciCheckPerRoot;
    private List listaMappeCheckPerRoot;
    // Da valutare
    private boolean checkBoxValueSelected;
    private boolean initAlberoOneri;
    
    private String serviceIdPagamenti;
    private double totalePagato;
    private double totalePagatoCommissioni;
    private boolean pagamentoEffettuato;
    private String modalitaPagamento;
    private int indiceSportello;
    private String htmlRiepilogo;
    
    private String dataNascitaRichiedente;
    private String emailRichiedente;
    
    private ParametriPUBean parametriPU;
    
    private boolean presaVisionePDF = false;
    
    public boolean isCheckBoxValueSelected() {
		return checkBoxValueSelected;
	}

	public void setCheckBoxValueSelected(boolean checkBoxValueSelected) {
		this.checkBoxValueSelected = checkBoxValueSelected;
	}

	public DatiTemporaneiBean() {
        servizi = new ArrayList();
        eventiVita = new ArrayList();
        tmpCodiciCheckPerRoot = new ArrayList();
        listaMappeCheckPerRoot = new ArrayList();
        this.pagamentoEffettuato=false;
        this.modalitaPagamento = Costant.MODALITA_PAGAMENTO_ON_LINE;
        this.indiceSportello=-1;
        
        this.comuniBookmarkSel=new String[0];
        this.comuniBookmark=new ArrayList();
        this.htmlRiepilogo="";
        this.dataNascitaRichiedente="";
        this.emailRichiedente="";
        
        this.parametriPU = new ParametriPUBean();
        this.presaVisionePDF = false;
    }
    
	public List getServizi() {
		return servizi;
	}
	public void setServizi(List servizi) {
		this.servizi = servizi;
	}
	public void addServizi(ServiziBean servizi) {
		this.servizi.add(servizi);
	}
	public List getEventiVita() {
		return eventiVita;
	}
	public void setEventiVita(List eventiVita) {
		this.eventiVita = eventiVita;
	}
	public void addEventiVita(EventiVitaBean eventiVita) {
		this.eventiVita.add(eventiVita);
	}
	public String getJspChiamante() {
		return jspChiamante;
	}
	public void setJspChiamante(String jspChiamante) {
		this.jspChiamante = jspChiamante;
	}
	public String getEventoVitaSel() {
		return eventoVitaSel;
	}
	public void setEventoVitaSel(String eventoVitaSel) {
		this.eventoVitaSel = eventoVitaSel;
	}
	public String getServizioSel() {
		return servizioSel;
	}
	public void setServizioSel(String servizioSel) {
		this.servizioSel = servizioSel;
	}

	public List getTmpCodiciCheckPerRoot() {
		return tmpCodiciCheckPerRoot;
	}

	public void setTmpCodiciCheckPerRoot(List tmpCodiciCheckPerRoot) {
		this.tmpCodiciCheckPerRoot = tmpCodiciCheckPerRoot;
	}


	public List getListaMappeCheckPerRoot() {
		return listaMappeCheckPerRoot;
	}

	public void setListaMappeCheckPerRoot(List listaMappeCheckPerRoot) {
		this.listaMappeCheckPerRoot = listaMappeCheckPerRoot;
	}

	public boolean isInitAlberoOneri() {
		return initAlberoOneri;
	}

	public void setInitAlberoOneri(boolean initAlberoOneri) {
		this.initAlberoOneri = initAlberoOneri;
	}

	public String getServiceIdPagamenti() {
		return serviceIdPagamenti;
	}

	public void setServiceIdPagamenti(String serviceIdPagamenti) {
		this.serviceIdPagamenti = serviceIdPagamenti;
	}

	public double getTotalePagato() {
		return totalePagato;
	}

	public void setTotalePagato(double totalePagato) {
		this.totalePagato = totalePagato;
	}

	public double getTotalePagatoCommissioni() {
		return totalePagatoCommissioni;
	}

	public void setTotalePagatoCommissioni(double totalePagatoCommissioni) {
		this.totalePagatoCommissioni = totalePagatoCommissioni;
	}

	public boolean isPagamentoEffettuato() {
		return pagamentoEffettuato;
	}

	public void setPagamentoEffettuato(boolean pagamentoEffettuato) {
		this.pagamentoEffettuato = pagamentoEffettuato;
	}

	public int getIndiceSportello() {
		return indiceSportello;
	}

	public void setIndiceSportello(int indiceSportello) {
		this.indiceSportello = indiceSportello;
	}

	
	public String[] getComuniBookmarkSel() {
		return comuniBookmarkSel;
	}

	public void setComuniBookmarkSel(String[] comuniBookmarkSel) {
		this.comuniBookmarkSel = comuniBookmarkSel;
	}

    public void addComuniBookmarkSel(String codComune) {

        int size = this.comuniBookmarkSel.length + 1;
        String[] temp = new String[size];

        try {
            for (int i = 0; i < this.comuniBookmarkSel.length; i++) {
                temp[i] = this.comuniBookmarkSel[i];
            }
            temp[size - 1] = codComune;
            this.comuniBookmarkSel = temp;
            // opsVec=vec;
        } catch (Exception e) {

        }
    }

	public ArrayList getComuniBookmark() {
		return comuniBookmark;
	}

	public void setComuniBookmark(ArrayList comuniBookmark) {
		this.comuniBookmark = comuniBookmark;
	}
	public void addComuniBookmark(BaseBean comune) {
		this.comuniBookmark.add(comune);
	}

	public String getHtmlRiepilogo() {
		return htmlRiepilogo;
	}

	public void setHtmlRiepilogo(String htmlRiepilogo) {
		this.htmlRiepilogo = htmlRiepilogo;
	}

	public String getDataNascitaRichiedente() {
		return dataNascitaRichiedente;
	}

	public void setDataNascitaRichiedente(String dataNascitaRichiedente) {
		this.dataNascitaRichiedente = dataNascitaRichiedente;
	}

	public String getEmailRichiedente() {
		return emailRichiedente;
	}

	public void setEmailRichiedente(String emailRichiedente) {
		this.emailRichiedente = emailRichiedente;
	}

	public ParametriPUBean getParametriPU() {
		return parametriPU;
	}

	public void setParametriPU(ParametriPUBean parametriPU) {
		this.parametriPU = parametriPU;
	}

	/**
	 * @return the modalitaPagamento
	 */
	public final String getModalitaPagamento() {
		return this.modalitaPagamento;
	}

	/**
	 * @param modalitaPagamento the modalitaPagamento to set
	 */
	public final void setModalitaPagamento(String modalitaPagamento) {
		this.modalitaPagamento = modalitaPagamento;
	}

	/**
	 * @return the presaVisionePDF
	 */
	public final boolean isPresaVisionePDF() {
		return this.presaVisionePDF;
	}

	/**
	 * @param presaVisionePDF the presaVisionePDF to set
	 */
	public final void setPresaVisionePDF(boolean presaVisionePDF) {
		this.presaVisionePDF = presaVisionePDF;
	}

}
