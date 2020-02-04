
package it.wego.cross.webservices.client.clear.stubs;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TipiSoggetto.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="TipiSoggetto">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="I"/>
 *     &lt;enumeration value="E"/>
 *     &lt;enumeration value="C"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipiSoggetto")
@XmlEnum
public enum TipiSoggetto {

    I,
    E,
    C;

    public String value() {
        return name();
    }

    public static TipiSoggetto fromValue(String v) {
        return valueOf(v);
    }

}
