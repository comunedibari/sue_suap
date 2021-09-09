
package it.eng.tz.avtmb.xsd.stampsign;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for posizioneTimbro.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="posizioneTimbro">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="header"/>
 *     &lt;enumeration value="footer"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "posizioneTimbro")
@XmlEnum
public enum PosizioneTimbro {

    @XmlEnumValue("header")
    HEADER("header"),
    @XmlEnumValue("footer")
    FOOTER("footer");
    private final String value;

    PosizioneTimbro(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PosizioneTimbro fromValue(String v) {
        for (PosizioneTimbro c: PosizioneTimbro.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
