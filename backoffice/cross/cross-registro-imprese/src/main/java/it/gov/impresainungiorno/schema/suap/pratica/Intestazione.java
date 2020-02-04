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


/**
 * <p>Classe Java per Intestazione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Intestazione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ufficio-destinatario" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}EstremiSuap"/>
 *         &lt;element name="impresa" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}AnagraficaImpresa"/>
 *         &lt;element name="oggetto-comunicazione" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}OggettoComunicazione"/>
 *         &lt;element name="codice-pratica" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" minOccurs="0"/>
 *         &lt;element name="procura-speciale" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}ProcuraSpeciale" minOccurs="0"/>
 *         &lt;element name="dichiarante" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}EstremiDichiarante"/>
 *         &lt;element name="domicilio-elettronico" type="{http://www.impresainungiorno.gov.it/schema/base}EMailIndirizzo" minOccurs="0"/>
 *         &lt;element name="impianto-produttivo" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}ImpiantoProduttivo" minOccurs="0"/>
 *         &lt;element name="protocollo" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}ProtocolloSUAP" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Intestazione", propOrder = {
    "ufficioDestinatario",
    "impresa",
    "oggettoComunicazione",
    "codicePratica",
    "procuraSpeciale",
    "dichiarante",
    "domicilioElettronico",
    "impiantoProduttivo",
    "protocollo"
})
public class Intestazione {

    @XmlElement(name = "ufficio-destinatario", required = true)
    protected EstremiSuap ufficioDestinatario;
    @XmlElement(required = true)
    protected AnagraficaImpresa impresa;
    @XmlElement(name = "oggetto-comunicazione", required = true)
    protected OggettoComunicazione oggettoComunicazione;
    @XmlElement(name = "codice-pratica")
    protected String codicePratica;
    @XmlElement(name = "procura-speciale")
    protected ProcuraSpeciale procuraSpeciale;
    @XmlElement(required = true)
    protected EstremiDichiarante dichiarante;
    @XmlElement(name = "domicilio-elettronico")
    protected String domicilioElettronico;
    @XmlElement(name = "impianto-produttivo")
    protected ImpiantoProduttivo impiantoProduttivo;
    protected ProtocolloSUAP protocollo;

    /**
     * Recupera il valore della propriet ufficioDestinatario.
     * 
     * @return
     *     possible object is
     *     {@link EstremiSuap }
     *     
     */
    public EstremiSuap getUfficioDestinatario() {
        return ufficioDestinatario;
    }

    /**
     * Imposta il valore della propriet ufficioDestinatario.
     * 
     * @param value
     *     allowed object is
     *     {@link EstremiSuap }
     *     
     */
    public void setUfficioDestinatario(EstremiSuap value) {
        this.ufficioDestinatario = value;
    }

    /**
     * Recupera il valore della propriet impresa.
     * 
     * @return
     *     possible object is
     *     {@link AnagraficaImpresa }
     *     
     */
    public AnagraficaImpresa getImpresa() {
        return impresa;
    }

    /**
     * Imposta il valore della propriet impresa.
     * 
     * @param value
     *     allowed object is
     *     {@link AnagraficaImpresa }
     *     
     */
    public void setImpresa(AnagraficaImpresa value) {
        this.impresa = value;
    }

    /**
     * Recupera il valore della propriet oggettoComunicazione.
     * 
     * @return
     *     possible object is
     *     {@link OggettoComunicazione }
     *     
     */
    public OggettoComunicazione getOggettoComunicazione() {
        return oggettoComunicazione;
    }

    /**
     * Imposta il valore della propriet oggettoComunicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link OggettoComunicazione }
     *     
     */
    public void setOggettoComunicazione(OggettoComunicazione value) {
        this.oggettoComunicazione = value;
    }

    /**
     * Recupera il valore della propriet codicePratica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodicePratica() {
        return codicePratica;
    }

    /**
     * Imposta il valore della propriet codicePratica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodicePratica(String value) {
        this.codicePratica = value;
    }

    /**
     * Recupera il valore della propriet procuraSpeciale.
     * 
     * @return
     *     possible object is
     *     {@link ProcuraSpeciale }
     *     
     */
    public ProcuraSpeciale getProcuraSpeciale() {
        return procuraSpeciale;
    }

    /**
     * Imposta il valore della propriet procuraSpeciale.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcuraSpeciale }
     *     
     */
    public void setProcuraSpeciale(ProcuraSpeciale value) {
        this.procuraSpeciale = value;
    }

    /**
     * Recupera il valore della propriet dichiarante.
     * 
     * @return
     *     possible object is
     *     {@link EstremiDichiarante }
     *     
     */
    public EstremiDichiarante getDichiarante() {
        return dichiarante;
    }

    /**
     * Imposta il valore della propriet dichiarante.
     * 
     * @param value
     *     allowed object is
     *     {@link EstremiDichiarante }
     *     
     */
    public void setDichiarante(EstremiDichiarante value) {
        this.dichiarante = value;
    }

    /**
     * Recupera il valore della propriet domicilioElettronico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomicilioElettronico() {
        return domicilioElettronico;
    }

    /**
     * Imposta il valore della propriet domicilioElettronico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomicilioElettronico(String value) {
        this.domicilioElettronico = value;
    }

    /**
     * Recupera il valore della propriet impiantoProduttivo.
     * 
     * @return
     *     possible object is
     *     {@link ImpiantoProduttivo }
     *     
     */
    public ImpiantoProduttivo getImpiantoProduttivo() {
        return impiantoProduttivo;
    }

    /**
     * Imposta il valore della propriet impiantoProduttivo.
     * 
     * @param value
     *     allowed object is
     *     {@link ImpiantoProduttivo }
     *     
     */
    public void setImpiantoProduttivo(ImpiantoProduttivo value) {
        this.impiantoProduttivo = value;
    }

    /**
     * Recupera il valore della propriet protocollo.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolloSUAP }
     *     
     */
    public ProtocolloSUAP getProtocollo() {
        return protocollo;
    }

    /**
     * Imposta il valore della propriet protocollo.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolloSUAP }
     *     
     */
    public void setProtocollo(ProtocolloSUAP value) {
        this.protocollo = value;
    }

}
