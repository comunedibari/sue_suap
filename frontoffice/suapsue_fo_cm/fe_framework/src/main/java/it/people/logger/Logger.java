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
 * PeopleLogger.java
 *
 * Created on 8 novembre 2004, 17.50
 */
package it.people.logger;

import java.util.Hashtable;

import it.people.City;
import it.people.core.CommuneManager;
import it.people.core.persistence.exception.peopleException;
import it.people.db.fedb.Service;
import it.people.exceptions.PeopleDBException;

import java.util.StringTokenizer;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;
import org.apache.log4j.Category;

/**
 * 
 * @author mparigiani
 */
public class Logger {
    protected static final int ALL = 0;
    protected static final int DEBUG = 1;
    protected static final int INFO = 2;
    protected static final int WARN = 3;
    protected static final int ERROR = 4;
    protected static final int FATAL = 5;
    protected static final int OFF = 6;

    public static Hashtable instances = new Hashtable();
    protected Appender appender;
    private String packageName;
    private Media media;
    private LoggerBean logBean;
    private static Category cat = Category.getInstance(Logger.class.getName());

    private Logger(String packageName, LoggerBean lb, Media media) {
	this.packageName = packageName;
	this.logBean = lb;
	setMedia(media);
    }

    /**
     * Ritorna il logger per il servizio indicato, � possibile utilizzare
     * direttamente i metodi di log della classe Step
     * 
     * @param servicePackage
     *            package del servizio
     * @param commune
     *            comune attuale
     * @param media
     *            media su cui effettuare il log
     * @return
     */
    static public Logger getLogger(String servicePackage, City commune,
	    Media media) {
	String communeId = commune.getOid();
	String loggerKey = communeId + "." + servicePackage;

	Logger logger = (Logger) instances.get(loggerKey);

	if (logger == null) {
	    int logId = ERROR;
	    try {
		Service service = Service.get(servicePackage, communeId);
		if (service != null)
		    logId = service.getLogId();
		else
		    cat.warn("getLogger(String servicePackage, City commune, Media media) - il servizio non � registrato per il comune con codice = '"
			    + communeId + "'");
	    } catch (PeopleDBException pdbex) {
		cat.error(
			"getLogger() - Impossibile determinare il livello di log per il servizio",
			pdbex);
	    }
	    LoggerBean lb = setLogBeanProperties(logId, commune.getOid(),
		    servicePackage);

	    logger = new Logger(servicePackage, lb, media);
	    instances.put(loggerKey, logger);
	}

	return logger;
    }

    /**
     * Ritorna il logger per il servizio indicato, come media � utilizzato il
     * Database, � possibile utilizzare direttamente i metodi di log della
     * classe Step
     * 
     * @param servicePackage
     *            package del servizio
     * @param commune
     *            commune attuale
     * @return
     */
    static public Logger getLogger(String servicePackage, City commune) {
	return getLogger(servicePackage, commune, Media.DB);
    }

    /**
     * @deprecated
     * @param actualPackage
     * @return
     */
    static public Logger getLogger(String actualPackage) {
	try {
	    cat.warn("getLogger(String actualPackage) - il metodo � deprecato");
	    return getLogger(actualPackage, CommuneManager.getInstance().get());
	} catch (peopleException pex) {
	    return null;
	}
    }

    /**
     * @deprecated
     * @param actualPackage
     * @param media
     * @return
     */
    static public Logger getLogger(String actualPackage, String media) {
	try {
	    cat.warn("getLogger(String actualPackage, String media) - il metodo � deprecato");
	    return getLogger(actualPackage, CommuneManager.getInstance().get(),
		    getMediaValue(media));
	} catch (peopleException ex) {
	    return null;
	}
    }

    /**
     * @deprecated
     * @param clazz
     * @return
     */
    static public Logger getLogger(Class clazz) {
	cat.warn("getLogger(Class clazz) - il metodo � deprecato");
	String packageName = clazz.getPackage().getName();
	String actualPackage = tokenizePackageName(packageName);
	return getLogger(actualPackage);
    }

