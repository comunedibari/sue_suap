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

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.people.dbm.xsd.importExportLocal package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DocumentRootInterventoCodiceOrigine_QNAME = new QName("", "codiceOrigine");
    private final static QName _DocumentRootInterventoCodiceEnteOrigine_QNAME = new QName("", "codiceEnteOrigine");
    private final static QName _AllegatoTypeNumPagineMax_QNAME = new QName("", "numPagineMax");
    private final static QName _AllegatoTypeTipologie_QNAME = new QName("", "tipologie");
    private final static QName _AllegatoTypeDimensioneMax_QNAME = new QName("", "dimensioneMax");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.people.dbm.xsd.importExportLocal
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DocumentRoot.Intervento.Normative.Normativa }
     * 
     */
    public DocumentRoot.Intervento.Normative.Normativa createDocumentRootInterventoNormativeNormativa() {
        return new DocumentRoot.Intervento.Normative.Normativa();
    }

    /**
     * Create an instance of {@link AllegatoType.Documento.Dichiarazione.Campi }
     * 
     */
    public AllegatoType.Documento.Dichiarazione.Campi createAllegatoTypeDocumentoDichiarazioneCampi() {
        return new AllegatoType.Documento.Dichiarazione.Campi();
    }

    /**
     * Create an instance of {@link DocumentRoot.Intervento }
     * 
     */
    public DocumentRoot.Intervento createDocumentRootIntervento() {
        return new DocumentRoot.Intervento();
    }

    /**
     * Create an instance of {@link AllegatoType.Condizione }
     * 
     */
    public AllegatoType.Condizione createAllegatoTypeCondizione() {
        return new AllegatoType.Condizione();
    }

    /**
     * Create an instance of {@link DocumentRoot.Intervento.Allegati }
     * 
     */
    public DocumentRoot.Intervento.Allegati createDocumentRootInterventoAllegati() {
        return new DocumentRoot.Intervento.Allegati();
    }

    /**
     * Create an instance of {@link DocumentRoot }
     * 
     */
    public DocumentRoot createDocumentRoot() {
        return new DocumentRoot();
    }

    /**
     * Create an instance of {@link AllegatoType.Documento.Dichiarazione.Campi.Campo }
     * 
     */
    public AllegatoType.Documento.Dichiarazione.Campi.Campo createAllegatoTypeDocumentoDichiarazioneCampiCampo() {
        return new AllegatoType.Documento.Dichiarazione.Campi.Campo();
    }

    /**
     * Create an instance of {@link AllegatoType }
     * 
     */
    public AllegatoType createAllegatoType() {
        return new AllegatoType();
    }

    /**
     * Create an instance of {@link CudType }
     * 
     */
    public CudType createCudType() {
        return new CudType();
    }

    /**
     * Create an instance of {@link AllegatoType.Documento.Dichiarazione.ValoriListBox.ValoreListBox }
     * 
     */
    public AllegatoType.Documento.Dichiarazione.ValoriListBox.ValoreListBox createAllegatoTypeDocumentoDichiarazioneValoriListBoxValoreListBox() {
        return new AllegatoType.Documento.Dichiarazione.ValoriListBox.ValoreListBox();
    }

    /**
     * Create an instance of {@link DocumentRoot.Intervento.Procedimento }
     * 
     */
    public DocumentRoot.Intervento.Procedimento createDocumentRootInterventoProcedimento() {
        return new DocumentRoot.Intervento.Procedimento();
    }

    /**
     * Create an instance of {@link DocumentRoot.Intervento.Normative }
     * 
     */
    public DocumentRoot.Intervento.Normative createDocumentRootInterventoNormative() {
        return new DocumentRoot.Intervento.Normative();
    }

    /**
     * Create an instance of {@link AllegatoType.Documento.Dichiarazione }
     * 
     */
    public AllegatoType.Documento.Dichiarazione createAllegatoTypeDocumentoDichiarazione() {
        return new AllegatoType.Documento.Dichiarazione();
    }

    /**
     * Create an instance of {@link TipoNormativa }
     * 
     */
    public TipoNormativa createTipoNormativa() {
        return new TipoNormativa();
    }

    /**
     * Create an instance of {@link AllegatoType.Documento.Dichiarazione.ValoriListBox }
     * 
     */
    public AllegatoType.Documento.Dichiarazione.ValoriListBox createAllegatoTypeDocumentoDichiarazioneValoriListBox() {
        return new AllegatoType.Documento.Dichiarazione.ValoriListBox();
    }

    /**
     * Create an instance of {@link DocumentoType }
     * 
     */
    public DocumentoType createDocumentoType() {
        return new DocumentoType();
    }

    /**
     * Create an instance of {@link AllegatoType.Documento }
     * 
     */
    public AllegatoType.Documento createAllegatoTypeDocumento() {
        return new AllegatoType.Documento();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "codiceOrigine", scope = DocumentRoot.Intervento.class)
    public JAXBElement<Integer> createDocumentRootInterventoCodiceOrigine(Integer value) {
        return new JAXBElement<Integer>(_DocumentRootInterventoCodiceOrigine_QNAME, Integer.class, DocumentRoot.Intervento.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "codiceEnteOrigine", scope = DocumentRoot.Intervento.class)
    public JAXBElement<String> createDocumentRootInterventoCodiceEnteOrigine(String value) {
        return new JAXBElement<String>(_DocumentRootInterventoCodiceEnteOrigine_QNAME, String.class, DocumentRoot.Intervento.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "numPagineMax", scope = AllegatoType.class)
    public JAXBElement<BigInteger> createAllegatoTypeNumPagineMax(BigInteger value) {
        return new JAXBElement<BigInteger>(_AllegatoTypeNumPagineMax_QNAME, BigInteger.class, AllegatoType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "tipologie", scope = AllegatoType.class)
    public JAXBElement<String> createAllegatoTypeTipologie(String value) {
        return new JAXBElement<String>(_AllegatoTypeTipologie_QNAME, String.class, AllegatoType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "dimensioneMax", scope = AllegatoType.class)
    public JAXBElement<BigInteger> createAllegatoTypeDimensioneMax(BigInteger value) {
        return new JAXBElement<BigInteger>(_AllegatoTypeDimensioneMax_QNAME, BigInteger.class, AllegatoType.class, value);
    }

}
