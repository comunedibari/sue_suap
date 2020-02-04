/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.utils;

import it.asitech.webservice.protocollo.ErrorMessage;
import it.asitech.webservice.protocollo.Field;
import it.asitech.webservice.protocollo.ResponseMessage;

/**
 *
 * @author Gabriele
 */
public class GenuitUtils {

    public static String serializeResponseMessagge(ResponseMessage message) {
        StringBuilder sb = new StringBuilder();
        if (message != null) {
            sb.append(message.getMessage()).append("\n");
        }
        if (message != null && hasError(message)) {
            ErrorMessage[] errors = message.getErrors();
            for (int i = 0; i < errors.length; i++) {
                sb.append("\t").append(errors[i].getLevel()).append(" : [").append(errors[i].getCode()).append("] ").append(errors[i].getDescr()).append(".\n");
            }
        }
        return sb.toString();
    }

    public static Boolean hasError(ResponseMessage message) {
        return message.getErrors() != null && message.getErrors().length > 0;
    }

    public static Object[] getFieldValue(Field[] fields, String key) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().equalsIgnoreCase(key)) {
                return fields[i].getValue();
            }
        }
        return null;
    }
}
