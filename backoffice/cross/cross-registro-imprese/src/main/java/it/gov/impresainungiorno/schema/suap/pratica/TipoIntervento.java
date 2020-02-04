//
// Questo file  stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr persa durante la ricompilazione dello schema di origine. 
// Generato il: 2012.11.27 alle 11:16:28 AM CET 
//


package it.gov.impresainungiorno.schema.suap.pratica;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TipoIntervento.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoIntervento">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="apertura"/>
 *     &lt;enumeration value="subentro"/>
 *     &lt;enumeration value="trasformazione"/>
 *     &lt;enumeration value="modifiche"/>
 *     &lt;enumeration value="cessazione"/>
 *     &lt;enumeration value="altro"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoIntervento")
@XmlEnum
public enum TipoIntervento {


    /**
     * Nuova apertura attivita'
     * 
     */
    @XmlEnumValue("apertura")
    APERTURA("apertura"),

    /**
     * Subentro nell'esercizio dell'attivita'
     * 
     */
    @XmlEnumValue("subentro")
    SUBENTRO("subentro"),

    /**
     * Realizzazione o modifiche di locali e/o impianti
     * 
     */
    @XmlEnumValue("trasformazione")
    TRASFORMAZIONE("trasformazione"),

    /**
     * qualsiasi tipo di variazione, richiesta di autorizzazione, trasferimento, sospensione, ...
     * 
     */
    @XmlEnumValue("modifiche")
    MODIFICHE("modifiche"),

    /**
     * Cessazione dell'attivita'
     * 
     */
    @XmlEnumValue("cessazione")
    CESSAZIONE("cessazione"),

    /**
     * Altre esigenze non riconducibili ai casi precedenti
     *                     
     *                     
     * 
     */
    @XmlEnumValue("altro")
    ALTRO("altro");
    private final String value;

    TipoIntervento(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoIntervento fromValue(String v) {
        for (TipoIntervento c: TipoIntervento.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
