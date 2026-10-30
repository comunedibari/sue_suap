package it.wego.cross.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.wego.cross.constants.TipoRuolo;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.PraticaDTO;
import it.wego.cross.dto.ProcedimentoDTO;
import it.wego.cross.dto.comunicazione.AnagraficaMinifiedDTO;
import it.wego.cross.dto.comunicazione.DettaglioPraticaDTO;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.serializer.DettaglioComunicazioneSerializer;
import it.wego.cross.utils.AnagraficaUtils;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.PraticaUtils;
import it.wego.cross.utils.RecapitoUtils;

public class PraticheServiceImpl {

	private PraticaDao praticaDao;
	private LookupDao lookupDao;
	private DettaglioComunicazioneSerializer dettaglioComunicazioneSerializer;
	private AnagraficaUtils anagraficaUtils;
	private RecapitoUtils recapitoUtils;



    public PraticheServiceImpl(PraticaDao praticaDao, LookupDao lookupDao,
			DettaglioComunicazioneSerializer dettaglioComunicazioneSerializer, AnagraficaUtils anagraficaUtils,
			RecapitoUtils recapitoUtils) {
		super();
		this.praticaDao = praticaDao;
		this.lookupDao = lookupDao;
		this.dettaglioComunicazioneSerializer = dettaglioComunicazioneSerializer;
		this.anagraficaUtils = anagraficaUtils;
		this.recapitoUtils = recapitoUtils;
	}


