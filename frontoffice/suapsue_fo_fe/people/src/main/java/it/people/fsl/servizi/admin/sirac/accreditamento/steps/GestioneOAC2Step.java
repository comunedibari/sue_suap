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
package it.people.fsl.servizi.admin.sirac.accreditamento.steps;

import it.people.Activity;
import it.people.ActivityState;
import it.people.DtoStep;
import it.people.IStep;
import it.people.IValidationErrors;
import it.people.StepState;
import it.people.core.PplUserData;
import it.people.core.exception.MappingException;
import it.people.fsl.servizi.admin.sirac.accreditamento.dto.ListaOperatoriDto;
import it.people.fsl.servizi.admin.sirac.accreditamento.dto.OperatoreDto;
import it.people.fsl.servizi.admin.sirac.accreditamento.model.ProcessData;
import it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.UtilHelper;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.process.PplData;
import it.people.process.dto.PeopleDto;
import it.people.sirac.accr.ProfiliHelper;
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.ProfiloLocale;
import it.people.sirac.accr.beans.ProfiloPersonaFisica;
import it.people.sirac.accr.beans.Qualifica2Persona;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.services.accr.IAccreditamentoClientAdapter;
import it.people.sirac.services.accr.management.IAccreditamentiManagementWS;
import it.people.sirac.services.accr.management.IAccreditamentiManagementWSServiceLocator;
import it.people.sirac.util.Utilities;
import it.people.wrappers.HttpServletRequestDelegateWrapperHelper;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.rpc.ServiceException;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;


public class GestioneOAC2Step extends DtoStep {
	

  final static String ACCR_ATTIVO = "1";
  final static String ACCR_DISATTIVO = "0";

  private static Logger logger = LoggerFactory.getLogger(GestioneOAC2Step.class.getName());

