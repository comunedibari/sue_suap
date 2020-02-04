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
 * LayoutObject.java
 *
 * Created on November 9, 2004, 4:28 PM
 */

package it.people.layout;

import it.people.process.view.ConcreteView;
import java.io.Serializable;

/**
 * LayoutObject e' la generalizzazione di un oggetto di layout per pagine JSP
 * che possa aggiornare e creare se' stesso in funzione della view associata al
 * processo stesso.
 * 
 * @author manelli
 */
public class LayoutObject implements Serializable {
    protected ConcreteView view;

    /**
     * Creates a new instance of LayoutObject
     */
    public LayoutObject() {
    }

    /**
     * Ritorna la view correntemente associata al LayoutObject
     * 
     * @return la ConcreteView associata.
     */
    public ConcreteView getView() {
	return view;
    }

    /**
     * Setta la ConcreteView associata al LayoutObject
     * 
     * @param la
     *            ConcreteView del processo.
     */
    public void setView(ConcreteView view) {
	this.view = view;
    }

    /**
     * Aggiorna la ConcreteView associata al processo. Se un discendente di
     * questa classe richiede l'aggiornamento ad ogni refresh di pagina, questo
     * e' il metodo da overridare.
     * 
     * @param la
     *            ConcreteView del processo.
     */
    public void update(ConcreteView view) {
	this.view = view;
    }
}
