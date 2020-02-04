/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.anagrafetributaria.serializer;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.IndirizziIntervento;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkProvincie;
import it.wego.cross.entity.LkTipoCollegio;
import it.wego.cross.entity.LkTipoParticella;
import it.wego.cross.entity.LkTipoQualifica;
import it.wego.cross.entity.LkTipoUnita;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Recapiti;
import it.wego.cross.events.anagrafetributaria.bean.AnagrafeTributariaCommercioDTO;
import it.wego.cross.events.anagrafetributaria.bean.AnagrafeTributariaDTO;
import it.wego.cross.events.anagrafetributaria.bean.DatiCatastaliDTO;
import it.wego.cross.events.anagrafetributaria.bean.AnagraficaDTO;
import it.wego.cross.events.anagrafetributaria.bean.ImpresaDTO;
import it.wego.cross.events.anagrafetributaria.bean.IndirizzoRichiestaDTO;
import it.wego.cross.events.anagrafetributaria.service.AnagrafeTributariaService;
import it.wego.cross.service.AnagraficheService;
import it.wego.cross.service.LookupService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.utils.Utils;
import it.wego.cross.xml.anagrafetributaria.edilizia.Beneficiario;
import it.wego.cross.xml.anagrafetributaria.edilizia.Concatenazione;
import it.wego.cross.xml.anagrafetributaria.edilizia.Controllo;
import it.wego.cross.xml.anagrafetributaria.edilizia.DatoCatastale;
import it.wego.cross.xml.anagrafetributaria.edilizia.IdentificazioneRichiesta;
import it.wego.cross.xml.anagrafetributaria.edilizia.IdentificazioneRichiesta.Richiesta;
import it.wego.cross.xml.anagrafetributaria.edilizia.Impresa;
import it.wego.cross.xml.anagrafetributaria.edilizia.Professionista;
import it.wego.cross.xml.anagrafetributaria.edilizia.Professionista.DettaglioProfessionista;
import it.wego.cross.xml.anagrafetributaria.edilizia.RecordDettaglio;
import it.wego.cross.xml.anagrafetributaria.edilizia.RecordDettaglio.Beneficiari;
import it.wego.cross.xml.anagrafetributaria.edilizia.RecordDettaglio.DatiCatastali;
import it.wego.cross.xml.anagrafetributaria.edilizia.RecordDettaglio.Imprese;
import it.wego.cross.xml.anagrafetributaria.edilizia.RecordDettaglio.Professionisti;
import it.wego.cross.xml.anagrafetributaria.edilizia.Soggetto;
import it.wego.cross.xml.anagrafetributaria.edilizia.SoggettoFisico;
import it.wego.cross.xml.anagrafetributaria.edilizia.SoggettoGiuridico;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author giuseppe
 */
@Component
public class AnagraficaTributariaSerializer {

    private static final String AT_DATE_FORMAT = "ddMMyyyy";
    private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
    private static final String PERSONA_FISICA = "F";
    @Autowired
    private AnagraficheService anagraficheService;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private AnagrafeTributariaService anagrafeTributariaService;
    @Autowired
    private LookupService lookupService;

    public IdentificazioneRichiesta serializeIdentificazioneRichiesta(AnagrafeTributariaDTO anagrafeTributaria, Pratica pratica) throws Exception {
        IdentificazioneRichiesta identificazioneRichiesta = new IdentificazioneRichiesta();
        identificazioneRichiesta.setTipoRecord("1");
        AnagraficaDTO richiedenteTributaria = anagrafeTributaria.getRichiedenti().get(0);
        Integer idRichiedente = richiedenteTributaria.getIdAnagrafica();
        Anagrafica richiedente = anagraficheService.findAnagraficaById(idRichiedente);
        Preconditions.checkNotNull(anagrafeTributaria.getIndirizzo(), "Non è stato indicato un indirizzo per la pratica");
        Soggetto soggettoRichiedente = serializeSoggetto(richiedente, anagrafeTributaria.getIndirizzo().getIdRecapito());
        identificazioneRichiesta.setSoggettoRichiedente(soggettoRichiedente);
        LkTipoQualifica lkTipoQualifica = lookupService.findLkTipoQualifica(richiedenteTributaria.getIdQualifica());
        identificazioneRichiesta.setQualificaRichiedente(lkTipoQualifica.getCodQualifica());
        Richiesta richiesta = serializeRichiesta(anagrafeTributaria, pratica);
        identificazioneRichiesta.setRichiesta(richiesta);
        Controllo controllo = serializeControllo();
        identificazioneRichiesta.setControllo(controllo);
        return identificazioneRichiesta;
    }

