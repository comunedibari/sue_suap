//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.03.28 alle 03:45:54 PM CET 
//


package it.wego.cross.client.xml.addud;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per SoggettoInternoInACLType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="SoggettoInternoInACLType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="InteroEnte" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="InteraAOO" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="ScrivanieDiUO" type="{}UOEstesaType"/>
 *         &lt;element name="Utente" type="{}UserType"/>
 *         &lt;element name="ScrivaniaVirtuale" type="{}ScrivaniaVirtualeType"/>
 *         &lt;element name="Gruppo" type="{}OggDiTabDiSistemaType"/>
 *         &lt;element name="RuoloAmmContestualizzato" type="{}RuoloAmmContestualizzatoType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SoggettoInternoInACLType", propOrder = {
    "interoEnte",
    "interaAOO",
    "scrivanieDiUO",
    "utente",
    "scrivaniaVirtuale",
    "gruppo",
    "ruoloAmmContestualizzato"
})
@XmlSeeAlso({
    ACLRecordType.class
})
public class SoggettoInternoInACLType {

    @XmlElement(name = "InteroEnte")
    protected Object interoEnte;
    @XmlElement(name = "InteraAOO")
    protected Object interaAOO;
    @XmlElement(name = "ScrivanieDiUO")
    protected UOEstesaType scrivanieDiUO;
    @XmlElement(name = "Utente")
    protected UserType utente;
    @XmlElement(name = "ScrivaniaVirtuale")
    protected ScrivaniaVirtualeType scrivaniaVirtuale;
    @XmlElement(name = "Gruppo")
    protected OggDiTabDiSistemaType gruppo;
    @XmlElement(name = "RuoloAmmContestualizzato")
    protected RuoloAmmContestualizzatoType ruoloAmmContestualizzato;

    /**
     * Recupera il valore della proprietinteroEnte.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getInteroEnte() {
        return interoEnte;
    }

    /**
     * Imposta il valore della proprietinteroEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setInteroEnte(Object value) {
        this.interoEnte = value;
    }

    /**
     * Recupera il valore della proprietinteraAOO.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getInteraAOO() {
        return interaAOO;
    }

    /**
     * Imposta il valore della proprietinteraAOO.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setInteraAOO(Object value) {
        this.interaAOO = value;
    }

    /**
     * Recupera il valore della proprietscrivanieDiUO.
     * 
     * @return
     *     possible object is
     *     {@link UOEstesaType }
     *     
     */
    public UOEstesaType getScrivanieDiUO() {
        return scrivanieDiUO;
    }

    /**
     * Imposta il valore della proprietscrivanieDiUO.
     * 
     * @param value
     *     allowed object is
     *     {@link UOEstesaType }
     *     
     */
    public void setScrivanieDiUO(UOEstesaType value) {
        this.scrivanieDiUO = value;
    }

    /**
     * Recupera il valore della proprietutente.
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
     * Imposta il valore della proprietutente.
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
     * Recupera il valore della proprietscrivaniaVirtuale.
     * 
     * @return
     *     possible object is
     *     {@link ScrivaniaVirtualeType }
     *     
     */
    public ScrivaniaVirtualeType getScrivaniaVirtuale() {
        return scrivaniaVirtuale;
    }

    /**
     * Imposta il valore della proprietscrivaniaVirtuale.
     * 
     * @param value
     *     allowed object is
     *     {@link ScrivaniaVirtualeType }
     *     
     */
    public void setScrivaniaVirtuale(ScrivaniaVirtualeType value) {
        this.scrivaniaVirtuale = value;
    }

    /**
     * Recupera il valore della proprietgruppo.
     * 
     * @return
     *     possible object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public OggDiTabDiSistemaType getGruppo() {
        return gruppo;
    }

    /**
     * Imposta il valore della proprietgruppo.
     * 
     * @param value
     *     allowed object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public void setGruppo(OggDiTabDiSistemaType value) {
        this.gruppo = value;
    }

    /**
     * Recupera il valore della proprietruoloAmmContestualizzato.
     * 
     * @return
     *     possible object is
     *     {@link RuoloAmmContestualizzatoType }
     *     
     */
    public RuoloAmmContestualizzatoType getRuoloAmmContestualizzato() {
        return ruoloAmmContestualizzato;
    }

    /**
     * Imposta il valore della proprietruoloAmmContestualizzato.
     * 
     * @param value
     *     allowed object is
     *     {@link RuoloAmmContestualizzatoType }
     *     
     */
    public void setRuoloAmmContestualizzato(RuoloAmmContestualizzatoType value) {
        this.ruoloAmmContestualizzato = value;
    }

}
