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
package it.people.util.params;

import it.people.City;
import it.people.process.AbstractPplProcess;
import it.people.util.PeopleProperties;
import it.people.validator.ValidatorLoader;

/**
 * @author Zoppello
 * 
 */
public class ParameterHelper implements IParamHelper {

    private IParamHelper strategy = null;

    private ParameterHelper(IParamHelper strategy) {
	this.strategy = strategy;
    }

    private static ParameterHelper instance = null;

    /**
     * @deprecated utilizzare getInstance(City city)
     * @return
     */
    public static ParameterHelper getInstance() {
	return getInstance(null);
    }

    public static ParameterHelper getInstance(City commune) {
	IParamHelper strategy = null;
	if (instance == null) {
	    synchronized (ValidatorLoader.class) {
		if (instance == null) {
		    try {
			String parameterLoaderClass = "";
			if (commune == null)
			    parameterLoaderClass = PeopleProperties.PARAMETER_LOADER_CLASS
				    .getValueString();
			else
			    parameterLoaderClass = PeopleProperties.PARAMETER_LOADER_CLASS
				    .getValueString(commune.getKey());

			strategy = (IParamHelper) Class.forName(
				parameterLoaderClass).newInstance();
			if (strategy == null)
			    strategy = new PropertiesParamHelperStrategy();
			instance = new ParameterHelper(strategy);
		    } // try
		    catch (Exception ex) {
		    }
		} // if (_instance == null)
	    } // synchronized()
	} // if (_instance == null)
	return instance;
    }

    /**
     * @see it.people.util.params.IParamHelper#getParameter(it.people.process.AbstractPplProcess,
     *      java.lang.String)
     */
    public String getParameter(AbstractPplProcess process, String paramName)
	    throws ParamNotFoundException {

	return strategy.getParameter(process, paramName);
    }

    /**
     * @see it.people.util.params.IParamHelper#paramExist(it.people.process.AbstractPplProcess,
     *      java.lang.String)
     */
    public boolean paramExist(AbstractPplProcess process, String paramName) {
	return strategy.paramExist(process, paramName);
    }
}
