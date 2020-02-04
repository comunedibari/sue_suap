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
 * Created on 30-lug-2004
 *
 */
package it.people.util.dto.monitors;

import org.apache.log4j.Logger;

import it.people.util.dto.IMonitor;

/**
 * @author Zoppello
 * 
 */
public class Log4jMonitor implements IMonitor {

    /*
     * (non-Javadoc)
     * 
     * @see it.people.util.dto.IMonitor#debug(java.lang.Class, java.lang.Object)
     */
    public void debug(Class clz, Object o) {
	Logger.getLogger(clz).debug(o);

    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.util.dto.IMonitor#info(java.lang.Class, java.lang.Object)
     */
    public void info(Class clz, Object o) {
	Logger.getLogger(clz).info(o);

    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.util.dto.IMonitor#warn(java.lang.Class, java.lang.Object)
     */
    public void warn(Class clz, Object o) {
	Logger.getLogger(clz).warn(o);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.util.dto.IMonitor#error(java.lang.Class, java.lang.Object)
     */
    public void error(Class clz, Object o) {
	Logger.getLogger(clz).error(o);

    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.util.dto.IMonitor#fatal(java.lang.Class, java.lang.Object)
     */
    public void fatal(Class clz, Object o) {
	Logger.getLogger(clz).fatal(o);

    }

}
