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
 * &lt;complexType name="dirittiSegreteriaRata>"
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="importo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataPrevista" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataPagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dirittiSegreteriaRata", propOrder = {
    "importo",
    "dataPrevista",
    "dataPagamento"
})
public class DirittiSegreteriaRata {

    @XmlElement(name = "importo")
    protected String importo;
    @XmlElement(name = "dataPrevista")
    protected String dataPrevista;
    @XmlElement(name = "dataPagamento")
    protected String dataPagamento;

    public String getImporto() {
        return importo;
    }

    public void setImporto(String value) {
        this.importo = value;
    }

    public String getDataPrevista() {
        return dataPrevista;
    }

    public void setDataPrevista(String value) {
        this.dataPrevista = value;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(String value) {
        this.dataPagamento = value;
    }
}
