
package it.eng.tz.avtmb.xsd.stampsign;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for documentInResponseType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="documentInResponseType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SI"/>
 *     &lt;enumeration value="NO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "documentInResponseType")
@XmlEnum
public enum DocumentInResponseType {

    SI,
    NO;

    public String value() {
        return name();
    }

    public static DocumentInResponseType fromValue(String v) {
        return valueOf(v);
    }

}
