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
package it.people.console.oxm.betwixt.stemmers;

import java.util.Map;

import org.apache.commons.betwixt.strategy.PluralStemmer;
import org.apache.commons.betwixt.ElementDescriptor;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 28/gen/2012 11:09:27
 *
 */
public class ItalianPluralStemmer implements PluralStemmer {

    public ElementDescriptor findPluralDescriptor( String propertyName, Map map ) {
        int foundKeyCount = 0;
        String _subProperty = null;
        ElementDescriptor answer = null;

        _subProperty = propertyName.substring(0, propertyName.length()-1);

        if ( answer == null && propertyName.endsWith( "a" )) {
            answer = (ElementDescriptor) map.get( _subProperty + "e" );
        }
        if ( answer == null && propertyName.endsWith( "o" )) {
            answer = (ElementDescriptor) map.get( _subProperty + "i" );
        }
        if ( answer == null && propertyName.endsWith( "e" )) {
            answer = (ElementDescriptor) map.get( _subProperty + "i" );
        }
        
        if (foundKeyCount > 1) {
        	//System.out.println("More than one type matches, using closest match "+answer.getQualifiedName());
        }
        return answer;
        
    }
	
}
