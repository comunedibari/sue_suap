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
package it.people.process.common.entity;

import it.people.util.betwixt.CustomObjectStringConverter;

import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.log4j.Category;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Oct 2, 2003 Time: 7:24:00 PM To
 * change this template use Options | File Templates.
 */
public class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 4395878723274214142L;

    private transient Category cat = Category.getInstance(AbstractEntity.class
	    .getName());

    private HashMap m_changedProperty = new HashMap();

    /**
     * @deprecated ( No more needed no more PCL )
     */
    public void changed(String property) {
	m_changedProperty.put(property, new Boolean(true));
    }

    /**
     * @deprecated ( No more needed no more PCL )
     */
    public boolean unchanged(String property) {
	return m_changedProperty.remove(property) != null;
    }

    /**
     * @deprecated ( No more needed no more PCL )
     */
    public boolean isChanged(String property) {
	return m_changedProperty.get(property) != null;

    }

    /**
     * Trasforma in XML il bean contenente i dati inseriti dagli utenti
     * 
     * @return Restituisce i dati inseriti dagli utente in formato stringa
     */
    public String marshall() {
	StringWriter sw = new StringWriter();
	try {
	    BeanWriter bw = new BeanWriter(sw);
	    bw.getBindingConfiguration().setMapIDs(false);
	    bw.getXMLIntrospector().getConfiguration()
		    .setAttributesForPrimitives(false);
	    bw.enablePrettyPrint();
	    bw.writeXmlDeclaration("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	    bw.write(this);
	    bw.flush();
	    bw.close();
	} catch (Exception e) {
	    cat.error(e);
	}

	return sw.toString();
    }

    /**
     * Trasforma xml passato in ingresso in oggetto AbstractEntity
     * 
     * @param xml
     *            Stringa contenente un xml
     * @return Oggetto rappresentante i dati inseriti dagli utenti
     */
    public static AbstractEntity unmarshall(Class clazz, String xml) {
	Category cat = Category.getInstance(AbstractEntity.class.getName());
	AbstractEntity newData = null;
	try {
	    BeanReader br = new BeanReader();
	    br.getBindingConfiguration().setMapIDs(false);
	    br.registerBeanClass(clazz);

	    br.getBindingConfiguration().setObjectStringConverter(
		    new CustomObjectStringConverter());

	    newData = (AbstractEntity) br.parse(new StringReader(xml));
	} catch (Exception e) {
	    cat.error(e);
	}
	return newData;
    }
}
