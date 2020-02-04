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
 * Created on 30-lug-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.util.dto.beancopier;

import it.people.util.dto.IAdapter;
import it.people.util.dto.IMonitor;
import it.people.util.dto.monitors.NullObjectMonitor;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author Zoppello
 */
public class BeanCopier {
    private Object sourceObject = null;
    private Object targetObject = null;
    private List rules = null;
    private IMonitor monitor = null;

    public BeanCopier() {
	super();
    }

    public BeanCopier(Object sourceObject, Object targetObject) {
	this.sourceObject = sourceObject;
	this.targetObject = targetObject;

    }

    public BeanCopier(Object sourceObject, Object targetObject, List rules) {
	this.sourceObject = sourceObject;
	this.targetObject = targetObject;
	this.rules = rules;
	monitor = new NullObjectMonitor();
    }

    public BeanCopier(Object sourceObject, Object targetObject, IMonitor monitor) {
	this.sourceObject = sourceObject;
	this.targetObject = targetObject;
	this.monitor = monitor;

    }

    public BeanCopier(Object sourceObject, Object targetObject, List rules,
	    IMonitor monitor) {
	this.sourceObject = sourceObject;
	this.targetObject = targetObject;
	this.rules = rules;
	this.monitor = monitor;
    }

    /**
     * @return Returns the rules.
     */
    public List getRules() {
	return rules;
    }

    /**
     * @param rules
     *            The rules to set.
     */
    public void setRules(List rules) {
	this.rules = rules;
    }

    /**
     * @return Returns the sourceObject.
     */
    public Object getSourceObject() {
	return sourceObject;
    }

    /**
     * @param sourceObject
     *            The sourceObject to set.
     */
    public void setSourceObject(Object sourceObject) {
	this.sourceObject = sourceObject;
    }

    /**
     * @return Returns the targetObject.
     */
    public Object getTargetObject() {
	return targetObject;
    }

    /**
     * @param targetObject
     *            The targetObject to set.
     */
    public void setTargetObject(Object targetObject) {
	this.targetObject = targetObject;
    }

    /**
     * @return Returns the monitor.
     */
    public IMonitor getMonitor() {
	return monitor;
    }

    /**
     * @param monitor
     *            The monitor to set.
     */
    public void setMonitor(IMonitor monitor) {
	this.monitor = monitor;
    }

    public void copy() {

	if (rules != null) {
	    Iterator rulesIterator = rules.iterator();
	    Rule aRule = null;
	    Object sourceValue = null;
	    Object targetValue = null;
	    IAdapter aAdapter = null;
	    String sourcePropertyName = null;
	    String targetPropertyName = null;
	    while (rulesIterator.hasNext()) {
		aRule = (Rule) rulesIterator.next();
		sourcePropertyName = aRule.getSourceProperty();
		targetPropertyName = aRule.getTargetProperty();
		aAdapter = aRule.getAdapter();
		// Ricavo il valore della property

		try {
		    sourceValue = PropertyUtils.getProperty(sourceObject,
			    sourcePropertyName);
		    targetValue = sourceValue;
		    if (aAdapter != null) {
			targetValue = aAdapter.adapt(sourceValue);
		    }
		    monitor.debug(this.getClass(), " [" + sourcePropertyName
			    + "] -> [" + targetPropertyName + "] : {["
			    + sourceValue + "] -> [" + targetValue + "]}");
		    // monitor.debug(this.getClass()," ["+sourceValue+"] adapted to ["
		    // + targetValue + "]");
		    PropertyUtils.setProperty(targetObject, targetPropertyName,
			    targetValue);
		} catch (Exception e) {
		    monitor.error(this.getClass(), e);
		}
	    }
	} else {
	    monitor.warn(this.getClass(), " No Rules Found no copy performed");
	}
    }
}
