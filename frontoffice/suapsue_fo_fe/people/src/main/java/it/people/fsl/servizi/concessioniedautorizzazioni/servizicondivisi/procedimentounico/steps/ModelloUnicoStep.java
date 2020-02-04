/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 *
 * For convenience a plain text copy of the English version of the Licence can
 * be found in the file LICENCE.txt in the top-level directory of this software
 * distribution.
 *
 * You may obtain a copy of the Licence in any of 22 European Languages at:
 *
 * http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations under
 * the Licence.
 *
 */
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.steps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import it.gruppoinit.b1.concessioniEAutorizzazioni.precompilazione.PrecompilazioneBeanDocument;
import it.gruppoinit.commons.Utilities;
import it.people.IValidationErrors;
import it.people.core.exception.ServiceException;
import it.people.fsl.adapters.ProxyManager;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.controllers.CaricaModuloCompilato;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.ProcedimentoUnicoDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AllegatoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AnagraficaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.BaseBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.CampoPrecompilazioneBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.DocumentoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.HrefCampiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.InterventoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ModelloUnicoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.NormativaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OnereBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OneriBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.PrecompilazioneBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoUnicoException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.RiepilogoOneriPagati;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SportelloBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Bean2XML;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Costant;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.HtmlRenderer;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.ManagerAnagrafica;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.ManagerAnagraficaAltroRichiedente;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.ManagerAttributiNuoviFramework;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.ManagerIntermediari;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.UtilProperties;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.XPathReader;
import it.people.fsl.servizi.oggetticondivisi.PersonaFisica;
import it.people.fsl.servizi.oggetticondivisi.Titolare;
import it.people.fsl.servizi.oggetticondivisi.personagiuridica.PersonaGiuridica;
import it.people.java.util.LinkedHashMap;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.process.SurfDirection;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.core.SiracHelper;
import it.people.util.ServiceParameters;
import it.people.util.XmlObjectWrapper;
import it.people.vsl.exception.SendException;
import it.people.wrappers.IRequestWrapper;

public class ModelloUnicoStep extends BaseStep {

    private Log log = LogFactory.getLog(this.getClass());

