package it.wego.auriga.xml;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
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
 *         &lt;choice>
 *           &lt;element name="DatiFascDiTitolario" type="{}FascDiTitolarioType"/>
 *           &lt;element name="DatiFolderCustom">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="NomeFolder" type="{}NomeFolderType"/>
 *                     &lt;element name="Rappresenta" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
 *         &lt;element name="Apertura" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;choice minOccurs="0">
 *                     &lt;element name="Anno" type="{}AnnoType" minOccurs="0"/>
 *                     &lt;element name="DataOra" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                   &lt;/choice>
 *                   &lt;element name="Utente" type="{}UserType" minOccurs="0"/>
 *                   &lt;element name="UO" type="{}UOType" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Oggetto" type="{}OggettoFolderType" minOccurs="0"/>
 *         &lt;element name="TipoDiDettaglio" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
 *         &lt;element name="SenzaProcedimentoAssociato" type="{}FlagSiNoType" minOccurs="0"/>
 *         &lt;element name="ParoleChiave" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="1000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Libreria" type="{}LibreriaType" minOccurs="0"/>
 *         &lt;element name="FolderAppartenenza" type="{}EstremiXIdentificazioneFolderNoLibType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="InCaricoA" type="{}AssegnatarioEffType" minOccurs="0"/>
 *         &lt;element name="AltroSoggettoInterno" type="{}SoggettoInternoEstesoType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Chiusura" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="DataOra" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                   &lt;element name="Utente" type="{}UserType" minOccurs="0"/>
 *                   &lt;element name="UO" type="{}UOType" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Conservazione" type="{}InfoConservazioneType" minOccurs="0"/>
 *         &lt;element name="FlagArchivioAppartenenza" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="C|D|S"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="VersamentoInDeposito" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Data" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="EffettuatoDa" type="{}UserType" minOccurs="0"/>
 *                   &lt;element name="AutorizzatoDa" type="{}UserType" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;choice>
 *           &lt;element name="VersamentoInStorico" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="Data" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                     &lt;element name="EffettuatoDa" type="{}UserType" minOccurs="0"/>
 *                     &lt;element name="AutorizzatoDa" type="{}UserType" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="Scarto" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="Data" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                     &lt;element name="EffettuatoDa" type="{}UserType" minOccurs="0"/>
 *                     &lt;element name="AutorizzatoDa" type="{}UserType" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
 *         &lt;element name="EliminazioneCartaceo" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Data" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="EffettuatoDa" type="{}UserType" minOccurs="0"/>
 *                   &lt;element name="AutorizzatoDa" type="{}UserType" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="SoggEsterno" type="{}SoggettoEstEstesoType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="LivelloRiservatezzaInterno" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *         &lt;element name="PermessoVsSoggInterno" type="{}ACLRecordType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="FlagEreditaPermessi" type="{}FlagSiNoType" minOccurs="0"/>
 *         &lt;element name="EreditaPermessiDa" type="{}EstremiXIdentificazioneFolderNoLibType" minOccurs="0"/>
 *         &lt;element name="FlagInibitaPropagazionePermessi" type="{}FlagSiNoType" minOccurs="0"/>
 *         &lt;element name="LivelloEvidenza" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *         &lt;element name="Stato" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
 *         &lt;element name="StatoDettaglio" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttributoAdd" type="{}AttributoAddizionaleType" maxOccurs="unbounded" minOccurs="0"/>
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
    "datiFascDiTitolario",
    "datiFolderCustom",
    "apertura",
    "oggetto",
    "tipoDiDettaglio",
    "senzaProcedimentoAssociato",
    "paroleChiave",
    "libreria",
    "folderAppartenenza",
    "inCaricoA",
    "altroSoggettoInterno",
    "chiusura",
    "conservazione",
    "flagArchivioAppartenenza",
    "versamentoInDeposito",
    "versamentoInStorico",
    "scarto",
    "eliminazioneCartaceo",
    "soggEsterno",
    "livelloRiservatezzaInterno",
    "permessoVsSoggInterno",
    "flagEreditaPermessi",
    "ereditaPermessiDa",
    "flagInibitaPropagazionePermessi",
    "livelloEvidenza",
    "stato",
    "statoDettaglio",
    "note",
    "attributoAdd"
})
@XmlRootElement(name = "NewFolder")
public class NewFolder {

