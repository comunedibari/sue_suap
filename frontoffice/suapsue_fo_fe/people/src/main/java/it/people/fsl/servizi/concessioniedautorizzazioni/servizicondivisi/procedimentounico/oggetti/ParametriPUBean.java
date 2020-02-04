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

import it.gruppoinit.commons.Utilities;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Costant;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.XPathReader;
import it.people.sirac.serviceprofile.xml.AccessoIntermediari;
import it.people.sirac.serviceprofile.xml.AccessoUtentePeopleRegistrato;
import it.people.sirac.serviceprofile.xml.AuthorizationProfile;
import it.people.sirac.serviceprofile.xml.PeopleServiceProfile;
import it.people.sirac.serviceprofile.xml.PeopleServiceProfileDocument;
import it.people.sirac.serviceprofile.xml.SecurityProfile;
import it.people.sirac.serviceprofile.xml.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.apache.xmlbeans.XmlOptions;

public class ParametriPUBean {

	boolean autenticazioneForte;
	boolean autenticazioneDebole;
	boolean abilitaUtente;
	boolean abilitaIntermediari;
	
	String tipo;
	boolean conInvio;
	boolean abilitaFirma;
	String pagamenti;
	String modalitaPagamenti;
	String modalitaPagamentiOpzionali;
	
	
	public ParametriPUBean(){
		this.autenticazioneForte=false;
		this.autenticazioneDebole=true;
		this.abilitaUtente=true;
		this.abilitaIntermediari=true;
		
		this.tipo=Costant.bookmarkTypeCompleteLabel;
		this.conInvio=true;
		this.abilitaFirma=true;
		this.pagamenti=Costant.pagamentoOpzionaleLabel;
		this.modalitaPagamenti=Costant.modalitaPagamentoSoloOnlineLabel;
		this.modalitaPagamentiOpzionali=Costant.modalitaPagamentoSoloOnlineLabel;
	}
	
	public ParametriPUBean(String xmlParametri){
		this.autenticazioneForte=false;
		this.autenticazioneDebole=true;
		this.abilitaUtente=true;
		this.abilitaIntermediari=true;
		
		XPathReader xpr = new XPathReader(xmlParametri);
		String tipoBookmark = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/TYPE"),"");
		String tipoFirma = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/FIRMADIGITALE"),"");
		String tipoPagamenti = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/PAGAMENTO"),"");
		String modalitaPagamenti = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/MODALITA_PAGAMENTO"),"");
		String modalitaPagamentiOpzionali = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/MODALITA_PAGAMENTO_OPZIONALE"),"");
		String conInvioString = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/CON_INVIO"),"");
		if (tipoBookmark.equalsIgnoreCase(Costant.bookmarkTypeCompleteLabel)){
			this.tipo=Costant.bookmarkTypeCompleteLabel;
		} else if (tipoBookmark.equalsIgnoreCase(Costant.bookmarkTypeCortesiaLabel)){
			this.tipo=Costant.bookmarkTypeCortesiaLabel;
		} else {
			this.tipo=Costant.bookmarkTypeLivello2Label;
		}

		if (tipoPagamenti.equalsIgnoreCase(Costant.disabilitaPagamentoLabel)){
			this.pagamenti=Costant.disabilitaPagamentoLabel;
		} else if (tipoPagamenti.equalsIgnoreCase(Costant.forzaPagamentoLabel)){
			this.pagamenti=Costant.forzaPagamentoLabel;
			this.modalitaPagamenti=modalitaPagamenti;
		} else {
			this.pagamenti=Costant.pagamentoOpzionaleLabel;
			this.modalitaPagamentiOpzionali=modalitaPagamentiOpzionali;
		}
		
		
		this.conInvio=Utilities.checked(conInvioString);
		this.abilitaFirma=Utilities.checked(tipoFirma);
		
	}

	
	public boolean isAutenticazioneForte() {
		return autenticazioneForte;
	}


	public void setAutenticazioneForte(boolean autenticazioneForte) {
		this.autenticazioneForte = autenticazioneForte;
	}


	public boolean isAutenticazioneDebole() {
		return autenticazioneDebole;
	}


	public void setAutenticazioneDebole(boolean autenticazioneDebole) {
		this.autenticazioneDebole = autenticazioneDebole;
	}
	
	public boolean isAbilitaUtente() {
		return abilitaUtente;
	}

	public void setAbilitaUtente(boolean abilitaUtente) {
		this.abilitaUtente = abilitaUtente;
	}

	public boolean isAbilitaIntermediari() {
		return abilitaIntermediari;
	}

	public void setAbilitaIntermediari(boolean abilitaIntermediari) {
		this.abilitaIntermediari = abilitaIntermediari;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isConInvio() {
		return conInvio;
	}

	public void setConInvio(boolean conInvio) {
		this.conInvio = conInvio;
	}

	public boolean isAbilitaFirma() {
		return abilitaFirma;
	}

	public void setAbilitaFirma(boolean abilitaFirma) {
		this.abilitaFirma = abilitaFirma;
	}

	public String getPagamenti() {
		return pagamenti;
	}

	public void setPagamenti(String pagamenti) {
		this.pagamenti = pagamenti;
	}

	/**
	 * @return the modalitaPagamenti
	 */
	public final String getModalitaPagamenti() {
		return this.modalitaPagamenti;
	}

	/**
	 * @param modalitaPagamenti the modalitaPagamenti to set
	 */
	public final void setModalitaPagamenti(String modalitaPagamenti) {
		this.modalitaPagamenti = modalitaPagamenti;
	}

	/**
	 * @return the modalitaPagamentiOpzionali
	 */
	public final String getModalitaPagamentiOpzionali() {
		return this.modalitaPagamentiOpzionali;
	}

	/**
	 * @param modalitaPagamentiOpzionali the modalitaPagamentiOpzionali to set
	 */
	public final void setModalitaPagamentiOpzionali(
			String modalitaPagamentiOpzionali) {
		this.modalitaPagamentiOpzionali = modalitaPagamentiOpzionali;
	}

	public String buildServiceProfileXML(){
	    String defaultServiceProfileString = null;
	    PeopleServiceProfileDocument document = PeopleServiceProfileDocument.Factory.newInstance();
	    PeopleServiceProfile serviceProfile = document.addNewPeopleServiceProfile();
	    Service service = serviceProfile.addNewService();
	    SecurityProfile securityProfile = service.addNewSecurityProfile();
	    AuthorizationProfile authorizationProfile = service.addNewAuthorizationProfile();
	    AccessoUtentePeopleRegistrato accessoUtentePeopleRegistrato = authorizationProfile.addNewAccessoUtentePeopleRegistrato();
	    AccessoIntermediari accessoIntermediari = authorizationProfile.addNewAccessoIntermediari();
	    service.setName("it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico");
	    service.setCategory("concessioniedautorizzazioni");
	    service.setSubcategory("servizicondivisi");
	    securityProfile.setWeakAuthentication(this.autenticazioneDebole);
	    securityProfile.setStrongAuthentication(this.autenticazioneForte);
	    accessoUtentePeopleRegistrato.setEnabled(this.abilitaUtente);
	    accessoIntermediari.setEnabled(this.abilitaIntermediari);
	    accessoIntermediari.setAll(this.abilitaIntermediari);
	    defaultServiceProfileString = document.xmlText();
	    return defaultServiceProfileString;
	}
	
}
