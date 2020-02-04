/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 * 
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.dbm.xsd.importExportLocal;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for allegatoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="allegatoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documento">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="flgDichiarazione">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="S"/>
 *                         &lt;enumeration value="N"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="dichiarazione" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="testoPiede" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="campi">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="campo" maxOccurs="unbounded">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="contatore" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                                                 &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="tipoRiga" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="tipoCampo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="riga" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                                                 &lt;element name="posizione" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                                                 &lt;element name="controllo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="tipoControllo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="lunghezza" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                                                 &lt;element name="decimali" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                                                 &lt;element name="valore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="editabile">
 *                                                   &lt;simpleType>
 *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                                       &lt;enumeration value="S"/>
 *                                                       &lt;enumeration value="N"/>
 *                                                     &lt;/restriction>
 *                                                   &lt;/simpleType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="raggruppamentoCheck" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="campoCollegato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="valoreCampoCollegato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="marcatoreIncrociato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="precompilazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="nomeWebService" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="nomeXsd" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="campoKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="campoDati" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="campoXmlMod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="errMsg" type="{http://www.w3.org/2001/XMLSchema}string"/> 
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="valoriListBox" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="valoreListBox" maxOccurs="unbounded">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="valoreListBox" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="descrizioneListBox" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="documentoFisico" type="{}documentoType" minOccurs="0"/>
 *                   &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="titolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="copie" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="condizione" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="flgAutocertificazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flgObbligatorieta" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipologie" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numPagineMax" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="dimensioneMax" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="ordinamento" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="flgVerificaFirma" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "allegatoType", propOrder = {
    "documento",
    "copie",
    "condizione",
    "flgAutocertificazione",
    "flgObbligatorieta",
    "tipologie",
    "numPagineMax",
    "dimensioneMax",
    "ordinamento",
    "flgVerificaFirma"
})
public class AllegatoType {

    @XmlElement(required = true)
    protected AllegatoType.Documento documento;
    @XmlElement(required = true)
    protected BigInteger copie;
    protected AllegatoType.Condizione condizione;
    @XmlElement(required = true)
    protected String flgAutocertificazione;
    @XmlElement(required = true)
    protected String flgObbligatorieta;
    @XmlElementRef(name = "tipologie", type = JAXBElement.class)
    protected JAXBElement<String> tipologie;
    @XmlElementRef(name = "numPagineMax", type = JAXBElement.class)
    protected JAXBElement<BigInteger> numPagineMax;
    @XmlElementRef(name = "dimensioneMax", type = JAXBElement.class)
    protected JAXBElement<BigInteger> dimensioneMax;
    @XmlElement(required = true, defaultValue = "9999")
    protected BigInteger ordinamento;
    @XmlElement(required = true, defaultValue = "0")
    protected BigInteger flgVerificaFirma;

    /**
     * Gets the value of the documento property.
     * 
     * @return
     *     possible object is
     *     {@link AllegatoType.Documento }
     *     
     */
    public AllegatoType.Documento getDocumento() {
        return documento;
    }

    /**
     * Sets the value of the documento property.
     * 
     * @param value
     *     allowed object is
     *     {@link AllegatoType.Documento }
     *     
     */
    public void setDocumento(AllegatoType.Documento value) {
        this.documento = value;
    }

    /**
     * Gets the value of the copie property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCopie() {
        return copie;
    }

    /**
     * Sets the value of the copie property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCopie(BigInteger value) {
        this.copie = value;
    }

    /**
     * Gets the value of the condizione property.
     * 
     * @return
     *     possible object is
     *     {@link AllegatoType.Condizione }
     *     
     */
    public AllegatoType.Condizione getCondizione() {
        return condizione;
    }

    /**
     * Sets the value of the condizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link AllegatoType.Condizione }
     *     
     */
    public void setCondizione(AllegatoType.Condizione value) {
        this.condizione = value;
    }

