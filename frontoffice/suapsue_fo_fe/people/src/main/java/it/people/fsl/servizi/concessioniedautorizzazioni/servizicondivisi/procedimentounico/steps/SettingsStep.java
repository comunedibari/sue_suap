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

import it.gruppoinit.commons.Utilities;
import it.people.IValidationErrors;
import it.people.core.PeopleContext;
import it.people.core.PplUser;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.ProcedimentoUnicoDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AnagraficaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ImportBookmarkBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.IntermediariBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ParametriPUBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoUnicoException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Bean2XML;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Costant;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.ManagerImportRavenna;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.ManagerIntermediari;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.ManagerWorkflow;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.XPathReader;
import it.people.fsl.servizi.oggetticondivisi.IdentificatorePeople;
import it.people.fsl.servizi.oggetticondivisi.IdentificatorediProtocollo;
import it.people.fsl.servizi.oggetticondivisi.PersonaFisica;
import it.people.fsl.servizi.oggetticondivisi.Richiedente;
import it.people.fsl.servizi.oggetticondivisi.Titolare;
import it.people.fsl.servizi.oggetticondivisi.personagiuridica.PersonaGiuridica;
import it.people.fsl.servizi.oggetticondivisi.personagiuridica.Sede;
import it.people.fsl.servizi.oggetticondivisi.profili.AccreditamentoCorrente;
import it.people.fsl.servizi.oggetticondivisi.profili.ProfiloTitolare;
import it.people.fsl.servizi.oggetticondivisi.profili.Session2AbstractDataProfileHelper;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.sirac.accr.beans.AbstractProfile;
import it.people.sirac.accr.beans.ProfiloPersonaFisica;
import it.people.sirac.accr.beans.ProfiloPersonaGiuridica;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.core.SiracHelper;
import it.people.util.IdentificatoreUnivoco;
import it.people.util.ServiceParameters;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;

public class SettingsStep extends BaseStep{

