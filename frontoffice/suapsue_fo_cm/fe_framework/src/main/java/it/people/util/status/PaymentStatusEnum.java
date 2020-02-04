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
package it.people.util.status;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         16/lug/2012 14:49:13
 */
public enum PaymentStatusEnum {

    paymentPending(10, "In attesa di pagamento", "payment.pending.description",
	    "payment.pending.alert"), paymentSuccessfull(11,
	    "Pagamento concluso", "payment.successfull.description", ""), paymentAborted(
	    12, "Pagamento abortito", "payment.aborted.description",
	    "payment.aborted.alert");

    /**
	 * 
	 */
    private int code;

    /**
	 * 
	 */
    private String description;

    /**
	 * 
	 */
    private String descriptionKey;

    /**
	 * 
	 */
    private String alertKey;

    /**
     * @param code
     * @param description
     */
    private PaymentStatusEnum(final int code, final String description,
	    final String descriptionKey, final String alertKey) {
	this.setCode(code);
	this.setDescription(description);
	this.setDescriptionKey(descriptionKey);
	this.setAlertKey(alertKey);
    }

    /**
     * @param code
     *            the code to set
     */
    private void setCode(int code) {
	this.code = code;
    }

    /**
     * @param description
     *            the description to set
     */
    private void setDescription(String description) {
	this.description = description;
    }

    /**
     * @param descriptionKey
     *            the descriptionKey to set
     */
    private void setDescriptionKey(String descriptionKey) {
	this.descriptionKey = descriptionKey;
    }

    /**
     * @param alertKey
     *            the alertKey to set
     */
    private void setAlertKey(String alertKey) {
	this.alertKey = alertKey;
    }

    /**
     * @return the code
     */
    public final int getCode() {
	return this.code;
    }

    /**
     * @return the description
     */
    public final String getDescription() {
	return this.description;
    }

    /**
     * @return the descriptionKey
     */
    public final String getDescriptionKey() {
	return this.descriptionKey;
    }

    /**
     * @return the alertKey
     */
    public final String getAlertKey() {
	return this.alertKey;
    }

}