    private Soggetto serializeSoggetto(Anagrafica richiedente, Integer indirizzo) throws ParseException {
        Soggetto soggetto = new Soggetto();
        soggetto.setCodiceFiscale(richiedente.getCodiceFiscale());
        if (String.valueOf(richiedente.getTipoAnagrafica()).equals(PERSONA_FISICA)) {
            SoggettoFisico soggettoFisico = new SoggettoFisico();
            soggettoFisico.setCodiceFiscale(richiedente.getCodiceFiscale());
            soggettoFisico.setCognome(richiedente.getCognome());
            soggettoFisico.setNome(richiedente.getNome());
            soggettoFisico.setSesso(String.valueOf(richiedente.getSesso()).toUpperCase());
            String dataNascita = serializeDate(richiedente.getDataNascita());
            soggettoFisico.setDataNascita(dataNascita);
            String codCatastaleComuneNascita = richiedente.getIdComuneNascita().getCodCatastale();
            soggettoFisico.setCodiceCatastaleComuneNascita(codCatastaleComuneNascita);
            soggetto.setSoggettoFisico(soggettoFisico);
        } else {
            SoggettoGiuridico soggettoGiuridico = new SoggettoGiuridico();
            String denominazione = richiedente.getDenominazione();
            if (denominazione.length() > 60) {
                denominazione = denominazione.substring(0, 59);
            }
            soggettoGiuridico.setDenominazione(denominazione);
            if (indirizzo != null) {
                Recapiti sedeLegale = anagraficheService.findRecapitoById(indirizzo);
                soggettoGiuridico.setCodiceCatastaleSedeLegale(sedeLegale.getIdComune().getCodCatastale());
            }
            soggetto.setSoggettoGiuridico(soggettoGiuridico);
        }
        return soggetto;
    }

    private Richiesta serializeRichiesta(AnagrafeTributariaDTO anagrafeTributaria, Pratica pratica) throws ParseException {
        Richiesta richiesta = new Richiesta();
        richiesta.setTipoRichiesta(anagrafeTributaria.getTipoRichiesta());
        richiesta.setTipologiaIntervento(anagrafeTributaria.getTipoIntervento());
        String numeroProtocollo = pratica.getCodRegistro() + "/" + pratica.getAnnoRiferimento() + "/" + pratica.getProtocollo();
        richiesta.setNumeroProtocollo(numeroProtocollo);
        richiesta.setTipologiaRichiesta(anagrafeTributaria.getTipologiaRichiesta());
        String dataPresentazione = serializeDate(pratica.getDataRicezione());
        richiesta.setDataPresentazioneRichiesta(dataPresentazione);
        if (!Utils.e(anagrafeTributaria.getDataInizioLavori())) {
            String dataInizioLavori = serializeDate(anagrafeTributaria.getDataInizioLavori());
            richiesta.setDataInizioLavori(dataInizioLavori);
        }
        if (!Utils.e(anagrafeTributaria.getDataFineLavori())) {
            String dataFineLavori = serializeDate(anagrafeTributaria.getDataFineLavori());
            richiesta.setDataFineLavori(dataFineLavori);
        }
        if (anagrafeTributaria.getIndirizzo() != null) {
            IndirizziIntervento indirizzo = praticheService.findIndirizziInterventoById(anagrafeTributaria.getIndirizzo().getIdRecapito());
            String indirizzoOggettoIstanza = indirizzo.getIndirizzo() + " " + indirizzo.getCivico();
            if (indirizzoOggettoIstanza.length() > 35) {
                indirizzoOggettoIstanza = indirizzoOggettoIstanza.substring(0, 34);
            }
            richiesta.setIndirizzoOggettoIstanza(indirizzoOggettoIstanza);
            richiesta.setIdIndirizzoOggettoIstanza(String.valueOf(indirizzo.getIdIndirizzoIntervento()));
        }

        return richiesta;
    }

