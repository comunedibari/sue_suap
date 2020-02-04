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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="intervento">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                   &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="flgObbligatorio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="procedimento">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="cud" type="{}cudType"/>
 *                             &lt;element name="terminiEvasione" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                             &lt;element name="tipoProc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="flgBollo">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;enumeration value="S"/>
 *                                   &lt;enumeration value="N"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="allegati" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="allegato" type="{}allegatoType" maxOccurs="unbounded"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="normative" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="normativa" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="nomeRiferimento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="tipoNormativa" type="{}tipoNormativa"/>
 *                                       &lt;element name="documentoFisico" type="{}documentoType" minOccurs="0"/>
 *                                       &lt;element name="articoloRiferimento" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
 *                   &lt;element name="codiceEnteOrigine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="codiceOrigine" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="codiceComune" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "intervento",
    "codiceComune"
})
@XmlRootElement(name = "documentRoot")
public class DocumentRoot {

    @XmlElement(required = true)
    protected DocumentRoot.Intervento intervento;
    @XmlElement(required = true)
    protected String codiceComune;

    /**
     * Gets the value of the intervento property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentRoot.Intervento }
     *     
     */
    public DocumentRoot.Intervento getIntervento() {
        return intervento;
    }

    /**
     * Sets the value of the intervento property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentRoot.Intervento }
     *     
     */
    public void setIntervento(DocumentRoot.Intervento value) {
        this.intervento = value;
    }

    /**
     * Gets the value of the codiceComune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceComune() {
        return codiceComune;
    }

    /**
     * Sets the value of the codiceComune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceComune(String value) {
        this.codiceComune = value;
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
     *         &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="flgObbligatorio" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="notePubblicazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="procedimento">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="cud" type="{}cudType"/>
     *                   &lt;element name="terminiEvasione" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                   &lt;element name="tipoProc" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="flgBollo">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;enumeration value="S"/>
     *                         &lt;enumeration value="N"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="allegati" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="allegato" type="{}allegatoType" maxOccurs="unbounded"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="normative" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="normativa" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="nomeRiferimento" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="tipoNormativa" type="{}tipoNormativa"/>
     *                             &lt;element name="documentoFisico" type="{}documentoType" minOccurs="0"/>
     *                             &lt;element name="articoloRiferimento" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
     *         &lt;element name="codiceEnteOrigine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="codiceOrigine" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
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
        "flgObbligatorio",
        "notePubblicazione",
        "procedimento",
        "allegati",
        "normative",
        "codiceEnteOrigine",
        "codiceOrigine"
    })
    public static class Intervento {

        @XmlElement(required = true)
        protected BigInteger codice;
        @XmlElement(required = true)
        protected String descrizione;
        @XmlElement(required = true)
        protected String flgObbligatorio;
        @XmlElement(required = true)
        protected String notePubblicazione;
        @XmlElement(required = true)
        protected DocumentRoot.Intervento.Procedimento procedimento;
        protected DocumentRoot.Intervento.Allegati allegati;
        protected DocumentRoot.Intervento.Normative normative;
        @XmlElementRef(name = "codiceEnteOrigine", type = JAXBElement.class)
        protected JAXBElement<String> codiceEnteOrigine;
        @XmlElementRef(name = "codiceOrigine", type = JAXBElement.class)
        protected JAXBElement<Integer> codiceOrigine;

        /**
         * Gets the value of the codice property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getCodice() {
            return codice;
        }

        /**
         * Sets the value of the codice property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setCodice(BigInteger value) {
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
		 * @return the notePubblicazione
		 */
		public final String getNotePubblicazione() {
			return notePubblicazione;
		}

		/**
		 * @param notePubblicazione the notePubblicazione to set
		 */
		public final void setNotePubblicazione(String notePubblicazione) {
			this.notePubblicazione = notePubblicazione;
		}

		/**
         * Gets the value of the flgObbligatorio property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFlgObbligatorio() {
            return flgObbligatorio;
        }

        /**
         * Sets the value of the flgObbligatorio property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFlgObbligatorio(String value) {
            this.flgObbligatorio = value;
        }

        /**
         * Gets the value of the procedimento property.
         * 
         * @return
         *     possible object is
         *     {@link DocumentRoot.Intervento.Procedimento }
         *     
         */
        public DocumentRoot.Intervento.Procedimento getProcedimento() {
            return procedimento;
        }

        /**
         * Sets the value of the procedimento property.
         * 
         * @param value
         *     allowed object is
         *     {@link DocumentRoot.Intervento.Procedimento }
         *     
         */
        public void setProcedimento(DocumentRoot.Intervento.Procedimento value) {
            this.procedimento = value;
        }

        /**
         * Gets the value of the allegati property.
         * 
         * @return
         *     possible object is
         *     {@link DocumentRoot.Intervento.Allegati }
         *     
         */
        public DocumentRoot.Intervento.Allegati getAllegati() {
            return allegati;
        }

        /**
         * Sets the value of the allegati property.
         * 
         * @param value
         *     allowed object is
         *     {@link DocumentRoot.Intervento.Allegati }
         *     
         */
        public void setAllegati(DocumentRoot.Intervento.Allegati value) {
            this.allegati = value;
        }

        /**
         * Gets the value of the normative property.
         * 
         * @return
         *     possible object is
         *     {@link DocumentRoot.Intervento.Normative }
         *     
         */
        public DocumentRoot.Intervento.Normative getNormative() {
            return normative;
        }

        /**
         * Sets the value of the normative property.
         * 
         * @param value
         *     allowed object is
         *     {@link DocumentRoot.Intervento.Normative }
         *     
         */
        public void setNormative(DocumentRoot.Intervento.Normative value) {
            this.normative = value;
        }

