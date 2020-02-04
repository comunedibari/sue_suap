/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dto.dozer.IndirizzoInterventoDTO;
import it.wego.cross.entity.IndirizziIntervento;
import it.wego.cross.entity.Pratica;
import it.wego.cross.utils.Utils;
import it.wego.cross.xml.IndirizzoIntervento;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author giuseppe
 */
@Service
public class IndirizziInterventoServiceImpl implements IndirizziInterventoService {

    @Autowired
    private Mapper mapper;

    @Override
    public it.wego.cross.xml.IndirizzoIntervento trovaStrutturaXmlPerIdIndirizzoIntervento(IndirizziIntervento datiDB, it.wego.cross.xml.IndirizziIntervento indirizziIntervento) {
        return trovaStrutturaXmlPerIdIndirizzoIntervento(datiDB.getIdIndirizzoIntervento(), indirizziIntervento);
    }

    @Override
    public it.wego.cross.xml.IndirizzoIntervento trovaStrutturaXmlPerIdIndirizzoIntervento(Integer id, it.wego.cross.xml.IndirizziIntervento indirizzoIntervento) {
        it.wego.cross.xml.IndirizzoIntervento ret = null;
        for (it.wego.cross.xml.IndirizzoIntervento i : indirizzoIntervento.getIndirizzoIntervento()) {
            if (i.getIdIndirizzoIntervento() != null && id != null) {
                if (id == i.getIdIndirizzoIntervento().intValue()) {
                    ret = i;
                    break;
                }
            }
        }
        return ret;
    }

    @Override
    public IndirizzoIntervento trovaStrutturaXmlPerCounter(IndirizzoInterventoDTO indirizzoInterventoDTO, it.wego.cross.xml.IndirizziIntervento indirizziIntervento) {
        return trovaStrutturaXmlPerCounter(indirizzoInterventoDTO.getCounter(), indirizziIntervento);
    }

    @Override
    public IndirizzoIntervento trovaStrutturaXmlPerCounter(Integer counter, it.wego.cross.xml.IndirizziIntervento indirizziIntervento) {
        it.wego.cross.xml.IndirizzoIntervento retIndirizzoIntervento = null;
        for (it.wego.cross.xml.IndirizzoIntervento indirizzoIntervento : indirizziIntervento.getIndirizzoIntervento()) {
            if (indirizzoIntervento.getCounter() != null && counter != null) {
                if (counter == indirizzoIntervento.getCounter().intValue()) {
                    retIndirizzoIntervento = indirizzoIntervento;
                    break;
                }
            }
        }
        return retIndirizzoIntervento;
    }

    @Override
    public void serializzaEntityXmlindirizzoIntervento(it.wego.cross.entity.IndirizziIntervento datiDB, it.wego.cross.xml.IndirizzoIntervento datiXML) {
        mapper.map(datiDB, datiXML);
        if (datiDB.getIdDug() != null) {
            datiXML.setIdDug(Utils.bi(datiDB.getIdDug().getIdDug()));
        }
    }

    @Override
    public it.wego.cross.entity.IndirizziIntervento copy(it.wego.cross.entity.IndirizziIntervento datiDB, Pratica pratica) {
        it.wego.cross.entity.IndirizziIntervento indirizzoCopy = mapper.map(datiDB, it.wego.cross.entity.IndirizziIntervento.class);
        indirizzoCopy.setIdPratica(pratica);
        return indirizzoCopy;
    }
    
    @Override
    public it.wego.cross.xml.IndirizziIntervento serializeIndirizziIntervento(it.wego.cross.entity.Pratica pratica) {
        it.wego.cross.xml.IndirizziIntervento listaIndirizzi = null;
        for (IndirizziIntervento indirizzoIntervento : pratica.getIndirizziInterventoList()) {
            it.wego.cross.xml.IndirizzoIntervento indirizzoInterventoXml = mapper.map(indirizzoIntervento, it.wego.cross.xml.IndirizzoIntervento.class);
            if (indirizzoIntervento.getIdDug() != null) {
                indirizzoInterventoXml.setIdDug(Utils.bi(indirizzoIntervento.getIdDug().getIdDug()));
            }
            if (listaIndirizzi == null){
                listaIndirizzi = new it.wego.cross.xml.IndirizziIntervento();
            }
            listaIndirizzi.getIndirizzoIntervento().add(indirizzoInterventoXml);
        }
        return listaIndirizzi;
    }

	@Override
	public java.util.List<IndirizziIntervento> serializeIndirizziInterventoXml(it.wego.cross.xml.Pratica pratica) {
    	java.util.List<IndirizziIntervento> listaIndirizzi = new ArrayList<IndirizziIntervento>();
        for (it.wego.cross.xml.IndirizzoIntervento indirizzoIntervento : pratica.getIndirizziIntervento().getIndirizzoIntervento()) {
        	IndirizziIntervento indirizzoInterventoEntity = mapper.map(indirizzoIntervento, IndirizziIntervento.class);
            
            
            listaIndirizzi.add(indirizzoInterventoEntity);
        }
        return listaIndirizzi;
    }
}