	public PraticaDTO dettaglioXmlPratica(Integer idPratica) throws Exception {

        Pratica pratica = praticaDao.findPratica(idPratica);
        String xmlPratica = new String(pratica.getIdStaging().getXmlPratica());
        it.wego.cross.xml.Pratica praticaXml = PraticaUtils.getPraticaFromXML(xmlPratica);
        PraticaDTO praticaDto = new PraticaDTO();
        praticaDto.setIdPratica(idPratica);

        praticaDto.setTipoMessaggio(pratica.getIdStaging().getTipoMessaggio());
        praticaDto.setDataApertura(pratica.getDataApertura());
        praticaDto.setDataChiusura(pratica.getDataChiusura());
        praticaDto.setDataRicezione(pratica.getDataRicezione());
        praticaDto.setIdentificativoPratica(pratica.getIdentificativoPratica());
        praticaDto.setOggettoPratica(pratica.getOggettoPratica());
        praticaDto.setProtocollo(pratica.getProtocollo());
// TODO: protocollo        
        //praticaDto.setFascicolo(pratica.getCodFascicolo());
        praticaDto.setRegistro(pratica.getCodRegistro());
        praticaDto.setAnnoRiferimento(pratica.getAnnoRiferimento());
        praticaDto.setIdPratica(pratica.getIdPratica());
        praticaDto.setResponsabileProcedimento(praticaXml.getResponsabileProcedimento());
        /* ^^CS AGGIUNTA PER CONFIGURAZIONE */
        praticaDto.setIdEnte(pratica.getIdProcEnte().getIdEnte().getIdEnte());
        praticaDto.setIdComune(pratica.getIdComune().getIdComune());
        //Anagrafiche
        List<AnagraficaDTO> anagraficaList = new ArrayList<AnagraficaDTO>();
        for (it.wego.cross.xml.Anagrafiche anagraficaXml : praticaXml.getAnagrafiche()) {
            AnagraficaDTO anagrafica = new AnagraficaDTO();
            anagraficaUtils.copyXML2Anagrafica(anagraficaXml, anagrafica, pratica);
            anagraficaList.add(anagrafica);
        }
        praticaDto.setAnagraficaList(anagraficaList);

        //Procedimenti
        List<ProcedimentoDTO> procedimentiList = new ArrayList<ProcedimentoDTO>();
        for (it.wego.cross.xml.Procedimento procedimentoXml : praticaXml.getProcedimenti().getProcedimento()) {
            ProcedimentoDTO procedimento = new ProcedimentoDTO();
            procedimento.fromXML(procedimentoXml);
            procedimentiList.add(procedimento);
        }
        praticaDto.setProcedimentiList(procedimentiList);
/*
        //^^CS NOTIFICA
        if (praticaXml.getNotifica() != null) {
            RecapitoDTO notifica = new RecapitoDTO();
            recapitoUtils.copyXML2Recapito(praticaXml.getNotifica(), notifica);
            praticaDto.setNotifica(notifica);
        }
        // ^^CS ALLEGATI
        List<AllegatoDTO> allegati = new ArrayList<AllegatoDTO>();
        it.wego.cross.xml.Allegati allegatiXML = AllegatiSerializer.serialize(pratica);
        for (it.wego.cross.xml.Allegato allegatoXMl : allegatiXML.getAllegato()) {
            AllegatoDTO allegato = AllegatiSerializer.serializeAllegatoXML(allegatoXMl);
            allegati.add(allegato);
        }
        AllegatoDTO ripilogo = new AllegatoDTO();
        if (pratica.getIdModello() != null) {
            ripilogo.setIdAllegato(pratica.getIdModello().getId());
        }
        praticaDto.setRiepilogoPratica(ripilogo);
        praticaDto.setAllegatiList(allegati);

        GestionePratica gp = pluginService.getGestionePratica(pratica.getIdProcEnte().getIdEnte().getIdEnte());
        //^^CS DATI CATASTALI
        List<DatiCatastaliDTO> datiList = new ArrayList<DatiCatastaliDTO>();
        if (praticaXml.getDatiCatastali() != null && praticaXml.getDatiCatastali().getImmobile() != null) {
            for (it.wego.cross.xml.Immobile dati : praticaXml.getDatiCatastali().getImmobile()) {
                DatiCatastaliDTO datiDto = new DatiCatastaliDTO();
                datiCatastaliSerializer.serialize(pratica, dati, datiDto);
                datiDto.setIdPratica(praticaDto.getIdPratica());
                if (Utils.e(dati.getConfermato())) {
                    datiDto.setConfermato("false");
                }
                datiList.add(datiDto);
            }
        }
        praticaDto.setDatiCatastali(datiList);
        List<IndirizzoInterventoDTO> datiListIndirizzi = new ArrayList<IndirizzoInterventoDTO>();
        if (praticaXml.getIndirizziIntervento() != null && praticaXml.getIndirizziIntervento().getIndirizzoIntervento() != null) {
            for (it.wego.cross.xml.IndirizzoIntervento dati : praticaXml.getIndirizziIntervento().getIndirizzoIntervento()) {
                IndirizzoInterventoDTO datiDto = new IndirizzoInterventoDTO();
                datiCatastaliSerializer.serialize(pratica, dati, datiDto);
                datiDto.setIdPratica(idPratica);
                if (Utils.e(dati.getConfermato())) {
                    datiDto.setConfermato("false");
                }
                datiListIndirizzi.add(datiDto);
            }
        }
        praticaDto.setIndirizziIntervento(datiListIndirizzi);

        praticaDto.setComune(lookupService.getComuneDTOFromId(praticaDto.getIdComune()));

        praticaDto.setEsistenzaStradario(gp.getEsistenzaStradario(pratica));
        praticaDto.setEsistenzaRicercaCatasto(gp.getEsistenzaRicercaCatasto(pratica));
*/
        return praticaDto;
    }

    
    public DettaglioPraticaDTO getDettaglioPratica(Pratica pratica) throws Exception {
        DettaglioPraticaDTO dettaglio = new DettaglioPraticaDTO();
        Log.APP.info("Creo il dettaglio della pratica");
        Log.APP.info("ID " + pratica.getIdentificativoPratica());
        dettaglio.setIdPratica(pratica.getIdPratica());
        dettaglio.setIdentificativoPratica(pratica.getIdentificativoPratica());
        dettaglio.setDataRicezione(pratica.getDataRicezione());
        Log.APP.info("Protocollo " + pratica.getProtocollo());
        dettaglio.setNumeroProtocollo(pratica.getProtocollo() + "/" + pratica.getAnnoRiferimento());
        Log.APP.info("Oggetto " + pratica.getOggettoPratica());
        dettaglio.setOggetto(pratica.getOggettoPratica());
        Log.APP.info("Stato pratica: " + pratica.getIdStatoPratica().getDescrizione());
        dettaglio.setStatoPratica(pratica.getIdStatoPratica().getDescrizione());
        dettaglio.setCodiceStatoPratica(pratica.getIdStatoPratica().getGrpStatoPratica());
        List<AnagraficaMinifiedDTO> anagrafiche = new LinkedList<AnagraficaMinifiedDTO>();
        Log.APP.info("Trovate " + pratica.getPraticaAnagraficaList().size() + " anagrafiche");

        List<AnagraficaMinifiedDTO> richiedenti = new ArrayList<AnagraficaMinifiedDTO>();
        List<AnagraficaMinifiedDTO> beneficiari = new ArrayList<AnagraficaMinifiedDTO>();
        List<AnagraficaMinifiedDTO> altro = new ArrayList<AnagraficaMinifiedDTO>();

        List<PraticaAnagrafica> praticaAnagraficaList = new ArrayList<PraticaAnagrafica>();
        List<PraticaAnagrafica> praticaAnagraficaCross = pratica.getPraticaAnagraficaList();
//        List<PraticaAnagrafica> praticaAnagraficaCivilia = civiliaMapper.getAnagraficheByIdentificativoPratica(pratica.getIdentificativoPratica());
        
//        DettaglioPratica dettPraticaDms = dmsClient.getDettaglioPraticheById(pratica.getIdentificativoPratica());
//        List<PraticaAnagrafica> praticaAnagraficaDms = dmsClient.getAnagraficheByIdentificativoPratica(pratica.getIdentificativoPratica());
//        List<PraticaAnagrafica> praticaAnagraficaDms = dettPraticaDms.getPratica();
//        Log.APP.info("Trovate " + praticaAnagraficaDms.size() + " praticaAnagraficaDms");
        
		praticaAnagraficaList.addAll(praticaAnagraficaCross);
//        praticaAnagraficaList.addAll(praticaAnagraficaCivilia);
//        praticaAnagraficaList.addAll(praticaAnagraficaDms);
//        
        Log.APP.info("Trovate " + praticaAnagraficaList.size() + " anagrafiche");


        for (PraticaAnagrafica pa : praticaAnagraficaList) {
            Anagrafica a = pa.getAnagrafica();
            Log.APP.info("Preparo il dettaglio per l'anagrafica " + a.getIdAnagrafica());
            AnagraficaMinifiedDTO anagrafica = dettaglioComunicazioneSerializer.serializeAnagrafica(pa);
            if (pa.getLkTipoRuolo() != null) {
                anagrafica.setRuolo(pa.getLkTipoRuolo().getDescrizione());
                anagrafica.setIdRuolo(pa.getLkTipoRuolo().getIdTipoRuolo());
            }
            if (pa.getLkTipoRuolo() != null) {
                anagrafica.setRuolo(pa.getLkTipoRuolo().getDescrizione());
                anagrafica.setIdRuolo(pa.getLkTipoRuolo().getIdTipoRuolo());
            }
            Log.APP.info("Verifico esistenza etichetta per le anagrafiche civilia " + pa.getIdEtichetta());

            int etichetta = 0;
            if(pa.getIdEtichetta() != null) { etichetta = pa.getIdEtichetta().intValue();}
            if (etichetta != 0) {              
            	String ruolo_civilia = lookupDao.findRuoloCiviliaByIdEtichetta(etichetta);
                Log.APP.info("Set ruolo etichetta anagrafica civilia " + ruolo_civilia + " Cognome " + anagrafica.getCognome() + " Nome " + anagrafica.getNome());
                anagrafica.setRuolo_civilia(ruolo_civilia);
            } else {
            	anagrafica.setRuolo_civilia("");
            }
            
            
            if (pa.getLkTipoRuolo().getCodRuolo().equals(TipoRuolo.RICHIEDENTE)) {
                //Il richiedente e' sempre persona fisica
                anagrafica.setIsDittaIndividuale(Boolean.FALSE);
            } else {
                if (pa.getFlgDittaIndividuale() != null && pa.getFlgDittaIndividuale().equalsIgnoreCase("S")) {
                    anagrafica.setIsDittaIndividuale(Boolean.TRUE);
                } else {
                    anagrafica.setIsDittaIndividuale(Boolean.FALSE);
                }
            }
            Log.APP.info("Aggiungo l'anagrafica di " + anagrafica.getCodiceFiscale() + " " + anagrafica.getPartitaIVA());
            Log.APP.info("Aanagrafica ruolo " + pa.getLkTipoRuolo().getIdTipoRuolo() 
            		+ " " + pa.getLkTipoRuolo().getCodRuolo()
            		+ " " + pa.getLkTipoRuolo().getDescrizione()
            		);
            if (pa.getLkTipoRuolo().getCodRuolo().equals(TipoRuolo.RICHIEDENTE)) {
                richiedenti.add(anagrafica);
            } else if (pa.getLkTipoRuolo().getCodRuolo().equals(TipoRuolo.BENEFICIARIO)) {
                beneficiari.add(anagrafica);
            } else {
                altro.add(anagrafica);
            }
        }

        anagrafiche.addAll(richiedenti);
        anagrafiche.addAll(beneficiari);
        anagrafiche.addAll(altro);
        dettaglio.setAnagrafiche(anagrafiche);

        return dettaglio;
    }

}
