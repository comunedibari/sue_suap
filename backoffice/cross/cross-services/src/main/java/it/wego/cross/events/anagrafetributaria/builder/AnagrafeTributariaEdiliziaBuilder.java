/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.anagrafetributaria.builder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Preconditions;

import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.AnagraficaRecapiti;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.IndirizziIntervento;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkTipoCollegio;
import it.wego.cross.entity.LkTipoQualifica;
import it.wego.cross.entity.LkTipoUnita;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticaOperazione;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.Recapiti;
import it.wego.cross.events.anagrafetributaria.bean.AnagrafeTributariaDTO;
import it.wego.cross.service.AnagraficheService;
import it.wego.cross.service.LookupService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import it.wego.cross.xml.anagrafetributaria.edilizia.AnagrafeTributariaEdilizia;
import it.wego.cross.xml.anagrafetributaria.edilizia.Beneficiario;
import it.wego.cross.xml.anagrafetributaria.edilizia.Concatenazione;
import it.wego.cross.xml.anagrafetributaria.edilizia.Controllo;
import it.wego.cross.xml.anagrafetributaria.edilizia.DatoCatastale;
import it.wego.cross.xml.anagrafetributaria.edilizia.IdentificazioneRichiesta;
import it.wego.cross.xml.anagrafetributaria.edilizia.IdentificazioneRichiesta.Richiesta;
import it.wego.cross.xml.anagrafetributaria.edilizia.Impresa;
import it.wego.cross.xml.anagrafetributaria.edilizia.Professionista;
import it.wego.cross.xml.anagrafetributaria.edilizia.Professionista.DettaglioProfessionista;
import it.wego.cross.xml.anagrafetributaria.edilizia.RecordControllo;
import it.wego.cross.xml.anagrafetributaria.edilizia.RecordDettaglio;
import it.wego.cross.xml.anagrafetributaria.edilizia.RecordDettaglio.Beneficiari;
import it.wego.cross.xml.anagrafetributaria.edilizia.RecordDettaglio.DatiCatastali;
import it.wego.cross.xml.anagrafetributaria.edilizia.RecordDettaglio.IdentificazioneRichieste;
import it.wego.cross.xml.anagrafetributaria.edilizia.RecordDettaglio.Imprese;
import it.wego.cross.xml.anagrafetributaria.edilizia.RecordDettaglio.Professionisti;
import it.wego.cross.xml.anagrafetributaria.edilizia.Soggetto;
import it.wego.cross.xml.anagrafetributaria.edilizia.SoggettoFisico;
import it.wego.cross.xml.anagrafetributaria.edilizia.SoggettoGiuridico;

/**
 *
 * @author giuseppe
 */
@Component
public class AnagrafeTributariaEdiliziaBuilder {

    private static final int ORDINARIO = 1;
    private static final int TAVOLARE = 2;
	private static final int PROFESSIONISTI = 3;
	private static final int IMPRESE = 4;
	private static final int BENEFICIARI = 2;
	private static final int RICHIEDENTE = 1;
	
	private static final String AT_DATE_FORMAT = "ddMMyyyy";
    private static final String PERSONA_FISICA = "F";


    private static Map<String,String> interventionTypes = new HashMap<>();
    static{
    	interventionTypes.put("500000011", "2");
    	interventionTypes.put("500000013", "2");
    	interventionTypes.put("500000004", "3");
    	interventionTypes.put("500000027", "4");
    	interventionTypes.put("510000001", "5");
    	interventionTypes.put("510000002", "5");
    	interventionTypes.put("510000003", "5");
    	interventionTypes.put("510000004", "5");
    	interventionTypes.put("510000005", "5");
    	interventionTypes.put("510000006", "5");
    	interventionTypes.put("510000007", "5");
    	interventionTypes.put("510000008", "6");
    }
    
    @Autowired
    private AnagraficheService anagraficheService;
    @Autowired
    private LookupService lookupService;
    