    /**
     * Gets the value of the flgAutocertificazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgAutocertificazione() {
        return flgAutocertificazione;
    }

    /**
     * Sets the value of the flgAutocertificazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgAutocertificazione(String value) {
        this.flgAutocertificazione = value;
    }

    /**
     * Gets the value of the flgObbligatorieta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgObbligatorieta() {
        return flgObbligatorieta;
    }

    /**
     * Sets the value of the flgObbligatorieta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgObbligatorieta(String value) {
        this.flgObbligatorieta = value;
    }

    /**
     * Gets the value of the tipologie property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTipologie() {
        return tipologie;
    }

    /**
     * Sets the value of the tipologie property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTipologie(JAXBElement<String> value) {
        this.tipologie = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the numPagineMax property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public JAXBElement<BigInteger> getNumPagineMax() {
        return numPagineMax;
    }

    /**
     * Sets the value of the numPagineMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public void setNumPagineMax(JAXBElement<BigInteger> value) {
        this.numPagineMax = ((JAXBElement<BigInteger> ) value);
    }

    /**
     * Gets the value of the dimensioneMax property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public JAXBElement<BigInteger> getDimensioneMax() {
        return dimensioneMax;
    }

    /**
     * Sets the value of the dimensioneMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public void setDimensioneMax(JAXBElement<BigInteger> value) {
        this.dimensioneMax = ((JAXBElement<BigInteger> ) value);
    }

    /**
     * Gets the value of the ordinamento property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOrdinamento() {
        return ordinamento;
    }

    /**
     * Sets the value of the ordinamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOrdinamento(BigInteger value) {
        this.ordinamento = value;
    }

        /**
     * Gets the value of the FlgVerificaFirma property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getFlgVerificaFirma() {
        return flgVerificaFirma;
    }

    /**
     * Sets the value of the FlgVerificaFirma property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setFlgVerificaFirma(BigInteger value) {
        this.flgVerificaFirma = value;
    }

    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "codice",
        "descrizione"
    })
    public static class Condizione {

        @XmlElement(required = true)
        protected String codice;
        @XmlElement(required = true)
        protected String descrizione;

        /**
         * Gets the value of the codice property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodice() {
            return codice;
        }

        /**
         * Sets the value of the codice property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodice(String value) {
            this.codice = value;
        }

        /**
         * Gets the value of the descrizione property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescrizione() {
            return descrizione;
        }

        /**
         * Sets the value of the descrizione property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescrizione(String value) {
            this.descrizione = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="flgDichiarazione">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="S"/>
     *               &lt;enumeration value="N"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="dichiarazione" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="testoPiede" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="campi">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="campo" maxOccurs="unbounded">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="contatore" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                                       &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="tipoRiga" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="tipoCampo" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="riga" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                                       &lt;element name="posizione" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                                       &lt;element name="controllo" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="tipoControllo" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="lunghezza" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                                       &lt;element name="decimali" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                                       &lt;element name="valore" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="editabile">
     *                                         &lt;simpleType>
     *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                             &lt;enumeration value="S"/>
     *                                             &lt;enumeration value="N"/>
     *                                           &lt;/restriction>
     *                                         &lt;/simpleType>
     *                                       &lt;/element>
     *                                       &lt;element name="raggruppamentoCheck" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="campoCollegato" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="valoreCampoCollegato" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="marcatoreIncrociato" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="precompilazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="nomeWebService" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="nomeXsd" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="campoKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="campoDati" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="campoXmlMod" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="errMsg" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="valoriListBox" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="valoreListBox" maxOccurs="unbounded">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="valoreListBox" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="descrizioneListBox" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
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
     *         &lt;element name="documentoFisico" type="{}documentoType" minOccurs="0"/>
     *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="titolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "codice",
        "flgDichiarazione",
        "dichiarazione",
        "documentoFisico",
        "descrizione",
        "titolo"
    })
    public static class Documento {

        @XmlElement(required = true)
        protected String codice;
        @XmlElement(required = true)
        protected String flgDichiarazione;
        protected AllegatoType.Documento.Dichiarazione dichiarazione;
        protected DocumentoType documentoFisico;
        protected String descrizione;
        @XmlElement(required = true)
        protected String titolo;

        /**
         * Gets the value of the codice property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodice() {
            return codice;
        }

        /**
         * Sets the value of the codice property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodice(String value) {
            this.codice = value;
        }

        /**
         * Gets the value of the flgDichiarazione property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFlgDichiarazione() {
            return flgDichiarazione;
        }

        /**
         * Sets the value of the flgDichiarazione property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFlgDichiarazione(String value) {
            this.flgDichiarazione = value;
        }

        /**
         * Gets the value of the dichiarazione property.
         * 
         * @return
         *     possible object is
         *     {@link AllegatoType.Documento.Dichiarazione }
         *     
         */
        public AllegatoType.Documento.Dichiarazione getDichiarazione() {
            return dichiarazione;
        }

