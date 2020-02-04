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
 * Created on 30-mag-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.util.payment;

/**
 * @author fabmi
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ObserverSetting {
    private int retryNumbers = 10;
    private int retryIntervalSeconds = 300;
    private boolean doNotSendConfirmationMail = false;

    /**
     * @return Returns the retryIntervalSeconds.
     */
    public int getRetryIntervalSeconds() {
	return retryIntervalSeconds;
    }

    /**
     * @param retryIntervalSeconds
     *            The retryIntervalSeconds to set.
     */
    public void setRetryIntervalSeconds(int retryIntervalSeconds) {
	this.retryIntervalSeconds = retryIntervalSeconds;
    }

    /**
     * @return Returns the retryNumbers.
     */
    public int getRetryNumbers() {
	return retryNumbers;
    }

    /**
     * @param retryNumbers
     *            The retryNumbers to set.
     */
    public void setRetryNumbers(int retryNumbers) {
	this.retryNumbers = retryNumbers;
    }

    /**
     * @return Returns the doNotSendConfirmationMail.
     */
    public boolean isDoNotSendConfirmationMail() {
	return doNotSendConfirmationMail;
    }

    /**
     * @param doNotSendConfirmationMail
     *            The doNotSendConfirmationMail to set.
     */
    public void setDoNotSendConfirmationMail(boolean doNotSendConfirmationMail) {
	this.doNotSendConfirmationMail = doNotSendConfirmationMail;
    }
}
