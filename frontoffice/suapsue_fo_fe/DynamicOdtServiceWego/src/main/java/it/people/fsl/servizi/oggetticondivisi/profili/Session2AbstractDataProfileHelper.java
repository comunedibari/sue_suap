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
package it.people.fsl.servizi.oggetticondivisi.profili;

import java.lang.reflect.Method;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;

public class Session2AbstractDataProfileHelper {
	
	/* nome della variabile di sessione contenente il profilo dell'operatore */
    public static final String SIRAC_ACCR_OPERATORE ="it.people.sirac.accr.profiloOperatore";
	/* nome della variabile di sessione contenente il profilo del richiedente */
    public static final String SIRAC_ACCR_RICHIEDENTE ="it.people.sirac.accr.profiloRichiedente";
	/* nome della variabile di sessione contenente il profilo del titolare */
	public static final String SIRAC_ACCR_TITOLARE ="it.people.sirac.accr.profiloTitolare";
    /* nome del convertitore di profili messo in sessione */
    public static final String SIRAC_CONVERTITORE_PROFILI ="it.people.sirac.accr.ConvertitoreProfili";
    /* nome della variabile di sessione contenente lo username dell'utente autenticato */
    public static final String SIRAC_AUTHENTICATED_USER = "it.people.sirac.authenticated_user";
    /* nome della variabile di sessione contenente i dati dell'utente autenticato */
    public static final String SIRAC_AUTHENTICATED_USERDATA = "it.people.sirac.authenticated_user_data";
    /* nome della variabile di sessione utilizzata per trasferire il domicilio elettronico selezionato
     * al filtro di aggiornamento del profilo */
    public static final String PEOPLE_SAVED_DOMICILIOELETTRONICO = "it.people.savedDomicilioElettronico";

	/**
	 * Aggiorna le variabili in session con i dati dei profili richiedente e titolare e con il domicilio elettronico
	 * indicato dall'utente
	 * 
	 * @param profiloRichiedente
	 * @param profiloTitolare
	 * @param domicilioElettronico
	 * @param session
	 * @throws Exception
	 */
    public static void storeProfilesInSession(ProfiloPersonaFisica profiloRichiedente,
			                                  ProfiloTitolare profiloTitolare, 
			                                  String domicilioElettronico,
			                                  HttpSession session) throws Exception {
		
		// istanzio un oggetto di tipo ProfiloPersonaFisica appartenente per� alla libreria SIRAC_LIB che non 
		// � accessibile nelle classi del fe_framework e lo faccio attraverso la reflection in modo che questa
		// classe compili. A runtime sar� necessario il jar sirac_lib.jar. 
		// Questo oggetto va bene per copiare il profilo del richiedente. Per il profilo del titolare si fa
		// lo stesso ma si potrebbe dover istanziare una classe di tipo ProfiloPersonaGiuridica
		Class siracPPFClass = Class.forName("it.people.sirac.accr.beans.ProfiloPersonaFisica");
		Object siracPPF_ProfiloRichiedente = siracPPFClass.newInstance();
		BeanUtils.copyProperties(siracPPF_ProfiloRichiedente, profiloRichiedente);
		session.setAttribute(SIRAC_ACCR_RICHIEDENTE, siracPPF_ProfiloRichiedente);
		
		if(profiloTitolare.isPersonaFisica()){
			Object siracPPF_ProfiloTitolare = siracPPFClass.newInstance();
			BeanUtils.copyProperties(siracPPF_ProfiloTitolare, profiloTitolare.getProfiloTitolarePF());
			session.setAttribute(SIRAC_ACCR_TITOLARE, siracPPF_ProfiloTitolare);
		} else {
			Class siracPPGClass = Class.forName("it.people.sirac.accr.beans.ProfiloPersonaGiuridica");
			Object siracPPG_ProfiloTitolare = siracPPGClass.newInstance();
			try {
				BeanUtils.copyProperties(siracPPG_ProfiloTitolare, profiloTitolare.getProfiloTitolarePG());
			} catch(IllegalArgumentException ex) {
				// questa eccezione viene sollevata quando nel bean ProfiloPersonaGiuridica viene trovato un bean
				// annidato, in particolare un ProfiloPersonaFisica corrispondente al rappresentante legale correlato
				// La libreria commons-beanutils utilizzata non � in grado di copiare automaticamente anche tale 
				// informazione passando in modo ricorsivo tutti gli attributi del bean esterno e quindi occorre
				// provvedere alla copia manuale di tale bean annidato.
				Method getRappresentanteLegaleMethod = siracPPGClass.getMethod("getRappresentanteLegale", null);
				BeanUtils.copyProperties(getRappresentanteLegaleMethod.invoke(siracPPG_ProfiloTitolare, null), profiloTitolare.getProfiloTitolarePG().getRappresentanteLegale());
				// Dopo aver copiato a mano il contenuto del bean del Rappresentante Legale, occorre proseguire a mano
				// la copia dei campi rimanenti del bean ProfiloPersonaGiuridica esterno, il cui parsing era 
				// stato interrotto dall'eccezione. L'unico campo residuo � la sede legale che viene copiata utilizzando
				// la reflection, per via del fatto che il bean da leggere � di un tipo contenuto nella libreria
				// SIRAC_LIB, non disponibile nel fe_framework.
				Method setSedeLegaleMethod = siracPPGClass.getMethod("setSedeLegale", new Class[]{String.class});
				setSedeLegaleMethod.invoke(siracPPG_ProfiloTitolare, new Object[]{profiloTitolare.getProfiloTitolarePG().getSedeLegale()});
			}
			session.setAttribute(SIRAC_ACCR_TITOLARE, siracPPG_ProfiloTitolare);
		}
		
		// Al pari del caso precedente, l'informazione sul domicilio elettronico che deve essere posta nella classe
		// PplUserDataExtended presente nella libreria SIRAC_LIB viene fatta mediante la reflection
		Object siracPplUserDataExt = session.getAttribute(SIRAC_AUTHENTICATED_USERDATA);
		Class siracPplUserDataExtClass = siracPplUserDataExt.getClass();
		Method setEmailaddressMethod = siracPplUserDataExtClass.getMethod("setEmailaddress", new Class[]{String.class});
		setEmailaddressMethod.invoke(siracPplUserDataExt, new Object[]{domicilioElettronico});
		
		// Salva il domicilio elettronico impostato dall'utente in modo che sia presentato nella pagina di conferma, a valle
		// del caricamento di una pratica tra quelle salvate. Altrimenti il filtro di aggiornamento del profilo presenta come
		// domicilio elettronico quello associato al profilo di accreditamento
		session.setAttribute(PEOPLE_SAVED_DOMICILIOELETTRONICO, domicilioElettronico);
	}
	