        /**
         * Sets the value of the dichiarazione property.
         * 
         * @param value
         *     allowed object is
         *     {@link AllegatoType.Documento.Dichiarazione }
         *     
         */
        public void setDichiarazione(AllegatoType.Documento.Dichiarazione value) {
            this.dichiarazione = value;
        }

        /**
         * Gets the value of the documentoFisico property.
         * 
         * @return
         *     possible object is
         *     {@link DocumentoType }
         *     
         */
        public DocumentoType getDocumentoFisico() {
            return documentoFisico;
        }

        /**
         * Sets the value of the documentoFisico property.
         * 
         * @param value
         *     allowed object is
         *     {@link DocumentoType }
         *     
         */
        public void setDocumentoFisico(DocumentoType value) {
            this.documentoFisico = value;
        }

        /**
         * Gets the value of the descrizione property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescrizione() {
            return descrizione;
        }

        /**
         * Sets the value of the descrizione property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescrizione(String value) {
            this.descrizione = value;
        }

        /**
         * Gets the value of the titolo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTitolo() {
            return titolo;
        }

        /**
         * Sets the value of the titolo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTitolo(String value) {
            this.titolo = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="testoPiede" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="campi">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="campo" maxOccurs="unbounded">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="contatore" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *                             &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="tipoRiga" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="tipoCampo" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="riga" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *                             &lt;element name="posizione" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *                             &lt;element name="controllo" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="tipoControllo" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="lunghezza" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *                             &lt;element name="decimali" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *                             &lt;element name="valore" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="editabile">
         *                               &lt;simpleType>
         *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                                   &lt;enumeration value="S"/>
         *                                   &lt;enumeration value="N"/>
         *                                 &lt;/restriction>
         *                               &lt;/simpleType>
         *                             &lt;/element>
         *                             &lt;element name="raggruppamentoCheck" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="campoCollegato" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="valoreCampoCollegato" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="marcatoreIncrociato" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="precompilazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="nomeWebService" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="nomeXsd" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="campoKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="campoDati" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="campoXmlMod" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>  
         *                             &lt;element name="errMsg" type="{http://www.w3.org/2001/XMLSchema}string"/>* 
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
         *         &lt;element name="valoriListBox" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="valoreListBox" maxOccurs="unbounded">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="valoreListBox" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="descrizioneListBox" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
            "codice",
            "descrizione",
            "testoPiede",
            "campi",
            "valoriListBox"
        })
        public static class Dichiarazione {

            @XmlElement(required = true)
            protected String codice;
            @XmlElement(required = true)
            protected String descrizione;
            protected String testoPiede;
            @XmlElement(required = true)
            protected AllegatoType.Documento.Dichiarazione.Campi campi;
            protected AllegatoType.Documento.Dichiarazione.ValoriListBox valoriListBox;

            /**
             * Gets the value of the codice property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodice() {
                return codice;
            }

            /**
             * Sets the value of the codice property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodice(String value) {
                this.codice = value;
            }

            /**
             * Gets the value of the descrizione property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescrizione() {
                return descrizione;
            }

            /**
             * Sets the value of the descrizione property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescrizione(String value) {
                this.descrizione = value;
            }

            /**
             * Gets the value of the testoPiede property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTestoPiede() {
                return testoPiede;
            }

            /**
             * Sets the value of the testoPiede property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTestoPiede(String value) {
                this.testoPiede = value;
            }

            /**
             * Gets the value of the campi property.
             * 
             * @return
             *     possible object is
             *     {@link AllegatoType.Documento.Dichiarazione.Campi }
             *     
             */
            public AllegatoType.Documento.Dichiarazione.Campi getCampi() {
                return campi;
            }

