//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.05.27 alle 07:27:17 PM CEST 
//
package it.wego.cross.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per ente complex type.
 *
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in
 * questa classe.
 *
 * <pre>
 * &lt;complexType name="dirittiSegreteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oneriUrbanizzazione" type="{http://www.w3.org/2001/XMLSchema}oneriUrbanizzazione" minOccurs="0"/>
 *         &lt;element name="contributiCosto" type="{http://www.w3.org/2001/XMLSchema}contributiCosto" minOccurs="0"/>
 *         &lt;element name="serviziTariffaIndividuale" type="{http://www.w3.org/2001/XMLSchema}serviziTariffaIndividuale" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dirittiSegreteria", propOrder = {
    "oneriUrbanizzazione",
    "contributiCosto",
    "serviziTariffaIndividuale"
})
public class DirittiSegreteria {

    @XmlElement(name = "oneriUrbanizzazione")
    protected OneriUrbanizzazione oneriUrbanizzazione;
    @XmlElement(name = "contributiCosto")
    protected ContributiCosto contributiCosto;
    @XmlElement(name = "serviziTariffaIndividuale")
    protected ServiziTariffaIndividuale serviziTariffaIndividuale;

    public OneriUrbanizzazione getOneriUrbanizzazione() {
        return oneriUrbanizzazione;
    }

    public void setOneriUrbanizzazione(OneriUrbanizzazione value) {
        this.oneriUrbanizzazione = value;
    }

    public ContributiCosto getContributiCosto() {
        return contributiCosto;
    }

    public void setContributiCosto(ContributiCosto value) {
        this.contributiCosto = value;
    }

    public ServiziTariffaIndividuale getServiziTariffaIndividuale() {
        return serviziTariffaIndividuale;
    }

    public void setServiziTariffaIndividuale(ServiziTariffaIndividuale value) {
        this.serviziTariffaIndividuale = value;
    }
}
