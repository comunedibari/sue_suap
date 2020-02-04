/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import it.gov.impresainungiorno.schema.base.AttivitaISTAT;
import it.gov.impresainungiorno.schema.base.EMail;
import it.gov.impresainungiorno.schema.base.IndirizzoConRecapiti;
import it.gov.impresainungiorno.schema.base.Telefono;
import it.gov.impresainungiorno.schema.suap.ri.spc.ErroreDettaglioImpresa;
import it.gov.impresainungiorno.schema.suap.ri.spc.IscrizioneImpresaRiSpcResponse;
import it.gov.impresainungiorno.schema.suap.ri.spc.IscrizioneImpresaRiSpcResponse.DatiIdentificativi;
import it.gov.impresainungiorno.schema.suap.ri.spc.WarningDettaglioImpresa;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.RecapitoDTO;
import it.wego.cross.dto.registroimprese.AttivitaIstatRIDTO;
import it.wego.cross.dto.registroimprese.DatiIdentificativiRIDTO;
import it.wego.cross.dto.registroimprese.ErroreRIDTO;
import it.wego.cross.dto.registroimprese.RecapitoRIDTO;
import it.wego.cross.dto.registroimprese.WarningRIDTO;
import it.wego.cross.service.ComuniService;
import it.wego.cross.utils.Log;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author giuseppe
 */
@Component
public class RegistroImpreseSerializer {

    @Autowired
    ComuniService comuniService;
    public  DatiIdentificativiRIDTO serialize(IscrizioneImpresaRiSpcResponse iscrizione) throws Exception{

        DatiIdentificativiRIDTO d = new DatiIdentificativiRIDTO();
        if (iscrizione.getDatiIdentificativi() != null) {
            DatiIdentificativi i = iscrizione.getDatiIdentificativi();
            Log.APP.info("Codice fiscale: " + i.getCodiceFiscale());
            d.setCodiceFiscale(i.getCodiceFiscale());
            Log.APP.info("Denominazione: " + i.getDenominazione());
            d.setDenominazione(i.getDenominazione());
            Log.APP.info("Numero REA: " + i.getNrea());
            d.setNrea(i.getNrea());
            Log.APP.info("Partita IVA: " + i.getPartitaIva());
            d.setPartitaIva(i.getPartitaIva());
            Log.APP.info("Partita IVA: " + i.getPartitaIva());
            d.setPartitaIva(i.getPartitaIva());
            Log.APP.info("CCIAA: " + i.getCciaa());
            d.setCciaa(i.getCciaa());
            Log.APP.info("Stato impresa: " + i.getStatoImpresa());
            d.setStatoImpresa(i.getStatoImpresa());
            if (iscrizione.getAttivita() != null) {
                List<AttivitaIstatRIDTO> attivita = new ArrayList<AttivitaIstatRIDTO>();
                for (AttivitaISTAT a : iscrizione.getAttivita()) {
                    AttivitaIstatRIDTO dto = serialize(a);
                    attivita.add(dto);
                }
                d.setAttivita(attivita);
            }
            Log.APP.info("Serializzo sede legale");
            if (iscrizione.getSedeLegale() != null) {
                RecapitoDTO sedeLegale = serialize(iscrizione.getSedeLegale());
                d.setSedeLegale(sedeLegale);
            }
        }
        if (iscrizione.getErrore() != null) {
            ErroreRIDTO errore = serialize(iscrizione.getErrore());
            d.setErrore(errore);
        }
        if (iscrizione.getWarning() != null && iscrizione.getWarning().size() > 0) {
            List<WarningRIDTO> warning = new ArrayList<WarningRIDTO>();
            for (WarningDettaglioImpresa w : iscrizione.getWarning()) {
                WarningRIDTO dto = serialize(w);
                warning.add(dto);
            }
            d.setWarning(warning);
        }
        return d;
    }

    public static AttivitaIstatRIDTO serialize(AttivitaISTAT a) {
        Log.APP.info("Serializzo Attivita ISTAT");
        AttivitaIstatRIDTO dto = new AttivitaIstatRIDTO();
        Log.APP.info("Catalogo: " + a.getCatalogo());
        dto.setCatalogo(a.getCatalogo());
        Log.APP.info("Codice istat: " + a.getCodiceIstat());
        dto.setCodiceIstat(a.getCodiceIstat());
        Log.APP.info("Principale: " + a.isPrincipale());
        dto.setPrincipale(a.isPrincipale());
        Log.APP.info("Value: " + a.getValue());
        dto.setValue(a.getValue());
        return dto;
    }