    public Beneficiari serializeBeneficiari(AnagrafeTributariaDTO anagrafeTributaria, Pratica pratica) throws ParseException, Exception {
        Beneficiari beneficiari = new Beneficiari();
        List<AnagraficaDTO> beneficiariDTO = anagrafeTributaria.getBeneficiari();
        for (AnagraficaDTO dto : beneficiariDTO) {
            Beneficiario beneficiario = new Beneficiario();
            beneficiario.setTipoRecord("2");
            Concatenazione concatenazione = serializeConcatenazione(anagrafeTributaria, pratica);
            beneficiario.setConcatenazione(concatenazione);
            Anagrafica anagraficaBeneficiario = anagraficheService.findAnagraficaById(dto.getIdAnagrafica());
//            List<AnagraficaRecapiti> sedi = anagrafeTributariaService.findRecapitiByTipologia(anagraficaBeneficiario, "SED");
            Recapiti recapito = anagraficheService.getRecapitoRiferimentoAnagrafica(anagraficaBeneficiario);
            Integer idRecapitoSede = null;
            if (recapito != null) {
                idRecapitoSede = recapito.getIdRecapito();
            }
            Soggetto soggetto = serializeSoggetto(anagraficaBeneficiario, idRecapitoSede);
            beneficiario.setSoggettoBeneficiario(soggetto);
            LkTipoQualifica lkTipoQualifica = lookupService.findLkTipoQualifica(dto.getIdQualifica());
            beneficiario.setQualificaBeneficiario(lkTipoQualifica.getCodQualifica());
            Controllo controllo = serializeControllo();
            beneficiario.setControllo(controllo);
            beneficiari.getBeneficiario().add(beneficiario);
        }
        return beneficiari;
    }

    public DatiCatastali serializeDatiCatastali(AnagrafeTributariaDTO anagrafeTributaria, Pratica pratica) {
        DatiCatastali datiCatastali = new DatiCatastali();
        List<DatiCatastaliDTO> datiCatastaliDTO = anagrafeTributaria.getDatiCatastali();
        for (DatiCatastaliDTO dto : datiCatastaliDTO) {
            DatoCatastale datoCatastale = new DatoCatastale();
            datoCatastale.setTipoRecord("3");
            Concatenazione concatenazione = serializeConcatenazione(anagrafeTributaria, pratica);
            datoCatastale.setConcatenazione(concatenazione);
            if (dto.getIdTipoUnita() != null) {
                LkTipoUnita tipoUnita = lookupService.findTipoUnitaById(dto.getIdTipoUnita());
                datoCatastale.setTipoUnita(tipoUnita.getCodTipoUnita());
            }
            datoCatastale.setSezione(dto.getSezione());
            datoCatastale.setFoglio(dto.getFoglio());
            datoCatastale.setParticella(dto.getParticella());
            datoCatastale.setEstensioneParticella(dto.getEstensioneParticella());
            if (dto.getIdTipologiaParticella() != null) {
                LkTipoParticella tipoParticella = lookupService.findTipoParticellaById(dto.getIdTipologiaParticella());
                datoCatastale.setTipoParticella(tipoParticella.getCodTipoParticella());
            }
            datoCatastale.setSubalterno(dto.getSubalterno());
            datoCatastale.setTipoCatasto(dto.getDesTipoCatasto());
            Controllo controllo = serializeControllo();
            datoCatastale.setControllo(controllo);
            datiCatastali.getDatoCatastale().add(datoCatastale);
        }

        return datiCatastali;
    }

    public Professionisti serializeProfessionisti(AnagrafeTributariaDTO anagrafeTributaria, Pratica pratica) {
        Professionisti professionisti = new Professionisti();
        List<AnagraficaDTO> professionistiDTO = anagrafeTributaria.getProfessionisti();
        for (AnagraficaDTO dto : professionistiDTO) {
            Professionista professionista = new Professionista();
            professionista.setTipoRecord("4");
            Concatenazione concatenazione = serializeConcatenazione(anagrafeTributaria, pratica);
            professionista.setConcatenazione(concatenazione);
            DettaglioProfessionista dettaglioProfessionista = serializeDettaglioProfessionista(dto);
            professionista.setDettaglioProfessionista(dettaglioProfessionista);
            Controllo controllo = serializeControllo();
            professionista.setControllo(controllo);
            professionisti.getProfessionista().add(professionista);
        }
        return professionisti;
    }

