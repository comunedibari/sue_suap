package it.wego.cross.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import it.wego.cross.beans.grid.GridEntiBean;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.dao.EntiDao;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.EnteDTO;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.dto.UtenteRuoloEnteDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.Utente;
import it.wego.cross.entity.UtenteRuoloEnte;
import it.wego.cross.serializer.EntiSerializer;
import it.wego.cross.serializer.FilterSerializer;

@Service
public class EntiServiceImpl implements EntiService {

    @Autowired
    private EntiDao entiDao;
    @Autowired
    private FilterSerializer filterSerializer;
    @Autowired
    private Mapper mapper;

    @Override
    public List<LkComuni> getComuniCollegati(Integer idEnte, Filter filter) {
        List<LkComuni> comuni = entiDao.findComuniCollegati(idEnte, filter);
        return comuni;
    }

    @Override
    public Long countComuniCollegati(Integer idEnte, Filter filter) {
        Long count = entiDao.countComuniCollegati(idEnte, filter);
        return count;
    }

    @Override
    public List<LkComuni> search(String descrizione, String codiceCatastale) {
        String description = "%" + (descrizione != null ? descrizione : "") + "%";
        String codCatastale = "%" + (codiceCatastale != null ? codiceCatastale : "") + "%";
        List<LkComuni> comuni = entiDao.genericComuniSearch(description, codCatastale);
        return comuni;
    }

    @Override
    public EnteDTO getEnteDTO(Integer idEnte) {
        Enti ente = entiDao.findByIdEnte(idEnte);
        EnteDTO enteDTO = EntiSerializer.serializer(ente);
        return enteDTO;
    }

    @Override
    public Long countAll(Filter filter) {
        Long count = entiDao.countAll(filter);
        return count;
    }

    @Override
    public List<Enti> findAll(Filter filter) {
        List<Enti> enti = entiDao.findAll(filter);
        return enti;
    }

    @Override
    public Enti findByIdEnte(Integer idEnte) {
        Enti ente = entiDao.findByIdEnte(idEnte);
        return ente;
    }

    @Override
    public Long countComuniFiltrati(Filter filter) {
        Long count = entiDao.countComuniFiltrati(filter);
        return count;
    }

    @Override
    public List<LkComuni> findComuniFiltrati(Filter filter) {
        List<LkComuni> comuni = entiDao.findComuniFiltrati(filter);
        return comuni;
    }

    @Override
    public Enti findByComune(ComuneDTO comune) {
        Enti ente = entiDao.findEnteByIDComune(comune.getIdComune());
        return ente;
    }

    @Override
    public Enti findByIdentificativoSuap(String identificativoSuap) {
        Enti ente = entiDao.findByIdentificativoSuap(identificativoSuap);
        return ente;
    }

    @Override
    public boolean isCrossUser(Enti ente) {
        Long procedimentiEnte = entiDao.countProcedimenti(ente);
        return procedimentiEnte != null && procedimentiEnte.intValue() > 0;
    }

    @Override
    public Enti findByCodEnte(String codEnte) {
        Enti ente = entiDao.findByCodEnte(codEnte);
        return ente;
    }

    @Override
    public EnteDTO findEnte(ComuneDTO comune) {
        Enti entedb = findByComune(comune);
        EnteDTO enteDTO = EntiSerializer.serializer(entedb);
        return enteDTO;
    }

    @Override
    public Enti getEnte(Integer idEnte) {
        Enti ente = entiDao.findByIdEnte(idEnte);
        return ente;
    }

    @Override
    public GridEntiBean getEnti(HttpServletRequest request, JqgridPaginator paginator, String filterName) throws Exception {
        GridEntiBean json = new GridEntiBean();
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
        Long countRighe = entiDao.countAllEntiByProcedimento(filter);
        List<EnteDTO> enti = cambiaFormato(entiDao.findAllEntiByProcedimento(filter));
        int totalRecords = countRighe.intValue();
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(countRighe.intValue());
        json.setTotal(totalRecords);
        json.setRows(enti);
        return json;
    }

