/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import it.wego.cross.dto.ScadenzaDTO;
import it.wego.cross.entity.ProcessiEventiScadenze;
import it.wego.cross.entity.Scadenze;
import it.wego.cross.entity.Utente;
import it.wego.cross.entity.view.ScadenzeDaChiudereView;
import it.wego.cross.utils.Utils;
import java.io.Serializable;
import javax.xml.datatype.DatatypeConfigurationException;

/**
 *
 * @author giuseppe
 */
public class ScadenzeSerializer implements Serializable {

    public static ScadenzaDTO serialize(Scadenze s) {
        ScadenzaDTO scadenza = new ScadenzaDTO();
        scadenza.setDataRicezione(s.getIdPratica().getDataRicezione());
        scadenza.setIdentificativo(s.getIdPratica().getIdentificativoPratica());
        scadenza.setOggetto(s.getIdPratica().getOggettoPratica());
        if (s.getIdPratica().getIdStatoPratica() != null) {
            scadenza.setStato(s.getIdPratica().getIdStatoPratica().getDescrizione());
        }
        if (!Utils.e(s.getDescrizioneScadenza())) {
            scadenza.setDescrizione(s.getDescrizioneScadenza());
        } else {
            scadenza.setDescrizione(s.getIdAnaScadenza().getDesAnaScadenze());
        }

        //Se la scadenza è chiusa allora metto 0 giorni
        if (s.getIdStato().getGrpStatoScadenza().equalsIgnoreCase("C")) {
            scadenza.setGiornirestanti(Long.valueOf(0));
        } else {
            scadenza.setGiornirestanti(Utils.getDaysBetweenDates(Utils.now(), s.getDataFineScadenza()));
        }

        scadenza.setDataFineScadenza(s.getDataFineScadenza());
        scadenza.setDataInizioScadenza(s.getDataInizioScadenza());
        scadenza.setDataScadenzaCalcolata(s.getDataFineScadenzaCalcolata());
        scadenza.setDataScadenza(s.getDataScadenza());
        scadenza.setIdAnaScadenza(s.getIdAnaScadenza().getIdAnaScadenze());
        scadenza.setPratica(s.getIdPratica().getIdPratica());
        scadenza.setCodStatoScadenza(s.getIdStato().getGrpStatoScadenza());
        scadenza.setStatoScadenza(s.getIdStato().getDesStatoScadenza());
        //scadenza.setProtocollo(s.getIdPratica().getProtocollo());
        scadenza.setProtocollo(s.getIdPratica().getProtocollo()+"/"+s.getIdPratica().getAnnoRiferimento());
        if (s.getIdPratica().getIdStatoPratica() != null) {
            scadenza.setStatoPratica(s.getIdPratica().getIdStatoPratica().getDescrizione());
        }
        if (s.getIdPratica().getIdUtente() != null) {
            Utente utente = s.getIdPratica().getIdUtente();
            String utenteInCarico = utente.getCognome() + " " + utente.getNome();
            scadenza.setUtenteInCarico(utenteInCarico);
        }
        return scadenza;
    }

    public static ScadenzaDTO serialize(ScadenzeDaChiudereView scadenza) {
        ScadenzaDTO dto = new ScadenzaDTO();
        dto.setPratica(scadenza.getIdPratica());
        dto.setDataRicezione(scadenza.getDataEventoDaChiudere());
        dto.setDataFineScadenza(scadenza.getDataFineScadenza());
        dto.setDataInizioScadenza(scadenza.getDataInizioScadenza());
        dto.setIdScadenza(scadenza.getIdScadenza());
        dto.setDesEnte(scadenza.getDesEnte());
        if (!Utils.e(scadenza.getDesScadenza())) {
            dto.setDescrizione(scadenza.getDesScadenza());
        } else {
            dto.setDescrizione(scadenza.getDesEventoOrigine());
        }
        dto.setProtocollo(scadenza.getNumeroProtocollo());
        return dto;
    }

    public static ScadenzaDTO serialize(ProcessiEventiScadenze pes) {
        ScadenzaDTO dto = new ScadenzaDTO();
        dto.setDescrizione(pes.getLkScadenze().getDesAnaScadenze());
        dto.setIdAnaScadenza(pes.getLkScadenze().getIdAnaScadenze());
        dto.setIdEvento(pes.getProcessiEventi().getIdEvento());
        dto.setTermini(pes.getTerminiScadenza());
        return dto;
    }
    public static it.wego.cross.xml.Scadenza serializeXML(Scadenze s) throws DatatypeConfigurationException {
        it.wego.cross.xml.Scadenza scadenzaXML = new it.wego.cross.xml.Scadenza();
        scadenzaXML.setDataFineScadenza(Utils.dateToXmlGregorianCalendar(s.getDataFineScadenza()));
        scadenzaXML.setDataInizioScadenza(Utils.dateToXmlGregorianCalendar(s.getDataInizioScadenza()));
        scadenzaXML.setDataScadenza(Utils.dateToXmlGregorianCalendar(s.getDataScadenza()));
        scadenzaXML.setDescrizione(s.getDescrizioneScadenza());
        scadenzaXML.setGiorniTeoriciScadenza(Utils.bi(s.getGiorniTeoriciScadenza()));
        scadenzaXML.setIdScadenza(Utils.bi(s.getIdScadenza()));
        if(s.getIdAnaScadenza()!=null)
        {
            scadenzaXML.setDesAnaScadenza(s.getIdAnaScadenza().getDesAnaScadenze());
            scadenzaXML.setFlgAnaScadenza(s.getIdAnaScadenza().getFlgScadenzaPratica());
            scadenzaXML.setIdAnaScadenza(s.getIdAnaScadenza().getIdAnaScadenze());        
        }
        if(s.getIdPratica()!=null)
        {
            scadenzaXML.setIdPratica(Utils.bi(s.getIdPratica().getIdPratica()));    
        }
        if(s.getIdPraticaEvento()!=null)
        {
            scadenzaXML.setIdPraticaEvento(Utils.bi(s.getIdPraticaEvento().getIdPraticaEvento()));
        }
        if(s.getIdStato()!=null)
        {
            scadenzaXML.setDesStatoScadenza(s.getIdStato().getDesStatoScadenza());
            scadenzaXML.setIdStatoScadenza(s.getIdStato().getIdStato());       
            scadenzaXML.setGrpStatoScadenza(s.getIdStato().getGrpStatoScadenza());    
        }
        return scadenzaXML;
    }
}
