//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.05.27 alle 07:27:17 PM CEST 
//
package it.wego.cross.xml;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per ente complex type.
 *
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in
 * questa classe.
 *
 * <pre>
 * &lt;complexType name="contributiCosto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dirittiSegreteriaGenerali" type="{http://www.w3.org/2001/XMLSchema}dirittiSegreteriaGenerali" minOccurs="0"/>
 *         &lt;element name="dirittiSegreteriaGaranzie" type="{http://www.w3.org/2001/XMLSchema}dirittiSegreteriaGaranzie" minOccurs="0"/>
 *         &lt;element name="dirittiSegreteriaRate" type="{http://www.w3.org/2001/XMLSchema}dirittiSegreteriaRate" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contributiCosto", propOrder = {
    "dirittiSegreteriaGenerali",
    "dirittiSegreteriaGaranzie",
    "dirittiSegreteriaRate"
})
public class ContributiCosto {

    @XmlElement(name = "dirittiSegreteriaGenerali")
    protected DirittiSegreteriaGenerali dirittiSegreteriaGenerali;
    @XmlElement(name = "dirittiSegreteriaGaranzie")
    protected DirittiSegreteriaGaranzie dirittiSegreteriaGaranzie;
    @XmlElement(name = "dirittiSegreteriaRate")
    protected DirittiSegreteriaRate dirittiSegreteriaRate;

    public DirittiSegreteriaGenerali getDirittiSegreteriaGenerali() {
        return dirittiSegreteriaGenerali;
    }

    public void setDirittiSegreteriaGenerali(DirittiSegreteriaGenerali value) {
        this.dirittiSegreteriaGenerali = value;
    }

    public DirittiSegreteriaGaranzie getDirittiSegreteriaGaranzie() {
        return dirittiSegreteriaGaranzie;
    }

    public void setDirittiSegreteriaGaranzie(DirittiSegreteriaGaranzie value) {
        this.dirittiSegreteriaGaranzie = value;
    }

    public DirittiSegreteriaRate getDirittiSegreteriaRate() {
        return dirittiSegreteriaRate;
    }

    public void setDirittiSegreteriaRate(DirittiSegreteriaRate value) {
        this.dirittiSegreteriaRate = value;
    }

}
