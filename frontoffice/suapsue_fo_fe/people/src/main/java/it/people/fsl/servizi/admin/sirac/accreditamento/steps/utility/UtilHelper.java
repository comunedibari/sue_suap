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
package it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility;

import it.people.core.PplUserData;
import it.people.fsl.servizi.admin.sirac.accreditamento.model.ProcessData;
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.Delega;
import it.people.sirac.accr.beans.ProfiloLocale;
import it.people.sirac.accr.beans.ProfiloPersonaFisica;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.services.accr.IAccreditamentoClientAdapter;
import it.people.sirac.util.Utilities;
import it.people.sirac.web.forms.AccrIntrmForm;
import it.people.sirac.web.forms.DelegaForm;
import it.people.sirac.web.forms.RapprLegaleForm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

public class UtilHelper {

  public static final String SIRAC_BACKUP_AUTHENTICATED_USER = "it.people.sirac.backup_operator_user";
  public static final String SIRAC_BACKUP_AUTHENTICATED_USERDATA = "it.people.sirac.backup_operator_userdata";
  public static final String SIRAC_UPDATE_PAGE_ALREADYSHOWN = "it.people.sirac.updatePageAlreadyShown";
  public static final String PEOPLE_SAVED_DOMICILIOELETTRONICO = "it.people.savedDomicilioElettronico";
  public static final String PEOPLE_RETURNING_FROM_PAYMENT = "it.people.returningFromPayment";

  public static void fillRLFormWithUserData(RapprLegaleForm rlForm, PplUserData userData) {
    rlForm.clear();
    rlForm.setNome(userData.getNome());
    rlForm.setCognome(userData.getCognome());
    rlForm.setCodiceFiscale(userData.getCodiceFiscale());
    rlForm.setDataNascita(userData.getDataNascita());
    rlForm.setLuogoNascita(userData.getLuogoNascita());
    rlForm.setProvinciaNascita(userData.getProvinciaNascita());
    rlForm.setSesso(userData.getSesso());
    //rlForm.setIndirizzoResidenza(userData.getIndirizzoResidenza());
    rlForm.setIndirizzoResidenza(userData.getCittaResidenza() 
        + "(" + userData.getProvinciaResidenza() + "), " + userData.getIndirizzoResidenza());
    
  }

