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

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * @author Riccardo Foraf� - Engineering Ingegneria Informatica - Genova
 *         11/ago/2011 10.02.03
 */
public class UnpackedBundleName {

    private String framework = null;

    private String process = null;

    private String nodeId = null;

    private String locale = null;

    /**
     * @param process
     * @param nodeId
     * @param locale
     */
    public UnpackedBundleName(final String framework, final String process,
	    final String nodeId, final String locale) {
	this.framework = framework;
	this.process = process;
	this.nodeId = nodeId;
	this.locale = locale;
    }

    /**
     * @return the process
     */
    public final String getProcess() {
	return this.process;
    }

    /**
     * @return the nodeId
     */
    public final String getNodeId() {
	return this.nodeId;
    }

    /**
     * @return the locale
     */
    public final String getLocale() {
	return this.locale;
    }

    /**
     * @return the framework
     */
    public final String getFramework() {
	return this.framework;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
	return new StringBuilder().append("[")
		.append("Framework = '" + framework + "';")
		.append("Process = '" + process + "';")
		.append("Node id = '" + nodeId + "';")
		.append("Locale = '" + locale + "';").append("]").toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object object) {
	if (!(object instanceof UnpackedBundleName)) {
	    return false;
	}
	UnpackedBundleName other = (UnpackedBundleName) object;
	return new EqualsBuilder().append(this.framework, other.framework)
		.append(this.process, other.process)
		.append(this.nodeId, other.nodeId)
		.append(this.locale, other.locale).isEquals();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
	return new HashCodeBuilder(45, 87).append(this.framework)
		.append(this.process).append(this.nodeId).append(this.locale)
		.hashCode();
    }

}
