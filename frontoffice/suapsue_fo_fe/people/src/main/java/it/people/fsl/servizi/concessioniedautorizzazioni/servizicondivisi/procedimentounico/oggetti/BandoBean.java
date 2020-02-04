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

public class BandoBean {

	private boolean flg_bando;
	private String cod_servizio;
	private String titoloServizio;
	private String dallaData;
	private String allaData;
	private boolean mostraData;
	private ArrayList accessList;
	private String descrizioneServizio;
	
	public BandoBean(){
		this.flg_bando=false;
		this.cod_servizio="";
		this.titoloServizio="";
		this.dallaData="";
		this.allaData="";
		this.mostraData=false;
		this.accessList = new ArrayList();
		this.descrizioneServizio="";
	}

	public boolean isFlg_bando() {
		return flg_bando;
	}

	public void setFlg_bando(boolean flg_bando) {
		this.flg_bando = flg_bando;
	}

	public String getCod_servizio() {
		return cod_servizio;
	}

	public void setCod_servizio(String cod_servizio) {
		this.cod_servizio = cod_servizio;
	}

	public String getTitoloServizio() {
		return titoloServizio;
	}

	public void setTitoloServizio(String titoloServizio) {
		this.titoloServizio = titoloServizio;
	}

	public String getDallaData() {
		return dallaData;
	}

	public void setDallaData(String dallaData) {
		this.dallaData = dallaData;
	}

	public String getAllaData() {
		return allaData;
	}

	public void setAllaData(String allaData) {
		this.allaData = allaData;
	}

	public boolean isMostraData() {
		return mostraData;
	}

	public void setMostraData(boolean mostraData) {
		this.mostraData = mostraData;
	}

	public ArrayList getAccessList() {
		return accessList;
	}

	public void setAccessList(ArrayList accessList) {
		this.accessList = accessList;
	}
	public void addAccessList(BaseBean bb) {
		if (this.accessList==null) {
			this.accessList = new ArrayList();
		} 
		this.accessList.add(bb);
	}
	public void removeElementAccessList(int i){
		if (this.accessList!=null && this.accessList.size()>i){
			this.accessList.remove(i);
		}
	}

	public String getDescrizioneServizio() {
		return descrizioneServizio;
	}

	public void setDescrizioneServizio(String descrizioneServizio) {
		this.descrizioneServizio = descrizioneServizio;
	}

	
	
}
