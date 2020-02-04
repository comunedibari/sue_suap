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
 * Creato il 10-lug-2006 da Cedaf s.r.l.
 *
 */
package it.people.filters.AdminAuthentication;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 */
public class AdminConfigElement {

    public static final String PARAMNAME_CAPDOMICILIO = "capDomicilio";
    public static final String PARAMNAME_CAPRESIDENZA = "capResidenza";
    public static final String PARAMNAME_CITTADOMICILIO = "cittaDomicilio";
    public static final String PARAMNAME_CITTARESIDENZA = "cittaResidenza";
    public static final String PARAMNAME_CODICEFISCALE = "codiceFiscale";
    public static final String PARAMNAME_COGNOME = "cognome";
    public static final String PARAMNAME_DATANASCITA = "dataNascita";
    public static final String PARAMNAME_EMAILADDRESS = "email";
    public static final String PARAMNAME_INDIRIZZODOMICILIO = "indirizzoDomicilio";
    public static final String PARAMNAME_INDIRIZZORESIDENZA = "indirizzoResidenza";
    public static final String PARAMNAME_LAVORO = "lavoro";
    public static final String PARAMNAME_LUOGONASCITA = "luogoNascita";
    public static final String PARAMNAME_NOME = "nome";
    public static final String PARAMNAME_PROVINCIADOMICILIO = "provinciaDomicilio";
    public static final String PARAMNAME_PROVINCIANASCITA = "provinciaNascita";
    public static final String PARAMNAME_PROVINCIARESIDENZA = "provinciaResidenza";
    public static final String PARAMNAME_SESSO = "sesso";
    public static final String PARAMNAME_STATODOMICILIO = "statoDomicilio";
    public static final String PARAMNAME_STATORESIDENZA = "statoResidenza";
    public static final String PARAMNAME_TELEFONO = "telefono";
    public static final String PARAMNAME_TITOLO = "titolo";
    public static final String PARAMNAME_USERPASSWORD = "password";
    public static final String PARAMNAME_USERPIN = "pin";

    public static final String[] PARAMNAMES = { PARAMNAME_CAPDOMICILIO,
	    PARAMNAME_CAPRESIDENZA, PARAMNAME_CITTADOMICILIO,
	    PARAMNAME_CITTARESIDENZA, PARAMNAME_CODICEFISCALE,
	    PARAMNAME_COGNOME, PARAMNAME_DATANASCITA, PARAMNAME_EMAILADDRESS,
	    PARAMNAME_INDIRIZZODOMICILIO, PARAMNAME_INDIRIZZORESIDENZA,
	    PARAMNAME_LAVORO, PARAMNAME_LUOGONASCITA, PARAMNAME_NOME,
	    PARAMNAME_PROVINCIADOMICILIO, PARAMNAME_PROVINCIANASCITA,
	    PARAMNAME_PROVINCIARESIDENZA, PARAMNAME_SESSO,
	    PARAMNAME_STATODOMICILIO, PARAMNAME_STATORESIDENZA,
	    PARAMNAME_TELEFONO, PARAMNAME_TITOLO, PARAMNAME_USERPASSWORD,
	    PARAMNAME_USERPIN };

    private String userName;
    private Hashtable userParameter = new Hashtable();
    private HashSet communeCodes = new HashSet();

    /**
     * Aggiunge il codice del comune per il quale l'utente � amministratore
     * 
     * @param code
     */
    public void addCommuneCode(String code) {
	this.communeCodes.add(code);
    }

    public void removeCommuneCode(String code) {
	this.communeCodes.remove(code);
    }

    /**
     * Determina se il codice comune passato � di un comune per il quale
     * l'utente � amministratore
     * 
     * @param code
     * @return true se l'utente � amministratore, false altrimenti
     */
    public boolean containsCommuneCode(String code) {
	return this.communeCodes.contains(code);
    }

    /**
     * Elenco dei comune per i quali l'utente � amministratore
     * 
     * @return
     */
    public Iterator getCommuneIterator() {
	return this.communeCodes.iterator();
    }

    /**
     * Ritorna il nome dell'utente autenticato sull'application server
     * 
     * @return
     */
    public String getUserName() {
	return userName;
    }

    /**
     * Imposta il nome dell'utente autenticato sull'application server
     * 
     * @return
     */
    public void setUserName(String userName) {
	this.userName = userName;
    }

    /**
     * Ritorna i parametri che saranno utilizzati per valorizzare l'utente
     * people creato per l'amministratore
     * 
     * @return
     */
    public String getUserParameter(String key) {
	return (String) userParameter.get(key);
    }

    /**
     * Imposta i parametri che saranno utilizzati per valorizzare l'utente
     * people creato per l'amministratore. Le chiavi valide sono:
     * "capDomicilio", "capResidenza", "cittaDomicilio", "cittaResidenza",
     * "codiceFiscale", "cognome", "dataNascita", "email", "indirizzoDomicilio",
     * "indirizzoResidenza", "lavoro", "luogoNascita", "nome",
     * "provinciaDomicilio", "provinciaNascita", "provinciaResidenza", "sesso",
     * "statoDomicilio", "statoResidenza", "telefono" "titolo", "password",
     * "pin".
     * 
     * @return
     */
    public void putUserParameter(String key, String value) {
	this.userParameter.put(key, value);
    }
}
