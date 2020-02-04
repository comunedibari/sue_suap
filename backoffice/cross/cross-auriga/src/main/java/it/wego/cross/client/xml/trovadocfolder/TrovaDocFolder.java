//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.03.22 alle 04:25:41 PM CET 
//


package it.wego.cross.client.xml.trovadocfolder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="FiltriPrincipali" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="TipoOggettiDaCercare" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;pattern value="D"/>
 *                         &lt;pattern value="F"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;sequence minOccurs="0">
 *                     &lt;element name="CercaInVistaUtente" minOccurs="0">
 *                       &lt;simpleType>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                           &lt;pattern value="BOZZE"/>
 *                           &lt;pattern value="PREFERITI"/>
 *                           &lt;pattern value="INVIATI"/>
 *                           &lt;pattern value="ELIMINATI"/>
 *                           &lt;pattern value="NEWS"/>
 *                           &lt;pattern value="WORK"/>
 *                         &lt;/restriction>
 *                       &lt;/simpleType>
 *                     &lt;/element>
 *                     &lt;element name="CercaInFolder" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;extension base="{}EstremiXIdentificazioneFolderType">
 *                             &lt;sequence>
 *                               &lt;element name="IncludiSubFolder" type="{}FlagSiNoType"/>
 *                             &lt;/sequence>
 *                           &lt;/extension>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/sequence>
 *                   &lt;element name="SoloRecenti" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;pattern value="V"/>
 *                         &lt;pattern value="L"/>
 *                         &lt;pattern value="D"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="SoloDaLeggere" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                   &lt;element name="FiltroFullText" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ListaParole" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="FlagTutteLeParole" type="{}FlagSiNoType"/>
 *                             &lt;element name="LimitaRiceraAdAttributo" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;pattern value="#FILE"/>
 *                                   &lt;pattern value="NOME"/>
 *                                   &lt;pattern value="NOMINATIVI_ESTERNI"/>
 *                                   &lt;pattern value="PAROLE_CHIAVE"/>
 *                                   &lt;pattern value="DES_OGG"/>
 *                                   &lt;pattern value="FILENAME"/>
 *                                   &lt;pattern value="NOTE"/>
 *                                   &lt;pattern value="ESTREMI_REG_NUM"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="FiltriAvanzati" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="NewsConNotificheCondivisione" type="{}FlagSiNoType" minOccurs="0"/>
 *                   &lt;element name="NewsConNotificheAutomatiche" type="{}FlagSiNoType" minOccurs="0"/>
 *                   &lt;element name="NewsConOsservazioni" type="{}FlagSiNoType" minOccurs="0"/>
 *                   &lt;element name="TipoDocumento" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
 *                   &lt;element name="TipoFolder" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
 *                   &lt;element name="StatoDocumento" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="StatoPrincipale" type="{}OggDiTabDiSistemaType" maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;element name="StatoDettaglio" type="{}OggDiTabDiSistemaType" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="StatoFolder" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="StatoPrincipale" type="{}OggDiTabDiSistemaType" maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;element name="StatoDettaglio" type="{}OggDiTabDiSistemaType" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="DataAggiornamentoStatoDa" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="DataAggiornamentoStatoA" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="SoloConLock" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence minOccurs="0">
 *                             &lt;choice minOccurs="0">
 *                               &lt;element name="DaUtenteConnesso" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                               &lt;element name="DaAltroUtente" type="{}UserType" minOccurs="0"/>
 *                             &lt;/choice>
 *                             &lt;element name="DaAlmenoGiorni" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="ApplicazionePropietaria" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="CodApplicazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="CodIstanzaApplicazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="RegistrazioneDoc" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;all>
 *                             &lt;element name="CategoriaReg" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;enumeration value="PG"/>
 *                                   &lt;enumeration value="PP"/>
 *                                   &lt;enumeration value="R"/>
 *                                   &lt;enumeration value="E"/>
 *                                   &lt;enumeration value="A"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="SiglaReg" type="{}SiglaRegNumType" minOccurs="0"/>
 *                             &lt;element name="AnnoReg" type="{}AnnoType" minOccurs="0"/>
 *                             &lt;element name="NumRegDa" type="{}NumRegType" minOccurs="0"/>
 *                             &lt;element name="NumRegA" type="{}NumRegType" minOccurs="0"/>
 *                             &lt;element name="DataRegDa" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                             &lt;element name="DataRegA" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                           &lt;/all>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="RuoloUtenteVsDocFolder" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
 *                   &lt;element name="AttributoAdd" type="{}CriterioRicercaSuAttributoAddType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="LimitaEstrazioneAlCampo" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="EstraiAncheAttributoCustom" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Ordinamento" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="PerCampo" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>positiveInteger">
 *                           &lt;attribute name="VersoDecrescente" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" fixed="1" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="EstrazionePaginata" type="{}PaginazioneType" minOccurs="0"/>
 *         &lt;element name="Off-line" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
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
    "filtriPrincipali",
    "filtriAvanzati",
    "limitaEstrazioneAlCampo",
    "estraiAncheAttributoCustom",
    "ordinamento",
    "estrazionePaginata",
    "offLine"
})
@XmlRootElement(name = "TrovaDocFolder")
public class TrovaDocFolder {

