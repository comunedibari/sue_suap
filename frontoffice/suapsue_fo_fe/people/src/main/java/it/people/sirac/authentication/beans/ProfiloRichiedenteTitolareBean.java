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
package it.people.sirac.authentication.beans;

import it.people.sirac.accr.beans.AbstractProfile;
import it.people.sirac.accr.beans.ProfiloPersonaFisica;

public class ProfiloRichiedenteTitolareBean {
  
  protected ProfiloPersonaFisica profiloRichiedente = null;
  protected AbstractProfile profiloTitolare = null;
  protected String domicilioElettronicoAssociazione = null;
  protected String formAction = null;
  protected String target = null;
  protected boolean editAllowed;
  
  /**
   * 
   */
  public ProfiloRichiedenteTitolareBean() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @return Returns the formAction.
   */
  public String getFormAction() {
    return formAction;
  }
  /**
   * @param formAction The formAction to set.
   */
  public void setFormAction(String formAction) {
    this.formAction = formAction;
  }
  /**
   * @return Returns the profiloRichiedente.
   */
  public ProfiloPersonaFisica getProfiloRichiedente() {
    return profiloRichiedente;
  }
  /**
   * @param profiloRichiedente The profiloRichiedente to set.
   */
  public void setProfiloRichiedente(ProfiloPersonaFisica profiloRichiedente) {
    this.profiloRichiedente = profiloRichiedente;
  }
  /**
   * @return Returns the profiloTitolare.
   */
  public AbstractProfile getProfiloTitolare() {
    return profiloTitolare;
  }
  /**
   * @param profiloTitolare The profiloTitolare to set.
   */
  public void setProfiloTitolare(AbstractProfile profiloTitolare) {
    this.profiloTitolare = profiloTitolare;
  }
  /**
   * @return Returns the target.
   */
  public String getTarget() {
    return target;
  }
  /**
   * @param target The target to set.
   */
  public void setTarget(String target) {
    this.target = target;
  }
  /**
   * @return Returns the domicilioElettronicoAssociazione.
   */
  public String getDomicilioElettronicoAssociazione() {
    return domicilioElettronicoAssociazione;
  }
  /**
   * @param domicilioElettronicoAssociazione The domicilioElettronicoAssociazione to set.
   */
  public void setDomicilioElettronicoAssociazione(String domicilioElettronicoAssociazione) {
    this.domicilioElettronicoAssociazione = domicilioElettronicoAssociazione;
  }

  public boolean isEditAllowed() {
    return editAllowed;
  }
	
  public void setEditAllowed(boolean editAllowed) {
    this.editAllowed = editAllowed;
  }
  
}
