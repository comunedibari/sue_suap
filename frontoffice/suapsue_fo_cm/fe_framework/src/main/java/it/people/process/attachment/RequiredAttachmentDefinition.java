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
/*
 * Created on 10-feb-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.process.attachment;

/**
 * @author fabmi
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class RequiredAttachmentDefinition {
    private String description;
    private boolean required;
    private String modelPropertyName;
    private boolean signRequired;

    /**
     * @return Returns the description.
     */
    public String getDescription() {
	return description;
    }

    /**
     * @param description
     *            The description to set.
     */
    public void setDescription(String description) {
	this.description = description;
    }

    /**
     * @return Returns the modelPropertyName.
     */
    public String getModelPropertyName() {
	return modelPropertyName;
    }

    /**
     * @param modelPropertyName
     *            The modelPropertyName to set.
     */
    public void setModelPropertyName(String modelPropertyName) {
	this.modelPropertyName = modelPropertyName;
    }

    /**
     * @return Returns the required.
     */
    public boolean isRequired() {
	return required;
    }

    /**
     * @param required
     *            The required to set.
     */
    public void setRequired(boolean required) {
	this.required = required;
    }

    /**
     * @return Returns the signRequired.
     */
    public boolean isSignRequired() {
	return signRequired;
    }

    /**
     * @param signRequired
     *            The signRequired to set.
     */
    public void setSignRequired(boolean signRequired) {
	this.signRequired = signRequired;
    }
}
