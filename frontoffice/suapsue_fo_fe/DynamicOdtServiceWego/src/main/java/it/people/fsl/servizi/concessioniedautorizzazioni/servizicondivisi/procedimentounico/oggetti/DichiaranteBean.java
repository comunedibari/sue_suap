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

public class DichiaranteBean extends PersonaBean 
{
    public DichiaranteBean()
    {
        agiscePerContoDi = "";
    }
  
    /*
     * 0 --> impresa
     * 1 --> se stesso
     * 2 --> altre persone fisiche
     * 3 --> persona giuridica non scopo di lucro (da 30/07/07 definito "Altro ente")
     * 4 --> Professionista (aggiunti 30/07/07)
     */
  public String agiscePerContoDi;

    public String getAgiscePerContoDi() {
        return agiscePerContoDi;
    }
    
    public void setAgiscePerContoDi(String agiscePerContoDi) {
        this.agiscePerContoDi = agiscePerContoDi;
    }
}