    @XmlElement(name = "FiltriPrincipali")
    protected TrovaDocFolder.FiltriPrincipali filtriPrincipali;
    @XmlElement(name = "FiltriAvanzati")
    protected TrovaDocFolder.FiltriAvanzati filtriAvanzati;
    @XmlElement(name = "LimitaEstrazioneAlCampo")
    @XmlSchemaType(name = "positiveInteger")
    protected List<BigInteger> limitaEstrazioneAlCampo;
    @XmlElement(name = "EstraiAncheAttributoCustom")
    protected List<String> estraiAncheAttributoCustom;
    @XmlElement(name = "Ordinamento")
    protected TrovaDocFolder.Ordinamento ordinamento;
    @XmlElement(name = "EstrazionePaginata")
    protected PaginazioneType estrazionePaginata;
    @XmlElement(name = "Off-line")
    protected Integer offLine;

    /**
     * Recupera il valore della proprietfiltriPrincipali.
     * 
     * @return
     *     possible object is
     *     {@link TrovaDocFolder.FiltriPrincipali }
     *     
     */
    public TrovaDocFolder.FiltriPrincipali getFiltriPrincipali() {
        return filtriPrincipali;
    }

    /**
     * Imposta il valore della proprietfiltriPrincipali.
     * 
     * @param value
     *     allowed object is
     *     {@link TrovaDocFolder.FiltriPrincipali }
     *     
     */
    public void setFiltriPrincipali(TrovaDocFolder.FiltriPrincipali value) {
        this.filtriPrincipali = value;
    }

    /**
     * Recupera il valore della proprietfiltriAvanzati.
     * 
     * @return
     *     possible object is
     *     {@link TrovaDocFolder.FiltriAvanzati }
     *     
     */
    public TrovaDocFolder.FiltriAvanzati getFiltriAvanzati() {
        return filtriAvanzati;
    }

    /**
     * Imposta il valore della proprietfiltriAvanzati.
     * 
     * @param value
     *     allowed object is
     *     {@link TrovaDocFolder.FiltriAvanzati }
     *     
     */
    public void setFiltriAvanzati(TrovaDocFolder.FiltriAvanzati value) {
        this.filtriAvanzati = value;
    }

    /**
     * Gets the value of the limitaEstrazioneAlCampo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the limitaEstrazioneAlCampo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLimitaEstrazioneAlCampo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * 
     * 
     */
    public List<BigInteger> getLimitaEstrazioneAlCampo() {
        if (limitaEstrazioneAlCampo == null) {
            limitaEstrazioneAlCampo = new ArrayList<BigInteger>();
        }
        return this.limitaEstrazioneAlCampo;
    }

    /**
     * Gets the value of the estraiAncheAttributoCustom property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the estraiAncheAttributoCustom property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEstraiAncheAttributoCustom().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getEstraiAncheAttributoCustom() {
        if (estraiAncheAttributoCustom == null) {
            estraiAncheAttributoCustom = new ArrayList<String>();
        }
        return this.estraiAncheAttributoCustom;
    }

    /**
     * Recupera il valore della proprietordinamento.
     * 
     * @return
     *     possible object is
     *     {@link TrovaDocFolder.Ordinamento }
     *     
     */
    public TrovaDocFolder.Ordinamento getOrdinamento() {
        return ordinamento;
    }

    /**
     * Imposta il valore della proprietordinamento.
     * 
     * @param value
     *     allowed object is
     *     {@link TrovaDocFolder.Ordinamento }
     *     
     */
    public void setOrdinamento(TrovaDocFolder.Ordinamento value) {
        this.ordinamento = value;
    }

    /**
     * Recupera il valore della proprietestrazionePaginata.
     * 
     * @return
     *     possible object is
     *     {@link PaginazioneType }
     *     
     */
    public PaginazioneType getEstrazionePaginata() {
        return estrazionePaginata;
    }

    /**
     * Imposta il valore della proprietestrazionePaginata.
     * 
     * @param value
     *     allowed object is
     *     {@link PaginazioneType }
     *     
     */
    public void setEstrazionePaginata(PaginazioneType value) {
        this.estrazionePaginata = value;
    }

