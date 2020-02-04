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

public class ComuneBean extends IndirizzoBean  implements Serializable{
	
	private static final long serialVersionUID = 6010411886634953393L;
	
	private String codEnte = null;
	private String codClasseEnte = null;
	private String ccb = null;
	private String ccp = null;
	private String tp_prg = null;
	private String prg_href = null;
	private String aoo = null;
	private String tipAggregazione = null;
//    private SportelloBean sportello = null;
    private String codBf = null;
    private String codIstat = null;
    
    public ComuneBean(){
    	
    }
    
	public String getCodEnte() {
		return codEnte;
	}
	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}
	public String getCodClasseEnte() {
		return codClasseEnte;
	}
	public void setCodClasseEnte(String codClasseEnte) {
		this.codClasseEnte = codClasseEnte;
	}
	public String getCcb() {
		return ccb;
	}
	public void setCcb(String ccb) {
		this.ccb = ccb;
	}
	public String getCcp() {
		return ccp;
	}
	public void setCcp(String ccp) {
		this.ccp = ccp;
	}
	public String getTp_prg() {
		return tp_prg;
	}
	public void setTp_prg(String tp_prg) {
		this.tp_prg = tp_prg;
	}
	public String getPrg_href() {
		return prg_href;
	}
	public void setPrg_href(String prg_href) {
		this.prg_href = prg_href;
	}
	public String getAoo() {
		return aoo;
	}
	public void setAoo(String aoo) {
		this.aoo = aoo;
	}
	public String getTipAggregazione() {
		return tipAggregazione;
	}
	public void setTipAggregazione(String tipAggregazione) {
		this.tipAggregazione = tipAggregazione;
	}
//	public SportelloBean getSportello() {
//		return sportello;
//	}
//	public void setSportello(SportelloBean sportello) {
//		this.sportello = sportello;
//	}
	public String getCodBf() {
		return codBf;
	}
	public void setCodBf(String codBf) {
		this.codBf = codBf;
	}
	public String getCodIstat() {
		return codIstat;
	}
	public void setCodIstat(String codIstat) {
		this.codIstat = codIstat;
	}	
	
	public String toXML(){
		String xml ="    <comuneSelezionato>\n"
			.concat(this.aoo==null?"      <aoo/>\n":"      <aoo>"+this.aoo+"</aoo>\n")
			.concat(this.getCap()==null?"      <cap/>\n":"      <cap>"+this.getCap()+"</cap>\n")
			.concat(this.ccb==null?"      <ccb/>\n":"      <ccb>"+this.ccb+"</ccb>\n")
			.concat(this.ccp==null?"      <ccp/>\n":"      <ccp>"+this.ccp+"</ccp>\n")
			.concat(this.getCitta()==null?"      <citta/>\n":"      <citta>"+this.getCitta()+"</citta>\n")
			.concat(this.codBf==null?"      <codBf/>\n":"      <codBf>"+this.codBf+"</codBf>\n")
			.concat(this.codClasseEnte==null?"      <codClasseEnte/>\n":"      <codClasseEnte>"+this.codClasseEnte+"</codClasseEnte>\n")
			.concat(this.codEnte==null?"      <codEnte/>\n":"      <codEnte>"+this.codEnte+"</codEnte>\n")
			.concat(this.codIstat==null?"      <codIstat/>\n":"      <codIstat>"+this.codIstat+"</codIstat>\n")
			.concat(this.getCodice()==null?"      <codice/>\n":"      <codice>"+this.getCodice()+"</codice>\n")
			.concat(this.getDescrizione()==null?"      <descrizione/>\n":"      <descrizione>"+this.getDescrizione()+"</descrizione>\n")
			.concat(this.getEmail()==null?"      <email/>\n":"      <email>"+this.getEmail()+"</email>\n")
    		.concat(this.getFax()==null?"      <fax/>\n":"      <fax>"+this.getFax()+"</fax>\n")
    		.concat(this.getNumero()==null?"      <numero/>\n":"      <numero>"+this.getNumero()+"</numero>\n")
    		.concat(this.prg_href==null?"      <prg_href/>\n":"      <prg_href>"+this.prg_href+"</prg_href>\n")
    		.concat(this.getProvincia()==null?"      <provincia/>\n":"      <provincia>"+this.getProvincia()+"</provincia>\n")
    		.concat(this.getStato()==null?"      <stato/>\n":"      <stato>"+this.getStato()+"</stato>\n")
    		.concat(this.getTelefono()==null?"      <telefono/>\n":"      <telefono>"+this.getTelefono()+"</telefono>\n")
    		.concat(this.tipAggregazione==null?"      <tipAggregazione/>\n":"      <tipAggregazione>"+this.tipAggregazione+"</tipAggregazione>\n")
    		.concat(this.tp_prg==null?"      <tp_prg/>\n":"      <tp_prg>"+this.tp_prg+"</tp_prg>\n")
    		.concat(this.getVia()==null?"      <via/>\n":"      <via>"+this.getVia()+"</via>\n")
    		.concat("    </comuneSelezionato>");
		return xml;
	}
	

}
