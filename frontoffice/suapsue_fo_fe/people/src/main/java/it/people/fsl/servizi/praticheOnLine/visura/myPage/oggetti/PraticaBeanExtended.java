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
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti;

import it.init.myPage.visuraTypes.DettaglioAttivitaType;
import it.people.process.common.entity.Attachment;
import it.people.process.data.AbstractData;

import java.util.ArrayList;
import java.util.List;

public class PraticaBeanExtended extends PraticaBean{

	
	private ArrayList listaAttivitaBO = new ArrayList();
	private ArrayList altriDichiaranti = new ArrayList();
	private ArrayList listaAllegati = new ArrayList();
	private String XMLprocessData;
	private AbstractData processDataPratica;
	private ArrayList certificato = new ArrayList();
	private InfoBoBean infoBO;
	private DettaglioAttivitaType[] dettaglioAttivitaBO;
	
	private List altriAllegati = new ArrayList();
	
	public PraticaBeanExtended(){
		super();
		this.XMLprocessData="";
		this.listaAttivitaBO = new ArrayList();
		this.altriDichiaranti = new ArrayList();
		this.listaAllegati = new ArrayList();
		this.certificato = new ArrayList();
		this.infoBO=null;
		this.dettaglioAttivitaBO=null;
		
	}
	
	public ArrayList getListaAttivitaBO() {
		return listaAttivitaBO;
	}
	public void setListaAttivitaBO(ArrayList listaAttivitaBO) {
		this.listaAttivitaBO = listaAttivitaBO;
	}
	public ArrayList getAltriDichiaranti() {
		return altriDichiaranti;
	}
	public void setAltriDichiaranti(ArrayList altriDichiaranti) {
		this.altriDichiaranti = altriDichiaranti;
	}
	public ArrayList getListaAllegati() {
		return listaAllegati;
	}
	public void setListaAllegati(ArrayList listaAllegati) {
		this.listaAllegati = listaAllegati;
	}

	public String getXMLprocessData() {
		return XMLprocessData;
	}

	public void setXMLprocessData(String XMLprocessData) {
		this.XMLprocessData = XMLprocessData;
	}

	public AbstractData getProcessDataPratica() {
		return processDataPratica;
	}

	public void setProcessDataPratica(AbstractData processDataPratica) {
		this.processDataPratica = processDataPratica;
	}

	public ArrayList getCertificato() {
		return certificato;
	}

	public void setCertificato(ArrayList certificato) {
		this.certificato = certificato;
	}
	public void addCertificato(Attachment certificato) {
		if (this.certificato == null) {
			this.certificato = new ArrayList();
		}
		this.certificato.add(certificato);
	}

	
	public final List getAltriAllegati() {
	    return altriAllegati;
	}

	public final void setAltriAllegati(List altriAllegati) {
	    this.altriAllegati = altriAllegati;
	}
	
	public final void addAltriAllegati(Attachment allegato) {
	    if (this.getAltriAllegati() == null) {
		this.setAltriAllegati(new ArrayList());
	    }
	    this.getAltriAllegati().add(allegato);
	}

	
	
	
	public InfoBoBean getInfoBO() {
		return infoBO;
	}

	public void setInfoBO(InfoBoBean infoBO) {
		this.infoBO = infoBO;
	}

	public DettaglioAttivitaType[] getDettaglioAttivitaBO() {
		return dettaglioAttivitaBO;
	}

	public void setDettaglioAttivitaBO(DettaglioAttivitaType[] dettaglioAttivitaBO) {
		this.dettaglioAttivitaBO = dettaglioAttivitaBO;
	}
	public void addDettaglioAttivitaBO(DettaglioAttivitaType dettaglioAttivitaBO) {
		if (this.dettaglioAttivitaBO==null){
			this.dettaglioAttivitaBO = new DettaglioAttivitaType[1];
			this.dettaglioAttivitaBO[0] = dettaglioAttivitaBO;
		} else {
			DettaglioAttivitaType[] nuovoDettaglio = new DettaglioAttivitaType[this.dettaglioAttivitaBO.length+1];
			for (int i = 0; i < this.dettaglioAttivitaBO.length; i++) {
				nuovoDettaglio[i] = this.dettaglioAttivitaBO[i];
			}
			nuovoDettaglio[this.dettaglioAttivitaBO.length] = dettaglioAttivitaBO;
		}
	}
	
}
