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
 * BEService.java
 *
 * Created on October 14, 2004, 3:23 PM
 */

package it.people.backend.webservice;

/**
 *
 * @author manelli
 */
public class BEService {
    
    public BEService() {
    }
    
    public String process(String data) {
        System.out.println("#### BACKEND RECEIVING A CALL");
        System.out.println(data);
        System.out.println("#### BACKEND RETURNS OK (ALWAYS)");
        return new String(data);
    }
}
