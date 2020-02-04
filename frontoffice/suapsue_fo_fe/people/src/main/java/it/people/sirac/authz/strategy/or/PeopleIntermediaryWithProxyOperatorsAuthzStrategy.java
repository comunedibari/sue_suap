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
package it.people.sirac.authz.strategy.or;

import it.people.fsl.servizi.deleghe.beans.PersonaFisica;
import it.people.fsl.servizi.deleghe.beans.PersonaGiuridica;
import it.people.fsl.servizi.deleghe.beans.Richiedente;
import it.people.fsl.servizi.deleghe.beans.Titolare;
import it.people.fsl.servizi.deleghe.beans.servizi.richieste.VisualizzazioneVerificaAbilitazioneIntermediario;
import it.people.fsl.servizi.deleghe.beans.servizi.risposte.RispostadiVisualizzazioneVerificaAbilitazioneIntermediario;
import it.people.fsl.servizi.deleghe.utility.XMLUtils;
import it.people.sirac.accr.ProfiliHelper;
import it.people.sirac.accr.beans.AbstractProfile;
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.ProfiloPersonaFisica;
import it.people.sirac.accr.beans.ProfiloPersonaGiuridica;
import it.people.sirac.accr.beans.Qualifica;
import it.people.sirac.authz.AuthorizationContextBean;
import it.people.sirac.authz.AuthorizationResponseBean;
import it.people.sirac.authz.strategy.IAuthorizationStrategy;
import it.people.sirac.deleghe.BEServiceClientAdapter;
import it.people.sirac.filters.dal.FENode;
import it.people.sirac.filters.dal.ServiceSoapDao;
import it.people.sirac.serviceProfile.ServiceProfile;
import it.people.sirac.services.accr.IAccreditamentoClientAdapter;
import it.people.util.ServiceParameters;