    private DettaglioProfessionista serializeDettaglioProfessionista(AnagraficaDTO dto) {
        DettaglioProfessionista dettaglioProfessionista = new DettaglioProfessionista();
        Anagrafica anagraficaProfessionista = anagraficheService.findAnagraficaById(dto.getIdAnagrafica());
        dettaglioProfessionista.setCodiceFiscale(anagraficaProfessionista.getCodiceFiscale());
        LkTipoCollegio albo = lookupService.findLkTipoCollegioById(dto.getIdTipoCollegio());
        dettaglioProfessionista.setAlboProfessionale(albo.getCodCollegio());
        dettaglioProfessionista.setProvinciaAlbo(dto.getCodProvinciaIscrizione());
        dettaglioProfessionista.setNumeroIscrizioneAlbo(dto.getNumeroIscrizioneAlbo());
        LkTipoQualifica lkTipoQualifica = lookupService.findLkTipoQualifica(dto.getIdQualifica());
        dettaglioProfessionista.setQualificaProfessionale(lkTipoQualifica.getCodQualifica());
        return dettaglioProfessionista;
    }

    public Imprese serializeImprese(AnagrafeTributariaDTO anagrafeTributaria, Pratica pratica) throws Exception {
        Imprese imprese = new Imprese();
        List<ImpresaDTO> impreseDTO = anagrafeTributaria.getImprese();
        if (!CollectionUtils.isEmpty(impreseDTO)) {
            for (ImpresaDTO dto : impreseDTO) {
                Impresa impresa = new Impresa();
                impresa.setTipoRecord("5");
                Concatenazione concatenazione = serializeConcatenazione(anagrafeTributaria, pratica);
                impresa.setConcatenazione(concatenazione);
                Anagrafica anagraficaImpresa = anagraficheService.findAnagraficaById(dto.getIdAnagrafica());
                impresa.setPartitaIvaImpresa(anagraficaImpresa.getPartitaIva());
                String denominazione = anagraficaImpresa.getDenominazione();
                if (denominazione.length() > 50) {
                    denominazione = denominazione.substring(0, 49);
                }
                impresa.setDenominazione(denominazione);
                Recapiti recapitoSedeLegale = anagraficheService.getRecapitoRiferimentoAnagrafica(anagraficaImpresa);
                impresa.setCodiceCatastaleComuneSedeLegale(recapitoSedeLegale.getIdComune().getCodCatastale());
                Controllo controllo = serializeControllo();
                impresa.setControllo(controllo);
                imprese.getImpresa().add(impresa);
            }
        }
        return imprese;
    }

    private String serializeDate(String date) throws ParseException {
        DateFormat inputDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        Date dataInput = inputDateFormat.parse(date);
        String data = serializeDate(dataInput);
        return data;
    }

    private String serializeDate(Date date) throws ParseException {
        DateFormat outputDateFormat = new SimpleDateFormat(AT_DATE_FORMAT);
        String data = outputDateFormat.format(date);
        return data;
    }

    private Concatenazione serializeConcatenazione(AnagrafeTributariaDTO anagrafeTributaria, Pratica pratica) {
        Concatenazione concatenazione = new Concatenazione();
        AnagraficaDTO anagrafica = anagrafeTributaria.getRichiedenti().get(0);
        Anagrafica richiedente = anagraficheService.findAnagraficaById(anagrafica.getIdAnagrafica());
        concatenazione.setCodiceFiscaleRichiedente(richiedente.getCodiceFiscale());
        String numeroProtocollo = pratica.getCodRegistro() + "/" + pratica.getAnnoRiferimento() + "/" + pratica.getProtocollo();
        concatenazione.setNumeroProtocollo(numeroProtocollo);
        return concatenazione;
    }

    private Controllo serializeControllo() {
        Controllo controllo = new Controllo();
        controllo.setCarattereControllo("A");
        controllo.setFiller(null);
        return controllo;
    }