  public static void fillProfiloPersonaFisicaWithUserData(ProfiloPersonaFisica profiloRichiedente, PplUserData userData) {
    profiloRichiedente.clear();
    profiloRichiedente.setNome(userData.getNome());
    profiloRichiedente.setCognome(userData.getCognome());
    profiloRichiedente.setCodiceFiscale(userData.getCodiceFiscale());
    try {
      profiloRichiedente.setDataNascitaString(userData.getDataNascita());
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    profiloRichiedente.setLuogoNascita(userData.getLuogoNascita());
    if (StringUtils.isBlank(userData.getProvinciaNascita())) {
        profiloRichiedente.setProvinciaNascita("_");
    } else {
        profiloRichiedente.setProvinciaNascita(userData.getProvinciaNascita());
    }
    profiloRichiedente.setSesso(userData.getSesso());
    //rlForm.setIndirizzoResidenza(userData.getIndirizzoResidenza());
    profiloRichiedente.setIndirizzoResidenza(userData.getCittaResidenza() 
        + "(" + userData.getProvinciaResidenza() + "), " + userData.getIndirizzoResidenza());
    // FIX 2007-06-13 - Aggiunto domicilio elettronico
    profiloRichiedente.setDomicilioElettronico(userData.getEmailaddress());
    
  }

  public static void cloneProfiloPersonaFisica(ProfiloPersonaFisica profiloSource, ProfiloPersonaFisica profiloTarget) {
    profiloTarget.clear();
    profiloTarget.setNome(profiloSource.getNome());
    profiloTarget.setCognome(profiloSource.getCognome());
    profiloTarget.setCodiceFiscale(profiloSource.getCodiceFiscale());
    try {
      profiloTarget.setDataNascitaString(profiloSource.getDataNascitaString());
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    profiloTarget.setLuogoNascita(profiloSource.getLuogoNascita());
    profiloTarget.setProvinciaNascita(profiloSource.getProvinciaNascita());
    profiloTarget.setSesso(profiloSource.getSesso());
    //rlForm.setIndirizzoResidenza(userData.getIndirizzoResidenza());
    profiloTarget.setIndirizzoResidenza(profiloSource.getIndirizzoResidenza());
    // FIX 2007-06-13 - Aggiunto domicilio elettronico
    // profiloTarget.setDomicilioElettronico(profiloSource.getDomicilioElettronico());
    
  }

  public static boolean isProfessionista(String tipoQualifica) {
    return "Professionista".equals(tipoQualifica);
  }
  
  public static Map createProfileDataMap(PplUserData userData, AccrIntrmForm accrIntrmForm) {
    String codiceFiscaleUtente = userData.getCodiceFiscale();
    String nome = userData.getNome();
    String cognome = userData.getCognome();
    String luogoNascita = userData.getLuogoNascita();
    String dataNascita = userData.getDataNascita();
    String indirizzoResidenza = 
      userData.getCittaResidenza() 
      + "(" + userData.getProvinciaResidenza() 
      + "), " + userData.getIndirizzoResidenza();

    SimpleDateFormat sdf = new SimpleDateFormat("dd MMMMM yyyy", java.util.Locale.ITALIAN);
    Date today = new Date();
    String dataCorrente = sdf.format(today);

    /** template symbol table */
    Map vars = new HashMap();
    
    vars.put("tipoQualifica", accrIntrmForm.getTipoQualifica());
    vars.put("descrizioneQualifica", accrIntrmForm.getDescrizioneQualifica());
    
    vars.put("nome", nome);
    vars.put("cognome", cognome);
    vars.put("codiceFiscale", codiceFiscaleUtente);
    vars.put("luogoNascita", luogoNascita);
    vars.put("dataNascita", dataNascita);
    vars.put("indirizzoResidenza", indirizzoResidenza);
    vars.put("dataCorrente", dataCorrente);
    vars.put("denominazione", (String) accrIntrmForm.getDenominazione());
    vars.put("sedeLegale", (String) accrIntrmForm.getSedeLegale());
    vars.put("rifDocumentiUfficiali", (String) accrIntrmForm.getDescrizione());
    vars.put("codiceFiscaleIntermediario", (String) accrIntrmForm.getCodiceFiscaleIntermediario());
    vars.put("partitaIvaIntermediario", (String) accrIntrmForm.getPartitaIvaIntermediario());
    vars.put("qualifica", accrIntrmForm.getDescrizioneQualifica());
    
    vars.put("nomeRapprLegale", accrIntrmForm.getRapprLegaleForm().getNome());
    vars.put("cognomeRapprLegale", accrIntrmForm.getRapprLegaleForm().getCognome());
    vars.put("codiceFiscaleRapprLegale", accrIntrmForm.getRapprLegaleForm().getCodiceFiscale());
    vars.put("dataNascitaRapprLegale", accrIntrmForm.getRapprLegaleForm().getDataNascita());
    vars.put("luogoNascitaRapprLegale", accrIntrmForm.getRapprLegaleForm().getLuogoNascita());
    vars.put("provinciaNascitaRapprLegale", accrIntrmForm.getRapprLegaleForm().getProvinciaNascita());
    vars.put("indirizzoResidenzaRapprLegale", accrIntrmForm.getRapprLegaleForm().getIndirizzoResidenza());
    
    
    return vars;
  }
  
  public static Map createDelegaDataMap(ProfiloPersonaFisica operatore, Accreditamento curAccr, ProcessData myData) throws Exception {
	  
	  DelegaForm delegaForm = myData.getDelegaForm();
	  
	  String dataCorrente = Utilities.formatDateToString(new Date());
	  String codiceFiscaleUtente = myData.getPplUser().getUserData().getCodiceFiscale();
      String nomeDelegato = delegaForm.getNome();
      String cognomeDelegato = delegaForm.getCognome();
      String codiceFiscaleDelegato = delegaForm.getCodiceFiscaleDelegato();
      
      String codiceFiscaleDelegante = operatore.getCodiceFiscale();
      String nomeDelegante = operatore.getNome();
      String cognomeDelegante = operatore.getCognome();
      String dataNascitaDelegante = operatore.getDataNascitaString();
      String luogoNascitaDelegante = operatore.getLuogoNascita();
      String indirizzoResidenzaDelegante = operatore.getIndirizzoResidenza();
      
      String codiceFiscaleIntermediario = curAccr.getProfilo().getCodiceFiscaleIntermediario();
      String partitaIvaIntermediario = curAccr.getProfilo().getPartitaIvaIntermediario();
      String sedeIntermediario = curAccr.getProfilo().getSedeLegale();
      String denominazioneIntermediario = curAccr.getProfilo().getDenominazione();
      String qualificaIntermediario = curAccr.getQualifica().getDescrizione();
      String sedeLegaleIntermediario = curAccr.getProfilo().getSedeLegale();
      
      /** template symbol table */
      Map vars = new HashMap();
      
      vars.put("nomeDelegato", nomeDelegato);
      vars.put("cognomeDelegato", cognomeDelegato);
      vars.put("codiceFiscaleDelegato", codiceFiscaleDelegato);

      vars.put("nomeDelegante", nomeDelegante);
      vars.put("cognomeDelegante", cognomeDelegante);
      vars.put("codiceFiscaleDelegante", codiceFiscaleUtente);
      vars.put("luogoNascitaDelegante", luogoNascitaDelegante);
      vars.put("dataNascitaDelegante", dataNascitaDelegante);
      vars.put("indirizzoResidenzaDelegante", indirizzoResidenzaDelegante);
      vars.put("dataCorrente", dataCorrente);
      
      vars.put("codiceFiscaleIntermediario", codiceFiscaleIntermediario);
      vars.put("partitaIvaIntermediario", partitaIvaIntermediario);
      vars.put("denominazione", denominazioneIntermediario);
      vars.put("sedeLegale", sedeLegaleIntermediario);
      vars.put("qualificaIntermediario", qualificaIntermediario);

      /*
      vars.put("nomeRapprLegale", accrIntrmForm.getRapprLegaleForm().getNome());
      vars.put("cognomeRapprLegale", accrIntrmForm.getRapprLegaleForm().getCognome());
      vars.put("codiceFiscaleRapprLegale", accrIntrmForm.getRapprLegaleForm().getCodiceFiscale());
      vars.put("dataNascitaRapprLegale", accrIntrmForm.getRapprLegaleForm().getDataNascita());
      vars.put("luogoNascitaRapprLegale", accrIntrmForm.getRapprLegaleForm().getLuogoNascita());
      vars.put("provinciaNascitaRapprLegale", accrIntrmForm.getRapprLegaleForm().getProvinciaNascita());
      vars.put("indirizzoResidenzaRapprLegale", accrIntrmForm.getRapprLegaleForm().getIndirizzoResidenza());
      */
      
	  return vars;
	  
  }
  
  public static boolean esisteDelega(IAccreditamentoClientAdapter wsAccr, String codiceFiscaleDelegante, String codiceFiscaleDelegato, String idComune, Accreditamento accreditamento) throws Exception {
	  
	  int idAccreditamento = accreditamento.getId();
	  Delega[] deleghe = wsAccr.getDeleghe(codiceFiscaleDelegante, idComune, idAccreditamento);
	  
	  boolean delegaAlreadyPresent = false;
	  
	  for(int i=0;deleghe != null && i< deleghe.length && !delegaAlreadyPresent ;i++){
		  String codiceFiscaleDelegatoFromDelega = deleghe[i].getCodiceFiscaleDelegato();
		  if(codiceFiscaleDelegatoFromDelega.equalsIgnoreCase(codiceFiscaleDelegato)){
			  delegaAlreadyPresent = true;
		  }
	  }
	  
	  return delegaAlreadyPresent;
  }
  
  public static ProfiloLocale getProfiloLocale(ProcessData myData, IAccreditamentoClientAdapter ws_accr, String codiceFiscaleUtente, String idComune) throws Exception {
	  ProfiloLocale profilo = myData.getProfiloLocale();
	  if (profilo==null) {
	    profilo = ws_accr.getProfiloLocale(codiceFiscaleUtente, idComune);
	    myData.setProfiloLocale(profilo);
	  }
	  return profilo;
  }
  
  public static String getCodiceFiscaleFromProfiloOperatore(HttpSession session) throws Exception {
	  ProfiloPersonaFisica profiloOperatore = (ProfiloPersonaFisica)session.getAttribute(SiracConstants.SIRAC_ACCR_OPERATORE);
	  if(profiloOperatore == null){
		  throw new Exception("Profilo operatore non trovato in sessione");
	  }
	  return profiloOperatore.getCodiceFiscale();
  }
  
}
