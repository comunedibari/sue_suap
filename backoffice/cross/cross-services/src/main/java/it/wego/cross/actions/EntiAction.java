package it.wego.cross.actions;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import it.wego.cross.constants.Constants;
import it.wego.cross.dao.EntiDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.dto.EnteDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.exception.CrossException;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.ProcedimentiService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author CS
 */
@Component
public class EntiAction {

    @Autowired
    private EntiDao entiDao;
    @Autowired
    private EntiService entiService;
    @Autowired
    private ProcedimentiDao procedimentiDao;
    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private ProcedimentiService procedimentiService;

    @Transactional(rollbackFor = Exception.class)
    public void modificaEnte(EnteDTO enteDTO) throws Exception {
        Enti ente = entiService.findByCodEnte(enteDTO.getCodEnte());
        if (ente == null) {
            ente = entiService.findByIdEnte(enteDTO.getIdEnte());
        }
        ente.setCodiceFiscale(enteDTO.getCodiceFiscale());
        ente.setPartitaIva(enteDTO.getPartitaIva());
        ente.setIndirizzo(enteDTO.getIndirizzo());
        ente.setCap(enteDTO.getCap());
        ente.setCitta(enteDTO.getCitta());
        ente.setProvincia(enteDTO.getProvincia());
        ente.setTelefono(enteDTO.getTelefono());
        ente.setFax(enteDTO.getFax());
        ente.setDescrizione(enteDTO.getDescrizione());
        ente.setEmail(enteDTO.getEmail());
        ente.setIdEnte(enteDTO.getIdEnte());
        ente.setPec(enteDTO.getPec());
        ente.setCodiceIstat(enteDTO.getCodiceIstat());
        ente.setTipoEnte(enteDTO.getTipoEnte());
        ente.setCodiceCatastale(enteDTO.getCodiceCatastale());
        ente.setIdentificativoSuap(enteDTO.getIdentificativoSuap());
        ente.setCodEnte(enteDTO.getCodEnte());
        ente.setCodiceAoo(enteDTO.getCodiceAoo());
        String unitaOrganizzativa = enteDTO.getUnitaOrganizzativa();
        if ((unitaOrganizzativa != null) && (!"".equals(unitaOrganizzativa))) {
            ente.setUnitaOrganizzativa(enteDTO.getUnitaOrganizzativa());
        } else {
            String vecchiaUnitaOrganizzativa = ente.getUnitaOrganizzativa();
            if ((vecchiaUnitaOrganizzativa == null) && ("".equals(vecchiaUnitaOrganizzativa))) {
                ente.setUnitaOrganizzativa(enteDTO.getCodEnte());
            }
        }
        ente.setCodiceAmministrazione(enteDTO.getCodiceAmministrazione());
        salvaEnte(ente);
    }

    @Transactional
    public void collegaEnteComune(Integer idEnte, Integer idComune) throws Exception {
        entiDao.collegaComuneEnte(idEnte, idComune);
    }

    @Transactional
    public void cancellaRelazioneEnteComune(Integer idEnte, Integer idComune) throws Exception {
        entiDao.scollegaComuneEnte(idEnte, idComune);
    }

    private void salvaEnte(Enti ente) throws Exception {
        entiDao.update(ente);
    }

    @Transactional
    public void inserisciEnte(Enti ente) throws Exception {
        entiDao.insert(ente);
    }

    @Transactional(rollbackFor = Exception.class)
    public void eliminaEnte(Integer codEnte) throws Exception {
        Enti ente = entiService.findByIdEnte(codEnte);
        entiDao.delete(ente);
    }