            /**
             * Sets the value of the campi property.
             * 
             * @param value
             *     allowed object is
             *     {@link AllegatoType.Documento.Dichiarazione.Campi }
             *     
             */
            public void setCampi(AllegatoType.Documento.Dichiarazione.Campi value) {
                this.campi = value;
            }

            /**
             * Gets the value of the valoriListBox property.
             * 
             * @return
             *     possible object is
             *     {@link AllegatoType.Documento.Dichiarazione.ValoriListBox }
             *     
             */
            public AllegatoType.Documento.Dichiarazione.ValoriListBox getValoriListBox() {
                return valoriListBox;
            }

            /**
             * Sets the value of the valoriListBox property.
             * 
             * @param value
             *     allowed object is
             *     {@link AllegatoType.Documento.Dichiarazione.ValoriListBox }
             *     
             */
            public void setValoriListBox(AllegatoType.Documento.Dichiarazione.ValoriListBox value) {
                this.valoriListBox = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="campo" maxOccurs="unbounded">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="contatore" type="{http://www.w3.org/2001/XMLSchema}integer"/>
             *                   &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="tipoRiga" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="tipoCampo" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="riga" type="{http://www.w3.org/2001/XMLSchema}integer"/>
             *                   &lt;element name="posizione" type="{http://www.w3.org/2001/XMLSchema}integer"/>
             *                   &lt;element name="controllo" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="tipoControllo" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="lunghezza" type="{http://www.w3.org/2001/XMLSchema}integer"/>
             *                   &lt;element name="decimali" type="{http://www.w3.org/2001/XMLSchema}integer"/>
             *                   &lt;element name="valore" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="editabile">
             *                     &lt;simpleType>
             *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                         &lt;enumeration value="S"/>
             *                         &lt;enumeration value="N"/>
             *                       &lt;/restriction>
             *                     &lt;/simpleType>
             *                   &lt;/element>
             *                   &lt;element name="raggruppamentoCheck" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="campoCollegato" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="valoreCampoCollegato" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="marcatoreIncrociato" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="precompilazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="nomeWebService" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="nomeXsd" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="campoKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="campoDati" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="campoXmlMod" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="errMsg" type="{http://www.w3.org/2001/XMLSchema}string"/> 
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
                "campo"
            })
            public static class Campi {

                @XmlElement(required = true)
                protected List<AllegatoType.Documento.Dichiarazione.Campi.Campo> campo;

                /**
                 * Gets the value of the campo property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the campo property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getCampo().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link AllegatoType.Documento.Dichiarazione.Campi.Campo }
                 * 
                 * 
                 */
                public List<AllegatoType.Documento.Dichiarazione.Campi.Campo> getCampo() {
                    if (campo == null) {
                        campo = new ArrayList<AllegatoType.Documento.Dichiarazione.Campi.Campo>();
                    }
                    return this.campo;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="contatore" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="tipoRiga" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="tipoCampo" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="riga" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                 *         &lt;element name="posizione" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                 *         &lt;element name="controllo" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="tipoControllo" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="lunghezza" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                 *         &lt;element name="decimali" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                 *         &lt;element name="valore" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="editabile">
                 *           &lt;simpleType>
                 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *               &lt;enumeration value="S"/>
                 *               &lt;enumeration value="N"/>
                 *             &lt;/restriction>
                 *           &lt;/simpleType>
                 *         &lt;/element>
                 *         &lt;element name="raggruppamentoCheck" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="campoCollegato" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="valoreCampoCollegato" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="marcatoreIncrociato" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="precompilazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="nomeWebService" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="nomeXsd" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="campoKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="campoDati" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="campoXmlMod" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="errMsg" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
                    "contatore",
                    "nome",
                    "tipoRiga",
                    "tipoCampo",
                    "riga",
                    "posizione",
                    "controllo",
                    "tipoControllo",
                    "lunghezza",
                    "decimali",
                    "valore",
                    "editabile",
                    "raggruppamentoCheck",
                    "campoCollegato",
                    "valoreCampoCollegato",
                    "marcatoreIncrociato",
                    "precompilazione",
                    "nomeWebService",
                    "nomeXsd",
                    "campoKey",
                    "campoDati",
                    "campoXmlMod",
                    "descrizione",
                    "pattern",
                    "errMsg"
                })
                public static class Campo {

                    @XmlElement(required = true)
                    protected BigInteger contatore;
                    @XmlElement(required = true)
                    protected String nome;
                    @XmlElement(required = true)
                    protected String tipoRiga;
                    @XmlElement(required = true)
                    protected String tipoCampo;
                    @XmlElement(required = true)
                    protected BigInteger riga;
                    @XmlElement(required = true)
                    protected BigInteger posizione;
                    @XmlElement(required = true, nillable = true)
                    protected String controllo;
                    @XmlElement(required = true)
                    protected String tipoControllo;
                    @XmlElement(required = true)
                    protected BigInteger lunghezza;
                    @XmlElement(required = true)
                    protected BigInteger decimali;
                    @XmlElement(required = true)
                    protected String valore;
                    @XmlElement(required = true)
                    protected String editabile;
                    @XmlElement(required = true, nillable = true)
                    protected String raggruppamentoCheck;
                    @XmlElement(required = true, nillable = true)
                    protected String campoCollegato;
                    @XmlElement(required = true, nillable = true)
                    protected String valoreCampoCollegato;
                    @XmlElement(required = true)
                    protected String marcatoreIncrociato;
                    @XmlElement(required = true)
                    protected String precompilazione;
                    @XmlElement(required = true, nillable = true)
                    protected String nomeWebService;
                    @XmlElement(required = true, nillable = true)
                    protected String nomeXsd;
                    @XmlElement(required = true, nillable = true)
                    protected String campoKey;
                    @XmlElement(required = true, nillable = true)
                    protected String campoDati;
                    @XmlElement(required = true, nillable = true)
                    protected String campoXmlMod;
                    @XmlElement(required = true)
                    protected String descrizione;
                    @XmlElement(required = true, nillable = true)
                    protected String pattern;  
                    @XmlElement(required = true)
                    protected String errMsg;

                    /**
                     * Gets the value of the contatore property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigInteger }
                     *     
                     */
                    public BigInteger getContatore() {
                        return contatore;
                    }

                    /**
                     * Sets the value of the contatore property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigInteger }
                     *     
                     */
                    public void setContatore(BigInteger value) {
                        this.contatore = value;
                    }

                    /**
                     * Gets the value of the nome property.
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
                     * Sets the value of the nome property.
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
                     * Gets the value of the tipoRiga property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getTipoRiga() {
                        return tipoRiga;
                    }

                    /**
                     * Sets the value of the tipoRiga property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setTipoRiga(String value) {
                        this.tipoRiga = value;
                    }

                    /**
                     * Gets the value of the tipoCampo property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getTipoCampo() {
                        return tipoCampo;
                    }

                    /**
                     * Sets the value of the tipoCampo property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setTipoCampo(String value) {
                        this.tipoCampo = value;
                    }

                    /**
                     * Gets the value of the riga property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigInteger }
                     *     
                     */
                    public BigInteger getRiga() {
                        return riga;
                    }

                    /**
                     * Sets the value of the riga property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigInteger }
                     *     
                     */
                    public void setRiga(BigInteger value) {
                        this.riga = value;
                    }

                    /**
                     * Gets the value of the posizione property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigInteger }
                     *     
                     */
                    public BigInteger getPosizione() {
                        return posizione;
                    }

                    /**
                     * Sets the value of the posizione property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigInteger }
                     *     
                     */
                    public void setPosizione(BigInteger value) {
                        this.posizione = value;
                    }

                    /**
                     * Gets the value of the controllo property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getControllo() {
                        return controllo;
                    }

                    /**
                     * Sets the value of the controllo property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setControllo(String value) {
                        this.controllo = value;
                    }

                    /**
                     * Gets the value of the tipoControllo property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getTipoControllo() {
                        return tipoControllo;
                    }

                    /**
                     * Sets the value of the tipoControllo property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setTipoControllo(String value) {
                        this.tipoControllo = value;
                    }

                    /**
                     * Gets the value of the lunghezza property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigInteger }
                     *     
                     */
                    public BigInteger getLunghezza() {
                        return lunghezza;
                    }

                    /**
                     * Sets the value of the lunghezza property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigInteger }
                     *     
                     */
                    public void setLunghezza(BigInteger value) {
                        this.lunghezza = value;
                    }

                    /**
                     * Gets the value of the decimali property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigInteger }
                     *     
                     */
                    public BigInteger getDecimali() {
                        return decimali;
                    }

                    /**
                     * Sets the value of the decimali property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigInteger }
                     *     
                     */
                    public void setDecimali(BigInteger value) {
                        this.decimali = value;
                    }

                    /**
                     * Gets the value of the valore property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getValore() {
                        return valore;
                    }

                    /**
                     * Sets the value of the valore property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setValore(String value) {
                        this.valore = value;
                    }

                    /**
                     * Gets the value of the editabile property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getEditabile() {
                        return editabile;
                    }

                    /**
                     * Sets the value of the editabile property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setEditabile(String value) {
                        this.editabile = value;
                    }

                    /**
                     * Gets the value of the raggruppamentoCheck property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getRaggruppamentoCheck() {
                        return raggruppamentoCheck;
                    }

                    /**
                     * Sets the value of the raggruppamentoCheck property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setRaggruppamentoCheck(String value) {
                        this.raggruppamentoCheck = value;
                    }

                    /**
                     * Gets the value of the campoCollegato property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCampoCollegato() {
                        return campoCollegato;
                    }

                    /**
                     * Sets the value of the campoCollegato property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCampoCollegato(String value) {
                        this.campoCollegato = value;
                    }

                    /**
                     * Gets the value of the valoreCampoCollegato property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getValoreCampoCollegato() {
                        return valoreCampoCollegato;
                    }

                    /**
                     * Sets the value of the valoreCampoCollegato property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setValoreCampoCollegato(String value) {
                        this.valoreCampoCollegato = value;
                    }

                    /**
                     * Gets the value of the marcatoreIncrociato property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getMarcatoreIncrociato() {
                        return marcatoreIncrociato;
                    }

                    /**
                     * Sets the value of the marcatoreIncrociato property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setMarcatoreIncrociato(String value) {
                        this.marcatoreIncrociato = value;
                    }

                    /**
                     * Gets the value of the precompilazione property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPrecompilazione() {
                        return precompilazione;
                    }

                    /**
                     * Sets the value of the precompilazione property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPrecompilazione(String value) {
                        this.precompilazione = value;
                    }

                    /**
                     * Gets the value of the nomeWebService property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getNomeWebService() {
                        return nomeWebService;
                    }

                    /**
                     * Sets the value of the nomeWebService property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setNomeWebService(String value) {
                        this.nomeWebService = value;
                    }

                    /**
                     * Gets the value of the nomeXsd property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getNomeXsd() {
                        return nomeXsd;
                    }

                    /**
                     * Sets the value of the nomeXsd property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setNomeXsd(String value) {
                        this.nomeXsd = value;
                    }

                    /**
                     * Gets the value of the campoKey property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCampoKey() {
                        return campoKey;
                    }

                    /**
                     * Sets the value of the campoKey property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCampoKey(String value) {
                        this.campoKey = value;
                    }

                    /**
                     * Gets the value of the campoDati property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCampoDati() {
                        return campoDati;
                    }

                    /**
                     * Sets the value of the campoDati property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCampoDati(String value) {
                        this.campoDati = value;
                    }

                    /**
                     * Gets the value of the campoXmlMod property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCampoXmlMod() {
                        return campoXmlMod;
                    }

                    /**
                     * Sets the value of the campoXmlMod property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCampoXmlMod(String value) {
                        this.campoXmlMod = value;
                    }

                    /**
                     * Gets the value of the descrizione property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDescrizione() {
                        return descrizione;
                    }

                    /**
                     * Sets the value of the descrizione property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDescrizione(String value) {
                        this.descrizione = value;
                    }

                    /**
                     * Gets the value of the pattern property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPattern() {
                        return pattern;
                    }

                    /**
                     * Sets the value of the pattern property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPattern(String value) {
                        this.pattern = value;
                    }
/**
                     * Gets the value of the errMsg property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getErrMsg() {
                        return errMsg;
                    }

                    /**
                     * Sets the value of the errMsg property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setErrMsg(String value) {
                        this.errMsg = value;
                    }                    
                    
                }

            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="valoreListBox" maxOccurs="unbounded">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="valoreListBox" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="descrizioneListBox" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
                "valoreListBox"
            })
            public static class ValoriListBox {

                @XmlElement(required = true)
                protected List<AllegatoType.Documento.Dichiarazione.ValoriListBox.ValoreListBox> valoreListBox;

                /**
                 * Gets the value of the valoreListBox property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the valoreListBox property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getValoreListBox().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link AllegatoType.Documento.Dichiarazione.ValoriListBox.ValoreListBox }
                 * 
                 * 
                 */
                public List<AllegatoType.Documento.Dichiarazione.ValoriListBox.ValoreListBox> getValoreListBox() {
                    if (valoreListBox == null) {
                        valoreListBox = new ArrayList<AllegatoType.Documento.Dichiarazione.ValoriListBox.ValoreListBox>();
                    }
                    return this.valoreListBox;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="valoreListBox" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="descrizioneListBox" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
                    "nome",
                    "valoreListBox",
                    "descrizioneListBox"
                })
                public static class ValoreListBox {

                    @XmlElement(required = true)
                    protected String nome;
                    @XmlElement(required = true)
                    protected String valoreListBox;
                    @XmlElement(required = true)
                    protected String descrizioneListBox;

                    /**
                     * Gets the value of the nome property.
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
                     * Sets the value of the nome property.
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
                     * Gets the value of the valoreListBox property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getValoreListBox() {
                        return valoreListBox;
                    }

                    /**
                     * Sets the value of the valoreListBox property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setValoreListBox(String value) {
                        this.valoreListBox = value;
                    }

                    /**
                     * Gets the value of the descrizioneListBox property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDescrizioneListBox() {
                        return descrizioneListBox;
                    }

                    /**
                     * Sets the value of the descrizioneListBox property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDescrizioneListBox(String value) {
                        this.descrizioneListBox = value;
                    }

                }

            }

        }

    }

}
