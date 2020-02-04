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
/*

  Licenza:	    Licenza Progetto PEOPLE
  Fornitore:    CEFRIEL
  Autori:       M. Pianciamore, P. Selvini

  Questo codice sorgente � protetto dalla licenza valida nell'ambito del
  progetto PEOPLE. La propriet� intellettuale di questo codice � e rester�
  esclusiva di "CEFRIEL Societ� Consortile a Responsabilit� Limitata" con
  sede legale in via Renato Fucini 2, 20133 Milano (MI).

  Disclaimer:

  COVERED CODE IS PROVIDED UNDER THIS LICENSE ON AN "AS IS" BASIS, WITHOUT
  WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, WITHOUT 
  LIMITATION, WARRANTIES THAT THE COVERED CODE IS FREE OF DEFECTS, MERCHANTABLE,
  FIT FOR A PARTICULAR PURPOSE OR NON-INFRINGING. THE ENTIRE RISK AS TO THE
  QUALITY AND PERFORMANCE OF THE COVERED CODE IS WITH YOU. SHOULD ANY COVERED
  CODE PROVE DEFECTIVE IN ANY RESPECT, YOU (NOT THE INITIAL DEVELOPER OR ANY
  OTHER CONTRIBUTOR) ASSUME THE COST OF ANY NECESSARY SERVICING, REPAIR OR
  CORRECTION.
    
*/

package it.people.envelope.util;

import org.apache.log4j.Logger;

import java.util.Date;

import it.people.envelope.IRequestEnvelope;
import it.people.envelope.RequestEnvelope;
import it.people.envelope.beans.Allegato;
import it.people.envelope.beans.CredenzialiUtenteCertificate;
import it.people.envelope.beans.EstremiRichiedente;
import it.people.envelope.beans.EstremiTitolare;
import it.people.envelope.beans.IdentificatoreDiRichiesta;
import it.people.envelope.beans.IdentificatoreUnivoco;
import it.people.fsl.servizi.deleghe.beans.PersonaFisica;
import it.people.fsl.servizi.deleghe.beans.PersonaGiuridica;
import it.people.fsl.servizi.deleghe.beans.Richiedente;
import it.people.fsl.servizi.deleghe.beans.Titolare;
import it.people.fsl.servizi.deleghe.beans.servizi.richieste.VisualizzazioneVerificaAbilitazioneIntermediario;
import it.people.fsl.servizi.deleghe.beans.servizi.risposte.RispostadiVisualizzazioneVerificaAbilitazioneIntermediario;
import it.people.fsl.servizi.deleghe.utility.XMLUtils;

public class DelegheHelper {

	private static final Logger logger = Logger.getLogger(DelegheHelper.class);
	
