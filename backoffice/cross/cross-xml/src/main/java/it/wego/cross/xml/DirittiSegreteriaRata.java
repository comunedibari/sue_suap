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
 * <p>Classe Java per dirittiSegreteriaRata complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="dirittiSegreteriaRata">
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

    /**
     * Recupera il valore della propriet� importo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporto() {
        return importo;
    }

    /**
     * Imposta il valore della propriet� importo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporto(String value) {
        this.importo = value;
    }

    /**
     * Recupera il valore della propriet� dataPrevista.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataPrevista() {
        return dataPrevista;
    }

    /**
     * Imposta il valore della propriet� dataPrevista.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataPrevista(String value) {
        this.dataPrevista = value;
    }

    /**
     * Recupera il valore della propriet� dataPagamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataPagamento() {
        return dataPagamento;
    }

    /**
     * Imposta il valore della propriet� dataPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataPagamento(String value) {
        this.dataPagamento = value;
    }

}
