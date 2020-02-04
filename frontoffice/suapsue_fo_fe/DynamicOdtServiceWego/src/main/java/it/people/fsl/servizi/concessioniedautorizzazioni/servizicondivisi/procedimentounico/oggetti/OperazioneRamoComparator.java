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

import java.util.Comparator;

/**
 * @author federicog
 * 
 * OperazioneRamoComparator.java
 * 
 * @date 27-ott-2005
 * 
 */
public class OperazioneRamoComparator implements Comparator {

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
       public int compare(Object o1, Object o2) throws ClassCastException
        {
          if(!(o1 instanceof OperazioneBean && o2 instanceof OperazioneBean))
          {
            throw new ClassCastException("Errore durante il compare di due OperazioneBean");
          }
          String codiceRamo1 = ((OperazioneBean)o1).getCodiceRamo()==null?"":((OperazioneBean)o1).getCodiceRamo();
          String codiceRamo2 = ((OperazioneBean)o2).getCodiceRamo()==null?"":((OperazioneBean)o2).getCodiceRamo();         
          
          return codiceRamo1.compareToIgnoreCase(codiceRamo2);

    }

}
