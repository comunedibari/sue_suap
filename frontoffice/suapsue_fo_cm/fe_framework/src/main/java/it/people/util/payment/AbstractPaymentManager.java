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
package it.people.util.payment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         14/nov/2012 12:02:47
 */
public abstract class AbstractPaymentManager {

    private static final Logger logger = LogManager
	    .getLogger(AbstractPaymentManager.class);

    protected String propFile;

    protected Properties oConfig;

    /**
     * @param propFilePath
     */
    public void initialize(String propFilePath) {
	propFile = propFilePath;
	oConfig = new Properties();
	try {
	    oConfig.load(new FileInputStream(propFile));
	} catch (FileNotFoundException e) {
	    logger.error("Unable to read configuration properties file.", e);
	} catch (IOException e) {
	    logger.error("Unable to read configuration properties file.", e);
	}
    }

    /**
     * @param sBufferData
     * @param sNumOperazione
     */
    protected void executeBufferDump(String sBufferData, String sNumOperazione,
	    boolean call) {

	if (Boolean.parseBoolean(String.valueOf(oConfig
		.get("payment.doBufferDataDump")))) {
	    try {
		File dumpPathFolder = new File(String.valueOf(oConfig
			.get("payment.dumpFolder")));
		if (dumpPathFolder.exists()) {
		    File dumpFile = new File(dumpPathFolder, "payment_"
			    + (call ? "call_" : "response_") + sNumOperazione
			    + "_" + Calendar.getInstance().getTimeInMillis());
		    FileOutputStream fileOutputStream = new FileOutputStream(
			    dumpFile);
		    fileOutputStream.write(sBufferData.getBytes());
		    fileOutputStream.flush();
		    fileOutputStream.close();
		}
	    } catch (Exception e) {
		logger.warn("Unable to do buffer dump.", e);
	    }
	}

    }

}
