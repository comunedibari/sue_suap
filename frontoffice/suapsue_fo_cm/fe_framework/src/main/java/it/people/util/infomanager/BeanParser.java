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
package it.people.util.infomanager;

import org.apache.log4j.Category;

/*
 * BeanParser.java
 *
 * Created on 29 novembre 2004, 17.06
 */

/**
 * 
 * @author mparigiani
 */
public class BeanParser {
    private it.people.util.infomanager.xmlbeans.MessaggiDocument msgDoc;
    private Category cat = Category.getInstance(this.getClass().getName());

    public BeanParser(String file) {
	try {
	    System.out.println(file);
	    msgDoc = it.people.util.infomanager.xmlbeans.MessaggiDocument.Factory
		    .parse(this.getClass().getResourceAsStream(file));
	} catch (Exception e) {
	    cat.error(e);
	}
    }

    public String findMessage(String msgId) {
	// it.people.util.infomanager.xmlbeans.MessaggiDocument msgDoc = null;

	it.people.util.infomanager.xmlbeans.MessaggiDocument.Messaggi msg = msgDoc
		.getMessaggi();
	it.people.util.infomanager.xmlbeans.MessaggiDocument.Messaggi.Messaggio[] msgArr = msg
		.getMessaggioArray();

	for (int i = 0; i < msgArr.length; i++) {
	    it.people.util.infomanager.xmlbeans.MessaggiDocument.Messaggi.Messaggio sms = msgArr[i];
	    String id = sms.getId();
	    String text = sms.getTesto();
	    if (id.equals(msgId)) {
		return text;
	    }
	}
	return "";
    }
}
