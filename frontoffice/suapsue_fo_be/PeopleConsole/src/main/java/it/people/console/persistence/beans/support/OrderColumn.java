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
package it.people.console.persistence.beans.support;

import it.people.console.enumerations.OrderTypes;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 10/gen/2011 20.59.45
 *
 */
public class OrderColumn {

	private String name;
	
	private OrderTypes orderType;
	
	public OrderColumn(final String name, final OrderTypes orderType) {
		this.setName(name);
		this.setOrderType(orderType);
	}

	public final String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public final OrderTypes getOrderType() {
		return orderType;
	}

	private void setOrderType(OrderTypes orderType) {
		this.orderType = orderType;
	}
	
}
