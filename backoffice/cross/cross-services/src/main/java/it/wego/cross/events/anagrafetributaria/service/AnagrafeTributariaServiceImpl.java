/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.anagrafetributaria.service;

import com.google.common.base.Strings;
import it.wego.cross.beans.layout.Select;
import it.wego.cross.dao.EntiDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.AnagraficaRecapiti;
import it.wego.cross.entity.DatiCatastali;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.IndirizziIntervento;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkProvincie;
import it.wego.cross.entity.LkTipoCollegio;
import it.wego.cross.entity.LkTipoIndirizzo;
import it.wego.cross.entity.LkTipoParticella;
import it.wego.cross.entity.LkTipoQualifica;
import it.wego.cross.entity.LkTipoRuolo;
import it.wego.cross.entity.LkTipoSistemaCatastale;
import it.wego.cross.entity.LkTipoUnita;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.Recapiti;
import it.wego.cross.events.anagrafetributaria.bean.AnagraficaDTO;
import it.wego.cross.events.anagrafetributaria.bean.DatiCatastaliDTO;
import it.wego.cross.events.anagrafetributaria.bean.ImpresaDTO;
import it.wego.cross.events.anagrafetributaria.bean.IndirizzoRichiestaDTO;
import it.wego.cross.events.anagrafetributaria.bean.QualificaLookupDTO;
import it.wego.cross.events.anagrafetributaria.dao.AnagrafeTributariaDao;
import it.wego.cross.events.anagrafetributaria.entity.AtRecordDettaglio;
import it.wego.cross.service.AnagraficheService;
import it.wego.cross.utils.Utils;
import it.wego.cross.xml.anagrafetributaria.commercio.AnagrafeTributariaCommercio;
import it.wego.cross.xml.anagrafetributaria.edilizia.AnagrafeTributariaEdilizia;
import it.wego.cross.xml.anagrafetributaria.edilizia.Beneficiario;
import it.wego.cross.xml.anagrafetributaria.edilizia.DatoCatastale;
import it.wego.cross.xml.anagrafetributaria.edilizia.IdentificazioneRichiesta;
import it.wego.cross.xml.anagrafetributaria.edilizia.IdentificazioneRichiesta.Richiesta;
import it.wego.cross.xml.anagrafetributaria.edilizia.Impresa;
import it.wego.cross.xml.anagrafetributaria.edilizia.Professionista;
import it.wego.cross.xml.anagrafetributaria.edilizia.RecordControllo;
import it.wego.cross.xml.anagrafetributaria.edilizia.RecordDettaglio;
import it.wego.cross.xml.anagrafetributaria.edilizia.Soggetto;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gabriele
 */
@Service
public class AnagrafeTributariaServiceImpl implements AnagrafeTributariaService {

    @Autowired
    private AnagrafeTributariaDao anagrafeTributariaDao;
    @Autowired
    private AnagraficheService anagraficheService;
    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private EntiDao entiDao;

    @Override
    public String getDocumentoAnagrafeTributariaCommercio(AnagrafeTributariaCommercio anagrafeTributariaCommercio) throws Exception {
        StringBuilder sb = new StringBuilder();

        anagrafeTributariaCommercio.getRecordTesta().setTipoRecord("0");
        anagrafeTributariaCommercio.getRecordCoda().setTipoRecord("9");

        sb.append(componiRecordControlloCommercio(anagrafeTributariaCommercio.getRecordTesta(), false));
        if (anagrafeTributariaCommercio.getRecordsDettaglio() == null || anagrafeTributariaCommercio.getRecordsDettaglio().getRecordDettaglio() == null || anagrafeTributariaCommercio.getRecordsDettaglio().getRecordDettaglio().isEmpty()) {
            throw new Exception("E' obbligatorio avere almeno un record");
        }
        int counter = 1;
        for (it.wego.cross.xml.anagrafetributaria.commercio.RecordDettaglio recordDettaglio : anagrafeTributariaCommercio.getRecordsDettaglio().getRecordDettaglio()) {
            sb.append(componiRecordDettaglioCommercio(recordDettaglio, counter));
            counter = counter + 1;
        }
        sb.append(componiRecordControlloCommercio(anagrafeTributariaCommercio.getRecordCoda(), true));

        String documentoAnagrafeTributaria = sb.toString();
        return documentoAnagrafeTributaria;
    }

    @Override
    public String getDocumentoAnagrafeTributariaEdilizia(AnagrafeTributariaEdilizia anagrafeTributaria) throws Exception {
        StringBuilder sb = new StringBuilder();

        anagrafeTributaria.getRecordTesta().setTipoRecord("0");
        anagrafeTributaria.getRecordCoda().setTipoRecord("9");

        sb.append(componiRecordControlloEdilizia(anagrafeTributaria.getRecordTesta()));
        if (anagrafeTributaria.getRecordsDettaglio() == null || anagrafeTributaria.getRecordsDettaglio().getRecordDettaglio() == null || anagrafeTributaria.getRecordsDettaglio().getRecordDettaglio().isEmpty()) {
            throw new Exception("E' obbligatorio avere almeno un record");
        }
        for (RecordDettaglio recordDettaglio : anagrafeTributaria.getRecordsDettaglio().getRecordDettaglio()) {
            sb.append(componiRecordDettaglioEdilizia(recordDettaglio));
        }
        sb.append(componiRecordControlloEdilizia(anagrafeTributaria.getRecordCoda()));

        String documentoAnagrafeTributaria = sb.toString();
        return documentoAnagrafeTributaria;
    }

