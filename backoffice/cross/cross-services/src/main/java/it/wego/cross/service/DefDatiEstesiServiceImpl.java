package it.wego.cross.service;

import it.wego.cross.beans.grid.GridDefDatiEstesiBean;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.dao.DefDatiEstesiDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dto.dozer.DefDatiEstesiDTO;
import it.wego.cross.dto.filters.GestioneDatiEstesiFilter;
import it.wego.cross.entity.DefDatiEstesi;
import it.wego.cross.entity.LkTipoOggetto;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefDatiEstesiServiceImpl implements DefDatiEstesiService {

    @Autowired
    private DefDatiEstesiDao defDatiEstesiDao;
    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private Mapper mapper;

    @Override
    public DefDatiEstesiDTO getDefDatiEstesi(Integer idDatoEsteso) {
        DefDatiEstesi de = defDatiEstesiDao.findById(idDatoEsteso);
        DefDatiEstesiDTO dto = mapper.map(de, DefDatiEstesiDTO.class);
        dto.setIdTipoOggetto(de.getIdTipoOggetto().getIdTipoOggetto());
        dto.setCodTipoOggetto(de.getIdTipoOggetto().getCodTipoOggetto());
        dto.setDesTipoOggetto(de.getIdTipoOggetto().getDesTipoOggetto());
        return dto;
    }

    @Override
    public GridDefDatiEstesiBean findDefDatiEstesi(HttpServletRequest request, JqgridPaginator paginator, String filterName) throws Exception {
        GridDefDatiEstesiBean json = new GridDefDatiEstesiBean();
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        String column = paginator.getSidx();
        String order = paginator.getSord();
        Integer firstRecord = (page * maxResult) - maxResult;
        GestioneDatiEstesiFilter filter = getSearchDatiEstesiFilter(request);
        filter.setPage(page);
        filter.setLimit(maxResult);
        filter.setOffset(firstRecord);
        filter.setOrderColumn(column);
        filter.setOrderDirection(order);
        request.getSession().setAttribute(filterName, filter);
        Long countRighe = defDatiEstesiDao.countAllDefDatiEstesi(filter);
        List<DefDatiEstesi> lista = defDatiEstesiDao.findAllDefDatiEstesi(filter);
        List<DefDatiEstesiDTO> datiEstesi = new ArrayList<DefDatiEstesiDTO>();
        if (lista != null && !lista.isEmpty()) {
            for (DefDatiEstesi d : lista) {
                DefDatiEstesiDTO dto = mapper.map(d, DefDatiEstesiDTO.class);
                dto.setIdTipoOggetto(d.getIdTipoOggetto().getIdTipoOggetto());
                dto.setCodTipoOggetto(d.getIdTipoOggetto().getCodTipoOggetto());
                dto.setDesTipoOggetto(d.getIdTipoOggetto().getDesTipoOggetto());
                datiEstesi.add(dto);
            }
        }
        int totalRecords = countRighe.intValue();
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(countRighe.intValue());
        json.setTotal(totalRecords);
        json.setRows(datiEstesi);
        return json;
    }

    @Override
    public DefDatiEstesiDTO getDefDatiEstesiUnique(DefDatiEstesiDTO defDatiEstesiDTO) {
        LkTipoOggetto lk = null;
        if (defDatiEstesiDTO.getIdTipoOggetto()!=null){
            lk = lookupDao.findTipoOggettoById(defDatiEstesiDTO.getIdTipoOggetto());
        }
        if (lk == null) {
            lk = lookupDao.findTipoOggettoByCodice(defDatiEstesiDTO.getCodTipoOggetto());
        }
        DefDatiEstesi de = defDatiEstesiDao.findByTipoOggettoIstanzaCodice(lk,defDatiEstesiDTO.getIdIstanza(),defDatiEstesiDTO.getCodValue());
        DefDatiEstesiDTO dto = mapper.map(de, DefDatiEstesiDTO.class);
        dto.setIdTipoOggetto(de.getIdTipoOggetto().getIdTipoOggetto());
        dto.setCodTipoOggetto(de.getIdTipoOggetto().getCodTipoOggetto());
        dto.setDesTipoOggetto(de.getIdTipoOggetto().getDesTipoOggetto());
        return dto;
    }

    private GestioneDatiEstesiFilter getSearchDatiEstesiFilter(HttpServletRequest request) throws ParseException {
        String codTipoOggetto = request.getParameter("search_codTipoOggetto");
        String idIstanza = request.getParameter("search_idIstanza");
        String codValue = request.getParameter("search_codValue");

        GestioneDatiEstesiFilter filter = new GestioneDatiEstesiFilter();

        filter.setIdIstanza(idIstanza);
        filter.setCodTipoOggetto(codTipoOggetto);
        filter.setCodValue(codValue);
        return filter;

    }
}
