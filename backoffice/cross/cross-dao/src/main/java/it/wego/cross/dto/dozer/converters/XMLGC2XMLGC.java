/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer.converters;

import javax.xml.datatype.XMLGregorianCalendar;
import org.dozer.DozerConverter;

/**
 *
 * @author piergiorgio
 */
public class XMLGC2XMLGC extends DozerConverter<XMLGregorianCalendar, XMLGregorianCalendar> {

    public XMLGC2XMLGC() {
        super(XMLGregorianCalendar.class, XMLGregorianCalendar.class);
    }

    @Override
    public XMLGregorianCalendar convertFrom(XMLGregorianCalendar src,
            XMLGregorianCalendar dest) {
        return src;
    }

    @Override
    public XMLGregorianCalendar convertTo(XMLGregorianCalendar src,
            XMLGregorianCalendar dest) {
        return dest;
    }
}