	/**
	 * Restituisce il ProfiloPersonaFisica del richiedente corrispondente alla struttura dati presente in sessione
	 * 
	 * @param session
	 * @return
	 * @throws Exception
	 */
    public static ProfiloPersonaFisica getProfiloRichiedenteFromSession(HttpSession session) throws Exception {
		ProfiloPersonaFisica profiloRichiedente = new ProfiloPersonaFisica();
		Object profiloRichiedenteFromSession = session.getAttribute(SIRAC_ACCR_RICHIEDENTE);
    	BeanUtils.copyProperties(profiloRichiedente, profiloRichiedenteFromSession);
    	return profiloRichiedente;
	}

    /**
	 * Restituisce il ProfiloTitolare (persona fisica o giuridica) del titolare corrispondente alla struttura
	 * dati presente in sessione
	 * 
	 * @param session
	 * @return
	 * @throws Exception
	 */
    public static ProfiloTitolare getProfiloTitolareFromSession(HttpSession session) throws Exception {
		Object profiloTitolareFromSession = session.getAttribute(SIRAC_ACCR_TITOLARE);
		ProfiloTitolare profiloTitolare = new ProfiloTitolare();
		if(isProfiloPersonaGiuridica(profiloTitolareFromSession)){
			profiloTitolare.setProfiloTitolarePG(new ProfiloPersonaGiuridica());
			try {
				BeanUtils.copyProperties(profiloTitolare.getProfiloTitolarePG(), profiloTitolareFromSession);
			} catch(IllegalArgumentException ex) {
				// questa eccezione viene sollevata quando nel bean ProfiloPersonaGiuridica viene trovato un bean
				// annidato, in particolare un ProfiloPersonaFisica corrispondente al rappresentante legale correlato
				// La libreria commons-beanutils utilizzata non � in grado di copiare automaticamente anche tale 
				// informazione passando in modo ricorsivo tutti gli attributi del bean esterno e quindi occorre
				// provvedere alla copia manuale di tale bean annidato.
				Class profiloPersonaGiuridicaPGClass = Class.forName("it.people.sirac.accr.beans.ProfiloPersonaGiuridica");
				Method getRappresentanteLegaleMethod = profiloPersonaGiuridicaPGClass.getMethod("getRappresentanteLegale", null);
				// Dopo aver copiato a mano il contenuto del bean del Rappresentante Legale, occorre proseguire a mano
				// la copia dei campi rimanenti del bean ProfiloPersonaGiuridica esterno, il cui parsing era 
				// stato interrotto dall'eccezione. L'unico campo residuo � la sede legale che viene copiata utilizzando
				// la reflection, per via del fatto che il bean da leggere � di un tipo contenuto nella libreria
				// SIRAC_LIB, non disponibile nel fe_framework.
				Method getSedeLegaleMethod = profiloPersonaGiuridicaPGClass.getMethod("getSedeLegale", null);
				BeanUtils.copyProperties(profiloTitolare.getProfiloTitolarePG().getRappresentanteLegale(), getRappresentanteLegaleMethod.invoke(profiloTitolareFromSession, null));
				profiloTitolare.getProfiloTitolarePG().setSedeLegale((String)getSedeLegaleMethod.invoke(profiloTitolareFromSession, null));
			}
    	} else {
    		profiloTitolare.setProfiloTitolarePF(new ProfiloPersonaFisica());
			BeanUtils.copyProperties(profiloTitolare.getProfiloTitolarePF(), profiloTitolareFromSession);
    	}
    	return profiloTitolare;
	}
	
	/**
	 * Restituisce la stringa del domicilio elettronico presente nella struttura relativa all'utente autenticato
	 * in sessione
	 *  
	 * @param session
	 * @return
	 * @throws Exception
	 */
    public static String getDomicilioElettronicoFromSession(HttpSession session) throws Exception {
		Object siracPplUserDataExt = session.getAttribute(SIRAC_AUTHENTICATED_USERDATA);
		Class siracPplUserDataExtClass = siracPplUserDataExt.getClass();
		Method getEmailaddressMethod = siracPplUserDataExtClass.getMethod("getEmailaddress", null);
		String domicilioElettronico = (String)getEmailaddressMethod.invoke(siracPplUserDataExt, null);
		return domicilioElettronico;
	}
	
	/**
	 * Determina se un profilo passato come Object corrisponde ad una persona fisica o ad una giuridica
	 * 
	 * @param profilo
	 * @return
	 */
	public static boolean isProfiloPersonaGiuridica(Object profilo){
		try {
			if(BeanUtils.getProperty(profilo, "partitaIva")==null) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		} 
	}
	
}