    @XmlElement(name = "DatiFascDiTitolario")
    protected FascDiTitolarioType datiFascDiTitolario;
    @XmlElement(name = "DatiFolderCustom")
    protected NewFolder.DatiFolderCustom datiFolderCustom;
    @XmlElement(name = "Apertura")
    protected NewFolder.Apertura apertura;
    @XmlElement(name = "Oggetto")
    protected String oggetto;
    @XmlElement(name = "TipoDiDettaglio")
    protected OggDiTabDiSistemaType tipoDiDettaglio;
    @XmlElement(name = "SenzaProcedimentoAssociato")
    protected String senzaProcedimentoAssociato;
    @XmlElement(name = "ParoleChiave")
    protected String paroleChiave;
    @XmlElement(name = "Libreria")
    protected String libreria;
    @XmlElement(name = "FolderAppartenenza")
    protected List<EstremiXIdentificazioneFolderNoLibType> folderAppartenenza;
    @XmlElement(name = "InCaricoA")
    protected AssegnatarioEffType inCaricoA;
    @XmlElement(name = "AltroSoggettoInterno")
    protected List<SoggettoInternoEstesoType> altroSoggettoInterno;
    @XmlElement(name = "Chiusura")
    protected NewFolder.Chiusura chiusura;
    @XmlElement(name = "Conservazione")
    protected InfoConservazioneType conservazione;
    @XmlElement(name = "FlagArchivioAppartenenza")
    protected String flagArchivioAppartenenza;
    @XmlElement(name = "VersamentoInDeposito")
    protected NewFolder.VersamentoInDeposito versamentoInDeposito;
    @XmlElement(name = "VersamentoInStorico")
    protected NewFolder.VersamentoInStorico versamentoInStorico;
    @XmlElement(name = "Scarto")
    protected NewFolder.Scarto scarto;
    @XmlElement(name = "EliminazioneCartaceo")
    protected NewFolder.EliminazioneCartaceo eliminazioneCartaceo;
    @XmlElement(name = "SoggEsterno")
    protected List<SoggettoEstEstesoType> soggEsterno;
    @XmlElement(name = "LivelloRiservatezzaInterno")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger livelloRiservatezzaInterno;
    @XmlElement(name = "PermessoVsSoggInterno")
    protected List<ACLRecordType> permessoVsSoggInterno;
    @XmlElement(name = "FlagEreditaPermessi")
    protected String flagEreditaPermessi;
    @XmlElement(name = "EreditaPermessiDa")
    protected EstremiXIdentificazioneFolderNoLibType ereditaPermessiDa;
    @XmlElement(name = "FlagInibitaPropagazionePermessi")
    protected String flagInibitaPropagazionePermessi;
    @XmlElement(name = "LivelloEvidenza")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger livelloEvidenza;
    @XmlElement(name = "Stato")
    protected OggDiTabDiSistemaType stato;
    @XmlElement(name = "StatoDettaglio")
    protected OggDiTabDiSistemaType statoDettaglio;
    @XmlElement(name = "Note")
    protected String note;
    @XmlElement(name = "AttributoAdd")
    protected List<AttributoAddizionaleType> attributoAdd;

    /**
     * Recupera il valore della propriet� datiFascDiTitolario.
     * 
     * @return
     *     possible object is
     *     {@link FascDiTitolarioType }
     *     
     */
    public FascDiTitolarioType getDatiFascDiTitolario() {
        return datiFascDiTitolario;
    }

    /**
     * Imposta il valore della propriet� datiFascDiTitolario.
     * 
     * @param value
     *     allowed object is
     *     {@link FascDiTitolarioType }
     *     
     */
    public void setDatiFascDiTitolario(FascDiTitolarioType value) {
        this.datiFascDiTitolario = value;
    }

