
package it.eng.tz.avtmb.xsd.stampsign;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for singleStampSizeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="singleStampSizeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="VENTI"/>
 *     &lt;enumeration value="QUARANTA"/>
 *     &lt;enumeration value="SESSANTA"/>
 *     &lt;enumeration value="OTTANTA"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "singleStampSizeType")
@XmlEnum
public enum SingleStampSizeType {

    VENTI,
    QUARANTA,
    SESSANTA,
    OTTANTA;

    public String value() {
        return name();
    }

    public static SingleStampSizeType fromValue(String v) {
        return valueOf(v);
    }

}
