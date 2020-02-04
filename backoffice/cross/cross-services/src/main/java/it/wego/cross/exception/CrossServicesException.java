/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.exception;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.ws.WebFault;

/**
 *
 * @author giuseppe
 */
@WebFault(name = "CrossServicesException")
@XmlAccessorType( XmlAccessType.FIELD)
public class CrossServicesException extends RuntimeException {

    String detail;

    public CrossServicesException() {
    }

    public CrossServicesException(String detail) {
        this.detail = detail;
    }
}
