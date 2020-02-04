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
 * Created on 7-mag-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.people;

/**
 * @author Zoppello
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PeopleConstants {

    // la costante � il nome dell'attributo valorizzato in Sessione dal
    // framework Struts
    public static final String USER_LOCALE_KEY = "org.apache.struts.action.LOCALE";

    // Nomi delle variabili di sessione
    public static final String SESSION_NAME_COMMUNE = "City";
    public static final String SESSION_NAME_COMMUNE_ID = "it.people.fsl.serviceProvider.idComune";

    // Nomi dei valori di inizializzazione
    public static final String CONTEXT_PARAMETER_NAME_HOSTURL = "peopleFSLHostURL";

    // Codice fiscale per l'utente anonimo
    public static final String ANONYMOUS_USERID = "NNMNNM70A01H536W@ANOMYMOUS";

    // Package del servizio attualmente instanziato
    // es. "it.people.fsl.servizi.esempi.tutorial.serviziotutorial1"
    public static final String REQUEST_PROCESS_NAME = "processName";

    /**
     * Prefix fo every session attribute that could and should be cleared at the
     * send process time.
     */
    public static final String CLEARABLE_SESSION_DATA = "PPL_CSD_";

    public static final String JAVASCRIPT_GUARD_STATE = "PPL_JG_STATE";

    /**
     * 
     */
    public static final String DEFAULT_CHARACTER_ENCODING = "ISO-8859-1";
    
    public static final String CONTEXT_PARAMETER_CROSS_ENDPOINT = "crossEndpoint";

}
