
package it.eng.tz.avtmb.xsd.stampsign;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for composizioneDocumento.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="composizioneDocumento">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="paginaSingola"/>
 *     &lt;enumeration value="paginaMultipla"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "composizioneDocumento")
@XmlEnum
public enum ComposizioneDocumento {

    @XmlEnumValue("paginaSingola")
    PAGINA_SINGOLA("paginaSingola"),
    @XmlEnumValue("paginaMultipla")
    PAGINA_MULTIPLA("paginaMultipla");
    private final String value;

    ComposizioneDocumento(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ComposizioneDocumento fromValue(String v) {
        for (ComposizioneDocumento c: ComposizioneDocumento.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
