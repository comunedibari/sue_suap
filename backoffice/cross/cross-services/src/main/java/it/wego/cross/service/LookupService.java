/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dto.AttoreDTO;
import it.wego.cross.dto.CittadinanzaDTO;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.NazionalitaDTO;
import it.wego.cross.dto.RuoloAnagraficaDTO;
import it.wego.cross.dto.StatoPraticaDTO;
import it.wego.cross.dto.TipoIndirizzoDTO;
import it.wego.cross.dto.TipoQualificaAnagraficaDTO;
import it.wego.cross.dto.TipoRuoloDTO;
import it.wego.cross.dto.TipologiaScadenzeDTO;
import it.wego.cross.dto.dozer.LkTipoParticellaDTO;
import it.wego.cross.dto.dozer.LkTipoSistemaCatastaleDTO;
import it.wego.cross.dto.dozer.LkTipoUnitaDTO;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkDug;
import it.wego.cross.entity.LkFormeGiuridiche;
import it.wego.cross.entity.LkNazionalita;
import it.wego.cross.entity.LkStatiScadenze;
import it.wego.cross.entity.LkStatoPratica;
import it.wego.cross.entity.LkTipoCollegio;
import it.wego.cross.entity.LkTipoIndirizzo;
import it.wego.cross.entity.LkTipoParticella;
import it.wego.cross.entity.LkTipoQualifica;
import it.wego.cross.entity.LkTipoRuolo;
import it.wego.cross.entity.LkTipoSistemaCatastale;
import it.wego.cross.entity.LkTipoUnita;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author giuseppe
 */
@Service
public interface LookupService {

    public LkComuni findComuneById(Integer idComune);

    public LkTipoIndirizzo findByIdTipoIndirizzo(Integer idTipoIndirizzo);

    public LkTipoIndirizzo findByCodTipoIndirizzo(String codiceIndirizzo);

    public List<TipoQualificaAnagraficaDTO> findAllLkTipoQualifica();

    public List<RuoloAnagraficaDTO> findAllLkTipoRuolo();

    public LkTipoRuolo findLkTipoRuoloById(Integer idTipoRuolo);

    public LkTipoQualifica findLkTipoQualifica(Integer idTipoQualifica);

    public List<TipoIndirizzoDTO> findAllLkTipoIndirizzo();

    public LkStatoPratica findStatoPraticaByCodice(String codice);

    public List<LkStatoPratica> findStatiPraticaByGruppo(String gruppo);

    public List<StatoPraticaDTO> findAllStatiPratica();

    public List<AttoreDTO> findAllAttori();

    public List<TipologiaScadenzeDTO> findAllLkScadenze();

    public LkStatiScadenze findStatoScadenza(String idStatoScadenza);

    public LkComuni findComuneByCodCatastale(String codEnte);

    public List<LkFormeGiuridiche> findAllLkFormeGiuridiche();

    public List<LkNazionalita> findAllLkNazionalita();

    public LkTipoQualifica findLkTipoQualificaByCodQualificaAndCondizione(String codiceQualifica, String condizione);

    public LkTipoRuolo findLkTipoRuoloByCodRuolo(String r);

    public LkNazionalita findLkNazionalitaById(Integer valueOf);

    public List<LkStatoPratica> findStatoPraticaByGruppoDiversoDa(String gruppo);

    @Deprecated
    public Map<Integer, String> getTipoSistemaCatastale();
    
    public List<LkTipoSistemaCatastaleDTO> getTipoSistemaCatastaleDto();

    public List<TipoRuoloDTO> getTipoRuolo();

    public Map<Integer, String> getDug();

    @Deprecated
    public Map<Integer, String> getTipoParticella();
    
    public List<LkTipoParticellaDTO> getTipoParticellaDto();

    @Deprecated
    public Map<Integer, String> getTipoUnita();
    
    public List<LkTipoUnitaDTO> getTipoUnitaDto();

    public Map<Integer, String> getLookupTipoCollegio(String descrizione);

    public Map<Integer, String> getLookupFormaGiuridica(String descrizione);

    public ComuneDTO getComuneDTOFromNomePreciso(String nome);

    public CittadinanzaDTO getCittadinanzaFromDescrizionePrecisa(String descrizione);

    public NazionalitaDTO getNazionalitaFromDescrizionePrecisa(String descrizione);

    public ComuneDTO getComuneDTOFromId(Integer id);

    public CittadinanzaDTO getCittadinanzaFromId(Integer id);

    public NazionalitaDTO getNazionalitaFromId(Integer id);

    public List<LkTipoUnitaDTO> findAllTipoUnita();

    public List<LkTipoParticellaDTO> findAllTipoParticella();

    public LkTipoSistemaCatastale findTipoSistemaCatastaleById(Integer id);

    public LkTipoParticella findTipoParticellaById(Integer idTipoParticella);

    public LkTipoUnita findTipoUnitaById(Integer idTipoUnita);

    public LkDug findDugById(Integer idDug);

    public LkTipoCollegio findLkTipoCollegioById(Integer idAlbo);

    public LkTipoCollegio findLkTipoCollegioByCodCollegio(String alboProfessionale);
    
    public LkTipoParticella findTipoParticellaByCodice(String codice);
    
}