  public void service(AbstractPplProcess process, IRequestWrapper request) throws IOException, ServletException {
    
	super.service(process, request);
	HttpServletRequestDelegateWrapperHelper rwh = new HttpServletRequestDelegateWrapperHelper(request);
    
	HttpServletRequest unwrappedRequest = request.getUnwrappedRequest();
	HttpSession session = unwrappedRequest.getSession();

	ProcessData myData = (ProcessData) process.getData();

    try {
	    String idComune = process.getCommune().getOid();
	    
	    if(myData.getPplUser()==null)
	      myData.setPplUser(request.getPplUser());
	
		String codiceFiscaleUtente = UtilHelper.getCodiceFiscaleFromProfiloOperatore(request.getUnwrappedRequest().getSession());
	    
	    String IAccreditamentoURLString = 
	      SiracHelper.getIAccreditamentoServiceURLString(request.getUnwrappedRequest().getSession().getServletContext());
	    
	    // Imposta il tipo di operazione
	    //myData.setOperazione(ProcessData.GESTIONE_ACCR_OPER_GESTIONEOPERATORI);
	    
	    Accreditamento[] elencoAccreditamenti = myData.getElencoAccreditamenti();
	    
	    Accreditamento accrSelezionato = null;
	    if(myData.getSelezioneAccreditamentoIndex() == 999){
	    	accrSelezionato = (Accreditamento)session.getAttribute("accreditamentoRCT");
	    } else {
	    	accrSelezionato = elencoAccreditamenti[myData.getSelezioneAccreditamentoIndex()];
	    }
	    
	    String tipoQualificaAccrSel = accrSelezionato.getQualifica().getTipoQualifica();
	    
	    Qualifica2Persona[] elencoQualifiche2Persona = null;
	
	    ProfiliHelper profiliHelper= null;
	    
	    

	    // Recupero accreditamenti operatori
		String codiceFiscaleIntermediario = accrSelezionato.getProfilo().getCodiceFiscaleIntermediario();
		String partitaIvaIntermediario = accrSelezionato.getProfilo().getPartitaIvaIntermediario();
		
		IAccreditamentiManagementWS accrService = getAccrService(request);
		Accreditamento[] operatori = accrService.getOperatoriAssociazione(idComune, codiceFiscaleIntermediario, partitaIvaIntermediario, "0", "0");
		
		ListaOperatoriDto listaOperatori = (ListaOperatoriDto)process.getDto();
		
		for (Accreditamento accreditamento : operatori) {
			boolean deleted = accrService.isAccreditamentoDeleted(accreditamento.getId());
			OperatoreDto operatore = new OperatoreDto();
			operatore.accreditamentoToOperatore(accreditamento, deleted);
			listaOperatori.addListaOperatori(operatore);
		}
		
	    
		IAccreditamentoClientAdapter accr = new IAccreditamentoClientAdapter(IAccreditamentoURLString);
	    ProfiloLocale profilo = UtilHelper.getProfiloLocale(myData, accr, codiceFiscaleUtente, idComune);
	    if (profilo == null) {
	    	rwh.addSiracError("GestioneOAC2Step::Errore1", "Non esiste un profilo locale per l'utente " + codiceFiscaleUtente);
	    	updateWorkflow(process);
	    	return;
		}
	    
	    elencoQualifiche2Persona = accr.getQualifiche2Persona(tipoQualificaAccrSel);
	    myData.setElencoQualifiche2Persona(elencoQualifiche2Persona);
	      
	    PplUserData authUserData = myData.getPplUser().getUserData();
	      
	    ProfiloPersonaFisica profiloOperatore = new ProfiloPersonaFisica();
	    profiloOperatore.setNome(authUserData.getNome());
	    profiloOperatore.setCognome(authUserData.getCognome());
	    profiloOperatore.setCodiceFiscale(authUserData.getCodiceFiscale());
	    profiloOperatore.setDataNascita(Utilities.parseDateString(authUserData.getDataNascita()));
	    profiloOperatore.setLuogoNascita(authUserData.getLuogoNascita());
	    profiloOperatore.setProvinciaNascita(authUserData.getProvinciaNascita());
	    profiloOperatore.setSesso(authUserData.getSesso());
	    profiloOperatore.setIndirizzoResidenza(authUserData.getCittaResidenza() 
	        + "(" + authUserData.getProvinciaResidenza() + "), " + authUserData.getIndirizzoResidenza());
	    // FIX 2007-06-13 - Aggiunto email al ProfiloPersonaFisica, quindi vado a popolarlo
	    profiloOperatore.setDomicilioElettronico(authUserData.getEmailaddress());
	      
	    myData.setSelaccrOperatore(profiloOperatore);
	      
	    profiliHelper = 
	        new ProfiliHelper(elencoQualifiche2Persona, accrSelezionato, profiloOperatore);
	      
	    profiliHelper.setProfiloRichiedente(new ProfiloPersonaFisica());
	      
	    if (profiliHelper.getSceltaProfiloRichiedente().equals(ProfiliHelper.SCELTAPROFILO_PROFILOREGISTRAZIONE)) {
	    	ProfiliHelper.initProfiloPersonaFisica(myData.getSelaccrRichiedente(), profiloOperatore);
	        ProfiliHelper.initProfiloPersonaFisica(profiliHelper.getProfiloRichiedente(), myData.getSelaccrRichiedente());
	        profiliHelper.setProfiloRichiedenteDefined(true);
	    } else if (profiliHelper.getSceltaProfiloRichiedente().equals(ProfiliHelper.SCELTAPROFILO_PERSONAFISICA)) {
	        ProfiliHelper.initProfiloPersonaFisica(profiliHelper.getProfiloRichiedente(), myData.getSelaccrRichiedente());
	        profiliHelper.setProfiloRichiedenteDefined(false);
	    }
	
	    myData.setSelAccrProfiliHelper(profiliHelper);
	      
	    // imposta il titolare provvisoriamente uguale al richiedente/operatore
	    myData.setSelaccrTitolare(myData.getSelaccrRichiedente());
	    profiliHelper.setProfiloTitolare(myData.getSelaccrRichiedente());
	    profiliHelper.setProfiloTitolareDefined(true);  
	    profiliHelper.setSceltaProfiloTitolare(ProfiliHelper.SCELTAPROFILO_PERSONAFISICA);
	      
	    // salva i valori attuali dell'accreditamento in sessione
	    if(session.getAttribute(SiracConstants.SIRAC_ACCR_ACCRSEL) != null){
	    	session.setAttribute("PREV_"+SiracConstants.SIRAC_ACCR_ACCRSEL, session.getAttribute(SiracConstants.SIRAC_ACCR_ACCRSEL));
	    }
	    if(session.getAttribute(SiracConstants.SIRAC_ACCR_TIPOQUALIFICA) != null){
	    	session.setAttribute("PREV_"+SiracConstants.SIRAC_ACCR_TIPOQUALIFICA, session.getAttribute(SiracConstants.SIRAC_ACCR_TIPOQUALIFICA));
	    }
	    if(session.getAttribute(SiracConstants.SIRAC_ACCR_DESCRQUALIFICA) != null){
	    	session.setAttribute("PREV_"+SiracConstants.SIRAC_ACCR_DESCRQUALIFICA, session.getAttribute(SiracConstants.SIRAC_ACCR_DESCRQUALIFICA));
	    }
	    if(session.getAttribute(SiracConstants.SIRAC_ACCR_OPERATORE) != null){
	    	session.setAttribute("PREV_"+SiracConstants.SIRAC_ACCR_OPERATORE, session.getAttribute(SiracConstants.SIRAC_ACCR_OPERATORE));
	    }
	    if(session.getAttribute(SiracConstants.SIRAC_ACCR_RICHIEDENTE) != null){
	    	session.setAttribute("PREV_"+SiracConstants.SIRAC_ACCR_RICHIEDENTE, session.getAttribute(SiracConstants.SIRAC_ACCR_RICHIEDENTE));
	    }
	    if(session.getAttribute(SiracConstants.SIRAC_ACCR_TITOLARE) != null){
	    	session.setAttribute("PREV_"+SiracConstants.SIRAC_ACCR_TITOLARE, session.getAttribute(SiracConstants.SIRAC_ACCR_TITOLARE));
	    }
	      
	    // imposta le variabili di sessioni del SIRAC con l'accreditamento selezionato
	    session.setAttribute(SiracConstants.SIRAC_ACCR_ACCRSEL, accrSelezionato);
	    session.setAttribute(SiracConstants.SIRAC_ACCR_TIPOQUALIFICA, accrSelezionato.getQualifica().getTipoQualifica());
	    session.setAttribute(SiracConstants.SIRAC_ACCR_DESCRQUALIFICA, accrSelezionato.getQualifica().getDescrizione());
	
	    session.setAttribute(SiracConstants.SIRAC_ACCR_OPERATORE, myData.getSelaccrOperatore());
	    session.setAttribute(SiracConstants.SIRAC_ACCR_RICHIEDENTE, myData.getSelaccrRichiedente());
	    session.setAttribute(SiracConstants.SIRAC_ACCR_TITOLARE, myData.getSelaccrTitolare());
	    
	    
	      
    } catch (Exception ex) {
      rwh.addSiracError("GestioneOAC2Step::Service()::Eccezione1", ex.getMessage());
    } finally {
      updateWorkflow(process);
    }
  }

/**
 * @param request
 * @return
 * @throws ServiceException
 */
	private IAccreditamentiManagementWS getAccrService(IRequestWrapper request)
			throws ServiceException {

		String IAccreditamentiManagementURLString = request.getUnwrappedRequest().getSession().getServletContext()
				.getInitParameter("people.sirac.webservice.accreditamentiManagement.address");
		IAccreditamentiManagementWSServiceLocator accrClientLocator = new IAccreditamentiManagementWSServiceLocator();
		accrClientLocator.setIAccreditamentiManagementWSEndpointAddress(IAccreditamentiManagementURLString);

		IAccreditamentiManagementWS accrService = accrClientLocator.getIAccreditamentiManagementWS();
		return accrService;
	}
  
