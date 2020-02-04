//
// Questo file e' stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andra' persa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.02.01 alle 04:09:31 PM CET 
//


package it.wego.cross.xml.anagrafetributaria.edilizia;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="recordTesta" type="{http://www.wego.it/cross}recordControllo"/>
 *         &lt;element name="recordsDettaglio">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="recordDettaglio" type="{http://www.wego.it/cross}recordDettaglio" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="recordCoda" type="{http://www.wego.it/cross}recordControllo"/>
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
    "recordTesta",
    "recordsDettaglio",
    "recordCoda"
})
@XmlRootElement(name = "anagrafeTributariaEdilizia")
public class AnagrafeTributariaEdilizia {

    @XmlElement(required = true)
    protected RecordControllo recordTesta;
    @XmlElement(required = true)
    protected AnagrafeTributariaEdilizia.RecordsDettaglio recordsDettaglio;
    @XmlElement(required = true)
    protected RecordControllo recordCoda;

    /**
     * Recupera il valore della proprieta' recordTesta.
     * 
     * @return
     *     possible object is
     *     {@link RecordControllo }
     *     
     */
    public RecordControllo getRecordTesta() {
        return recordTesta;
    }

    /**
     * Imposta il valore della proprieta' recordTesta.
     * 
     * @param value
     *     allowed object is
     *     {@link RecordControllo }
     *     
     */
    public void setRecordTesta(RecordControllo value) {
        this.recordTesta = value;
    }

    /**
     * Recupera il valore della proprieta' recordsDettaglio.
     * 
     * @return
     *     possible object is
     *     {@link AnagrafeTributariaEdilizia.RecordsDettaglio }
     *     
     */
    public AnagrafeTributariaEdilizia.RecordsDettaglio getRecordsDettaglio() {
        return recordsDettaglio;
    }

    /**
     * Imposta il valore della proprieta' recordsDettaglio.
     * 
     * @param value
     *     allowed object is
     *     {@link AnagrafeTributariaEdilizia.RecordsDettaglio }
     *     
     */
    public void setRecordsDettaglio(AnagrafeTributariaEdilizia.RecordsDettaglio value) {
        this.recordsDettaglio = value;
    }

    /**
     * Recupera il valore della proprieta' recordCoda.
     * 
     * @return
     *     possible object is
     *     {@link RecordControllo }
     *     
     */
    public RecordControllo getRecordCoda() {
        return recordCoda;
    }

    /**
     * Imposta il valore della proprieta' recordCoda.
     * 
     * @param value
     *     allowed object is
     *     {@link RecordControllo }
     *     
     */
    public void setRecordCoda(RecordControllo value) {
        this.recordCoda = value;
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
     *         &lt;element name="recordDettaglio" type="{http://www.wego.it/cross}recordDettaglio" maxOccurs="unbounded"/>
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
        "recordDettaglio"
    })
    public static class RecordsDettaglio {

        @XmlElement(required = true)
        protected List<RecordDettaglio> recordDettaglio;

        /**
         * Gets the value of the recordDettaglio property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the recordDettaglio property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRecordDettaglio().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RecordDettaglio }
         * 
         * 
         */
        public List<RecordDettaglio> getRecordDettaglio() {
            if (recordDettaglio == null) {
                recordDettaglio = new ArrayList<RecordDettaglio>();
            }
            return this.recordDettaglio;
        }

    }

}
