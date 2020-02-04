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
 * OperazioneBeanComparator.java
 * 
 * @date 13-ott-2005
 * 
 */
public class OperazioneBeanComparator implements Comparator{
    public int compare(Object o1, Object o2) throws ClassCastException
    {
      if(!(o1 instanceof OperazioneBean && o2 instanceof OperazioneBean))
      {
        throw new ClassCastException("Errore durante il compare di due OperazioneBean");
      }
      String codice1 = ((OperazioneBean)o1).getCodice()==null?"":((OperazioneBean)o1).getCodice();
      String codice2 = ((OperazioneBean)o2).getCodice()==null?"":((OperazioneBean)o2).getCodice();
      String descrizione1 = ((OperazioneBean)o1).getDescrizione()==null?"":((OperazioneBean)o1).getDescrizione();
      String descrizione2 = ((OperazioneBean)o2).getDescrizione()==null?"":((OperazioneBean)o2).getDescrizione();
      String codiceRamo1 = ((OperazioneBean)o1).getCodiceRamo()==null?"":((OperazioneBean)o1).getCodiceRamo();
      String codiceRamo2 = ((OperazioneBean)o2).getCodiceRamo()==null?"":((OperazioneBean)o2).getCodiceRamo();
      String descrizioneRamo1 = ((OperazioneBean)o1).getDescrizioneRamo()==null?"":((OperazioneBean)o1).getDescrizioneRamo();
      String descrizioneRamo2 = ((OperazioneBean)o2).getDescrizioneRamo()==null?"":((OperazioneBean)o2).getDescrizioneRamo();
      
      
      return codice1.equalsIgnoreCase(codice2) && descrizione1.equalsIgnoreCase(descrizione2) 
      && codiceRamo1.equalsIgnoreCase(codiceRamo2) && descrizioneRamo1.equalsIgnoreCase(descrizioneRamo2)? 
              0:-1; 
    }
}
