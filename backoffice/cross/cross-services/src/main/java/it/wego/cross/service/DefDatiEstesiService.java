package it.wego.cross.service;

import it.wego.cross.beans.grid.GridDefDatiEstesiBean;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.dto.dozer.DefDatiEstesiDTO;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface DefDatiEstesiService {

    public DefDatiEstesiDTO getDefDatiEstesi(Integer idDatoEsteso);

    public GridDefDatiEstesiBean findDefDatiEstesi(HttpServletRequest request, JqgridPaginator paginator, String filterName) throws Exception;
    
    public DefDatiEstesiDTO getDefDatiEstesiUnique(DefDatiEstesiDTO defDatiEstesiDTO);

}