    public RecapitoDTO serialize(IndirizzoConRecapiti indirizzo) throws Exception {
        Log.APP.info("Serializzo recapito");
        RecapitoDTO dto = new RecapitoDTO();
//        if (indirizzo.getSitoWeb() != null) {
//            Log.APP.info("Siti web totali: " + indirizzo.getSitoWeb().size());
//            dto.setSitoWeb(indirizzo.getSitoWeb());
//        }
        if (indirizzo.getTelefono() != null) {
            Log.APP.info("Telefoni totali: " + indirizzo.getTelefono().size());
            Map<String, String> telefoni = new HashMap<String, String>();
            for (Telefono t : indirizzo.getTelefono()) {
                Log.APP.info("Aggiungo telefono " + t.getValue() + " (" + t.getTipo() + ")");
               // telefoni.put(t.getTipo(), t.getValue());
                dto.setTelefono(t.getValue());
                break;
            }
            
        }
        if (indirizzo.getEMail() != null) {
            Log.APP.info("Email totali: " + indirizzo.getEMail().size());
            Map<String, String> email = new HashMap<String, String>();
            for (EMail mail : indirizzo.getEMail()) {
                Log.APP.info("Aggiungo email " + mail.getValue() + " (" + mail.getTipo() + ")");
                //email.put(mail.getTipo(), mail.getValue());
                dto.setEmail(mail.getValue());
                break;
            }
         
        }
        if(indirizzo.getCap()!=null)
        {
            Log.APP.info("cap: " + indirizzo.getCap());
            dto.setCap(indirizzo.getCap());    
        }
        if(indirizzo.getComune()!=null)
        {
            Log.APP.info("cOMUNE: " + indirizzo.getComune().getValue());
            dto.setDescComune(indirizzo.getComune().getValue());
            ComuneDTO comune = comuniService.trovaComune(indirizzo.getComune().getValue(), new Date());
            Log.APP.info("Comune: query DB cerca comune" + indirizzo.getComune().getValue());
            dto.setIdComune(comune.getIdComune());
            Log.APP.info("Provincia: " + indirizzo.getComune().getValue());
            dto.setDescProvincia(comune.getProvincia().getDescrizione());
            Log.APP.info("Provicnia: query DB cerca provincia" + indirizzo.getComune().getValue());
            dto.setIdProvincia(comune.getProvincia().getIdProvincie());
            Log.APP.info("Stato:" + comune.getStato().getDescrizione());
            dto.setDescStato(comune.getStato().getDescrizione());
            Log.APP.info("Stato: query DB cerac Stato" + indirizzo.getStato().getValue());
            dto.setIdStato(comune.getStato().getIdStato());
        }
//        if(indirizzo.getProvincia()!=null)
//        {
//            Log.APP.info("Provincia: " + indirizzo.getComune().getValue());
//            dto.setDescProvincia(indirizzo.getProvincia().getValue());
//            Log.APP.info("Provicnia: query DB cerca provincia" + indirizzo.getComune().getValue());
//            dto.setIdProvincia(Integer.MIN_VALUE);
//        } 
//        if(indirizzo.getStato()!=null)
//        {
//            Log.APP.info("Stato:" + indirizzo.getStato().getValue());
//            dto.setDescStato(indirizzo.getStato().getValue());
//            Log.APP.info("Stato: query DB cerac Stato" + indirizzo.getStato().getValue());
//            dto.setIdStato(Integer.SIZE);
//            //dto.setCodStato(indirizzo.getStato().getCodice());
//        }
        if(indirizzo.getNumeroCivico()!=null)
        {
            Log.APP.info("N civico:" + indirizzo.getNumeroCivico());
            dto.setnCivico(indirizzo.getNumeroCivico());
        }
        if(indirizzo.getDenominazioneStradale()!=null)
        {
            Log.APP.info("Indirizzo:" + indirizzo.getToponimo()+" "+indirizzo.getDenominazioneStradale());
            dto.setIndirizzo(indirizzo.getToponimo()+" "+indirizzo.getDenominazioneStradale());
        }
        Log.APP.info("Localita:" + indirizzo.getFrazione());
        dto.setLocalita(indirizzo.getFrazione());
        //lookup
        
        dto.setDescTipoIndirizzo("SEDE");
        Log.APP.info("TipoIndirizzo: query DB SEDE");
        dto.setIdTipoIndirizzo(Integer.MIN_VALUE);
        Log.APP.info("AltreInfo: "+indirizzo.getCittaStraniera());
        dto.setAltreInfoIndirizzo(indirizzo.getCittaStraniera());
        
        
        
        return dto;
    }

    public static ErroreRIDTO serialize(ErroreDettaglioImpresa errore) {
        Log.APP.info("Serializzo errore");
        ErroreRIDTO dto = new ErroreRIDTO();
        Log.APP.info("Codice: " + errore.getCodice());
        dto.setCodice(errore.getCodice());
        Log.APP.info("Valore: " + errore.getValue());
        dto.setValore(errore.getValue());
        return dto;
    }

    public static WarningRIDTO serialize(WarningDettaglioImpresa warn) {
        Log.APP.info("Serializzo warning");
        WarningRIDTO dto = new WarningRIDTO();
        Log.APP.info("Codice: " + warn.getCodice());
        dto.setCodice(warn.getCodice());
        Log.APP.info("Valore: " + warn.getValue());
        dto.setValore(warn.getValue());
        return dto;
    }
}