    @Transactional(rollbackFor = Exception.class)
    public Enti scollegaPadre(Integer codEnte) throws Exception {
        Enti ente = entiService.findByIdEnte(codEnte);
        ente.setIdEntePadre(null);
        salvaEnte(ente);
        return ente;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer collegaPadre(Integer codEnte, Integer codEntePadre) throws Exception {
        Enti enteOrigine = entiService.findByIdEnte(codEnte);
        Enti entePadre = entiService.findByIdEnte(codEntePadre);
        enteOrigine.setIdEntePadre(entePadre);
        salvaEnte(enteOrigine);
        return codEnte;
    }

    @Transactional(rollbackFor = Exception.class)
    public void salvaProcedimentoEnte(ProcedimentiEnti procedimentoEnte) throws Exception {
        procedimentiDao.salvaProcedimentoEnte(procedimentoEnte);
    }

    @Transactional(rollbackFor = Exception.class)
    public void salvaProcedimentiEnte(int codEnte, int idProc) throws Exception {
        Enti ente = entiService.findByIdEnte(codEnte);

        Procedimenti procedimento = procedimentiService.findProcedimentoByIdProc(idProc);
        ProcedimentiEnti pe = procedimentiService.findProcedimentiEnti( codEnte, idProc);
        if (pe == null) {
            pe = new ProcedimentiEnti();
        }
        pe.setIdEnte(ente);
        pe.setIdProc(procedimento);
        procedimentiDao.salvaProcedimentoEnte(pe);
    }

    private void eliminaProcedimentoEnte(ProcedimentiEnti procedimentoEnte) throws Exception {
        procedimentiDao.eliminaProcedimentoEnte(procedimentoEnte);
    }

    @Transactional(rollbackFor = Exception.class)
    public void disabilitaProcedimentiEnti(int idEnte, int idProcedimento) throws Exception {
        ProcedimentiEnti pe = procedimentiService.findProcedimentiEnti(idEnte, idProcedimento);
        List<PraticaProcedimenti> praticaByProcedimentoEnte = praticaDao.findAllPraticaByProcedimentoEnte(idProcedimento, idEnte);
        if (!praticaByProcedimentoEnte.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (PraticaProcedimenti praticaProcedimenti : praticaByProcedimentoEnte) {
                sb.append(praticaProcedimenti.getPratica().getIdPratica()).append("; ");
            }
            throw new CrossException("Impossibile eliminare l'associazione tra Procedimento ed Ente se utilizzata per le pratiche: <br/>" + sb.toString() + "");
        }

        eliminaProcedimentoEnte(pe);
    }

    public String getAlberoEnteProcedimentiEnti(Integer idEnte, Boolean nonVisualizzareSuap) {
        final Gson gson = new Gson();
        List<Enti> enti = entiService.findAll(new Filter());
        {
            List<Enti> entiNonSuap = new ArrayList();
            for (Enti ente : enti) {
                if (!Constants.TIPO_ENTE_SUAP.equals(entiService.isSuap(ente.getIdEnte()))) {
                    entiNonSuap.add(ente);
                }
            }
            if (nonVisualizzareSuap) {
                enti = entiNonSuap;
            }
        }

        Enti enteSelezionato = entiService.findByIdEnte(idEnte);
        List<ProcedimentiEnti> procedimentiAbilitati = enteSelezionato.getEndoProcedimentiList();
        Set<Integer> procedimentiEntiId = Sets.newHashSet();
        for (ProcedimentiEnti proc : procedimentiAbilitati) {
            procedimentiEntiId.add(proc.getIdProcEnte());
        }

        List listaEnti = Lists.newArrayList();
        List listaRadiceAlbero = Lists.newArrayList(ImmutableMap.of(
                "title", "enti",
                "key", "enti",
                "isFolder", true,
                "expand", true,
                "children", listaEnti));

        for (Enti ente : enti) {
            List listaprocedimenti = Lists.newArrayList();

            for (ProcedimentiEnti procedimento : ente.getProcedimentiEntiList()) {
                listaprocedimenti.add(ImmutableMap.of(
                        "key", ente.getIdEnte() + "#" + procedimento.getIdProcEnte(),
                        "title", procedimentiService.getDescrizioneProcedimentoLingua(1, procedimento.getIdProc().getIdProc()),
                        "select", procedimentiEntiId.contains(procedimento.getIdProcEnte())));
            }
            listaEnti.add(ImmutableMap.of(
                    "key", ente.getIdEnte().toString(),
                    "title", ente.getDescrizione(),
                    "children", listaprocedimenti,
                    "unselectable", listaprocedimenti.isEmpty(),
                    "hideCheckbox", listaprocedimenti.isEmpty()));
        }
        return gson.toJson(listaRadiceAlbero);
    }

    @Transactional
    public void modificaEndoProcedimenti(Integer idEnte, List<Integer> idProcedimentiEnti) throws Exception {
        Enti ente = entiService.findByIdEnte(idEnte);
        List<ProcedimentiEnti> procedimentiEnti = new ArrayList();
        for (Integer idProcedimentoEnte : idProcedimentiEnti) {
            ProcedimentiEnti procedimentoEnte = procedimentiDao.findProcedimentoEnteById(idProcedimentoEnte);
            procedimentiEnti.add(procedimentoEnte);
        }
        ente.setEndoProcedimentiList(procedimentiEnti);
        salvaEnte(ente);
    }
    
}
