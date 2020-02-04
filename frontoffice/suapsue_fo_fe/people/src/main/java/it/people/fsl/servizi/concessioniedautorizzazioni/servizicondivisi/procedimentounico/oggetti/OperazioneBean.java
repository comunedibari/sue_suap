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

public class OperazioneBean extends BaseBean implements Serializable {
	
	private static final long serialVersionUID = -7901966973307234405L;

	private String codiceOperazione;
	private String descrizioneOperazione;
	private boolean selezionato;
	private int profondita;
	private String codicePadre;
	private String codRif;
	private ArrayList stringPath;
	private ArrayList listaCodiciFigli;
	private String raggruppamentoCheck;
	
	private String sino;
	private String valueSiNo;
	private String etichettaAlternativa;
	
	public OperazioneBean(){
		this.codiceOperazione="";
		this.descrizioneOperazione="";
		this.selezionato = false;
		this.profondita=0;
		this.codicePadre = "";
		this.stringPath = new ArrayList();
		this.listaCodiciFigli = new ArrayList();
		this.codRif = "";
		this.raggruppamentoCheck=null;
		
		this.sino="N";
		this.valueSiNo="";
		this.etichettaAlternativa="";
	}

	public String getCodiceOperazione() {
		return codiceOperazione;
	}
	public void setCodiceOperazione(String codiceOperazione) {
		this.codiceOperazione = codiceOperazione;
	}
	public String getDescrizioneOperazione() {
		return descrizioneOperazione;
	}
	public void setDescrizioneOperazione(String descrizioneOperazione) {
		this.descrizioneOperazione = descrizioneOperazione;
	}
	public boolean isSelezionato() {
		return selezionato;
	}
	public void setSelezionato(boolean selezionato) {
		this.selezionato = selezionato;
	}
	public String getCodicePadre() {
		return codicePadre;
	}
	public void setCodicePadre(String codicePadre) {
		this.codicePadre = codicePadre;
	}
	public ArrayList getStringPath() {
		return stringPath;
	}
	public void setStringPath(ArrayList stringPath) {
		this.stringPath = stringPath;
	}
	public void addStringPath(String path){
		this.stringPath.add(path);
	}
	
	public ArrayList getListaCodiciFigli() {
		return listaCodiciFigli;
	}
	public void setListaCodiciFigli(ArrayList listaCodiciFigli) {
		this.listaCodiciFigli = listaCodiciFigli;
	}
	public void addListaCodiciFigli(String codiceFiglio){
		this.listaCodiciFigli.add(codiceFiglio);
	}

	public int getProfondita() {
		return profondita;
	}

	public void setProfondita(int profondita) {
		this.profondita = profondita;
	}

	public String getCodRif() {
		return codRif;
	}

	public void setCodRif(String codRif) {
		this.codRif = codRif;
	}

	public String getRaggruppamentoCheck() {
		return raggruppamentoCheck;
	}

	public void setRaggruppamentoCheck(String raggruppamentoCheck) {
		this.raggruppamentoCheck = raggruppamentoCheck;
	}

	public String getSino() {
		return sino;
	}

	public void setSino(String sino) {
		this.sino = sino;
	}

	public String getValueSiNo() {
		return valueSiNo;
	}

	public void setValueSiNo(String valueSiNo) {
		this.valueSiNo = valueSiNo;
	}
	
}
