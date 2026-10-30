//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.05.27 alle 07:27:17 PM CEST 
//
package it.wego.cross.xml;

import it.wego.cross.xml.adapter.DateAdapter;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Classe Java per anagrafiche complex type.
 *
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in
 * questa classe.
 *
 * <pre>
 * &lt;complexType name="anagrafiche">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id_anagrafica" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="anagrafica" type="{http://www.wego.it/cross}anagrafica"/>
 *         &lt;element name="notifica" type="{http://www.wego.it/cross}recapito" minOccurs="0"/>
 *         &lt;element name="id_tipo_ruolo" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="cod_tipo_ruolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="des_tipo_ruolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="data_inizio_validita" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="id_tipo_qualifica" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="des_tipo_qualifica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descrizione_titolarita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="assenso_uso_pec" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="pec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "anagrafiche", propOrder = {
    "idAnagrafica",
    "anagrafica",
    "notifica",
    "idTipoRuolo",
    "codTipoRuolo",
    "desTipoRuolo",
    "dataInizioValidita",
    "idTipoQualifica",
    "desTipoQualifica",
    "descrizioneTitolarita",
    "assensoUsoPec",
    "pec"
})
public class Anagrafiche {

    @XmlElement(name = "id_anagrafica")
    protected BigInteger idAnagrafica;
    @XmlElement(required = true)
    protected Anagrafica anagrafica;
    protected Recapito notifica;
    @XmlElement(name = "id_tipo_ruolo")
    protected BigInteger idTipoRuolo;
    @XmlElement(name = "cod_tipo_ruolo")
    protected String codTipoRuolo;
    @XmlElement(name = "des_tipo_ruolo")
    protected String desTipoRuolo;
    @XmlElement(name = "data_inizio_validita")
    @XmlSchemaType(name = "date")
    @XmlJavaTypeAdapter(DateAdapter.class)
    protected XMLGregorianCalendar dataInizioValidita;
    @XmlElement(name = "id_tipo_qualifica")
    protected BigInteger idTipoQualifica;
    @XmlElement(name = "des_tipo_qualifica")
    protected String desTipoQualifica;
    @XmlElement(name = "descrizione_titolarita")
    protected String descrizioneTitolarita;
    @XmlElement(name = "assenso_uso_pec")
    protected Boolean assensoUsoPec;
    protected String pec;

    /**
     * Recupera il valore della propriet idAnagrafica.
     *
     * @return possible object is {@link BigInteger }
     *
     */
    public BigInteger getIdAnagrafica() {
        return idAnagrafica;
    }

    /**
     * Imposta il valore della propriet idAnagrafica.
     *
     * @param value allowed object is {@link BigInteger }
     *
     */
    public void setIdAnagrafica(BigInteger value) {
        this.idAnagrafica = value;
    }

    /**
     * Recupera il valore della proprietanagrafica.
     *
     * @return possible object is {@link Anagrafica }
     *
     */
    public Anagrafica getAnagrafica() {
        return anagrafica;
    }

    /**
     * Imposta il valore della proprietanagrafica.
     *
     * @param value allowed object is {@link Anagrafica }
     *
     */
    public void setAnagrafica(Anagrafica value) {
        this.anagrafica = value;
    }

    /**
     * Recupera il valore della proprietnotifica.
     *
     * @return possible object is {@link Recapito }
     *
     */
    public Recapito getNotifica() {
        return notifica;
    }

    /**
     * Imposta il valore della proprietnotifica.
     *
     * @param value allowed object is {@link Recapito }
     *
     */
    public void setNotifica(Recapito value) {
        this.notifica = value;
    }

    /**
     * Recupera il valore della proprietidTipoRuolo.
     *
     * @return possible object is {@link BigInteger }
     *
     */
    public BigInteger getIdTipoRuolo() {
        return idTipoRuolo;
    }

    /**
     * Imposta il valore della proprietidTipoRuolo.
     *
     * @param value allowed object is {@link BigInteger }
     *
     */
    public void setIdTipoRuolo(BigInteger value) {
        this.idTipoRuolo = value;
    }

    /**
     * Recupera il valore della proprietcodTipoRuolo.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCodTipoRuolo() {
        return codTipoRuolo;
    }

    /**
     * Imposta il valore della proprietcodTipoRuolo.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCodTipoRuolo(String value) {
        this.codTipoRuolo = value;
    }

    /**
     * Recupera il valore della proprietdesTipoRuolo.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDesTipoRuolo() {
        return desTipoRuolo;
    }

    /**
     * Imposta il valore della proprietdesTipoRuolo.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDesTipoRuolo(String value) {
        this.desTipoRuolo = value;
    }

    /**
     * Recupera il valore della proprietdataInizioValidita.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * Imposta il valore della proprietdataInizioValidita.
     *
     * @param value allowed object is {@link XMLGregorianCalendar }
     *
     */
    public void setDataInizioValidita(XMLGregorianCalendar value) {
        this.dataInizioValidita = value;
    }

    /**
     * Recupera il valore della proprietidTipoQualifica.
     *
     * @return possible object is {@link BigInteger }
     *
     */
    public BigInteger getIdTipoQualifica() {
        return idTipoQualifica;
    }

    /**
     * Imposta il valore della proprietidTipoQualifica.
     *
     * @param value allowed object is {@link BigInteger }
     *
     */
    public void setIdTipoQualifica(BigInteger value) {
        this.idTipoQualifica = value;
    }

    /**
     * Recupera il valore della proprietdesTipoQualifica.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDesTipoQualifica() {
        return desTipoQualifica;
    }

    /**
     * Imposta il valore della proprietdesTipoQualifica.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDesTipoQualifica(String value) {
        this.desTipoQualifica = value;
    }

    /**
     * Recupera il valore della proprietdescrizioneTitolarita.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDescrizioneTitolarita() {
        return descrizioneTitolarita;
    }

    /**
     * Imposta il valore della proprietdescrizioneTitolarita.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDescrizioneTitolarita(String value) {
        this.descrizioneTitolarita = value;
    }

    /**
     * Recupera il valore della proprietassensoUsoPec.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public Boolean isAssensoUsoPec() {
        return assensoUsoPec;
    }

    /**
     * Imposta il valore della proprietassensoUsoPec.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setAssensoUsoPec(Boolean value) {
        this.assensoUsoPec = value;
    }

    /**
     * Recupera il valore della proprietpec.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPec() {
        return pec;
    }

    /**
     * Imposta il valore della proprietpec.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPec(String value) {
        this.pec = value;
    }
}