    private List<EnteDTO> cambiaFormato(List<Enti> searchEnti) {
        List<EnteDTO> enti = new ArrayList<EnteDTO>();
        for (Enti a : searchEnti) {
            EnteDTO p = new EnteDTO();
            p.setIdEnte(a.getIdEnte());
            p.setDescrizione(a.getDescrizione());
            p.setCodEnte(a.getCodEnte());
            enti.add(p);
        }
        return enti;
    }

    @Override
    public List<EnteDTO> getEntiFromUtente(Utente utente) throws Exception {
        List<Enti> enti = null;
        if (utente.getUtenteRuoloEnteList() != null) {
            enti = new ArrayList<Enti>();
            for (UtenteRuoloEnte ruolo : utente.getUtenteRuoloEnteList()) {
                Enti ente = ruolo.getIdEnte();
                if (!enti.contains(ente)) {
                    enti.add(ente);
                }
            }
            List<EnteDTO> dto = null;
            if (enti != null) {
                dto = new ArrayList<EnteDTO>();
                for (Enti e : enti) {
                    EnteDTO ente = new EnteDTO();
                    ente.setIdEnte(e.getIdEnte());
                    ente.setDescrizione(e.getDescrizione());
                    dto.add(ente);
                }
            }
            return dto;
        } else {
            return null;
        }
    }

    public List<Enti> getListaEntiPerRicerca(UtenteDTO utente) {
        List<Enti> enti = new ArrayList<Enti>();
        List<Integer> listaEnti = new ArrayList<Integer>();
        for (UtenteRuoloEnteDTO UtentrRuoloEnteDTO : utente.getUtenteRuoloEnte()) {
            if (!listaEnti.contains(UtentrRuoloEnteDTO.getIdEnte())) {
                listaEnti.add(UtentrRuoloEnteDTO.getIdEnte());
            }
        }
        for (Integer idEnte : listaEnti) {
            enti.add(getEnte(idEnte));
        }
        Collections.sort(enti, new Comparator<Enti>() {

			@Override
			public int compare(Enti o1, Enti o2) {
				String descr1 = o1.getDescrizione();
				String descr2 = o2.getDescrizione();
				if( !StringUtils.hasText(descr1) )
				{
					return 1;
				}
				else if( !StringUtils.hasText(descr2) )
				{
					return 1;
				}
				else if( !StringUtils.hasText(descr1) && !StringUtils.hasText(descr2) )
				{
					return 0;
				}
				return descr1.toLowerCase().compareTo(descr2.toLowerCase());
			}
		});
        return enti;
    }

    public String isSuap(Integer idEnte) {
        Enti ente = entiDao.findByIdEnte(idEnte);
        return ente.getTipoEnte();
    }

    @Override
    public Enti findByUnitaOrganizzativa(String unitaOrganizzativa) {
        Enti ente = entiDao.findByUnitaOrganizzativa(unitaOrganizzativa);
        return ente;
    }

    @Override
    public it.wego.cross.xml.Enti serializeEnti(it.wego.cross.entity.Pratica pratica) {
        List<Integer> esistenza = new ArrayList<Integer>();
        it.wego.cross.xml.Enti listaEnti = null;
        for (PraticaProcedimenti praticaProcedimento : pratica.getPraticaProcedimentiList()) {
            Enti ente = praticaProcedimento.getEnti();
            if (!esistenza.contains(ente.getIdEnte())) {
                esistenza.add(ente.getIdEnte());
                it.wego.cross.xml.Ente enteXml = mapper.map(ente, it.wego.cross.xml.Ente.class);
                if (listaEnti == null) {
                    listaEnti = new it.wego.cross.xml.Enti();
                }
                listaEnti.getEnte().add(enteXml);
            }
        }
        return listaEnti;
    }

    @Override
    public List<EnteDTO> searchEntiLike(String ricerca) {
        List<Enti> enti = entiDao.searchEntiLike(ricerca);
        List<EnteDTO> lista = new ArrayList<EnteDTO>();
        for (Enti a : enti) {
            EnteDTO eDTO = mapper.map(a, EnteDTO.class);
            lista.add(eDTO);
        }
        return lista;
    }
}
