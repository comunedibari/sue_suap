//
// Questo file  stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr persa durante la ricompilazione dello schema di origine. 
// Generato il: 2012.11.27 alle 11:16:28 AM CET 
//


package it.gov.impresainungiorno.schema.suap.pratica;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.gov.impresainungiorno.schema.base.CodiceREA;
import it.gov.impresainungiorno.schema.base.IndirizzoConRecapiti;


/**
 * 
 *             Dati relativi alla sede legale dell'impresa
 *         
 * 
 * <p>Classe Java per AnagraficaImpresa complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AnagraficaImpresa">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="forma-giuridica" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}FormaGiuridica"/>
 *         &lt;element name="ragione-sociale" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa"/>
 *         &lt;element name="codice-fiscale" type="{http://www.impresainungiorno.gov.it/schema/base}CodiceFiscale" minOccurs="0"/>
 *         &lt;element name="partita-iva" type="{http://www.impresainungiorno.gov.it/schema/base}PartitaIVA" minOccurs="0"/>
 *         &lt;element name="codice-REA" type="{http://www.impresainungiorno.gov.it/schema/base}CodiceREA" minOccurs="0"/>
 *         &lt;element name="indirizzo" type="{http://www.impresainungiorno.gov.it/schema/base}IndirizzoConRecapiti"/>
 *         &lt;element name="legale-rappresentante" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}AnagraficaRappresentante"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnagraficaImpresa", propOrder = {
    "formaGiuridica",
    "ragioneSociale",
    "codiceFiscale",
    "partitaIva",
    "codiceREA",
    "indirizzo",
    "legaleRappresentante"
})
public class AnagraficaImpresa {

    @XmlElement(name = "forma-giuridica", required = true)
    protected FormaGiuridica formaGiuridica;
    @XmlElement(name = "ragione-sociale", required = true)
    protected String ragioneSociale;
    @XmlElement(name = "codice-fiscale")
    protected String codiceFiscale;
    @XmlElement(name = "partita-iva")
    protected String partitaIva;
    @XmlElement(name = "codice-REA")
    protected CodiceREA codiceREA;
    @XmlElement(required = true)
    protected IndirizzoConRecapiti indirizzo;
    @XmlElement(name = "legale-rappresentante", required = true)
    protected AnagraficaRappresentante legaleRappresentante;

    /**
     * Recupera il valore della propriet formaGiuridica.
     * 
     * @return
     *     possible object is
     *     {@link FormaGiuridica }
     *     
     */
    public FormaGiuridica getFormaGiuridica() {
        return formaGiuridica;
    }

    /**
     * Imposta il valore della propriet formaGiuridica.
     * 
     * @param value
     *     allowed object is
     *     {@link FormaGiuridica }
     *     
     */
    public void setFormaGiuridica(FormaGiuridica value) {
        this.formaGiuridica = value;
    }

    /**
     * Recupera il valore della propriet ragioneSociale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRagioneSociale() {
        return ragioneSociale;
    }

    /**
     * Imposta il valore della propriet ragioneSociale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRagioneSociale(String value) {
        this.ragioneSociale = value;
    }

    /**
     * Recupera il valore della propriet codiceFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Imposta il valore della propriet codiceFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

    /**
     * Recupera il valore della propriet partitaIva.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartitaIva() {
        return partitaIva;
    }

    /**
     * Imposta il valore della propriet partitaIva.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartitaIva(String value) {
        this.partitaIva = value;
    }

    /**
     * Recupera il valore della propriet codiceREA.
     * 
     * @return
     *     possible object is
     *     {@link CodiceREA }
     *     
     */
    public CodiceREA getCodiceREA() {
        return codiceREA;
    }

    /**
     * Imposta il valore della propriet codiceREA.
     * 
     * @param value
     *     allowed object is
     *     {@link CodiceREA }
     *     
     */
    public void setCodiceREA(CodiceREA value) {
        this.codiceREA = value;
    }

    /**
     * Recupera il valore della propriet indirizzo.
     * 
     * @return
     *     possible object is
     *     {@link IndirizzoConRecapiti }
     *     
     */
    public IndirizzoConRecapiti getIndirizzo() {
        return indirizzo;
    }

    /**
     * Imposta il valore della propriet indirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link IndirizzoConRecapiti }
     *     
     */
    public void setIndirizzo(IndirizzoConRecapiti value) {
        this.indirizzo = value;
    }

    /**
     * Recupera il valore della propriet legaleRappresentante.
     * 
     * @return
     *     possible object is
     *     {@link AnagraficaRappresentante }
     *     
     */
    public AnagraficaRappresentante getLegaleRappresentante() {
        return legaleRappresentante;
    }

    /**
     * Imposta il valore della propriet legaleRappresentante.
     * 
     * @param value
     *     allowed object is
     *     {@link AnagraficaRappresentante }
     *     
     */
    public void setLegaleRappresentante(AnagraficaRappresentante value) {
        this.legaleRappresentante = value;
    }

}
