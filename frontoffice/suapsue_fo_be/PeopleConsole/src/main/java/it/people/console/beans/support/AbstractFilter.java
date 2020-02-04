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
import it.people.console.utils.StringUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 03/dic/2010 10.09.32
 *
 */
public abstract class AbstractFilter implements IFilter {

	protected Vector<IOperatorsEnum> filterAllowedOperators = null;
	
	protected boolean showOperators = true;
	
	protected boolean active = false;

	protected boolean visible = false;
	
	protected String label;
	
	protected String name;
	
	protected String operator = "";
	
	protected Types type;
	
	protected String value = "";
	
	protected AbstractFilter(Vector<IOperatorsEnum> filterAllowedOperators) {
		this.setFilterAllowedOperators(filterAllowedOperators);
	}
	
	public String getLabel() {
		return this.label;
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.beans.support.IFilter#getName()
	 */
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see it.people.console.beans.support.IFilter#getOperator()
	 */
	public String getOperator() {
		return this.operator;
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.beans.support.IFilter#getType()
	 */
	public Types getType() {
		return this.type;
	}

	/* (non-Javadoc)
	 * @see it.people.console.beans.support.IFilter#getValue()
	 */
	public String getValue() {
		return StringUtils.nullToEmpty(this.value);
	}

	/* (non-Javadoc)
	 * @see it.people.console.beans.support.IFilter#isActive()
	 */
	public boolean isActive() {
		return this.active;
	}

	/* (non-Javadoc)
	 * @see it.people.console.beans.support.IFilter#isVisible()
	 */
	public boolean isVisible() {
		return this.visible;
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.beans.support.IFilter#isShowOperators()
	 */
	public boolean isShowOperators() {
		return this.showOperators;
	}

	/* (non-Javadoc)
	 * @see it.people.console.beans.support.IFilter#getFilterAllowedOperators()
	 */
	public Vector<IOperatorsEnum> getFilterAllowedOperators() {
		if (this.filterAllowedOperators == null) {
			this.filterAllowedOperators = new Vector<IOperatorsEnum>();
		}
		return this.filterAllowedOperators;
	}

	/* (non-Javadoc)
	 * @see it.people.console.beans.support.IFilter#setFilterAllowedOperators(java.util.Vector)
	 */
	public void setFilterAllowedOperators(
			Vector<IOperatorsEnum> filterAllowedOperators) {
		this.filterAllowedOperators = filterAllowedOperators;
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.beans.support.IFilter#addFilterAllowedOperator(it.people.console.enumerations.IOperatorsEnum)
	 */
	public void addFilterAllowedOperator(IOperatorsEnum operator) {
		this.getFilterAllowedOperators().add(operator);
	}
	
	public boolean isListable() {

		boolean result = false;
		
		Class<?>[] filterObjectInterfaces = this.getClass().getInterfaces();
		for(Class<?> clazz : filterObjectInterfaces) {
			if (clazz.isInterface() && clazz.getCanonicalName().equalsIgnoreCase(
					ListableFilter.class.getCanonicalName())) {
				result = true;
				break;
			}
		}
		
		return result;
		
	}

	protected abstract void setLabel(String label);
	
	protected abstract void setName(String name);

	public abstract void setOperator(String operator);

	protected abstract void setType(Types type);

	public abstract void setValue(String value);

	public abstract void setActive(boolean active);

	public abstract void setVisible(boolean visible);
	
	public abstract void setShowOperators(boolean showOperators);
	
}
