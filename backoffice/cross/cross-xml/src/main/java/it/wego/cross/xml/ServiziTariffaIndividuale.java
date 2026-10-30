//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2024.04.16 alle 09:52:47 AM CEST 
//


package it.wego.cross.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per serviziTariffaIndividuale complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="serviziTariffaIndividuale">
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

    /**
     * Recupera il valore della propriet� importoDovuto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImportoDovuto() {
        return importoDovuto;
    }

    /**
     * Imposta il valore della propriet� importoDovuto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImportoDovuto(String value) {
        this.importoDovuto = value;
    }

    /**
     * Recupera il valore della propriet� importoPagato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImportoPagato() {
        return importoPagato;
    }

    /**
     * Imposta il valore della propriet� importoPagato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImportoPagato(String value) {
        this.importoPagato = value;
    }

    /**
     * Recupera il valore della propriet� importoConguaglio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImportoConguaglio() {
        return importoConguaglio;
    }

    /**
     * Imposta il valore della propriet� importoConguaglio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImportoConguaglio(String value) {
        this.importoConguaglio = value;
    }

}