	public void updateWorkflow(AbstractPplProcess process) {

		Activity currentActivity = process.getView().getCurrentActivity();
		IStep currentStep = currentActivity.getCurrentStep();

		process.getView().setBottomNavigationBarEnabled(true);

		currentStep.setState(StepState.COMPLETED);
		currentActivity.setState(ActivityState.COMPLETED);

	}

  
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.people.IStep#logicalValidate(it.people.process.AbstractPplProcess,
	 * it.people.wrappers.IRequestWrapper, it.people.IValidationErrors)
	 */
	public boolean logicalValidate(AbstractPplProcess process,
			IRequestWrapper request, IValidationErrors errors)
			throws ParserException {

		return true;

	}

	public void loopBack(AbstractPplProcess process, IRequestWrapper request,
			String propertyName, int index) throws IOException, ServletException {
		
//		if (propertyName.equalsIgnoreCase("salvaOperatori")) {
//			HashMap<Integer, CheckboxElement> checkValues = new HashMap<Integer, CheckboxElement>();
//			checkValues = saveCheckValues(request, checkValues);
//			boolean success = updateAccreditamenti(request, checkValues);
//
//			if (success) {
//				// aggiorno i valori dei checkbox per gli operatori
//				ListaOperatoriDto listaOperatori = (ListaOperatoriDto) process
//						.getDto();
//				ArrayList<OperatoreDto> operatori = (ArrayList<OperatoreDto>) listaOperatori
//						.getListaOperatori();
//				for (OperatoreDto operatoreDto : operatori) {
//
//					int id = operatoreDto.getId();
//					CheckboxElement pair = checkValues.get(new Integer(id));
//					operatoreDto.setAttivo(pair.getAttivo());
//					operatoreDto.setDeleted(pair.getDeleted());
//				}
//			}
//
//		} 


		if (propertyName.equalsIgnoreCase("disattivaAccreditamento")) {
			// disattivaAccreditamento
			boolean success = updateAccreditamento(request, index, ACCR_DISATTIVO);
			if (success) {
				updateOperatore(process, index, false);
			}
		} 
		else 
		if (propertyName.equalsIgnoreCase("attivaAccreditamento")) {
			// attivaAccreditamento
			boolean success = updateAccreditamento(request, index, ACCR_ATTIVO);
			if (success) {
				updateOperatore(process, index, true);
			}
		}

	}

	/**
	 * @param process
	 * @param index idaccreditamento
	 * @param status valore a cui impostare il campo 'attivo'
	 */
	private void updateOperatore(AbstractPplProcess process, int index,
			boolean status) {

		// aggiorno l'operatore selezionato
		ListaOperatoriDto listaOperatori = (ListaOperatoriDto) process.getDto();
		ArrayList<OperatoreDto> operatori = (ArrayList<OperatoreDto>) listaOperatori.getListaOperatori();
		
		for (OperatoreDto operatoreDto : operatori) {

			if (operatoreDto.getId() == index) {
				operatoreDto.setAttivo(status);
			}
		}
	}

  
