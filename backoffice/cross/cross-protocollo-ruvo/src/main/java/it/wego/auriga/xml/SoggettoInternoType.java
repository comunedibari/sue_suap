package it.wego.auriga.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Rappresenta un soggetto interno all'AOO che pu� essere: una UO, un utente, una scrivania virtuale (che rappresenta l'utente non come persona, ma nella funzione che svolge presso una certa UO), un gruppo (di utenti o UO o scrivanie virtuali) o un ruolo amministrativo (es: Dirigente, Direttore Generale ecc). Quando si assegna ad un gruppo o un ruolo il sistema assegna a tutti gli utenti, UO e scrivanie del gruppo o aventi il ruolo
 * 
 * <p>Classe Java per SoggettoInternoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="SoggettoInternoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="UO" type="{}UOType"/>
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
@XmlType(name = "SoggettoInternoType", propOrder = {
    "uo",
    "utente",
    "scrivaniaVirtuale",
    "gruppo",
    "ruoloAmmContestualizzato"
})
@XmlSeeAlso({
    AssegnazioneInternaType.class,
    SoggettoInternoEstesoType.class
})
public class SoggettoInternoType {

    @XmlElement(name = "UO")
    protected UOType uo;
    @XmlElement(name = "Utente")
    protected UserType utente;
    @XmlElement(name = "ScrivaniaVirtuale")
    protected ScrivaniaVirtualeType scrivaniaVirtuale;
    @XmlElement(name = "Gruppo")
    protected OggDiTabDiSistemaType gruppo;
    @XmlElement(name = "RuoloAmmContestualizzato")
    protected RuoloAmmContestualizzatoType ruoloAmmContestualizzato;

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
     * Recupera il valore della propriet� scrivaniaVirtuale.
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
     * Imposta il valore della propriet� scrivaniaVirtuale.
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
     * Recupera il valore della propriet� gruppo.
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
     * Imposta il valore della propriet� gruppo.
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
     * Recupera il valore della propriet� ruoloAmmContestualizzato.
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
     * Imposta il valore della propriet� ruoloAmmContestualizzato.
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
