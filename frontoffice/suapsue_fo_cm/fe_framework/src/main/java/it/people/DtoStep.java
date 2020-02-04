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
package it.people;

import it.people.core.Logger;
import it.people.core.PplACE;
import it.people.core.exception.MappingException;
import it.people.process.PplData;
import it.people.process.dto.PeopleDto;
import it.people.util.dto.IAdapter;
import it.people.util.dto.adapters.AnyToStringAdapter;
import it.people.util.dto.adapters.NoneAdapter;
import it.people.util.frontend.WorkflowController;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Category;

public abstract class DtoStep extends Step {

    private Category cat = Category.getInstance(DtoStep.class.getName());

    /**
     *
     */
    public DtoStep() {
	super();
    }

    /**
     * @param p_jspPath
     * @param p_helpUrl
     * @param state
     */
    public DtoStep(String p_jspPath, String p_helpUrl, StepState state) {
	super(p_jspPath, p_helpUrl, state);

    }

    /**
     * @param p_jspPath
     * @param p_helpUrl
     * @param controller
     */
    public DtoStep(String p_jspPath, String p_helpUrl,
	    WorkflowController controller) {
	super(p_jspPath, p_helpUrl, controller);

    }

    /**
     * @param p_jspPath
     * @param p_helpUrl
     * @param state
     * @param controller
     */
    public DtoStep(String p_jspPath, String p_helpUrl, StepState state,
	    WorkflowController controller) {
	super(p_jspPath, p_helpUrl, state, controller);

    }

    /**
     * @param m_jspPath
     * @param m_helpUrl
     * @param state
     * @param controller
     * @param ACL
     */
    public DtoStep(String m_jspPath, String m_helpUrl, StepState state,
	    WorkflowController controller, PplACE[] ACL) {
	super(m_jspPath, m_helpUrl, state, controller, ACL);

    }

    public abstract void writeDto(PplData data, PeopleDto dto)
	    throws MappingException;

    public abstract void readDto(PplData data, PeopleDto dto)
	    throws MappingException;

    public String getPropertyFromData(PplData data, String property) {

	Object value = null;
	try {
	    value = PropertyUtils.getProperty(data, property);
	} catch (Exception e) {
	    cat.error("Failed read from [" + property
		    + "]  Property does not exist in PplData", e);
	}

	if (value == null)
	    return null;
	else
	    return String.valueOf(value);
    }

    public String getPropertyFromDto(PeopleDto dto, String property) {

	Object value = null;
	try {
	    value = PropertyUtils.getProperty(dto, property);
	} catch (Exception e) {
	    cat.error("Failed read from [" + property
		    + "]  Property does not exist in PplDto", e);
	}

	if (value == null)
	    return null;
	else
	    return String.valueOf(value);
    }

    public void copyInTo(PeopleDto dto, PplData data, String dtoPropertyName,
	    String dataPropertyName) {
	genericCopy(data, dto, dataPropertyName, dtoPropertyName,
		new AnyToStringAdapter());
    }

    public void copyInPplData(PeopleDto dto, PplData data,
	    String dtoPropertyName, String dataPropertyName, IAdapter adapter) {

	genericCopy(dto, data, dtoPropertyName, dataPropertyName, adapter);

    }

    public void copyInPplData(PeopleDto dto, PplData data,
	    String dtoPropertyName, String dataPropertyName) {
	copyInPplData(dto, data, dtoPropertyName, dataPropertyName,
		new NoneAdapter());
    }

    public void genericCopy(Object sourceObject, Object targetObject,
	    String sourceProperty, String targetProperty, IAdapter dtoAdapter) {

	Logger.debug("Generic Copy of [" + sourceProperty + "] to ["
		+ targetProperty + "]");
	Object valueToCopy = null;
	try {
	    valueToCopy = PropertyUtils.getProperty(sourceObject,
		    sourceProperty);
	} catch (Exception e) {
	    cat.error(
		    "Failed to retrieve [" + sourceProperty + "] - "
			    + e.getMessage(), e);
	}

	Object adaptedValueToCopy = dtoAdapter.adapt(valueToCopy);

	try {
	    PropertyUtils.setProperty(targetObject, targetProperty,
		    adaptedValueToCopy);
	} catch (Exception e) {
	    cat.error(
		    "Failed to copy into [" + targetProperty + "] - "
			    + e.getMessage(), e);
	}

    }
}
