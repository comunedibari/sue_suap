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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Output {
	
    private List sportelli;
    private Log logger = LogFactory.getLog(this.getClass());
	
    public Output() {
        sportelli = new ArrayList();
    }

    
    /**
     * @return Returns the sportello.
     */
    public List getSportelli() {
        return sportelli;
    }

    /**
     * @param sportello The sportello to set.
     */
    public void setSportelli(List sportelli) {
        this.sportelli = sportelli;
    }

    public void addSportelli(SportelloBean bean){
        try {
            sportelli.add(bean);
        } catch (Exception e) {
            logger.error(e);
        }       
    }
}