	public void service(AbstractPplProcess process, IRequestWrapper request) {
		try {
			if (initialise(process,request)) {
				logger.debug("SettingsStep - service method");
				ProcessData dataForm = (ProcessData) process.getData();				
				resetError(dataForm);
				
				
				
				if (dataForm.getIdBookmark()==null) {
					
					// E' stato richiamato un bookmark dalla index.jsp
		        	session.setAttribute("byPassStepSceltaComune", null);
					String idBookmark = request.getUnwrappedRequest().getParameter("idBookmark");
					if (idBookmark != null) {
						String codEnte = Utilities.NVL(request.getUnwrappedRequest().getParameter("codEnte"),"");
						String codEveVita = Utilities.NVL(request.getUnwrappedRequest().getParameter("codEveVita"),"");
					
						session.setAttribute("parametriBookmark","&idBookmark="+idBookmark+"&codEnte="+codEnte+"&codEveVita="+codEveVita);
					
						Titolare titolareTemp = ((ProcessData) process.getData()).getTitolare();
						ProfiloTitolare ptTemp = ((ProcessData) process.getData()).getProfiloTitolare();
						it.people.fsl.servizi.oggetticondivisi.profili.ProfiloPersonaFisica poTemp = ((ProcessData) process.getData()).getProfiloOperatore();
						AccreditamentoCorrente acTemp = ((ProcessData) process.getData()).getAccreditamentoCorrente();
	                    AnagraficaBean anag = ((ProcessData) process.getData()).getAnagrafica();
	                    Richiedente richiedenteTemp = ((ProcessData) process.getData()).getRichiedente();
	                    it.people.fsl.servizi.oggetticondivisi.profili.ProfiloPersonaFisica ppfTemp = ((ProcessData) process.getData()).getProfiloRichiedente();
	                    IdentificatorediProtocollo idProtTemp = ((ProcessData) process.getData()).getIdentificatorediProtocollo();
	                    IdentificatorePeople idPeopleTemp = ((ProcessData) process.getData()).getIdentificatorePeople();
	                    IdentificatoreUnivoco idUnivocoTemp = ((ProcessData) process.getData()).getIdentificatoreUnivoco();

	                    initBookmark(process, request, idBookmark);
					
						// ------------ patch messa per il bug nella serializzazione/deserializzazione
						if(((ProcessData) process.getData()).getListaHref()!=null && ((ProcessData) process.getData()).getListaHref().size()>0){
				        	String userID = (String)session.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER);
				        	boolean isAnonymus = SiracHelper.isAnonymousUser(userID);
							ProcedimentoUnicoDAO delegate2 = new ProcedimentoUnicoDAO(db, language);
// PC - Reiterazione domanda inizio                                                        
//							delegate2.getDichiarazioniDinamiche(((ProcessData) process.getData()));
//							delegate2.getDettaglioDichiarazioniDinamiche(((ProcessData) process.getData()),isAnonymus);
							delegate2.getDichiarazioniDinamiche(((ProcessData) process.getData()), isAnonymus);
// PC - Reiterazione domanda fine                                                        
						}
						//-------------------- fine patch
					
	                    ((ProcessData) process.getData()).setTitolare(titolareTemp);
	                    ((ProcessData) process.getData()).setProfiloTitolare(ptTemp);
	                    ((ProcessData) process.getData()).setProfiloOperatore(poTemp);
	                    ((ProcessData) process.getData()).setAccreditamentoCorrente(acTemp);
	                    ((ProcessData) process.getData()).setAnagrafica(anag);
	                    ((ProcessData) process.getData()).setRichiedente(richiedenteTemp);
	                    ((ProcessData) process.getData()).setProfiloRichiedente(ppfTemp);
	                    ((ProcessData) process.getData()).setIdentificatorediProtocollo(idProtTemp);
	                    ((ProcessData) process.getData()).setIdentificatorePeople(idPeopleTemp);
	                    ((ProcessData) process.getData()).setIdentificatoreUnivoco(idUnivocoTemp);
	                    ((ProcessData) process.getData()).setLivelloSceltaMinOp(((ProcessData) process.getData()).getLivelloSceltaOp());
	                    ((ProcessData) process.getData()).setLivelloSceltaMinSettore(((ProcessData) process.getData()).getLivelloSceltaSettore());
	                    
						((ProcessData) process.getData()).setIdBookmark(idBookmark);
						((ProcessData) process.getData()).setInfomativaPrivacy(false);
						((ProcessData) process.getData()).getAnagrafica().setInitialized(false);
						((ProcessData) process.getData()).setAltriRichiedenti(new ArrayList());
					
					
						// check attivazione intermediari
						ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
						String intermed = params.get("abilita_intermediari");
						if (intermed!=null && intermed.equalsIgnoreCase("true")) {
							IntermediariBean intermediari = ManagerIntermediari.buildBeanIntermediari(request);
							((ProcessData) process.getData()).setIntermediari(intermediari);
						}
						
						// check lista d'accesso
						PplUser peopleUser = PeopleContext.create(request.getUnwrappedRequest()).getUser();
						ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
						boolean ok = delegate.checkAccessList(process,idBookmark,peopleUser.getUserData().getCodiceFiscale());
						if (!ok){
				        	request.setAttribute("bookmarkAccessListLock", "true");
				        	process.getView().setBottomNavigationBarEnabled(false);
				        	process.getView().setBottomSaveBarEnabled(false);
				        	showJsp(process, "bandi_error.jsp", false);
				        	return;
						}
						
						// check intervallo di validita
						if (!isBandoValido(idBookmark,request)) {
				        	request.setAttribute("bookmarkNonDisponibile", "true");
				        	process.getView().setBottomNavigationBarEnabled(false);
				        	process.getView().setBottomSaveBarEnabled(false);
				        	showJsp(process, "bandi_error.jsp", false);
				        	return;
						} else {
							Date df = delegate.getDataScadenza(idBookmark);
							if (df!=null){request.setAttribute("dataScadenzaBandi", (new SimpleDateFormat("dd/MM/yyyy")).format(df));}
							Date di = delegate.getDataInizioValidita(idBookmark);
							if (di!=null){request.setAttribute("dataInizioBandi", (new SimpleDateFormat("dd/MM/yyyy")).format(di));}
						}
						
						// check attivazione funzionalità bandi
						String bandiAttiviString = params.get("bandi");
						if (bandiAttiviString!=null && bandiAttiviString.equalsIgnoreCase("true")){
							if (delegate.bookmarkIsBando(idBookmark)  ) {
								((ProcessData) process.getData()).setBandiAttivi(true);
							} else {
								((ProcessData) process.getData()).setBandiAttivi(false);
							}
						} else {
							((ProcessData) process.getData()).setBandiAttivi(false);
						}
						
						// Aggiornamento impostazioni servizio
						((ProcessData) process.getData()).setEmbedAttachmentInXml(process.getServiceConf().isEmbedAttachmentInXml() );
						((ProcessData) process.getData()).setEnabledAuditProcessors(process.getServiceConf().getEnabledProcessors());
						((ProcessData) process.getData()).setPrivacyDisclaimerRequireAcceptance(process.getServiceConf().isPrivacyDisclaimerRequireAcceptance());
						((ProcessData) process.getData()).setReceiptMailAttachment(process.getServiceConf().isReceiptMailAttachment());
						((ProcessData) process.getData()).setSendMailToOwner(process.getServiceConf().isSendMailToOwner());
						((ProcessData) process.getData()).setShowPrivacyDisclaimer(process.getServiceConf().isShowPrivacyDisclaimer());
						
						
						loadParametriModelloUnico(process);	
		            } else {
		            	settaParametriModelloUnico(process,session);
		            	// CCD - Modifica 09.02.2012 - bug per cui a fronte della selezione di un bookmark
		            	// all'accesso successivo senza bookmark non viene più presentata la scelta del comune
			        	session.setAttribute("byPassStepSceltaComune", null);

                                        
		            }
					
					
				} else {

					// if (((ProcessData) process.getData()).isBandiAttivi() ){
						ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
						Date df = delegate.getDataScadenza(((ProcessData) process.getData()).getIdBookmark());
						if (df!=null){request.setAttribute("dataScadenzaBandi", (new SimpleDateFormat("dd/MM/yyyy")).format(df));}
						Date di = delegate.getDataInizioValidita(((ProcessData) process.getData()).getIdBookmark());
						if (di!=null){request.setAttribute("dataInizioBandi", (new SimpleDateFormat("dd/MM/yyyy")).format(di));}
					// }
				}
//				String nuovaGestioneFile = request.getUnwrappedRequest().getSession().getServletContext().getInitParameter("remoteAttachFile");
//		    	if (nuovaGestioneFile!=null && nuovaGestioneFile.equalsIgnoreCase("true")){
		    	if (!process.isEmbedAttachmentInXml()){
		    		((ProcessData) process.getData()).setRemoteAttachFile("true");
		    	} else {
		    		((ProcessData) process.getData()).setRemoteAttachFile("false");
		    	}
				((ProcessData) process.getData()).getDatiTemporanei().setEmailRichiedente((String)((ProcessData) process.getData()).getRichiedente().getUtenteAutenticato().getRecapito().get(0));
				
				PplUser peopleUser = PeopleContext.create(request.getUnwrappedRequest()).getUser();
				if (!peopleUser.isAnonymous()){
					((ProcessData) process.getData()).setDomicilioElettronico(Session2AbstractDataProfileHelper.getDomicilioElettronicoFromSession(session));
					String codFis = ((ProcessData) process.getData()).getRichiedente().getUtenteAutenticato().getCodiceFiscale();
					if (((ProcessData) process.getData()).getRichiedente().getUtenteAutenticato().getRecapito().size()>1){
						((ProcessData) process.getData()).setNteldic((String)((ProcessData) process.getData()).getRichiedente().getUtenteAutenticato().getRecapito().get(1));
					}

					ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
//					boolean trovato = false;
					
		            ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
		            boolean amministratoreBookmark = delegate.isParametroConfigurazionePresent(process.getCommune().getOid(), "amministratoreBookmark", codFis);
		            boolean amministratorePU = delegate.isParametroConfigurazionePresent(process.getCommune().getOid(), "amministratorePU", codFis);
		            boolean amministratoreStampe = delegate.isParametroConfigurazionePresent(process.getCommune().getOid(), "amministratoreStampe", codFis);
		            peopleUser.addExtendedAttribute(Costant.PplUser.AMMINISTRATORE_BOOKMARK, new Boolean(amministratoreBookmark));
		            peopleUser.addExtendedAttribute(Costant.PplUser.AMMINISTRATORE_PROCEDIMENTO_UNICO, new Boolean(amministratorePU));
		            peopleUser.addExtendedAttribute(Costant.PplUser.AMMINISTRATORE_STAMPE, new Boolean(amministratoreStampe));
//		            if (trovato){
//		            	peopleUser.setPeopleAdmin(true);
//		            } else {
//		            	peopleUser.setPeopleAdmin(false);
//		            }
					
					// sezione inserita per permettere l'importazione dei bookmark di ravenna
					String importazione = params.get("importRA");
					if (importazione!=null && importazione.equalsIgnoreCase("true")){
						request.setAttribute("importRA", "importRA");
					}
					// fine sezione di import 
				}
				
				
				if (dataForm.getIdBookmark()==null && session!=null) {
					ManagerWorkflow mw = new ManagerWorkflow();
					mw.updateWorkflow1(session, request, process, dataForm, db, language);
				}
				
				if (dataForm != null) {
					dataForm.setEntePeopleKey(process.getCommune().getKey());
				}
				
			} else {
				throw new ProcedimentoUnicoException("Sessione scaduta");
			}
			logger.debug("SettingsStep - service method END");
		} catch (Exception e){
			gestioneEccezioni(process,0,e);
		}
	}
	
	private void loadParametriModelloUnico(AbstractPplProcess process) {
		ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
		String xmlPermessi ="";
		try {
			xmlPermessi = delegate.getXMLPermessiPU(((ProcessData)process.getData()).getIdentificatoreUnivoco().getCodiceSistema().getCodiceAmministrazione());
		} catch (Exception e){}
		XPathReader xpr = new XPathReader(xmlPermessi);
		String tipoBookmark = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/TYPE"),"");
		String tipoFirma = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/FIRMADIGITALE"),"");
		String tipoPagamenti = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/PAGAMENTO"),"");
		String modalitaPagamenti = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/MODALITA_PAGAMENTO"),"");
		String modalitaPagamentiOpzionali = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/MODALITA_PAGAMENTO_OPZIONALE"),"");
		String conInvioString = Utilities.NVL(xpr.readElementString("/CONFIGURAZIONE/CON_INVIO"),"");
		
		boolean conInvio = Utilities.checked(conInvioString.equalsIgnoreCase("")?"true":conInvioString);
		ParametriPUBean parametri = new ParametriPUBean();
		parametri.setConInvio(conInvio);
		parametri.setTipo(tipoBookmark);
		parametri.setPagamenti(tipoPagamenti);
		parametri.setModalitaPagamenti(modalitaPagamenti);
		parametri.setModalitaPagamentiOpzionali(modalitaPagamentiOpzionali);
		parametri.setAbilitaFirma(Utilities.checked(tipoFirma.equalsIgnoreCase("")?"true":tipoFirma));
		((ProcessData)process.getData()).getDatiTemporanei().setParametriPU(parametri);
	}
	


	private void riconfiguraAnagrafica(ProcessData dataForm) {
		AbstractProfile profiloTitolare=null;
		it.people.sirac.accr.beans.ProfiloPersonaFisica profiloRichiedente=null;
		profiloRichiedente = (it.people.sirac.accr.beans.ProfiloPersonaFisica) session.getAttribute("it.people.sirac.accr.profiloRichiedente");
		if (profiloRichiedente!=null){
			//dataForm.getRichiedente().getUtenteAutenticato().setCittadinanza(profiloRichiedente.get);
			dataForm.getRichiedente().getUtenteAutenticato().setCodiceFiscale(profiloRichiedente.getCodiceFiscale());
			dataForm.getRichiedente().getUtenteAutenticato().setCognome(profiloRichiedente.getCognome());
			try {
				dataForm.getRichiedente().getUtenteAutenticato().getDatadiNascita().setData(profiloRichiedente.getDataNascitaString(),"dd/MM/yyyy");
				dataForm.getDatiTemporanei().setDataNascitaRichiedente(profiloRichiedente.getDataNascitaString());
			} catch (Exception e){
				
			}
			// dataForm.getRichiedente().getUtenteAutenticato().setDomicilio(profiloRichiedente.getDomicilioElettronico());
			//currentLoggedUser
			String email = "";
			try {
				it.people.core.PplUser user = (it.people.core.PplUser)session.getAttribute("currentLoggedUser");
				if (user!=null){
					email = user.getEMail();
				}
			} catch (Exception e){}
			dataForm.getDatiTemporanei().setEmailRichiedente(email);
			//dataForm.getRichiedente().getUtenteAutenticato().setGradodiParentela(gradodiParentela);
			dataForm.getRichiedente().getUtenteAutenticato().getLuogodiNascita().getComune().setNome(profiloRichiedente.getLuogoNascita());
			dataForm.getRichiedente().getUtenteAutenticato().getLuogodiNascita().getComune().getProvincia().setNome(profiloRichiedente.getProvinciaNascita());
			dataForm.getRichiedente().getUtenteAutenticato().setNome(profiloRichiedente.getNome());
			//dataForm.getRichiedente().getUtenteAutenticato().setNomeSecondario(string);
			//dataForm.getRichiedente().getUtenteAutenticato().setNote();
			List lista = new ArrayList();
			lista.add(profiloRichiedente.getDomicilioElettronico());
			dataForm.getRichiedente().getUtenteAutenticato().setRecapito(lista);
			dataForm.getRichiedente().getUtenteAutenticato().getResidenza().setVia(profiloRichiedente.getIndirizzoResidenza());
			dataForm.getRichiedente().getUtenteAutenticato().setSesso(profiloRichiedente.getSesso());
			// dataForm.getRichiedente().getUtenteAutenticato().setTitoloPersonale();
		}
		profiloTitolare = (AbstractProfile)session.getAttribute("it.people.sirac.accr.profiloTitolare");
		if (profiloTitolare!=null) {
			if (profiloTitolare instanceof ProfiloPersonaFisica){
				
			} else {
				//dataForm.getTitolare().getPersonaGiuridica().setCodiceAmministrazione(((ProfiloPersonaGiuridica)profiloTitolare).get);
				try {
					if (dataForm.getTitolare()==null){
						dataForm.setTitolare(new Titolare());
					}
					if (dataForm.getTitolare().getPersonaGiuridica()==null){
						dataForm.getTitolare().setPersonaGiuridica(new PersonaGiuridica());
					}
					
				} catch (Exception e){
					e.printStackTrace();
				}
				dataForm.getTitolare().getPersonaGiuridica().setCodiceFiscale(((ProfiloPersonaGiuridica)profiloTitolare).getCodiceFiscale());
				dataForm.getTitolare().getPersonaGiuridica().setDenominazione(((ProfiloPersonaGiuridica)profiloTitolare).getDenominazione());
				//dataForm.getTitolare().getPersonaGiuridica().setIscrizioneallaCameradiCommercio(((ProfiloPersonaGiuridica)profiloTitolare).get);
				//dataForm.getTitolare().getPersonaGiuridica().setIscrizioneallAlboProfessionale(((ProfiloPersonaGiuridica)profiloTitolare).get);
				//dataForm.getTitolare().getPersonaGiuridica().setNote();
				dataForm.getTitolare().getPersonaGiuridica().setPartitaIVA(((ProfiloPersonaGiuridica)profiloTitolare).getPartitaIva());
				//dataForm.getTitolare().getPersonaGiuridica().setProcuratore();
				dataForm.getTitolare().getPersonaGiuridica().setRagioneSociale(((ProfiloPersonaGiuridica)profiloTitolare).getDenominazione());
				//dataForm.getTitolare().getPersonaGiuridica().setRappresentante(((ProfiloPersonaGiuridica)profiloTitolare).get);
				if (dataForm.getTitolare().getPersonaGiuridica().getRappresentanteLegale()==null){
					dataForm.getTitolare().getPersonaGiuridica().setRappresentanteLegale(new PersonaFisica());
				}
				if (dataForm.getTitolare().getPersonaGiuridica().getSedeLegale()==null){
					dataForm.getTitolare().getPersonaGiuridica().setSedeLegale(new Sede());
				}
				dataForm.getTitolare().getPersonaGiuridica().getRappresentanteLegale().setNome(((ProfiloPersonaGiuridica)profiloTitolare).getRappresentanteLegale().getNome());
				dataForm.getTitolare().getPersonaGiuridica().getRappresentanteLegale().setCognome(((ProfiloPersonaGiuridica)profiloTitolare).getRappresentanteLegale().getCognome());
				dataForm.getTitolare().getPersonaGiuridica().getRappresentanteLegale().setCodiceFiscale(((ProfiloPersonaGiuridica)profiloTitolare).getRappresentanteLegale().getCodiceFiscale());
				dataForm.getTitolare().getPersonaGiuridica().getSedeLegale().setIndirizzoTestuale(((ProfiloPersonaGiuridica)profiloTitolare).getSedeLegale());
				// dataForm.getTitolare().getPersonaGiuridica().setSedeOperativa();
				//dataForm.getTitolare().getPersonaGiuridica().setTipoPersonaGiuridica();
			}
		}
	}

	public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException, ServletException {
		logger.debug("SettingsStep - loopback method");
		if (propertyName!=null && propertyName.equalsIgnoreCase("importRavenna")) {
			ManagerImportRavenna mir = new ManagerImportRavenna(db,language);
			ArrayList lista = new ArrayList();
			try {
				lista = mir.getListaServiziDaImportare(process.getCommune().getOid());
			} catch (Exception e){}
			request.setAttribute("listaBookmark", lista);
			showJsp(process, "importRavenna.jsp", false);
		} else if (propertyName!=null && propertyName.equalsIgnoreCase("eseguiImport")){
			
			String tipoImport = (String)request.getParameter("tipo");
			String processDataAttuale="";
			processDataAttuale = Bean2XML.marshallPplData(process.getData(), request.getUnwrappedRequest().getCharacterEncoding());
			if (tipoImport!=null && tipoImport.equalsIgnoreCase("0")){ // importazione singolo bookmark
				ManagerImportRavenna mir = new ManagerImportRavenna(db,language);
				String codEnte = (String)request.getParameter("codice_comune");
				String codiceEventoVita = (String)request.getParameter("codice_evento_vita");
				String titoloServizio = (String)request.getParameter("titolo_servizio");
				String descServizio = (String)request.getParameter("desc_servizio");
				String xmlRavenna = (String)request.getParameter("xmlServizio");
				String configurazion = (String)request.getParameter("configurazione");
				
				try {
					String newXML = mir.buildSingoloBookmark(process,xmlRavenna);
					mir.saveSingoloBookmarkLibero(newXML,configurazion,codEnte,codiceEventoVita,titoloServizio,descServizio);
				}catch (Exception e){
					e.printStackTrace();
				}
				
			} else if (tipoImport!=null && tipoImport.equalsIgnoreCase("1")) {// importazione tutti i bookmark
				ArrayList listaBookmark = new ArrayList();
				ProcedimentoUnicoDAO deleg = new ProcedimentoUnicoDAO(db,language);
				listaBookmark = deleg.getListaBookmark();
				ManagerImportRavenna mir = new ManagerImportRavenna(db,language);
				for (Iterator iterator = listaBookmark.iterator(); iterator.hasNext();) {
					
					ImportBookmarkBean bookmark = (ImportBookmarkBean) iterator.next();
					String codEnte = bookmark.getCodEnte();
					String codiceEventoVita = bookmark.getCodEventoVita();
					String codiceServizio = bookmark.getCodServizio();
					String xmlRavenna = bookmark.getBookmark();
			    	String configuration ="<CONFIGURAZIONE>\n"+
									"	<TYPE_OPZ>COMPLETO|CORTESIA|LIVELLO2</TYPE_OPZ>\n"+
									"	<TYPE>COMPLETO</TYPE>\n"+
									"   <CON_INVIO_OPZ>TRUE|FALSE</CON_INVIO_OPZ>\n"+
									"   <CON_INVIO>FALSE</CON_INVIO>\n"+
									"	<FIRMADIGITALE_OPZ>TRUE|FALSE</FIRMADIGITALE_OPZ>\n"+
									"	<FIRMADIGITALE>TRUE</FIRMADIGITALE>\n"+
									"	<PAGAMENTO_OPZ>DISABILITA|FORZA_PAGAMENTO|OPZIONALE</PAGAMENTO_OPZ>\n"+
									"	<PAGAMENTO>OPZIONALE</PAGAMENTO>\n"+
									"</CONFIGURAZIONE>"; 
					try {
						String newXML = mir.buildSingoloBookmark(process,xmlRavenna);
						mir.saveSingoloBookmark(newXML,configuration,codEnte+"_"+codiceEventoVita+"_"+codiceServizio);
					}catch (Exception e){
						e.printStackTrace();
					}
					process.setMarshalledData(processDataAttuale);
				}
//				processDataAttuale = Bean2XML.marshallPplData(process.getData());
//				//ManagerImportRavenna mir = new ManagerImportRavenna(request,db,language,"",process);
//				try {
//					ImportRavennaDAO delegate2 = new ImportRavennaDAO(db,language);
//					delegate2.importaAll(request,language,process);
//				}catch (Exception e){
//					e.printStackTrace();
//				}
			} else if (tipoImport!=null && tipoImport.equalsIgnoreCase("2")) {
				ManagerImportRavenna mir = new ManagerImportRavenna(db,language);
				//String codEnte = (String)request.getParameter("codice_comune");
				//String codiceEventoVita = (String)request.getParameter("codice_evento_vita");
				//String codiceServizio = (String)request.getParameter("codice_servizio");
				String cod = (String)request.getParameter("cod_bookmark");
				String xmlRavenna2="";
				try {
					xmlRavenna2 = mir.getXMLServizioNET(cod);
				} catch (Exception e){
					e.printStackTrace();
				}
				String configurazion = (String)request.getParameter("configurazione");
				
				try {
					String newXML = mir.buildSingoloBookmark(process,xmlRavenna2);
					mir.saveSingoloBookmark(newXML,configurazion,cod);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
			
			
			
			
			// recupero process data attuale
			if (Utilities.isset(processDataAttuale)){
				process.setMarshalledData(processDataAttuale);
			}
			ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
			String importazione = params.get("importRA");
			if (importazione!=null && importazione.equalsIgnoreCase("true")){
				request.setAttribute("importRA", "importRA");
			}
			ManagerImportRavenna mir = new ManagerImportRavenna(db,language);
			ArrayList lista = new ArrayList();
			try {
				lista = mir.getListaServiziDaImportare(process.getCommune().getOid());
			} catch (Exception e){}
			request.setAttribute("listaBookmark", lista);
			showJsp(process, "importRavenna.jsp", false);
		} else {

			ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
			String importazione = params.get("importRA");
			if (importazione!=null && importazione.equalsIgnoreCase("true")){
				request.setAttribute("importRA", "importRA");
			}
			showJsp(process, "default_main.jsp", false);
		}
	}		
	
	public boolean logicalValidate(AbstractPplProcess process,IRequestWrapper request,IValidationErrors errors) throws ParserException {
		try {
			logger.debug("SettingsStep - logicalValidate method");
			ProcessData data = (ProcessData) process.getData();
			
			String javascriptDisabilitati = request.getParameter("javascript");
			if(javascriptDisabilitati != null){
				session.setAttribute("NOJAVASCRIPT", "TRUE");
			}
			String s = (String) request.getParameter("data.infomativaPrivacy");
			data.setInfomativaPrivacy(Utilities.checked(s));
	        if(!data.isInfomativaPrivacy()){
	            errors.add("error.accettazioneprivacy");
	            logger.debug("SettingsStep - logicalValidate method END");
	            ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
				Date df = delegate.getDataScadenza(((ProcessData) process.getData()).getIdBookmark());
				if (df!=null){request.setAttribute("dataScadenzaBandi", (new SimpleDateFormat("dd/MM/yyyy")).format(df));}
				Date di = delegate.getDataInizioValidita(((ProcessData) process.getData()).getIdBookmark());
				if (di!=null){request.setAttribute("dataInizioBandi", (new SimpleDateFormat("dd/MM/yyyy")).format(di));}
	            return false;
	        }
	        logger.debug("SettingsStep - logicalValidate method END");
			return true;		
		} catch (Exception e) {
			errors.add("error.generic","Errore interno");
			gestioneEccezioni(process,0,e);
			return false;
		}
	}
}
