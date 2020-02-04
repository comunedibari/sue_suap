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
 * Created on 29-giu-2004
 *
 */
package it.people.process.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.log4j.Category;

/**
 * @author Bernabei
 * 
 */
public class PeopleDynaBean implements DynaBean {
    private Category cat = Category.getInstance(this.getClass().getName());
    private DynaBean dto = null;

    public PeopleDynaBean() {

	// da configurare dinamicamente a seconda del procedimento
	DynaProperty[] props = new DynaProperty[] {
		new DynaProperty("nome", java.lang.String.class),
		new DynaProperty("cognome", java.lang.String.class),
		new DynaProperty("anni", java.util.ArrayList.class),
		new DynaProperty("materie",
			org.apache.commons.beanutils.BasicDynaBean.class) };
	// ...........
	BasicDynaClass dynaClass = new BasicDynaClass("dto", null, props);

	// da configurare dinamicamente a seconda del procedimento
	DynaProperty[] props2 = new DynaProperty[] {
		new DynaProperty("nome", java.lang.String.class),
		new DynaProperty("cognome", java.lang.String.class), };
	// ...........
	BasicDynaClass dynaClass2 = new BasicDynaClass("dto", null, props);

	// da configurare dinamicamente a seconda del procedimento
	DynaProperty[] props3 = new DynaProperty[] {
		new DynaProperty("nome", java.lang.String.class),
		new DynaProperty("cognome", java.lang.String.class), };
	// ...........
	BasicDynaClass dynaClass3 = new BasicDynaClass("dto", null, props);
	try {
	    dto = dynaClass.newInstance();
	    DynaBean dto2 = dynaClass2.newInstance();
	    DynaBean dto3 = dynaClass3.newInstance();
	    dto.set("nome", "PIPPO");
	    dto.set("materie", dto2);
	    ((DynaBean) dto.get("materie")).set("nome", "PLUTO");
	    dto.set("anni", new ArrayList());
	    ((Collection) dto.get("anni")).add(dto3);
	} catch (IllegalAccessException e) {
	    cat.error(e);
	} catch (InstantiationException e) {
	    cat.error(e);
	}

    }

    /**
     * @param arg0
     * @param arg1
     * @return
     */
    public boolean contains(String arg0, String arg1) {
	return dto.contains(arg0, arg1);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
	return dto.equals(obj);
    }

    /**
     * @param arg0
     * @return
     */
    public Object get(String arg0) {
	return dto.get(arg0);
    }

    /**
     * @param arg0
     * @param arg1
     * @return
     */
    public Object get(String arg0, int arg1) {
	return dto.get(arg0, arg1);
    }

    /**
     * @param arg0
     * @param arg1
     * @return
     */
    public Object get(String arg0, String arg1) {
	return dto.get(arg0, arg1);
    }

    /**
     * @return
     */
    public DynaClass getDynaClass() {
	return dto.getDynaClass();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
	return dto.hashCode();
    }

    /**
     * @param arg0
     * @param arg1
     */
    public void remove(String arg0, String arg1) {
	dto.remove(arg0, arg1);
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     */
    public void set(String arg0, int arg1, Object arg2) {
	dto.set(arg0, arg1, arg2);
    }

    /**
     * @param arg0
     * @param arg1
     */
    public void set(String arg0, Object arg1) {
	dto.set(arg0, arg1);
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     */
    public void set(String arg0, String arg1, Object arg2) {
	dto.set(arg0, arg1, arg2);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
	return dto.toString();
    }
}
