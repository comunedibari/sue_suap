
package it.wego.cross.webservices.client.clear.stubs;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TipiStatoPost.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="TipiStatoPost">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="A"/>
 *     &lt;enumeration value="C"/>
 *     &lt;enumeration value="S"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipiStatoPost")
@XmlEnum
public enum TipiStatoPost {

    A,
    C,
    S;

    public String value() {
        return name();
    }

    public static TipiStatoPost fromValue(String v) {
        return valueOf(v);
    }

}
