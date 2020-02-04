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
package it.people.util.debug;

import it.people.core.PplDelegate;
import it.people.core.PplPrincipal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import org.apache.log4j.Category;

/**
 * User: Luigi Corollo Date: 11-dic-2003 Time: 10.58.10 <br>
 * <br>
 * Classe d'utilita' per il debug.
 */
public class Debugger {
    private Category cat = Category.getInstance(this.getClass().getName());

    /**
     * printObject (Object o)
     * 
     */
    static public String printObject(Object o) {
	Class c = o.getClass();
	Method[] publicMethods = c.getDeclaredMethods();
	boolean isGetter = false;

	StringBuffer out = new StringBuffer();

	out.append("\n ******** "
		+ c.getName().substring((c.getName().lastIndexOf(".") + 1))
		+ " ************ \n");

	for (int i = 0; i < publicMethods.length; i++) {
	    String methodName = publicMethods[i].getName();
	    String methodValue = "";

	    try {
		Object[] arguments = new Object[0];
		isGetter = methodName.startsWith("get");

		if (isGetter)
		    methodValue = "" + publicMethods[i].invoke(o, arguments)
			    + "  ";
	    } catch (IllegalAccessException e) {
		// cat.error(e);
	    } catch (IllegalArgumentException e) {
		// cat.error(e);
	    } catch (InvocationTargetException e) {
		// cat.error(e);
	    }

	    if (isGetter)
		out.append(" " + methodName + " = " + methodValue + "\n");
	}
	out.append(" ******************** \n");
	return out.toString();
    }

    /**
     * printCollection (Collection coll)
     * 
     */
    static public String printCollection(Collection coll) {
	StringBuffer out = new StringBuffer();
	out.append(" ******* COLLECTION ******* \n");

	Iterator it = coll.iterator();
	while (it.hasNext()) {
	    out.append("- " + it.next().toString() + "\n");
	}

	out.append(" ************************** \n");
	return out.toString();
    }

    /**
     * printHashTable
     * 
     */
    static public String printHashTable(Hashtable hashtable) {
	StringBuffer out = new StringBuffer();
	out.append("\n ---- Debug HashTable:  ---- \n");

	Enumeration keys = hashtable.keys();
	Enumeration values = hashtable.elements();

	while (keys.hasMoreElements()) {
	    out.append(" (" + (String) keys.nextElement() + ", "
		    + values.nextElement() + ")\n");
	}
	out.append(" ------------------------- \n");
	return out.toString();
    }

    /**
     * printMap
     * 
     */
    static public String printMap(Map map) {
	StringBuffer out = new StringBuffer();
	out.append("\n ----- Debug Map:  ----- \n");

	Set entries = map.entrySet();
	Iterator iterator = entries.iterator();

	while (iterator.hasNext()) {
	    Map.Entry entry = (Map.Entry) iterator.next();
	    String value = "" + entry.getValue().toString();
	    out.append("" + entry.getKey() + " = " + value + "\n");
	}
	out.append(" ------------------------- \n");
	return out.toString();
    }

    /**
     * printRequestParameters
     * 
     */
    static public String printRequestParameters(
	    javax.servlet.ServletRequest request) {
	StringBuffer out = new StringBuffer();
	out.append("\n ---- Debug Request Parameters:  ---- \n");
	Enumeration paramNames = request.getParameterNames();

	while (paramNames.hasMoreElements()) {
	    String nome_par = (String) paramNames.nextElement();
	    String[] valore_par = request.getParameterValues(nome_par);
	    String valore = "";

	    for (int i = 0; i < valore_par.length; i++) {
		valore = valore + (i > 0 ? "," : "") + valore_par[i];
	    }
	    out.append(" " + nome_par + " = " + valore + "\n");
	}
	out.append(" ------------------------------------ \n");
	return out.toString();
    }

