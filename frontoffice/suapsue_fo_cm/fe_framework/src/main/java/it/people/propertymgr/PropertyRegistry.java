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

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

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

public interface PropertyRegistry {

    public void addProperty(Property p_obj_property);

    public Property getProperty(String p_str_name);

    public void load(Properties p_obj_properties) throws PropertyParseException;

    public void load(Properties p_obj_properties, boolean p_b_whole)
	    throws PropertyParseException;

    public void load(InputStream p_obj_properties)
	    throws PropertyParseException;

    public void load(InputStream p_obj_properties, boolean p_b_whole)
	    throws PropertyParseException;

    public void removeProperty(Property p_obj_property);

    public void store(Properties p_obj_properties, boolean p_b_whole)
	    throws PropertyFormatException;

    public void store(Properties p_obj_properties)
	    throws PropertyFormatException;

    public void store(OutputStream p_obj_properties, boolean p_b_whole)
	    throws PropertyFormatException;

    public void store(OutputStream p_obj_properties)
	    throws PropertyFormatException;
}
