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
 * Created on 10-giu-2005
 *
 */
package it.people.util.payment;

import javax.mail.internet.InternetAddress;

/**
 * @author FabMi
 * @deprecated utilizzare la nuova interfaccia IStartPayment
 */
public interface IRimp {
    /**
     * Identificativo del servizio concordato con il CIC
     * 
     * @return
     */
    public String getServiceId();

    /**
     * Indirizzo di mail dell'utente, per utilizzare l'indirizzo di registrato
     * dall'utente ritornare la stringa vuota
     * 
     * @return
     */
    public InternetAddress getEmail();

    /**
     * Dati specifici salvati nel flusso di rendicontazione del MIP, per non
     * indicare dati specifici ritornare la string vuota
     * 
     * @return
     */
    public String getDatiSpecifici();

    /**
     * Importo da pagare espresso in centesimi di euro
     * 
     * @return
     */
    public Long getImporto();
}
