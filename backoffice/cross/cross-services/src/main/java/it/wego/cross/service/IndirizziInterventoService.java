/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dto.dozer.IndirizzoInterventoDTO;
import it.wego.cross.entity.IndirizziIntervento;
import it.wego.cross.xml.IndirizzoIntervento;
import org.springframework.stereotype.Service;

/**
 *
 * @author giuseppe
 */
@Service
public interface IndirizziInterventoService {

    public it.wego.cross.xml.IndirizzoIntervento trovaStrutturaXmlPerIdIndirizzoIntervento(IndirizziIntervento datiDB, it.wego.cross.xml.IndirizziIntervento indirizziIntervento);

    public it.wego.cross.xml.IndirizzoIntervento trovaStrutturaXmlPerIdIndirizzoIntervento(Integer id, it.wego.cross.xml.IndirizziIntervento indirizzoIntervento);

    public IndirizzoIntervento trovaStrutturaXmlPerCounter(IndirizzoInterventoDTO indirizzoInterventoDTO, it.wego.cross.xml.IndirizziIntervento indirizziIntervento);

    public IndirizzoIntervento trovaStrutturaXmlPerCounter(Integer counter, it.wego.cross.xml.IndirizziIntervento indirizziIntervento);

    public void serializzaEntityXmlindirizzoIntervento(it.wego.cross.entity.IndirizziIntervento datiDB, it.wego.cross.xml.IndirizzoIntervento datiXML);

    public IndirizziIntervento copy(it.wego.cross.entity.IndirizziIntervento datiDB, it.wego.cross.entity.Pratica pratica);
    
    public it.wego.cross.xml.IndirizziIntervento serializeIndirizziIntervento(it.wego.cross.entity.Pratica pratica);
    
    public java.util.List<IndirizziIntervento> serializeIndirizziInterventoXml(it.wego.cross.xml.Pratica pratica);

}
