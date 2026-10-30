//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.03.28 alle 03:45:54 PM CET 
//


package it.wego.cross.client.xml.addud;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Classe Java per AttributoAddizionaleType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AttributoAddizionaleType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Nome" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;choice>
 *           &lt;element name="ValoreSemplice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="Lista">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="Riga" maxOccurs="unbounded" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="Colonna" maxOccurs="unbounded">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;attribute name="Nro" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttributoAddizionaleType", propOrder = {
    "nome",
    "valoreSemplice",
    "lista"
})
public class AttributoAddizionaleType {

    @XmlElement(name = "Nome", required = true)
    protected String nome;
    @XmlElement(name = "ValoreSemplice")
    protected String valoreSemplice;
    @XmlElement(name = "Lista")
    protected AttributoAddizionaleType.Lista lista;

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
     * Recupera il valore della proprietvaloreSemplice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValoreSemplice() {
        return valoreSemplice;
    }

    /**
     * Imposta il valore della proprietvaloreSemplice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValoreSemplice(String value) {
        this.valoreSemplice = value;
    }

    /**
     * Recupera il valore della proprietlista.
     * 
     * @return
     *     possible object is
     *     {@link AttributoAddizionaleType.Lista }
     *     
     */
    public AttributoAddizionaleType.Lista getLista() {
        return lista;
    }

    /**
     * Imposta il valore della proprietlista.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributoAddizionaleType.Lista }
     *     
     */
    public void setLista(AttributoAddizionaleType.Lista value) {
        this.lista = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Riga" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Colonna" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attribute name="Nro" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "riga"
    })
    public static class Lista {

        @XmlElement(name = "Riga")
        protected List<AttributoAddizionaleType.Lista.Riga> riga;

        /**
         * Gets the value of the riga property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the riga property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRiga().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AttributoAddizionaleType.Lista.Riga }
         * 
         * 
         */
        public List<AttributoAddizionaleType.Lista.Riga> getRiga() {
            if (riga == null) {
                riga = new ArrayList<AttributoAddizionaleType.Lista.Riga>();
            }
            return this.riga;
        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="Colonna" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attribute name="Nro" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "colonna"
        })
        public static class Riga {

            @XmlElement(name = "Colonna", required = true)
            protected List<AttributoAddizionaleType.Lista.Riga.Colonna> colonna;

            /**
             * Gets the value of the colonna property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the colonna property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getColonna().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link AttributoAddizionaleType.Lista.Riga.Colonna }
             * 
             * 
             */
            public List<AttributoAddizionaleType.Lista.Riga.Colonna> getColonna() {
                if (colonna == null) {
                    colonna = new ArrayList<AttributoAddizionaleType.Lista.Riga.Colonna>();
                }
                return this.colonna;
            }


            /**
             * <p>Classe Java per anonymous complex type.
             * 
             * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;attribute name="Nro" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "content"
            })
            public static class Colonna {

                @XmlValue
                protected String content;
                @XmlAttribute(name = "Nro", required = true)
                @XmlSchemaType(name = "positiveInteger")
                protected BigInteger nro;

                /**
                 * Recupera il valore della proprietcontent.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getContent() {
                    return content;
                }

                /**
                 * Imposta il valore della proprietcontent.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setContent(String value) {
                    this.content = value;
                }

                /**
                 * Recupera il valore della proprietnro.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigInteger }
                 *     
                 */
                public BigInteger getNro() {
                    return nro;
                }

                /**
                 * Imposta il valore della proprietnro.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigInteger }
                 *     
                 */
                public void setNro(BigInteger value) {
                    this.nro = value;
                }

            }

        }

    }

}