        /**
         * Gets the value of the codiceEnteOrigine property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getCodiceEnteOrigine() {
            return codiceEnteOrigine;
        }

        /**
         * Sets the value of the codiceEnteOrigine property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setCodiceEnteOrigine(JAXBElement<String> value) {
            this.codiceEnteOrigine = ((JAXBElement<String> ) value);
        }

        /**
         * Gets the value of the codiceOrigine property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
         *     
         */
        public JAXBElement<Integer> getCodiceOrigine() {
            return codiceOrigine;
        }

        /**
         * Sets the value of the codiceOrigine property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
         *     
         */
        public void setCodiceOrigine(JAXBElement<Integer> value) {
            this.codiceOrigine = ((JAXBElement<Integer> ) value);
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
         *         &lt;element name="allegato" type="{}allegatoType" maxOccurs="unbounded"/>
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
            "allegato"
        })
        public static class Allegati {

            @XmlElement(required = true)
            protected List<AllegatoType> allegato;

            /**
             * Gets the value of the allegato property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the allegato property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAllegato().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link AllegatoType }
             * 
             * 
             */
            public List<AllegatoType> getAllegato() {
                if (allegato == null) {
                    allegato = new ArrayList<AllegatoType>();
                }
                return this.allegato;
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
         *         &lt;element name="normativa" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="nomeRiferimento" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="tipoNormativa" type="{}tipoNormativa"/>
         *                   &lt;element name="documentoFisico" type="{}documentoType" minOccurs="0"/>
         *                   &lt;element name="articoloRiferimento" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
            "normativa"
        })
        public static class Normative {

            @XmlElement(required = true)
            protected List<DocumentRoot.Intervento.Normative.Normativa> normativa;

            /**
             * Gets the value of the normativa property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the normativa property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getNormativa().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link DocumentRoot.Intervento.Normative.Normativa }
             * 
             * 
             */
            public List<DocumentRoot.Intervento.Normative.Normativa> getNormativa() {
                if (normativa == null) {
                    normativa = new ArrayList<DocumentRoot.Intervento.Normative.Normativa>();
                }
                return this.normativa;
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
             *         &lt;element name="nomeRiferimento" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="tipoNormativa" type="{}tipoNormativa"/>
             *         &lt;element name="documentoFisico" type="{}documentoType" minOccurs="0"/>
             *         &lt;element name="articoloRiferimento" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
                "nomeRiferimento",
                "tipoNormativa",
                "documentoFisico",
                "articoloRiferimento"
            })
            public static class Normativa {

                @XmlElement(required = true)
                protected String codice;
                @XmlElement(required = true)
                protected String descrizione;
                @XmlElement(required = true, nillable = true)
                protected String nomeRiferimento;
                @XmlElement(required = true)
                protected TipoNormativa tipoNormativa;
                protected DocumentoType documentoFisico;
                @XmlElement(required = true)
                protected String articoloRiferimento;

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
                 * Gets the value of the nomeRiferimento property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getNomeRiferimento() {
                    return nomeRiferimento;
                }

                /**
                 * Sets the value of the nomeRiferimento property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setNomeRiferimento(String value) {
                    this.nomeRiferimento = value;
                }

                /**
                 * Gets the value of the tipoNormativa property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TipoNormativa }
                 *     
                 */
                public TipoNormativa getTipoNormativa() {
                    return tipoNormativa;
                }

                /**
                 * Sets the value of the tipoNormativa property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TipoNormativa }
                 *     
                 */
                public void setTipoNormativa(TipoNormativa value) {
                    this.tipoNormativa = value;
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
                 * Gets the value of the articoloRiferimento property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getArticoloRiferimento() {
                    return articoloRiferimento;
                }

                /**
                 * Sets the value of the articoloRiferimento property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setArticoloRiferimento(String value) {
                    this.articoloRiferimento = value;
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
         *         &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="cud" type="{}cudType"/>
         *         &lt;element name="terminiEvasione" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *         &lt;element name="tipoProc" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="flgBollo">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;enumeration value="S"/>
         *               &lt;enumeration value="N"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
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
            "cud",
            "terminiEvasione",
            "tipoProc",
            "flgBollo",
            "descrizione"
        })
        public static class Procedimento {

            @XmlElement(required = true)
            protected String codice;
            @XmlElement(required = true)
            protected CudType cud;
            @XmlElement(required = true)
            protected BigInteger terminiEvasione;
            @XmlElement(required = true)
            protected String tipoProc;
            @XmlElement(required = true)
            protected String flgBollo;
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
             * Gets the value of the cud property.
             * 
             * @return
             *     possible object is
             *     {@link CudType }
             *     
             */
            public CudType getCud() {
                return cud;
            }

            /**
             * Sets the value of the cud property.
             * 
             * @param value
             *     allowed object is
             *     {@link CudType }
             *     
             */
            public void setCud(CudType value) {
                this.cud = value;
            }

            /**
             * Gets the value of the terminiEvasione property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getTerminiEvasione() {
                return terminiEvasione;
            }

            /**
             * Sets the value of the terminiEvasione property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setTerminiEvasione(BigInteger value) {
                this.terminiEvasione = value;
            }

            /**
             * Gets the value of the tipoProc property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTipoProc() {
                return tipoProc;
            }

            /**
             * Sets the value of the tipoProc property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTipoProc(String value) {
                this.tipoProc = value;
            }

            /**
             * Gets the value of the flgBollo property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFlgBollo() {
                return flgBollo;
            }

            /**
             * Sets the value of the flgBollo property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFlgBollo(String value) {
                this.flgBollo = value;
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

    }

}
