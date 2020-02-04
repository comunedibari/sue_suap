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
 * &lt;complexType name="serviziTariffaIndividuale>"
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="importoDovuto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="importoPagato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="importoConguaglio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serviziTariffaIndividuale", propOrder = {
    "importoDovuto",
    "importoPagato",
    "importoConguaglio"
})
public class ServiziTariffaIndividuale {

    @XmlElement(name = "importoDovuto")
    protected String importoDovuto;
    @XmlElement(name = "importoPagato")
    protected String importoPagato;
    @XmlElement(name = "importoConguaglio")
    protected String importoConguaglio;

    public String getImportoDovuto() {
        return importoDovuto;
    }

    public void setImportoDovuto(String value) {
        this.importoDovuto = value;
    }

    public String getImportoPagato() {
        return importoPagato;
    }

    public void setImportoPagato(String value) {
        this.importoPagato = value;
    }

    public String getImportoConguaglio() {
        return importoConguaglio;
    }

    public void setImportoConguaglio(String value) {
        this.importoConguaglio = value;
    }
}
