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
package it.people.envelope.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import it.people.envelope.util.EnvelopeHelper;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 * 23/gen/2012 22:28:02
 */
public class Test1 {

	@Test
	public final void test() {

		try {
			InputStream is = this.getClass().getResourceAsStream("envelopeXML");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] isBytes;
				isBytes = new byte[is.available()];
			baos.write(isBytes);
			baos.flush();
			
			assertTrue("Valid xml", EnvelopeHelper.parseXMLAndValidate(baos.toString(), EnvelopeHelper.getDefaultXmlOptions()));
			
			baos.close();
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
