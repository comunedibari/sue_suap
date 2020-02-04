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
package it.people.fsl.servizi.admin.sirac.accreditamento.model;

import it.people.core.PeopleContext;
import it.people.core.PplUser;
import it.people.process.AbstractPplProcess;
import it.people.process.data.AbstractData;
import it.people.sirac.accr.ProfiliHelper;
import it.people.sirac.accr.beans.AbstractProfile;
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.ProfiloLocale;
import it.people.sirac.accr.beans.ProfiloPersonaFisica;
import it.people.sirac.accr.beans.Qualifica2Persona;
import it.people.sirac.deleghe.beans.CriteriRicercaDeleganti;
import it.people.sirac.web.forms.AccrIntrmForm;
import it.people.sirac.web.forms.DelegaForm;
import it.people.vsl.PipelineData;

import java.util.List;

/**
 * @author max
 *
 */
public class ProcessData extends AbstractData {

  private ProfiloLocale profiloLocale;
  private PplUser pplUser;
  private AccrIntrmForm accrIntrmForm;
  private DelegaForm delegaForm;
  private String operazione;
  private int selezioneAccreditamentoIndex;
  private boolean precompilaFormProfilo;
  
  private final int annullaSelezioneAccreditamentoIndexValue = 9999; 
  
  /* true all'inizializzazione del processo, poi impostato a false */
  protected boolean showActivityMenu = true;
  
  /* Utilizzato per contenere la lista degli accreditamenti presentata all'utente */
  private Accreditamento[] elencoAccreditamenti;
  private Qualifica2Persona[] elencoQualifiche2Persona;
  private ProfiliHelper selAccrProfiliHelper;
  private ProfiloPersonaFisica selaccrOperatore;
  private ProfiloPersonaFisica selaccrRichiedente;
  private AbstractProfile selaccrTitolare;
  
  /* Utilizzato per contenere l'elenco dei criter di ricerca dei deleganti 
   * E' stato messo qui perch� � utilizzato in una loopback e sembra che il 
   * codice del framework non consenta l'uso dei DTO durante una loopback
   * (vedere classe it.people.action.Loopback del fe_framework) */
  private CriteriRicercaDeleganti criteriRicercaDeleganti;
  private String codiceFiscaleDeleganteSelezionato;
  private List elencoDelegantiTrovati;
  private boolean mostraLinkConferma;
  private boolean enableModifyTitolare;
  
  private String tipoRichiestaDelega;
  
  public static final String GESTIONE_ACCR_OPER_CREAACCR = "Creazione Accreditamento";
  public static final String GESTIONE_ACCR_OPER_CREADELEGA = "Creazione Delega";
  public static final String GESTIONE_ACCR_OPER_SELACCR = "Selezione Accreditamento";
  public static final String GESTIONE_ACCR_OPER_UNDEFINED = "UNDEFINED";
  
  public static final String RICHIESTA_PRELIMINARE_DELEGA = "Richiesta Preliminare Delega";
  public static final String RICHIESTA_ATTIVAZIONE_DELEGA = "Richiesta Attivazione Delega";
  
  public ProcessData() {
    super();
    this.m_clazz = ProcessData.class;
}

  /**
   * @see it.people.process.PplData#initialize(it.people.core.PeopleContext, it.people.process.AbstractPplProcess)
   */
  public void initialize(PeopleContext context, AbstractPplProcess pplProcess) {
    accrIntrmForm = new AccrIntrmForm();
    criteriRicercaDeleganti = new CriteriRicercaDeleganti();
    elencoDelegantiTrovati = null;
    codiceFiscaleDeleganteSelezionato = null;
    delegaForm = new DelegaForm();
    operazione = GESTIONE_ACCR_OPER_UNDEFINED;
    selezioneAccreditamentoIndex=0;
    elencoAccreditamenti = null;
    elencoQualifiche2Persona=null;
    precompilaFormProfilo = true;
    selaccrOperatore = new ProfiloPersonaFisica();
    selaccrRichiedente = new ProfiloPersonaFisica();
    selaccrTitolare = null;
    mostraLinkConferma = false;
    enableModifyTitolare = true;
    
    setShowActivityMenu(false);
  }

