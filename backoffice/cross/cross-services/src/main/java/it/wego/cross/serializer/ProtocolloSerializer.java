package it.wego.cross.serializer;

import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.AnagraficaRecapiti;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.Recapiti;
import it.wego.cross.plugins.protocollo.beans.SoggettoProtocollo;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CS
 */
public class ProtocolloSerializer {

    public static List<SoggettoProtocollo> serialize(List<it.wego.cross.entity.PraticaAnagrafica> destinatari) {
        List<SoggettoProtocollo> soggetti = new ArrayList<SoggettoProtocollo>();
        if (destinatari != null && destinatari.size() > 0) {
            for (PraticaAnagrafica an : destinatari) {
                SoggettoProtocollo soggetto = ProtocolloSerializer.serialize(an.getAnagrafica());
                soggetti.add(soggetto);
            }
        }
        return soggetti;
    }

    public static List<SoggettoProtocollo> serializeXml(List<it.wego.cross.xml.Anagrafiche> destinatariXML) {
        List<SoggettoProtocollo> soggetti = new ArrayList<SoggettoProtocollo>();
        if (destinatariXML != null && destinatariXML.size() > 0) {
            for (it.wego.cross.xml.Anagrafiche an : destinatariXML) {
                SoggettoProtocollo soggetto = ProtocolloSerializer.serialize(an.getAnagrafica());
                soggetti.add(soggetto);
            }
        }
        return soggetti;
    }

    public static SoggettoProtocollo serialize(Enti ente) {
        /**
         * TODO: DA MODIFICARE
         */
        SoggettoProtocollo soggetto = new SoggettoProtocollo();
        soggetto.setCap(String.valueOf(ente.getCap()));
        soggetto.setCitta(ente.getCitta());
        soggetto.setCodice(null);
        soggetto.setCodiceFiscale(ente.getCodiceFiscale());
        soggetto.setDenominazione(ente.getDescrizione());
        soggetto.setTipoPersona("G");
        soggetto.setIndirizzo(ente.getIndirizzo());
        soggetto.setMail(ente.getEmail());
        soggetto.setMezzo(null);
        soggetto.setPartitaIva(ente.getPartitaIva());
        soggetto.setTipo(null);
        soggetto.setMailPec(ente.getPec());
        return soggetto;
    }

    public static SoggettoProtocollo serialize(Anagrafica a) {
        SoggettoProtocollo soggetto = new SoggettoProtocollo();
        soggetto.setCodiceFiscale(a.getCodiceFiscale());
        if (!Utils.e(a.getDenominazione())) {
            soggetto.setDenominazione(a.getDenominazione());
            soggetto.setTipoPersona("G");
        } else {
            soggetto.setNome(a.getNome());
            soggetto.setCognome(a.getCognome());
            soggetto.setTipoPersona("F");
        }
        soggetto.setPartitaIva(a.getPartitaIva());
        Recapiti ufficiale = null;
        for (AnagraficaRecapiti ar : a.getAnagraficaRecapitiList()) {
            ufficiale = ar.getIdRecapito();
        }
        if (ufficiale != null) {
            soggetto.setCap(ufficiale.getCap());
            soggetto.setCitta(ufficiale.getIdComune() != null ? ufficiale.getIdComune().getDescrizione() : null);
            soggetto.setIndirizzo(ufficiale.getIndirizzo());
            if (ufficiale.getPec() != null) {
                soggetto.setMail(ufficiale.getPec());
            } else {
                soggetto.setMail(ufficiale.getEmail());
            }
            soggetto.setMailPec(ufficiale.getPec());
        }
        return soggetto;
    }

    public static SoggettoProtocollo serialize(it.wego.cross.xml.Anagrafica a) {
        SoggettoProtocollo soggetto = new SoggettoProtocollo();
        soggetto.setCodiceFiscale(a.getCodiceFiscale());
        if (!Utils.e(a.getDenominazione())) {
            soggetto.setDenominazione(a.getDenominazione());
            soggetto.setTipoPersona("G");
        } else {
            soggetto.setNome(a.getNome());
            soggetto.setCognome(a.getCognome());
            soggetto.setTipoPersona("F");
        }
        soggetto.setPartitaIva(a.getPartitaIva());
        it.wego.cross.xml.Recapito ufficiale = null;
        for (it.wego.cross.xml.Recapito ar : a.getRecapiti().getRecapito()) {
            ufficiale = ar;
        }
        if (ufficiale != null) {
            soggetto.setCap(ufficiale.getCap());
            soggetto.setCitta(ufficiale.getDesComune());
            soggetto.setIndirizzo(ufficiale.getIndirizzo());
            if (ufficiale.getPec() != null) {
                soggetto.setMail(ufficiale.getPec());
            } else {
                soggetto.setMail(ufficiale.getEmail());
            }
            soggetto.setMailPec(ufficiale.getPec());
        }
        return soggetto;
    }
}