    /**
     * Recupera il valore della propriet� datiFolderCustom.
     * 
     * @return
     *     possible object is
     *     {@link NewFolder.DatiFolderCustom }
     *     
     */
    public NewFolder.DatiFolderCustom getDatiFolderCustom() {
        return datiFolderCustom;
    }

    /**
     * Imposta il valore della propriet� datiFolderCustom.
     * 
     * @param value
     *     allowed object is
     *     {@link NewFolder.DatiFolderCustom }
     *     
     */
    public void setDatiFolderCustom(NewFolder.DatiFolderCustom value) {
        this.datiFolderCustom = value;
    }

    /**
     * Recupera il valore della propriet� apertura.
     * 
     * @return
     *     possible object is
     *     {@link NewFolder.Apertura }
     *     
     */
    public NewFolder.Apertura getApertura() {
        return apertura;
    }

    /**
     * Imposta il valore della propriet� apertura.
     * 
     * @param value
     *     allowed object is
     *     {@link NewFolder.Apertura }
     *     
     */
    public void setApertura(NewFolder.Apertura value) {
        this.apertura = value;
    }

    /**
     * Recupera il valore della propriet� oggetto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggetto() {
        return oggetto;
    }

    /**
     * Imposta il valore della propriet� oggetto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggetto(String value) {
        this.oggetto = value;
    }

    /**
     * Recupera il valore della propriet� tipoDiDettaglio.
     * 
     * @return
     *     possible object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public OggDiTabDiSistemaType getTipoDiDettaglio() {
        return tipoDiDettaglio;
    }

    /**
     * Imposta il valore della propriet� tipoDiDettaglio.
     * 
     * @param value
     *     allowed object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public void setTipoDiDettaglio(OggDiTabDiSistemaType value) {
        this.tipoDiDettaglio = value;
    }

    /**
     * Recupera il valore della propriet� senzaProcedimentoAssociato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenzaProcedimentoAssociato() {
        return senzaProcedimentoAssociato;
    }

    /**
     * Imposta il valore della propriet� senzaProcedimentoAssociato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenzaProcedimentoAssociato(String value) {
        this.senzaProcedimentoAssociato = value;
    }

    /**
     * Recupera il valore della propriet� paroleChiave.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParoleChiave() {
        return paroleChiave;
    }

    /**
     * Imposta il valore della propriet� paroleChiave.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParoleChiave(String value) {
        this.paroleChiave = value;
    }

    /**
     * Recupera il valore della propriet� libreria.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLibreria() {
        return libreria;
    }

    /**
     * Imposta il valore della propriet� libreria.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLibreria(String value) {
        this.libreria = value;
    }

    /**
     * Gets the value of the folderAppartenenza property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the folderAppartenenza property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFolderAppartenenza().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EstremiXIdentificazioneFolderNoLibType }
     * 
     * 
     */
    public List<EstremiXIdentificazioneFolderNoLibType> getFolderAppartenenza() {
        if (folderAppartenenza == null) {
            folderAppartenenza = new ArrayList<EstremiXIdentificazioneFolderNoLibType>();
        }
        return this.folderAppartenenza;
    }

    /**
     * Recupera il valore della propriet� inCaricoA.
     * 
     * @return
     *     possible object is
     *     {@link AssegnatarioEffType }
     *     
     */
    public AssegnatarioEffType getInCaricoA() {
        return inCaricoA;
    }

    /**
     * Imposta il valore della propriet� inCaricoA.
     * 
     * @param value
     *     allowed object is
     *     {@link AssegnatarioEffType }
     *     
     */
    public void setInCaricoA(AssegnatarioEffType value) {
        this.inCaricoA = value;
    }

