//
// Questo file  stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr persa durante la ricompilazione dello schema di origine. 
// Generato il: 2012.11.27 alle 11:16:28 AM CET 
//


package it.gov.impresainungiorno.schema.suap.rea;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import it.gov.impresainungiorno.schema.suap.pratica.TipoIntervento;


/**
 * 
 * 			Valorizzare ad esempio con:
 * 			(Apertura/Chiusura) pratica SUAP [id pratica]
 * ]			
 * 
 * <p>Classe Java per OggettoComunicazioneREA complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="OggettoComunicazioneREA">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.impresainungiorno.gov.it/schema/suap/rea>TestoComunicazione">
 *       &lt;attribute name="tipo-procedimento">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="SCIA"/>
 *             &lt;enumeration value="silenzio-assenso"/>
 *             &lt;enumeration value="ordinario"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="tipo-intervento" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}TipoIntervento" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OggettoComunicazioneREA", propOrder = {
    "value"
})
public class OggettoComunicazioneREA {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "tipo-procedimento")
    protected String tipoProcedimento;
    @XmlAttribute(name = "tipo-intervento")
    protected TipoIntervento tipoIntervento;

    /**
     * Recupera il valore della propriet value.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Imposta il valore della propriet value.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Recupera il valore della propriet tipoProcedimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoProcedimento() {
        return tipoProcedimento;
    }

    /**
     * Imposta il valore della propriet tipoProcedimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoProcedimento(String value) {
        this.tipoProcedimento = value;
    }

    /**
     * Recupera il valore della propriet tipoIntervento.
     * 
     * @return
     *     possible object is
     *     {@link TipoIntervento }
     *     
     */
    public TipoIntervento getTipoIntervento() {
        return tipoIntervento;
    }

    /**
     * Imposta il valore della propriet tipoIntervento.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoIntervento }
     *     
     */
    public void setTipoIntervento(TipoIntervento value) {
        this.tipoIntervento = value;
    }

}
