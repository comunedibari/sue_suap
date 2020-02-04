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
 * Created on 19-ott-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.taglib;

import javax.servlet.jsp.JspException;
import org.apache.struts.taglib.html.LinkTag;
import org.apache.struts.taglib.logic.IterateTag;

/**
 * @author FabMi Il Tag ha la funzione di inserire il controllo per
 *         l'eliminazione di oggetti da una lista. Gli attributi pi�
 *         importanti sono: property: nome della propriet� in cui inserire
 *         l'oggetto (obbligatorio) propertyIndex: indice dell'elemento da
 *         rimuovere indexed: indica se l'indice deve essere calcolato
 *         automaticamente, funziona solo se inserito il tag � inserito in un
 *         controllo logic:iterate
 * 
 *         Gli attributi propertyIndex e indexed si escludono a vicenda, ovvero
 *         se specificato l'attributo indexed questo ha la priorit�.
 * 
 *         Se non sono indicati nessuno dei due attributi propertyIndex e
 *         indexed, alla action removeObject non � passato nessun indice.
 */
public class LinkLoopbackTag extends LinkTag {
    protected final static String LOOPBACK_ACTION_NAME = "loopBack";
    protected boolean indexed = false;
    protected String property = null;

    // � necessario che sia stringa per capire quando
    // il valore � effettivamente valorizzato
    protected String propertyIndex;

    public LinkLoopbackTag() {
	super();
    }

    public int doStartTag() throws JspException {
	String indexParameterString = "";
	boolean addIndex = false;

	String actionUrl = LOOPBACK_ACTION_NAME + ".do" + "?propertyName="
		+ this.property;

	// Se specificato l'attributo indexed ha la priorit�
	// nella generazione dell'indice.
	// Se � specificato un propertyIndex ma non indexed,
	// � usato il valore del propertyIndex specificato.
	// Se non � indicato nulla la proprit� non � valorizzata
	// poich� la action removeObject gestisce anche il caso
	// senza parametro
	if (this.indexed && this.getParent() instanceof IterateTag) {
	    // IterateTag.getIndex()
	    // Return the zero-relative index of the current iteration through
	    // the loop.
	    // If you specify an offset, the first iteration through the loop
	    // will have that value;
	    // otherwise, the first iteration will return zero.
	    // This property is read-only, and gives nested custom tags access
	    // to this information.
	    // Therefore, it is only valid in between calls to doStartTag() and
	    // doEndTag().
	    IterateTag iterateTag = (IterateTag) this.getParent();
	    actionUrl += "&amp;index=" + iterateTag.getIndex();
	} else if (propertyIndex != null)
	    actionUrl += "&amp;index=" + propertyIndex;

	this.setAction(actionUrl);
	return super.doStartTag();
    }

    // � utile nel caso l'indice sulla pagina jsp sia un int,
    // cosa possibile quando l'incremento dell'indice � fatto sulla pagina
    public void setPropertyIndex(int value) {
	this.propertyIndex = "" + value;
    }

    public void setPropertyIndex(String value) {
	this.propertyIndex = value;
    }

    public void setProperty(String value) {
	this.property = value;
    }

    public void setIndexed(boolean value) {
	this.indexed = value;
    }
}