    public AnagrafeTributariaEdilizia buildAnagrafeTributariaEdilizia(AnagrafeTributariaDTO anagrafeTributaria, List<Pratica> paperworks) throws Exception {
        Controllo controllo = serializeControllo();
        Soggetto soggettoObbligato = new Soggetto();
        SoggettoGiuridico soggettoGiuridico = new SoggettoGiuridico();
        LkComuni comune = lookupService.findComuneById(anagrafeTributaria.getIdSoggettoObbligato());
        Enti ente = lookupService.findFirstInstitutionSUEInTown(comune);
        Preconditions.checkArgument(comune != null, "Tax Register: town not found");
        Preconditions.checkArgument(ente != null, "Tax Register: institution not found");
        Preconditions.checkArgument(!Utils.e(ente.getCodiceFiscale()), "Tax Register: institution fiscal code not found");
        
        soggettoObbligato.setCodiceFiscale(ente.getCodiceFiscale());
        soggettoGiuridico.setCodiceCatastaleSedeLegale(comune.getCodCatastale());
        soggettoGiuridico.setDenominazione(comune.getDescrizione());
        soggettoObbligato.setSoggettoGiuridico(soggettoGiuridico);
        
        RecordControllo recordTestaCoda = new RecordControllo();
        recordTestaCoda.setTipoRecord("0");
        recordTestaCoda.setCodiceIdentificativoFornitura(anagrafeTributaria.getCodFornitura());
        recordTestaCoda.setCodiceNumericoFornitura("29");
        recordTestaCoda.setSoggettoObbligato(soggettoObbligato);
        recordTestaCoda.setAnnoRiferimento(String.valueOf(anagrafeTributaria.getAnnoRiferimento()));
        recordTestaCoda.setControllo(controllo);
        AnagrafeTributariaEdilizia anagrafeTributariaEdilizia = new AnagrafeTributariaEdilizia();
        AnagrafeTributariaEdilizia.RecordsDettaglio details = new AnagrafeTributariaEdilizia.RecordsDettaglio();
        for(Pratica paperwork: paperworks) {
        	Log.APP.info("Build AnagrafeTributariaEdilizia with Paperwork " + paperwork.getIdPratica());
        	details.getRecordDettaglio().add(serializeRecordDettaglioEdilizia(paperwork));
        }
        anagrafeTributariaEdilizia.setRecordTesta(recordTestaCoda);
        anagrafeTributariaEdilizia.setRecordsDettaglio(details);
        anagrafeTributariaEdilizia.setRecordCoda(recordTestaCoda);
        return anagrafeTributariaEdilizia;
    }
    
    public RecordDettaglio serializeRecordDettaglioEdilizia(Pratica pratica) throws Exception {
    	RecordDettaglio record = new RecordDettaglio();
    	IdentificazioneRichieste identificazioneRichieste = serializeIdentificazioneRichiesta(pratica);
    	record.setIdentificazioneRichieste(identificazioneRichieste);
    	RecordDettaglio.Beneficiari beneficiari = serializeBeneficiari(pratica);
    	record.setBeneficiari(beneficiari);
    	RecordDettaglio.DatiCatastali datiCatastali = serializeDatiCatastali(pratica);
    	record.setDatiCatastali(datiCatastali);
    	RecordDettaglio.Professionisti professionisti = serializeProfessionisti(pratica);
    	record.setProfessionisti(professionisti);
    	RecordDettaglio.Imprese imprese = serializeImprese(pratica);
    	record.setImprese(imprese);
    	return record;
    }

    public IdentificazioneRichieste serializeIdentificazioneRichiesta(Pratica paperwork) throws Exception {
    	IdentificazioneRichieste identificazioneRichieste = new IdentificazioneRichieste();
        List<PraticaAnagrafica> paperworksRequester = get(paperwork, RICHIEDENTE, false);
        Preconditions.checkArgument(paperworksRequester != null, "Tax Register paperwork "+  paperwork.getIdPratica()+ ": Non è presente un richiedente");
        
        for(PraticaAnagrafica paperworkRequester : paperworksRequester) {
        	IdentificazioneRichiesta identificazioneRichiesta = new IdentificazioneRichiesta();
        	identificazioneRichiesta.setTipoRecord("1");
	        Anagrafica richiedente = paperworkRequester.getAnagrafica();

	        Integer idRecapito = null;
	        if (!String.valueOf(richiedente.getTipoAnagrafica()).equals(PERSONA_FISICA)) {
	        	for(AnagraficaRecapiti recap: paperworkRequester.getAnagrafica().getAnagraficaRecapitiList()) {
	        		if(recap.getIdTipoIndirizzo().getIdTipoIndirizzo().equals(3))
	        			idRecapito = recap.getIdRecapito().getIdRecapito();
	        	}
	        }
	        
	        Soggetto soggettoRichiedente = serializeSoggetto(paperwork, richiedente, idRecapito);
	        Preconditions.checkArgument(soggettoRichiedente.isChosen(), "Tax Register: requester not valid");
	        identificazioneRichiesta.setSoggettoRichiedente(soggettoRichiedente);

	        if(paperworkRequester.getIdTipoQualifica() != null) {
		        LkTipoQualifica lkTipoQualifica = lookupService.findLkTipoQualifica(paperworkRequester.getIdTipoQualifica().getIdTipoQualifica());
		        identificazioneRichiesta.setQualificaRichiedente(lkTipoQualifica.getCodQualifica());
	        }
	        Richiesta richiesta = serializeRichiesta(paperwork);
	        identificazioneRichiesta.setRichiesta(richiesta);
	        Controllo controllo = serializeControllo();
	        identificazioneRichiesta.setControllo(controllo);
	        identificazioneRichieste.getIdentificazioneRichieste().add(identificazioneRichiesta);
        }
        return identificazioneRichieste;
    }