    /**
     * Gets the value of the altroSoggettoInterno property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the altroSoggettoInterno property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAltroSoggettoInterno().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SoggettoInternoEstesoType }
     * 
     * 
     */
    public List<SoggettoInternoEstesoType> getAltroSoggettoInterno() {
        if (altroSoggettoInterno == null) {
            altroSoggettoInterno = new ArrayList<SoggettoInternoEstesoType>();
        }
        return this.altroSoggettoInterno;
    }

    /**
     * Recupera il valore della propriet� chiusura.
     * 
     * @return
     *     possible object is
     *     {@link NewFolder.Chiusura }
     *     
     */
    public NewFolder.Chiusura getChiusura() {
        return chiusura;
    }

    /**
     * Imposta il valore della propriet� chiusura.
     * 
     * @param value
     *     allowed object is
     *     {@link NewFolder.Chiusura }
     *     
     */
    public void setChiusura(NewFolder.Chiusura value) {
        this.chiusura = value;
    }

    /**
     * Recupera il valore della propriet� conservazione.
     * 
     * @return
     *     possible object is
     *     {@link InfoConservazioneType }
     *     
     */
    public InfoConservazioneType getConservazione() {
        return conservazione;
    }

    /**
     * Imposta il valore della propriet� conservazione.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoConservazioneType }
     *     
     */
    public void setConservazione(InfoConservazioneType value) {
        this.conservazione = value;
    }

    /**
     * Recupera il valore della propriet� flagArchivioAppartenenza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagArchivioAppartenenza() {
        return flagArchivioAppartenenza;
    }

    /**
     * Imposta il valore della propriet� flagArchivioAppartenenza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagArchivioAppartenenza(String value) {
        this.flagArchivioAppartenenza = value;
    }

    /**
     * Recupera il valore della propriet� versamentoInDeposito.
     * 
     * @return
     *     possible object is
     *     {@link NewFolder.VersamentoInDeposito }
     *     
     */
    public NewFolder.VersamentoInDeposito getVersamentoInDeposito() {
        return versamentoInDeposito;
    }

    /**
     * Imposta il valore della propriet� versamentoInDeposito.
     * 
     * @param value
     *     allowed object is
     *     {@link NewFolder.VersamentoInDeposito }
     *     
     */
    public void setVersamentoInDeposito(NewFolder.VersamentoInDeposito value) {
        this.versamentoInDeposito = value;
    }

    /**
     * Recupera il valore della propriet� versamentoInStorico.
     * 
     * @return
     *     possible object is
     *     {@link NewFolder.VersamentoInStorico }
     *     
     */
    public NewFolder.VersamentoInStorico getVersamentoInStorico() {
        return versamentoInStorico;
    }

    /**
     * Imposta il valore della propriet� versamentoInStorico.
     * 
     * @param value
     *     allowed object is
     *     {@link NewFolder.VersamentoInStorico }
     *     
     */
    public void setVersamentoInStorico(NewFolder.VersamentoInStorico value) {
        this.versamentoInStorico = value;
    }

    /**
     * Recupera il valore della propriet� scarto.
     * 
     * @return
     *     possible object is
     *     {@link NewFolder.Scarto }
     *     
     */
    public NewFolder.Scarto getScarto() {
        return scarto;
    }

    /**
     * Imposta il valore della propriet� scarto.
     * 
     * @param value
     *     allowed object is
     *     {@link NewFolder.Scarto }
     *     
     */
    public void setScarto(NewFolder.Scarto value) {
        this.scarto = value;
    }

    /**
     * Recupera il valore della propriet� eliminazioneCartaceo.
     * 
     * @return
     *     possible object is
     *     {@link NewFolder.EliminazioneCartaceo }
     *     
     */
    public NewFolder.EliminazioneCartaceo getEliminazioneCartaceo() {
        return eliminazioneCartaceo;
    }

    /**
     * Imposta il valore della propriet� eliminazioneCartaceo.
     * 
     * @param value
     *     allowed object is
     *     {@link NewFolder.EliminazioneCartaceo }
     *     
     */
    public void setEliminazioneCartaceo(NewFolder.EliminazioneCartaceo value) {
        this.eliminazioneCartaceo = value;
    }

