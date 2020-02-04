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
package it.people.util;

import it.people.parser.FieldValidator;
import it.people.process.PplObserver;

import org.apache.commons.beanutils.DynaProperty;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Sep 9, 2003 Time: 2:04:08 PM To
 * change this template use Options | File Templates.
 */
public class PplProperty extends DynaProperty {
    private FieldValidator m_validator;
    private PplObserver m_observer;

    public PplProperty(String name, Class type, FieldValidator validator,
	    PplObserver observer) {
	super(name, type);
	m_validator = validator;
	m_observer = observer;
    }

    public FieldValidator getValidator() {
	return m_validator;
    }

    public void setValidator(FieldValidator p_validator) {
	m_validator = p_validator;
    }

    public PplObserver getObserver() {
	return m_observer;
    }

    public void setObserver(PplObserver p_observer) {
	m_observer = p_observer;
    }
}