	private Soggetto serializeSoggetto(Pratica paperwork, Anagrafica richiedente, Integer idRecapito) throws ParseException {
        Soggetto soggetto = new Soggetto();
        soggetto.setCodiceFiscale(richiedente.getCodiceFiscale());
        if (String.valueOf(richiedente.getTipoAnagrafica()).equals(PERSONA_FISICA)) {
            SoggettoFisico soggettoFisico = new SoggettoFisico();
            soggettoFisico.setCodiceFiscale(richiedente.getCodiceFiscale());
            soggettoFisico.setCognome(richiedente.getCognome());
            soggettoFisico.setNome(richiedente.getNome());
            soggettoFisico.setSesso(String.valueOf(richiedente.getSesso()).toUpperCase());
//            Preconditions.checkArgument(richiedente.getDataNascita() != null, "Tax Register paperwork " + paperwork.getIdPratica() + ": date of birth not found");
//            Preconditions.checkArgument(richiedente.getIdComuneNascita() != null, "Tax Register paperwork " + paperwork.getIdPratica() + ": place of birth not found");
            if(richiedente.getDataNascita() != null) {
	            String dataNascita = serializeDate(richiedente.getDataNascita());
	            soggettoFisico.setDataNascita(dataNascita);
            }
            if(richiedente.getIdComuneNascita() != null) {
	            String codCatastaleComuneNascita = richiedente.getIdComuneNascita().getCodCatastale();
	            soggettoFisico.setCodiceCatastaleComuneNascita(codCatastaleComuneNascita);
            }
            soggetto.setSoggettoFisico(soggettoFisico);
        } else {
            SoggettoGiuridico soggettoGiuridico = new SoggettoGiuridico();
            String denominazione = richiedente.getDenominazione();
            if (denominazione.length() > 60) {
                denominazione = denominazione.substring(0, 59);
            }
            soggettoGiuridico.setDenominazione(denominazione);
            Recapiti sedeLegale = anagraficheService.findRecapitoById(idRecapito);
//            Preconditions.checkArgument(sedeLegale != null && sedeLegale.getIdComune() != null, "Tax Register paperwork "+  paperwork.getIdPratica()+ ": legal address not found");
            if(sedeLegale != null && sedeLegale.getIdComune() != null)
            	soggettoGiuridico.setCodiceCatastaleSedeLegale(sedeLegale.getIdComune().getCodCatastale());
            soggetto.setSoggettoGiuridico(soggettoGiuridico);
        }
        return soggetto;
    }