    /**
     * Gets the value of the soggEsterno property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the soggEsterno property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSoggEsterno().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SoggettoEstEstesoType }
     * 
     * 
     */
    public List<SoggettoEstEstesoType> getSoggEsterno() {
        if (soggEsterno == null) {
            soggEsterno = new ArrayList<SoggettoEstEstesoType>();
        }
        return this.soggEsterno;
    }

    /**
     * Recupera il valore della propriet� livelloRiservatezzaInterno.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLivelloRiservatezzaInterno() {
        return livelloRiservatezzaInterno;
    }

    /**
     * Imposta il valore della propriet� livelloRiservatezzaInterno.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLivelloRiservatezzaInterno(BigInteger value) {
        this.livelloRiservatezzaInterno = value;
    }

    /**
     * Gets the value of the permessoVsSoggInterno property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the permessoVsSoggInterno property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPermessoVsSoggInterno().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ACLRecordType }
     * 
     * 
     */
    public List<ACLRecordType> getPermessoVsSoggInterno() {
        if (permessoVsSoggInterno == null) {
            permessoVsSoggInterno = new ArrayList<ACLRecordType>();
        }
        return this.permessoVsSoggInterno;
    }

    /**
     * Recupera il valore della propriet� flagEreditaPermessi.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagEreditaPermessi() {
        return flagEreditaPermessi;
    }

    /**
     * Imposta il valore della propriet� flagEreditaPermessi.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagEreditaPermessi(String value) {
        this.flagEreditaPermessi = value;
    }

    /**
     * Recupera il valore della propriet� ereditaPermessiDa.
     * 
     * @return
     *     possible object is
     *     {@link EstremiXIdentificazioneFolderNoLibType }
     *     
     */
    public EstremiXIdentificazioneFolderNoLibType getEreditaPermessiDa() {
        return ereditaPermessiDa;
    }

    /**
     * Imposta il valore della propriet� ereditaPermessiDa.
     * 
     * @param value
     *     allowed object is
     *     {@link EstremiXIdentificazioneFolderNoLibType }
     *     
     */
    public void setEreditaPermessiDa(EstremiXIdentificazioneFolderNoLibType value) {
        this.ereditaPermessiDa = value;
    }

    /**
     * Recupera il valore della propriet� flagInibitaPropagazionePermessi.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagInibitaPropagazionePermessi() {
        return flagInibitaPropagazionePermessi;
    }

    /**
     * Imposta il valore della propriet� flagInibitaPropagazionePermessi.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagInibitaPropagazionePermessi(String value) {
        this.flagInibitaPropagazionePermessi = value;
    }

    /**
     * Recupera il valore della propriet� livelloEvidenza.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLivelloEvidenza() {
        return livelloEvidenza;
    }

    /**
     * Imposta il valore della propriet� livelloEvidenza.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLivelloEvidenza(BigInteger value) {
        this.livelloEvidenza = value;
    }

    /**
     * Recupera il valore della propriet� stato.
     * 
     * @return
     *     possible object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public OggDiTabDiSistemaType getStato() {
        return stato;
    }

    /**
     * Imposta il valore della propriet� stato.
     * 
     * @param value
     *     allowed object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public void setStato(OggDiTabDiSistemaType value) {
        this.stato = value;
    }

    /**
     * Recupera il valore della propriet� statoDettaglio.
     * 
     * @return
     *     possible object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public OggDiTabDiSistemaType getStatoDettaglio() {
        return statoDettaglio;
    }

    /**
     * Imposta il valore della propriet� statoDettaglio.
     * 
     * @param value
     *     allowed object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public void setStatoDettaglio(OggDiTabDiSistemaType value) {
        this.statoDettaglio = value;
    }

    /**
     * Recupera il valore della propriet� note.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Imposta il valore della propriet� note.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
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
     * {@link AttributoAddizionaleType }
     * 
     * 
     */
    public List<AttributoAddizionaleType> getAttributoAdd() {
        if (attributoAdd == null) {
            attributoAdd = new ArrayList<AttributoAddizionaleType>();
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
     *         &lt;choice minOccurs="0">
     *           &lt;element name="Anno" type="{}AnnoType" minOccurs="0"/>
     *           &lt;element name="DataOra" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
     *         &lt;/choice>
     *         &lt;element name="Utente" type="{}UserType" minOccurs="0"/>
     *         &lt;element name="UO" type="{}UOType" minOccurs="0"/>
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
        "anno",
        "dataOra",
        "utente",
        "uo"
    })
    public static class Apertura {