    /**
     * Recupera il valore della proprietoffLine.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOffLine() {
        return offLine;
    }

    /**
     * Imposta il valore della proprietoffLine.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOffLine(Integer value) {
        this.offLine = value;
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
     *         &lt;element name="NewsConNotificheCondivisione" type="{}FlagSiNoType" minOccurs="0"/>
     *         &lt;element name="NewsConNotificheAutomatiche" type="{}FlagSiNoType" minOccurs="0"/>
     *         &lt;element name="NewsConOsservazioni" type="{}FlagSiNoType" minOccurs="0"/>
     *         &lt;element name="TipoDocumento" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
     *         &lt;element name="TipoFolder" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
     *         &lt;element name="StatoDocumento" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="StatoPrincipale" type="{}OggDiTabDiSistemaType" maxOccurs="unbounded" minOccurs="0"/>
     *                   &lt;element name="StatoDettaglio" type="{}OggDiTabDiSistemaType" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="StatoFolder" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="StatoPrincipale" type="{}OggDiTabDiSistemaType" maxOccurs="unbounded" minOccurs="0"/>
     *                   &lt;element name="StatoDettaglio" type="{}OggDiTabDiSistemaType" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="DataAggiornamentoStatoDa" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="DataAggiornamentoStatoA" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="SoloConLock" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence minOccurs="0">
     *                   &lt;choice minOccurs="0">
     *                     &lt;element name="DaUtenteConnesso" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                     &lt;element name="DaAltroUtente" type="{}UserType" minOccurs="0"/>
     *                   &lt;/choice>
     *                   &lt;element name="DaAlmenoGiorni" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="ApplicazionePropietaria" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="CodApplicazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="CodIstanzaApplicazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="RegistrazioneDoc" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;all>
     *                   &lt;element name="CategoriaReg" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;enumeration value="PG"/>
     *                         &lt;enumeration value="PP"/>
     *                         &lt;enumeration value="R"/>
     *                         &lt;enumeration value="E"/>
     *                         &lt;enumeration value="A"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="SiglaReg" type="{}SiglaRegNumType" minOccurs="0"/>
     *                   &lt;element name="AnnoReg" type="{}AnnoType" minOccurs="0"/>
     *                   &lt;element name="NumRegDa" type="{}NumRegType" minOccurs="0"/>
     *                   &lt;element name="NumRegA" type="{}NumRegType" minOccurs="0"/>
     *                   &lt;element name="DataRegDa" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *                   &lt;element name="DataRegA" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                 &lt;/all>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="RuoloUtenteVsDocFolder" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
     *         &lt;element name="AttributoAdd" type="{}CriterioRicercaSuAttributoAddType" maxOccurs="unbounded" minOccurs="0"/>
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
        "newsConNotificheCondivisione",
        "newsConNotificheAutomatiche",
        "newsConOsservazioni",
        "tipoDocumento",
        "tipoFolder",
        "statoDocumento",
        "statoFolder",
        "dataAggiornamentoStatoDa",
        "dataAggiornamentoStatoA",
        "soloConLock",
        "applicazionePropietaria",
        "registrazioneDoc",
        "ruoloUtenteVsDocFolder",
        "attributoAdd"
    })
    public static class FiltriAvanzati {

        @XmlElement(name = "NewsConNotificheCondivisione")
        protected String newsConNotificheCondivisione;
        @XmlElement(name = "NewsConNotificheAutomatiche")
        protected String newsConNotificheAutomatiche;
        @XmlElement(name = "NewsConOsservazioni")
        protected String newsConOsservazioni;
        @XmlElement(name = "TipoDocumento")
        protected OggDiTabDiSistemaType tipoDocumento;
        @XmlElement(name = "TipoFolder")
        protected OggDiTabDiSistemaType tipoFolder;
        @XmlElement(name = "StatoDocumento")
        protected TrovaDocFolder.FiltriAvanzati.StatoDocumento statoDocumento;
        @XmlElement(name = "StatoFolder")
        protected TrovaDocFolder.FiltriAvanzati.StatoFolder statoFolder;
        @XmlElement(name = "DataAggiornamentoStatoDa")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar dataAggiornamentoStatoDa;
        @XmlElement(name = "DataAggiornamentoStatoA")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar dataAggiornamentoStatoA;
        @XmlElement(name = "SoloConLock")
        protected TrovaDocFolder.FiltriAvanzati.SoloConLock soloConLock;
        @XmlElement(name = "ApplicazionePropietaria")
        protected TrovaDocFolder.FiltriAvanzati.ApplicazionePropietaria applicazionePropietaria;
        @XmlElement(name = "RegistrazioneDoc")
        protected TrovaDocFolder.FiltriAvanzati.RegistrazioneDoc registrazioneDoc;
        @XmlElement(name = "RuoloUtenteVsDocFolder")
        protected OggDiTabDiSistemaType ruoloUtenteVsDocFolder;
        @XmlElement(name = "AttributoAdd")
        protected List<CriterioRicercaSuAttributoAddType> attributoAdd;

        /**
         * Recupera il valore della proprietnewsConNotificheCondivisione.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNewsConNotificheCondivisione() {
            return newsConNotificheCondivisione;
        }

        /**
         * Imposta il valore della proprietnewsConNotificheCondivisione.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNewsConNotificheCondivisione(String value) {
            this.newsConNotificheCondivisione = value;
        }

        /**
         * Recupera il valore della proprietnewsConNotificheAutomatiche.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNewsConNotificheAutomatiche() {
            return newsConNotificheAutomatiche;
        }

        /**
         * Imposta il valore della proprietnewsConNotificheAutomatiche.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNewsConNotificheAutomatiche(String value) {
            this.newsConNotificheAutomatiche = value;
        }

        /**
         * Recupera il valore della proprietnewsConOsservazioni.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNewsConOsservazioni() {
            return newsConOsservazioni;
        }

        /**
         * Imposta il valore della proprietnewsConOsservazioni.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNewsConOsservazioni(String value) {
            this.newsConOsservazioni = value;
        }

        /**
         * Recupera il valore della propriettipoDocumento.
         * 
         * @return
         *     possible object is
         *     {@link OggDiTabDiSistemaType }
         *     
         */
        public OggDiTabDiSistemaType getTipoDocumento() {
            return tipoDocumento;
        }

