/**
 * Superclass di tutti i messaggi di ritorno del ws
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.asitech.webservice.protocollo;

public class PRIOut  {
    protected it.asitech.webservice.protocollo.ResponseMessage returnCode;

    public PRIOut() {
    }

    public PRIOut(it.asitech.webservice.protocollo.ResponseMessage returnCode) {
           this.returnCode = returnCode;
    }


    /**
     * Gets the returnCode _value for this PRIObjectOut.
     * 
     * @return returnCode
     */
    public it.asitech.webservice.protocollo.ResponseMessage getReturnCode() {
        return returnCode;
    }


    /**
     * Sets the returnCode _value for this PRIObjectOut.
     * 
     * @param returnCode
     */
    public void setReturnCode(it.asitech.webservice.protocollo.ResponseMessage returnCode) {
        this.returnCode = returnCode;
    }

}
