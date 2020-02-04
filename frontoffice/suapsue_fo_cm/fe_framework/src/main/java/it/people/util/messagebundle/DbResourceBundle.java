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
package it.people.util.messagebundle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import sun.util.ResourceBundleEnumeration;

/**
 * @author Riccardo Foraf� - Engineering Ingegneria Informatica - Genova
 *         11/ago/2011 15.27.35
 */
public class DbResourceBundle extends ResourceBundle {

    @SuppressWarnings("rawtypes")
    private Map lookup;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public DbResourceBundle(ResultSet resultSet) throws SQLException {
	lookup = new HashMap();
	resultSet.beforeFirst();
	while (resultSet.next()) {
	    lookup.put(resultSet.getString(1), resultSet.getString(2));
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.ResourceBundle#handleGetObject(java.lang.String)
     */
    @Override
    protected Object handleGetObject(String key) {
	if (key == null) {
	    throw new NullPointerException();
	}
	return lookup.get(key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.ResourceBundle#getKeys()
     */
    @SuppressWarnings("unchecked")
    @Override
    public Enumeration<String> getKeys() {
	ResourceBundle parent = this.parent;
	return new ResourceBundleEnumeration(lookup.keySet(),
		(parent != null) ? parent.getKeys() : null);
    }

}