        /**
         * Imposta il valore della propriettipoDocumento.
         * 
         * @param value
         *     allowed object is
         *     {@link OggDiTabDiSistemaType }
         *     
         */
        public void setTipoDocumento(OggDiTabDiSistemaType value) {
            this.tipoDocumento = value;
        }

        /**
         * Recupera il valore della propriettipoFolder.
         * 
         * @return
         *     possible object is
         *     {@link OggDiTabDiSistemaType }
         *     
         */
        public OggDiTabDiSistemaType getTipoFolder() {
            return tipoFolder;
        }

        /**
         * Imposta il valore della propriettipoFolder.
         * 
         * @param value
         *     allowed object is
         *     {@link OggDiTabDiSistemaType }
         *     
         */
        public void setTipoFolder(OggDiTabDiSistemaType value) {
            this.tipoFolder = value;
        }

        /**
         * Recupera il valore della proprietstatoDocumento.
         * 
         * @return
         *     possible object is
         *     {@link TrovaDocFolder.FiltriAvanzati.StatoDocumento }
         *     
         */
        public TrovaDocFolder.FiltriAvanzati.StatoDocumento getStatoDocumento() {
            return statoDocumento;
        }

        /**
         * Imposta il valore della proprietstatoDocumento.
         * 
         * @param value
         *     allowed object is
         *     {@link TrovaDocFolder.FiltriAvanzati.StatoDocumento }
         *     
         */
        public void setStatoDocumento(TrovaDocFolder.FiltriAvanzati.StatoDocumento value) {
            this.statoDocumento = value;
        }

        /**
         * Recupera il valore della proprietstatoFolder.
         * 
         * @return
         *     possible object is
         *     {@link TrovaDocFolder.FiltriAvanzati.StatoFolder }
         *     
         */
        public TrovaDocFolder.FiltriAvanzati.StatoFolder getStatoFolder() {
            return statoFolder;
        }

        /**
         * Imposta il valore della proprietstatoFolder.
         * 
         * @param value
         *     allowed object is
         *     {@link TrovaDocFolder.FiltriAvanzati.StatoFolder }
         *     
         */
        public void setStatoFolder(TrovaDocFolder.FiltriAvanzati.StatoFolder value) {
            this.statoFolder = value;
        }

        /**
         * Recupera il valore della proprietdataAggiornamentoStatoDa.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDataAggiornamentoStatoDa() {
            return dataAggiornamentoStatoDa;
        }

        /**
         * Imposta il valore della proprietdataAggiornamentoStatoDa.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDataAggiornamentoStatoDa(XMLGregorianCalendar value) {
            this.dataAggiornamentoStatoDa = value;
        }

        /**
         * Recupera il valore della proprietdataAggiornamentoStatoA.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDataAggiornamentoStatoA() {
            return dataAggiornamentoStatoA;
        }

        /**
         * Imposta il valore della proprietdataAggiornamentoStatoA.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDataAggiornamentoStatoA(XMLGregorianCalendar value) {
            this.dataAggiornamentoStatoA = value;
        }

        /**
         * Recupera il valore della proprietsoloConLock.
         * 
         * @return
         *     possible object is
         *     {@link TrovaDocFolder.FiltriAvanzati.SoloConLock }
         *     
         */
        public TrovaDocFolder.FiltriAvanzati.SoloConLock getSoloConLock() {
            return soloConLock;
        }

        /**
         * Imposta il valore della proprietsoloConLock.
         * 
         * @param value
         *     allowed object is
         *     {@link TrovaDocFolder.FiltriAvanzati.SoloConLock }
         *     
         */
        public void setSoloConLock(TrovaDocFolder.FiltriAvanzati.SoloConLock value) {
            this.soloConLock = value;
        }

        /**
         * Recupera il valore della proprietapplicazionePropietaria.
         * 
         * @return
         *     possible object is
         *     {@link TrovaDocFolder.FiltriAvanzati.ApplicazionePropietaria }
         *     
         */
        public TrovaDocFolder.FiltriAvanzati.ApplicazionePropietaria getApplicazionePropietaria() {
            return applicazionePropietaria;
        }

        /**
         * Imposta il valore della proprietapplicazionePropietaria.
         * 
         * @param value
         *     allowed object is
         *     {@link TrovaDocFolder.FiltriAvanzati.ApplicazionePropietaria }
         *     
         */
        public void setApplicazionePropietaria(TrovaDocFolder.FiltriAvanzati.ApplicazionePropietaria value) {
            this.applicazionePropietaria = value;
        }

        /**
         * Recupera il valore della proprietregistrazioneDoc.
         * 
         * @return
         *     possible object is
         *     {@link TrovaDocFolder.FiltriAvanzati.RegistrazioneDoc }
         *     
         */
        public TrovaDocFolder.FiltriAvanzati.RegistrazioneDoc getRegistrazioneDoc() {
            return registrazioneDoc;
        }

        /**
         * Imposta il valore della proprietregistrazioneDoc.
         * 
         * @param value
         *     allowed object is
         *     {@link TrovaDocFolder.FiltriAvanzati.RegistrazioneDoc }
         *     
         */
        public void setRegistrazioneDoc(TrovaDocFolder.FiltriAvanzati.RegistrazioneDoc value) {
            this.registrazioneDoc = value;
        }

