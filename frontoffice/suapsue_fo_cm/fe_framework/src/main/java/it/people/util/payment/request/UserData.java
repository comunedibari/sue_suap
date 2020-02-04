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
 * Created on 20-apr-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.util.payment.request;

import it.people.fsl.servizi.oggetticondivisi.PersonaFisica;
import it.people.fsl.servizi.oggetticondivisi.personagiuridica.PersonaGiuridica;

import javax.mail.internet.InternetAddress;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class UserData {
    protected InternetAddress emailUtente;

    private PersonaFisica personaFisica;
    private PersonaGiuridica personaGiuridica;

    public UserData() {
	this.setPersonaFisica(null);
	this.setPersonaGiuridica(null);
    }

    public UserData(InternetAddress emailUtente) {
	this.emailUtente = emailUtente;
	this.setPersonaFisica(null);
	this.setPersonaGiuridica(null);
    }

    /**
     * @return Returns the emailUtente.
     */
    public InternetAddress getEmailUtente() {
	return emailUtente;
    }

    /**
     * Indirizzo di e-mail a cui il sistema di pagamento far� riferimento per
     * l'invio dell'email di notifica di avvenuto pagamento. Per utilizzare
     * l'indirizzo dell'utente riportato dal sirac ritornare la stringa vuota.
     * 
     * @param emailUtente
     *            il mail dell'utente da impostare.
     */
    public void setEmailUtente(InternetAddress emailUtente) {
	this.emailUtente = emailUtente;
    }

    /**
     * @return the personaFisica
     */
    public final PersonaFisica getPersonaFisica() {
	return personaFisica;
    }

    /**
     * @param personaFisica
     *            the personaFisica to set
     */
    public final void setPersonaFisica(PersonaFisica personaFisica) {
	this.personaFisica = personaFisica;
    }

    /**
     * @return the personaGiuridica
     */
    public final PersonaGiuridica getPersonaGiuridica() {
	return personaGiuridica;
    }

    /**
     * @param personaGiuridica
     *            the personaGiuridica to set
     */
    public final void setPersonaGiuridica(PersonaGiuridica personaGiuridica) {
	this.personaGiuridica = personaGiuridica;
    }

}
