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
package it.people.envelope.util;

import it.people.backend.webservice.BEService;
import it.people.backend.webservice.BEServiceServiceLocator;
import it.people.backend.webservice.BEServiceStub;

import java.net.URL;
import java.rmi.RemoteException;

/**
 * @author piancia
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BEServiceClientAdapter implements
   BEService {

  protected java.net.URL endpointURL;
  
  protected int timeoutmsec = 120000;

  public BEServiceClientAdapter(String endpointURLString) throws Exception {
    if (endpointURLString == null) 
      throw new Exception ("BEServiceDelegheClientAdapter:: constructor:: Exception. Endpoint URL String Unspecified.");

    endpointURL = new URL(endpointURLString);
  }  

  public BEServiceClientAdapter(String endpointURLString, int _timeout) throws Exception {
    if (endpointURLString == null) 
      throw new Exception ("BEServiceDelegheClientAdapter:: constructor:: Exception. Endpoint URL String Unspecified.");

    endpointURL = new URL(endpointURLString);
    timeoutmsec = _timeout;
  }  

  public String process(String data) throws RemoteException {
    String result;
    try {
      result = getAdaptee().process(data);
    } catch (Exception e) {
    	e.printStackTrace();
      throw new RemoteException("Remote Exception: " + e.getMessage());
    }
    return result;
  }

    
//--------------------------------------------------------------------------------------
  // Support methods


    private BEServiceStub getAdaptee()  throws Exception {
    	BEServiceStub binding;
        try {
          if (endpointURL == null) throw new Exception ("BEServiceDelegheClientAdapter:: getAdaptee:: Cannot connect to Web Service. Endpoint Unspecified.");
            binding = (BEServiceStub)
                          new BEServiceServiceLocator().getBEService(endpointURL);
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new Exception("BEServiceDelegheClientAdapter - getAdaptee - JAX-RPC ServiceException caught: " + jre);
        }
        //assertNotNull("binding is null", binding);

        // Sets binding Time out
        return binding;
    }
}
