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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author fabmi
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class RequiredAttachmentList {
    /**
     * @clientCardinality 1
     * @supplierCardinality 0..*
     */

    private RequiredAttachmentDefinition lnkRequiredAttachmentDefinition;

    private String freeAttachmentModelPropertyName = "";
    private ArrayList reqAttachments = new ArrayList();

    /**
     * Iteratore sugli allegati obbligatori
     * 
     * @return
     */
    public Iterator iterator() {
	return this.reqAttachments.iterator();
    }

    /**
     * Rimuove la definizione di un allegato obbligatorio
     * 
     * @param definition
     * @return
     */
    public boolean remove(RequiredAttachmentDefinition definition) {
	return this.reqAttachments.remove(definition);
    }

    /**
     * Aggiunge la definizione di un allegato obbligatorio
     * 
     * @param definition
     */
    public void add(RequiredAttachmentDefinition definition) {
	this.reqAttachments.add(definition);
    }

    /**
     * Rimuove tutte le definizioni degli allegati obbligatori
     */
    public void clear() {
	this.reqAttachments.clear();
    }

    /**
     * Abilita la possibilit� di inserire allegati liberi
     * 
     * @param modelPropertyName
     *            nome della propriet� dove salvare gli allegati liberi
     */
    public void allowFreeAttachment(String modelPropertyName) {
	this.freeAttachmentModelPropertyName = modelPropertyName;
    }

    /**
     * Disabilita la possibilit� di inserire allegati liberi
     */
    public void denyFreeAttachment() {
	this.freeAttachmentModelPropertyName = "";
    }

    /**
     * Ritorna se sono ammessi allagati liberi
     * 
     * @return
     */
    public boolean isFreeAttachmentAllowed() {
	if (freeAttachmentModelPropertyName.equalsIgnoreCase(""))
	    return false;
	else
	    return true;
    }

}
