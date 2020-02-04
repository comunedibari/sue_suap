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
package it.people.console.beans.support;

import java.util.Vector;

import it.people.console.enumerations.IOperatorsEnum;
import it.people.console.enumerations.Types;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 03/dic/2010 09.13.44
 *
 */
public class TextColumnFilter extends AbstractFilter {

	public TextColumnFilter(String label, String name, Types type, Vector<IOperatorsEnum> filterAllowedOperators) {
		super(filterAllowedOperators);
		this.setLabel(label);
		this.setName(name);
		this.setType(type);
	}

	/* (non-Javadoc)
	 * @see it.people.console.beans.support.AbstractFilter#setActive(boolean)
	 */
	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	/* (non-Javadoc)
	 * @see it.people.console.beans.support.AbstractFilter#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.beans.support.AbstractFilter#setName(java.lang.String)
	 */
	@Override
	protected void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see it.people.console.beans.support.AbstractFilter#setOperator(java.lang.String)
	 */
	@Override
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/* (non-Javadoc)
	 * @see it.people.console.beans.support.AbstractFilter#setType(java.lang.String)
	 */
	@Override
	protected void setType(Types type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see it.people.console.beans.support.AbstractFilter#setValue(java.lang.String)
	 */
	@Override
	public void setValue(String value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see it.people.console.beans.support.AbstractFilter#setShowOperators(boolean)
	 */
	@Override
	public void setShowOperators(boolean showOperators) {
		this.showOperators = showOperators;
	}

	/* (non-Javadoc)
	 * @see it.people.console.beans.support.AbstractFilter#setLabel(java.lang.String)
	 */
	@Override
	protected void setLabel(String label) {
		this.label = label;
	}
	
}