  /* (non-Javadoc)
   * @see it.people.process.data.AbstractData#doDefineValidators()
   */
  protected void doDefineValidators() {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see it.people.process.PplData#exportToPipeline(it.people.vsl.PipelineData)
   */
  public void exportToPipeline(PipelineData pd) {
    // TODO Auto-generated method stub

  }
  
  /*public boolean validate() {
   return true;
  }*/

  /**
   * @return Returns the profiloLocale.
   */
  public ProfiloLocale getProfiloLocale() {
    return profiloLocale;
  }
  /**
   * @param profiloLocale The profiloLocale to set.
   */
  public void setProfiloLocale(ProfiloLocale profiloLocale) {
    this.profiloLocale = profiloLocale;
  }
  
  /**
   * @return Returns the pplUser.
   */
  public PplUser getPplUser() {
    return pplUser;
  }
  /**
   * @param pplUser The pplUser to set.
   */
  public void setPplUser(PplUser pplUser) {
    this.pplUser = pplUser;
  }
  /**
   * @return Returns the accrIntrmForm.
   */
  public AccrIntrmForm getAccrIntrmForm() {
    return accrIntrmForm;
  }
  /**
   * @param accrIntrmForm The accrIntrmForm to set.
   */
  public void setAccrIntrmForm(AccrIntrmForm accrIntrmForm) {
    this.accrIntrmForm = accrIntrmForm;
  }
  /**
   * @return Returns the delegaForm.
   */
  public DelegaForm getDelegaForm() {
    return delegaForm;
  }
  /**
   * @param delegaForm The delegaForm to set.
   */
  public void setDelegaForm(DelegaForm delegaForm) {
    this.delegaForm = delegaForm;
  }
  /**
   * @return Returns the operazione.
   */
  public String getOperazione() {
    return operazione;
  }
  /**
   * @param operazione The operazione to set.
   */
  public void setOperazione(String operazione) {
    this.operazione = operazione;
  }
  /**
   * @return Returns the selezioneAccreditamentoIndex.
   */
  public int getSelezioneAccreditamentoIndex() {
    return selezioneAccreditamentoIndex;
  }
  /**
   * @param selezioneAccreditamentoIndex The selezioneAccreditamentoIndex to set.
   */
  public void setSelezioneAccreditamentoIndex(int selezioneAccreditamentoIndex) {
    this.selezioneAccreditamentoIndex = selezioneAccreditamentoIndex;
  }
  /**
   * @return Returns the elencoAccreditamenti.
   */
  public Accreditamento[] getElencoAccreditamenti() {
    return elencoAccreditamenti;
  }
  /**
   * @param elencoAccreditamenti The elencoAccreditamenti to set.
   */
  public void setElencoAccreditamenti(Accreditamento[] elencoAccreditamenti) {
    this.elencoAccreditamenti = elencoAccreditamenti;
  }
  /**
   * @return Returns the elencoQualifiche2Persona.
   */
  public Qualifica2Persona[] getElencoQualifiche2Persona() {
    return elencoQualifiche2Persona;
  }
  /**
   * @param elencoQualifiche2Persona The elencoQualifiche2Persona to set.
   */
  public void setElencoQualifiche2Persona(
      Qualifica2Persona[] elencoQualifiche2Persona) {
    this.elencoQualifiche2Persona = elencoQualifiche2Persona;
  }
  /**
   * @return Returns the selaccrOperatore.
   */
  public ProfiloPersonaFisica getSelaccrOperatore() {
    return selaccrOperatore;
  }
  /**
   * @param selaccrOperatore The selaccrOperatore to set.
   */
  public void setSelaccrOperatore(ProfiloPersonaFisica selaccrOperatore) {
    this.selaccrOperatore = selaccrOperatore;
  }
  /**
   * @return Returns the selaccrRichiedente.
   */
  public ProfiloPersonaFisica getSelaccrRichiedente() {
    return selaccrRichiedente;
  }
  /**
   * @param selaccrRichiedente The selaccrRichiedente to set.
   */
  public void setSelaccrRichiedente(ProfiloPersonaFisica selaccrRichiedente) {
    this.selaccrRichiedente = selaccrRichiedente;
  }
  /**
   * @return Returns the selaccrTitolare.
   */
  public AbstractProfile getSelaccrTitolare() {
    return selaccrTitolare;
  }
  /**
   * @param selaccrTitolare The selaccrTitolare to set.
   */
  public void setSelaccrTitolare(AbstractProfile selaccrTitolare) {
    this.selaccrTitolare = selaccrTitolare;
  }
  /**
   * @return Returns the precompilaFormProfilo.
   */
  public boolean isPrecompilaFormProfilo() {
    return precompilaFormProfilo;
  }
  /**
   * @param precompilaFormProfilo The precompilaFormProfilo to set.
   */
  public void setPrecompilaFormProfilo(boolean precompilaFormRL) {
    this.precompilaFormProfilo = precompilaFormRL;
  }
  /**
   * @return Returns the selAccrProfiliHelper.
   */
  public ProfiliHelper getSelAccrProfiliHelper() {
    return selAccrProfiliHelper;
  }
  /**
   * @param selAccrProfiliHelper The selAccrProfiliHelper to set.
   */
  public void setSelAccrProfiliHelper(ProfiliHelper selAccrProfiliHelper) {
    this.selAccrProfiliHelper = selAccrProfiliHelper;
  }
  /**
   * @return Returns the showActivityMenu.
   */
  public boolean isShowActivityMenu() {
    return showActivityMenu;
  }
  /**
   * @param showActivityMenu The showActivityMenu to set.
   */
  public void setShowActivityMenu(boolean showActivityMenu) {
    this.showActivityMenu = showActivityMenu;
  }
  /**
   * @return Returns the annullaSelezioneAccreditamentoIndexValue.
   */
  public int getAnnullaSelezioneAccreditamentoIndexValue() {
    return annullaSelezioneAccreditamentoIndexValue;
  }
  /**
   * @param criteriRicercaDeleganti The criteriRicercaDeleganti to set.
   */
  public void setCriteriRicercaDeleganti(CriteriRicercaDeleganti criteriRicercaDeleganti) {
    this.criteriRicercaDeleganti = criteriRicercaDeleganti;
  }
  /**
   * @return Returns the criteriRicercaDeleganti.
   */
  public CriteriRicercaDeleganti getCriteriRicercaDeleganti() {
    return criteriRicercaDeleganti;
  }
  /**
   * @param codiceFiscaleDeleganteSelezionato The codiceFiscaleDeleganteSelezionato to set.
   */
  public void setCodiceFiscaleDeleganteSelezionato(String codiceFiscaleDeleganteSelezionato) {
    this.codiceFiscaleDeleganteSelezionato = codiceFiscaleDeleganteSelezionato;
  }
  /**
   * @return Returns the codiceFiscaleDeleganteSelezionato.
   */
  public String getCodiceFiscaleDeleganteSelezionato() {
    return codiceFiscaleDeleganteSelezionato;
  }
  /**
   * @param elencoDelegantiTrovati The elencoDelegantiTrovati to set.
   */
  public void setElencoDelegantiTrovati(List elencoDelegantiTrovati) {
    this.elencoDelegantiTrovati = elencoDelegantiTrovati;
  }
  /**
   * @return Returns the elencoDelegantiTrovati.
   */
  public List getElencoDelegantiTrovati() {
    return elencoDelegantiTrovati;
  }
  /**
   * @param mostraLinkConferma The mostraLinkConferma to set.
   */
  public void setMostraLinkConferma(boolean mostraLinkConferma) {
    this.mostraLinkConferma = mostraLinkConferma;
  }
  /**
   * @return Returns the mostraLinkConferma.
   */
  public boolean getMostraLinkConferma() {
    return mostraLinkConferma;
  }
  /**
   * @param enableModifyTitolare The enableModifyTitolare to set.
   */
  public void setEnableModifyTitolare(boolean enableModifyTitolare) {
    this.enableModifyTitolare = enableModifyTitolare;
  }
  /**
   * @return Returns the enabledModifyTitolare.
   */
  public boolean getEnableModifyTitolare() {
    return enableModifyTitolare;
  }
  
  /**
   * @param tipoRichiestaDelega The tipoRichiestaDelega to set.
   */
  public void setTipoRichiestaDelega(String tipoRichiestaDelega ) {
    this.tipoRichiestaDelega = tipoRichiestaDelega;
  }
  /**
   * @return Returns the tipoRichiestaDelega.
   */
  public String getTipoRichiestaDelega() {
    return tipoRichiestaDelega;
  }
  
}