    private Richiesta serializeRichiesta(Pratica paperwork) throws ParseException {
        Richiesta richiesta = new Richiesta();
        
        String numeroProtocollo = getFirstEventWithValidProtocol(paperwork).getProtocollo();
        Preconditions.checkArgument(!Utils.e(numeroProtocollo), "Tax Register paperwork "+  paperwork.getIdPratica()+ ": protocol number not found");
	    richiesta.setNumeroProtocollo(numeroProtocollo);
        richiesta.setDataPresentazioneRichiesta(serializeDate(getFirstEventWithValidProtocol(paperwork).getDataEvento()));
        
        IndirizziIntervento indirizzo = getFirstAdress(paperwork); 
//        for(IndirizziIntervento temp: paperwork.getIndirizziInterventoList()) {
//        	String indirizzoOggetto1 = indirizzo.getIndirizzo() + " " + indirizzo.getCivico();
//        	String indirizzoOggetto2 = temp.getIndirizzo() + " " + temp.getCivico();
//        	Preconditions.checkArgument(indirizzoOggetto1.equals(indirizzoOggetto2), "Tax Register paperwork "+  paperwork.getIdPratica()+ ": problems with addresses");
//        }
        if(indirizzo != null) {
	        String indirizzoOggettoIstanza = indirizzo.getIndirizzo() + " " + indirizzo.getCivico();
	        if (indirizzoOggettoIstanza.length() > 35) {
	            indirizzoOggettoIstanza = indirizzoOggettoIstanza.substring(0, 34);
	        }
	        richiesta.setIndirizzoOggettoIstanza(indirizzoOggettoIstanza);
	        richiesta.setIdIndirizzoOggettoIstanza(String.valueOf(indirizzo.getIdIndirizzoIntervento()));
        }
        richiesta.setTipoRichiesta(paperwork.getTipoPratica().getTipoPratica().startsWith("SCIA")?"1":"0");
        boolean found = false;
        for(PraticaOperazione po: paperwork.getPraticaOperazioni()) {
        	String code = po.getOperazione().getCodice();
        	if(!Utils.e(code)) {
        		richiesta.setTipologiaIntervento(interventionTypes.get(code) != null?interventionTypes.get(code):"7");
        		found = true;
        	}
        }
//        Preconditions.checkArgument(found, "Tax Register paperwork "+  paperwork.getIdPratica()+ ": intervention not found");
        return richiesta;
    }

    public Beneficiari serializeBeneficiari(Pratica paperwork) throws ParseException, Exception {
        Beneficiari beneficiari = new Beneficiari();
        List<PraticaAnagrafica> beneficiariDTO = get(paperwork, BENEFICIARI, false);
        for (PraticaAnagrafica dto : beneficiariDTO) {
            Beneficiario beneficiario = new Beneficiario();
            beneficiario.setTipoRecord("2");
            Concatenazione concatenazione = serializeConcatenazione(paperwork);
            beneficiario.setConcatenazione(concatenazione);
            Anagrafica anagraficaBeneficiario = anagraficheService.findAnagraficaById(dto.getAnagrafica().getIdAnagrafica());
            Recapiti recapito = anagraficheService.getRecapitoRiferimentoAnagrafica(anagraficaBeneficiario);
            Integer idRecapitoSede = null;
            if (recapito != null) {
                idRecapitoSede = recapito.getIdRecapito();
            }  
            Soggetto soggetto = serializeSoggetto(paperwork, anagraficaBeneficiario, idRecapitoSede);
            beneficiario.setSoggettoBeneficiario(soggetto);
            if(dto.getIdTipoQualifica() != null) {
	            LkTipoQualifica lkTipoQualifica = lookupService.findLkTipoQualifica(dto.getIdTipoQualifica().getIdTipoQualifica());
	            beneficiario.setQualificaBeneficiario(lkTipoQualifica.getCodQualifica());
            }
            Controllo controllo = serializeControllo();
            beneficiario.setControllo(controllo);
            beneficiari.getBeneficiario().add(beneficiario);
        }
        return beneficiari;
    }

    public DatiCatastali serializeDatiCatastali(Pratica paperwork) {
        DatiCatastali datiCatastali = new DatiCatastali();
        List<it.wego.cross.entity.DatiCatastali> datiCatastaliDTO = paperwork.getDatiCatastaliList();
        for (it.wego.cross.entity.DatiCatastali dto : datiCatastaliDTO) {        	
        	LkTipoUnita tipoUnita = lookupService.findTipoUnitaById(dto.getIdTipoUnitaInt());
        	if(dto.getIdTipoSistemaCatastale().getIdTipoSistemaCatastale().equals(ORDINARIO)) {
//        		Preconditions.checkArgument(tipoUnita != null ,"Tax Register paperwork " + paperwork.getIdPratica() + ": unit type not found");
        		Preconditions.checkArgument(dto.getFoglio() != null ,"Tax Register paperwork " + paperwork.getIdPratica() + ": cadastral sheet not found");
//        		Preconditions.checkArgument(dto.getMappale() != null ,"Tax Register paperwork " + paperwork.getIdPratica() + ": cadastral parcel type not found");
        	}
        	if(dto.getIdTipoSistemaCatastale().getIdTipoSistemaCatastale().equals(TAVOLARE)) {
        		Preconditions.checkArgument(dto.getEstensioneParticella() != null ,"Tax Register paperwork " + paperwork.getIdPratica() + ": cadastral extension parcel type not found");
        		Preconditions.checkArgument(dto.getIdTipoParticella() != null ,"Tax Register paperwork " + paperwork.getIdPratica() + ": cadastral parcel type type not found");
        	}
        	
        	DatoCatastale datoCatastale = new DatoCatastale();
            datoCatastale.setTipoRecord("3");
            Concatenazione concatenazione = serializeConcatenazione(paperwork);
            datoCatastale.setConcatenazione(concatenazione);
            if(tipoUnita != null)
            	datoCatastale.setTipoUnita(tipoUnita.getCodTipoUnita());
            
            datoCatastale.setSezione(dto.getSezione());
            datoCatastale.setFoglio(dto.getFoglio());
            if(dto.getIdTipoParticella() != null)
            	datoCatastale.setParticella(dto.getIdTipoParticella().getDescrizione());
            if(dto.getEstensioneParticella() != null)
            	datoCatastale.setEstensioneParticella(dto.getEstensioneParticella());
            datoCatastale.setSubalterno(dto.getSubalterno());
            Controllo controllo = serializeControllo();
            datoCatastale.setControllo(controllo);
            datiCatastali.getDatoCatastale().add(datoCatastale);
        }

        return datiCatastali;
    }

