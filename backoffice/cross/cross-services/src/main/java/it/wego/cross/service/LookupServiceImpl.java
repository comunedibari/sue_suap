/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dao.LookupDao;
import it.wego.cross.dto.AttoreDTO;
import it.wego.cross.dto.CittadinanzaDTO;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.NazionalitaDTO;
import it.wego.cross.dto.ProvinciaDTO;
import it.wego.cross.dto.RuoloAnagraficaDTO;
import it.wego.cross.dto.StatoDTO;
import it.wego.cross.dto.StatoPraticaDTO;
import it.wego.cross.dto.TipoIndirizzoDTO;
import it.wego.cross.dto.TipoQualificaAnagraficaDTO;
import it.wego.cross.dto.TipoRuoloDTO;
import it.wego.cross.dto.TipologiaScadenzeDTO;
import it.wego.cross.dto.dozer.LkTipoParticellaDTO;
import it.wego.cross.dto.dozer.LkTipoSistemaCatastaleDTO;
import it.wego.cross.dto.dozer.LkTipoUnitaDTO;
import it.wego.cross.entity.LkCittadinanza;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkDug;
import it.wego.cross.entity.LkFormeGiuridiche;
import it.wego.cross.entity.LkNazionalita;
import it.wego.cross.entity.LkScadenze;
import it.wego.cross.entity.LkStatiScadenze;
import it.wego.cross.entity.LkStatoPratica;
import it.wego.cross.entity.LkTipiAttore;
import it.wego.cross.entity.LkTipoCollegio;
import it.wego.cross.entity.LkTipoIndirizzo;
import it.wego.cross.entity.LkTipoParticella;
import it.wego.cross.entity.LkTipoQualifica;
import it.wego.cross.entity.LkTipoRuolo;
import it.wego.cross.entity.LkTipoSistemaCatastale;
import it.wego.cross.entity.LkTipoUnita;
import it.wego.cross.serializer.AnagraficheSerializer;
import it.wego.cross.utils.DozerUtils;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.commons.lang.StringEscapeUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author giuseppe
 */
@Service
public class LookupServiceImpl implements LookupService {

    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private Mapper mapper;

    @Override
    public LkComuni findComuneById(Integer idComune) {
        return lookupDao.findComuneById(idComune);
    }

    @Override
    public LkTipoIndirizzo findByIdTipoIndirizzo(Integer idTipoIndirizzo) {
        return lookupDao.findTipoIndirizzoById(idTipoIndirizzo);
    }

    @Override
    public LkTipoIndirizzo findByCodTipoIndirizzo(String codiceIndirizzo) {
        return lookupDao.findTipoIndirizzoByCod(codiceIndirizzo);
    }

    @Override
    public List<TipoQualificaAnagraficaDTO> findAllLkTipoQualifica() {
        List<LkTipoQualifica> lookup = lookupDao.findAllTipoQualifica();
        List<TipoQualificaAnagraficaDTO> qualifiche = new ArrayList<TipoQualificaAnagraficaDTO>();
        for (LkTipoQualifica qualifica : lookup) {
            TipoQualificaAnagraficaDTO dto = AnagraficheSerializer.serializeTipoQualifica(qualifica);
            qualifiche.add(dto);
        }
        return qualifiche;
    }

    @Override
    public List<RuoloAnagraficaDTO> findAllLkTipoRuolo() {
        List<LkTipoRuolo> lookup = lookupDao.findAllTipoRuolo();
        List<RuoloAnagraficaDTO> ruoli = new ArrayList<RuoloAnagraficaDTO>();
        for (LkTipoRuolo tipoRuolo : lookup) {
            RuoloAnagraficaDTO dto = AnagraficheSerializer.serializeTipoRuolo(tipoRuolo);
            ruoli.add(dto);
        }
        return ruoli;
    }

    @Override
    public LkTipoRuolo findLkTipoRuoloById(Integer idTipoRuolo) {
        return lookupDao.findTipoRuoloById(idTipoRuolo);
    }

    @Override
    public LkTipoQualifica findLkTipoQualifica(Integer idTipoQualifica) {
        return lookupDao.findTipoQualificaById(idTipoQualifica);
    }

