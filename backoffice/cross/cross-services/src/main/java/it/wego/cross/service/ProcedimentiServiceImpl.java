package it.wego.cross.service;

import com.google.common.base.Strings;
import it.wego.cross.dto.PermessiEnteProcedimentoDTO;
import it.wego.cross.beans.ProcedimentoPermessiBean;
import it.wego.cross.beans.grid.GridProcedimenti;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.constants.ConfigurationConstants;
import it.wego.cross.dao.EntiDao;
import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.dao.ProcessiDao;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.ProcedimentoDTO;
import it.wego.cross.dto.TipoProcedimentoDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.ProcedimentiTesti;
import it.wego.cross.entity.Processi;
import it.wego.cross.entity.Utente;
import it.wego.cross.entity.view.ProcedimentiCollegatiView;
import it.wego.cross.entity.view.ProcedimentiLocalizzatiView;
import it.wego.cross.serializer.FilterSerializer;
import it.wego.cross.serializer.ProcedimentiSerializer;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcedimentiServiceImpl implements ProcedimentiService {

    @Autowired
    private ProcedimentiDao procedimentiDao;
    @Autowired
    private FilterSerializer filterSerializer;
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private EntiService entiService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private EntiDao entiDao;
    @Autowired
    private ProcessiDao processiDao;

    @Override
    public Long countProcedimentiByCodEnte(int codEnte, Filter filter) {
        Long count = procedimentiDao.countByCodEnte(codEnte, filter);
        return count;
    }

    @Override
    public ProcedimentoPermessiBean getProcedimentoPermessiBean(Integer codUtente, Integer codEnte, String codPermesso, Integer idProc, String lang) {
        ProcedimentoPermessiBean procedimento = procedimentiDao.getProcedimentoPermessiBean(codUtente, codEnte, codPermesso, idProc, lang);
        return procedimento;
    }

    @Override
    public Procedimenti findProcedimentoByIdProc(Integer idProc) {
        Procedimenti procedimento = procedimentiDao.findProcedimentoByIdProc(idProc);
        return procedimento;
    }

    @Override
    public ProcedimentiEnti findProcedimentiEnti(Integer idEnte, Integer idProc) {
        ProcedimentiEnti pe = procedimentiDao.findProcedimentiEntiByProcedimentoEnte(idEnte, idProc);
        return pe;
    }

    @Override
    public void eliminaProcedimentoEnte(ProcedimentiEnti procedimentoEnte) throws Exception {
        procedimentiDao.eliminaProcedimentoEnte(procedimentoEnte);
    }

    @Override
    public List<ProcedimentiLocalizzatiView> getProcedimentiLocalizzati(String lang, Filter filter) {
        return procedimentiDao.findAllProcedimentiLocalizzatiPaginated("it", filter);
    }

    @Override
    public List<ProcedimentiLocalizzatiView> getAllProcedimentiLocalizzati(String lang) {
        return procedimentiDao.findAllProcedimentiLocalizzati("it");
    }

    @Override
    public ProcedimentiLocalizzatiView getProcedimentoLocalizzato(String lang, Integer idProc) {
        return procedimentiDao.findProcedimentoLocalizzato(lang, idProc);
    }

    @Override
    public Long countProcedimentiLocalizzati(String it, Filter filter) {
        return procedimentiDao.countAllProcedimentiLocalizzati("it", filter);
    }

    @Override
    public List<ProcedimentoDTO> findProcedimentiByDescriptionAndIdEnte(String description, Integer idEnte) {
        List<ProcedimentiCollegatiView> procedimenti = procedimentiDao.findProcedimentiByDescriptionAndIdEnte(description, idEnte);
        List<ProcedimentoDTO> procedimentiDto = new ArrayList<ProcedimentoDTO>();
        for (ProcedimentiCollegatiView p : procedimenti) {
            ProcedimentoDTO d = ProcedimentiSerializer.serialize(p);
            procedimentiDto.add(d);
        }
        return procedimentiDto;
    }

    @Override
    public List<ProcedimentoDTO> findProcedimentiCollegatiByIdEnte(Integer idEnte) {
        List<ProcedimentiCollegatiView> procedimenti = procedimentiDao.findProcedimentiCollegatiByIdEnte(idEnte);
        List<ProcedimentoDTO> procedimentiDto = new ArrayList<ProcedimentoDTO>();
        for (ProcedimentiCollegatiView p : procedimenti) {
            ProcedimentoDTO d = ProcedimentiSerializer.serialize(p);
            procedimentiDto.add(d);
        }
        return procedimentiDto;
    }

    @Override
    public Procedimenti findProcedimentoByCodProc(String codProc) {
        return procedimentiDao.findProcedimentoByCodProc(codProc);
    }

    /**
     * ^^CS partendo dal comune trova tutti i ProcedimentiEnti associati
     *
     * @param comune
     * @return
     */
    @Override
    public List<ProcedimentiEnti> findProcedimentiEnti(ComuneDTO comune) {
        List<ProcedimentiEnti> procedimenti = procedimentiDao.findProcedimentiEnti(comune.getIdComune());
        return procedimenti;
    }


    @Override
    public List<ProcedimentiEnti> findProcedimentiEntiByDescProcedimento(ProcedimentoDTO procedimento) {
        List<ProcedimentiEnti> procedimenti = procedimentiDao.findProcedimentiEntiByDescProcedimento(procedimento.getDesProcedimento());
        return procedimenti;
    }

    @Override
    public List<ProcedimentiEnti> findProcedimentiEntiByDescProcedimento(ProcedimentoDTO procedimento, Utente connectedUser) {
        List<ProcedimentiEnti> procedimenti = procedimentiDao.findProcedimentiEntiByDescProcedimento(procedimento.getDesProcedimento(), connectedUser);
        return procedimenti;
    }

    @Override
    public List<ProcedimentiLocalizzatiView> findProcedimenti(Filter filter) {
        if (!Utils.e(filter.getOrderColumn())) {

            if (filter.getOrderColumn().equals("idProcedimento")) {
                filter.setOrderColumn("idProc");
            }
            if (filter.getOrderColumn().equals("desProcedimento")) {
                filter.setOrderColumn("desProc");
            }
        }
        List<ProcedimentiLocalizzatiView> procedimenti = procedimentiDao.findAllProcedimentiLocalizzatiPaginated("it", filter);
        return procedimenti;
    }

    @Override
    public List<ProcedimentiEnti> findAllProcedimentiEnti() {
        List<ProcedimentiEnti> procedimentiEnti = procedimentiDao.findAllProcedimentiEnti();
        return procedimentiEnti;
    }

    @Override
    public List<Enti> findEntiFromProcedimento(Integer idProc) {
        List<Enti> enti = procedimentiDao.findEntiFromProcedimento(idProc);
        return enti;
    }

    @Override
    public List<ProcedimentiLocalizzatiView> getProcedimentiLocalizzatiPaginate(String lang, Filter filter) {
        return procedimentiDao.findProcedimentiNonLocalizzatiAbilitati(lang, filter);
    }

    @Override
    public Long countProcedimentiLocalizzatiPaginate(String lang, Filter filter) {
        Long procedimentiNonAbilitati = procedimentiDao.countProcedimentiNonLocalizzatiAbilitati(lang, filter);
        return procedimentiNonAbilitati;
    }

    @Override
    public List<ProcedimentiTesti> findProcedimentiTestiByIdProc(Integer idProc) {
        List<ProcedimentiTesti> testi = procedimentiDao.findProcedimentiTestiByIdProc(idProc);
        return testi;
    }

    @Override
    public List<TipoProcedimentoDTO> findDistinctTipoProcedimento() {
        List<String> tipoProcedimentoString = procedimentiDao.findDistinctTipiProcedimento();
        List<TipoProcedimentoDTO> tipoProcedimento = new ArrayList<TipoProcedimentoDTO>();
        if (tipoProcedimentoString != null && !tipoProcedimentoString.isEmpty()) {
            for (String tipo : tipoProcedimentoString) {
                TipoProcedimentoDTO dto = new TipoProcedimentoDTO();
                dto.setDescription(tipo);
                dto.setId(tipo);
                tipoProcedimento.add(dto);
            }
        }
        return tipoProcedimento;
    }

    @Override
    public List<ProcedimentoDTO> getProcedimenti(Filter filter) {
        List<ProcedimentoDTO> lista = new ArrayList<ProcedimentoDTO>();
        List<ProcedimentiLocalizzatiView> listaDB = findProcedimenti(filter);
        for (ProcedimentiLocalizzatiView proDB : listaDB) {
            ProcedimentoDTO pro = ProcedimentiSerializer.serialize(proDB);
            lista.add(pro);
        }
        return lista;
    }

    @Override
    public GridProcedimenti getProcedimentiLocalizzati(HttpServletRequest request, JqgridPaginator paginator, String filterName) throws Exception {
        GridProcedimenti json = new GridProcedimenti();
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        String column = paginator.getSidx();
        String order = paginator.getSord();
        Integer firstRecord = (page * maxResult) - maxResult;
        Filter filter = filterSerializer.getSearchFilter(request);
        filter.setPage(page);
        filter.setLimit(maxResult);
        filter.setOffset(firstRecord);
        filter.setOrderColumn(column);
        filter.setOrderDirection(order);
        request.getSession().setAttribute(filterName, filter);
        Long countRighe = countProcedimentiLocalizzatiPaginate("it", filter);
        List<ProcedimentoDTO> procedimenti = cambiaDenominazione(getProcedimentiLocalizzatiPaginate("it", filter));
        int totalRecords = countRighe.intValue();
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(countRighe.intValue());
        json.setTotal(totalRecords);
        json.setRows(procedimenti);
        return json;
    }

    @Override
    public List<ProcedimentoDTO> cambiaDenominazione(List<ProcedimentiLocalizzatiView> searchProcedimenti) {
        List<ProcedimentoDTO> procedimenti = new ArrayList<ProcedimentoDTO>();
        for (ProcedimentiLocalizzatiView a : searchProcedimenti) {
            ProcedimentoDTO p = new ProcedimentoDTO();
            p.setIdProcedimento(a.getIdProc());
            p.setDesProcedimento(a.getDesProc());
            p.setCodProcedimento(a.getCodProc());
            procedimenti.add(p);
        }
        return procedimenti;
    }

    @Override
    public Long countProcedimenti(Filter filter, String localizzazione) throws Exception {
        Long count = countProcedimentiLocalizzati(localizzazione, filter);
        return count;
    }

    @Override
    public ProcedimentoDTO getByID(ProcedimentoDTO proc) throws Exception {
        if (proc != null && proc.getIdProcedimento() != null) {
            Procedimenti p = findProcedimentoByIdProc(proc.getIdProcedimento());
            ProcedimentoDTO procedimentoDTO = ProcedimentiSerializer.serialize(p);
            return procedimentoDTO;
        } else {
            return proc;
        }
    }

    @Override
    public List<ProcedimentoDTO> findEntiProcedimenti(ComuneDTO comune) {
        List<ProcedimentiEnti> procedimenti = findProcedimentiEnti(comune);
        List<ProcedimentoDTO> procedimentiDTO = new ArrayList<ProcedimentoDTO>();
        for (ProcedimentiEnti procedimento : procedimenti) {
            procedimentiDTO.add(ProcedimentiSerializer.serialize(procedimento));
        }
        return procedimentiDTO;
    }

    @Override
    public List<ProcedimentoDTO> findEntiProcedimenti(ProcedimentoDTO proc) {
        List<ProcedimentiEnti> procedimenti = findProcedimentiEntiByDescProcedimento(proc);
        List<ProcedimentoDTO> procedimentiDTO = new ArrayList<ProcedimentoDTO>();
        for (ProcedimentiEnti procedimento : procedimenti) {
            procedimentiDTO.add(ProcedimentiSerializer.serialize(procedimento));
        }
        return procedimentiDTO;
    }

    @Override
    public List<ProcedimentoDTO> findEntiProcedimenti(ProcedimentoDTO proc, Utente connectedUser) {
        List<ProcedimentiEnti> procedimenti = findProcedimentiEntiByDescProcedimento(proc, connectedUser);
        List<ProcedimentoDTO> procedimentiDTO = new ArrayList<ProcedimentoDTO>();
        for (ProcedimentiEnti procedimento : procedimenti) {
            procedimentiDTO.add(ProcedimentiSerializer.serialize(procedimento));
        }
        return procedimentiDTO;
    }

    @Override
    public List<ProcedimentoDTO> findProcedimenti(Integer idEnte, String query) {
        List<ProcedimentoDTO> procedimenti = findProcedimentiByDescriptionAndIdEnte(query, idEnte);
        return procedimenti;
    }

    @Override
    public Long contaProcedimentiLocalizzati(Filter filter, Integer idEnte) {
        Long count = countProcedimentiLocalizzati("it", filter);
        return count;
    }

    @Override
    public List<PermessiEnteProcedimentoDTO> getProcedimentiLocalizzati(Integer codEnte, Filter filter) {
        List<ProcedimentiLocalizzatiView> procedimentiLocalizzati = getProcedimentiLocalizzati("it", filter);
        List<PermessiEnteProcedimentoDTO> procedimenti = new ArrayList<PermessiEnteProcedimentoDTO>();
        for (ProcedimentiLocalizzatiView procedimento : procedimentiLocalizzati) {
            PermessiEnteProcedimentoDTO bean = utentiService.getPermesso(procedimento, codEnte);
            procedimenti.add(bean);
        }
        return procedimenti;
    }

    @Override
    public Procedimenti getProcedimento(ProcedimentoDTO dto) throws Exception {
        Procedimenti p = new Procedimenti();
        if (dto.getIdProcedimento() != null) {
            p = findProcedimentoByIdProc(dto.getIdProcedimento());
        } else if (dto.getCodProcedimento() != null) {
            p = findProcedimentoByCodProc(dto.getCodProcedimento());
        } else {
            //Non deve essere possibile questo caso
            throw new Exception("Non è stato specificato ne id ne codice del procedimento.");
        }
        return p;
    }

    @Override
    public ProcedimentoDTO serializeProcedimento(it.wego.cross.xml.Procedimento p) throws Exception {
        ProcedimentoDTO dto = new ProcedimentoDTO();
        dto.setCodEnteDestinatario(p.getCodEnteDestinatario());
        //TODO: FISSO AD ITALIANO
        dto.setCodLang("it");
        dto.setCodProcedimento(p.getCodProcedimento());
        dto.setDesEnteDestinatario(p.getDesEnteDestinatario());
        dto.setDesProcedimento(p.getDesProcedimento());
        if (p.getIdEnteDestinatario() != null) {
            dto.setIdEnteDestinatario(p.getIdEnteDestinatario().intValue());
        }
        dto.setIdProcedimento(p.getIdProcedimento());
        if (p.getTermini() != null) {
            dto.setTermini(p.getTermini().intValue());
        }

        return dto;
    }

    @Override
    public Procedimenti getProcedimento(String codiceProcedimento) {
        Procedimenti procedimento = procedimentiDao.findProcedimentoByCodProc(codiceProcedimento);
        return procedimento;
    }

    @Override
    public String getDescrizioneProcedimentoLingua(Integer lingua, Integer idProc) {
        ProcedimentiTesti pt = procedimentiDao.findProcedimentiTestiByIdProcAndLang(lingua, idProc);
        if (pt != null) {
            return pt.getDesProc();
        } else {
            return "La descrizione non è presente.";
        }
    }

    @Override
    public ProcedimentiEnti findProcedimentiEntyByKey(Integer idProcedimentoEnte) {
        ProcedimentiEnti procedimentoEnte = procedimentiDao.findProcedimentoEnteById(idProcedimentoEnte);
        return procedimentoEnte;
    }

    @Override
    public void eliminaPraticaProcedimento(PraticaProcedimenti praticaProcedimentoToDelete) throws Exception {
        procedimentiDao.delete(praticaProcedimentoToDelete);
    }

    @Override
    public ProcedimentiEnti requireProcedimentoEnte(Integer idProcedimento, Integer idEnte, Enti entePratica, LkComuni comunePratica) throws Exception {
        ProcedimentiEnti procedimentoEnte = procedimentiDao.findProcedimentiEntiByProcedimentoEnte(idEnte, idProcedimento);
        Enti enteTerzo = entiDao.findByIdEnte(idEnte);
        entePratica = entePratica != null ? entePratica : enteTerzo;

        String codProcessoDefault = configurationService.getCachedConfiguration(ConfigurationConstants.PROCESSO_DEFAULT, entePratica.getIdEnte(), null);
        //Creo il ProcedimentoEnte solo se è specificato il parametro con il procedimento di default
        if (!Strings.isNullOrEmpty(codProcessoDefault)) {
            //Non ho trovato il Procedimento-Ente, lo creo ...
            Processi processoDefault = processiDao.findProcessiByCodProcesso(codProcessoDefault);
            if (procedimentoEnte == null) {
                procedimentoEnte = new ProcedimentiEnti();
                Procedimenti procedimento = procedimentiDao.findProcedimentoByIdProc(idProcedimento);
                procedimentoEnte.setIdEnte(enteTerzo);
                procedimentoEnte.setIdProc(procedimento);
                procedimentoEnte.setIdProcesso(processoDefault);
                procedimentiDao.salvaProcedimentoEnte(procedimentoEnte);
            } else {
                //Il Procedimento-Ente non ha un processo, gli associo quello di default
                if (procedimentoEnte.getIdProcesso() == null) {
                    procedimentoEnte.setIdProcesso(processoDefault);
                    procedimentiDao.salvaProcedimentoEnte(procedimentoEnte);
                }
            }
        }
        return procedimentoEnte;
    }

    @Override
    public ProcedimentiEnti requireProcedimentoEnte(Integer idProcedimentoEnte, Enti entePratica, LkComuni comunePratica) throws Exception {
        ProcedimentiEnti procedimentoEnte = procedimentiDao.findProcedimentoEnteById(idProcedimentoEnte);
        return requireProcedimentoEnte(procedimentoEnte.getIdProc().getIdProc(), procedimentoEnte.getIdEnte().getIdEnte(), entePratica, comunePratica);
    }

	@Override
	public ProcedimentiEnti findIdUfficioByIdProcIdEnte(Integer idProcedimento, BigInteger idEnteDestinatario) {
		ProcedimentiEnti ufficio = procedimentiDao.findUfficiByProcEnte(idProcedimento, idEnteDestinatario.intValue());
		return ufficio;
	}

}