    private String componiRecordDettaglioCommercio(it.wego.cross.xml.anagrafetributaria.commercio.RecordDettaglio recordDettaglio, int progressivoRecord) {
        StringBuilder sb = new StringBuilder();
        sb.append(ess(Utils.trim(recordDettaglio.getTipoRecord()), 1));
        sb.append(ess(Utils.trim(recordDettaglio.getCodiceFiscale()), 16, true));
        if (!Strings.isNullOrEmpty(recordDettaglio.getDataNascita())) {
            //E' una persona fisica
            sb.append(ess(Utils.trim(recordDettaglio.getCognome()), 26));
            sb.append(ess(Utils.trim(recordDettaglio.getNome()), 25));
            sb.append(ess(Utils.trim(recordDettaglio.getSesso()), 1));
            sb.append(ess(Utils.trim(recordDettaglio.getDataNascita()), 8, true));
        } else {
            //E' una persona giuridica
            sb.append(ess(Utils.trim(recordDettaglio.getDenominazione()), 60));
        }
        sb.append(ess(Utils.trim(recordDettaglio.getComune()), 35));
        sb.append(ess(Utils.trim(recordDettaglio.getProvincia()), 2));
        sb.append(ess(recordDettaglio.getCodiceProvvedimento(), 2));
        sb.append(ess(recordDettaglio.getNumeroProvvedimento(), 16, true));
        sb.append(ess(recordDettaglio.getDataInizioProvvedimento(), 8, true));
        sb.append(ess(recordDettaglio.getDataFineProvvedimento(), 8, true));
        sb.append(ess(recordDettaglio.getProgressivoInvio(), 7, true));
        sb.append(ess(String.valueOf(progressivoRecord), 6, true));
        sb.append(ess(recordDettaglio.getFiller(), 38));
        sb.append("\n");
        return sb.toString();
    }

    private String componiRecordDettaglioEdilizia(RecordDettaglio recordDettaglio) throws Exception {
        StringBuilder sb = new StringBuilder();

        sb.append(componiIdentificazioneRichiesta(recordDettaglio.getIdentificazioneRichiesta()));
        if (recordDettaglio.getBeneficiari() != null && recordDettaglio.getBeneficiari().getBeneficiario() != null && !recordDettaglio.getBeneficiari().getBeneficiario().isEmpty()) {
            for (Beneficiario beneficiario : recordDettaglio.getBeneficiari().getBeneficiario()) {
                sb.append(componiBeneficiario(beneficiario));
            }
        }
        if (recordDettaglio.getDatiCatastali() != null && recordDettaglio.getDatiCatastali().getDatoCatastale() != null && !recordDettaglio.getDatiCatastali().getDatoCatastale().isEmpty()) {
            for (DatoCatastale datoCatastale : recordDettaglio.getDatiCatastali().getDatoCatastale()) {
                sb.append(componiDatiCatastali(datoCatastale));
            }
        }

        if (recordDettaglio.getProfessionisti() != null && recordDettaglio.getProfessionisti().getProfessionista() != null && !recordDettaglio.getProfessionisti().getProfessionista().isEmpty()) {
            for (Professionista professionista : recordDettaglio.getProfessionisti().getProfessionista()) {
                sb.append(componiProfessionista(professionista));
            }
        }

        if (recordDettaglio.getImprese() != null && recordDettaglio.getImprese().getImpresa() != null && !recordDettaglio.getImprese().getImpresa().isEmpty()) {
            for (Impresa impresa : recordDettaglio.getImprese().getImpresa()) {
                sb.append(componiImpresa(impresa));
            }
        }

        return sb.toString();
    }

    private String componiIdentificazioneRichiesta(IdentificazioneRichiesta identificazioneRichiesta) throws Exception {
        IdentificazioneRichiesta ir = identificazioneRichiesta;
        if (ir == null) {
            throw new Exception("L'identificazioneRichiesta è obbligatoria");
        }

        StringBuilder sb = new StringBuilder();
        sb.append(ir.getTipoRecord() != null ? ir.getTipoRecord() : 1);
        sb.append(getSoggetto(ir.getSoggettoRichiedente()));
        sb.append(ir.getQualificaRichiedente() != null ? ir.getQualificaRichiedente() : ess("", 1));
        if (ir.getRichiesta() == null) {
            //tipoRichiesta
            sb.append(ess("", 1));
            //tipologiaIntervento
            sb.append(ess("", 1));
            //numeroProtocollo
            sb.append(ess("", 20));
            //tipologiaRichiesta
            sb.append(ess("", 1));
            //dataPresentazione
            sb.append(ess("", 8));
            //dataInizioLavori
            sb.append(ess("", 8));
            //dataFineLavori
            sb.append(ess("", 8));
            //indirizzoOggettoIstanza
            sb.append(ess("", 35));
        } else {
            Richiesta r = ir.getRichiesta();
            sb.append(r.getTipoRichiesta() != null ? ess(r.getTipoRichiesta(), 1) : ess("", 1));
            sb.append(r.getTipologiaIntervento() != null ? ess(r.getTipologiaIntervento(), 1) : ess("", 1));
            sb.append(r.getNumeroProtocollo() != null ? ess(r.getNumeroProtocollo(), 20) : ess("", 20));
            sb.append(r.getTipologiaRichiesta() != null ? ess(r.getTipologiaRichiesta(), 1) : ess("", 1));
            sb.append(r.getDataPresentazioneRichiesta() != null ? ess(r.getDataPresentazioneRichiesta(), 8) : ess("", 8));
            sb.append(r.getDataInizioLavori() != null ? ess(r.getDataInizioLavori(), 8) : ess("", 8));
            sb.append(r.getDataFineLavori() != null ? ess(r.getDataFineLavori(), 8) : ess("", 8));
            sb.append(r.getIndirizzoOggettoIstanza() != null ? ess(r.getIndirizzoOggettoIstanza(), 35) : ess("", 35));
        }

        sb.append(ess("", 139));
        sb.append(ir.getControllo() != null && ir.getControllo().getCarattereControllo() != null ? ir.getControllo().getCarattereControllo() : ess("", 1));
        sb.append("\r\n");
        return sb.toString();
    }

