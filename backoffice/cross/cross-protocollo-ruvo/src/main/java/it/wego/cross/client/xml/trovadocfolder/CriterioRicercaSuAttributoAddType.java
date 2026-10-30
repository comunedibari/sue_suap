//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.03.22 alle 04:25:41 PM CET 
//


package it.wego.cross.client.xml.trovadocfolder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per CriterioRicercaSuAttributoAddType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="CriterioRicercaSuAttributoAddType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Nome" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OperatoreLogico">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="uguale"/>
 *               &lt;enumeration value="simile a (case-sensitive)"/>
 *               &lt;enumeration value="simile a (case-insensitive)"/>
 *               &lt;enumeration value="minore"/>
 *               &lt;enumeration value="maggiore o uguale"/>
 *               &lt;enumeration value="minore"/>
 *               &lt;enumeration value="minore o uguale"/>
 *               &lt;enumeration value="compreso tra"/>
 *               &lt;enumeration value="non valorizzato"/>
 *               &lt;enumeration value="valorizzato"/>
 *               &lt;enumeration value="spuntato"/>
 *               &lt;enumeration value="non spuntato"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ValoreConfronto_1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ValoreConfronto_2" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CriterioRicercaSuAttributoAddType", propOrder = {
    "nome",
    "operatoreLogico",
    "valoreConfronto1",
    "valoreConfronto2"
})
public class CriterioRicercaSuAttributoAddType {

    @XmlElement(name = "Nome", required = true)
    protected String nome;
    @XmlElement(name = "OperatoreLogico", required = true)
    protected String operatoreLogico;
    @XmlElement(name = "ValoreConfronto_1")
    protected String valoreConfronto1;
    @XmlElement(name = "ValoreConfronto_2")
    protected Object valoreConfronto2;

    /**
     * Recupera il valore della proprietnome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il valore della proprietnome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Recupera il valore della proprietoperatoreLogico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperatoreLogico() {
        return operatoreLogico;
    }

    /**
     * Imposta il valore della proprietoperatoreLogico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperatoreLogico(String value) {
        this.operatoreLogico = value;
    }

    /**
     * Recupera il valore della proprietvaloreConfronto1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValoreConfronto1() {
        return valoreConfronto1;
    }

    /**
     * Imposta il valore della proprietvaloreConfronto1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValoreConfronto1(String value) {
        this.valoreConfronto1 = value;
    }

    /**
     * Recupera il valore della proprietvaloreConfronto2.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getValoreConfronto2() {
        return valoreConfronto2;
    }

    /**
     * Imposta il valore della proprietvaloreConfronto2.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setValoreConfronto2(Object value) {
        this.valoreConfronto2 = value;
    }

}