  public static void checkDelega(IRequestEnvelope busta, String checkDelegheWSURL) throws Exception { 
  	
	if(!busta.getForceSkipCheckDelega()){  
	  
	  	EstremiRichiedente richiedenteBusta = busta.getEstremiRichiedente();
	  	EstremiTitolare titolareBusta = busta.getEstremiTitolare();
	  	EstremiTitolare soggettoDelegatoBusta = busta.getEstremiSoggettoDelegato();
	  	
	  	String nomeServizio = busta.getNomeServizio();
	  	String contestoServizio = busta.getContestoServizio();
	  	
	  	IdentificatoreDiRichiesta idRichiestaBusta = busta.getIdentificatoreDiRichiesta();
	  	IdentificatoreUnivoco idUnivocoBusta = idRichiestaBusta.getIdentificatoreUnivoco();
	  	String idRichiesta = idUnivocoBusta.getCodiceIdentificativoOperazione();
	//  	String cfDelegato = richiedenteBusta.getCodiceFiscale();
	  	String codiceAmministrazione = busta.getCodiceDestinatario();
	  
	//  	it.people.envelope.beans.Recapito[] recapitiBusta = busta.getRecapiti();
	//  	Recapito recapitoBE = new Recapito();
	//  	boolean emailAlreadyFound = false;
	//  	
	//  	for(int i=0; i < recapitiBusta.length; i++){
	//  		it.people.envelope.beans.Recapito recapitoBusta = recapitiBusta[i];
	//  		recapitoBE.setReferente(recapitoBusta.getReferente());
	//  		if(recapitoBusta.isRecapitoIndirizzoEmail() && !emailAlreadyFound){
	//  			recapitoBE.setEmail(recapitoBusta.getIndirizzoEmail());
	//  			emailAlreadyFound = true;
	//  		} else if(recapitoBusta.isRecapitoPostale()){
	//  			recapitoBE.setIndirizzo(recapitoBusta.getRecapitoPostaleTestuale());
	//  		} else if(recapitoBusta.isRecapitoTelefonico()){
	//  			RecapitoTelefonico recapitoTelefonico = recapitoBusta.getRecapitoTelefonico();
	//  			if(recapitoTelefonico.isNumeroTelefonicoEstero()){
	//  				recapitoBE.setTelefono(recapitoTelefonico.getNumeroTelefonicoEstero().toString());
	//  			} else{
	//  				recapitoBE.setTelefono(recapitoTelefonico.getNumeroTelefonicoNazionale().toString());
	//  			}
	//  		}
	//  	}
	
	  	Richiedente richiedenteBE = new Richiedente();
	  	richiedenteBE.setCodiceFiscale(richiedenteBusta.getCodiceFiscale());
	  	richiedenteBE.setCognome(richiedenteBusta.getCognome());
	  	richiedenteBE.setNome(richiedenteBusta.getNome());
	  	
	  	String cfDelegato = null;
		if(soggettoDelegatoBusta.isEstremiPersonaFisica()){
			cfDelegato = soggettoDelegatoBusta.getEstremiPersonaFisica().getCodiceFiscale(); 
		} else{
			cfDelegato = soggettoDelegatoBusta.getEstremiPersonaGiuridica().getCodiceFiscalePG();
		}
	  	
	  	Titolare titolareBE = new Titolare();
	  	String cfDelegante = null;
	  	if(titolareBusta.isEstremiPersonaFisica()){
	  		cfDelegante = titolareBusta.getEstremiPersonaFisica().getCodiceFiscale();
	  		PersonaFisica titolarePFBE = new PersonaFisica();
	  		titolarePFBE.setCodiceFiscale(cfDelegato);
	  		titolarePFBE.setCognome(titolareBusta.getEstremiPersonaFisica().getCognome());
	  		titolarePFBE.setNome(titolareBusta.getEstremiPersonaFisica().getNome());
	//  		titolarePFBE.setRecapito(recapitoBE);
	  		titolareBE.setPersonaFisica(titolarePFBE);
	  	} else{
	  		cfDelegante = titolareBusta.getEstremiPersonaGiuridica().getCodiceFiscalePG();
	  		PersonaGiuridica titolarePGBE = new PersonaGiuridica();
	  		titolarePGBE.setCodiceFiscale(cfDelegato);
	  		titolarePGBE.setDenominazione(titolareBusta.getEstremiPersonaGiuridica().getDenominazioneoRagioneSocialePG());
	//  		titolarePGBE.setRecapito(recapitoBE);
	  		titolareBE.setPersonaGiuridica(titolarePGBE);
	  	}
		
	  	VisualizzazioneVerificaAbilitazioneIntermediario bean = new VisualizzazioneVerificaAbilitazioneIntermediario();
	  	bean.setCodiceAmministrazione(codiceAmministrazione);
	  	bean.setCodiceFiscaleDelegato(cfDelegato);
	  	bean.setCodiceFiscaleDelegante(cfDelegante);
	  	bean.setIdentificatorediRichiesta(idRichiesta);
	  	bean.setNomeServizio(nomeServizio);
	  	bean.setContesto(contestoServizio);
	  	bean.setRichiedente(richiedenteBE);
	  	bean.setTitolare(titolareBE);
	  	
	  	String beanXML = XMLUtils.marshall(bean);
	  	
	  	logger.debug(beanXML);
	
	  	BEServiceClientAdapter ws = new BEServiceClientAdapter(checkDelegheWSURL);
	  	String output = ws.process(beanXML);
	  	
			Object responseObj = XMLUtils.unmarshall(RispostadiVisualizzazioneVerificaAbilitazioneIntermediario.class,output);
	  	RispostadiVisualizzazioneVerificaAbilitazioneIntermediario response = (RispostadiVisualizzazioneVerificaAbilitazioneIntermediario)responseObj;
	   	
	  	if(!response.isAbilitato()){ 
	  		throw new Exception("Il codice fiscale " + cfDelegato + " non possiede una delega ad operare per conto di " + cfDelegante + " per il servizio " + nomeServizio + " nel contesto " + contestoServizio);
	  	}
	}
  	
  }

  public static void main(String[] args){
  	
  	try{
	  	IRequestEnvelope requestBean = new RequestEnvelope();
	  	requestBean.setNomeServizio("servizio1");
	    requestBean.setContestoServizio("it.people.fsl.servizi.admin.sirac.accreditamento");
	    // Codice amministrazione destinataria. Secondo direttive CNIPA
	    // dovrebbe avere il seguente formato c_<codicebelfiorecomune>, ma la modellazione
	    // attualmente non consente l'uso di '_' nel codice amministrazione
	    requestBean.setCodiceDestinatario("F205"); 
	    
	    requestBean.setIdentificatoreDiRichiesta( 
	      new IdentificatoreDiRichiesta(
	          new IdentificatoreUnivoco(
	              "1001", "PRJ001", "feF205", "SERVER001", new Date())));
	    
	    requestBean.setEstremiRichiedente( 
	      new EstremiRichiedente("Mario", "Rossi", "RSSMRA50A01F205R"));
	    
	    requestBean.setEstremiTitolare(
	      new EstremiTitolare(requestBean.getEstremiRichiedente().getEstremiPersonaFisica())); 
	    
	    requestBean.setMittente( 
	      new CredenzialiUtenteCertificate(
	          requestBean.getEstremiRichiedente().getCodiceFiscale(), 
	          new Allegato(
	              "Credenziali.xml", 
	              "Credenziali firmate del richiedente", 
	              new String("fjhgfjhgfjhjqwer5435").getBytes(),
	              null, Allegato.TIPO_ALLEGATO_FIRMATO)));
	    
	    it.people.envelope.beans.Recapito recapito = new it.people.envelope.beans.Recapito("ReferenteRecapito", it.people.envelope.beans.Recapito.PRIORITA_RECAPITO_PRINCIPALE);
	    recapito.setIndirizzoEmail("aa@bb.cdef");
	    //recapito.setRecapitoPostaleTestuale("Recapito postale testuale");
	    
	    requestBean.addRecapito(recapito);
	    
	    DelegheHelper.checkDelega(requestBean, "http://localhost:8000/BEDeleghe/services/deleghe.visualizzazioneverificaabilitazioneintermediario");
	    
	    logger.info("OK");
	    
  	} catch(Exception ex){
  		ex.printStackTrace();
  	}
  		
  	
  }
  
}