//	private HashMap<Integer, CheckboxElement> saveCheckValues(
//			IRequestWrapper request, HashMap<Integer, CheckboxElement> checkValues) {
//		
//		HttpServletRequest req = request.getUnwrappedRequest();
//		
//		if (req != null) {
//			@SuppressWarnings("unchecked")
//			Enumeration<String> en = req.getParameterNames();
//			for (; en.hasMoreElements();) {
//				CheckboxElement coppia;
//				
//				String param = en.nextElement();
//				if(param.startsWith("attivo_")){
//					String id = param.split("\\_")[1];
//					coppia = checkValues.get(new Integer(id));
//					if(coppia!=null){
//						coppia.setAttivo(req.getParameter(param));
//					} else {
//						coppia = new CheckboxElement(req.getParameter(param), "");
//					}
//					checkValues.put(new Integer(id), coppia);
//				}
//				else if(param.startsWith("deleted_")){
//					String id = param.split("\\_")[1];
//					coppia = checkValues.get(new Integer(id));
//					if(coppia!=null){
//						coppia.setDeleted(req.getParameter(param));
//					} else {
//						coppia = new CheckboxElement("", req.getParameter(param));
//					}
//					checkValues.put(new Integer(id), coppia);
//				}
//			}
//			
//		}
//		return checkValues;
//	}
	
	/**
	 * Salva i valori degli attributi 'attivo' e 'deleted' per gli accreditamenti visionati 
	 */
