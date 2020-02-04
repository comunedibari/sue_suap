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
 * LoggerBean.java
 *
 * Created on 8 novembre 2004, 17.38
 */

package it.people.logger;

import java.sql.Date;

/**
 * 
 * @author mparigiani
 */
public class LoggerBean {

    public LoggerBean() {
    }

    private String messaggio;
    private String idcomune;
    // private int idservizio;
    private int idloglevel;
    private Date date;
    private String dateString;
    private String servicePackage;

    public void setDate(Date date) {
	this.date = date;
    }

    public Date getDate() {
	return date;
    }

    public String getMessaggio() {
	return messaggio;
    }

    public void setMessaggio(String messaggio) {
	this.messaggio = messaggio;
    }

    public String getIdcomune() {
	return idcomune;
    }

    public void setIdcomune(String idcomune) {
	this.idcomune = idcomune;
    }

    /*
     * public int getIdservizio() { return idservizio; }
     * 
     * public void setIdservizio(int idservizio) { this.idservizio = idservizio;
     * }
     */

    public int getIdloglevel() {
	return idloglevel;
    }

    public void setIdloglevel(int idloglevel) {
	this.idloglevel = idloglevel;
    }

    public String getDateString() {
	return dateString;
    }

    public void setDateString(String dateString) {
	this.dateString = dateString;
    }

    public String getServicePackage() {
	return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
	this.servicePackage = servicePackage;
    }
}