    public void service(AbstractPplProcess process, IRequestWrapper request) {
        try {
            if (initialise(process, request)) {
                logger.debug("ModelloUnicoStep - service method");
                checkRecoveryBookmark(process, request);
                ProcessData dataForm = (ProcessData) process.getData();
                resetError(dataForm);
                settaDescrizionePratica(dataForm);

                ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
                String userID = (String) session.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER);
                boolean isAnonymus = SiracHelper.isAnonymousUser(userID);
                if ((getSurfDirection(process) == SurfDirection.backward) && isAnonymus) {
                    String param = (String) session.getAttribute("parametriBookmark");
                    String codEventoVita = "";
                    try {
                        if (param != null) {
                            String paramToken[] = param.split("&");
                            if (paramToken != null && paramToken.length == 4) {
                                String tmp = paramToken[3];
                                String valToken[] = tmp.split("=");
                                if (valToken != null && valToken.length == 2) {
                                    codEventoVita = valToken[1];
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                    configuraParametri(process, dataForm, delegate, isAnonymus, codEventoVita);
//					if (dataForm.getIdBookmark()!=null) { 
//						String xmlPermessi = delegate.getXMLPermessiBookmark(dataForm.getIdBookmark(), dataForm.getComuneSelezionato().getCodEnte(), codEventoVita);
//						XPathReader xpr = new XPathReader(xmlPermessi);
//			    		String tipoBookmark = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/TYPE"),"");
//			    		String conInvioString = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/CON_INVIO"),"");
//			    		boolean conInvio = Utilities.checked(conInvioString.equalsIgnoreCase("")?"true":conInvioString);
//			    		if (tipoBookmark.equalsIgnoreCase(Costant.bookmarkTypeCompleteLabel)){
//			    			if (isAnonymus){
//			    				if (conInvio){
//			    					((ProcessData)process.getData()).setTipoBookmark(Costant.bookmarkTypeCortesiaLabel);
//			    				} else {
//			    					((ProcessData)process.getData()).setTipoBookmark(Costant.bookmarkTypeLivello2Label);
//			    				}
//			    			} else {
//			    				((ProcessData)process.getData()).setTipoBookmark(Costant.bookmarkTypeCompleteLabel);
//			    			}
//			    		} else if (tipoBookmark.equalsIgnoreCase(Costant.bookmarkTypeCortesiaLabel)){
//			    			if (isAnonymus){
//			    				((ProcessData)process.getData()).setTipoBookmark(Costant.bookmarkTypeCortesiaLabel);
//			    			} else {
//			    				((ProcessData)process.getData()).setTipoBookmark(Costant.bookmarkTypeCortesiaLabel);
//			    			}
//			    		} else {
//			    			((ProcessData)process.getData()).setTipoBookmark(Costant.bookmarkTypeLivello2Label);
//			    		}
//					} else {
//						settaParametriModelloUnico(process,session);
//					}
                }
                SezioneCompilabileBean scb = delegate.getOggettoPratica(dataForm);
                if (scb == null) {
                    dataForm.setOggettoIstanza(null);
                } else if (dataForm.getOggettoIstanza() == null || (scb != null && !scb.getHref().equalsIgnoreCase(dataForm.getOggettoIstanza().getHref()))) {
                    dataForm.setOggettoIstanza(scb);
                }

                delegate.settaOneriPerProcedimento(dataForm);
                precompilaHref(dataForm, request);

                dataForm.setListaDichiarazioniStatiche(new LinkedHashMap());
                delegate.getDichiarazioniStatiche(dataForm);

                dataForm.setListaModulistica(new LinkedHashMap());
                delegate.getModulistica(dataForm);

                dataForm.setListaDocRichiesti(new LinkedHashMap());
                delegate.getDocumentiRichiesti(dataForm);

                dataForm.setListaDestinatari(new HashMap());
                delegate.getDestinatari(dataForm);

                // anagrafica
                ManagerAnagrafica ma = new ManagerAnagrafica();
                if (!dataForm.getAnagrafica().isInitialized()) {
                    dataForm.getAnagrafica().setInitialized(true);
                    ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
                    String intermed = params.get("abilita_intermediari");
                    boolean intermediariAttivo = false;
                    if (intermed != null && intermed.equalsIgnoreCase("true")) {
                        intermediariAttivo = true;
                    }
                    ArrayList anagraficaCampi = delegate.getAnagraficaDinamica(dataForm, isAnonymus);
                    dataForm.getAnagrafica().setListaCampi(anagraficaCampi);
                    dataForm.getAnagrafica().setFineCompilazione(false);
                    dataForm.getAnagrafica().setHtmlHistory(new ArrayList());
                    dataForm.getAnagrafica().setHtmlStepAttuale("");
                    dataForm.getAnagrafica().setListaCampiStep(new ArrayList());
                    if (!intermediariAttivo) {
                        ma.precompilaAnagraficaDaXML(session, dataForm, request.getUnwrappedRequest().getCharacterEncoding());
                    }
                    String htmlIniziale = ma.getHtmlNonCompilabileIniziale(dataForm, request);
                    request.setAttribute("htmlIniziale", htmlIniziale);
                } else {
                    if (dataForm.getAnagrafica().getHtmlHistory() == null || dataForm.getAnagrafica().getHtmlHistory().size() == 0) {
                        String htmlIniziale = ma.getHtmlNonCompilabileIniziale(dataForm, request);
                        request.setAttribute("htmlIniziale", htmlIniziale);
                    }
                }
//				configuraRicevutaPagamenti(dataForm,delegate);
//				if (!dataForm.isAttivaPagamenti() && dataForm.getRiepilogoOneri().getTotale()>0){
//					Set setProcedimentiKey = dataForm.getListaProcedimenti().keySet();
//					for (Iterator iterator = setProcedimentiKey.iterator(); iterator.hasNext();) {
//						String key = (String) iterator.next();
//						ProcedimentoBean procedimento = (ProcedimentoBean) dataForm.getListaProcedimenti().get(key);
//						if (procedimentoConOneriAnticipati(procedimento, dataForm)){
//							for (Iterator iterator2 = procedimento.getCodInterventi().iterator(); iterator2.hasNext();) {
//								String codInt = (String) iterator2.next();
//								boolean trovato = false;
//								Iterator itInterventi = dataForm.getInterventi().iterator();
//								while (itInterventi.hasNext() && !trovato){
//									InterventoBean intervento = (InterventoBean)itInterventi.next();
//									boolean saltaIntervento=false;
//									for (Iterator iterator3 = intervento.getListaCodiciAllegati().iterator(); iterator3.hasNext();) {
//										String codAll = (String) iterator3.next();
//										if (codAll!=null && codAll.equalsIgnoreCase("ALLPAG")){
//											saltaIntervento=true;
//										}
//									}
//									if (intervento.getCodice().equalsIgnoreCase(codInt) && !saltaIntervento){
//										trovato=true;
//										intervento.getListaCodiciAllegati().add("ALLPAG");
//									}
//								}
//								if (!trovato) {
//									Iterator itInterventiF = dataForm.getInterventiFacoltativi().iterator();
//									while (itInterventiF.hasNext() && !trovato){
//										InterventoBean intervento = (InterventoBean)itInterventiF.next();
//										boolean saltaIntervento=false;
//										for (Iterator iterator3 = intervento.getListaCodiciAllegati().iterator(); iterator3.hasNext();) {
//											String codAll = (String) iterator3.next();
//											if (codAll!=null && codAll.equalsIgnoreCase("ALLPAG")){
//												saltaIntervento=true;
//											}
//										}
//										if (intervento.getCodice().equalsIgnoreCase(codInt) && !saltaIntervento){
//											trovato=true;
//											intervento.getListaCodiciAllegati().add("ALLPAG");
//										}
//									}
//								}
//							}
//						}
//					}
//					AllegatoBean allPag = delegate.getAllegatoRicevutaPagamento();
//					dataForm.getListaAllegati().put("ALLPAG", allPag);
//					DocumentoBean docPag = delegate.getDocumentoRicevutaPagamento();
//					dataForm.getListaDocRichiesti().put("ALLPAG",docPag);
//
//				}

                // recupero info 
                ArrayList listaProc = buildModelloUnicoBean(dataForm);
                request.setAttribute("listaProcedimenti", listaProc);
            } else {
                throw new ProcedimentoUnicoException("Sessione scaduta");
            }
            logger.debug("ModelloUnicoStep - service method END");
        } catch (Exception e) {
            gestioneEccezioni(process, 5, e);
        }
    }

    public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException, ServletException {
        try {
            logger.debug("ModelloUnicoStep - loopBack method");
            ProcessData dataForm = (ProcessData) process.getData();
            ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
// PC - Anagrafica altri richiedenti inizio    
            String proteggiAltriDichiaranti = delegate.getParametroConfigurazione(dataForm.getIdentificatoreUnivoco().getCodiceSistema().getCodiceAmministrazione(), "proteggiAltriDichiaranti");
            String forzaInQualitaAltriDichiaranti = delegate.getParametroConfigurazione(dataForm.getIdentificatoreUnivoco().getCodiceSistema().getCodiceAmministrazione(), "forzaInQualitaAltriDichiaranti");
            HashMap<String, String> forzaInQualita = trasformaInQualita(forzaInQualitaAltriDichiaranti);
  // PC - Anagrafica altri richiedenti fine

            // BEGIN modulo compilabile
            if (propertyName.equals("moduloCompilabile_throwError")) {
                Throwable t = (Throwable) CaricaModuloCompilato.extractException(request.getParameter("errorMessageId"));
                gestioneEccezioni(process, 5, t instanceof Exception ? (Exception) t : new Exception(t));
            } else if (propertyName.equals("moduloCompilabile_refreshModulo")) {
                for (Iterator iterator = dataForm.getListaHref().keySet().iterator(); iterator.hasNext();) {
                    salvaDatiHrefCollegati(iterator.next().toString(), dataForm, request);
                }
                loopBack(process, request, "modelloUnico.jsp", index);
            } else if (propertyName.equals("moduloCompilabile_retryUpload")) {
                String message = (String) CaricaModuloCompilato.extractException(request.getParameter("errorMessageId"));
                request.setAttribute("moduloCompilabile_errorMessage", message);
                showJsp(process, "moduloCompilabile.jsp", false);
            } else if (propertyName.equals("moduloCompilabile.jsp")) {
                showJsp(process, "moduloCompilabile.jsp", false);
                // END modulo compilabile                        

            } else if (propertyName.equalsIgnoreCase("normativa.jsp")) {
                String idx = request.getParameter("index");
                //enableBottomNavigationBar(process, false);
                logger.debug(process.getView().getCurrentActivity().getCurrentStep().getJspPath());
                //String codRif = request.getParameter("codRif");
                request.setAttribute("listaDocumenti", delegate.getDocumentiNormative(String.valueOf(idx)));
                request.setAttribute("MU", "true");
                showJsp(process, propertyName, false);
            } else if (propertyName.equalsIgnoreCase("documenti.jsp")) {
                String idx = request.getParameter("index");
                request.setAttribute("listaDocumenti", delegate.getDocumentiAllegati(String.valueOf(idx)));
                request.setAttribute("MU", "true");
                showJsp(process, propertyName, false);
            } else if (propertyName.equalsIgnoreCase("anagrafica.jsp")) {
                enableBottomNavigationBar(process, false);
                ManagerAnagrafica ma = new ManagerAnagrafica();
                ma.genereteStepAnagrafica(dataForm, request);
                if (dataForm.getAnagrafica().getHtmlHistory().size() == 0) {
                    request.setAttribute("AVANTI", "true");
                } else {
                    if (dataForm.getAnagrafica().isFineCompilazione()) {
                        request.setAttribute("INDIETRO", "true");
                    } else {
                        request.setAttribute("AVANTI", "true");
                        request.setAttribute("INDIETRO", "true");
                    }
                }
                showJsp(process, propertyName, false);
            } else if (propertyName.equalsIgnoreCase("avantiAnagrafica")) {
                enableBottomNavigationBar(process, false);
                ManagerAnagrafica ma = new ManagerAnagrafica();
                ma.saveAnagrafica(dataForm, request);
                ma.genereteNextStepAnagrafica(dataForm, request);
                if (dataForm.getAnagrafica().getHtmlHistory().size() == 0) {
                    request.setAttribute("AVANTI", "true");
                } else {
                    if (dataForm.getAnagrafica().isFineCompilazione()) {
                        request.setAttribute("INDIETRO", "true");
                    } else {
                        request.setAttribute("AVANTI", "true");
                        request.setAttribute("INDIETRO", "true");
                    }
                }
                dataForm.getAnagrafica().setLivelloAttuale(dataForm.getAnagrafica().getLivelloAttuale() + 1);
                showJsp(process, "anagrafica.jsp", false);
            } else if (propertyName.equalsIgnoreCase("indietroAnagrafica")) {
                enableBottomNavigationBar(process, false);
                ManagerAnagrafica ma = new ManagerAnagrafica();
                ma.saveAnagrafica(dataForm, request);
                ma.genereteBackStepAnagrafica(dataForm, request);
                if (dataForm.getAnagrafica().getHtmlHistory().size() == 0) {
                    request.setAttribute("AVANTI", "true");
                } else {
                    if (dataForm.getAnagrafica().isFineCompilazione()) {
                        request.setAttribute("INDIETRO", "true");
                    } else {
                        request.setAttribute("AVANTI", "true");
                        request.setAttribute("INDIETRO", "true");
                    }
                }
                dataForm.getAnagrafica().setLivelloAttuale(dataForm.getAnagrafica().getLivelloAttuale() - 1);
                showJsp(process, "anagrafica.jsp", false);
            } else if (propertyName.indexOf("addCampiMultipliAnagrafica") != -1) {
                enableBottomNavigationBar(process, false);
                ManagerAnagrafica ma = new ManagerAnagrafica();
                ma.saveAnagrafica(dataForm, request);
                ma.refresh(dataForm, request);
                if (dataForm.getAnagrafica().getHtmlHistory().size() == 0) {
                    request.setAttribute("AVANTI", "true");
                } else {
                    if (dataForm.getAnagrafica().isFineCompilazione()) {
                        request.setAttribute("INDIETRO", "true");
                    } else {
                        request.setAttribute("AVANTI", "true");
                        request.setAttribute("INDIETRO", "true");
                    }
                }
                showJsp(process, "anagrafica.jsp", false);
            } else if (propertyName.equalsIgnoreCase("addRichiedente.jsp")) {
                // anagrafica richiedente --
                ManagerAnagrafica ma_ = new ManagerAnagrafica();
                String htmlIniziale = ma_.getHtmlNonCompilabileIniziale(dataForm, request);
                request.setAttribute("htmlIniziale", htmlIniziale);
                // --fine anagrafica richiedente 
                ArrayList listaProc = buildModelloUnicoBean(dataForm);
                request.setAttribute("listaProcedimenti", listaProc);

                String userID = (String) session.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER);
                boolean isAnonymus = SiracHelper.isAnonymousUser(userID);
                ManagerAnagraficaAltroRichiedente ma = new ManagerAnagraficaAltroRichiedente();
                ArrayList anagraficaCampi = delegate.getAnagraficaDinamica(dataForm, isAnonymus);
                precompilaCampiAltriRichiedenti(dataForm.getAnagrafica(), anagraficaCampi, proteggiAltriDichiaranti,forzaInQualita);
                AnagraficaBean altroRichiedente = new AnagraficaBean();
                altroRichiedente.setListaCampi(anagraficaCampi);
                altroRichiedente.setFineCompilazione(false);
                altroRichiedente.setHtmlHistory(new ArrayList());
                altroRichiedente.setListaCampiStep(new ArrayList());
                altroRichiedente.setCodice(dataForm.getAltriRichiedenti().size() == 0 ? "0" : String.valueOf(Integer.parseInt(((AnagraficaBean) dataForm.getAltriRichiedenti().get(dataForm.getAltriRichiedenti().size() - 1)).getCodice()) + 1));
                String htmlIniziale__ = ma.getHtmlNonCompilabileInizialeAltroRichiedente(altroRichiedente, request, Integer.parseInt(altroRichiedente.getCodice()));
                altroRichiedente.setHtmlStepAttuale(htmlIniziale__);
                dataForm.getAltriRichiedenti().add(altroRichiedente);
                showJsp(process, "modelloUnico.jsp", false);
            } else if (propertyName.indexOf("removeRichiedente.jsp") != -1) {
                dataForm.getAltriRichiedenti().remove(index - 1);
                ManagerAnagrafica ma = new ManagerAnagrafica();
                String htmlIniziale = ma.getHtmlNonCompilabileIniziale(dataForm, request);
                request.setAttribute("htmlIniziale", htmlIniziale);
                salvaDatiHref(request, dataForm, process);
                ArrayList listaProc = buildModelloUnicoBean(dataForm);
                request.setAttribute("listaProcedimenti", listaProc);
                // salvaDatiHref(request, dataForm,process);
                // se modulo intermediari attivato aggiorno il bean degli intermediari
                ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
                String intermed = params.get("abilita_intermediari");
                if (intermed != null && intermed.equalsIgnoreCase("true")) {
                    String href = (String) request.getParameter("href");
                    String href_intermed = params.get("href_intermediari");
                    if (href != null && (href.equalsIgnoreCase("ANAG") || (href_intermed != null && href.equalsIgnoreCase(href_intermed)))) {
                        ManagerIntermediari.aggiornaBeanIntermediari(dataForm, href);
                    }
                }
                aggiornaInfoAltriRichiedenti(dataForm);
                // fine interfacciamento modulo intermediari
                showJsp(process, "modelloUnico.jsp", false);
            } else if (propertyName.indexOf("compilaRichiedente.jsp") != -1) {
                //String indexANAG = (String)request.getParameter("href");
                if (propertyName.indexOf("&svuota=TRUE&") != -1) {
                    // compilaRichiedente.jsp&svuota=TRUE&href=ANAG_0
                    String indiceAnagrafica = null;
                    if (propertyName.indexOf("&href=ANAG_") != -1) {
                        indiceAnagrafica = propertyName.substring((propertyName.indexOf("&href=ANAG_") + "&href=ANAG_".length()), propertyName.length());
                        int idxAnag = -1;
                        try {
                            idxAnag = Integer.parseInt(indiceAnagrafica);
                        } catch (Exception e) {
                        }
                        if (idxAnag != -1) {
                            AnagraficaBean an = (AnagraficaBean) dataForm.getAltriRichiedenti().get(idxAnag);
                            svuotaCampiAltriRichiedenti(an.getListaCampi());
                            index = idxAnag + 1;
                        }
                    }
                }
                //int index_=0;
                //if (indexANAG!=null){index_ = Integer.parseInt(indexANAG);}
                enableBottomNavigationBar(process, false);
                ManagerAnagraficaAltroRichiedente ma = new ManagerAnagraficaAltroRichiedente();
                ma.genereteStepAnagraficaAltroRichiedente(dataForm, request, index - 1);
                request.setAttribute("INDEX", String.valueOf(index - 1));
                if (((AnagraficaBean) (dataForm.getAltriRichiedenti().get(index - 1))).getHtmlHistory().size() == 0) {
                    request.setAttribute("AVANTI", "true");
                } else {
                    if (((AnagraficaBean) (dataForm.getAltriRichiedenti().get(index - 1))).isFineCompilazione()) {
                        request.setAttribute("INDIETRO", "true");
                    } else {
                        request.setAttribute("AVANTI", "true");
                        request.setAttribute("INDIETRO", "true");
                    }
                }
                showJsp(process, "anagrafica_altroRichiedente.jsp", false);
            } else if (propertyName.indexOf("avantiAnagraficaAltroRichiedente") != -1) {
                enableBottomNavigationBar(process, false);
                String hrefString = null;
                String[] tmpToken = propertyName.split("&");
                if (tmpToken != null && tmpToken.length == 2) {
                    String[] tmpToken2 = tmpToken[1].split("=");
                    if (tmpToken2 != null && tmpToken2.length == 2) {
                        hrefString = tmpToken2[1];
                    }
                }
                String codice = null;
                AnagraficaBean tmp = null;
                if ((hrefString).indexOf("ANAG_") != -1) {
                    // sto tornanado dalla sezione "altri richiedenti"
                    ManagerAnagraficaAltroRichiedente ma = new ManagerAnagraficaAltroRichiedente();
                    String[] codiceToken = hrefString.split("_");
                    if (codiceToken != null && codiceToken.length == 2) {
                        codice = codiceToken[1];
                        Iterator itt = dataForm.getAltriRichiedenti().iterator();
                        boolean trovato = false;
                        while (itt.hasNext() && !trovato) {
                            AnagraficaBean an = (AnagraficaBean) itt.next();
                            if (an.getCodice().equalsIgnoreCase(codice)) {
                                trovato = true;
                                tmp = an;
                                ma.salvaDatiAnagAltriRich(request, dataForm, tmp);
                                ma.genereteNextStepAnagraficaAltroRichiedente(an, request);
                                an.setLivelloAttuale(an.getLivelloAttuale() + 1);
                            }
                        }
                    }
                }
                request.setAttribute("INDEX", codice);
                if (tmp.getHtmlHistory().size() == 0) {
                    request.setAttribute("AVANTI", "true");
                } else {
                    if (tmp.isFineCompilazione()) {
                        request.setAttribute("INDIETRO", "true");
                    } else {
                        request.setAttribute("AVANTI", "true");
                        request.setAttribute("INDIETRO", "true");
                    }
                }
                showJsp(process, "anagrafica_altroRichiedente.jsp", false);
            } else if (propertyName.indexOf("indietroAnagraficaAltroRichiedente") != -1) {
                enableBottomNavigationBar(process, false);
                String hrefString = null;
                String[] tmpToken = propertyName.split("&");
                if (tmpToken != null && tmpToken.length == 2) {
                    String[] tmpToken2 = tmpToken[1].split("=");
                    if (tmpToken2 != null && tmpToken2.length == 2) {
                        hrefString = tmpToken2[1];
                    }
                }
                String codice = null;
                AnagraficaBean tmp = null;
                if ((hrefString).indexOf("ANAG_") != -1) {
                    ManagerAnagraficaAltroRichiedente ma = new ManagerAnagraficaAltroRichiedente();
                    String[] codiceToken = hrefString.split("_");
                    if (codiceToken != null && codiceToken.length == 2) {
                        codice = codiceToken[1];
                        Iterator itt = dataForm.getAltriRichiedenti().iterator();
                        boolean trovato = false;
                        while (itt.hasNext() && !trovato) {
                            AnagraficaBean an = (AnagraficaBean) itt.next();
                            if (an.getCodice().equalsIgnoreCase(codice)) {
                                trovato = true;
                                tmp = an;
                                ma.salvaDatiAnagAltriRich(request, dataForm, tmp);
                                ma.genereteBackStepAnagraficaAltroRichiedente(dataForm, request, an);
                                an.setLivelloAttuale(an.getLivelloAttuale() - 1);
                            }
                        }
                    }
                }
                request.setAttribute("INDEX", codice);
                if (tmp.getHtmlHistory().size() == 0) {
                    request.setAttribute("AVANTI", "true");
                } else {
                    if (tmp.isFineCompilazione()) {
                        request.setAttribute("INDIETRO", "true");
                    } else {
                        request.setAttribute("AVANTI", "true");
                        request.setAttribute("INDIETRO", "true");
                    }
                }
                showJsp(process, "anagrafica_altroRichiedente.jsp", false);
            } else if (propertyName.equalsIgnoreCase("listadichiarazioni.jsp")) {
                precompilaHref(dataForm, request);
                enableBottomNavigationBar(process, false);
                salvaDatiHref(request, dataForm, process);
                String javascriptDisabled = (String) request.getParameter("javascriptDisabled");
                if (javascriptDisabled != null) {
                    String href = (String) request.getParameter("href");
                    SezioneCompilabileBean dataBean = (SezioneCompilabileBean) dataForm.getListaHref().get(href);
                    ArrayList listaErrori = new ArrayList();
                    checkValidita(dataForm, listaErrori, dataBean.getCampi());
                    if (listaErrori.size() > 0) {
                        request.setAttribute("errorValidInput", listaErrori);
                        session.setAttribute("sezioneBean", dataBean);
                        showJsp(process, "renderHref.jsp", false);
                    } else {
                        showJsp(process, propertyName, false);
                    }
                } else {
                    showJsp(process, propertyName, false);
                }
            } else if (propertyName.equalsIgnoreCase("abilitaCampi")) {
                enableBottomNavigationBar(process, false);
                String href = (String) request.getParameter("href");
                salvaDatiHref(request, dataForm, process);
                SezioneCompilabileBean dataBean = (SezioneCompilabileBean) dataForm.getListaHref().get(href);
                ArrayList listaErrori = new ArrayList();
                checkValidita(dataForm, listaErrori, dataBean.getCampi());
                request.setAttribute("errorValidInput", listaErrori);
                session.setAttribute("sezioneBean", dataBean);
                showJsp(process, "renderHref.jsp", false);
            } else if (propertyName.equalsIgnoreCase("modelloUnico.jsp")) {

                // blocco inserito x controllo lunghezza textarea se javascript disbilitato
                if (session.getAttribute("NOJAVASCRIPT") != null && ((String) session.getAttribute("NOJAVASCRIPT")).equalsIgnoreCase("TRUE")) {
                    enableBottomNavigationBar(process, false);
                    String href = (String) request.getParameter("href");
                    if (href != null && !href.equalsIgnoreCase("ANAG")) {
                        salvaDatiHref(request, dataForm, process);
                        SezioneCompilabileBean dataBean = (SezioneCompilabileBean) dataForm.getListaHref().get(href);
                        ArrayList listaErrori = new ArrayList();
                        if (!checkValidita(dataForm, listaErrori, dataBean.getCampi())) {
                            request.setAttribute("errorValidInput", listaErrori);
                            session.setAttribute("sezioneBean", dataBean);
                            showJsp(process, "renderHref.jsp", false);
                            return;
                        }
                    }
                }

                String href__ = (String) request.getParameter("href");
                if (((String) request.getParameter("href")) != null && ((String) request.getParameter("href")).indexOf("ANAG_") != -1) {
                    // sto tornanado dalla sezione "altri richiedenti"
                    ManagerAnagraficaAltroRichiedente ma = new ManagerAnagraficaAltroRichiedente();
//            		ma.salvaDatiAnagAltriRich(request,dataForm,(String)request.getAttribute("href"),process.getCommune().getOid(),"","");
                    String[] codiceToken = href__.split("_");
                    if (codiceToken != null && codiceToken.length == 2) {
                        String codice = codiceToken[1];
                        Iterator itt = dataForm.getAltriRichiedenti().iterator();
                        AnagraficaBean an = null;
                        boolean trovato = false;
                        while (itt.hasNext() && !trovato) {
                            AnagraficaBean tmp = (AnagraficaBean) itt.next();
                            if (tmp.getCodice().equalsIgnoreCase(codice)) {
                                trovato = true;
                                an = tmp;
                            }
                        }
                        String htmlIniziale__ = ma.getHtmlNonCompilabileInizialeAltroRichiedente(an, request, Integer.parseInt(an.getCodice()));
                        an.setHtmlStepAttuale(htmlIniziale__);
                        aggiornaInfoAltriRichiedenti(dataForm);
                    }
                }

                enableBottomNavigationBar(process, true);
                ManagerAnagrafica ma = new ManagerAnagrafica();
                String htmlIniziale = ma.getHtmlNonCompilabileIniziale(dataForm, request);
                request.setAttribute("htmlIniziale", htmlIniziale);
                salvaDatiHref(request, dataForm, process);
// PC - Anagrafiche altri richiedenti inizio               
                rigeneraAltriRichiedenti(dataForm, request, proteggiAltriDichiaranti,  forzaInQualita);
// PC - Anagrafiche altri richiedenti fine                                

                ArrayList listaProc = buildModelloUnicoBean(dataForm);
                request.setAttribute("listaProcedimenti", listaProc);
                // salvaDatiHref(request, dataForm,process);
                // se modulo intermediari attivato aggiorno il bean degli intermediari
                ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
                String intermed = params.get("abilita_intermediari");
                if (intermed != null && intermed.equalsIgnoreCase("true")) {
                    String href = (String) request.getParameter("href");
                    String href_intermed = params.get("href_intermediari");
                    if (href != null && (href.equalsIgnoreCase("ANAG") || (href_intermed != null && href.equalsIgnoreCase(href_intermed)))) {
                        ManagerIntermediari.aggiornaBeanIntermediari(dataForm, href);
                    }
                }
                // fine interfacciamento modulo intermediari
                showJsp(process, propertyName, false);
            } else if (propertyName.equalsIgnoreCase("bookmark")) {
                gestioneBookmark(process, request);
            } else if (propertyName.equalsIgnoreCase("avantiSenzacontrollo")) {
                dataForm.setTipoBookmark(Costant.bookmarkTypeLivello2Label);
                setStep(process, request, getCurrentActivityIndex(process), getCurrentStepIndex(process) + 1);
            } else if (propertyName.indexOf("renderHref.jsp") != -1) {
                String href = request.getParameter("href");
                if (request.getParameter("MU") != null) {
                    request.setAttribute("MU", "MU");
                }

                if (propertyName.indexOf("aggiungi=si") != -1) {
                    salvaDatiHref(request, dataForm, process);

                    SezioneCompilabileBean dataBean = (SezioneCompilabileBean) dataForm.getListaHref().get(href);
                    aggiungiSezioneMultipla(dataBean);
                    dataBean.setHtmlEditable(HtmlRenderer.costruisciStringaHtml(dataBean, true, "SectionText", "TableRow0", "96%", "0", "6", null, "25", dataForm, request));
                    dataBean.setHtml(HtmlRenderer.costruisciStringaHtml(dataBean, false, "SectionText", null, null, null, null, null, null, dataForm, request));
                } else if (propertyName.indexOf("togli=si") != -1) {
                    salvaDatiHref(request, dataForm, process);

                    SezioneCompilabileBean dataBean = (SezioneCompilabileBean) dataForm.getListaHref().get(href);
                    eliminaSezioneMultipla(dataBean);
                    dataBean.setHtmlEditable(HtmlRenderer.costruisciStringaHtml(dataBean, true, "SectionText", "TableRow0", "96%", "0", "6", null, "25", dataForm, request));
                    dataBean.setHtml(HtmlRenderer.costruisciStringaHtml(dataBean, false, "SectionText", null, null, null, null, null, null, dataForm, request));
                } else if (propertyName.indexOf("accedi") != -1) {
                    if (href != null && href.equalsIgnoreCase("ANAG")) {
                        ManagerAnagrafica ma = new ManagerAnagrafica();
                        ma.saveAnagrafica(dataForm, request);
                    } else {
                        salvaDatiHref(request, dataForm, process);
                    }
// pc - ws modifica inizio
                    String propertyString = propertyName.split("&livello=")[1];
                    String livelloMultiploString = propertyString.split("&")[0];
                    Integer livelloMultiplo = Integer.valueOf(livelloMultiploString);
                    String codCampo = propertyName.substring((propertyName.indexOf("&href=") + "&href=".length()), propertyName.length());
                    chiamaWebService(process, request, dataForm, codCampo, livelloMultiplo);
                    // tolto chiamaWebService(process, request, dataForm, codCampo);                    
// pc - ws modifica fine  
                } else if (propertyName.indexOf("nojavascript") != -1 && !href.equalsIgnoreCase("ANAG")) {
                    String[] token = propertyName.split("&");
                    String nomeCampoToken = "";
                    String nomeCampo = "";
                    String valoreToken = "";
                    String valore = "";
                    String[] tmp;
                    if (token != null && token.length == 5) {
                        nomeCampoToken = token[2];
                        valoreToken = token[3];
                        tmp = nomeCampoToken.split("=");
                        if (tmp != null && tmp.length == 2) {
                            nomeCampo = tmp[1];
                        }
                        tmp = valoreToken.split("=");
                        if (tmp != null && tmp.length == 2) {
                            valore = tmp[1];
                        }
                    }
                    salvaDatiHref(request, dataForm, null, process.getCommune().getOid(), nomeCampo, valore);
                    SezioneCompilabileBean dataBean = (SezioneCompilabileBean) dataForm.getListaHref().get(href);

                    dataBean.setHtmlEditable(HtmlRenderer.costruisciStringaHtml(dataBean, true, "SectionText", "TableRow0", "96%", "0", "6", null, "25", dataForm, request));
                    dataBean.setHtml(HtmlRenderer.costruisciStringaHtml(dataBean, false, "SectionText", null, null, null, null, null, null, dataForm, request));
                }
                if (href != null && href.equalsIgnoreCase("ANAG")) {
                    ManagerAnagrafica ma = new ManagerAnagrafica();
                    ma.refresh(dataForm, request);
                    if (dataForm.getAnagrafica().getHtmlHistory().size() == 0) {
                        request.setAttribute("AVANTI", "true");
                    } else {
                        if (dataForm.getAnagrafica().isFineCompilazione()) {
                            request.setAttribute("INDIETRO", "true");
                        } else {
                            request.setAttribute("AVANTI", "true");
                            request.setAttribute("INDIETRO", "true");
                        }
                    }
                    showJsp(process, "anagrafica.jsp", false);
                } else {
                    SezioneCompilabileBean dataBean = (SezioneCompilabileBean) dataForm.getListaHref().get(href);
                    if (dataBean == null && dataForm.getOggettoIstanza() != null && dataForm.getOggettoIstanza().getHref().equalsIgnoreCase(href)) {
                        dataBean = dataForm.getOggettoIstanza();
                    }
                    session.setAttribute("sezioneBean", dataBean);
                    showJsp(process, "renderHref.jsp", false);
                }
            } else {

                ArrayList listaProc = buildModelloUnicoBean(dataForm);
                request.setAttribute("listaProcedimenti", listaProc);
            }
            logger.debug("ModelloUnicoStep - loopBack method END");

//    		System.out.println("-----MODELLO UNICO STEP");
//	        for (int i = 0; i < dataForm.getAnagrafica().getListaCampi().size(); i++) {
//	        	HrefCampiBean campo = (HrefCampiBean)dataForm.getAnagrafica().getListaCampi().get(i);
//	        	if (campo.getNome().startsWith("ana01050") || campo.getNome().startsWith("ana01051")) {
//	        		System.out.println(campo.getNome());
//	        		System.out.println(campo.getDescrizione());
//	        		System.out.println(campo.getValoreUtente());
//	        		System.out.println();
//	        	}
//	        }
        } catch (Exception e) {
            gestioneEccezioni(process, 5, e);
        }

    }

    private void precompilaHref(ProcessData dataForm, IRequestWrapper request) {
        String bookmarkPointer = Bean2XML.marshallPplData(dataForm, request.getUnwrappedRequest().getCharacterEncoding());
// PC - problemi encoding - inizio         
        XPathReader xpr = new XPathReader(bookmarkPointer, request.getUnwrappedRequest().getCharacterEncoding());
// PC - problemi encoding - fine         
        Iterator it = dataForm.getListaHref().keySet().iterator();
        while (it.hasNext()) {
            String href = (String) it.next();
            SezioneCompilabileBean sezComp = (SezioneCompilabileBean) dataForm.getListaHref().get(href);
            if (sezComp != null) {
                boolean trovato = false;
                for (Iterator iterator = sezComp.getCampi().iterator(); iterator.hasNext();) {
                    HrefCampiBean campo = (HrefCampiBean) iterator.next();
                    if (campo.getPrecompilazione() != null && !campo.getPrecompilazione().equalsIgnoreCase("")) {
                        String ret = xpr.readElementString(campo.getPrecompilazione());
                        campo.setValoreUtente(ret);
                        trovato = true;
                    }
                }
                if (trovato) {
                    salvaDatiHrefCollegati(href, dataForm, request);
                }
            }
// PC - problema campi collegati inizio
            if (sezComp.getCampi() != null && sezComp.getCampi().size() > 0) {
                for (Iterator iter = sezComp.getCampi().iterator(); iter.hasNext();) {
                    HrefCampiBean campo = (HrefCampiBean) iter.next();
                    ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
                    ArrayList ar = new ArrayList();
                    try {
                        ar = delegate.getCampiCollegati(sezComp.getHref(), campo.getNome());
                    } catch (Exception ex) {

                    }
                    campo.setCampiCollegati(ar);
                }
            }
// PC - problema campi collegati fine                        
            sezComp.setHtml(HtmlRenderer.costruisciStringaHtml(sezComp, false, "SectionText", null, null, null, null, null, null, dataForm, request));
            sezComp.setHtmlEditable(HtmlRenderer.costruisciStringaHtml(sezComp, true, "SectionText", "TableRow0", "96%", "0", "6", null, "25", dataForm, request));
        }
        if (dataForm.getOggettoIstanza() != null) {
            dataForm.getOggettoIstanza().setHtml(HtmlRenderer.costruisciStringaHtml(dataForm.getOggettoIstanza(), false, "SectionText", null, null, null, null, null, null, dataForm, request));
            dataForm.getOggettoIstanza().setHtmlEditable(HtmlRenderer.costruisciStringaHtml(dataForm.getOggettoIstanza(), true, "SectionText", "TableRow0", "96%", "0", "6", null, "25", dataForm, request));
        }
    }

    private void eliminaSezioneMultipla(SezioneCompilabileBean dataBean) {
        int offsetRiga = (dataBean.getLastRowCampoMultiplo()) - (dataBean.getFirstRowCampoMultiplo() - 1);
        offsetRiga = offsetRiga / dataBean.getNumSezioniMultiple();
        ArrayList newListaCampiHref = new ArrayList();
        for (Iterator iterator = dataBean.getCampi().iterator(); iterator.hasNext();) {
            HrefCampiBean campoDinamico = (HrefCampiBean) iterator.next();
            if (!(campoDinamico.getRiga() > (dataBean.getLastRowCampoMultiplo() - offsetRiga) && campoDinamico.getRiga() <= dataBean.getLastRowCampoMultiplo())) {
                if (campoDinamico.getRiga() > dataBean.getLastRowCampoMultiplo()) {
                    campoDinamico.setRiga(campoDinamico.getRiga() - offsetRiga);
                }
                newListaCampiHref.add(campoDinamico);
            }
        }
        dataBean.setCampi(newListaCampiHref);
        dataBean.setLastRowCampoMultiplo(dataBean.getLastRowCampoMultiplo() - offsetRiga);
        dataBean.setNumSezioniMultiple(dataBean.getNumSezioniMultiple() - 1);
        dataBean.setRowCount(dataBean.getRowCount() - offsetRiga);
    }

    public void aggiungiSezioneMultipla(SezioneCompilabileBean dataBean) {
        ArrayList listaCampiAggiuntivi = new ArrayList();
        ArrayList listaCampiAggiornata = new ArrayList();
        int offsetRiga = (dataBean.getLastRowCampoMultiplo()) - (dataBean.getFirstRowCampoMultiplo() - 1);
        offsetRiga = offsetRiga / dataBean.getNumSezioniMultiple();
        for (Iterator iterator = dataBean.getCampi().iterator(); iterator.hasNext();) {
            HrefCampiBean campoDinamico = (HrefCampiBean) iterator.next();
            if (campoDinamico.getRiga() >= dataBean.getFirstRowCampoMultiplo() && campoDinamico.getRiga() < dataBean.getFirstRowCampoMultiplo() + offsetRiga) {
                HrefCampiBean newCampo = clonaCampoDinamico(dataBean, campoDinamico, offsetRiga);
                listaCampiAggiuntivi.add(newCampo);
            }
        }
        boolean inserito = false;
        for (Iterator iterator = dataBean.getCampi().iterator(); iterator.hasNext();) {
            HrefCampiBean campoDinamico = (HrefCampiBean) iterator.next();

            if (campoDinamico.getRiga() <= dataBean.getLastRowCampoMultiplo()) {
                listaCampiAggiornata.add(campoDinamico);
            } else if ((campoDinamico.getRiga() == (dataBean.getLastRowCampoMultiplo() + 1) || !iterator.hasNext()) && !inserito) {
                for (Iterator iterator2 = listaCampiAggiuntivi.iterator(); iterator2.hasNext();) {
                    HrefCampiBean campoDaAggiungere = (HrefCampiBean) iterator2.next();
                    listaCampiAggiornata.add(campoDaAggiungere);
                }
                inserito = true;
                campoDinamico.setRiga(campoDinamico.getRiga() + offsetRiga);
                listaCampiAggiornata.add(campoDinamico);
            } else {
                campoDinamico.setRiga(campoDinamico.getRiga() + offsetRiga);
                listaCampiAggiornata.add(campoDinamico);
            }
        }
        if (!inserito) {
            for (Iterator iterator2 = listaCampiAggiuntivi.iterator(); iterator2.hasNext();) {
                HrefCampiBean campoDaAggiungere = (HrefCampiBean) iterator2.next();
                listaCampiAggiornata.add(campoDaAggiungere);
            }
        }
        dataBean.setCampi(listaCampiAggiornata);
        dataBean.setLastRowCampoMultiplo(dataBean.getLastRowCampoMultiplo() + offsetRiga);
        dataBean.setNumSezioniMultiple(dataBean.getNumSezioniMultiple() + 1);
        dataBean.setRowCount(dataBean.getRowCount() + offsetRiga);
    }

    private HrefCampiBean clonaCampoDinamico(SezioneCompilabileBean dataBean, HrefCampiBean campoDinamico, int offset) {
        HrefCampiBean campo = new HrefCampiBean();
        campo.setNome(campoDinamico.getNome() + "_" + (dataBean.getNumSezioniMultiple() + 1));
        campo.setDescrizione(campoDinamico.getDescrizione());
        campo.setRiga(campoDinamico.getRiga() + (dataBean.getNumSezioniMultiple() * offset));
        campo.setPosizione(campoDinamico.getPosizione());
        campo.setMolteplicita(dataBean.getNumSezioniMultiple() + 1);
        campo.setTipo(campoDinamico.getTipo());
        campo.setControllo(campoDinamico.getControllo());
        campo.setValore(campoDinamico.getValore());
        campo.setValoreUtente("");
        campo.setNumCampo(campoDinamico.getNumCampo());

        ArrayList opzioniCombo = new ArrayList();
        for (Iterator iterator = campoDinamico.getOpzioniCombo().iterator(); iterator.hasNext();) {
            BaseBean bb = (BaseBean) iterator.next();
            BaseBean bb2 = new BaseBean();
            bb2.setCodice(bb.getCodice());
            bb2.setDescrizione(bb.getDescrizione());
            opzioniCombo.add(bb2);
        }
        campo.setOpzioniCombo(opzioniCombo);

        campo.setWeb_serv(campoDinamico.getWeb_serv());
        campo.setNome_xsd(campoDinamico.getNome_xsd());
        campo.setCampo_key(campoDinamico.getCampo_key());
        campo.setCampo_dati(campoDinamico.getCampo_dati());
        campo.setTp_controllo(campoDinamico.getTp_controllo());
        campo.setLunghezza(campoDinamico.getLunghezza());
        campo.setDecimali(campoDinamico.getDecimali());
        campo.setEdit(campoDinamico.getEdit());
        campo.setContatore(campoDinamico.getContatore());
        campo.setCampo_xml_mod(campoDinamico.getCampo_xml_mod());
        campo.setRaggruppamento_check(campoDinamico.getRaggruppamento_check());

        Iterator it2 = dataBean.getCampi().iterator();
        HrefCampiBean campoHrefColl = null;
        boolean trovato = false;
        while (it2.hasNext() && !trovato) {
            campoHrefColl = (HrefCampiBean) it2.next();
            if (campoHrefColl.getNome().equalsIgnoreCase(campoDinamico.getCampo_collegato())) {
                trovato = true;
            }
        }

        if (/*campoDinamico.getNumCampo()==1 && */trovato && campoHrefColl.getNumCampo() == 1) {
            campo.setCampo_collegato(campoDinamico.getCampo_collegato() + "_" + (dataBean.getNumSezioniMultiple() + 1));
        } else {
            campo.setCampo_collegato(campoDinamico.getCampo_collegato());

            if (campoDinamico.getCampo_collegato() != null) {
                Iterator it = dataBean.getCampi().iterator();
                while (it.hasNext()) {
                    HrefCampiBean campoHref = (HrefCampiBean) it.next();
                    if (campoHref.getNome().equalsIgnoreCase(campo.getCampo_collegato()) /*&& campoDinamico.getNumCampo()!=1*/) {
                        String[] tmp = new String[2];
                        tmp[0] = campoDinamico.getNome() + "_" + (dataBean.getNumSezioniMultiple() + 1);
                        tmp[1] = campoDinamico.getVal_campo_collegato();
                        campoHref.getCampiCollegati().add(tmp);
                    }
                }
            }
        }
        campo.setVal_campo_collegato(campoDinamico.getVal_campo_collegato());
        ArrayList campiCollegati = new ArrayList();
        if (campoDinamico.getCampiCollegati() != null) {
            for (Iterator iterator = campoDinamico.getCampiCollegati().iterator(); iterator.hasNext();) {
                String[] str = (String[]) iterator.next();
                String[] copyStr = new String[str.length];
                for (int i = 0; i < str.length; i++) {
                    copyStr[i] = new String(i == 0 ? str[i] + "_" + (dataBean.getNumSezioniMultiple() + 1) : str[i]);
                }
                campiCollegati.add(copyStr);
            }
        }
        campo.setCampiCollegati(campiCollegati);

        // pc  aggiunta dati  mancanti inizio
        campo.setErr_msg(campoDinamico.getErr_msg());
        campo.setPattern(campoDinamico.getPattern());
        // pc  aggiunta dati  mancanti fine   
        return campo;
    }

    public boolean logicalValidate(AbstractPplProcess process, IRequestWrapper request, IValidationErrors errors) throws ParserException {
        ProcessData dataForm = (ProcessData) process.getData();
        doSave(process, request);
        try {
            logger.debug("ModelloUnicoStep - logicalValidate method");

//			String href = (String) request.getParameter("href");
//			if (href!=null) {
//				salvaDatiHref(request, dataForm,process);
//				SezioneCompilabileBean scb = (SezioneCompilabileBean) dataForm.getListaHref().get(href);
//				if (scb!=null){
//					ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
//					String caratteriAmmessi = delegate.getParametroConfigurazione(process.getCommune().getOid(),"caratteriAmmessi");
//					for (Iterator iterator = scb.getCampi().iterator(); iterator.hasNext();) {
//						HrefCampiBean campo = (HrefCampiBean) iterator.next();
//						if ((campo.getTipo().equalsIgnoreCase("I") || campo.getTipo().equalsIgnoreCase("A")) && Utilities.isset(campo.getValoreUtente())){							
//							if (!Pattern.matches("["+caratteriAmmessi+"]*", StringEscapeUtils.unescapeHtml(campo.getValoreUtente()))){
//								errors.add("error.generic","Nel campo <i>"+campo.getDescrizione()+"</i> ci sono caratteri non supportati");
//							}
//						}
//					}
//					if (!errors.isEmpty()){
//						return false;
//					}
//				}
//			}
            String r = request.getParameter("navigation.button.loopbackValidate$avantiAnagrafica");
            if (r != null) {
                ManagerAnagrafica ma = new ManagerAnagrafica();
                ma.saveAnagrafica(dataForm, request);
                if (!ma.checkComplete(dataForm, errors)) {
                    ma.refresh(dataForm, request);
                    if (dataForm.getAnagrafica().getHtmlHistory().size() == 0) {
                        request.setAttribute("AVANTI", "true");
                    } else {
                        if (dataForm.getAnagrafica().isFineCompilazione()) {
                            request.setAttribute("INDIETRO", "true");
                        } else {
                            request.setAttribute("AVANTI", "true");
                            request.setAttribute("INDIETRO", "true");
                        }
                    }
                    return false;
                }
            }

            // controllo dati anagrafica - altri richiedenti
            Map mappa = request.getParameterMap();
            if (mappa != null) {
                Set s = mappa.keySet();
                if (s != null) {
                    Iterator it_ = s.iterator();
                    boolean trovato = false;
                    AnagraficaBean tmp = null;
                    while (it_.hasNext() && !trovato) {
                        String key = (String) it_.next();
                        if (key.indexOf("navigation.button.loopbackValidate$avantiAnagraficaAltroRichiedente") != -1) {
                            String[] tmpToken = key.split("&href=ANAG_");
                            if (tmpToken != null && tmpToken.length == 2) {
                                ManagerAnagraficaAltroRichiedente ma = new ManagerAnagraficaAltroRichiedente();
                                String index = tmpToken[1];
                                Iterator itt = dataForm.getAltriRichiedenti().iterator();
                                boolean trovato2 = false;
                                while (itt.hasNext() && !trovato2) {
                                    AnagraficaBean an = (AnagraficaBean) itt.next();
                                    if (an.getCodice().equalsIgnoreCase(index)) {
                                        trovato2 = true;
                                        tmp = an;
                                        ma.salvaDatiAnagAltriRich(request, dataForm, an);
                                        if (!ma.checkComplete(an, errors)) {
                                            ma.refresh(an, request);
                                            if (an.getHtmlHistory().size() == 0) {
                                                request.setAttribute("AVANTI", "true");
                                            } else {
                                                if (an.isFineCompilazione()) {
                                                    request.setAttribute("INDIETRO", "true");
                                                } else {
                                                    request.setAttribute("AVANTI", "true");
                                                    request.setAttribute("INDIETRO", "true");
                                                }
                                            }
                                            request.setAttribute("INDEX", index);
                                            showJsp(process, "anagrafica_altroRichiedente.jsp", false);
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // per procedere allo step successivo mi devo assicurare che sia l'anagrafica sia gli href siano completi o che l'utente sia un amministratore di stampe
            r = request.getParameter("navigation.button.next");
            boolean ammStampe = checkAmministratoreStampe(process);
            if (r != null && !ammStampe) {
                boolean hrefCompleti = true;
                Set s = dataForm.getListaHref().keySet();
                for (Iterator iterator = s.iterator(); iterator.hasNext();) {
                    String key = (String) iterator.next();
                    SezioneCompilabileBean scb = (SezioneCompilabileBean) dataForm.getListaHref().get(key);
                    hrefCompleti = hrefCompleti && scb.isComplete();
                }
                if (dataForm.getOggettoIstanza() != null) {
                    hrefCompleti = hrefCompleti && dataForm.getOggettoIstanza().isComplete();
                }
                boolean fineCompilazioneAltriRichiedenti = true;
                for (Iterator iterator = dataForm.getAltriRichiedenti().iterator(); iterator.hasNext();) {
                    AnagraficaBean altraAnagrafica = (AnagraficaBean) iterator.next();
                    if (!altraAnagrafica.isFineCompilazione()) {
                        fineCompilazioneAltriRichiedenti = false;
                    }
                }

                String userID = (String) session.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER);
                boolean isAnonymus = SiracHelper.isAnonymousUser(userID);
                if (!dataForm.getAnagrafica().isFineCompilazione() || !fineCompilazioneAltriRichiedenti) {
                    if (isAnonymus /*&& (dataForm.getTipoBookmark().equalsIgnoreCase(Costant.bookmarkTypeCortesiaLabel) || dataForm.getTipoBookmark().equalsIgnoreCase(Costant.bookmarkTypeCompleteLabel) )*/) {
                        errors.add("error.anagraficaNonCompletaConPulsante");
                        ManagerAnagrafica ma = new ManagerAnagrafica();
                        String htmlIniziale = ma.getHtmlNonCompilabileIniziale(dataForm, request);
                        request.setAttribute("htmlIniziale", htmlIniziale);
                        ArrayList listaProc = buildModelloUnicoBean(dataForm);
                        request.setAttribute("listaProcedimenti", listaProc);
                        showJsp(process, "modelloUnico.jsp", false);
                    } else {
                        errors.add("error.anagraficaNonCompleta");
                        ManagerAnagrafica ma = new ManagerAnagrafica();
                        String htmlIniziale = ma.getHtmlNonCompilabileIniziale(dataForm, request);
                        request.setAttribute("htmlIniziale", htmlIniziale);
                        ArrayList listaProc = buildModelloUnicoBean(dataForm);
                        request.setAttribute("listaProcedimenti", listaProc);
                        showJsp(process, "modelloUnico.jsp", false);
                    }
                } else if (!hrefCompleti && !isAnonymus) {
                    errors.add("error.dichiarazioniDinamicheNonComplete");
                    ManagerAnagrafica ma = new ManagerAnagrafica();
                    String htmlIniziale = ma.getHtmlNonCompilabileIniziale(dataForm, request);
                    request.setAttribute("htmlIniziale", htmlIniziale);
                    ArrayList listaProc = buildModelloUnicoBean(dataForm);
                    request.setAttribute("listaProcedimenti", listaProc);
                    showJsp(process, "modelloUnico.jsp", false);
                }
            }
            logger.debug("ModelloUnicoStep - logicalValidate method END");
            request.setAttribute("forward", "forward");
            return true;
        } catch (Exception e) {
            errors.add("error.generic", "Errore interno");
            gestioneEccezioni(process, 5, e);
            dataForm.setInternalError(true);
            return false;
        }
    }

    private ArrayList buildModelloUnicoBean(ProcessData dataForm) {
        boolean aggiuntoAttestatoVersamentoOneri = false;
        // recupero info 
        ArrayList listaProc = new ArrayList();
        Set setProcedimenti = dataForm.getListaProcedimenti().keySet();
        for (Iterator iterator = setProcedimenti.iterator(); iterator.hasNext();) {
            String keyProc = (String) iterator.next();
            ProcedimentoBean procedimento = (ProcedimentoBean) dataForm.getListaProcedimenti().get(keyProc);
            ModelloUnicoBean mub = new ModelloUnicoBean();
            mub.setCodice(procedimento.getCodice());
            mub.setDescrizione(procedimento.getNome());
            mub.setEnte(procedimento.getEnte());
            HashMap tmpMap = new HashMap();
            for (Iterator iterator2 = procedimento.getCodInterventi().iterator(); iterator2.hasNext();) {
                String codInt = (String) iterator2.next();
                for (Iterator iterator3 = dataForm.getInterventi().iterator(); iterator3.hasNext();) {
                    InterventoBean interv = (InterventoBean) iterator3.next();
                    if (interv.getCodice() != null && codInt != null && interv.getCodice().equalsIgnoreCase(codInt)) {
                        mub.addListaIntervento(interv.getDescrizione());
                        for (Iterator iterator4 = interv.getListaCodiciNormative().iterator(); iterator4.hasNext();) {
                            String codNormativa = (String) iterator4.next();
                            NormativaBean n = (NormativaBean) dataForm.getListaNormative().get(codNormativa);
                            if (n != null && !mub.getListaNormative().containsKey(codNormativa)) {
                                mub.addListaNormative(codNormativa, n);
                            }
                        }
                        for (Iterator iterator4 = interv.getListaCodiciAllegati().iterator(); iterator4.hasNext();) {
                            String codAllegato = (String) iterator4.next();
                            DocumentoBean a = (DocumentoBean) dataForm.getListaDocRichiesti().get(codAllegato);
                            if (a != null && !tmpMap.containsKey(codAllegato)) {
                                tmpMap.put(codAllegato, "");
                                AllegatoBean ab = (AllegatoBean) dataForm.getListaAllegati().get(codAllegato);
                                mub.addListaDocumenti(ab);
                            }
                        }
                    }
                }
                for (Iterator iterator3 = dataForm.getInterventiFacoltativi().iterator(); iterator3.hasNext();) {
                    InterventoBean interv = (InterventoBean) iterator3.next();
                    if (interv.getCodice() != null && codInt != null && interv.getCodice().equalsIgnoreCase(codInt)) {
                        mub.addListaIntervento(interv.getDescrizione());
                        for (Iterator iterator4 = interv.getListaCodiciNormative().iterator(); iterator4.hasNext();) {
                            String codNormativa = (String) iterator4.next();
                            NormativaBean n = (NormativaBean) dataForm.getListaNormative().get(codNormativa);
                            if (n != null && !mub.getListaNormative().containsKey(codNormativa)) {
                                mub.addListaNormative(codNormativa, n);
                            }
                        }
                        for (Iterator iterator4 = interv.getListaCodiciAllegati().iterator(); iterator4.hasNext();) {
                            String codAllegato = (String) iterator4.next();
                            DocumentoBean a = (DocumentoBean) dataForm.getListaDocRichiesti().get(codAllegato);
                            if (a != null && !tmpMap.containsKey(codAllegato)) {
                                tmpMap.put(codAllegato, "");
                                AllegatoBean ab = (AllegatoBean) dataForm.getListaAllegati().get(codAllegato);
                                mub.addListaDocumenti(ab);
                            }
                        }
                    }
                }
            }
            Set sett = mub.getListaNormative().keySet();
            for (Iterator iterator3 = sett.iterator(); iterator3.hasNext();) {
                String keyNormativa = (String) iterator3.next();
                NormativaBean norm = (NormativaBean) mub.getListaNormative().get(keyNormativa);
                mub.addListaTmp(norm);
            }
            if (dataForm.isAttestatoPagamentoObbligatorio() && !aggiuntoAttestatoVersamentoOneri) {
                ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);

                String codiceSportello = "";
                SportelloBean sportello = null;
                Set chiaviSettore = dataForm.getListaSportelli().keySet();
                boolean trovato = sportello != null;
                Iterator chiaviSettoreIterator = chiaviSettore.iterator();
                while (chiaviSettoreIterator.hasNext() && !trovato) {
                    String chiaveSettore = (String) chiaviSettoreIterator.next();
                    if (sportello == null) {
                        sportello = (SportelloBean) dataForm.getListaSportelli().get(chiaveSettore);
                        trovato = true;
                        codiceSportello = sportello.getCodiceSportello();
                    }
                }

                AllegatoBean attestazioneVersamento = delegate.getAllegatoAttestatoVersamento(codiceSportello);
                if (attestazioneVersamento != null) {
                    dataForm.getListaAllegati().put(attestazioneVersamento.getCodice(), attestazioneVersamento);
                    mub.getListaDocumenti().add(attestazioneVersamento);
                    DocumentoBean documentoBean = new DocumentoBean();
                    documentoBean.setCodiceDoc(attestazioneVersamento.getCodice());
                    documentoBean.setDescrizione(attestazioneVersamento.getDescrizione());
                    documentoBean.setTitolo(attestazioneVersamento.getTitolo());

                    dataForm.getListaDocRichiesti().put(attestazioneVersamento.getCodice(), documentoBean);
                }

                aggiuntoAttestatoVersamentoOneri = true;

            }
            if (dataForm.isAttestatoPagamentoObbligatorio()) {
                setOneriAnticipati(dataForm);
            }
            if (!dataForm.isAttestatoPagamentoObbligatorio()) {
                ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
                String codiceSportello = "";
                SportelloBean sportello = null;
                Set chiaviSettore = dataForm.getListaSportelli().keySet();
                boolean trovato = sportello != null;
                Iterator chiaviSettoreIterator = chiaviSettore.iterator();
                while (chiaviSettoreIterator.hasNext() && !trovato) {
                    String chiaveSettore = (String) chiaviSettoreIterator.next();
                    if (sportello == null) {
                        sportello = (SportelloBean) dataForm.getListaSportelli().get(chiaveSettore);
                        trovato = true;
                        codiceSportello = sportello.getCodiceSportello();
                    }
                }
                AllegatoBean attestazioneVersamento = delegate.getAllegatoAttestatoVersamento(codiceSportello);
                mub.getListaDocumenti().remove(attestazioneVersamento.getCodice());
                dataForm.getListaAllegati().remove(attestazioneVersamento.getCodice());
                dataForm.getListaDocRichiesti().remove(attestazioneVersamento.getCodice());
            }
            listaProc.add(mub);
        }
        return listaProc;
    }

    private void salvaDatiHref(IRequestWrapper request, ProcessData dataForm, AbstractPplProcess process) {
        salvaDatiHref(request, dataForm, null, process.getCommune().getOid());
    }

    private void salvaDatiHref(IRequestWrapper request, ProcessData dataForm, String hreff, String oidComune) {
        salvaDatiHref(request, dataForm, null, oidComune, null, null);
    }

    private void salvaDatiHref(IRequestWrapper request, ProcessData dataForm, String hreff, String oidComune, String nomeCampoNoJavascript, String valoreCampoNoJavascript) {
        String href = (String) request.getParameter("href");
        if (href != null) {
            request.setAttribute("lastHref", href);
        }
//        dataForm.getDescrizioneCampiNonCompilati().clear();
        HashMap valoriIncrociati = new HashMap();
        HashMap hmCheckRaggruppati = new HashMap();
        HashMap hm = dataForm.getListaHref();
        HashMap mappaCampiDaRipulire = new HashMap();
        SezioneCompilabileBean sezComp = (SezioneCompilabileBean) hm.get(href);
        if (sezComp == null && dataForm.getOggettoIstanza() != null && dataForm.getOggettoIstanza().getHref().equalsIgnoreCase(href)) {
            sezComp = dataForm.getOggettoIstanza();
        }
        boolean complete = true;
        if (sezComp != null) {
            List listaHrefCampiBean = sezComp.getCampi();
            Iterator it = listaHrefCampiBean.iterator();

            while (it.hasNext()) {
                HrefCampiBean hrefCampi = (HrefCampiBean) it.next();
                String nomeCampo = hrefCampi.getNome();
                if (Utilities.isset(hrefCampi.getMarcatore_incrociato())) {
                    valoriIncrociati.put(hrefCampi.getMarcatore_incrociato(), hrefCampi);
                }
                if (!hrefCampi.getTipo().equalsIgnoreCase("R")) {
                    String valore = request.getUnwrappedRequest().getParameter(href + "_" + nomeCampo);

                    if (mappaCampiDaRipulire.containsKey(nomeCampo)) {
                        if (hrefCampi.getTipo().equalsIgnoreCase("R")) {
                            if (Utilities.NVL(hrefCampi.getValore(), "").equalsIgnoreCase((String) mappaCampiDaRipulire.get(nomeCampo))) {
                                valore = "";
                            }
                        } else {
                            valore = "";
                        }
                    }
                    if (hrefCampi.getTipo().equalsIgnoreCase("C") && nomeCampoNoJavascript != null && hrefCampi.getNome().equalsIgnoreCase(nomeCampoNoJavascript)) {
                        if (hrefCampi.getValoreUtente() == null || hrefCampi.getValoreUtente().equalsIgnoreCase("")) {
//                    		hrefCampi.setValoreUtente("1");
                            valore = "1";
                        } else {
//                    		hrefCampi.setValoreUtente("");
                            valore = "";
                            for (Iterator iterator = hrefCampi.getCampiCollegati().iterator(); iterator.hasNext();) {
                                String[] nomeCampoColl = (String[]) iterator.next();
                                for (Iterator iterator2 = sezComp.getCampi().iterator(); iterator2.hasNext();) {
                                    HrefCampiBean campo = (HrefCampiBean) iterator2.next();
                                    if (campo.getNome().equalsIgnoreCase(nomeCampoColl[0])) {
                                        mappaCampiDaRipulire.put(campo.getNome(), "y");
                                        campo.setValoreUtente("");
                                    }
                                }
                            }
                        }
                    }
//                    else{
                    hrefCampi.setValoreUtente(valore);
//                    }
                    if ((valore == null || valore.equalsIgnoreCase("")) && hrefCampi.getControllo() != null && !hrefCampi.getControllo().equalsIgnoreCase("")) {
                        if (hrefCampi.getTipo().equalsIgnoreCase("C") && hrefCampi.getRaggruppamento_check() != null && !hrefCampi.getRaggruppamento_check().equalsIgnoreCase("")) {
                            if (hmCheckRaggruppati.get(hrefCampi.getRaggruppamento_check()) == null) {
                                // controllo se il checkbox ï¿½ collegato a qualche radiobutton 
                                if (Utilities.isset(hrefCampi.getCampo_collegato())) {
                                    // String radioButtonCollegato = request.getParameter(hrefCampi.getCampo_collegato());
                                    String radioButtonCollegato = request.getParameter(href + "_" + hrefCampi.getCampo_collegato());
                                    if (radioButtonCollegato != null && radioButtonCollegato.equalsIgnoreCase(hrefCampi.getVal_campo_collegato())) {
                                        hmCheckRaggruppati.put(hrefCampi.getRaggruppamento_check(), "N");
                                    }
                                } else {
                                    hmCheckRaggruppati.put(hrefCampi.getRaggruppamento_check(), "N");
                                }
                            }
                            //complete = true;
                        } else if ((hrefCampi.getTipo().equalsIgnoreCase("I") || hrefCampi.getTipo().equalsIgnoreCase("A")
                                // pc - controllo inizio                                
                                || hrefCampi.getTipo().equalsIgnoreCase("C") || hrefCampi.getTipo().equalsIgnoreCase("R") || hrefCampi.getTipo().equalsIgnoreCase("L") // pc - controllo fine                                
                                ) && hrefCampi.getCampo_collegato() != null && !hrefCampi.getCampo_collegato().equalsIgnoreCase("")
                                && hrefCampi.getVal_campo_collegato() != null && !hrefCampi.getVal_campo_collegato().equalsIgnoreCase("")) {
                            String valCampoCollegato = request.getParameter(href + "_" + hrefCampi.getCampo_collegato());
                            if (valCampoCollegato != null && valCampoCollegato.equalsIgnoreCase(hrefCampi.getVal_campo_collegato())) {
                                complete = false;
                            }
                        } else {
                            complete = false;
                        }
                    } else {
                        if (hrefCampi.getTipo().equalsIgnoreCase("C") && hrefCampi.getRaggruppamento_check() != null && !hrefCampi.getRaggruppamento_check().equalsIgnoreCase("")) {
                            hmCheckRaggruppati.put(hrefCampi.getRaggruppamento_check(), "Y");
                        }
                    }
                    if (nomeCampoNoJavascript != null) {

                    }
                } else {
                    String radioSelezionato = request.getParameter(href + "_" + nomeCampo);
                    if (radioSelezionato != null
                            && radioSelezionato.equalsIgnoreCase(hrefCampi.getValore())) {
                        hrefCampi.setValoreUtente(radioSelezionato);
                    } else {
                        hrefCampi.setValoreUtente(null);
                    }

                    if (nomeCampoNoJavascript != null && valoreCampoNoJavascript != null) {
                        if (hrefCampi.getNome().equalsIgnoreCase(nomeCampoNoJavascript)) {
                            if (hrefCampi.getValore().equalsIgnoreCase(valoreCampoNoJavascript)) {
                                hrefCampi.setValoreUtente(valoreCampoNoJavascript);

                            } else {
                                hrefCampi.setValoreUtente("");
                                for (Iterator iterator = hrefCampi.getCampiCollegati().iterator(); iterator.hasNext();) {
                                    String nomeCampoCollegato[] = (String[]) iterator.next();
                                    for (Iterator iterator2 = listaHrefCampiBean.iterator(); iterator2.hasNext();) {
                                        HrefCampiBean campo = (HrefCampiBean) iterator2.next();
                                        if (campo.getNome().equalsIgnoreCase(nomeCampoCollegato[0])) {
                                            if (campo.getTipo().equalsIgnoreCase("R") && campo.getValore().equalsIgnoreCase(nomeCampoCollegato[1])) {
                                                campo.setValoreUtente("");
                                                mappaCampiDaRipulire.put(campo.getNome(), campo.getValore());
                                            } else if (!campo.getTipo().equalsIgnoreCase("R")) {
                                                campo.setValoreUtente("");
                                                mappaCampiDaRipulire.put(campo.getNome(), campo.getValore());
                                            }

                                            //
                                            //		&& campo.getValore()!=null && campo.getValore().equalsIgnoreCase(nomeCampoCollegato[1])){
                                        }
                                    }
                                }
                            }
                        }
//                        for (Iterator iterator = listaHrefCampiBean.iterator(); iterator.hasNext();) {
//    						HrefCampiBean campo = (HrefCampiBean) iterator.next();
//    						if (campo.get
//    						
//    					}
                    }

                    if (radioSelezionato == null && hrefCampi.getControllo() != null && !hrefCampi.getControllo().equalsIgnoreCase("")) {
                        if (Utilities.isset(hrefCampi.getCampo_collegato())) {
                            String val = request.getParameter(href + "_" + hrefCampi.getCampo_collegato());
                            if (Utilities.isset(val) && val.equalsIgnoreCase(hrefCampi.getVal_campo_collegato())) {
                                complete = false;
                            }
                        } else {
                            complete = false;
                        }
                    }
                }
            }

            // Gestione check raggruppati
            if (hmCheckRaggruppati.size() > 0) {
                Set key = hmCheckRaggruppati.keySet();
                Iterator itKey = key.iterator();
                while (itKey.hasNext()) {
                    String value = (String) hmCheckRaggruppati.get((String) itKey.next());
                    if (value != null && value.equalsIgnoreCase("N")) {
                        complete = false;
                    }
                }
            }
            sezComp.setComplete(complete);
            sezComp.setHtml(HtmlRenderer.costruisciStringaHtml(sezComp, false, "SectionText", null, null, null, null, null, null, dataForm, request));
            sezComp.setHtmlEditable(HtmlRenderer.costruisciStringaHtml(sezComp, true, "SectionText", "TableRow0", "96%", "0", "6", null, "25", dataForm, request));

            // elaborazione campi "accessibilita' incrociata dati inseriti"
            if (valoriIncrociati.size() > 0) {
                inserimentoIncrociato(href, valoriIncrociati, dataForm, request);
            }
            // 

        }

        // sezione per aggiornare l'oggetto della pratica all'interno dell'AbstractData
        if (((SezioneCompilabileBean) hm.get(href)) == null && dataForm.getOggettoIstanza() != null && dataForm.getOggettoIstanza().getHref().equalsIgnoreCase(href)) {
            String oggettoPratica = "";
            ArrayList listaOggetti = new ArrayList();
            for (Iterator iterator = dataForm.getOggettoIstanza().getCampi().iterator(); iterator.hasNext();) {
                HrefCampiBean campo = (HrefCampiBean) iterator.next();
                if (campo.getCampo_xml_mod() != null && campo.getCampo_xml_mod().endsWith("OGGETTO_PRATICA")) {
                    if (campo.getValoreUtente() != null && !campo.getValoreUtente().equalsIgnoreCase("")) {
                        listaOggetti.add(campo.getValoreUtente());
                    }
                }
            }
            for (Iterator iterator = listaOggetti.iterator(); iterator.hasNext();) {
                String val = (String) iterator.next();
                oggettoPratica += val;
                if (iterator.hasNext()) {
                    oggettoPratica += " - ";
                }
            }
            // dataForm.setOggettoPratica(oggettoPratica);
            ManagerAttributiNuoviFramework manf = new ManagerAttributiNuoviFramework();
            manf.settaAttributoProcessData(dataForm, "oggettoPratica", String.class, oggettoPratica);
        }

    }

    private void inserimentoIncrociato(String hrefOrigine, HashMap valoriIncrociati, ProcessData dataForm, IRequestWrapper request) {
        Iterator it = dataForm.getListaHref().keySet().iterator();
        while (it.hasNext()) {
            String href = (String) it.next();
            SezioneCompilabileBean sezComp = (SezioneCompilabileBean) dataForm.getListaHref().get(href);
            boolean trovato = false;
            if (!hrefOrigine.equalsIgnoreCase(href) && sezComp != null) {
                for (Iterator iterator = sezComp.getCampi().iterator(); iterator.hasNext();) {
                    HrefCampiBean campo = (HrefCampiBean) iterator.next();
                    if (Utilities.isset(campo.getMarcatore_incrociato()) && valoriIncrociati.containsKey(campo.getMarcatore_incrociato())) {
                        HrefCampiBean campoOrigine = (HrefCampiBean) valoriIncrociati.get(campo.getMarcatore_incrociato());
                        if (campo.getTipo().equalsIgnoreCase(campoOrigine.getTipo())) {
                            campo.setValoreUtente(campoOrigine.getValoreUtente());
                            trovato = true;
                        }
                    }
                }
                if (trovato) {
                    salvaDatiHrefCollegati(href, dataForm, request);
                }
            }
        }
    }

    private void salvaDatiHrefCollegati(String href, ProcessData dataForm, IRequestWrapper request) {
        HashMap valoriIncrociati = new HashMap();
        HashMap hmCheckRaggruppati = new HashMap();
        HashMap hm = dataForm.getListaHref();
        SezioneCompilabileBean sezComp = (SezioneCompilabileBean) hm.get(href);
        boolean complete = true;
        if (sezComp != null) {
            List listaHrefCampiBean = sezComp.getCampi();
            Iterator it = listaHrefCampiBean.iterator();

            while (it.hasNext()) {
                HrefCampiBean hrefCampi = (HrefCampiBean) it.next();
                String nomeCampo = hrefCampi.getNome();
                if (!hrefCampi.getTipo().equalsIgnoreCase("R")) {
                    //String valore = request.getParameter(nomeCampo);
                    String valore = hrefCampi.getValoreUtente();
                    boolean continua = true;

                    //hrefCampi.setValoreUtente(valore);
                    if ((valore == null || valore.equalsIgnoreCase(""))
                            && hrefCampi.getControllo() != null
                            && !hrefCampi.getControllo().equalsIgnoreCase("")) {
                        if (hrefCampi.getTipo().equalsIgnoreCase("C") && hrefCampi.getRaggruppamento_check() != null && !hrefCampi.getRaggruppamento_check().equalsIgnoreCase("")) {
                            if (hmCheckRaggruppati.get(hrefCampi.getRaggruppamento_check()) == null) {
                                // controllo se il checkbox ï¿½ collegato a qualche radiobutton 
                                if (Utilities.isset(hrefCampi.getCampo_collegato())) {
                                    //String radioButtonCollegato = request.getParameter(hrefCampi.getCampo_collegato());
                                    String radioButtonCollegato = hrefCampi.getCampo_collegato();
                                    if (radioButtonCollegato != null && radioButtonCollegato.equalsIgnoreCase(hrefCampi.getVal_campo_collegato())) {
                                        hmCheckRaggruppati.put(hrefCampi.getRaggruppamento_check(), "N");
                                    }
                                } else {
                                    hmCheckRaggruppati.put(hrefCampi.getRaggruppamento_check(), "N");
                                }
                            }
                            //complete = true;
                        } else if (hrefCampi.getTipo().equalsIgnoreCase("I")
                                && hrefCampi.getCampo_collegato() != null && !hrefCampi.getCampo_collegato().equalsIgnoreCase("")
                                && hrefCampi.getVal_campo_collegato() != null && !hrefCampi.getVal_campo_collegato().equalsIgnoreCase("")) {
                            //String valCampoCollegato = request.getParameter(hrefCampi.getCampo_collegato());
                            String valCampoCollegato = hrefCampi.getCampo_collegato();
                            if (valCampoCollegato != null && valCampoCollegato.equalsIgnoreCase(hrefCampi.getVal_campo_collegato())) {
                                complete = false;
                            }
                        } else {
                            complete = false;
                        }
                    } else {
                        if (hrefCampi.getTipo().equalsIgnoreCase("C") && hrefCampi.getRaggruppamento_check() != null && !hrefCampi.getRaggruppamento_check().equalsIgnoreCase("")) {
                            hmCheckRaggruppati.put(hrefCampi.getRaggruppamento_check(), "Y");
                        }
                    }
                } else {
                    //String radioSelezionato = request.getParameter(nomeCampo);
                    String radioSelezionato = hrefCampi.getValore();
                    if (radioSelezionato != null && radioSelezionato.equalsIgnoreCase(hrefCampi.getValoreUtente())) {
                        hrefCampi.setValoreUtente(radioSelezionato);
                    } else {
                        hrefCampi.setValoreUtente(null);
                    }

//                    if(nomeCampoNoJavascript!=null && valoreCampoNoJavascript!=null){
//                    	if(hrefCampi.getNome().equalsIgnoreCase(nomeCampoNoJavascript)){
//                			if(hrefCampi.getValore().equalsIgnoreCase(valoreCampoNoJavascript)){
//                				hrefCampi.setValoreUtente(valoreCampoNoJavascript);
//
//                			}
//                			else{
//                				hrefCampi.setValoreUtente("");
//                            	for (Iterator iterator = hrefCampi.getCampiCollegati().iterator(); iterator.hasNext();) {
//                            		String nomeCampoCollegato[] = (String[]) iterator.next();
//                            		for (Iterator iterator2 = listaHrefCampiBean.iterator(); iterator2.hasNext();) {
//										HrefCampiBean campo = (HrefCampiBean) iterator2.next();
//										if (campo.getNome().equalsIgnoreCase(nomeCampoCollegato[0])) {
//											if (campo.getTipo().equalsIgnoreCase("R") && campo.getValore().equalsIgnoreCase(nomeCampoCollegato[1])){
//												campo.setValoreUtente("");
//											} else if (!campo.getTipo().equalsIgnoreCase("R")){
//												campo.setValoreUtente("");
//											}
//											
//										//
//										//		&& campo.getValore()!=null && campo.getValore().equalsIgnoreCase(nomeCampoCollegato[1])){
//											
//										}
//									}
//                            	}
//                			}
//                		}
//
//                    if (radioSelezionato == null
//                        && hrefCampi.getControllo() != null
//                        && !hrefCampi.getControllo().equalsIgnoreCase("")) {
//                        complete = false;
//                    }                   
                }
            }
            sezComp.setHtml(HtmlRenderer.costruisciStringaHtml(sezComp, false, "SectionText", null, null, null, null, null, null, dataForm, request));
            sezComp.setHtmlEditable(HtmlRenderer.costruisciStringaHtml(sezComp, true, "SectionText", "TableRow0", "96%", "0", "6", null, "25", dataForm, request));

            // Gestione check raggruppati
            if (hmCheckRaggruppati.size() > 0) {
                Set key = hmCheckRaggruppati.keySet();
                Iterator itKey = key.iterator();
                while (itKey.hasNext()) {
                    String value = (String) hmCheckRaggruppati.get((String) itKey.next());
                    if (value != null && value.equalsIgnoreCase("N")) {
                        complete = false;
                    }
                }
            }
            sezComp.setComplete(complete);

        }

    }

// pc - ws modificato per gestire i ws su dichiarazione multipla  inizio
    // tolto private void chiamaWebService(AbstractPplProcess process, IRequestWrapper request, ProcessData dataForm, String codCampo) {
    private void chiamaWebService(AbstractPplProcess process, IRequestWrapper request, ProcessData dataForm, String codCampo, Integer livelloMultiplo) {
// pc - ws modificato per gestire i ws su dichiarazione multipla fine

        process.cleanErrors();

        String href = request.getParameter("href");
// pc - ws multiriga eliminato inizio        
//        if (href == null) {
//            href = request.getParameter("href");
//        }
// pc - ws multiriga eliminato fine   

        SezioneCompilabileBean sezComp = new SezioneCompilabileBean();
        String webService = null;
        if (href != null && href.equalsIgnoreCase("ANAG")) {
            String codiceCampoCollegato = null;
            String valoreCampoCollegato = null;
            for (Iterator iterator = dataForm.getAnagrafica().getListaCampiStep().iterator(); iterator.hasNext();) {
                HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator.next();
                if (campoAnagrafica.getNome().equalsIgnoreCase(codCampo)) {
                    codiceCampoCollegato = campoAnagrafica.getCampo_collegato();
                    valoreCampoCollegato = campoAnagrafica.getVal_campo_collegato();
                    webService = campoAnagrafica.getWeb_serv();
                    break;
                }
            }
            int numRiga = 0;
            int maxcolonna = 0;
            for (Iterator iterator2 = dataForm.getAnagrafica().getListaCampiStep().iterator(); iterator2.hasNext();) {
                HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator2.next();
                if (campoAnagrafica.getRiga() > numRiga) {
                    numRiga = campoAnagrafica.getRiga();
                }
                if (campoAnagrafica.getPosizione() > maxcolonna) {
                    maxcolonna = campoAnagrafica.getPosizione();
                }
                if (campoAnagrafica.getCampo_collegato() != null && campoAnagrafica.getCampo_collegato().equalsIgnoreCase(codiceCampoCollegato)
                        && campoAnagrafica.getVal_campo_collegato() != null && campoAnagrafica.getVal_campo_collegato().equalsIgnoreCase(valoreCampoCollegato)
                        && campoAnagrafica.getWeb_serv() != null && campoAnagrafica.getWeb_serv().equalsIgnoreCase(webService)) {
                    sezComp.addCampi(campoAnagrafica);
                }
            }
            sezComp.setRowCount(numRiga);
            sezComp.setTdCount(maxcolonna);
            sezComp.setTitolo("");
            sezComp.setPiedeHref("");
            sezComp.setHref("ANAG");
        } else {
            Map hm = dataForm.getListaHref();
            sezComp = (SezioneCompilabileBean) hm.get(href);
        }
// pc - ws modificato per azzerare solo i dati valorizzabili da ws solo per il livello attuale inizio         
//        if (sezComp.getCampi()!= null) {
//        	Iterator iter = sezComp.getCampi().iterator();
//        	while (iter.hasNext()) {
//        		HrefCampiBean campo = (HrefCampiBean) iter.next();
//        		if ((campo.getCampo_key().equalsIgnoreCase("N"))){
//        			campo.setValoreUtente("");
//        		}
//        	}
//        }  

        if (sezComp.getCampi() != null) {
            Iterator iter = sezComp.getCampi().iterator();
            while (iter.hasNext()) {
                HrefCampiBean campo = (HrefCampiBean) iter.next();
                if ((campo.getCampo_key() == null) && (campo.getCampo_dati() != null)) {
                    if (livelloMultiplo != null) {
                        if (campo.getMolteplicita() == livelloMultiplo.intValue()) {
                            campo.setValoreUtente("");
                        }
                    } else {
                        campo.setValoreUtente("");
                    }
                }
            }
        }
// pc - ws modificato fine  
        String urlWebService = "";
        String nomeWebService = "";
        String metodoWebService = "";
        if (sezComp != null) {
            List listaHrefCampiBean = sezComp.getCampi();
            Iterator it = listaHrefCampiBean.iterator();
            // Controllo i campi chiave
            PrecompilazioneBean precompilazioneBean = new PrecompilazioneBean();

            // String valoreCampoChiave = "";
            while (it.hasNext()) {
                HrefCampiBean hrefCampi = (HrefCampiBean) it.next();
// pc - ws modificato    inizio                
                String nomeCampo = null;
                if (livelloMultiplo != null) {
                    if (hrefCampi.getMolteplicita() == livelloMultiplo.intValue()) {
                        nomeCampo = hrefCampi.getNome();
                    }
                } else {
                    nomeCampo = hrefCampi.getNome();
                }
                if (hrefCampi.getNome().equals(nomeCampo)) {
// pc - ws modificato fine 
                    if (hrefCampi.getCampo_key() != null) {
                        CampoPrecompilazioneBean campoPrecBean = new CampoPrecompilazioneBean();
                        campoPrecBean.setCodice(hrefCampi.getCampo_dati());
                        if (hrefCampi.getTipo().equalsIgnoreCase("N")) {
                            campoPrecBean.getDescrizione().add(hrefCampi.getValore());
                        } else {
                            campoPrecBean.getDescrizione().add(hrefCampi.getValoreUtente());
                        }
                        //campoPrecBean.getDescrizione().add(hrefCampi.getValoreUtente());
                        if (hrefCampi.getWeb_serv() != null
                                && !"".equalsIgnoreCase(hrefCampi.getWeb_serv())) {
                            urlWebService = hrefCampi.getWeb_serv();
                        }
                        if (hrefCampi.getNome_xsd() != null
                                && !"".equalsIgnoreCase(hrefCampi.getNome_xsd())) {
                            String tmp = hrefCampi.getNome_xsd();
                            nomeWebService = tmp.substring(0, tmp.indexOf("|") > 0 ? tmp.indexOf("|") : tmp.length());
                            if (tmp.equalsIgnoreCase(nomeWebService)) {
                                metodoWebService = "process";
                            } else {
                                metodoWebService = tmp.substring(tmp.indexOf("|") + 1, tmp.length());
                            }
                        }
                        precompilazioneBean.getInput().add(campoPrecBean);
                    }
// pc - ws modificato   inizio              
                }
// pc - ws modificato fine 
            }
            if ("".equalsIgnoreCase(urlWebService)
                    || "".equalsIgnoreCase(nomeWebService)) {
                //gestioneEccezioni(process, new Exception("Non sono stati settati i parametri di connessone al web service"));
                dataForm.getErroreSuHref().add(new String("Non sono stati settati i parametri di connessone al web service"));
                return;
            }
            String richiestaWS = getXmlRixhiestaPrecompilazione(precompilazioneBean, dataForm);

            log.debug("--------- INIZIO XML INVIATO PER PRECOMPILAZIONE ---------");
            log.debug(richiestaWS);
            log.debug("--------- FINE XML INVIATO PER PRECOMPILAZIONE ---------");
            // String
            // richiestaWS=marshallPrecompilazioneBean(precompilazioneBean);
            String messaggio = "";
            boolean error = false;
            
            String sVisura = urlWebService;
            
            try {
                // utilizzo del proxy per ambiente di sviluppo (necessaria per invocazione dei ws in rete)
                ServletContext sc = request.getUnwrappedRequest().getSession().getServletContext();               
                ProxyManager proxy = null;
                if(StringUtils.isNotBlank(sc.getInitParameter("proxyHost"))) {
                	proxy = new ProxyManager(sc.getInitParameter("proxyHost"), sc.getInitParameter("proxyPort"), sc.getInitParameter("proxyUser"), sc.getInitParameter("proxyPassword").toCharArray(), sc.getInitParameter("nonProxyHosts"));
                	proxy.updateSystemProxy();
                }
                Properties p = System.getProperties();
                Enumeration keys = p.keys();
                while (keys.hasMoreElements()) {
                    String key = (String)keys.nextElement();
                    String value = (String)p.get(key);
                    logger.info(key + ": " + value);
                }       
                messaggio = process.callService(sVisura, richiestaWS);
                // rimozione del proxy per ambiente di sviluppo dopo la chiamata
                if(StringUtils.isNotBlank(sc.getInitParameter("proxyHost"))) {
                	proxy = new ProxyManager("", "", "", "".toCharArray(), "");
                	proxy.updateSystemProxy();
                }
                while (keys.hasMoreElements()) {
                    String key = (String)keys.nextElement();
                    String value = (String)p.get(key);
                    logger.debug(key + ": " + value);
                }
            } catch (ServiceException e) {
                ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
                String absPathToService = params.get("absPathToService");
                String resourcePath = absPathToService + System.getProperty("file.separator") + "risorse" + System.getProperty("file.separator");
                Properties props[] = UtilProperties.getProperties(resourcePath, "messaggi", process.getCommune().getOid());
                String messaggioErrore = UtilProperties.getPropertyKey(props[0], props[1], props[2], "error.precompilazione");
                dataForm.getErroreSuHref().add(messaggioErrore);
                log.error("Errore durante l'invocazione " + sVisura + " del web service per la precompilazione", e);
                error = true;
            } catch (SendException e) {
                ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
                String absPathToService = params.get("absPathToService");
                String resourcePath = absPathToService + System.getProperty("file.separator") + "risorse" + System.getProperty("file.separator");
                Properties props[] = UtilProperties.getProperties(resourcePath, "messaggi", process.getCommune().getOid());
                String messaggioErrore = UtilProperties.getPropertyKey(props[0], props[1], props[2], "error.precompilazione");
                dataForm.getErroreSuHref().add(messaggioErrore == null ? "Errore nell'invocazione del Web service di precompilazione" : messaggioErrore);
                log.error("Errore durante l'invocazione " + sVisura + " del web service per la precompilazione", e);
                error = true;

            } catch(Throwable t) {
            	log.error("Throwable", t);
            }
             
            if (!error) {
                log.debug("--------- INIZIO XML RICEVUTO PER PRECOMPILAZIONE ---------");
                log.debug(messaggio);
                log.debug("--------- FINE XML RICEVUTO PER PRECOMPILAZIONE ---------");

                // precompilazioneBean = unmarshallPrecompilazioneBean(messaggio);
                try {
                    precompilazioneBean = leggiXml(messaggio, dataForm);
                    String descrizioneErrore = null;
                    PrecompilazioneBeanDocument precomompilazioneBeanDoc = PrecompilazioneBeanDocument.Factory.parse(messaggio);
                    descrizioneErrore = precomompilazioneBeanDoc.getPrecompilazioneBean().getDescrizioneErrore();
                    if (precomompilazioneBeanDoc.getPrecompilazioneBean().getOutput() == null
                            || precomompilazioneBeanDoc.getPrecompilazioneBean().getOutput().getCampoPrecompilazioneBeanArray() == null
                            || precomompilazioneBeanDoc.getPrecompilazioneBean().getOutput().getCampoPrecompilazioneBeanArray().length == 0
                            || precomompilazioneBeanDoc.getPrecompilazioneBean().getOutput().getCampoPrecompilazioneBeanArray()[0].getDescrizioneArray() == null
                            || precomompilazioneBeanDoc.getPrecompilazioneBean().getOutput().getCampoPrecompilazioneBeanArray()[0].getDescrizioneArray().length == 0) {
                        if ((descrizioneErrore != null) && (!descrizioneErrore.equalsIgnoreCase(""))) {
                            dataForm.getErroreSuHref().add(descrizioneErrore);
                            List listaHrefCampiBean2 = sezComp.getCampi();
                            for (Iterator iterator = listaHrefCampiBean2.iterator(); iterator.hasNext();) {
                                HrefCampiBean hrefCampi = (HrefCampiBean) iterator.next();
                                if (hrefCampi.getCampo_dati() != null) {
                                    if (hrefCampi.getCampo_key() != null && hrefCampi.getTipo().equalsIgnoreCase("N")) {
                                        // null
                                    } else {
                                        hrefCampi.setValoreUtente("");
                                        if (hrefCampi.getTipo().equalsIgnoreCase("N")) {
                                            hrefCampi.setValore("");
                                        }
                                    }

//		                        	if (!hrefCampi.getTipo().equalsIgnoreCase("N")){
//		                        		hrefCampi.setValoreUtente("");
//		                        	} else if (hrefCampi.getTipo().equalsIgnoreCase("N") && !Utilities.isset(hrefCampi.getValore())){
//		                        		hrefCampi.setValoreUtente("");
//		                        	}
                                }
                            }
                            //int liv = livelloMaxCampiMultipli(sezComp);
                            sezComp.setHtml(HtmlRenderer.costruisciStringaHtml(sezComp, false, "SectionText", null, null, null, null, null, null, dataForm, request));
                            sezComp.setHtmlEditable(HtmlRenderer.costruisciStringaHtml(sezComp, true, "SectionText", "TableRow0", "96%", "0", "6", null, "25", dataForm, request));
                            process.cleanErrors();
                            process.addServiceError(descrizioneErrore);
                        } else {
                            ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
                            String absPathToService = params.get("absPathToService");
                            String resourcePath = absPathToService + System.getProperty("file.separator") + "risorse" + System.getProperty("file.separator");
                            Properties props[] = UtilProperties.getProperties(resourcePath, "messaggi", process.getCommune().getOid());
                            String messaggioErrore = UtilProperties.getPropertyKey(props[0], props[1], props[2], "error.precompilazioneVuota");
                            dataForm.getErroreSuHref().add(messaggioErrore);
                        }
                        return;
                    }
                } catch (Exception e) {
                    log.error("Errore durante il parsing dell XML ricevuto in output dal webservice di precompilazione");
                }

//	    		RichiestadiConcessioniEAutorizzazioniType cea = null;
                if (precompilazioneBean == null || precompilazioneBean.getOutput() == null || precompilazioneBean.getOutput().size() == 0) {
                    dataForm.getErroreSuHref().add("La ricerca non ha prodotto nessun risultato");
                    return;
                }

                if (precompilazioneBean != null) {
                    List output = precompilazioneBean.getOutput();
                    if (output.size() > 0
                            && ((CampoPrecompilazioneBean) output.get(0)).getDescrizione().size() == 1) {
                        it = listaHrefCampiBean.iterator();
                        while (it.hasNext()) {
                            HrefCampiBean hrefCampi = (HrefCampiBean) it.next();
// pc - ws modificato   inizio                                 
                            if (hrefCampi.getNumCampo() == 0 || (livelloMultiplo != null && hrefCampi.getMolteplicita() == livelloMultiplo.intValue())) {
// pc - ws modificato fine 
                                Iterator itCampiWebService = output.iterator();
                                while (itCampiWebService.hasNext()) {
                                    CampoPrecompilazioneBean campoPrecomp = (CampoPrecompilazioneBean) itCampiWebService.next();
                                    if (campoPrecomp.getCodice().equalsIgnoreCase(hrefCampi.getCampo_dati())) {
                                        List descrizioni = campoPrecomp.getDescrizione();
                                        if (descrizioni.size() == 1) {
                                            hrefCampi.setValoreUtente((String) descrizioni.get(0));
                                        }
                                    }
                                }
// pc - ws modificato   inizio                                     
                            }
// pc - ws modificato fine                                 
                        }
                    } else if (output.size() > 0
                            && ((CampoPrecompilazioneBean) output.get(0)).getDescrizione().size() > 1) {
                        List attivaRicerca = new LinkedList();
                        HashMap hmCampi = hrefCampiPerNome(listaHrefCampiBean);
                        Vector vec = null;
                        int numeroDiDesc = ((CampoPrecompilazioneBean) output.get(0)).getDescrizione().size();
                        // Intestazione
                        vec = new Vector();
                        for (int i = 0; i < output.size(); i++) {
                            CampoPrecompilazioneBean campoPrecomp = (CampoPrecompilazioneBean) output.get(i);
                            HrefCampiBean hrefCampi = (HrefCampiBean) hmCampi.get(campoPrecomp.getCodice());
                            if (hrefCampi != null && hrefCampi.getTipo().equalsIgnoreCase("I") || hrefCampi != null && hrefCampi.getTipo().equalsIgnoreCase("N")) {
                                vec.add(hrefCampi.getDescrizione());
                            }
                        }
                        attivaRicerca.add(vec);
                        // Dati
                        for (int j = 0; j < numeroDiDesc; j++) {
                            vec = new Vector();
                            for (int i = 0; i < output.size(); i++) {
                                CampoPrecompilazioneBean campoPrecomp = (CampoPrecompilazioneBean) output.get(i);
                                HrefCampiBean hrefCampi = (HrefCampiBean) hmCampi.get(campoPrecomp.getCodice());
                                if (hrefCampi != null && hrefCampi.getTipo().equalsIgnoreCase("I") || hrefCampi != null && hrefCampi.getTipo().equalsIgnoreCase("N")) {
                                    vec.add(campoPrecomp.getDescrizione().get(j)
                                            + "|" + hrefCampi.getNome());
                                }
                            }
                            attivaRicerca.add(vec);
                        }
                        request.setAttribute("attivaRicerca", attivaRicerca);
                    }
                }
                //int liv = livelloMaxCampiMultipli(sezComp);
                sezComp.setHtml(HtmlRenderer.costruisciStringaHtml(sezComp, false, "SectionText", null, null, null, null, null, null, dataForm, request));
                sezComp.setHtmlEditable(HtmlRenderer.costruisciStringaHtml(sezComp, true, "SectionText", "TableRow0", "96%", "0", "6", null, "25", dataForm, request));
            }
        }
    }

    private String getXmlRixhiestaPrecompilazione(PrecompilazioneBean precBean, ProcessData dataForm) {
        PrecompilazioneBeanDocument precomompilazioneBeanDoc = PrecompilazioneBeanDocument.Factory.newInstance();
        PrecompilazioneBeanDocument.PrecompilazioneBean precompilazioneBean = precomompilazioneBeanDoc.addNewPrecompilazioneBean();
        PrecompilazioneBeanDocument.PrecompilazioneBean.Input input = precompilazioneBean.addNewInput();
        // dataForm.getComune().get
        precompilazioneBean.setCodEnte(dataForm.getComuneSelezionato().getCodIstat() != null ? dataForm.getComuneSelezionato().getCodIstat() : "054024");

        List inputDati = precBean.getInput();
        Iterator it = inputDati.iterator();

        HashMap hm = new HashMap();
        while (it.hasNext()) {
            CampoPrecompilazioneBean campoPB = (CampoPrecompilazioneBean) it.next();
            if (hm.get(campoPB.getCodice()) == null) {
                it.gruppoinit.b1.concessioniEAutorizzazioni.precompilazione.CampoPrecompilazioneBean campoPre = input.addNewCampoPrecompilazioneBean();
                campoPre.setCodice(campoPB.getCodice());
                String descRadio = getValoreRadioButton(campoPB.getCodice(), inputDati);
                if (descRadio != null) {
                    campoPre.addDescrizione(descRadio);
                } else {
                    Iterator it2 = campoPB.getDescrizione().iterator();
                    while (it2.hasNext()) {
                        campoPre.addDescrizione((String) it2.next());
                    }
                }
            }
            hm.put(campoPB.getCodice(), "Y");
        }
        String xml = XmlObjectWrapper.generateXml(precomompilazioneBeanDoc);
        return xml;
    }

    private String getValoreRadioButton(String codice, List inputDati) {
        Iterator it = inputDati.iterator();
        String retVal = "";
        while (it.hasNext()) {
            CampoPrecompilazioneBean campoPB = (CampoPrecompilazioneBean) it.next();
            if (campoPB.getCodice().equalsIgnoreCase(codice) && campoPB.getDescrizione() != null && campoPB.getDescrizione().get(0) != null && !campoPB.getDescrizione().get(0).equals("")) {
                retVal = campoPB.getDescrizione().get(0).toString();
            }
        }
        return retVal;
    }

    private PrecompilazioneBean leggiXml(String sXml, ProcessData dataForm) {
        Document xml = null;
        List pratichePerUtente = new ArrayList();
        try {
            xml = DocumentHelper.parseText(sXml);
        } catch (DocumentException e) {
            dataForm.getErroreSuHref().add(new String(e.toString()));
            e.printStackTrace();
        }
        List nodoMultiplo = null;
        Node nodoSingolo = null;

        PrecompilazioneBean precBean = new PrecompilazioneBean();
        try {
            // nodoSingolo = xml.selectSingleNode("//Output");

            // nodoMultiplo =
            // nodoSingolo.selectNodes("//CampoPrecompilazioneBean");
            nodoMultiplo = xml.selectNodes("//Output/CampoPrecompilazioneBean");

            if (nodoMultiplo != null && nodoMultiplo.size() > 0) {
                Iterator itDati = nodoMultiplo.iterator();
                while (itDati.hasNext()) {
                    Node nodoCampoPrecBean = (Node) itDati.next();
                    CampoPrecompilazioneBean campoPrecBean = new CampoPrecompilazioneBean();
                    List output = precBean.getOutput();

                    String codice = nodoCampoPrecBean.valueOf("Codice");
                    campoPrecBean.setCodice(codice.equalsIgnoreCase("") ? "" : codice);
                    List descrizioni = new ArrayList();
                    String sCount = nodoCampoPrecBean.valueOf("count(Descrizione)");
                    int count = 0;
                    try {
                        count = Integer.parseInt(sCount);
                    } catch (Exception e) {
                    }

                    for (int i = 1; i <= count; i++) {
                        String desc = nodoCampoPrecBean.valueOf("Descrizione["
                                + i + "]");
                        String descMod = desc.replace('\'', '`');
                        descrizioni.add(descMod);
                    }

                    campoPrecBean.setDescrizione(descrizioni);
                    output.add(campoPrecBean);

                    precBean.setOutput(output);
                }
            }
        } catch (Exception e) {
            // An error occurred parsing or executing the XPath
            dataForm.getErroreSuHref().add(new String(e.toString()));
        }
        return precBean;
    }

    private HashMap hrefCampiPerNome(List listaHrefCampiBean) {
        HashMap hm = new HashMap();
        Iterator it = listaHrefCampiBean.iterator();
        while (it.hasNext()) {
            HrefCampiBean hrefCampi = (HrefCampiBean) it.next();
            hm.put(hrefCampi.getCampo_dati(), hrefCampi);
        }
        return hm;
    }

    private boolean checkAmministratoreStampe(AbstractPplProcess process) {
        String userID = (String) session.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER);
        boolean isAnonymus = SiracHelper.isAnonymousUser(userID);
        boolean trovato = false;
        if (!isAnonymus) {
//			ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
//			String absPathToService = params.get("absPathToService");
//			String resourcePath = absPathToService+System.getProperty("file.separator")+"risorse"+System.getProperty("file.separator");
//			Properties props[] = UtilProperties.getProperties(resourcePath,"parametri", process.getCommune().getOid());
            ProcessData dataForm = ((ProcessData) process.getData());
//			int index = 1;
//			String value = UtilProperties.getPropertyKey(props[0], props[1], props[2], "amministratoreStampe_"+index);
//			while (value!=null && !trovato){
//				if (dataForm.getRichiedente().getUtenteAutenticato().getCodiceFiscale().equalsIgnoreCase(value)){
//					trovato = true;
//				} else {
//					index++;
//					value = UtilProperties.getPropertyKey(props[0], props[1], props[2], "amministratoreStampe_"+index);
//				}
//			}

            ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
            trovato = delegate.isParametroConfigurazionePresent(process.getCommune().getOid(), "amministratoreStampe", dataForm.getRichiedente().getUtenteAutenticato().getCodiceFiscale());
        }
        return trovato;
    }

    private boolean procedimentoConOneriAnticipati(ProcedimentoBean procedimento, ProcessData dataForm) {
        boolean trovato = false;
        if (procedimento.getListaCodiciOneri() != null && procedimento.getListaCodiciOneri().size() > 0) {
            Iterator it = procedimento.getListaCodiciOneri().iterator();
            while (it.hasNext() && !trovato) {
                String codOnere = (String) it.next();
                Iterator itOneri = dataForm.getOneriAnticipati().iterator();
                while (itOneri.hasNext()) {
                    OneriBean onere = (OneriBean) itOneri.next();
                    if (onere.getCodice().equalsIgnoreCase(codOnere)) {
                        if (onere.getImporto() > 0) {
                            trovato = true;
                        }
                    }
                }
            }
        }
        return trovato;
    }

    private void configuraParametri(AbstractPplProcess process, ProcessData dataForm, ProcedimentoUnicoDAO delegate, boolean isAnonymus, String codEventoVita) throws Exception {
        if (dataForm.getIdBookmark() != null) {
            String xmlPermessi = "";
            xmlPermessi = delegate.getXMLPermessiBookmark(dataForm.getIdBookmark(), dataForm.getComuneSelezionato().getCodEnte(), codEventoVita);
            XPathReader xpr = new XPathReader(xmlPermessi);
            String tipoBookmark = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/TYPE"), "");
            String conInvioString = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/CON_INVIO"), "");
            boolean conInvio = Utilities.checked(conInvioString.equalsIgnoreCase("") ? "true" : conInvioString);
            if (tipoBookmark.equalsIgnoreCase(Costant.bookmarkTypeCompleteLabel)) {
                if (isAnonymus) {
                    if (conInvio) {
                        ((ProcessData) process.getData()).setTipoBookmark(Costant.bookmarkTypeCortesiaLabel);
                    } else {
                        ((ProcessData) process.getData()).setTipoBookmark(Costant.bookmarkTypeLivello2Label);
                    }
                } else {
                    ((ProcessData) process.getData()).setTipoBookmark(Costant.bookmarkTypeCompleteLabel);
                }
            } else if (tipoBookmark.equalsIgnoreCase(Costant.bookmarkTypeCortesiaLabel)) {
                if (isAnonymus) {
                    ((ProcessData) process.getData()).setTipoBookmark(Costant.bookmarkTypeCortesiaLabel);
                } else {
                    ((ProcessData) process.getData()).setTipoBookmark(Costant.bookmarkTypeCortesiaLabel);
                }
            } else {
                ((ProcessData) process.getData()).setTipoBookmark(Costant.bookmarkTypeLivello2Label);
            }
        } else {
            settaParametriModelloUnico(process, session);
        }
    }

//	private void configuraRicevutaPagamenti(ProcessData dataForm, ProcedimentoUnicoDAO delegate) throws Exception {
//		if (!dataForm.isAttivaPagamenti() && dataForm.getRiepilogoOneri().getTotale()>0){
//			Set setProcedimentiKey = dataForm.getListaProcedimenti().keySet();
//			for (Iterator iterator = setProcedimentiKey.iterator(); iterator.hasNext();) {
//				String key = (String) iterator.next();
//				ProcedimentoBean procedimento = (ProcedimentoBean) dataForm.getListaProcedimenti().get(key);
//				if (procedimentoConOneriAnticipati(procedimento, dataForm)){
//					for (Iterator iterator2 = procedimento.getCodInterventi().iterator(); iterator2.hasNext();) {
//						String codInt = (String) iterator2.next();
//						boolean trovato = false;
//						Iterator itInterventi = dataForm.getInterventi().iterator();
//						while (itInterventi.hasNext() && !trovato){
//							InterventoBean intervento = (InterventoBean)itInterventi.next();
//							boolean saltaIntervento=false;
//							for (Iterator iterator3 = intervento.getListaCodiciAllegati().iterator(); iterator3.hasNext();) {
//								String codAll = (String) iterator3.next();
//								if (codAll!=null && codAll.equalsIgnoreCase("ALLPAG")){
//									saltaIntervento=true;
//								}
//							}
//							if (intervento.getCodice().equalsIgnoreCase(codInt) && !saltaIntervento){
//								trovato=true;
//								intervento.getListaCodiciAllegati().add("ALLPAG");
//							}
//						}
//						if (!trovato) {
//							Iterator itInterventiF = dataForm.getInterventiFacoltativi().iterator();
//							while (itInterventiF.hasNext() && !trovato){
//								InterventoBean intervento = (InterventoBean)itInterventiF.next();
//								boolean saltaIntervento=false;
//								for (Iterator iterator3 = intervento.getListaCodiciAllegati().iterator(); iterator3.hasNext();) {
//									String codAll = (String) iterator3.next();
//									if (codAll!=null && codAll.equalsIgnoreCase("ALLPAG")){
//										saltaIntervento=true;
//									}
//								}
//								if (intervento.getCodice().equalsIgnoreCase(codInt) && !saltaIntervento){
//									trovato=true;
//									intervento.getListaCodiciAllegati().add("ALLPAG");
//								}
//							}
//						}
//					}
//				}
//			}
//			AllegatoBean allPag = delegate.getAllegatoRicevutaPagamento();
//			dataForm.getListaAllegati().put("ALLPAG", allPag);
//			DocumentoBean docPag = delegate.getDocumentoRicevutaPagamento();
//			dataForm.getListaDocRichiesti().put("ALLPAG",docPag);
//		}
//	}
    private void precompilaCampiAltriRichiedenti(AnagraficaBean anagrafica, ArrayList anagraficaCampi, String proteggiAltriDichiaranti, HashMap<String,String> forzaInQualita) {
        for (Iterator iterator = anagraficaCampi.iterator(); iterator.hasNext();) {
            HrefCampiBean campo = (HrefCampiBean) iterator.next();
            if (campo.getFlg_precompilazione() != null && campo.getFlg_precompilazione().equalsIgnoreCase("S")) {
                Iterator it2 = anagrafica.getListaCampi().iterator();
                boolean trovato = false;
                while (!trovato && it2.hasNext()) {
                    HrefCampiBean campoAnag = (HrefCampiBean) it2.next();
                    if (campoAnag.getNome().equalsIgnoreCase(campo.getNome()) && campoAnag.getContatore().equalsIgnoreCase(campo.getContatore())) {
                        campo.setValoreUtente(campoAnag.getValoreUtente());
// PC - Anagrafica altri richiedenti inizio     
                        if (proteggiAltriDichiaranti != null && "true".equalsIgnoreCase(proteggiAltriDichiaranti)) {
                            campo.setEdit("N");
                        }
// PC - Anagrafica altri richiedenti fine                                               
                        trovato = true;
                    }
                }
            }
            if (forzaInQualita.get(campo.getNome()) != null && forzaInQualita.get(campo.getNome()).equals(campo.getValore())) {
                if (campo.getTipo().equalsIgnoreCase("R")) {
                    for (Iterator it = anagraficaCampi.iterator(); it.hasNext();) {
                        HrefCampiBean c = (HrefCampiBean) it.next();
                        if (c.getNome().equals(campo.getNome())) {
                            if (c.getValore().equals(campo.getValore())) {
                                c.setValoreUtente(c.getValore());
                                c.setEdit("S");
                            } else {
                                c.setEdit("N");
                            }
                        }
                    }
                }
            }
        }
    }

    private void svuotaCampiAltriRichiedenti(ArrayList anagraficaCampi) {
        for (Iterator iterator = anagraficaCampi.iterator(); iterator.hasNext();) {
            HrefCampiBean campo = (HrefCampiBean) iterator.next();
            if (campo.getFlg_precompilazione() != null && campo.getFlg_precompilazione().equalsIgnoreCase("S")) {
                campo.setValoreUtente("");
            }
        }
    }

    private void settaDescrizionePratica(ProcessData dataForm) {
        String descrizionePratica = "";
        Set set = dataForm.getListaProcedimenti().keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            ProcedimentoBean proc = (ProcedimentoBean) dataForm.getListaProcedimenti().get(key);
            if (proc != null) {
                descrizionePratica += proc.getNome();
                if (iterator.hasNext()) {
                    descrizionePratica += "<br />";
                }
            }
        }
        //dataForm.setDescrizionePratica(descrizionePratica);
        ManagerAttributiNuoviFramework manf = new ManagerAttributiNuoviFramework();
        manf.settaAttributoProcessData(dataForm, "descrizionePratica", String.class, descrizionePratica);
    }

    private void aggiornaInfoAltriRichiedenti(ProcessData dataForm) {
        HashMap tmp = new HashMap();
        String codFiscRic = getCampoDinamicoAnagrafica(dataForm.getAnagrafica().getListaCampi(), "ANAG_CODFISC_DICHIARANTE");
        if (codFiscRic != null && !codFiscRic.equalsIgnoreCase("")) {
            tmp.put(codFiscRic, "");
        }
        ArrayList listaTitolari = new ArrayList();
        for (Iterator iterator = dataForm.getAltriRichiedenti().iterator(); iterator.hasNext();) {
            AnagraficaBean anag = (AnagraficaBean) iterator.next();
            if (anag.isFineCompilazione()) {
                Titolare tit = new Titolare();
                PersonaFisica pf = null;
                PersonaGiuridica pg = null;
                Titolare tit2 = new Titolare();
                PersonaFisica pf2 = null;
                PersonaGiuridica pg2 = null;
                String inQualitaDi = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_DICHIARANTE_QUALITA_1");
                if (inQualitaDi != null && !inQualitaDi.equalsIgnoreCase("")) {
                    pg = new PersonaGiuridica();
                    String denominazione = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_RAPPSOC_DENOM");
                    pg.setDenominazione(Utilities.NVL(denominazione, ""));
                    String piva = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_RAPPSOC_PIVA");
                    if (piva == null || piva.equalsIgnoreCase("")) {
                        piva = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_RAPPSOC_CODFISC");
                    }
                    pg.setPartitaIVA((Utilities.NVL(piva, "")).toUpperCase());
                } else {
                    inQualitaDi = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_DICHIARANTE_QUALITA_2");
                    if (inQualitaDi != null && !inQualitaDi.equalsIgnoreCase("")) {
                        pf = new PersonaFisica();
                        String nome = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_DICHIARANTE_NOME");
                        pf.setNome(Utilities.NVL(nome, ""));
                        String cognome = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_DICHIARANTE_COGNOME");
                        pf.setCognome(Utilities.NVL(cognome, ""));
                        String codFisc = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_CODFISC_DICHIARANTE");
                        pf.setCodiceFiscale(Utilities.NVL(codFisc, ""));
                    } else {
                        inQualitaDi = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_DICHIARANTE_QUALITA_3");
                        if (inQualitaDi != null && !inQualitaDi.equalsIgnoreCase("")) {
                            pf = new PersonaFisica();
                            String nome = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_DICHIARANTE_NOME");
                            pf.setNome(Utilities.NVL(nome, ""));
                            String cognome = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_DICHIARANTE_COGNOME");
                            pf.setCognome(Utilities.NVL(cognome, ""));
                            String codFisc = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_CODFISC_DICHIARANTE");
                            pf.setCodiceFiscale(Utilities.NVL(codFisc, ""));
                        } else {
                            inQualitaDi = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_DICHIARANTE_QUALITA_4");
                            if (inQualitaDi != null && !inQualitaDi.equalsIgnoreCase("")) {
                                pg = new PersonaGiuridica();
                                String denominazione = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_RAPPENTE_DENOM");
                                pg.setDenominazione(Utilities.NVL(denominazione, ""));
                                String piva = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_RAPPENTE_PIVA");
                                if (piva == null || piva.equalsIgnoreCase("")) {
                                    piva = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_RAPPENTE_CODFISC");
                                }
                                pg.setPartitaIVA(Utilities.NVL(piva.toUpperCase(), ""));
                            } else {
                                inQualitaDi = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_DICHIARANTE_QUALITA_5");
                                if (inQualitaDi != null && !inQualitaDi.equalsIgnoreCase("")) {
                                    pf = new PersonaFisica();
                                    String nome = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_DICHIARANTE_NOME");
                                    pf.setNome(Utilities.NVL(nome, ""));
                                    String cognome = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_DICHIARANTE_COGNOME");
                                    pf.setCognome(Utilities.NVL(cognome, ""));
                                    String codFisc = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_CODFISC_DICHIARANTE");
                                    pf.setCodiceFiscale(Utilities.NVL(codFisc, ""));
                                    // devo controllare/inserire anche  PF o PG per cui sta lavorando
                                    String radioButtonPf = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_PROF_PERSFISICA");
                                    String radioButtonPg = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_PROF_PERSGIURIDICA");
                                    if (radioButtonPf != null && !radioButtonPf.equalsIgnoreCase("")) {
                                        pf2 = new PersonaFisica();
                                        String nome2 = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_PFISICA_NOME");
                                        pf2.setNome(Utilities.NVL(nome2, ""));
                                        String cognome2 = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_PFISICA_COGNOME");
                                        pf2.setCognome(Utilities.NVL(cognome2, ""));
                                        String codFisc2 = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_PFISICA_CODFISC");
                                        pf2.setCodiceFiscale(Utilities.NVL(codFisc2, ""));
                                    } else if (radioButtonPg != null && !radioButtonPg.equalsIgnoreCase("")) {
                                        pg2 = new PersonaGiuridica();
                                        String denominazione2 = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_PGIURIDICA_DENOM");
                                        pg2.setDenominazione(Utilities.NVL(denominazione2, ""));
                                        String piva2 = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_PGIURIDICA_PIVA");
                                        if (piva2 == null || piva2.equalsIgnoreCase("")) {
                                            piva2 = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_PGIURIDICA_CODFISC");
                                        }
                                        pg2.setPartitaIVA(Utilities.NVL(piva2.toUpperCase(), ""));
                                    }
                                } else {
                                    inQualitaDi = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_DICHIARANTE_QUALITA_6");
                                    if (inQualitaDi != null && !inQualitaDi.equalsIgnoreCase("")) {
                                        pf = new PersonaFisica();
                                        String nome = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_DICHIARANTE_NOME");
                                        pf.setNome(Utilities.NVL(nome, ""));
                                        String cognome = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_DICHIARANTE_COGNOME");
                                        pf.setCognome(Utilities.NVL(cognome, ""));
                                        String codFisc = getCampoDinamicoAnagrafica(anag.getListaCampi(), "ANAG_CODFISC_DICHIARANTE");
                                        pf.setCodiceFiscale(Utilities.NVL(codFisc, ""));
                                    }
                                }
                            }
                        }
                    }
                }
                if (pf != null) {
                    if (!tmp.containsKey(pf.getCodiceFiscale())) {
                        tmp.put(pf.getCodiceFiscale().toUpperCase(), "");
                        tit.setPersonaFisica(pf);
                        listaTitolari.add(tit);
                    }
                } else if (pg != null) {
                    if (!tmp.containsKey(pg.getPartitaIVA())) {
                        tmp.put(pg.getPartitaIVA().toUpperCase(), "");
                        tit.setPersonaGiuridica(pg);
                        listaTitolari.add(tit);
                    }
                }

                if (pf2 != null) {
                    if (!tmp.containsKey(pf2.getCodiceFiscale())) {
                        tmp.put(pf2.getCodiceFiscale().toUpperCase(), "");
                        tit2.setPersonaFisica(pf2);
                        listaTitolari.add(tit2);
                    }
                } else if (pg2 != null) {
                    if (!tmp.containsKey(pg2.getPartitaIVA())) {
                        tmp.put(pg2.getPartitaIVA().toUpperCase(), "");
                        tit2.setPersonaGiuridica(pg2);
                        listaTitolari.add(tit2);
                    }
                }
            }
        }
        // dataForm.setAltriDichiaranti(listaTitolari);
        ManagerAttributiNuoviFramework manf = new ManagerAttributiNuoviFramework();
        manf.settaAttributoProcessData(dataForm, "altriDichiaranti", ArrayList.class, listaTitolari);
    }

    private void setOneriAnticipati(ProcessData dataForm) {
        Set oneriAnticipati = dataForm.getOneriAnticipati();
        if (!oneriAnticipati.isEmpty()) {
            RiepilogoOneriPagati riepilogoOneriPagati = new RiepilogoOneriPagati();
            double totaleOneri = (new Double(0.0D)).doubleValue();
            OnereBean onereBean;
            for (Iterator oneriAnticipatiIterator = oneriAnticipati.iterator(); oneriAnticipatiIterator.hasNext(); riepilogoOneriPagati.addOneri(onereBean)) {
                OneriBean oneriBean = (OneriBean) oneriAnticipatiIterator.next();
                totaleOneri += oneriBean.getImporto();
                double importoOnere = oneriBean.getImporto();
                double bufferImporto = Math.round(oneriBean.getImporto() * 100D);
                int importoInt = (new Double(bufferImporto)).intValue();
                Long valore = new Long(importoInt);
                String codiceOnere = oneriBean.getCodice();
                String codDestinatario = oneriBean.getCodDestinatario();
                String desDestinatario = oneriBean.getDesDestinatario();
                String desOnere = oneriBean.getDescrizione();
                String aeCodiceUtente = oneriBean.getAe_codice_utente();
                String aeCodiceEnte = oneriBean.getAe_codice_ente();
                String aeTipoUfficio = oneriBean.getAe_tipo_ufficio();
                String aeCodiceUfficio = oneriBean.getAe_codice_ufficio();
                boolean riversamentoAutomaticoOnere = oneriBean.isRiversamentoAutomatico();
// PC - Oneri MIP - inizio                             
                String identificativoContabileOnere = oneriBean.getIdentificativoContabile();
// PC - Oneri MIP - fine  
                if (dataForm.isOneriCalcolatiPresent() && oneriBean.getCampoFormula() != null && !oneriBean.getCampoFormula().equalsIgnoreCase("") && codDestinatario == null && desDestinatario == null) {
                    for (Iterator listaAlberoOneriIterator = dataForm.getListaAlberoOneri().iterator(); listaAlberoOneriIterator.hasNext();) {
                        OneriBean oneriBeanLista = (OneriBean) listaAlberoOneriIterator.next();
                        if (oneriBeanLista.getCodiceAntenato().equalsIgnoreCase(oneriBean.getCodiceAntenato())) {
                            codiceOnere = oneriBeanLista.getCodice();
                            codDestinatario = oneriBeanLista.getCodDestinatario();
                            desDestinatario = oneriBeanLista.getDesDestinatario();
                            desOnere = oneriBeanLista.getDescrizione();
                            aeCodiceUtente = oneriBeanLista.getAe_codice_utente();
                            aeCodiceEnte = oneriBeanLista.getAe_codice_ente();
                            aeTipoUfficio = oneriBeanLista.getAe_tipo_ufficio();
                            aeCodiceUfficio = oneriBeanLista.getAe_codice_ufficio();
                            riversamentoAutomaticoOnere = oneriBeanLista.isRiversamentoAutomatico();
// PC - Oneri MIP - inizio                             
                            identificativoContabileOnere = oneriBeanLista.getIdentificativoContabile();
// PC - Oneri MIP - fine 
                            break;
                        }
                    }

                }
                if (!riversamentoAutomaticoOnere) {
                    aeCodiceUtente = "";
                    aeCodiceEnte = "";
                    aeTipoUfficio = "";
                    aeCodiceUfficio = "";
                }
                onereBean = new OnereBean();
                onereBean.setCodice(codiceOnere);
                onereBean.setAeCodiceUtente(aeCodiceUtente);
                onereBean.setAeCodiceEnte(aeCodiceEnte);
                onereBean.setAeCodiceUfficio(aeCodiceUfficio);
                onereBean.setAeTipoUfficio(aeTipoUfficio);
                onereBean.setCodiceDestinatario(codDestinatario);
                onereBean.setDescrizione(desOnere);
                onereBean.setDescrizioneDestinatario(desDestinatario);
                onereBean.setImporto(importoOnere);
                onereBean.setRiversamentoAutomatico(riversamentoAutomaticoOnere);
// PC - Oneri MIP - inizio                
                onereBean.setIdentificativoContabile(identificativoContabileOnere);
// PC - Oneri MIP - fine 
            }

            riepilogoOneriPagati.setTotale(totaleOneri);
            dataForm.setRiepilogoOneriPagati(riepilogoOneriPagati);
        }
    }

    private void rigeneraAltriRichiedenti(ProcessData dataForm, IRequestWrapper request, String proteggiAltriDichiaranti, HashMap<String,String> forzaInQualita) {
        if (proteggiAltriDichiaranti != null && "true".equalsIgnoreCase(proteggiAltriDichiaranti)) {
            ManagerAnagraficaAltroRichiedente ma = new ManagerAnagraficaAltroRichiedente();
            for (Iterator it = dataForm.getAltriRichiedenti().iterator(); it.hasNext();) {
                AnagraficaBean altri = (AnagraficaBean) it.next();
                AnagraficaBean origine = dataForm.getAnagrafica();
                precompilaCampiAltriRichiedenti(origine, altri.getListaCampi(), proteggiAltriDichiaranti, forzaInQualita);
                String htmlIniziale__ = ma.getHtmlNonCompilabileInizialeAltroRichiedente(altri, request, Integer.parseInt(altri.getCodice()));
                altri.setHtmlStepAttuale(htmlIniziale__);
            }
        }
    }

    private HashMap<String, String> trasformaInQualita(String forzaInQualitaAltriDichiaranti) {
        HashMap<String, String> ret = new HashMap<String, String>();
        if (forzaInQualitaAltriDichiaranti != null && !"".equals(forzaInQualitaAltriDichiaranti)) {
            String[] s = forzaInQualitaAltriDichiaranti.split("\\|\\|");
            for (int i = 0; i < s.length; i++) {
                String codice = null;
                String valore = null;
                String[] s1 = s[i].split("codice=");
                if (s1.length > 0) {
                    String[] s2 = s1[1].split("valore=");
                    if (s2.length > 0) {
                        codice = s2[0].trim();
                        valore = s2[1].trim();
                    }
                }
                if (codice != null) {
                    ret.put(codice, valore);
                }
            }
        }
        return ret;

    }

}