        /**
         * Recupera il valore della proprietruoloUtenteVsDocFolder.
         * 
         * @return
         *     possible object is
         *     {@link OggDiTabDiSistemaType }
         *     
         */
        public OggDiTabDiSistemaType getRuoloUtenteVsDocFolder() {
            return ruoloUtenteVsDocFolder;
        }

        /**
         * Imposta il valore della proprietruoloUtenteVsDocFolder.
         * 
         * @param value
         *     allowed object is
         *     {@link OggDiTabDiSistemaType }
         *     
         */
        public void setRuoloUtenteVsDocFolder(OggDiTabDiSistemaType value) {
            this.ruoloUtenteVsDocFolder = value;
        }

        /**
         * Gets the value of the attributoAdd property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the attributoAdd property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAttributoAdd().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CriterioRicercaSuAttributoAddType }
         * 
         * 
         */
        public List<CriterioRicercaSuAttributoAddType> getAttributoAdd() {
            if (attributoAdd == null) {
                attributoAdd = new ArrayList<CriterioRicercaSuAttributoAddType>();
            }
            return this.attributoAdd;
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
         *         &lt;element name="CodApplicazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="CodIstanzaApplicazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
            "codApplicazione",
            "codIstanzaApplicazione"
        })
        public static class ApplicazionePropietaria {

            @XmlElement(name = "CodApplicazione", required = true, defaultValue = "#BY_CONN_TKN")
            protected String codApplicazione;
            @XmlElement(name = "CodIstanzaApplicazione", defaultValue = "#BY_CONN_TKN")
            protected String codIstanzaApplicazione;

            /**
             * Recupera il valore della proprietcodApplicazione.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodApplicazione() {
                return codApplicazione;
            }

            /**
             * Imposta il valore della proprietcodApplicazione.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodApplicazione(String value) {
                this.codApplicazione = value;
            }

            /**
             * Recupera il valore della proprietcodIstanzaApplicazione.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodIstanzaApplicazione() {
                return codIstanzaApplicazione;
            }

            /**
             * Imposta il valore della proprietcodIstanzaApplicazione.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodIstanzaApplicazione(String value) {
                this.codIstanzaApplicazione = value;
            }

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
         *       &lt;all>
         *         &lt;element name="CategoriaReg" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;enumeration value="PG"/>
         *               &lt;enumeration value="PP"/>
         *               &lt;enumeration value="R"/>
         *               &lt;enumeration value="E"/>
         *               &lt;enumeration value="A"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="SiglaReg" type="{}SiglaRegNumType" minOccurs="0"/>
         *         &lt;element name="AnnoReg" type="{}AnnoType" minOccurs="0"/>
         *         &lt;element name="NumRegDa" type="{}NumRegType" minOccurs="0"/>
         *         &lt;element name="NumRegA" type="{}NumRegType" minOccurs="0"/>
         *         &lt;element name="DataRegDa" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
         *         &lt;element name="DataRegA" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *       &lt;/all>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {

        })
        public static class RegistrazioneDoc {

            @XmlElement(name = "CategoriaReg")
            protected String categoriaReg;
            @XmlElement(name = "SiglaReg")
            protected String siglaReg;
            @XmlElement(name = "AnnoReg")
            protected Integer annoReg;
            @XmlElement(name = "NumRegDa")
            protected Integer numRegDa;
            @XmlElement(name = "NumRegA")
            protected Integer numRegA;
            @XmlElement(name = "DataRegDa")
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar dataRegDa;
            @XmlElement(name = "DataRegA")
            protected Object dataRegA;

            /**
             * Recupera il valore della proprietcategoriaReg.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCategoriaReg() {
                return categoriaReg;
            }

            /**
             * Imposta il valore della proprietcategoriaReg.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCategoriaReg(String value) {
                this.categoriaReg = value;
            }

            /**
             * Recupera il valore della proprietsiglaReg.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSiglaReg() {
                return siglaReg;
            }

            /**
             * Imposta il valore della proprietsiglaReg.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSiglaReg(String value) {
                this.siglaReg = value;
            }

            /**
             * Recupera il valore della proprietannoReg.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getAnnoReg() {
                return annoReg;
            }

            /**
             * Imposta il valore della proprietannoReg.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setAnnoReg(Integer value) {
                this.annoReg = value;
            }

            /**
             * Recupera il valore della proprietnumRegDa.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getNumRegDa() {
                return numRegDa;
            }

            /**
             * Imposta il valore della proprietnumRegDa.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setNumRegDa(Integer value) {
                this.numRegDa = value;
            }

            /**
             * Recupera il valore della proprietnumRegA.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getNumRegA() {
                return numRegA;
            }

            /**
             * Imposta il valore della proprietnumRegA.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setNumRegA(Integer value) {
                this.numRegA = value;
            }

            /**
             * Recupera il valore della proprietdataRegDa.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getDataRegDa() {
                return dataRegDa;
            }

            /**
             * Imposta il valore della proprietdataRegDa.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setDataRegDa(XMLGregorianCalendar value) {
                this.dataRegDa = value;
            }

            /**
             * Recupera il valore della proprietdataRegA.
             * 
             * @return
             *     possible object is
             *     {@link Object }
             *     
             */
            public Object getDataRegA() {
                return dataRegA;
            }

            /**
             * Imposta il valore della proprietdataRegA.
             * 
             * @param value
             *     allowed object is
             *     {@link Object }
             *     
             */
            public void setDataRegA(Object value) {
                this.dataRegA = value;
            }

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
         *       &lt;sequence minOccurs="0">
         *         &lt;choice minOccurs="0">
         *           &lt;element name="DaUtenteConnesso" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *           &lt;element name="DaAltroUtente" type="{}UserType" minOccurs="0"/>
         *         &lt;/choice>
         *         &lt;element name="DaAlmenoGiorni" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
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
            "daUtenteConnesso",
            "daAltroUtente",
            "daAlmenoGiorni"
        })
        public static class SoloConLock {

            @XmlElement(name = "DaUtenteConnesso")
            protected Object daUtenteConnesso;
            @XmlElement(name = "DaAltroUtente")
            protected UserType daAltroUtente;
            @XmlElement(name = "DaAlmenoGiorni")
            @XmlSchemaType(name = "positiveInteger")
            protected BigInteger daAlmenoGiorni;

            /**
             * Recupera il valore della proprietdaUtenteConnesso.
             * 
             * @return
             *     possible object is
             *     {@link Object }
             *     
             */
            public Object getDaUtenteConnesso() {
                return daUtenteConnesso;
            }

            /**
             * Imposta il valore della proprietdaUtenteConnesso.
             * 
             * @param value
             *     allowed object is
             *     {@link Object }
             *     
             */
            public void setDaUtenteConnesso(Object value) {
                this.daUtenteConnesso = value;
            }

            /**
             * Recupera il valore della proprietdaAltroUtente.
             * 
             * @return
             *     possible object is
             *     {@link UserType }
             *     
             */
            public UserType getDaAltroUtente() {
                return daAltroUtente;
            }

            /**
             * Imposta il valore della proprietdaAltroUtente.
             * 
             * @param value
             *     allowed object is
             *     {@link UserType }
             *     
             */
            public void setDaAltroUtente(UserType value) {
                this.daAltroUtente = value;
            }

            /**
             * Recupera il valore della proprietdaAlmenoGiorni.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getDaAlmenoGiorni() {
                return daAlmenoGiorni;
            }

            /**
             * Imposta il valore della proprietdaAlmenoGiorni.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setDaAlmenoGiorni(BigInteger value) {
                this.daAlmenoGiorni = value;
            }

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
         *         &lt;element name="StatoPrincipale" type="{}OggDiTabDiSistemaType" maxOccurs="unbounded" minOccurs="0"/>
         *         &lt;element name="StatoDettaglio" type="{}OggDiTabDiSistemaType" maxOccurs="unbounded" minOccurs="0"/>
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
            "statoPrincipale",
            "statoDettaglio"
        })
        public static class StatoDocumento {

            @XmlElement(name = "StatoPrincipale")
            protected List<OggDiTabDiSistemaType> statoPrincipale;
            @XmlElement(name = "StatoDettaglio")
            protected List<OggDiTabDiSistemaType> statoDettaglio;

            /**
             * Gets the value of the statoPrincipale property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the statoPrincipale property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getStatoPrincipale().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link OggDiTabDiSistemaType }
             * 
             * 
             */
            public List<OggDiTabDiSistemaType> getStatoPrincipale() {
                if (statoPrincipale == null) {
                    statoPrincipale = new ArrayList<OggDiTabDiSistemaType>();
                }
                return this.statoPrincipale;
            }

            /**
             * Gets the value of the statoDettaglio property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the statoDettaglio property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getStatoDettaglio().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link OggDiTabDiSistemaType }
             * 
             * 
             */
            public List<OggDiTabDiSistemaType> getStatoDettaglio() {
                if (statoDettaglio == null) {
                    statoDettaglio = new ArrayList<OggDiTabDiSistemaType>();
                }
                return this.statoDettaglio;
            }

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
         *         &lt;element name="StatoPrincipale" type="{}OggDiTabDiSistemaType" maxOccurs="unbounded" minOccurs="0"/>
         *         &lt;element name="StatoDettaglio" type="{}OggDiTabDiSistemaType" maxOccurs="unbounded" minOccurs="0"/>
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
            "statoPrincipale",
            "statoDettaglio"
        })
        public static class StatoFolder {

            @XmlElement(name = "StatoPrincipale")
            protected List<OggDiTabDiSistemaType> statoPrincipale;
            @XmlElement(name = "StatoDettaglio")
            protected List<OggDiTabDiSistemaType> statoDettaglio;

            /**
             * Gets the value of the statoPrincipale property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the statoPrincipale property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getStatoPrincipale().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link OggDiTabDiSistemaType }
             * 
             * 
             */
            public List<OggDiTabDiSistemaType> getStatoPrincipale() {
                if (statoPrincipale == null) {
                    statoPrincipale = new ArrayList<OggDiTabDiSistemaType>();
                }
                return this.statoPrincipale;
            }

            /**
             * Gets the value of the statoDettaglio property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the statoDettaglio property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getStatoDettaglio().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link OggDiTabDiSistemaType }
             * 
             * 
             */
            public List<OggDiTabDiSistemaType> getStatoDettaglio() {
                if (statoDettaglio == null) {
                    statoDettaglio = new ArrayList<OggDiTabDiSistemaType>();
                }
                return this.statoDettaglio;
            }

        }

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
     *         &lt;element name="TipoOggettiDaCercare" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;pattern value="D"/>
     *               &lt;pattern value="F"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;sequence minOccurs="0">
     *           &lt;element name="CercaInVistaUtente" minOccurs="0">
     *             &lt;simpleType>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                 &lt;pattern value="BOZZE"/>
     *                 &lt;pattern value="PREFERITI"/>
     *                 &lt;pattern value="INVIATI"/>
     *                 &lt;pattern value="ELIMINATI"/>
     *                 &lt;pattern value="NEWS"/>
     *                 &lt;pattern value="WORK"/>
     *               &lt;/restriction>
     *             &lt;/simpleType>
     *           &lt;/element>
     *           &lt;element name="CercaInFolder" minOccurs="0">
     *             &lt;complexType>
     *               &lt;complexContent>
     *                 &lt;extension base="{}EstremiXIdentificazioneFolderType">
     *                   &lt;sequence>
     *                     &lt;element name="IncludiSubFolder" type="{}FlagSiNoType"/>
     *                   &lt;/sequence>
     *                 &lt;/extension>
     *               &lt;/complexContent>
     *             &lt;/complexType>
     *           &lt;/element>
     *         &lt;/sequence>
     *         &lt;element name="SoloRecenti" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;pattern value="V"/>
     *               &lt;pattern value="L"/>
     *               &lt;pattern value="D"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="SoloDaLeggere" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *         &lt;element name="FiltroFullText" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ListaParole" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="FlagTutteLeParole" type="{}FlagSiNoType"/>
     *                   &lt;element name="LimitaRiceraAdAttributo" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;pattern value="#FILE"/>
     *                         &lt;pattern value="NOME"/>
     *                         &lt;pattern value="NOMINATIVI_ESTERNI"/>
     *                         &lt;pattern value="PAROLE_CHIAVE"/>
     *                         &lt;pattern value="DES_OGG"/>
     *                         &lt;pattern value="FILENAME"/>
     *                         &lt;pattern value="NOTE"/>
     *                         &lt;pattern value="ESTREMI_REG_NUM"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
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
        "tipoOggettiDaCercare",
        "cercaInVistaUtente",
        "cercaInFolder",
        "soloRecenti",
        "soloDaLeggere",
        "filtroFullText"
    })
    public static class FiltriPrincipali {

        @XmlElement(name = "TipoOggettiDaCercare")
        protected String tipoOggettiDaCercare;
        @XmlElement(name = "CercaInVistaUtente")
        protected String cercaInVistaUtente;
        @XmlElement(name = "CercaInFolder")
        protected TrovaDocFolder.FiltriPrincipali.CercaInFolder cercaInFolder;
        @XmlElement(name = "SoloRecenti")
        protected String soloRecenti;
        @XmlElement(name = "SoloDaLeggere")
        protected Object soloDaLeggere;
        @XmlElement(name = "FiltroFullText")
        protected TrovaDocFolder.FiltriPrincipali.FiltroFullText filtroFullText;

        /**
         * Recupera il valore della propriettipoOggettiDaCercare.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTipoOggettiDaCercare() {
            return tipoOggettiDaCercare;
        }

        /**
         * Imposta il valore della propriettipoOggettiDaCercare.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTipoOggettiDaCercare(String value) {
            this.tipoOggettiDaCercare = value;
        }

        /**
         * Recupera il valore della proprietcercaInVistaUtente.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCercaInVistaUtente() {
            return cercaInVistaUtente;
        }

        /**
         * Imposta il valore della proprietcercaInVistaUtente.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCercaInVistaUtente(String value) {
            this.cercaInVistaUtente = value;
        }

        /**
         * Recupera il valore della proprietcercaInFolder.
         * 
         * @return
         *     possible object is
         *     {@link TrovaDocFolder.FiltriPrincipali.CercaInFolder }
         *     
         */
        public TrovaDocFolder.FiltriPrincipali.CercaInFolder getCercaInFolder() {
            return cercaInFolder;
        }

        /**
         * Imposta il valore della proprietcercaInFolder.
         * 
         * @param value
         *     allowed object is
         *     {@link TrovaDocFolder.FiltriPrincipali.CercaInFolder }
         *     
         */
        public void setCercaInFolder(TrovaDocFolder.FiltriPrincipali.CercaInFolder value) {
            this.cercaInFolder = value;
        }

        /**
         * Recupera il valore della proprietsoloRecenti.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSoloRecenti() {
            return soloRecenti;
        }

        /**
         * Imposta il valore della proprietsoloRecenti.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSoloRecenti(String value) {
            this.soloRecenti = value;
        }

        /**
         * Recupera il valore della proprietsoloDaLeggere.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getSoloDaLeggere() {
            return soloDaLeggere;
        }

        /**
         * Imposta il valore della proprietsoloDaLeggere.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setSoloDaLeggere(Object value) {
            this.soloDaLeggere = value;
        }

        /**
         * Recupera il valore della proprietfiltroFullText.
         * 
         * @return
         *     possible object is
         *     {@link TrovaDocFolder.FiltriPrincipali.FiltroFullText }
         *     
         */
        public TrovaDocFolder.FiltriPrincipali.FiltroFullText getFiltroFullText() {
            return filtroFullText;
        }

        /**
         * Imposta il valore della proprietfiltroFullText.
         * 
         * @param value
         *     allowed object is
         *     {@link TrovaDocFolder.FiltriPrincipali.FiltroFullText }
         *     
         */
        public void setFiltroFullText(TrovaDocFolder.FiltriPrincipali.FiltroFullText value) {
            this.filtroFullText = value;
        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{}EstremiXIdentificazioneFolderType">
         *       &lt;sequence>
         *         &lt;element name="IncludiSubFolder" type="{}FlagSiNoType"/>
         *       &lt;/sequence>
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "includiSubFolder"
        })
        public static class CercaInFolder
            extends EstremiXIdentificazioneFolderType
        {

            @XmlElement(name = "IncludiSubFolder", required = true, defaultValue = "1")
            protected String includiSubFolder;

            /**
             * Recupera il valore della proprietincludiSubFolder.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIncludiSubFolder() {
                return includiSubFolder;
            }

            /**
             * Imposta il valore della proprietincludiSubFolder.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIncludiSubFolder(String value) {
                this.includiSubFolder = value;
            }

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
         *         &lt;element name="ListaParole" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="FlagTutteLeParole" type="{}FlagSiNoType"/>
         *         &lt;element name="LimitaRiceraAdAttributo" maxOccurs="unbounded" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;pattern value="#FILE"/>
         *               &lt;pattern value="NOME"/>
         *               &lt;pattern value="NOMINATIVI_ESTERNI"/>
         *               &lt;pattern value="PAROLE_CHIAVE"/>
         *               &lt;pattern value="DES_OGG"/>
         *               &lt;pattern value="FILENAME"/>
         *               &lt;pattern value="NOTE"/>
         *               &lt;pattern value="ESTREMI_REG_NUM"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
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
            "listaParole",
            "flagTutteLeParole",
            "limitaRiceraAdAttributo"
        })
        public static class FiltroFullText {

            @XmlElement(name = "ListaParole", required = true)
            protected String listaParole;
            @XmlElement(name = "FlagTutteLeParole", required = true)
            protected String flagTutteLeParole;
            @XmlElement(name = "LimitaRiceraAdAttributo")
            protected List<String> limitaRiceraAdAttributo;

            /**
             * Recupera il valore della proprietlistaParole.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getListaParole() {
                return listaParole;
            }

            /**
             * Imposta il valore della proprietlistaParole.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setListaParole(String value) {
                this.listaParole = value;
            }

            /**
             * Recupera il valore della proprietflagTutteLeParole.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFlagTutteLeParole() {
                return flagTutteLeParole;
            }

            /**
             * Imposta il valore della proprietflagTutteLeParole.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFlagTutteLeParole(String value) {
                this.flagTutteLeParole = value;
            }

            /**
             * Gets the value of the limitaRiceraAdAttributo property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the limitaRiceraAdAttributo property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getLimitaRiceraAdAttributo().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link String }
             * 
             * 
             */
            public List<String> getLimitaRiceraAdAttributo() {
                if (limitaRiceraAdAttributo == null) {
                    limitaRiceraAdAttributo = new ArrayList<String>();
                }
                return this.limitaRiceraAdAttributo;
            }

        }

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
     *         &lt;element name="PerCampo" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>positiveInteger">
     *                 &lt;attribute name="VersoDecrescente" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" fixed="1" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
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
        "perCampo"
    })
    public static class Ordinamento {

        @XmlElement(name = "PerCampo", required = true)
        protected List<TrovaDocFolder.Ordinamento.PerCampo> perCampo;

        /**
         * Gets the value of the perCampo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the perCampo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPerCampo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TrovaDocFolder.Ordinamento.PerCampo }
         * 
         * 
         */
        public List<TrovaDocFolder.Ordinamento.PerCampo> getPerCampo() {
            if (perCampo == null) {
                perCampo = new ArrayList<TrovaDocFolder.Ordinamento.PerCampo>();
            }
            return this.perCampo;
        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>positiveInteger">
         *       &lt;attribute name="VersoDecrescente" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" fixed="1" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class PerCampo {

            @XmlValue
            @XmlSchemaType(name = "positiveInteger")
            protected BigInteger value;
            @XmlAttribute(name = "VersoDecrescente")
            @XmlSchemaType(name = "anySimpleType")
            protected String versoDecrescente;

            /**
             * Recupera il valore della proprietvalue.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getValue() {
                return value;
            }

            /**
             * Imposta il valore della proprietvalue.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setValue(BigInteger value) {
                this.value = value;
            }

            /**
             * Recupera il valore della proprietversoDecrescente.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVersoDecrescente() {
                if (versoDecrescente == null) {
                    return "1";
                } else {
                    return versoDecrescente;
                }
            }

            /**
             * Imposta il valore della proprietversoDecrescente.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVersoDecrescente(String value) {
                this.versoDecrescente = value;
            }

        }

    }

}