    private String componiBeneficiario(Beneficiario beneficiario) throws Exception {
        Beneficiario b = beneficiario;
        if (b == null) {
            throw new Exception("Il beneficiario è obbligatoria");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(b.getTipoRecord() != null ? b.getTipoRecord() : 2);
        if (b.getConcatenazione() == null) {
            sb.append(ess("", 16));
            sb.append(ess("", 20));
        } else {
            sb.append(b.getConcatenazione().getCodiceFiscaleRichiedente() != null ? ess(b.getConcatenazione().getCodiceFiscaleRichiedente(), 16) : ess("", 16));
            sb.append(b.getConcatenazione().getNumeroProtocollo() != null ? ess(b.getConcatenazione().getNumeroProtocollo(), 20) : ess("", 20));
        }
        sb.append(getSoggetto(b.getSoggettoBeneficiario()));
        sb.append(b.getQualificaBeneficiario() != null ? ess(b.getQualificaBeneficiario(), 1) : ess("", 1));

        sb.append(ess("", 185));
        sb.append(b.getControllo() != null && b.getControllo().getCarattereControllo() != null ? b.getControllo().getCarattereControllo() : ess("", 1));
        sb.append("\r\n");
        return sb.toString();
    }

    private String componiDatiCatastali(DatoCatastale datoCatastale) throws Exception {
        DatoCatastale d = datoCatastale;
        if (d == null) {
            throw new Exception("I dati catastali sono obbligatori");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(d.getTipoRecord() != null ? d.getTipoRecord() : 3);
        if (d.getConcatenazione() == null) {
            sb.append(ess("", 16));
            sb.append(ess("", 20));
        } else {
            sb.append(d.getConcatenazione().getCodiceFiscaleRichiedente() != null ? ess(d.getConcatenazione().getCodiceFiscaleRichiedente(), 16) : ess("", 16));
            sb.append(d.getConcatenazione().getNumeroProtocollo() != null ? ess(d.getConcatenazione().getNumeroProtocollo(), 20) : ess("", 20));
        }
        sb.append(d.getTipoUnita() != null ? ess(d.getTipoUnita(), 1) : ess("", 1));
        sb.append(d.getSezione() != null ? ess(d.getSezione(), 3) : ess("", 3));
        sb.append(d.getFoglio() != null ? ess(d.getFoglio(), 5) : ess("", 5));
        sb.append(d.getParticella() != null ? ess(d.getParticella(), 5) : ess("", 5));
        sb.append(d.getEstensioneParticella() != null ? ess(d.getEstensioneParticella(), 4) : ess("", 4));
        sb.append(d.getTipoParticella() != null ? ess(d.getTipoParticella(), 1) : ess("", 1));
        sb.append(d.getSubalterno() != null ? ess(d.getSubalterno(), 4) : ess("", 4));

        sb.append(ess("", 307));
        sb.append(d.getControllo() != null && d.getControllo().getCarattereControllo() != null ? d.getControllo().getCarattereControllo() : ess("", 1));
        sb.append("\r\n");
        return sb.toString();
    }

    private String componiProfessionista(Professionista professionista) throws Exception {
        Professionista p = professionista;
        if (p == null) {
            throw new Exception("Il professionista è obbligatorio");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(p.getTipoRecord() != null ? p.getTipoRecord() : 4);
        if (p.getConcatenazione() == null) {
            sb.append(ess("", 16));
            sb.append(ess("", 20));
        } else {
            sb.append(p.getConcatenazione().getCodiceFiscaleRichiedente() != null ? ess(p.getConcatenazione().getCodiceFiscaleRichiedente(), 16) : ess("", 16));
            sb.append(p.getConcatenazione().getNumeroProtocollo() != null ? ess(p.getConcatenazione().getNumeroProtocollo(), 20) : ess("", 20));
        }
        if (p.getDettaglioProfessionista() == null) {
            sb.append(ess("", 16));
            sb.append(ess("", 1));
            sb.append(ess("", 2));
            sb.append(ess("", 10));
            sb.append(ess("", 1));
        } else {
            sb.append(p.getDettaglioProfessionista().getCodiceFiscale() != null ? ess(p.getDettaglioProfessionista().getCodiceFiscale(), 16) : ess("", 16));
            sb.append(p.getDettaglioProfessionista().getAlboProfessionale() != null ? ess(p.getDettaglioProfessionista().getAlboProfessionale(), 1) : ess("", 1));
            sb.append(p.getDettaglioProfessionista().getProvinciaAlbo() != null ? ess(p.getDettaglioProfessionista().getProvinciaAlbo(), 2) : ess("", 2));
            sb.append(p.getDettaglioProfessionista().getNumeroIscrizioneAlbo() != null ? ess(p.getDettaglioProfessionista().getNumeroIscrizioneAlbo(), 10) : ess("", 10));
            sb.append(p.getDettaglioProfessionista().getQualificaProfessionale() != null ? ess(p.getDettaglioProfessionista().getQualificaProfessionale(), 1) : ess("", 1));
        }

        sb.append(ess("", 300));
        sb.append(p.getControllo() != null && p.getControllo().getCarattereControllo() != null ? p.getControllo().getCarattereControllo() : ess("", 1));
        sb.append("\r\n");
        return sb.toString();
    }

    private String componiImpresa(Impresa impresa) throws Exception {
        Impresa i = impresa;
        if (i == null) {
            throw new Exception("L'impresa è obbligatoria");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(i.getTipoRecord() != null ? i.getTipoRecord() : 5);
        if (i.getConcatenazione() == null) {
            sb.append(ess("", 16));
            sb.append(ess("", 20));
        } else {
            sb.append(i.getConcatenazione().getCodiceFiscaleRichiedente() != null ? ess(i.getConcatenazione().getCodiceFiscaleRichiedente(), 16) : ess("", 16));
            sb.append(i.getConcatenazione().getNumeroProtocollo() != null ? ess(i.getConcatenazione().getNumeroProtocollo(), 20) : ess("", 20));
        }
        sb.append(i.getPartitaIvaImpresa() != null ? ess(i.getPartitaIvaImpresa(), 11) : ess("", 11));
        sb.append(i.getDenominazione() != null ? ess(i.getDenominazione(), 50) : ess("", 50));
        sb.append(i.getCodiceCatastaleComuneSedeLegale() != null ? ess(i.getCodiceCatastaleComuneSedeLegale(), 4) : ess("", 4));

        sb.append(ess("", 265));
        sb.append(i.getControllo() != null && i.getControllo().getCarattereControllo() != null ? i.getControllo().getCarattereControllo() : ess("", 1));
        sb.append("\r\n");
        return sb.toString();
    }

    private String componiRecordControlloCommercio(it.wego.cross.xml.anagrafetributaria.commercio.RecordControllo recordControllo, boolean isRecordDiCoda) {
        StringBuilder sb = new StringBuilder();
        sb.append(recordControllo.getTipoRecord());
        sb.append(ess(Utils.trim(recordControllo.getCodiceFiscaleUfficio()), 11, true));
        sb.append(ess(Utils.trim(recordControllo.getDenominazioneUfficio()), 60));
        sb.append(ess(Utils.trim(recordControllo.getDomicilioFiscaleUfficio()), 35));
        sb.append(ess(Utils.trim(recordControllo.getProvinciaDomicilioUfficio()), 2));
        sb.append(ess(Utils.trim(recordControllo.getIndirizzoUfficio()), 35));
        sb.append(ess(Utils.trim(recordControllo.getCapUfficio()), 5, true));
        sb.append(ess(recordControllo.getNaturaUfficio(), 2, true));
        if (isRecordDiCoda) {
            sb.append(ess(String.valueOf(recordControllo.getTotaleRecordInviati()), 7, true));
            sb.append(ess(recordControllo.getAnnoRiferimento(), 4, true));
            sb.append(ess(recordControllo.getCodiceFornitura(), 2));
            sb.append(ess(recordControllo.getProgressivoInvio(), 7, true));
            sb.append(recordControllo.getDataInvio());
            sb.append(recordControllo.getFlagRiciclo());
            sb.append(ess(recordControllo.getFiller(), 19));
        } else {
            sb.append(ess(recordControllo.getAnnoRiferimento(), 4, true));
            sb.append(ess(recordControllo.getCodiceFornitura(), 2));
            sb.append(ess(recordControllo.getProgressivoInvio(), 7, true));
            sb.append(recordControllo.getDataInvio());
            sb.append(recordControllo.getFlagRiciclo());
            sb.append(ess(recordControllo.getFiller(), 26));
        }
        sb.append("\n");
        return sb.toString();
    }

    private String componiRecordControlloEdilizia(RecordControllo recordControllo) {
        StringBuilder sb = new StringBuilder();
        sb.append(recordControllo.getTipoRecord());
        sb.append(recordControllo.getCodiceIdentificativoFornitura());
        sb.append(recordControllo.getCodiceNumericoFornitura());
        sb.append(getSoggetto(recordControllo.getSoggettoObbligato()));
        sb.append(recordControllo.getAnnoRiferimento() != null ? recordControllo.getAnnoRiferimento() : ess("", 4));
        sb.append(ess("", 211));
        sb.append(recordControllo.getControllo() != null && recordControllo.getControllo().getCarattereControllo() != null ? recordControllo.getControllo().getCarattereControllo() : ess("", 1));
        sb.append("\r\n");
        return sb.toString();
    }

    private String getSoggetto(Soggetto soggetto) {
        StringBuilder sb = new StringBuilder();
        if (soggetto == null) {
            sb.append(ess("", 16));
            sb.append(ess("", 26));
            sb.append(ess("", 25));
            sb.append(ess("", 1));
            sb.append(ess("", 8));
            sb.append(ess("", 4));
            sb.append(ess("", 60));
            sb.append(ess("", 4));
        } else {
            if (soggetto.getCodiceFiscale() == null) {
                sb.append(ess("", 16));
            } else if (StringUtils.isNumeric(soggetto.getCodiceFiscale())) {
                sb.append(ess(soggetto.getCodiceFiscale(), 16, Boolean.FALSE));
            } else {
                sb.append(ess(soggetto.getCodiceFiscale(), 16, Boolean.TRUE));
            }
            if (soggetto.getSoggettoFisico() == null) {
                //metto il recordo vuoto sul fisico
                sb.append(ess("", 26));
                sb.append(ess("", 25));
                sb.append(ess("", 1));
                sb.append(ess("", 8));
                sb.append(ess("", 4));
            } else {
                //metto il fisico
                sb.append(soggetto.getSoggettoFisico().getCognome() != null ? ess(soggetto.getSoggettoFisico().getCognome(), 26) : ess("", 26));
                sb.append(soggetto.getSoggettoFisico().getNome() != null ? ess(soggetto.getSoggettoFisico().getNome(), 25) : ess("", 25));
                sb.append(soggetto.getSoggettoFisico().getSesso() != null ? soggetto.getSoggettoFisico().getSesso() : ess("", 1));
                sb.append(soggetto.getSoggettoFisico().getDataNascita() != null ? soggetto.getSoggettoFisico().getDataNascita() : ess("", 8));
                sb.append(soggetto.getSoggettoFisico().getCodiceCatastaleComuneNascita() != null ? soggetto.getSoggettoFisico().getCodiceCatastaleComuneNascita() : ess("", 4));
            }
            if (soggetto.getSoggettoGiuridico() == null) {
                //metto il recordo vuoto sul giuridico
                sb.append(ess("", 60));
                sb.append(ess("", 4));
            } else {
                //metto il giuridico
                sb.append(soggetto.getSoggettoGiuridico().getDenominazione() != null ? ess(soggetto.getSoggettoGiuridico().getDenominazione(), 60) : ess("", 60));
                sb.append(soggetto.getSoggettoGiuridico().getCodiceCatastaleSedeLegale() != null ? ess(soggetto.getSoggettoGiuridico().getCodiceCatastaleSedeLegale(), 4) : ess("", 4));
            }
        }

        return sb.toString();
    }

    private String ess(String originalString, Integer resultLenght) {
        return ess(originalString, resultLenght, Boolean.TRUE);
    }

    private String ess(String originalString, Integer resultLenght, Boolean rightAlignament) {
        if (originalString == null) {
            originalString = "";
        }
        //Prima facciamo l'extend per essere sicuri di avere la stringa lunga al minimo come resultLenght
        String tmpString = Utils.extendString(originalString, resultLenght, " ", rightAlignament);
        //potrebbe però essere più lunga....quindi la tranciamo.
        if (tmpString.length() > resultLenght) {
            return tmpString.substring(0, resultLenght);
        } else {
            return tmpString;
        }
    }

    @Override
    public List<QualificaLookupDTO> findLookupQualificaByCondizione(String condizione) {
        List<LkTipoQualifica> tipiQualifica = lookupDao.findQualificaByCondizione(condizione);
        List<QualificaLookupDTO> qualifiche = new ArrayList<QualificaLookupDTO>();
        for (LkTipoQualifica tipo : tipiQualifica) {
            QualificaLookupDTO dto = new QualificaLookupDTO();
            dto.setCodice(String.valueOf(tipo.getIdTipoQualifica()));
            dto.setDescrizione(StringEscapeUtils.escapeJavaScript(tipo.getDescrizione()));
            qualifiche.add(dto);
        }
        return qualifiche;
    }

    @Override
    public List<AnagraficaDTO> findRichiedentiPratica(Pratica pratica) {
        LkTipoRuolo tipoRuolo = lookupDao.findTipoRuoloByCodRuolo("R");
        List<PraticaAnagrafica> praticheAnagrafiche = praticaDao.findPraticaAnagraficaByTipoRuolo(pratica, tipoRuolo);
        List<AnagraficaDTO> richiedenti = new ArrayList<AnagraficaDTO>();
        if (praticheAnagrafiche != null && !praticheAnagrafiche.isEmpty()) {
            for (PraticaAnagrafica pa : praticheAnagrafiche) {
                AnagraficaDTO dto = serialize(pa);
                richiedenti.add(dto);
            }
        }
        return richiedenti;
    }

    @Override
    public List<AnagraficaDTO> findBeneficiariPratica(Pratica pratica) {
        LkTipoRuolo tipoRuolo = lookupDao.findTipoRuoloByCodRuolo("B");
        List<PraticaAnagrafica> praticheAnagrafiche = praticaDao.findPraticaAnagraficaByTipoRuolo(pratica, tipoRuolo);
        List<AnagraficaDTO> beneficiari = new ArrayList<AnagraficaDTO>();
        if (praticheAnagrafiche != null && !praticheAnagrafiche.isEmpty()) {
            for (PraticaAnagrafica pa : praticheAnagrafiche) {
                AnagraficaDTO dto = serialize(pa);
                beneficiari.add(dto);
            }
        }
        return beneficiari;
    }

    @Override
    public List<AnagraficaDTO> findProfessionistiPratica(Pratica pratica) {
        LkTipoRuolo tipoRuolo = lookupDao.findTipoRuoloByCodRuolo("P");
        List<PraticaAnagrafica> praticheAnagrafiche = praticaDao.findPraticaAnagraficaByTipoRuolo(pratica, tipoRuolo);
        List<AnagraficaDTO> professionisti = new ArrayList<AnagraficaDTO>();
        if (praticheAnagrafiche != null && !praticheAnagrafiche.isEmpty()) {
            for (PraticaAnagrafica pa : praticheAnagrafiche) {
                AnagraficaDTO dto = serialize(pa);
                professionisti.add(dto);
            }
        }
        return professionisti;
    }

    @Override
    public List<ImpresaDTO> findImpresePratica(Pratica pratica) throws Exception {
        LkTipoRuolo tipoRuolo = lookupDao.findTipoRuoloByCodRuolo("I");
        List<PraticaAnagrafica> praticheAnagrafiche = praticaDao.findPraticaAnagraficaByTipoRuolo(pratica, tipoRuolo);
        List<ImpresaDTO> imprese = new ArrayList<ImpresaDTO>();
        if (praticheAnagrafiche != null && !praticheAnagrafiche.isEmpty()) {
            for (PraticaAnagrafica pa : praticheAnagrafiche) {
                Anagrafica a = pa.getAnagrafica();
                ImpresaDTO dto = new ImpresaDTO();
                String descrizione;
                if ("F".equalsIgnoreCase(String.valueOf(a.getTipoAnagrafica())) && !"I".equalsIgnoreCase(a.getVarianteAnagrafica())) {
                    descrizione = a.getCognome() + " " + a.getNome() + " (" + a.getCodiceFiscale() + ")";
                } else {
                    descrizione = a.getDenominazione() + " (" + a.getCodiceFiscale() + ")";
                }
                dto.setIdAnagrafica(a.getIdAnagrafica());
                dto.setDescrizione(descrizione);

                Recapiti recapito = anagraficheService.getRecapitoRiferimentoAnagrafica(a);
                dto.setIdRecapito(recapito.getIdRecapito());
                String descrizioneRecapito = recapito.getIndirizzo() + " " + recapito.getNCivico() + " - " + recapito.getCap() + " " + pratica.getIdComune().getDescrizione() + (!Strings.isNullOrEmpty(recapito.getLocalita()) ? "(" + recapito.getLocalita() + ")" : "");
                dto.setDescrizioneRecapito(descrizioneRecapito);
                imprese.add(dto);

            }
        }
        return imprese;
    }

    private AnagraficaDTO serialize(PraticaAnagrafica pa) {
        Anagrafica a = pa.getAnagrafica();
        AnagraficaDTO dto = new AnagraficaDTO();
        String descrizione;
        if ("F".equalsIgnoreCase(String.valueOf(a.getTipoAnagrafica()))) {
            if ("S".equals(pa.getFlgDittaIndividuale())) {
                descrizione = a.getDenominazione() + " (" + a.getCodiceFiscale() + ")";
            } else {
                descrizione = a.getCognome() + " " + a.getNome() + " (" + a.getCodiceFiscale() + ")";
            }
        } else {
            descrizione = a.getDenominazione() + " (" + a.getCodiceFiscale() + ")";
        }
        dto.setDescrizione(descrizione);
        dto.setIdAnagrafica(a.getIdAnagrafica());
        if (pa.getIdTipoQualifica() != null) {
            dto.setIdQualifica(pa.getIdTipoQualifica().getIdTipoQualifica());
        }
        if (a.getIdTipoCollegio() != null) {
            dto.setIdTipoCollegio(a.getIdTipoCollegio().getIdTipoCollegio());
        }
        if (a.getIdProvinciaIscrizione() != null) {
            dto.setCodProvinciaIscrizione(a.getIdProvinciaIscrizione().getCodCatastale());
        }
        dto.setNumeroIscrizioneAlbo(a.getNumeroIscrizione());
        return dto;
    }

    @Override
    public List<IndirizzoRichiestaDTO> findIndirizzoRichiesta(Pratica pratica) {
        List<IndirizzoRichiestaDTO> indirizzi = new ArrayList<IndirizzoRichiestaDTO>();
        List<IndirizziIntervento> indirizziInterventoList = pratica.getIndirizziInterventoList();
        if (indirizziInterventoList != null) {
            for (IndirizziIntervento indirizzo : indirizziInterventoList) {
                IndirizzoRichiestaDTO dto = new IndirizzoRichiestaDTO();
                dto.setIdRecapito(indirizzo.getIdIndirizzoIntervento());
                String descrizione = indirizzo.getIndirizzo() + " " + indirizzo.getCivico() + " - " + Strings.nullToEmpty(indirizzo.getCap()) + " " + pratica.getIdComune().getDescrizione() + (!Strings.isNullOrEmpty(indirizzo.getLocalita()) ? "(" + indirizzo.getLocalita() + ")" : "");
                dto.setDescrizione(descrizione);
                indirizzi.add(dto);
            }
        }
        return indirizzi;
    }

    @Override
    public List<Select> findAllTipoUnita() {
        List<LkTipoUnita> tipiUnitaLookup = lookupDao.findAllLkTipoUnita();
        List<Select> tipiUnita = new ArrayList<Select>();
        for (LkTipoUnita tipoUnita : tipiUnitaLookup) {
            Select s = new Select();
            s.setItemLabel(tipoUnita.getDescrizione());
            s.setItemValue(String.valueOf(tipoUnita.getIdTipoUnita()));
            tipiUnita.add(s);
        }
        return tipiUnita;
    }

    @Override
    public List<Select> findAllTipoParticella() {
        List<LkTipoParticella> tipiParticellaLookup = lookupDao.findAllLkTipoParticella();
//        LkTipoSistemaCatastale catastoOrdinario = lookupDao.findIdTipoCatastaleByDesc("ORDINARIO");
        List<Select> tipiParticella = new ArrayList<Select>();
        for (LkTipoParticella p : tipiParticellaLookup) {
            //L'anagrafe tributaria gestisce solo il catasto ordinario, non il tavolare, per la tipologia di particella
            Select s = new Select();
            s.setItemLabel(p.getDescrizione());
            s.setItemValue(String.valueOf(p.getIdTipoParticella()));
            tipiParticella.add(s);
        }
        return tipiParticella;
    }

    @Override
    public List<AnagraficaDTO> findAllAnagraficheByTipologia(Pratica pratica, String tipologia) {
        List<AnagraficaDTO> anagrafiche = new ArrayList<AnagraficaDTO>();
        for (PraticaAnagrafica pa : pratica.getPraticaAnagraficaList()) {
            if (tipologia != null) {
                if (String.valueOf(pa.getAnagrafica().getTipoAnagrafica()).equalsIgnoreCase(tipologia)) {
                    AnagraficaDTO dto = new AnagraficaDTO();
                    String descrizione;
                    if (String.valueOf(pa.getAnagrafica().getTipoAnagrafica()).equals("F")) {
                        descrizione = pa.getAnagrafica().getCognome() + " " + pa.getAnagrafica().getNome() + " (" + pa.getAnagrafica().getCodiceFiscale() + ")";
                    } else {
                        descrizione = pa.getAnagrafica().getDenominazione() + " (" + pa.getAnagrafica().getCodiceFiscale() + ")";
                    }
                    dto.setDescrizione(StringEscapeUtils.escapeJavaScript(descrizione));
                    dto.setIdAnagrafica(pa.getAnagrafica().getIdAnagrafica());
                    anagrafiche.add(dto);
                }
            } else {
                AnagraficaDTO dto = new AnagraficaDTO();
                String descrizione;
                if (String.valueOf(pa.getAnagrafica().getTipoAnagrafica()).equals("F")) {
                    descrizione = pa.getAnagrafica().getCognome() + " " + pa.getAnagrafica().getNome() + " (" + pa.getAnagrafica().getCodiceFiscale() + ")";
                } else {
                    descrizione = pa.getAnagrafica().getDenominazione() + " (" + pa.getAnagrafica().getCodiceFiscale() + ")";
                }
                dto.setDescrizione(StringEscapeUtils.escapeJavaScript(descrizione));
                dto.setIdAnagrafica(pa.getAnagrafica().getIdAnagrafica());
                anagrafiche.add(dto);
            }
        }
        return anagrafiche;
    }

    @Override
    public List<Select> findAllAlbiProfessionali() {
        List<LkTipoCollegio> collegi = lookupDao.findAllLkTipoCollegio();
        List<Select> albi = new ArrayList<Select>();
        for (LkTipoCollegio collegio : collegi) {
            Select s = new Select();
            s.setItemLabel(StringEscapeUtils.escapeJavaScript(collegio.getDescrizione()));
            s.setItemValue(String.valueOf(collegio.getIdTipoCollegio()));
            albi.add(s);
        }
        return albi;
    }

    @Override
    public List<Select> findAllProvincie() {
        List<LkProvincie> provinceLookup = lookupDao.findAllProvince();
        List<Select> provincie = new ArrayList<Select>();
        for (LkProvincie provincia : provinceLookup) {
            Select s = new Select();
            s.setItemLabel(StringEscapeUtils.escapeJavaScript(provincia.getDescrizione()));
            s.setItemValue(provincia.getCodCatastale());
            provincie.add(s);
        }
        return provincie;
    }

    @Override
    public List<IndirizzoRichiestaDTO> findIndrizzoSede(List<AnagraficaDTO> imprese) {
        List<IndirizzoRichiestaDTO> indirizzi = new ArrayList<IndirizzoRichiestaDTO>();
        LkTipoIndirizzo sede = lookupDao.findTipoIndirizzoByCod("SED");
        for (AnagraficaDTO impresa : imprese) {
            List<AnagraficaRecapiti> recapiti = anagrafeTributariaDao.findRecapitiByIdAnagraficaIdTipoIndirizzo(impresa.getIdAnagrafica(), sede);
            if (recapiti != null && !recapiti.isEmpty()) {
                for (AnagraficaRecapiti recapito : recapiti) {
                    IndirizzoRichiestaDTO dto = serializzaIndirizzo(recapito);
                    indirizzi.add(dto);
                }
            } else {
                recapiti = anagrafeTributariaDao.findRecapitiByIdAnagrafica(impresa.getIdAnagrafica());
                for (AnagraficaRecapiti recapito : recapiti) {
                    IndirizzoRichiestaDTO dto = serializzaIndirizzo(recapito);
                    indirizzi.add(dto);
                }
            }

        }
        return indirizzi;
    }

    private IndirizzoRichiestaDTO serializzaIndirizzo(AnagraficaRecapiti anagraficaRecapito) {
        IndirizzoRichiestaDTO dto = new IndirizzoRichiestaDTO();
        Recapiti recapito = anagraficaRecapito.getIdRecapito();
        dto.setIdRecapito(recapito.getIdRecapito());
        String descrizione = recapito.getIndirizzo() + " " + Utils.escapeNullString(recapito.getNCivico())
                + " (" + Utils.escapeNullString(recapito.getCap()) + " " + recapito.getIdComune().getDescrizione() + ") - tipologia di recapito " + anagraficaRecapito.getIdTipoIndirizzo().getDescrizione();
        dto.setDescrizione(StringEscapeUtils.escapeJavaScript(descrizione));
        return dto;
    }

    @Override
    public void salvaRecordDettaglio(AtRecordDettaglio recordDettaglio) throws Exception {
        anagrafeTributariaDao.merge(recordDettaglio);
    }

    @Override
    public List<AnagraficaRecapiti> findRecapitiByTipologia(Anagrafica anagraficaBeneficiario, String tipologia) {
        LkTipoIndirizzo sede = lookupDao.findTipoIndirizzoByCod(tipologia);
        List<AnagraficaRecapiti> recapiti = anagrafeTributariaDao.findRecapitiByIdAnagraficaIdTipoIndirizzo(anagraficaBeneficiario.getIdAnagrafica(), sede);
        return recapiti;
    }

    @Override
    public List<Select> findAllEnti() {
        List<Enti> enti = entiDao.findAll();
        List<Select> select = new ArrayList<Select>();
        for (Enti e : enti) {
            Select s = new Select();
            s.setItemLabel(e.getDescrizione());
            s.setItemValue(String.valueOf(e.getIdEnte()));
            select.add(s);
        }
        return select;
    }

    @Override
    public List<Select> findAllComuni() {
        List<LkComuni> comuni = lookupDao.findAllEntiComuni();
        List<Select> select = new ArrayList<Select>();
        for (LkComuni comune : comuni) {
            Select option = new Select();
            option.setItemLabel(comune.getDescrizione());
            option.setItemValue(String.valueOf(comune.getIdComune()));
            select.add(option);
        }
        return select;
    }

    @Override
    public List<AtRecordDettaglio> getRecordDettaglio(String codFornitura, Integer annoRiferimento, Integer idSoggettoObbligato) {
        List<AtRecordDettaglio> recordsDettaglio = anagrafeTributariaDao.findRecordDettaglio(codFornitura, annoRiferimento, idSoggettoObbligato);
        return recordsDettaglio;
    }

    @Override
    public List<AtRecordDettaglio> findRecordDettaglio(Integer idPratica, String codFornitura) {
        List<AtRecordDettaglio> recordDettaglio = anagrafeTributariaDao.findRecordDettaglio(idPratica, codFornitura);
        return recordDettaglio;
    }

    @Override
    public LkTipoUnita findLkTipoUnitaByCod(String tipoUnita) {
        LkTipoUnita tu = lookupDao.findTipoUnitaByCod(tipoUnita);
        return tu;
    }

    @Override
    public AtRecordDettaglio findRecordDettaglioById(Integer idDettaglio) {
        AtRecordDettaglio recordDettaglio = anagrafeTributariaDao.findRecordDettaglioBtId(idDettaglio);
        return recordDettaglio;
    }

    @Override
    public LkComuni findComuneSede(Integer idSoggetto) {
        LkComuni comuneSede = anagrafeTributariaDao.findComuneSede(idSoggetto);
        return comuneSede;
    }

    @Override
    public Enti findEnteById(Integer idSoggettoObbligato) {
        Enti ente = entiDao.findByIdEnte(idSoggettoObbligato);
        return ente;
    }

    @Override
    public List<Select> findAllTipoCatasto() {
        List<LkTipoSistemaCatastale> tipoSistemaCatastale = anagrafeTributariaDao.findAllTipoCatasto();
        List<Select> select = new ArrayList<Select>();
        for (LkTipoSistemaCatastale catasto : tipoSistemaCatastale) {
            Select option = new Select();
            option.setItemLabel(catasto.getDescrizione());
            option.setItemValue(catasto.getDescrizione());
            select.add(option);
        }
        return select;
    }

    @Override
    public List<DatiCatastaliDTO> findDatiCatastali(Pratica pratica) {
        List<DatiCatastali> datiCatastaliList = pratica.getDatiCatastaliList();
        List<DatiCatastaliDTO> result = new ArrayList<DatiCatastaliDTO>();
        if (!CollectionUtils.isEmpty(datiCatastaliList)) {
            for (DatiCatastali dato : datiCatastaliList) {
                DatiCatastaliDTO dto = new DatiCatastaliDTO();
                if (dato.getIdTipoSistemaCatastale() != null) {
                    dto.setDesTipoCatasto(dato.getIdTipoSistemaCatastale().getDescrizione());
                }
                dto.setEstensioneParticella(dato.getEstensioneParticella());
                dto.setFoglio(dato.getFoglio());
                //Mappale coincide con la particella: http://bit.ly/1trmlSd
                dto.setParticella(dato.getMappale());

                dto.setSezione(dato.getSezione());
                dto.setSubalterno(dato.getSubalterno());
                if (dato.getIdTipoUnita() != null) {
                    dto.setIdTipoUnita(dato.getIdTipoUnita().getIdTipoUnita());
                }
                if (dato.getIdTipoParticella() != null) {
                    dto.setIdTipologiaParticella(dato.getIdTipoParticella().getIdTipoParticella());
                }
                result.add(dto);
            }
        }
        return result;
    }
}