        @XmlElement(name = "Anno")
        @XmlSchemaType(name = "integer")
        protected Integer anno;
        @XmlElement(name = "DataOra")
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar dataOra;
        @XmlElement(name = "Utente")
        protected UserType utente;
        @XmlElement(name = "UO")
        protected UOType uo;

        /**
         * Recupera il valore della propriet� anno.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getAnno() {
            return anno;
        }

        /**
         * Imposta il valore della propriet� anno.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setAnno(Integer value) {
            this.anno = value;
        }

        /**
         * Recupera il valore della propriet� dataOra.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDataOra() {
            return dataOra;
        }

        /**
         * Imposta il valore della propriet� dataOra.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDataOra(XMLGregorianCalendar value) {
            this.dataOra = value;
        }

        /**
         * Recupera il valore della propriet� utente.
         * 
         * @return
         *     possible object is
         *     {@link UserType }
         *     
         */
        public UserType getUtente() {
            return utente;
        }

        /**
         * Imposta il valore della propriet� utente.
         * 
         * @param value
         *     allowed object is
         *     {@link UserType }
         *     
         */
        public void setUtente(UserType value) {
            this.utente = value;
        }

        /**
         * Recupera il valore della propriet� uo.
         * 
         * @return
         *     possible object is
         *     {@link UOType }
         *     
         */
        public UOType getUO() {
            return uo;
        }

        /**
         * Imposta il valore della propriet� uo.
         * 
         * @param value
         *     allowed object is
         *     {@link UOType }
         *     
         */
        public void setUO(UOType value) {
            this.uo = value;
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
     *         &lt;element name="DataOra" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
     *         &lt;element name="Utente" type="{}UserType" minOccurs="0"/>
     *         &lt;element name="UO" type="{}UOType" minOccurs="0"/>
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
        "dataOra",
        "utente",
        "uo"
    })
    public static class Chiusura {

        @XmlElement(name = "DataOra")
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar dataOra;
        @XmlElement(name = "Utente")
        protected UserType utente;
        @XmlElement(name = "UO")
        protected UOType uo;

        /**
         * Recupera il valore della propriet� dataOra.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDataOra() {
            return dataOra;
        }

        /**
         * Imposta il valore della propriet� dataOra.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDataOra(XMLGregorianCalendar value) {
            this.dataOra = value;
        }

        /**
         * Recupera il valore della propriet� utente.
         * 
         * @return
         *     possible object is
         *     {@link UserType }
         *     
         */
        public UserType getUtente() {
            return utente;
        }

        /**
         * Imposta il valore della propriet� utente.
         * 
         * @param value
         *     allowed object is
         *     {@link UserType }
         *     
         */
        public void setUtente(UserType value) {
            this.utente = value;
        }

        /**
         * Recupera il valore della propriet� uo.
         * 
         * @return
         *     possible object is
         *     {@link UOType }
         *     
         */
        public UOType getUO() {
            return uo;
        }

        /**
         * Imposta il valore della propriet� uo.
         * 
         * @param value
         *     allowed object is
         *     {@link UOType }
         *     
         */
        public void setUO(UOType value) {
            this.uo = value;
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
     *         &lt;element name="NomeFolder" type="{}NomeFolderType"/>
     *         &lt;element name="Rappresenta" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
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
        "nomeFolder",
        "rappresenta"
    })
    public static class DatiFolderCustom {

