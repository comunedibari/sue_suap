//
// Questo file  stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr persa durante la ricompilazione dello schema di origine. 
// Generato il: 2012.11.27 alle 11:16:28 AM CET 
//


package it.gov.impresainungiorno.schema.suap.rea;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per StatoPratica.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="StatoPratica">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="istruttoria"/>
 *     &lt;enumeration value="evasa"/>
 *     &lt;enumeration value="sospesa"/>
 *     &lt;enumeration value="rifiutata"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StatoPratica")
@XmlEnum
public enum StatoPratica {

    @XmlEnumValue("istruttoria")
    ISTRUTTORIA("istruttoria"),
    @XmlEnumValue("evasa")
    EVASA("evasa"),
    @XmlEnumValue("sospesa")
    SOSPESA("sospesa"),
    @XmlEnumValue("rifiutata")
    RIFIUTATA("rifiutata");
    private final String value;

    StatoPratica(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StatoPratica fromValue(String v) {
        for (StatoPratica c: StatoPratica.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
