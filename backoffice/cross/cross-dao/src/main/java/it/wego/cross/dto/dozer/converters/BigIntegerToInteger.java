/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer.converters;

import java.math.BigInteger;
import javax.xml.datatype.XMLGregorianCalendar;
import org.dozer.CustomConverter;
import org.dozer.MappingException;

/**
 *
 * @author piergiorgio
 */
public class BigIntegerToInteger implements CustomConverter {

    public Object convert(Object existingDestinationFieldValue,
            Object sourceFieldValue,
            @SuppressWarnings("rawtypes") Class destinationClass,
            @SuppressWarnings("rawtypes") Class sourceClass) {
        if (sourceFieldValue == null) {
            return null;
        }

        if (sourceFieldValue instanceof java.math.BigInteger) {
            BigInteger in = (BigInteger) sourceFieldValue;
            java.lang.Integer out = in.intValue();
            return out;
        }
        if (sourceFieldValue instanceof java.lang.Integer) {
            Integer in = (Integer) sourceFieldValue;
            java.math.BigInteger out = BigInteger.valueOf(in);
            return out;
        }
        throw new MappingException("Misconfigured/unsupported mapping");
    }
}