    /**
     * @deprecated
     * @param clazz
     * @param media
     * @return
     */
    static public Logger getLogger(Class clazz, String media) {
	cat.warn("getLogger(Class clazz, String media) - il metodo � deprecato");
	String packageName = clazz.getPackage().getName();
	String actualPackage = tokenizePackageName(packageName);
	return getLogger(actualPackage, media);
    }

    private static LoggerBean setLogBeanProperties(int logId, String idcomune,
	    String servicePackage) {
	LoggerBean lb = new LoggerBean();
	lb.setIdcomune(idcomune);
	lb.setServicePackage(servicePackage);
	lb.setIdloglevel(logId);
	return lb;
    }

    private void setMedia(Media media) {
	this.media = media;
	if (media == Media.DB && !(appender instanceof PeopleAppenderOnDB)) {
	    this.appender = new PeopleAppenderOnDB(this);
	} else if (media == Media.FILE
		&& !(appender instanceof PeopleAppenderOnFile)) {
	    this.appender = new PeopleAppenderOnFile(this,
		    tokenizePackageName(packageName));
	}
    }

    private static Media getMediaValue(String mediaValue) {
	if ("file".equalsIgnoreCase(mediaValue))
	    return Media.FILE;
	else
	    return Media.DB;
    }

    private static String tokenizePackageName(String packageName) {
	String actualPackage = "";
	StringTokenizer tk = new StringTokenizer(packageName, ".");
	for (int j = 0; j < 6; j++) {
	    actualPackage += tk.nextToken() + ".";
	}
	actualPackage += tk.nextToken();
	return actualPackage;
    }

    public LoggerBean setLogBeanData(LoggerBean bean) {
	Calendar calendar = new GregorianCalendar();
	Date trialTime = new Date();
	java.sql.Date date = new java.sql.Date(trialTime.getTime());
	bean.setDateString(date.toString());
	bean.setDate(date);
	return bean;
    }

    public void debug(String messaggio) {
	int loglevel = logBean.getIdloglevel();
	if (loglevel == Logger.OFF)
	    return;
	if (loglevel <= Logger.DEBUG || loglevel == Logger.ALL) {
	    logBean.setMessaggio(messaggio);
	    logBean = setLogBeanData(logBean);
	    appender.append(logBean, Logger.DEBUG);
	}
    }

    public void info(String messaggio) {
	int loglevel = logBean.getIdloglevel();
	if (loglevel == Logger.OFF)
	    return;
	if (loglevel <= Logger.INFO || loglevel == Logger.ALL) {
	    logBean.setMessaggio(messaggio);
	    logBean = setLogBeanData(logBean);
	    appender.append(logBean, Logger.INFO);
	}
    }

    public void warn(String messaggio) {
	int loglevel = logBean.getIdloglevel();
	if (loglevel == Logger.OFF)
	    return;
	if (loglevel <= Logger.WARN || loglevel == Logger.ALL) {
	    logBean.setMessaggio(messaggio);
	    logBean = setLogBeanData(logBean);
	    appender.append(logBean, Logger.WARN);
	}
    }

    public void error(String messaggio) {
	int loglevel = logBean.getIdloglevel();
	if (loglevel == Logger.OFF)
	    return;
	if (loglevel <= Logger.ERROR || loglevel == Logger.ALL) {
	    logBean.setMessaggio(messaggio);
	    logBean = setLogBeanData(logBean);
	    appender.append(logBean, Logger.ERROR);
	}
    }

    public void fatal(String messaggio) {
	int loglevel = logBean.getIdloglevel();
	if (loglevel == Logger.OFF)
	    return;
	if (loglevel <= Logger.FATAL || loglevel == Logger.ALL) {
	    logBean.setMessaggio(messaggio);
	    logBean = setLogBeanData(logBean);
	    appender.append(logBean, Logger.FATAL);
	}
    }
}
