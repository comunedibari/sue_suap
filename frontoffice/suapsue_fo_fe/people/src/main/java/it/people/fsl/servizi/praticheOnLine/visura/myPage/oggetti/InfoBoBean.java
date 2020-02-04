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

import java.io.Serializable;

public class InfoBoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String process_data_id;
	private long timestamp_evento;
	private String descrizione_evento;
	private String id_bo;
	private String url_visura;
	private String altreInfo;
	private boolean visibilita;
	
	
	public String getProcess_data_id() {
		return process_data_id;
	}
	public void setProcess_data_id(String process_data_id) {
		this.process_data_id = process_data_id;
	}
	public String getDescrizione_evento() {
		return descrizione_evento;
	}
	public void setDescrizione_evento(String descrizione_evento) {
		this.descrizione_evento = descrizione_evento;
	}
	public String getId_bo() {
		return id_bo;
	}
	public void setId_bo(String id_bo) {
		this.id_bo = id_bo;
	}
	public String getUrl_visura() {
		return url_visura;
	}
	public void setUrl_visura(String url_visura) {
		this.url_visura = url_visura;
	}
	public long getTimestamp_evento() {
		return timestamp_evento;
	}
	public void setTimestamp_evento(long timestamp_evento) {
		this.timestamp_evento = timestamp_evento;
	}
	public String getAltreInfo() {
		return altreInfo;
	}
	public void setAltreInfo(String altreInfo) {
		this.altreInfo = altreInfo;
	}
	public boolean isVisibilita() {
		return visibilita;
	}
	public void setVisibilita(boolean visibilita) {
		this.visibilita = visibilita;
	}
	
	
	
	
}
