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

/*
 * Created on Sep 5, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.sirac.idp.authentication.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;

import it.people.sirac.idp.authentication.AuthenticationClientAdapter;
import it.people.sirac.idp.beans.RegBean;
import it.people.sirac.idp.beans.ResAuthBean;
import junit.framework.TestCase;

/**
 * @author piancia
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AuthenticationWSTest extends TestCase {
  /**
   * Logger for this class
   */
  private static final Logger logger = LoggerFactory.getLogger(AuthenticationWSTest.class);
  
//  protected String authenticationEIWSAddress = 
//    "http://peopleserver.people.it:9000/ws_regauth/services/AuthenticationInterface";
  protected String authenticationEIWSAddress = 
		    "http://localhost:9080/ws_regauth/services/AuthenticationInterface";
  
  protected AuthenticationClientAdapter authWS = null;

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    authWS= new AuthenticationClientAdapter(authenticationEIWSAddress);
    
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testExecuteBasicAuthentication() {
    System.out.println("Primo test autenticazione");
    ResAuthBean authResult;
    try {
      authResult = authWS.executeBasicAuthentication("PNCMSM67A16F205P", "max");
      System.out.println(authResult);

      System.out.println("Secondo test autenticazione");
      authResult = authWS.executeBasicAuthentication("CODICEFISC205P", "abc");
      System.out.println(authResult);
      
      System.out.println("Terzo test autenticazione");
      authResult = authWS.executeBasicAuthentication("CTTTCN57P51F205W", "wego2013");
      System.out.println(authResult);
    } catch (RemoteException e) {
      // TODO Auto-generated catch block
      logger.error("An Exception has been generated", e);
      fail("A Remote Exception has been generated");
    }

  }

  public void testExecutePINAuthentication() {
    System.out.println("Primo test autenticazione");
    ResAuthBean authResult;
    try {
      authResult = authWS.executePINAuthentication("PNCMSM67A16F205P", "1234567890");
      System.out.println(authResult);

      System.out.println("Secondo test autenticazione");
      authResult = authWS.executePINAuthentication("CODICEFISC205P", "abc");
      System.out.println(authResult);
    } catch (RemoteException e) {
      // TODO Auto-generated catch block
      logger.error("An Exception has been generated", e);
      fail("A Remote Exception has been generated");
    }
  }

  public void testGetUserData() {
    System.out.println("Primo test getUserData()");
    RegBean userData;
    try {
      userData = authWS.getUserData("PNCMSM67A16F205P", "max");
      System.out.println(userData);

      System.out.println("Secondo test getUserData()");
      userData = authWS.getUserData("CODICEFISC205P", "abc");
      System.out.println(userData);
    } catch (RemoteException e) {
      // TODO Auto-generated catch block
      logger.error("An Exception has been generated", e);
      fail("A Remote Exception has been generated");
    }
  }

  public void testGetUserDataWithCIE() {
    System.out.println("Primo test getUserDataWithCIE()");
    RegBean userData;
    try {
      userData = authWS.getUserDataWithCIE("AA0054738");
      System.out.println(userData);

      System.out.println("Secondo test getUserDataWithCIE()");
      userData = authWS.getUserDataWithCIE("12345");
      System.out.println(userData);
    } catch (RemoteException e) {
      // TODO Auto-generated catch block
      logger.error("An Exception has been generated", e);
      fail("A Remote Exception has been generated");
    }
  }

  public void testGetUserDataWithCodiceFiscale() {
    System.out.println("Primo test getUserDataWithCodiceFiscale()");
    RegBean userData;
    try {
      userData = authWS.getUserDataWithCodiceFiscale("SLVPLA74C22C933Y");
      System.out.println(userData);
    } catch (RemoteException e) {
      // TODO Auto-generated catch block
      logger.error("An Exception has been generated", e);
      fail("A Remote Exception has been generated");
    }
  }

}