import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PeopleIntermediaryWithProxyOperatorsAuthzStrategy implements IAuthorizationStrategy {

	private static Logger logger = LoggerFactory.getLogger(PeopleIntermediaryWithProxyAuthzStrategy.class);

	private static final String ERROR_MESSAGE_1 = "La qualifica corrente non � abilitata, ma esiste una qualifica compatibile fra le qualifiche accreditate per l'utente.";

	private static final String ERROR_MESSAGE_2 = "Utilizzare il Servizio di Gestione Accreditamenti per selezionare un accreditamento compatibile con i requisiti del servizio richiesto.";

	private static final String ERROR_MESSAGE_3 = "La qualifica corrente non � abilitata.";

	private static final String ERROR_MESSAGE_4 = "Non � stato possibile verificare la presenza di una delega per la fruizione di questo servizio.";

	private static final String ERROR_MESSAGE_5 = "L'accesso al servizio come intermediario richiede il possesso di una delega da parte del titolare";

	private static final String ERROR_MESSAGE_6 = "L'accesso al servizio agli intermediari non � consentito.";

	public AuthorizationResponseBean authorize(
			AuthorizationContextBean authzContext) {

		AuthorizationResponseBean response = new AuthorizationResponseBean();

		ServiceProfile serviceProfile = authzContext.getProfiloServizio();
		Accreditamento accrCorrente = authzContext.getAccreditamentoOperatore();
		ProfiloPersonaFisica operatore = authzContext.getProfiloOperatore();
		AbstractProfile titolare = authzContext.getProfiloTitolare();
		String[] descrQualificheIntermediariAbilitate = serviceProfile.getQualificheIntermediarioAbilitate();
		String codiceFiscale=null;
		String idComune=null;
		IAccreditamentoClientAdapter wsAccrAdapter = null;
		String requestedPeopleService = null;
		boolean defaultControlloDelegheInAccesso = false;
		if (operatore!=null) {
			codiceFiscale = operatore.getCodiceFiscale();
			idComune = authzContext.getIdComune();
			wsAccrAdapter = authzContext.getWsAccrAdapter();
			requestedPeopleService = authzContext.getRequestedService();
			defaultControlloDelegheInAccesso = authzContext.isControlloDelegheInAccesso();
		}
		if (isIntermediario(accrCorrente)) { 
			if (serviceProfile.isAccessoIntermediariEnabled()) { 
				if (!serviceProfile.isAllQualificheIntermediariEnabled()) { 
					if (!isQualificaAbilitata(accrCorrente.getQualifica(), descrQualificheIntermediariAbilitate)) {
						// Verifica se tra le qualifiche dell'utente registrato
						// ne esiste una compatibile
						boolean esisteQualifica = esisteQualificaByDesc(
								codiceFiscale, idComune,
								descrQualificheIntermediariAbilitate,
								wsAccrAdapter);

						if (esisteQualifica) {
							// KO: - l'operatore � qualificato come intermediario 
							//	   - � consentito l'accesso agli intermediari 
							//     - ci sono vincoli sul tipo di intermediario
							//     - l'operatore non � un intermediario di uno dei tipi abilitati
							//     - l'operatore � dotato di una qualifica che gli
							//       consentirebbe di accedere al servizio
							logger.error(ERROR_MESSAGE_1);
							response.addMessage(ERROR_MESSAGE_1);
							response.addMessage(getDescrQualificheAbilitateMsg(descrQualificheIntermediariAbilitate));
							response.addMessage(ERROR_MESSAGE_2);
							response.setAuthorized(false);
						} else {
							// KO: - l'operatore � qualificato come intermediario
							//     - � consentito l'accesso agli intermediari
							//     - ci sono vincoli sul tipo di intermediario
							//     - l'operatore non � un intermediario di uno dei tipi abilitati
							//     - l'operatore non � dotato di una qualifica che
							//       gli consentirebbe di accedere al servizio
							logger.error(ERROR_MESSAGE_3);
							response.addMessage(ERROR_MESSAGE_3);
							response.setAuthorized(false);
						}
					} else {
						// OK: - l'operatore � qualificato come intermediario
						//     - � consentito l'accesso agli intermediari
						//     - ci sono vincoli sul tipo di intermediario
						//     - l'operatore � un intermediario di uno dei tipi abilitati
						response.setAuthorized(true);
					}
				} else {
					// OK: - l'operatore � qualificato come intermediario
					//     - � consentito l'accesso agli intermediari
					//     - non ci sono vincoli sul tipo di intermediario
					response.setAuthorized(true);
				}
				
			} else {
				// KO: - l'operatore � qualificato come intermediario
				//     - non � consentito l'accesso agli intermediari
				logger.error(ERROR_MESSAGE_6);
				response.addMessage(ERROR_MESSAGE_6);
				response.setAuthorized(false);
			}
			
			// passaggio a controllo delle deleghe
			if (response.isAuthorized() && requestedPeopleService != null) {
				ServiceParameters serviceParams = new ServiceParameters(requestedPeopleService, idComune);
				boolean disabilitaControlloDelegheInAccesso = new Boolean(serviceParams.get("disabilitaControlloDelegheInAccesso")).booleanValue();

				boolean mustCheckDelegate =(defaultControlloDelegheInAccesso && !disabilitaControlloDelegheInAccesso) &&
										   !isRappresentantePG(accrCorrente) && 
										   !isOperatoreAssociazioneCategoria(accrCorrente);
				
				if (mustCheckDelegate) {
					Richiedente richiedenteBE = new Richiedente();
					if (isIntermediario(accrCorrente)) { 
						richiedenteBE.setCodiceFiscale(accrCorrente.getProfilo().getCodiceFiscaleIntermediario());						
					} else {
						richiedenteBE.setCodiceFiscale(operatore.getCodiceFiscale());
					}
//					richiedenteBE.setNome(operatore.getNome());
//					richiedenteBE.setCognome(operatore.getCognome());
					Titolare titolareBE = new Titolare();
					if (titolare.isPersonaFisica()) {
						PersonaFisica titolarePFBE = new PersonaFisica();
						titolarePFBE.setCodiceFiscale(((ProfiloPersonaFisica) titolare).getCodiceFiscale());
						titolarePFBE.setNome(((ProfiloPersonaFisica) titolare).getNome());
						titolarePFBE.setCognome(((ProfiloPersonaFisica) titolare).getCognome());
						titolareBE.setPersonaFisica(titolarePFBE);
					} else {
						PersonaGiuridica titolarePGBE = new PersonaGiuridica();
						titolarePGBE.setCodiceFiscale(((ProfiloPersonaGiuridica) titolare).getCodiceFiscale());
						titolarePGBE.setDenominazione(((ProfiloPersonaGiuridica) titolare).getDenominazione());
						titolarePGBE.setPartitaIVA(((ProfiloPersonaGiuridica) titolare).getPartitaIva());
						titolareBE.setPersonaGiuridica(titolarePGBE);
					}

					// il controllo di delega viene fatto solo nel caso in cui
					// operatore/richiedente e titolare siano due persone diverse
					if (titolareBE.getPersonaGiuridica()!= null || 
							!operatore.getCodiceFiscale().equalsIgnoreCase(titolareBE.getPersonaFisica().getCodiceFiscale())) {
						try {
							if (!esisteDelega(idComune, requestedPeopleService,
									richiedenteBE, titolareBE)) {
								// KO: non esiste una delega
								response.addMessage(ERROR_MESSAGE_5);
								response.setAuthorized(false);
							}
						} catch (Exception ex) {
							// KO: errore durante il controllo delle deleghe
							response.addMessage(ERROR_MESSAGE_4);
							response.setAuthorized(false);
						}
					}
				}
			}
		} else {
			// KO: - l'operatore non � qualificato come intermediario - questa strategy non si applica
			response.setAuthorized(false);
		}

		return response;

	}

	private boolean isIntermediario(Accreditamento accr) {
		if (accr == null) {
			return false;
		}
		return (!accr.getQualifica().getIdQualifica().equals(
				ProfiliHelper.getQualificaUtentePeopleRegistrato()
						.getIdQualifica()));
	}

	private boolean isRappresentantePG(Accreditamento accr) {
		return accr.getQualifica().getTipoQualifica().equals(ProfiliHelper.TIPOQUALIFICA_RAPPRPERSGIURIDICA);
	}
	
	private boolean isOperatoreAssociazioneCategoria(Accreditamento accr) {
		return ("OAC".equals(accr.getQualifica().getIdQualifica()) || "RCT".equals(accr.getQualifica().getIdQualifica()));
	}
	
	private boolean isQualificaAbilitata(Qualifica qualifica,
			String[] qualificheAbilitate) {
		boolean result = false;
		if (qualifica != null && qualificheAbilitate != null) {
			String descrQualifica = qualifica.getDescrizione();
			if (descrQualifica == null)
				return false;
			for (int i = 0; i < qualificheAbilitate.length; i++) {
				String descrQualificaAbilitata = qualificheAbilitate[i];
				if (descrQualifica.equals(descrQualificaAbilitata))
					return true;
			}
		}
		return result;
	}

	private boolean esisteQualificaByDesc(String codiceFiscale,
			String idComune, String[] descrQualificheAbilitate,
			IAccreditamentoClientAdapter accrWSClient) {
		boolean result = false;
		Qualifica[] qualificheUtenteAbilitate = null;
		try {
			qualificheUtenteAbilitate = accrWSClient.getQualificheAbilitate(
					codiceFiscale, idComune);
		} catch (RemoteException e) {
			return false;
		}
		if (qualificheUtenteAbilitate != null) {
			for (int i = 0; i < qualificheUtenteAbilitate.length; i++) {
				Qualifica qualificaUtente = qualificheUtenteAbilitate[i];
				if (isQualificaAbilitata(qualificaUtente,
						descrQualificheAbilitate)) {
					return true;
				}
			}
		}
		return result;
	}

	private String getDescrQualificheAbilitateMsg(
			String[] descrQualificheAbilitate) {
		StringBuffer sb = new StringBuffer(
				"Qualifiche abilitate all'accesso al servizio: ");
		if (descrQualificheAbilitate == null
				|| descrQualificheAbilitate.length == 0) {
			sb.append("nessuna.");
		} else {
			for (int i = 0; i < descrQualificheAbilitate.length; i++) {
				if (i > 0)
					sb.append(",");
				sb.append(descrQualificheAbilitate[i]);
			}
			sb.append(".");
		}

		return sb.toString();
	}

	private String getBslDelegheWSAddress(String idComune) throws Exception {
		ServiceSoapDao dao = new ServiceSoapDao();
		FENode feNode = null;

		try {
			dao.open();
			feNode = dao.getFENode(idComune);
			dao.close();
		} catch (Exception ex) {
			logger.error("", ex);
			throw ex;
		} finally {
			if (dao != null)
				try {
					dao.close();
				} catch (Exception ex) {
				}
			;
		}
		return feNode.getCheckDelegateURL();
	}

	private boolean esisteDelega(String idComune, String nomeServizio,
			Richiedente richiedente, Titolare titolare) throws Exception {

		VisualizzazioneVerificaAbilitazioneIntermediario bean = new VisualizzazioneVerificaAbilitazioneIntermediario();
		bean.setCodiceAmministrazione(idComune);
		bean.setCodiceFiscaleDelegato(richiedente.getCodiceFiscale());

		String cfTitolare = null;
		if (titolare.getPersonaFisica() != null) {
			cfTitolare = titolare.getPersonaFisica().getCodiceFiscale();
		} else {
			cfTitolare = titolare.getPersonaGiuridica().getCodiceFiscale();
		}
		bean.setCodiceFiscaleDelegante(cfTitolare);
		bean.setNomeServizio(nomeServizio);
		bean.setContesto(nomeServizio);
		bean.setRichiedente(richiedente);
		bean.setTitolare(titolare);

		String beanXML = XMLUtils.marshall(bean);

		logger.debug(beanXML);

		String bslDelegheWSAddress = getBslDelegheWSAddress(idComune);
		BEServiceClientAdapter ws = new BEServiceClientAdapter(
				bslDelegheWSAddress);

		String output = ws.process(beanXML);

		Object responseObj = XMLUtils
				.unmarshall(
						RispostadiVisualizzazioneVerificaAbilitazioneIntermediario.class,
						output);
		RispostadiVisualizzazioneVerificaAbilitazioneIntermediario response = (RispostadiVisualizzazioneVerificaAbilitazioneIntermediario) responseObj;

		return response.isAbilitato();
	}

}
