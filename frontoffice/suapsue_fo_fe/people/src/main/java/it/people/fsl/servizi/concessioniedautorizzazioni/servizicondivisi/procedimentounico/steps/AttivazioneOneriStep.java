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
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.steps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletException;

import it.gruppoinit.commons.Utilities;
import it.people.IValidationErrors;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.ProcedimentoUnicoDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.InterventoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoUnicoException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.BuilderHtml;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Costant;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.UtilProperties;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.process.SurfDirection;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.core.SiracHelper;
import it.people.util.ServiceParameters;
import it.people.wrappers.IRequestWrapper;

public class AttivazioneOneriStep extends BaseStep {

	public void service(AbstractPplProcess process, IRequestWrapper request) {
		try {
			if (initialise(process, request)) {
                            
                            
				logger.debug("AttivazioneOneriStep - service method");
				checkRecoveryBookmark(process, request);
				ProcessData dataForm = (ProcessData) process.getData();
                                  
				resetError(dataForm);
				int index = getCurrentStepIndex(process);

				// ----
				String userID = (String) session
						.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER);
				boolean isAnonymus = SiracHelper.isAnonymousUser(userID);

				ProcedimentoUnicoDAO delegate2 = new ProcedimentoUnicoDAO(db,
						language);
                                
// PC - Reiterazione domanda inizio    
//                                dataForm.setListaHref(new HashMap());                                
// PC - Reiterazione domanda fine                                
				// PC - ordinamento allegati
				dataForm.setListaHrefOrdered(new ArrayList());
				// PC - ordinamento allegati
// PC - Reiterazione domanda inizio                                 
//				delegate2.getDichiarazioniDinamiche(dataForm);
//				delegate2.getDettaglioDichiarazioniDinamiche(dataForm,
//						isAnonymus);
                                delegate2.getDichiarazioniDinamiche(dataForm, isAnonymus);
// PC - Reiterazione domanda fine 
				BuilderHtml builder = new BuilderHtml();
				Set set = dataForm.getListaHref().keySet();
				for (Iterator iterator = set.iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					SezioneCompilabileBean scb = (SezioneCompilabileBean) dataForm
							.getListaHref().get(key);
					String html = builder.generateHtmlCompilabile(dataForm,
							scb, request);
					scb.setHtmlEditable(html);
					html = builder.generateHtmlNonCompilabile(dataForm, scb,
							request);
					scb.setHtml(html);
				}

				// ---

				String clickButtonSave = (String) request
						.getAttribute("navigation.button.save");
				if ((getSurfDirection(process) == SurfDirection.forward)
						&& (clickButtonSave == null)) {
					ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(
							db, language);

					ArrayList listaInterventi = new ArrayList();
					for (Iterator iterator = dataForm.getInterventi()
							.iterator(); iterator.hasNext();) {
						InterventoBean intervento = (InterventoBean) iterator
								.next();
						listaInterventi.add(intervento.getCodice());

					}
					for (Iterator iterator = dataForm
							.getInterventiFacoltativi().iterator(); iterator
							.hasNext();) {
						InterventoBean interventoFac = (InterventoBean) iterator
								.next();
						listaInterventi.add(interventoFac.getCodice());

					}

					ArrayList root = delegate.getOneriAlbero(dataForm);
					if ((root == null || root.size() == 0)
							&& dataForm.getOneriAnticipati().size() == 0) {
						dataForm.setOneriAnticipatiPresent(false);
						dataForm.setOneriCalcolatiPresent(false);
					    dataForm.setOneriVec(new String[]{});
					    dataForm.getListaAlberoOneri().clear();
						setStep(process, request, 0, (index + 1));
						return;
					} else {
						dataForm.setOneriAnticipatiPresent(true);
// PC - Reiterazione domanda inizio
// tolto                            dataForm.setTipoAttivazioneOneri(null);
                         dataForm.setTipoAttivazioneOneri(dataForm.getTipoAttivazioneOneri());
// PC - Reiterazione domanda fine   
                         
                         
						if (root != null && root.size() > 0) {
							dataForm.setOneriCalcolatiPresent(true);
							dataForm.setListaAlberoOneri(root);
						} else {
							dataForm.setOneriCalcolatiPresent(false);
						    dataForm.setOneriVec(new String[]{});
						    dataForm.getListaAlberoOneri().clear();
						}
						// boolean forzaPagamento =
						// forzaPagamento(dataForm,process.getCommune().getOid());
						boolean forzaPagamento = dataForm
								.getTipoPagamentoBookmark().equalsIgnoreCase(
										Costant.forzaPagamentoLabel);
						boolean disattivaPagamento = dataForm
								.getTipoPagamentoBookmark().equalsIgnoreCase(
										Costant.disabilitaPagamentoLabel);
						if (disattivaPagamento) {
							dataForm.setAttivaPagamenti(false);
							setStep(process, request, 0, (index + 1));
							return;
						}
						if (forzaPagamento) {
							dataForm.setAttivaPagamenti(true);
							if (dataForm.isAttestatoPagamentoObbligatorio()) {
								dataForm.setAttivaPagamenti(false);
							}
							if (dataForm
									.isModalitaPagamentoOpzionaleSoloOnLine()) {
								setStep(process, request, 0, (index + 1));
							}
							return;
						}
					}

				} else {
					boolean forzaPagamento = false;
					boolean disabilitaPagamemento = false;
					try {
						forzaPagamento = dataForm.getTipoPagamentoBookmark()
								.equalsIgnoreCase(Costant.forzaPagamentoLabel);
						disabilitaPagamemento = dataForm
								.getTipoPagamentoBookmark().equalsIgnoreCase(
										Costant.disabilitaPagamentoLabel);
					} catch (Exception e) {
					}
					if (!dataForm.isOneriAnticipatiPresent()
							|| (forzaPagamento == true)
							|| (disabilitaPagamemento == true)) {
						setStep(process, request,
								getCurrentActivityIndex(process),
								getCurrentStepIndex(process) - 1);
						return;
					}
				}
			} else {
				throw new ProcedimentoUnicoException("Sessione scaduta");
			}
			logger.debug("AttivazioneOneriStep - service method END");
		} catch (Exception e) {
			gestioneEccezioni(process, 5, e);
		}
	}

	public void loopBack(AbstractPplProcess process, IRequestWrapper request,
			String propertyName, int index) throws IOException,
			ServletException {
		try {
			logger.debug("AttivazioneOneriStep - loopBack method");

			if (propertyName.equalsIgnoreCase("bookmark")) {
				gestioneBookmark(process, request);
			}

			logger.debug("AttivazioneOneriStep - loopBack method END");
		} catch (Exception e) {
			gestioneEccezioni(process, 5, e);
		}
	}

	public boolean logicalValidate(AbstractPplProcess process,
			IRequestWrapper request, IValidationErrors errors)
					throws ParserException {
		ProcessData dataForm = (ProcessData) process.getData();
		try {
			logger.debug("AttivazioneOneriStep - logicalValidate method");

			if (dataForm.getTipoAttivazioneOneri() != null) {
                          
                            
				if (dataForm.getTipoAttivazioneOneri().equalsIgnoreCase(
						Costant.ATTIVAZIONE_ONERI_PAGAMENTO_ON_LINE)) {
					dataForm.setAttivaPagamenti(true);
					dataForm.getDatiTemporanei().setModalitaPagamento(
							Costant.MODALITA_PAGAMENTO_ON_LINE);
					dataForm.setAttestatoPagamentoObbligatorio(false);
				} else if (dataForm.getTipoAttivazioneOneri().equalsIgnoreCase(
						Costant.ATTIVAZIONE_ONERI_PAGAMENTO_OFF_LINE)) {
					dataForm.setAttivaPagamenti(false);
					dataForm.setAttestatoPagamentoObbligatorio(true);
					dataForm.getDatiTemporanei().setModalitaPagamento(
							Costant.MODALITA_PAGAMENTO_OFF_LINE);
				} else {
					dataForm.setAttivaPagamenti(false);
					dataForm.setAttestatoPagamentoObbligatorio(false);
					dataForm.getDatiTemporanei().setModalitaPagamento(
							Costant.MODALITA_PAGAMENTO_ON_LINE);
				}
                              
				logger.debug("AttivazioneOneriStep - logicalValidate method END");
				return true;
			} else {
				errors.add("error.nessunaselezione");
				return false;
			}

		} catch (Exception e) {
			errors.add("error.generic", "Errore interno");
			gestioneEccezioni(process, 5, e);
			dataForm.setInternalError(true);
			return false;
		}
	}

	// private boolean forzaPagamento(ProcessData dataForm,String oidComune){
	// try {
	// ServiceParameters params = (ServiceParameters)
	// session.getAttribute("serviceParameters");
	// String absPathToService = params.get("absPathToService");
	// String resourcePath =
	// absPathToService+System.getProperty("file.separator")+"risorse"+System.getProperty("file.separator");
	// Properties props[] =
	// UtilProperties.getProperties(resourcePath,"parametri", oidComune);
	//
	// String forzaPagamento = UtilProperties.getPropertyKey(props[0], props[1],
	// props[2],
	// "AC.forzapagamento"+Utilities.NVL(dataForm.getIdBookmark(),""));
	//
	// if(forzaPagamento != null && forzaPagamento.equalsIgnoreCase("Y")){
	// //dataForm.getDatiTemporanei().setForzaPagamento(true);
	// return true;
	// }
	// return false;
	// } catch (Exception e) {
	// logger.error(e);
	// return true;
	// }
	// }

}
