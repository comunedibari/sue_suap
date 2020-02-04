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
package it.people.process.sign;

import it.people.process.AbstractPplProcess;
import it.people.process.common.entity.SignedAttachment;
import it.people.process.sign.entity.SignedData;
import it.people.process.sign.entity.SignedInfo;
import it.people.util.PKCS7Parser;

import java.io.File;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Nov 30, 2003 Time: 9:10:32 PM To
 * change this template use Options | File Templates.
 */
public class SignedDataHelper {
    public static SignedDataHolder exportToHolder(SignedData sd,
	    AbstractPplProcess pplProcess) {
	if (sd.isPersistented()) {
	    SignedDataHolder sdh = new SignedDataHolder();

	    sdh.setStepOid(sd.getKey());
	    sdh.setFileName(sd.getFriendlyName());
	    sdh.setFilePath(sd.getPath());

	    sdh.setParentOid(pplProcess.getOid());

	    return sdh;
	}
	return null;
    }

    public static SignedData importFromHolder(SignedDataHolder sdh) {
	if (sdh != null) {
	    SignedInfo si = new SignedInfo();
	    PKCS7Parser parser = new PKCS7Parser(new File(sdh.getFilePath()));
	    parser.decode(si);

	    SignedData sd = new SignedData(sdh.getStepOid(), sdh.getFileName(),
		    si);
	    sd.setPath(sdh.getFilePath());
	    return sd;
	}
	return null;
    }

    public static SignedData buildFromAttachment(String key, SignedAttachment sa) {
	if (sa != null) {
	    SignedInfo si = new SignedInfo();
	    PKCS7Parser parser = new PKCS7Parser(new File(sa.getPath()));
	    parser.decode(si);

	    SignedData sd = new SignedData(key, sa.getName(), si);
	    return sd;
	}
	return null;
    }
}
