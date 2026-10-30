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
 * &lt;complexType name="dirittiSegreteriaGaranzie>"
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="banca" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="importo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dirittiSegreteriaGaranzie", propOrder = {
    "banca",
    "data",
    "importo"
})
public class DirittiSegreteriaGaranzie {

    @XmlElement(name = "banca")
    protected String banca;
    @XmlElement(name = "data")
    protected String data;
    @XmlElement(name = "importo")
    protected String importo;

    public String getBanca() {
        return banca;
    }

    public void setBanca(String value) {
        this.banca = value;
    }

    public String getData() {
        return data;
    }

    public void setData(String value) {
        this.data = value;
    }

    public String getImporto() {
        return importo;
    }

    public void setImporto(String value) {
        this.importo = value;
    }
}