    public AnagrafeTributariaDTO serializeAnagrafeTributaria(RecordDettaglio recordDettaglio, Pratica pratica) throws ParseException, Exception {
        AnagrafeTributariaDTO anagrafeTributaria = new AnagrafeTributariaDTO();
        if ((recordDettaglio.getIdentificazioneRichiesta() != null)) {
            if (recordDettaglio.getIdentificazioneRichiesta().getSoggettoRichiedente() != null
                    && recordDettaglio.getIdentificazioneRichiesta().getSoggettoRichiedente().getCodiceFiscale() != null
                    && !recordDettaglio.getIdentificazioneRichiesta().getSoggettoRichiedente().getCodiceFiscale().equals("")) {
                String codiceFiscaleRichiedente = recordDettaglio.getIdentificazioneRichiesta().getSoggettoRichiedente().getCodiceFiscale();
                Anagrafica anagraficaRichiedente = anagraficheService.findAnagraficaByCodFiscale(codiceFiscaleRichiedente);
                AnagraficaDTO richiedente = new AnagraficaDTO();
                richiedente.setIdAnagrafica(anagraficaRichiedente.getIdAnagrafica());
                String descrizione;
                if ("F".equals(String.valueOf(anagraficaRichiedente.getTipoAnagrafica()))) {
                    descrizione = anagraficaRichiedente.getNome() + " " + anagraficaRichiedente.getCognome();
                } else {
                    descrizione = anagraficaRichiedente.getDenominazione();
                }
                richiedente.setDescrizione(descrizione);

                List<AnagraficaDTO> richiedentiList = Lists.newArrayList(richiedente);
                anagrafeTributaria.setRichiedenti(richiedentiList);
            }
            if (recordDettaglio.getIdentificazioneRichiesta().getRichiesta() != null) {
                if (!Strings.isNullOrEmpty(recordDettaglio.getIdentificazioneRichiesta().getRichiesta().getIdIndirizzoOggettoIstanza())) {
                    Integer idIndirizzo = Integer.valueOf(recordDettaglio.getIdentificazioneRichiesta().getRichiesta().getIdIndirizzoOggettoIstanza());
                    IndirizziIntervento indirizzo = praticheService.findIndirizziInterventoById(idIndirizzo);
                    IndirizzoRichiestaDTO dto = new IndirizzoRichiestaDTO();
                    dto.setIdRecapito(idIndirizzo);
                    String indirizzoOggettoIstanza = indirizzo.getIndirizzo() + " " + indirizzo.getCivico();
                    dto.setDescrizione(indirizzoOggettoIstanza);
                    anagrafeTributaria.setIndirizzo(dto);
                }
                anagrafeTributaria.setTipoRichiesta(recordDettaglio.getIdentificazioneRichiesta().getRichiesta().getTipoRichiesta());
                anagrafeTributaria.setTipoIntervento(recordDettaglio.getIdentificazioneRichiesta().getRichiesta().getTipologiaIntervento());
                anagrafeTributaria.setTipologiaRichiesta(recordDettaglio.getIdentificazioneRichiesta().getRichiesta().getTipologiaRichiesta());
                if (recordDettaglio.getIdentificazioneRichiesta().getRichiesta().getDataInizioLavori() != null
                        && !"".equals(recordDettaglio.getIdentificazioneRichiesta().getRichiesta().getDataInizioLavori())) {
                    DateFormat atFormat = new SimpleDateFormat(AT_DATE_FORMAT);
                    Date dataInizioLavori = atFormat.parse(recordDettaglio.getIdentificazioneRichiesta().getRichiesta().getDataInizioLavori());
                    DateFormat defaultFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
                    String dataInizioLavoriString = defaultFormat.format(dataInizioLavori);
                    anagrafeTributaria.setDataInizioLavori(dataInizioLavoriString);
                }
                if (recordDettaglio.getIdentificazioneRichiesta().getRichiesta().getDataFineLavori() != null
                        && !"".equals(recordDettaglio.getIdentificazioneRichiesta().getRichiesta().getDataFineLavori())) {
                    DateFormat atFormat = new SimpleDateFormat(AT_DATE_FORMAT);
                    Date dataFineLavori = atFormat.parse(recordDettaglio.getIdentificazioneRichiesta().getRichiesta().getDataFineLavori());
                    DateFormat defaultFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
                    String dataFineLavoriString = defaultFormat.format(dataFineLavori);
                    anagrafeTributaria.setDataFineLavori(dataFineLavoriString);
                }
            }
        }
        List<DatiCatastaliDTO> datiCatastali = new ArrayList<DatiCatastaliDTO>();
        if (recordDettaglio.getDatiCatastali() != null && recordDettaglio.getDatiCatastali().getDatoCatastale() != null
                && !recordDettaglio.getDatiCatastali().getDatoCatastale().isEmpty()) {
            for (DatoCatastale datoCatastale : recordDettaglio.getDatiCatastali().getDatoCatastale()) {
                DatiCatastaliDTO dto = new DatiCatastaliDTO();
                if (!Strings.isNullOrEmpty(datoCatastale.getTipoUnita())) {
                    LkTipoUnita tipoUnita = anagrafeTributariaService.findLkTipoUnitaByCod(datoCatastale.getTipoUnita());
                    dto.setIdTipoUnita(tipoUnita.getIdTipoUnita());
                }
                dto.setSezione(datoCatastale.getSezione());
                dto.setFoglio(datoCatastale.getFoglio());
                dto.setParticella(datoCatastale.getParticella());
                dto.setEstensioneParticella(datoCatastale.getEstensioneParticella());
                if (!Strings.isNullOrEmpty(datoCatastale.getTipoParticella())) {
                    LkTipoParticella tipoParticella = lookupService.findTipoParticellaByCodice(datoCatastale.getTipoParticella());
                    dto.setIdTipologiaParticella(tipoParticella.getIdTipoParticella());
                }
                dto.setSubalterno(datoCatastale.getSubalterno());
                dto.setDesTipoCatasto(datoCatastale.getTipoCatasto());
                datiCatastali.add(dto);
            }
        } else {
            DatiCatastaliDTO dto = new DatiCatastaliDTO();
            datiCatastali.add(dto);
        }
        anagrafeTributaria.setDatiCatastali(datiCatastali);

        List<AnagraficaDTO> beneficiari = new ArrayList<AnagraficaDTO>();
        if (recordDettaglio.getBeneficiari() != null && !recordDettaglio.getBeneficiari().getBeneficiario().isEmpty()) {
            for (Beneficiario beneficiario : recordDettaglio.getBeneficiari().getBeneficiario()) {
                AnagraficaDTO beneficiarioDto = new AnagraficaDTO();
                String codiceFiscaleRichiedente = beneficiario.getSoggettoBeneficiario().getCodiceFiscale();
                Anagrafica anagraficaRichiedente = anagraficheService.findAnagraficaByCodFiscale(codiceFiscaleRichiedente);
                beneficiarioDto.setIdAnagrafica(anagraficaRichiedente.getIdAnagrafica());
                LkTipoQualifica qualifica = lookupService.findLkTipoQualificaByCodQualificaAndCondizione(beneficiario.getQualificaBeneficiario(), "RICHIEDENTE");
                beneficiarioDto.setIdQualifica(qualifica.getIdTipoQualifica());
                Soggetto soggettoBeneficiario = beneficiario.getSoggettoBeneficiario();
                if (soggettoBeneficiario.getSoggettoFisico() != null) {
                    String descrizione = anagraficaRichiedente.getCognome() + " " + anagraficaRichiedente.getNome() + " (" + anagraficaRichiedente.getCodiceFiscale() + ")";
                    beneficiarioDto.setDescrizione(descrizione);
                } else {
                    String descrizione = anagraficaRichiedente.getDenominazione() + " (" + anagraficaRichiedente.getCodiceFiscale() + ")";
                    beneficiarioDto.setDescrizione(descrizione);
                }
                beneficiari.add(beneficiarioDto);
            }
        } else {
            AnagraficaDTO beneficiarioDto = new AnagraficaDTO();
            beneficiari.add(beneficiarioDto);
        }
        anagrafeTributaria.setBeneficiari(beneficiari);

        List<AnagraficaDTO> professionisti = new ArrayList<AnagraficaDTO>();
        if (recordDettaglio.getProfessionisti() != null && !recordDettaglio.getProfessionisti().getProfessionista().isEmpty()) {
            for (Professionista professionista : recordDettaglio.getProfessionisti().getProfessionista()) {
                AnagraficaDTO anagrafeProfessionistaDto = new AnagraficaDTO();
                if (professionista.getDettaglioProfessionista() != null) {
                    String codiceFiscaleProfessionista = professionista.getDettaglioProfessionista().getCodiceFiscale();
                    Anagrafica anagraficaProfessionista = anagraficheService.findAnagraficaByCodFiscale(codiceFiscaleProfessionista);
                    anagrafeProfessionistaDto.setIdAnagrafica(anagraficaProfessionista.getIdAnagrafica());
                    LkTipoCollegio albo = lookupService.findLkTipoCollegioByCodCollegio(professionista.getDettaglioProfessionista().getAlboProfessionale());
                    anagrafeProfessionistaDto.setIdTipoCollegio(albo.getIdTipoCollegio());
                    anagrafeProfessionistaDto.setCodProvinciaIscrizione(professionista.getDettaglioProfessionista().getProvinciaAlbo());
                    anagrafeProfessionistaDto.setNumeroIscrizioneAlbo(professionista.getDettaglioProfessionista().getNumeroIscrizioneAlbo());
                    LkTipoQualifica qualifica = lookupService.findLkTipoQualificaByCodQualificaAndCondizione(professionista.getDettaglioProfessionista().getQualificaProfessionale(), "TECNICO");
                    anagrafeProfessionistaDto.setIdQualifica(qualifica.getIdTipoQualifica());
                    String descrizione = anagraficaProfessionista.getCognome() + " " + anagraficaProfessionista.getNome() + " (" + anagraficaProfessionista.getCodiceFiscale() + ")";
                    anagrafeProfessionistaDto.setDescrizione(descrizione);
                }
                professionisti.add(anagrafeProfessionistaDto);
            }
        } else {
            AnagraficaDTO anagrafeProfessionistaDto = new AnagraficaDTO();
            professionisti.add(anagrafeProfessionistaDto);
        }
        anagrafeTributaria.setProfessionisti(professionisti);

        if (recordDettaglio.getImprese() != null && !recordDettaglio.getImprese().getImpresa().isEmpty()) {
            List<ImpresaDTO> imprese = new ArrayList<ImpresaDTO>();
            for (Impresa impresa : recordDettaglio.getImprese().getImpresa()) {
                ImpresaDTO anagrafeImpresa = new ImpresaDTO();
                String partitaIva = impresa.getPartitaIvaImpresa();
                Anagrafica anagraficaImprese = anagraficheService.findAnagraficaByPartitaIva(partitaIva);
                anagrafeImpresa.setIdAnagrafica(anagraficaImprese.getIdAnagrafica());
                String descrizione = impresa.getDenominazione() + " (" + impresa.getPartitaIvaImpresa() + ")";
                Recapiti recapito = anagraficheService.getRecapitoRiferimentoAnagrafica(anagraficaImprese);
                String descrizioneRecapito = recapito.getIndirizzo() + " " + recapito.getNCivico() + " - " + recapito.getCap() + " " + pratica.getIdComune().getDescrizione() + (!Strings.isNullOrEmpty(recapito.getLocalita()) ? "(" + recapito.getLocalita() + ")" : "");
                anagrafeImpresa.setDescrizione(descrizione);
                anagrafeImpresa.setIdRecapito(recapito.getIdRecapito());
                anagrafeImpresa.setDescrizioneRecapito(descrizioneRecapito);
                imprese.add(anagrafeImpresa);
            }
            anagrafeTributaria.setImprese(imprese);
        }

        return anagrafeTributaria;
    }

