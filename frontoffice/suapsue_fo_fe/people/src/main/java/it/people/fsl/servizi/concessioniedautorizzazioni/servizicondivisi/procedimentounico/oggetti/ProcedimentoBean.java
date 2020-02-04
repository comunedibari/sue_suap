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

public class ProcedimentoBean extends BaseBean implements Serializable{
	

	private static final long serialVersionUID = -693938749128210965L;
	
	private String nome;
	private String terminiEvasione;
	private String ente;
	private String codiceEnte;
	private String codiceClasseEnte;
	private String descrizioneClasseEnte;
	private ArrayList codInterventi;
//	private String titoloIntervento;
	private String codiceProcedimento;
	private String flagComune;
	private String flagPu;
	private String flagProcedimento;
	private String codiceCud;
	private String descrizioneCud;
	private int tipo;
	private double oneriDovuti;
//	private ArrayList oneriPadri;
	private String codiceSportello;
	private String desSportello;
	private String flg_bollo;
	private ArrayList listaCodiciOneri;
	private String codDest;
	  
	public ProcedimentoBean(){
		this.nome="";
		this.terminiEvasione="";
		this.ente="";
		this.codiceEnte="";
		this.codiceClasseEnte="";
		this.codInterventi=new ArrayList();;
//		this.titoloIntervento="";
		this.codiceProcedimento="";
		this.tipo=0;
		this.oneriDovuti=0;
//		private ArrayList oneriPadri;
		this.codiceSportello="";
		this.desSportello="";
		this.flg_bollo="";
		
		this.flagComune="";
		this.flagPu="";
		this.flagProcedimento="";
		this.codiceCud="";
		this.listaCodiciOneri=new ArrayList();
		this.descrizioneCud="";
		this.descrizioneClasseEnte="";
		this.codDest="";
	}
	  
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTerminiEvasione() {
		return terminiEvasione;
	}
	public void setTerminiEvasione(String terminiEvasione) {
		this.terminiEvasione = terminiEvasione;
	}
	public String getEnte() {
		return ente;
	}
	public void setEnte(String ente) {
		this.ente = ente;
	}
	public String getCodiceEnte() {
		return codiceEnte;
	}
	public void setCodiceEnte(String codiceEnte) {
		this.codiceEnte = codiceEnte;
	}
	public String getCodiceClasseEnte() {
		return codiceClasseEnte;
	}
	public void setCodiceClasseEnte(String codiceClasseEnte) {
		this.codiceClasseEnte = codiceClasseEnte;
	}

//	public String getTitoloIntervento() {
//		return titoloIntervento;
//	}
//	public void setTitoloIntervento(String titoloIntervento) {
//		this.titoloIntervento = titoloIntervento;
//	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public double getOneriDovuti() {
		return oneriDovuti;
	}
	public void setOneriDovuti(double oneriDovuti) {
		this.oneriDovuti = oneriDovuti;
	}
	public String getDesSportello() {
		return desSportello;
	}
	public void setDesSportello(String desSportello) {
		this.desSportello = desSportello;
	}
	public String getFlg_bollo() {
		return flg_bollo;
	}
	public void setFlg_bollo(String flg_bollo) {
		this.flg_bollo = flg_bollo;
	}
	public ArrayList getCodInterventi() {
		return codInterventi;
	}
	public void setCodInterventi(ArrayList codInterventi) {
		this.codInterventi = codInterventi;
	}
	public void addCodInterventi(String codInt) {
		if (codInterventi==null) {
			codInterventi = new ArrayList();
		}
		this.codInterventi.add(codInt);
	}

	public String getCodiceProcedimento() {
		return codiceProcedimento;
	}

	public void setCodiceProcedimento(String codiceProcedimento) {
		this.codiceProcedimento = codiceProcedimento;
	}

	public String getFlagComune() {
		return flagComune;
	}

	public void setFlagComune(String flagComune) {
		this.flagComune = flagComune;
	}

	public String getFlagPu() {
		return flagPu;
	}

	public void setFlagPu(String flagPu) {
		this.flagPu = flagPu;
	}

	public String getFlagProcedimento() {
		return flagProcedimento;
	}

	public void setFlagProcedimento(String flagProcedimento) {
		this.flagProcedimento = flagProcedimento;
	}

	public String getCodiceCud() {
		return codiceCud;
	}

	public void setCodiceCud(String codiceCud) {
		this.codiceCud = codiceCud;
	}

	public String getCodiceSportello() {
		return codiceSportello;
	}

	public void setCodiceSportello(String codiceSportello) {
		this.codiceSportello = codiceSportello;
	}

	public ArrayList getListaCodiciOneri() {
		return listaCodiciOneri;
	}

	public void setListaCodiciOneri(ArrayList listaCodiciOneri) {
		this.listaCodiciOneri = listaCodiciOneri;
	}
	public void addListaCodiciOneri(String codiceOneri) {
		this.listaCodiciOneri.add(codiceOneri);
	}

	public String getDescrizioneCud() {
		return descrizioneCud;
	}

	public void setDescrizioneCud(String descrizioneCud) {
		this.descrizioneCud = descrizioneCud;
	}

	public String getDescrizioneClasseEnte() {
		return descrizioneClasseEnte;
	}

	public void setDescrizioneClasseEnte(String descrizioneClasseEnte) {
		this.descrizioneClasseEnte = descrizioneClasseEnte;
	}

	public String getCodDest() {
		return codDest;
	}

	public void setCodDest(String codDest) {
		this.codDest = codDest;
	}
}
