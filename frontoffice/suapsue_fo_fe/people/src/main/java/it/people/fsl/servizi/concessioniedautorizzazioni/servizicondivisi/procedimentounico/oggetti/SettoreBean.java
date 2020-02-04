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

public class SettoreBean extends BaseBean implements Serializable{

	private static final long serialVersionUID = -7191155243058292546L;
	
	private String codiceRamo;
	private String descrizioneRamo;
	private boolean selezionato;
	private String codiceRamoPadre;
	private ArrayList stringPath;
	private ArrayList listaCodiciFigli;
	private int profondita;
	private String cod_rif_normativa;
	
	public SettoreBean(){
		this.codiceRamo="";
		this.descrizioneRamo="";
		this.selezionato = false;
		this.codiceRamoPadre = "";
		this.stringPath = new ArrayList();
		this.listaCodiciFigli = new ArrayList();
		this.profondita=0;
		this.cod_rif_normativa=null;
	}
	
	
	public String getCodiceRamo() {
		return codiceRamo;
	}
	public void setCodiceRamo(String codiceRamo) {
		this.codiceRamo = codiceRamo;
	}
	public String getDescrizioneRamo() {
		return descrizioneRamo;
	}
	public void setDescrizioneRamo(String descrizioneRamo) {
		this.descrizioneRamo = descrizioneRamo;
	}
	public boolean isSelezionato() {
		return selezionato;
	}
	public void setSelezionato(boolean selezionato) {
		this.selezionato = selezionato;
	}
	public String getCodiceRamoPadre() {
		return codiceRamoPadre;
	}
	public void setCodiceRamoPadre(String codiceRamoPadre) {
		this.codiceRamoPadre = codiceRamoPadre;
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


	public String getCod_rif_normativa() {
		return cod_rif_normativa;
	}


	public void setCod_rif_normativa(String cod_rif_normativa) {
		this.cod_rif_normativa = cod_rif_normativa;
	}
	
	
}
