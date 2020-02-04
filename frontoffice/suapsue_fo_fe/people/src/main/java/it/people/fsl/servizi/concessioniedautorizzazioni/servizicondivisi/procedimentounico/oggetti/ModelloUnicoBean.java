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
import java.util.HashMap;

public class ModelloUnicoBean extends BaseBean implements Serializable{

	private static final long serialVersionUID = -5246644432097053698L;
	
	private String tmp;
	private ArrayList listaIntervento;
	private HashMap listaNormative;
	private ArrayList listaDocumenti;
	private String ente;
	private ArrayList listaTmp;
	
	public ModelloUnicoBean() {
		this.tmp="";
		this.listaIntervento=new ArrayList();
		this.listaNormative=new HashMap();
		this.listaDocumenti=new ArrayList();
		this.listaTmp = new ArrayList();
		this.ente="";
	}
	
	public String getTmp() {
		return tmp;
	}
	public void setTmp(String tmp) {
		this.tmp = tmp;
	}
	public ArrayList getListaIntervento() {
		return listaIntervento;
	}
	public void setListaIntervento(ArrayList listaIntervento) {
		this.listaIntervento = listaIntervento;
	}
	public void addListaIntervento(String descrizioneIntervento) {
		this.listaIntervento.add(descrizioneIntervento);
	}
	public HashMap getListaNormative() {
		return listaNormative;
	}
	public void setListaNormative(HashMap listaNormative) {
		this.listaNormative = listaNormative;
	}
	public void addListaNormative(String key,NormativaBean normativa) {
		this.listaNormative.put(key,normativa);
	}
	public String getEnte() {
		return ente;
	}
	public void setEnte(String ente) {
		this.ente = ente;
	}

	public ArrayList getListaDocumenti() {
		return listaDocumenti;
	}
	public void setListaDocumenti(ArrayList listaDocumenti) {
		this.listaDocumenti = listaDocumenti;
	}
	public void addListaDocumenti(AllegatoBean documento) {
		this.listaDocumenti.add(documento);
	}

	public ArrayList getListaTmp() {
		return listaTmp;
	}

	public void setListaTmp(ArrayList listaTmp) {
		this.listaTmp = listaTmp;
	}
	public void addListaTmp(NormativaBean tmp) {
		this.listaTmp.add(tmp);
	}
}
