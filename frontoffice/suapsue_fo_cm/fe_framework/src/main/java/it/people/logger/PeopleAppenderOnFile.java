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
 * PeopleAppenderOnFile.java
 *
 * Created on 8 novembre 2004, 18.21
 */

package it.people.logger;

import it.people.util.PeopleProperties;
import java.io.*;
import org.apache.log4j.Category;

/**
 * 
 * @author mparigiani
 */
public class PeopleAppenderOnFile implements Appender {
    private Category cat = Category.getInstance(PeopleAppenderOnFile.class
	    .getName());
    private File logFile;
    private PrintStream oss;
    private Logger logger;
    private String fileName;

    public PeopleAppenderOnFile(Logger logger, String fileName) {
	this.fileName = (String) (PeopleProperties.SERVICES_LOG_DIR.getValue())
		+ File.separator + fileName + ".txt";
	this.logger = logger;
    }

    public void append(LoggerBean lb, int level) {
	try {
	    this.logFile = new File(fileName);
	    this.oss = new PrintStream(new FileOutputStream(this.logFile));
	    oss.println(level + "\t" + lb.getIdcomune() + "\t"
		    + lb.getServicePackage() + "\t" + lb.getDateString() + "\t"
		    + lb.getMessaggio());

	} catch (IOException e) {
	    cat.error(e);
	} finally {
	    oss.close();
	}
    }
}