        @XmlElement(name = "NomeFolder", required = true)
        protected String nomeFolder;
        @XmlElement(name = "Rappresenta")
        protected OggDiTabDiSistemaType rappresenta;

        /**
         * Recupera il valore della propriet� nomeFolder.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNomeFolder() {
            return nomeFolder;
        }

        /**
         * Imposta il valore della propriet� nomeFolder.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNomeFolder(String value) {
            this.nomeFolder = value;
        }

        /**
         * Recupera il valore della propriet� rappresenta.
         * 
         * @return
         *     possible object is
         *     {@link OggDiTabDiSistemaType }
         *     
         */
        public OggDiTabDiSistemaType getRappresenta() {
            return rappresenta;
        }

        /**
         * Imposta il valore della propriet� rappresenta.
         * 
         * @param value
         *     allowed object is
         *     {@link OggDiTabDiSistemaType }
         *     
         */
        public void setRappresenta(OggDiTabDiSistemaType value) {
            this.rappresenta = value;
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
     *         &lt;element name="Data" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="EffettuatoDa" type="{}UserType" minOccurs="0"/>
     *         &lt;element name="AutorizzatoDa" type="{}UserType" minOccurs="0"/>
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
        "data",
        "effettuatoDa",
        "autorizzatoDa"
    })
    public static class EliminazioneCartaceo {

        @XmlElement(name = "Data")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar data;
        @XmlElement(name = "EffettuatoDa")
        protected UserType effettuatoDa;
        @XmlElement(name = "AutorizzatoDa")
        protected UserType autorizzatoDa;

        /**
         * Recupera il valore della propriet� data.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getData() {
            return data;
        }

        /**
         * Imposta il valore della propriet� data.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setData(XMLGregorianCalendar value) {
            this.data = value;
        }

        /**
         * Recupera il valore della propriet� effettuatoDa.
         * 
         * @return
         *     possible object is
         *     {@link UserType }
         *     
         */
        public UserType getEffettuatoDa() {
            return effettuatoDa;
        }

        /**
         * Imposta il valore della propriet� effettuatoDa.
         * 
         * @param value
         *     allowed object is
         *     {@link UserType }
         *     
         */
        public void setEffettuatoDa(UserType value) {
            this.effettuatoDa = value;
        }

        /**
         * Recupera il valore della propriet� autorizzatoDa.
         * 
         * @return
         *     possible object is
         *     {@link UserType }
         *     
         */
        public UserType getAutorizzatoDa() {
            return autorizzatoDa;
        }

        /**
         * Imposta il valore della propriet� autorizzatoDa.
         * 
         * @param value
         *     allowed object is
         *     {@link UserType }
         *     
         */
        public void setAutorizzatoDa(UserType value) {
            this.autorizzatoDa = value;
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
     *         &lt;element name="Data" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="EffettuatoDa" type="{}UserType" minOccurs="0"/>
     *         &lt;element name="AutorizzatoDa" type="{}UserType" minOccurs="0"/>
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
        "data",
        "effettuatoDa",
        "autorizzatoDa"
    })
    public static class Scarto {

        @XmlElement(name = "Data")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar data;
        @XmlElement(name = "EffettuatoDa")
        protected UserType effettuatoDa;
        @XmlElement(name = "AutorizzatoDa")
        protected UserType autorizzatoDa;

        /**
         * Recupera il valore della propriet� data.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getData() {
            return data;
        }

        /**
         * Imposta il valore della propriet� data.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setData(XMLGregorianCalendar value) {
            this.data = value;
        }

        /**
         * Recupera il valore della propriet� effettuatoDa.
         * 
         * @return
         *     possible object is
         *     {@link UserType }
         *     
         */
        public UserType getEffettuatoDa() {
            return effettuatoDa;
        }

        /**
         * Imposta il valore della propriet� effettuatoDa.
         * 
         * @param value
         *     allowed object is
         *     {@link UserType }
         *     
         */
        public void setEffettuatoDa(UserType value) {
            this.effettuatoDa = value;
        }

