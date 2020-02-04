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
 * Created on 6-lug-2004
 *
 */
package it.people.util.table;

import it.people.annotations.DeveloperTaskEnd;
import it.people.annotations.DeveloperTaskStart;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zoppello
 * 
 */
public class OptionBean {

    private List label = null;

    private String value = null;

    /**
     * @param label
     * @param value
     */
    public OptionBean(String label, String value) {
	super();
	this.value = value;
	this.label = new ArrayList();
	this.label.add(label);
    }

    public OptionBean(List label, String value) {
	super();
	this.label = label;
	this.value = value;
    }

    public void setLabel(String l) {
	if (this.label.size() > 1)
	    this.label.set(0, l);
	else
	    this.label.add(l);
    }

    public void setLabel(String l, int i) {
	if (this.label.size() >= i + 1)
	    this.label.set(i, l);
    }

    /**
     * @return Returns the label.
     */
    public String getLabel() {
	if (label.size() > 0)
	    return (String) label.get(0);
	return "";
    }

    public String getLabel(int i) {
	if (label.size() >= i + 1)
	    return (String) label.get(i);
	return "";
    }

    /**
     * @param label
     *            The label to set.
     */
    public void setLabel(List label) {
	this.label = label;
    }

    /**
     * @return Returns the value.
     */
    public String getValue() {
	return value;
    }

    /**
     * @param value
     *            The value to set.
     */
    public void setValue(String value) {
	this.value = value;
    }

    @DeveloperTaskStart(name = "Riccardo Foraf�", date = "23.05.2011", bugDescription = "Impossibile utilizzare il metodo contains degli oggetti che implementano, "
	    + "l'interfaccia Collection.", description = "Aggiunto l'overriding del metodo"
	    + " equals.")
    @DeveloperTaskEnd(name = "Riccardo Foraf�", date = "23.05.2011")
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {

	if (!(obj instanceof OptionBean)) {
	    return false;
	}

	OptionBean objOB = (OptionBean) obj;

	return objOB.getValue().toLowerCase().trim()
		.equalsIgnoreCase(this.getValue().toLowerCase().trim())
		&& objOB.getLabel().toLowerCase().trim()
			.equalsIgnoreCase(this.getLabel().toLowerCase().trim());

    }

}
