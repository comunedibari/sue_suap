//
// Questo file  stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr persa durante la ricompilazione dello schema di origine. 
// Generato il: 2012.11.27 alle 11:16:28 AM CET 
//


package it.gov.impresainungiorno.schema.suap.pratica;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *             Singolo endoprocedimento (adempimento)
 *         
 * 
 * <p>Classe Java per AdempimentoSUAP complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AdempimentoSUAP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ente-coinvolto" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}EstremiEnte" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="distinta-modello-attivita" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}ModelloAttivita"/>
 *         &lt;element name="documento-allegato" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}AllegatoGenerico" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="nome" use="required" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" />
 *       &lt;attribute name="cod" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdempimentoSUAP", propOrder = {
    "enteCoinvolto",
    "distintaModelloAttivita",
    "documentoAllegato"
})
public class AdempimentoSUAP {

    @XmlElement(name = "ente-coinvolto")
    protected List<EstremiEnte> enteCoinvolto;
    @XmlElement(name = "distinta-modello-attivita", required = true)
    protected ModelloAttivita distintaModelloAttivita;
    @XmlElement(name = "documento-allegato")
    protected List<AllegatoGenerico> documentoAllegato;
    @XmlAttribute(name = "nome", required = true)
    protected String nome;
    @XmlAttribute(name = "cod")
    protected String cod;

    /**
     * Gets the value of the enteCoinvolto property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the enteCoinvolto property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEnteCoinvolto().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EstremiEnte }
     * 
     * 
     */
    public List<EstremiEnte> getEnteCoinvolto() {
        if (enteCoinvolto == null) {
            enteCoinvolto = new ArrayList<EstremiEnte>();
        }
        return this.enteCoinvolto;
    }

    /**
     * Recupera il valore della propriet distintaModelloAttivita.
     * 
     * @return
     *     possible object is
     *     {@link ModelloAttivita }
     *     
     */
    public ModelloAttivita getDistintaModelloAttivita() {
        return distintaModelloAttivita;
    }

    /**
     * Imposta il valore della propriet distintaModelloAttivita.
     * 
     * @param value
     *     allowed object is
     *     {@link ModelloAttivita }
     *     
     */
    public void setDistintaModelloAttivita(ModelloAttivita value) {
        this.distintaModelloAttivita = value;
    }

    /**
     * Gets the value of the documentoAllegato property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the documentoAllegato property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocumentoAllegato().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AllegatoGenerico }
     * 
     * 
     */
    public List<AllegatoGenerico> getDocumentoAllegato() {
        if (documentoAllegato == null) {
            documentoAllegato = new ArrayList<AllegatoGenerico>();
        }
        return this.documentoAllegato;
    }

    /**
     * Recupera il valore della propriet nome.
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
     * Imposta il valore della propriet nome.
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
     * Recupera il valore della propriet cod.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCod() {
        return cod;
    }

    /**
     * Imposta il valore della propriet cod.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCod(String value) {
        this.cod = value;
    }

}