    public AnagrafeTributariaCommercioDTO serializeAnagrafeTributariaCommercio(it.wego.cross.xml.anagrafetributaria.commercio.RecordDettaglio recordDettaglio) throws ParseException {
        AnagrafeTributariaCommercioDTO dto = new AnagrafeTributariaCommercioDTO();
        Anagrafica anagraficaRichiedente = anagraficheService.findAnagraficaByCodFiscale(recordDettaglio.getCodiceFiscale());
        dto.setIdSoggetto(anagraficaRichiedente.getIdAnagrafica());
        dto.setCodProvvedimento(recordDettaglio.getCodiceProvvedimento());

        if (!Utils.e(recordDettaglio.getDataFineProvvedimento())) {
            DateFormat atFormat = new SimpleDateFormat(AT_DATE_FORMAT);
            Date dataFineProvvedimento = atFormat.parse(recordDettaglio.getDataFineProvvedimento());
            DateFormat defaultFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            String dataFineProvvedimentoString = defaultFormat.format(dataFineProvvedimento);
            dto.setFineProvvedimento(dataFineProvvedimentoString);
        }

        if (!Utils.e(recordDettaglio.getDataInizioProvvedimento())) {
            DateFormat atFormat = new SimpleDateFormat(AT_DATE_FORMAT);
            Date dataInizioProvvedimento = atFormat.parse(recordDettaglio.getDataInizioProvvedimento());
            DateFormat defaultFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            String dataInizioProvvedimentoString = defaultFormat.format(dataInizioProvvedimento);
            dto.setInizioProvvedimento(dataInizioProvvedimentoString);
        }
        return dto;
    }