//	private boolean updateAccreditamenti(IRequestWrapper request, HashMap<Integer, CheckboxElement> checkValues ) {
//			boolean success;
//			HttpServletRequestDelegateWrapperHelper rwh = new HttpServletRequestDelegateWrapperHelper(request);
//
//			try {
//				success = true;
//				
//				List<String> attivi = new ArrayList<String>();
//				List<String> non_attivi = new ArrayList<String>();
//				List<String> deleted = new ArrayList<String>();
//				List<String> non_deleted = new ArrayList<String>();
//				
//				Set<Integer> chiavi = checkValues.keySet();
//				for (Integer i : chiavi) {
//					CheckboxElement coppia = checkValues.get(i);
//					//attivo
//					if(coppia.getAttivo()){ 
//						attivi.add(Integer.toString(i));
//					} else {
//						non_attivi.add(Integer.toString(i));
//					}
//					//deleted
//					if(coppia.getDeleted()){ 
//						deleted.add(Integer.toString(i));
//					} else {
//						non_deleted.add(Integer.toString(i));
//					}
//				
//				}
//				
//				String[] accreditamenti_attivo = (String[])attivi.toArray(new String[attivi.size()]);
//				String[] accreditamenti_non_attivo = (String[])non_attivi.toArray(new String[non_attivi.size()]);
//				String[] accreditamenti_deleted = (String[])deleted.toArray(new String[deleted.size()]);
//				String[] accreditamenti_non_deleted = (String[])non_deleted.toArray(new String[non_deleted.size()]);
//				
//				IAccreditamentiManagementWS accrService = getAccrService(request);
//				
//				accrService.setAccreditamenti_Attivo(accreditamenti_attivo, accreditamenti_non_attivo);
//				accrService.setAccreditamenti_Deleted(accreditamenti_deleted, accreditamenti_non_deleted);
//
//		    } catch (Exception ex) {
//		    	success = false;
//		        rwh.addSiracError("GestioneOAC2Step::Service()::Eccezione1", ex.getMessage());
//		    } 
//
//		
//		return success;
//
//	}
	
	/**
	 * Imposta il valore dell'attributo 'attivo' per l'accreditamento selezionato 
	 */
	private boolean updateAccreditamento(IRequestWrapper request, int idAccreditamento, String status ) {
		boolean success;
		HttpServletRequestDelegateWrapperHelper rwh = new HttpServletRequestDelegateWrapperHelper(request);
		
		try {
			success = true;
			
			IAccreditamentiManagementWS accrService = getAccrService(request);
			accrService.setAccreditamentoAttivo(idAccreditamento, status);
			
		} catch (Exception ex) {
			success = false;
			rwh.addSiracError("GestioneOAC2Step::Service()::UpdateAccreditamento", ex.getMessage());
		} 
		
		return success;
		
	}

	@Override
	public void readDto(PplData data, PeopleDto dto) throws MappingException {

		// pulizia Dto
		ListaOperatoriDto listaOperatori = (ListaOperatoriDto) dto;
		listaOperatori.setListaOperatori(new ArrayList<OperatoreDto>());
	}

	@Override
	public void writeDto(PplData data, PeopleDto dto) throws MappingException {
		// nessuna operazione
	}

	
//	private class CheckboxElement implements Serializable {
//
//		private static final long serialVersionUID = -8785844993061925064L;
//
//		private Boolean attivo;
//		private Boolean deleted;
//
//		public CheckboxElement(Boolean attivo, Boolean deleted) {
//			this.setAttivo(attivo);
//			this.setDeleted(deleted);
//		}
//
//		public CheckboxElement(String attivo, String deleted) {
//			this.setAttivo(convertValue(attivo));
//			this.setDeleted(convertValue(deleted));
//		}
//
//		public Boolean getAttivo() {
//			return attivo;
//		}
//
//		public void setAttivo(Boolean attivo) {
//			this.attivo = attivo;
//		}
//		public void setAttivo(String attivo) {
//			this.attivo = convertValue(attivo);
//		}
//
//		public Boolean getDeleted() {
//			return deleted;
//		}
//
//		public void setDeleted(Boolean deleted) {
//			this.deleted = deleted;
//		}
//		public void setDeleted(String deleted) {
//			this.deleted = convertValue(deleted);
//		}
//
//		private Boolean convertValue(String value) {
//			if (value.equals("on") || value.equals("true")) {
//				return true; 
//			} else {
//				return false;
//			}
//		}
//		
//	}


}
