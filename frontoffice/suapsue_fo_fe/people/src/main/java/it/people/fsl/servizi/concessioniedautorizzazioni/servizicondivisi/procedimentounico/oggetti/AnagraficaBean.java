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

public class AnagraficaBean extends BaseBean implements Serializable {
	
	private static final long serialVersionUID = 7453764677961360877L;
	
	private ArrayList listaCampi;
	private ArrayList listaCampiStep;
	private String htmlStepAttuale;
	private ArrayList htmlHistory;
	private boolean fineCompilazione;
	private boolean initialized;
	private int livelloAttuale;

	public AnagraficaBean() {
		this.listaCampi = new ArrayList();
		this.listaCampiStep = new ArrayList();
		this.htmlStepAttuale="";
		this.htmlHistory = new ArrayList();
		this.fineCompilazione = false;
		this.initialized=false;
		this.livelloAttuale=1;
	}

	public ArrayList getListaCampi() {
		return listaCampi;
	}

	public void setListaCampi(ArrayList listaCampi) {
		this.listaCampi = listaCampi;
	}
	public void addListaCampi(HrefCampiBean campo) {
		this.listaCampi.add(campo);
	}

	public ArrayList getListaCampiStep() {
		return listaCampiStep;
	}

	public void setListaCampiStep(ArrayList listaCampiStep) {
		this.listaCampiStep = listaCampiStep;
	}
	
	public void addListaCampiStep(HrefCampiBean campo) {
		this.listaCampiStep.add(campo);
	}

	public String getHtmlStepAttuale() {
		return htmlStepAttuale;
	}

	public void setHtmlStepAttuale(String htmlStepAttuale) {
		this.htmlStepAttuale = htmlStepAttuale;
	}

	public ArrayList getHtmlHistory() {
		return htmlHistory;
	}

	public void setHtmlHistory(ArrayList htmlHistory) {
		this.htmlHistory = htmlHistory;
	}
	public void addHtmlHistory(String htmlStep) {
		this.htmlHistory.add(htmlStep);
	}
	public void removeHtmlHistory() {
		if (this.htmlHistory.size()>0) {
			this.htmlHistory.remove(this.htmlHistory.size()-1);
		}
	}

	public boolean isFineCompilazione() {
		return fineCompilazione;
	}

	public void setFineCompilazione(boolean fineCompilazione) {
		this.fineCompilazione = fineCompilazione;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public int getLivelloAttuale() {
		return livelloAttuale;
	}

	public void setLivelloAttuale(int livelloAttuale) {
		this.livelloAttuale = livelloAttuale;
	}

}
