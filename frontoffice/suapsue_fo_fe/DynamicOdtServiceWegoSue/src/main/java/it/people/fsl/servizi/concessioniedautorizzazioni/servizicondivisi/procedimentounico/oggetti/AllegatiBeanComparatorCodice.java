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

public class AllegatiBeanComparatorCodice implements Comparator {

    public int compare(Object o1, Object o2) throws ClassCastException {
        if (!(o1 instanceof AllegatiBean && o2 instanceof AllegatiBean)) {
            throw new ClassCastException("Errore durante il compare di due AllegatiBean");
        }
        AllegatiBean allegato1 = (AllegatiBean) o1;
        AllegatiBean allegato2 = (AllegatiBean) o2;
        // Il controllo sta dentro AllegatiBeanComarator: qui metto dentro tutto
//        if (allegato1.getCodice().equalsIgnoreCase(allegato2.getCodice())) {
//            return 0;
//        }
        return -1;
    }
}