    public Professionisti serializeProfessionisti(Pratica pratica) {
        Professionisti professionisti = new Professionisti();
        List<PraticaAnagrafica> professionistiDTO = get(pratica, PROFESSIONISTI, false);
        for (PraticaAnagrafica dto : professionistiDTO) {
            Professionista professionista = new Professionista();
            professionista.setTipoRecord("4");
            Concatenazione concatenazione = serializeConcatenazione(pratica);
            professionista.setConcatenazione(concatenazione);
            DettaglioProfessionista dettaglioProfessionista = serializeDettaglioProfessionista(dto);
            professionista.setDettaglioProfessionista(dettaglioProfessionista);
            Controllo controllo = serializeControllo();
            professionista.setControllo(controllo);
            professionisti.getProfessionista().add(professionista);
        }
        return professionisti;
    }

    private DettaglioProfessionista serializeDettaglioProfessionista(PraticaAnagrafica praticaAnagrafica) {
    	Anagrafica anagrafica = praticaAnagrafica.getAnagrafica();
        DettaglioProfessionista dettaglioProfessionista = new DettaglioProfessionista();
        Anagrafica anagraficaProfessionista = anagraficheService.findAnagraficaById(anagrafica.getIdAnagrafica());
        dettaglioProfessionista.setCodiceFiscale(anagraficaProfessionista.getCodiceFiscale());
        LkTipoCollegio albo = anagrafica.getIdTipoCollegio();
//        Preconditions.checkArgument(albo != null, "Tax Register paperwork "+  praticaAnagrafica.getIdPratica()+ ": professional board not found");
        if(albo != null)
        	dettaglioProfessionista.setAlboProfessionale(albo.getCodCollegio());
        if(anagraficaProfessionista.getIdProvinciaIscrizione() != null)
        	dettaglioProfessionista.setProvinciaAlbo(anagraficaProfessionista.getIdProvinciaIscrizione().getCodCatastale());
        dettaglioProfessionista.setNumeroIscrizioneAlbo(anagraficaProfessionista.getNumeroIscrizione());
        if(praticaAnagrafica.getIdTipoQualifica() != null)
        	dettaglioProfessionista.setQualificaProfessionale(praticaAnagrafica.getIdTipoQualifica().getCodQualifica());
        return dettaglioProfessionista;
    }

    public Imprese serializeImprese(Pratica paperwork) throws Exception {
        Imprese imprese = new Imprese();
        List<PraticaAnagrafica> impreseDTO = get(paperwork, IMPRESE, false);
        if (!CollectionUtils.isEmpty(impreseDTO)) {
            for (PraticaAnagrafica dto : impreseDTO) {
                Impresa impresa = new Impresa();
                impresa.setTipoRecord("5");
                Concatenazione concatenazione = serializeConcatenazione(paperwork);
                impresa.setConcatenazione(concatenazione);
                Anagrafica anagraficaImpresa = anagraficheService.findAnagraficaById(dto.getAnagrafica().getIdAnagrafica());
                Preconditions.checkArgument(!Utils.e(anagraficaImpresa.getPartitaIva()), "Tax Register paperwork "+  paperwork.getIdPratica()+ ": Vat number not found");
                impresa.setPartitaIvaImpresa(anagraficaImpresa.getPartitaIva());
                String denominazione = anagraficaImpresa.getDenominazione();
                if (denominazione != null && denominazione.length() > 50) {
                    denominazione = denominazione.substring(0, 49);
                }
                impresa.setDenominazione(denominazione);
                Recapiti recapitoSedeLegale = anagraficheService.getRecapitoRiferimentoAnagrafica(anagraficaImpresa);
//                Preconditions.checkArgument(recapitoSedeLegale.getIdComune() != null, "Tax Register paperwork "+  paperwork.getIdPratica()+ ": enterprise legal address not found");
                if(recapitoSedeLegale != null && recapitoSedeLegale.getIdComune() != null)
                	impresa.setCodiceCatastaleComuneSedeLegale(recapitoSedeLegale.getIdComune().getCodCatastale());
                Controllo controllo = serializeControllo();
                impresa.setControllo(controllo);
                imprese.getImpresa().add(impresa);
            }
        }
        return imprese;
    }
    
