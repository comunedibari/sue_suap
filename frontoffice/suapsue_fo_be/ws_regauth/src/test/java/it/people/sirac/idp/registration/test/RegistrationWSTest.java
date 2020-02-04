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

package it.people.sirac.idp.registration.test;

import java.rmi.RemoteException;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;

import it.people.sirac.idp.beans.ComuneBean;
import it.people.sirac.idp.beans.RegBean;
import it.people.sirac.idp.beans.ResRegBean;
import it.people.sirac.idp.registration.RegistrationClientAdapter;

//import it.people.core.PplUserData;

public class RegistrationWSTest extends TestCase {

  /**
   * Logger for this class
   */
  private static final Logger logger = LoggerFactory.getLogger(RegistrationWSTest.class);
  
  protected String registrationWSAddress = 
    "http://peopleserver.people.it:9000/ws_regauth/services/RegistrationInterface";
  
  protected RegistrationClientAdapter regWS = null;

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    regWS= new RegistrationClientAdapter(registrationWSAddress);
    
  }

  
  private String generatePassword()
  {
    // Password formata da
    // 3 lettere A-Z
    // 1 punto
    // 3 cifre 0-9
    Random rnd = new Random();
    StringBuffer pwd = new StringBuffer(8);
    final String block1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";     
    int i;
    
    for(i=0; i<8; i++)
      pwd.append(block1.charAt(rnd.nextInt(block1.length())));

    return pwd.toString();
  }
  
  private String generatePin()
  {
    // PIN formato da 
    // 10 cifre 0-9
    
    Random rnd = new Random();
    int i;
    StringBuffer pin = new StringBuffer(10);
    final String numbers = "0123456789";

    for(i=0; i<9; i++)
      pin.append(numbers.charAt(rnd.nextInt(10)));

    return pin.toString();
  }
  
  public void testExecuteRegistration(){
  	ResRegBean response = null;
   try{
     RegBean rb = new RegBean();

     rb.setNome("nome");
     rb.setCognome("cognome");
     rb.setCodiceFiscale("1234567890ABCDEF");
     rb.setEmail("email@host.com");
     rb.setIndirizzoResidenza("indirizzoResidenza");
     rb.setCittaResidenza("cittaResidenza");
     rb.setCapResidenza("12345");
     rb.setProvinciaResidenza("PR");
     rb.setStatoResidenza("Italia");
     rb.setLavoro("lavoro");
     rb.setIndirizzoDomicilio("indirizzoDomicilio");
     rb.setCapDomicilio("12345");
     rb.setCittaDomicilio("cittaDomicilio");
     rb.setProvinciaDomicilio("PD");
     rb.setStatoDomicilio("Italia");
     rb.setDataNascita("01/01/1970");
     rb.setLuogoNascita("luogoNascita");
     rb.setProvinciaNascita("PN");
     rb.setStatoNascita("Italia");
     rb.setSesso("M");
     rb.setTelefono("00-0011223344");
     rb.setCellulare("000-000111222");
     rb.setTitolo("titolo");
     rb.setDomicilioElettronico("dom_elet@idp-people.it");
     rb.setIdComune("00000");
     rb.setCartaIdentita("AA11223344");
     rb.setPassword(generatePassword());
     rb.setPin(generatePin());
     
     response = regWS.executeRegistration(rb);
     if("FAILED".equalsIgnoreCase(response.getEsito())){
    	 fail(response.getMessaggio());
     }
   } catch(Exception ex){
     logger.error("An Exception has been generated", ex);
     fail("An exception was raised: " + ex);
   }
  }

  public void testUpdateRegistration(){

	try {
	    RegBean rb = new RegBean();
	
	    rb.setCodiceFiscale("1234567890ABCDEF");
	    rb.setNome("nome-modificato");
	    rb.setCognome("cognome-modificato");
	    rb.setEmail("email@host.com");
	    rb.setIndirizzoResidenza("indirizzoResidenza");
	    rb.setCittaResidenza("cittaResidenza");
	    rb.setCapResidenza("12345");
	    rb.setProvinciaResidenza("PR");
	    rb.setStatoResidenza("Italia");
	    rb.setLavoro("lavoro");
	    rb.setIndirizzoDomicilio("indirizzoDomicilio");
	    rb.setCapDomicilio("12345");
	    rb.setCittaDomicilio("cittaDomicilio");
	    rb.setProvinciaDomicilio("PD");
	    rb.setStatoDomicilio("Italia");
	    rb.setDataNascita("01/01/1970");
	    rb.setLuogoNascita("luogoNascita");
	    rb.setProvinciaNascita("PN");
	    rb.setStatoNascita("Italia");
	    rb.setSesso("M");
	    rb.setTelefono("00-0011223344");
	    rb.setCellulare("000-000111222");
	    rb.setTitolo("titolo");
	    rb.setDomicilioElettronico("dom_elet@idp-people.it");
	    rb.setIdComune("00000");
	    rb.setCartaIdentita("AA11223344");
	    rb.setPassword(generatePassword());
	    rb.setPin(generatePin());
	     
	    regWS.updateRegistration(rb);
    } catch(Exception ex){
      logger.error("An Exception has been generated", ex);
      fail("An exception was raised: " + ex);
    }
  }
  
  public void testChangePassword(){
  	try {
			String codiceFiscale = "1234567890ABCDEF";
			String oldPassword = "GOUL6PH9"; 
			String newPassword = "paolo";
	    ResRegBean response = regWS.changePassword(codiceFiscale, oldPassword, newPassword);
	    if("FAILED".equalsIgnoreCase(response.getEsito())){
	    	fail(response.getMessaggio());
	    }
		} catch(Exception ex){
		  logger.error("An Exception has been generated", ex);
		  fail("An exception was raised: " + ex);
		}
  }  	
  
  public void testActivateUser(){

  	try {
  			String codiceFiscale = "1234567890ABCDEF";
  	    regWS.activateUser(codiceFiscale);
  	} catch(Exception ex){
      logger.error("An Exception has been generated", ex);
      fail("An exception was raised: " + ex);
    }
  }
  
  public void testDeleteRegistration() {
	  try {
			String codiceFiscale = "1234567890ABCDEF";
			regWS.deleteRegistration(codiceFiscale);
	  } catch(Exception ex){
		logger.error("An Exception has been generated", ex);
		fail("An exception was raised: " + ex);
	  }
  }
  
  public void testIsUserRegistered() {
    System.out.println("Primo test isUserPresent()");
    boolean isPresent;
    try {
      isPresent = regWS.isUserRegistered("1234567890ABCDEF");
      System.out.println(isPresent);
      if(!isPresent) 
    	  fail("Not present");
    } catch (RemoteException ex) {
      logger.error("An Exception has been generated", ex);
      fail("A Remote Exception has been generated: " + ex);
    }
  }

  public void testGetComuneByCodiceBelfiore() {
    ComuneBean comune = null;
    try {
      comune = regWS.getComuneByCodiceBelfiore("C933");
      if(comune==null) 
    	  fail("Not present");
      else
          System.out.println(comune.getNome());
    } catch (RemoteException ex) {
      logger.error("An Exception has been generated", ex);
      fail("A Remote Exception has been generated: " + ex);
    }
  }
  
  public void testGetComuneByCodiceIstat() {
	    ComuneBean comune = null;
	    try {
	      comune = regWS.getComuneByCodiceIstat("010025");
	      if(comune==null) 
	    	  fail("Not present");
	      else
	          System.out.println(comune.getNome());
	    } catch (RemoteException ex) {
	      logger.error("An Exception has been generated", ex);
	      fail("A Remote Exception has been generated: " + ex);
	    }
	  }
}
