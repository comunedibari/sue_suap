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

  Licenza:	    Licenza Progetto PEOPLE
  Fornitore:    CEFRIEL
  Autori:       M. Pianciamore, P. Selvini

  Questo codice sorgente � protetto dalla licenza valida nell'ambito del
  progetto PEOPLE. La propriet� intellettuale di questo codice � e rester�
  esclusiva di "CEFRIEL Societ� Consortile a Responsabilit� Limitata" con
  sede legale in via Renato Fucini 2, 20133 Milano (MI).

  Disclaimer:

  COVERED CODE IS PROVIDED UNDER THIS LICENSE ON AN "AS IS" BASIS, WITHOUT
  WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, WITHOUT 
  LIMITATION, WARRANTIES THAT THE COVERED CODE IS FREE OF DEFECTS, MERCHANTABLE,
  FIT FOR A PARTICULAR PURPOSE OR NON-INFRINGING. THE ENTIRE RISK AS TO THE
  QUALITY AND PERFORMANCE OF THE COVERED CODE IS WITH YOU. SHOULD ANY COVERED
  CODE PROVE DEFECTIVE IN ANY RESPECT, YOU (NOT THE INITIAL DEVELOPER OR ANY
  OTHER CONTRIBUTOR) ASSUME THE COST OF ANY NECESSARY SERVICING, REPAIR OR
  CORRECTION.
    
*/

package it.people.sirac.idp.registration;

import java.rmi.RemoteException;

import it.people.sirac.idp.beans.ComuneBean;
import it.people.sirac.idp.beans.RegBean;
import it.people.sirac.idp.beans.ResKeystoreBean;
import it.people.sirac.idp.beans.ResRegBean;


public interface RegistrationInterface {

  /**
   * Riceve una richiesta di registrazione di un nuovo utente
   * Restituisce un oggetto ResRegBean contenente il risultato dell'operazione
   *
   * @param it.people.sirac.idp.beans.RegBean userRegistrationData
   * @return it.people.sirac.idp.beans.ResRegBean
   * @throws java.rmi.RemoteException
  */
  public ResRegBean executeRegistration(RegBean userRegistrationData) throws RemoteException;
  
  /**
   * Riceve una richiesta di aggiornamento della registrazione di un nuovo utente
   * Restituisce un oggetto ResRegBean contenente il risultato dell'operazione
   *
   * @param it.people.sirac.idp.beans.RegBean userRegistrationData
   * @return it.people.sirac.idp.beans.ResRegBean
   * @throws java.rmi.RemoteException
  */
  public ResRegBean updateRegistration(RegBean userRegistrationData) throws RemoteException;
  
  /**
   * Riceve una richiesta di rimozione della registrazione di un utente esistente
   * Restituisce un oggetto ResRegBean contenente il risultato dell'operazione
   *
   * @param it.people.sirac.idp.beans.RegBean userRegistrationData
   * @return it.people.sirac.idp.beans.ResRegBean
   * @throws java.rmi.RemoteException
  */
  public ResRegBean deleteRegistration(String codiceFiscale) throws RemoteException;

  /**
   * Restituisce true/false secondo che l'utente identificato dal Codice Fiscale
   * fornito sia presente o no nel database/ldap
   * 
   * @param String COD_FIS
   * @return boolean
   * @throws java.rmi.RemoteException
   */
  public boolean isUserRegistered(String codiceFiscale) throws RemoteException;

  /**
   * Imposta il flag ATTIVO per l'utente di cui � specificato il codice fiscale.
   * 
   * @param String COD_FIS
   * @return ResRegBean
   * @throws java.rmi.RemoteException
   */
  public ResRegBean activateUser(String codiceFiscale) throws RemoteException;
  
  /**
   * Cambia la password dell'utente indicato
   * 
   * @param String codiceFiscale
   * @param String oldPassword
   * @param String newPassword
   * @return ResRegBean
   * @throws java.rmi.RemoteException
   */
  public ResRegBean changePassword(String codiceFiscale, String oldPassword, String newPassword) throws RemoteException;
  
  /**
   * Restituisce il comune associato al codice Belfiore indicato
   * 
   * @param String codiceBelfiore
   * @return ComuneBean
   * @throws java.rmi.RemoteException
   */
  public ComuneBean getComuneByCodiceBelfiore(String codiceBelfiore) throws RemoteException;
  
  /**
   * Restituisce il comune associato al codice Istat indicato
   * 
   * @param String codiceIstat
   * @return ComuneBean
   * @throws java.rmi.RemoteException
   */
  public ComuneBean getComuneByCodiceIstat(String codiceBelfiore) throws RemoteException;
  
  /**
   * Inserisce il keystore per la firma remota, associato ad un utente
   * 
   * @param String userID
   * @param String pin
   * @param String keystoreB64
   * @return ResKeystoreBean
   * @throws java.rmi.RemoteException
   */
  public ResKeystoreBean insertNewKeystoreData(String userID, String pin, String keystoreB64) throws RemoteException;
  
}
