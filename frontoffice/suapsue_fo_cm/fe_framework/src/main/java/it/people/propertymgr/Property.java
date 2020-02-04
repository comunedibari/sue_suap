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
package it.people.propertymgr;

import java.util.List;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public interface Property {
    public Object getDefaultValue();

    public String getDefaultValueString() throws PropertyFormatException;

    public String getName();

    public PropertyParser getParser();

    public String getSuffixedName();

    public List getSuffixes();

    public Object getValue();

    public Object getValue(String communeId);

    public List getValues();

    public String getValueString() throws PropertyFormatException;

    /**
     * Ritorna il valore specifico per il comune se indicato, quello generale
     * altrimenti
     * 
     * @param communeId
     *            identificativo del comune
     * @return valore della propriet�
     * @throws PropertyFormatException
     */
    public String getValueString(String communeId)
	    throws PropertyFormatException;

    public boolean isAssignableFrom(String p_str_propertyName);

    public boolean isMandatory();

    public void setRegistry(PropertyRegistry p_obj_appProperties);

    public void setValue(String p_str_propertyKey, String p_str_propertyValue)
	    throws PropertyParseException;

    public void setValue(String p_str_propertyValue)
	    throws PropertyParseException;

    public void copyTo(PropertyRegistry p_obj_registry);
}
