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
 * @author Riccardo Forafo'
 * @version 1.0
 * @created 29-nov-2010 21:54:59
 */
public interface IFilter {

	public String getLabel();
	
	public String getName();

	public String getOperator();

	public void setOperator(String operator);
	
	public String getValue();

	public void setValue(String value);
	
	public Types getType();

	public boolean isVisible();
	
	public boolean isActive();
	
	public boolean isShowOperators();

	public void setVisible(boolean visible);
	
	public void setActive(boolean active);
	
	public void setShowOperators(boolean showOperators);
	
	public Vector<IOperatorsEnum> getFilterAllowedOperators();

	public void setFilterAllowedOperators(Vector<IOperatorsEnum> filterAllowedOperators);
	
	public void addFilterAllowedOperator(IOperatorsEnum operator);
	
	public boolean isListable();
		
}
