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
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti;

/**
 * @author federicog
 * 
 * ProcedimentoUnicoException.java
 * 
 * @date 11-nov-2005
 * 
 */
public class ProcedimentoUnicoException extends Exception {

    /**
     * 
     */
    public ProcedimentoUnicoException() {
        super();

    }

    /**
     * @param message
     */
    public ProcedimentoUnicoException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ProcedimentoUnicoException(Throwable cause) {
        super(cause);

    }

    /**
     * @param message
     * @param cause
     */
    public ProcedimentoUnicoException(String message, Throwable cause) {
        super(message, cause);
    }

}
