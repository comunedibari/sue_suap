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
/**
 * 
 */
package it.people.gateway;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import it.ictechnology.certificate.parser.CertificateParser;
import it.ictechnology.certificate.parser.impl.ParserFactory;
import it.infocamere.util.signature.SignedDocument;
import it.infocamere.util.signature.impl.bouncyCastle.BCSafeSignatureFactory;
import it.infocamere.util.signature.impl.bouncyCastle.BCSignatureFactory;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 * 06/nov/2012 08:41:34
 */
public class SignatureFactory implements ISignatureFactory {

	private static SignatureFactory instance = null;
	
	private BCSignatureFactory bcSignatureFactory;
	private BCSafeSignatureFactory bcSafeSignatureFactory;
	private static boolean useSafeFactory = true;
	
	private SignatureFactory (boolean useSafeFactory) throws SAXException, IOException, URISyntaxException {
		
		SignatureFactory.setUseSafeFactory(useSafeFactory);
		
		bcSignatureFactory = new BCSignatureFactory();
		bcSafeSignatureFactory = new BCSafeSignatureFactory();
		URL certificateDescriptions = SignatureFactory.class.getResource("/it/people/resources/certificate-descriptions.xml");

		CertificateParser certificateParser = new ParserFactory().parse(new InputSource(certificateDescriptions.toURI().toString()));
		
		bcSignatureFactory.setCertificateParser(certificateParser);
		bcSafeSignatureFactory.setCertificateParser(certificateParser);
		
	}
	
	private BCSignatureFactory getBCSignatureFactory() {
		return this.bcSignatureFactory;
	}

	private BCSafeSignatureFactory getBCSafeSignatureFactory() {
		return this.bcSafeSignatureFactory;
	}
	
	private static boolean isUseSafeFactory() {
		return useSafeFactory;
	}

	private static void setUseSafeFactory(boolean useSafeFactory) {
		SignatureFactory.useSafeFactory = useSafeFactory;
	}

	public static final SignatureFactory getInstance(boolean useSafeFactory) throws SAXException, IOException, URISyntaxException {
		
		if (SignatureFactory.instance == null) {
			SignatureFactory.instance = new SignatureFactory(useSafeFactory);
		}
		
		return instance;
		
	}

	/* (non-Javadoc)
	 * @see it.people.gateway.ISignatureFactory#createDocument(byte[])
	 */
	public SignedDocument createDocument(byte[] bytes) {
		return SignatureFactory.isUseSafeFactory() ? this.getBCSafeSignatureFactory().createDocument(bytes) : this.getBCSignatureFactory().createDocument(bytes);
	}

	/* (non-Javadoc)
	 * @see it.people.gateway.ISignatureFactory#createDocument(java.io.File)
	 */
	public SignedDocument createDocument(File file) throws FileNotFoundException {
		return SignatureFactory.isUseSafeFactory() ? this.getBCSafeSignatureFactory().createDocument(file) : this.getBCSignatureFactory().createDocument(file);
	}

	/* (non-Javadoc)
	 * @see it.people.gateway.ISignatureFactory#createDocument(java.io.InputStream)
	 */
	public SignedDocument createDocument(InputStream inputStream) {
		return SignatureFactory.isUseSafeFactory() ? this.getBCSafeSignatureFactory().createDocument(inputStream) : this.getBCSignatureFactory().createDocument(inputStream);
	}
	
}
