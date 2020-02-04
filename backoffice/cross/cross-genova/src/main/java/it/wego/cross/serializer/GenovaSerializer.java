/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import it.wego.cross.constants.AnaTipiEvento;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAllegati;
import it.wego.cross.entity.ProcedimentiTesti;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Recapiti;
import it.wego.cross.entity.Scadenze;
import it.wego.cross.entity.Utente;
import it.wego.cross.service.EntiService;
import it.wego.cross.utils.Utils;
import it.wego.cross.xml.Allegati;
import it.wego.cross.xml.Anagrafiche;
import it.wego.cross.xml.DatiCatastali;
import it.wego.cross.xml.Eventi;
import it.wego.cross.xml.Evento;
import it.wego.cross.xml.Procedimenti;
import it.wego.cross.xml.Procedimento;
import it.wego.cross.xml.Recapito;
import it.wego.cross.xml.Scadenza;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.apache.commons.codec.binary.Base64;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author giuseppe
 */
@Component
public class GenovaSerializer {

    @Autowired
    private Mapper mapper;
    @Autowired
    private PraticheSerializer praticheSerializer;
    
    public it.wego.cross.xml.Pratica serialize(Pratica pratica, PraticheEventi ultimoEvento, Utente user) throws Exception {
        it.wego.cross.xml.Pratica xml = praticheSerializer.serialize(pratica, ultimoEvento, user);
        
        PraticheEventi evento = null;
        for (PraticheEventi ev : pratica.getPraticheEventiList()) {
            if (ev.getIdEvento() != null && ev.getIdEvento().getCodEvento().equals(AnaTipiEvento.RICEZIONE_PRATICA)) {
                evento = ev;
            }
        }
        if (evento != null) {
            String protocollo = evento.getProtocollo();
            if (!Utils.e(protocollo)) {
                String[] dati = protocollo.split("/");
                if (dati.length == 3) {
                    xml.setRegistro(dati[0]);
                    xml.setAnno(dati[1]);
                    xml.setProtocollo(dati[2]);
                }
            }
            xml.setDataProtocollo(Utils.dateToXmlGregorianCalendar(evento.getDataProtocollo()));
        }

        return xml;
    }

}