        /**
         * Recupera il valore della propriet� autorizzatoDa.
         * 
         * @return
         *     possible object is
         *     {@link UserType }
         *     
         */
        public UserType getAutorizzatoDa() {
            return autorizzatoDa;
        }

        /**
         * Imposta il valore della propriet� autorizzatoDa.
         * 
         * @param value
         *     allowed object is
         *     {@link UserType }
         *     
         */
        public void setAutorizzatoDa(UserType value) {
            this.autorizzatoDa = value;
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
     *         &lt;element name="Data" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="EffettuatoDa" type="{}UserType" minOccurs="0"/>
     *         &lt;element name="AutorizzatoDa" type="{}UserType" minOccurs="0"/>
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
        "data",
        "effettuatoDa",
        "autorizzatoDa"
    })
    public static class VersamentoInDeposito {

        @XmlElement(name = "Data")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar data;
        @XmlElement(name = "EffettuatoDa")
        protected UserType effettuatoDa;
        @XmlElement(name = "AutorizzatoDa")
        protected UserType autorizzatoDa;

        /**
         * Recupera il valore della propriet� data.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getData() {
            return data;
        }

        /**
         * Imposta il valore della propriet� data.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setData(XMLGregorianCalendar value) {
            this.data = value;
        }

        /**
         * Recupera il valore della propriet� effettuatoDa.
         * 
         * @return
         *     possible object is
         *     {@link UserType }
         *     
         */
        public UserType getEffettuatoDa() {
            return effettuatoDa;
        }

        /**
         * Imposta il valore della propriet� effettuatoDa.
         * 
         * @param value
         *     allowed object is
         *     {@link UserType }
         *     
         */
        public void setEffettuatoDa(UserType value) {
            this.effettuatoDa = value;
        }

        /**
         * Recupera il valore della propriet� autorizzatoDa.
         * 
         * @return
         *     possible object is
         *     {@link UserType }
         *     
         */
        public UserType getAutorizzatoDa() {
            return autorizzatoDa;
        }

        /**
         * Imposta il valore della propriet� autorizzatoDa.
         * 
         * @param value
         *     allowed object is
         *     {@link UserType }
         *     
         */
        public void setAutorizzatoDa(UserType value) {
            this.autorizzatoDa = value;
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
     *         &lt;element name="Data" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="EffettuatoDa" type="{}UserType" minOccurs="0"/>
     *         &lt;element name="AutorizzatoDa" type="{}UserType" minOccurs="0"/>
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
        "data",
        "effettuatoDa",
        "autorizzatoDa"
    })
    public static class VersamentoInStorico {

        @XmlElement(name = "Data")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar data;
        @XmlElement(name = "EffettuatoDa")
        protected UserType effettuatoDa;
        @XmlElement(name = "AutorizzatoDa")
        protected UserType autorizzatoDa;

        /**
         * Recupera il valore della propriet� data.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getData() {
            return data;
        }

        /**
         * Imposta il valore della propriet� data.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setData(XMLGregorianCalendar value) {
            this.data = value;
        }

        /**
         * Recupera il valore della propriet� effettuatoDa.
         * 
         * @return
         *     possible object is
         *     {@link UserType }
         *     
         */
        public UserType getEffettuatoDa() {
            return effettuatoDa;
        }

        /**
         * Imposta il valore della propriet� effettuatoDa.
         * 
         * @param value
         *     allowed object is
         *     {@link UserType }
         *     
         */
        public void setEffettuatoDa(UserType value) {
            this.effettuatoDa = value;
        }

        /**
         * Recupera il valore della propriet� autorizzatoDa.
         * 
         * @return
         *     possible object is
         *     {@link UserType }
         *     
         */
        public UserType getAutorizzatoDa() {
            return autorizzatoDa;
        }

        /**
         * Imposta il valore della propriet� autorizzatoDa.
         * 
         * @param value
         *     allowed object is
         *     {@link UserType }
         *     
         */
        public void setAutorizzatoDa(UserType value) {
            this.autorizzatoDa = value;
        }

    }

}
