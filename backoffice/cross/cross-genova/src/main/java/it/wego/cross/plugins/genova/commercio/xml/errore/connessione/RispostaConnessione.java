//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.05.07 alle 02:14:24 PM CEST 
//
package it.wego.cross.plugins.genova.commercio.xml.errore.connessione;

import it.wego.cross.plugins.genova.commercio.xml.RispostaErmes;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per anonymous complex type.
 *
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in
 * questa classe.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="esito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "esito",
    "descrizione"
})
@XmlRootElement(name = "risposta_connessione")
public class RispostaConnessione extends RispostaErmes {

//    protected String esito;
//    protected String descrizione;
//
//    public String getEsito() {
//        return esito;
//    }
//
//    public void setEsito(String value) {
//        this.esito = value;
//    }
//
//    public String getDescrizione() {
//        return descrizione;
//    }
//
//    public void setDescrizione(String descrizione) {
//        this.descrizione = descrizione;
//    }

}
