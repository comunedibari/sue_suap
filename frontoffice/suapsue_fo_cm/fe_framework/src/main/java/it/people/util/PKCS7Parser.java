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
package it.people.util;

import it.people.process.sign.entity.SignedInfo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.xerces.utils.Base64;
import org.mozilla.jss.asn1.SET;
import org.mozilla.jss.pkcs7.ContentInfo;
import org.mozilla.jss.pkcs7.SignedData;
import org.mozilla.jss.pkcs7.SignerInfo;
import org.mozilla.jss.pkix.cert.Certificate;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Sep 30, 2003 Time: 10:26:47 PM
 * To change this template use Options | File Templates.
 */
public class PKCS7Parser {
    private byte[] m_pkcs7Data = null;
    private ArrayList m_signer = new ArrayList();

    public PKCS7Parser(String base64Str) {
	this(Base64.decode(base64Str.getBytes()));
    }

    public PKCS7Parser(byte[] input) {
	m_pkcs7Data = input;
    }

    public PKCS7Parser(File file) {
	try {
	    FileInputStream fis = new FileInputStream(file);
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    byte[] buffer = new byte[4096];
	    int read = 0;
	    while ((read = fis.read(buffer, 0, buffer.length)) > 0)
		baos.write(buffer, 0, read);
	    fis.close();
	    m_pkcs7Data = baos.toByteArray();
	    baos.close();
	} catch (FileNotFoundException fnfEx) {
	} catch (IOException ioEx) {

	}
    }

    public PKCS7Parser(InputStream istream) {
	try {
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    byte[] buffer = new byte[4096];
	    int read = 0;
	    while ((read = istream.read(buffer, 0, buffer.length)) > 0)
		baos.write(buffer, 0, read);
	    istream.close();
	    m_pkcs7Data = baos.toByteArray();
	    baos.close();
	} catch (IOException ioEx) {
	}
    }

    /*
     * public byte[] getContent() { return m_content; } public String
     * getContentStr() { return new String(m_content); }
     */
    public List getSigner() {
	List res = new ArrayList();
	res.addAll(m_signer);
	return res;
    }

    public boolean isP7mFile() {
	try {
	    ByteArrayInputStream istream = new ByteArrayInputStream(m_pkcs7Data);
	    ContentInfo.Template tmpl = new ContentInfo.Template();
	    tmpl.decode(istream);
	    return true;
	} catch (Exception ex) {
	}
	return false;
    }

    public void decode(SignedInfo sInfo) {
	sInfo.setPkcs7Data(m_pkcs7Data);

	try {
	    ByteArrayInputStream istream = new ByteArrayInputStream(m_pkcs7Data);

	    ContentInfo.Template tmpl = new ContentInfo.Template();
	    ContentInfo val = (ContentInfo) tmpl.decode(istream);
	    SignedData sd = null;
	    switch ((int) val.getContentType().getNumbers()[6]) {
	    case 1:
		break;
	    case 2:
		sd = (SignedData) val.getContent().decodeWith(
			new SignedData.Template());
		break;
	    }
	    ContentInfo ci = sd.getContentInfo();
	    if (ci.getContentType().getNumbers()[6] == 1)
		sInfo.setData(ci.getContent().getContents());

	    SET sis = sd.getSignerInfos();
	    for (int i = 0; i < sis.size(); i++) {
		SignerInfo si = (SignerInfo) sis.elementAt(i);
		if (i == 0) {
		    sInfo.setSign(si.getEncryptedDigest());
		    // sInfo.setUserDN(si.getIssuerAndSerialNumber().getIssuer().getRFC1485());
		}

		m_signer.add(si.getEncryptedDigest());
	    }

	    SET certificates = sd.getCertificates();
	    for (int i = 0; i < certificates.size(); i++) {
		Certificate cert = (Certificate) certificates.elementAt(i);
		if (i == 0) {
		    /*
		     * SET exts = cert.getInfo().getExtensions(); for (int j=0;
		     * j < exts.size(); j++) { Extension extn =
		     * (Extension)exts.elementAt(j);
		     * 
		     * String id = extn.getExtnId().toString(); String id1 =
		     * extn.getTag().toString(); String idTag =
		     * extn.getExtnId().getTag().toString(); String value = new
		     * String(extn.getExtnValue().toByteArray());
		     * System.out.println(value) ; }
		     */
		    sInfo.setUserDN(cert.getInfo().getSubject().getRFC1485());
		}
	    }
	} catch (Exception ex) {
	}
    }
}