    @Override
    public List<TipoIndirizzoDTO> findAllLkTipoIndirizzo() {
        List<LkTipoIndirizzo> tipiIndirizzo = lookupDao.findAllTipoIndirizzo();
        List<TipoIndirizzoDTO> dtoList = new ArrayList<TipoIndirizzoDTO>();
        for (LkTipoIndirizzo tipo : tipiIndirizzo) {
            TipoIndirizzoDTO dto = new TipoIndirizzoDTO();
            dto.setDescTipoIndirizzo(tipo.getDescrizione());
            dto.setIdTipoIndirizzo(tipo.getIdTipoIndirizzo());
            dto.setCodTipoIndirizzo(tipo.getCodTipoIndirizzo());
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public LkStatoPratica findStatoPraticaByCodice(String codice) {
        return lookupDao.findStatoPraticaByCodice(codice);
    }

    @Override
    public List<LkStatoPratica> findStatiPraticaByGruppo(String gruppo) {
        return lookupDao.findStatiPraticaByGruppo(gruppo);
    }

    @Override
    public List<StatoPraticaDTO> findAllStatiPratica() {
        List<LkStatoPratica> statiPratica = lookupDao.findAllLkStatoPratica();
        List<StatoPraticaDTO> list = new ArrayList<StatoPraticaDTO>();
        for (LkStatoPratica stato : statiPratica) {
            StatoPraticaDTO dto = new StatoPraticaDTO();
            dto.setDescrizione(stato.getDescrizione());
            dto.setIdStatoPratica(stato.getIdStatoPratica());
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<AttoreDTO> findAllAttori() {
        List<LkTipiAttore> tipiAttore = lookupDao.findAllTipiAttore();
        List<AttoreDTO> attori = new ArrayList<AttoreDTO>();
        for (LkTipiAttore attore : tipiAttore) {
            AttoreDTO a = new AttoreDTO();
            a.setDesTipoAttore(attore.getDesTipoAttore());
            a.setIdTipoAttore(attore.getIdTipoAttore());
            attori.add(a);
        }
        return attori;
    }

    @Override
    public List<TipologiaScadenzeDTO> findAllLkScadenze() {
        List<LkScadenze> scadenze = lookupDao.findAllScadenze();
        List<TipologiaScadenzeDTO> list = new ArrayList<TipologiaScadenzeDTO>();
        if (scadenze != null) {
            for (LkScadenze scadenza : scadenze) {
                TipologiaScadenzeDTO dto = new TipologiaScadenzeDTO();
                dto.setIdAnaScadenza(scadenza.getIdAnaScadenze());
                dto.setDesAnaScadenza(StringEscapeUtils.escapeJavaScript(scadenza.getDesAnaScadenze()));
                dto.setFlgScadenzaPratica(scadenza.getFlgScadenzaPratica());
                list.add(dto);
            }
        }
        return list;
    }

    @Override
    public LkStatiScadenze findStatoScadenza(String idStatoScadenza) {
        LkStatiScadenze statoScadenza = lookupDao.findStatoScadenzaById(idStatoScadenza);
        return statoScadenza;
    }

    @Override
    public LkComuni findComuneByCodCatastale(String codEnte) {
        LkComuni comune = lookupDao.findComuneByCodCatastale(codEnte);
        return comune;
    }

    @Override
    public List<LkFormeGiuridiche> findAllLkFormeGiuridiche() {
        List<LkFormeGiuridiche> formeGiuridiche = lookupDao.findAllLkFormeGiuridiche();
        return formeGiuridiche;
    }

    @Override
    public List<LkNazionalita> findAllLkNazionalita() {
        List<LkNazionalita> nazionalita = lookupDao.findAllNazionalita();
        return nazionalita;
    }

    @Override
    public LkTipoQualifica findLkTipoQualificaByCodQualificaAndCondizione(String codiceQualifica, String condizione) {
        LkTipoQualifica qualifica = lookupDao.findTipoQualificaByCodQualificaAndCondizione(codiceQualifica, condizione);
        return qualifica;
    }

    @Override
    public LkTipoRuolo findLkTipoRuoloByCodRuolo(String codRuolo) {
        LkTipoRuolo ruolo = lookupDao.findTipoRuoloByCodRuolo(codRuolo);
        return ruolo;
    }

    @Override
    public LkNazionalita findLkNazionalitaById(Integer idNazionalita) {
        LkNazionalita nazionalita = lookupDao.findNazionalitaByID(idNazionalita);
        return nazionalita;
    }

    @Override
    public List<LkStatoPratica> findStatoPraticaByGruppoDiversoDa(String gruppo) {
        List<LkStatoPratica> statoPratica = lookupDao.findStatoPraticaByGruppoDiversoDa(gruppo);
        return statoPratica;
    }

    @Override
    public Map<Integer, String> getTipoSistemaCatastale() {
        Map<Integer, String> data = new HashMap<Integer, String>();
        List<LkTipoSistemaCatastale> list = lookupDao.findAllTipoCatastale();
        for (LkTipoSistemaCatastale row : list) {
            data.put(row.getIdTipoSistemaCatastale(), row.getDescrizione());
        }
        return data;
    }

    @Override
    public List<LkTipoSistemaCatastaleDTO> getTipoSistemaCatastaleDto() {
        List<LkTipoSistemaCatastale> listTipoSistemaCatastale = lookupDao.findAllTipoCatastale();
        return DozerUtils.map(mapper, listTipoSistemaCatastale, LkTipoSistemaCatastaleDTO.class);
    }

    @Override
    public List<TipoRuoloDTO> getTipoRuolo() {
        List<TipoRuoloDTO> data = new ArrayList<TipoRuoloDTO>();
        List<LkTipoRuolo> list = lookupDao.findAllTipoRuolo();
        for (LkTipoRuolo row : list) {
            TipoRuoloDTO ruolo = new TipoRuoloDTO();
            ruolo.setCodRuolo(row.getCodRuolo());
            ruolo.setDescrizione(row.getDescrizione());
            ruolo.setIdTipoRuolo(row.getIdTipoRuolo());
            data.add(ruolo);
        }
        return data;
    }

    @Override
    public Map<Integer, String> getDug() {
        Map<Integer, String> data = new HashMap<Integer, String>();
        List<LkDug> listLkDug = lookupDao.findAllDug();
        for (LkDug row : listLkDug) {
            data.put(row.getIdDug(), row.getDescrizione());
        }
        return data;
    }

    @Override
    public Map<Integer, String> getTipoParticella() {
        Map<Integer, String> data = new HashMap<Integer, String>();
        List<LkTipoParticella> listTipoParticella = lookupDao.findAllLkTipoParticella();
        for (LkTipoParticella row : listTipoParticella) {
            data.put(row.getIdTipoParticella(), row.getDescrizione());
        }
        return data;
    }

    @Override
    public List<LkTipoParticellaDTO> getTipoParticellaDto() {
        List<LkTipoParticella> listTipoParticella = lookupDao.findAllLkTipoParticella();
        return DozerUtils.map(mapper, listTipoParticella, LkTipoParticellaDTO.class);
    }

    @Override
    public Map<Integer, String> getTipoUnita() {
        Map<Integer, String> data = new HashMap<Integer, String>();
        List<LkTipoUnita> listTipoUnita = lookupDao.findAllLkTipoUnita();
        for (LkTipoUnita row : listTipoUnita) {
            data.put(row.getIdTipoUnita(), row.getDescrizione());
        }
        return data;
    }

    @Override
    public List<LkTipoUnitaDTO> getTipoUnitaDto() {
        List<LkTipoUnita> listTipoUnita = lookupDao.findAllLkTipoUnita();
        return DozerUtils.map(mapper, listTipoUnita, LkTipoUnitaDTO.class);
    }

    @Override
    public Map<Integer, String> getLookupFormaGiuridica(String descrizione) {
        Map<Integer, String> map = new HashMap<Integer, String>();
        List<LkFormeGiuridiche> forme = lookupDao.findLkFormeGiuridicheByDesc(descrizione);
        for (LkFormeGiuridiche forma : forme) {
            map.put(forma.getIdFormeGiuridiche(), forma.getDescrizione());
        }
        return map;
    }

    @Override
    public Map<Integer, String> getLookupTipoCollegio(String descrizione) {
        Map<Integer, String> map = new HashMap<Integer, String>();
        List<LkTipoCollegio> forme = lookupDao.findLookupTipoCollegioByDescrizione(descrizione);
        for (LkTipoCollegio forma : forme) {
            map.put(forma.getIdTipoCollegio(), forma.getDescrizione());
        }
        return map;
    }

    @Nullable
    @Override
    public ComuneDTO getComuneDTOFromNomePreciso(String nome) {
        ComuneDTO result = null;
        if (!Utils.e(nome)) {
            Date dataOggi = new Date();
            List<LkComuni> comuniTrovati = lookupDao.findComuniByDescrizionePrecisa(nome, dataOggi);
            if (comuniTrovati.size() > 0) {
                result = new ComuneDTO();
                result.setCodCatastale(comuniTrovati.get(0).getCodCatastale());
                result.setDataFineValidita(comuniTrovati.get(0).getDataFineValidita());
                result.setDescrizione(comuniTrovati.get(0).getDescrizione());
                result.setIdComune(comuniTrovati.get(0).getIdComune());
                ProvinciaDTO provinciaDTO = new ProvinciaDTO();
                provinciaDTO.setCodCatastale(comuniTrovati.get(0).getIdProvincia().getCodCatastale());
                provinciaDTO.setDataFineValidita(comuniTrovati.get(0).getIdProvincia().getDataFineValidita());
                provinciaDTO.setDescrizione(comuniTrovati.get(0).getIdProvincia().getDescrizione());
                provinciaDTO.setFlgInfocamere(Character.toString(comuniTrovati.get(0).getIdProvincia().getFlgInfocamere()));
                provinciaDTO.setIdProvincie(comuniTrovati.get(0).getIdProvincia().getIdProvincie());
                result.setProvincia(provinciaDTO);
                StatoDTO statoDTO = new StatoDTO();
                statoDTO.setCodIstat(comuniTrovati.get(0).getIdStato().getCodIstat());
                statoDTO.setDataFine(comuniTrovati.get(0).getIdStato().getDataFine());
                statoDTO.setDataInizio(comuniTrovati.get(0).getIdStato().getDataInizio());
                statoDTO.setDescrizione(comuniTrovati.get(0).getIdStato().getDescrizione());
                statoDTO.setIdStato(comuniTrovati.get(0).getIdStato().getIdStato());
                result.setStato(statoDTO);
            }
        }
        return result;
    }

    @Nullable
    @Override
    public CittadinanzaDTO getCittadinanzaFromDescrizionePrecisa(String descrizione) {
        CittadinanzaDTO cittadinanza = null;
        List<LkCittadinanza> cittadinanzeTrovate = lookupDao.findCittadinanzaByDescrizionePrecisa(descrizione);
        if (cittadinanzeTrovate.size() > 0) {
            cittadinanza = new CittadinanzaDTO();
            cittadinanza.setCodCittadinanza(cittadinanzeTrovate.get(0).getCodCittadinanza());
            cittadinanza.setDescrizione(cittadinanzeTrovate.get(0).getDescrizione());
            cittadinanza.setIdCittadinanza(cittadinanzeTrovate.get(0).getIdCittadinanza());
        }
        return cittadinanza;
    }

    @Nullable
    @Override
    public NazionalitaDTO getNazionalitaFromDescrizionePrecisa(String descrizione) {
        NazionalitaDTO nazionalita = null;
        List<LkNazionalita> cittadinanzeTrovate = lookupDao.findNazionalitaByDescrizionePrecisa(descrizione);
        if (cittadinanzeTrovate.size() > 0) {
            nazionalita = new NazionalitaDTO();
            nazionalita.setCodNazionalita(cittadinanzeTrovate.get(0).getCodNazionalita());
            nazionalita.setDescrizione(cittadinanzeTrovate.get(0).getDescrizione());
            nazionalita.setIdNazionalita(cittadinanzeTrovate.get(0).getIdNazionalita());
        }
        return nazionalita;
    }

    @Nullable
    @Override
    public ComuneDTO getComuneDTOFromId(Integer id) {
        ComuneDTO result = null;
        if (id != null) {
            LkComuni lk = findComuneById(id);
            if (lk != null) {
                result = new ComuneDTO();
                result.setCodCatastale(lk.getCodCatastale());
                result.setDataFineValidita(lk.getDataFineValidita());
                result.setDescrizione(lk.getDescrizione());
                result.setIdComune(lk.getIdComune());
                result.setFlgTavolare(lk.getFlgTavolare());
                ProvinciaDTO provinciaDTO = new ProvinciaDTO();
                provinciaDTO.setCodCatastale(lk.getIdProvincia().getCodCatastale());
                provinciaDTO.setDataFineValidita(lk.getIdProvincia().getDataFineValidita());
                provinciaDTO.setDescrizione(lk.getIdProvincia().getDescrizione());
                provinciaDTO.setFlgInfocamere(Character.toString(lk.getIdProvincia().getFlgInfocamere()));
                provinciaDTO.setIdProvincie(lk.getIdProvincia().getIdProvincie());
                result.setProvincia(provinciaDTO);
                StatoDTO statoDTO = new StatoDTO();
                statoDTO.setCodIstat(lk.getIdStato().getCodIstat());
                statoDTO.setDataFine(lk.getIdStato().getDataFine());
                statoDTO.setDataInizio(lk.getIdStato().getDataInizio());
                statoDTO.setDescrizione(lk.getIdStato().getDescrizione());
                statoDTO.setIdStato(lk.getIdStato().getIdStato());
                result.setStato(statoDTO);
            }
        }
        return result;
    }

    @Nullable
    @Override
    public CittadinanzaDTO getCittadinanzaFromId(Integer id) {
        CittadinanzaDTO result = null;
        if (id != null && id > 0) {
            LkCittadinanza lk = lookupDao.findCittadinanzaByID(id);
            result = new CittadinanzaDTO();
            result.setCodCittadinanza(lk.getCodCittadinanza());
            result.setDescrizione(lk.getDescrizione());
            result.setIdCittadinanza(lk.getIdCittadinanza());
        }
        return result;

    }

    @Nullable
    @Override
    public NazionalitaDTO getNazionalitaFromId(Integer id) {
        NazionalitaDTO result = null;
        if (id != null && id > 0) {
            LkNazionalita lk = lookupDao.findNazionalitaByID(id);
            result = new NazionalitaDTO();
            result.setCodNazionalita(lk.getCodNazionalita());
            result.setDescrizione(lk.getDescrizione());
            result.setIdNazionalita(lk.getIdNazionalita());
        }
        return result;
    }

    @Nullable
    @Override
    public List<LkTipoUnitaDTO> findAllTipoUnita() {
        List<LkTipoUnitaDTO> o = new ArrayList<LkTipoUnitaDTO>();
        for (LkTipoUnita lk : lookupDao.findAllLkTipoUnita()) {
            LkTipoUnitaDTO dto = mapper.map(lk, LkTipoUnitaDTO.class);
            o.add(dto);
        }
        return o;
    }

    @Nullable
    @Override
    public List<LkTipoParticellaDTO> findAllTipoParticella() {
        List<LkTipoParticellaDTO> o = new ArrayList<LkTipoParticellaDTO>();
        for (LkTipoParticella lk : lookupDao.findAllLkTipoParticella()) {
            LkTipoParticellaDTO dto = mapper.map(lk, LkTipoParticellaDTO.class);
            o.add(dto);
        }
        return o;
    }

    @Nullable
    @Override
    public LkTipoSistemaCatastale findTipoSistemaCatastaleById(Integer id) {
        LkTipoSistemaCatastale lk = lookupDao.findIdTipoCatastaleById(id);
        return lk;
    }

    @Override
    public LkTipoParticella findTipoParticellaById(Integer idTipoParticella) {
        LkTipoParticella particella = lookupDao.findTipoParticellaById(idTipoParticella);
        return particella;
    }

    @Override
    public LkTipoUnita findTipoUnitaById(Integer idTipoUnita) {
        LkTipoUnita unita = lookupDao.findTipoUnitaById(idTipoUnita);
        return unita;
    }

    @Override
    public LkDug findDugById(Integer idDug) {
        LkDug dug = lookupDao.findDugById(idDug);
        return dug;
    }

    @Override
    public LkTipoCollegio findLkTipoCollegioById(Integer idAlbo) {
        LkTipoCollegio tipoCollegio = lookupDao.findLookupTipoCollegioById(idAlbo);
        return tipoCollegio;
    }

    @Override
    public LkTipoCollegio findLkTipoCollegioByCodCollegio(String codiceCollegio) {
        LkTipoCollegio tipoCollegio = lookupDao.findLookupTipoCollegioByCodiceCollegio(codiceCollegio);
        return tipoCollegio;
    }

    @Override
    public LkTipoParticella findTipoParticellaByCodice(String codice) {
        LkTipoParticella tipoParticella = lookupDao.findTipoParticellaByCod(codice);
        return tipoParticella;
    }

}