    private Concatenazione serializeConcatenazione(Pratica pratica) {
        Concatenazione concatenazione = new Concatenazione();
        Anagrafica richiedente = getRichiedente(pratica);
        PraticheEventi pe = getFirstEventWithValidProtocol(pratica);
        concatenazione.setCodiceFiscaleRichiedente(richiedente.getCodiceFiscale());
        concatenazione.setNumeroProtocollo(pe.getProtocollo());
        return concatenazione;
    }

    private String serializeDate(Date date) throws ParseException {
    	DateFormat outputDateFormat = new SimpleDateFormat(AT_DATE_FORMAT);
    	String data = outputDateFormat.format(date);
    	return data;
    }

    private Controllo serializeControllo() {
        Controllo controllo = new Controllo();
        controllo.setCarattereControllo("A");
        controllo.setFiller(null);
        return controllo;
    }
    
    private List<PraticaAnagrafica> get(Pratica paperwork, Integer role, boolean mandatory){
    	List<PraticaAnagrafica> list = new ArrayList<>();
    	for(PraticaAnagrafica pa: paperwork.getPraticaAnagraficaList())
        	if(pa.getLkTipoRuolo().getIdTipoRuolo().equals(role))
        		list.add(pa);
    	if(mandatory)
    		Preconditions.checkArgument(!Utils.empty(list), "Tax Register paperwork "+  paperwork.getIdPratica()+ ": Role " + role + " not found");
		return list;
    }
    
    private Anagrafica getRichiedente(Pratica paperwork) {
    	List<PraticaAnagrafica> paperworkRequester = get(paperwork, RICHIEDENTE, true);
        Preconditions.checkNotNull(paperworkRequester.get(0), "Tax Register paperwork "+  paperwork.getIdPratica()+ ": requester not found");
        return anagraficheService.findAnagraficaById(paperworkRequester.get(0).getAnagrafica().getIdAnagrafica());
	}
    
	private PraticheEventi getFirstEventWithValidProtocol(Pratica paperwork) {
		PraticheEventi first = null;

		for(PraticheEventi praticaEvento: paperwork.getPraticheEventiList()) {
			if(praticaEvento.getIdEvento() != null 
					&& "RIC".equals(praticaEvento.getIdEvento().getCodEvento()) 
					&& !Utils.e(praticaEvento.getProtocollo())
					&& (first == null || first.getDataEvento().compareTo(praticaEvento.getDataEvento())>0)
					) 
				first = praticaEvento;
		}
		if(first == null)
			for(PraticheEventi praticaEvento: paperwork.getPraticheEventiList()) {
				if(praticaEvento.getIdEvento() != null 
						&& (first == null || first.getDataEvento().compareTo(praticaEvento.getDataEvento())>0)
						&& !Utils.e(praticaEvento.getProtocollo())) 
					first = praticaEvento;
			}
		Preconditions.checkArgument(first != null, "Tax Register paperwork "+  paperwork.getIdPratica()+ ": event not found");
		return first;
	}

	private IndirizziIntervento getFirstAdress(Pratica paperwork) {
		IndirizziIntervento first = null;
		
		for(IndirizziIntervento praticaEvento: paperwork.getIndirizziInterventoList()) {
			if(first == null || first.getIdIndirizzoIntervento().compareTo(praticaEvento.getIdIndirizzoIntervento())>0)
					 
				first = praticaEvento;
		}
//		Preconditions.checkArgument(first != null, "Tax Register paperwork "+  paperwork.getIdPratica()+ ": address not found");
		return first;
	}
}