    public it.wego.cross.xml.anagrafetributaria.commercio.RecordDettaglio serializeRecordDettaglioCommercio(AnagrafeTributariaCommercioDTO anagrafeTributaria, Pratica pratica) throws ParseException {
        it.wego.cross.xml.anagrafetributaria.commercio.RecordDettaglio record = new it.wego.cross.xml.anagrafetributaria.commercio.RecordDettaglio();
        record.setTipoRecord("1");
        Anagrafica richiedente = anagraficheService.findAnagraficaById(anagrafeTributaria.getIdSoggetto());
        record.setCodiceFiscale(richiedente.getCodiceFiscale());
        record.setCognome(Utils.truncate(richiedente.getCognome(), 26));
        record.setNome(Utils.truncate(richiedente.getNome(), 25));
        record.setSesso(String.valueOf(richiedente.getSesso()));
        DateFormat atFormat = new SimpleDateFormat(AT_DATE_FORMAT);
        if (richiedente.getDataNascita() != null) {
            String dataNascita = atFormat.format(richiedente.getDataNascita());
            record.setDataNascita(dataNascita);
        }
        record.setDenominazione(Utils.truncate(richiedente.getDenominazione(), 60));
        if (richiedente.getIdComuneNascita() != null) {
            LkComuni comuneNascita = richiedente.getIdComuneNascita();
            LkProvincie provincia = comuneNascita.getIdProvincia();
            record.setComune(Utils.truncate(comuneNascita.getDescrizione(), 35));
            record.setProvincia(Utils.truncate(provincia.getCodCatastale(), 2));
        } else {
            LkComuni comuneSede = anagrafeTributariaService.findComuneSede(anagrafeTributaria.getIdSoggetto());
            LkProvincie provincia = comuneSede.getIdProvincia();
            record.setComune(Utils.truncate(comuneSede.getDescrizione(), 35));
            record.setProvincia(Utils.truncate(provincia.getCodCatastale(), 2));
        }
        record.setCodiceProvvedimento(anagrafeTributaria.getCodProvvedimento());
        record.setNumeroProvvedimento(String.valueOf(pratica.getIdPratica()));

        if (!Utils.e(anagrafeTributaria.getInizioProvvedimento())) {
            DateFormat defaultFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            Date inizioProvvedimento = defaultFormat.parse(anagrafeTributaria.getInizioProvvedimento());
            String inizioProvvedimentoString = atFormat.format(inizioProvvedimento);
            record.setDataInizioProvvedimento(inizioProvvedimentoString);
        }

        if (!Utils.e(anagrafeTributaria.getFineProvvedimento())) {
            DateFormat defaultFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            Date fineProvvedimento = defaultFormat.parse(anagrafeTributaria.getFineProvvedimento());
            String fineProvvedimentoString = atFormat.format(fineProvvedimento);
            record.setDataFineProvvedimento(fineProvvedimentoString);
        }

        record.setFiller(null);
        return record;
    }
}