    /**
     * printSessionVars
     * 
     */
    static public String printSessionVars(HttpSession session) {
	StringBuffer out = new StringBuffer();
	out.append("\n ----- Debug Session Vars:  ----- \n");
	Enumeration paramNames = session.getAttributeNames();

	while (paramNames.hasMoreElements()) {
	    String nome_var = (String) paramNames.nextElement();
	    String valore_var = "" + session.getAttribute(nome_var);

	    out.append(" " + nome_var + " = " + valore_var + "\n");
	}
	out.append(" ------------------------------------ \n");
	return out.toString();
    }

    /**
     * printJspPageContext
     * 
     */
    static public String printJspPageContext(PageContext pageContext) {
	StringBuffer out = new StringBuffer();
	out.append("\n ----- Debug Jsp PageContext:  ----- \n");

	Enumeration paramNames = null;
	String scope = "";

	// Applicazione
	scope = "A";
	paramNames = pageContext
		.getAttributeNamesInScope(PageContext.APPLICATION_SCOPE);
	while (paramNames.hasMoreElements()) {
	    String nome_var = (String) paramNames.nextElement();
	    String valore_var = ""
		    + pageContext.getAttribute(nome_var,
			    PageContext.APPLICATION_SCOPE);

	    out.append(" " + scope + " " + nome_var + " = " + valore_var + "\n");
	}

	// Page
	scope = "P";
	paramNames = pageContext
		.getAttributeNamesInScope(PageContext.PAGE_SCOPE);
	while (paramNames.hasMoreElements()) {
	    String nome_var = (String) paramNames.nextElement();
	    String valore_var = ""
		    + pageContext
			    .getAttribute(nome_var, PageContext.PAGE_SCOPE);

	    out.append(" " + scope + " " + nome_var + " = " + valore_var + "\n");
	}

	// Request
	scope = "R";
	paramNames = pageContext
		.getAttributeNamesInScope(PageContext.REQUEST_SCOPE);
	while (paramNames.hasMoreElements()) {
	    String nome_var = (String) paramNames.nextElement();
	    String valore_var = ""
		    + pageContext.getAttribute(nome_var,
			    PageContext.REQUEST_SCOPE);

	    out.append(" " + scope + " " + nome_var + " = " + valore_var + "\n");
	}

	// Session
	scope = "S";
	paramNames = pageContext
		.getAttributeNamesInScope(PageContext.SESSION_SCOPE);
	while (paramNames.hasMoreElements()) {
	    String nome_var = (String) paramNames.nextElement();
	    String valore_var = ""
		    + pageContext.getAttribute(nome_var,
			    PageContext.SESSION_SCOPE);

	    out.append(" " + scope + " " + nome_var + " = " + valore_var + "\n");
	}

	out.append(" ------------------------------------ \n");
	return out.toString();
    }

    /**
     * printArray
     * 
     */
    static public String printArray(Object[] array) {
	StringBuffer out = new StringBuffer();
	out.append("\n -------- Debug Array:  --------- \n");

	for (int i = 0; i < array.length; i++) {
	    if (array[i] instanceof PplPrincipal)
		out.append("[" + i + "] principal: "
			+ ((PplPrincipal) array[i]).getUserID() + "\n");
	    else if (array[i] instanceof PplDelegate)
		out.append("[" + i + "] delegate: "
			+ ((PplDelegate) array[i]).getDelegateID() + "\n");
	    else
		out.append("[" + i + "] " + array[i].toString() + "\n");
	}

	out.append(" ------------------------------------ \n");
	return out.toString();
    }

    /**
     * printHeaders
     * 
     */
    static public String printHeaders(HttpServletRequest request) {
	StringBuffer out = new StringBuffer();
	out.append("\n ---- Debug Request Headers:  ---- \n");
	Enumeration paramNames = request.getHeaderNames();

	while (paramNames.hasMoreElements()) {
	    String nome_par = (String) paramNames.nextElement();
	    String valore = request.getHeader(nome_par);

	    out.append(" " + nome_par + " = " + valore + "\n");
	}
	out.append(" ------------------------------------ \n");
	return out.toString();
    }
}
