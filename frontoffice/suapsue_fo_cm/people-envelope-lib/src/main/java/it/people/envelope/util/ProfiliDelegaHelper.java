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

/*
 * ======================================================
 * ATTENZIONE ATTENZIONE ATTENZIONE ATTENZIONE ATTENZIONE
 * ======================================================
 * Questa classe � la copia speculare di quella contenuta
 * nel progetto SIRAC_LIB, nel package it.people.sirac.deleghe
 * 
 * La duplicazione si � resa necessaria per rendere indipendenti
 * i progetti PeopleEnvelope e SIRAC_LIB, in modo da svincolare
 * i rispettivi rilasci, rispetto al framework PEOPLE
 * 
 * Ogni modifica qui riportata deve essere propagata in modo
 * manuale alla classe presente nel progetto SIRAC_LIB
 * 
 */


package it.people.envelope.util;

import it.people.sirac.accr.ProfiliHelper;
import it.people.sirac.accr.beans.AbstractProfile;
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.ProfiloAccreditamento;
import it.people.sirac.accr.beans.ProfiloPersonaFisica;
import it.people.sirac.accr.beans.ProfiloPersonaGiuridica;
import it.people.sirac.accr.beans.RappresentanteLegale;
import it.people.sirac.core.SiracConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ProfiliDelegaHelper {

  protected Accreditamento selAccr;
  
  protected ProfiloPersonaFisica profiloRichiedente = null;
  protected ProfiloPersonaFisica profiloOperatore = null;
  protected AbstractProfile profiloTitolare = null;
  protected String tipoQualifica;
  protected boolean forceSkipCheckDelega;
  
  public static final String TIPOQUALIFICA_PROFESSIONISTA="Professionista";
  public static final String TIPOQUALIFICA_INTERMEDIARIO="Intermediario";
  public static final String TIPOQUALIFICA_RAPPRPERSGIURIDICA="Rappresentante Persona Giuridica";
  public static final String TIPOQUALIFICA_UTENTE="Utente";

  
  public ProfiliDelegaHelper(HttpServletRequest request) throws Exception {
	  this(request.getSession());
  }
  /**
   * 
   */
  public ProfiliDelegaHelper(HttpSession session) throws Exception {
    
	if (session==null) throw new Exception("DelegheHelper: sessione non valida (null). Impossibile definire delegato.");  
    this.profiloOperatore = 
    	(ProfiloPersonaFisica) session.getAttribute(SiracConstants.SIRAC_ACCR_OPERATORE);

    try {
        this.profiloRichiedente = 
        	(ProfiloPersonaFisica) session.getAttribute(SiracConstants.SIRAC_ACCR_RICHIEDENTE);
        this.profiloTitolare =
        	(AbstractProfile) session.getAttribute(SiracConstants.SIRAC_ACCR_TITOLARE);
        this.selAccr = 
        	(Accreditamento) session.getAttribute(SiracConstants.SIRAC_ACCR_ACCRSEL);
        this.tipoQualifica = selAccr.getQualifica().getTipoQualifica();
        
        // FIX 16/11/2007 - Impostazione nuovo attributo forceSkipCheckDelega introdotto nella busta 
        // versione 002.03.004 utilizzato nel tag InformazioniperVerificaDelega. Questo flag � opzionale
        // ed ha come valore di default "false". Viene impostato a true solo nel caso in cui l'accreditamento
        // selezionato e presente in sessione sia di tipo Rappresentante di Persona Giuridica
        if(selAccr.getQualifica().getTipoQualifica().equals(ProfiliHelper.TIPOQUALIFICA_RAPPRPERSGIURIDICA)){
        	forceSkipCheckDelega = true;
        } else {
        	forceSkipCheckDelega = false;
        }
    	
    } catch (Exception e) {
    	throw new Exception("DelegheHelper: Impossibile definire delegato. " + e.getMessage());  
    }
  }
  
  public AbstractProfile getProfiloDelegato() {
	  AbstractProfile profiloDelegato = null;
	  
	  if (tipoQualifica.equalsIgnoreCase(TIPOQUALIFICA_INTERMEDIARIO)) {
		  // Il delegato � la persona giuridica (CAF o assimilato) associata al profilo
		  ProfiloPersonaGiuridica profiloDelegatoPG = (ProfiloPersonaGiuridica) new ProfiloPersonaGiuridica();
		  ProfiloAccreditamento profiloIntermediario = selAccr.getProfilo();
		  profiloDelegatoPG.setCodiceFiscale(profiloIntermediario.getCodiceFiscaleIntermediario());
		  profiloDelegatoPG.setPartitaIva(profiloIntermediario.getPartitaIvaIntermediario());
		  profiloDelegatoPG.setDenominazione(profiloIntermediario.getDenominazione());
		  profiloDelegatoPG.setDescrizione(profiloIntermediario.getDescrizione());
//		  profiloDelegatoPG.setDomicilioElettronico(profiloIntermediario.getDomicilioElettronico());
		  profiloDelegatoPG.setDomicilioElettronico(profiloRichiedente.getDomicilioElettronico());
		  profiloDelegatoPG.setRappresentanteLegale(
				  ProfiliDelegaHelper.createProfiloPFFromRappresentanteLegale(profiloIntermediario.getRappresentanteLegale()));
		  profiloDelegatoPG.setSedeLegale(profiloIntermediario.getSedeLegale());
		  
		  profiloDelegato = profiloDelegatoPG;
	  } else  {
		  // In tutti gli altri casi il delegato � il richiedente
		  ProfiloPersonaFisica estremiDelegatoPF = new ProfiloPersonaFisica();
		  ProfiliDelegaHelper.initProfiloPersonaFisica(estremiDelegatoPF, profiloRichiedente);
		  profiloDelegato = estremiDelegatoPF;
	  } 
	  return profiloDelegato;
  }
  
  public AbstractProfile getProfiloDelegante() {
	  AbstractProfile profiloDelegante = null;
	  
	  if (profiloTitolare.isPersonaFisica()) {
		  ProfiloPersonaFisica profiloDelegantePF = new ProfiloPersonaFisica();
		  initProfiloPersonaFisica(profiloDelegantePF, (ProfiloPersonaFisica) profiloTitolare);
		  
		  profiloDelegante = profiloDelegantePF;
	  } else {
		  ProfiloPersonaGiuridica profiloDelegantePG = new ProfiloPersonaGiuridica();
		  initProfiloPersonaGiuridica(profiloDelegantePG, (ProfiloPersonaGiuridica) profiloTitolare);
		  profiloDelegante = profiloDelegantePG;
	  }
	  
	  return profiloDelegante;
  }

  
  /**
   * @return Returns the tipoQualifica.
   */
  public String getTipoQualifica() {
    return tipoQualifica;
  }
  /**
   * @param tipoQualifica The tipoQualifica to set.
   */
  public void setTipoQualifica(String tipoQualifica) {
    this.tipoQualifica = tipoQualifica;
  }
  
  /**
   * @return the profiloOperatore
   */
  public ProfiloPersonaFisica getProfiloOperatore() {
  	return profiloOperatore;
  }
  /**
   * @return the profiloRichiedente
   */
  public ProfiloPersonaFisica getProfiloRichiedente() {
  	return profiloRichiedente;
  }
  /**
   * @return the profiloTitolare
   */
  public AbstractProfile getProfiloTitolare() {
  	return profiloTitolare;
  }
  
  /**
   * @return the forceSkipCheckDelega
   */
  public boolean getForceSkipCheckDelega() {
	return forceSkipCheckDelega;
  }
  
  /**
   * @param forceSkipCheckDelega The forceSkipCheckDelega to set.
   */
  public void setForceSkipCheckDelega(boolean forceSkipCheckDelega){
	  this.forceSkipCheckDelega = forceSkipCheckDelega;
  }
  
//--------------------------------------------------------------

public static void initProfiloPersonaFisica(ProfiloPersonaFisica targetProfile, ProfiloPersonaFisica initProfile) {
    targetProfile.setNome(initProfile.getNome());
    targetProfile.setCognome(initProfile.getCognome());
    targetProfile.setCodiceFiscale(initProfile.getCodiceFiscale());
    targetProfile.setSesso(initProfile.getSesso());
    targetProfile.setDataNascita(initProfile.getDataNascita());
    targetProfile.setLuogoNascita(initProfile.getLuogoNascita());
    targetProfile.setProvinciaNascita(initProfile.getProvinciaNascita());
    targetProfile.setIndirizzoResidenza(initProfile.getIndirizzoResidenza());
//     FIX 2007-06-13 - Aggiunto domicilio elettronico
     targetProfile.setDomicilioElettronico(initProfile.getDomicilioElettronico());
    
  }
  
  public static void initProfiloPersonaGiuridica(ProfiloPersonaGiuridica targetProfile, ProfiloPersonaGiuridica initProfile) {
    targetProfile.setCodiceFiscale(initProfile.getCodiceFiscale());
    targetProfile.setSedeLegale(initProfile.getSedeLegale());
    targetProfile.setDenominazione(initProfile.getDenominazione());
    targetProfile.setDescrizione(initProfile.getDescrizione());
    targetProfile.setDomicilioElettronico(initProfile.getDomicilioElettronico());
    targetProfile.setPartitaIva(initProfile.getPartitaIva());
    targetProfile.setRappresentanteLegale(initProfile.getRappresentanteLegale());
    
  }
  
  protected static ProfiloPersonaFisica createProfiloPFFromRappresentanteLegale(RappresentanteLegale rl) {
	  ProfiloPersonaFisica pf = new ProfiloPersonaFisica();
	  pf.setCodiceFiscale(rl.getCodiceFiscale());
	  pf.setCognome(rl.getCognome());
	  pf.setDataNascita(rl.getDataNascita());
	  pf.setIndirizzoResidenza(rl.getIndirizzoResidenza());
	  pf.setLuogoNascita(rl.getLuogoNascita());
	  pf.setNome(rl.getNome());
	  pf.setProvinciaNascita(rl.getProvinciaNascita());
	  pf.setSesso(rl.getSesso());
	  return pf;
  }
}
