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
 * Created on 6-ott-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.layout;

import it.people.annotations.DeveloperTaskEnd;
import it.people.annotations.DeveloperTaskStart;

/**
 * @author FabMi
 * 
 *         La classe contiene tutte le chiavi delle etichette dei pulsanti.
 */
public abstract class ButtonKey {

    // Intestazione
    public static final String LOGOFF = "header.button.logoff";
    public static final String LOGIN = "header.button.login";
    public static final String MY_PROCESS = "header.button.myProcess";
    public static final String SERVICE = "header.button.service";
    public static final String HOME = "header.button.home";

    // Barra di navigazione
    public static final String NEXT = "navigation.button.next";
    public static final String SIGN = "navigation.button.sign";
    public static final String SAVE_AND_SEND = "navigation.button.saveAndSend";
    public static final String NEXT_MODULE = "navigation.button.nextModule";
    public static final String START_PAYMENT = "navigation.button.startPayment";
    public static final String PREVIOUS = "navigation.button.previous";
    public static final String PREVIOUS_MODULE = "navigation.button.previousModule";

    @DeveloperTaskStart(name = "Riccardo Forafo'", date = "23.05.2011", bugDescription = "", description = "Aggiunta la proprieta' ON_LINE_HELP_LOOP_BACK per la nuova gestione"
	    + " dell'help on line negli steps dei servizi.")
    public static final String ON_LINE_HELP_LOOP_BACK = "navigation.button.onLineHelpLoopback";
    @DeveloperTaskEnd(name = "Riccardo Forafo'", date = "23.05.2011")
    public static final String USER_PANEL_LOOP_BACK = "navigation.button.userPanelLoopback";

    // Amministrazione aiuto in linea
    public static final String ON_LINE_HELP_MANAGEMENT_LOOP_BACK = "navigation.button.onLineHelpManagementLoopback";

    // Barra di salvataggio
    public static final String SAVE = "navigation.button.save";

    // Pulsanti inseribili dal servizio
    public static final String LOOPBACK = "navigation.button.loopback";
    public static final String LOOPBACK_VALIDATE = "navigation.button.loopbackValidate";
    public static final String ADD_NEW_OBJECT = "navigation.button.addNewObject";
    // public static final String REMOVE_OBJECT =
    // "navigation.button.removeObject"; // forse lo si può ripristinare
    public static final String SAVE_UPLOADED_FILE = "navigation.button.saveUploadedFile";

    // Firma off line
    public static final String OFF_LINE_SIGN_DOWNLOAD = "navigation.button.offLineSignFileDownload";
    public static final String OFF_LINE_SIGN_UPLOAD = "navigation.button.offLineSignFileUpload";

}
